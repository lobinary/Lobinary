/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.rmi.activation;

import java.io.Serializable;
import java.rmi.MarshalledObject;

/**
 * An activation descriptor contains the information necessary to
 * activate an object: <ul>
 * <li> the object's group identifier,
 * <li> the object's fully-qualified class name,
 * <li> the object's code location (the location of the class), a codebase URL
 * path,
 * <li> the object's restart "mode", and,
 * <li> a "marshalled" object that can contain object specific
 * initialization data. </ul>
 *
 * <p>A descriptor registered with the activation system can be used to
 * recreate/activate the object specified by the descriptor. The
 * <code>MarshalledObject</code> in the object's descriptor is passed
 * as the second argument to the remote object's constructor for
 * object to use during reinitialization/activation.
 *
 * <p>
 *  激活描述符包含激活对象所需的信息：<ul> <li>对象的组标识符,<li>对象的完全限定类名,<li>对象的代码位置(类的位置)代码库URL路径,<li>对象的重新启动"模式",<li>可包含对象特
 * 定初始化数据的"已编组"对象。
 *  </ul>。
 * 
 *  <p>使用激活系统注册的描述符可用于重新创建/激活描述符指定的对象。
 * 对象描述符中的<code> MarshalledObject </code>作为第二个参数传递给远程对象的构造函数,以便在重新初始化/激活期间使用对象。
 * 
 * 
 * @author      Ann Wollrath
 * @since       1.2
 * @see         java.rmi.activation.Activatable
 */
public final class ActivationDesc implements Serializable {

    /**
    /* <p>
    /* 
     * @serial the group's identifier
     */
    private ActivationGroupID groupID;

    /**
    /* <p>
    /* 
     * @serial the object's class name
     */
    private String className;

    /**
    /* <p>
    /* 
     * @serial the object's code location
     */
    private String location;

    /**
    /* <p>
    /* 
     * @serial the object's initialization data
     */
    private MarshalledObject<?> data;

    /**
    /* <p>
    /* 
     * @serial indicates whether the object should be restarted
     */
    private boolean restart;

    /** indicate compatibility with the Java 2 SDK v1.2 version of class */
    private static final long serialVersionUID = 7455834104417690957L;

    /**
     * Constructs an object descriptor for an object whose class name
     * is <code>className</code>, that can be loaded from the
     * code <code>location</code> and whose initialization
     * information is <code>data</code>. If this form of the constructor
     * is used, the <code>groupID</code> defaults to the current id for
     * <code>ActivationGroup</code> for this VM. All objects with the
     * same <code>ActivationGroupID</code> are activated in the same VM.
     *
     * <p>Note that objects specified by a descriptor created with this
     * constructor will only be activated on demand (by default, the restart
     * mode is <code>false</code>).  If an activatable object requires restart
     * services, use one of the <code>ActivationDesc</code> constructors that
     * takes a boolean parameter, <code>restart</code>.
     *
     * <p> This constructor will throw <code>ActivationException</code> if
     * there is no current activation group for this VM.  To create an
     * <code>ActivationGroup</code> use the
     * <code>ActivationGroup.createGroup</code> method.
     *
     * <p>
     *  为类名为<code> className </code>的对象构造对象描述符,该对象可以从代码<code> location </code>加载,并且其初始化信息为<code> data </code>
     * 。
     * 如果使用此形式的构造函数,则<code> groupID </code>默认为此VM的<code> ActivationGroup </code>的当前ID。
     * 具有相同<code> ActivationGroupID </code>的所有对象都在同一虚拟机中激活。
     * 
     * <p>请注意,使用此构造函数创建的描述符指定的对象只会在需要时激活(默认情况下,重新启动模式为<code> false </code>)。
     * 如果可激活对象需要重新启动服务,请使用带有布尔参数<code> restart </code>的<code> ActivationDesc </code>构造函数之一。
     * 
     *  <p>如果此虚拟机没有当前激活组,此构造函数将抛出<code> ActivationException </code>。
     * 要创建<code> ActivationGroup </code>,请使用<code> ActivationGroup.createGroup </code>方法。
     * 
     * 
     * @param className the object's fully package qualified class name
     * @param location the object's code location (from where the class is
     * loaded)
     * @param data the object's initialization (activation) data contained
     * in marshalled form.
     * @exception ActivationException if the current group is nonexistent
     * @exception UnsupportedOperationException if and only if activation is
     * not supported by this implementation
     * @since 1.2
     */
    public ActivationDesc(String className,
                          String location,
                          MarshalledObject<?> data)
        throws ActivationException
    {
        this(ActivationGroup.internalCurrentGroupID(),
             className, location, data, false);
    }

    /**
     * Constructs an object descriptor for an object whose class name
     * is <code>className</code>, that can be loaded from the
     * code <code>location</code> and whose initialization
     * information is <code>data</code>. If this form of the constructor
     * is used, the <code>groupID</code> defaults to the current id for
     * <code>ActivationGroup</code> for this VM. All objects with the
     * same <code>ActivationGroupID</code> are activated in the same VM.
     *
     * <p>This constructor will throw <code>ActivationException</code> if
     * there is no current activation group for this VM.  To create an
     * <code>ActivationGroup</code> use the
     * <code>ActivationGroup.createGroup</code> method.
     *
     * <p>
     *  为类名为<code> className </code>的对象构造对象描述符,该对象可以从代码<code> location </code>加载,并且其初始化信息为<code> data </code>
     * 。
     * 如果使用此形式的构造函数,则<code> groupID </code>默认为此VM的<code> ActivationGroup </code>的当前ID。
     * 具有相同<code> ActivationGroupID </code>的所有对象都在同一虚拟机中激活。
     * 
     *  <p>如果此虚拟机没有当前激活组,此构造函数将抛出<code> ActivationException </code>。
     * 要创建<code> ActivationGroup </code>,请使用<code> ActivationGroup.createGroup </code>方法。
     * 
     * 
     * @param className the object's fully package qualified class name
     * @param location the object's code location (from where the class is
     * loaded)
     * @param data the object's initialization (activation) data contained
     * in marshalled form.
     * @param restart if true, the object is restarted (reactivated) when
     * either the activator is restarted or the object's activation group
     * is restarted after an unexpected crash; if false, the object is only
     * activated on demand.  Specifying <code>restart</code> to be
     * <code>true</code> does not force an initial immediate activation of
     * a newly registered object;  initial activation is lazy.
     * @exception ActivationException if the current group is nonexistent
     * @exception UnsupportedOperationException if and only if activation is
     * not supported by this implementation
     * @since 1.2
     */
    public ActivationDesc(String className,
                          String location,
                          MarshalledObject<?> data,
                          boolean restart)
        throws ActivationException
    {
        this(ActivationGroup.internalCurrentGroupID(),
             className, location, data, restart);
    }

    /**
     * Constructs an object descriptor for an object whose class name
     * is <code>className</code> that can be loaded from the
     * code <code>location</code> and whose initialization
     * information is <code>data</code>. All objects with the same
     * <code>groupID</code> are activated in the same Java VM.
     *
     * <p>Note that objects specified by a descriptor created with this
     * constructor will only be activated on demand (by default, the restart
     * mode is <code>false</code>).  If an activatable object requires restart
     * services, use one of the <code>ActivationDesc</code> constructors that
     * takes a boolean parameter, <code>restart</code>.
     *
     * <p>
     *  为其类名为<code> className </code>的对象构造对象描述符,该对象可以从代码<code> location </code>加载,并且其初始化信息为<code> data </code>
     * 。
     * 具有相同<code> groupID </code>的所有对象在同一个Java VM中激活。
     * 
     * <p>请注意,使用此构造函数创建的描述符指定的对象只会在需要时激活(默认情况下,重新启动模式为<code> false </code>)。
     * 如果可激活对象需要重新启动服务,请使用带有布尔参数<code> restart </code>的<code> ActivationDesc </code>构造函数之一。
     * 
     * 
     * @param groupID the group's identifier (obtained from registering
     * <code>ActivationSystem.registerGroup</code> method). The group
     * indicates the VM in which the object should be activated.
     * @param className the object's fully package-qualified class name
     * @param location the object's code location (from where the class is
     * loaded)
     * @param data  the object's initialization (activation) data contained
     * in marshalled form.
     * @exception IllegalArgumentException if <code>groupID</code> is null
     * @exception UnsupportedOperationException if and only if activation is
     * not supported by this implementation
     * @since 1.2
     */
    public ActivationDesc(ActivationGroupID groupID,
                          String className,
                          String location,
                          MarshalledObject<?> data)
    {
        this(groupID, className, location, data, false);
    }

    /**
     * Constructs an object descriptor for an object whose class name
     * is <code>className</code> that can be loaded from the
     * code <code>location</code> and whose initialization
     * information is <code>data</code>. All objects with the same
     * <code>groupID</code> are activated in the same Java VM.
     *
     * <p>
     *  为其类名为<code> className </code>的对象构造对象描述符,该对象可以从代码<code> location </code>加载,并且其初始化信息为<code> data </code>
     * 。
     * 具有相同<code> groupID </code>的所有对象在同一个Java VM中激活。
     * 
     * 
     * @param groupID the group's identifier (obtained from registering
     * <code>ActivationSystem.registerGroup</code> method). The group
     * indicates the VM in which the object should be activated.
     * @param className the object's fully package-qualified class name
     * @param location the object's code location (from where the class is
     * loaded)
     * @param data  the object's initialization (activation) data contained
     * in marshalled form.
     * @param restart if true, the object is restarted (reactivated) when
     * either the activator is restarted or the object's activation group
     * is restarted after an unexpected crash; if false, the object is only
     * activated on demand.  Specifying <code>restart</code> to be
     * <code>true</code> does not force an initial immediate activation of
     * a newly registered object;  initial activation is lazy.
     * @exception IllegalArgumentException if <code>groupID</code> is null
     * @exception UnsupportedOperationException if and only if activation is
     * not supported by this implementation
     * @since 1.2
     */
    public ActivationDesc(ActivationGroupID groupID,
                          String className,
                          String location,
                          MarshalledObject<?> data,
                          boolean restart)
    {
        if (groupID == null)
            throw new IllegalArgumentException("groupID can't be null");
        this.groupID = groupID;
        this.className = className;
        this.location = location;
        this.data = data;
        this.restart = restart;
    }

    /**
     * Returns the group identifier for the object specified by this
     * descriptor. A group provides a way to aggregate objects into a
     * single Java virtual machine. RMI creates/activates objects with
     * the same <code>groupID</code> in the same virtual machine.
     *
     * <p>
     *  返回此描述符指定的对象的组标识符。组提供了将对象聚合到单个Java虚拟机中的方法。 RMI在同一虚拟机中使用相同的<code> groupID </code>创建/激活对象。
     * 
     * 
     * @return the group identifier
     * @since 1.2
     */
    public ActivationGroupID getGroupID() {
        return groupID;
    }

    /**
     * Returns the class name for the object specified by this
     * descriptor.
     * <p>
     *  返回此描述符指定的对象的类名。
     * 
     * 
     * @return the class name
     * @since 1.2
     */
    public String getClassName() {
        return className;
    }

    /**
     * Returns the code location for the object specified by
     * this descriptor.
     * <p>
     *  返回此描述符指定的对象的代码位置。
     * 
     * 
     * @return the code location
     * @since 1.2
     */
    public String getLocation() {
        return location;
    }

    /**
     * Returns a "marshalled object" containing intialization/activation
     * data for the object specified by this descriptor.
     * <p>
     *  返回包含由此描述符指定的对象的初始化/激活数据的"编组对象"。
     * 
     * 
     * @return the object specific "initialization" data
     * @since 1.2
     */
    public MarshalledObject<?> getData() {
        return data;
    }

    /**
     * Returns the "restart" mode of the object associated with
     * this activation descriptor.
     *
     * <p>
     *  返回与此激活描述符相关联的对象的"重新启动"模式。
     * 
     * 
     * @return true if the activatable object associated with this
     * activation descriptor is restarted via the activation
     * daemon when either the daemon comes up or the object's group
     * is restarted after an unexpected crash; otherwise it returns false,
     * meaning that the object is only activated on demand via a
     * method call.  Note that if the restart mode is <code>true</code>, the
     * activator does not force an initial immediate activation of
     * a newly registered object;  initial activation is lazy.
     * @since 1.2
     */
    public boolean getRestartMode() {
        return restart;
    }

    /**
     * Compares two activation descriptors for content equality.
     *
     * <p>
     *  比较两个激活描述符的内容相等性。
     * 
     * 
     * @param   obj     the Object to compare with
     * @return  true if these Objects are equal; false otherwise.
     * @see             java.util.Hashtable
     * @since 1.2
     */
    public boolean equals(Object obj) {

        if (obj instanceof ActivationDesc) {
            ActivationDesc desc = (ActivationDesc) obj;
            return
                ((groupID == null ? desc.groupID == null :
                  groupID.equals(desc.groupID)) &&
                 (className == null ? desc.className == null :
                  className.equals(desc.className)) &&
                 (location == null ? desc.location == null:
                  location.equals(desc.location)) &&
                 (data == null ? desc.data == null :
                  data.equals(desc.data)) &&
                 (restart == desc.restart));

        } else {
            return false;
        }
    }

    /**
     * Return the same hashCode for similar <code>ActivationDesc</code>s.
     * <p>
     *  为类似的<code> ActivationDesc </code>返回相同的hashCode。
     * 
     * @return an integer
     * @see java.util.Hashtable
     */
    public int hashCode() {
        return ((location == null
                    ? 0
                    : location.hashCode() << 24) ^
                (groupID == null
                    ? 0
                    : groupID.hashCode() << 16) ^
                (className == null
                    ? 0
                    : className.hashCode() << 9) ^
                (data == null
                    ? 0
                    : data.hashCode() << 1) ^
                (restart
                    ? 1
                    : 0));
    }
}
