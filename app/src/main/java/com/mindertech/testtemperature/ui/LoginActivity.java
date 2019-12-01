package com.mindertech.testtemperature.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.gyf.immersionbar.ImmersionBar;
import com.mindertech.testtemperature.MainActivity;
import com.mindertech.testtemperature.R;
import com.mindertech.testtemperature.model.MDAccount;
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
public class LoginActivity extends BaseActivity {

    @BindView(R.id.layout_1)
    LinearLayout layout1;
    @BindView(R.id.et_user_name)
    EditText etUserName;
    @BindView(R.id.layout_user_name)
    LinearLayout layoutUserName;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.layout_password)
    LinearLayout layoutPassword;
    @BindView(R.id.tv_regist)
    TextView tvRegist;
    @BindView(R.id.layout_regist)
    RelativeLayout layoutRegist;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.layout_login)
    LinearLayout layoutLogin;
    @BindView(R.id.sign_in_copyright)
    TextView signInCopyright;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideActionBar();
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        ImmersionBar.with(this).init();

        MDAccount account = RealmUtils.findAccount();
        if (null != account) {
//            MDAccountManager.getInstance().autologin(account);
//            toMainPage();
            etUserName.setText(account.username);
            etPassword.setText(account.password);
            requestLogin();
        }
    }

    @OnClick({R.id.tv_regist, R.id.tv_login})
    public void onTextViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_regist:
                toRegistPage();
                break;
            case R.id.tv_login:
                requestLogin();
                break;
            default:
                break;
        }
    }

    private void requestLogin() {
        String email = etUserName.getText() + "";
        String password = etPassword.getText() + "";

        if (0 == email.length()) {
            Toast.makeText(this, "请输入用户名或邮箱", Toast.LENGTH_SHORT).show();
            return;
        }

        if (0 == password.length()) {
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
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
                toMainPage();
            }

            @Override
            public void failure(int code, String failure) {
                hideLoading();
                showToastFailure(failure);
            }
        };
        Api.self(AppHttp.class).signIn(email, password).enqueue(callback);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        MDAccountManager.getInstance().logout();
        RealmUtils.deleteAccount();
    }

    private void toMainPage() {
        Intent intent = new Intent();
        intent.setClass(this, MainActivity.class);
        startActivity(intent);
    }

    private void toRegistPage() {
        Intent intent = new Intent();
        intent.setClass(this, RegistActivity.class);
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (100 == requestCode && 1 == resultCode) {
            toMainPage();
            etUserName.setText(MDAccountManager.getInstance().getUserName());
            etPassword.setText(MDAccountManager.getInstance().getPassword());
        }
    }
}
