package com.example.test2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Chatroom extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom);
        TextView tv = findViewById(R.id.tv_with_name);
        int n = getIntent().getIntExtra("n", 0);
        tv.setText("No." + Integer.toString(n) + "item.");
    }

}
