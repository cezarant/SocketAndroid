package com.connect.socketsandroid.net;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by cezar on 2/6/17.
 */

public class TCPBridge
{
    private PrintWriter _mBufferOut;
    private BufferedReader _mBufferIn;
    private Socket _socket;

    public PrintWriter get_mBufferOut() {
        return _mBufferOut;
    }

    public void set_mBufferOut(PrintWriter _mBufferOut) {
        this._mBufferOut = _mBufferOut;
    }

    public BufferedReader get_mBufferIn() {
        return _mBufferIn;
    }

    public void set_mBufferIn(BufferedReader _mBufferIn) {
        this._mBufferIn = _mBufferIn;
    }

    public Socket get_socket() {
        return _socket;
    }

    public void set_socket(Socket _socket) {
        this._socket = _socket;
    }
}
