package com.lobinary.工具类.http;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLDecoder;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.aspectj.apache.bcel.classfile.annotation.NameValuePair;
import org.jboss.netty.channel.ConnectTimeoutException;
import org.jboss.netty.handler.codec.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;

import com.lobinary.工具类.StringUtil;

/**
 * 
 * <pre>
 * http工具类
 * </pre>
 */
public class HttpUtil {

	/**
	 * 连接超时时间-单位毫秒
	 */
	public static final int CONNECTION_TIME_OUT = 20000;

	public static final int READ_TIME_OUT = 20000;

	private static Logger logger = LoggerFactory.getLogger(HttpUtil.class);

	private HttpUtil() {
	}

	/**
	 * 发送HTTP_GET请求
	 * 
	 * @see 该方法会自动关闭连接,释放资源
	 * @param requestURL
	 *            请求地址(含参数)
	 * @param decodeCharset
	 *            解码字符集,解析响应数据时用之,其为null时默认采用UTF-8解码
	 * @return 远程主机响应正文
	 */
	public static String sendGetRequest(String reqURL, String decodeCharset) {
		long responseLength = 0; // 响应长度
		String responseContent = null; // 响应内容
		HttpClient httpClient = new DefaultHttpClient(); // 创建默认的httpClient实例
		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, HttpUtil.CONNECTION_TIME_OUT);
		httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, HttpUtil.READ_TIME_OUT);
		HttpGet httpGet = new HttpGet(reqURL); // 创建org.apache.http.client.methods.HttpGet
		try {
			HttpResponse response = httpClient.execute(httpGet); // 执行GET请求
			HttpEntity entity = response.getEntity(); // 获取响应实体
			if (null != entity) {
				responseLength = entity.getContentLength();
				responseContent = EntityUtils.toString(entity, decodeCharset == null ? "UTF-8" : decodeCharset);
				EntityUtils.consume(entity); // Consume response content
			}
			System.out.println("请求地址: " + httpGet.getURI());
			System.out.println("响应状态: " + response.getStatusLine());
			System.out.println("响应长度: " + responseLength);
			System.out.println("响应内容: " + responseContent);
		} catch (ClientProtocolException e) {
			logger.error("该异常通常是协议错误导致,比如构造HttpGet对象时传入的协议不对(将'http'写成'htp')或者服务器端返回的内容不符合HTTP协议要求等,堆栈信息如下", e);
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error("该异常通常是网络原因引起的,如HTTP服务器未启动等,堆栈信息如下", e);
		} finally {
			httpClient.getConnectionManager().shutdown(); // 关闭连接,释放资源
		}
		return responseContent;
	}

	/**
	 * 发送HTTP_POST请求
	 * 
	 * @see 该方法为<code>sendPostRequest(String,String,boolean,String,String)</code>
	 *      的简化方法
	 * @see 该方法在对请求数据的编码和响应数据的解码时,所采用的字符集均为UTF-8
	 * @see 当<code>isEncoder=true</code>时,其会自动对<code>sendData</code>中的[中文][|][
	 *      ]等特殊字符进行<code>URLEncoder.encode(string,"UTF-8")</code>
	 * @param isEncoder
	 *            用于指明请求数据是否需要UTF-8编码,true为需要
	 * @throws Exception
	 */
	public static String sendPostRequest(String reqURL, String sendData, boolean isEncoder) throws Exception {
		return sendPostRequest(reqURL, sendData, isEncoder, null, null);
	}

	/**
	 * 发送HTTP_POST请求
	 * 
	 * @see 该方法会自动关闭连接,释放资源
	 * @see 当<code>isEncoder=true</code>时,其会自动对<code>sendData</code>中的[中文][|][
	 *      ]等特殊字符进行<code>URLEncoder.encode(string,encodeCharset)</code>
	 * @param reqURL
	 *            请求地址
	 * @param sendData
	 *            请求参数,若有多个参数则应拼接成param11=value11¶m22=value22¶m33=value33的形式后,
	 *            传入该参数中
	 * @param isEncoder
	 *            请求数据是否需要encodeCharset编码,true为需要
	 * @param encodeCharset
	 *            编码字符集,编码请求数据时用之,其为null时默认采用UTF-8解码
	 * @param decodeCharset
	 *            解码字符集,解析响应数据时用之,其为null时默认采用UTF-8解码
	 * @return 远程主机响应正文
	 * @throws Exception
	 */
	public static String sendPostRequest(String reqURL, String sendData, boolean isEncoder, String encodeCharset, String decodeCharset) throws Exception {
		String responseContent = null;
		HttpClient httpClient = new DefaultHttpClient();
		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, HttpUtil.CONNECTION_TIME_OUT);
		httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, HttpUtil.READ_TIME_OUT);
		HttpPost httpPost = new HttpPost(reqURL);
		// httpPost.setHeader(HTTP.CONTENT_TYPE,
		// "application/x-www-form-urlencoded; charset=UTF-8");
		httpPost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded");
		try {
			if (isEncoder) {
				List<NameValuePair> formParams = new ArrayList<NameValuePair>();
				for (String str : sendData.split("&")) {
					formParams.add(new BasicNameValuePair(str.substring(0, str.indexOf("=")), str.substring(str.indexOf("=") + 1)));
				}
				httpPost.setEntity(new StringEntity(URLEncodedUtils.format(formParams, encodeCharset == null ? "UTF-8" : encodeCharset)));
			} else {
				httpPost.setEntity(new StringEntity(sendData));
			}

			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			if (null != entity) {
				responseContent = EntityUtils.toString(entity, decodeCharset == null ? "UTF-8" : decodeCharset);
				EntityUtils.consume(entity);
			}
		} catch (Exception e) {
			logger.error("与[" + reqURL + "]通信过程中发生异常,堆栈信息如下", e);
			throw e;
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
		return responseContent;
	}

	/**
	 * 发送HTTP_POST请求
	 * 
	 * @see 该方法会自动关闭连接,释放资源
	 * @param reqURL
	 *            请求地址
	 * @param sendData
	 *            请求参数
	 * @param charset
	 *            编码字符集,编码请求数据时用之,其为null时默认采用UTF-8解码
	 * @return 远程主机响应正文
	 * @throws Exception
	 */
	public static String sendPostRequest(String reqURL, String sendData, String charset) throws Exception {
		String responseContent = null;
		HttpClient httpClient = new DefaultHttpClient();
		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, HttpUtil.CONNECTION_TIME_OUT);
		httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, HttpUtil.READ_TIME_OUT);
		HttpPost httpPost = new HttpPost(reqURL);
		httpPost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded");
		try {
			charset = (null == charset) ? "UTF-8" : charset;
			httpPost.setEntity(new StringEntity(sendData, charset));

			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			if (null != entity) {
				responseContent = EntityUtils.toString(entity, charset);
				EntityUtils.consume(entity);
			}
		} catch (Exception e) {
			logger.error("与[" + reqURL + "]通信过程中发生异常,堆栈信息如下", e);
			throw e;
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
		return responseContent;
	}

	/**
	 * 发送HTTP_POST请求
	 * 
	 * @see 该方法会自动关闭连接,释放资源
	 * @see 该方法会自动对<code>params</code>中的[中文][|][ ]等特殊字符进行
	 *      <code>URLEncoder.encode(string,encodeCharset)</code>
	 * @param reqURL
	 *            请求地址
	 * @param params
	 *            请求参数
	 * @param encodeCharset
	 *            编码字符集,编码请求数据时用之,其为null时默认采用UTF-8解码
	 * @param decodeCharset
	 *            解码字符集,解析响应数据时用之,其为null时默认采用UTF-8解码
	 * @return 远程主机响应正文
	 * @throws Exception 
	 */
	public static String sendPostRequest(String reqURL, Map<String, String> params, String encodeCharset, String decodeCharset) throws Exception {
		String responseContent = null;
		HttpClient httpClient = new DefaultHttpClient();
		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, HttpUtil.CONNECTION_TIME_OUT);
		httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, HttpUtil.READ_TIME_OUT);
		HttpPost httpPost = new HttpPost(reqURL);
		List<NameValuePair> formParams = new ArrayList<NameValuePair>(); // 创建参数队列
		if(params!=null){
			for (Map.Entry<String, String> entry : params.entrySet()) {
				formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
		}
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(formParams, encodeCharset == null ? "UTF-8" : encodeCharset));
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			if (null != entity) {
				responseContent = EntityUtils.toString(entity, decodeCharset == null ? "UTF-8" : decodeCharset);
				EntityUtils.consume(entity);
			}
		} catch (SocketTimeoutException e) {
			throw e;
		} catch (Exception e) {
			logger.error("http通讯异常，异常原因为：\n", e);
			throw e;
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
		return responseContent;
	}

	/**
	 * 发送HTTP_POST请求，可以设置连接超时时间和响应超时时间
	 * 
	 * @see 该方法会自动关闭连接,释放资源
	 * @see 该方法会自动对<code>params</code>中的[中文][|][ ]等特殊字符进行
	 *      <code>URLEncoder.encode(string,encodeCharset)</code> * @param reqURL
	 *      请求地址
	 * @param params
	 *            请求参数
	 * @param paramEncoding
	 *            参数字符集
	 * @param connTimeout
	 *            连接超时时间(毫秒)
	 * @param respTimeout
	 *            响应超时时间(毫秒)
	 * @return
	 */
	public static String sendPostRequest(String reqURL, Map<String, String> params, String paramEncoding, 
			int connTimeout, int respTimeout) throws Exception {
		String responseContent = null;
		
		HttpClient httpClient = new DefaultHttpClient();
		// 设置连接超时,毫秒
		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, connTimeout);
		// 设置获取响应超时,毫秒
		httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, respTimeout);
		
		HttpPost httpPost = new HttpPost(reqURL);
		List<NameValuePair> formParams = new ArrayList<NameValuePair>(); // 创建参数队列
		for (Map.Entry<String, String> entry : params.entrySet()) {
			formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(formParams, paramEncoding == null ? "UTF-8" : paramEncoding));
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			if (null != entity) {
				responseContent = EntityUtils.toString(entity, paramEncoding == null ? "UTF-8" : paramEncoding);
				EntityUtils.consume(entity);
			} else {
				throw new Exception("http请求异常,entity为空");
			}
		} catch (ConnectionClosedException e) {
			logger.error("http通讯异常ConnectionClosedException", e);
			throw e;
		} catch (HttpHostConnectException e) {
			logger.error("http通讯异常HttpHostConnectException", e);
			throw e;
		} catch (ConnectTimeoutException e) {
			logger.error("http通讯异常ConnectTimeoutException", e);
			throw e;
		} catch (SocketTimeoutException e) {
			logger.error("http通讯异常SocketTimeoutException", e);
			throw e;
		} catch (Exception e) {
			logger.error("http通讯异常Exception", e);
			throw e;
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
		
		return responseContent;
	}

	/**
	 * 发送HTTPS_POST请求
	 * 
	 * @throws Exception
	 * 
	 * @see 该方法为<code>sendPostSSLRequest(String,Map<String,String>,String,String)</code>
	 *      方法的简化方法
	 * @see 该方法在对请求数据的编码和响应数据的解码时,所采用的字符集均为UTF-8
	 * @see 该方法会自动对<code>params</code>中的[中文][|][ ]等特殊字符进行
	 *      <code>URLEncoder.encode(string,"UTF-8")</code>
	 */
	public static String sendPostSSLRequest(String reqURL, Map<String, String> params) throws Exception {
		return sendPostSSLRequest(reqURL, params, null, null);
	}

	/**
	 * 发送HTTPS_POST请求，会自动为返回结果进行decode，对[中文][|][ ]等特殊字符还原
	 * 
	 * @see 该方法为<code>sendPostSSLRequest(String,Map<String,String>,String,String)</code>
	 *      方法的简化方法
	 * @see 该方法在对请求数据的编码和响应数据的解码时,所采用的字符集均为UTF-8
	 * @see 该方法会自动对<code>params</code>中的[中文][|][ ]等特殊字符进行
	 *      <code>URLEncoder.encode(string,"UTF-8")</code>
	 */
	public static String sendPostSSLRequestDecode(String reqURL, Map<String, String> params) throws Exception {
		return URLDecoder.decode(sendPostSSLRequest(reqURL, params, "UTF-8", "UTF-8"), "UTF-8");
	}

	/**
	 * 发送HTTPS_POST请求
	 * 
	 * @see 该方法会自动关闭连接,释放资源
	 * @see 该方法会自动对<code>params</code>中的[中文][|][ ]等特殊字符进行
	 *      <code>URLEncoder.encode(string,encodeCharset)</code>
	 * @param reqURL
	 *            请求地址
	 * @param params
	 *            请求参数
	 * @param encodeCharset
	 *            编码字符集,编码请求数据时用之,其为null时默认采用UTF-8解码
	 * @param decodeCharset
	 *            解码字符集,解析响应数据时用之,其为null时默认采用UTF-8解码
	 * @return 远程主机响应正文
	 * @throws Exception
	 */
	public static String sendPostSSLRequest(String reqURL, Map<String, String> params, String encodeCharset, String decodeCharset) throws Exception {
		String responseContent = "";
		HttpClient httpClient = new DefaultHttpClient();
		try {
			httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, HttpUtil.CONNECTION_TIME_OUT);
			httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, HttpUtil.READ_TIME_OUT);
			X509TrustManager xtm = new X509TrustManager() {
				public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				}

				public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				}

				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}
			};

			SSLContext ctx = SSLContext.getInstance("TLS");
			ctx.init(null, new TrustManager[] { xtm }, null);
			SSLSocketFactory socketFactory = new SSLSocketFactory(ctx);
			httpClient.getConnectionManager().getSchemeRegistry().register(new Scheme("https", 443, socketFactory));

			HttpPost httpPost = new HttpPost(reqURL);
			List<NameValuePair> formParams = new ArrayList<NameValuePair>();
			for (Map.Entry<String, String> entry : params.entrySet()) {
				formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
			httpPost.setEntity(new UrlEncodedFormEntity(formParams, encodeCharset == null ? "UTF-8" : encodeCharset));

			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			if (null != entity) {
				responseContent = EntityUtils.toString(entity, decodeCharset == null ? "UTF-8" : decodeCharset);
				EntityUtils.consume(entity);
			}
		} catch (Exception e) {
			logger.error("与[" + reqURL + "]通信过程中发生异常,堆栈信息为", e);
			throw e;
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
		return responseContent;
	}

	/**
	 * 发送HTTP_POST请求
	 * 
	 * @see 若发送的<code>params</code>中含有中文,记得按照双方约定的字符集将中文
	 *      <code>URLEncoder.encode(string,encodeCharset)</code>
	 * @see 本方法默认的连接超时时间为30秒,默认的读取超时时间为30秒
	 * @param reqURL
	 *            请求地址
	 * @param params
	 *            发送到远程主机的正文数据,其数据类型为<code>java.util.Map<String, String></code>
	 * @return 远程主机响应正文`HTTP状态码,如<code>"SUCCESS`200"</code><br>
	 *         若通信过程中发生异常则返回"Failed`HTTP状态码",如<code>"Failed`500"</code>
	 */
	public static String sendPostRequestByJava(String reqURL, Map<String, String> params) {
		StringBuilder sendData = new StringBuilder();
		for (Map.Entry<String, String> entry : params.entrySet()) {
			sendData.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
		}
		if (sendData.length() > 0) {
			sendData.setLength(sendData.length() - 1); // 删除最后一个&符号
		}
		return sendPostRequestByJava(reqURL, sendData.toString());
	}

	/**
	 * 发送HTTP_POST请求
	 * 
	 * @see 若发送的<code>sendData</code>中含有中文,记得按照双方约定的字符集将中文
	 *      <code>URLEncoder.encode(string,encodeCharset)</code>
	 * @see 本方法默认的连接超时时间为30秒,默认的读取超时时间为30秒
	 * @param reqURL
	 *            请求地址
	 * @param sendData
	 *            发送到远程主机的正文数据
	 * @return 远程主机响应正文`HTTP状态码,如<code>"SUCCESS`200"</code><br>
	 *         若通信过程中发生异常则返回"Failed`HTTP状态码",如<code>"Failed`500"</code>
	 */
	public static String sendPostRequestByJava(String reqURL, String sendData) {
		HttpURLConnection httpURLConnection = null;
		OutputStream out = null; // 写
		InputStream in = null; // 读
		int httpStatusCode = 0; // 远程主机响应的HTTP状态码
		try {
			URL sendUrl = new URL(reqURL);
			httpURLConnection = (HttpURLConnection) sendUrl.openConnection();
			httpURLConnection.setRequestMethod("POST");
			httpURLConnection.setDoOutput(true); // 指示应用程序要将数据写入URL连接,其值默认为false
			httpURLConnection.setUseCaches(false);
			httpURLConnection.setConnectTimeout(HttpUtil.CONNECTION_TIME_OUT); // 30秒连接超时
			httpURLConnection.setReadTimeout(HttpUtil.READ_TIME_OUT); // 30秒读取超时

			out = httpURLConnection.getOutputStream();
			out.write(sendData.toString().getBytes());

			// 清空缓冲区,发送数据
			out.flush();

			// 获取HTTP状态码
			httpStatusCode = httpURLConnection.getResponseCode();

			// 该方法只能获取到[HTTP/1.0 200 OK]中的[OK]
			// 若对方响应的正文放在了返回报文的最后一行,则该方法获取不到正文,而只能获取到[OK],稍显遗憾
			// respData = httpURLConnection.getResponseMessage();

			// //处理返回结果
			// BufferedReader br = new BufferedReader(new
			// InputStreamReader(httpURLConnection.getInputStream()));
			// String row = null;
			// String respData = "";
			// if((row=br.readLine()) != null){
			// //readLine()方法在读到换行[\n]或回车[\r]时,即认为该行已终止
			// respData = row; //HTTP协议POST方式的最后一行数据为正文数据
			// }
			// br.close();

			in = httpURLConnection.getInputStream();
			byte[] byteDatas = new byte[in.available()];
			in.read(byteDatas);
			return new String(byteDatas) + "`" + httpStatusCode;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return "Failed`" + httpStatusCode;
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (Exception e) {
					logger.error("关闭输出流时发生异常,堆栈信息如下", e);
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
					logger.error("关闭输入流时发生异常,堆栈信息如下", e);
				}
			}
			if (httpURLConnection != null) {
				httpURLConnection.disconnect();
				httpURLConnection = null;
			}
		}
	}

	/**
	 * 
	 * 通过response返回对应的returnInfo
	 * 
	 * @param response
	 * @param returnInfo
	 */
	public static void returnStr(HttpServletResponse response, String returnInfo) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/plain");
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		out.println(returnInfo);
		out.flush();
		out.close();
	}

	/**
	 * 
	 * 通过response返回对应的returnInfo
	 * 
	 * @param response
	 * @param returnInfo
	 */
	public static void returnStr(HttpServletResponse response, String returnInfo, String encoding) {
		response.setCharacterEncoding(encoding);
		response.setContentType("text/plain");
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		out.println(returnInfo);
		out.flush();
		out.close();
	}

	/**
	 * 发送HTTP_POST_SSL请求
	 * 
	 * @see 1)该方法会自动关闭连接,释放资源
	 * @see 2)该方法亦可处理普通的HTTP_POST请求
	 * @see 3)方法内设置了连接和读取超时时间,单位为毫秒,超时后方法会自动返回空字符串
	 * @see 4)请求参数含中文等特殊字符时,可在传入前自行
	 *      <code>URLEncoder.encode(string,encodeCharset)</code>
	 *      ,再指定encodeCharset为null
	 * @see 5)亦可指定encodeCharset参数值,由本方法代为编码
	 * @see 6)该方法在解码响应报文时所采用的编码,为响应消息头中[Content-Type:text/html;
	 *      charset=GBK]的charset值
	 * @see 若响应消息头中未指定Content-Type属性,则默认使用HttpClient内部默认的ISO-8859-1
	 * @see 
	 *      7)若reqURL的HTTPS端口不是默认的443,那么只需要在reqURL中指明其实际的HTTPS端口即可,而无需修改本方法内部指定的443
	 * @see 此时,方法默认会取指明的HTTPS端口进行连接..如reqURL=https://123.125.97.66:8085/pay/
	 *      则实际请求即对方HTTPS_8085端口
	 * @param reqURL
	 *            请求地址
	 * @param params
	 *            请求参数
	 * @param encodeCharset
	 *            编码字符集,编码请求数据时用之,其为null表示请求参数已编码完毕,不需要二次编码
	 * @return 远程主机响应正文
	 */
	public static String sendPostSSLRequest(String reqURL, Map<String, String> params, String encodeCharset) {
		String responseContent = "";
		HttpClient httpClient = new DefaultHttpClient();
		// 设置代理服务器
		// httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,
		// new HttpHost("10.0.0.4", 8080));
		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, HttpUtil.CONNECTION_TIME_OUT);
		httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, HttpUtil.READ_TIME_OUT);
		// 创建TrustManager
		// 用于解决javax.net.ssl.SSLPeerUnverifiedException: peer not authenticated
		X509TrustManager trustManager = new X509TrustManager() {
			@Override
			public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
			}

			@Override
			public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
			}

			@Override
			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}
		};
		// 创建HostnameVerifier
		// 用于解决javax.net.ssl.SSLException: hostname in certificate didn't match:
		// <123.125.97.66> != <123.125.97.241>
		X509HostnameVerifier hostnameVerifier = new X509HostnameVerifier() {
			@Override
			public void verify(String host, SSLSocket ssl) throws IOException {
			}

			@Override
			public void verify(String host, X509Certificate cert) throws SSLException {
			}

			@Override
			public void verify(String host, String[] cns, String[] subjectAlts) throws SSLException {
			}

			@Override
			public boolean verify(String arg0, SSLSession arg1) {
				return true;
			}
		};
		try {
			// TLS1.0与SSL3.0基本上没有太大的差别,可粗略理解为TLS是SSL的继承者，但它们使用的是相同的SSLContext
			SSLContext sslContext = SSLContext.getInstance(SSLSocketFactory.TLS);
			// 使用TrustManager来初始化该上下文,TrustManager只是被SSL的Socket所使用
			sslContext.init(null, new TrustManager[] { trustManager }, null);
			// 创建SSLSocketFactory
			SSLSocketFactory socketFactory = new SSLSocketFactory(sslContext, hostnameVerifier);
			// 通过SchemeRegistry将SSLSocketFactory注册到HttpClient上
			httpClient.getConnectionManager().getSchemeRegistry().register(new Scheme("https", 443, socketFactory));
			// 创建HttpPost
			HttpPost httpPost = new HttpPost(reqURL);
			// httpPost.setHeader(HTTP.CONTENT_TYPE,
			// "application/x-www-form-urlencoded; charset=UTF-8");
			// 构建POST请求的表单参数
			if (null != params) {
				List<NameValuePair> formParams = new ArrayList<NameValuePair>();
				for (Map.Entry<String, String> entry : params.entrySet()) {
					formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
				}
				httpPost.setEntity(new UrlEncodedFormEntity(formParams, encodeCharset));
			}
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			if (null != entity) {
				// //打印HTTP响应报文的第一行,即状态行
				// System.out.println(response.getStatusLine());
				// //打印HTTP响应头信息
				// for(Header header : response.getAllHeaders()){
				// System.out.println(header.toString());
				// }
				responseContent = EntityUtils.toString(entity, ContentType.getOrDefault(entity).getCharset());
				EntityUtils.consume(entity);
			}
		} catch (ConnectTimeoutException cte) {
			// Should catch ConnectTimeoutException, and don`t catch
			// org.apache.http.conn.HttpHostConnectException
			logger.error("与[" + reqURL + "]连接超时,自动返回空字符串");
		} catch (SocketTimeoutException ste) {
			logger.error("与[" + reqURL + "]读取超时,自动返回空字符串");
		} catch (Exception e) {
			logger.error("与[" + reqURL + "]通信异常,堆栈信息为", e);
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
		return responseContent;
	}

	/**
	 * 
	 * <pre>
	 * 防钓鱼专用
	 * </pre>
	 * 
	 * @param reqReferer
	 * @param respReferer
	 * @return
	 */
	public static boolean getAntiPhishingStatus(String reqReferer, String respReferer) {
		// 只判断REFERER 不判断IP
		boolean antiPhishingStatus = false;
		// String reqIP = "";
		// String respIP = respDto.getCLIENTIP();
		if (respReferer.contains("|")) {
			// 银行返回的referer不唯一
			for (String ourReferer : respReferer.split("\\|")) {
				if (antiPhishing(reqReferer, ourReferer)) {
					antiPhishingStatus = true;
					break;
				}
			}
		} else {
			// 银行返回的referer唯一
			antiPhishingStatus = antiPhishing(reqReferer, respReferer);
		}
		return antiPhishingStatus;
	}

	/**
	 * 暂时不需要，现在只在portal校验referer，不校验IP，采用下边的函数
	 * 
	 * @param reqIP
	 * @param reqReferer
	 * @param respIP
	 * @param ourReferer
	 * @return
	 */
	public static boolean antiPhishing(String reqIP, String reqReferer, String respIP, String respReferer) {
		if ((StringUtil.isBlank(reqIP, false) && StringUtil.isBlank(reqReferer, false)) || (StringUtil.isBlank(respIP, false) && StringUtil.isBlank(respReferer, false))) {
			logger.info("防钓鱼验证-->未通过:请求参数不可空");
			return false;
		}
		// 先校验Referer
		if (respReferer.startsWith("http%253A") || respReferer.startsWith("https%253A")) {
			respReferer = decode(decode(respReferer, "GBK"), "GBK");
		} else if (respReferer.startsWith("http%3A") || respReferer.startsWith("https%3A")) {
			respReferer = decode(respReferer, "GBK");
		}
		if (respReferer.contains("?")) {
			respReferer = respReferer.substring(0, respReferer.lastIndexOf("?"));
		}
		if (respReferer.startsWith("http://")) {
			respReferer = respReferer.replaceAll("http://", "");
		} else if (respReferer.startsWith("https://")) {
			respReferer = respReferer.replaceAll("https://", "");
		}
		if (respReferer.contains(":")) {
			respReferer = respReferer.substring(0, respReferer.indexOf(":"));
		} else if (respReferer.contains("/")) {
			respReferer = respReferer.substring(0, respReferer.indexOf("/"));
		}
		if (reqReferer.contains(respReferer)) {
			logger.info("防钓鱼验证-->通过:Referer验证通过");
			return true;
		}
		// 再校验IP
		if (reqIP.contains(".")) {
			reqIP = reqIP.substring(0, reqIP.lastIndexOf("."));
		}
		if (respIP.contains(".")) {
			respIP = respIP.substring(0, respIP.lastIndexOf("."));
		}
		if (reqIP.equals(respIP)) {
			logger.info("防钓鱼验证-->通过:IP验证通过");
			return true;
		}
		return false;
	}

	/**
	 * @param reqReferer
	 * @param ourReferer
	 * @return
	 */
	public static boolean antiPhishing(String reqReferer, String respReferer) {
		if (StringUtil.isBlank(reqReferer, false) || StringUtil.isBlank(respReferer, false)) {
			logger.info("防钓鱼验证-->未通过:请求参数不可空");
			return false;
		}
		// 先校验Referer
		if (respReferer.startsWith("http%253A") || respReferer.startsWith("https%253A")) {
			respReferer = decode(decode(respReferer, "GBK"), "GBK");
		} else if (respReferer.startsWith("http%3A") || respReferer.startsWith("https%3A")) {
			respReferer = decode(respReferer, "GBK");
		}
		if (respReferer.contains("?")) {
			respReferer = respReferer.substring(0, respReferer.lastIndexOf("?"));
		}
		if (respReferer.startsWith("http://")) {
			respReferer = respReferer.replaceAll("http://", "");
		} else if (respReferer.startsWith("https://")) {
			respReferer = respReferer.replaceAll("https://", "");
		}
		if (respReferer.contains(":")) {
			respReferer = respReferer.substring(0, respReferer.indexOf(":"));
		} else if (respReferer.contains("/")) {
			respReferer = respReferer.substring(0, respReferer.indexOf("/"));
		}
		if (reqReferer.contains(respReferer)) {
			logger.info("防钓鱼验证-->通过:Referer验证通过");
			return true;
		}
		return false;
	}

	public static String decode(String chinese, String charset) {
		chinese = (chinese == null ? "" : chinese);
		try {
			return URLDecoder.decode(chinese, charset);
		} catch (UnsupportedEncodingException e) {
			logger.error("解码字符串[" + chinese + "]时发生异常:系统不支持该字符集[" + charset + "]");
			return chinese;
		}
	}

	/**
	 * 
	 * <pre>
	 * 返回文件，文件名需要有格式  如：  a.txt
	 * </pre>
	 * 
	 * @author 吕斌
	 * @param returnFile
	 * @param fileType
	 *            如 txt jpg xml excel
	 * @param returnFileName
	 * @param response
	 */
	public static void returnFile(File returnFile, String fileType, String returnFileName, HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Content-Type", "application/force-download");
		if (fileType.equals("txt")) {
			response.setHeader("Content-Type", "application/text/plain");
		} else if (fileType.equals("xml")) {
			response.setHeader("Content-Type", "application/text/xml");
		} else if (fileType.equals("mp3")) {
			response.setHeader("Content-Type", "application/audio/mp3");
		} else if (fileType.equals("xml")) {
			response.setHeader("Content-Type", "application/text/xml");
		} else if (fileType.equals("csv")) {
			response.setHeader("Content-Type", "application/vnd.ms-excel");
		}
		try {
			// returnFileName = URLEncoder.encode(returnFileName, "UTF-8");
			response.setHeader("Content-disposition", "attachment;filename=\"" + returnFileName + "\"");
			// OutputStream ouputStream;
			// ouputStream = response.getOutputStream();
			// BufferedWriter bw;

			InputStream fis = new BufferedInputStream(new FileInputStream(returnFile));
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			fis.close();
			// 清空response
			response.reset();
			// 设置response的Header
			response.setHeader("Content-disposition", "attachment;filename=\"" + returnFileName + "\"");
			// response.addHeader("Content-Disposition", "attachment;filename="
			// + new String(returnFileName.getBytes("utf-8"),"ISO-8859-1"));
			response.addHeader("Content-Length", "" + returnFile.length());
			OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
			// response.setContentType("application/octet-stream");
			toClient.write(buffer);
			toClient.flush();
			toClient.close();

			// ouputStream.flush();
			// ouputStream.close();
		} catch (UnsupportedEncodingException e) {
			logger.error("在对文件名进行转码时,出现问题,请您输入正确的Encode字符串!");
		} catch (IOException e) {
			logger.error("准备将生成的Excel输出到response时出现问题,原因有可能是response已经关闭或其他问题!");
		}
	}

}
