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

package java.io;

import java.security.*;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.StringTokenizer;

/**
 * This class is for Serializable permissions. A SerializablePermission
 * contains a name (also referred to as a "target name") but
 * no actions list; you either have the named permission
 * or you don't.
 *
 * <P>
 * The target name is the name of the Serializable permission (see below).
 *
 * <P>
 * The following table lists all the possible SerializablePermission target names,
 * and for each provides a description of what the permission allows
 * and a discussion of the risks of granting code the permission.
 *
 * <table border=1 cellpadding=5 summary="Permission target name, what the permission allows, and associated risks">
 * <tr>
 * <th>Permission Target Name</th>
 * <th>What the Permission Allows</th>
 * <th>Risks of Allowing this Permission</th>
 * </tr>
 *
 * <tr>
 *   <td>enableSubclassImplementation</td>
 *   <td>Subclass implementation of ObjectOutputStream or ObjectInputStream
 * to override the default serialization or deserialization, respectively,
 * of objects</td>
 *   <td>Code can use this to serialize or
 * deserialize classes in a purposefully malfeasant manner. For example,
 * during serialization, malicious code can use this to
 * purposefully store confidential private field data in a way easily accessible
 * to attackers. Or, during deserialization it could, for example, deserialize
 * a class with all its private fields zeroed out.</td>
 * </tr>
 *
 * <tr>
 *   <td>enableSubstitution</td>
 *   <td>Substitution of one object for another during
 * serialization or deserialization</td>
 *   <td>This is dangerous because malicious code
 * can replace the actual object with one which has incorrect or
 * malignant data.</td>
 * </tr>
 *
 * </table>
 *
 * <p>
 *  这个类用于Serializable权限。 SerializablePermission包含名称(也称为"目标名称"),但没有动作列表;你有命名的权限或你不。
 * 
 * <P>
 *  目标名称是Serializable权限的名称(见下文)。
 * 
 * <P>
 *  下表列出了所有可能的SerializablePermission目标名称,并为每个提供了权限允许的描述以及授予代码权限的风险的讨论。
 * 
 * <table border=1 cellpadding=5 summary="Permission target name, what the permission allows, and associated risks">
 * <tr>
 *  <th>权限目标名称</th> <th>权限允许</th> <th>允许此权限的风险</th>
 * </tr>
 * 
 * <tr>
 *  <td> enableSubclassImplementation </td> <td> ObjectOutputStream或ObjectInputStream的子类实现分别覆盖对象的默认序列化或反
 * 序列化</td> <td>代码可以使用它以有意的恶意方式对类进行序列化或反序列化。
 * 例如,在序列化期间,恶意代码可以使用它以攻击者容易访问的方式有目的地存储机密私有字段数据。或者,在反序列化期间,它可以,例如,反序列化具有所有其私有字段被归零的类。</td>。
 * 
 * @see java.security.BasicPermission
 * @see java.security.Permission
 * @see java.security.Permissions
 * @see java.security.PermissionCollection
 * @see java.lang.SecurityManager
 *
 *
 * @author Joe Fialli
 * @since 1.2
 */

/* code was borrowed originally from java.lang.RuntimePermission. */

public final class SerializablePermission extends BasicPermission {

    private static final long serialVersionUID = 8537212141160296410L;

    /**
    /* <p>
    /* </tr>
    /* 
    /* <tr>
    /* <td> enableSubstitution </td> <td>在序列化或反序列化期间将一个对象替换为另一个对象</td> <td>这是很危险的,因为恶意代码可以将实际对象替换为具有不正确或恶意数据
    /* 的对象。
    /*  td>。
    /* </tr>
    /* 
    /* </table>
    /* 
     * @serial
     */
    private String actions;

    /**
     * Creates a new SerializablePermission with the specified name.
     * The name is the symbolic name of the SerializablePermission, such as
     * "enableSubstitution", etc.
     *
     * <p>
     * 
     * 
     * @param name the name of the SerializablePermission.
     *
     * @throws NullPointerException if <code>name</code> is <code>null</code>.
     * @throws IllegalArgumentException if <code>name</code> is empty.
     */
    public SerializablePermission(String name)
    {
        super(name);
    }

    /**
     * Creates a new SerializablePermission object with the specified name.
     * The name is the symbolic name of the SerializablePermission, and the
     * actions String is currently unused and should be null.
     *
     * <p>
     * 
     * @param name the name of the SerializablePermission.
     * @param actions currently unused and must be set to null
     *
     * @throws NullPointerException if <code>name</code> is <code>null</code>.
     * @throws IllegalArgumentException if <code>name</code> is empty.
     */

    public SerializablePermission(String name, String actions)
    {
        super(name, actions);
    }
}
