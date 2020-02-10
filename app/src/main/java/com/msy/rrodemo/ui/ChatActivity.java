package com.msy.rrodemo.ui;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.misy.photopicker.picker.PhotoPicker;
import com.msy.rrodemo.R;
import com.msy.rrodemo.adapter.ChatAdapter;
import com.msy.rrodemo.app.MyApp;
import com.msy.rrodemo.basic.BaseActivity;
import com.msy.rrodemo.basic.BaseRefreshActivity;
import com.msy.rrodemo.di.component.ActivityComponent;
import com.msy.rrodemo.entity.BaseCollection;
import com.msy.rrodemo.entity.ChatBean;
import com.msy.rrodemo.entity.UserBean;
import com.msy.rrodemo.net.callback.BaseResponseCall;
import com.msy.rrodemo.presenter.ChatPresenter;
import com.msy.rrodemo.service.MyWebSocketClientService;
import com.msy.rrodemo.utils.PermissionUtils;
import com.msy.rrodemo.view.ChatView;
import com.msy.rrodemo.websocket.MyWebSocketClient;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okio.BufferedSource;
import okio.Okio;
import okio.Sink;
import top.zibin.luban.Luban;

import static com.msy.rrodemo.contacts.SocketContacts.IMAGE_MESSAGE_TYPE;
import static com.msy.rrodemo.contacts.SocketContacts.TEXT_MESSAGE_TYPE;

/**
 * Created by Administrator on 2019/12/16/016.
 */

public class ChatActivity extends BaseActivity<ChatPresenter> implements ChatView.View,View.OnClickListener,SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.input_edt)
    EditText inputEdt;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refreshLayout;

    private String targetUserId;
    private MyWebSocketClient client;
    private MyWebSocketClientService myService;
    private MyWebSocketClientService.MyWebSocketClientBinder binder;
    private ChatBroadcastReceiver chatBroadcastReceiver;
    private ChatAdapter adapter;
    private List<ChatBean> mList = new ArrayList<>();
    private ChatBean msgBody;
    private int mPage = 1;
    private boolean isLoadMore = false;

    private static final String TAG = ChatActivity.class.getSimpleName();
    public static final int CLOSE_SUCCESS_CODE = 21;
    public static final int REQUEST_CODE_CHOOSE = 1000;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_chat;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        toolbar.setTitle("会话");
    }

    @Override
    protected void initWidget() {
        refreshLayout.setColorSchemeResources(R.color.base_color);
    }

    @Override
    protected void initListner() {
        refreshLayout.setOnRefreshListener(this);
    }

    @Override
    protected void initIntentData() {
        PermissionUtils.verifyStoragePermissions(this);
        targetUserId = getIntent().getStringExtra("targetUserId");
    }

    @Override
    protected void initData() {
        msgBody = new ChatBean();
        msgBody.setSendUserId(getUserId());
        msgBody.setTargetUserId(targetUserId);
        msgBody.setRealname(getRealName());
        msgBody.setHeaderUrl(getHeaderUrl());
    }

    @Override
    protected void initRecyclerView() {
        closeKeyboard();
        adapter = new ChatAdapter(this, mList);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setStackFromEnd(true);
        manager.scrollToPositionWithOffset(adapter.getItemCount() - 1, Integer.MIN_VALUE);
        recycler.setLayoutManager(manager);
        recycler.setAdapter(adapter);
        recycler.setHasFixedSize(true);
    }

    @Override
    protected void loadData() {
        super.loadData();
        mPresenter.getChatHistory(targetUserId, mPage);
    }

    @Override
    protected void bindService() {
        Intent intent = new Intent(this, MyWebSocketClientService.class);
        bindService(intent, connection, BIND_AUTO_CREATE);
        registerReceiver();
    }

    @OnClick({R.id.connect_btn,R.id.menu_img})
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.connect_btn:
                String inputStr = inputEdt.getText().toString().trim();
                msgBody.setContent(inputStr);
                msgBody.setByteContent(null);
                msgBody.setMessageType(TEXT_MESSAGE_TYPE);
                addChatItem(msgBody);
                inputEdt.setText(null);
                client.send(JSONObject.toJSONString(msgBody));
                closeKeyboard();
                break;
            case R.id.menu_img:
                PhotoPicker.build()
                        .maxSelectNum(2)
                        .showCamera(true)
                        .start(this);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PhotoPicker.REQUEST_CODE && resultCode == PhotoPicker.RESULT_CODE) {
            List<String> pathList = data.getStringArrayListExtra(PhotoPicker.PHOTO_PICKER_KEY);
            mPresenter.compressPhoto(ChatActivity.this, pathList);
        }
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder iBinder) {
            binder = (MyWebSocketClientService.MyWebSocketClientBinder) iBinder;
            myService = binder.getService();
            client = myService.client(ChatActivity.this);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, name.toShortString());
        }
    };

    private void registerReceiver(){
        chatBroadcastReceiver = new ChatBroadcastReceiver();
        IntentFilter filter = new IntentFilter("com.misy.rrodemo.receiver");
        registerReceiver(chatBroadcastReceiver, filter);
    }


    private class ChatBroadcastReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            String content = intent.getStringExtra("message");
            if ("连接成功".equals(content) || "该用户不存在或者不在线".equals(content)){
                Log.d(TAG, "-------------" + content + "-----------");
                showError(content);
                return;
            }
            ChatBean bean = JSON.parseObject(content, ChatBean.class);
            if (targetUserId.equals(bean.getSendUserId())){
                addChatItem(bean);
            }
        }
    }

    @Override
    public void onRefresh() {
        isLoadMore = true;
        mPage++;
        mPresenter.getChatHistory(targetUserId, mPage);
    }

    @Override
    public void setData(BaseCollection<ChatBean> data) {
        if (!isLoadMore){
            mList = data.getList();
            Collections.reverse(mList);
            adapter.setNewData(mList);
            recycler.smoothScrollToPosition(adapter.getItemCount());
        }else {//加载更多
            Collections.reverse(mList);
            List item = data.getList();
            mList.addAll(item);
            Collections.reverse(mList);
            adapter.setNewData(mList);
            refreshLayout.setRefreshing(false);
            if (!data.isHasNextPage()){
                showError("已加载全部数据");
                refreshLayout.setEnabled(false);
            }else {
                refreshLayout.setEnabled(true);
            }
        }
    }

    @Override
    public void setPhoto(List<File> fileList) {
        try {
            File sendFile = fileList.get(0);
            BufferedSource source = Okio.buffer(Okio.source(sendFile));
            byte[] content = source.readByteArray();
            msgBody.setContent(null);
            msgBody.setByteContent(content);
            msgBody.setMessageType(IMAGE_MESSAGE_TYPE);
            addChatItem(msgBody);
//            client.send(JSONObject.toJSONString(msgBody));
            source.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {

        }
    }

    private void addChatItem(ChatBean bean){
        bean.setCreateDate(System.currentTimeMillis());
        mList.add(bean);
        adapter.notifyDataSetChanged();
        recycler.smoothScrollToPosition(adapter.getItemCount());
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != chatBroadcastReceiver){
            unregisterReceiver(chatBroadcastReceiver);
        }
        unbindService(connection);
        if (null == client)return;
        client.close(CLOSE_SUCCESS_CODE);
        client = null;
    }
}
