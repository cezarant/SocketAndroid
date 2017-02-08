package com.connect.socketsandroid.interfaces;

/**
 * Created by cezar on 2/6/17.
 */

public interface IConnnectType
{
    public void onSendMensage(ITcpListener aTcp, String sMsg);
    public void onReceiveMensage(String msg, ITcpListener aTcp);
}
