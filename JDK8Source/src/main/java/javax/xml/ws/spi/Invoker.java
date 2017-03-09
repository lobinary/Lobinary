/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2009, Oracle and/or its affiliates. All rights reserved.
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

package javax.xml.ws.spi;

import javax.xml.ws.WebServiceContext;
import javax.xml.ws.WebServiceFeature;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;

/**
 * Invoker hides the detail of calling into application endpoint
 * implementation. Container hands over an implementation of Invoker
 * to JAX-WS runtime, and jax-ws runtime calls {@link #invoke}
 * for a web service invocation. Finally, Invoker does the actual
 * invocation of web service on endpoint instance.
 *
 * Container also injects the provided <code>WebServiceContext</code> and takes
 * care of invoking <code>javax.annotation.PostConstruct</code> methods,
 * if present, on the endpoint implementation.
 *
 * <p>
 *  调用者隐藏调用进入应用程序端点实现的细节。容器将Invoker实现传递给JAX-WS运行时,而jax-ws运行时调用{@link #invoke}用于Web服务调用。
 * 最后,Invoker在端点实例上执行Web服务的实际调用。
 * 
 *  Container还注入所提供的<code> WebServiceContext </code>,并负责在端点实现上调用<code> javax.annotation.PostConstruct </code>
 * 方法(如果存在)。
 * 
 * 
 * @see Provider#createEndpoint(String, Class, Invoker, WebServiceFeature...)
 * @author Jitendra Kotamraju
 * @since JAX-WS 2.2
 */

public abstract class Invoker {

    /**
     * JAX-WS runtimes calls this method to ask container to inject
     * WebServiceContext on the endpoint instance. The
     * <code>WebServiceContext</code> object uses thread-local information
     * to return the correct information during the actual endpoint invocation
     * regardless of how many threads are concurrently being used to serve
     * requests.
     *
     * <p>
     *  JAX-WS运行时调用此方法请求容器在端点实例上注入WebServiceContext。
     *  <code> WebServiceContext </code>对象使用线程本地信息在实际端点调用期间返回正确的信息,而不管同时使用多少线程来服务请求。
     * 
     * 
     * @param webServiceContext a holder for MessageContext
     * @throws IllegalAccessException if the injection done
     *         by reflection API throws this exception
     * @throws IllegalArgumentException if the injection done
     *         by reflection API throws this exception
     * @throws InvocationTargetException if the injection done
     *         by reflection API throws this exception
     */
    public abstract void inject(WebServiceContext webServiceContext)
    throws IllegalAccessException, IllegalArgumentException, InvocationTargetException;

    /**
     * JAX-WS runtime calls this method to do the actual web service
     * invocation on endpoint instance. The injected
     * <code>WebServiceContext.getMessageContext()</code> gives the correct
     * information for this invocation.
     *
     * <p>
     * 
     * @param m Method to be invoked on the service
     * @param args Method arguments
     * @return return value of the method
     * @throws IllegalAccessException if the invocation done
     *         by reflection API throws this exception
     * @throws IllegalArgumentException if the invocation done
     *         by reflection API throws this exception
     * @throws InvocationTargetException if the invocation done
     *         by reflection API throws this exception

     * @see Method#invoke
     */
    public abstract Object invoke(Method m, Object... args)
    throws  IllegalAccessException, IllegalArgumentException, InvocationTargetException;

}
