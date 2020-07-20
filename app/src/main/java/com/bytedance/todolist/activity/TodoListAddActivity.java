package com.bytedance.todolist.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.bytedance.todolist.R;

public class TodoListAddActivity extends AppCompatActivity {


    private Button BtnAdd;
    private EditText Item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todo_add_item_layout);

        BtnAdd = findViewById(R.id.btn_add);
        BtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Item = findViewById(R.id.et_item);
                final String text = Item.getText().toString();
                Log.i("TodoListActivity", "back1 = "+text);
                final Intent add_intent = new Intent();
                add_intent.setClass(TodoListAddActivity.this, TodoListActivity.class);
                /*startActivity(add_intent);
                add_intent.putExtra("text", text);*/
               // startActivityForResult(add_intent, 1);
                add_intent.putExtra("text",text);
                setResult(RESULT_OK, add_intent);
                finish();
            }
        });
    }
}
