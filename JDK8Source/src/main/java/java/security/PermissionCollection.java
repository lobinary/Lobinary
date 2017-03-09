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

import java.util.*;

/**
 * Abstract class representing a collection of Permission objects.
 *
 * <p>With a PermissionCollection, you can:
 * <UL>
 * <LI> add a permission to the collection using the {@code add} method.
 * <LI> check to see if a particular permission is implied in the
 *      collection, using the {@code implies} method.
 * <LI> enumerate all the permissions, using the {@code elements} method.
 * </UL>
 *
 * <p>When it is desirable to group together a number of Permission objects
 * of the same type, the {@code newPermissionCollection} method on that
 * particular type of Permission object should first be called. The default
 * behavior (from the Permission class) is to simply return null.
 * Subclasses of class Permission override the method if they need to store
 * their permissions in a particular PermissionCollection object in order
 * to provide the correct semantics when the
 * {@code PermissionCollection.implies} method is called.
 * If a non-null value is returned, that PermissionCollection must be used.
 * If null is returned, then the caller of {@code newPermissionCollection}
 * is free to store permissions of the
 * given type in any PermissionCollection they choose
 * (one that uses a Hashtable, one that uses a Vector, etc).
 *
 * <p>The PermissionCollection returned by the
 * {@code Permission.newPermissionCollection}
 * method is a homogeneous collection, which stores only Permission objects
 * for a given Permission type.  A PermissionCollection may also be
 * heterogeneous.  For example, Permissions is a PermissionCollection
 * subclass that represents a collection of PermissionCollections.
 * That is, its members are each a homogeneous PermissionCollection.
 * For example, a Permissions object might have a FilePermissionCollection
 * for all the FilePermission objects, a SocketPermissionCollection for all the
 * SocketPermission objects, and so on. Its {@code add} method adds a
 * permission to the appropriate collection.
 *
 * <p>Whenever a permission is added to a heterogeneous PermissionCollection
 * such as Permissions, and the PermissionCollection doesn't yet contain a
 * PermissionCollection of the specified permission's type, the
 * PermissionCollection should call
 * the {@code newPermissionCollection} method on the permission's class
 * to see if it requires a special PermissionCollection. If
 * {@code newPermissionCollection}
 * returns null, the PermissionCollection
 * is free to store the permission in any type of PermissionCollection it
 * desires (one using a Hashtable, one using a Vector, etc.). For example,
 * the Permissions object uses a default PermissionCollection implementation
 * that stores the permission objects in a Hashtable.
 *
 * <p> Subclass implementations of PermissionCollection should assume
 * that they may be called simultaneously from multiple threads,
 * and therefore should be synchronized properly.  Furthermore,
 * Enumerations returned via the {@code elements} method are
 * not <em>fail-fast</em>.  Modifications to a collection should not be
 * performed while enumerating over that collection.
 *
 * <p>
 *  表示Permission对象集合的抽象类。
 * 
 *  <p>使用PermissionCollection,您可以：
 * <UL>
 *  <LI>使用{@code add}方法向该集合添加权限。 <LI>使用{@code implies}方法检查集合中是否暗示了特定权限。
 *  <LI>枚举所有权限,使用{@code elements}方法。
 * </UL>
 * 
 *  <p>当希望将多个相同类型的Permission对象组合在一起时,应首先调用该特定类型的Permission对象上的{@code newPermissionCollection}方法。
 * 默认行为(来自Permission类)是简单地返回null。
 * 如果需要在特定的PermissionCollection对象中存储它们的权限,为了在调用{@code PermissionCollection.implies}方法时提供正确的语义,Permission
 * 的子类重写该方法。
 * 默认行为(来自Permission类)是简单地返回null。如果返回非空值,那么必须使用该PermissionCollection。
 * 如果返回null,那么{@code newPermissionCollection}的调用者可以自由地将给定类型的权限存储在他们选择的任何PermissionCollection中(使用Hashtabl
 * e,使用向量等)。
 * 默认行为(来自Permission类)是简单地返回null。如果返回非空值,那么必须使用该PermissionCollection。
 * 
 * <p> {@code Permission.newPermissionCollection}方法返回的PermissionCollection是一个同类集合,仅存储给定权限类型的Permission对象
 * 。
 *  PermissionCollection也可以是异构的。例如,Permissions是一个PermissionCollection子类,表示PermissionCollections的集合。
 * 也就是说,它的成员都是一个同类的PermissionCollection。
 * 例如,Permissions对象可能具有所有FilePermission对象的FilePermissionCollection,所有SocketPermission对象的SocketPermission
 * Collection,等等。
 * 也就是说,它的成员都是一个同类的PermissionCollection。它的{@code add}方法向适当的集合添加一个权限。
 * 
 *  <p>每当将权限添加到异构PermissionCollection(例如Permissions),并且PermissionCollection尚未包含指定权限类型的PermissionCollecti
 * on时,PermissionCollection应调用该权限类的{@code newPermissionCollection}方法,以查看它需要一个特殊的PermissionCollection。
 * 如果{@code newPermissionCollection}返回null,PermissionCollection可以在任何类型的PermissionCollection中存储权限(一个使用Has
 * htable,一个使用Vector等)。
 * 例如,Permissions对象使用默认的PermissionCollection实现,将实例存储在Hashtable中。
 * 
 * @see Permission
 * @see Permissions
 *
 *
 * @author Roland Schemers
 */

public abstract class PermissionCollection implements java.io.Serializable {

    private static final long serialVersionUID = -6727011328946861783L;

    // when set, add will throw an exception.
    private volatile boolean readOnly;

    /**
     * Adds a permission object to the current collection of permission objects.
     *
     * <p>
     * 
     * <p> PermissionCollection的子类实现应该假设它们可以从多个线程同时调用,因此应该正确同步。
     * 此外,通过{@code elements}方法返回的枚举不是快速失败的<em> </em>。在对该集合进行枚举时,不应对集合进行修改。
     * 
     * 
     * @param permission the Permission object to add.
     *
     * @exception SecurityException -  if this PermissionCollection object
     *                                 has been marked readonly
     * @exception IllegalArgumentException - if this PermissionCollection
     *                object is a homogeneous collection and the permission
     *                is not of the correct type.
     */
    public abstract void add(Permission permission);

    /**
     * Checks to see if the specified permission is implied by
     * the collection of Permission objects held in this PermissionCollection.
     *
     * <p>
     *  将权限对象添加到权限对象的当前集合。
     * 
     * 
     * @param permission the Permission object to compare.
     *
     * @return true if "permission" is implied by the  permissions in
     * the collection, false if not.
     */
    public abstract boolean implies(Permission permission);

    /**
     * Returns an enumeration of all the Permission objects in the collection.
     *
     * <p>
     *  检查以确定指定的权限是否由该PermissionCollection中保存的Permission对象的集合隐含。
     * 
     * 
     * @return an enumeration of all the Permissions.
     */
    public abstract Enumeration<Permission> elements();

    /**
     * Marks this PermissionCollection object as "readonly". After
     * a PermissionCollection object
     * is marked as readonly, no new Permission objects can be added to it
     * using {@code add}.
     * <p>
     *  返回集合中所有Permission对象的枚举。
     * 
     */
    public void setReadOnly() {
        readOnly = true;
    }

    /**
     * Returns true if this PermissionCollection object is marked as readonly.
     * If it is readonly, no new Permission objects can be added to it
     * using {@code add}.
     *
     * <p>By default, the object is <i>not</i> readonly. It can be set to
     * readonly by a call to {@code setReadOnly}.
     *
     * <p>
     *  将此PermissionCollection对象标记为"只读"。将PermissionCollection对象标记为只读后,不能使用{@code add}向其中添加新的Permission对象。
     * 
     * 
     * @return true if this PermissionCollection object is marked as readonly,
     * false otherwise.
     */
    public boolean isReadOnly() {
        return readOnly;
    }

    /**
     * Returns a string describing this PermissionCollection object,
     * providing information about all the permissions it contains.
     * The format is:
     * <pre>
     * super.toString() (
     *   // enumerate all the Permission
     *   // objects and call toString() on them,
     *   // one per line..
     * )</pre>
     *
     * {@code super.toString} is a call to the {@code toString}
     * method of this
     * object's superclass, which is Object. The result is
     * this PermissionCollection's type name followed by this object's
     * hashcode, thus enabling clients to differentiate different
     * PermissionCollections object, even if they contain the same permissions.
     *
     * <p>
     *  如果此PermissionCollection对象标记为只读,则返回true。如果它是只读的,没有新的Permission对象可以使用{@code add}添加到它。
     * 
     *  <p>默认情况下,对象是<i>不是</i> readonly。它可以通过调用{@code setReadOnly}设置为只读。
     * 
     * 
     * @return information about this PermissionCollection object,
     *         as described above.
     *
     */
    public String toString() {
        Enumeration<Permission> enum_ = elements();
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString()+" (\n");
        while (enum_.hasMoreElements()) {
            try {
                sb.append(" ");
                sb.append(enum_.nextElement().toString());
                sb.append("\n");
            } catch (NoSuchElementException e){
                // ignore
            }
        }
        sb.append(")\n");
        return sb.toString();
    }
}
