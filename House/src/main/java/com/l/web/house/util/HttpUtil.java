package com.l.web.house.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

public class HttpUtil {

    private static final Logger logger = Logger.getLogger(HttpUtil.class);
    private static final Integer TIMEOUT_IN_MILLIONS = 60000;
    private static String uuid = "224ea28c-8b4b-4d21-806b-6b3f8c90d6bf";

    public static void main(String[] args) throws Exception {
        uuid = "224ea28c-8b4b-4d21-806b-6b3f8c90d6bf";
//        String content = HttpUtil.sendGetRequest("https://bj.lianjia.com/ershoufang/pg1p2p1p4p3/");
        String content = HttpUtil.sendGet("https://bj.lianjia.com/ershoufang/pg1p2p1p4p3/");
         System.out.println(content);
        // System.out.println(doGet("http://bj.lianjia.com/ershoufang/pg1l1l2l3l4p1p2"));
    }

    private HttpUtil() {
    }

    /**
     * 向指定URL发送GET方法的请求
     * 
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     * @throws FileNotFoundException 
     */
    public static String sendGet(String url) throws FileNotFoundException {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.setRequestProperty("Cookie", "lianjia_uuid=" + uuid);
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
//            for (String key : map.keySet()) {
//                System.out.println(key + "--->" + map.get(key));
//            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (FileNotFoundException e) {
            System.out.println("发送GET请求出现异常！" + e);
            throw e;
        } catch (Exception e1) {
            System.out.println("发送GET请求出现异常！" + e1);
            e1.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    
    /**
     * 发送Get请求
     *
     * @param requestUrl
     * @return
     */
    @SuppressWarnings("finally")
    public static String sendGetRequest(String requestUrl) {
        String content = "";
        try {
            String url = URLDecoder.decode(requestUrl, "utf-8");
            CookieStore cookieStore = new BasicCookieStore();
            BasicClientCookie uuidCookie = new BasicClientCookie("lianjia_uuid",uuid);
            uuidCookie.setVersion(0);
            uuidCookie.setDomain("lianjia.com");
            uuidCookie.setPath("/");
            uuidCookie.setExpiryDate(DU.getDate("2018"));

            BasicClientCookie ssidCookie = new BasicClientCookie("lianjia_ssid","94ceb488-4dcb-4bdf-99be-6fbbdf5fe77c");
            ssidCookie.setVersion(0);
            ssidCookie.setDomain("lianjia.com");
            ssidCookie.setPath("/");
            ssidCookie.setExpiryDate(DU.getDate("2018"));
            
            BasicClientCookie csrfCookie = new BasicClientCookie("_csrf","c580880bb5afb10d61030fa351d95c1d7152517f7446a1945b2d707e594a7be0a%3A2%3A%7Bi%3A0%3Bs%3A5%3A%22_csrf%22%3Bi%3A1%3Bs%3A32%3A%22GwME-y2o0qQteT5HAIO9acqZ0aBeqEWS%22%3B%7D");
            csrfCookie.setVersion(0);
            csrfCookie.setDomain("lianjia.com");
            csrfCookie.setPath("/");
            csrfCookie.setExpiryDate(DU.getDate("2018"));

//[version: 0][name: _csrf][value: c580880bb5afb10d61030fa351d95c1d7152517f7446a1945b2d707e594a7be0a%3A2%3A%7Bi%3A0%3Bs%3A5%3A%22_csrf%22%3Bi%3A1%3Bs%3A32%3A%22GwME-y2o0qQteT5HAIO9acqZ0aBeqEWS%22%3B%7D][domain: captcha.lianjia.com][path: /][expiry: null]
//[version: 0][name: app_matrix_servers][value: 8767092da9592f5969c80321604670f6][domain: captcha.lianjia.com][path: /][expiry: null]
            
            System.out.println(uuid.toString());
//            HttpHost proxy = new HttpHost("58.222.254.11",3128);
            Request request = Request.Get(url).connectTimeout(TIMEOUT_IN_MILLIONS).socketTimeout(TIMEOUT_IN_MILLIONS);
            // cookieStore.addCookie();
            cookieStore.addCookie(uuidCookie);
            cookieStore.addCookie(ssidCookie);
            cookieStore.addCookie(csrfCookie);
            Executor executor = Executor.newInstance();
            executor = executor.use(cookieStore);
            Response response = executor.execute(request);
            Content returnContent = response.returnContent();
            content = returnContent.asString();
            List<Cookie> cookies = cookieStore.getCookies();
            for(Cookie ck:cookies){
                System.out.println(ck.toString());
            }
        } catch (Exception ex) {
            logger.error(requestUrl, ex);
        } finally {
            return content;
        }
    }

    /**
     * 发送Post请求
     *
     * @param requestUrl
     * @param params
     * @param charset
     * @return
     */
    @SuppressWarnings("finally")
    public static String sendPostRequest(String requestUrl, Map<String, String> params, Charset charset) {
        String content = "";
        try {
            Form form = Form.form();
            // 封装参数
            if (params != null) {
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    form.add(entry.getKey(), entry.getValue());
                }
            }
            HttpResponse response = Request.Post(requestUrl).connectTimeout(TIMEOUT_IN_MILLIONS).socketTimeout(TIMEOUT_IN_MILLIONS).bodyForm(form.build(), charset)
                    .execute().returnResponse();
            content = decode(response);
        } catch (Exception ex) {
            logger.error(null, ex);
        } finally {
            return content;
        }
    }

    /**
     * 发送Post请求（UTF-8）
     *
     * @param requestUrl
     * @param params
     * @return
     */
    public static String sendPostRequest(String requestUrl, Map<String, String> params) {
        return sendPostRequest(requestUrl, params, Consts.UTF_8);
    }

    /**
     * 上传文件和参数
     *
     * @param requestUrl
     * @param bin
     * @param params
     * @param charset
     * @return
     * @throws IOException
     */
    @SuppressWarnings("finally")
    public static String sendMultipartPost(String requestUrl, Map<String, File> bin, Map<String, String> params, Charset charset) throws IOException {
        String content = "";
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpPost httppost = new HttpPost(requestUrl);
            // 生成ContentType
            ContentType contentType = ContentType.create(ContentType.TEXT_PLAIN.getMimeType(), charset);
            // 以浏览器兼容模式运行，防止文件名乱码。
            MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create().setCharset(charset).setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            // 封装附件
            if (bin != null) {
                for (Map.Entry<String, File> entry : bin.entrySet()) {
                    String name = entry.getKey();
                    File file = entry.getValue();
                    multipartEntityBuilder.addBinaryBody(name, file);
                }
            }
            // 封装参数
            if (bin != null) {
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    String name = entry.getKey();
                    String value = entry.getValue();
                    multipartEntityBuilder.addTextBody(name, value, contentType);
                }
            }

            HttpEntity reqEntity = multipartEntityBuilder.build();

            httppost.setEntity(reqEntity);

            System.out.println("executing request " + httppost.getRequestLine());
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                System.out.println("----------------------------------------");
                System.out.println(response.getStatusLine());
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    contentType = ContentType.getOrDefault(resEntity);
                    Charset respCharset = contentType.getCharset();
                    // 使用EntityUtils的toString方法，传递编码，默认编码是ISO-8859-1
                    if (respCharset == null) {
                        respCharset = Consts.UTF_8;
                    }
                    content = EntityUtils.toString(resEntity, respCharset);
                    System.out.println("Response content length: " + resEntity.getContentLength());
                }
                EntityUtils.consume(resEntity);
            } catch (IOException ex) {
                logger.error(null, ex);
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
            return content;
        }
    }

    /**
     * 上传文件
     *
     * @param requestUrl
     * @param bin
     * @param params
     * @return
     * @throws IOException
     */
    public static String sendMultipartPost(String requestUrl, Map<String, File> bin, Map<String, String> params) throws IOException {
        return sendMultipartPost(requestUrl, bin, params, Consts.UTF_8);
    }

    /**
     * 上传文件
     *
     * @param requestUrl
     * @param bin
     * @return
     * @throws IOException
     */
    public static String sendMultipartPost(String requestUrl, Map<String, File> bin) throws IOException {
        return sendMultipartPost(requestUrl, bin, null, Consts.UTF_8);
    }

    /**
     * response 解码
     *
     * @param response
     * @return
     * @throws IOException
     */
    private static String decode(HttpResponse response) throws IOException {
        HttpEntity entity = response.getEntity();
        if (entity == null) {
            return "";
        }
        ContentType contentType = ContentType.getOrDefault(entity);
        Charset charset = contentType.getCharset();
        // 使用EntityUtils的toString方法，传递编码，默认编码是ISO-8859-1
        if (charset == null) {
            charset = Consts.UTF_8;
        }
        return EntityUtils.toString(entity, charset);
    }

    public interface CallBack {
        void onRequestComplete(String result);
    }

    /**
     * 异步的Get请求
     * 
     * @param urlStr
     * @param callBack
     */
    public static void doGetAsyn(final String urlStr, final CallBack callBack) {
        new Thread() {
            public void run() {
                try {
                    String result = doGet(urlStr);
                    if (callBack != null) {
                        callBack.onRequestComplete(result);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            };
        }.start();
    }

    /**
     * 异步的Post请求
     * 
     * @param urlStr
     * @param params
     * @param callBack
     * @throws Exception
     */
    public static void doPostAsyn(final String urlStr, final String params, final CallBack callBack) throws Exception {
        new Thread() {
            public void run() {
                try {
                    String result = doPost(urlStr, params);
                    if (callBack != null) {
                        callBack.onRequestComplete(result);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            };
        }.start();

    }

    /**
     * Get请求，获得返回数据
     * 
     * @param urlStr
     * @return
     * @throws Exception
     * @throws Exception
     */
    public static String doGet(String urlStr) throws Exception {
        return sendGet(urlStr);
//        return sendGetRequest(urlStr);
    }

    public static String doGetRequest(String urlStr) throws Exception {

        URL url = null;
        HttpURLConnection conn = null;
        InputStream is = null;
        ByteArrayOutputStream baos = null;
        try {
            url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(TIMEOUT_IN_MILLIONS);
            conn.setConnectTimeout(TIMEOUT_IN_MILLIONS);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");

            /*
             * select_city=110000 all-lj=138f1e66bf8c368d8c8b328e9ff033b4
             * lianjia_ssid=cb059b8c-5dc3-4950-bd93-286629102373
             * lianjia_uuid=7c3d327e-7e22-4b25-a24c-1215ec539095
             * lianjia_ssid=b0bb61ef-da4e-4c47-a79e-7fc82e30af91
             * lianjia_uuid=a813ac8b-eaee-40d4-b954-b336ff4a641e
             */
            if (uuid != null) {
                conn.setRequestProperty("Cookie", "lianjia_uuid=" + uuid);
                conn.setRequestProperty("Cookie", "lianjia_ssid=2ba5f40b-a09e-c3f9-f892-0c23db81055d");
            }
            if (conn.getResponseCode() == 200 || conn.getResponseCode() == 301) {
                is = conn.getInputStream();
                baos = new ByteArrayOutputStream();
                int len = -1;
                byte[] buf = new byte[128];

                while ((len = is.read(buf)) != -1) {
                    baos.write(buf, 0, len);
                }
                baos.flush();
                return baos.toString();
            } else if (conn.getResponseCode() == 404) {
                throw new RuntimeException("404");
            } else {
                System.out.println("返回码是：" + conn.getResponseCode());
                throw new RuntimeException(" responseCode is not 200 ... ");
            }

        } catch (Exception e) {
            System.out.println("访问网址发生异常：" + urlStr);
            throw e;
        } finally {
            try {
                if (is != null)
                    is.close();
            } catch (IOException e) {
            }
            try {
                if (baos != null)
                    baos.close();
            } catch (IOException e) {
            }
            conn.disconnect();
        }

    }

    /**
     * 向指定 URL 发送POST方法的请求
     * 
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     * @throws Exception
     */
    public static String doPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("charset", "utf-8");
            conn.setUseCaches(false);
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setReadTimeout(TIMEOUT_IN_MILLIONS);
            conn.setConnectTimeout(TIMEOUT_IN_MILLIONS);

            if (param != null && !param.trim().equals("")) {
                // 获取URLConnection对象对应的输出流
                out = new PrintWriter(conn.getOutputStream());
                // 发送请求参数
                out.print(param);
                // flush输出流的缓冲
                out.flush();
            }
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    public static String getUuid() {
        return uuid;
    }

    public static void setUuid(String uuid) {
        logger.info("正在设置HTTP————>UUID：" + uuid);
        HttpUtil.uuid = uuid;
    }
}
