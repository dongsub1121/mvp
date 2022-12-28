package com.mpas.mvp.management.ui.payments;

import android.util.Log;
import com.mpas.mvp.management.ui.payments.pays.PayMart;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.HashMap;


public class PayRequest {

    public interface OnVanResponse {
        void onResponse (HashMap<String,String> responseMap);
    }

    private OnVanResponse onVanResponse;
    PayRequest.SocketThread thread;
    PayMart payMart;

    public void execute(byte[] protocol, OnVanResponse vanResponse, PayMart mart){
        Log.e("VanRequest","exe");
        this.onVanResponse = vanResponse;
        this.payMart = mart;

        try {

            Log.e("DATA",new String(protocol,"EUC-KR"));

            thread = new PayRequest.SocketThread(protocol);
            thread.start();

        } catch (NullPointerException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }


    public void setResponseMap(byte[] response){
        try{
            if(thread != null){
                thread.interrupt();
            } //TODO pay 응답 개선
            onVanResponse.onResponse(payMart.getResAuthData(response));
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
        PayMart payMart;

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
                e.printStackTrace();
            }
        }
    }
}
