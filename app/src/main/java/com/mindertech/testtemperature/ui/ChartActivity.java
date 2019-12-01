package com.mindertech.testtemperature.ui;

import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.gyf.immersionbar.ImmersionBar;
import com.mindertech.testtemperature.AAChartCoreLib.AAChartConfiger.AAChartModel;
import com.mindertech.testtemperature.AAChartCoreLib.AAChartConfiger.AAChartView;
import com.mindertech.testtemperature.AAChartCoreLib.AAChartConfiger.AASeriesElement;
import com.mindertech.testtemperature.AAChartCoreLib.AAChartEnum.AAChartType;
import com.mindertech.testtemperature.R;
import com.mindertech.testtemperature.model.MDTemperature;
import com.mindertech.testtemperature.model.Temperature;
import com.mindertech.testtemperature.utils.MDAccountManager;
import com.mindertech.testtemperature.utils.NfcUtils;
import com.mindertech.testtemperature.utils.RealmUtils;
import com.mindertech.testtemperature.utils.Utils;
import com.tencent.bugly.crashreport.CrashReport;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @project TestTemperature
 * @package：com.mindertech.testtemperature.ui
 * @anthor xiangxia
 * @time 2019-11-28 15:41
 * @description 描述
 */
public class ChartActivity extends BaseActivity {

    @BindView(R.id.chart_shoulder_left)
    AAChartView chartShoulderLeft;
    @BindView(R.id.layout_shoulder_left)
    LinearLayout layoutShoulderLeft;
    @BindView(R.id.chart_shoulder_right)
    AAChartView chartShoulderRight;
    @BindView(R.id.layout_shoulder_right)
    LinearLayout layoutShoulderRight;
    @BindView(R.id.chart_arm_left)
    AAChartView chartArmLeft;
    @BindView(R.id.layout_arm_left)
    LinearLayout layoutArmLeft;
    @BindView(R.id.chart_arm_right)
    AAChartView chartArmRight;
    @BindView(R.id.layout_arm_right)
    LinearLayout layoutArmRight;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideActionBar();
        setContentView(R.layout.activity_chart);
        ButterKnife.bind(this);
        ImmersionBar.with(this).init();

        initData();
    }

    private void initData() {

        List<Temperature> list1 = MDAccountManager.getInstance().getTemperatureList(Utils.temperature_point_shoulder_left);
        List<Temperature> list2 = MDAccountManager.getInstance().getTemperatureList(Utils.temperature_point_shoulder_right);
        List<Temperature> list3 = MDAccountManager.getInstance().getTemperatureList(Utils.temperature_point_arm_left);
        List<Temperature> list4 = MDAccountManager.getInstance().getTemperatureList(Utils.temperature_point_arm_right);

        chartShoulderLeft.aa_drawChartWithChartModel(getSeries(list1, Utils.getPointString(Utils.temperature_point_shoulder_left)));
        chartShoulderRight.aa_drawChartWithChartModel(getSeries(list2, Utils.getPointString(Utils.temperature_point_shoulder_left)));
        chartArmLeft.aa_drawChartWithChartModel(getSeries(list3, Utils.getPointString(Utils.temperature_point_arm_left)));
        chartArmRight.aa_drawChartWithChartModel(getSeries(list4, Utils.getPointString(Utils.temperature_point_arm_right)));
    }

    private AAChartModel getSeries(List<Temperature> list, String point) {

        Object[] prop = new Object[list.size()];
        for (int i = 0;i < list.size();i ++) {
            Temperature temperature = list.get(i);
            prop[i] = Float.valueOf(temperature.temperature).floatValue();
        }

        AASeriesElement aaSeriesElement = new AASeriesElement();
        aaSeriesElement.name(point);
        aaSeriesElement.color("#008577");
        aaSeriesElement.data(prop);

        AAChartModel aaChartModel = new AAChartModel()
                .chartType(AAChartType.Spline)
                .title("")
                .subtitle("")
                .backgroundColor("#ffffff")
                .dataLabelsEnabled(true)
                .yAxisGridLineWidth((float) 0.0)
                .series(new AASeriesElement[]{aaSeriesElement});
        return aaChartModel;
    }
}
