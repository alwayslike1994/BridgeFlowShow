/**
 * Created by alwayslike on 2017/10/11.
 */
var bridge;
var dateType;
function createXMLHttpRequest() {
    if (window.XMLHttpRequest) {
        return new XMLHttpRequest();
    } else {
        if (window.ActiveXObject) {
            try {//IE5、6
                return new ActiveXObject("Microsoft.XMLHTTP");
            } catch (e) {
                try {
                    return new ActiveXObject("Msxml2.XMLHTTP");
                } catch (e) {
                }
            }
        }
    }
}

function sendRequest() {
    var XMLHttpRequest = createXMLHttpRequest();
    var a = Math.random();
    var url = "http://222.20.80.221:9009/AAA/" + a;
    alert(url);
    XMLHttpRequest.open("POST", url, true);
    XMLHttpRequest.setRequestHeader("Content-Type", "text/plain");
    XMLHttpRequest.onreadystatechange = processResponse(XMLHttpRequest, a);
    XMLHttpRequest.send("{\"bridge\":" + bridge + "," + "\"dateType\":" + dateType + "," + "\"a\":" + a + "}");


}

function processResponse(XMLHttpRequest, a) {
    alert("xmlhttp.status = " + XMLHttpRequest.status);
    alert("xmlhttp.readyState = " + XMLHttpRequest.readyState);
    if (XMLHttpRequest.readyState === 4) {
        if (XMLHttpRequest.status === 200 || XMLHttpRequest.status === 0) {

            document.getElementById("img").src = "http://222.20.80.221:9009/img/chart" + a + ".png";
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
function isload() {

}