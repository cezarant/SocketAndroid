package com.connect.socketsandroid.net;

import com.connect.socketsandroid.interfaces.IConnnectType;
import com.connect.socketsandroid.interfaces.ITcpListener;
import com.koushikdutta.async.AsyncServer;
import com.koushikdutta.async.AsyncSocket;
import com.koushikdutta.async.ByteBufferList;
import com.koushikdutta.async.DataEmitter;
import com.koushikdutta.async.Util;
import com.koushikdutta.async.callback.CompletedCallback;
import com.koushikdutta.async.callback.ConnectCallback;
import com.koushikdutta.async.callback.DataCallback;

import java.net.InetSocketAddress;
/**
 * Created by cezar on 2/8/17.
 */
public class AssincronnousConnectType implements IConnnectType
{
    @Override
    public void onSendMensage(final ITcpListener aTcp, final String sMsg) {
        AsyncServer.getDefault().connectSocket(new InetSocketAddress(NetParameters.ipESP8266,
                        NetParameters.portaESP8266),
                new ConnectCallback() {
                    @Override
                    public void onConnectCompleted(Exception ex, final AsyncSocket socket) {
                        try {
                            Util.writeAll(socket, "Hello Server".getBytes(), new CompletedCallback() {
                                @Override
                                public void onCompleted(Exception ex) {
                                    System.out.println("Conectado com sucesso!");
                                }
                            });

                            socket.setDataCallback(new DataCallback() {
                                @Override
                                public void onDataAvailable(DataEmitter emitter, ByteBufferList bb) {
                                    onReceiveMensage(new String(bb.getAllByteArray()), aTcp);
                                }
                            });

                            socket.setClosedCallback(new CompletedCallback() {
                                @Override
                                public void onCompleted(Exception ex) {
                                    System.out.println("Successfully closed connection");
                                }
                            });

                            socket.setEndCallback(new CompletedCallback() {
                                @Override
                                public void onCompleted(Exception ex) {
                                    System.out.println("Successfully end connection");
                                }
                            });
                        } catch (Exception exc) {
                            System.out.println("Erro" + exc.getMessage());
                        }
                    }
                });
    }

    @Override
    public void onReceiveMensage(String msg, ITcpListener bTCP) {
        bTCP.onTCPMessageRecieved(msg);
    }
}
