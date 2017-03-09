/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 1999, Oracle and/or its affiliates. All rights reserved.
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
 * An object containing the information necessary for
 * invoking a method.  This class is
 * the cornerstone of the ORB Dynamic
 * Invocation Interface (DII), which allows dynamic creation and
 * invocation of requests.
 * A server cannot tell the difference between a client
 * invocation using a client stub and a request using the DII.
 * <P>
 * A <code>Request</code> object consists of:
 * <UL>
 * <LI>the name of the operation to be invoked
 * <LI>an <code>NVList</code> containing arguments for the operation.<BR>
 * Each item in the list is a <code>NamedValue</code> object, which has three
 * parts:
 *  <OL>
 *    <LI>the name of the argument
 *    <LI>the value of the argument (as an <code>Any</code> object)
 *    <LI>the argument mode flag indicating whether the argument is
 *        for input, output, or both
 *  </OL>
 * </UL>
 * <P>
 * <code>Request</code> objects may also contain additional information,
 * depending on how an operation was defined in the original IDL
 * interface definition.  For example, where appropriate, they may contain
 * a <code>NamedValue</code> object to hold the return value or exception,
 * a context, a list of possible exceptions, and a list of
 * context strings that need to be resolved.
 * <P>
 * New <code>Request</code> objects are created using one of the
 * <code>create_request</code> methods in the <code>Object</code> class.
 * In other words, a <code>create_request</code> method is performed on the
 * object which is to be invoked.
 *
 * <p>
 *  包含调用方法所需的信息的对象。这个类是ORB动态调用接口(DII)的基石,它允许动态创建和调用请求。服务器无法区分使用客户端存根的客户端调用和使用DII的请求之间的区别。
 * <P>
 *  <code>请求</code>对象包括：
 * <UL>
 *  <LI>要调用的操作的名称<LI>包含操作参数的<code> NVList </code> <BR>列表中的每个项目都是一个<code> NamedValue </code>有三个部分：
 * <OL>
 *  <LI>参数的名称<LI>参数的值(作为<code> Any </code>对象)<LI>参数模式标志,表示参数是输入,输出还是两者
 * </OL>
 * </UL>
 * <P>
 *  <code>请求</code>对象还可能包含附加信息,这取决于在原始IDL接口定义中如何定义操作。
 * 例如,在适当的情况下,它们可以包含用于保存返回值或异常的<code> NamedValue </code>对象,上下文,可能异常的列表以及需要解析的上下文字符串的列表。
 * <P>
 *  使用<code> Object </code>类中的<code> create_request </code>方法之一创建新的<code> Request </code>对象。
 * 换句话说,对要调用的对象执行<code> create_request </code>方法。
 * 
 * 
 * @see org.omg.CORBA.NamedValue
 *
 */

public abstract class Request {

    /**
     * Retrieves the the target object reference.
     *
     * <p>
     * 检索目标对象引用。
     * 
     * 
     * @return                  the object reference that points to the
     *                    object implementation for the method
     *                    to be invoked
     */

    public abstract org.omg.CORBA.Object target();

    /**
     * Retrieves the name of the method to be invoked.
     *
     * <p>
     *  获取要调用的方法的名称。
     * 
     * 
     * @return                  the name of the method to be invoked
     */

    public abstract String operation();

    /**
     * Retrieves the <code>NVList</code> object containing the arguments
     * to the method being invoked.  The elements in the list are
     * <code>NamedValue</code> objects, with each one describing an argument
     * to the method.
     *
     * <p>
     *  检索包含要调用的方法的参数的<code> NVList </code>对象。列表中的元素是<code> NamedValue </code>对象,每个对象描述该方法的参数。
     * 
     * 
     * @return  the <code>NVList</code> object containing the arguments
     *                  for the method
     *
     */

    public abstract NVList arguments();

    /**
     * Retrieves the <code>NamedValue</code> object containing the return
     * value for the method.
     *
     * <p>
     *  检索包含方法的返回值的<code> NamedValue </code>对象。
     * 
     * 
     * @return          the <code>NamedValue</code> object containing the result
     *                          of the method
     */

    public abstract NamedValue result();

    /**
     * Retrieves the <code>Environment</code> object for this request.
     * It contains the exception that the method being invoked has
     * thrown (after the invocation returns).
     *
     *
     * <p>
     *  检索此请求的<code> Environment </code>对象。它包含被调用的方法已抛出的异常(在调用返回之后)。
     * 
     * 
     * @return  the <code>Environment</code> object for this request
     */

    public abstract Environment env();

    /**
     * Retrieves the <code>ExceptionList</code> object for this request.
     * This list contains <code>TypeCode</code> objects describing the
     * exceptions that may be thrown by the method being invoked.
     *
     * <p>
     *  检索此请求的<code> ExceptionList </code>对象。此列表包含描述被调用的方法可能抛出的异常的<code> TypeCode </code>对象。
     * 
     * 
     * @return  the <code>ExceptionList</code> object describing the exceptions
     *            that may be thrown by the method being invoked
     */

    public abstract ExceptionList exceptions();

    /**
     * Retrieves the <code>ContextList</code> object for this request.
     * This list contains context <code>String</code>s that need to
     * be resolved and sent with the invocation.
     *
     *
     * <p>
     *  检索此请求的<code> ContextList </code>对象。此列表包含需要解析并与调用一起发送的上下文<code> String </code>。
     * 
     * 
     * @return                  the list of context strings whose values
     *                          need to be resolved and sent with the
     *                          invocation.
     */

    public abstract ContextList contexts();

    /**
     * Retrieves the <code>Context</code> object for this request.
     * This is a list of properties giving information about the
     * client, the environment, or the circumstances of this request.
     *
     * <p>
     *  检索此请求的<code> Context </code>对象。这是一个属性列表,提供有关客户端,环境或此请求的情况的信息。
     * 
     * 
     * @return          the <code>Context</code> object that is to be used
     *                          to resolve any context strings whose
     *                          values need to be sent with the invocation
     */

    public abstract Context ctx();

    /**
     * Sets this request's <code>Context</code> object to the one given.
     *
     * <p>
     *  将此请求的<code> Context </code>对象设置为给定的对象。
     * 
     * 
     * @param c         the new <code>Context</code> object to be used for
     *                          resolving context strings
     */

    public abstract void ctx(Context c);


    /**
     * Creates an input argument and adds it to this <code>Request</code>
     * object.
     *
     * <p>
     *  创建输入参数并将其添加到此<code> Request </code>对象。
     * 
     * 
     * @return          an <code>Any</code> object that contains the
     *                value and typecode for the input argument added
     */

    public abstract Any add_in_arg();

    /**
     * Creates an input argument with the given name and adds it to
     * this <code>Request</code> object.
     *
     * <p>
     *  创建具有给定名称的输入参数,并将其添加到此<code> Request </code>对象。
     * 
     * 
     * @param name              the name of the argument being added
     * @return          an <code>Any</code> object that contains the
     *                value and typecode for the input argument added
     */

    public abstract Any add_named_in_arg(String name);

    /**
     * Adds an input/output argument to this <code>Request</code> object.
     *
     * <p>
     *  向此<code> Request </code>对象添加输入/输出参数。
     * 
     * 
     * @return          an <code>Any</code> object that contains the
     *                value and typecode for the input/output argument added
     */

    public abstract Any add_inout_arg();

    /**
     * Adds an input/output argument with the given name to this
     * <code>Request</code> object.
     *
     * <p>
     *  将具有给定名称的输入/输出参数添加到此<code> Request </code>对象。
     * 
     * 
     * @param name              the name of the argument being added
     * @return          an <code>Any</code> object that contains the
     *                value and typecode for the input/output argument added
     */

    public abstract Any add_named_inout_arg(String name);


    /**
     * Adds an output argument to this <code>Request</code> object.
     *
     * <p>
     * 向此<code> Request </code>对象添加输出参数。
     * 
     * 
     * @return          an <code>Any</code> object that contains the
     *                value and typecode for the output argument added
     */

    public abstract Any add_out_arg();

    /**
     * Adds an output argument with the given name to this
     * <code>Request</code> object.
     *
     * <p>
     *  将具有给定名称的输出参数添加到此<code> Request </code>对象。
     * 
     * 
     * @param name              the name of the argument being added
     * @return          an <code>Any</code> object that contains the
     *                value and typecode for the output argument added
     */

    public abstract Any add_named_out_arg(String name);

    /**
     * Sets the typecode for the return
     * value of the method.
     *
     * <p>
     *  设置方法的返回值的类型代码。
     * 
     * 
     * @param tc                        the <code>TypeCode</code> object containing type information
     *                   for the return value
     */

    public abstract void set_return_type(TypeCode tc);

    /**
     * Returns the <code>Any</code> object that contains the value for the
     * result of the method.
     *
     * <p>
     *  返回包含方法结果值的<code> Any </code>对象。
     * 
     * 
     * @return                  an <code>Any</code> object containing the value and
     *                   typecode for the return value
     */

    public abstract Any return_value();

    /**
     * Makes a synchronous invocation using the
     * information in the <code>Request</code> object. Exception information is
     * placed into the <code>Request</code> object's environment object.
     * <p>
     *  使用<code> Request </code>对象中的信息进行同步调用。异常信息放在<code> Request </code>对象的环境对象中。
     * 
     */

    public abstract void invoke();

    /**
     * Makes a oneway invocation on the
     * request. In other words, it does not expect or wait for a
     * response. Note that this can be used even if the operation was
     * not declared as oneway in the IDL declaration. No response or
     * exception information is returned.
     * <p>
     *  对请求进行单向调用。换句话说,它不期望或等待响应。请注意,即使在IDL声明中未将操作声明为单向,也可以使用此选项。不返回响应或异常信息。
     * 
     */

    public abstract void send_oneway();

    /**
     * Makes an asynchronous invocation on
     * the request. In other words, it does not wait for a response before it
     * returns to the user. The user can then later use the methods
     * <code>poll_response</code> and <code>get_response</code> to get
     * the result or exception information for the invocation.
     * <p>
     *  对请求进行异步调用。换句话说,它不会在返回给用户之前等待响应。
     * 然后,用户可以稍后使用方法<code> poll_response </code>和<code> get_response </code>来获取调用的结果或异常信息。
     * 
     */

    public abstract void send_deferred();

    /**
     * Allows the user to determine
     * whether a response has been received for the invocation triggered
     * earlier with the <code>send_deferred</code> method.
     *
     * <p>
     *  允许用户确定是否已收到早期使用<code> send_deferred </code>方法触发的调用的响应。
     * 
     * 
     * @return          <code>true</code> if the method response has
     *                          been received; <code>false</code> otherwise
     */

    public abstract boolean poll_response();

    /**
     * Allows the user to access the
     * response for the invocation triggered earlier with the
     * <code>send_deferred</code> method.
     *
     * <p>
     *  允许用户使用<code> send_deferred </code>方法访问先前触发的调用的响应。
     * 
     * @exception WrongTransaction  if the method <code>get_response</code> was invoked
     * from a different transaction's scope than the one from which the
     * request was originally sent. See the OMG Transaction Service specification
     * for details.
     */

    public abstract void get_response() throws WrongTransaction;

};
