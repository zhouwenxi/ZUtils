package com.qishui.commontoolslibrary.http.easyhttp;

import com.qishui.commontoolslibrary.core.FileUtils;
import com.qishui.commontoolslibrary.http.callback.ICallBack;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;

public class HttpService {

    /**
     * 超时时间
     */
    private static final int TIMEOUT_IN_MILLIONS = 15000;

    private static HttpService httpService;

    private HttpService() {
    }

    public static HttpService with() {
        if (httpService == null) {
            synchronized (HttpService.class) {
                if (httpService == null) {
                    httpService = new HttpService();
                }
            }
        }

        return httpService;
    }


    /**
     * Get请求，获得返回数据
     *
     * @param url
     * @return
     * @throws Exception
     */
    public void get(String url, ICallBack callBack) {

        InputStream is = null;
        HttpURLConnection httpURLConnection = null;
        try {
            httpURLConnection = setGETHttpURLConnection(new URL(url));
            httpURLConnection.connect();
            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                is = httpURLConnection.getInputStream();
                if (callBack != null) {
                    callBack.onSuccess(FileUtils.is2String(is));
                }
            } else {
                if (callBack != null) {
                    callBack.onfalure("errorCode_" + httpURLConnection.getResponseCode());
                }
            }
        } catch (Exception e) {
            if (callBack != null) {
                callBack.onfalure("errorMsg_" + e.getMessage());
            }
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }
    }

    /**
     * Get
     *
     * @param url
     * @param map
     * @param callBack
     */
    public void get(String url, HashMap<String, Object> map, ICallBack callBack) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(url);
        if (map != null) {
            stringBuilder.append("?");
            int size = map.size();
            int cur = 0;
            for (String key : map.keySet()) {
                cur++;
                stringBuilder.append(key).append("=").append(map.get(key));
                if (cur < size) {
                    stringBuilder.append("&");
                }
            }
        }
        get(stringBuilder.toString(), callBack);
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url 发送请求的 URL
     * @param map 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     * @throws Exception
     */
    public void post(String url, HashMap<String, Object> map, ICallBack callBack) {
        PrintWriter out = null;
        InputStream is = null;
        HttpURLConnection httpURLConnection = null;
        try {

            httpURLConnection = setPOSTHttpURLConnection(new URL(url));
            httpURLConnection.connect();
            if (map != null) {
                int size = map.size();
                int cur = 0;
                StringBuilder stringBuilder = new StringBuilder();
                for (String key : map.keySet()) {
                    cur++;
                    stringBuilder.append(key).append("=").append(map.get(key));
                    if (cur < size) {
                        stringBuilder.append("&");
                    }
                }

                // 获取URLConnection对象对应的输出流
                out = new PrintWriter(httpURLConnection.getOutputStream());
                // 发送请求参数
                out.print(stringBuilder.toString());
                // flush输出流的缓冲
                out.flush();
            }
            // 定义BufferedReader输入流来读取URL的响应

            int responseCode = httpURLConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                if (callBack != null) {
                    is = httpURLConnection.getInputStream();
                    callBack.onSuccess(FileUtils.is2String(is));
                }
            } else {
                if (callBack != null) {
                    callBack.onfalure("errorCode_" + httpURLConnection.getResponseCode());
                }
            }

        } catch (Exception e) {
            if (callBack != null) {
                callBack.onfalure("errorMsg_" + e.getMessage());
            }
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }
    }

    /**
     * Post
     *
     * @param url
     * @param callBack
     */
    public void post(String url, ICallBack callBack) {
        post(url, null, callBack);
    }

    public void postJson(String url, String json, ICallBack callBack) {
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;
        try {
            httpURLConnection = setPOSTJSONHttpURLConnection(new URL(url));
            httpURLConnection.connect();
            //使用字节流发送数据
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedOutputStream bos = new BufferedOutputStream(outputStream);
            //判断请求数据是否为空，并写入输出流
            if (json != null) {
                bos.write(json.getBytes("utf-8"));
            }
            bos.flush();
            outputStream.close();
            bos.close();
            //字符流写入数据
            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = httpURLConnection.getInputStream();
                if (callBack != null) {
                    callBack.onSuccess(FileUtils.is2String(inputStream));
                }
            } else {
                if (callBack != null) {
                    callBack.onfalure("errorCode_" + httpURLConnection.getResponseCode());
                }
            }
            if (inputStream != null) {
                inputStream.close();
            }
        } catch (Exception e) {
            if (callBack != null) {
                callBack.onfalure("errorMsg_" + e.getMessage());
            }
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }
    }


    /**
     * PostJson
     *
     * @param url
     * @return
     * @throws IOException
     * @throws ProtocolException
     */
    private HttpURLConnection setPOSTJSONHttpURLConnection(URL url) throws IOException, ProtocolException {
        HttpURLConnection httpURLConnection = setHttpURLConnection(url);
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setDoInput(true);
        return httpURLConnection;
    }

    /**
     * Post
     *
     * @param url
     * @return
     * @throws IOException
     */
    private HttpURLConnection setPOSTHttpURLConnection(URL url) throws IOException {
        HttpURLConnection httpURLConnection = setHttpURLConnection(url);
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setDoInput(true);
        return httpURLConnection;
    }

    /**
     * Get
     *
     * @param url
     * @return
     * @throws IOException
     */
    private HttpURLConnection setGETHttpURLConnection(URL url) throws IOException {
        HttpURLConnection httpURLConnection = setHttpURLConnection(url);
        httpURLConnection.setRequestMethod("GET");
        return httpURLConnection;
    }

    /**
     * 常用设置
     */
    private HttpURLConnection setHttpURLConnection(URL url) throws IOException {

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        // 设置通用的请求属性
        conn.setRequestProperty("accept", "*/*");
        conn.setRequestProperty("connection", "Keep-Alive");
        conn.setRequestProperty("charset", "utf-8");
        conn.setReadTimeout(TIMEOUT_IN_MILLIONS);
        conn.setConnectTimeout(TIMEOUT_IN_MILLIONS);
        conn.setUseCaches(false);
        return conn;
    }

}
