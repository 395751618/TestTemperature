package com.mindertech.testtemperature.ui;

import android.accounts.Account;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.mindertech.testtemperature.R;
import com.mindertech.testtemperature.utils.RealmUtils;

import butterknife.ButterKnife;

/**
 * @project TestTemperature
 * @package：com.mindertech.testtemperature.ui
 * @anthor xiangxia
 * @time 2019-11-27 13:48
 * @description 描述
 */
public class WelcomeActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideActionBar();
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);


    }
}
