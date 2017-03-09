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

package javax.management;

/**
 * <p>An MBean whose management interface is determined by reflection
 * on a Java interface, and that emits notifications.</p>
 *
 * <p>The following example shows how to use the public constructor
 * {@link #StandardEmitterMBean(Object, Class, NotificationEmitter)
 * StandardEmitterMBean(implementation, mbeanInterface, emitter)} to
 * create an MBean emitting notifications with any
 * implementation class name <i>Impl</i>, with a management
 * interface defined (as for current Standard MBeans) by any interface
 * <i>Intf</i>, and with any implementation of the interface
 * {@link NotificationEmitter}. The example uses the class
 * {@link NotificationBroadcasterSupport} as an implementation
 * of the interface {@link NotificationEmitter}.</p>
 *
 *     <pre>
 *     MBeanServer mbs;
 *     ...
 *     final String[] types = new String[] {"sun.disc.space","sun.disc.alarm"};
 *     final MBeanNotificationInfo info = new MBeanNotificationInfo(
 *                                          types,
 *                                          Notification.class.getName(),
 *                                          "Notification about disc info.");
 *     final NotificationEmitter emitter =
 *                    new NotificationBroadcasterSupport(info);
 *
 *     final Intf impl = new Impl(...);
 *     final Object mbean = new StandardEmitterMBean(
 *                                     impl, Intf.class, emitter);
 *     mbs.registerMBean(mbean, objectName);
 *     </pre>
 *
 * <p>
 *  <p> MBean,其管理接口由Java接口上的反射确定,并发出通知。</p>
 * 
 *  <p>以下示例显示如何使用公共构造函数{@link #StandardEmitterMBean(Object,Class,NotificationEmitter)StandardEmitterMBean(implementation,mbeanInterface,emitter)}
 * 创建一个MBean,发出任何实现类名称的通知<i> Impl < / i>,具有由任何接口Intf </i>定义的管理接口(对于当前标准MBeans),以及具有接口{@link NotificationEmitter}
 * 的任何实现。
 * 该示例使用{@link NotificationBroadcasterSupport}类作为接口{@link NotificationEmitter}的实现。</p>。
 * 
 * <pre>
 *  MBeanServer mbs; ... final String [] types = new String [] {"sun.disc.space","sun.disc.alarm"}; fina
 * l MBeanNotificationInfo info = new MBeanNotificationInfo(types,Notification.class.getName(),"关于磁盘信息的通
 * 知。
 *  final NotificationEmitter emitter = new NotificationBroadcasterSupport(info);。
 * 
 *  final Intf impl = new Impl(...); final object mbean = new StandardEmitterMBean(impl,Intf.class,emitt
 * er); mbs.registerMBean(mbean,objectName);。
 * </pre>
 * 
 * 
 * @see StandardMBean
 *
 * @since 1.6
 */
public class StandardEmitterMBean extends StandardMBean
        implements NotificationEmitter {

    private static final MBeanNotificationInfo[] NO_NOTIFICATION_INFO =
        new MBeanNotificationInfo[0];

    private final NotificationEmitter emitter;
    private final MBeanNotificationInfo[] notificationInfo;

    /**
     * <p>Make an MBean whose management interface is specified by
     * {@code mbeanInterface}, with the given implementation and
     * where notifications are handled by the given {@code NotificationEmitter}.
     * The resultant MBean implements the {@code NotificationEmitter} interface
     * by forwarding its methods to {@code emitter}.  It is legal and useful
     * for {@code implementation} and {@code emitter} to be the same object.</p>
     *
     * <p>If {@code emitter} is an instance of {@code
     * NotificationBroadcasterSupport} then the MBean's {@link #sendNotification
     * sendNotification} method will call {@code emitter.}{@link
     * NotificationBroadcasterSupport#sendNotification sendNotification}.</p>
     *
     * <p>The array returned by {@link #getNotificationInfo()} on the
     * new MBean is a copy of the array returned by
     * {@code emitter.}{@link NotificationBroadcaster#getNotificationInfo
     * getNotificationInfo()} at the time of construction.  If the array
     * returned by {@code emitter.getNotificationInfo()} later changes,
     * that will have no effect on this object's
     * {@code getNotificationInfo()}.</p>
     *
     * <p>
     * <p>创建一个MBean,其管理接口由{@code mbeanInterface}指定,使用给定的实现,通知由给定的{@code NotificationEmitter}处理。
     * 生成的MBean通过将其方法转发到{@code emitter}来实现{@code NotificationEmitter}接口。
     *  {@code implementation}和{@code emitter}是同一个对象,这是合法有用的。</p>。
     * 
     *  <p>如果{@code emitter}是{@code NotificationBroadcasterSupport}的实例,那么MBean的{@link #sendNotification sendNotification}
     * 方法将调用{@code emitter.}{@link NotificationBroadcasterSupport#sendNotification sendNotification}。
     * </p>。
     * 
     *  <p> {@link #getNotificationInfo()}在新MBean上返回的数组是在构建时由{@code emitter.}{@link NotificationBroadcaster#getNotificationInfo getNotificationInfo()}
     * 返回的数组的副本。
     * 如果{@code emitter.getNotificationInfo()}返回的数组稍后发生变化,那么对该对象的{@code getNotificationInfo()}没有影响。</p>。
     * 
     * 
     * @param implementation the implementation of the MBean interface.
     * @param mbeanInterface a Standard MBean interface.
     * @param emitter the object that will handle notifications.
     *
     * @throws IllegalArgumentException if the {@code mbeanInterface}
     *    does not follow JMX design patterns for Management Interfaces, or
     *    if the given {@code implementation} does not implement the
     *    specified interface, or if {@code emitter} is null.
     */
    public <T> StandardEmitterMBean(T implementation, Class<T> mbeanInterface,
                                    NotificationEmitter emitter) {
        this(implementation, mbeanInterface, false, emitter);
    }

    /**
     * <p>Make an MBean whose management interface is specified by
     * {@code mbeanInterface}, with the given implementation and where
     * notifications are handled by the given {@code
     * NotificationEmitter}.  This constructor can be used to make
     * either Standard MBeans or MXBeans.  The resultant MBean
     * implements the {@code NotificationEmitter} interface by
     * forwarding its methods to {@code emitter}.  It is legal and
     * useful for {@code implementation} and {@code emitter} to be the
     * same object.</p>
     *
     * <p>If {@code emitter} is an instance of {@code
     * NotificationBroadcasterSupport} then the MBean's {@link #sendNotification
     * sendNotification} method will call {@code emitter.}{@link
     * NotificationBroadcasterSupport#sendNotification sendNotification}.</p>
     *
     * <p>The array returned by {@link #getNotificationInfo()} on the
     * new MBean is a copy of the array returned by
     * {@code emitter.}{@link NotificationBroadcaster#getNotificationInfo
     * getNotificationInfo()} at the time of construction.  If the array
     * returned by {@code emitter.getNotificationInfo()} later changes,
     * that will have no effect on this object's
     * {@code getNotificationInfo()}.</p>
     *
     * <p>
     * <p>创建一个MBean,其管理接口由{@code mbeanInterface}指定,使用给定的实现,通知由给定的{@code NotificationEmitter}处理。
     * 此构造函数可用于制作标准MBeans或MXBeans。生成的MBean通过将其方法转发到{@code emitter}来实现{@code NotificationEmitter}接口。
     *  {@code implementation}和{@code emitter}是同一个对象,这是合法有用的。</p>。
     * 
     *  <p>如果{@code emitter}是{@code NotificationBroadcasterSupport}的实例,那么MBean的{@link #sendNotification sendNotification}
     * 方法将调用{@code emitter.}{@link NotificationBroadcasterSupport#sendNotification sendNotification}。
     * </p>。
     * 
     *  <p> {@link #getNotificationInfo()}在新MBean上返回的数组是在构建时由{@code emitter.}{@link NotificationBroadcaster#getNotificationInfo getNotificationInfo()}
     * 返回的数组的副本。
     * 如果{@code emitter.getNotificationInfo()}返回的数组稍后发生变化,那么对该对象的{@code getNotificationInfo()}没有影响。</p>。
     * 
     * 
     * @param implementation the implementation of the MBean interface.
     * @param mbeanInterface a Standard MBean interface.
     * @param isMXBean If true, the {@code mbeanInterface} parameter
     * names an MXBean interface and the resultant MBean is an MXBean.
     * @param emitter the object that will handle notifications.
     *
     * @throws IllegalArgumentException if the {@code mbeanInterface}
     *    does not follow JMX design patterns for Management Interfaces, or
     *    if the given {@code implementation} does not implement the
     *    specified interface, or if {@code emitter} is null.
     */
    public <T> StandardEmitterMBean(T implementation, Class<T> mbeanInterface,
                                    boolean isMXBean,
                                    NotificationEmitter emitter) {
        super(implementation, mbeanInterface, isMXBean);
        if (emitter == null)
            throw new IllegalArgumentException("Null emitter");
        this.emitter = emitter;
        MBeanNotificationInfo[] infos = emitter.getNotificationInfo();
        if (infos == null || infos.length == 0) {
            this.notificationInfo = NO_NOTIFICATION_INFO;
        } else {
            this.notificationInfo = infos.clone();
        }
    }

    /**
     * <p>Make an MBean whose management interface is specified by
     * {@code mbeanInterface}, and
     * where notifications are handled by the given {@code NotificationEmitter}.
     * The resultant MBean implements the {@code NotificationEmitter} interface
     * by forwarding its methods to {@code emitter}.</p>
     *
     * <p>If {@code emitter} is an instance of {@code
     * NotificationBroadcasterSupport} then the MBean's {@link #sendNotification
     * sendNotification} method will call {@code emitter.}{@link
     * NotificationBroadcasterSupport#sendNotification sendNotification}.</p>
     *
     * <p>The array returned by {@link #getNotificationInfo()} on the
     * new MBean is a copy of the array returned by
     * {@code emitter.}{@link NotificationBroadcaster#getNotificationInfo
     * getNotificationInfo()} at the time of construction.  If the array
     * returned by {@code emitter.getNotificationInfo()} later changes,
     * that will have no effect on this object's
     * {@code getNotificationInfo()}.</p>
     *
     * <p>This constructor must be called from a subclass that implements
     * the given {@code mbeanInterface}.</p>
     *
     * <p>
     *  <p>创建一个MBean,其管理接口由{@code mbeanInterface}指定,其中通知由给定的{@code NotificationEmitter}处理。
     * 生成的MBean通过将其方法转发到{@code emitter}来实现{@code NotificationEmitter}接口。</p>。
     * 
     * <p>如果{@code emitter}是{@code NotificationBroadcasterSupport}的实例,那么MBean的{@link #sendNotification sendNotification}
     * 方法将调用{@code emitter.}{@link NotificationBroadcasterSupport#sendNotification sendNotification}。
     * </p>。
     * 
     *  <p> {@link #getNotificationInfo()}在新MBean上返回的数组是在构建时由{@code emitter.}{@link NotificationBroadcaster#getNotificationInfo getNotificationInfo()}
     * 返回的数组的副本。
     * 如果{@code emitter.getNotificationInfo()}返回的数组稍后发生变化,那么对该对象的{@code getNotificationInfo()}没有影响。</p>。
     * 
     *  <p>此构造函数必须从实现给定{@code mbeanInterface}的子类中调用。</p>
     * 
     * 
     * @param mbeanInterface a StandardMBean interface.
     * @param emitter the object that will handle notifications.
     *
     * @throws IllegalArgumentException if the {@code mbeanInterface}
     *    does not follow JMX design patterns for Management Interfaces, or
     *    if {@code this} does not implement the specified interface, or
     *    if {@code emitter} is null.
     */
    protected StandardEmitterMBean(Class<?> mbeanInterface,
                                   NotificationEmitter emitter) {
        this(mbeanInterface, false, emitter);
    }

    /**
     * <p>Make an MBean whose management interface is specified by
     * {@code mbeanInterface}, and where notifications are handled by
     * the given {@code NotificationEmitter}.  This constructor can be
     * used to make either Standard MBeans or MXBeans.  The resultant
     * MBean implements the {@code NotificationEmitter} interface by
     * forwarding its methods to {@code emitter}.</p>
     *
     * <p>If {@code emitter} is an instance of {@code
     * NotificationBroadcasterSupport} then the MBean's {@link #sendNotification
     * sendNotification} method will call {@code emitter.}{@link
     * NotificationBroadcasterSupport#sendNotification sendNotification}.</p>
     *
     * <p>The array returned by {@link #getNotificationInfo()} on the
     * new MBean is a copy of the array returned by
     * {@code emitter.}{@link NotificationBroadcaster#getNotificationInfo
     * getNotificationInfo()} at the time of construction.  If the array
     * returned by {@code emitter.getNotificationInfo()} later changes,
     * that will have no effect on this object's
     * {@code getNotificationInfo()}.</p>
     *
     * <p>This constructor must be called from a subclass that implements
     * the given {@code mbeanInterface}.</p>
     *
     * <p>
     *  <p>创建一个MBean,其管理接口由{@code mbeanInterface}指定,其中通知由给定的{@code NotificationEmitter}处理。
     * 此构造函数可用于制作标准MBeans或MXBeans。生成的MBean通过将其方法转发到{@code emitter}来实现{@code NotificationEmitter}接口。</p>。
     * 
     *  <p>如果{@code emitter}是{@code NotificationBroadcasterSupport}的实例,那么MBean的{@link #sendNotification sendNotification}
     * 方法将调用{@code emitter.}{@link NotificationBroadcasterSupport#sendNotification sendNotification}。
     * </p>。
     * 
     * <p> {@link #getNotificationInfo()}在新MBean上返回的数组是在构建时由{@code emitter.}{@link NotificationBroadcaster#getNotificationInfo getNotificationInfo()}
     * 返回的数组的副本。
     * 如果{@code emitter.getNotificationInfo()}返回的数组稍后发生变化,那么对该对象的{@code getNotificationInfo()}没有影响。</p>。
     * 
     *  <p>此构造函数必须从实现给定{@code mbeanInterface}的子类中调用。</p>
     * 
     * 
     * @param mbeanInterface a StandardMBean interface.
     * @param isMXBean If true, the {@code mbeanInterface} parameter
     * names an MXBean interface and the resultant MBean is an MXBean.
     * @param emitter the object that will handle notifications.
     *
     * @throws IllegalArgumentException if the {@code mbeanInterface}
     *    does not follow JMX design patterns for Management Interfaces, or
     *    if {@code this} does not implement the specified interface, or
     *    if {@code emitter} is null.
     */
    protected StandardEmitterMBean(Class<?> mbeanInterface, boolean isMXBean,
                                   NotificationEmitter emitter) {
        super(mbeanInterface, isMXBean);
        if (emitter == null)
            throw new IllegalArgumentException("Null emitter");
        this.emitter = emitter;
        MBeanNotificationInfo[] infos = emitter.getNotificationInfo();
        if (infos == null || infos.length == 0) {
            this.notificationInfo = NO_NOTIFICATION_INFO;
        } else {
            this.notificationInfo = infos.clone();
        }
    }

    public void removeNotificationListener(NotificationListener listener)
            throws ListenerNotFoundException {
        emitter.removeNotificationListener(listener);
    }

    public void removeNotificationListener(NotificationListener listener,
                                           NotificationFilter filter,
                                           Object handback)
            throws ListenerNotFoundException {
        emitter.removeNotificationListener(listener, filter, handback);
    }

    public void addNotificationListener(NotificationListener listener,
                                        NotificationFilter filter,
                                        Object handback) {
        emitter.addNotificationListener(listener, filter, handback);
    }

    public MBeanNotificationInfo[] getNotificationInfo() {
        // this getter might get called from the super constructor
        // when the notificationInfo has not been properly set yet
        if (notificationInfo == null) {
            return NO_NOTIFICATION_INFO;
        }
        if (notificationInfo.length == 0) {
            return notificationInfo;
        } else {
            return notificationInfo.clone();
        }
    }

    /**
     * <p>Sends a notification.</p>
     *
     * <p>If the {@code emitter} parameter to the constructor was an
     * instance of {@code NotificationBroadcasterSupport} then this
     * method will call {@code emitter.}{@link
     * NotificationBroadcasterSupport#sendNotification
     * sendNotification}.</p>
     *
     * <p>
     * 
     * @param n the notification to send.
     *
     * @throws ClassCastException if the {@code emitter} parameter to the
     * constructor was not a {@code NotificationBroadcasterSupport}.
     */
    public void sendNotification(Notification n) {
        if (emitter instanceof NotificationBroadcasterSupport)
            ((NotificationBroadcasterSupport) emitter).sendNotification(n);
        else {
            final String msg =
                "Cannot sendNotification when emitter is not an " +
                "instance of NotificationBroadcasterSupport: " +
                emitter.getClass().getName();
            throw new ClassCastException(msg);
        }
    }

    /**
     * <p>Get the MBeanNotificationInfo[] that will be used in the
     * MBeanInfo returned by this MBean.</p>
     *
     * <p>The default implementation of this method returns
     * {@link #getNotificationInfo()}.</p>
     *
     * <p>
     *  <p>发送通知。</p>
     * 
     *  <p>如果构造函数的{@code emitter}参数是{@code NotificationBroadcasterSupport}的实例,则此方法将调用{@code emitter.}{@link NotificationBroadcasterSupport#sendNotification sendNotification}
     * 。
     * </p>。
     * 
     * 
     * @param info The default MBeanInfo derived by reflection.
     * @return the MBeanNotificationInfo[] for the new MBeanInfo.
     */
    @Override
    MBeanNotificationInfo[] getNotifications(MBeanInfo info) {
        return getNotificationInfo();
    }
}
