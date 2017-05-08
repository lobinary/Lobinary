package com.lobinary.工具类;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.spi.TimeZoneNameProvider;

public class TxtFormater {

	/**
	 * 
	 * An abstract class for service providers that provide localized string
	 * representations (display names) of {@code Calendar} field values.
	 *
	 * <p>
	 * <a name="calendartypes"><b>Calendar Types</b></a>
	 *
	 * <p>
	 * Calendar types are used to specify calendar systems for which the
	 * {@link #getDisplayName(String, int, int, int, Locale) getDisplayName} and
	 * {@link #getDisplayNames(String, int, int, Locale) getDisplayNames}
	 * methods provide calendar field value names. See
	 * {@link Calendar#getCalendarType()} for details.
	 *
	 * <p>
	 * <b>Calendar Fields</b>
	 *
	 * <p>
	 * Calendar fields are specified with the constants defined in
	 * {@link Calendar}. The following are calendar-common fields and their
	 * values to be supported for each calendar system.
	 *
	 * <table style=
	 * "border-bottom:1px solid" border="1" cellpadding="3" cellspacing="0"
	 * summary="Field values">
	 * <tr>
	 * <th>Field</th>
	 * <th>Value</th>
	 * <th>Description</th>
	 * </tr>
	 * <tr>
	 * <td valign="top">{@link Calendar#MONTH}</td>
	 * <td valign="top">{@link Calendar#JANUARY} to {@link Calendar#UNDECIMBER}
	 * </td>
	 * <td>Month numbering is 0-based (e.g., 0 - January, ..., 11 - December).
	 * Some calendar systems have 13 months. Month names need to be supported in
	 * both the formatting and stand-alone forms if required by the supported
	 * locales. If there's no distinction in the two forms, the same names
	 * should be returned in both of the forms.</td>
	 * </tr>
	 * <tr>
	 * <td valign="top">{@link Calendar#DAY_OF_WEEK}</td>
	 * <td valign="top">{@link Calendar#SUNDAY} to {@link Calendar#SATURDAY}
	 * </td>
	 * <td>Day-of-week numbering is 1-based starting from Sunday (i.e., 1 -
	 * Sunday, ..., 7 - Saturday).</td>
	 * </tr>
	 * <tr>
	 * <td valign="top">{@link Calendar#AM_PM}</td>
	 * <td valign="top">{@link Calendar#AM} to {@link Calendar#PM}</td>
	 * <td>0 - AM, 1 - PM</td>
	 * </tr>
	 * </table>
	 *
	 * <p style="margin-top:20px">
	 * The following are calendar-specific fields and their values to be
	 * supported.
	 *
	 * <table style=
	 * "border-bottom:1px solid" border="1" cellpadding="3" cellspacing="0"
	 * summary="Calendar type and field values">
	 * <tr>
	 * <th>Calendar Type</th>
	 * <th>Field</th>
	 * <th>Value</th>
	 * <th>Description</th>
	 * </tr>
	 * <tr>
	 * <td rowspan="2" valign="top">{@code "gregory"}</td>
	 * <td rowspan="2" valign="top">{@link Calendar#ERA}</td>
	 * <td>0</td>
	 * <td>{@link java.util.GregorianCalendar#BC} (BCE)</td>
	 * </tr>
	 * <tr>
	 * <td>1</td>
	 * <td>{@link java.util.GregorianCalendar#AD} (CE)</td>
	 * </tr>
	 * <tr>
	 * <td rowspan="2" valign="top">{@code "buddhist"}</td>
	 * <td rowspan="2" valign="top">{@link Calendar#ERA}</td>
	 * <td>0</td>
	 * <td>BC (BCE)</td>
	 * </tr>
	 * <tr>
	 * <td>1</td>
	 * <td>B.E. (Buddhist Era)</td>
	 * </tr>
	 * <tr>
	 * <td rowspan="6" valign="top">{@code "japanese"}</td>
	 * <td rowspan="5" valign="top">{@link Calendar#ERA}</td>
	 * <td>0</td>
	 * <td>Seireki (Before Meiji)</td>
	 * </tr>
	 * <tr>
	 * <td>1</td>
	 * <td>Meiji</td>
	 * </tr>
	 * <tr>
	 * <td>2</td>
	 * <td>Taisho</td>
	 * </tr>
	 * <tr>
	 * <td>3</td>
	 * <td>Showa</td>
	 * </tr>
	 * <tr>
	 * <td>4</td>
	 * <td >Heisei</td>
	 * </tr>
	 * <tr>
	 * <td>{@link Calendar#YEAR}</td>
	 * <td>1</td>
	 * <td>the first year in each era. It should be returned when a long style (
	 * {@link Calendar#LONG_FORMAT} or {@link Calendar#LONG_STANDALONE}) is
	 * specified. See also the
	 * <a href="../../text/SimpleDateFormat.html#year"> Year representation in
	 * {@code SimpleDateFormat}</a>.</td>
	 * </tr>
	 * <tr>
	 * <td rowspan="2" valign="top">{@code "roc"}</td>
	 * <td rowspan="2" valign="top">{@link Calendar#ERA}</td>
	 * <td>0</td>
	 * <td>Before R.O.C.</td>
	 * </tr>
	 * <tr>
	 * <td>1</td>
	 * <td>R.O.C.</td>
	 * </tr>
	 * <tr>
	 * <td rowspan="2" valign="top">{@code "islamic"}</td>
	 * <td rowspan="2" valign="top">{@link Calendar#ERA}</td>
	 * <td>0</td>
	 * <td>Before AH</td>
	 * </tr>
	 * <tr>
	 * <td>1</td>
	 * <td>Anno Hijrah (AH)</td>
	 * </tr>
	 * </table>
	 *
	 * <p>
	 * Calendar field value names for {@code "gregory"} must be consistent with
	 * the date-time symbols provided by
	 * {@link java.text.spi.DateFormatSymbolsProvider}.
	 *
	 * <p>
	 * Time zone names are supported by {@link TimeZoneNameProvider}.
	 *
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		String s = "An abstract class for service providers that provide localized string representations (display names) of {@code Calendar} field values. \n  <p><a name=\"calendartypes\"><b>Calendar Types</b></a> \n  <p>Calendar types are used to specify calendar systems for which the {@link #getDisplayName(String, int, int, int, Locale) getDisplayName} and {@link #getDisplayNames(String, int, int, Locale) getDisplayNames} methods provide calendar field value names. See {@link Calendar#getCalendarType()} for details. \n  <p><b>Calendar Fields</b> \n  <p>Calendar fields are specified with the constants defined in {@link Calendar}. The following are calendar-common fields and their values to be supported for each calendar system. \n  <table style=\"border-bottom:1px solid\" border=\"1\" cellpadding=\"3\" cellspacing=\"0\" summary=\"Field values\"> <tr> <th>Field</th> <th>Value</th> <th>Description</th> </tr> <tr> <td valign=\"top\">{@link Calendar#MONTH}</td> <td valign=\"top\">{@link Calendar#JANUARY} to {@link Calendar#UNDECIMBER}</td> <td>Month numbering is 0-based (e.g., 0 - January, ..., 11 - December). Some calendar systems have 13 months. Month names need to be supported in both the formatting and stand-alone forms if required by the supported locales. If there's no distinction in the two forms, the same names should be returned in both of the forms.</td> </tr> <tr> <td valign=\"top\">{@link Calendar#DAY_OF_WEEK}</td> <td valign=\"top\">{@link Calendar#SUNDAY} to {@link Calendar#SATURDAY}</td> <td>Day-of-week numbering is 1-based starting from Sunday (i.e., 1 - Sunday, ..., 7 - Saturday).</td> </tr> <tr> <td valign=\"top\">{@link Calendar#AM_PM}</td> <td valign=\"top\">{@link Calendar#AM} to {@link Calendar#PM}</td> <td>0 - AM, 1 - PM</td> </tr> </table> \n  <p style=\"margin-top:20px\">The following are calendar-specific fields and their values to be supported. \n  <table style=\"border-bottom:1px solid\" border=\"1\" cellpadding=\"3\" cellspacing=\"0\" summary=\"Calendar type and field values\"> <tr> <th>Calendar Type</th> <th>Field</th> <th>Value</th> <th>Description</th> </tr> <tr> <td rowspan=\"2\" valign=\"top\">{@code \"gregory\"}</td> <td rowspan=\"2\" valign=\"top\">{@link Calendar#ERA}</td> <td>0</td> <td>{@link java.util.GregorianCalendar#BC} (BCE)</td> </tr> <tr> <td>1</td> <td>{@link java.util.GregorianCalendar#AD} (CE)</td> </tr> <tr> <td rowspan=\"2\" valign=\"top\">{@code \"buddhist\"}</td> <td rowspan=\"2\" valign=\"top\">{@link Calendar#ERA}</td> <td>0</td> <td>BC (BCE)</td> </tr> <tr> <td>1</td> <td>B.E. (Buddhist Era)</td> </tr> <tr> <td rowspan=\"6\" valign=\"top\">{@code \"japanese\"}</td> <td rowspan=\"5\" valign=\"top\">{@link Calendar#ERA}</td> <td>0</td> <td>Seireki (Before Meiji)</td> </tr> <tr> <td>1</td> <td>Meiji</td> </tr> <tr> <td>2</td> <td>Taisho</td> </tr> <tr> <td>3</td> <td>Showa</td> </tr> <tr> <td>4</td> <td >Heisei</td> </tr> <tr> <td>{@link Calendar#YEAR}</td> <td>1</td> <td>the first year in each era. It should be returned when a long style ({@link Calendar#LONG_FORMAT} or {@link Calendar#LONG_STANDALONE}) is specified. See also the <a href=\"../../text/SimpleDateFormat.html#year\"> Year representation in {@code SimpleDateFormat}</a>.</td> </tr> <tr> <td rowspan=\"2\" valign=\"top\">{@code \"roc\"}</td> <td rowspan=\"2\" valign=\"top\">{@link Calendar#ERA}</td> <td>0</td> <td>Before R.O.C.</td> </tr> <tr> <td>1</td> <td>R.O.C.</td> </tr> <tr> <td rowspan=\"2\" valign=\"top\">{@code \"islamic\"}</td> <td rowspan=\"2\" valign=\"top\">{@link Calendar#ERA}</td> <td>0</td> <td>Before AH</td> </tr> <tr> <td>1</td> <td>Anno Hijrah (AH)</td> </tr> </table> \n  <p>Calendar field value names for {@code \"gregory\"} must be consistent with the date-time symbols provided by {@link java.text.spi.DateFormatSymbolsProvider}. \n  <p>Time zone names are supported by {@link TimeZoneNameProvider}. \n  \n  \n  <td> \n ";
		System.out.println(s);
		List<String> 全文缓冲池 = new ArrayList<String>();
		StringBuffer 行缓冲池 = new StringBuffer();
		StringBuffer 标签缓冲池 = new StringBuffer();
		String 本次字符串;
		int 每行的长度 = 50;
		boolean 标签开始 = false;
//		boolean 强制不换行 = false;// 当遇到一些不换行标签时，需要要求本次的缓冲池强制不换行,所以需要该标志
		for (int i = 0; i < s.length(); i++) {
			本次字符串 = s.substring(i, i + 1);
			if (是标签(本次字符串)) {// 如果是标签，就把数据存到标签缓冲池
				标签开始 = true;
			}
			if (标签开始) {
				标签缓冲池.append(本次字符串);
				if (标签缓冲池.equals("<<")) {// 那么这就不是标签，并且需要判断是<<还是<<<
					if (i + 1 < s.length() && s.substring(i + 1, i + 2).equals("<")) {// 证明<<的下一位是<，那么代表这是一个<<<标签
						i++;// 我们提前把数据拿下，防止下次循环又循环到单个<而误识别成标签
						标签缓冲池.append("<");
						行缓冲池.append(标签缓冲池);// <<<这类的都不换行，继续随着行输出
						标签缓冲池.delete(0, 标签缓冲池.length());// 清空标签缓冲池
						标签开始 = false;
					} else {
						// 如果不是<<<,那么代表后方的还是文字，继续由系统来处理吧
						行缓冲池.append(标签缓冲池);// <<这类的都不换行，继续随着行输出
						标签缓冲池.delete(0, 标签缓冲池.length());// 清空标签缓冲池
						标签开始 = false;
					}
				} else if (不换行的标签(标签缓冲池)) {
					// 不换行就随着输出，但是需要保证该不换行标签必须头尾标签都在同一行,也就是，当识别到<b>后，需要找到</b>才能换行
					if (不换行标签已经结束(标签缓冲池)) {// 也就是已经出现了</b>在结尾，那么就可以结束了标签记录
						行缓冲池.append(标签缓冲池);
						标签缓冲池.delete(0, 标签缓冲池.length());// 清空标签缓冲池
						标签开始 = false;
					}
				} else if (前后都换行的缩进标签(标签缓冲池)) {
					if (行缓冲池.length() == 0) {
						全文缓冲池.add(标签缓冲池.toString());
					}
				}
			} else {
				if(每行的长度<行缓冲池.length()){
					全文缓冲池.add(行缓冲池.toString());
					行缓冲池.delete(0, 行缓冲池.length());// 清空行缓冲池
				}else{
					行缓冲池.append(本次字符串);
				}
			}
		}
		
		System.out.println("##########################################################准备输出数据#################################################");
		for (String 测试数据 : 全文缓冲池) {
			System.out.println(测试数据);
		}
		System.out.println("##########################################################准备输出数据#################################################");
	}

	private static boolean 不换行标签已经结束(StringBuffer 标签缓冲池) throws Exception {
		String s = 标签缓冲池.toString();
		String 前缀标签 = 获取前缀标签(s);
		String 后缀标签 = 获取后缀标签(s);
		return 前缀标签!=null&&后缀标签!=null&&前缀标签.equals(后缀标签.replace("/", ""));
	}

	/**
	 * 后缀标签不能有属性，所以我们默认后缀标签都是这样子的： </ b  >
	 * 
	 * @param s
	 * @return
	 * @throws Exception
	 */
	private static String 获取后缀标签(String s) throws Exception {
		String sb = new String();
		boolean 开始记录标签开始字符 = false;//
		boolean 开始寻找标签结束标志 = false;// 也就是寻找结束的 >
		if (s.contains("</")) {//代表有结束标签
			for (int i = s.length(); i >0; i--) {
				String ss = s.substring(i-1, i);
				if (i == s.length() && ss.equals(">")) {
					sb = ">";
				}
				if (i < s.length() && !ss.equals(" ")) {
					开始记录标签开始字符 = true;
				}
				if (开始记录标签开始字符) {
					sb = ss + sb;
				}
				if (开始记录标签开始字符 && ss.equals(" ")) {// </   b  >
					开始寻找标签结束标志 = true;
				}

				if (开始寻找标签结束标志) {
					if(i-2<0)return null;
					ss = s.substring(i-2, i);
					if (ss.equals("</")) {
						if (sb.toString().equals("</>")) throw new Exception("发现非法标签数据</>，字符串为[" + s + "]");
						return "</"+sb;
					}
				}
			}
		}
		return null;
	}

	/**
	 * 必须是标签开头的才能抓取到
	 * 
	 * @param s
	 * @return
	 * @throws Exception
	 */
	private static String 获取前缀标签(String s) throws Exception {
		StringBuffer sb = new StringBuffer();
		boolean 开始记录标签开始字符 = false;//
		boolean 开始寻找标签结束标志 = false;// 也就是寻找结束的 >
		for (int i = 0; i < s.length(); i++) {
			String ss = s.substring(i, i + 1);
			if (i == 0 && ss.equals("<")) {
				sb.append(ss);
			} else {
				throw new Exception("非标签开头字符串无法获取前缀标签，字符串为[" + s + "]");
			}
			if (i > 0 && !ss.equals(" ")) {
				开始记录标签开始字符 = true;
			}
			if (开始记录标签开始字符) {
				sb.append(ss);
			}
			if (开始记录标签开始字符 && ss.equals(" ")) {
				开始寻找标签结束标志 = true;
			}

			if (开始寻找标签结束标志) {
				if (ss.equals(">")) {
					if (sb.toString().equals("<>"))
						throw new Exception("发现非法标签数据<>，字符串为[" + s + "]");
					return sb.append(">").toString();
				}
			}
		}
		return null;
	}

	private static boolean 不换行的标签(StringBuffer ss) {
		String s = ss.toString();
		return s.startsWith("<b");
	}

	/**
	 * 判断是不是前后都换行的标签
	 * 
	 * @param 标签缓冲池
	 * @return
	 */
	private static boolean 前后都换行的缩进标签(StringBuffer 标签缓冲池) {
		String s = 标签缓冲池.toString();
		return s.equals("<table") || s.equals("<tr");
	}

	/**
	 * 判断后边是否是标签
	 * 
	 * @param 本次字符串
	 * @return
	 */
	private static boolean 是标签(String 本次字符串) {
		return 本次字符串.equals("<") || 本次字符串.equals("{");
	}
	
	/**
	 * An abstract class for service providers that provide localized string representations (display names) of {@code Calendar} field values. 
  <p><a name="calendartypes"><b>Calendar Types</b></a> 
  <p>Calendar types are used to specify calendar systems for which the {@link #getDisplayName(String, int, int, int, Locale) getDisplayName} and {@link #getDisplayNames(String, int, int, Locale) getDisplayNames} methods provide calendar field value names. See {@link Calendar#getCalendarType()} for details. 
  <p><b>Calendar Fields</b> 
  <p>Calendar fields are specified with the constants defined in {@link Calendar}. The following are calendar-common fields and their values to be supported for each calendar system. 
  <table style="border-bottom:1px solid" border="1" cellpadding="3" cellspacing="0" summary="Field values"> <tr> <th>Field</th> <th>Value</th> <th>Description</th> </tr> <tr> <td valign="top">{@link Calendar#MONTH}</td> <td valign="top">{@link Calendar#JANUARY} to {@link Calendar#UNDECIMBER}</td> <td>Month numbering is 0-based (e.g., 0 - January, ..., 11 - December). Some calendar systems have 13 months. Month names need to be supported in both the formatting and stand-alone forms if required by the supported locales. If there's no distinction in the two forms, the same names should be returned in both of the forms.</td> </tr> <tr> <td valign="top">{@link Calendar#DAY_OF_WEEK}</td> <td valign="top">{@link Calendar#SUNDAY} to {@link Calendar#SATURDAY}</td> <td>Day-of-week numbering is 1-based starting from Sunday (i.e., 1 - Sunday, ..., 7 - Saturday).</td> </tr> <tr> <td valign="top">{@link Calendar#AM_PM}</td> <td valign="top">{@link Calendar#AM} to {@link Calendar#PM}</td> <td>0 - AM, 1 - PM</td> </tr> </table> 
  <p style="margin-top:20px">The following are calendar-specific fields and their values to be supported. 
  <table style="border-bottom:1px solid" border="1" cellpadding="3" cellspacing="0" summary="Calendar type and field values"> <tr> <th>Calendar Type</th> <th>Field</th> <th>Value</th> <th>Description</th> </tr> <tr> <td rowspan="2" valign="top">{@code "gregory"}</td> <td rowspan="2" valign="top">{@link Calendar#ERA}</td> <td>0</td> <td>{@link java.util.GregorianCalendar#BC} (BCE)</td> </tr> <tr> <td>1</td> <td>{@link java.util.GregorianCalendar#AD} (CE)</td> </tr> <tr> <td rowspan="2" valign="top">{@code "buddhist"}</td> <td rowspan="2" valign="top">{@link Calendar#ERA}</td> <td>0</td> <td>BC (BCE)</td> </tr> <tr> <td>1</td> <td>B.E. (Buddhist Era)</td> </tr> <tr> <td rowspan="6" valign="top">{@code "japanese"}</td> <td rowspan="5" valign="top">{@link Calendar#ERA}</td> <td>0</td> <td>Seireki (Before Meiji)</td> </tr> <tr> <td>1</td> <td>Meiji</td> </tr> <tr> <td>2</td> <td>Taisho</td> </tr> <tr> <td>3</td> <td>Showa</td> </tr> <tr> <td>4</td> <td >Heisei</td> </tr> <tr> <td>{@link Calendar#YEAR}</td> <td>1</td> <td>the first year in each era. It should be returned when a long style ({@link Calendar#LONG_FORMAT} or {@link Calendar#LONG_STANDALONE}) is specified. See also the <a href="../../text/SimpleDateFormat.html#year"> Year representation in {@code SimpleDateFormat}</a>.</td> </tr> <tr> <td rowspan="2" valign="top">{@code "roc"}</td> <td rowspan="2" valign="top">{@link Calendar#ERA}</td> <td>0</td> <td>Before R.O.C.</td> </tr> <tr> <td>1</td> <td>R.O.C.</td> </tr> <tr> <td rowspan="2" valign="top">{@code "islamic"}</td> <td rowspan="2" valign="top">{@link Calendar#ERA}</td> <td>0</td> <td>Before AH</td> </tr> <tr> <td>1</td> <td>Anno Hijrah (AH)</td> </tr> </table> 
  <p>Calendar field value names for {@code "gregory"} must be consistent with the date-time symbols provided by {@link java.text.spi.DateFormatSymbolsProvider}. 
  <p>Time zone names are supported by {@link TimeZoneNameProvider}. 
  
  用于提供{@code Calendar}字段值的本地化字符串表示（显示名称）的服务提供程序的抽象类。
  <p> <a name="calendartypes"> <b>日历类型</ b> </a>
  <p>日历类型用于指定{@link #getDisplayName（String，int，int，int，Locale）getDisplayName}和{@link #getDisplayNames（String，int，int，Locale）getDisplayNames}方法的日历系统提供日历字段值名称。有关详情，请参阅{@link Calendar＃getCalendarType（）}。
  <p> <b>日历字段</ b>
  <p>日历字段使用{@link Calendar}中定义的常量指定。以下是日历常用字段及其为每个日历系统支持的值。
  <table style =“border-bottom：1px solid”border =“1”cellpadding =“3”cellspacing =“0”summary =“Field values”> <tr> <th> Field </ th> <th> Value < / th> <th>说明</ th> </ tr> <tr> <td valign =“top”> {@ link Calendar＃MONTH} </ td> <td valign =“top”> {@ link Calendar＃ JANUARY}到{@link Calendar＃UNDECIMBER} </ td> <td>月份编号为0（例如，0 - 1月，...，11 - 12月）。一些日历系统有13个月。如果受支持的语言环境需要，则需要在格式化和独立表单中支持月份名称。 </ td> </ tr> <tr> <td valign =“top”> {@ link Calendar＃DAY_OF_WEEK} </ td </ td>如果两种形式没有区别，则应以两种形式返回相同的名称。 > <td valign =“top”> {@ link日历＃SUNDAY}到{@link日历＃SATURDAY} </ td> <td>星期几编号从1开始， ，...，7 - 星期六）。</ td> </ tr> <tr> <td valign =“top”> {@ link Calendar＃AM_PM} </ td> <td valign =“top”> { link Calendar＃AM} to {@link Calendar＃PM} </ td> <td> 0 - AM，1 - PM </ td> </ tr> </ table>
  <p style =“margin-top：20px”>以下是特定于日历的字段及其支持的值。
  <table style =“border-bottom：1px solid”border =“1”cellpadding =“3”cellspacing =“0”summary =“日历类型和字段值”> <tr> <th>日历类型</ th> th>字段</ th> <th>值</ th> <th>描述</ th> </ tr> <tr> <td rowspan =“2”valign =“top”> {@ code“gregory”} </ td> <td> <td> <td> <td> {@ link java.util.GregorianCalendar＃BC} </ td> <td rowspan =“2”valign =“top”> {@ link calendar＃ （BCE）</ td> </ tr> <tr> <td> 1 </ td> <td> {@ link java.util.GregorianCalendar＃AD} <td rowspan =“2”valign =“top”> {@ code“buddhist”} </ td> <td rowspan =“2”valign =“top”> {@ link Calendar＃ERA} </ td> <td > 0 </ td> <td> BC（BCE）</ td> </ tr> <tr> <td> 1 </ td> <td> （Buddhist Era）</ td> </ tr> <tr> <td rowspan =“6”valign =“top”> {@ code“japanese”} </ td> <td rowspan =“5”valign = “> {@ link Calendar＃ERA} </ td> <td> 0 </ td> <td> Seireki（Before Meiji）</ td> </ tr> <tr> <td> 1 </ td> <td > Meiji </ td> </ tr> <tr> <td> 2 </ td> <td> Taisho </ td> </ tr> <tr> <td> 3 </ td> <td> td> </ tr> <tr> <td> 4 </ td> <td> Heisei </ td> </ tr> <tr> <td> {@ link Calendar＃YEAR} </ td> <td> 1 </ td> <td>每个时代的第一年。如果指定了长字体（{@link Calendar＃LONG_FORMAT}或{@link Calendar＃LONG_STANDALONE}），则应返回。另请参见<a href="../../text/SimpleDateFormat.html#year"> Year在{@code SimpleDateFormat} </a>中的表示。</ td> </ tr> <tr> <td rowspan =“2”valign =“top”> {@ code“roc”} </ td> <td rowspan =“2”valign =“top”> {@ link Calendar＃ERA} </ td> <td> / td> <td>在ROC之前</ td> </ tr> <tr> <td> 1 </ td> <td> ROC </ td> </ tr> <tr> <td rowspan =“2”valign =“top”> {@ code“islamic”} </ td> <td rowspan =“2”valign =“top”> {@ link Calendar＃ERA} </ td> <td> 0 </ td> <td > Before AH </ td> </ tr> <tr> <td> 1 </ td> <td> Anno Hijrah（AH）</ td> </ tr> </ table>
  <p> {@code“gregory”}的日历字段值名称必须与{@link java.text.spi.DateFormatSymbolsProvider}提供的日期时间符号一致。
  <p>时区名称由{@link TimeZoneNameProvider}支持。
  <td> 
 

	 */
	public void test (){
		
	}

}
