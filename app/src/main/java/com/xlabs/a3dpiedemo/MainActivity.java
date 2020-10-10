package com.xlabs.a3dpiedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.java4less.rchart.Chart;
import com.java4less.rchart.FillStyle;
import com.java4less.rchart.Legend;
import com.java4less.rchart.LineStyle;
import com.java4less.rchart.PieDataSerie;
import com.java4less.rchart.PiePlotter;
import com.java4less.rchart.Title;
import com.java4less.rchart.android.ChartPanel;
import com.java4less.rchart.gc.ChartColor;
import com.java4less.rchart.gc.ChartFont;
import com.java4less.rchart.gc.ChartImage;
import com.java4less.rchart.gc.GraphicsProvider;

import java.util.Date;

public class MainActivity extends AppCompatActivity {
//    ChartPanel chartPanel;


    private LinearLayout main;
    TextView text;
    private static int width;
    private static int height;
    ChartPanel chartPanel1;
    static String[] lableName=new String[]{"破洞","油污","色差","掉边","折皱"};//名称
    static double[] values=new double[]{20.3,50.8,34.1,12.4,20.3};//存放数值
    static boolean[] groupValues ;//是否分组的标识
    static FillStyle[] fillStyleColor;//存放颜色
    static ChartColor[] color = new ChartColor[] {//颜色表
            GraphicsProvider.getColor(ChartColor.AQUAMARINE), //蓝绿色
            GraphicsProvider.getColor(ChartColor.VIOLET), //紫罗兰色
            GraphicsProvider.getColor(ChartColor.BLUEVIOLET), //蓝紫色
            GraphicsProvider.getColor(ChartColor.CORAL),//珊瑚色
            GraphicsProvider.getColor(ChartColor.GREENYELLOW), //黄绿色
            GraphicsProvider.getColor(ChartColor.SKYBLUE), //天蓝色
            GraphicsProvider.getColor(ChartColor.PINK), //粉红色
            GraphicsProvider.getColor(ChartColor.BORLYWOOD), //实木色
            GraphicsProvider.getColor(ChartColor.FORESTGREEN)};//森林绿色




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text=findViewById(R.id.text);
        Typeface tf=Typeface.createFromAsset(getAssets(),"DS-DIGIB-2.ttf");
        text.setTypeface(tf);
        text.setText(new Date()+"");


        main=(LinearLayout)findViewById(R.id.main);
        //获取设备屏幕的宽高
        WindowManager wm = (WindowManager) this
                .getSystemService(Context.WINDOW_SERVICE);
        width = wm.getDefaultDisplay().getWidth();
        height = wm.getDefaultDisplay().getHeight();
        groupValues=new boolean[values.length];
        fillStyleColor=new FillStyle[values.length];
        for(int i=0;i<values.length;i++)
        {
            fillStyleColor[i]=new FillStyle(color[i]);
            if(i<values.length-1)
            {
                groupValues[i]=false;//除了最后一个外都赋值为true
            }
        }
        chartPanel1 = new ChartPanel(this);
//        chartPanel1=findViewById(R.id.chartPanel1);
        chartPanel1.setChart(TheChart());
        main.addView(chartPanel1);



    }

    protected void onDestroy() {
        if (chartPanel1 != null)
            if (chartPanel1.getChart() != null)
                chartPanel1.getChart().stopUpdater();
        super.onDestroy();
    }

    public Chart TheChart()
    {
        //new出来一个图饼的对象， 参数简介1.所占的比例 2.颜色 3.是否分组（是否种其他的块是分开的） 4.文字介绍
        PieDataSerie pds = new PieDataSerie(values, fillStyleColor, groupValues, lableName);
        //设置label的样式
        pds.valueFont = GraphicsProvider.getFont("Arial", ChartFont.PLAIN, (int) getResources().getDimension(R.dimen.dimen1));
        //设置label到中心的距离
        pds.textDistanceToCenter = 1.7;
        pds.valueColor=GraphicsProvider.getColor(ChartColor.WHITE);

        //设置图饼的标题
//        Title title = new Title("理财账户");
        //开始绘图
        PiePlotter pp = new PiePlotter();
        //设置3D效果为true
        pp.effect3D = true;
        //设置边框
        pp.border = new LineStyle(1,GraphicsProvider.getColor(ChartColor.WHITE),LineStyle.LINE_NORMAL);
        //设置label的格式（#PERCENTAGE#，#VALUE#,#LABEL#）什么也不想显示的话直接“ ”里面有一个空格就可以了
        pp.labelFormat = " #PERCENTAGE#(#LABEL#)";
        //设置半径
        pp.radiusModifier = 1.5;
        //设置块与块之间的间隔
        pp.space = 10;
        //设置label到块之间的线的样式
        pp.labelLine = new LineStyle(1,GraphicsProvider.getColor(ChartColor.WHITE),LineStyle.LINE_NORMAL);

//设置图饼旁边的文字解说的样式
        //生成一个对象
        Legend legend = new Legend();
        legend.verticalLayout=false;
        legend.background=new FillStyle(GraphicsProvider.getColor(ChartColor.WHITE));
        legend.font=GraphicsProvider.getFont("Arial", ChartFont.PLAIN, (int) getResources().getDimension(R.dimen.dimen1));
        //因为我这里不需要解说，所以设置它为" ",中间有空格，没有空格的话，会出现多余的文字
        legend.legendLabel="";
        for (int i = 0; i < values.length; i++)
        {
//            legend.background=new FillStyle(color[i]);
            legend.addItem(lableName[i], new FillStyle(color[i]));
        }
        //new一个图表对象，用来存放生成的图饼
        com.java4less.rchart.Chart chart = new com.java4less.rchart.Chart(null, pp, null, null);
        chart.layout = Chart.LAYOUT_LEGEND_BOTTOM;
        chart.back = new FillStyle(GraphicsProvider.getColor(ChartColor.BLACK));
        chart.topMargin = 0.2;
        chart.bottomMargin = 0.1;
        chart.leftMargin =0.1;
        chart.legend = legend;
        chart.setHeight(width/3*2);
//        chart.setHeight(width/3*2);
//        chart.setHeight((int) getResources().getDimension(R.dimen.dimen2));
        chart.setWidth(height/3*2);
        chart.addSerie(pds);
        return chart;
    }

}