package com.msy.rrodemo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.msy.rrodemo.R;
import com.msy.rrodemo.entity.ChatBean;
import com.msy.rrodemo.utils.FileUtils;
import com.msy.rrodemo.utils.GlideUtils;
import com.msy.rrodemo.utils.SPUtils;
import com.msy.rrodemo.widget.ChatDateTextView;

import java.io.File;
import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.msy.rrodemo.contacts.AboutSPContacts.USER_ID;
import static com.msy.rrodemo.contacts.AboutSPContacts.USER_SP_KEY;
import static com.msy.rrodemo.contacts.SocketContacts.IMAGE_MESSAGE_TYPE;
import static com.msy.rrodemo.contacts.SocketContacts.TEXT_MESSAGE_TYPE;

/**
 * Created by Administrator on 2019/12/25/025.
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder>{

    private Context mContext;
    private List<ChatBean> mList;
    private static final int MY_CHATINFO_TYPE = 0;
    private static final int OTHER_CHATINFO_TYPE = 1;

   public ChatAdapter(Context mContext, List<ChatBean> list){
       this.mContext = mContext;
       this.mList = list;
   }

   public void setNewData(List<ChatBean> list){
       this.mList = list;
       notifyDataSetChanged();
   }

    @NonNull
    @Override
    public ChatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
       View rootView = LayoutInflater.from(mContext).inflate(R.layout.item_chat, null);
       ViewHolder holder = new ViewHolder(rootView);
       return holder;
    }

    @Override

    public void onBindViewHolder(@NonNull ChatAdapter.ViewHolder holder, int position) {
       ChatBean item = mList.get(position);
       holder.dateTv.setDate(item.getCreateDate());
        switch (getItemViewType(position)){
            case MY_CHATINFO_TYPE:
                holder.leftLayout.setVisibility(View.GONE);
                holder.rightLayout.setVisibility(View.VISIBLE);
                GlideUtils.loadHeaderImg(mContext, item.getHeaderUrl(), holder.rightHeaderImg);

                if (item.getMessageType() == TEXT_MESSAGE_TYPE){
                    holder.rightContentTv.setVisibility(View.VISIBLE);
                    holder.rightContentImg.setVisibility(View.GONE);
                    holder.rightContentTv.setText(item.getContent());
                }else if (item.getMessageType() == IMAGE_MESSAGE_TYPE){
                    holder.rightContentTv.setVisibility(View.GONE);
                    holder.rightContentImg.setVisibility(View.VISIBLE);
                    try {
                        GlideUtils.loadImg(mContext, FileUtils.createAndWriteFile(item.getByteContent()), holder.rightContentImg);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else {
                    holder.rightContentTv.setVisibility(View.GONE);
                    holder.rightContentImg.setVisibility(View.GONE);
                }
                break;
            case OTHER_CHATINFO_TYPE:
                holder.rightLayout.setVisibility(View.GONE);
                holder.leftLayout.setVisibility(View.VISIBLE);
                holder.leftContentTv.setText(item.getContent());
                GlideUtils.loadHeaderImg(mContext, item.getHeaderUrl(), holder.leftHeaderImg);

                if (item.getMessageType() == TEXT_MESSAGE_TYPE){
                    holder.leftContentTv.setVisibility(View.VISIBLE);
                    holder.leftContentImg.setVisibility(View.GONE);
                    holder.rightContentTv.setText(item.getContent());
                }else if (item.getMessageType() == IMAGE_MESSAGE_TYPE){
                    holder.leftContentTv.setVisibility(View.GONE);
                    holder.leftContentImg.setVisibility(View.VISIBLE);
                    try {
                        GlideUtils.loadHeaderImg(mContext, FileUtils.createAndWriteFile(item.getByteContent()), holder.leftContentImg);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else {
                    holder.leftContentTv.setVisibility(View.GONE);
                    holder.leftContentImg.setVisibility(View.GONE);
                }
                break;
        }
    }

    @Override
    public int getItemCount() {
       return null == mList ? 0 : mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mList.size() == 0)return -1;
        ChatBean chatBean = mList.get(position);
        String sendUserId = chatBean.getSendUserId();
        String myUserId = SPUtils.getInstance(USER_SP_KEY).getString(USER_ID);
        if (sendUserId.equals(myUserId)){
            return MY_CHATINFO_TYPE;
        }else {
            return OTHER_CHATINFO_TYPE;
        }
    }


    class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.date_tv)
        ChatDateTextView dateTv;
        @BindView(R.id.left_layout)
        ViewGroup leftLayout;
        @BindView(R.id.left_header_img)
        ImageView leftHeaderImg;
        @BindView(R.id.left_content_tv)
        TextView leftContentTv;
        @BindView(R.id.left_content_img)
        ImageView leftContentImg;
        @BindView(R.id.right_layout)
        ViewGroup rightLayout;
        @BindView(R.id.right_header_img)
        ImageView rightHeaderImg;
        @BindView(R.id.right_content_tv)
        TextView rightContentTv;
        @BindView(R.id.right_content_img)
        ImageView rightContentImg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
