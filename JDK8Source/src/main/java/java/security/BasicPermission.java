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

import java.util.Enumeration;
import java.util.Map;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Collections;
import java.io.ObjectStreamField;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;

/**
 * The BasicPermission class extends the Permission class, and
 * can be used as the base class for permissions that want to
 * follow the same naming convention as BasicPermission.
 * <P>
 * The name for a BasicPermission is the name of the given permission
 * (for example, "exit",
 * "setFactory", "print.queueJob", etc). The naming
 * convention follows the  hierarchical property naming convention.
 * An asterisk may appear by itself, or if immediately preceded by a "."
 * may appear at the end of the name, to signify a wildcard match.
 * For example, "*" and "java.*" signify a wildcard match, while "*java", "a*b",
 * and "java*" do not.
 * <P>
 * The action string (inherited from Permission) is unused.
 * Thus, BasicPermission is commonly used as the base class for
 * "named" permissions
 * (ones that contain a name but no actions list; you either have the
 * named permission or you don't.)
 * Subclasses may implement actions on top of BasicPermission,
 * if desired.
 * <p>
 * <p>
 *  BasicPermission类扩展了Permission类,并且可以用作希望遵循与BasicPermission相同的命名约定的权限的基类。
 * <P>
 *  BasicPermission的名称是给定权限的名称(例如,"exit","setFactory","print.queueJob"等)。命名约定遵循分层属性命名约定。
 * 星号可以单独出现,或者如果紧接在前面有""。可能出现在名称的结尾,表示通配符匹配。例如,"*"和"java。*"表示通配符匹配,而"* java","a * b"和"java *"不匹配。
 * <P>
 *  操作字符串(继承自Permission)未使用。因此,BasicPermission通常用作"命名"权限的基类(包含名称但没有动作列表的基类;您具有命名权限,或者不具有命名权限)。
 * 如果需要,子类可以在BasicPermission之上实现动作。
 * <p>
 * 
 * @see java.security.Permission
 * @see java.security.Permissions
 * @see java.security.PermissionCollection
 * @see java.lang.SecurityManager
 *
 * @author Marianne Mueller
 * @author Roland Schemers
 */

public abstract class BasicPermission extends Permission
    implements java.io.Serializable
{

    private static final long serialVersionUID = 6279438298436773498L;

    // does this permission have a wildcard at the end?
    private transient boolean wildcard;

    // the name without the wildcard on the end
    private transient String path;

    // is this permission the old-style exitVM permission (pre JDK 1.6)?
    private transient boolean exitVM;

    /**
     * initialize a BasicPermission object. Common to all constructors.
     * <p>
     *  初始化一个BasicPermission对象。所有构造函数的公共。
     * 
     */
    private void init(String name) {
        if (name == null)
            throw new NullPointerException("name can't be null");

        int len = name.length();

        if (len == 0) {
            throw new IllegalArgumentException("name can't be empty");
        }

        char last = name.charAt(len - 1);

        // Is wildcard or ends with ".*"?
        if (last == '*' && (len == 1 || name.charAt(len - 2) == '.')) {
            wildcard = true;
            if (len == 1) {
                path = "";
            } else {
                path = name.substring(0, len - 1);
            }
        } else {
            if (name.equals("exitVM")) {
                wildcard = true;
                path = "exitVM.";
                exitVM = true;
            } else {
                path = name;
            }
        }
    }

    /**
     * Creates a new BasicPermission with the specified name.
     * Name is the symbolic name of the permission, such as
     * "setFactory",
     * "print.queueJob", or "topLevelWindow", etc.
     *
     * <p>
     *  创建具有指定名称的新BasicPermission。 Name是权限的符号名称,例如"setFactory","print.queueJob"或"topLevelWindow"等。
     * 
     * 
     * @param name the name of the BasicPermission.
     *
     * @throws NullPointerException if {@code name} is {@code null}.
     * @throws IllegalArgumentException if {@code name} is empty.
     */
    public BasicPermission(String name) {
        super(name);
        init(name);
    }


    /**
     * Creates a new BasicPermission object with the specified name.
     * The name is the symbolic name of the BasicPermission, and the
     * actions String is currently unused.
     *
     * <p>
     *  创建具有指定名称的新BasicPermission对象。名称是BasicPermission的符号名称,并且操作String目前未使用。
     * 
     * 
     * @param name the name of the BasicPermission.
     * @param actions ignored.
     *
     * @throws NullPointerException if {@code name} is {@code null}.
     * @throws IllegalArgumentException if {@code name} is empty.
     */
    public BasicPermission(String name, String actions) {
        super(name);
        init(name);
    }

    /**
     * Checks if the specified permission is "implied" by
     * this object.
     * <P>
     * More specifically, this method returns true if:
     * <ul>
     * <li> <i>p</i>'s class is the same as this object's class, and
     * <li> <i>p</i>'s name equals or (in the case of wildcards)
     *      is implied by this object's
     *      name. For example, "a.b.*" implies "a.b.c".
     * </ul>
     *
     * <p>
     * 检查此对象是否"隐含"指定的权限。
     * <P>
     *  更具体地说,这个方法返回true如果：
     * <ul>
     *  <li> <i> p </i>的类别与此物件的类别相同,而且<li> <i>的名称等于或(在通配符的情况下)对象的名称。例如,"a.b. *"表示"a.b.c"。
     * </ul>
     * 
     * 
     * @param p the permission to check against.
     *
     * @return true if the passed permission is equal to or
     * implied by this permission, false otherwise.
     */
    public boolean implies(Permission p) {
        if ((p == null) || (p.getClass() != getClass()))
            return false;

        BasicPermission that = (BasicPermission) p;

        if (this.wildcard) {
            if (that.wildcard) {
                // one wildcard can imply another
                return that.path.startsWith(path);
            } else {
                // make sure ap.path is longer so a.b.* doesn't imply a.b
                return (that.path.length() > this.path.length()) &&
                    that.path.startsWith(this.path);
            }
        } else {
            if (that.wildcard) {
                // a non-wildcard can't imply a wildcard
                return false;
            }
            else {
                return this.path.equals(that.path);
            }
        }
    }

    /**
     * Checks two BasicPermission objects for equality.
     * Checks that <i>obj</i>'s class is the same as this object's class
     * and has the same name as this object.
     * <P>
     * <p>
     *  检查两个BasicPermission对象是否相等。检查<i> obj </i>的类与此对象的类是否相同,并且具有与此对象相同的名称。
     * <P>
     * 
     * @param obj the object we are testing for equality with this object.
     * @return true if <i>obj</i>'s class is the same as this object's class
     *  and has the same name as this BasicPermission object, false otherwise.
     */
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        if ((obj == null) || (obj.getClass() != getClass()))
            return false;

        BasicPermission bp = (BasicPermission) obj;

        return getName().equals(bp.getName());
    }


    /**
     * Returns the hash code value for this object.
     * The hash code used is the hash code of the name, that is,
     * {@code getName().hashCode()}, where {@code getName} is
     * from the Permission superclass.
     *
     * <p>
     *  返回此对象的哈希码值。使用的哈希码是名称的哈希码,即{@code getName()。hashCode()},其中{@code getName}来自Permission超类。
     * 
     * 
     * @return a hash code value for this object.
     */
    public int hashCode() {
        return this.getName().hashCode();
    }

    /**
     * Returns the canonical string representation of the actions,
     * which currently is the empty string "", since there are no actions for
     * a BasicPermission.
     *
     * <p>
     *  返回动作的规范字符串表示形式,目前是空字符串"",因为没有对BasicPermission的动作。
     * 
     * 
     * @return the empty string "".
     */
    public String getActions() {
        return "";
    }

    /**
     * Returns a new PermissionCollection object for storing BasicPermission
     * objects.
     *
     * <p>BasicPermission objects must be stored in a manner that allows them
     * to be inserted in any order, but that also enables the
     * PermissionCollection {@code implies} method
     * to be implemented in an efficient (and consistent) manner.
     *
     * <p>
     *  返回一个新的PermissionCollection对象用于存储BasicPermission对象。
     * 
     *  <p> BasicPermission对象必须以允许以任何顺序插入的方式存储,但这也使得PermissionCollection {@code implies}方法能够以高效(且一致)的方式实现。
     * 
     * 
     * @return a new PermissionCollection object suitable for
     * storing BasicPermissions.
     */
    public PermissionCollection newPermissionCollection() {
        return new BasicPermissionCollection(this.getClass());
    }

    /**
     * readObject is called to restore the state of the BasicPermission from
     * a stream.
     * <p>
     *  readObject被调用以从流中恢复BasicPermission的状态。
     * 
     */
    private void readObject(ObjectInputStream s)
         throws IOException, ClassNotFoundException
    {
        s.defaultReadObject();
        // init is called to initialize the rest of the values.
        init(getName());
    }

    /**
     * Returns the canonical name of this BasicPermission.
     * All internal invocations of getName should invoke this method, so
     * that the pre-JDK 1.6 "exitVM" and current "exitVM.*" permission are
     * equivalent in equals/hashCode methods.
     *
     * <p>
     *  返回此BasicPermission的规范名称。 getName的所有内部调用都应调用此方法,以使JDK 1.6"exitVM"和当前"exitVM。
     * *"权限在equals / hashCode方法中是等效的。
     * 
     * 
     * @return the canonical name of this BasicPermission.
     */
    final String getCanonicalName() {
        return exitVM ? "exitVM.*" : getName();
    }
}

/**
 * A BasicPermissionCollection stores a collection
 * of BasicPermission permissions. BasicPermission objects
 * must be stored in a manner that allows them to be inserted in any
 * order, but enable the implies function to evaluate the implies
 * method in an efficient (and consistent) manner.
 *
 * A BasicPermissionCollection handles comparing a permission like "a.b.c.d.e"
 * with a Permission such as "a.b.*", or "*".
 *
 * <p>
 * BasicPermissionCollection存储BasicPermission权限的集合。
 *  BasicPermission对象必须以允许以任何顺序插入的方式存储,但使implicit函数能够以高效(且一致)的方式评估implies方法。
 * 
 *  BasicPermissionCollection处理将诸如"a.b.c.d.e"之类的权限与诸如"a.b. *"或"*"之类的权限进行比较。
 * 
 * 
 * @see java.security.Permission
 * @see java.security.Permissions
 *
 *
 * @author Roland Schemers
 *
 * @serial include
 */

final class BasicPermissionCollection
    extends PermissionCollection
    implements java.io.Serializable
{

    private static final long serialVersionUID = 739301742472979399L;

    /**
      * Key is name, value is permission. All permission objects in
      * collection must be of the same type.
      * Not serialized; see serialization section at end of class.
      * <p>
      *  键是名称,值是权限。集合中的所有权限对象必须为相同类型。未序列化;请参见类末尾的序列化部分。
      * 
      */
    private transient Map<String, Permission> perms;

    /**
     * This is set to {@code true} if this BasicPermissionCollection
     * contains a BasicPermission with '*' as its permission name.
     *
     * <p>
     *  如果此BasicPermissionCollection包含具有"*"作为其权限名称的BasicPermission,则设置为{@code true}。
     * 
     * 
     * @see #serialPersistentFields
     */
    private boolean all_allowed;

    /**
     * The class to which all BasicPermissions in this
     * BasicPermissionCollection belongs.
     *
     * <p>
     *  此BasicPermissionCollection中的所有BasicPermissions所属的类。
     * 
     * 
     * @see #serialPersistentFields
     */
    private Class<?> permClass;

    /**
     * Create an empty BasicPermissionCollection object.
     *
     * <p>
     *  创建一个空的BasicPermissionCollection对象。
     * 
     */

    public BasicPermissionCollection(Class<?> clazz) {
        perms = new HashMap<String, Permission>(11);
        all_allowed = false;
        permClass = clazz;
    }

    /**
     * Adds a permission to the BasicPermissions. The key for the hash is
     * permission.path.
     *
     * <p>
     *  向BasicPermissions添加权限。哈希的键是permission.path。
     * 
     * 
     * @param permission the Permission object to add.
     *
     * @exception IllegalArgumentException - if the permission is not a
     *                                       BasicPermission, or if
     *                                       the permission is not of the
     *                                       same Class as the other
     *                                       permissions in this collection.
     *
     * @exception SecurityException - if this BasicPermissionCollection object
     *                                has been marked readonly
     */
    public void add(Permission permission) {
        if (! (permission instanceof BasicPermission))
            throw new IllegalArgumentException("invalid permission: "+
                                               permission);
        if (isReadOnly())
            throw new SecurityException("attempt to add a Permission to a readonly PermissionCollection");

        BasicPermission bp = (BasicPermission) permission;

        // make sure we only add new BasicPermissions of the same class
        // Also check null for compatibility with deserialized form from
        // previous versions.
        if (permClass == null) {
            // adding first permission
            permClass = bp.getClass();
        } else {
            if (bp.getClass() != permClass)
                throw new IllegalArgumentException("invalid permission: " +
                                                permission);
        }

        synchronized (this) {
            perms.put(bp.getCanonicalName(), permission);
        }

        // No sync on all_allowed; staleness OK
        if (!all_allowed) {
            if (bp.getCanonicalName().equals("*"))
                all_allowed = true;
        }
    }

    /**
     * Check and see if this set of permissions implies the permissions
     * expressed in "permission".
     *
     * <p>
     *  检查并确定这组权限是否意味着在"权限"中表达的权限。
     * 
     * 
     * @param permission the Permission object to compare
     *
     * @return true if "permission" is a proper subset of a permission in
     * the set, false if not.
     */
    public boolean implies(Permission permission) {
        if (! (permission instanceof BasicPermission))
            return false;

        BasicPermission bp = (BasicPermission) permission;

        // random subclasses of BasicPermission do not imply each other
        if (bp.getClass() != permClass)
            return false;

        // short circuit if the "*" Permission was added
        if (all_allowed)
            return true;

        // strategy:
        // Check for full match first. Then work our way up the
        // path looking for matches on a.b..*

        String path = bp.getCanonicalName();
        //System.out.println("check "+path);

        Permission x;

        synchronized (this) {
            x = perms.get(path);
        }

        if (x != null) {
            // we have a direct hit!
            return x.implies(permission);
        }

        // work our way up the tree...
        int last, offset;

        offset = path.length()-1;

        while ((last = path.lastIndexOf(".", offset)) != -1) {

            path = path.substring(0, last+1) + "*";
            //System.out.println("check "+path);

            synchronized (this) {
                x = perms.get(path);
            }

            if (x != null) {
                return x.implies(permission);
            }
            offset = last -1;
        }

        // we don't have to check for "*" as it was already checked
        // at the top (all_allowed), so we just return false
        return false;
    }

    /**
     * Returns an enumeration of all the BasicPermission objects in the
     * container.
     *
     * <p>
     *  返回容器中所有BasicPermission对象的枚举。
     * 
     * 
     * @return an enumeration of all the BasicPermission objects.
     */
    public Enumeration<Permission> elements() {
        // Convert Iterator of Map values into an Enumeration
        synchronized (this) {
            return Collections.enumeration(perms.values());
        }
    }

    // Need to maintain serialization interoperability with earlier releases,
    // which had the serializable field:
    //
    // @serial the Hashtable is indexed by the BasicPermission name
    //
    // private Hashtable permissions;
    /**
    /* <p>
    /* 
     * @serialField permissions java.util.Hashtable
     *    The BasicPermissions in this BasicPermissionCollection.
     *    All BasicPermissions in the collection must belong to the same class.
     *    The Hashtable is indexed by the BasicPermission name; the value
     *    of the Hashtable entry is the permission.
     * @serialField all_allowed boolean
     *   This is set to {@code true} if this BasicPermissionCollection
     *   contains a BasicPermission with '*' as its permission name.
     * @serialField permClass java.lang.Class
     *   The class to which all BasicPermissions in this
     *   BasicPermissionCollection belongs.
     */
    private static final ObjectStreamField[] serialPersistentFields = {
        new ObjectStreamField("permissions", Hashtable.class),
        new ObjectStreamField("all_allowed", Boolean.TYPE),
        new ObjectStreamField("permClass", Class.class),
    };

    /**
    /* <p>
    /* 
     * @serialData Default fields.
     */
    /*
     * Writes the contents of the perms field out as a Hashtable for
     * serialization compatibility with earlier releases. all_allowed
     * and permClass unchanged.
     * <p>
     *  将perms字段的内容作为Hashtable写入,以便与早期版本的序列化兼容性。 all_allowed和permClass不变。
     * 
     */
    private void writeObject(ObjectOutputStream out) throws IOException {
        // Don't call out.defaultWriteObject()

        // Copy perms into a Hashtable
        Hashtable<String, Permission> permissions =
                new Hashtable<>(perms.size()*2);

        synchronized (this) {
            permissions.putAll(perms);
        }

        // Write out serializable fields
        ObjectOutputStream.PutField pfields = out.putFields();
        pfields.put("all_allowed", all_allowed);
        pfields.put("permissions", permissions);
        pfields.put("permClass", permClass);
        out.writeFields();
    }

    /**
     * readObject is called to restore the state of the
     * BasicPermissionCollection from a stream.
     * <p>
     *  readObject被调用以从流中恢复BasicPermissionCollection的状态。
     */
    private void readObject(java.io.ObjectInputStream in)
         throws IOException, ClassNotFoundException
    {
        // Don't call defaultReadObject()

        // Read in serialized fields
        ObjectInputStream.GetField gfields = in.readFields();

        // Get permissions
        // writeObject writes a Hashtable<String, Permission> for the
        // permissions key, so this cast is safe, unless the data is corrupt.
        @SuppressWarnings("unchecked")
        Hashtable<String, Permission> permissions =
                (Hashtable<String, Permission>)gfields.get("permissions", null);
        perms = new HashMap<String, Permission>(permissions.size()*2);
        perms.putAll(permissions);

        // Get all_allowed
        all_allowed = gfields.get("all_allowed", false);

        // Get permClass
        permClass = (Class<?>) gfields.get("permClass", null);

        if (permClass == null) {
            // set permClass
            Enumeration<Permission> e = permissions.elements();
            if (e.hasMoreElements()) {
                Permission p = e.nextElement();
                permClass = p.getClass();
            }
        }
    }
}
