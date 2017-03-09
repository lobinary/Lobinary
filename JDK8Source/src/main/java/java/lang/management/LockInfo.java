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

package java.lang.management;

import javax.management.openmbean.CompositeData;
import java.util.concurrent.locks.*;
import sun.management.LockInfoCompositeData;

/**
 * Information about a <em>lock</em>.  A lock can be a built-in object monitor,
 * an <em>ownable synchronizer</em>, or the {@link Condition Condition}
 * object associated with synchronizers.
 * <p>
 * <a name="OwnableSynchronizer">An ownable synchronizer</a> is
 * a synchronizer that may be exclusively owned by a thread and uses
 * {@link AbstractOwnableSynchronizer AbstractOwnableSynchronizer}
 * (or its subclass) to implement its synchronization property.
 * {@link ReentrantLock ReentrantLock} and
 * {@link ReentrantReadWriteLock ReentrantReadWriteLock} are
 * two examples of ownable synchronizers provided by the platform.
 *
 * <h3><a name="MappedType">MXBean Mapping</a></h3>
 * <tt>LockInfo</tt> is mapped to a {@link CompositeData CompositeData}
 * as specified in the {@link #from from} method.
 *
 * <p>
 *  有关<em>锁定的信息</em>。锁可以是内置对象监视器,可拥有的同步器</em>或与同步器关联的{@link条件条件}对象。
 * <p>
 *  <a name="OwnableSynchronizer">可拥有的同步器</a>是一个同步器,可以由线程独占,并使用{@link AbstractOwnableSynchronizer AbstractOwnableSynchronizer}
 * (或其子类)来实现其同步属性。
 *  {@link ReentrantLock ReentrantLock}和{@link ReentrantReadWriteLock ReentrantReadWriteLock}是平台提供的可拥有同步
 * 器的两个示例。
 * 
 *  <h3> <a name="MappedType"> MXBean映射</a> </h3> <tt> LockInfo </tt>映射到{@link #from from}中指定的{@link CompositeData CompositeData}
 * 方法。
 * 
 * 
 * @see java.util.concurrent.locks.AbstractOwnableSynchronizer
 * @see java.util.concurrent.locks.Condition
 *
 * @author  Mandy Chung
 * @since   1.6
 */

public class LockInfo {

    private String className;
    private int    identityHashCode;

    /**
     * Constructs a <tt>LockInfo</tt> object.
     *
     * <p>
     *  构造<tt> LockInfo </tt>对象。
     * 
     * 
     * @param className the fully qualified name of the class of the lock object.
     * @param identityHashCode the {@link System#identityHashCode
     *                         identity hash code} of the lock object.
     */
    public LockInfo(String className, int identityHashCode) {
        if (className == null) {
            throw new NullPointerException("Parameter className cannot be null");
        }
        this.className = className;
        this.identityHashCode = identityHashCode;
    }

    /**
     * package-private constructors
     * <p>
     *  package-private构造函数
     * 
     */
    LockInfo(Object lock) {
        this.className = lock.getClass().getName();
        this.identityHashCode = System.identityHashCode(lock);
    }

    /**
     * Returns the fully qualified name of the class of the lock object.
     *
     * <p>
     *  返回锁定对象的类的完全限定名称。
     * 
     * 
     * @return the fully qualified name of the class of the lock object.
     */
    public String getClassName() {
        return className;
    }

    /**
     * Returns the identity hash code of the lock object
     * returned from the {@link System#identityHashCode} method.
     *
     * <p>
     *  返回从{@link System#identityHashCode}方法返回的锁对象的标识哈希码。
     * 
     * 
     * @return the identity hash code of the lock object.
     */
    public int getIdentityHashCode() {
        return identityHashCode;
    }

    /**
     * Returns a {@code LockInfo} object represented by the
     * given {@code CompositeData}.
     * The given {@code CompositeData} must contain the following attributes:
     * <blockquote>
     * <table border summary="The attributes and the types the given CompositeData contains">
     * <tr>
     *   <th align=left>Attribute Name</th>
     *   <th align=left>Type</th>
     * </tr>
     * <tr>
     *   <td>className</td>
     *   <td><tt>java.lang.String</tt></td>
     * </tr>
     * <tr>
     *   <td>identityHashCode</td>
     *   <td><tt>java.lang.Integer</tt></td>
     * </tr>
     * </table>
     * </blockquote>
     *
     * <p>
     *  返回由给定的{@code CompositeData}表示的{@code LockInfo}对象。给定的{@code CompositeData}必须包含以下属性：
     * <blockquote>
     * <table border summary="The attributes and the types the given CompositeData contains">
     * <tr>
     *  <th align = left>属性名称</th> <th align = left>键入</th>
     * </tr>
     * <tr>
     *  <td> className </td> <td> <tt> java.lang.String </tt> </td>
     * </tr>
     * <tr>
     *  <td> identityHashCode </td> <td> <tt> java.lang.Integer </tt> </td>
     * </tr>
     * 
     * @param cd {@code CompositeData} representing a {@code LockInfo}
     *
     * @throws IllegalArgumentException if {@code cd} does not
     *   represent a {@code LockInfo} with the attributes described
     *   above.
     * @return a {@code LockInfo} object represented
     *         by {@code cd} if {@code cd} is not {@code null};
     *         {@code null} otherwise.
     *
     * @since 1.8
     */
    public static LockInfo from(CompositeData cd) {
        if (cd == null) {
            return null;
        }

        if (cd instanceof LockInfoCompositeData) {
            return ((LockInfoCompositeData) cd).getLockInfo();
        } else {
            return LockInfoCompositeData.toLockInfo(cd);
        }
    }

    /**
     * Returns a string representation of a lock.  The returned
     * string representation consists of the name of the class of the
     * lock object, the at-sign character `@', and the unsigned
     * hexadecimal representation of the <em>identity</em> hash code
     * of the object.  This method returns a string equals to the value of:
     * <blockquote>
     * <pre>
     * lock.getClass().getName() + '@' + Integer.toHexString(System.identityHashCode(lock))
     * </pre></blockquote>
     * where <tt>lock</tt> is the lock object.
     *
     * <p>
     * </table>
     * </blockquote>
     * 
     * 
     * @return the string representation of a lock.
     */
    public String toString() {
        return className + '@' + Integer.toHexString(identityHashCode);
    }
}
