/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2014, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.corba.se.impl.orbutil;

// for computing the structural UID
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.DigestOutputStream;
import java.security.AccessController;
import java.security.PrivilegedExceptionAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedAction;
import java.io.DataOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import java.util.Arrays;
import java.util.Comparator;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Array;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Constructor;


import com.sun.corba.se.impl.io.ObjectStreamClass;

public final class ObjectStreamClassUtil_1_3 {

    // maintained here for backward compatability with JDK 1.3, where
    // writeObject method was not being checked at all, so there is
    // no need to lookup the ObjectStreamClass

    public static long computeSerialVersionUID(final Class cl) {

        long csuid = ObjectStreamClass.getSerialVersionUID(cl);
        if (csuid == 0)
            return csuid; // for non-serializable/proxy classes

        csuid = (ObjectStreamClassUtil_1_3.getSerialVersion(csuid, cl).longValue());
        return csuid;
    }


    // to maintain same suid as the JDK 1.3, we pick
    // up suid only for classes with private,static,final
    // declarations, and compute it for all others

    private static Long getSerialVersion(final long csuid, final Class cl)
    {
        return (Long) AccessController.doPrivileged(new PrivilegedAction() {
          public Object run() {
            long suid;
            try {
                final Field f = cl.getDeclaredField("serialVersionUID");
                int mods = f.getModifiers();
                if (Modifier.isStatic(mods) &&
                    Modifier.isFinal(mods) && Modifier.isPrivate(mods)) {
                    suid = csuid;
                 } else {
                    suid = _computeSerialVersionUID(cl);
                 }
              } catch (NoSuchFieldException ex) {
                  suid = _computeSerialVersionUID(cl);
              //} catch (IllegalAccessException ex) {
              //     suid = _computeSerialVersionUID(cl);
              }
              return new Long(suid);
           }
        });
    }

    public static long computeStructuralUID(boolean hasWriteObject, Class<?> cl) {
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

            //In the old case, for the caller class, the write Method wasn't considered
            // for rep-id calculations correctly, but for parent classes it was taken
            // into account.  That is the reason there is the klude of getting the write
            // Object method in there

            // Get SUID of parent
            Class<?> parent = cl.getSuperclass();
            if ((parent != null) && (parent != java.lang.Object.class)) {
                boolean hasWriteObjectFlag = false;
                Class [] args = {java.io.ObjectOutputStream.class};
                Method hasWriteObjectMethod = ObjectStreamClassUtil_1_3.getDeclaredMethod(parent, "writeObject", args,
                       Modifier.PRIVATE, Modifier.STATIC);
                if (hasWriteObjectMethod != null)
                    hasWriteObjectFlag = true;
                data.writeLong(ObjectStreamClassUtil_1_3.computeStructuralUID(hasWriteObjectFlag, parent));
            }

            if (hasWriteObject)
                data.writeInt(2);
            else
                data.writeInt(1);

            /* Sort the field names to get a deterministic order */
            Field[] field = ObjectStreamClassUtil_1_3.getDeclaredFields(cl);
            Arrays.sort(field, compareMemberByName);

            for (int i = 0; i < field.length; i++) {
                Field f = field[i];

                                /* Include in the hash all fields except those that are
                                 * transient or static.
                                 * <p>
                                 *  瞬态或静态。
                                 * 
                                 */
                int m = f.getModifiers();
                if (Modifier.isTransient(m) || Modifier.isStatic(m))
                    continue;

                data.writeUTF(f.getName());
                data.writeUTF(getSignature(f.getType()));
            }

            /* Compute the hash value for this class.
             * Use only the first 64 bits of the hash.
             * <p>
             *  只使用哈希的前64位。
             * 
             */
            data.flush();
            byte hasharray[] = md.digest();
            int minimum = Math.min(8, hasharray.length);
            for (int i = minimum; i > 0; i--) {
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

    /*
     * Compute a hash for the specified class.  Incrementally add
     * items to the hash accumulating in the digest stream.
     * Fold the hash into a long.  Use the SHA secure hash function.
     * <p>
     *  计算指定类的哈希值。增量地将项目添加到在摘要流中累积的散列中。将哈希折叠成一个长。使用SHA安全散列函数。
     * 
     */
    private static long _computeSerialVersionUID(Class cl) {
        ByteArrayOutputStream devnull = new ByteArrayOutputStream(512);

        long h = 0;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA");
            DigestOutputStream mdo = new DigestOutputStream(devnull, md);
            DataOutputStream data = new DataOutputStream(mdo);


            data.writeUTF(cl.getName());

            int classaccess = cl.getModifiers();
            classaccess &= (Modifier.PUBLIC | Modifier.FINAL |
                            Modifier.INTERFACE | Modifier.ABSTRACT);

            /* Workaround for javac bug that only set ABSTRACT for
             * interfaces if the interface had some methods.
             * The ABSTRACT bit reflects that the number of methods > 0.
             * This is required so correct hashes can be computed
             * for existing class files.
             * Previously this hack was previously present in the VM.
             * <p>
             *  接口如果接口有一些方法。 ABSTRACT位反映方法数> 0。这是必需的,因此可以为现有类文件计算正确的散列。以前,此黑客以前存在于VM中。
             * 
             */
            Method[] method = cl.getDeclaredMethods();
            if ((classaccess & Modifier.INTERFACE) != 0) {
                classaccess &= (~Modifier.ABSTRACT);
                if (method.length > 0) {
                    classaccess |= Modifier.ABSTRACT;
                }
            }

            data.writeInt(classaccess);

            /*
             * Get the list of interfaces supported,
             * Accumulate their names their names in Lexical order
             * and add them to the hash
             * <p>
             *  获取支持的接口列表,按词法顺序收集名称及其名称,并将它们添加到哈希
             * 
             */
            if (!cl.isArray()) {
                /* In 1.2fcs, getInterfaces() was modified to return
                 * {java.lang.Cloneable, java.io.Serializable} when
                 * called on array classes.  These values would upset
                 * the computation of the hash, so we explicitly omit
                 * them from its computation.
                 * <p>
                 *  {java.lang.Cloneable,java.io.Serializable}在数组类上调用时。这些值将破坏散列的计算,因此我们从其计算中明确省略它们。
                 * 
                 */

                Class interfaces[] = cl.getInterfaces();
                Arrays.sort(interfaces, compareClassByName);

                for (int i = 0; i < interfaces.length; i++) {
                    data.writeUTF(interfaces[i].getName());
                }
            }

            /* Sort the field names to get a deterministic order */
            Field[] field = cl.getDeclaredFields();
            Arrays.sort(field, compareMemberByName);

            for (int i = 0; i < field.length; i++) {
                Field f = field[i];

                /* Include in the hash all fields except those that are
                 * private transient and private static.
                 * <p>
                 *  私有瞬态和私有静态。
                 * 
                 */
                int m = f.getModifiers();
                if (Modifier.isPrivate(m) &&
                    (Modifier.isTransient(m) || Modifier.isStatic(m)))
                    continue;

                data.writeUTF(f.getName());
                data.writeInt(m);
                data.writeUTF(getSignature(f.getType()));
            }

            // need to find the java replacement for hasStaticInitializer
            if (hasStaticInitializer(cl)) {
                data.writeUTF("<clinit>");
                data.writeInt(Modifier.STATIC); // TBD: what modifiers does it have
                data.writeUTF("()V");
            }

            /*
             * Get the list of constructors including name and signature
             * Sort lexically, add all except the private constructors
             * to the hash with their access flags
             * <p>
             *  获取构造函数的列表,包括名称和签名排序,添加除了私有构造函数的所有的构造函数到它们的访问标志的哈希
             * 
             */

            MethodSignature[] constructors =
                MethodSignature.removePrivateAndSort(cl.getDeclaredConstructors());
            for (int i = 0; i < constructors.length; i++) {
                MethodSignature c = constructors[i];
                String mname = "<init>";
                String desc = c.signature;
                desc = desc.replace('/', '.');
                data.writeUTF(mname);
                data.writeInt(c.member.getModifiers());
                data.writeUTF(desc);
            }

            /* Include in the hash all methods except those that are
             * private transient and private static.
             * <p>
             *  私有瞬态和私有静态。
             * 
             */
            MethodSignature[] methods =
                MethodSignature.removePrivateAndSort(method);
            for (int i = 0; i < methods.length; i++ ) {
                MethodSignature m = methods[i];
                String desc = m.signature;
                desc = desc.replace('/', '.');
                data.writeUTF(m.member.getName());
                data.writeInt(m.member.getModifiers());
                data.writeUTF(desc);
            }

            /* Compute the hash value for this class.
             * Use only the first 64 bits of the hash.
             * <p>
             *  只使用哈希的前64位。
             * 
             */
            data.flush();
            byte hasharray[] = md.digest();
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
            Class c1 = (Class)o1;
            Class c2 = (Class)o2;
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

    /**
     * Compute the JVM signature for the class.
     * <p>
     *  计算类的JVM签名。
     * 
     */
    private static String getSignature(Class clazz) {
        String type = null;
        if (clazz.isArray()) {
            Class cl = clazz;
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
     *  计算方法的JVM方法描述符。
     * 
     */
    private static String getSignature(Method meth) {
        StringBuffer sb = new StringBuffer();

        sb.append("(");

        Class[] params = meth.getParameterTypes(); // avoid clone
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
     *  计算构造函数的JVM构造函数描述符。
     * 
     */
    private static String getSignature(Constructor cons) {
        StringBuffer sb = new StringBuffer();

        sb.append("(");

        Class[] params = cons.getParameterTypes(); // avoid clone
        for (int j = 0; j < params.length; j++) {
            sb.append(getSignature(params[j]));
        }
        sb.append(")V");
        return sb.toString();
    }

    private static Field[] getDeclaredFields(final Class clz) {
        return (Field[]) AccessController.doPrivileged(new PrivilegedAction() {
            public Object run() {
                return clz.getDeclaredFields();
            }
        });
    }

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
                signature = ObjectStreamClassUtil_1_3.getSignature((Constructor)m);
            } else {
                signature = ObjectStreamClassUtil_1_3.getSignature((Method)m);
            }
        }
    }

    /* Find out if the class has a static class initializer <clinit> */
    // use java.io.ObjectStream's hasStaticInitializer method
    // private static native boolean hasStaticInitializer(Class cl);

    private static Method hasStaticInitializerMethod = null;
    /**
     * Returns true if the given class defines a static initializer method,
     * false otherwise.
     * <p>
     * 如果给定类定义了静态初始化方法,则返回true,否则返回false。
     */
    private static boolean hasStaticInitializer(Class cl) {
        if (hasStaticInitializerMethod == null) {
            Class classWithThisMethod = null;

            try {
                if (classWithThisMethod == null)
                    classWithThisMethod = java.io.ObjectStreamClass.class;

                hasStaticInitializerMethod =
                    classWithThisMethod.getDeclaredMethod("hasStaticInitializer",
                                                          new Class[] { Class.class });
            } catch (NoSuchMethodException ex) {
            }

            if (hasStaticInitializerMethod == null) {
                throw new InternalError("Can't find hasStaticInitializer method on "
                                        + classWithThisMethod.getName());
            }
            hasStaticInitializerMethod.setAccessible(true);
        }
        try {
            Boolean retval = (Boolean)
                hasStaticInitializerMethod.invoke(null, new Object[] { cl });
            return retval.booleanValue();
        } catch (Exception ex) {
            throw new InternalError("Error invoking hasStaticInitializer: "
                                    + ex);
        }
    }

    private static Method getDeclaredMethod(final Class cl, final String methodName, final Class[] args,
                                     final int requiredModifierMask,
                                     final int disallowedModifierMask) {
        return (Method) AccessController.doPrivileged(new PrivilegedAction() {
            public Object run() {
                Method method = null;
                try {
                    method =
                        cl.getDeclaredMethod(methodName, args);
                        int mods = method.getModifiers();
                        if ((mods & disallowedModifierMask) != 0 ||
                            (mods & requiredModifierMask) != requiredModifierMask) {
                            method = null;
                        }
                        //if (!Modifier.isPrivate(mods) ||
                        //    Modifier.isStatic(mods)) {
                        //    method = null;
                        //}
                } catch (NoSuchMethodException e) {
                // Since it is alright if methodName does not exist,
                // no need to do anything special here.
                }
                return method;
            }
        });
    }

}
