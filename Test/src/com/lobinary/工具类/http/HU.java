package com.lobinary.工具类.http;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.Map;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

public class HU {

    private static final Logger logger = Logger.getLogger(HU.class);
    private static final Integer timeout = 60000;
    private static final Integer TIMEOUT_IN_MILLIONS = 60000;

    private HU() {
    }
	/**
	 * Get请求，获得返回数据
	 * 
	 * @param urlStr
	 * @return
	 * @throws Exception
	 */
	public static String doGet(String urlStr) {
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
//			conn.setRequestProperty("connection", "Keep-Alive");
//        	conn.setRequestProperty("Cookie", "NID=97=bSBCozyfHzYdHtZ9PdSRPugOtBdLWVbhbQyXHKBVVHwuq7DrTrAhsPhXCXDOZRRN1A3_lhhlzqugEwVe8QPpm4Mb3JjaEteNXETm_xoiYNcF61JqMfthHAFDF3HBLl8P; _ga=GA1.3.840429089.1476179852");  
			if (conn.getResponseCode() == 200) {
				is = conn.getInputStream();
				baos = new ByteArrayOutputStream();
				int len = -1;
				byte[] buf = new byte[128];

				while ((len = is.read(buf)) != -1) {
					baos.write(buf, 0, len);
				}
				baos.flush();
				return baos.toString();
			} else {
				is = conn.getInputStream();
				baos = new ByteArrayOutputStream();
				int len = -1;
				byte[] buf = new byte[128];

				while ((len = is.read(buf)) != -1) {
					baos.write(buf, 0, len);
				}
				baos.flush();
				return baos.toString();
//				throw new RuntimeException(" responseCode is not 200 ... ");
			}

		} catch (Exception e) {
			e.printStackTrace();
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

		return null;

	}
	/**
	 * Get请求，获得返回数据
	 * 
	 * @param urlStr
	 * @return
	 * @throws Exception
	 */
	public static String doGetNoExceptionTrace(String urlStr) {
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
//			conn.setRequestProperty("connection", "Keep-Alive");
//        	conn.setRequestProperty("Cookie", "NID=97=bSBCozyfHzYdHtZ9PdSRPugOtBdLWVbhbQyXHKBVVHwuq7DrTrAhsPhXCXDOZRRN1A3_lhhlzqugEwVe8QPpm4Mb3JjaEteNXETm_xoiYNcF61JqMfthHAFDF3HBLl8P; _ga=GA1.3.840429089.1476179852");  
			if (conn.getResponseCode() == 200) {
				is = conn.getInputStream();
				baos = new ByteArrayOutputStream();
				int len = -1;
				byte[] buf = new byte[128];

				while ((len = is.read(buf)) != -1) {
					baos.write(buf, 0, len);
				}
				baos.flush();
				return baos.toString();
			} else {
				is = conn.getInputStream();
				baos = new ByteArrayOutputStream();
				int len = -1;
				byte[] buf = new byte[128];

				while ((len = is.read(buf)) != -1) {
					baos.write(buf, 0, len);
				}
				baos.flush();
				return baos.toString();
//				throw new RuntimeException(" responseCode is not 200 ... ");
			}

		} catch (Exception e) {
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

		return null;

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
            HttpResponse response = Request.Get(url).connectTimeout(timeout).socketTimeout(timeout).execute().returnResponse();
            content = decode(response);
        } catch (Exception ex) {
            logger.error(requestUrl, ex);
        } finally {
            return content;
        }
    }
	
	 public static String sendGet(String url, String charset) throws Exception {  
	        String result = "";  
	        BufferedReader in = null;  
	        try {  
	            URL realUrl = new URL(url);  
	            // 打开和URL之间的连接  
	            URLConnection connection = realUrl.openConnection();  
	            // 设置通用的请求属性  
	            connection.setRequestProperty("accept", "*/*");  
	            connection.setRequestProperty("connection", "Keep-Alive");  
	            connection.setRequestProperty("user-agent",  
	                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");  
	            // 建立实际的连接  
	            connection.connect();  
	            // 定义 BufferedReader输入流来读取URL的响应  
	            in = new BufferedReader(new InputStreamReader(  
	                    connection.getInputStream(), charset));  
	            String line;  
	            while ((line = in.readLine()) != null) {  
	                result += line;  
	            }  
	        } catch (Exception e) {  
	            System.out.println("发送GET请求出现异常！" + e);  
	            throw e;
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
            //封装参数
            if (params != null) {
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    form.add(entry.getKey(), entry.getValue());
                }
            }
            HttpResponse response = Request.Post(requestUrl).connectTimeout(timeout).socketTimeout(timeout).bodyForm(form.build(), charset).execute().returnResponse();
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
            //生成ContentType
            ContentType contentType = ContentType.create(ContentType.TEXT_PLAIN.getMimeType(), charset);
            //以浏览器兼容模式运行，防止文件名乱码。
            MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create().setCharset(charset).setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            //封装附件
            if (bin != null) {
                for (Map.Entry<String, File> entry : bin.entrySet()) {
                    String name = entry.getKey();
                    File file = entry.getValue();
                    multipartEntityBuilder.addBinaryBody(name, file);
                }
            }
            //封装参数
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
    
    public static void main(String[] args) {
        String content = HU.sendGetRequest("http://192.168.10.200:8080/abc/sms/sendSms.hd?phone=18608623600&msg=hello");
        System.out.println(content);
    }
}
