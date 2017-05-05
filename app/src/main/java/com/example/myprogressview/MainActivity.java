package com.example.myprogressview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textView;
    private ProgressView progressView;
    private Random random = new Random(System.currentTimeMillis());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textview);
        progressView = (ProgressView) findViewById(R.id.spring_progress_view);
        progressView.setMaxCount(1000.0f);
        findViewById(R.id.click).setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        progressView.setCurrentCount(random.nextInt(1000));
        textView.setText("最大值："+progressView.getMaxCount()+"   当前值："+progressView.getCurrentCount());
    }

}
