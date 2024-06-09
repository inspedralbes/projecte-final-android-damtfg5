package com.example.projectofinal;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class SocketManager {
    private static final String SERVER_URL = "http://192.168.1.17:3005/";
    private static Socket mSocket;
    private SocketManager() {
        try {
            mSocket = IO.socket(SERVER_URL);
            mSocket.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
    public static Socket getInstance() {
        if (mSocket == null) {
            new SocketManager();
        }
        return mSocket;
    }
}
