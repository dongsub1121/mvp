package com.mpas.mvp.management.ui.payments.pays;

import java.nio.ByteBuffer;

public class NiceProtocol {

    byte STX;
    byte[] LEN;
    byte KIND; //일반 승인
    byte[] VER;
    byte[] DLL;
    byte NETWORK;
    byte FILLER;
    byte[] DATA; // header+pay
    byte ETX;

    public NiceProtocol(byte[] data) {
        this.STX = (byte) 0x02;
        this.LEN = String.valueOf(data.length+21).getBytes();
        this.KIND = (byte) 0x1212;
        this.VER = "MA1".getBytes();
        this.DLL = "1.01.001".getBytes();
        this.NETWORK = (byte) 32;
        this.FILLER = (byte) 32;
        this.DATA = data;
        this.ETX = (byte) 0x03;
    }

    public byte[] get() {

        ByteBuffer byteBuffer = ByteBuffer.allocate(4095);
        this.LEN = String.valueOf(DATA.length+21).getBytes();

        byteBuffer.put(STX);
        byteBuffer.put(LEN);
        byteBuffer.put(KIND);
        byteBuffer.put(VER);
        byteBuffer.put(DLL);
        byteBuffer.put(NETWORK);
        byteBuffer.put(FILLER);
        byteBuffer.put(DATA);
        byteBuffer.put(ETX);

        byteBuffer.flip();

        return byteBuffer.array();
    }

    public Boolean setData (byte[] data) {

        try {
            this.DATA = data;
        }catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }
}
