package com.connect.socketsandroid.net;

import android.os.AsyncTask;
import android.util.Log;

import com.connect.socketsandroid.interfaces.ITcpListener;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by cezar on 2/6/17.
 */

public class ConnectBridgeAsyncTask extends AsyncTask<Void,Void,Void>
{
    private InetAddress serverAddr = null;
    private ITcpListener _OuvinteTCP;
    private TCPBridge vo_Pkt;

    public ConnectBridgeAsyncTask(ITcpListener aTCPListener, TCPBridge aTcpBridge)
    {
        _OuvinteTCP = aTCPListener;
        vo_Pkt = aTcpBridge;
    }
    @Override
    protected Void doInBackground(Void... voids)
    {
        try {
            serverAddr = InetAddress.getByName(NetParameters.ipESP8266);
            vo_Pkt.set_socket( new Socket(serverAddr, NetParameters.portaESP8266));

            vo_Pkt.set_mBufferOut(new PrintWriter(new BufferedWriter(
                    new OutputStreamWriter(vo_Pkt.get_socket().getOutputStream())), true));

            vo_Pkt.set_mBufferIn(new BufferedReader(
                    new InputStreamReader(vo_Pkt.get_socket().getInputStream())));

        }catch (UnknownHostException ex){
            _OuvinteTCP.onExceptionOcorred(ex.getMessage());
        }catch (IOException ex){
            _OuvinteTCP.onExceptionOcorred(ex.getMessage());
        }
        return null;
    }
    protected void onPostExecute(Void result)
    {
       if(vo_Pkt.get_socket() == null)
           _OuvinteTCP.onFailToConnect();
       else {
           _OuvinteTCP.onBridgeIsCreated(vo_Pkt);
       }
    }
}
