/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2001, 2014, Oracle and/or its affiliates. All rights reserved.
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
/*
 * Licensed Materials - Property of IBM
 * RMI-IIOP v1.0
 * Copyright IBM Corp. 1998 1999  All Rights Reserved
 *
 * <p>
 *  许可的材料 -  IBM RMI-IIOP v10的属性版权所有IBM Corp 1998 1999保留所有权利
 * 
 */

package com.sun.corba.se.impl.orbutil;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.DigestOutputStream;
import java.security.AccessController;
import java.security.PrivilegedExceptionAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedAction;

import java.lang.reflect.Modifier;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Constructor;
import java.lang.reflect.Proxy;
import java.lang.reflect.InvocationTargetException;

import java.io.IOException;
import java.io.DataOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.InvalidClassException;
import java.io.Serializable;
import java.io.Externalizable;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Hashtable;

import org.omg.CORBA.ValueMember;

import com.sun.corba.se.impl.io.ValueUtility;
import com.sun.corba.se.impl.io.ObjectStreamClass;

/**
 * This is duplicated here to preserve the JDK 1.3.1FCS behavior
 * of calculating the OMG hash code incorrectly when serialPersistentFields
 * is used, but some of the fields no longer exist in the class itself.
 *
 * We have to duplicate it since we aren't allowed to modify the
 * com.sun.corba.se.impl.io version further, and can't make it
 * public outside of its package for security reasons.
 * <p>
 *  这里重复这里,以保留JDK 131FCS行为,当使用serialPersistentFields时计算OMG哈希代码不正确,但是一些字段不再存在于类本身中
 * 
 *  我们必须复制它,因为我们不允许进一步修改comsuncorbaseimplio版本,并且由于安全原因不能将其公开在其包之外
 * 
 */
/**
 * A ObjectStreamClass_1_3_1 describes a class that can be serialized to a stream
 * or a class that was serialized to a stream.  It contains the name
 * and the serialVersionUID of the class.
 * <br>
 * The ObjectStreamClass_1_3_1 for a specific class loaded in this Java VM can
 * be found using the lookup method.
 *
 * <p>
 * ObjectStreamClass_1_3_1描述了一个可以序列化到流或被序列化到流的类的类。它包含类的名称和serialVersionUID
 * <br>
 *  可以使用lookup方法找到在此Java VM中加载的特定类的ObjectStreamClass_1_3_1
 * 
 * 
 * @author  Roger Riggs
 * @since   JDK1.1
 */
public class ObjectStreamClass_1_3_1 implements java.io.Serializable {

    public static final long kDefaultUID = -1;

    private static Object noArgsList[] = {};
    private static Class<?> noTypesList[] = {};

    private static Hashtable translatedFields;

    /** Find the descriptor for a class that can be serialized.  Null
     * is returned if the specified class does not implement
     * java.io.Serializable or java.io.Externalizable.
     * <p>
     *  如果指定的类不实现javaioSerializable或javaioExternalizable,则返回
     * 
     */
    static final ObjectStreamClass_1_3_1 lookup(Class<?> cl)
    {
        ObjectStreamClass_1_3_1 desc = lookupInternal(cl);
        if (desc.isSerializable() || desc.isExternalizable())
            return desc;
        return null;
    }

    /*
     * Find the class descriptor for the specified class.
     * Package access only so it can be called from ObjectIn/OutStream.
     * <p>
     *  找到指定类的类描述符,只有包访问,所以它可以从ObjectIn / OutStream中调用
     * 
     */
    static ObjectStreamClass_1_3_1 lookupInternal(Class<?> cl)
    {
        /* Synchronize on the hashtable so no two threads will do
         * this at the same time.
         * <p>
         *  这个在同一时间
         * 
         */
        ObjectStreamClass_1_3_1 desc = null;
        synchronized (descriptorFor) {
            /* Find the matching descriptor if it already known */
            desc = findDescriptorFor(cl);
            if (desc != null) {
                return desc;
            }

                /* Check if it's serializable */
                boolean serializable = Serializable.class.isAssignableFrom(cl);
                /* If the class is only Serializable,
                 * lookup the descriptor for the superclass.
                 * <p>
                 *  查找超类的描述符
                 * 
                 */
                ObjectStreamClass_1_3_1 superdesc = null;
                if (serializable) {
                    Class<?> superclass = cl.getSuperclass();
                    if (superclass != null)
                        superdesc = lookup(superclass);
                }

                /* Check if its' externalizable.
                 * If it's Externalizable, clear the serializable flag.
                 * Only one or the other may be set in the protocol.
                 * <p>
                 *  如果它是Externalizable,清除serializable标志在协议中只能设置一个或另一个
                 * 
                 */
                boolean externalizable = false;
                if (serializable) {
                    externalizable =
                        ((superdesc != null) && superdesc.isExternalizable()) ||
                        Externalizable.class.isAssignableFrom(cl);
                    if (externalizable) {
                        serializable = false;
                    }
                }

            /* Create a new version descriptor,
             * it put itself in the known table.
             * <p>
             *  它把自己放在已知的表中
             * 
             */
            desc = new ObjectStreamClass_1_3_1(cl, superdesc,
                                         serializable, externalizable);
        }
        desc.init();
        return desc;
    }

    /**
     * The name of the class described by this descriptor.
     * <p>
     *  此描述符描述的类的名称
     * 
     */
    public final String getName() {
        return name;
    }

    /**
     * Return the serialVersionUID for this class.
     * The serialVersionUID defines a set of classes all with the same name
     * that have evolved from a common root class and agree to be serialized
     * and deserialized using a common format.
     * <p>
     * 返回此类的serialVersionUID serialVersionUID定义了一组类,它们都具有相同的名称,这些类从公共根类演化而来,并且同意使用公共格式进行序列化和反序列化
     * 
     */
    public static final long getSerialVersionUID( java.lang.Class<?> clazz) {
        ObjectStreamClass_1_3_1 theosc = ObjectStreamClass_1_3_1.lookup( clazz );
        if( theosc != null )
        {
                return theosc.getSerialVersionUID( );
        }
        return 0;
    }

    /**
     * Return the serialVersionUID for this class.
     * The serialVersionUID defines a set of classes all with the same name
     * that have evolved from a common root class and agree to be serialized
     * and deserialized using a common format.
     * <p>
     *  返回此类的serialVersionUID serialVersionUID定义了一组类,它们都具有相同的名称,这些类从公共根类演化而来,并且同意使用公共格式进行序列化和反序列化
     * 
     */
    public final long getSerialVersionUID() {
        return suid;
    }

    /**
     * Return the serialVersionUID string for this class.
     * The serialVersionUID defines a set of classes all with the same name
     * that have evolved from a common root class and agree to be serialized
     * and deserialized using a common format.
     * <p>
     *  返回此类的serialVersionUID字符串serialVersionUID定义了一组类,它们具有相同的名称,这些类从公共根类演化而来,并且同意使用公共格式进行序列化和反序列化
     * 
     */
    public final String getSerialVersionUIDStr() {
        if (suidStr == null)
            suidStr = Long.toHexString(suid).toUpperCase();
        return suidStr;
    }

    /**
     * Return the actual (computed) serialVersionUID for this class.
     * <p>
     *  返回此类的实际(计算)serialVersionUID
     * 
     */
    public static final long getActualSerialVersionUID( java.lang.Class<?> clazz )
    {
        ObjectStreamClass_1_3_1 theosc = ObjectStreamClass_1_3_1.lookup( clazz );
        if( theosc != null )
        {
                return theosc.getActualSerialVersionUID( );
        }
        return 0;
    }

    /**
     * Return the actual (computed) serialVersionUID for this class.
     * <p>
     * 返回此类的实际(计算)serialVersionUID
     * 
     */
    public final long getActualSerialVersionUID() {
        return actualSuid;
    }

    /**
     * Return the actual (computed) serialVersionUID for this class.
     * <p>
     *  返回此类的实际(计算)serialVersionUID
     * 
     */
    public final String getActualSerialVersionUIDStr() {
        if (actualSuidStr == null)
            actualSuidStr = Long.toHexString(actualSuid).toUpperCase();
        return actualSuidStr;
    }

    /**
     * Return the class in the local VM that this version is mapped to.
     * Null is returned if there is no corresponding local class.
     * <p>
     *  如果没有相应的本地类,则返回本版本映射到Null的本地VM中的类
     * 
     */
    public final Class<?> forClass() {
        return ofClass;
    }

    /**
     * Return an array of the fields of this serializable class.
     * <p>
     *  返回此可序列化类的字段的数组
     * 
     * 
     * @return an array containing an element for each persistent
     * field of this class. Returns an array of length zero if
     * there are no fields.
     * @since JDK1.2
     */
    public ObjectStreamField[] getFields() {
        // Return a copy so the caller can't change the fields.
        if (fields.length > 0) {
            ObjectStreamField[] dup = new ObjectStreamField[fields.length];
            System.arraycopy(fields, 0, dup, 0, fields.length);
            return dup;
        } else {
            return fields;
        }
    }

    public boolean hasField(ValueMember field){

        for (int i = 0; i < fields.length; i++){
            try{
                if (fields[i].getName().equals(field.name)) {

                    if (fields[i].getSignature().equals(ValueUtility.getSignature(field)))
                        return true;
                }
            }
            catch(Throwable t){}
        }
        return false;
    }

    /* Avoid unnecessary allocations. */
    final ObjectStreamField[] getFieldsNoCopy() {
        return fields;
    }

    /**
     * Get the field of this class by name.
     * <p>
     *  按名称获取此类的字段
     * 
     * 
     * @return The ObjectStreamField object of the named field or null if there
     * is no such named field.
     */
    public final ObjectStreamField getField(String name) {
        /* Binary search of fields by name.
        /* <p>
         */
        for (int i = fields.length-1; i >= 0; i--) {
            if (name.equals(fields[i].getName())) {
                return fields[i];
            }
        }
        return null;
    }

    public Serializable writeReplace(Serializable value) {
        if (writeReplaceObjectMethod != null) {
            try {
                return (Serializable) writeReplaceObjectMethod.invoke(value,noArgsList);
            }
            catch(Throwable t) {
                throw new RuntimeException(t.getMessage());
            }
        }
        else return value;
    }

    public Object readResolve(Object value) {
        if (readResolveObjectMethod != null) {
            try {
                return readResolveObjectMethod.invoke(value,noArgsList);
            }
            catch(Throwable t) {
                throw new RuntimeException(t.getMessage());
            }
        }
        else return value;
    }

    /**
     * Return a string describing this ObjectStreamClass_1_3_1.
     * <p>
     *  返回一个描述此ObjectStreamClass_1_3_1的字符串
     * 
     */
    public final String toString() {
        StringBuffer sb = new StringBuffer();

        sb.append(name);
        sb.append(": static final long serialVersionUID = ");
        sb.append(Long.toString(suid));
        sb.append("L;");
        return sb.toString();
    }

    /*
     * Create a new ObjectStreamClass_1_3_1 from a loaded class.
     * Don't call this directly, call lookup instead.
     * <p>
     *  从加载的类创建一个新的ObjectStreamClass_1_3_1不要直接调用它,而是调用查找
     * 
     */
    private ObjectStreamClass_1_3_1(java.lang.Class<?> cl, ObjectStreamClass_1_3_1 superdesc,
                              boolean serial, boolean extern)
    {
        ofClass = cl;           /* created from this class */

        if (Proxy.isProxyClass(cl)) {
            forProxyClass = true;
        }

        name = cl.getName();
        superclass = superdesc;
        serializable = serial;
        if (!forProxyClass) {
            // proxy classes are never externalizable
            externalizable = extern;
        }

        /*
         * Enter this class in the table of known descriptors.
         * Otherwise, when the fields are read it may recurse
         * trying to find the descriptor for itself.
         * <p>
         *  在已知描述符的表中输入此类否则,当字段被读取时,它可能递归尝试找到它自己的描述符
         * 
         */
        insertDescriptorFor(this);

        /*
         * The remainder of initialization occurs in init(), which is called
         * after the lock on the global class descriptor table has been
         * released.
         * <p>
         *  初始化的剩余部分发生在init()中,init()在全局类描述符表上的锁释放之后调用
         * 
         */
    }

    /*
     * Initialize class descriptor.  This method is only invoked on class
     * descriptors created via calls to lookupInternal().  This method is kept
     * separate from the ObjectStreamClass_1_3_1 constructor so that lookupInternal
     * does not have to hold onto a global class descriptor table lock while the
     * class descriptor is being initialized (see bug 4165204).
     * <p>
     * 初始化类描述符此方法仅在通过调用lookupInternal()创建的类描述符上调用此方法与ObjectStreamClass_1_3_1构造函数保持分离,以便lookupInternal不必在初始化类
     * 描述符时保持全局类描述符表锁定见错误4165204)。
     * 
     */


    private void init() {
      synchronized (lock) {

        final Class<?> cl = ofClass;

        if (fields != null) // already initialized
                return;


        if (!serializable ||
            externalizable ||
            forProxyClass ||
            name.equals("java.lang.String")) {
            fields = NO_FIELDS;
        } else if (serializable) {

            /* Ask for permission to override field access checks.
            /* <p>
             */
            AccessController.doPrivileged(new PrivilegedAction() {
                public Object run() {
                /* Fill in the list of persistent fields.
                 * If it is declared, use the declared serialPersistentFields.
                 * Otherwise, extract the fields from the class itself.
                 * <p>
                 *  如果它被声明,使用声明的serialPersistentFields否则,从类本身提取字段
                 * 
                 */
                try {
                    Field pf = cl.getDeclaredField("serialPersistentFields");
                    // serial bug 7; the serialPersistentFields were not
                    // being read and stored as Accessible bit was not set
                    pf.setAccessible(true);
                    // serial bug 7; need to find if the field is of type
                    // java.io.ObjectStreamField
                    java.io.ObjectStreamField[] f =
                           (java.io.ObjectStreamField[])pf.get(cl);
                    int mods = pf.getModifiers();
                    if ((Modifier.isPrivate(mods)) &&
                        (Modifier.isStatic(mods)) &&
                        (Modifier.isFinal(mods)))
                    {
                        fields = (ObjectStreamField[])translateFields((Object[])pf.get(cl));
                    }
                } catch (NoSuchFieldException e) {
                    fields = null;
                } catch (IllegalAccessException e) {
                    fields = null;
                } catch (IllegalArgumentException e) {
                    fields = null;
                } catch (ClassCastException e) {
                    /* Thrown if a field serialPersistentField exists
                     * but it is not of type ObjectStreamField.
                     * <p>
                     *  但它不是ObjectStreamField类型
                     * 
                     */
                    fields = null;
                }


                if (fields == null) {
                    /* Get all of the declared fields for this
                     * Class. setAccessible on all fields so they
                     * can be accessed later.  Create a temporary
                     * ObjectStreamField array to hold each
                     * non-static, non-transient field. Then copy the
                     * temporary array into an array of the correct
                     * size once the number of fields is known.
                     * <p>
                     *  类setAccessible在所有字段上,以便以后可以访问它们创建一个临时ObjectStreamField数组来保存每个非静态,非瞬态字段然后,一旦字段数量已知,将临时数组复制到正确大小的数组中
                     * 
                     */
                    Field[] actualfields = cl.getDeclaredFields();

                    int numFields = 0;
                    ObjectStreamField[] tempFields =
                        new ObjectStreamField[actualfields.length];
                    for (int i = 0; i < actualfields.length; i++) {
                        int modifiers = actualfields[i].getModifiers();
                        if (!Modifier.isStatic(modifiers) &&
                            !Modifier.isTransient(modifiers)) {
                            tempFields[numFields++] =
                                new ObjectStreamField(actualfields[i]);
                        }
                    }
                    fields = new ObjectStreamField[numFields];
                    System.arraycopy(tempFields, 0, fields, 0, numFields);

                } else {
                    // For each declared persistent field, look for an actual
                    // reflected Field. If there is one, make sure it's the correct
                    // type and cache it in the ObjectStreamClass_1_3_1 for that field.
                    for (int j = fields.length-1; j >= 0; j--) {
                        try {
                            Field reflField = cl.getDeclaredField(fields[j].getName());
                            if (fields[j].getType() == reflField.getType()) {
                                // reflField.setAccessible(true);
                                fields[j].setField(reflField);
                            }
                        } catch (NoSuchFieldException e) {
                            // Nothing to do
                        }
                    }
                }
                return null;
            }
            });

            if (fields.length > 1)
                Arrays.sort(fields);

            /* Set up field data for use while writing using the API api. */
            computeFieldInfo();
        }

        /* Get the serialVersionUID from the class.
         * It uses the access override mechanism so make sure
         * the field objects is only used here.
         *
         * NonSerializable classes have a serialVerisonUID of 0L.
         * <p>
         * 它使用访问覆盖机制,因此请确保字段对象仅在此处使用
         * 
         *  NonSerializable类的serialVerisonUID为0L
         * 
         */
         if (isNonSerializable()) {
             suid = 0L;
         } else {
             // Lookup special Serializable members using reflection.
             AccessController.doPrivileged(new PrivilegedAction() {
                public Object run() {
                if (forProxyClass) {
                    // proxy classes always have serialVersionUID of 0L
                    suid = 0L;
                } else {
                    try {
                        final Field f = cl.getDeclaredField("serialVersionUID");
                        int mods = f.getModifiers();
                    // SerialBug 5:  static final SUID should be read
                        if (Modifier.isStatic(mods) &&
                            Modifier.isFinal(mods) ) {
                            f.setAccessible(true);
                            suid = f.getLong(cl);
                            // get rid of native code
                            // suid = getSerialVersionUIDField(cl);
                    // SerialBug 2: should be computed after writeObject
                    // actualSuid = computeStructuralUID(cl);
                        } else {
                            suid = ObjectStreamClass.getSerialVersionUID(cl);
                            // SerialBug 2: should be computed after writeObject
                            // actualSuid = computeStructuralUID(cl);
                        }
                    } catch (NoSuchFieldException ex) {
                        suid = ObjectStreamClass.getSerialVersionUID(cl);
                        // SerialBug 2: should be computed after writeObject
                        // actualSuid = computeStructuralUID(cl);
                    } catch (IllegalAccessException ex) {
                        suid = ObjectStreamClass.getSerialVersionUID(cl);
                    }
                }


                try {
                    writeReplaceObjectMethod = cl.getDeclaredMethod("writeReplace", noTypesList);
                    if (Modifier.isStatic(writeReplaceObjectMethod.getModifiers())) {
                        writeReplaceObjectMethod = null;
                    } else {
                        writeReplaceObjectMethod.setAccessible(true);
                    }

                } catch (NoSuchMethodException e2) {

                }

                try {
                    readResolveObjectMethod = cl.getDeclaredMethod("readResolve", noTypesList);
                    if (Modifier.isStatic(readResolveObjectMethod.getModifiers())) {
                       readResolveObjectMethod = null;
                    } else {
                       readResolveObjectMethod.setAccessible(true);
                    }

                } catch (NoSuchMethodException e2) {

                }

                /* Cache lookup of writeObject and readObject for
                 * Serializable classes. (Do not lookup for
                 * Externalizable)
                 * <p>
                 *  可序列化类(不要查找Externalizable)
                 * 
                 */

                if (serializable && !forProxyClass) {

                    /* Look for the writeObject method
                     * Set the accessible flag on it here. ObjectOutputStream
                     * will call it as necessary.
                     * <p>
                     *  设置其上的可访问标志,ObjectOutputStream将根据需要调用它
                     * 
                     */
                    try {
                      Class<?>[] args = {java.io.ObjectOutputStream.class};
                      writeObjectMethod = cl.getDeclaredMethod("writeObject", args);
                      hasWriteObjectMethod = true;
                      int mods = writeObjectMethod.getModifiers();

                      // Method must be private and non-static
                      if (!Modifier.isPrivate(mods) ||
                        Modifier.isStatic(mods)) {
                        writeObjectMethod = null;
                        hasWriteObjectMethod = false;
                      }

                    } catch (NoSuchMethodException e) {
                    }

                    /* Look for the readObject method
                     * set the access override and save the reference for
                     * ObjectInputStream so it can all the method directly.
                     * <p>
                     *  设置访问重写并保存ObjectInputStream的引用,所以它可以直接的所有方法
                     * 
                     */
                    try {
                      Class<?>[] args = {java.io.ObjectInputStream.class};
                      readObjectMethod = cl.getDeclaredMethod("readObject", args);
                      int mods = readObjectMethod.getModifiers();

                      // Method must be private and non-static
                      if (!Modifier.isPrivate(mods) ||
                        Modifier.isStatic(mods)) {
                        readObjectMethod = null;
                      }
                    } catch (NoSuchMethodException e) {
                    }
                    // Compute the structural UID.  This must be done after the
                    // calculation for writeObject.  Fixed 4/20/2000, eea1
                    // SerialBug 2: to have correct value in RepId
                }
                return null;
            }
          });
        }

        actualSuid = computeStructuralUID(this, cl);
      }

    }

    /*
     * Create an empty ObjectStreamClass_1_3_1 for a class about to be read.
     * This is separate from read so ObjectInputStream can assign the
     * wire handle early, before any nested ObjectStreamClass_1_3_1 might
     * be read.
     * <p>
     *  为要读取的类创建一个空ObjectStreamClass_1_3_1这是与读取分开的,因此ObjectInputStream可以在任何嵌套的ObjectStreamClass_1_3_1可能被读取之前
     * 提前分配wire句柄。
     * 
     */
    ObjectStreamClass_1_3_1(String n, long s) {
        name = n;
        suid = s;
        superclass = null;
    }

    private static Object[] translateFields(Object objs[])
        throws NoSuchFieldException {
        try{
            java.io.ObjectStreamField fields[] = (java.io.ObjectStreamField[])objs;
            Object translation[] = null;

            if (translatedFields == null)
                translatedFields = new Hashtable();

            translation = (Object[])translatedFields.get(fields);

            if (translation != null)
                return translation;
            else {
                Class<?> osfClass = com.sun.corba.se.impl.orbutil.ObjectStreamField.class;

                translation = (Object[])java.lang.reflect.Array.newInstance(osfClass, objs.length);
                Object arg[] = new Object[2];
                Class<?> types[] = {String.class, Class.class};
                Constructor constructor = osfClass.getDeclaredConstructor(types);
                for (int i = fields.length -1; i >= 0; i--){
                    arg[0] = fields[i].getName();
                    arg[1] = fields[i].getType();

                    translation[i] = constructor.newInstance(arg);
                }
                translatedFields.put(fields, translation);

            }

            return (Object[])translation;
        }
        catch(Throwable t){
            throw new NoSuchFieldException();
        }
    }

    /* Compare the base class names of streamName and localName.
     *
     * <p>
     * 
     * @return  Return true iff the base class name compare.
     * @parameter streamName    Fully qualified class name.
     * @parameter localName     Fully qualified class name.
     * @parameter pkgSeparator  class names use either '.' or '/'.
     *
     * Only compare base class name to allow package renaming.
     */
    static boolean compareClassNames(String streamName,
                                     String localName,
                                     char pkgSeparator) {
        /* compare the class names, stripping off package names. */
        int streamNameIndex = streamName.lastIndexOf(pkgSeparator);
        if (streamNameIndex < 0)
            streamNameIndex = 0;

        int localNameIndex = localName.lastIndexOf(pkgSeparator);
        if (localNameIndex < 0)
            localNameIndex = 0;

        return streamName.regionMatches(false, streamNameIndex,
                                        localName, localNameIndex,
                                        streamName.length() - streamNameIndex);
    }

    /*
     * Compare the types of two class descriptors.
     * They match if they have the same class name and suid
     * <p>
     *  比较两个类描述符的类型如果它们具有相同的类名和suid,则它们匹配
     * 
     */
    final boolean typeEquals(ObjectStreamClass_1_3_1 other) {
        return (suid == other.suid) &&
            compareClassNames(name, other.name, '.');
    }

    /*
     * Return the superclass descriptor of this descriptor.
     * <p>
     *  返回此描述符的超类描述符
     * 
     */
    final void setSuperclass(ObjectStreamClass_1_3_1 s) {
        superclass = s;
    }

    /*
     * Return the superclass descriptor of this descriptor.
     * <p>
     * 返回此描述符的超类描述符
     * 
     */
    final ObjectStreamClass_1_3_1 getSuperclass() {
        return superclass;
    }

    /*
     * Return whether the class has a writeObject method
     * <p>
     *  返回类是否有writeObject方法
     * 
     */
    final boolean hasWriteObject() {
        return hasWriteObjectMethod;
    }

    final boolean isCustomMarshaled() {
        return (hasWriteObject() || isExternalizable());
    }

    /*
     * Return true if all instances of 'this' Externalizable class
     * are written in block-data mode from the stream that 'this' was read
     * from. <p>
     *
     * In JDK 1.1, all Externalizable instances are not written
     * in block-data mode.
     * In JDK 1.2, all Externalizable instances, by default, are written
     * in block-data mode and the Externalizable instance is terminated with
     * tag TC_ENDBLOCKDATA. Change enabled the ability to skip Externalizable
     * instances.
     *
     * IMPLEMENTATION NOTE:
     *   This should have been a mode maintained per stream; however,
     *   for compatibility reasons, it was only possible to record
     *   this change per class. All Externalizable classes within
     *   a given stream should either have this mode enabled or
     *   disabled. This is enforced by not allowing the PROTOCOL_VERSION
     *   of a stream to he changed after any objects have been written.
     *
     * <p>
     *  如果'this'的所有实例都以块数据模式从从"<p>"读取的流中写入,则返回true;
     * 
     *  在JDK 11中,所有Externalizable实例都不是以块数据模式写入的在JDK 12中,默认情况下,所有Externalizable实例都以块数据模式写入,而Externalizable实例以
     * 标签TC_ENDBLOCKDATA终止。
     * Change允许跳过Externalizable实例。
     * 
     * 实现注意：这应该是每个流保持的模式;然而,出于兼容性原因,只能够记录每个类的更改所有可外部化类在给定流中应该启用或禁用此模式这是通过不允许流的PROTOCOL_VERSION在任何对象书面
     * 
     * 
     * @see ObjectOutputStream#useProtocolVersion
     * @see ObjectStreamConstants#PROTOCOL_VERSION_1
     * @see ObjectStreamConstants#PROTOCOL_VERSION_2
     *
     * @since JDK 1.2
     */
    boolean hasExternalizableBlockDataMode() {
        return hasExternalizableBlockData;
    }

    /*
     * Return the ObjectStreamClass_1_3_1 of the local class this one is based on.
     * <p>
     *  返回此对象所基于的本地类的ObjectStreamClass_1_3_1
     * 
     */
    final ObjectStreamClass_1_3_1 localClassDescriptor() {
        return localClassDesc;
    }

    /*
     * Get the Serializability of the class.
     * <p>
     *  获取类的可串行化
     * 
     */
    boolean isSerializable() {
        return serializable;
    }

    /*
     * Get the externalizability of the class.
     * <p>
     *  获得类的外向性
     * 
     */
    boolean isExternalizable() {
        return externalizable;
    }

    boolean isNonSerializable() {
        return ! (externalizable || serializable);
    }

    /*
     * Calculate the size of the array needed to store primitive data and the
     * number of object references to read when reading from the input
     * stream.
     * <p>
     *  计算从输入流读取时存储基本数据所需的数组大小和要读取的对象引用数
     * 
     */
    private void computeFieldInfo() {
        primBytes = 0;
        objFields = 0;

        for (int i = 0; i < fields.length; i++ ) {
            switch (fields[i].getTypeCode()) {
            case 'B':
            case 'Z':
                primBytes += 1;
                break;
            case 'C':
            case 'S':
                primBytes += 2;
                break;

            case 'I':
            case 'F':
                primBytes += 4;
                break;
            case 'J':
            case 'D' :
                primBytes += 8;
                break;

            case 'L':
            case '[':
                objFields += 1;
                break;
            }
        }
    }

    private static long computeStructuralUID(ObjectStreamClass_1_3_1 osc, Class<?> cl) {
        ByteArrayOutputStream devnull = new ByteArrayOutputStream(512);

        long h = 0;
        try {

            if ((!java.io.Serializable.class.isAssignableFrom(cl)) ||
                (cl.isInterface())){
                return 0;
            }

            if (java.io.Externalizable.class.isAssignableFrom(cl)) {
                return 1;
            }

            MessageDigest md = MessageDigest.getInstance("SHA");
            DigestOutputStream mdo = new DigestOutputStream(devnull, md);
            DataOutputStream data = new DataOutputStream(mdo);

            // Get SUID of parent
            Class<?> parent = cl.getSuperclass();
            if ((parent != null))
            // SerialBug 1; acc. to spec the one for
            // java.lang.object
            // should be computed and put
            //     && (parent != java.lang.Object.class))
            {
                                //data.writeLong(computeSerialVersionUID(null,parent));
                data.writeLong(computeStructuralUID(lookup(parent), parent));
            }

            if (osc.hasWriteObject())
                data.writeInt(2);
            else
                data.writeInt(1);

            /* Sort the field names to get a deterministic order */
            // Field[] field = ObjectStreamClass_1_3_1.getDeclaredFields(cl);

            ObjectStreamField[] fields = osc.getFields();

            // Must make sure that the Field array we allocate
            // below is exactly the right size.  Bug fix for
            // 4397133.
            int numNonNullFields = 0;
            for (int i = 0; i < fields.length; i++)
                if (fields[i].getField() != null)
                    numNonNullFields++;

            Field [] field = new java.lang.reflect.Field[numNonNullFields];
            for (int i = 0, fieldNum = 0; i < fields.length; i++) {
                if (fields[i].getField() != null) {
                    field[fieldNum++] = fields[i].getField();
                }
            }

            if (field.length > 1)
                Arrays.sort(field, compareMemberByName);

            for (int i = 0; i < field.length; i++) {
                Field f = field[i];

                                /* Include in the hash all fields except those that are
                                 * transient
                                 * <p>
                                 *  短暂的
                                 * 
                                 */
                int m = f.getModifiers();
                //Serial 6
                //if (Modifier.isTransient(m) || Modifier.isStatic(m))
                // spec reference 00-01-06.pdf, 1.3.5.6, states non-static
                // non-transient, public fields are mapped to Java IDL.
                //
                // Here's the quote from the first paragraph:
                // Java non-static non-transient public fields are mapped to
                // OMG IDL public data members, and other Java fields are
                // not mapped.

                // if (Modifier.isTransient(m) || Modifier.isStatic(m))
                //     continue;

                data.writeUTF(f.getName());
                data.writeUTF(getSignature(f.getType()));
            }

            /* Compute the hash value for this class.
             * Use only the first 64 bits of the hash.
             * <p>
             *  只使用散列的前64位
             * 
             */
            data.flush();
            byte hasharray[] = md.digest();
            // int minimum = Math.min(8, hasharray.length);
            // SerialBug 3: SHA computation is wrong; for loop reversed
            //for (int i = minimum; i > 0; i--)
            for (int i = 0; i < Math.min(8, hasharray.length); i++) {
                h += (long)(hasharray[i] & 255) << (i * 8);
            }
        } catch (IOException ignore) {
            /* can't happen, but be deterministic anyway. */
            h = -1;
        } catch (NoSuchAlgorithmException complain) {
            throw new SecurityException(complain.getMessage());
        }
        return h;
    }

    /**
     * Compute the JVM signature for the class.
     * <p>
     *  计算类的JVM签名
     * 
     */
    static String getSignature(Class<?> clazz) {
        String type = null;
        if (clazz.isArray()) {
            Class<?> cl = clazz;
            int dimensions = 0;
            while (cl.isArray()) {
                dimensions++;
                cl = cl.getComponentType();
            }
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < dimensions; i++) {
                sb.append("[");
            }
            sb.append(getSignature(cl));
            type = sb.toString();
        } else if (clazz.isPrimitive()) {
            if (clazz == Integer.TYPE) {
                type = "I";
            } else if (clazz == Byte.TYPE) {
                type = "B";
            } else if (clazz == Long.TYPE) {
                type = "J";
            } else if (clazz == Float.TYPE) {
                type = "F";
            } else if (clazz == Double.TYPE) {
                type = "D";
            } else if (clazz == Short.TYPE) {
                type = "S";
            } else if (clazz == Character.TYPE) {
                type = "C";
            } else if (clazz == Boolean.TYPE) {
                type = "Z";
            } else if (clazz == Void.TYPE) {
                type = "V";
            }
        } else {
            type = "L" + clazz.getName().replace('.', '/') + ";";
        }
        return type;
    }

    /*
     * Compute the JVM method descriptor for the method.
     * <p>
     * 计算方法的JVM方法描述符
     * 
     */
    static String getSignature(Method meth) {
        StringBuffer sb = new StringBuffer();

        sb.append("(");

        Class<?>[] params = meth.getParameterTypes(); // avoid clone
        for (int j = 0; j < params.length; j++) {
            sb.append(getSignature(params[j]));
        }
        sb.append(")");
        sb.append(getSignature(meth.getReturnType()));
        return sb.toString();
    }

    /*
     * Compute the JVM constructor descriptor for the constructor.
     * <p>
     *  计算构造函数的JVM构造函数描述符
     * 
     */
    static String getSignature(Constructor cons) {
        StringBuffer sb = new StringBuffer();

        sb.append("(");

        Class<?>[] params = cons.getParameterTypes(); // avoid clone
        for (int j = 0; j < params.length; j++) {
            sb.append(getSignature(params[j]));
        }
        sb.append(")V");
        return sb.toString();
    }

    /*
     * Cache of Class -> ClassDescriptor Mappings.
     * <p>
     *  类的缓存 - > ClassDescriptor映射
     * 
     */
    static private ObjectStreamClassEntry[] descriptorFor = new ObjectStreamClassEntry[61];

    /*
     * findDescriptorFor a Class.  This looks in the cache for a
     * mapping from Class -> ObjectStreamClass mappings.  The hashCode
     * of the Class is used for the lookup since the Class is the key.
     * The entries are extended from java.lang.ref.SoftReference so the
     * gc will be able to free them if needed.
     * <p>
     *  findDescriptorFor一个类在缓存中查找Class  - > ObjectStreamClass映射的映射Class的hashCode用于查找,因为类是关键字这些条目从javalangre
     * fSoftReference扩展,因此gc将能够释放它们,如果需要的话。
     * 
     */
    private static ObjectStreamClass_1_3_1 findDescriptorFor(Class<?> cl) {

        int hash = cl.hashCode();
        int index = (hash & 0x7FFFFFFF) % descriptorFor.length;
        ObjectStreamClassEntry e;
        ObjectStreamClassEntry prev;

        /* Free any initial entries whose refs have been cleared */
        while ((e = descriptorFor[index]) != null && e.get() == null) {
            descriptorFor[index] = e.next;
        }

        /* Traverse the chain looking for a descriptor with ofClass == cl.
         * unlink entries that are unresolved.
         * <p>
         *  取消链接未解析的条目
         * 
         */
        prev = e;
        while (e != null ) {
            ObjectStreamClass_1_3_1 desc = (ObjectStreamClass_1_3_1)(e.get());
            if (desc == null) {
                // This entry has been cleared,  unlink it
                prev.next = e.next;
            } else {
                if (desc.ofClass == cl)
                    return desc;
                prev = e;
            }
            e = e.next;
        }
        return null;
    }

    /*
     * insertDescriptorFor a Class -> ObjectStreamClass_1_3_1 mapping.
     * <p>
     *  insertDescriptorFor一个Class  - > ObjectStreamClass_1_3_1映射
     * 
     */
    private static void insertDescriptorFor(ObjectStreamClass_1_3_1 desc) {
        // Make sure not already present
        if (findDescriptorFor(desc.ofClass) != null) {
            return;
        }

        int hash = desc.ofClass.hashCode();
        int index = (hash & 0x7FFFFFFF) % descriptorFor.length;
        ObjectStreamClassEntry e = new ObjectStreamClassEntry(desc);
        e.next = descriptorFor[index];
        descriptorFor[index] = e;
    }

    private static Field[] getDeclaredFields(final Class clz) {
        return (Field[]) AccessController.doPrivileged(new PrivilegedAction() {
            public Object run() {
                return clz.getDeclaredFields();
            }
        });
    }


    /*
     * The name of this descriptor
     * <p>
     *  此描述符的名称
     * 
     */
    private String name;

    /*
     * The descriptor of the supertype.
     * <p>
     *  超类型的描述符
     * 
     */
    private ObjectStreamClass_1_3_1 superclass;

    /*
     * Flags for Serializable and Externalizable.
     * <p>
     *  可序列化和可外部化的标志
     * 
     */
    private boolean serializable;
    private boolean externalizable;

    /*
     * Array of persistent fields of this class, sorted by
     * type and name.
     * <p>
     *  该类的持久字段数组,按类型和名称排序
     * 
     */
    private ObjectStreamField[] fields;

    /*
     * Class that is a descriptor for in this virtual machine.
     * <p>
     *  作为此虚拟机中的描述符的类
     * 
     */
    private Class<?> ofClass;

    /*
     * True if descriptor for a proxy class.
     * <p>
     * 如果代理类的描述符为True
     * 
     */
    boolean forProxyClass;


    /*
     * SerialVersionUID for this class.
     * <p>
     *  此类的SerialVersionUID
     * 
     */
    private long suid = kDefaultUID;
    private String suidStr = null;

    /*
     * Actual (computed) SerialVersionUID for this class.
     * <p>
     *  此类的实际(计算)SerialVersionUID
     * 
     */
    private long actualSuid = kDefaultUID;
    private String actualSuidStr = null;

    /*
     * The total number of bytes of primitive fields.
     * The total number of object fields.
     * <p>
     *  基本字段的字节总数对象字段的总数
     * 
     */
    int primBytes;
    int objFields;

    /* Internal lock object. */
    private Object lock = new Object();

    /* True if this class has/had a writeObject method */
    private boolean hasWriteObjectMethod;

    /* In JDK 1.1, external data was not written in block mode.
     * As of JDK 1.2, external data is written in block data mode. This
     * flag enables JDK 1.2 to be able to read JDK 1.1 written external data.
     *
     * <p>
     *  从JDK 12开始,外部数据以块数据模式写入此标志使JDK 12能够读取JDK 11写入的外部数据
     * 
     * 
     * @since JDK 1.2
     */
    private boolean hasExternalizableBlockData;
    Method writeObjectMethod;
    Method readObjectMethod;
    private transient Method writeReplaceObjectMethod;
    private transient Method readResolveObjectMethod;

    /*
     * ObjectStreamClass_1_3_1 that this one was built from.
     * <p>
     *  ObjectStreamClass_1_3_1这个是从中构建的
     * 
     */
    private ObjectStreamClass_1_3_1 localClassDesc;

    /* Get the private static final field for serial version UID */
    // private static native long getSerialVersionUIDField(Class cl);

    /** use serialVersionUID from JDK 1.1. for interoperability */
    private static final long serialVersionUID = -6120832682080437368L;

    /**
     * Set serialPersistentFields of a Serializable class to this value to
     * denote that the class has no Serializable fields.
     * <p>
     *  将Serializable类的serialPersistentFields设置为此值,以表示该类没有可序列化字段
     * 
     */
    public static final ObjectStreamField[] NO_FIELDS =
        new ObjectStreamField[0];

    /*
     * Entries held in the Cache of known ObjectStreamClass_1_3_1 objects.
     * Entries are chained together with the same hash value (modulo array size).
     * <p>
     *  保存在已知ObjectStreamClass_1_3_1对象的Cache中的条目条目与相同的哈希值(模数组大小)
     * 
     */
    private static class ObjectStreamClassEntry // extends java.lang.ref.SoftReference
    {
        ObjectStreamClassEntry(ObjectStreamClass_1_3_1 c) {
            //super(c);
            this.c = c;
        }
        ObjectStreamClassEntry next;

        public Object get()
        {
            return c;
        }
        private ObjectStreamClass_1_3_1 c;
    }

    /*
     * Comparator object for Classes and Interfaces
     * <p>
     *  类和接口的比较器对象
     * 
     */
    private static Comparator compareClassByName =
        new CompareClassByName();

    private static class CompareClassByName implements Comparator {
        public int compare(Object o1, Object o2) {
            Class<?> c1 = (Class)o1;
            Class<?> c2 = (Class)o2;
            return (c1.getName()).compareTo(c2.getName());
        }
    }

    /*
     * Comparator object for Members, Fields, and Methods
     * <p>
     *  成员,字段和方法的比较器对象
     * 
     */
    private static Comparator compareMemberByName =
        new CompareMemberByName();

    private static class CompareMemberByName implements Comparator {
        public int compare(Object o1, Object o2) {
            String s1 = ((Member)o1).getName();
            String s2 = ((Member)o2).getName();

            if (o1 instanceof Method) {
                s1 += getSignature((Method)o1);
                s2 += getSignature((Method)o2);
            } else if (o1 instanceof Constructor) {
                s1 += getSignature((Constructor)o1);
                s2 += getSignature((Constructor)o2);
            }
            return s1.compareTo(s2);
        }
    }

    /* It is expensive to recompute a method or constructor signature
    /* <p>
    /* 
       many times, so compute it only once using this data structure. */
    private static class MethodSignature implements Comparator {
        Member member;
        String signature;      // cached parameter signature

        /* Given an array of Method or Constructor members,
        /* <p>
        /* 
           return a sorted array of the non-private members.*/
        /* A better implementation would be to implement the returned data
        /* <p>
        /* 
           structure as an insertion sorted link list.*/
        static MethodSignature[] removePrivateAndSort(Member[] m) {
            int numNonPrivate = 0;
            for (int i = 0; i < m.length; i++) {
                if (! Modifier.isPrivate(m[i].getModifiers())) {
                    numNonPrivate++;
                }
            }
            MethodSignature[] cm = new MethodSignature[numNonPrivate];
            int cmi = 0;
            for (int i = 0; i < m.length; i++) {
                if (! Modifier.isPrivate(m[i].getModifiers())) {
                    cm[cmi] = new MethodSignature(m[i]);
                    cmi++;
                }
            }
            if (cmi > 0)
                Arrays.sort(cm, cm[0]);
            return cm;
        }

        /* Assumes that o1 and o2 are either both methods
        /* <p>
        /* 
           or both constructors.*/
        public int compare(Object o1, Object o2) {
            /* Arrays.sort calls compare when o1 and o2 are equal.*/
            if (o1 == o2)
                return 0;

            MethodSignature c1 = (MethodSignature)o1;
            MethodSignature c2 = (MethodSignature)o2;

            int result;
            if (isConstructor()) {
                result = c1.signature.compareTo(c2.signature);
            } else { // is a Method.
                result = c1.member.getName().compareTo(c2.member.getName());
                if (result == 0)
                    result = c1.signature.compareTo(c2.signature);
            }
            return result;
        }

        final private boolean isConstructor() {
            return member instanceof Constructor;
        }
        private MethodSignature(Member m) {
            member = m;
            if (isConstructor()) {
                signature = ObjectStreamClass_1_3_1.getSignature((Constructor)m);
            } else {
                signature = ObjectStreamClass_1_3_1.getSignature((Method)m);
            }
        }
    }
}
