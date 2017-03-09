/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, 2013, Oracle and/or its affiliates. All rights reserved.
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

package javax.sql.rowset.serial;

import java.io.*;
import java.lang.reflect.*;
import java.util.Arrays;
import java.util.Vector;
import javax.sql.rowset.RowSetWarning;
import sun.reflect.CallerSensitive;
import sun.reflect.Reflection;
import sun.reflect.misc.ReflectUtil;

/**
 * A serializable mapping in the Java programming language of an SQL
 * <code>JAVA_OBJECT</code> value. Assuming the Java object
 * implements the <code>Serializable</code> interface, this class simply wraps the
 * serialization process.
 * <P>
 * If however, the serialization is not possible because
 * the Java object is not immediately serializable, this class will
 * attempt to serialize all non-static members to permit the object
 * state to be serialized.
 * Static or transient fields cannot be serialized; an attempt to serialize
 * them will result in a <code>SerialException</code> object being thrown.
 *
 * <h3> Thread safety </h3>
 *
 * A SerialJavaObject is not safe for use by multiple concurrent threads.  If a
 * SerialJavaObject is to be used by more than one thread then access to the
 * SerialJavaObject should be controlled by appropriate synchronization.
 *
 * <p>
 *  值为Java <code> JAVA_OBJECT </code>值的Java编程语言中的可序列化映射。
 * 假设Java对象实现<code> Serializable </code>接口,该类简单地包装序列化过程。
 * <P>
 *  然而,如果序列化是不可能的,因为Java对象不是可立即序列化的,这个类将尝试序列化所有非静态成员以允许对象状态被序列化。
 * 静态或瞬态字段不能序列化;试图序列化它们将导致<code> SerialException </code>对象被抛出。
 * 
 *  <h3>线程安全</h3>
 * 
 *  SerialJavaObject不能安全地用于多个并发线程。如果一个SerialJavaObject被多个线程使用,那么对SerialJavaObject的访问应该由适当的同步控制。
 * 
 * 
 * @author Jonathan Bruce
 */
public class SerialJavaObject implements Serializable, Cloneable {

    /**
     * Placeholder for object to be serialized.
     * <p>
     *  要序列化的对象的占位符。
     * 
     */
    private Object obj;


   /**
    * Placeholder for all fields in the <code>JavaObject</code> being serialized.
    * <p>
    *  正在序列化的<code> JavaObject </code>中的所有字段的占位符。
    * 
    */
    private transient Field[] fields;

    /**
     * Constructor for <code>SerialJavaObject</code> helper class.
     * <p>
     *
     * <p>
     *  <code> SerialJavaObject </code>帮助程序类的构造方法。
     * <p>
     * 
     * 
     * @param obj the Java <code>Object</code> to be serialized
     * @throws SerialException if the object is found not to be serializable
     */
    public SerialJavaObject(Object obj) throws SerialException {

        // if any static fields are found, an exception
        // should be thrown


        // get Class. Object instance should always be available
        Class<?> c = obj.getClass();

        // determine if object implements Serializable i/f
        if (!(obj instanceof java.io.Serializable)) {
            setWarning(new RowSetWarning("Warning, the object passed to the constructor does not implement Serializable"));
        }

        // can only determine public fields (obviously). If
        // any of these are static, this should invalidate
        // the action of attempting to persist these fields
        // in a serialized form
        fields = c.getFields();

        if (hasStaticFields(fields)) {
            throw new SerialException("Located static fields in " +
                "object instance. Cannot serialize");
        }

        this.obj = obj;
    }

    /**
     * Returns an <code>Object</code> that is a copy of this <code>SerialJavaObject</code>
     * object.
     *
     * <p>
     *  返回一个<code> Object </code>,它是这个<code> SerialJavaObject </code>对象的副本。
     * 
     * 
     * @return a copy of this <code>SerialJavaObject</code> object as an
     *         <code>Object</code> in the Java programming language
     * @throws SerialException if the instance is corrupt
     */
    public Object getObject() throws SerialException {
        return this.obj;
    }

    /**
     * Returns an array of <code>Field</code> objects that contains each
     * field of the object that this helper class is serializing.
     *
     * <p>
     *  返回一个<code> Field </code>对象的数组,其中包含该帮助类序列化的对象的每个字段。
     * 
     * 
     * @return an array of <code>Field</code> objects
     * @throws SerialException if an error is encountered accessing
     * the serialized object
     * @throws  SecurityException  If a security manager, <i>s</i>, is present
     * and the caller's class loader is not the same as or an
     * ancestor of the class loader for the class of the
     * {@linkplain #getObject object} being serialized
     * and invocation of {@link SecurityManager#checkPackageAccess
     * s.checkPackageAccess()} denies access to the package
     * of that class.
     * @see Class#getFields
     */
    @CallerSensitive
    public Field[] getFields() throws SerialException {
        if (fields != null) {
            Class<?> c = this.obj.getClass();
            SecurityManager sm = System.getSecurityManager();
            if (sm != null) {
                /*
                 * Check if the caller is allowed to access the specified class's package.
                 * If access is denied, throw a SecurityException.
                 * <p>
                 *  检查调用者是否被允许访问指定类的包。如果访问被拒绝,抛出一个SecurityException。
                 * 
                 */
                Class<?> caller = sun.reflect.Reflection.getCallerClass();
                if (ReflectUtil.needsPackageAccessCheck(caller.getClassLoader(),
                                                        c.getClassLoader())) {
                    ReflectUtil.checkPackageAccess(c);
                }
            }
            return c.getFields();
        } else {
            throw new SerialException("SerialJavaObject does not contain" +
                " a serialized object instance");
        }
    }

    /**
     * The identifier that assists in the serialization of this
     * <code>SerialJavaObject</code> object.
     * <p>
     * 有助于序列化<code> SerialJavaObject </code>对象的标识符。
     * 
     */
    static final long serialVersionUID = -1465795139032831023L;

    /**
     * A container for the warnings issued on this <code>SerialJavaObject</code>
     * object. When there are multiple warnings, each warning is chained to the
     * previous warning.
     * <p>
     *  在此<code> SerialJavaObject </code>对象上发出的警告的容器。当有多个警告时,每个警告链接到先前的警告。
     * 
     */
    Vector<RowSetWarning> chain;

    /**
     * Compares this SerialJavaObject to the specified object.
     * The result is {@code true} if and only if the argument
     * is not {@code null} and is a {@code SerialJavaObject}
     * object that is identical to this object
     *
     * <p>
     *  将此SerialJavaObject与指定的对象进行比较。
     * 结果是{@code true}当且仅当参数不是{@code null},并且是与此对象相同的{@code SerialJavaObject}对象。
     * 
     * 
     * @param  o The object to compare this {@code SerialJavaObject} against
     *
     * @return  {@code true} if the given object represents a {@code SerialJavaObject}
     *          equivalent to this SerialJavaObject, {@code false} otherwise
     *
     */
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof SerialJavaObject) {
            SerialJavaObject sjo = (SerialJavaObject) o;
            return obj.equals(sjo.obj);
        }
        return false;
    }

    /**
     * Returns a hash code for this SerialJavaObject. The hash code for a
     * {@code SerialJavaObject} object is taken as the hash code of
     * the {@code Object} it stores
     *
     * <p>
     *  返回此SerialJavaObject的散列代码。 {@code SerialJavaObject}对象的哈希码被作为它存储的{@code Object}的哈希码
     * 
     * 
     * @return  a hash code value for this object.
     */
    public int hashCode() {
        return 31 + obj.hashCode();
    }

    /**
     * Returns a clone of this {@code SerialJavaObject}.
     *
     * <p>
     *  返回此{@code SerialJavaObject}的克隆。
     * 
     * 
     * @return  a clone of this SerialJavaObject
     */

    public Object clone() {
        try {
            SerialJavaObject sjo = (SerialJavaObject) super.clone();
            sjo.fields = Arrays.copyOf(fields, fields.length);
            if (chain != null)
                sjo.chain = new Vector<>(chain);
            return sjo;
        } catch (CloneNotSupportedException ex) {
            // this shouldn't happen, since we are Cloneable
            throw new InternalError();
        }
    }

    /**
     * Registers the given warning.
     * <p>
     *  注册给定的警告。
     * 
     */
    private void setWarning(RowSetWarning e) {
        if (chain == null) {
            chain = new Vector<>();
        }
        chain.add(e);
    }

    /**
     * readObject is called to restore the state of the {@code SerialJavaObject}
     * from a stream.
     * <p>
     *  readObject被调用以从流中恢复{@code SerialJavaObject}的状态。
     * 
     */
    private void readObject(ObjectInputStream s)
            throws IOException, ClassNotFoundException {

        ObjectInputStream.GetField fields1 = s.readFields();
        @SuppressWarnings("unchecked")
        Vector<RowSetWarning> tmp = (Vector<RowSetWarning>)fields1.get("chain", null);
        if (tmp != null)
            chain = new Vector<>(tmp);

        obj = fields1.get("obj", null);
        if (obj != null) {
            fields = obj.getClass().getFields();
            if(hasStaticFields(fields))
                throw new IOException("Located static fields in " +
                "object instance. Cannot serialize");
        } else {
            throw new IOException("Object cannot be null!");
        }

    }

    /**
     * writeObject is called to save the state of the {@code SerialJavaObject}
     * to a stream.
     * <p>
     *  writeObject被调用来将{@code SerialJavaObject}的状态保存到流。
     * 
     */
    private void writeObject(ObjectOutputStream s)
            throws IOException {
        ObjectOutputStream.PutField fields = s.putFields();
        fields.put("obj", obj);
        fields.put("chain", chain);
        s.writeFields();
    }

    /*
     * Check to see if there are any Static Fields in this object
     * <p>
     *  检查此对象中是否存在任何静态字段
     */
    private static boolean hasStaticFields(Field[] fields) {
        for (Field field : fields) {
            if ( field.getModifiers() == Modifier.STATIC) {
                return true;
            }
        }
        return false;
    }
}
