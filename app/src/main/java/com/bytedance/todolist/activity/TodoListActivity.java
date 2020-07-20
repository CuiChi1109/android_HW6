package com.bytedance.todolist.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;

import com.bytedance.todolist.database.TodoListDao;
import com.bytedance.todolist.database.TodoListDatabase;
import com.bytedance.todolist.database.TodoListEntity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.bytedance.todolist.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.Date;
import java.util.List;

public class TodoListActivity extends AppCompatActivity {

    private String TAG="TodoListActivity";
    private TodoListAdapter mAdapter;
    private FloatingActionButton mFab;
    private TextView tv_item;
    private TextView tv_time;
    private CheckBox cb_del;
    private String returnData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todo_list_activity_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.rv_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new TodoListAdapter();
        recyclerView.setAdapter(mAdapter);

        final Intent add_item_intent = new Intent();
        add_item_intent.setClass(this, TodoListAddActivity.class);

        /*cb_del = findViewById(R.id.cb_delete);
        tv_item = findViewById(R.id.tv_content);
        tv_time = findViewById(R.id.tv_timestamp);

        cb_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_item.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                tv_time.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            }
        });*/

        mFab = findViewById(R.id.fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO
                // 新增事件
                Log.i(TAG,"start add");
                startActivityForResult(add_item_intent, 1);
            }
        });

        mFab.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new Thread() {
                    @Override
                    public void run() {
                        TodoListDao dao = TodoListDatabase.inst(TodoListActivity.this).todoListDao();
                        dao.deleteAll();
                        for (int i = 0; i < 20; ++i) {
                            dao.addTodo(new TodoListEntity("This is " + i + " item", new Date(System.currentTimeMillis())));
                        }
                        Snackbar.make(mFab, R.string.hint_insert_complete, Snackbar.LENGTH_SHORT).show();
                    }
                }.start();
                return true;
            }
        });
        loadFromDatabase();
    }

    private void loadFromDatabase() {
        new Thread() {
            @Override
            public void run() {
                Log.i(TAG, "load");
                TodoListDao dao = TodoListDatabase.inst(TodoListActivity.this).todoListDao();
                final List<TodoListEntity> entityList = dao.loadAll();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.setData(entityList);
                    }
                });
            }
        }.start();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    //获取输入数据
                    returnData = data.getStringExtra("text");
                    Log.i(TAG, "text = "+returnData);
                    new Thread(){
                        @Override
                        public void run() {
                            //更新数据库
                            TodoListDao dao = TodoListDatabase.inst(TodoListActivity.this).todoListDao();
                            dao.addTodo(new TodoListEntity(returnData, new Date(System.currentTimeMillis())));
                        }
                    }.start();
                    //刷新
                    loadFromDatabase();
                }
                break;
        }
        return;
    }

}
