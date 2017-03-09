/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2005, 2010, Oracle and/or its affiliates. All rights reserved.
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

import java.util.concurrent.Future;

/** The <code>Dispatch</code> interface provides support
 *  for the dynamic invocation of a service endpoint operations. The
 *  <code>javax.xml.ws.Service</code>
 *  class acts as a factory for the creation of <code>Dispatch</code>
 *  instances.
 *
 * <p>
 *  用于动态调用服务端点操作。 <code> javax.xml.ws.Service </code>类作为创建<code> Dispatch </code>实例的工厂。
 * 
 * 
 *  @since JAX-WS 2.0
**/
public interface Dispatch<T> extends BindingProvider {

    /** Invoke a service operation synchronously.
     *
     * The client is responsible for ensuring that the <code>msg</code> object
     * when marshalled is formed according to the requirements of the protocol
     * binding in use.
     *
     * <p>
     *  当根据使用中的协议绑定的要求形成编组时,客户端负责确保<code> msg </code>对象。
     * 
     * 
     * @param msg An object that will form the message or payload of
     *     the message used to invoke the operation.
     * @return The response message or message payload to the
     *     operation invocation.
     * @throws WebServiceException If a fault occurs during communication with
     *     the service
     * @throws WebServiceException If there is any error in the configuration of
     *     the <code>Dispatch</code> instance
    **/
    public T invoke(T msg);

    /** Invoke a service operation asynchronously.  The
     *  method returns without waiting for the response to the operation
     *  invocation, the results of the operation are obtained by polling the
     *  returned <code>Response</code>.
     * <p>
     * The client is responsible for ensuring that the <code>msg</code> object
     * when marshalled is formed according to the requirements of the protocol
     * binding in use.
     *
     * <p>
     *  方法返回而不等待对操作调用的响应,通过轮询返回的<code> Response </code>获得操作的结果。
     * <p>
     *  当根据使用中的协议绑定的要求形成编组时,客户端负责确保<code> msg </code>对象。
     * 
     * 
     * @param msg An object that will form the message or payload of
     *     the message used to invoke the operation.
     * @return The response message or message payload to the
     *     operation invocation.
     * @throws WebServiceException If there is any error in the configuration of
     *     the <code>Dispatch</code> instance
    **/
    public Response<T> invokeAsync(T msg);

    /** Invoke a service operation asynchronously. The
     *  method returns without waiting for the response to the operation
     *  invocation, the results of the operation are communicated to the client
     *  via the passed in <code>handler</code>.
     * <p>
     * The client is responsible for ensuring that the <code>msg</code> object
     * when marshalled is formed according to the requirements of the protocol
     * binding in use.
     *
     * <p>
     *  方法返回而不等待对操作调用的响应,操作的结果通过在<code>处理程序</code>中传递到客户端。
     * <p>
     *  当根据使用中的协议绑定的要求形成编组时,客户端负责确保<code> msg </code>对象。
     * 
     * 
     * @param msg An object that will form the message or payload of
     *     the message used to invoke the operation.
     * @param handler The handler object that will receive the
     *     response to the operation invocation.
     * @return A <code>Future</code> object that may be used to check the status
     *     of the operation invocation. This object MUST NOT be used to try to
     *     obtain the results of the operation - the object returned from
     *     <code>Future&lt;?>.get()</code> is implementation dependent
     *     and any use of it will result in non-portable behaviour.
     * @throws WebServiceException If there is any error in the configuration of
     *     the <code>Dispatch</code> instance
    **/
    public Future<?> invokeAsync(T msg, AsyncHandler<T> handler);

    /** Invokes a service operation using the one-way
     *  interaction mode. The operation invocation is logically non-blocking,
     *  subject to the capabilities of the underlying protocol, no results
     *  are returned. When
     *  the protocol in use is SOAP/HTTP, this method MUST block until
     *  an HTTP response code has been received or an error occurs.
     * <p>
     * The client is responsible for ensuring that the <code>msg</code> object
     * when marshalled is formed according to the requirements of the protocol
     * binding in use.
     *
     * <p>
     *  交互模式。操作调用在逻辑上是非阻塞的,根据底层协议的能力,不返回结果。当使用的协议是SOAP / HTTP时,此方法必须阻塞,直到接收到HTTP响应代码或发生错误。
     * <p>
     * 
     * @param msg An object that will form the message or payload of
     *     the message used to invoke the operation.
     * @throws WebServiceException If there is any error in the configuration of
     *     the <code>Dispatch</code> instance or if an error occurs during the
     *     invocation.
    **/
    public void invokeOneWay(T msg);
}
