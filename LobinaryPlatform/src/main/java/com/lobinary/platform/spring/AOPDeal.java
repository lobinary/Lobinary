package com.lobinary.platform.spring;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import com.lobinary.platform.util.LogUtil;


@Aspect
@Component("actionDeal")
public class AOPDeal {
	
	private Logger logger = LogUtil.getLog(getClass());
	
    @Pointcut("execution(* com.boce.cms.service..*.*(..))")
    public void allServiceMethod() {}
    
    @Pointcut("execution(* com.boce.cms..*.*(..))")
    public void allMethod() {}
    
    @Pointcut("execution(* com.boce.cms.util..*.*(..))")
    public void allUtilMethod() {}
	
//	@Around("execution(* com.boce.cms.service..*.*(..))")
//	public Object logg(ProceedingJoinPoint pjp) throws Throwable{
//		String runName = pjp.getTarget().getClass().getSimpleName();
//		org.aspectj.lang.Signature s = pjp.getSignature();
//		LogUtil.log(runName + "." + s.getName() + "()执行中");
//		return pjp.proceed();
//	}
	
	@AfterThrowing(pointcut="allMethod()",throwing="e")
	public void allServiceExceptionInterceptor(Exception e){
		if(e.getMessage().contains("AccessDecisionManager")){
		}else{
			logger.info("*****************************************************************************");
			logger.info("*********************AOP异常拦截器拦截到异常，已经记录到日志中!***************************");
			logger.info("*****************************************************************************");
			LogUtil.logException(e);
		}
	}

}
