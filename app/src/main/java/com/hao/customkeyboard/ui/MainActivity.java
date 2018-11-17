package com.hao.customkeyboard.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.hao.customkeyboard.R;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    String customizeKeyboard = "CustomerKeyboard";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.act_main_btn_pay).setOnClickListener(this);
        findViewById(R.id.act_main_btn_car).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.act_main_btn_pay:
                showPayKeyBoard();
                break;
            case R.id.act_main_btn_car:
                showCarNumKeyBoard();
                break;
        }
    }

    public void showPayKeyBoard() {
        PayDialogFragment payDialogFragment = new PayDialogFragment();
        payDialogFragment.show(getSupportFragmentManager(), "payFragment");
    }

    public void showCarNumKeyBoard() {
        CarNumDialogFragment carNumDialogFragment = new CarNumDialogFragment();
        carNumDialogFragment.show(getSupportFragmentManager(), "carNumFragment");
    }
}
