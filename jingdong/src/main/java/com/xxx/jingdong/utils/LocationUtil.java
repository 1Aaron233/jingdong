package com.xxx.jingdong.utils;

import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * ClassName:LocationUtil
 * Package:com.example.jingdong.utils
 * Description:
 *
 * @Author:Aaron
 * @Create:2023/8/18 - 18:40
 * @Version:v1.0
 */
public class LocationUtil {
    public final static String BAIDU_MAP_AK = "CKpchaUtRt8OgrzufGtiFnZ7qNGHBxnc";

    /**
     * 根据经纬度调用百度API获取 地理位置信息，根据经纬度
     * @param longitude 经度
     * @param latitude 纬度
     * @return
     */
    public static JSONObject getAddressInfoByLngAndLat(String longitude, String latitude){
        JSONObject obj = new JSONObject();
        String location=latitude+","+longitude;
        //百度url  coordtype :bd09ll（百度经纬度坐标）、bd09mc（百度米制坐标）、gcj02ll（国测局经纬度坐标，仅限中国）、wgs84ll（ GPS经纬度）
        //v3
//        String url ="http://api.map.baidu.com/reverse_geocoding/v3/?ak="+BAIDU_MAP_AK+"&output=json&coordtype=wgs84ll&location="+location;
        //v2
        String url1 = "http://api.map.baidu.com/geocoder/v2/?location=" + location + "&output=json&coordtype=wgs84ll&ak=" + BAIDU_MAP_AK;
        try {
//            String result1 = HttpUtils.get(url1);
//            JSONObject jsonObject = new JSONObject();
//            if (StringUtils.isNotBlank(result1)) {
//                jsonObject = JSONObject.parseObject(result1);
//                System.out.println(jsonObject);
//            }
            String json = loadJSON(url1);
            obj = JSONObject.parseObject(json);
            System.out.println(obj.toString());
            // status:0 成功
            String success="0";
            String status = String.valueOf(obj.get("status"));
            if(success.equals(status)){
                String result = String.valueOf(obj.get("result"));
                JSONObject resultObj = JSONObject.parseObject(result);
                String addressComponent = String.valueOf(resultObj.get("addressComponent"));
                //JSON字符串转换成Java对象
                // AddressComponent addressComponentInfo = JSONObject.parseObject(addressComponent, AddressComponent.class);
                System.out.println("addressComponentInfo:"+addressComponent);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("未找到相匹配的经纬度，请检查地址！");
        }
        return obj;
    }

    public static String loadJSON(String url) {
        StringBuilder json = new StringBuilder();
        try {
            URL oracle = new URL(url);
            URLConnection yc = oracle.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream(), "UTF-8"));
            String inputLine = null;
            while ((inputLine = in.readLine()) != null) {
                json.append(inputLine);
            }
            in.close();
        } catch (MalformedURLException e) {} catch (IOException e) {}
        return json.toString();
    }
}
