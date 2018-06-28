package com.lobinary.test.normal;
//package com.boce.test;
//
//import static org.junit.Assert.assertNotNull;
//
//import javax.annotation.Resource;
//
//import org.junit.Assert;
//import org.junit.Test;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
//
//import com.unicompayment.fap.common.dto.xml.FAP;
//import com.unicompayment.fap.common.dto.xml.HEAD;
//import com.unicompayment.fap.common.dto.xml.TX_INFO;
//import com.unicompayment.fap.common.utils.encrypt.PrintEncrypt;
//import com.unicompayment.fap.portal.util.FAPAssembleUtil;
//
//@ContextConfiguration(locations="classpath:spring/spring.xml")
//public class Spring测试问题 extends AbstractJUnit4SpringContextTests{
//	
//	@Resource(name="fapPortalHandler")
//	FapPortalHandler fapPortalHandler;
//	
//	@Test
//	public void testSendRequestNull() {
//		FAP requestFap = FAPAssembleUtil.createNewReqFap();
//		requestFap.getREQUEST().getENVELOPE().getTX_INFO().setFUND_CHANNEL_CODE("WZF_ALL");
//		requestFap.getREQUEST().getENVELOPE().getHEAD().setTX_TYPE("1001");
//		requestFap.getREQUEST().getENVELOPE().getTX_INFO().setORDER_NO("123456789");
//		FAP resultFap = fapPortalHandler.sendTx2Bank(requestFap);
//		Assert.assertNotNull(resultFap);
//		TX_INFO txInfo = resultFap.getRESPONSE().getENVELOPE().getTX_INFO();
//		HEAD head = resultFap.getRESPONSE().getENVELOPE().getHEAD();
//		System.out.println(head.getRET_CODE());
//		System.out.println(head.getRET_MSG());
//		System.out.println(txInfo.getERROR_CODE());
//		System.out.println(txInfo.getERROR_MSG());
//		System.out.println(txInfo.getAGREEMENT_NO());
//		System.out.println(txInfo.getFUND_CHANNEL_CODE());
//		System.out.println(txInfo.getORDER_NO());
//		String oh = PrintEncrypt.printObj(false, resultFap);
//		System.out.println(oh);
//	}
//	
//
//	/**
//	 * 空参数测试
//	 */
//	@Test
//	public void testSendTx2BankNullParam() {
//		assertNotNull(fapPortalHandler);
//		FAP fap = fapPortalHandler.sendTx2Bank(null);
//		assertNotNull(fap.getRESPONSE().getENVELOPE().getTX_INFO().getERROR_CODE());
//	}
//	
//	/**
//	 * 其他空参数测试
//	 */
//	@Test
//	public void testSendTx2BankOtherNullParam() {
//		FAP fap = FAPAssembleUtil.createNewReqFap();
//		FAP fap1 = fapPortalHandler.sendTx2Bank(fap);
//		assertNotNull(fap1.getRESPONSE().getENVELOPE().getTX_INFO().getERROR_CODE());
//	}
//	
//	/**
//	 * 空业务测试
//	 */
//	@Test
//	public void testSendTx2BankNullBusiness() {
//		FAP fap = FAPAssembleUtil.createNewReqFap();
//		HEAD head = fap.getREQUEST().getENVELOPE().getHEAD();
//		head.setTX_TYPE("0001");
//		TX_INFO txInfo = fap.getREQUEST().getENVELOPE().getTX_INFO();
//		txInfo.setFUND_CHANNEL_CODE("CGB_FAST");
//		txInfo.setBANK_ACCOUNT_TYPE("1");
//		FAP fap1 = fapPortalHandler.sendTx2Bank(fap);
//		assertNotNull(fap1.getRESPONSE().getENVELOPE().getTX_INFO().getERROR_CODE());
//	}
//	
//	
//	
//	/**
//	 * 广发快捷签约测试
//	 */
//	@Test
//	public void testSendCGBFASTSIGN2Bank() {
//		FAP fap = FAPAssembleUtil.createNewReqFap();
//		HEAD head = fap.getREQUEST().getENVELOPE().getHEAD();
//		head.setTX_TYPE("0001");
//		TX_INFO txInfo = fap.getREQUEST().getENVELOPE().getTX_INFO();
//		txInfo.setFUND_CHANNEL_CODE("CGB_FAST");
//		txInfo.setBANK_ACCOUNT_TYPE("1");
//		FAP fap1 = fapPortalHandler.sendTx2Bank(fap);
//		assertNotNull(fap1.getRESPONSE().getENVELOPE().getTX_INFO().getERROR_CODE());
//	}	
//	/**
//	 * 广发快捷消费测试
//	 * java.lang.NoSuchMethodError: org.springframework.core.annotation.AnnotationUtils.isInJavaLangAnnotationPackage(Ljava/lang/annotation/Annotation;)Z
//	at org.springframework.test.context.MetaAnnotationUtils.findAnnotationDescriptor(MetaAnnotationUtils.java:126)
//	at org.springframework.test.context.MetaAnnotationUtils.findAnnotationDescriptor(MetaAnnotationUtils.java:96)
//	at org.springframework.test.context.ContextLoaderUtils.resolveContextConfigurationAttributes(ContextLoaderUtils.java:432)
//	at org.springframework.test.context.ContextLoaderUtils.buildMergedContextConfiguration(ContextLoaderUtils.java:656)
//	at org.springframework.test.context.DefaultTestContext.<init>(DefaultTestContext.java:92)
//	at org.springframework.test.context.TestContextManager.<init>(TestContextManager.java:122)
//	at org.springframework.test.context.junit4.SpringJUnit4ClassRunner.createTestContextManager(SpringJUnit4ClassRunner.java:118)
//	at org.springframework.test.context.junit4.SpringJUnit4ClassRunner.<init>(SpringJUnit4ClassRunner.java:107)
//	at sun.reflect.NativeConstructorAccessorImpl.newInstance0(Native Method)
//	at sun.reflect.NativeConstructorAccessorImpl.newInstance(NativeConstructorAccessorImpl.java:39)
//	at sun.reflect.DelegatingConstructorAccessorImpl.newInstance(DelegatingConstructorAccessorImpl.java:27)
//	at java.lang.reflect.Constructor.newInstance(Constructor.java:513)
//	at org.junit.internal.builders.AnnotatedBuilder.buildRunner(AnnotatedBuilder.java:29)
//	at org.junit.internal.builders.AnnotatedBuilder.runnerForClass(AnnotatedBuilder.java:21)
//	at org.junit.runners.model.RunnerBuilder.safeRunnerForClass(RunnerBuilder.java:59)
//	at org.junit.internal.builders.AllDefaultPossibilitiesBuilder.runnerForClass(AllDefaultPossibilitiesBuilder.java:26)
//	at org.junit.runners.model.RunnerBuilder.safeRunnerForClass(RunnerBuilder.java:59)
//	at org.junit.internal.requests.ClassRequest.getRunner(ClassRequest.java:26)
//	at org.junit.internal.requests.FilterRequest.getRunner(FilterRequest.java:31)
//	at org.eclipse.jdt.internal.junit4.runner.JUnit4TestReference.<init>(JUnit4TestReference.java:33)
//	at org.eclipse.jdt.internal.junit4.runner.JUnit4TestMethodReference.<init>(JUnit4TestMethodReference.java:25)
//	at org.eclipse.jdt.internal.junit4.runner.JUnit4TestLoader.createTest(JUnit4TestLoader.java:54)
//	at org.eclipse.jdt.internal.junit4.runner.JUnit4TestLoader.loadTests(JUnit4TestLoader.java:38)
//	at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.runTests(RemoteTestRunner.java:452)
//	at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.runTests(RemoteTestRunner.java:683)
//	at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.run(RemoteTestRunner.java:390)
//	at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.main(RemoteTestRunner.java:197)
//	出现此错误为引入其他common工程导致，具体原因待议
//	出现此问题的原因已经查明，原因为在UNPcommon中有3.2.3版本的spring 包 更改为统一的4.0.8就不会报错了
//	具体原因为同时存在3.2.3和4.0.8的spring-core包，导致报错3.2.3包中不存在此方法，所以找不到该方法
//
//	 */
//	@Test
//	public void testSendCGBFASTPay2Bank() {
//		FAP fap = FAPAssembleUtil.createNewReqFap();
//		HEAD head = fap.getREQUEST().getENVELOPE().getHEAD();
//		head.setTX_TYPE("0002");
//		TX_INFO txInfo = fap.getREQUEST().getENVELOPE().getTX_INFO();
//		txInfo.setFUND_CHANNEL_CODE("CGB_FAST");
//		txInfo.setBANK_ACCOUNT_TYPE("1");
//		FAP fap1 = fapPortalHandler.sendTx2Bank(fap);
//		assertNotNull(fap1.getRESPONSE().getENVELOPE().getTX_INFO().getERROR_CODE());
//	}
//}
//
