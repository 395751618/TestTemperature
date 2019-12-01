package com.mindertech.testtemperature;

import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.gyf.immersionbar.ImmersionBar;
import com.mindertech.testtemperature.adapter.HistoryAdapter;
import com.mindertech.testtemperature.model.MDAccount;
import com.mindertech.testtemperature.model.MDTemperature;
import com.mindertech.testtemperature.model.MDTemperature1;
import com.mindertech.testtemperature.model.Temperature;
import com.mindertech.testtemperature.network.MDCallback;
import com.mindertech.testtemperature.request.Api;
import com.mindertech.testtemperature.request.AppHttp;
import com.mindertech.testtemperature.ui.BaseActivity;
import com.mindertech.testtemperature.ui.ChartActivity;
import com.mindertech.testtemperature.ui.LoginActivity;
import com.mindertech.testtemperature.ui.NFCActivity;
import com.mindertech.testtemperature.utils.MDAccountManager;
import com.mindertech.testtemperature.utils.NfcUtils;
import com.mindertech.testtemperature.utils.RealmUtils;
import com.mindertech.testtemperature.utils.Utils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.iv_point_shoulder_left)
    ImageView ivPointShoulderLeft;
    @BindView(R.id.iv_point_shoulder_right)
    ImageView ivPointShoulderRight;
    @BindView(R.id.iv_point_arm_left)
    ImageView ivPointArmLeft;
    @BindView(R.id.iv_point_arm_right)
    ImageView ivPointArmRight;
    @BindView(R.id.tv_chart)
    TextView tvChart;
    private String temperature_point;

    @BindView(R.id.iv_point)
    ImageView ivPoint;
    @BindView(R.id.list_history)
    ListView listHistory;

    private HistoryAdapter historyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideActionBar();
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        ImmersionBar.with(this).init();

        historyAdapter = new HistoryAdapter(this, null);
        listHistory.setAdapter(historyAdapter);

        temperature_point = Utils.temperature_point_shoulder_right;
        ivPoint.setImageResource(R.mipmap.img_shoulder_right);

        //nfc初始化设置
        Utils.hasSystemFeature = NfcUtils.hasSystemFeature(this);

        updateData();
        initView();

        if (false == Utils.hasSystemFeature) {
            Toast.makeText(this, "设备不支持NFC功能!", Toast.LENGTH_SHORT).show();
            return;
        }

        NfcUtils.nfcInit(this);
    }

    private void initView() {
        // 869/1655

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                toLoginPage();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void toNFCPage() {
        Intent intent = new Intent();
        intent.setClass(this, NFCActivity.class);
        startActivity(intent);
    }

    private void toLoginPage() {
        Intent intent = new Intent();
        intent.setClass(this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (null != NfcUtils.mNfcAdapter) {
            //开启前台调度系统
            NfcUtils.mNfcAdapter.enableForegroundDispatch(this, NfcUtils.mPendingIntent, NfcUtils.mIntentFilter, NfcUtils.mTechList);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (null != NfcUtils.mNfcAdapter) {
            //关闭前台调度系统
            NfcUtils.mNfcAdapter.disableForegroundDispatch(this);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        //当该Activity接收到NFC标签时，运行该方法
        //调用工具方法，读取NFC数据
        try {
            if (Utils.isChoosePoint(temperature_point)) {
                String string = NfcUtils.readNFTemperatureCFromTag(intent);
                System.out.println(string);

                if (null != string) {
//                    RealmUtils.addTemperature(MDAccountManager.getInstance().getUserId(), temperature_point, string);
                    requestAddTemperature(string);
                }

            } else {
                System.out.println("please choose point");
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void requestAddTemperature(String temperature) {

        final long currentTimeMillis = System.currentTimeMillis();
        Api.self(AppHttp.class).addTemperature(MDAccountManager.getInstance().getUserId(), Utils.getPointString(temperature_point), Utils.getImageString(temperature_point), temperature).enqueue(new MDCallback<MDTemperature>() {
            @Override
            public void success(MDTemperature temperature) {
                MDAccountManager.getInstance().addTemperature(temperature_point, temperature.temperature, currentTimeMillis);
                updateData();
            }

            @Override
            public void failure(int code, String failure) {
                showToastFailure(failure);
            }
        });
    }

    @OnClick({R.id.tv_chart, R.id.iv_point_shoulder_left, R.id.iv_point_shoulder_right, R.id.iv_point_arm_left, R.id.iv_point_arm_right})
    public void onImageViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_chart:
                toChartPage();
                break;
            case R.id.iv_point_shoulder_left:
                temperature_point = Utils.temperature_point_shoulder_left;
                ivPoint.setImageResource(R.mipmap.img_shoulder_left);
                updateData();
                break;
            case R.id.iv_point_shoulder_right:
                temperature_point = Utils.temperature_point_shoulder_right;
                ivPoint.setImageResource(R.mipmap.img_shoulder_right);
                updateData();
                break;
            case R.id.iv_point_arm_left:
                temperature_point = Utils.temperature_point_arm_left;
                ivPoint.setImageResource(R.mipmap.img_arm_left);
                updateData();
                break;
            case R.id.iv_point_arm_right:
                temperature_point = Utils.temperature_point_arm_right;
                ivPoint.setImageResource(R.mipmap.img_arm_right);
                updateData();
                break;
            default:
                break;
        }
    }

    private void updateData() {
//        if (false == Utils.hasSystemFeature) {
//            historyAdapter.setData(MDAccountManager.getInstance().getTemperatures(temperature_point));
//        } else {
//            List<Temperature> list = RealmUtils.findAllTemperature(MDAccountManager.getInstance().getUserId(), temperature_point);
//            historyAdapter.setData(list);
//        }
        historyAdapter.setData(MDAccountManager.getInstance().getTemperatureList(temperature_point));
    }

    private void toChartPage() {
        Intent intent = new Intent();
        intent.setClass(this, ChartActivity.class);
        startActivity(intent);
    }
}
