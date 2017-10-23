/**
 * Created by alwayslike on 2017/10/18.
 */
var bridge;
var dateType;
var XMLHttpReq;
var a;
function createXMLHttpRequest() {
    if (window.XMLHttpRequest) {
        //DOM 2浏览器
        XMLHttpReq = new XMLHttpRequest();
    }
    else if (window.ActiveXObject) {
        // IE浏览器
        try {
            XMLHttpReq = new ActiveXObject("Msxml2.XMLHTTP");
        }
        catch (e) {
            try {
                XMLHttpReq = new
                ActiveXObject("Microsoft.XMLHTTP");
            }
            catch (e) {
            }
        }
    }
}
function setBridge(id) {
    switch (id) {
        case"A":
            bridge = "temp01";
            // alert(bridge);
            break;
    }
}
function setDateType(type) {
    switch (type) {
        case"day":
            dateType = "day";
            break;
        case"minute":
            dateType = "minute";
            break;
        case"hour":
            dateType = "hour";
            break;
        case"year":
            dateType = "year";
            break;
    }
}
function sendRequest() {
    //完成XMLHttpRequest对象的初始化
    createXMLHttpRequest();
    //定义发送请求的目标URL
    a = Math.random();
    var url = "http://222.20.82.132:9009/AAA/" + a;
    //通过open方法取得与服务器的连接
    //发送POST请求

    XMLHttpReq.open("POST", url, true);

    //设置请求头-发送POST请求时需要该请求头
    XMLHttpReq.setRequestHeader("Content-Type", "text/plain");

    //指定XMLHttpRequest状态改变时的处理函数
    XMLHttpReq.onreadystatechange = processResponse;


    //即以：请求参数名=请求参数值 的形式发送请求参数。
    XMLHttpReq.send("{\"bridge\":" + bridge + "," + "\"dateType\":" + dateType + "," + "\"a\":" + a + "}");
}
function processResponse() {
    //当XMLHttpRequest读取服务器响应完成
    if (XMLHttpReq.readyState === 4) {
        //服务器响应正确（当服务器响应正确时，返回值为200的状态码）
        if (XMLHttpReq.status === 200 || XMLHttpReq.status === 0) {
            document.getElementById("img").src = "http://222.20.82.132:9009/img/chart" + a + ".png";

        }
        else {
            //提示页面不正常
            window.alert("您所请求的页面有异常。");
        }
    }
}