package com.lobinary.工具类;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.l.web.house.dto.房屋统计信息;
import com.l.web.house.model.房屋基本信息;
import com.l.web.house.service.catchsystem.impl.LinkedHouseImpl;
import com.l.web.house.util.CreateChartServiceImpl;
import com.lobinary.工具类.date.DateUtil;

/**
 * 定时器工具类
 * 
 * 负责定时启动以及相关的提示等功能
 * 
 * @author Lobinary
 * @see 本工具类使用了下方代码
 * @see com.lobinary.工具类.LU
 * @see com.lobinary.工具类.date.DateUtil
 *
 */
public class TU {

    private static final String FILE_LOCATION = "/apps/logs/house/house" + DateUtil.getDate(new Date()) + ".log";
    private static String appName = "House";
    private static int times = 0;

    /**
     *  https://bj.lianjia.com/ershoufang/pg1p2p1p4p3/
     *  https://bj.lianjia.com/ershoufang/101101999172.html
        https://bj.lianjia.com/tools/calccost?house_code=101101997938
     * 
     * 
     * @since add by bin.lv 2017年9月12日 上午11:09:22
     * @param args
     * @throws UnsupportedEncodingException
     * @throws MessagingException
     * @throws InterruptedException
     */
    public static void main(String[] args) throws UnsupportedEncodingException, MessagingException, InterruptedException {
        //  1   2不刷新房屋明细，获取详情   3刷新房屋明细并获取详情   4不刷新房屋
        args = new String[3];
        args[0] = "1";
        args[1] = "24aa469b-9a2d-58f4-968b-f22bcd0fa756";
        startJob(args);
    }

    /**
     * 启动定时启动器
     * 
     * @param args
     * @throws MessagingException
     * @throws UnsupportedEncodingException
     * @throws InterruptedException
     */
    public static void startJob(String[] args) throws UnsupportedEncodingException, MessagingException, InterruptedException {
        times++;
        LU.changeLogFile(FILE_LOCATION);
        LU.l("======================" + appName + "定时任务=====执行开始======================");
//        LU.changeSystemOut2Log();
        try {
            job(args);
        } catch (Exception e) {
            String fullStackTrace = ExceptionUtils.getFullStackTrace(e);
            LU.l("捕获到异常" + e.getMessage());
            e.printStackTrace();
            fullStackTrace = fullStackTrace.replace("\n", "<br>");
//             MU.sendMail("房屋捕获系统报警",
//             "捕获房屋系统时发生异常，异常信息如下:<br>"+e.getMessage()+"<br>"+fullStackTrace,
//             "", "919515134@qq.com","ljrxxx@aliyun.com");
//            if (times >= 1)
//                return;
//            Thread.sleep(10 * 60 * 1000);
//            startJob(args);
        }

        LU.l("======================" + appName + "定时任务=====执行结束======================");

    }

    private static void job(String[] args) throws Exception {
        LU.changeLogFile(FILE_LOCATION);
        ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath*:spring/spring-application.xml");
        LinkedHouseImpl 链家房屋信息捕获 = (LinkedHouseImpl) ctx.getBean("linkedHouseImpl");
        if(args.length != 4) {
                try {
                    链家房屋信息捕获.捕获房屋信息(args);
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
        房屋统计信息 查询捕获房屋统计信息 = 链家房屋信息捕获.查询捕获房屋统计信息();
        List<房屋统计信息> 查询房屋价格走势根据批次号 = 链家房屋信息捕获.查询房屋价格走势根据批次号("20170701000000");
        String 创建链家价格走势图文件 = CreateChartServiceImpl.创建链家价格走势图(查询房屋价格走势根据批次号);
        List<房屋统计信息> 查询批次号价格变动数据 = 链家房屋信息捕获.查询批次号价格变动数据(null);
        List<房屋基本信息> 本日捕获的房屋数据 = 链家房屋信息捕获.查询房屋基本信息通过创建日期(DateUtil.DateToString(new Date(), "yyyy-MM-dd 00:00:00"));
        StringBuilder sb = new StringBuilder();
        sb.append("<br>");
        sb.append("本日价格变动房屋汇总");
        sb.append("<br>");
        sb.append("<table border=\"1\" cellspacing=\"0\" cellpadding=\"0\" style=\"border-collapse:collapse; border-color:#000000\">");
        sb.append("<th>序号</th>");
        sb.append("<th>网址</th>");
        sb.append("<th>当前价格</th>");
        sb.append("<th>评估价格</th>");
        sb.append("<th>首付</th>");
        sb.append("<th>每平米价格</th>");
        sb.append("<th>户型</th>");
        sb.append("<th>实用面积</th>");
        sb.append("<th>建筑类型</th>");
        sb.append("<th>所在区县</th>");
        sb.append("<th>所在小区</th>");
        sb.append("<th>标题</th>");
        sb.append("<th>朝向</th>");
        sb.append("<th>产权年限</th>");
        sb.append("<th>差距价格</th>");
//        sb.append("<th>房屋基本信息id</th>");
//        sb.append("<th>房屋本批次号</th>");
//        sb.append("<th>房屋上一批次号</th>");
        sb.append("<th>原价格</th>");
        int i = 0;
        for (房屋统计信息 f : 查询批次号价格变动数据) {
            i++;
            sb.append("<tr>");
            sb.append("<td>" + i + "</td>");
            sb.append("<td><a href='" + f.get网址() + "' >点击访问</a></td>");
            sb.append("<td>" + f.get房屋本批次价格() + "</td>");
            sb.append("<td>" + f.get房屋本批次评估价() + "</td>");
            sb.append("<td>" + f.get房屋本批次首付() + "</td>");
            sb.append("<td>" + getAveragePrice(f.get房屋本批次价格(),f.get实用面积()) + "</td>");
            sb.append("<td>" + f.get户型() + "</td>");
            sb.append("<td>" + f.get实用面积() + "</td>");
            sb.append("<td>" + f.get建筑类型() + "</td>");
            sb.append("<td>" + f.get所在区县() + "</td>");
            sb.append("<td>" + f.get所在小区() + "</td>");
            sb.append("<td>" + f.get标题() + "</td>");
            sb.append("<td>" + f.get朝向() + "</td>");
            sb.append("<td>" + f.get产权年限() + "</td>");
            sb.append("<td>" + f.get差距价格() + "</td>");
//            sb.append("<td>" + f.get房屋基本信息id() + "</td>");
//            sb.append("<td>" + f.get房屋本批次号() + "</td>");
//            sb.append("<td>" + f.get房屋上一批次号() + "</td>");
            sb.append("<td>" + f.get房屋上批次价格() + "</td>");
            sb.append("</tr>");
        }
        sb.append("</table>");
        System.out.println("==================================================");
        sb.append("<br>");
        sb.append("本日新增房屋汇总");
        sb.append("<br>");
        sb.append("<table border=\"1\" cellspacing=\"0\" cellpadding=\"0\" style=\"border-collapse:collapse; border-color:#000000\">");
        sb.append("<th>序号</th>");
        sb.append("<th>网址</th>");
        sb.append("<th>当前价格</th>");
        sb.append("<th>评估价格</th>");
        sb.append("<th>首付</th>");
        sb.append("<th>每平米价格</th>");
        sb.append("<th>户型</th>");
        sb.append("<th>实用面积</th>");
        sb.append("<th>建筑类型</th>");
        sb.append("<th>所在区县</th>");
        sb.append("<th>所在小区</th>");
        sb.append("<th>标题</th>");
        sb.append("<th>朝向</th>");
        sb.append("<th>产权年限</th>");
//        sb.append("<th>房屋基本信息id</th>");
        i = 0;
        for (房屋基本信息 f : 本日捕获的房屋数据) {
            i++;
            sb.append("<tr>");
            sb.append("<td>" + i + "</td>");
            sb.append("<td><a href='" + f.get网址() + "' >点击访问</a></td>");
            sb.append("<td>" + f.get总价() + "</td>");
            sb.append("<td>" + f.get评估价() + "</td>");
            sb.append("<td>" + f.get首付() + "</td>");
            sb.append("<td>" + getAveragePrice(""+f.get总价(),f.get实用面积()) + "</td>");
            sb.append("<td>" + f.get户型() + "</td>");
            sb.append("<td>" + f.get实用面积() + "</td>");
            sb.append("<td>" + f.get建筑类型() + "</td>");
            sb.append("<td>" + f.get所在区县() + "</td>");
            sb.append("<td>" + f.get所在小区() + "</td>");
            sb.append("<td>" + f.get标题() + "</td>");
            sb.append("<td>" + f.get朝向() + "</td>");
            sb.append("<td>" + f.get产权年限() + "</td>");
//            sb.append("<td>" + f.get房屋基本信息id() + "</td>");
            sb.append("</tr>");
        }
        sb.append("</table>");
         AttchAndImgMail.sendEmail("房屋捕获系统通知",
         "捕获房屋数据完成,统计数据如下<br>"+查询捕获房屋统计信息.toString()+"<br>近日房屋涨跌数量走势图如下:<br>{image}"+sb.toString(),
         创建链家价格走势图文件, null);
//         MU.sendMail("房屋捕获系统通知", "捕获房屋数据完成,统计数据如下<br>"+查询捕获房屋统计信息.toString(),
//         "", "919515134@qq.com","ljrxxx@aliyun.com");
    }

    private static String getAveragePrice(String 房屋本批次价格, Double 实用面积) {
        int result = 0;
        try {
            double p = Double.parseDouble(房屋本批次价格);
            result = (int) (p/实用面积);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return ""+result;
    }

}
