package servlet;

import com.google.gson.Gson;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.*;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.time.*;
import org.jfree.data.xy.XYDataset;
import utils.ChartUtil;
import utils.DButil;
import utils.ReqBean;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

/**
 * Created by alwayslike on 2017/9/14.
 */
@WebServlet(name = "servlet2", urlPatterns = "/AAA/*")
public class LineServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String bridge = req.getParameter("bridge");
        String dateType = req.getParameter("dateType");
        String str2 = "select * from ";
        resp.setContentType("image/jpeg");
        JFreeChart chart = null;
        switch (dateType) {
            case "分":
                str2 += "minute_flow";
                ResultSet rs2 = DButil.getResultSet(bridge, str2);
                TimeSeries timeSeries2 = new TimeSeries("分访问", Minute.class);
                chart = ChartUtil.doLineChart(rs2, timeSeries2,"minute");
                break;
            case "时":
                str2 += "hour_flow";
                ResultSet rs3 = DButil.getResultSet(bridge, str2);
                TimeSeries timeSeries3 = new TimeSeries("时访问", Hour.class);
                chart = ChartUtil.doLineChart(rs3, timeSeries3,"hour");
                break;
            case "day":
                str2 += "day_flow";
                ResultSet rs4 = DButil.getResultSet(bridge, str2);
                chart = ChartUtil.doPillarChart(rs4, dateType);
                break;
            case "年":
                str2 += "year_flow";
                ResultSet rs5 = DButil.getResultSet(bridge, str2);
                chart = ChartUtil.doPillarChart(rs5, dateType);
                break;
        }
        PrintWriter writer = resp.getWriter();
        writer.write("G:\\springmvc_demo\\newDemo\\web\\img\\chart1.png");
        writer.close();
        // 生成图片并输出
        ChartUtilities.writeChartAsJPEG(resp.getOutputStream(), 1.0f,
                chart, 1600, 800, null);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//
        BufferedReader bufferedReader = req.getReader();
        StringBuilder builder = new StringBuilder();
        String line = "";
        while ((line = bufferedReader.readLine()) != null) {
            builder.append(line);
        }
        String reqData = builder.toString();
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=utf-8");
        PrintWriter out=resp.getWriter();


//        String bridge = req.getParameter("bridge");
//        String datetype=req.getParameter("dateType");
        ReqBean reqBean = new Gson().fromJson(reqData, ReqBean.class);
        String bridge = reqBean.getBridge();
        String datetype = reqBean.getDateType();
        Double a=reqBean.getA();
        String path = this.getServletContext().getRealPath("/");
        System.out.println(path);
        String str2 = "select * from ";
        JFreeChart chart = null;
        switch (datetype) {
            case "minute":
                str2 += "minute_flow";
                ResultSet rs2 = DButil.getResultSet(bridge, str2);
                TimeSeries timeSeries2 = new TimeSeries("分访问", Minute.class);
                chart = ChartUtil.doLineChart(rs2, timeSeries2,"minute");
                break;
            case "hour":
                str2 += "hour_flow";
                ResultSet rs3 = DButil.getResultSet(bridge, str2);
                TimeSeries timeSeries3 = new TimeSeries("时访问", Hour.class);
                chart = ChartUtil.doLineChart(rs3, timeSeries3,"hour");
                break;
            case "day":
                str2 += "day_flow";
                ResultSet rs4 = DButil.getResultSet(bridge, str2);
                chart = ChartUtil.doPillarChart(rs4, datetype);
                break;
            case "year":
                str2 += "year_flow";
                ResultSet rs5 = DButil.getResultSet(bridge, str2);
                chart = ChartUtil.doPillarChart(rs5, datetype);
                break;
        }
        // 生成图片并输出
//        ChartUtilities.writeChartAsJPEG(resp.getOutputStream(), 1.0f,
//                chart, 1600, 800, null);
        ChartUtilities.saveChartAsPNG(new File(path+"img\\chart" + a+ ".png"), chart, 1680, 800);
//        writer.write("G:\\springmvc_demo\\newDemo\\web\\img\\chart1.png");
        System.out.println(a);
        out.print("true");


    }

}


