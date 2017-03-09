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

/**
  Provides HTTP SPI that is used for portable deployment of JAX-WS
  web services in containers(for e.g. servlet containers). This SPI
  is not for end developers but provides a way for the container
  developers to deploy JAX-WS services portably.

  <p>
  The portable deployment is done as below:
  <ol>
  <li>Container creates {@link javax.xml.ws.Endpoint} objects for an
  application. The necessary information to create Endpoint objects
  may be got from web service deployment descriptor files.</li>
  <li>Container needs to create {@link javax.xml.ws.spi.http.HttpContext}
  objects for the deployment. For example, a HttpContext could be
  created using servlet configuration(for e.g url-pattern) for the
  web service in servlet container case.</li>
  <li>Then publishes all the endpoints using
  {@link javax.xml.ws.Endpoint#publish(HttpContext)}. During publish(),
  JAX-WS runtime registers a {@link javax.xml.ws.spi.http.HttpHandler}
  callback to handle incoming requests or
  {@link javax.xml.ws.spi.http.HttpExchange} objects. The HttpExchange
  object encapsulates a HTTP request and a response.
  </ol>

  <pre>
  Container                               JAX-WS runtime
  ---------                               --------------
  1. Creates Invoker1, ... InvokerN
  2. Provider.createEndpoint(...)     --> 3. creates Endpoint1
     configures Endpoint1
     ...
  4. Provider.createEndpoint(...)     --> 5. creates EndpointN
     configures EndpointN
  6. Creates ApplicationContext
  7. creates HttpContext1, ... HttpContextN
  8. Endpoint1.publish(HttpContext1)  --> 9. creates HttpHandler1
                                          HttpContext1.setHandler(HttpHandler1)
     ...
 10. EndpointN.publish(HttpContextN)  --> 11. creates HttpHandlerN
                                         HttpContextN.setHandler(HttpHandlerN)

  </pre>

  The request processing is done as below(for every request):
  <pre>
  Container                               JAX-WS runtime
  ---------                               --------------
  1. Creates a HttpExchange
  2. Gets handler from HttpContext
  3. HttpHandler.handle(HttpExchange) --> 4. reads request from HttpExchange
                                      <-- 5. Calls Invoker
  6. Invokes the actual instance
                                          7. Writes the response to HttpExchange
  </pre>

  <p>
  The portable undeployment is done as below:
  <pre>
  Container
  ---------
  1. @preDestroy on instances
  2. Endpoint1.stop()
  ...
  3. EndpointN.stop()
  </pre>

/* <p>
/*  提供HTTP SPI,用于在容器(例如servlet容器)中便携式部署JAX-WS Web服务。这个SPI不是为最终开发人员,但为容器开发人员提供一种方式来可移植地部署JAX-WS服务。
/* 
/* <p>
/*  可移植部署如下：
/* <ol>
/*  <li>容器为应用程序创建{@link javax.xml.ws.Endpoint}对象。创建Endpoint对象的必要信息可能来自Web服务部署描述符文件。
/* </li> <li>容器需要为部署创建{@link javax.xml.ws.spi.http.HttpContext}对象。
/* 例如,可以使用Servlet配置(例如url-pattern)为servlet容器中的web服务创建HttpContext。
/* </li> <li>然后使用{@link javax.xml.ws.Endpoint)发布所有端点#publish(HttpContext)}。
/* 在publish()期间,JAX-WS运行时注册一个{@link javax.xml.ws.spi.http.HttpHandler}回调以处理传入的请求或{@link javax.xml.ws.spi.http.HttpExchange}
/* 对象。
/* </li> <li>然后使用{@link javax.xml.ws.Endpoint)发布所有端点#publish(HttpContext)}。 HttpExchange对象封装了HTTP请求和响应。
/* </ol>
/* 
/* <pre>
/* 容器JAX-WS运行时--------- -------------- 1.创建Invoker1,... InvokerN 2. Provider.createEndpoint(...) - > 3.创
/* 建Endpoint1配置Endpoint1 ... 4. Provider.createEndpoint(...) - > 5.创建EndpointN配置EndpointN 6.创建Applicatio
/* 
  @author Jitendra Kotamraju
  @since JAX-WS 2.2
 */
package javax.xml.ws.spi.http;
