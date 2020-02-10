package com.msy.rrodemo.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.msy.rrodemo.utils.DateUtils;

/**
 * Created by Administrator on 2019/12/27/027.
 */

public class ChatDateTextView extends android.support.v7.widget.AppCompatTextView {

    public boolean isVisiable = false;
    public long date;

    public ChatDateTextView(Context context) {
        super(context);
    }

    public ChatDateTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ChatDateTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);
        setVisibility(getIsVisibility() ? VISIBLE : GONE);
    }
    public void setDate(long date){
        this.date = date;
        setText(DateUtils.subTwoDate(date));
    }
    public boolean getIsVisibility(){
        if (0 == date){
            return false;
        }
        long subDate = System.currentTimeMillis() - date;
        if (subDate > (60000 * 10)){
            isVisiable = true;
        }
        return isVisiable;
    }
}
