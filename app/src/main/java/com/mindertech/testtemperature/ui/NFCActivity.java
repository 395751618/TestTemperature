package com.mindertech.testtemperature.ui;

import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.mindertech.testtemperature.R;
import com.mindertech.testtemperature.utils.NfcUtils;

import java.io.UnsupportedEncodingException;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @project TestTemperature
 * @package：com.mindertech.testtemperature.ui
 * @anthor xiangxia
 * @time 2019-11-26 10:17
 * @description 描述
 */
public class NFCActivity extends AppCompatActivity {

    @BindView(R.id.tv_temperature)
    TextView tvTemperature;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc);
        ButterKnife.bind(this);
        //给ViewText添加滚动条
        tvTemperature.setMovementMethod(ScrollingMovementMethod.getInstance());

        //nfc初始化设置
        NfcUtils nfcUtils = new NfcUtils(this);
        if (false == NfcUtils.hasSystemFeature(this)) {
            Toast.makeText(this, "设备不支持NFC功能!", Toast.LENGTH_SHORT);
            return;
        }

        NfcAdapter adapter = NfcUtils.NfcCheck(this);
        if (null == adapter || false == adapter.isEnabled()) {
            Toast.makeText(this, "请到系统设置中打开NFC功能!", Toast.LENGTH_SHORT);
            return;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //开启前台调度系统
        NfcUtils.mNfcAdapter.enableForegroundDispatch(this, NfcUtils.mPendingIntent, NfcUtils.mIntentFilter, NfcUtils.mTechList);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //关闭前台调度系统
        NfcUtils.mNfcAdapter.disableForegroundDispatch(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        //当该Activity接收到NFC标签时，运行该方法
        //调用工具方法，读取NFC数据
        try {
            String string = NfcUtils.readNFTemperatureCFromTag(intent);
            tvTemperature.setText(string);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
