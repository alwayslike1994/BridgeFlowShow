package utils;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.*;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.time.Hour;
import org.jfree.data.time.Minute;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * Created by alwayslike on 2017/9/16.
 */
public class ChartUtil {

    public static JFreeChart doLineChart(ResultSet set, TimeSeries timeSeries, String dateType) {

        TimeSeriesCollection chartTime = new TimeSeriesCollection();

        if (dateType.equals("minute")) {
            try {
                while (set.next()) {
                    String date = set.getString("date");
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmm");
                    Date date1 = dateFormat.parse(date);
                    double flow = set.getInt("flow");
                    int minute = date1.getMinutes();
                    int hour = date1.getHours();
                    int day = date1.getDate();
                    int month = date1.getMonth() + 1;
                    int year = date1.getYear() + 1900;

                    timeSeries.add(new Minute(minute, hour, day, month, year), flow);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            if (dateType.equals("hour")) {
                try {
                    while (set.next()) {
                        String date = set.getString("date");
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddhh");
                        Date date1 = dateFormat.parse(date);
                        double flow = set.getInt("flow");
                        int hour = date1.getHours();
                        int day = date1.getDate();
                        int month = date1.getMonth() + 1;
                        int year = date1.getYear() + 1900;
                        timeSeries.add(new Hour(hour, day, month, year), flow);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }


        chartTime.addSeries(timeSeries);
        XYDataset date = chartTime;

        // 使用ChartFactory来创建时间序列的图表对象
        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                "车流量"+dateType+"统计图", // 图形标题
                "时间", // X轴说明
                "访问量", // Y轴说明
                date, // 数据
                true, // 是否创建图例
                true, // 是否生成Tooltips
                false // 是否生产URL链接
        );


        // 设置整个图片的背景色
        chart.setBackgroundPaint(Color.pink);
        // 设置图片有边框
        chart.setBorderVisible(true);
        // 获得图表区域对象
        XYPlot xyPlot = (XYPlot) chart.getPlot();
        // 设置报表区域的背景色
        xyPlot.setBackgroundPaint(Color.white);
        // 设置横 纵坐标网格颜色
        xyPlot.setDomainGridlinePaint(Color.DARK_GRAY);
        xyPlot.setRangeGridlinePaint(Color.DARK_GRAY);
        // 设置横、纵坐标交叉线是否显示
        xyPlot.setDomainCrosshairVisible(true);
        xyPlot.setRangeCrosshairVisible(true);
        // 获得数据点（X,Y）的render，负责描绘数据点
        XYItemRenderer xyItemRenderer = xyPlot.getRenderer();
        if (xyItemRenderer instanceof XYLineAndShapeRenderer) {
            XYLineAndShapeRenderer xyLineAndShapeRenderer = (XYLineAndShapeRenderer) xyItemRenderer;
            xyLineAndShapeRenderer.setShapesVisible(true); // 数据点可见
            xyLineAndShapeRenderer.setShapesFilled(true); // 数据点是实心点
            xyLineAndShapeRenderer.setSeriesFillPaint(0, Color.BLUE); // 数据点填充为蓝色
            xyLineAndShapeRenderer.setSeriesFillPaint(1, Color.red); // 数据点填充为蓝色
            xyLineAndShapeRenderer.setUseFillPaint(true);// 将设置好的属性应用到render上
        }
        // 配置以下内容方可解决乱码问题
        // 配置字体
        Font xfont = new Font("宋体", Font.PLAIN, 12);    // X轴
        Font yfont = new Font("宋体", Font.PLAIN, 12);    // Y轴
        Font kfont = new Font("宋体", Font.PLAIN, 12);    // 底部
        Font titleFont = new Font("宋体", Font.BOLD, 25); // 图片标题
        // 图片标题
        chart.setTitle(new TextTitle(chart.getTitle().getText(), titleFont));
        // 底部
        chart.getLegend().setItemFont(kfont);
        // X 轴
        ValueAxis domainAxis = xyPlot.getDomainAxis();
        domainAxis.setLabelFont(xfont);// 轴标题
        domainAxis.setTickLabelFont(xfont);// 轴数值
        domainAxis.setTickLabelPaint(Color.BLACK); // 字体颜色
        domainAxis.setUpperMargin(0.06);
        domainAxis.setAutoTickUnitSelection(false);
        // Y 轴
        ValueAxis rangeAxis = xyPlot.getRangeAxis();
        rangeAxis.setLabelFont(yfont);
        rangeAxis.setLabelPaint(Color.BLACK); // 字体颜色
        rangeAxis.setTickLabelFont(yfont);
        // 定义坐标轴上日期显示的格式
        DateAxis dateAxis = (DateAxis) xyPlot.getDomainAxis();
        // 设置日期格式
        if (dateType.equals("minute")) {
            dateAxis.setTickUnit(new DateTickUnit(DateTickUnit.MINUTE, 10, new SimpleDateFormat("yyyy年MM月dd日hh时mm分")));

        } else {
            if (dateType.equals("hour")) {
                dateAxis.setTickUnit(new DateTickUnit(DateTickUnit.HOUR, 1, new SimpleDateFormat("yyyy年MM月dd日hh时")));

            }
        }

        return chart;
    }

    public static JFreeChart doPillarChart(ResultSet set, String dateType) {
        DefaultCategoryDataset datasets = new DefaultCategoryDataset();
        try {
            while (set.next()) {
                datasets.addValue((double) set.getInt("flow"), "车流量",
                        set.getString("date"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // 使用ChartFactory创建3D柱状图，不想使用3D，直接使用createBarChart
        JFreeChart chart = ChartFactory.createBarChart3D(
                "车辆" + dateType + "访问量统计图", // 图表标题
                "时间", // 目录轴的显示标签
                "访问量", // 数值轴的显示标签
                datasets, // 数据集
                PlotOrientation.VERTICAL, // 图表方向，此处为垂直方向
                // PlotOrientation.HORIZONTAL, //图表方向，此处为水平方向
                true, // 是否显示图例
                true, // 是否生成工具
                false // 是否生成URL链接
        );
        // 设置整个图片的背景色
        chart.setBackgroundPaint(Color.pink);
        // 设置图片有边框
        chart.setBorderVisible(true);
        Font kfont = new Font("宋体", Font.PLAIN, 12);    // 底部
        Font titleFont = new Font("宋体", Font.BOLD, 25); // 图片标题
        // 图片标题
        chart.setTitle(new TextTitle(chart.getTitle().getText(), titleFont));
        // 底部
        chart.getLegend().setItemFont(kfont);
        // 得到坐标设置字体解决乱码
        CategoryPlot categoryplot = (CategoryPlot) chart.getPlot();
        categoryplot.setDomainGridlinesVisible(true);

        categoryplot.setRangeCrosshairVisible(true);
        categoryplot.setRangeGridlinePaint(Color.DARK_GRAY);
        categoryplot.setDomainGridlinePaint(Color.DARK_GRAY);
        categoryplot.setBackgroundPaint(Color.white);
        categoryplot.setRangeCrosshairPaint(Color.DARK_GRAY);
        NumberAxis numberaxis = (NumberAxis) categoryplot.getRangeAxis();
        numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        BarRenderer barrenderer = (BarRenderer) categoryplot.getRenderer();
        barrenderer.setBaseItemLabelFont(new Font("宋体", Font.PLAIN, 12));
        barrenderer.setSeriesItemLabelFont(1, new Font("宋体", Font.PLAIN, 12));
        CategoryAxis domainAxis = categoryplot.getDomainAxis();
            /*------设置X轴坐标上的文字-----------*/
        domainAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 11));
            /*------设置X轴的标题文字------------*/
        domainAxis.setLabelFont(new Font("宋体", Font.PLAIN, 12));
            /*------设置Y轴坐标上的文字-----------*/
        numberaxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 12));
            /*------设置Y轴的标题文字------------*/
        numberaxis.setLabelFont(new Font("宋体", Font.PLAIN, 12));
            /*------这句代码解决了底部汉字乱码的问题-----------*/
        chart.getLegend().setItemFont(new Font("宋体", Font.PLAIN, 12));

        return chart;
    }
}

