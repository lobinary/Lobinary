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

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.rmi.MarshalledObject;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.UnmarshalException;
import java.rmi.server.RemoteObject;
import java.rmi.server.RemoteObjectInvocationHandler;
import java.rmi.server.RemoteRef;
import java.rmi.server.UID;

/**
 * Activation makes use of special identifiers to denote remote
 * objects that can be activated over time. An activation identifier
 * (an instance of the class <code>ActivationID</code>) contains several
 * pieces of information needed for activating an object:
 * <ul>
 * <li> a remote reference to the object's activator (a {@link
 * java.rmi.server.RemoteRef RemoteRef}
 * instance), and
 * <li> a unique identifier (a {@link java.rmi.server.UID UID}
 * instance) for the object. </ul> <p>
 *
 * An activation identifier for an object can be obtained by registering
 * an object with the activation system. Registration is accomplished
 * in a few ways: <ul>
 * <li>via the <code>Activatable.register</code> method
 * <li>via the first <code>Activatable</code> constructor (that takes
 * three arguments and both registers and exports the object, and
 * <li>via the first <code>Activatable.exportObject</code> method
 * that takes the activation descriptor, object and port as arguments;
 * this method both registers and exports the object. </ul>
 *
 * <p>
 *  激活使用特殊标识符表示可以随时间激活的远程对象。激活标识符(<code> ActivationID </code>类的实例)包含激活对象所需的几个信息：
 * <ul>
 *  <li>对象激活程序的远程引用({@link java.rmi.server.RemoteRef RemoteRef}实例)和<li>唯一标识符({@link java.rmi.server.UID UID}
 * 实例)为对象。
 *  </ul> <p>。
 * 
 *  可以通过向激活系统注册对象来获得对象的激活标识符。
 * 注册通过第一个<code> Activatable </code>构造函数(有三个参数和两个寄存器)通过<code> Activatable.register </code>方法<li>以几种方式完成注
 * 册：<ul> <li>并通过以激活描述符,对象和端口作为参数的第一个<code> Activatable.exportObject </code>方法导出对象,</ul>。
 *  可以通过向激活系统注册对象来获得对象的激活标识符。
 * 
 * 
 * @author      Ann Wollrath
 * @see         Activatable
 * @since       1.2
 */
public class ActivationID implements Serializable {
    /**
     * the object's activator
     * <p>
     *  对象的激活器
     * 
     */
    private transient Activator activator;

    /**
     * the object's unique id
     * <p>
     *  对象的唯一ID
     * 
     */
    private transient UID uid = new UID();

    /** indicate compatibility with the Java 2 SDK v1.2 version of class */
    private static final long serialVersionUID = -4608673054848209235L;

    /**
     * The constructor for <code>ActivationID</code> takes a single
     * argument, activator, that specifies a remote reference to the
     * activator responsible for activating the object associated with
     * this identifier. An instance of <code>ActivationID</code> is globally
     * unique.
     *
     * <p>
     *  <code> ActivationID </code>的构造函数采用单个参数,激活器,其指定对激活与该标识符相关联的对象的激活器的远程引用。
     *  <code> ActivationID </code>的实例是全局唯一的。
     * 
     * 
     * @param activator reference to the activator responsible for
     * activating the object
     * @throws UnsupportedOperationException if and only if activation is
     *         not supported by this implementation
     * @since 1.2
     */
    public ActivationID(Activator activator) {
        this.activator = activator;
    }

    /**
     * Activate the object for this id.
     *
     * <p>
     *  激活此ID的对象。
     * 
     * 
     * @param force if true, forces the activator to contact the group
     * when activating the object (instead of returning a cached reference);
     * if false, returning a cached value is acceptable.
     * @return the reference to the active remote object
     * @exception ActivationException if activation fails
     * @exception UnknownObjectException if the object is unknown
     * @exception RemoteException if remote call fails
     * @since 1.2
     */
    public Remote activate(boolean force)
        throws ActivationException, UnknownObjectException, RemoteException
    {
        try {
            MarshalledObject<? extends Remote> mobj =
                activator.activate(this, force);
            return mobj.get();
        } catch (RemoteException e) {
            throw e;
        } catch (IOException e) {
            throw new UnmarshalException("activation failed", e);
        } catch (ClassNotFoundException e) {
            throw new UnmarshalException("activation failed", e);
        }

    }

    /**
     * Returns a hashcode for the activation id.  Two identifiers that
     * refer to the same remote object will have the same hash code.
     *
     * <p>
     * 返回激活ID的哈希码。引用同一远程对象的两个标识符将具有相同的哈希码。
     * 
     * 
     * @see java.util.Hashtable
     * @since 1.2
     */
    public int hashCode() {
        return uid.hashCode();
    }

    /**
     * Compares two activation ids for content equality.
     * Returns true if both of the following conditions are true:
     * 1) the unique identifiers equivalent (by content), and
     * 2) the activator specified in each identifier
     *    refers to the same remote object.
     *
     * <p>
     *  比较两个激活标识以获取内容相等性。如果以下两个条件都为真,则返回true：1)唯一标识符等效(按内容),2)每个标识符中指定的激活程序引用同一远程对象。
     * 
     * 
     * @param   obj     the Object to compare with
     * @return  true if these Objects are equal; false otherwise.
     * @see             java.util.Hashtable
     * @since 1.2
     */
    public boolean equals(Object obj) {
        if (obj instanceof ActivationID) {
            ActivationID id = (ActivationID) obj;
            return (uid.equals(id.uid) && activator.equals(id.activator));
        } else {
            return false;
        }
    }

    /**
     * <code>writeObject</code> for custom serialization.
     *
     * <p>This method writes this object's serialized form for
     * this class as follows:
     *
     * <p>The <code>writeObject</code> method is invoked on
     * <code>out</code> passing this object's unique identifier
     * (a {@link java.rmi.server.UID UID} instance) as the argument.
     *
     * <p>Next, the {@link
     * java.rmi.server.RemoteRef#getRefClass(java.io.ObjectOutput)
     * getRefClass} method is invoked on the activator's
     * <code>RemoteRef</code> instance to obtain its external ref
     * type name.  Next, the <code>writeUTF</code> method is
     * invoked on <code>out</code> with the value returned by
     * <code>getRefClass</code>, and then the
     * <code>writeExternal</code> method is invoked on the
     * <code>RemoteRef</code> instance passing <code>out</code>
     * as the argument.
     *
     * <p>
     *  <code> writeObject </code>用于自定义序列化。
     * 
     *  <p>此方法将此对象的序列化表单写入此类,如下所示：
     * 
     *  <p> <code> writeObject </code>方法在传递此对象的唯一标识符({@link java.rmi.server.UID UID}实例)作为参数的<code> out </code>
     * 上调用。
     * 
     *  <p>接下来,在激活器的<code> RemoteRef </code>实例上调用{@link java.rmi.server.RemoteRef#getRefClass(java.io.ObjectOutput)getRefClass}
     * 方法以获取其外部引用类型名称。
     * 接下来,使用<code> getRefClass </code>返回的值在<code> out </code>上调用<code> writeUTF </code>方法,然后<code> writeExt
     * ernal </code>在<code> RemoteRef </code>实例上传递<code> out </code>作为参数时调用。
     * 
     * 
     * @serialData The serialized data for this class comprises a
     * <code>java.rmi.server.UID</code> (written with
     * <code>ObjectOutput.writeObject</code>) followed by the
     * external ref type name of the activator's
     * <code>RemoteRef</code> instance (a string written with
     * <code>ObjectOutput.writeUTF</code>), followed by the
     * external form of the <code>RemoteRef</code> instance as
     * written by its <code>writeExternal</code> method.
     *
     * <p>The external ref type name of the
     * <code>RemoteRef</Code> instance is
     * determined using the definitions of external ref type
     * names specified in the {@link java.rmi.server.RemoteObject
     * RemoteObject} <code>writeObject</code> method
     * <b>serialData</b> specification.  Similarly, the data
     * written by the <code>writeExternal</code> method and read
     * by the <code>readExternal</code> method of
     * <code>RemoteRef</code> implementation classes
     * corresponding to each of the defined external ref type
     * names is specified in the {@link
     * java.rmi.server.RemoteObject RemoteObject}
     * <code>writeObject</code> method <b>serialData</b>
     * specification.
     **/
    private void writeObject(ObjectOutputStream out)
        throws IOException, ClassNotFoundException
    {
        out.writeObject(uid);

        RemoteRef ref;
        if (activator instanceof RemoteObject) {
            ref = ((RemoteObject) activator).getRef();
        } else if (Proxy.isProxyClass(activator.getClass())) {
            InvocationHandler handler = Proxy.getInvocationHandler(activator);
            if (!(handler instanceof RemoteObjectInvocationHandler)) {
                throw new InvalidObjectException(
                    "unexpected invocation handler");
            }
            ref = ((RemoteObjectInvocationHandler) handler).getRef();

        } else {
            throw new InvalidObjectException("unexpected activator type");
        }
        out.writeUTF(ref.getRefClass(out));
        ref.writeExternal(out);
    }

    /**
     * <code>readObject</code> for custom serialization.
     *
     * <p>This method reads this object's serialized form for this
     * class as follows:
     *
     * <p>The <code>readObject</code> method is invoked on
     * <code>in</code> to read this object's unique identifier
     * (a {@link java.rmi.server.UID UID} instance).
     *
     * <p>Next, the <code>readUTF</code> method is invoked on
     * <code>in</code> to read the external ref type name of the
     * <code>RemoteRef</code> instance for this object's
     * activator.  Next, the <code>RemoteRef</code>
     * instance is created of an implementation-specific class
     * corresponding to the external ref type name (returned by
     * <code>readUTF</code>), and the <code>readExternal</code>
     * method is invoked on that <code>RemoteRef</code> instance
     * to read the external form corresponding to the external
     * ref type name.
     *
     * <p>Note: If the external ref type name is
     * <code>"UnicastRef"</code>, <code>"UnicastServerRef"</code>,
     * <code>"UnicastRef2"</code>, <code>"UnicastServerRef2"</code>,
     * or <code>"ActivatableRef"</code>, a corresponding
     * implementation-specific class must be found, and its
     * <code>readExternal</code> method must read the serial data
     * for that external ref type name as specified to be written
     * in the <b>serialData</b> documentation for this class.
     * If the external ref type name is any other string (of non-zero
     * length), a <code>ClassNotFoundException</code> will be thrown,
     * unless the implementation provides an implementation-specific
     * class corresponding to that external ref type name, in which
     * case the <code>RemoteRef</code> will be an instance of
     * that implementation-specific class.
     * <p>
     *  <code> readObject </code>用于自定义序列化。
     * 
     *  <p>此方法读取此对象的此类的序列化形式,如下所示：
     * 
     *  <p>在<code>中的<code>中调用<code> readObject </code>方法来读取此对象的唯一标识符({@link java.rmi.server.UID UID}实例)。
     * 
     * <p>接下来,在</code>中的<code>上调用<code> readUTF </code>方法来读取该对象的激活程序的<code> RemoteRef </code>实例的外部引用类型名称。
     * 接下来,创建对应于外部引用类型名称(由<code> readUTF </code>返回)和<code> readExternal </code>的实现特定类的<code> RemoteRef </code>
     * 方法在<code> RemoteRef </code>实例上被调用,以读取与外部引用类型名称相对应的外部表单。
     */
    private void readObject(ObjectInputStream in)
        throws IOException, ClassNotFoundException
    {
        uid = (UID)in.readObject();

        try {
            Class<? extends RemoteRef> refClass =
                Class.forName(RemoteRef.packagePrefix + "." + in.readUTF())
                .asSubclass(RemoteRef.class);
            RemoteRef ref = refClass.newInstance();
            ref.readExternal(in);
            activator = (Activator)
                Proxy.newProxyInstance(null,
                                       new Class<?>[] { Activator.class },
                                       new RemoteObjectInvocationHandler(ref));

        } catch (InstantiationException e) {
            throw (IOException)
                new InvalidObjectException(
                    "Unable to create remote reference").initCause(e);
        } catch (IllegalAccessException e) {
            throw (IOException)
                new InvalidObjectException(
                    "Unable to create remote reference").initCause(e);
        }
    }
}
