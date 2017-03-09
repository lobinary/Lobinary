/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 2004, Oracle and/or its affiliates. All rights reserved.
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

/**
 * An object that captures the explicit state of a request
 * for the Dynamic Skeleton Interface (DSI).  This class, the
 * cornerstone of the DSI, is analogous to the <code>Request</code>
 * object in the DII.
 * <P>
 * The ORB is responsible for creating this embodiment of a request,
 * and delivering it to a Dynamic Implementation Routine (DIR).
 * A dynamic servant (a DIR) is created by implementing the
 * <code>DynamicImplementation</code> class,
 * which has a single <code>invoke</code> method.  This method accepts a
 * <code>ServerRequest</code> object.
 *
 * The abstract class <code>ServerRequest</code> defines
 * methods for accessing the
 * method name, the arguments and the context of the request, as
 * well as methods for setting the result of the request either as a
 * return value or an exception. <p>
 *
 * A subtlety with accessing the arguments of the request is that the
 * DIR needs to provide type information about the
 * expected arguments, since there is no compiled information about
 * these. This information is provided through an <code>NVList</code>,
 * which is a list of <code>NamedValue</code> objects.
 * Each <code>NamedValue</code> object
 * contains an <code>Any</code> object, which in turn
 * has a <code>TypeCode</code> object representing the type
 * of the argument. <p>
 *
 * Similarly, type information needs to be provided for the response,
 * for either the expected result or for an exception, so the methods
 * <code>result</code> and <code>except</code> take an <code>Any</code>
 * object as a parameter. <p>
 *
 * <p>
 *  捕获动态骨架接口(DSI)请求的显式状态的对象。这个类是DSI的基石,类似于DII中的<code> Request </code>对象。
 * <P>
 *  ORB负责创建请求的这个实施例,并将其传递到动态实现例程(DIR)。
 * 通过实现<code> DynamicImplementation </code>类创建动态服务(DIR),该类具有单个<code> invoke </code>方法。
 * 此方法接受一个<code> ServerRequest </code>对象。
 * 
 *  抽象类<code> ServerRequest </code>定义了访问方法名,请求的参数和上下文的方法,以及将请求结果设置为返回值或异常的方法。 <p>
 * 
 *  访问请求参数的一个微妙之处是DIR需要提供关于期望参数的类型信息,因为没有关于这些参数的编译信息。
 * 该信息通过<code> NVList </code>提供,它是<code> NamedValue </code>对象的列表。
 * 每个<code> NamedValue </code>对象包含一个<code> Any </code>对象,该对象又具有表示参数类型的<code> TypeCode </code>对象。 <p>。
 * 
 * 类似地,需要为响应提供类型信息,对于预期结果或异常,所以方法<code> result </code>和<code>(</code>除外) code> object作为参数。 <p>
 * 
 * 
 * @see org.omg.CORBA.DynamicImplementation
 * @see org.omg.CORBA.NVList
 * @see org.omg.CORBA.NamedValue
 *
 */

public abstract class ServerRequest {

    /**
     * Retrieves the name of the operation being
     * invoked. According to OMG IDL's rules, these names must be unique
     * among all operations supported by this object's "most-derived"
     * interface. Note that the operation names for getting and setting
     * attributes are <code>_get_&lt;attribute_name&gt;</code>
     * and <code>_set_&lt;attribute_name&gt;</code>,
     * respectively.
     *
     * <p>
     *  检索要调用的操作的名称。根据OMG IDL的规则,这些名称在该对象的"最多派生"接口支持的所有操作中必须是唯一的。
     * 注意,用于获取和设置属性的操作名称分别是<code> _get_&lt; attribute_name&gt; </code>和<code> _set_&lt; attribute_name&gt; </code>
     * 。
     *  检索要调用的操作的名称。根据OMG IDL的规则,这些名称在该对象的"最多派生"接口支持的所有操作中必须是唯一的。
     * 
     * 
     * @return     the name of the operation to be invoked
     * @deprecated use operation()
     */
    @Deprecated
    public String op_name()
    {
        return operation();
    }


    /**
     * Throws an <code>org.omg.CORBA.NO_IMPLEMENT</code> exception.
     * <P>
     * Retrieves the name of the operation being
     * invoked. According to OMG IDL's rules, these names must be unique
     * among all operations supported by this object's "most-derived"
     * interface. Note that the operation names for getting and setting
     * attributes are <code>_get_&lt;attribute_name&gt;</code>
     * and <code>_set_&lt;attribute_name&gt;</code>,
     * respectively.
     *
     * <p>
     *  引发<code> org.omg.CORBA.NO_IMPLEMENT </code>异常。
     * <P>
     *  检索要调用的操作的名称。根据OMG IDL的规则,这些名称在该对象的"最多派生"接口支持的所有操作中必须是唯一的。
     * 注意,用于获取和设置属性的操作名称分别是<code> _get_&lt; attribute_name&gt; </code>和<code> _set_&lt; attribute_name&gt; </code>
     * 。
     *  检索要调用的操作的名称。根据OMG IDL的规则,这些名称在该对象的"最多派生"接口支持的所有操作中必须是唯一的。
     * 
     * 
     * @return     the name of the operation to be invoked
     * @see <a href="package-summary.html#unimpl"><code>CORBA</code>
     *      package comments for unimplemented features</a>
     */
    public String operation()
    {
        throw new org.omg.CORBA.NO_IMPLEMENT();
    }


    /**
     * Specifies method parameter types and retrieves "in" and "inout"
     * argument values.
     * <P>
     * Note that this method is deprecated; use the method
     * <code>arguments</code> in its place.
     * <P>
     * Unless it calls the method <code>set_exception</code>,
     * the DIR must call this method exactly once, even if the
     * method signature contains no parameters. Once the method <code>
     * arguments</code> or <code>set_exception</code>
     * has been called, calling <code>arguments</code> on the same
     * <code>ServerRequest</code> object
     * will result in a <code>BAD_INV_ORDER</code> system exception.
     * The DIR must pass in to the method <code>arguments</code>
     * an NVList initialized with TypeCodes and Flags
     * describing the parameter types for the operation, in the order in which
     * they appear in the IDL specification (left to right). A
     * potentially-different NVList will be returned from
     * <code>arguments</code>, with the
     * "in" and "inout" argument values supplied. If it does not call
     * the method <code>set_exception</code>,
     * the DIR must supply the returned NVList with return
     * values for any "out" arguments before returning, and may also change
     * the return values for any "inout" arguments.
     *
     * <p>
     *  指定方法参数类型并检索"in"和"inout"参数值。
     * <P>
     *  请注意,此方法已弃用;在其位置使用方法<code> arguments </code>。
     * <P>
     * 除非它调用方法<code> set_exception </code>,DIR必须调用此方法一次,即使方法签名不包含参数。
     * 一旦方法<code> arguments </code>或<code> set_exception </code>被调用,在同一<code> ServerRequest </code>对象上调用<code>
     *  arguments </code>代码> BAD_INV_ORDER </code>系统异常。
     * 除非它调用方法<code> set_exception </code>,DIR必须调用此方法一次,即使方法签名不包含参数。
     *  DIR必须按照它们在IDL规范(从左到右)中出现的顺序,传递到用描述操作的参数类型的TypeCodes和Flags初始化的NVList方法<code> arguments </code>。
     * 将从<code> arguments </code>返回一个潜在不同的NVList,并提供"in"和"inout"参数值。
     * 如果它不调用<code> set_exception </code>方法,则DIR必须在返回之前为返回的任何"out"参数提供返回值的NVList,并且还可以更改任何"inout"参数的返回值。
     * 
     * 
     * @param params            the arguments of the method, in the
     *                          form of an <code>NVList</code> object
     * @deprecated use the method <code>arguments</code>
     */
    @Deprecated
    public void params(NVList params)
    {
        arguments(params);
    }

    /**
     * Specifies method parameter types and retrieves "in" and "inout"
     * argument values.
     * Unless it calls the method <code>set_exception</code>,
     * the DIR must call this method exactly once, even if the
     * method signature contains no parameters. Once the method <code>
     * arguments</code> or <code>set_exception</code>
     * has been called, calling <code>arguments</code> on the same
     * <code>ServerRequest</code> object
     * will result in a <code>BAD_INV_ORDER</code> system exception.
     * The DIR must pass in to the method <code>arguments</code>
     * an NVList initialized with TypeCodes and Flags
     * describing the parameter types for the operation, in the order in which
     * they appear in the IDL specification (left to right). A
     * potentially-different NVList will be returned from
     * <code>arguments</code>, with the
     * "in" and "inout" argument values supplied. If it does not call
     * the method <code>set_exception</code>,
     * the DIR must supply the returned NVList with return
     * values for any "out" arguments before returning, and it may also change
     * the return values for any "inout" arguments.
     *
     * <p>
     * 指定方法参数类型并检索"in"和"inout"参数值。除非它调用方法<code> set_exception </code>,DIR必须调用此方法一次,即使方法签名不包含参数。
     * 一旦方法<code> arguments </code>或<code> set_exception </code>被调用,在同一<code> ServerRequest </code>对象上调用<code>
     *  arguments </code>代码> BAD_INV_ORDER </code>系统异常。
     * 指定方法参数类型并检索"in"和"inout"参数值。除非它调用方法<code> set_exception </code>,DIR必须调用此方法一次,即使方法签名不包含参数。
     *  DIR必须按照它们在IDL规范(从左到右)中出现的顺序,传递到用描述操作的参数类型的TypeCodes和Flags初始化的NVList方法<code> arguments </code>。
     * 将从<code> arguments </code>返回一个潜在不同的NVList,并提供"in"和"inout"参数值。
     * 如果它不调用<code> set_exception </code>方法,DIR必须在返回之前为返回的任何"out"参数返回值提供返回的NVList,并且它还可以更改任何"inout"参数的返回值。
     * 
     * 
     * @param args              the arguments of the method, in the
     *                            form of an NVList
     * @see <a href="package-summary.html#unimpl"><code>CORBA</code>
     *      package comments for unimplemented features</a>
     */
    public void arguments(org.omg.CORBA.NVList args) {
        throw new org.omg.CORBA.NO_IMPLEMENT();
    }



    /**
     * Specifies any return value for the call.
     * <P>
     * Note that this method is deprecated; use the method
     * <code>set_result</code> in its place.
     * <P>
     * Unless the method
     * <code>set_exception</code> is called, if the invoked method
     * has a non-void result type, the method <code>set_result</code>
     * must be called exactly once before the DIR returns.
     * If the operation has a void result type, the method
     * <code>set_result</code> may optionally be
     * called once with an <code>Any</code> object whose type is
     * <code>tk_void</code>. Calling the method <code>set_result</code> before
     * the method <code>arguments</code> has been called or after
     * the method <code>set_result</code> or <code>set_exception</code> has been
     * called will result in a BAD_INV_ORDER exception. Calling the method
     * <code>set_result</code> without having previously called
     * the method <code>ctx</code> when the IDL operation contains a
     * context expression, or when the NVList passed to arguments did not
     * describe all parameters passed by the client, may result in a MARSHAL
     * system exception.
     *
     * <p>
     *  指定调用的任何返回值。
     * <P>
     *  请注意,此方法已弃用;在其位置使用方法<code> set_result </code>。
     * <P>
     * 除非调用方法<code> set_exception </code>,如果被调用的方法具有非void结果类型,则在DIR返回之前必须正好调用<code> set_result </code>方法一次。
     * 如果操作具有void结果类型,则可以可选地使用类型为<code> tk_void </code>的<code> Any </code>对象来调用<code> set_result </code>方法一次
     * 。
     * 在方法<code> arguments </code>被调用之前或方法<code> set_result </code>或<code> set_exception </code>被调用后,调用<code>
     *  set_result </code>导致BAD_INV_ORDER异常。
     * 当IDL操作包含上下文表达式时,或者当传递给参数的NVList没有描述客户端传递的所有参数时,调用方法<code> set_result </code>而没有先前调用方法<code> ctx </code>
     *  ,可能导致MARSHAL系统异常。
     * 
     * 
     * @param any an <code>Any</code> object containing the return value to be set
     * @deprecated use the method <code>set_result</code>
     */
    @Deprecated
    public void result(Any any)
    {
        set_result(any);
    }


    /**
     * Throws an <code>org.omg.CORBA.NO_IMPLEMENT</code> exception.
     * <P>
     * Specifies any return value for the call. Unless the method
     * <code>set_exception</code> is called, if the invoked method
     * has a non-void result type, the method <code>set_result</code>
     * must be called exactly once before the DIR returns.
     * If the operation has a void result type, the method
     * <code>set_result</code> may optionally be
     * called once with an <code>Any</code> object whose type is
     * <code>tk_void</code>. Calling the method <code>set_result</code> before
     * the method <code>arguments</code> has been called or after
     * the method <code>set_result</code> or <code>set_exception</code> has been
     * called will result in a BAD_INV_ORDER exception. Calling the method
     * <code>set_result</code> without having previously called
     * the method <code>ctx</code> when the IDL operation contains a
     * context expression, or when the NVList passed to arguments did not
     * describe all parameters passed by the client, may result in a MARSHAL
     * system exception.
     *
     * <p>
     *  引发<code> org.omg.CORBA.NO_IMPLEMENT </code>异常。
     * <P>
     * 指定调用的任何返回值。
     * 除非调用方法<code> set_exception </code>,如果被调用的方法具有非void结果类型,则在DIR返回之前必须正好调用<code> set_result </code>方法一次。
     * 如果操作具有void结果类型,则可以可选地使用类型为<code> tk_void </code>的<code> Any </code>对象来调用<code> set_result </code>方法一次
     * 。
     * 除非调用方法<code> set_exception </code>,如果被调用的方法具有非void结果类型,则在DIR返回之前必须正好调用<code> set_result </code>方法一次。
     * 在方法<code> arguments </code>被调用之前或方法<code> set_result </code>或<code> set_exception </code>被调用后,调用<code>
     *  set_result </code>导致BAD_INV_ORDER异常。
     * 除非调用方法<code> set_exception </code>,如果被调用的方法具有非void结果类型,则在DIR返回之前必须正好调用<code> set_result </code>方法一次。
     * 当IDL操作包含上下文表达式时,或者当传递给参数的NVList没有描述客户端传递的所有参数时,调用方法<code> set_result </code>而没有先前调用方法<code> ctx </code>
     *  ,可能导致MARSHAL系统异常。
     * 除非调用方法<code> set_exception </code>,如果被调用的方法具有非void结果类型,则在DIR返回之前必须正好调用<code> set_result </code>方法一次。
     * 
     * 
     * @param any an <code>Any</code> object containing the return value to be set
     * @see <a href="package-summary.html#unimpl"><code>CORBA</code>
     *      package comments for unimplemented features</a>
     */
    public void set_result(org.omg.CORBA.Any any)
    {
        throw new org.omg.CORBA.NO_IMPLEMENT();
    }


    /**
     * The DIR may call set_exception at any time to return an exception to the
     * client. The Any passed to set_exception must contain either a system
     * exception or a user exception specified in the raises expression
     * of the invoked operation's IDL definition. Passing in an Any that does
     * not
     * contain an exception will result in a BAD_PARAM system exception. Passing
     * in an unlisted user exception will result in either the DIR receiving a
     * BAD_PARAM system exception or in the client receiving an
     * UNKNOWN_EXCEPTION system exception.
     *
     * <p>
     *  DIR可以在任何时候调用set_exception向客户端返回异常。传递给set_exception的Any必须包含系统异常或在调用操作的IDL定义的raises表达式中指定的用户异常。
     * 传入不包含异常的Any将导致BAD_PARAM系统异常。传递未列出的用户异常将导致DIR接收到BAD_PARAM系统异常或在接收UNKNOWN_EXCEPTION系统异常的客户端中。
     * 
     * 
     * @param any       the <code>Any</code> object containing the exception
     * @deprecated use set_exception()
     */
    @Deprecated
    public void except(Any any)
    {
        set_exception(any);
    }

    /**
     * Throws an <code>org.omg.CORBA.NO_IMPLEMENT</code> exception.
     * <P>
     * Returns the given exception to the client.  This method
     * is invoked by the DIR, which may call it at any time.
     * The <code>Any</code> object  passed to this method must
     * contain either a system
     * exception or one of the user exceptions specified in the
     * invoked operation's IDL definition. Passing in an
     * <code>Any</code> object that does not contain an exception
     * will cause a BAD_PARAM system exception to be thrown. Passing
     * in an unlisted user exception will result in either the DIR receiving a
     * BAD_PARAM system exception or in the client receiving an
     * UNKNOWN_EXCEPTION system exception.
     *
     * <p>
     * 引发<code> org.omg.CORBA.NO_IMPLEMENT </code>异常。
     * <P>
     *  将给定的异常返回给客户端。这个方法由DIR调用,它可以在任何时候调用它。传递给此方法的<code> Any </code>对象必须包含系统异常或在调用操作的IDL定义中指定的用户异常之一。
     * 传入不包含异常的<code>任何</code>对象将导致抛出BAD_PARAM系统异常。
     * 传递未列出的用户异常将导致DIR接收到BAD_PARAM系统异常或在接收UNKNOWN_EXCEPTION系统异常的客户端中。
     * 
     * 
     * @param any       the <code>Any</code> object containing the exception
     * @exception BAD_PARAM if the given <code>Any</code> object does not
     *                      contain an exception or the exception is an
     *                      unlisted user exception
     * @exception UNKNOWN_EXCEPTION if the given exception is an unlisted
     *                              user exception and the DIR did not
     *                              receive a BAD_PARAM exception
     * @see <a href="package-summary.html#unimpl"><code>CORBA</code>
     *      package comments for unimplemented features</a>
     */
    public void set_exception(Any any)
    {
        throw new org.omg.CORBA.NO_IMPLEMENT();
    }

    /**
     * Returns the context information specified in IDL for the operation
     * when the operation is not an attribute access and the operation's IDL
     * definition contains a context expression; otherwise it returns
     * a nil <code>Context</code> reference. Calling the method
     * <code>ctx</code> before the method <code>arguments</code> has
     * been called or after the method <code>ctx</code>,
     * <code>set_result</code>, or <code>set_exception</code>
     * has been called will result in a
     * BAD_INV_ORDER system exception.
     *
     * <p>
     * 
     * @return                  the context object that is to be used
     *                          to resolve any context strings whose
     *                          values need to be sent with the invocation.
     * @exception BAD_INV_ORDER if (1) the method <code>ctx</code> is called
     *                          before the method <code>arguments</code> or
     *                          (2) the method <code>ctx</code> is called
     *                          after calling <code>set_result</code> or
     *                          <code>set_exception</code>
     */
    public abstract Context ctx();

}
