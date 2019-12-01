package com.mindertech.testtemperature.ui;

import android.view.MenuItem;
import android.view.View;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import com.mumu.dialog.MMLoading;
import com.mumu.dialog.MMToast;

/**
 * @project TestTemperature
 * @package：com.mindertech.testtemperature.ui
 * @anthor xiangxia
 * @time 2019-11-26 13:53
 * @description 描述
 */
public class BaseActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private View contentView;
    private int contentPTop;
    private MMLoading mmLoading;
    private MMToast mmToast;

    public void hideActionBar() {
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
    }

    public void addBackActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    protected void showLoading() {
        if (mmLoading == null) {
            MMLoading.Builder builder = new MMLoading.Builder(this)
                    .setMessage("加载中...")
                    .setCancelable(false)
                    .setCancelOutside(false);
            mmLoading = builder.create();
        }else {
            mmLoading.dismiss();
            MMLoading.Builder builder = new MMLoading.Builder(this)
                    .setMessage("加载中...")
                    .setCancelable(false)
                    .setCancelOutside(false);
            mmLoading = builder.create();
        }
        mmLoading.show();
    }

    protected void showLoading(String msg) {
        if (null == msg || "".equals(msg)) {
            msg = "加载中...";
        }
        if (mmLoading == null) {
            MMLoading.Builder builder = new MMLoading.Builder(this)
                    .setMessage(msg)
                    .setCancelable(false)
                    .setCancelOutside(false);
            mmLoading = builder.create();
        }else {
            mmLoading.dismiss();
            MMLoading.Builder builder = new MMLoading.Builder(this)
                    .setMessage(msg)
                    .setCancelable(false)
                    .setCancelOutside(false);
            mmLoading = builder.create();
        }
        mmLoading.show();
    }

    protected void hideLoading() {
        if (mmLoading != null && mmLoading.isShowing()) {
            mmLoading.dismiss();
        }
    }

    protected void showToastSuccess(String msg) {
        if (null == msg || "".equals(msg)) {
            msg = "成功";
        }
        if (mmToast == null) {
            MMToast.Builder builder=new MMToast.Builder(this)
                    .setMessage(msg)
                    .setSuccess(true);
            mmToast=builder.create();
        }else {
            mmToast.cancel();
            MMToast.Builder builder=new MMToast.Builder(this)
                    .setMessage(msg)
                    .setSuccess(true);
            mmToast=builder.create();
        }
        mmToast.show();
    }

    protected void showToastFailure(String msg) {
        if (mmToast == null) {
            MMToast.Builder builder=new MMToast.Builder(this)
                    .setMessage(msg)
                    .setSuccess(false);
            mmToast=builder.create();
        }else {
            mmToast.cancel();
            MMToast.Builder builder=new MMToast.Builder(this)
                    .setMessage(msg)
                    .setSuccess(false);
            mmToast=builder.create();
        }
        mmToast.show();
    }

}
