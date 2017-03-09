/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 2011, Oracle and/or its affiliates. All rights reserved.
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

package java.rmi.server;

import java.rmi.Remote;
import java.rmi.NoSuchObjectException;
import java.lang.reflect.Proxy;
import sun.rmi.server.Util;

/**
 * The <code>RemoteObject</code> class implements the
 * <code>java.lang.Object</code> behavior for remote objects.
 * <code>RemoteObject</code> provides the remote semantics of Object by
 * implementing methods for hashCode, equals, and toString.
 *
 * <p>
 *  <code> RemoteObject </code>类实现远程对象的<code> java.lang.Object </code>行为。
 *  <code> RemoteObject </code>通过实现hashCode,equals和toString的方法提供Object的远程语义。
 * 
 * 
 * @author      Ann Wollrath
 * @author      Laird Dornin
 * @author      Peter Jones
 * @since       JDK1.1
 */
public abstract class RemoteObject implements Remote, java.io.Serializable {

    /** The object's remote reference. */
    transient protected RemoteRef ref;

    /** indicate compatibility with JDK 1.1.x version of class */
    private static final long serialVersionUID = -3215090123894869218L;

    /**
     * Creates a remote object.
     * <p>
     *  创建远程对象。
     * 
     */
    protected RemoteObject() {
        ref = null;
    }

    /**
     * Creates a remote object, initialized with the specified remote
     * reference.
     * <p>
     *  创建使用指定的远程引用初始化的远程对象。
     * 
     * 
     * @param newref remote reference
     */
    protected RemoteObject(RemoteRef newref) {
        ref = newref;
    }

    /**
     * Returns the remote reference for the remote object.
     *
     * <p>Note: The object returned from this method may be an instance of
     * an implementation-specific class.  The <code>RemoteObject</code>
     * class ensures serialization portability of its instances' remote
     * references through the behavior of its custom
     * <code>writeObject</code> and <code>readObject</code> methods.  An
     * instance of <code>RemoteRef</code> should not be serialized outside
     * of its <code>RemoteObject</code> wrapper instance or the result may
     * be unportable.
     *
     * <p>
     *  返回远程对象的远程引用。
     * 
     *  <p>注意：从此方法返回的对象可能是特定于实现的类的实例。
     *  <code> RemoteObject </code>类通过其自定义<code> writeObject </code>和<code> readObject </code>方法的行为确保其实例的远程引
     * 用的序列化可移植性。
     *  <p>注意：从此方法返回的对象可能是特定于实现的类的实例。
     *  <code> RemoteRef </code>的实例不应在其<code> RemoteObject </code>包装程序实例之外序列化,否则结果可能不可移植。
     * 
     * 
     * @return remote reference for the remote object
     * @since 1.2
     */
    public RemoteRef getRef() {
        return ref;
    }

    /**
     * Returns the stub for the remote object <code>obj</code> passed
     * as a parameter. This operation is only valid <i>after</i>
     * the object has been exported.
     * <p>
     *  返回作为参数传递的远程对象<code> obj </code>的存根。此操作仅在导出对象</i>之后有效。
     * 
     * 
     * @param obj the remote object whose stub is needed
     * @return the stub for the remote object, <code>obj</code>.
     * @exception NoSuchObjectException if the stub for the
     * remote object could not be found.
     * @since 1.2
     */
    public static Remote toStub(Remote obj) throws NoSuchObjectException {
        if (obj instanceof RemoteStub ||
            (obj != null &&
             Proxy.isProxyClass(obj.getClass()) &&
             Proxy.getInvocationHandler(obj) instanceof
             RemoteObjectInvocationHandler))
        {
            return obj;
        } else {
            return sun.rmi.transport.ObjectTable.getStub(obj);
        }
    }

    /**
     * Returns a hashcode for a remote object.  Two remote object stubs
     * that refer to the same remote object will have the same hash code
     * (in order to support remote objects as keys in hash tables).
     *
     * <p>
     *  返回远程对象的哈希码。引用同一远程对象的两个远程对象存根将具有相同的哈希码(以支持远程对象作为哈希表中的密钥)。
     * 
     * 
     * @see             java.util.Hashtable
     */
    public int hashCode() {
        return (ref == null) ? super.hashCode() : ref.remoteHashCode();
    }

    /**
     * Compares two remote objects for equality.
     * Returns a boolean that indicates whether this remote object is
     * equivalent to the specified Object. This method is used when a
     * remote object is stored in a hashtable.
     * If the specified Object is not itself an instance of RemoteObject,
     * then this method delegates by returning the result of invoking the
     * <code>equals</code> method of its parameter with this remote object
     * as the argument.
     * <p>
     * 比较两个远程对象是否相等。返回一个布尔值,指示此远程对象是否等同于指定的对象。当远程对象存储在散列表中时,将使用此方法。
     * 如果指定的对象本身不是RemoteObject的实例,则该方法通过返回调用其参数的<code> equals </code>方法的结果作为参数来委托。
     * 
     * 
     * @param   obj     the Object to compare with
     * @return  true if these Objects are equal; false otherwise.
     * @see             java.util.Hashtable
     */
    public boolean equals(Object obj) {
        if (obj instanceof RemoteObject) {
            if (ref == null) {
                return obj == this;
            } else {
                return ref.remoteEquals(((RemoteObject)obj).ref);
            }
        } else if (obj != null) {
            /*
             * Fix for 4099660: if object is not an instance of RemoteObject,
             * use the result of its equals method, to support symmetry is a
             * remote object implementation class that does not extend
             * RemoteObject wishes to support equality with its stub objects.
             * <p>
             *  修正为4099660：如果对象不是RemoteObject的实例,使用其equals方法的结果,以支持对称性是一个远程对象实现类,不扩展RemoteObject希望支持与其存根对象的相等性。
             * 
             */
            return obj.equals(this);
        } else {
            return false;
        }
    }

    /**
     * Returns a String that represents the value of this remote object.
     * <p>
     *  返回表示此远程对象的值的字符串。
     * 
     */
    public String toString() {
        String classname = Util.getUnqualifiedName(getClass());
        return (ref == null) ? classname :
            classname + "[" + ref.remoteToString() + "]";
    }

    /**
     * <code>writeObject</code> for custom serialization.
     *
     * <p>This method writes this object's serialized form for this class
     * as follows:
     *
     * <p>The {@link RemoteRef#getRefClass(java.io.ObjectOutput) getRefClass}
     * method is invoked on this object's <code>ref</code> field
     * to obtain its external ref type name.
     * If the value returned by <code>getRefClass</code> was
     * a non-<code>null</code> string of length greater than zero,
     * the <code>writeUTF</code> method is invoked on <code>out</code>
     * with the value returned by <code>getRefClass</code>, and then
     * the <code>writeExternal</code> method is invoked on
     * this object's <code>ref</code> field passing <code>out</code>
     * as the argument; otherwise,
     * the <code>writeUTF</code> method is invoked on <code>out</code>
     * with a zero-length string (<code>""</code>), and then
     * the <code>writeObject</code> method is invoked on <code>out</code>
     * passing this object's <code>ref</code> field as the argument.
     *
     * <p>
     *  <code> writeObject </code>用于自定义序列化。
     * 
     *  <p>此方法将此对象的序列化表单写入此类,如下所示：
     * 
     * <p>在此对象的<code> ref </code>字段上调用{@link RemoteRef#getRefClass(java.io.ObjectOutput)getRefClass}方法以获取其外部
     * 引用类型名称。
     * 如果<code> getRefClass </code>返回的值是长度大于零的非<code> null </code>字符串,则<code> writeUTF </code>方法在<code> / co
     * de>与<code> getRefClass </code>返回的值,然后在该对象的<code> ref </code>字段传递<code> writeExternal </code>代码>作为参数;否
     * 则,使用零长度字符串(<code>""</code>)在<code> out </code>上调用<code> writeUTF </code>方法,然后<code> writeObject <代码>方
     * 法在<code> out </code>上调用,将此对象的<code> ref </code>字段作为参数传递。
     * 
     * 
     * @serialData
     *
     * The serialized data for this class comprises a string (written with
     * <code>ObjectOutput.writeUTF</code>) that is either the external
     * ref type name of the contained <code>RemoteRef</code> instance
     * (the <code>ref</code> field) or a zero-length string, followed by
     * either the external form of the <code>ref</code> field as written by
     * its <code>writeExternal</code> method if the string was of non-zero
     * length, or the serialized form of the <code>ref</code> field as
     * written by passing it to the serialization stream's
     * <code>writeObject</code> if the string was of zero length.
     *
     * <p>If this object is an instance of
     * {@link RemoteStub} or {@link RemoteObjectInvocationHandler}
     * that was returned from any of
     * the <code>UnicastRemoteObject.exportObject</code> methods
     * and custom socket factories are not used,
     * the external ref type name is <code>"UnicastRef"</code>.
     *
     * If this object is an instance of
     * <code>RemoteStub</code> or <code>RemoteObjectInvocationHandler</code>
     * that was returned from any of
     * the <code>UnicastRemoteObject.exportObject</code> methods
     * and custom socket factories are used,
     * the external ref type name is <code>"UnicastRef2"</code>.
     *
     * If this object is an instance of
     * <code>RemoteStub</code> or <code>RemoteObjectInvocationHandler</code>
     * that was returned from any of
     * the <code>java.rmi.activation.Activatable.exportObject</code> methods,
     * the external ref type name is <code>"ActivatableRef"</code>.
     *
     * If this object is an instance of
     * <code>RemoteStub</code> or <code>RemoteObjectInvocationHandler</code>
     * that was returned from
     * the <code>RemoteObject.toStub</code> method (and the argument passed
     * to <code>toStub</code> was not itself a <code>RemoteStub</code>),
     * the external ref type name is a function of how the remote object
     * passed to <code>toStub</code> was exported, as described above.
     *
     * If this object is an instance of
     * <code>RemoteStub</code> or <code>RemoteObjectInvocationHandler</code>
     * that was originally created via deserialization,
     * the external ref type name is the same as that which was read
     * when this object was deserialized.
     *
     * <p>If this object is an instance of
     * <code>java.rmi.server.UnicastRemoteObject</code> that does not
     * use custom socket factories,
     * the external ref type name is <code>"UnicastServerRef"</code>.
     *
     * If this object is an instance of
     * <code>UnicastRemoteObject</code> that does
     * use custom socket factories,
     * the external ref type name is <code>"UnicastServerRef2"</code>.
     *
     * <p>Following is the data that must be written by the
     * <code>writeExternal</code> method and read by the
     * <code>readExternal</code> method of <code>RemoteRef</code>
     * implementation classes that correspond to the each of the
     * defined external ref type names:
     *
     * <p>For <code>"UnicastRef"</code>:
     *
     * <ul>
     *
     * <li>the hostname of the referenced remote object,
     * written by {@link java.io.ObjectOutput#writeUTF(String)}
     *
     * <li>the port of the referenced remote object,
     * written by {@link java.io.ObjectOutput#writeInt(int)}
     *
     * <li>the data written as a result of calling
     * {link java.rmi.server.ObjID#write(java.io.ObjectOutput)}
     * on the <code>ObjID</code> instance contained in the reference
     *
     * <li>the boolean value <code>false</code>,
     * written by {@link java.io.ObjectOutput#writeBoolean(boolean)}
     *
     * </ul>
     *
     * <p>For <code>"UnicastRef2"</code> with a
     * <code>null</code> client socket factory:
     *
     * <ul>
     *
     * <li>the byte value <code>0x00</code>
     * (indicating <code>null</code> client socket factory),
     * written by {@link java.io.ObjectOutput#writeByte(int)}
     *
     * <li>the hostname of the referenced remote object,
     * written by {@link java.io.ObjectOutput#writeUTF(String)}
     *
     * <li>the port of the referenced remote object,
     * written by {@link java.io.ObjectOutput#writeInt(int)}
     *
     * <li>the data written as a result of calling
     * {link java.rmi.server.ObjID#write(java.io.ObjectOutput)}
     * on the <code>ObjID</code> instance contained in the reference
     *
     * <li>the boolean value <code>false</code>,
     * written by {@link java.io.ObjectOutput#writeBoolean(boolean)}
     *
     * </ul>
     *
     * <p>For <code>"UnicastRef2"</code> with a
     * non-<code>null</code> client socket factory:
     *
     * <ul>
     *
     * <li>the byte value <code>0x01</code>
     * (indicating non-<code>null</code> client socket factory),
     * written by {@link java.io.ObjectOutput#writeByte(int)}
     *
     * <li>the hostname of the referenced remote object,
     * written by {@link java.io.ObjectOutput#writeUTF(String)}
     *
     * <li>the port of the referenced remote object,
     * written by {@link java.io.ObjectOutput#writeInt(int)}
     *
     * <li>a client socket factory (object of type
     * <code>java.rmi.server.RMIClientSocketFactory</code>),
     * written by passing it to an invocation of
     * <code>writeObject</code> on the stream instance
     *
     * <li>the data written as a result of calling
     * {link java.rmi.server.ObjID#write(java.io.ObjectOutput)}
     * on the <code>ObjID</code> instance contained in the reference
     *
     * <li>the boolean value <code>false</code>,
     * written by {@link java.io.ObjectOutput#writeBoolean(boolean)}
     *
     * </ul>
     *
     * <p>For <code>"ActivatableRef"</code> with a
     * <code>null</code> nested remote reference:
     *
     * <ul>
     *
     * <li>an instance of
     * <code>java.rmi.activation.ActivationID</code>,
     * written by passing it to an invocation of
     * <code>writeObject</code> on the stream instance
     *
     * <li>a zero-length string (<code>""</code>),
     * written by {@link java.io.ObjectOutput#writeUTF(String)}
     *
     * </ul>
     *
     * <p>For <code>"ActivatableRef"</code> with a
     * non-<code>null</code> nested remote reference:
     *
     * <ul>
     *
     * <li>an instance of
     * <code>java.rmi.activation.ActivationID</code>,
     * written by passing it to an invocation of
     * <code>writeObject</code> on the stream instance
     *
     * <li>the external ref type name of the nested remote reference,
     * which must be <code>"UnicastRef2"</code>,
     * written by {@link java.io.ObjectOutput#writeUTF(String)}
     *
     * <li>the external form of the nested remote reference,
     * written by invoking its <code>writeExternal</code> method
     * with the stream instance
     * (see the description of the external form for
     * <code>"UnicastRef2"</code> above)
     *
     * </ul>
     *
     * <p>For <code>"UnicastServerRef"</code> and
     * <code>"UnicastServerRef2"</code>, no data is written by the
     * <code>writeExternal</code> method or read by the
     * <code>readExternal</code> method.
     */
    private void writeObject(java.io.ObjectOutputStream out)
        throws java.io.IOException, java.lang.ClassNotFoundException
    {
        if (ref == null) {
            throw new java.rmi.MarshalException("Invalid remote object");
        } else {
            String refClassName = ref.getRefClass(out);
            if (refClassName == null || refClassName.length() == 0) {
                /*
                 * No reference class name specified, so serialize
                 * remote reference.
                 * <p>
                 *  没有指定引用类名,所以序列化远程引用。
                 * 
                 */
                out.writeUTF("");
                out.writeObject(ref);
            } else {
                /*
                 * Built-in reference class specified, so delegate
                 * to reference to write out its external form.
                 * <p>
                 *  内置引用类指定,所以委托引用来写出它的外部形式。
                 * 
                 */
                out.writeUTF(refClassName);
                ref.writeExternal(out);
            }
        }
    }

    /**
     * <code>readObject</code> for custom serialization.
     *
     * <p>This method reads this object's serialized form for this class
     * as follows:
     *
     * <p>The <code>readUTF</code> method is invoked on <code>in</code>
     * to read the external ref type name for the <code>RemoteRef</code>
     * instance to be filled in to this object's <code>ref</code> field.
     * If the string returned by <code>readUTF</code> has length zero,
     * the <code>readObject</code> method is invoked on <code>in</code>,
     * and than the value returned by <code>readObject</code> is cast to
     * <code>RemoteRef</code> and this object's <code>ref</code> field is
     * set to that value.
     * Otherwise, this object's <code>ref</code> field is set to a
     * <code>RemoteRef</code> instance that is created of an
     * implementation-specific class corresponding to the external ref
     * type name returned by <code>readUTF</code>, and then
     * the <code>readExternal</code> method is invoked on
     * this object's <code>ref</code> field.
     *
     * <p>If the external ref type name is
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
     * case this object's <code>ref</code> field will be set to an
     * instance of that implementation-specific class.
     * <p>
     *  <code> readObject </code>用于自定义序列化。
     * 
     *  <p>此方法读取此对象的此类的序列化形式,如下所示：
     * 
     * <p>在<code>中的<code>中调用<code> readUTF </code>方法以读取要填充到此对象的<code> RemoteRef </code>实例的外部引用类型名称< code> re
     * f </code>字段。
     * 如果<code> readUTF </code>返回的字符串长度为零,则</code>中的<code>上调用<code> readObject </code>方法,而不是<code> readObjec
     * t < / code>被转换为<code> RemoteRef </code>,并且此对象的<code> ref </code>字段设置为该值。
     * 否则,此对象的<code> ref </code>字段设置为<code> RemoteRef </code>实例,该实例由对应于<code>返回的外部引用类型名称的特定实现类class readUTF 
     * < code>,然后在此对象的<code> ref </code>字段上调用<code> readExternal </code>方法。
     * 
     * <p>如果外部参考类型名称为<code>"UnicastRef"</code>,<code>"UnicastServerRef"</code>,<code>"UnicastRef2"</code>,<code>
     * "UnicastServerRef2" / code>或<code>"ActivatableRef"</code>,必须找到相应的特定于实现的类,并且其<code> readExternal </code>
     */
    private void readObject(java.io.ObjectInputStream in)
        throws java.io.IOException, java.lang.ClassNotFoundException
    {
        String refClassName = in.readUTF();
        if (refClassName == null || refClassName.length() == 0) {
            /*
             * No reference class name specified, so construct
             * remote reference from its serialized form.
             * <p>
             * 方法必须读取指定的外部引用类型名称的串行数据要写入此类的<b> serialData </b>文档中。
             * 如果外部引用类型名称是任何其他字符串(非零长度),将抛出一个<code> ClassNotFoundException </code>,除非实现提供了一个对应于该外部引用类型名称的实现特定类,其中cas
             * e这个对象的<code> ref </code>字段将被设置为该实现特定的类的实例。
             * 
             */
            ref = (RemoteRef) in.readObject();
        } else {
            /*
             * Built-in reference class specified, so delegate to
             * internal reference class to initialize its fields from
             * its external form.
             * <p>
             *  没有指定引用类名,因此从其序列化形式构造远程引用。
             * 
             */
            String internalRefClassName =
                RemoteRef.packagePrefix + "." + refClassName;
            Class<?> refClass = Class.forName(internalRefClassName);
            try {
                ref = (RemoteRef) refClass.newInstance();

                /*
                 * If this step fails, assume we found an internal
                 * class that is not meant to be a serializable ref
                 * type.
                 * <p>
                 *  内置引用类指定,因此委托给内部引用类从其外部形式初始化其字段。
                 * 
                 */
            } catch (InstantiationException e) {
                throw new ClassNotFoundException(internalRefClassName, e);
            } catch (IllegalAccessException e) {
                throw new ClassNotFoundException(internalRefClassName, e);
            } catch (ClassCastException e) {
                throw new ClassNotFoundException(internalRefClassName, e);
            }
            ref.readExternal(in);
        }
    }
}
