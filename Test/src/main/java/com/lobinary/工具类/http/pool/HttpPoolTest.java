package com.lobinary.工具类.http.pool;

import main.java.com.lobinary.工具类.http.pool.IdleConnectionMonitorThread;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.conn.DnsResolver;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.conn.SystemDefaultDnsResolver;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import javax.net.ssl.SSLContext;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Set;

/**
 * 相关文档
 * http://hc.apache.org/httpcomponents-client-ga/tutorial/html/connmgmt.html#d5e659
 */
public class HttpPoolTest {

    private static final Logger logger = Logger.getLogger(HttpPoolTest.class);

    private static final String DEFAULT_CHARSET = "UTF-8";
    private static PoolingHttpClientConnectionManager cm = null;

    private static final int POOLS_SIZE = 5000;
    private static CloseableHttpClient httpClient;
    static Set<String> unAdd = new HashSet<>();
    /*
     [org.apache.http.headers]:122  http-outgoing-0 << HTTP/1.1 200 OK
 [org.apache.http.headers]:125  http-outgoing-0 << Server: nginx/1.8.1
 [org.apache.http.headers]:125  http-outgoing-0 << Date: Fri, 01 Jun 2018 09:19:51 GMT
 [org.apache.http.headers]:125  http-outgoing-0 << Content-Type: application/json; charset=utf-8
 [org.apache.http.headers]:125  http-outgoing-0 << Content-Length: 101
 [org.apache.http.headers]:125  http-outgoing-0 << Connection: keep-alive
 [org.apache.http.headers]:125  http-outgoing-0 << Request-ID: bc1cc8fe8c944754e098a08e9f554960
 [org.apache.http.headers]:125  http-outgoing-0 << Wechatpay-Nonce: bc1cc8fe8c944754e098a08e9f554960
 [org.apache.http.headers]:125  http-outgoing-0 << Wechatpay-Signature: pa+LVB/Rt7pBSEhms0CU3hzWtm5xTaAOAhxFLeQJM6yFoqnNhwz5pLPaunSgQ9Jfxeao2dQfH+8Ti6fjVOBWy+K1taJvwuSvwLspZ6qAlFZivbWHRrBeM4SaiMNU+M1ysc5pZ4pR0AmgIW8FL1gKLD6h+ZcTUU6lYorjiViG1g8c/DlgI0DottqGTkCyhTbySYD1tianCLhhR+aQ6stmIvV/kvRq+wH5QAHeGvI1qtGof7qNnvWQwIO7uUKex8fcBFOqK4kpLHC8CZMqa93nPJ8MJbqM1v/itZ+8gwdkM867Idb4Tpq3me3qyoaGPdHxST+KtoK/S8IXk5bqDuLlVg==
 [org.apache.http.headers]:125  http-outgoing-0 << Wechatpay-Timestamp: 1527844791
 [org.apache.http.headers]:125  http-outgoing-0 << Wechatpay-Serial: 16E08D66CE296252285DFC97EC40B50B1904A6D8
 [org.apache.http.headers]:125  http-outgoing-0 << Cache-Control: no-cache, must-revalidate
     */
    static {
        unAdd.add("Wechatpay-Serial");
        unAdd.add("Wechatpay-Timestamp");
        unAdd.add("Wechatpay-Signature");
        unAdd.add("Request-ID");
        unAdd.add("Wechatpay-Nonce");
        unAdd.add("Date");

        SSLContext sslcontext = SSLContexts.createSystemDefault();

        // Create a registry of custom connection socket factories for supported
        // protocol schemes.
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("https", new SSLConnectionSocketFactory(sslcontext))
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .build();

        //DNS模拟
        DnsResolver dnsResolver = new SystemDefaultDnsResolver() {

            @Override
            public InetAddress[] resolve(final String host) throws UnknownHostException {
                if (host.equalsIgnoreCase("api.mch.weixin.qq.com")) {
                    //183.3.224.148
                    return new InetAddress[]{InetAddress.getByName("101.227.162.159")};
                } else {
                    return super.resolve(host);
                }
            }

        };

        cm = new PoolingHttpClientConnectionManager(socketFactoryRegistry, dnsResolver);
        cm.setMaxTotal(POOLS_SIZE);
        cm.setDefaultMaxPerRoute(POOLS_SIZE);
        //经过实验证明，MaxTotal为线程池链接总数，MaxPerRouter为单个域名最大总数，
        // 当MaxTotal为2，MaxPerRouter为1时，发送两个相同域名请求（默认一定超时），第一个请求会获取到连接，并发送请求，但是第二个就会获取连接池连接失败（Timeout waiting for connection from pool）
        ConnectionKeepAliveStrategy connectionKeepAliveStrategy = new ConnectionKeepAliveStrategy() {
            @Override
            public long getKeepAliveDuration(HttpResponse httpResponse, HttpContext httpContext) {
                return 6 * 1000; // tomcat默认keepAliveTimeout为20s
            }
        };
        HttpRequestRetryHandler handler;
        httpClient = HttpClients.custom().setConnectionManager(cm).setKeepAliveStrategy(connectionKeepAliveStrategy).setRetryHandler(new HttpPoolRequestRetryHandler()).build();
//        httpClient = HttpClients.createDefault();
        new IdleConnectionMonitorThread(cm).start();
    }

    public static void main(String[] args) throws Exception {
        printCmStatus();
//        for (int l = 0; l < 10000; l++) {
//            send();
//        }
        for (int l = 5; l < 1000; l++) {
            int size = POOLS_SIZE *l;
            int per = 10;
            int perSize = size/per;
            for (int m = 0; m < per; m++) {
                System.out.println("###############################################释放数据大小：         "+size + ",         准备注入数量：" + (size /100));
                for (int i = 0; i < perSize; i++) {
                    sendByThread();
                    Thread.sleep(100);
                }
                Thread.sleep(1000);
            }
            Thread.sleep(1000);
        }
    }

    /**
     * 11:13:09,050
     * 11:14:10,311
     */
    private static void printCmStatus() {
        new Thread() {
            @Override
            public void run() {
                while (true) {
//                    logger.info(" success : " + i + ",avg cost : " + (cost / (i + 1)) + ",-------retry num:"+ HttpPoolRequestRetryHandler.retryNum+",maxCount:"+ HttpPoolRequestRetryHandler.maxExecutionCount+"--------j:" + j + ",K:" + k + "cm status ====> : " + cm.getTotalStats() + ",   total:"+(cm.getTotalStats().getLeased()+cm.getTotalStats().getAvailable()));
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {


                    }
                }
            }
        }.start();
    }

    private static void sendByThread() {
        new Thread() {
            @Override
            public void run() {
                send();
            }
        }.start();
    }

    private static int i = 0;
    private static long cost = 0;

    private static synchronized void add(long c) {
        i++;
        cost += c;
    }

    private static int j = 0;

    private static synchronized void addj() {
        j++;
    }

    private static int k = 0;

    private static synchronized void addk() {
        k++;
    }

    private static void send() {
        long s = 0;
        try {
            String requestUrl = "http://api3.mch.weixin.qq.com/qsv3/sandbox/transactions";
//            String requestUrl = "https://route.showapi.com/104-29";
            HttpPost httpRequest = new HttpPost(requestUrl);
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(60000).setConnectionRequestTimeout(2000).setSocketTimeout(20000).build();
            httpRequest.setConfig(requestConfig);
//            logger.info("准备发送请求");
            s = System.currentTimeMillis();
            httpRequest.addHeader("Authorization", generateAuthorizationInfo());
            //{"acquiring_bank_id":"1900008751","merchant_id":"11386271","acquiring_bank_appid":"wx2421b1c4370ec43b","channel_id":"24006513",description":"hello wx","notify_url":"https://barcode.nucc.com/notify/wechat","out_trade_no":"12345678901522330588708","trade_type":"NATIVE","payer":{"auth_code":"1234567890"},"amount":{"total":1}}
            String reqData = "{\n" +
                    "  \"acquiring_bank_id\": \"1900008751\",\n" +
                    "  \"acquiring_bank_appid\": \"wx2421b1c4370ec43b\",\n" +
                    "  \"channel_id\": \"24006513\",\n" +
                    "  \"description\": \"??-??-????????\",\n" +
                    "  \"merchant_id\":\"11386271\",\n" +
                    "  \"out_trade_no\": \"2100100" + System.currentTimeMillis() + "\",\n" +
                    "  \"trade_type\": \"MICROPAY\",\n" +
                    "  \"payer\":{\"auth_code\":\"134702778674040903\"},\n" +
                    "  \"amount\": {\n" +
                    "    \"total\": 3000\n" +
                    "  }\n" +
                    "}";
            ((HttpPost) httpRequest).setEntity(new StringEntity(reqData, Charset.forName(DEFAULT_CHARSET)));
            long ss = System.currentTimeMillis();
            CloseableHttpResponse response = httpClient.execute(httpRequest);
            Header[] hs = response.getAllHeaders();
            for(Header h :hs){
                if(unAdd.contains(h.getName()))continue;
                String hn = h.getName()+":"+h.getValue();
                hm.add(hn);
            }
            HttpEntity entity = response.getEntity();
            String respStr = EntityUtils.toString(entity, DEFAULT_CHARSET);
            int statusCode = response.getStatusLine().getStatusCode();
//            logger.info("statusCode:"+statusCode+",respStr:"+respStr + ",cost:"+(System.currentTimeMillis()-ss));
//            if(401!=statusCode){
//                logger.info("###############################response code:"+statusCode);
//            }



            logger.info("statusCode:"+statusCode+(statusCode==500?respStr:""));
            if (respStr.contains("SUCCESS") || respStr.contains("沙")) {
                add(System.currentTimeMillis() - s);
            } else {
                logger.error("##########################################################################################" + respStr);
            }




        }catch (ConnectionPoolTimeoutException e) {
//            logger.error("##################################################################################################################发送异常,间隔："+((System.currentTimeMillis()-s)/1000)+ "秒",e);
       } /*catch (NoHttpResponseException e) {
            addj();
//            logger.error("##################################################################################################################发送异常,间隔："+((System.currentTimeMillis()-s)/1000)+ "秒",e);
        } catch (SSLHandshakeException e) {
            addk();
            logger.error("##################################################################################################################发送异常,间隔：" + ((System.currentTimeMillis() - s) / 1000) + "秒", e);
        }*/ catch (Exception e) {
//            addk();
            for(String ss : hm){
                logger.error("HEADER:"+ss);
            }
//            logger.error("##################################################################################################################发送异常,间隔：" + ((System.currentTimeMillis() - s) / 1000) + "秒", e);
            logger.error(""+e);
        }
    }

    static Set<String> hm = new HashSet<>();


    /**
     * 生成授权信息
     *
     * @return 返回授权信息串
     */
    private static String generateAuthorizationInfo() throws Exception {
//        String sign = AlipaySignature.rsaSign(signatureOriStr,priKey,"UTF-8","RSA2");
        return "WECHATPAY2-SHA256-RSA2048 mch_id=\"" + "1900008751" +
                "\",nonce_str=\"" + "1234567890" +
                "\",signature=\"" + "1234567890" +
                "\",timestamp=\"" + getNowSecondsStamp() +
                "\",serial_no=\"" + "1234567890" + "\"";
    }


    /**
     * 获取微信专用的秒级时间戳
     */
    public static String getNowSecondsStamp() {
        return String.valueOf(System.currentTimeMillis() / 1000);
    }

}
