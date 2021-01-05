package com.fz.zf.util;

import com.baidu.aip.ocr.AipOcr;
import org.json.JSONObject;

import java.util.HashMap;

public class BaiDuScanUtils {
    //设置APPID/AK/SK
    public static final String APP_ID = "23487330";
    public static final String API_KEY = "EWWbIGuNfEtLVM630C7MKYs9";
    public static final String SECRET_KEY = "pvuuNdxq6acmi4cQFjwKSWnOKYgUiEcW";

    public static JSONObject getRes(String url) {
        // 初始化一个AipOcr
        AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        // 调用接口
        // String path = "D:\\aaaa.png";
        JSONObject res = client.basicGeneralUrl(url, new HashMap<String, String>());
        System.out.println(res.toString(2));
        return res;
    }

}
