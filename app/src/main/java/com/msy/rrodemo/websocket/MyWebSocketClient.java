package com.msy.rrodemo.websocket;

import android.util.Log;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.Map;

import static com.msy.rrodemo.ui.ChatActivity.CLOSE_SUCCESS_CODE;

/**
 * Created by Administrator on 2019/12/16/016.
 */

public class MyWebSocketClient extends WebSocketClient {

    private static final String TAG = MyWebSocketClient.class.getSimpleName();

    public MyWebSocketClient(URI serverUri) {
        super(serverUri);
    }

    public MyWebSocketClient(URI serverUri, Draft protocolDraft) {
        super(serverUri, protocolDraft);
    }

    public MyWebSocketClient(URI serverUri, Map<String, String> httpHeaders) {
        super(serverUri, httpHeaders);
    }

    public MyWebSocketClient(URI serverUri, Draft protocolDraft, Map<String, String> httpHeaders) {
        super(serverUri, protocolDraft, httpHeaders);
    }

    public MyWebSocketClient(URI serverUri, Draft protocolDraft, Map<String, String> httpHeaders, int connectTimeout) {
        super(serverUri, protocolDraft, httpHeaders, connectTimeout);
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        Log.d(TAG, "open");
    }

    @Override
    public void onMessage(String message) {
        Log.d(TAG, "message");
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        if (CLOSE_SUCCESS_CODE != code){//非正常关闭
            reconnect();
        }
        Log.d(TAG, "close");
    }

    @Override
    public void onError(Exception ex) {
        Log.d(TAG, "error");
    }
}
