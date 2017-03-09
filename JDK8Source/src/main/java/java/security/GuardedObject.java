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

package java.security;

/**
 * A GuardedObject is an object that is used to protect access to
 * another object.
 *
 * <p>A GuardedObject encapsulates a target object and a Guard object,
 * such that access to the target object is possible
 * only if the Guard object allows it.
 * Once an object is encapsulated by a GuardedObject,
 * access to that object is controlled by the {@code getObject}
 * method, which invokes the
 * {@code checkGuard} method on the Guard object that is
 * guarding access. If access is not allowed,
 * an exception is thrown.
 *
 * <p>
 *  GuardedObject是用于保护对另一个对象的访问的对象。
 * 
 *  <p> GuardedObject封装了目标对象和Guard对象,因此只有Guard对象允许它才可以访问目标对象。
 * 一旦一个对象被GuardedObject封装,对该对象的访问由{@code getObject}方法控制,该方法调用Guard对象的{@code checkGuard}方法,该方法保护访问。
 * 如果不允许访问,则抛出异常。
 * 
 * 
 * @see Guard
 * @see Permission
 *
 * @author Roland Schemers
 * @author Li Gong
 */

public class GuardedObject implements java.io.Serializable {

    private static final long serialVersionUID = -5240450096227834308L;

    private Object object; // the object we are guarding
    private Guard guard;   // the guard

    /**
     * Constructs a GuardedObject using the specified object and guard.
     * If the Guard object is null, then no restrictions will
     * be placed on who can access the object.
     *
     * <p>
     *  使用指定的对象和保护构造一个GuardedObject。如果Guard对象为null,则不会对谁可以访问对象施加任何限制。
     * 
     * 
     * @param object the object to be guarded.
     *
     * @param guard the Guard object that guards access to the object.
     */

    public GuardedObject(Object object, Guard guard)
    {
        this.guard = guard;
        this.object = object;
    }

    /**
     * Retrieves the guarded object, or throws an exception if access
     * to the guarded object is denied by the guard.
     *
     * <p>
     *  检索受保护的对象,如果守卫拒绝对受保护对象的访问,则抛出异常。
     * 
     * 
     * @return the guarded object.
     *
     * @exception SecurityException if access to the guarded object is
     * denied.
     */
    public Object getObject()
        throws SecurityException
    {
        if (guard != null)
            guard.checkGuard(object);

        return object;
    }

    /**
     * Writes this object out to a stream (i.e., serializes it).
     * We check the guard if there is one.
     * <p>
     *  将此对象写入流(即,将其序列化)。如果有一个,我们检查看守。
     */
    private void writeObject(java.io.ObjectOutputStream oos)
        throws java.io.IOException
    {
        if (guard != null)
            guard.checkGuard(object);

        oos.defaultWriteObject();
    }
}
