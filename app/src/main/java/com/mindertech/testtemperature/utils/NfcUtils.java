package com.mindertech.testtemperature.utils;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Parcelable;
import android.provider.Settings;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * @project TestTemperature
 * @package：com.mindertech.testtemperature.utils
 * @anthor xiangxia
 * @time 2019-11-26 10:14
 * @description 描述
 */
public class NfcUtils {

    //nfc
    public static NfcAdapter mNfcAdapter;
    public static IntentFilter[] mIntentFilter = null;
    public static PendingIntent mPendingIntent = null;
    public static String[][] mTechList = null;

    /**
     * 构造函数，用于初始化nfc
     */
    public NfcUtils(Activity activity) {
        mNfcAdapter = NfcCheck(activity);
        nfcInit(activity);
    }

    /**
     * 初始化nfc设置
     */
    public static void nfcInit(Activity activity) {
        NfcUtils.mNfcAdapter = NfcCheck(activity);

        mPendingIntent = PendingIntent.getActivity(activity, 0, new Intent(activity, activity.getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        IntentFilter filter = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
        IntentFilter filter2 = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        try {
            filter.addDataType("*/*");
        } catch (IntentFilter.MalformedMimeTypeException e) {
            e.printStackTrace();
        }
        mIntentFilter = new IntentFilter[]{filter, filter2};
        mTechList = null;
    }

    /**
     * 判断设备是否是nfc功能
     *
     * @author xiangxia
     * @createAt 2019-11-26 10:22
     */
    public static boolean hasSystemFeature(Activity activity) {
        PackageManager packageManager = activity.getPackageManager();
        boolean bol = packageManager.hasSystemFeature(PackageManager.FEATURE_NFC);
        return bol;
    }

    /**
     * 检查NFC是否打开
     */
    public static NfcAdapter NfcCheck(Activity activity) {
        NfcAdapter mNfcAdapter = NfcAdapter.getDefaultAdapter(activity);
        if (mNfcAdapter == null) {
            return null;
        } else {
            if (!mNfcAdapter.isEnabled()) {
                Intent setNfc = new Intent(Settings.ACTION_NFC_SETTINGS);
                activity.startActivity(setNfc);
            }
        }
        return mNfcAdapter;
    }

    /**
     * 读取NFC的数据
     */
    public static String readNFCFromTag(Intent intent) throws UnsupportedEncodingException {
        Parcelable[] rawArray = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
        if (rawArray != null) {
            NdefMessage mNdefMsg = (NdefMessage) rawArray[0];
            NdefRecord mNdefRecord = mNdefMsg.getRecords()[0];
            if (mNdefRecord != null) {
                String readResult = new String(mNdefRecord.getPayload(), "UTF-8");
                return readResult;
            }
        }
        return "";
    }

    public static String readNFCFromTag1(Intent intent) throws UnsupportedEncodingException {
        Parcelable[] rawArray = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
        String string = "";
        if (rawArray != null) {
            for (int i = 0;i < rawArray.length;i ++) {
                NdefMessage mNdefMsg = (NdefMessage)rawArray[i];
                NdefRecord[] records = mNdefMsg.getRecords();
                for (int j = 0;j < records.length;j ++) {
                    NdefRecord mNdefRecord = records[j];
                    if (mNdefRecord != null) {
                        String readResult = new String(mNdefRecord.getPayload(), "UTF-8");

                        string = string + "\n" + readResult;
                    }
                }
            }
        }
        return string;
    }

    /**
     * 获取温度
     *
     * @author xiangxia
     * @createAt 2019-11-26 13:44
     */
    public static String readNFTemperatureCFromTag(Intent intent) throws UnsupportedEncodingException {
        Parcelable[] rawArray = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
        String string = null;
        if (rawArray != null) {
            for (int i = 0;i < rawArray.length;i ++) {
                NdefMessage mNdefMsg = (NdefMessage)rawArray[i];
                NdefRecord[] records = mNdefMsg.getRecords();
                for (int j = 0;j < records.length;j ++) {
                    NdefRecord mNdefRecord = records[j];
                    if (mNdefRecord != null) {
                        String readResult = new String(mNdefRecord.getPayload(), "UTF-8");
                        String string1 = "Current temperature:  ";
                        int index = readResult.indexOf(string1);
                        if (index > 0) {
                            String string2 = readResult.substring(index + string1.length());
                            String string3 = "C";
                            index = string2.indexOf(string3);
                            String string4 = string2.substring(0, index);
                            string = string4;
                            break;
                        }
                    }
                }
            }
        }
        return string;
    }

    /**
     * 往nfc写入数据
     */
    public static void writeNFCToTag(String data, Intent intent) throws IOException, FormatException {
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        Ndef ndef = Ndef.get(tag);
        ndef.connect();
        NdefRecord ndefRecord = NdefRecord.createTextRecord(null, data);
        NdefRecord[] records = {ndefRecord};
        NdefMessage ndefMessage = new NdefMessage(records);
        ndef.writeNdefMessage(ndefMessage);
    }

    /**
     * 读取nfcID
     */
    public static String readNFCId(Intent intent) throws UnsupportedEncodingException {
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        String id = ByteArrayToHexString(tag.getId());
        return id;
    }

    /**
     * 将字节数组转换为字符串
     */
    private static String ByteArrayToHexString(byte[] inarray) {
        int i, j, in;
        String[] hex = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"};
        String out = "";

        for (j = 0; j < inarray.length; ++j) {
            in = (int) inarray[j] & 0xff;
            i = (in >> 4) & 0x0f;
            out += hex[i];
            i = in & 0x0f;
            out += hex[i];
        }
        return out;
    }
}
