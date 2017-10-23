package utils;

/**
 * Created by alwayslike on 2017/9/16.
 */
public class ReqBean {
    String bridge;
    String dateType;
    double a;

    public ReqBean(String bridge, String dateType, double aDouble) {
        this.bridge = bridge;
        this.dateType = dateType;
        this.a = aDouble;
    }


    public String getBridge() {
        return bridge;
    }

    public String getDateType() {
        return dateType;
    }

    public double getA() {
        return a;
    }
}
