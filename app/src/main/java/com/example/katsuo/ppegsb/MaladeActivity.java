package com.example.katsuo.ppegsb;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by Katsuo on 27/04/2017.
 */

public class MaladeActivity extends AppCompatActivity {

    private TextView txtInsuline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_malade);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        txtInsuline = (TextView) findViewById(R.id.txtInsuline);
        Intent intent = getIntent();
        String item = intent.getStringExtra("selected-item");
        txtInsuline.setText(item);
    }
}
