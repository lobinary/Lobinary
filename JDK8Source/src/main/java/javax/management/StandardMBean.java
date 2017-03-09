/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2002, 2008, Oracle and/or its affiliates. All rights reserved.
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

import static com.sun.jmx.defaults.JmxProperties.MISC_LOGGER;
import com.sun.jmx.mbeanserver.DescriptorCache;
import com.sun.jmx.mbeanserver.Introspector;
import com.sun.jmx.mbeanserver.MBeanSupport;
import com.sun.jmx.mbeanserver.MXBeanSupport;
import com.sun.jmx.mbeanserver.StandardMBeanSupport;
import com.sun.jmx.mbeanserver.Util;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.logging.Level;
import javax.management.openmbean.OpenMBeanAttributeInfo;
import javax.management.openmbean.OpenMBeanAttributeInfoSupport;
import javax.management.openmbean.OpenMBeanConstructorInfo;
import javax.management.openmbean.OpenMBeanConstructorInfoSupport;
import javax.management.openmbean.OpenMBeanOperationInfo;
import javax.management.openmbean.OpenMBeanOperationInfoSupport;
import javax.management.openmbean.OpenMBeanParameterInfo;
import javax.management.openmbean.OpenMBeanParameterInfoSupport;

/**
 * <p>An MBean whose management interface is determined by reflection
 * on a Java interface.</p>
 *
 * <p>This class brings more flexibility to the notion of Management
 * Interface in the use of Standard MBeans.  Straightforward use of
 * the patterns for Standard MBeans described in the JMX Specification
 * means that there is a fixed relationship between the implementation
 * class of an MBean and its management interface (i.e., if the
 * implementation class is Thing, the management interface must be
 * ThingMBean).  This class makes it possible to keep the convenience
 * of specifying the management interface with a Java interface,
 * without requiring that there be any naming relationship between the
 * implementation and interface classes.</p>
 *
 * <p>By making a DynamicMBean out of an MBean, this class makes
 * it possible to select any interface implemented by the MBean as its
 * management interface, provided that it complies with JMX patterns
 * (i.e., attributes defined by getter/setter etc...).</p>
 *
 * <p> This class also provides hooks that make it possible to supply
 * custom descriptions and names for the {@link MBeanInfo} returned by
 * the DynamicMBean interface.</p>
 *
 * <p>Using this class, an MBean can be created with any
 * implementation class name <i>Impl</i> and with a management
 * interface defined (as for current Standard MBeans) by any interface
 * <i>Intf</i>, in one of two general ways:</p>
 *
 * <ul>
 *
 * <li>Using the public constructor
 *     {@link #StandardMBean(java.lang.Object, java.lang.Class, boolean)
 *     StandardMBean(impl,interface)}:
 *     <pre>
 *     MBeanServer mbs;
 *     ...
 *     Impl impl = new Impl(...);
 *     StandardMBean mbean = new StandardMBean(impl, Intf.class, false);
 *     mbs.registerMBean(mbean, objectName);
 *     </pre></li>
 *
 * <li>Subclassing StandardMBean:
 *     <pre>
 *     public class Impl extends StandardMBean implements Intf {
 *        public Impl() {
 *          super(Intf.class, false);
 *       }
 *       // implement methods of Intf
 *     }
 *
 *     [...]
 *
 *     MBeanServer mbs;
 *     ....
 *     Impl impl = new Impl();
 *     mbs.registerMBean(impl, objectName);
 *     </pre></li>
 *
 * </ul>
 *
 * <p>In either case, the class <i>Impl</i> must implement the
 * interface <i>Intf</i>.</p>
 *
 * <p>Standard MBeans based on the naming relationship between
 * implementation and interface classes are of course still
 * available.</p>
 *
 * <p>This class may also be used to construct MXBeans.  The usage
 * is exactly the same as for Standard MBeans except that in the
 * examples above, the {@code false} parameter to the constructor or
 * {@code super(...)} invocation is instead {@code true}.</p>
 *
 * <p>
 *  <p> MBean,其管理界面由Java界面上的反映决定。</p>
 * 
 *  <p>此类在使用标准MBean时为管理接口的概念带来更多的灵活性。
 * 直接使用JMX规范中描述的标准MBean的模式意味着在MBean的实现类与其管理接口之间存在固定关系(即,如果实现类是Thing,则管理接口必须是ThingMBean)。
 * 该类使得可以保持使用Java接口指定管理接口的方便性,而不需要在实现和接口类之间存在任何命名关系。</p>。
 * 
 *  <p>通过从MBean创建DynamicMBean,此类可以选择由MBean实现的任何接口作为其管理接口,只要它符合JMX模式(即由getter / setter等定义的属性...) )。</p>
 * 
 *  <p>此类还提供了钩子,可以为DynamicMBean接口返回的{@link MBeanInfo}提供自定义描述和名称。</p>
 * 
 * <p>使用此类,可以通过任何实现类名<i> Impl </i>创建MBean,并通过任何接口Intf </i>定义管理接口(如当前标准MBean)以两种一般方式之一：</p>
 * 
 * <ul>
 * 
 *  <li>使用公共构造函数{@link #StandardMBean(java.lang.Object,java.lang.Class,boolean)StandardMBean(impl,interface)}
 * ：。
 * <pre>
 *  MBeanServer mbs; ... Impl impl = new Impl(...); StandardMBean mbean = new StandardMBean(impl,Intf.cl
 * ass,false); mbs.registerMBean(mbean,objectName); </pre> </li>。
 * 
 *  <li>子类化StandardMBean：
 * <pre>
 *  public class Impl extends StandardMBean implements Intf {public Impl(){super(Intf.class,false); } //
 * 实现Intf的方法}。
 * 
 *  [...]
 * 
 *  MBeanServer mbs; ... Impl impl = new Impl(); mbs.registerMBean(impl,objectName); </pre> </li>
 * 
 * </ul>
 * 
 *  <p>在任一情况下,类<im> </i>必须实现接口<i> Intf </i>。</p>
 * 
 *  <p>基于实现和接口类之间的命名关系的标准MBeans当然仍然可用。</p>
 * 
 *  <p>此类也可用于构建MXBeans。除了在上面的示例中,构造函数的{@code false}参数或{@code super(...)}调用的代码是{@code true}。
 * 用法与标准MBeans完全相同。</p >。
 * 
 * 
 * @since 1.5
 */
public class StandardMBean implements DynamicMBean, MBeanRegistration {

    private final static DescriptorCache descriptors =
        DescriptorCache.getInstance(JMX.proof);

    /**
     * The DynamicMBean that wraps the MXBean or Standard MBean implementation.
     * <p>
     *  包装MXBean或标准MBean实现的DynamicMBean。
     * 
     * 
     **/
    private volatile MBeanSupport<?> mbean;

    /**
     * The cached MBeanInfo.
     * <p>
     *  缓存的MBeanInfo。
     * 
     * 
     **/
    private volatile MBeanInfo cachedMBeanInfo;

    /**
     * Make a DynamicMBean out of <var>implementation</var>, using the
     * specified <var>mbeanInterface</var> class.
     * <p>
     *  使用指定的<var> mbeanInterface </var>类,从<var>实现</var>中创建DynamicMBean。
     * 
     * 
     * @param implementation The implementation of this MBean.
     *        If <code>null</code>, and null implementation is allowed,
     *        then the implementation is assumed to be <var>this</var>.
     * @param mbeanInterface The Management Interface exported by this
     *        MBean's implementation. If <code>null</code>, then this
     *        object will use standard JMX design pattern to determine
     *        the management interface associated with the given
     *        implementation.
     * @param nullImplementationAllowed <code>true</code> if a null
     *        implementation is allowed. If null implementation is allowed,
     *        and a null implementation is passed, then the implementation
     *        is assumed to be <var>this</var>.
     * @exception IllegalArgumentException if the given
     *    <var>implementation</var> is null, and null is not allowed.
     **/
    private <T> void construct(T implementation, Class<T> mbeanInterface,
                               boolean nullImplementationAllowed,
                               boolean isMXBean)
                               throws NotCompliantMBeanException {
        if (implementation == null) {
            // Have to use (T)this rather than mbeanInterface.cast(this)
            // because mbeanInterface might be null.
            if (nullImplementationAllowed)
                implementation = Util.<T>cast(this);
            else throw new IllegalArgumentException("implementation is null");
        }
        if (isMXBean) {
            if (mbeanInterface == null) {
                mbeanInterface = Util.cast(Introspector.getMXBeanInterface(
                        implementation.getClass()));
            }
            this.mbean = new MXBeanSupport(implementation, mbeanInterface);
        } else {
            if (mbeanInterface == null) {
                mbeanInterface = Util.cast(Introspector.getStandardMBeanInterface(
                        implementation.getClass()));
            }
            this.mbean =
                    new StandardMBeanSupport(implementation, mbeanInterface);
        }
    }

    /**
     * <p>Make a DynamicMBean out of the object
     * <var>implementation</var>, using the specified
     * <var>mbeanInterface</var> class.</p>
     *
     * <p>
     * <p>使用指定的<var> mbeanInterface </var>类,从对象<var>实现</var>中创建DynamicMBean。</p>
     * 
     * 
     * @param implementation The implementation of this MBean.
     * @param mbeanInterface The Management Interface exported by this
     *        MBean's implementation. If <code>null</code>, then this
     *        object will use standard JMX design pattern to determine
     *        the management interface associated with the given
     *        implementation.
     * @param <T> Allows the compiler to check
     * that {@code implementation} does indeed implement the class
     * described by {@code mbeanInterface}.  The compiler can only
     * check this if {@code mbeanInterface} is a class literal such
     * as {@code MyMBean.class}.
     *
     * @exception IllegalArgumentException if the given
     *    <var>implementation</var> is null.
     * @exception NotCompliantMBeanException if the <var>mbeanInterface</var>
     *    does not follow JMX design patterns for Management Interfaces, or
     *    if the given <var>implementation</var> does not implement the
     *    specified interface.
     **/
    public <T> StandardMBean(T implementation, Class<T> mbeanInterface)
        throws NotCompliantMBeanException {
        construct(implementation, mbeanInterface, false, false);
    }

    /**
     * <p>Make a DynamicMBean out of <var>this</var>, using the specified
     * <var>mbeanInterface</var> class.</p>
     *
     * <p>Calls {@link #StandardMBean(java.lang.Object, java.lang.Class)
     *       this(this,mbeanInterface)}.
     * This constructor is reserved to subclasses.</p>
     *
     * <p>
     *  <p>使用指定的<var> mbeanInterface </var>类,从<var> this </var>中创建DynamicMBean。</p>
     * 
     *  <p>调用{@link #StandardMBean(java.lang.Object,java.lang.Class)this(this,mbeanInterface)}。此构造函数保留给子类。
     * </p>。
     * 
     * 
     * @param mbeanInterface The Management Interface exported by this
     *        MBean.
     *
     * @exception NotCompliantMBeanException if the <var>mbeanInterface</var>
     *    does not follow JMX design patterns for Management Interfaces, or
     *    if <var>this</var> does not implement the specified interface.
     **/
    protected StandardMBean(Class<?> mbeanInterface)
        throws NotCompliantMBeanException {
        construct(null, mbeanInterface, true, false);
    }

    /**
     * <p>Make a DynamicMBean out of the object
     * <var>implementation</var>, using the specified
     * <var>mbeanInterface</var> class, and choosing whether the
     * resultant MBean is an MXBean.  This constructor can be used
     * to make either Standard MBeans or MXBeans.  Unlike the
     * constructor {@link #StandardMBean(Object, Class)}, it
     * does not throw NotCompliantMBeanException.</p>
     *
     * <p>
     *  <p>使用指定的<var> mbeanInterface </var>类,从对象<var>实现</var>中创建DynamicMBean,并选择结果MBean是否为MXBean。
     * 此构造函数可用于制作标准MBeans或MXBeans。
     * 与构造函数{@link #StandardMBean(Object,Class)}不同,它不会抛出NotCompliantMBeanException。</p>。
     * 
     * 
     * @param implementation The implementation of this MBean.
     * @param mbeanInterface The Management Interface exported by this
     *        MBean's implementation. If <code>null</code>, then this
     *        object will use standard JMX design pattern to determine
     *        the management interface associated with the given
     *        implementation.
     * @param isMXBean If true, the {@code mbeanInterface} parameter
     * names an MXBean interface and the resultant MBean is an MXBean.
     * @param <T> Allows the compiler to check
     * that {@code implementation} does indeed implement the class
     * described by {@code mbeanInterface}.  The compiler can only
     * check this if {@code mbeanInterface} is a class literal such
     * as {@code MyMBean.class}.
     *
     * @exception IllegalArgumentException if the given
     *    <var>implementation</var> is null, or if the <var>mbeanInterface</var>
     *    does not follow JMX design patterns for Management Interfaces, or
     *    if the given <var>implementation</var> does not implement the
     *    specified interface.
     *
     * @since 1.6
     **/
    public <T> StandardMBean(T implementation, Class<T> mbeanInterface,
                             boolean isMXBean) {
        try {
            construct(implementation, mbeanInterface, false, isMXBean);
        } catch (NotCompliantMBeanException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * <p>Make a DynamicMBean out of <var>this</var>, using the specified
     * <var>mbeanInterface</var> class, and choosing whether the resulting
     * MBean is an MXBean.  This constructor can be used
     * to make either Standard MBeans or MXBeans.  Unlike the
     * constructor {@link #StandardMBean(Object, Class)}, it
     * does not throw NotCompliantMBeanException.</p>
     *
     * <p>Calls {@link #StandardMBean(java.lang.Object, java.lang.Class, boolean)
     *       this(this, mbeanInterface, isMXBean)}.
     * This constructor is reserved to subclasses.</p>
     *
     * <p>
     *  <p>使用指定的<var> mbeanInterface </var>类,从<var> this </var>中创建DynamicMBean,并选择生成的MBean是否为MXBean。
     * 此构造函数可用于制作标准MBeans或MXBeans。
     * 与构造函数{@link #StandardMBean(Object,Class)}不同,它不会抛出NotCompliantMBeanException。</p>。
     * 
     *  <p>调用{@link #StandardMBean(java.lang.Object,java.lang.Class,boolean)this(this,mbeanInterface,isMXBean)}
     * 。
     * 此构造函数保留给子类。</p>。
     * 
     * 
     * @param mbeanInterface The Management Interface exported by this
     *        MBean.
     * @param isMXBean If true, the {@code mbeanInterface} parameter
     * names an MXBean interface and the resultant MBean is an MXBean.
     *
     * @exception IllegalArgumentException if the <var>mbeanInterface</var>
     *    does not follow JMX design patterns for Management Interfaces, or
     *    if <var>this</var> does not implement the specified interface.
     *
     * @since 1.6
     **/
    protected StandardMBean(Class<?> mbeanInterface, boolean isMXBean) {
        try {
            construct(null, mbeanInterface, true, isMXBean);
        } catch (NotCompliantMBeanException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * <p>Replace the implementation object wrapped in this object.</p>
     *
     * <p>
     *  <p>替换包含在此对象中的实现对象。</p>
     * 
     * 
     * @param implementation The new implementation of this Standard MBean
     * (or MXBean). The <code>implementation</code> object must implement
     * the Standard MBean (or MXBean) interface that was supplied when this
     * <code>StandardMBean</code> was constructed.
     *
     * @exception IllegalArgumentException if the given
     * <var>implementation</var> is null.
     *
     * @exception NotCompliantMBeanException if the given
     * <var>implementation</var> does not implement the
     * Standard MBean (or MXBean) interface that was
     * supplied at construction.
     *
     * @see #getImplementation
     **/
    public void setImplementation(Object implementation)
        throws NotCompliantMBeanException {

        if (implementation == null)
            throw new IllegalArgumentException("implementation is null");

        if (isMXBean()) {
            this.mbean = new MXBeanSupport(implementation,
                    Util.<Class<Object>>cast(getMBeanInterface()));
        } else {
            this.mbean = new StandardMBeanSupport(implementation,
                    Util.<Class<Object>>cast(getMBeanInterface()));
        }
    }

    /**
     * Get the implementation of this Standard MBean (or MXBean).
     * <p>
     *  获取此标准MBean(或MXBean)的实现。
     * 
     * 
     * @return The implementation of this Standard MBean (or MXBean).
     *
     * @see #setImplementation
     **/
    public Object getImplementation() {
        return mbean.getResource();
    }

    /**
     * Get the Management Interface of this Standard MBean (or MXBean).
     * <p>
     *  获取此标准MBean(或MXBean)的管理界面。
     * 
     * 
     * @return The management interface of this Standard MBean (or MXBean).
     **/
    public final Class<?> getMBeanInterface() {
        return mbean.getMBeanInterface();
    }

    /**
     * Get the class of the implementation of this Standard MBean (or MXBean).
     * <p>
     * 获取此标准MBean(或MXBean)的实现类。
     * 
     * 
     * @return The class of the implementation of this Standard MBean (or MXBean).
     **/
    public Class<?> getImplementationClass() {
        return mbean.getResource().getClass();
    }

    // ------------------------------------------------------------------
    // From the DynamicMBean interface.
    // ------------------------------------------------------------------
    public Object getAttribute(String attribute)
        throws AttributeNotFoundException,
               MBeanException,
               ReflectionException {
        return mbean.getAttribute(attribute);
    }

    // ------------------------------------------------------------------
    // From the DynamicMBean interface.
    // ------------------------------------------------------------------
    public void setAttribute(Attribute attribute)
        throws AttributeNotFoundException,
               InvalidAttributeValueException,
               MBeanException,
               ReflectionException {
        mbean.setAttribute(attribute);
    }

    // ------------------------------------------------------------------
    // From the DynamicMBean interface.
    // ------------------------------------------------------------------
    public AttributeList getAttributes(String[] attributes) {
        return mbean.getAttributes(attributes);
    }

    // ------------------------------------------------------------------
    // From the DynamicMBean interface.
    // ------------------------------------------------------------------
    public AttributeList setAttributes(AttributeList attributes) {
        return mbean.setAttributes(attributes);
    }

    // ------------------------------------------------------------------
    // From the DynamicMBean interface.
    // ------------------------------------------------------------------
    public Object invoke(String actionName, Object params[], String signature[])
            throws MBeanException, ReflectionException {
        return mbean.invoke(actionName, params, signature);
    }

    /**
     * Get the {@link MBeanInfo} for this MBean.
     * <p>
     * This method implements
     * {@link javax.management.DynamicMBean#getMBeanInfo()
     *   DynamicMBean.getMBeanInfo()}.
     * <p>
     * This method first calls {@link #getCachedMBeanInfo()} in order to
     * retrieve the cached MBeanInfo for this MBean, if any. If the
     * MBeanInfo returned by {@link #getCachedMBeanInfo()} is not null,
     * then it is returned.<br>
     * Otherwise, this method builds a default MBeanInfo for this MBean,
     * using the Management Interface specified for this MBean.
     * <p>
     * While building the MBeanInfo, this method calls the customization
     * hooks that make it possible for subclasses to supply their custom
     * descriptions, parameter names, etc...<br>
     * Finally, it calls {@link #cacheMBeanInfo(javax.management.MBeanInfo)
     * cacheMBeanInfo()} in order to cache the new MBeanInfo.
     * <p>
     *  获取此MBean的{@link MBeanInfo}。
     * <p>
     *  此方法实现{@link javax.management.DynamicMBean#getMBeanInfo()DynamicMBean.getMBeanInfo()}。
     * <p>
     *  此方法首先调用{@link #getCachedMBeanInfo()},以检索此MBean的缓存MBeanInfo(如果有)。
     * 如果{@link #getCachedMBeanInfo()}返回的MBeanInfo不为null,则返回。
     * <br>否则,此方法使用为此MBean指定的管理接口为此MBean构建一个默认MBeanInfo。
     * <p>
     *  在构建MBeanInfo时,此方法调用自定义钩子,使子类可以提供其自定义描述,参数名称等。
     * <br>最后,它调用{@link #cacheMBeanInfo(javax.management.MBeanInfo)cacheMBeanInfo ()},以便缓存新的MBeanInfo。
     * 
     * 
     * @return The cached MBeanInfo for that MBean, if not null, or a
     *         newly built MBeanInfo if none was cached.
     **/
    public MBeanInfo getMBeanInfo() {
        try {
            final MBeanInfo cached = getCachedMBeanInfo();
            if (cached != null) return cached;
        } catch (RuntimeException x) {
            if (MISC_LOGGER.isLoggable(Level.FINEST)) {
                MISC_LOGGER.logp(Level.FINEST,
                        MBeanServerFactory.class.getName(), "getMBeanInfo",
                        "Failed to get cached MBeanInfo", x);
            }
        }

        if (MISC_LOGGER.isLoggable(Level.FINER)) {
            MISC_LOGGER.logp(Level.FINER,
                    MBeanServerFactory.class.getName(), "getMBeanInfo",
                    "Building MBeanInfo for " +
                    getImplementationClass().getName());
        }

        MBeanSupport<?> msupport = mbean;
        final MBeanInfo bi = msupport.getMBeanInfo();
        final Object impl = msupport.getResource();

        final boolean immutableInfo = immutableInfo(this.getClass());

        final String                  cname = getClassName(bi);
        final String                  text  = getDescription(bi);
        final MBeanConstructorInfo[]  ctors = getConstructors(bi,impl);
        final MBeanAttributeInfo[]    attrs = getAttributes(bi);
        final MBeanOperationInfo[]    ops   = getOperations(bi);
        final MBeanNotificationInfo[] ntfs  = getNotifications(bi);
        final Descriptor              desc  = getDescriptor(bi, immutableInfo);

        final MBeanInfo nmbi = new MBeanInfo(
                cname, text, attrs, ctors, ops, ntfs, desc);
        try {
            cacheMBeanInfo(nmbi);
        } catch (RuntimeException x) {
            if (MISC_LOGGER.isLoggable(Level.FINEST)) {
                MISC_LOGGER.logp(Level.FINEST,
                        MBeanServerFactory.class.getName(), "getMBeanInfo",
                        "Failed to cache MBeanInfo", x);
            }
        }

        return nmbi;
    }

    /**
     * Customization hook:
     * Get the className that will be used in the MBeanInfo returned by
     * this MBean.
     * <br>
     * Subclasses may redefine this method in order to supply their
     * custom class name.  The default implementation returns
     * {@link MBeanInfo#getClassName() info.getClassName()}.
     * <p>
     *  自定义钩子：获取将在此MBean返回的MBeanInfo中使用的className。
     * <br>
     *  子类可以重新定义此方法,以提供其自定义类名。默认实现返回{@link MBeanInfo#getClassName()info.getClassName()}。
     * 
     * 
     * @param info The default MBeanInfo derived by reflection.
     * @return the class name for the new MBeanInfo.
     **/
    protected String getClassName(MBeanInfo info) {
        if (info == null) return getImplementationClass().getName();
        return info.getClassName();
    }

    /**
     * Customization hook:
     * Get the description that will be used in the MBeanInfo returned by
     * this MBean.
     * <br>
     * Subclasses may redefine this method in order to supply their
     * custom MBean description.  The default implementation returns
     * {@link MBeanInfo#getDescription() info.getDescription()}.
     * <p>
     *  自定义钩子：获取将在此MBean返回的MBeanInfo中使用的描述。
     * <br>
     *  子类可以重新定义此方法,以提供其自定义MBean描述。默认实现返回{@link MBeanInfo#getDescription()info.getDescription()}。
     * 
     * 
     * @param info The default MBeanInfo derived by reflection.
     * @return the description for the new MBeanInfo.
     **/
    protected String getDescription(MBeanInfo info) {
        if (info == null) return null;
        return info.getDescription();
    }

    /**
     * <p>Customization hook:
     * Get the description that will be used in the MBeanFeatureInfo
     * returned by this MBean.</p>
     *
     * <p>Subclasses may redefine this method in order to supply
     * their custom description.  The default implementation returns
     * {@link MBeanFeatureInfo#getDescription()
     * info.getDescription()}.</p>
     *
     * <p>This method is called by
     *      {@link #getDescription(MBeanAttributeInfo)},
     *      {@link #getDescription(MBeanOperationInfo)},
     *      {@link #getDescription(MBeanConstructorInfo)}.</p>
     *
     * <p>
     * <p>自定义钩子：获取将在此MBean返回的MBeanFeatureInfo中使用的描述。</p>
     * 
     *  <p>子类可以重新定义此方法,以提供其自定义描述。默认实现返回{@link MBeanFeatureInfo#getDescription()info.getDescription()}。</p>
     * 
     *  <p>此方法由{@link #getDescription(MBeanAttributeInfo)},{@link #getDescription(MBeanOperationInfo)},{@link #getDescription(MBeanConstructorInfo)}
     * 调用。
     * </p>。
     * 
     * 
     * @param info The default MBeanFeatureInfo derived by reflection.
     * @return the description for the given MBeanFeatureInfo.
     **/
    protected String getDescription(MBeanFeatureInfo info) {
        if (info == null) return null;
        return info.getDescription();
    }

    /**
     * Customization hook:
     * Get the description that will be used in the MBeanAttributeInfo
     * returned by this MBean.
     *
     * <p>Subclasses may redefine this method in order to supply their
     * custom description.  The default implementation returns {@link
     * #getDescription(MBeanFeatureInfo)
     * getDescription((MBeanFeatureInfo) info)}.
     * <p>
     *  自定义钩子：获取将在此MBean返回的MBeanAttributeInfo中使用的描述。
     * 
     *  <p>子类可以重新定义此方法,以提供其自定义描述。
     * 默认实现返回{@link #getDescription(MBeanFeatureInfo)getDescription((MBeanFeatureInfo)info)}。
     * 
     * 
     * @param info The default MBeanAttributeInfo derived by reflection.
     * @return the description for the given MBeanAttributeInfo.
     **/
    protected String getDescription(MBeanAttributeInfo info) {
        return getDescription((MBeanFeatureInfo)info);
    }

    /**
     * Customization hook:
     * Get the description that will be used in the MBeanConstructorInfo
     * returned by this MBean.
     * <br>
     * Subclasses may redefine this method in order to supply their
     * custom description.
     * The default implementation returns {@link
     * #getDescription(MBeanFeatureInfo)
     * getDescription((MBeanFeatureInfo) info)}.
     * <p>
     *  自定义钩子：获取将在此MBean返回的MBeanConstructorInfo中使用的描述。
     * <br>
     *  子类可以重新定义此方法以提供其自定义描述。
     * 默认实现返回{@link #getDescription(MBeanFeatureInfo)getDescription((MBeanFeatureInfo)info)}。
     * 
     * 
     * @param info The default MBeanConstructorInfo derived by reflection.
     * @return the description for the given MBeanConstructorInfo.
     **/
    protected String getDescription(MBeanConstructorInfo info) {
        return getDescription((MBeanFeatureInfo)info);
    }

    /**
     * Customization hook:
     * Get the description that will be used for the  <var>sequence</var>
     * MBeanParameterInfo of the MBeanConstructorInfo returned by this MBean.
     * <br>
     * Subclasses may redefine this method in order to supply their
     * custom description.  The default implementation returns
     * {@link MBeanParameterInfo#getDescription() param.getDescription()}.
     *
     * <p>
     *  自定义钩子：获取将用于此MBean返回的MBeanConstructorInfo的<var> sequence </var> MBeanParameterInfo的描述。
     * <br>
     *  子类可以重新定义此方法以提供其自定义描述。默认实现返回{@link MBeanParameterInfo#getDescription()param.getDescription()}。
     * 
     * 
     * @param ctor  The default MBeanConstructorInfo derived by reflection.
     * @param param The default MBeanParameterInfo derived by reflection.
     * @param sequence The sequence number of the parameter considered
     *        ("0" for the first parameter, "1" for the second parameter,
     *        etc...).
     * @return the description for the given MBeanParameterInfo.
     **/
    protected String getDescription(MBeanConstructorInfo ctor,
                                    MBeanParameterInfo   param,
                                    int sequence) {
        if (param == null) return null;
        return param.getDescription();
    }

    /**
     * Customization hook:
     * Get the name that will be used for the <var>sequence</var>
     * MBeanParameterInfo of the MBeanConstructorInfo returned by this MBean.
     * <br>
     * Subclasses may redefine this method in order to supply their
     * custom parameter name.  The default implementation returns
     * {@link MBeanParameterInfo#getName() param.getName()}.
     *
     * <p>
     * 自定义钩子：获取将用于此MBean返回的MBeanConstructorInfo的<var> sequence </var> MBeanParameterInfo的名称。
     * <br>
     *  子类可以重新定义此方法,以提供其自定义参数名称。默认实现返回{@link MBeanParameterInfo#getName()param.getName()}。
     * 
     * 
     * @param ctor  The default MBeanConstructorInfo derived by reflection.
     * @param param The default MBeanParameterInfo derived by reflection.
     * @param sequence The sequence number of the parameter considered
     *        ("0" for the first parameter, "1" for the second parameter,
     *        etc...).
     * @return the name for the given MBeanParameterInfo.
     **/
    protected String getParameterName(MBeanConstructorInfo ctor,
                                      MBeanParameterInfo param,
                                      int sequence) {
        if (param == null) return null;
        return param.getName();
    }

    /**
     * Customization hook:
     * Get the description that will be used in the MBeanOperationInfo
     * returned by this MBean.
     * <br>
     * Subclasses may redefine this method in order to supply their
     * custom description.  The default implementation returns
     * {@link #getDescription(MBeanFeatureInfo)
     * getDescription((MBeanFeatureInfo) info)}.
     * <p>
     *  自定义钩子：获取将在此MBean返回的MBeanOperationInfo中使用的描述。
     * <br>
     *  子类可以重新定义此方法以提供其自定义描述。
     * 默认实现返回{@link #getDescription(MBeanFeatureInfo)getDescription((MBeanFeatureInfo)info)}。
     * 
     * 
     * @param info The default MBeanOperationInfo derived by reflection.
     * @return the description for the given MBeanOperationInfo.
     **/
    protected String getDescription(MBeanOperationInfo info) {
        return getDescription((MBeanFeatureInfo)info);
    }

    /**
     * Customization hook:
     * Get the <var>impact</var> flag of the operation that will be used in
     * the MBeanOperationInfo returned by this MBean.
     * <br>
     * Subclasses may redefine this method in order to supply their
     * custom impact flag.  The default implementation returns
     * {@link MBeanOperationInfo#getImpact() info.getImpact()}.
     * <p>
     *  自定义钩子：获取将在此MBean返回的MBeanOperationInfo中使用的操作的<var> impact </var>标志。
     * <br>
     *  子类可以重新定义此方法以提供其自定义影响标志。默认实现返回{@link MBeanOperationInfo#getImpact()info.getImpact()}。
     * 
     * 
     * @param info The default MBeanOperationInfo derived by reflection.
     * @return the impact flag for the given MBeanOperationInfo.
     **/
    protected int getImpact(MBeanOperationInfo info) {
        if (info == null) return MBeanOperationInfo.UNKNOWN;
        return info.getImpact();
    }

    /**
     * Customization hook:
     * Get the name that will be used for the <var>sequence</var>
     * MBeanParameterInfo of the MBeanOperationInfo returned by this MBean.
     * <br>
     * Subclasses may redefine this method in order to supply their
     * custom parameter name.  The default implementation returns
     * {@link MBeanParameterInfo#getName() param.getName()}.
     *
     * <p>
     *  自定义钩子：获取将用于此MBean返回的MBeanOperationInfo的<var> sequence </var> MBeanParameterInfo的名称。
     * <br>
     *  子类可以重新定义此方法,以便提供其自定义参数名称。默认实现返回{@link MBeanParameterInfo#getName()param.getName()}。
     * 
     * 
     * @param op    The default MBeanOperationInfo derived by reflection.
     * @param param The default MBeanParameterInfo derived by reflection.
     * @param sequence The sequence number of the parameter considered
     *        ("0" for the first parameter, "1" for the second parameter,
     *        etc...).
     * @return the name to use for the given MBeanParameterInfo.
     **/
    protected String getParameterName(MBeanOperationInfo op,
                                      MBeanParameterInfo param,
                                      int sequence) {
        if (param == null) return null;
        return param.getName();
    }

    /**
     * Customization hook:
     * Get the description that will be used for the  <var>sequence</var>
     * MBeanParameterInfo of the MBeanOperationInfo returned by this MBean.
     * <br>
     * Subclasses may redefine this method in order to supply their
     * custom description.  The default implementation returns
     * {@link MBeanParameterInfo#getDescription() param.getDescription()}.
     *
     * <p>
     *  自定义钩子：获取将用于此MBean返回的MBeanOperationInfo的<var> sequence </var> MBeanParameterInfo的描述。
     * <br>
     * 子类可以重新定义此方法以提供其自定义描述。默认实现返回{@link MBeanParameterInfo#getDescription()param.getDescription()}。
     * 
     * 
     * @param op    The default MBeanOperationInfo derived by reflection.
     * @param param The default MBeanParameterInfo derived by reflection.
     * @param sequence The sequence number of the parameter considered
     *        ("0" for the first parameter, "1" for the second parameter,
     *        etc...).
     * @return the description for the given MBeanParameterInfo.
     **/
    protected String getDescription(MBeanOperationInfo op,
                                    MBeanParameterInfo param,
                                    int sequence) {
        if (param == null) return null;
        return param.getDescription();
    }

    /**
     * Customization hook:
     * Get the MBeanConstructorInfo[] that will be used in the MBeanInfo
     * returned by this MBean.
     * <br>
     * By default, this method returns <code>null</code> if the wrapped
     * implementation is not <var>this</var>. Indeed, if the wrapped
     * implementation is not this object itself, it will not be possible
     * to recreate a wrapped implementation by calling the implementation
     * constructors through <code>MBeanServer.createMBean(...)</code>.<br>
     * Otherwise, if the wrapped implementation is <var>this</var>,
     * <var>ctors</var> is returned.
     * <br>
     * Subclasses may redefine this method in order to modify this
     * behavior, if needed.
     * <p>
     *  自定义钩子：获取将在此MBean返回的MBeanInfo中使用的MBeanConstructorInfo []。
     * <br>
     *  默认情况下,如果被包装的实现不是<var> this </var>,这个方法返回<code> null </code>。
     * 实际上,如果被包装的实现不是这个对象本身,将不可能通过调用实现构造函数通过<code> MBeanServer.createMBean(...)</code>重新创建一个被包装的实现。
     * <br>否则,如果包装的实现是<var> this </var>,<var> ctors </var>。
     * <br>
     *  如果需要,子类可以重新定义此方法以修改此行为。
     * 
     * 
     * @param ctors The default MBeanConstructorInfo[] derived by reflection.
     * @param impl  The wrapped implementation. If <code>null</code> is
     *        passed, the wrapped implementation is ignored and
     *        <var>ctors</var> is returned.
     * @return the MBeanConstructorInfo[] for the new MBeanInfo.
     **/
    protected MBeanConstructorInfo[]
        getConstructors(MBeanConstructorInfo[] ctors, Object impl) {
            if (ctors == null) return null;
            if (impl != null && impl != this) return null;
            return ctors;
    }

    /**
     * Customization hook:
     * Get the MBeanNotificationInfo[] that will be used in the MBeanInfo
     * returned by this MBean.
     * <br>
     * Subclasses may redefine this method in order to supply their
     * custom notifications.
     * <p>
     *  自定义钩子：获取将在此MBean返回的MBeanInfo中使用的MBeanNotificationInfo []。
     * <br>
     *  子类可以重新定义此方法,以提供其自定义通知。
     * 
     * 
     * @param info The default MBeanInfo derived by reflection.
     * @return the MBeanNotificationInfo[] for the new MBeanInfo.
     **/
    MBeanNotificationInfo[] getNotifications(MBeanInfo info) {
        return null;
    }

    /**
     * <p>Get the Descriptor that will be used in the MBeanInfo
     * returned by this MBean.</p>
     *
     * <p>Subclasses may redefine this method in order to supply
     * their custom descriptor.</p>
     *
     * <p>The default implementation of this method returns a Descriptor
     * that contains at least the field {@code interfaceClassName}, with
     * value {@link #getMBeanInterface()}.getName(). It may also contain
     * the field {@code immutableInfo}, with a value that is the string
     * {@code "true"} if the implementation can determine that the
     * {@code MBeanInfo} returned by {@link #getMBeanInfo()} will always
     * be the same. It may contain other fields: fields defined by the
     * JMX specification must have appropriate values, and other fields
     * must follow the conventions for non-standard field names.</p>
     *
     * <p>
     *  <p>获取将在此MBean返回的MBeanInfo中使用的描述符。</p>
     * 
     *  <p>子类可以重新定义此方法,以提供其自定义描述符。</p>
     * 
     * <p>此方法的默认实现返回一个描述符,其至少包含字段{@code interfaceClassName},值为{@link #getMBeanInterface()}。getName()。
     * 如果实现可以确定{@link #getMBeanInfo()}返回的{@code MBeanInfo}将始终为{@code MBeanInfo},它还可以包含字段{@code immutableInfo}
     * ,值为字符串{@code"true"是相同的。
     * <p>此方法的默认实现返回一个描述符,其至少包含字段{@code interfaceClassName},值为{@link #getMBeanInterface()}。getName()。
     * 它可能包含其他字段：由JMX规范定义的字段必须具有适当的值,其他字段必须遵循非标准字段名的约定。</p>。
     * 
     * 
     * @param info The default MBeanInfo derived by reflection.
     * @return the Descriptor for the new MBeanInfo.
     */
    Descriptor getDescriptor(MBeanInfo info, boolean immutableInfo) {
        ImmutableDescriptor desc;
        if (info == null ||
            info.getDescriptor() == null ||
            info.getDescriptor().getFieldNames().length == 0) {
            final String interfaceClassNameS =
                "interfaceClassName=" + getMBeanInterface().getName();
            final String immutableInfoS =
                "immutableInfo=" + immutableInfo;
            desc = new ImmutableDescriptor(interfaceClassNameS, immutableInfoS);
            desc = descriptors.get(desc);
        } else {
            Descriptor d = info.getDescriptor();
            Map<String,Object> fields = new HashMap<String,Object>();
            for (String fieldName : d.getFieldNames()) {
                if (fieldName.equals("immutableInfo")) {
                    // Replace immutableInfo as the underlying MBean/MXBean
                    // could already implement NotificationBroadcaster and
                    // return immutableInfo=true in its MBeanInfo.
                    fields.put(fieldName, Boolean.toString(immutableInfo));
                } else {
                    fields.put(fieldName, d.getFieldValue(fieldName));
                }
            }
            desc = new ImmutableDescriptor(fields);
        }
        return desc;
    }

    /**
     * Customization hook:
     * Return the MBeanInfo cached for this object.
     *
     * <p>Subclasses may redefine this method in order to implement their
     * own caching policy.  The default implementation stores one
     * {@link MBeanInfo} object per instance.
     *
     * <p>
     *  定制钩子：返回为此对象缓存的MBeanInfo。
     * 
     *  <p>子类可以重新定义此方法,以便实现自己的缓存策略。默认实现每个实例存储一个{@link MBeanInfo}对象。
     * 
     * 
     * @return The cached MBeanInfo, or null if no MBeanInfo is cached.
     *
     * @see #cacheMBeanInfo(MBeanInfo)
     **/
    protected MBeanInfo getCachedMBeanInfo() {
        return cachedMBeanInfo;
    }

    /**
     * Customization hook:
     * cache the MBeanInfo built for this object.
     *
     * <p>Subclasses may redefine this method in order to implement
     * their own caching policy.  The default implementation stores
     * <code>info</code> in this instance.  A subclass can define
     * other policies, such as not saving <code>info</code> (so it is
     * reconstructed every time {@link #getMBeanInfo()} is called) or
     * sharing a unique {@link MBeanInfo} object when several
     * <code>StandardMBean</code> instances have equal {@link
     * MBeanInfo} values.
     *
     * <p>
     *  自定义钩子：缓存为此对象构建的MBeanInfo。
     * 
     *  <p>子类可以重新定义此方法,以便实现自己的缓存策略。默认实现在此实例中存储<code> info </code>。
     * 子类可以定义其他策略,例如不保存<code> info </code>(所以每次调用{@link #getMBeanInfo()}时都会重构)或者共享一个唯一的{@link MBeanInfo} cod
     * e> StandardMBean </code>实例具有相等的{@link MBeanInfo}值。
     *  <p>子类可以重新定义此方法,以便实现自己的缓存策略。默认实现在此实例中存储<code> info </code>。
     * 
     * 
     * @param info the new <code>MBeanInfo</code> to cache.  Any
     * previously cached value is discarded.  This parameter may be
     * null, in which case there is no new cached value.
     **/
    protected void cacheMBeanInfo(MBeanInfo info) {
        cachedMBeanInfo = info;
    }

    private boolean isMXBean() {
        return mbean.isMXBean();
    }

    private static <T> boolean identicalArrays(T[] a, T[] b) {
        if (a == b)
            return true;
        if (a == null || b == null || a.length != b.length)
            return false;
        for (int i = 0; i < a.length; i++) {
            if (a[i] != b[i])
                return false;
        }
        return true;
    }

    private static <T> boolean equal(T a, T b) {
        if (a == b)
            return true;
        if (a == null || b == null)
            return false;
        return a.equals(b);
    }

    private static MBeanParameterInfo
            customize(MBeanParameterInfo pi,
                      String name,
                      String description) {
        if (equal(name, pi.getName()) &&
                equal(description, pi.getDescription()))
            return pi;
        else if (pi instanceof OpenMBeanParameterInfo) {
            OpenMBeanParameterInfo opi = (OpenMBeanParameterInfo) pi;
            return new OpenMBeanParameterInfoSupport(name,
                                                     description,
                                                     opi.getOpenType(),
                                                     pi.getDescriptor());
        } else {
            return new MBeanParameterInfo(name,
                                          pi.getType(),
                                          description,
                                          pi.getDescriptor());
        }
    }

    private static MBeanConstructorInfo
            customize(MBeanConstructorInfo ci,
                      String description,
                      MBeanParameterInfo[] signature) {
        if (equal(description, ci.getDescription()) &&
                identicalArrays(signature, ci.getSignature()))
            return ci;
        if (ci instanceof OpenMBeanConstructorInfo) {
            OpenMBeanParameterInfo[] oparams =
                paramsToOpenParams(signature);
            return new OpenMBeanConstructorInfoSupport(ci.getName(),
                                                       description,
                                                       oparams,
                                                       ci.getDescriptor());
        } else {
            return new MBeanConstructorInfo(ci.getName(),
                                            description,
                                            signature,
                                            ci.getDescriptor());
        }
    }

    private static MBeanOperationInfo
            customize(MBeanOperationInfo oi,
                      String description,
                      MBeanParameterInfo[] signature,
                      int impact) {
        if (equal(description, oi.getDescription()) &&
                identicalArrays(signature, oi.getSignature()) &&
                impact == oi.getImpact())
            return oi;
        if (oi instanceof OpenMBeanOperationInfo) {
            OpenMBeanOperationInfo ooi = (OpenMBeanOperationInfo) oi;
            OpenMBeanParameterInfo[] oparams =
                paramsToOpenParams(signature);
            return new OpenMBeanOperationInfoSupport(oi.getName(),
                                                     description,
                                                     oparams,
                                                     ooi.getReturnOpenType(),
                                                     impact,
                                                     oi.getDescriptor());
        } else {
            return new MBeanOperationInfo(oi.getName(),
                                          description,
                                          signature,
                                          oi.getReturnType(),
                                          impact,
                                          oi.getDescriptor());
        }
    }

    private static MBeanAttributeInfo
            customize(MBeanAttributeInfo ai,
                      String description) {
        if (equal(description, ai.getDescription()))
            return ai;
        if (ai instanceof OpenMBeanAttributeInfo) {
            OpenMBeanAttributeInfo oai = (OpenMBeanAttributeInfo) ai;
            return new OpenMBeanAttributeInfoSupport(ai.getName(),
                                                     description,
                                                     oai.getOpenType(),
                                                     ai.isReadable(),
                                                     ai.isWritable(),
                                                     ai.isIs(),
                                                     ai.getDescriptor());
        } else {
            return new MBeanAttributeInfo(ai.getName(),
                                          ai.getType(),
                                          description,
                                          ai.isReadable(),
                                          ai.isWritable(),
                                          ai.isIs(),
                                          ai.getDescriptor());
        }
    }

    private static OpenMBeanParameterInfo[]
            paramsToOpenParams(MBeanParameterInfo[] params) {
        if (params instanceof OpenMBeanParameterInfo[])
            return (OpenMBeanParameterInfo[]) params;
        OpenMBeanParameterInfo[] oparams =
            new OpenMBeanParameterInfoSupport[params.length];
        System.arraycopy(params, 0, oparams, 0, params.length);
        return oparams;
    }

    // ------------------------------------------------------------------
    // Build the custom MBeanConstructorInfo[]
    // ------------------------------------------------------------------
    private MBeanConstructorInfo[]
            getConstructors(MBeanInfo info, Object impl) {
        final MBeanConstructorInfo[] ctors =
            getConstructors(info.getConstructors(), impl);
        if (ctors == null)
            return null;
        final int ctorlen = ctors.length;
        final MBeanConstructorInfo[] nctors = new MBeanConstructorInfo[ctorlen];
        for (int i=0; i<ctorlen; i++) {
            final MBeanConstructorInfo c = ctors[i];
            final MBeanParameterInfo[] params = c.getSignature();
            final MBeanParameterInfo[] nps;
            if (params != null) {
                final int plen = params.length;
                nps = new MBeanParameterInfo[plen];
                for (int ii=0;ii<plen;ii++) {
                    MBeanParameterInfo p = params[ii];
                    nps[ii] = customize(p,
                                        getParameterName(c,p,ii),
                                        getDescription(c,p,ii));
                }
            } else {
                nps = null;
            }
            nctors[i] =
                customize(c, getDescription(c), nps);
        }
        return nctors;
    }

    // ------------------------------------------------------------------
    // Build the custom MBeanOperationInfo[]
    // ------------------------------------------------------------------
    private MBeanOperationInfo[] getOperations(MBeanInfo info) {
        final MBeanOperationInfo[] ops = info.getOperations();
        if (ops == null)
            return null;
        final int oplen = ops.length;
        final MBeanOperationInfo[] nops = new MBeanOperationInfo[oplen];
        for (int i=0; i<oplen; i++) {
            final MBeanOperationInfo o = ops[i];
            final MBeanParameterInfo[] params = o.getSignature();
            final MBeanParameterInfo[] nps;
            if (params != null) {
                final int plen = params.length;
                nps = new MBeanParameterInfo[plen];
                for (int ii=0;ii<plen;ii++) {
                    MBeanParameterInfo p = params[ii];
                    nps[ii] = customize(p,
                                        getParameterName(o,p,ii),
                                        getDescription(o,p,ii));
                }
            } else {
                nps = null;
            }
            nops[i] = customize(o, getDescription(o), nps, getImpact(o));
        }
        return nops;
    }

    // ------------------------------------------------------------------
    // Build the custom MBeanAttributeInfo[]
    // ------------------------------------------------------------------
    private MBeanAttributeInfo[] getAttributes(MBeanInfo info) {
        final MBeanAttributeInfo[] atts = info.getAttributes();
        if (atts == null)
            return null; // should not happen
        final MBeanAttributeInfo[] natts;
        final int attlen = atts.length;
        natts = new MBeanAttributeInfo[attlen];
        for (int i=0; i<attlen; i++) {
            final MBeanAttributeInfo a = atts[i];
            natts[i] = customize(a, getDescription(a));
        }
        return natts;
    }

    /**
     * <p>Allows the MBean to perform any operations it needs before
     * being registered in the MBean server.  If the name of the MBean
     * is not specified, the MBean can provide a name for its
     * registration.  If any exception is raised, the MBean will not be
     * registered in the MBean server.</p>
     *
     * <p>The default implementation of this method returns the {@code name}
     * parameter.  It does nothing else for
     * Standard MBeans.  For MXBeans, it records the {@code MBeanServer}
     * and {@code ObjectName} parameters so they can be used to translate
     * inter-MXBean references.</p>
     *
     * <p>It is good practice for a subclass that overrides this method
     * to call the overridden method via {@code super.preRegister(...)}.
     * This is necessary if this object is an MXBean that is referenced
     * by attributes or operations in other MXBeans.</p>
     *
     * <p>
     * <p>允许MBean在注册到MBean服务器之前执行所需的任何操作。如果未指定MBean的名称,则MBean可以为其注册提供名称。如果发生任何异常,MBean将不会在MBean服务器中注册。</p>
     * 
     *  <p>此方法的默认实现返回{@code name}参数。它对标准MBean没有任何作用。
     * 对于MXBeans,它记录{@code MBeanServer}和{@code ObjectName}参数,以便它们可以用于翻译MXBean之间的引用。</p>。
     * 
     *  <p>这是一个很好的做法是覆盖这个方法的子类通过{@code super.preRegister(...)}调用重写的方法。
     * 如果此对象是由其他MXBeans中的属性或操作引用的MXBean,那么这是必需的。</p>。
     * 
     * 
     * @param server The MBean server in which the MBean will be registered.
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
     * @throws IllegalArgumentException if this is an MXBean and
     * {@code name} is null.
     *
     * @throws InstanceAlreadyExistsException if this is an MXBean and
     * it has already been registered under another name (in this
     * MBean Server or another).
     *
     * @throws Exception no other checked exceptions are thrown by
     * this method but {@code Exception} is declared so that subclasses
     * can override the method and throw their own exceptions.
     *
     * @since 1.6
     */
    public ObjectName preRegister(MBeanServer server, ObjectName name)
            throws Exception {
        mbean.register(server, name);
        return name;
    }

    /**
     * <p>Allows the MBean to perform any operations needed after having been
     * registered in the MBean server or after the registration has failed.</p>
     *
     * <p>The default implementation of this method does nothing for
     * Standard MBeans.  For MXBeans, it undoes any work done by
     * {@link #preRegister preRegister} if registration fails.</p>
     *
     * <p>It is good practice for a subclass that overrides this method
     * to call the overridden method via {@code super.postRegister(...)}.
     * This is necessary if this object is an MXBean that is referenced
     * by attributes or operations in other MXBeans.</p>
     *
     * <p>
     *  <p>允许MBean在MBean服务器中注册后或注册失败后执行所需的任何操作。</p>
     * 
     *  <p>此方法的默认实现对标准MBean没有任何作用。对于MXBeans,如果注册失败,它将撤消由{@link #preRegister preRegister}完成的任何工作。</p>
     * 
     *  <p>这是一个好的做法,一个子类,覆盖此方法通过{@code super.postRegister(...)}调用覆盖的方法。
     * 如果此对象是由其他MXBeans中的属性或操作引用的MXBean,那么这是必需的。</p>。
     * 
     * 
     * @param registrationDone Indicates whether or not the MBean has
     * been successfully registered in the MBean server. The value
     * false means that the registration phase has failed.
     *
     * @since 1.6
     */
    public void postRegister(Boolean registrationDone) {
        if (!registrationDone)
            mbean.unregister();
    }

    /**
     * <p>Allows the MBean to perform any operations it needs before
     * being unregistered by the MBean server.</p>
     *
     * <p>The default implementation of this method does nothing.</p>
     *
     * <p>It is good practice for a subclass that overrides this method
     * to call the overridden method via {@code super.preDeregister(...)}.</p>
     *
     * <p>
     *  <p>允许MBean在MBean服务器取消注册之前执行所需的任何操作。</p>
     * 
     * <p>此方法的默认实现不执行任何操作。</p>
     * 
     *  <p>这是一个好的做法,一个子类覆盖此方法通过{@code super.preDeregister(...)}调用覆盖的方法。</p>
     * 
     * 
     * @throws Exception no checked exceptions are throw by this method
     * but {@code Exception} is declared so that subclasses can override
     * this method and throw their own exceptions.
     *
     * @since 1.6
     */
    public void preDeregister() throws Exception {
    }

    /**
     * <p>Allows the MBean to perform any operations needed after having been
     * unregistered in the MBean server.</p>
     *
     * <p>The default implementation of this method does nothing for
     * Standard MBeans.  For MXBeans, it removes any information that
     * was recorded by the {@link #preRegister preRegister} method.</p>
     *
     * <p>It is good practice for a subclass that overrides this method
     * to call the overridden method via {@code super.postRegister(...)}.
     * This is necessary if this object is an MXBean that is referenced
     * by attributes or operations in other MXBeans.</p>
     *
     * <p>
     *  <p>允许MBean在MBean服务器中取消注册后执行所需的任何操作。</p>
     * 
     *  <p>此方法的默认实现对标准MBean没有任何作用。对于MXBeans,它会删除{@link #preRegister preRegister}方法记录的任何信息。</p>
     * 
     *  <p>这是一个好的做法,一个子类,覆盖此方法通过{@code super.postRegister(...)}调用覆盖的方法。
     * 如果此对象是由其他MXBeans中的属性或操作引用的MXBean,那么这是必需的。</p>。
     * 
     * 
     * @since 1.6
     */
    public void postDeregister() {
        mbean.unregister();
    }

    //
    // MBeanInfo immutability
    //

    /**
     * Cached results of previous calls to immutableInfo. This is
     * a WeakHashMap so that we don't prevent a class from being
     * garbage collected just because we know whether its MBeanInfo
     * is immutable.
     * <p>
     *  先前调用immutableInfo的缓存结果。这是一个WeakHashMap,所以我们不会阻止一个类被垃圾收集只是因为我们知道它的MBeanInfo是不可变的。
     * 
     */
    private static final Map<Class<?>, Boolean> mbeanInfoSafeMap =
        new WeakHashMap<Class<?>, Boolean>();

    /**
     * Return true if {@code subclass} is known to preserve the immutability
     * of the {@code MBeanInfo}. The {@code subclass} is considered to have
     * an immutable {@code MBeanInfo} if it does not override any of the
     * getMBeanInfo, getCachedMBeanInfo, cacheMBeanInfo and getNotificationInfo
     * methods.
     * <p>
     *  返回true如果已知{@code subclass}保留{@code MBeanInfo}的不变性。
     * 如果{@code MBeanInfo}不覆盖任何getMBeanInfo,getCachedMBeanInfo,cacheMBeanInfo和getNotificationInfo方法,则{@code子类}
     * 被认为具有不可变的{@code MBeanInfo}。
     */
    static boolean immutableInfo(Class<? extends StandardMBean> subclass) {
        if (subclass == StandardMBean.class ||
            subclass == StandardEmitterMBean.class)
            return true;
        synchronized (mbeanInfoSafeMap) {
            Boolean safe = mbeanInfoSafeMap.get(subclass);
            if (safe == null) {
                try {
                    MBeanInfoSafeAction action =
                        new MBeanInfoSafeAction(subclass);
                    safe = AccessController.doPrivileged(action);
                } catch (Exception e) { // e.g. SecurityException
                    /* We don't know, so we assume it isn't.  */
                    safe = false;
                }
                mbeanInfoSafeMap.put(subclass, safe);
            }
            return safe;
        }
    }

    static boolean overrides(Class<?> subclass, Class<?> superclass,
                             String name, Class<?>... params) {
        for (Class<?> c = subclass; c != superclass; c = c.getSuperclass()) {
            try {
                c.getDeclaredMethod(name, params);
                return true;
            } catch (NoSuchMethodException e) {
                // OK: this class doesn't override it
            }
        }
        return false;
    }

    private static class MBeanInfoSafeAction
            implements PrivilegedAction<Boolean> {

        private final Class<?> subclass;

        MBeanInfoSafeAction(Class<?> subclass) {
            this.subclass = subclass;
        }

        public Boolean run() {
            // Check for "void cacheMBeanInfo(MBeanInfo)" method.
            //
            if (overrides(subclass, StandardMBean.class,
                          "cacheMBeanInfo", MBeanInfo.class))
                return false;

            // Check for "MBeanInfo getCachedMBeanInfo()" method.
            //
            if (overrides(subclass, StandardMBean.class,
                          "getCachedMBeanInfo", (Class<?>[]) null))
                return false;

            // Check for "MBeanInfo getMBeanInfo()" method.
            //
            if (overrides(subclass, StandardMBean.class,
                          "getMBeanInfo", (Class<?>[]) null))
                return false;

            // Check for "MBeanNotificationInfo[] getNotificationInfo()"
            // method.
            //
            // This method is taken into account for the MBeanInfo
            // immutability checks if and only if the given subclass is
            // StandardEmitterMBean itself or can be assigned to
            // StandardEmitterMBean.
            //
            if (StandardEmitterMBean.class.isAssignableFrom(subclass))
                if (overrides(subclass, StandardEmitterMBean.class,
                              "getNotificationInfo", (Class<?>[]) null))
                    return false;
            return true;
        }
    }
}
