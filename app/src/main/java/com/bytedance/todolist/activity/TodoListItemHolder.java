package com.bytedance.todolist.activity;

import android.graphics.Paint;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bytedance.todolist.R;
import com.bytedance.todolist.database.TodoListEntity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author wangrui.sh
 * @since Jul 11, 2020
 */
public class TodoListItemHolder extends RecyclerView.ViewHolder {
    public TextView mContent;
    public TextView mTimestamp;
    public CheckBox cbDel;
    public ImageButton btn_rm;
/*    private boolean Delete[];
    private boolean del;
    private int k = 0;*/
    private String TAG = "TodoListItemHolder";

    public TodoListItemHolder(@NonNull View itemView) {
        super(itemView);
        mContent = itemView.findViewById(R.id.tv_content);
        mTimestamp = itemView.findViewById(R.id.tv_timestamp);
        cbDel = itemView.findViewById(R.id.cb_delete);
        btn_rm = itemView.findViewById(R.id.ib_delete);

    }


    public void bind(final TodoListEntity entity) {
        mContent.setText(entity.getContent());
        mTimestamp.setText(formatDate(entity.getTime()));
        deleteLine(entity.getDel());
    }

    public void deleteLine(Boolean isDelete){
        cbDel.setChecked(isDelete);
        if(isDelete){
            mContent.setPaintFlags(mContent.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            mTimestamp.setPaintFlags(mTimestamp.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }else{
            mContent.setPaintFlags(mContent.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            mTimestamp.setPaintFlags(mTimestamp.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }
    }


    private String formatDate(Date date) {
        DateFormat format = SimpleDateFormat.getDateInstance();
        return format.format(date);
    }

    public void setCheckBoxListener(CheckBox.OnClickListener listener){
        cbDel.setOnClickListener(listener);
    }

    public void setImageBtnListener(ImageButton.OnClickListener listener){
        btn_rm.setOnClickListener(listener);
    }
}
