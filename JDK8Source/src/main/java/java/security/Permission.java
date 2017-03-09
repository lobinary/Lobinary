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
 * Abstract class for representing access to a system resource.
 * All permissions have a name (whose interpretation depends on the subclass),
 * as well as abstract functions for defining the semantics of the
 * particular Permission subclass.
 *
 * <p>Most Permission objects also include an "actions" list that tells the actions
 * that are permitted for the object.  For example,
 * for a {@code java.io.FilePermission} object, the permission name is
 * the pathname of a file (or directory), and the actions list
 * (such as "read, write") specifies which actions are granted for the
 * specified file (or for files in the specified directory).
 * The actions list is optional for Permission objects, such as
 * {@code java.lang.RuntimePermission},
 * that don't need such a list; you either have the named permission (such
 * as "system.exit") or you don't.
 *
 * <p>An important method that must be implemented by each subclass is
 * the {@code implies} method to compare Permissions. Basically,
 * "permission p1 implies permission p2" means that
 * if one is granted permission p1, one is naturally granted permission p2.
 * Thus, this is not an equality test, but rather more of a
 * subset test.
 *
 * <P> Permission objects are similar to String objects in that they
 * are immutable once they have been created. Subclasses should not
 * provide methods that can change the state of a permission
 * once it has been created.
 *
 * <p>
 *  用于表示对系统资源的访问的抽象类。所有权限都有一个名称(其解释取决于子类),以及用于定义特定Permission子类的语义的抽象函数。
 * 
 *  <p>大多数权限对象还包括一个"操作"列表,用于指示对象允许的操作。
 * 例如,对于{@code java.io.FilePermission}对象,权限名称是文件(或目录)的路径名,操作列表(例如"读取,写入")​​指定为指定文件(或指定目录中的文件)。
 * 对于不需要这样的列表的Permission对象(例如{@code java.lang.RuntimePermission}),操作列表是可选的;你有命名权限(如"system.exit")或者你没有。
 * 
 *  <p>每个子类必须实现的一个重要方法是{@code implies}方法来比较权限。基本上,"权限p1意味着权限p2"意味着如果一个被授予权限p1,则自然地授予权限p2。
 * 因此,这不是一个等式测试,而是更多的子集测试。
 * 
 * <P>权限对象类似于String对象,因为它们在创建后是不可变的。子类不应提供在创建后可以更改权限状态的方法。
 * 
 * 
 * @see Permissions
 * @see PermissionCollection
 *
 *
 * @author Marianne Mueller
 * @author Roland Schemers
 */

public abstract class Permission implements Guard, java.io.Serializable {

    private static final long serialVersionUID = -5636570222231596674L;

    private String name;

    /**
     * Constructs a permission with the specified name.
     *
     * <p>
     *  构造具有指定名称的权限。
     * 
     * 
     * @param name name of the Permission object being created.
     *
     */

    public Permission(String name) {
        this.name = name;
    }

    /**
     * Implements the guard interface for a permission. The
     * {@code SecurityManager.checkPermission} method is called,
     * passing this permission object as the permission to check.
     * Returns silently if access is granted. Otherwise, throws
     * a SecurityException.
     *
     * <p>
     *  实现保护接口的权限。调用{@code SecurityManager.checkPermission}方法,传递此权限对象作为检查权限。如果授予访问权限,则默认返回。
     * 否则,抛出一个SecurityException。
     * 
     * 
     * @param object the object being guarded (currently ignored).
     *
     * @throws SecurityException
     *        if a security manager exists and its
     *        {@code checkPermission} method doesn't allow access.
     *
     * @see Guard
     * @see GuardedObject
     * @see SecurityManager#checkPermission
     *
     */
    public void checkGuard(Object object) throws SecurityException {
        SecurityManager sm = System.getSecurityManager();
        if (sm != null) sm.checkPermission(this);
    }

    /**
     * Checks if the specified permission's actions are "implied by"
     * this object's actions.
     * <P>
     * This must be implemented by subclasses of Permission, as they are the
     * only ones that can impose semantics on a Permission object.
     *
     * <p>The {@code implies} method is used by the AccessController to determine
     * whether or not a requested permission is implied by another permission that
     * is known to be valid in the current execution context.
     *
     * <p>
     *  检查指定的权限的操作是否"暗示"此对象的操作。
     * <P>
     *  这必须由Permission的子类实现,因为它们是唯一可以在Permission对象上施加语义的类。
     * 
     *  <p> AccessController使用{@code implies}方法来确定所请求的权限是否被已知在当前执行上下文中有效的另一个权限所暗示。
     * 
     * 
     * @param permission the permission to check against.
     *
     * @return true if the specified permission is implied by this object,
     * false if not.
     */

    public abstract boolean implies(Permission permission);

    /**
     * Checks two Permission objects for equality.
     * <P>
     * Do not use the {@code equals} method for making access control
     * decisions; use the {@code implies} method.
     *
     * <p>
     *  检查两个Permission对象是否相等。
     * <P>
     *  不要使用{@code equals}方法来进行访问控制决策;使用{@code implies}方法。
     * 
     * 
     * @param obj the object we are testing for equality with this object.
     *
     * @return true if both Permission objects are equivalent.
     */

    public abstract boolean equals(Object obj);

    /**
     * Returns the hash code value for this Permission object.
     * <P>
     * The required {@code hashCode} behavior for Permission Objects is
     * the following:
     * <ul>
     * <li>Whenever it is invoked on the same Permission object more than
     *     once during an execution of a Java application, the
     *     {@code hashCode} method
     *     must consistently return the same integer. This integer need not
     *     remain consistent from one execution of an application to another
     *     execution of the same application.
     * <li>If two Permission objects are equal according to the
     *     {@code equals}
     *     method, then calling the {@code hashCode} method on each of the
     *     two Permission objects must produce the same integer result.
     * </ul>
     *
     * <p>
     *  返回此Permission对象的哈希码值。
     * <P>
     *  Permission对象所需的{@code hashCode}行为如下：
     * <ul>
     * <li>每当在Java应用程序执行期间在同一个Permission对象上多次调用它时,{@code hashCode}方法必须始终返回相同的整数。
     * 从应用程序的一个执行到相同应用程序的另一个执行,此整数不需要保持一致。
     *  <li>如果两个Permission对象根据{@code equals}方法相等,则对两个Permission对象中的每一个调用{@code hashCode}方法必须产生相同的整数结果。
     * </ul>
     * 
     * 
     * @return a hash code value for this object.
     */

    public abstract int hashCode();

    /**
     * Returns the name of this Permission.
     * For example, in the case of a {@code java.io.FilePermission},
     * the name will be a pathname.
     *
     * <p>
     *  返回此权限的名称。例如,在{@code java.io.FilePermission}的情况下,该名称将是一个路径名。
     * 
     * 
     * @return the name of this Permission.
     *
     */

    public final String getName() {
        return name;
    }

    /**
     * Returns the actions as a String. This is abstract
     * so subclasses can defer creating a String representation until
     * one is needed. Subclasses should always return actions in what they
     * consider to be their
     * canonical form. For example, two FilePermission objects created via
     * the following:
     *
     * <pre>
     *   perm1 = new FilePermission(p1,"read,write");
     *   perm2 = new FilePermission(p2,"write,read");
     * </pre>
     *
     * both return
     * "read,write" when the {@code getActions} method is invoked.
     *
     * <p>
     *  以String形式返回操作。这是抽象的,所以子类可以推迟创建一个String表示,直到需要一个。子类应该总是返回他们认为是他们的规范形式的操作。
     * 例如,通过以下方式创建的两个FilePermission对象：。
     * 
     * <pre>
     *  perm1 = new FilePermission(p1,"read,write"); perm2 = new FilePermission(p2,"write,read");
     * </pre>
     * 
     *  当调用{@code getActions}方法时返回"读,写"。
     * 
     * 
     * @return the actions of this Permission.
     *
     */

    public abstract String getActions();

    /**
     * Returns an empty PermissionCollection for a given Permission object, or null if
     * one is not defined. Subclasses of class Permission should
     * override this if they need to store their permissions in a particular
     * PermissionCollection object in order to provide the correct semantics
     * when the {@code PermissionCollection.implies} method is called.
     * If null is returned,
     * then the caller of this method is free to store permissions of this
     * type in any PermissionCollection they choose (one that uses a Hashtable,
     * one that uses a Vector, etc).
     *
     * <p>
     * 返回给定Permission对象的空PermissionCollection,如果没有定义,则返回null。
     * 如果需要在特定的PermissionCollection对象中存储他们的权限,为了在调用{@code PermissionCollection.implies}方法时提供正确的语义,类Permissio
     * n的子类应该重写这个。
     * 返回给定Permission对象的空PermissionCollection,如果没有定义,则返回null。
     * 如果返回null,那么这个方法的调用者可以自由地将这种类型的权限存储在他们选择的任何PermissionCollection中(一个使用Hashtable,一个使用Vector等)。
     * 
     * @return a new PermissionCollection object for this type of Permission, or
     * null if one is not defined.
     */

    public PermissionCollection newPermissionCollection() {
        return null;
    }

    /**
     * Returns a string describing this Permission.  The convention is to
     * specify the class name, the permission name, and the actions in
     * the following format: '("ClassName" "name" "actions")', or
     * '("ClassName" "name")' if actions list is null or empty.
     *
     * <p>
     * 
     * 
     * @return information about this Permission.
     */
    public String toString() {
        String actions = getActions();
        if ((actions == null) || (actions.length() == 0)) { // OPTIONAL
            return "(\"" + getClass().getName() + "\" \"" + name + "\")";
        } else {
            return "(\"" + getClass().getName() + "\" \"" + name +
                 "\" \"" + actions + "\")";
        }
    }
}
