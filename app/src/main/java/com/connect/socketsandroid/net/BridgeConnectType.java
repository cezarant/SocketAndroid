package com.connect.socketsandroid.net;

import com.connect.socketsandroid.interfaces.IConnnectType;
import com.connect.socketsandroid.interfaces.ITcpListener;
import java.io.IOException;
/**
 * Created by cezar on 2/6/17.
 */
public class BridgeConnectType implements IConnnectType
{
    private TCPBridge _brigdeConn;
    public BridgeConnectType(TCPBridge apckConn)
    {
        _brigdeConn = apckConn;
    }
    @Override
    public void onSendMensage(ITcpListener aTcp, String aMsg)
    {
        char[] vetAlgo = new char[NetParameters.sizeOfResponse];
        try {
            _brigdeConn.get_mBufferOut().println(aMsg);
            _brigdeConn.get_mBufferOut().flush();
            _brigdeConn.get_mBufferIn().read(vetAlgo);

            onReceiveMensage(String.valueOf(vetAlgo),aTcp);
        } catch (IOException e){
            aTcp.onExceptionOcorred(e.getMessage());
        } catch (NullPointerException e){
            aTcp.onExceptionOcorred(e.getMessage());
        } catch (Exception e){
            aTcp.onExceptionOcorred(e.getMessage());
        }
    }
    @Override
    public void onReceiveMensage(String msg, ITcpListener bTcpListener)
    {
        bTcpListener.onTCPMessageRecieved(msg);
    }
}
