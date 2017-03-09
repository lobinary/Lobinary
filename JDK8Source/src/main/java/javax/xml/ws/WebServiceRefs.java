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

package javax.xml.ws;

import java.lang.annotation.Documented;
import java.lang.annotation.Target;
import java.lang.annotation.Retention;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

/**
 * The <code>WebServiceRefs</code> annotation allows
 * multiple web service references to be declared at the
 * class level.
 *
 * <p>
 * It can be used to inject both service and proxy
 * instances. These injected references are not thread safe.
 * If the references are accessed by multiple threads,
 * usual synchronization techniques can be used to
 * support multiple threads.
 *
 * <p>
 * There is no way to associate web service features with
 * the injected instances. If an instance needs to be
 * configured with web service features, use @WebServiceRef
 * to inject the resource along with its features.
 *
 * <p>
 * <b>Example</b>: The <code>StockQuoteProvider</code>
 * proxy instance, and the <code>StockQuoteService</code> service
 * instance are injected using @WebServiceRefs.
 *
 * <pre><code>
 *    &#64;WebServiceRefs({&#64;WebServiceRef(name="service/stockquoteservice", value=StockQuoteService.class),
 *                     &#64;WebServiceRef(name="service/stockquoteprovider", type=StockQuoteProvider.class, value=StockQuoteService.class})
 *    public class MyClient {
 *        void init() {
 *            Context ic = new InitialContext();
 *            StockQuoteService service = (StockQuoteService) ic.lookup("java:comp/env/service/stockquoteservice");
 *            StockQuoteProvider port = (StockQuoteProvider) ic.lookup("java:comp/env/service/stockquoteprovider");
 *            ...
 *       }
 *       ...
 *    }
 * </code></pre>
 *
 * <p>
 *  <code> WebServiceRefs </code>注释允许在类级别声明多个Web服务引用。
 * 
 * <p>
 *  它可以用于注入服务和代理实例。这些注入的引用不是线程安全的。如果引用被多个线程访问,则通常的同步技术可以用于支持多个线程。
 * 
 * <p>
 *  没有办法将Web服务功能与注入的实例相关联。如果实例需要配置Web服务功能,请使用@WebServiceRef注入资源及其功能。
 * 
 * <p>
 *  <b>示例</b>：使用@WebServiceRefs注入<code> StockQuoteProvider </code>代理实例和<code> StockQuoteService </code>服
 * 
 * @see WebServiceRef
 * @since 2.0
 */

@Documented
@Retention(RUNTIME)
@Target(TYPE)
public @interface WebServiceRefs {
   /**
    * Array used for multiple web service reference declarations.
    * <p>
    * 务实例。
    * 
    *  <pre> <code> @WebServiceRefs({@ WebServiceRef(name ="service / stockquoteservice",value = StockQuoteService.class),@WebServiceRef(name ="service / stockquoteprovider",type = StockQuoteProvider.class,value = StockQuoteService.class }
    * )public class MyClient {void init(){Context ic = new InitialContext(); StockQuoteService service =(StockQuoteService)ic.lookup("java：comp / env / service / stockquoteservice"); StockQuoteProvider port =(StockQuoteProvider)ic。
    *  lookup("java：comp / env / service / stockquoteprovider"); ...} ...} </code> </pre>。
    */
   WebServiceRef[] value();
}
