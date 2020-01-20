package com.hi.weiliao.base.utils;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class HttpClientUtil {

    private static final Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

    private static HostnameVerifier ignoreHostnameVerifier = new HostnameVerifier() {
        public boolean verify(String s, SSLSession sslsession) {
            return true;
        }
    };

    public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr,
            Map<String, String> headers) {
        HttpsURLConnection.setDefaultHostnameVerifier(ignoreHostnameVerifier);
        JSONObject jsonObject = null;
        HttpsURLConnection conn = null;
        try {
            @SuppressWarnings("restriction")
			URL url = new URL(null, requestUrl, new sun.net.www.protocol.https.Handler());
            conn = (HttpsURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            conn.setRequestMethod(requestMethod);
            if (headers != null && headers.size() > 0) {
                for (String key : headers.keySet()) {
                    conn.setRequestProperty(key, headers.get(key));
                }
            }
            // 当outputStr不为null时向输出流写数据
            if (null != outputStr) {
                OutputStream outputStream = conn.getOutputStream();
                // 注意编码格式
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }
            // 从输入流读取返回内容
            InputStream inputStream = null;
            try {
                inputStream = conn.getInputStream();
            } catch (Exception e){
                inputStream = conn.getErrorStream();
            } finally {
                if (inputStream == null) {
                    StringBuilder msg = new StringBuilder();
                    msg.append("https请求异常：");
                    msg.append("\n").append("requestUrl：").append(requestUrl);
                    msg.append("\n").append("requestMethod：").append(requestMethod);
                    msg.append("\n").append("outputStr：").append(outputStr);
                    msg.append("\n").append("headers：").append(headers);
                    logger.error("Can not get inputStream" + msg.toString());
                }
            }
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str;
            StringBuffer buffer = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            // 释放资源
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            jsonObject = JSONObject.parseObject(buffer.toString());
        } catch (ConnectException ce) {
            StringBuilder msg = new StringBuilder();
            msg.append("连接超时：");
            msg.append("\n").append("requestUrl：").append(requestUrl);
            msg.append("\n").append("requestMethod：").append(requestMethod);
            msg.append("\n").append("outputStr：").append(outputStr);
            msg.append("\n").append("headers：").append(headers);
            logger.error(msg.toString(),ce);
            return null;
        } catch (Exception e) {
            StringBuilder msg = new StringBuilder();
            msg.append("https请求异常：");
            msg.append("\n").append("requestUrl：").append(requestUrl);
            msg.append("\n").append("requestMethod：").append(requestMethod);
            msg.append("\n").append("outputStr：").append(outputStr);
            msg.append("\n").append("headers：").append(headers);
            logger.error(msg.toString(),e);
            return null;
        } finally {
            if (conn != null) {
                conn.disconnect();
            } else {
                StringBuilder msg = new StringBuilder();
                msg.append("https请求正常：");
                msg.append("\n").append("requestUrl：").append(requestUrl);
                msg.append("\n").append("requestMethod：").append(requestMethod);
                msg.append("\n").append("outputStr：").append(outputStr);
                msg.append("\n").append("headers：").append(headers);
                logger.debug(msg.toString());
            }
        }
        if (jsonObject == null){
            StringBuilder msg = new StringBuilder();
            msg.append("远程请求异常：");
            msg.append("\n").append("requestUrl：").append(requestUrl);
            msg.append("\n").append("requestMethod：").append(requestMethod);
            msg.append("\n").append("outputStr：").append(outputStr);
            msg.append("\n").append("headers：").append(headers);
            logger.error(msg.toString());
        }
        return jsonObject;
    }

    public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr,
            Map<String, String> headers, String encodeType) {
        HttpsURLConnection.setDefaultHostnameVerifier(ignoreHostnameVerifier);
        JSONObject jsonObject = null;
        HttpsURLConnection conns = null;
        HttpURLConnection conn = null;
        try {
            @SuppressWarnings("restriction")
			URL url = new URL(null, requestUrl, new sun.net.www.protocol.https.Handler());
            if ("https".equals(url.getProtocol())) {
                conns = (HttpsURLConnection) url.openConnection();// 正式
                conns.setDoOutput(true);
                conns.setDoInput(true);
                conns.setUseCaches(false);
                // 设置请求方式（GET/POST）
                conns.setRequestMethod(requestMethod);
                conns.setInstanceFollowRedirects(true);
                if (headers != null && headers.size() > 0) {
                    for (String key : headers.keySet()) {
                        conns.setRequestProperty(key, headers.get(key));
                    }
                }
                conns.connect();
                // 当outputStr不为null时向输出流写数据
                if (null != outputStr) {
                    OutputStream outputStream = conns.getOutputStream();
                    // 注意编码格式
                    outputStream.write(outputStr.getBytes(encodeType));
                    outputStream.close();
                }
                // 从输入流读取返回内容
                InputStream inputStream = conns.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, encodeType);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String str = null;
                StringBuffer buffer = new StringBuffer();
                while ((str = bufferedReader.readLine()) != null) {
                    buffer.append(str);
                }
                // 释放资源
                bufferedReader.close();
                inputStreamReader.close();
                inputStream.close();
                inputStream = null;
                conns.disconnect();
                jsonObject = JSONObject.parseObject(buffer.toString());
            } else {
                conn = (HttpURLConnection) url.openConnection();// 测试
                conn.setDoOutput(true);
                conn.setDoInput(true);
                conn.setUseCaches(false);
                // 设置请求方式（GET/POST）
                conn.setRequestMethod(requestMethod);
                conn.setInstanceFollowRedirects(true);
                if (headers != null && headers.size() > 0) {
                    for (String key : headers.keySet()) {
                        conn.setRequestProperty(key, headers.get(key));
                    }
                }
                conn.connect();
                // 当outputStr不为null时向输出流写数据
                if (null != outputStr) {
                    OutputStream outputStream = conn.getOutputStream();
                    // 注意编码格式
                    outputStream.write(outputStr.getBytes(encodeType));
                    outputStream.close();
                }
                // 从输入流读取返回内容
                InputStream inputStream = conn.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, encodeType);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String str = null;
                StringBuffer buffer = new StringBuffer();
                while ((str = bufferedReader.readLine()) != null) {
                    buffer.append(str);
                }
                // 释放资源
                bufferedReader.close();
                inputStreamReader.close();
                inputStream.close();
                inputStream = null;
                conn.disconnect();
                jsonObject = JSONObject.parseObject(buffer.toString());
            }
        } catch (ConnectException ce) {
            StringBuilder msg = new StringBuilder();
            msg.append("连接超时：");
            msg.append("\n").append("requestUrl：").append(requestUrl);
            msg.append("\n").append("requestMethod：").append(requestMethod);
            msg.append("\n").append("outputStr：").append(outputStr);
            msg.append("\n").append("headers：").append(headers);
            msg.append("\n").append("encodeType：").append(encodeType);
            logger.error(msg.toString(),ce);
        } catch (Exception e) {
            StringBuilder msg = new StringBuilder();
            msg.append("https请求异常：");
            msg.append("\n").append("requestUrl：").append(requestUrl);
            msg.append("\n").append("requestMethod：").append(requestMethod);
            msg.append("\n").append("outputStr：").append(outputStr);
            msg.append("\n").append("headers：").append(headers);
            msg.append("\n").append("encodeType：").append(encodeType);
            logger.error(msg.toString(),e);
        } finally {
            if (conns != null) {
                conns.disconnect();
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
        if (jsonObject == null){
            StringBuilder msg = new StringBuilder();
            msg.append("远程请求异常：");
            msg.append("\n").append("requestUrl：").append(requestUrl);
            msg.append("\n").append("requestMethod：").append(requestMethod);
            msg.append("\n").append("outputStr：").append(outputStr);
            msg.append("\n").append("headers：").append(headers);
            msg.append("\n").append("encodeType：").append(encodeType);
            logger.error(msg.toString());
        }
        return jsonObject;
    }

    public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr) {
        return httpRequest(requestUrl, requestMethod, outputStr, null);
    }

    @SuppressWarnings("unused")
	private void httptest() {
        String url = "http://webzhj.kisdee.com/engine";
        JSONObject head = new JSONObject();
        head.fluentPut("cmd", 101);
        head.fluentPut("sub", 2);
        head.fluentPut("ver", 7);

        JSONObject body = new JSONObject();
        body.fluentPut("kdusr", "hkqyy556");
        body.fluentPut("pwd", "VLjvvbXsdWKajSkyq252SQ==");
        body.fluentPut("pos_flag", 0);

        JSONObject param = new JSONObject();
        param.fluentPut("head", head);
        param.fluentPut("body", body);

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        JSONObject jsonObject = HttpClientUtil.httpRequest(url, "POST", param.toString(), headers);
        String kduser = (String) jsonObject.get("kdtoken");
        System.out.println(kduser);
    }

}
