package com.mindertech.testtemperature.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.gyf.immersionbar.ImmersionBar;
import com.mindertech.testtemperature.R;
import com.mindertech.testtemperature.model.Regist;
import com.mindertech.testtemperature.network.MDCallback;
import com.mindertech.testtemperature.request.Api;
import com.mindertech.testtemperature.request.AppHttp;
import com.mindertech.testtemperature.utils.MDAccountManager;
import com.mindertech.testtemperature.utils.RealmUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @project TestTemperature
 * @package：com.mindertech.testtemperature.ui
 * @anthor xiangxia
 * @time 2019-11-26 13:45
 * @description 描述
 */
public class RegistActivity extends BaseActivity {

    @BindView(R.id.layout_1)
    LinearLayout layout1;
    @BindView(R.id.et_user_name)
    EditText etUserName;
    @BindView(R.id.layout_user_name)
    LinearLayout layoutUserName;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.layout_email)
    LinearLayout layoutEmail;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.layout_password)
    LinearLayout layoutPassword;
    @BindView(R.id.et_repeat_password)
    EditText etRepeatPassword;
    @BindView(R.id.layout_repeat_password)
    LinearLayout layoutRepeatPassword;
    @BindView(R.id.tv_regist)
    TextView tvRegist;
    @BindView(R.id.layout_regist)
    LinearLayout layoutRegist;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideActionBar();
        setContentView(R.layout.activity_regist);
        ButterKnife.bind(this);
        ImmersionBar.with(this).init();
    }

    @OnClick({R.id.tv_regist})
    public void onTextViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_regist:
                requestRegist();
                break;
            default:
                break;
        }
    }

    private void requestRegist() {
        String username = etUserName.getText() + "";
        String email = etEmail.getText() + "";
        String password = etPassword.getText() + "";
        String repassword = etRepeatPassword.getText() + "";

        if (0 == username.length()) {
            Toast.makeText(this, "请输入用户名", Toast.LENGTH_SHORT).show();
            return;
        }

        if (0 == email.length()) {
            Toast.makeText(this, "请输入邮箱", Toast.LENGTH_SHORT).show();
            return;
        }

        if (0 == password.length()) {
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        }

        if (0 == repassword.length()) {
            Toast.makeText(this, "请输入重复密码", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(repassword)){
            Toast.makeText(this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
            return;
        }

        showLoading("");
        MDCallback<Regist> callback = new MDCallback<Regist>() {

            @Override
            public void success(Regist regist) {
                hideLoading();
                showToastSuccess("");
                RealmUtils.updateAccount(regist, password);
                MDAccountManager.getInstance().login(regist, password);

                Intent intent = new Intent();
                setResult(1, intent);
                finish();
            }

            @Override
            public void failure(int code, String failure) {
                hideLoading();
                showToastFailure(failure);
            }
        };
        Api.self(AppHttp.class).regist(username, email, password).enqueue(callback);
    }
}
