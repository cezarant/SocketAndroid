package com.connect.socketsandroid.net;

import android.os.AsyncTask;

import com.connect.socketsandroid.interfaces.IConnnectType;
import com.connect.socketsandroid.interfaces.ITcpListener;

/**
 * Created by cezar on 2/6/17.
 */
public class AsyncSendSocketTask extends AsyncTask<Void,Void,Void>
{
    private ITcpListener _oTcpListener;
    private IConnnectType _genericConn;

    public AsyncSendSocketTask(ITcpListener aTCP, IConnnectType aGenericConn)
    {
        _oTcpListener =  aTCP;
        _genericConn = aGenericConn;
    }
    @Override
    protected Void doInBackground(Void... voids)
    {
        try {
            _genericConn.onSendMensage(_oTcpListener,"Teste");
        }catch (NullPointerException ex){
            _oTcpListener.onExceptionOcorred(ex.getMessage());
        }
        return null;
    }
}
