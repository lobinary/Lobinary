package com.lobinary.test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Test {

	  private final DateFormat enUsFormat
	      = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, Locale.US);
	  private final DateFormat localFormat
	      = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT);
	  private final DateFormat iso8601Format = buildIso8601Format();

	public static void main(String[] args) throws ParseException {

		//		 List<?> datas = new Gson().fromJson("2016-10-31 00:00:00", new TypeToken<List<?>>() {}.getType());
//		 System.out.println(datas);
//		 DateFormat a = buildIso8601Format();
//		 Date parse = a.parse("2016-10-31 00:00:00");
//		 System.out.println(parse);
//		 Test t = new Test();
//		 System.out.println(t.enUsFormat.format(new Date()));
//		 System.out.println(t.localFormat.format(new Date()));
//		testGSON();
		
		Locale l = new Locale("en_US");
		DateFormat d = DateFormat.getDateTimeInstance(DateFormat.DEFAULT,DateFormat.DEFAULT,  l);
		System.out.println(d.format(new Date()));
		System.out.println(d.parse("2016-10-31 00:00:00"));
		
	}	
	private static DateFormat buildIso8601Format() {
	    DateFormat iso8601Format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
	    iso8601Format.setTimeZone(TimeZone.getTimeZone("UTC"));
	    return iso8601Format;
	  }
	

public static void testGSON(){
//    String json = "[{\"assessmentDate\":null,\"billType\":\"1\",\"bizType\":\"memberPay\",\"bizTypeName\":\"会员支付\"," +
//            "\"bussinessEntity\":null,\"currency\":null,\"desc\":\"2016-10\",\"glDate\":null,\"id\":null," +
//            "\"invoiceAmountTax\":\"0.64\",\"invoiceDate\":\"2016-10-31T00:00:00'\",\"invoiceNo\":\"D234234\"," +
//            "\"invoiceTotalAmount\":\"22.00\",\"invoiceType\":\"COMPANY_VAT_INVOICE\",\"invoiceTypeName\":" +
//            "\"公司-代开增值税发票3%\",\"lineAmount\":\"194.17\",\"name\":\"西安云上票务服务有限公司-10013060426\",\"num\":\"" +
//            "10013060426\",\"payType\":null,\"productionLine\":\"airTravel\",\"profitAmountTax\":\"5.83\",\"profitRecordId" +
//            "\":2552,\"profitTotalAmount\":200,\"saleName\":\"陈蔡金\",\"validationColumn\":\"200.00\"}]";

    String json =
    "[{\"assessmentDate\":\"2016-10-31T00:00:00Z\",\"billConfirm\":\"0\",\"billType\":\"标准\",\"bizType\":\"epos\",\"bizTypeName\":\"EPOS\",\"bussinessEntity\":\"301_OU_易宝总部\",\"currency\":\"CNY\",\"desc\":\"2016-09\",\"glDate\":\"2016-10-31 00:00:00\",\"id\":124,\"invoiceAmountTax\":\"0.15\",\"invoiceDate\":\"2016-10-31 00:00:00\",\"invoiceNo\":\"2212121\",\"invoiceTotalAmount\":\"5.00\",\"invoiceType\":\"COMPANY_VAT_INVOICE\",\"invoiceTypeName\":\"公司-代开增值税发票3%\",\"lastUpdateTime\":null,\"lastUpdateTimeStr\":\"\",\"lineAmount\":\"4.93\",\"name\":\"生产内测代理商金鑫2\",\"num\":\"80000000792\",\"payType\":\"YPY_标准付款\",\"productionLine\":\"productCenter\",\"profitAmountTax\":\"0.15\",\"profitMonth\":null,\"profitRecordId\":34007,\"profitTotalAmount\":5.08,\"profitYear\":null,\"saleName\":\"内测\",\"validationColumn\":\"5.08\"}]";

    Gson gson = new Gson();
    List<InvoiceConfirmDTO> ps = gson.fromJson(json, new TypeToken<List<InvoiceConfirmDTO>>(){}.getType());
    for(int i = 0; i < ps.size() ; i++)
    {
        InvoiceConfirmDTO p = ps.get(i);
        System.out.println(p.toString());
    }

}
}
