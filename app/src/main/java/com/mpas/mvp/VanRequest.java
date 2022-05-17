package com.mpas.mvp;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class VanRequest {

    public interface OnVanResponse {
        void onResponse (HashMap<String,String> responseMap);
    }

    private OnVanResponse onVanResponse;
    LinkedHashMap<String, byte[]> authMap;
    LinkedHashMap<String, byte[]> headerMap;
    SocketThread thread;

    public void execute(PayUtil.AuthItem item, OnVanResponse vanResponse){
        Log.e("VanRequest","exe");
        try {
            this.onVanResponse = vanResponse;
            this.authMap = PayUtil.getMap(item.pay);
            this.headerMap = Header.getHeaderMap();
            String jobCode = item.getJobCode();
            headerMap.put("JOB_CODE", jobCode.getBytes());
            Log.e("Send authItem",item.getJobCode() );
            authMap.put("AMOUNT", String.format("%012d", item.getAmount()).getBytes());

            if (jobCode.equals("8051")) {
                authMap.put("AUTH_DATE", item.getAuthDate().getBytes());
                authMap.put("AUTH_NUM", item.getAuthNum().getBytes());
            }

            byte[] auth = PayUtil.getMapToByte(authMap);
            Log.e("auth",new String(auth,"EUC-KR"));
            byte[] header = PayUtil.getMapToByte(headerMap);
            Log.e("header",new String(header,"EUC-KR"));

            ByteBuffer byteBuffer = ByteBuffer.allocate(4200);
            byteBuffer.put((byte) 0x02);
            byteBuffer.put(("0" + (21 + auth.length + header.length)).getBytes());
            byteBuffer.put((byte) 0x12);
            byteBuffer.put((byte) 0x12);
            byteBuffer.put("MA1".getBytes());
            byteBuffer.put("1.01.001".getBytes());
            byteBuffer.put((byte) 32);
            byteBuffer.put((byte) 32);
            byteBuffer.put(header);
            byteBuffer.put(auth);
            byteBuffer.put((byte) 0x03);
            byteBuffer.flip();
            byte[] tmp = new byte[byteBuffer.limit()];
            byteBuffer.get(tmp, 0, byteBuffer.limit());
            Log.e("DATA",new String(tmp,"EUC-KR"));

            thread = new SocketThread(byteBuffer.array());
            thread.start();

        } catch (NullPointerException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }


    public void setResponseMap(byte[] response){
        try{
            if(thread != null){
                thread.interrupt();
            }
            onVanResponse.onResponse(PayUtil.getResAuthData(ZeroPay.setResItem(), response));
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    class SocketThread extends Thread {

        String host = "211.48.96.28";
        int port = 11722;
        byte[] data;
        DataInputStream dataInputStream;
        DataOutputStream dataOutputStream;
        Socket socket;

        public SocketThread(byte[] data) {
            this.data = data;
        }

        @Override
        public void run() {
            Log.e("SocketThread","run");
            try {
                socket = new Socket(host, port);
                System.out.println(socket.isConnected());

                dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
                dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
                dataOutputStream.write(data);
                dataOutputStream.flush();

                byte[] res = new byte[1024];
                byte[] bLen = new byte[4];

                dataInputStream.read(res);

                System.arraycopy(res, 1, bLen, 0, 4);

                int len = Integer.parseInt(new String(bLen));
                byte[] data = new byte[len];

                System.arraycopy(res, 115, data, 0, len - 116);
                Log.e("res",new String(data));
                setResponseMap(data);

            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                // 소켓 종료.
                if (dataInputStream != null)
                    dataInputStream.close();

                if (dataOutputStream != null)
                    dataOutputStream.close();

                if (socket != null)
                    socket.close();

            } catch (Exception e) {
                // TODO : process exceptions.
            }
        }
    }

}
