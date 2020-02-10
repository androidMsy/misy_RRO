package com.msy.rrodemo.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.msy.rrodemo.R;
import com.msy.rrodemo.entity.UserBean;
import com.msy.rrodemo.utils.GlideUtils;

import java.util.List;

/**
 * Created by Administrator on 2019/12/20/020.
 */

public class ContactsAdapter extends BaseQuickAdapter<UserBean, BaseViewHolder> {


    public ContactsAdapter(List<UserBean> data) {
        super(R.layout.item_contacts, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, UserBean userBean) {
        baseViewHolder.setText(R.id.username_tv, userBean.getRealname())
                .addOnClickListener(R.id.content_group);
        GlideUtils.loadHeaderImg(mContext, userBean.getHeaderUrl(), baseViewHolder.getView(R.id.head_img));
    }
}
