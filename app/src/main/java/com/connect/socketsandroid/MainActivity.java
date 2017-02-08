package com.connect.socketsandroid;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.connect.socketsandroid.interfaces.ITcpListener;
import com.connect.socketsandroid.net.ConnectBridgeAsyncTask;
import com.connect.socketsandroid.net.AsyncSendSocketTask;
import com.connect.socketsandroid.net.NetParameters;
import com.connect.socketsandroid.net.TCPBridge;
import com.connect.socketsandroid.net.BridgeConnectType;

import java.util.Random;

public class MainActivity extends Activity implements ITcpListener
{
    private Random numRand;
    private Button btnSend;
    private TextView txtExplicacao;
    private TextView txtResultado;
    private TCPBridge principalBridge;
    private Handler handTmConnect;
    private int contConnectedTry = 0;
    private boolean isBridgeConnected = false;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        numRand = new Random();
        txtExplicacao = (TextView) findViewById(R.id.txtExplicacao);
        txtResultado = (TextView) findViewById(R.id.txtResult);
        btnSend = (Button) findViewById(R.id.btnEnviar);
    }
    private void initTimerToConnect()
    {
        txtExplicacao.setText(getResources().getString(R.string.wait));
        // Start a timer to try connect
        handTmConnect = new Handler();
        handTmConnect.removeCallbacks(vh_tryConnectAtESP8266);
        handTmConnect.postDelayed(vh_tryConnectAtESP8266, NetParameters.intervalToTry);
    }
    private Runnable vh_tryConnectAtESP8266 = new Runnable()
    {
        public void run()
        {
            makeTheBrideAsync();
        }
    };
    private void makeTheBrideAsync()
    {
        new ConnectBridgeAsyncTask(this,new TCPBridge()).execute();
    }
    public void sendSocket(View v)
    {
        if(!isBridgeConnected)
            initTimerToConnect();
        else
            new AsyncSendSocketTask(this,new BridgeConnectType(principalBridge)).execute();
    }
    /**********************************************************************************************/
    /* Implementation of methods */
    @Override
    public void onBridgeIsCreated(TCPBridge vo_Bridge)
    {
        principalBridge = vo_Bridge;
        handTmConnect.removeCallbacks(vh_tryConnectAtESP8266);
        runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                txtExplicacao.setText(getResources().getString(R.string.clickToSend));
                btnSend.setText(getResources().getString(R.string.Send));
                isBridgeConnected = true;
            }
        });
    }
    @Override
    public void onExceptionOcorred(final String message)
    {
        runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                txtExplicacao.setText(message);
            }
        });
    }
    @Override
    public void onFailToConnect()
    {
        runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {

                if (contConnectedTry < NetParameters.numberToTryConnect)
                {
                    handTmConnect.postDelayed(vh_tryConnectAtESP8266, NetParameters.intervalToTry);
                    contConnectedTry++;
                    txtExplicacao.setText(getResources().getString(R.string.tryToConnect) + " - " +  contConnectedTry);
                }else{
                    contConnectedTry = 0;
                    txtExplicacao.setText(getResources().getString(R.string.endOfTry));
                    handTmConnect.removeCallbacks(vh_tryConnectAtESP8266);
                }
            }
        });
    }
    @Override
    public void onTCPMessageRecieved(final String message)
    {
        runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                // if the message is equal, the random number show the difference
                txtResultado.setText(numRand.nextDouble() + message);
            }
        });
    }
    /**********************************************************************************************/
}
