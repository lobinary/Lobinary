/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2005, 2013, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

package javax.annotation;

import java.lang.annotation.*;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

/**
 * The PreDestroy annotation is used on methods as a callback notification to
 * signal that the instance is in the process of being removed by the
 * container. The method annotated with PreDestroy is typically used to
 * release resources that it has been holding. This annotation MUST be
 * supported by all container managed objects that support PostConstruct
 * except the application client container in Java EE 5. The method on which
 * the PreDestroy annotation is applied MUST fulfill all of the following
 * criteria:
 * <p>
 * <ul>
 * <li>The method MUST NOT have any parameters except in the case of
 * interceptors in which case it takes an InvocationContext object as
 * defined by the Interceptors specification.</li>
 * <li>The method defined on an interceptor class MUST HAVE one of the
 * following signatures:
 * <p>
 * void &#060;METHOD&#062;(InvocationContext)
 * <p>
 * Object &#060;METHOD&#062;(InvocationContext) throws Exception
 * <p>
 * <i>Note: A PreDestroy interceptor method must not throw application
 * exceptions, but it may be declared to throw checked exceptions including
 * the java.lang.Exception if the same interceptor method interposes on
 * business or timeout methods in addition to lifecycle events. If a
 * PreDestroy interceptor method returns a value, it is ignored by
 * the container.</i>
 * </li>
 * <li>The method defined on a non-interceptor class MUST HAVE the
 * following signature:
 * <p>
 * void &#060;METHOD&#062;()
 * </li>
 * <li>The method on which PreDestroy is applied MAY be public, protected,
 * package private or private.</li>
 * <li>The method MUST NOT be static.</li>
 * <li>The method MAY be final.</li>
 * <li>If the method throws an unchecked exception it is ignored except in the
 * case of EJBs where the EJB can handle exceptions.</li>
 * </ul>
 *
 * <p>
 *  PreDestroy注释在方法上用作回调通知,以表示实例正在由容器删除的过程中。使用PreDestroy注释的方法通常用于释放它持有的资源。
 * 除了Java EE 5中的应用程序客户端容器,支持PostConstruct的所有容器管理对象都必须支持此注释。应用PreDestroy注释的方法必须满足以下所有条件：。
 * <p>
 * <ul>
 *  <li>该方法不能有任何参数,除非在拦截器的情况下,它需要一个InvocationContext对象,如拦截器规范定义。</li> <li>在拦截器类上定义的方法必须有一个以下签名：
 * <p>
 *  void <METHOD>(InvocationContext)
 * <p>
 *  对象<METHOD>(InvocationContext)抛出异常
 * <p>
 *  <i>注意：PreDestroy拦截器方法不能抛出应用程序异常,但是如果相同的拦截器方法插入到生命周期事件之外的业务或超时方法,它可能会被声明抛出检查异常,包括java.lang.Exception。
 * 
 * @see javax.annotation.PostConstruct
 * @see javax.annotation.Resource
 * @since Common Annotations 1.0
 */

@Documented
@Retention (RUNTIME)
@Target(METHOD)
public @interface PreDestroy {
}
