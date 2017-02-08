package com.connect.socketsandroid.net;

import android.util.Log;

import com.connect.socketsandroid.interfaces.IConnnectType;
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
 * Created by cezar on 2/8/17.
 */
public class WithOutBridgeConnectType implements IConnnectType
{
    private PrintWriter out;
    private BufferedReader in;
    private String sResult;
    private boolean mRun = true;
    @Override
    public void onSendMensage(ITcpListener aTcp, String sMsg)
    {
        InetAddress serverAddr = null;
        Socket socket = null;
        try
        {
            serverAddr = InetAddress.getByName(NetParameters.ipESP8266);
            socket = new Socket(serverAddr,NetParameters.portaESP8266);

            out = new PrintWriter(new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream())), true);

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out.println(sMsg);
            out.flush();

            while (mRun)
            {
                char[] vet = new char[NetParameters.sizeOfResponse];
                sResult = String.valueOf(in.read(vet));
                if (sResult != null)
                {
                    onReceiveMensage(sResult,aTcp);
                    mRun = false;
                }
            }
        } catch (UnknownHostException e1)
        {
            aTcp.onExceptionOcorred(e1.getMessage());
        } catch (IOException e1) {
            aTcp.onExceptionOcorred(e1.getMessage());
        }finally{
            try{
                socket.close();
            }catch (IOException e)
            {
                aTcp.onExceptionOcorred(e.getMessage());
            }
        }
    }
    @Override
    public void onReceiveMensage(String msg, ITcpListener aTcp)
    {
        aTcp.onTCPMessageRecieved(msg);
    }
}
