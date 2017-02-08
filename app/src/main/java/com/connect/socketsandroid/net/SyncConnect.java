package com.connect.socketsandroid.net;

import com.connect.socketsandroid.interfaces.IConnnectType;
import com.connect.socketsandroid.interfaces.ITcpListener;
import java.io.IOException;
/**
 * Created by cezar on 2/6/17.
 */
public class SyncConnect implements IConnnectType
{
    private TCPBridge _brigdeConn;
    public SyncConnect(TCPBridge apckConn)
    {
        _brigdeConn = apckConn;
    }
    @Override
    public void onSendMensage(ITcpListener aTcp, String aMsg)
    {
        withBridge(aTcp, _brigdeConn,aMsg);
    }
    private void withBridge(ITcpListener aTcp, TCPBridge _bridgeTCP, String aMsg)
    {
        char[] vetAlgo = new char[NetParameters.sizeOfResponse];
        try {
            _bridgeTCP.get_mBufferOut().println(aMsg);
            _bridgeTCP.get_mBufferOut().flush();
            _bridgeTCP.get_mBufferIn().read(vetAlgo);

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
