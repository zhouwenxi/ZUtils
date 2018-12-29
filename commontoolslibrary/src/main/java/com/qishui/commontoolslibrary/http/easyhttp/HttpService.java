package com.qishui.commontoolslibrary.http.easyhttp;

import com.qishui.commontoolslibrary.core.FileUtils;
import com.qishui.commontoolslibrary.http.callback.ICallBack;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

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

    private HttpURLConnection setUploadHttpURLConnection(URL url) throws IOException {
        HttpURLConnection httpURLConnection = setHttpURLConnection(url);
        httpURLConnection.setRequestMethod("POST");
        //  httpURLConnection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
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

    /**
     * 下载文件
     * @param urlPath
     * @param map
     * @param downloadDir
     * @param downloadFileName
     * @param callBack
     */
    public  void downloadFile(String urlPath, Map<String,Object>map,String downloadDir,String downloadFileName,ICallBack callBack){

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(urlPath);
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

        downloadFile(stringBuilder.toString(),  downloadDir, downloadFileName, callBack);
    }
    /**
     * 下载文件
     * @param urlPath  下载路径
     * @param downloadDir 下载目录
     * @param downloadFileName 下载名字
     * @param callBack
     */
    public  void downloadFile(String urlPath, String downloadDir,String downloadFileName,ICallBack callBack){

        File file = null;
        try {
            HttpURLConnection httpURLConnection = setGETHttpURLConnection(new URL(urlPath));
            httpURLConnection.connect();
            // 文件大小
            int fileLength = httpURLConnection.getContentLength();
            String path;
            if(downloadFileName==null || "".equals(downloadFileName)){
                // 文件名
                String filePathUrl = httpURLConnection.getURL().getFile();
                String fileFullName = filePathUrl.substring(filePathUrl.lastIndexOf(File.separatorChar) + 1);
                path = downloadDir + File.separatorChar + fileFullName;
            }else {
                path = downloadDir + File.separatorChar + downloadFileName;
            }
            BufferedInputStream bin = new BufferedInputStream(httpURLConnection.getInputStream());

            file = new File(path);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            OutputStream out = new FileOutputStream(file);
            int size;
            int len = 0;
            int index=0;
            byte[] buf = new byte[1024];
            while ((size = bin.read(buf)) != -1) {
                len += size;
                out.write(buf, 0, size);
                index++;
                if(index%10==0){
                    if(callBack!=null){
                        callBack.inProgress(len * 100 / fileLength);
                    }
                }
            }
            close(bin,out);
        } catch (Exception e) {
            if(callBack!=null){
                callBack.onfalure("errorMsg_"+e.getMessage());
            }
        }
        if(callBack!=null){
            if(file!=null){
                callBack.onSuccess(file.getAbsolutePath());
            }else {
                callBack.onfalure("errorMsg_file_null");
            }
        }
    }
    /**
     * 下载 使用服务器文件名字
     *
     * @param urlPath
     * @param downloadDir
     * @return
     */
    public  void downloadFile(String urlPath, String downloadDir,ICallBack callBack) {
        downloadFile(urlPath,downloadDir,null,callBack);
    }


    /**
     * 上传单个文件
     *
     * @param url
     * @param map
     * @param fileParam
     * @param filePath
     * @param callBack
     */
    public void uploadFile(String url, Map<String, Object> map, String fileParam, String filePath, ICallBack callBack) {

        if(url==null || "".equals(url)){
            throw new RuntimeException("上传路径不能为空!");
        }

        if(fileParam==null || "".equals(fileParam)){
            fileParam="file";
        }
        if(filePath==null || "".equals(filePath)){
            throw new RuntimeException("上传文件路径不能为空!");
        }
        if(!FileUtils.isExistFile(filePath)){
            throw new RuntimeException("上传文件不存在!");
        }

        uploadMulFile(url, map, new String[]{fileParam}, new String[]{filePath}, callBack);
    }

    /**
     * 上传单个文件
     * @param url
     * @param fileParam
     * @param filePath
     * @param callBack
     */
    public void uploadFile(String url, String fileParam, String filePath, ICallBack callBack) {
        uploadFile(url, null, fileParam, filePath, callBack);
    }

    /**
     * 上传单个文件
     * @param url
     * @param filePath
     * @param callBack
     */
    public void uploadFile(String url, String filePath, ICallBack callBack) {
        uploadFile(url, null, null, filePath, callBack);
    }

    /**
     * 多文件上传的方法
     *
     * @param url：上传的路径
     * @param uploadFilePaths：需要上传的文件路径
     * @return
     */
    //换行
    private static final String END = "\r\n";
    //必须存在
    private static final String TWO_HYPHENS = "--";
    //边界标识
    private static final String boundary = "*****";

    /**
     * 表单形式上传多个文件及携带参数提交
     *
     * @param url
     * @param map
     * @param fileParam
     * @param uploadFilePaths
     * @param callBack
     */
    public void uploadMulFile(String url, Map<String, Object> map, String[] fileParam, String[] uploadFilePaths, ICallBack callBack) {

        DataOutputStream ds = null;
        InputStream inputStream = null;
        //总长度
        int sum = 0;

        //多文件矫正
        String[] lastFiles=new String[]{};
        if(uploadFilePaths.length>1){
            ArrayList<String>list=new ArrayList<>();
            //上传总长度
            for (int i = 0; i < uploadFilePaths.length; i++) {
                if(FileUtils.isExistFile(uploadFilePaths[i])){
                    sum = +uploadFilePaths[i].length();
                    list.add(uploadFilePaths[i]);
                }
            }
            lastFiles = (String[]) list.toArray();
            if(lastFiles.length<=0){
                if(callBack!=null){
                    callBack.onfalure("errorMsg_files not exists ...");
                }
                return;
            }
        }else  if(uploadFilePaths.length==1){
            //单文件
            lastFiles=uploadFilePaths;
        }

        try {

            HttpURLConnection httpURLConnection = setPOSTHttpURLConnection(new URL(url));
            httpURLConnection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

            // 设置DataOutputStream
            ds = new DataOutputStream(httpURLConnection.getOutputStream());
            //其他参数上传
            writeParams(ds, map);
            //文件上传
            writeFiles(fileParam, lastFiles, ds,sum, callBack);

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
        } catch (Exception e) {
            if (callBack != null) {
                callBack.onfalure("errorMsg_" + e.getMessage());
            }
        } finally {
            close(ds, inputStream);
        }
    }

    /**
     * 写入上传文件流
     *
     * @param fileParam
     * @param uploadFilePaths
     * @param ds
     * @param callBack
     * @throws IOException
     */
    private void writeFiles(String[] fileParam, String[] uploadFilePaths, DataOutputStream ds,int sum, ICallBack callBack) throws IOException {

        //当前长度
        int curSize = 0;
        //刷新频率
        int index = 0;

        for (int i = 0; i < uploadFilePaths.length; i++) {
            String uploadFile = uploadFilePaths[i];
            String filename = uploadFile.substring(uploadFile.lastIndexOf("//") + 1);
            ds.writeBytes(TWO_HYPHENS + boundary + END);
            ds.writeBytes("Content-Disposition: form-data; " + "name=\"" + fileParam[i] + "\";filename=\"" + filename + "\"" + END);
            // Content-Disposition: form-data; name="file0";filename="xxx.png" \r\n
            ds.writeBytes(END);
            FileInputStream fStream = new FileInputStream(uploadFile);

            int bufferSize = 1024;
            byte[] buffer = new byte[bufferSize];
            int length;

            while ((length = fStream.read(buffer)) != -1) {
                curSize = +length;
                ds.write(buffer, 0, length);
                index++;
                //每个10KB 刷新
                if (index % 10 == 0) {
                    if (callBack != null) {
                        callBack.inProgress((float) (1.0 * curSize / sum * 100));
                    }
                }
            }
            ds.writeBytes(END);
            fStream.close();
        }
        ds.writeBytes(TWO_HYPHENS + boundary + TWO_HYPHENS + END);
        ds.flush();
    }


    /**
     * 其他参数上传
     *
     * @param os
     * @param params
     */
    private void writeParams(DataOutputStream os, Map<String, Object> params) throws Exception {

        //参数为空
        if (params == null) {
            return;
        }
        StringBuilder requestParams = new StringBuilder();
        Set<Map.Entry<String, Object>> set = params.entrySet();
        Iterator<Map.Entry<String, Object>> it = set.iterator();
        while (it.hasNext()) {
            Map.Entry<String, Object> entry = it.next();
            requestParams.append(TWO_HYPHENS).append(boundary).append(END);
            requestParams.append("Content-Disposition: form-data; name=\"").append(entry.getKey()).append("\"").append(END);
            requestParams.append("Content-Type: text/plain; charset=utf-8").append(END);
            requestParams.append("Content-Transfer-Encoding: 8bit").append(END);
            // 参数头设置完以后需要两个换行，然后才是参数内容
            requestParams.append(END).append(entry.getValue()).append(END);
        }
        os.write(requestParams.toString().getBytes());
        os.flush();
    }


    /**
     * 关闭
     *
     * @param closeables
     */
    private void close(Closeable... closeables) {
        if (closeables == null) {
            return;
        }
        for (int i = 0; i < closeables.length; i++) {
            if (closeables[i] != null) {
                try {
                    closeables[i].close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
