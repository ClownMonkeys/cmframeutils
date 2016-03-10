package com.cm.cmframeutils;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import nicespinnerview.NiceSpinner;
import utlis.ToastUtils;

public class Main2Activity extends AppCompatActivity{
    private ImageView an;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        an= (ImageView) findViewById(R.id.an);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                startActivity(new Intent(Main2Activity.this,ScrollingActivity.class));
            }
        });
        NiceSpinner niceSpinner = (NiceSpinner) findViewById(R.id.nice_spinner);
        List<String> dataset = new LinkedList<>(Arrays.asList("One", "Two", "Three", "Four", "Five"));
        niceSpinner.attachDataSource(dataset);
    }

    public void onClick(View v) {
        switch (v.getId()){
            case R.id.an:
                ToastUtils.show(Main2Activity.this,"aaaaaa");
                break;
        }
    }
}
