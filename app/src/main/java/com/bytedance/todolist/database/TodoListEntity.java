package com.bytedance.todolist.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

/**
 * @author wangrui.sh
 * @since Jul 11, 2020
 */
@Entity(tableName = "todo")
public class TodoListEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private Long mId;

    @ColumnInfo(name = "content")
    private String mContent;

    @ColumnInfo(name = "time")
    private Date mTime;

    @ColumnInfo(name = "delete")
    protected boolean mDelete;

    public TodoListEntity(String mContent, Date mTime) {
        this.mContent = mContent;
        this.mTime = mTime;
        this.mDelete = false;
    }

    public String getContent() {
        return mContent;
    }


    public void setDelete(boolean del){
        this.mDelete = del;
    }
    public void changeDel(boolean b){
        mDelete = b;
    }

    public boolean getDel(){
        return mDelete;
    }

    public void reverseDel(){
        mDelete = !mDelete;
    }

    public void setContent(String mContent) {
        this.mContent = mContent;
    }

    public Date getTime() {
        return mTime;
    }

    public void setTime(Date mTime) {
        this.mTime = mTime;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long mId) {
        this.mId = mId;
    }
}
