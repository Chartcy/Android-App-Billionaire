/**
 * Copyright 2014  XCL-Charts
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * right_icon may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * @Project XCL-Charts
 * @Description Android图表基类库
 * @author XiongChuanLiang<br />(xcl_168@aliyun.com)
 * @Copyright Copyright (c) 2014 XCL-Charts (www.xclcharts.com)
 * @license http://www.apache.org/licenses/  Apache v2 License
 * @version 1.0
 */

package i.com.TrillionaireBill.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import org.xclcharts.chart.PieChart;
import org.xclcharts.chart.PieData;
import org.xclcharts.common.DensityUtil;
import org.xclcharts.common.MathHelper;
import org.xclcharts.event.click.ArcPosition;
import org.xclcharts.renderer.XEnum;
import org.xclcharts.renderer.plot.PlotLegend;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Locale;

public class PieChart02View extends DemoView implements Runnable {

    private String TAG = "PieChart02View";
    private PieChart chart = new PieChart();
    private LinkedList<PieData> chartData = new LinkedList<PieData>();
    Paint mPaintToolTip = new Paint(Paint.ANTI_ALIAS_FLAG);
    private double total = 0;

    private PieChartListener listener;

    public void setListener(PieChartListener listener) {
        this.listener = listener;
    }

    public interface PieChartListener {
        void itemClick(String name);
    }

    public PieChart02View(Context context) {
        super(context);
    }

    public PieChart02View(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    private void initView() {
        chartRender();

        //綁定手势滑动事件
        this.bindTouch(this, chart);
        new Thread(this).start();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //图所占范围大小
        chart.setChartRange(w, h);
    }


    private void chartRender() {
        try {
            //标签显示(隐藏，显示在中间，显示在扇区外面,折线注释方式)
            chart.setLabelStyle(XEnum.SliceLabelStyle.BROKENLINE);
            chart.getLabelBrokenLine().setLinePointStyle(XEnum.LabelLinePoint.END);
            chart.syncLabelColor();
            chart.syncLabelPointColor();

            //图的内边距
            //注释折线较长，缩进要多些
            int[] ltrb = new int[4];
            ltrb[0] = DensityUtil.dip2px(getContext(), 80); //left
            ltrb[1] = DensityUtil.dip2px(getContext(), 65); //up_icon
            ltrb[2] = DensityUtil.dip2px(getContext(), 80); //right
            ltrb[3] = DensityUtil.dip2px(getContext(), 50); //bottom

            chart.setPadding(ltrb[0], ltrb[1], ltrb[2], ltrb[3]);

            //标题
            chart.setTitle("总额 ".concat(String.format(Locale.CHINA, "%.2f", total)));
            chart.setTitleVerticalAlign(XEnum.VerticalAlign.BOTTOM);
            //隐藏渲染效果
            chart.hideGradient();
            chart.disablePanMode();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            Log.e(TAG, e.toString());
        }
    }

    @Override
    public void render(Canvas canvas) {
        // TODO Auto-generated method stub
        try {
            chart.render(canvas);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        super.onTouchEvent(event);
        if (event.getAction() == MotionEvent.ACTION_UP) {
            triggerClick(event.getX(), event.getY());
        }
        return true;
    }

    //触发监听
    private void triggerClick(float x, float y) {
        if (!chart.getListenItemClickStatus()) return;
        ArcPosition record = chart.getPositionRecord(x, y);
        if (null == record) {
            return;
        }

        PieData pData = chartData.get(record.getDataID());

        for (int i = 0; i < chartData.size(); i++) {
            PieData cData = chartData.get(i);
            if (i == record.getDataID()) {
                if (cData.getSelected()) {
                    break;
                } else {
                    cData.setSelected(true);
                }
            } else {
                cData.setSelected(false);
            }
        }

        listener.itemClick(pData.getKey());

        //显示选中框
        chart.showFocusArc(record, pData.getSelected());
        chart.getFocusPaint().setStyle(Style.STROKE);
        chart.getFocusPaint().setStrokeWidth(5);
        chart.getFocusPaint().setColor(Color.GREEN);
        chart.getFocusPaint().setAlpha(100);

        //在点击处显示tooltip
        mPaintToolTip.setColor(Color.RED);
        chart.getToolTip().setCurrentXY(x, y);
        chart.getToolTip().addToolTip(" key:" + pData.getKey() + " Label:" + pData.getLabel(), mPaintToolTip);

        this.refreshChart();
    }


    @Override
    public void run() {
        // TODO Auto-generated method stub
        try {
            chartAnimation();
        } catch (Exception e) {
            Thread.currentThread().interrupt();
        }
    }

    private void chartAnimation() {
        try {

            float sum = 0.0f;
            int count = chartData.size();
            for (int i = 0; i < count; i++) {
                Thread.sleep(150);
                ArrayList<PieData> animationData = new ArrayList<PieData>();

                sum = 0.0f;

                for (int j = 0; j <= i; j++) {
                    animationData.add(chartData.get(j));
                    sum = (float) MathHelper.getInstance().add(
                            sum, chartData.get(j).getPercentage());
                }

                animationData.add(new PieData("", "", MathHelper.getInstance().sub(100.0f, sum),
                        Color.argb(1, 0, 0, 0)));
                chart.setDataSource(animationData);

                //激活点击监听
                if (count - 1 == i) {
                    //激活点击监听
                    chart.ActiveListenItemClick();
                    chart.showClikedFocus();
                    chart.disablePanMode();

                    //显示图例
                    PlotLegend legend = chart.getPlotLegend();
                    legend.show();
                    legend.setHorizontalAlign(XEnum.HorizontalAlign.CENTER);
                    legend.setVerticalAlign(XEnum.VerticalAlign.BOTTOM);
                    legend.showBox();
                }
                postInvalidate();
            }

        } catch (Exception e) {
            Thread.currentThread().interrupt();
        }

    }

    public void setData(LinkedList<PieData> data, double totalTimePrice) {
        this.chartData = data;
        this.total = totalTimePrice;
        initView();
        invalidate();
    }
}
