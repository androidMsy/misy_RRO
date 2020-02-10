package com.msy.rrodemo.service;

import android.accounts.NetworkErrorException;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.msy.rrodemo.contacts.UrlContacts;
import com.msy.rrodemo.ui.ChatActivity;
import com.msy.rrodemo.utils.SPUtils;
import com.msy.rrodemo.websocket.MyWebSocketClient;

import java.net.URI;

import static com.msy.rrodemo.contacts.AboutSPContacts.USER_ID;
import static com.msy.rrodemo.contacts.AboutSPContacts.USER_SP_KEY;

/**
 * Created by Administrator on 2019/12/16/016.
 */

public class MyWebSocketClientService extends Service {

    public MyWebSocketClient client;
    public URI uri;
    public MyWebSocketClientBinder binder = new MyWebSocketClientBinder();

    private static final String TAG = MyWebSocketClientService.class.getSimpleName();

    public class MyWebSocketClientBinder extends Binder{
        public MyWebSocketClientService getService(){
            return MyWebSocketClientService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public MyWebSocketClient client(ChatActivity activity) {
        if (client != null)return client;
        String userId = SPUtils.getInstance(USER_SP_KEY).getString(USER_ID);
        uri = URI.create(UrlContacts.BASE_URL.replace("http", "ws") + "/myWebSocketServer/" + userId);
        client = new MyWebSocketClient(uri){
            @Override
            public void onMessage(String message) {
                //通过broadcast修改UI
                Intent intent = new Intent();
                intent.setAction("com.misy.rrodemo.receiver");
                intent.putExtra("message", message);
                sendBroadcast(intent);
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
                 if (activity.isDestroyed()){
                     Log.d(TAG, TAG + "isdestroyed");
                    return;
                }
                super.onClose(code, reason, remote);
            }
        };
        client.connect();
        return client;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        client.close();
        client = null;
    }
}
