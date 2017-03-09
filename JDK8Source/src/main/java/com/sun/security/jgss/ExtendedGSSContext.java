/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2009, 2013, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.security.jgss;

import org.ietf.jgss.*;

/**
 * The extended GSSContext interface for supporting additional
 * functionalities not defined by {@code org.ietf.jgss.GSSContext},
 * such as querying context-specific attributes.
 * <p>
 *  扩展的GSSContext接口,用于支持未由{@code org.ietf.jgss.GSSContext}定义的其他功能,例如查询特定于上下文的属性。
 * 
 */
@jdk.Exported
public interface ExtendedGSSContext extends GSSContext {
    /**
     * Return the mechanism-specific attribute associated with {@code type}.
     * <br><br>
     * For each supported attribute type, the type for the output are
     * defined below.
     * <ol>
     * <li>{@code KRB5_GET_TKT_FLAGS}:
     * the returned object is a boolean array for the service ticket flags,
     * which is long enough to contain all true bits. This means if
     * the user wants to get the <em>n</em>'th bit but the length of the
     * returned array is less than <em>n</em>, it is regarded as false.
     * <li>{@code KRB5_GET_SESSION_KEY}:
     * the returned object is an instance of {@link java.security.Key},
     * which has the following properties:
     *    <ul>
     *    <li>Algorithm: enctype as a string, where
     *        enctype is defined in RFC 3961, section 8.
     *    <li>Format: "RAW"
     *    <li>Encoded form: the raw key bytes, not in any ASN.1 encoding
     *    </ul>
     * <li>{@code KRB5_GET_AUTHZ_DATA}:
     * the returned object is an array of
     * {@link com.sun.security.jgss.AuthorizationDataEntry}, or null if the
     * optional field is missing in the service ticket.
     * <li>{@code KRB5_GET_AUTHTIME}:
     * the returned object is a String object in the standard KerberosTime
     * format defined in RFC 4120 5.2.3
     * </ol>
     *
     * If there is a security manager, an {@link InquireSecContextPermission}
     * with the name {@code type.mech} must be granted. Otherwise, this could
     * result in a {@link SecurityException}.<p>
     *
     * Example:
     * <pre>
     *      GSSContext ctxt = m.createContext(...)
     *      // Establishing the context
     *      if (ctxt instanceof ExtendedGSSContext) {
     *          ExtendedGSSContext ex = (ExtendedGSSContext)ctxt;
     *          try {
     *              Key key = (key)ex.inquireSecContext(
     *                      InquireType.KRB5_GET_SESSION_KEY);
     *              // read key info
     *          } catch (GSSException gsse) {
     *              // deal with exception
     *          }
     *      }
     * </pre>
     * <p>
     *  返回与{@code type}相关联的机制特定属性。 <br> <br>对于每种受支持的属性类型,输出的类型在下面定义。
     * <ol>
     *  <li> {@ code KRB5_GET_TKT_FLAGS}：返回的对象是服务标记的布尔数组,其长度足以包含所有真位。
     * 这意味着如果用户想要获取<em> n </em>位,但返回的数组的长度小于<em> n </em>,则视为false。
     *  <li> {@ code KRB5_GET_SESSION_KEY}：返回的对象是{@link java.security.Key}的实例,它具有以下属性：。
     * <ul>
     *  <li>算法：enctype为字符串,其中enctype在RFC 3961第8节中定义。<li>格式："RAW"<li>编码形式：原始键字节,不在任何ASN.1编码
     * </ul>
     *  <li> {@ code KRB5_GET_AUTHZ_DATA}：返回的对象是{@link com.sun.security.jgss.AuthorizationDataEntry}的数组,如果服务
     * ticket中缺少可选字段,则返回null。
     *  <li> {@ code KRB5_GET_AUTHTIME}：返回的对象是在RFC 4120中定义的标准KerberosTime格式的String对象5.2.3。
     * </ol>
     * 
     * 如果有安全管理器,则必须授予名称为{@code type.mech}的{@link InquireSecContextPermission}。
     * 否则,这可能会导致{@link SecurityException}。<p>。
     * 
     *  例：
     * <pre>
     *  GSSContext ctxt = m.createContext(...)//建立上下文if(ctxt instanceof ExtendedGSSContext){ExtendedGSSContext ex =(ExtendedGSSContext)ctxt; try {Key key =(key)ex.inquireSecContext(InquireType.KRB5_GET_SESSION_KEY); // read key info}
     *  catch(GSSException gsse){//处理异常}}。
     * </pre>
     * 
     * @param type the type of the attribute requested
     * @return the attribute, see the method documentation for details.
     * @throws GSSException containing  the following
     * major error codes:
     *   {@link GSSException#BAD_MECH GSSException.BAD_MECH} if the mechanism
     *   does not support this method,
     *   {@link GSSException#UNAVAILABLE GSSException.UNAVAILABLE} if the
     *   type specified is not supported,
     *   {@link GSSException#NO_CONTEXT GSSException.NO_CONTEXT} if the
     *   security context is invalid,
     *   {@link GSSException#FAILURE GSSException.FAILURE} for other
     *   unspecified failures.
     * @throws SecurityException if a security manager exists and a proper
     *   {@link InquireSecContextPermission} is not granted.
     * @see InquireSecContextPermission
     */
    public Object inquireSecContext(InquireType type)
            throws GSSException;

    /**
     * Requests that the delegation policy be respected. When a true value is
     * requested, the underlying context would use the delegation policy
     * defined by the environment as a hint to determine whether credentials
     * delegation should be performed. This request can only be made on the
     * context initiator's side and it has to be done prior to the first
     * call to <code>initSecContext</code>.
     * <p>
     * When this flag is false, delegation will only be tried when the
     * {@link GSSContext#requestCredDeleg(boolean) credentials delegation flag}
     * is true.
     * <p>
     * When this flag is true but the
     * {@link GSSContext#requestCredDeleg(boolean) credentials delegation flag}
     * is false, delegation will be only tried if the delegation policy permits
     * delegation.
     * <p>
     * When both this flag and the
     * {@link GSSContext#requestCredDeleg(boolean) credentials delegation flag}
     * are true, delegation will be always tried. However, if the delegation
     * policy does not permit delegation, the value of
     * {@link #getDelegPolicyState} will be false, even
     * if delegation is performed successfully.
     * <p>
     * In any case, if the delegation is not successful, the value returned
     * by {@link GSSContext#getCredDelegState()} is false, and the value
     * returned by {@link #getDelegPolicyState()} is also false.
     * <p>
     * Not all mechanisms support delegation policy. Therefore, the
     * application should check to see if the request was honored with the
     * {@link #getDelegPolicyState() getDelegPolicyState} method. When
     * delegation policy is not supported, <code>requestDelegPolicy</code>
     * should return silently without throwing an exception.
     * <p>
     * Note: for the Kerberos 5 mechanism, the delegation policy is expressed
     * through the OK-AS-DELEGATE flag in the service ticket. When it's true,
     * the KDC permits delegation to the target server. In a cross-realm
     * environment, in order for delegation be permitted, all cross-realm TGTs
     * on the authentication path must also have the OK-AS-DELAGATE flags set.
     * <p>
     *  要求尊重代表团的政策。当请求真值时,底层上下文将使用由环境定义的委托策略作为提示来确定是否应该执行证书委派。
     * 这个请求只能在上下文发起方上进行,并且必须在第一次调用<code> initSecContext </code>之前完成。
     * <p>
     *  当此标志为false时,将仅在{@link GSSContext#requestCredDeleg(boolean)凭证委托标志为真时尝试委派。
     * <p>
     *  当此标记为真,但{@link GSSContext#requestCredDeleg(boolean)凭证授权标志}为假时,将仅在代理策略允许委派时尝试委派。
     * <p>
     * 当此标志和{@link GSSContext#requestCredDeleg(boolean)凭证委托标志}为真时,将始终尝试委派。
     * 但是,如果委派策略不允许委派,则{@link #getDelegPolicyState}的值将为false,即使成功执行委派也是如此。
     * <p>
     *  在任何情况下,如果委派不成功,{@link GSSContext#getCredDelegState()}返回的值为false,{@link #getDelegPolicyState()}返回的值也为
     * 
     * @param state true if the policy should be respected
     * @throws GSSException containing the following
     * major error codes:
     *   {@link GSSException#FAILURE GSSException.FAILURE}
     */
    public void requestDelegPolicy(boolean state) throws GSSException;

    /**
     * Returns the delegation policy response. Called after a security context
     * is established. This method can be only called on the initiator's side.
     * See {@link ExtendedGSSContext#requestDelegPolicy}.
     * <p>
     * false。
     * <p>
     *  并不是所有的机制都支持授权策略。因此,应用程序应检查该请求是否符合{@link #getDelegPolicyState()getDelegPolicyState}方法。
     * 当不支持委派策略时,<code> requestDelegPolicy </code>应该静默返回而不抛出异常。
     * <p>
     *  注意：对于Kerberos 5机制,委派策略通过服务票证中的OK-AS-DELEGATE标志表示。当它为真时,KDC允许委派到目标服务器。
     * 
     * @return the delegation policy response
     */
    public boolean getDelegPolicyState();
}
