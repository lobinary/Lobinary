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

package java.util;

import java.io.Serializable;
import java.io.IOException;
import java.security.*;
import java.util.Map;
import java.util.HashMap;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Collections;
import java.io.ObjectStreamField;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import sun.security.util.SecurityConstants;

/**
 * This class is for property permissions.
 *
 * <P>
 * The name is the name of the property ("java.home",
 * "os.name", etc). The naming
 * convention follows the  hierarchical property naming convention.
 * Also, an asterisk
 * may appear at the end of the name, following a ".", or by itself, to
 * signify a wildcard match. For example: "java.*" and "*" signify a wildcard
 * match, while "*java" and "a*b" do not.
 * <P>
 * The actions to be granted are passed to the constructor in a string containing
 * a list of one or more comma-separated keywords. The possible keywords are
 * "read" and "write". Their meaning is defined as follows:
 *
 * <DL>
 *    <DT> read
 *    <DD> read permission. Allows <code>System.getProperty</code> to
 *         be called.
 *    <DT> write
 *    <DD> write permission. Allows <code>System.setProperty</code> to
 *         be called.
 * </DL>
 * <P>
 * The actions string is converted to lowercase before processing.
 * <P>
 * Care should be taken before granting code permission to access
 * certain system properties.  For example, granting permission to
 * access the "java.home" system property gives potentially malevolent
 * code sensitive information about the system environment (the Java
 * installation directory).  Also, granting permission to access
 * the "user.name" and "user.home" system properties gives potentially
 * malevolent code sensitive information about the user environment
 * (the user's account name and home directory).
 *
 * <p>
 *  此类用于属性权限。
 * 
 * <P>
 *  名称是属性的名称("java.home","os.name"等)。命名约定遵循分层属性命名约定。此外,星号可能出现在名称的末尾,跟在"。"后面,或者它本身,表示通配符匹配。例如："java。
 * *"和"*"表示通配符匹配,而"* java"和"a * b"则不匹配。
 * <P>
 *  要授予的操作将在包含一个或多个逗号分隔关键字列表的字符串中传递给构造函数。可能的关键字是"读取"和"写入"。它们的含义定义如下：
 * 
 * <DL>
 *  <DT>读取<DD>读取权限。允许调用<code> System.getProperty </code>。 <DT>写<DD>写权限。
 * 允许调用<code> System.setProperty </code>。
 * </DL>
 * <P>
 *  在处理之前,操作字符串将转换为小写。
 * <P>
 *  在授予代码权限以访问某些系统属性之前,应小心。例如,授予访问"java.home"系统属性的权限可能会给系统环境(Java安装目录)带来恶意的代码敏感信息。
 * 另外,授予访问"user.name"和"user.home"系统属性的权限给出了关于用户环境(用户的帐户名和主目录)的潜在恶意代码敏感信息。
 * 
 * 
 * @see java.security.BasicPermission
 * @see java.security.Permission
 * @see java.security.Permissions
 * @see java.security.PermissionCollection
 * @see java.lang.SecurityManager
 *
 *
 * @author Roland Schemers
 * @since 1.2
 *
 * @serial exclude
 */

public final class PropertyPermission extends BasicPermission {

    /**
     * Read action.
     * <p>
     *  读取操作。
     * 
     */
    private final static int READ    = 0x1;

    /**
     * Write action.
     * <p>
     * 写操作。
     * 
     */
    private final static int WRITE   = 0x2;
    /**
     * All actions (read,write);
     * <p>
     *  所有操作(读,写);
     * 
     */
    private final static int ALL     = READ|WRITE;
    /**
     * No actions.
     * <p>
     *  无操作。
     * 
     */
    private final static int NONE    = 0x0;

    /**
     * The actions mask.
     *
     * <p>
     *  动作掩码。
     * 
     */
    private transient int mask;

    /**
     * The actions string.
     *
     * <p>
     *  操作字符串。
     * 
     * 
     * @serial
     */
    private String actions; // Left null as long as possible, then
                            // created and re-used in the getAction function.

    /**
     * initialize a PropertyPermission object. Common to all constructors.
     * Also called during de-serialization.
     *
     * <p>
     *  初始化PropertyPermission对象。所有构造函数的公共。在反序列化期间也调用。
     * 
     * 
     * @param mask the actions mask to use.
     *
     */
    private void init(int mask) {
        if ((mask & ALL) != mask)
            throw new IllegalArgumentException("invalid actions mask");

        if (mask == NONE)
            throw new IllegalArgumentException("invalid actions mask");

        if (getName() == null)
            throw new NullPointerException("name can't be null");

        this.mask = mask;
    }

    /**
     * Creates a new PropertyPermission object with the specified name.
     * The name is the name of the system property, and
     * <i>actions</i> contains a comma-separated list of the
     * desired actions granted on the property. Possible actions are
     * "read" and "write".
     *
     * <p>
     *  创建具有指定名称的新PropertyPermission对象。名称是系统属性的名称,<i> actions </i>包含在属性上授予的所需操作的逗号分隔列表。可能的操作是"读取"和"写入"。
     * 
     * 
     * @param name the name of the PropertyPermission.
     * @param actions the actions string.
     *
     * @throws NullPointerException if <code>name</code> is <code>null</code>.
     * @throws IllegalArgumentException if <code>name</code> is empty or if
     * <code>actions</code> is invalid.
     */
    public PropertyPermission(String name, String actions) {
        super(name,actions);
        init(getMask(actions));
    }

    /**
     * Checks if this PropertyPermission object "implies" the specified
     * permission.
     * <P>
     * More specifically, this method returns true if:
     * <ul>
     * <li> <i>p</i> is an instanceof PropertyPermission,
     * <li> <i>p</i>'s actions are a subset of this
     * object's actions, and
     * <li> <i>p</i>'s name is implied by this object's
     *      name. For example, "java.*" implies "java.home".
     * </ul>
     * <p>
     *  检查此PropertyPermission对象是否"暗示"指定的权限。
     * <P>
     *  更具体地说,这个方法返回true如果：
     * <ul>
     *  <li> <i> p </i>是PropertyPermission的实例,<li> <i> p </i>的操作是此对象操作的子集,<li> <i> p </i>该名称由该对象的名称隐含。
     * 例如,"java。*"意味着"java.home"。
     * </ul>
     * 
     * @param p the permission to check against.
     *
     * @return true if the specified permission is implied by this object,
     * false if not.
     */
    public boolean implies(Permission p) {
        if (!(p instanceof PropertyPermission))
            return false;

        PropertyPermission that = (PropertyPermission) p;

        // we get the effective mask. i.e., the "and" of this and that.
        // They must be equal to that.mask for implies to return true.

        return ((this.mask & that.mask) == that.mask) && super.implies(that);
    }

    /**
     * Checks two PropertyPermission objects for equality. Checks that <i>obj</i> is
     * a PropertyPermission, and has the same name and actions as this object.
     * <P>
     * <p>
     *  检查两个PropertyPermission对象是否相等。检查<i> obj </i>是否是PropertyPermission,并且具有与此对象相同的名称和操作。
     * <P>
     * 
     * @param obj the object we are testing for equality with this object.
     * @return true if obj is a PropertyPermission, and has the same name and
     * actions as this PropertyPermission object.
     */
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        if (! (obj instanceof PropertyPermission))
            return false;

        PropertyPermission that = (PropertyPermission) obj;

        return (this.mask == that.mask) &&
            (this.getName().equals(that.getName()));
    }

    /**
     * Returns the hash code value for this object.
     * The hash code used is the hash code of this permissions name, that is,
     * <code>getName().hashCode()</code>, where <code>getName</code> is
     * from the Permission superclass.
     *
     * <p>
     *  返回此对象的哈希码值。使用的哈希码是此权限名称的哈希码,即<code> getName()。
     * hashCode()</code>,其中<code> getName </code>来自Permission超类。
     * 
     * 
     * @return a hash code value for this object.
     */
    public int hashCode() {
        return this.getName().hashCode();
    }

    /**
     * Converts an actions String to an actions mask.
     *
     * <p>
     *  将操作字符串转换为操作掩码。
     * 
     * 
     * @param actions the action string.
     * @return the actions mask.
     */
    private static int getMask(String actions) {

        int mask = NONE;

        if (actions == null) {
            return mask;
        }

        // Use object identity comparison against known-interned strings for
        // performance benefit (these values are used heavily within the JDK).
        if (actions == SecurityConstants.PROPERTY_READ_ACTION) {
            return READ;
        } if (actions == SecurityConstants.PROPERTY_WRITE_ACTION) {
            return WRITE;
        } else if (actions == SecurityConstants.PROPERTY_RW_ACTION) {
            return READ|WRITE;
        }

        char[] a = actions.toCharArray();

        int i = a.length - 1;
        if (i < 0)
            return mask;

        while (i != -1) {
            char c;

            // skip whitespace
            while ((i!=-1) && ((c = a[i]) == ' ' ||
                               c == '\r' ||
                               c == '\n' ||
                               c == '\f' ||
                               c == '\t'))
                i--;

            // check for the known strings
            int matchlen;

            if (i >= 3 && (a[i-3] == 'r' || a[i-3] == 'R') &&
                          (a[i-2] == 'e' || a[i-2] == 'E') &&
                          (a[i-1] == 'a' || a[i-1] == 'A') &&
                          (a[i] == 'd' || a[i] == 'D'))
            {
                matchlen = 4;
                mask |= READ;

            } else if (i >= 4 && (a[i-4] == 'w' || a[i-4] == 'W') &&
                                 (a[i-3] == 'r' || a[i-3] == 'R') &&
                                 (a[i-2] == 'i' || a[i-2] == 'I') &&
                                 (a[i-1] == 't' || a[i-1] == 'T') &&
                                 (a[i] == 'e' || a[i] == 'E'))
            {
                matchlen = 5;
                mask |= WRITE;

            } else {
                // parse error
                throw new IllegalArgumentException(
                        "invalid permission: " + actions);
            }

            // make sure we didn't just match the tail of a word
            // like "ackbarfaccept".  Also, skip to the comma.
            boolean seencomma = false;
            while (i >= matchlen && !seencomma) {
                switch(a[i-matchlen]) {
                case ',':
                    seencomma = true;
                    break;
                case ' ': case '\r': case '\n':
                case '\f': case '\t':
                    break;
                default:
                    throw new IllegalArgumentException(
                            "invalid permission: " + actions);
                }
                i--;
            }

            // point i at the location of the comma minus one (or -1).
            i -= matchlen;
        }

        return mask;
    }


    /**
     * Return the canonical string representation of the actions.
     * Always returns present actions in the following order:
     * read, write.
     *
     * <p>
     *  返回操作的规范字符串表示形式。始终按以下顺序返回当前操作：读取,写入。
     * 
     * 
     * @return the canonical string representation of the actions.
     */
    static String getActions(int mask) {
        StringBuilder sb = new StringBuilder();
        boolean comma = false;

        if ((mask & READ) == READ) {
            comma = true;
            sb.append("read");
        }

        if ((mask & WRITE) == WRITE) {
            if (comma) sb.append(',');
            else comma = true;
            sb.append("write");
        }
        return sb.toString();
    }

    /**
     * Returns the "canonical string representation" of the actions.
     * That is, this method always returns present actions in the following order:
     * read, write. For example, if this PropertyPermission object
     * allows both write and read actions, a call to <code>getActions</code>
     * will return the string "read,write".
     *
     * <p>
     * 返回操作的"规范字符串表示"。也就是说,此方法总是按以下顺序返回当前操作：读取,写入。
     * 例如,如果此PropertyPermission对象允许写入和读取操作,则调用<code> getActions </code>将返回字符串"read,write"。
     * 
     * 
     * @return the canonical string representation of the actions.
     */
    public String getActions() {
        if (actions == null)
            actions = getActions(this.mask);

        return actions;
    }

    /**
     * Return the current action mask.
     * Used by the PropertyPermissionCollection
     *
     * <p>
     *  返回当前操作掩码。由PropertyPermissionCollection使用
     * 
     * 
     * @return the actions mask.
     */
    int getMask() {
        return mask;
    }

    /**
     * Returns a new PermissionCollection object for storing
     * PropertyPermission objects.
     * <p>
     *
     * <p>
     *  返回用于存储PropertyPermission对象的新PermissionCollection对象。
     * <p>
     * 
     * 
     * @return a new PermissionCollection object suitable for storing
     * PropertyPermissions.
     */
    public PermissionCollection newPermissionCollection() {
        return new PropertyPermissionCollection();
    }


    private static final long serialVersionUID = 885438825399942851L;

    /**
     * WriteObject is called to save the state of the PropertyPermission
     * to a stream. The actions are serialized, and the superclass
     * takes care of the name.
     * <p>
     *  WriteObject被调用以将PropertyPermission的状态保存到流。操作是序列化的,超类负责处理名称。
     * 
     */
    private synchronized void writeObject(java.io.ObjectOutputStream s)
        throws IOException
    {
        // Write out the actions. The superclass takes care of the name
        // call getActions to make sure actions field is initialized
        if (actions == null)
            getActions();
        s.defaultWriteObject();
    }

    /**
     * readObject is called to restore the state of the PropertyPermission from
     * a stream.
     * <p>
     *  readObject被调用以从流中恢复PropertyPermission的状态。
     * 
     */
    private synchronized void readObject(java.io.ObjectInputStream s)
         throws IOException, ClassNotFoundException
    {
        // Read in the action, then initialize the rest
        s.defaultReadObject();
        init(getMask(actions));
    }
}

/**
 * A PropertyPermissionCollection stores a set of PropertyPermission
 * permissions.
 *
 * <p>
 *  PropertyPermissionCollection存储一组PropertyPermission权限。
 * 
 * 
 * @see java.security.Permission
 * @see java.security.Permissions
 * @see java.security.PermissionCollection
 *
 *
 * @author Roland Schemers
 *
 * @serial include
 */
final class PropertyPermissionCollection extends PermissionCollection
    implements Serializable
{

    /**
     * Key is property name; value is PropertyPermission.
     * Not serialized; see serialization section at end of class.
     * <p>
     *  Key是属性名;值为PropertyPermission。未序列化;请参见类末尾的序列化部分。
     * 
     */
    private transient Map<String, PropertyPermission> perms;

    /**
     * Boolean saying if "*" is in the collection.
     *
     * <p>
     *  布尔说如果"*"在集合中。
     * 
     * 
     * @see #serialPersistentFields
     */
    // No sync access; OK for this to be stale.
    private boolean all_allowed;

    /**
     * Create an empty PropertyPermissionCollection object.
     * <p>
     *  创建一个空PropertyPermissionCollection对象。
     * 
     */
    public PropertyPermissionCollection() {
        perms = new HashMap<>(32);     // Capacity for default policy
        all_allowed = false;
    }

    /**
     * Adds a permission to the PropertyPermissions. The key for the hash is
     * the name.
     *
     * <p>
     *  向PropertyPermissions添加权限。哈希的键是名称。
     * 
     * 
     * @param permission the Permission object to add.
     *
     * @exception IllegalArgumentException - if the permission is not a
     *                                       PropertyPermission
     *
     * @exception SecurityException - if this PropertyPermissionCollection
     *                                object has been marked readonly
     */
    public void add(Permission permission) {
        if (! (permission instanceof PropertyPermission))
            throw new IllegalArgumentException("invalid permission: "+
                                               permission);
        if (isReadOnly())
            throw new SecurityException(
                "attempt to add a Permission to a readonly PermissionCollection");

        PropertyPermission pp = (PropertyPermission) permission;
        String propName = pp.getName();

        synchronized (this) {
            PropertyPermission existing = perms.get(propName);

            if (existing != null) {
                int oldMask = existing.getMask();
                int newMask = pp.getMask();
                if (oldMask != newMask) {
                    int effective = oldMask | newMask;
                    String actions = PropertyPermission.getActions(effective);
                    perms.put(propName, new PropertyPermission(propName, actions));
                }
            } else {
                perms.put(propName, pp);
            }
        }

        if (!all_allowed) {
            if (propName.equals("*"))
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
        if (! (permission instanceof PropertyPermission))
                return false;

        PropertyPermission pp = (PropertyPermission) permission;
        PropertyPermission x;

        int desired = pp.getMask();
        int effective = 0;

        // short circuit if the "*" Permission was added
        if (all_allowed) {
            synchronized (this) {
                x = perms.get("*");
            }
            if (x != null) {
                effective |= x.getMask();
                if ((effective & desired) == desired)
                    return true;
            }
        }

        // strategy:
        // Check for full match first. Then work our way up the
        // name looking for matches on a.b.*

        String name = pp.getName();
        //System.out.println("check "+name);

        synchronized (this) {
            x = perms.get(name);
        }

        if (x != null) {
            // we have a direct hit!
            effective |= x.getMask();
            if ((effective & desired) == desired)
                return true;
        }

        // work our way up the tree...
        int last, offset;

        offset = name.length()-1;

        while ((last = name.lastIndexOf(".", offset)) != -1) {

            name = name.substring(0, last+1) + "*";
            //System.out.println("check "+name);
            synchronized (this) {
                x = perms.get(name);
            }

            if (x != null) {
                effective |= x.getMask();
                if ((effective & desired) == desired)
                    return true;
            }
            offset = last -1;
        }

        // we don't have to check for "*" as it was already checked
        // at the top (all_allowed), so we just return false
        return false;
    }

    /**
     * Returns an enumeration of all the PropertyPermission objects in the
     * container.
     *
     * <p>
     *  返回容器中所有PropertyPermission对象的枚举。
     * 
     * 
     * @return an enumeration of all the PropertyPermission objects.
     */
    @SuppressWarnings("unchecked")
    public Enumeration<Permission> elements() {
        // Convert Iterator of Map values into an Enumeration
        synchronized (this) {
            /**
             * Casting to rawtype since Enumeration<PropertyPermission>
             * cannot be directly cast to Enumeration<Permission>
             * <p>
             *  因为枚举<PropertyPermission>不能直接转换为枚举<Permission>
             * 
             */
            return (Enumeration)Collections.enumeration(perms.values());
        }
    }

    private static final long serialVersionUID = 7015263904581634791L;

    // Need to maintain serialization interoperability with earlier releases,
    // which had the serializable field:
    //
    // Table of permissions.
    //
    // @serial
    //
    // private Hashtable permissions;
    /**
    /* <p>
    /* 
     * @serialField permissions java.util.Hashtable
     *     A table of the PropertyPermissions.
     * @serialField all_allowed boolean
     *     boolean saying if "*" is in the collection.
     */
    private static final ObjectStreamField[] serialPersistentFields = {
        new ObjectStreamField("permissions", Hashtable.class),
        new ObjectStreamField("all_allowed", Boolean.TYPE),
    };

    /**
    /* <p>
    /* 
     * @serialData Default fields.
     */
    /*
     * Writes the contents of the perms field out as a Hashtable for
     * serialization compatibility with earlier releases. all_allowed
     * unchanged.
     * <p>
     *  将perms字段的内容作为Hashtable写入,以便与早期版本的序列化兼容性。 all_allowed不变。
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
        out.writeFields();
    }

    /*
     * Reads in a Hashtable of PropertyPermissions and saves them in the
     * perms field. Reads in all_allowed.
     * <p>
     * 读取PropertyPermissions的Hashtable并将其保存在perms字段中。在all_allowed中读取。
     */
    private void readObject(ObjectInputStream in)
        throws IOException, ClassNotFoundException
    {
        // Don't call defaultReadObject()

        // Read in serialized fields
        ObjectInputStream.GetField gfields = in.readFields();

        // Get all_allowed
        all_allowed = gfields.get("all_allowed", false);

        // Get permissions
        @SuppressWarnings("unchecked")
        Hashtable<String, PropertyPermission> permissions =
            (Hashtable<String, PropertyPermission>)gfields.get("permissions", null);
        perms = new HashMap<>(permissions.size()*2);
        perms.putAll(permissions);
    }
}
