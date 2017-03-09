/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2008, Oracle and/or its affiliates. All rights reserved.
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

package javax.management;


/**
 * <p>Can be implemented by an MBean in order to
 * carry out operations before and after being registered or unregistered from
 * the MBean Server.  An MBean can also implement this interface in order
 * to get a reference to the MBean Server and/or its name within that
 * MBean Server.</p>
 *
 * <p>
 *  <p>可以由MBean实施,以便在从MBean服务器注册或注销之前和之后执行操作。 MBean还可以实现此接口,以获取对MBean Server和/或其在MBean Server中的名称的引用。
 * </p>。
 * 
 * 
 * @since 1.5
 */
public interface MBeanRegistration   {


    /**
     * Allows the MBean to perform any operations it needs before
     * being registered in the MBean Server.  If the name of the MBean
     * is not specified, the MBean can provide a name for its
     * registration.  If any exception is raised, the MBean will not be
     * registered in the MBean Server.
     *
     * <p>
     *  允许MBean在注册到MBean Server之前执行所需的任何操作。如果未指定MBean的名称,则MBean可以为其注册提供名称。
     * 如果引发任何异常,MBean将不会在MBean Server中注册。
     * 
     * 
     * @param server The MBean Server in which the MBean will be registered.
     *
     * @param name The object name of the MBean.  This name is null if
     * the name parameter to one of the <code>createMBean</code> or
     * <code>registerMBean</code> methods in the {@link MBeanServer}
     * interface is null.  In that case, this method must return a
     * non-null ObjectName for the new MBean.
     *
     * @return The name under which the MBean is to be registered.
     * This value must not be null.  If the <code>name</code>
     * parameter is not null, it will usually but not necessarily be
     * the returned value.
     *
     * @exception java.lang.Exception This exception will be caught by
     * the MBean Server and re-thrown as an {@link
     * MBeanRegistrationException}.
     */
    public ObjectName preRegister(MBeanServer server,
                                  ObjectName name) throws java.lang.Exception;

    /**
     * Allows the MBean to perform any operations needed after having been
     * registered in the MBean server or after the registration has failed.
     * <p>If the implementation of this method throws a {@link RuntimeException}
     * or an {@link Error}, the MBean Server will rethrow those inside
     * a {@link RuntimeMBeanException} or {@link RuntimeErrorException},
     * respectively. However, throwing an exception in {@code postRegister}
     * will not change the state of the MBean:
     * if the MBean was already registered ({@code registrationDone} is
     * {@code true}), the MBean will remain registered. </p>
     * <p>This might be confusing for the code calling {@code createMBean()}
     * or {@code registerMBean()}, as such code might assume that MBean
     * registration has failed when such an exception is raised.
     * Therefore it is recommended that implementations of
     * {@code postRegister} do not throw Runtime Exceptions or Errors if it
     * can be avoided.</p>
     * <p>
     * 允许MBean在MBean服务器中注册后或注册失败后执行所需的任何操作。
     *  <p>如果此方法的实现引发{@link RuntimeException}或{@link错误},MBean Server将分别重新抛出{@link RuntimeMBeanException}或{@link RuntimeErrorException}
     * 中的内容。
     * 允许MBean在MBean服务器中注册后或注册失败后执行所需的任何操作。
     * 但是,在{@code postRegister}中抛出异常不会改变MBean的状态：如果MBean已经注册({@code registrationDone}是{@code true}),MBean将保持
     * 注册。
     * 允许MBean在MBean服务器中注册后或注册失败后执行所需的任何操作。
     *  </p> <p>这可能会让调用{@code createMBean()}或{@code registerMBean()}的代码感到困惑,因为这样的代码可能会假设当引发这种异常时MBean注册失败。
     * 因此,建议{@code postRegister}的实现不抛出运行时异常或错误,如果可以避免。</p>。
     * 
     * 
     * @param registrationDone Indicates whether or not the MBean has
     * been successfully registered in the MBean server. The value
     * false means that the registration phase has failed.
     */
    public void postRegister(Boolean registrationDone);

    /**
     * Allows the MBean to perform any operations it needs before
     * being unregistered by the MBean server.
     *
     * <p>
     *  允许MBean在由MBean服务器取消注册之前执行其所需的任何操作。
     * 
     * 
     * @exception java.lang.Exception This exception will be caught by
     * the MBean server and re-thrown as an {@link
     * MBeanRegistrationException}.
     */
    public void preDeregister() throws java.lang.Exception ;

    /**
     * Allows the MBean to perform any operations needed after having been
     * unregistered in the MBean server.
     * <p>If the implementation of this method throws a {@link RuntimeException}
     * or an {@link Error}, the MBean Server will rethrow those inside
     * a {@link RuntimeMBeanException} or {@link RuntimeErrorException},
     * respectively. However, throwing an exception in {@code postDeregister}
     * will not change the state of the MBean:
     * the MBean was already successfully deregistered and will remain so. </p>
     * <p>This might be confusing for the code calling
     * {@code unregisterMBean()}, as it might assume that MBean deregistration
     * has failed. Therefore it is recommended that implementations of
     * {@code postDeregister} do not throw Runtime Exceptions or Errors if it
     * can be avoided.</p>
     * <p>
     * 允许MBean在MBean服务器中取消注册后执行所需的任何操作。
     *  <p>如果此方法的实现引发{@link RuntimeException}或{@link错误},MBean Server将分别重新抛出{@link RuntimeMBeanException}或{@link RuntimeErrorException}
     * 中的内容。
     * 允许MBean在MBean服务器中取消注册后执行所需的任何操作。但是,在{@code postDeregister}中抛出异常不会更改MBean的状态：MBean已成功取消注册,并保持不变。
     */
    public void postDeregister();

 }
