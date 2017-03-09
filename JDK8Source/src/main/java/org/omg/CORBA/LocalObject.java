/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2001, Oracle and/or its affiliates. All rights reserved.
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

package org.omg.CORBA;
import org.omg.CORBA.portable.*;


/**
 * <P>Used as a base class for implementation of a local IDL interface in the
 * Java language mapping.  It is a class which implements all the operations
 * in the <tt>org.omg.CORBA.Object</tt> interface.
 * <P>Local interfaces are implemented by using CORBA::LocalObject
 *  to provide implementations of <code>Object</code> pseudo
 *  operations and any other ORB-specific support mechanisms that are
 *  appropriate for such objects.  Object implementation techniques are
 *  inherently language-mapping specific.  Therefore, the
 *  <code>LocalObject</code> type is not defined in IDL, but is specified
 *  in each language mapping.
 *  <P>Methods that do not apply to local objects throw
 *  an <code>org.omg.CORBA.NO_IMPLEMENT</code> exception with the message,
 *  "This is a locally contrained object."  Attempting to use a
 *  <TT>LocalObject</TT> to create a DII request results in NO_IMPLEMENT
 *  system exception.  Attempting to marshal or stringify a
 *  <TT>LocalObject</TT> results in a MARSHAL system exception.  Narrowing
 *  and widening references to <TT>LocalObjects</TT> must work as for regular
 *  object references.
 *  <P><code>LocalObject</code> is to be used as the base class of locally
 *  constrained objects, such as those in the PortableServer module.
 *  The specification here is based on the CORBA Components
 *  Volume I - orbos/99-07-01<P>
 * <p>
 *  <P>用作在Java语言映射中实现本地IDL接口的基类。它是一个实现<tt> org.omg.CORBA.Object </tt>接口中所有操作的类。
 * 本地接口通过使用CORBA :: LocalObject来实现,以提供<code> Object </code>伪操作的实现以及适用于这些对象的任何其他ORB特定的支持机制。
 * 对象实现技术本质上是语言映射特定的。因此,<code> LocalObject </code>类型未在IDL中定义,而是在每个语言映射中指定。
 *  <P>不适用于本地对象的方法会在消息"这是一个局部约束的对象"中抛出一个<code> org.omg.CORBA.NO_IMPLEMENT </code>异常。
 * 尝试使用<TT> LocalObject </TT>创建DII请求会导致NO_IMPLEMENT系统异常。尝试编组或字符串化<TT> LocalObject </TT>会导致MARSHAL系统异常。
 * 缩小和扩展对<TT> LocalObjects </TT>的引用必须像常规对象引用一样工作。
 *  <P> <code> LocalObject </code>将用作局部约束对象的基类,例如PortableServer模块中的对象。
 * 这里的规范是基于CORBA组件卷I-orbos / 99-07-01 <P>。
 * 
 * 
 * @see <a href="package-summary.html#unimpl"><code>CORBA</code> package
     *      comments for unimplemented features</a>
 */

public class LocalObject implements org.omg.CORBA.Object
{
    private static String reason = "This is a locally constrained object.";

    /**
     * Constructs a default <code>LocalObject</code> instance.
     * <p>
     *  构造默认的<code> LocalObject </code>实例。
     * 
     */
    public LocalObject() {}

    /**
     * <P>Determines whether the two object references are equivalent,
     * so far as the ORB can easily determine. Two object references are equivalent
     * if they are identical. Two distinct object references which in fact refer to
     * the same object are also equivalent. However, ORBs are not required
     * to attempt determination of whether two distinct object references
     * refer to the same object, since such determination could be impractically
     * expensive.
     * <P>Default implementation of the org.omg.CORBA.Object method. <P>
     *
     * <p>
     * <P>确定这两个对象引用是否相等,只要ORB可以轻松确定。如果两个对象引用相同,则它们是等价的。两个不同的对象引用实际上指向相同的对象也是等效的。
     * 然而,ORB不需要尝试确定两个不同的对象引用是否指向相同的对象,因为这样的确定可能是不切实际的昂贵的。 <P> org.omg.CORBA.Object方法的默认实现。 <P>。
     * 
     * 
     * @param that the object reference with which to check for equivalence
     * @return <code>true</code> if this object reference is known to be
     *         equivalent to the given object reference.
     *         Note that <code>false</code> indicates only that the two
     *         object references are distinct, not necessarily that
     *         they reference distinct objects.
     */
    public boolean _is_equivalent(org.omg.CORBA.Object that) {
        return equals(that) ;
    }

    /**
     * Always returns <code>false</code>.
     * This method is the default implementation of the
     * <code>org.omg.CORBA.Object</code> method.<P>
     *
     * <p>
     *  始终返回<code> false </code>。此方法是<code> org.omg.CORBA.Object </code>方法的默认实现。<P>
     * 
     * 
     * @return <code>false</code>
     */
    public boolean _non_existent() {
        return false;
    }

    /**
     * Returns a hash value that is consistent for the
     * lifetime of the object, using the given number as the maximum.
     * This method is the default implementation of the
     * <code>org.omg.CORBA.Object</code> method.<P>
     * <p>
     *  返回与对象的生命周期一致的哈希值,使用给定数作为最大值。此方法是<code> org.omg.CORBA.Object </code>方法的默认实现。<P>
     * 
     * 
     * @param maximum an <code>int</code> identifying maximum value of
     *                  the hashcode
     * @return this instance's hashcode
     */
    public int _hash(int maximum) {
        return hashCode() ;
    }

    /**
     * Throws an <code>org.omg.CORBA.NO_IMPLEMENT</code> exception with
     * the message "This is a locally constrained object."  This method
     * does not apply to local objects and is therefore not implemented.
     * This method is the default implementation of the
     * <code>org.omg.CORBA.Object</code> method.<P>
     *
     * <p>
     *  将抛出一个<code> org.omg.CORBA.NO_IMPLEMENT </code>异常,并显示消息"This is a locally constrained object"。
     * 此方法不适用于本地对象,因此未实现。此方法是<code> org.omg.CORBA.Object </code>方法的默认实现。<P>。
     * 
     * 
     * @param repository_id a <code>String</code>
     * @return NO_IMPLEMENT because this is a locally constrained object
     *      and this method does not apply to local objects
     * @exception NO_IMPLEMENT because this is a locally constrained object
     *      and this method does not apply to local objects
     * @see <a href="package-summary.html#unimpl"><code>CORBA</code> package
     *      comments for unimplemented features</a>
     */
    public boolean _is_a(String repository_id) {
        throw new org.omg.CORBA.NO_IMPLEMENT(reason);
    }

    /**
     * Throws an <code>org.omg.CORBA.NO_IMPLEMENT</code> exception with
     * the message "This is a locally constrained object."
     * This method is the default implementation of the
     * <code>org.omg.CORBA.Object</code> method.<P>
     * <p>
     *  将抛出一个<code> org.omg.CORBA.NO_IMPLEMENT </code>异常,并显示消息"This is a locally constrained object"。
     * 此方法是<code> org.omg.CORBA.Object </code>方法的默认实现。<P>。
     * 
     * 
     * @return a duplicate of this <code>LocalObject</code> instance.
     * @exception NO_IMPLEMENT
     * @see <a href="package-summary.html#unimpl"><code>CORBA</code> package
     *      comments for unimplemented features</a>
     */
    public org.omg.CORBA.Object _duplicate() {
        throw new org.omg.CORBA.NO_IMPLEMENT(reason);
    }

    /**
     * Throws an <code>org.omg.CORBA.NO_IMPLEMENT</code> exception with
     * the message "This is a locally constrained object."
     * This method is the default implementation of the
     * <code>org.omg.CORBA.Object</code> method.<P>
     * <p>
     * 将抛出一个<code> org.omg.CORBA.NO_IMPLEMENT </code>异常,并显示消息"This is a locally constrained object"。
     * 此方法是<code> org.omg.CORBA.Object </code>方法的默认实现。<P>。
     * 
     * 
     * @exception NO_IMPLEMENT
     * @see <a href="package-summary.html#unimpl"><code>CORBA</code> package
     *      comments for unimplemented features</a>
     */
    public void _release() {
        throw new org.omg.CORBA.NO_IMPLEMENT(reason);
    }

    /**
     * Throws an <code>org.omg.CORBA.NO_IMPLEMENT</code> exception with
     * the message "This is a locally constrained object."
     * This method is the default implementation of the
     * <code>org.omg.CORBA.Object</code> method.<P>
     *
     * <p>
     *  将抛出一个<code> org.omg.CORBA.NO_IMPLEMENT </code>异常,并显示消息"This is a locally constrained object"。
     * 此方法是<code> org.omg.CORBA.Object </code>方法的默认实现。<P>。
     * 
     * 
     * @param operation a <code>String</code> giving the name of an operation
     *        to be performed by the request that is returned
     * @return a <code>Request</code> object with the given operation
     * @exception NO_IMPLEMENT
     * @see <a href="package-summary.html#unimpl"><code>CORBA</code> package
     *      comments for unimplemented features</a>
     */
    public Request _request(String operation) {
        throw new org.omg.CORBA.NO_IMPLEMENT(reason);
    }

    /**
     * Throws an <code>org.omg.CORBA.NO_IMPLEMENT</code> exception with
     * the message "This is a locally constrained object."
     * This method is the default implementation of the
     * <code>org.omg.CORBA.Object</code> method.<P>
     *
     * <p>
     *  将抛出一个<code> org.omg.CORBA.NO_IMPLEMENT </code>异常,并显示消息"This is a locally constrained object"。
     * 此方法是<code> org.omg.CORBA.Object </code>方法的默认实现。<P>。
     * 
     * 
     * @param ctx          a <code>Context</code> object containing
     *                     a list of properties
     * @param operation    the <code>String</code> representing the name of the
     *                     method to be invoked
     * @param arg_list     an <code>NVList</code> containing the actual arguments
     *                     to the method being invoked
     * @param result       a <code>NamedValue</code> object to serve as a
     *                     container for the method's return value
     * @return a new <code>Request</code> object initialized with the given
     * arguments
     * @exception NO_IMPLEMENT
     * @see <a href="package-summary.html#unimpl"><code>CORBA</code> package
     *      comments for unimplemented features</a>
     */
    public Request _create_request(Context ctx,
                                   String operation,
                                   NVList arg_list,
                                   NamedValue result) {
        throw new org.omg.CORBA.NO_IMPLEMENT(reason);
    }

    /**
     * Throws an <code>org.omg.CORBA.NO_IMPLEMENT</code> exception with
     * the message "This is a locally constrained object."
     * This method is the default implementation of the
     * <code>org.omg.CORBA.Object</code> method.<P>
     *
     * <p>
     *  将抛出一个<code> org.omg.CORBA.NO_IMPLEMENT </code>异常,并显示消息"This is a locally constrained object"。
     * 此方法是<code> org.omg.CORBA.Object </code>方法的默认实现。<P>。
     * 
     * 
     * @param ctx          a <code>Context</code> object containing
     *                     a list of properties
     * @param operation    the name of the method to be invoked
     * @param arg_list     an <code>NVList</code> containing the actual arguments
     *                     to the method being invoked
     * @param result       a <code>NamedValue</code> object to serve as a
     *                     container for the method's return value
     * @param exceptions   an <code>ExceptionList</code> object containing a
     *                     list of possible exceptions the method can throw
     * @param contexts     a <code>ContextList</code> object containing a list of
     *                     context strings that need to be resolved and sent
     *                     with the
     *                     <code>Request</code> instance
     * @return the new <code>Request</code> object initialized with the given
     * arguments
     * @exception NO_IMPLEMENT
     * @see <a href="package-summary.html#unimpl"><code>CORBA</code> package
     *      comments for unimplemented features</a>
     */
    public Request _create_request(Context ctx,
                                   String operation,
                                   NVList arg_list,
                                   NamedValue result,
                                   ExceptionList exceptions,
                                   ContextList contexts) {
        throw new org.omg.CORBA.NO_IMPLEMENT(reason);
    }

    /**
     * Throws an <code>org.omg.CORBA.NO_IMPLEMENT</code> exception with
     * the message "This is a locally constrained object." This method
     * does not apply to local objects and is therefore not implemented.
     * This method is the default implementation of the
     * <code>org.omg.CORBA.Object</code> method.<P>
     * <p>
     *  将抛出一个<code> org.omg.CORBA.NO_IMPLEMENT </code>异常,并显示消息"This is a locally constrained object"。
     * 此方法不适用于本地对象,因此未实现。此方法是<code> org.omg.CORBA.Object </code>方法的默认实现。<P>。
     * 
     * 
     * @return NO_IMPLEMENT because this is a locally constrained object
     *      and this method does not apply to local objects
     * @exception NO_IMPLEMENT because this is a locally constrained object
     *      and this method does not apply to local objects
     * @see <a href="package-summary.html#unimpl"><code>CORBA</code> package
     *      comments for unimplemented features</a>
     */
    public org.omg.CORBA.Object _get_interface()
    {
        throw new org.omg.CORBA.NO_IMPLEMENT(reason);
    }

    /**
     * Throws an <code>org.omg.CORBA.NO_IMPLEMENT</code> exception with
     * the message "This is a locally constrained object."
     * This method is the default implementation of the
     * <code>org.omg.CORBA.Object</code> method.<P>
     * <p>
     *  将抛出一个<code> org.omg.CORBA.NO_IMPLEMENT </code>异常,并显示消息"This is a locally constrained object"。
     * 此方法是<code> org.omg.CORBA.Object </code>方法的默认实现。<P>。
     * 
     * 
     * @exception NO_IMPLEMENT
     * @see <a href="package-summary.html#unimpl"><code>CORBA</code> package
     *      comments for unimplemented features</a>
     */
    public org.omg.CORBA.Object _get_interface_def()
    {
        // First try to call the delegate implementation class's
        // "Object get_interface_def(..)" method (will work for JDK1.2
        // ORBs).
        // Else call the delegate implementation class's
        // "InterfaceDef get_interface(..)" method using reflection
        // (will work for pre-JDK1.2 ORBs).

        throw new org.omg.CORBA.NO_IMPLEMENT(reason);
    }

    /**
     * Throws an <code>org.omg.CORBA.NO_IMPLEMENT</code> exception with
     * the message "This is a locally constrained object."
     * This method is the default implementation of the
     * <code>org.omg.CORBA.Object</code> method.<P>
     * <p>
     * 将抛出一个<code> org.omg.CORBA.NO_IMPLEMENT </code>异常,并显示消息"This is a locally constrained object"。
     * 此方法是<code> org.omg.CORBA.Object </code>方法的默认实现。<P>。
     * 
     * 
     * @return the ORB instance that created the Delegate contained in this
     * <code>ObjectImpl</code>
     * @exception NO_IMPLEMENT
     * @see <a href="package-summary.html#unimpl"><code>CORBA</code> package
     *      comments for unimplemented features</a>
     */
    public org.omg.CORBA.ORB _orb() {
        throw new org.omg.CORBA.NO_IMPLEMENT(reason);
    }

    /**
     * Throws an <code>org.omg.CORBA.NO_IMPLEMENT</code> exception with
     * the message "This is a locally constrained object." This method
     * does not apply to local objects and is therefore not implemented.
     * This method is the default implementation of the
     * <code>org.omg.CORBA.Object</code> method.<P>
     * <p>
     *  将抛出一个<code> org.omg.CORBA.NO_IMPLEMENT </code>异常,并显示消息"This is a locally constrained object"。
     * 此方法不适用于本地对象,因此未实现。此方法是<code> org.omg.CORBA.Object </code>方法的默认实现。<P>。
     * 
     * 
     * @param policy_type  an <code>int</code>
     * @return NO_IMPLEMENT because this is a locally constrained object
     *      and this method does not apply to local objects
     * @exception NO_IMPLEMENT because this is a locally constrained object
     *      and this method does not apply to local objects
     * @see <a href="package-summary.html#unimpl"><code>CORBA</code> package
     *      comments for unimplemented features</a>
     */
    public org.omg.CORBA.Policy _get_policy(int policy_type) {
        throw new org.omg.CORBA.NO_IMPLEMENT(reason);
    }


    /**
     * Throws an <code>org.omg.CORBA.NO_IMPLEMENT</code> exception with
     * the message "This is a locally constrained object." This method
     * does not apply to local objects and is therefore not implemented.
     * This method is the default implementation of the
     * <code>org.omg.CORBA.Object</code> method.<P>
     * <p>
     *  将抛出一个<code> org.omg.CORBA.NO_IMPLEMENT </code>异常,并显示消息"This is a locally constrained object"。
     * 此方法不适用于本地对象,因此未实现。此方法是<code> org.omg.CORBA.Object </code>方法的默认实现。<P>。
     * 
     * 
     * @exception NO_IMPLEMENT
     * @see <a href="package-summary.html#unimpl"><code>CORBA</code> package
     *      comments for unimplemented features</a>
     */
    public org.omg.CORBA.DomainManager[] _get_domain_managers() {
        throw new org.omg.CORBA.NO_IMPLEMENT(reason);
    }

    /**
     * Throws an <code>org.omg.CORBA.NO_IMPLEMENT</code> exception with
     * the message "This is a locally constrained object." This method
     * does not apply to local objects and is therefore not implemented.
     * This method is the default implementation of the
     * <code>org.omg.CORBA.Object</code> method.
     *
     * <p>
     *  将抛出一个<code> org.omg.CORBA.NO_IMPLEMENT </code>异常,并显示消息"This is a locally constrained object"。
     * 此方法不适用于本地对象,因此未实现。这个方法是<code> org.omg.CORBA.Object </code>方法的默认实现。
     * 
     * 
     * @param policies an array
     * @param set_add a flag
     * @return NO_IMPLEMENT because this is a locally constrained object
     *      and this method does not apply to local objects
     * @exception NO_IMPLEMENT because this is a locally constrained object
     *      and this method does not apply to local objects
     * @see <a href="package-summary.html#unimpl"><code>CORBA</code> package
     *      comments for unimplemented features</a>
     */
    public org.omg.CORBA.Object
        _set_policy_override(org.omg.CORBA.Policy[] policies,
                             org.omg.CORBA.SetOverrideType set_add) {
        throw new org.omg.CORBA.NO_IMPLEMENT(reason);
    }


    /**
     * Throws an <code>org.omg.CORBA.NO_IMPLEMENT</code> exception with
     * the message "This is a locally constrained object."
     * This method is the default implementation of the
     * <code>org.omg.CORBA.Object</code> method.<P>
     * Returns <code>true</code> for this <code>LocalObject</code> instance.<P>
     * <p>
     *  将抛出一个<code> org.omg.CORBA.NO_IMPLEMENT </code>异常,并显示消息"This is a locally constrained object"。
     * 这个方法是<code> org.omg.CORBA.Object </code>方法的默认实现。
     * <P>为<code> LocalObject </code>实例返回<code> true </code> >。
     * 
     * 
     * @return <code>true</code> always
     * @exception NO_IMPLEMENT
     * @see <a href="package-summary.html#unimpl"><code>CORBA</code> package
     *      comments for unimplemented features</a>
     */
    public boolean _is_local() {
        throw new org.omg.CORBA.NO_IMPLEMENT(reason);
    }

    /**
     * Throws an <code>org.omg.CORBA.NO_IMPLEMENT</code> exception with
     * the message "This is a locally constrained object."
     * This method is the default implementation of the
     * <code>org.omg.CORBA.Object</code> method.<P>
     * <p>
     * 将抛出一个<code> org.omg.CORBA.NO_IMPLEMENT </code>异常,并显示消息"This is a locally constrained object"。
     * 此方法是<code> org.omg.CORBA.Object </code>方法的默认实现。<P>。
     * 
     * 
     * @param operation a <code>String</code> indicating which operation
     *                  to preinvoke
     * @param expectedType the class of the type of operation mentioned above
     * @return NO_IMPLEMENT because this is a locally constrained object
     *      and this method does not apply to local objects
     * @exception NO_IMPLEMENT because this is a locally constrained object
     *      and this method does not apply to local object
     * @see <a href="package-summary.html#unimpl"><code>CORBA</code> package
     *      comments for unimplemented features</a>
     */
    public ServantObject _servant_preinvoke(String operation,
                                            Class expectedType) {
        throw new org.omg.CORBA.NO_IMPLEMENT(reason);
    }

    /**
     * Throws an <code>org.omg.CORBA.NO_IMPLEMENT</code> exception with
     * the message "This is a locally constrained object."
     * This method is the default implementation of the
     * <code>org.omg.CORBA.Object</code> method.<P>
     * <p>
     *  将抛出一个<code> org.omg.CORBA.NO_IMPLEMENT </code>异常,并显示消息"This is a locally constrained object"。
     * 此方法是<code> org.omg.CORBA.Object </code>方法的默认实现。<P>。
     * 
     * 
     * @param servant the servant object on which to post-invoke
     * @exception NO_IMPLEMENT
     * @see <a href="package-summary.html#unimpl"><code>CORBA</code> package
     *      comments for unimplemented features</a>
     */
    public void _servant_postinvoke(ServantObject servant) {
        throw new org.omg.CORBA.NO_IMPLEMENT(reason);
    }

    /*
     * The following methods were added by orbos/98-04-03: Java to IDL
     * Mapping. These are used by RMI over IIOP.
     * <p>
     *  以下方法由orbos / 98-04-03：Java添加到IDL映射。这些被RMI用于IIOP。
     * 
     */

    /**
     * Throws an <code>org.omg.CORBA.NO_IMPLEMENT</code> exception with
     * the message "This is a locally constrained object."
     * This method is the default implementation of the
     * <code>org.omg.CORBA.Object</code> method.
     * <P>Called by a stub to obtain an OutputStream for
     * marshaling arguments. The stub must supply the operation name,
     * and indicate if a response is expected (i.e is this a oneway
     * call).<P>
     * <p>
     *  将抛出一个<code> org.omg.CORBA.NO_IMPLEMENT </code>异常,并显示消息"This is a locally constrained object"。
     * 这个方法是<code> org.omg.CORBA.Object </code>方法的默认实现。 <P>由存根调用以获取用于封送参数的OutputStream。
     * 存根必须提供操作名称,并指示是否期望响应(即这是单向呼叫)。<P>。
     * 
     * 
     * @param operation the name of the operation being requested
     * @param responseExpected <code>true</code> if a response is expected,
     *                         <code>false</code> if it is a one-way call
     * @return NO_IMPLEMENT because this is a locally constrained object
     *      and this method does not apply to local objects
     * @exception NO_IMPLEMENT because this is a locally constrained object
     *      and this method does not apply to local objects
     * @see <a href="package-summary.html#unimpl"><code>CORBA</code> package
     *      comments for unimplemented features</a>
     */
    public OutputStream _request(String operation,
                                 boolean responseExpected) {
        throw new org.omg.CORBA.NO_IMPLEMENT(reason);
    }

    /**
     * Throws an <code>org.omg.CORBA.NO_IMPLEMENT</code> exception with
     * the message "This is a locally constrained object."
     * This method is the default implementation of the
     * <code>org.omg.CORBA.Object</code> method.
     * <P>Called to invoke an operation. The stub provides an
     * <code>OutputStream</code> that was previously returned by a
     * <code>_request()</code>
     * call. <code>_invoke</code> returns an <code>InputStream</code> which
     * contains the
     * marshaled reply. If an exception occurs, <code>_invoke</code> may throw an
     * <code>ApplicationException</code> object which contains an
     * <code>InputStream</code> from
     * which the user exception state may be unmarshaled.<P>
     * <p>
     * 将抛出一个<code> org.omg.CORBA.NO_IMPLEMENT </code>异常,并显示消息"This is a locally constrained object"。
     * 这个方法是<code> org.omg.CORBA.Object </code>方法的默认实现。 <P>调用调用操作。
     * 存根提供了之前由<code> _request()</code>调用返回的<code> OutputStream </code>。
     *  <code> _invoke </code>返回一个包含被封送回复的<code> InputStream </code>。
     * 如果发生异常,<code> _invoke </code>可能会抛出一个<code> ApplicationException </code>对象,该对象包含一个可以被解除用户异常状态的<code> I
     * nputStream </code>。
     *  <code> _invoke </code>返回一个包含被封送回复的<code> InputStream </code>。
     * 
     * 
     * @param output the <code>OutputStream</code> to invoke
     * @return NO_IMPLEMENT because this is a locally constrained object
     *      and this method does not apply to local objects
     * @throws ApplicationException If an exception occurs,
     * <code>_invoke</code> may throw an
     * <code>ApplicationException</code> object which contains
     * an <code>InputStream</code> from
     * which the user exception state may be unmarshaled.
     * @throws RemarshalException If an exception occurs,
     * <code>_invoke</code> may throw an
     * <code>ApplicationException</code> object which contains
     * an <code>InputStream</code> from
     * which the user exception state may be unmarshaled.
     * @exception NO_IMPLEMENT because this is a locally constrained object
     *      and this method does not apply to local objects
     * @see <a href="package-summary.html#unimpl"><code>CORBA</code> package
     *      comments for unimplemented features</a>
     */
    public InputStream _invoke(OutputStream output)
        throws ApplicationException, RemarshalException
    {
        throw new org.omg.CORBA.NO_IMPLEMENT(reason);
    }

    /**
     * Throws an <code>org.omg.CORBA.NO_IMPLEMENT</code> exception with
     * the message "This is a locally constrained object."
     * This method is the default implementation of the
     * <code>org.omg.CORBA.Object</code> method.
     * <P>May optionally be called by a stub to release a
     * reply stream back to the ORB when the unmarshaling has
     * completed. The stub passes the <code>InputStream</code> returned by
     * <code>_invoke()</code> or
     * <code>ApplicationException.getInputStream()</code>.
     * A null
     * value may also be passed to <code>_releaseReply</code>, in which case the
     * method is a no-op.<P>
     * <p>
     *  将抛出一个<code> org.omg.CORBA.NO_IMPLEMENT </code>异常,并显示消息"This is a locally constrained object"。
     * 这个方法是<code> org.omg.CORBA.Object </code>方法的默认实现。 <P>可选地可以由存根调用,以在取消编序完成时将回复流释放回ORB。
     * 存根传递<code> _invoke()</code>或<code> ApplicationException.getInputStream()</code>返回的<code> InputStream 
     * </code>。
     * 这个方法是<code> org.omg.CORBA.Object </code>方法的默认实现。 <P>可选地可以由存根调用,以在取消编序完成时将回复流释放回ORB。
     * 空值也可以传递给<code> _releaseReply </code>,在这种情况下,该方法是无操作。<P>。
     * 
     * @param input the reply stream back to the ORB or null
     * @exception NO_IMPLEMENT
     * @see <a href="package-summary.html#unimpl"><code>CORBA</code> package
     *      comments for unimplemented features</a>
     */
    public void _releaseReply(InputStream input) {
        throw new org.omg.CORBA.NO_IMPLEMENT(reason);
    }

    /**
     * Throws an <code>org.omg.CORBA.NO_IMPLEMENT</code> exception with
     * the message "This is a locally constrained object." This method
     * does not apply to local objects and is therefore not implemented.
     * This method is the default implementation of the
     * <code>org.omg.CORBA.Object</code> method.<P>
     * <p>
     * 
     * 
     * @return NO_IMPLEMENT because this is a locally constrained object
     *      and this method does not apply to local objects
     * @exception NO_IMPLEMENT because this is a locally constrained object
     *      and this method does not apply to local objects
     * @see <a href="package-summary.html#unimpl"><code>CORBA</code> package
     *      comments for unimplemented features</a>
     */

    public boolean validate_connection() {
        throw new org.omg.CORBA.NO_IMPLEMENT(reason);
    }
}
