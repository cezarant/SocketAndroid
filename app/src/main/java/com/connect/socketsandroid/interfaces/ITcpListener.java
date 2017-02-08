package com.connect.socketsandroid.interfaces;

import com.connect.socketsandroid.net.TCPBridge;

public interface ITcpListener
{
	public void onTCPMessageRecieved(String message);
	public void onBridgeIsCreated(TCPBridge vo_pck);
	public void onExceptionOcorred(String message);
	public void onFailToConnect();
}
