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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.rmi.MarshalledObject;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.activation.UnknownGroupException;
import java.rmi.activation.UnknownObjectException;
import java.rmi.server.RMIClassLoader;
import java.rmi.server.UnicastRemoteObject;
import java.security.AccessController;
import sun.security.action.GetIntegerAction;

/**
 * An <code>ActivationGroup</code> is responsible for creating new
 * instances of "activatable" objects in its group, informing its
 * <code>ActivationMonitor</code> when either: its object's become
 * active or inactive, or the group as a whole becomes inactive. <p>
 *
 * An <code>ActivationGroup</code> is <i>initially</i> created in one
 * of several ways: <ul>
 * <li>as a side-effect of creating an <code>ActivationDesc</code>
 *     without an explicit <code>ActivationGroupID</code> for the
 *     first activatable object in the group, or
 * <li>via the <code>ActivationGroup.createGroup</code> method
 * <li>as a side-effect of activating the first object in a group
 *     whose <code>ActivationGroupDesc</code> was only registered.</ul><p>
 *
 * Only the activator can <i>recreate</i> an
 * <code>ActivationGroup</code>.  The activator spawns, as needed, a
 * separate VM (as a child process, for example) for each registered
 * activation group and directs activation requests to the appropriate
 * group. It is implementation specific how VMs are spawned. An
 * activation group is created via the
 * <code>ActivationGroup.createGroup</code> static method. The
 * <code>createGroup</code> method has two requirements on the group
 * to be created: 1) the group must be a concrete subclass of
 * <code>ActivationGroup</code>, and 2) the group must have a
 * constructor that takes two arguments:
 *
 * <ul>
 * <li> the group's <code>ActivationGroupID</code>, and
 * <li> the group's initialization data (in a
 *      <code>java.rmi.MarshalledObject</code>)</ul><p>
 *
 * When created, the default implementation of
 * <code>ActivationGroup</code> will override the system properties
 * with the properties requested when its
 * <code>ActivationGroupDesc</code> was created, and will set a
 * {@link SecurityManager} as the default system
 * security manager.  If your application requires specific properties
 * to be set when objects are activated in the group, the application
 * should create a special <code>Properties</code> object containing
 * these properties, then create an <code>ActivationGroupDesc</code>
 * with the <code>Properties</code> object, and use
 * <code>ActivationGroup.createGroup</code> before creating any
 * <code>ActivationDesc</code>s (before the default
 * <code>ActivationGroupDesc</code> is created).  If your application
 * requires the use of a security manager other than
 * {@link SecurityManager}, in the
 * ActivativationGroupDescriptor properties list you can set
 * <code>java.security.manager</code> property to the name of the security
 * manager you would like to install.
 *
 * <p>
 *  <code> ActivationGroup </code>负责在其组中创建"可激活"对象的新实例,通知其<code> ActivationMonitor </code>：其对象变为活动或非活动状态,
 * 或组作为一个整体变得不活动。
 *  <p>。
 * 
 *  以下几种方式之一创建<code> ActivationGroup </code> </i> </i>：<ul> <li>作为创建<code> ActivationDesc </code>对于组中的第一
 * 个可激活对象,通过<code> ActivationGroup.createGroup </code>方法<li>作为激活组中第一个对象的副作用,<code> ActivationGroupID </code>
 * 其<code> ActivationGroupDesc </code>仅被注册。
 * </ul> <p>。
 * 
 *  只有激活者可以</i>重新创建<code> ActivationGroup </code>。激活器根据需要为每个注册的激活组生成单独的VM(例如作为子进程),并将激活请求引导到适当的组。
 * 它是实现特定如何产生虚拟机。通过<code> ActivationGroup.createGroup </code>静态方法创建激活组。
 *  <code> createGroup </code>方法对要创建的组有两个要求：1)组必须是<code> ActivationGroup </code>的具体子类,以及2)组必须有一个构造函数,两个参
 * 数：。
 * 它是实现特定如何产生虚拟机。通过<code> ActivationGroup.createGroup </code>静态方法创建激活组。
 * 
 * <ul>
 * <li>群组的<code> ActivationGroupID </code>和<li>群组的初始化资料(在<code> java.rmi.MarshalledObject </code>)</ul>。
 * 
 *  创建时,<code> ActivationGroup </code>的默认实现将覆盖具有创建其<code> ActivationGroupDesc </code>时请求的属性的系统属性,并将设置{@link SecurityManager}
 * 作为默认系统安全管理器。
 * 如果应用程序需要在组中激活对象时设置特定属性,应用程序应创建一个包含这些属性的特殊<code> Properties </code>对象,然后使用<code> ActivationGroupDesc </code>
 * 代码>属性</code>对象,并在创建任何<code> ActivationDesc </code>之前(在创建默认的<code> ActivationGroupDesc </code>之前)使用<code>
 *  ActivationGroup.createGroup </code>。
 * 如果您的应用程序需要使用除{@link SecurityManager}之外的安全管理器,则在ActivativationGroupDescriptor属性列表中,您可以将<code> java.sec
 * urity.manager </code>属性设置为您想要的安全管理器的名称安装。
 * 
 * 
 * @author      Ann Wollrath
 * @see         ActivationInstantiator
 * @see         ActivationGroupDesc
 * @see         ActivationGroupID
 * @since       1.2
 */
public abstract class ActivationGroup
        extends UnicastRemoteObject
        implements ActivationInstantiator
{
    /**
    /* <p>
    /* 
     * @serial the group's identifier
     */
    private ActivationGroupID groupID;

    /**
    /* <p>
    /* 
     * @serial the group's monitor
     */
    private ActivationMonitor monitor;

    /**
    /* <p>
    /* 
     * @serial the group's incarnation number
     */
    private long incarnation;

    /** the current activation group for this VM */
    private static ActivationGroup currGroup;
    /** the current group's identifier */
    private static ActivationGroupID currGroupID;
    /** the current group's activation system */
    private static ActivationSystem currSystem;
    /** used to control a group being created only once */
    private static boolean canCreate = true;

    /** indicate compatibility with the Java 2 SDK v1.2 version of class */
    private static final long serialVersionUID = -7696947875314805420L;

    /**
     * Constructs an activation group with the given activation group
     * identifier.  The group is exported as a
     * <code>java.rmi.server.UnicastRemoteObject</code>.
     *
     * <p>
     *  构造具有给定激活组标识符的激活组。该组将导出为<code> java.rmi.server.UnicastRemoteObject </code>。
     * 
     * 
     * @param   groupID the group's identifier
     * @throws  RemoteException if this group could not be exported
     * @throws  UnsupportedOperationException if and only if activation is
     *          not supported by this implementation
     * @since   1.2
     */
    protected ActivationGroup(ActivationGroupID groupID)
        throws RemoteException
    {
        // call super constructor to export the object
        super();
        this.groupID = groupID;
    }

    /**
     * The group's <code>inactiveObject</code> method is called
     * indirectly via a call to the <code>Activatable.inactive</code>
     * method. A remote object implementation must call
     * <code>Activatable</code>'s <code>inactive</code> method when
     * that object deactivates (the object deems that it is no longer
     * active). If the object does not call
     * <code>Activatable.inactive</code> when it deactivates, the
     * object will never be garbage collected since the group keeps
     * strong references to the objects it creates.
     *
     * <p>The group's <code>inactiveObject</code> method unexports the
     * remote object from the RMI runtime so that the object can no
     * longer receive incoming RMI calls. An object will only be unexported
     * if the object has no pending or executing calls.
     * The subclass of <code>ActivationGroup</code> must override this
     * method and unexport the object.
     *
     * <p>After removing the object from the RMI runtime, the group
     * must inform its <code>ActivationMonitor</code> (via the monitor's
     * <code>inactiveObject</code> method) that the remote object is
     * not currently active so that the remote object will be
     * re-activated by the activator upon a subsequent activation
     * request.
     *
     * <p>This method simply informs the group's monitor that the object
     * is inactive.  It is up to the concrete subclass of ActivationGroup
     * to fulfill the additional requirement of unexporting the object. <p>
     *
     * <p>
     * 组的<code> inactiveObject </code>方法通过调用<code> Activatable.inactive </code>方法间接调用。
     * 远程对象实现必须在对象停用(对象认为其不再活动)时调用<code> Activatable </code>的<code> inactive </code>方法。
     * 如果对象在停用时不调用<code> Activatable.inactive </code>,则对象将永远不会被垃圾回收,因为该组保持对其创建的对象的强引用。
     * 
     *  <p>群组的<code> inactiveObject </code>方法会从RMI运行时取消导出远程对象,以使对象无法再接收到传入的RMI调用。如果对象没有挂起或执行调用,则只会取消导出对象。
     *  <code> ActivationGroup </code>的子类必须覆盖此方法并取消导出对象。
     * 
     *  <p>从RMI运行时移除对象后,组必须通过其<code> ActivationMonitor </code>(通过监视器的<code> inactiveObject </code>方法),远程对象当前
     * 不活动,远程对象将在随后的激活请求时被激活器重新激活。
     * 
     *  <p>此方法只是通知组的监视器对象不活动。它取决于ActivationGroup的具体子类,以满足取消导出对象的附加要求。 <p>
     * 
     * 
     * @param id the object's activation identifier
     * @return true if the object was successfully deactivated; otherwise
     *         returns false.
     * @exception UnknownObjectException if object is unknown (may already
     * be inactive)
     * @exception RemoteException if call informing monitor fails
     * @exception ActivationException if group is inactive
     * @since 1.2
     */
    public boolean inactiveObject(ActivationID id)
        throws ActivationException, UnknownObjectException, RemoteException
    {
        getMonitor().inactiveObject(id);
        return true;
    }

    /**
     * The group's <code>activeObject</code> method is called when an
     * object is exported (either by <code>Activatable</code> object
     * construction or an explicit call to
     * <code>Activatable.exportObject</code>. The group must inform its
     * <code>ActivationMonitor</code> that the object is active (via
     * the monitor's <code>activeObject</code> method) if the group
     * hasn't already done so.
     *
     * <p>
     * 当对象被导出时(通过<code> Activatable </code>对象构造或显式调用<code> Activatable.exportObject </code>),组的<code> active
     * Object </code>通知其<code> ActivationMonitor </code>对象是活动的(通过监视器的<code> activeObject </code>方法),如果组尚未这样做
     * 的话。
     * 
     * 
     * @param id the object's identifier
     * @param obj the remote object implementation
     * @exception UnknownObjectException if object is not registered
     * @exception RemoteException if call informing monitor fails
     * @exception ActivationException if group is inactive
     * @since 1.2
     */
    public abstract void activeObject(ActivationID id, Remote obj)
        throws ActivationException, UnknownObjectException, RemoteException;

    /**
     * Create and set the activation group for the current VM.  The
     * activation group can only be set if it is not currently set.
     * An activation group is set using the <code>createGroup</code>
     * method when the <code>Activator</code> initiates the
     * re-creation of an activation group in order to carry out
     * incoming <code>activate</code> requests. A group must first be
     * registered with the <code>ActivationSystem</code> before it can
     * be created via this method.
     *
     * <p>The group class specified by the
     * <code>ActivationGroupDesc</code> must be a concrete subclass of
     * <code>ActivationGroup</code> and have a public constructor that
     * takes two arguments: the <code>ActivationGroupID</code> for the
     * group and the <code>MarshalledObject</code> containing the
     * group's initialization data (obtained from the
     * <code>ActivationGroupDesc</code>.
     *
     * <p>If the group class name specified in the
     * <code>ActivationGroupDesc</code> is <code>null</code>, then
     * this method will behave as if the group descriptor contained
     * the name of the default activation group implementation class.
     *
     * <p>Note that if your application creates its own custom
     * activation group, a security manager must be set for that
     * group.  Otherwise objects cannot be activated in the group.
     * {@link SecurityManager} is set by default.
     *
     * <p>If a security manager is already set in the group VM, this
     * method first calls the security manager's
     * <code>checkSetFactory</code> method.  This could result in a
     * <code>SecurityException</code>. If your application needs to
     * set a different security manager, you must ensure that the
     * policy file specified by the group's
     * <code>ActivationGroupDesc</code> grants the group the necessary
     * permissions to set a new security manager.  (Note: This will be
     * necessary if your group downloads and sets a security manager).
     *
     * <p>After the group is created, the
     * <code>ActivationSystem</code> is informed that the group is
     * active by calling the <code>activeGroup</code> method which
     * returns the <code>ActivationMonitor</code> for the group. The
     * application need not call <code>activeGroup</code>
     * independently since it is taken care of by this method.
     *
     * <p>Once a group is created, subsequent calls to the
     * <code>currentGroupID</code> method will return the identifier
     * for this group until the group becomes inactive.
     *
     * <p>
     *  创建并设置当前虚拟机的激活组。激活组只能在当前未设置的情况下设置。
     * 当<code> Activator </code>启动重新创建激活组以执行传入<code>激活</code>请求时,使用<code> createGroup </code>方法设置激活组。
     * 组必须首先注册到<code> ActivationSystem </code>,然后才能通过此方法创建。
     * 
     *  <p> <code> ActivationGroupDesc </code>指定的组类必须是<code> ActivationGroup </code>的具体子类,并且有一个public构造函数,它接
     * 受两个参数：<code> ActivationGroupID </code>以及包含组的初始化数据(从<code> ActivationGroupDesc </code>获取)的<code> Marsh
     * alledObject </code>。
     * 
     *  <p>如果<code> ActivationGroupDesc </code>中指定的组类名为<code> null </code>,那么此方法的行为将类似于组描述符包含默认激活组实现类的名称。
     * 
     * <p>请注意,如果您的应用程序创建自己的自定义激活组,则必须为该组设置安全管理器。否则,无法在组中激活对象。 {@link SecurityManager}默认设置。
     * 
     *  <p>如果已在组VM中设置了安全管理器,则此方法首先调用安全管理器的<code> checkSetFactory </code>方法。
     * 这可能导致<code> SecurityException </code>。
     * 如果应用程序需要设置不同的安全管理器,则必须确保组的<code> ActivationGroupDesc </code>指定的策略文件向组授予设置新安全管理器所需的权限。
     *  (注意：如果您的组下载并设置了安全管理器,这将是必要的。
     * 
     *  <p>创建组之后,通过调用<code> activeGroup </code>方法通知<code> ActivationSystem </code>该组处于活动状态,该方法返回<code> Activ
     * ationMonitor </code>组。
     * 应用程序不需要独立调用<code> activeGroup </code>,因为它是由这个方法照顾。
     * 
     *  <p>创建组后,对<code> currentGroupID </code>方法的后续调用将返回此组的标识符,直到该组变为非活动状态。
     * 
     * 
     * @param id the activation group's identifier
     * @param desc the activation group's descriptor
     * @param incarnation the group's incarnation number (zero on group's
     * initial creation)
     * @return the activation group for the VM
     * @exception ActivationException if group already exists or if error
     * occurs during group creation
     * @exception SecurityException if permission to create group is denied.
     * (Note: The default implementation of the security manager
     * <code>checkSetFactory</code>
     * method requires the RuntimePermission "setFactory")
     * @exception UnsupportedOperationException if and only if activation is
     * not supported by this implementation
     * @see SecurityManager#checkSetFactory
     * @since 1.2
     */
    public static synchronized
        ActivationGroup createGroup(ActivationGroupID id,
                                    final ActivationGroupDesc desc,
                                    long incarnation)
        throws ActivationException
    {
        SecurityManager security = System.getSecurityManager();
        if (security != null)
            security.checkSetFactory();

        if (currGroup != null)
            throw new ActivationException("group already exists");

        if (canCreate == false)
            throw new ActivationException("group deactivated and " +
                                          "cannot be recreated");

        try {
            // load group's class
            String groupClassName = desc.getClassName();
            Class<? extends ActivationGroup> cl;
            Class<? extends ActivationGroup> defaultGroupClass =
                sun.rmi.server.ActivationGroupImpl.class;
            if (groupClassName == null ||       // see 4252236
                groupClassName.equals(defaultGroupClass.getName()))
            {
                cl = defaultGroupClass;
            } else {
                Class<?> cl0;
                try {
                    cl0 = RMIClassLoader.loadClass(desc.getLocation(),
                                                   groupClassName);
                } catch (Exception ex) {
                    throw new ActivationException(
                        "Could not load group implementation class", ex);
                }
                if (ActivationGroup.class.isAssignableFrom(cl0)) {
                    cl = cl0.asSubclass(ActivationGroup.class);
                } else {
                    throw new ActivationException("group not correct class: " +
                                                  cl0.getName());
                }
            }

            // create group
            Constructor<? extends ActivationGroup> constructor =
                cl.getConstructor(ActivationGroupID.class,
                                  MarshalledObject.class);
            ActivationGroup newGroup =
                constructor.newInstance(id, desc.getData());
            currSystem = id.getSystem();
            newGroup.incarnation = incarnation;
            newGroup.monitor =
                currSystem.activeGroup(id, newGroup, incarnation);
            currGroup = newGroup;
            currGroupID = id;
            canCreate = false;
        } catch (InvocationTargetException e) {
                e.getTargetException().printStackTrace();
                throw new ActivationException("exception in group constructor",
                                              e.getTargetException());

        } catch (ActivationException e) {
            throw e;

        } catch (Exception e) {
            throw new ActivationException("exception creating group", e);
        }

        return currGroup;
    }

    /**
     * Returns the current activation group's identifier.  Returns null
     * if no group is currently active for this VM.
     * <p>
     *  返回当前激活组的标识符。如果此虚拟机当前没有活动组,则返回null。
     * 
     * 
     * @exception UnsupportedOperationException if and only if activation is
     * not supported by this implementation
     * @return the activation group's identifier
     * @since 1.2
     */
    public static synchronized ActivationGroupID currentGroupID() {
        return currGroupID;
    }

    /**
     * Returns the activation group identifier for the VM.  If an
     * activation group does not exist for this VM, a default
     * activation group is created. A group can be created only once,
     * so if a group has already become active and deactivated.
     *
     * <p>
     * 返回VM的激活组标识符。如果此虚拟机不存在激活组,则会创建一个默认激活组。一个组只能创建一次,因此,如果一个组已经变为活动和停用。
     * 
     * 
     * @return the activation group identifier
     * @exception ActivationException if error occurs during group
     * creation, if security manager is not set, or if the group
     * has already been created and deactivated.
     */
    static synchronized ActivationGroupID internalCurrentGroupID()
        throws ActivationException
    {
        if (currGroupID == null)
            throw new ActivationException("nonexistent group");

        return currGroupID;
    }

    /**
     * Set the activation system for the VM.  The activation system can
     * only be set it if no group is currently active. If the activation
     * system is not set via this call, then the <code>getSystem</code>
     * method attempts to obtain a reference to the
     * <code>ActivationSystem</code> by looking up the name
     * "java.rmi.activation.ActivationSystem" in the Activator's
     * registry. By default, the port number used to look up the
     * activation system is defined by
     * <code>ActivationSystem.SYSTEM_PORT</code>. This port can be overridden
     * by setting the property <code>java.rmi.activation.port</code>.
     *
     * <p>If there is a security manager, this method first
     * calls the security manager's <code>checkSetFactory</code> method.
     * This could result in a SecurityException.
     *
     * <p>
     *  设置虚拟机的激活系统。仅当当前没有组处于活动状态时,才能设置激活系统。
     * 如果激活系统没有通过此调用设置,则<code> getSystem </code>方法尝试通过查找名称"java.rmi.activation.ActivationSystem"来获得对<code> A
     * ctivationSystem </code> "在激活者的注册表中。
     *  设置虚拟机的激活系统。仅当当前没有组处于活动状态时,才能设置激活系统。
     * 默认情况下,用于查找激活系统的端口号由<code> ActivationSystem.SYSTEM_PORT </code>定义。
     * 可以通过设置属性<code> java.rmi.activation.port </code>来覆盖此端口。
     * 
     *  <p>如果有安全管理器,此方法首先调用安全管理器的<code> checkSetFactory </code>方法。这可能导致SecurityException。
     * 
     * 
     * @param system remote reference to the <code>ActivationSystem</code>
     * @exception ActivationException if activation system is already set
     * @exception SecurityException if permission to set the activation system is denied.
     * (Note: The default implementation of the security manager
     * <code>checkSetFactory</code>
     * method requires the RuntimePermission "setFactory")
     * @exception UnsupportedOperationException if and only if activation is
     * not supported by this implementation
     * @see #getSystem
     * @see SecurityManager#checkSetFactory
     * @since 1.2
     */
    public static synchronized void setSystem(ActivationSystem system)
        throws ActivationException
    {
        SecurityManager security = System.getSecurityManager();
        if (security != null)
            security.checkSetFactory();

        if (currSystem != null)
            throw new ActivationException("activation system already set");

        currSystem = system;
    }

    /**
     * Returns the activation system for the VM. The activation system
     * may be set by the <code>setSystem</code> method. If the
     * activation system is not set via the <code>setSystem</code>
     * method, then the <code>getSystem</code> method attempts to
     * obtain a reference to the <code>ActivationSystem</code> by
     * looking up the name "java.rmi.activation.ActivationSystem" in
     * the Activator's registry. By default, the port number used to
     * look up the activation system is defined by
     * <code>ActivationSystem.SYSTEM_PORT</code>. This port can be
     * overridden by setting the property
     * <code>java.rmi.activation.port</code>.
     *
     * <p>
     * 返回VM的激活系统。激活系统可以通过<code> setSystem </code>方法设置。
     * 如果激活系统未通过<code> setSystem </code>方法设置,则<code> getSystem </code>方法尝试通过查找名称获得对<code> ActivationSystem </code>
     * 的引用在激活器的注册表中的"java.rmi.activation.ActivationSystem"。
     * 返回VM的激活系统。激活系统可以通过<code> setSystem </code>方法设置。
     * 默认情况下,用于查找激活系统的端口号由<code> ActivationSystem.SYSTEM_PORT </code>定义。
     * 可以通过设置属性<code> java.rmi.activation.port </code>来覆盖此端口。
     * 
     * 
     * @return the activation system for the VM/group
     * @exception ActivationException if activation system cannot be
     *  obtained or is not bound
     * (means that it is not running)
     * @exception UnsupportedOperationException if and only if activation is
     * not supported by this implementation
     * @see #setSystem
     * @since 1.2
     */
    public static synchronized ActivationSystem getSystem()
        throws ActivationException
    {
        if (currSystem == null) {
            try {
                int port = AccessController.doPrivileged(
                    new GetIntegerAction("java.rmi.activation.port",
                                         ActivationSystem.SYSTEM_PORT));
                currSystem = (ActivationSystem)
                    Naming.lookup("//:" + port +
                                  "/java.rmi.activation.ActivationSystem");
            } catch (Exception e) {
                throw new ActivationException(
                    "unable to obtain ActivationSystem", e);
            }
        }
        return currSystem;
    }

    /**
     * This protected method is necessary for subclasses to
     * make the <code>activeObject</code> callback to the group's
     * monitor. The call is simply forwarded to the group's
     * <code>ActivationMonitor</code>.
     *
     * <p>
     *  这个受保护的方法对于子类来说是必要的,以使<code> activeObject </code>回调到组的监视器。该呼叫仅转发到组的<code> ActivationMonitor </code>。
     * 
     * 
     * @param id the object's identifier
     * @param mobj a marshalled object containing the remote object's stub
     * @exception UnknownObjectException if object is not registered
     * @exception RemoteException if call informing monitor fails
     * @exception ActivationException if an activation error occurs
     * @since 1.2
     */
    protected void activeObject(ActivationID id,
                                MarshalledObject<? extends Remote> mobj)
        throws ActivationException, UnknownObjectException, RemoteException
    {
        getMonitor().activeObject(id, mobj);
    }

    /**
     * This protected method is necessary for subclasses to
     * make the <code>inactiveGroup</code> callback to the group's
     * monitor. The call is simply forwarded to the group's
     * <code>ActivationMonitor</code>. Also, the current group
     * for the VM is set to null.
     *
     * <p>
     *  这个受保护的方法对于子类来说是必要的,使<code> inactiveGroup </code>回调到组的监视器。该呼叫仅转发到组的<code> ActivationMonitor </code>。
     * 此外,VM的当前组设置为null。
     * 
     * 
     * @exception UnknownGroupException if group is not registered
     * @exception RemoteException if call informing monitor fails
     * @since 1.2
     */
    protected void inactiveGroup()
        throws UnknownGroupException, RemoteException
    {
        try {
            getMonitor().inactiveGroup(groupID, incarnation);
        } finally {
            destroyGroup();
        }
    }

    /**
     * Returns the monitor for the activation group.
     * <p>
     *  返回激活组的监视器。
     * 
     */
    private ActivationMonitor getMonitor() throws RemoteException {
        synchronized (ActivationGroup.class) {
            if (monitor != null) {
                return monitor;
            }
        }
        throw new RemoteException("monitor not received");
    }

    /**
     * Destroys the current group.
     * <p>
     *  销毁当前组。
     * 
     */
    private static synchronized void destroyGroup() {
        currGroup = null;
        currGroupID = null;
        // NOTE: don't set currSystem to null since it may be needed
    }

    /**
     * Returns the current group for the VM.
     * <p>
     *  返回VM的当前组。
     * 
     * @exception ActivationException if current group is null (not active)
     */
    static synchronized ActivationGroup currentGroup()
        throws ActivationException
    {
        if (currGroup == null) {
            throw new ActivationException("group is not active");
        }
        return currGroup;
    }

}
