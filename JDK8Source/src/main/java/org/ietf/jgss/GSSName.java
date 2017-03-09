/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2013, Oracle and/or its affiliates. All rights reserved.
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

package org.ietf.jgss;

import sun.security.jgss.spi.*;
import java.util.Vector;
import java.util.Enumeration;

/**
 * This interface encapsulates a single GSS-API principal entity. The
 * application obtains an implementation of this interface
 * through one of the <code>createName</code> methods that exist in the {@link
 * GSSManager GSSManager} class. Conceptually a GSSName contains many
 * representations of the entity or many primitive name elements, one for
 * each supported underlying mechanism. In GSS terminology, a GSSName that
 * contains an element from just one mechanism is called a Mechanism Name
 * (MN)<p>
 *
 * Since different authentication mechanisms may employ different
 * namespaces for identifying their principals, GSS-API's naming support is
 * necessarily complex in multi-mechanism environments (or even in some
 * single-mechanism environments where the underlying mechanism supports
 * multiple namespaces). Different name formats and their definitions are
 * identified with {@link Oid Oid's} and some standard types
 * are defined in this interface. The format of the names can be derived
 * based on the unique <code>Oid</code> of its name type.<p>
 *
 * Included below are code examples utilizing the <code>GSSName</code> interface.
 * The code below creates a <code>GSSName</code>, converts it to an MN, performs a
 * comparison, obtains a printable representation of the name, exports it
 * to a byte array and then re-imports to obtain a
 * new <code>GSSName</code>.<p>
 * <pre>
 *      GSSManager manager = GSSManager.getInstance();
 *
 *      // create a host based service name
 *      GSSName name = manager.createName("service@host",
 *                   GSSName.NT_HOSTBASED_SERVICE);
 *
 *      Oid krb5 = new Oid("1.2.840.113554.1.2.2");
 *
 *      GSSName mechName = name.canonicalize(krb5);
 *
 *      // the above two steps are equivalent to the following
 *      GSSName mechName = manager.createName("service@host",
 *                      GSSName.NT_HOSTBASED_SERVICE, krb5);
 *
 *      // perform name comparison
 *      if (name.equals(mechName))
 *              print("Names are equals.");
 *
 *      // obtain textual representation of name and its printable
 *      // name type
 *      print(mechName.toString() +
 *                      mechName.getStringNameType().toString());
 *
 *      // export and re-import the name
 *      byte [] exportName = mechName.export();
 *
 *      // create a new name object from the exported buffer
 *      GSSName newName = manager.createName(exportName,
 *                      GSSName.NT_EXPORT_NAME);
 *
 * </pre>
 * <p>
 *  此接口封装单个GSS-API主体实体。应用程序通过{@link GSSManager GSSManager}类中存在的<code> createName </code>方法之一获得此接口的实现。
 * 在概念上,GSSName包含实体或许多原始名称元素的许多表示,每个支持的底层机制一个。在GSS术语中,包含来自仅一个机制的元素的GSSName称为机制名称(MN)<p>。
 * 
 *  由于不同的认证机制可以使用不同的命名空间来标识它们的原则,所以GSS-API的命名支持在多机制环境(或者甚至在底层机制支持多个命名空间的单机制环境中)中必然是复杂的。
 * 不同的名称格式及其定义用{@link Oid Oid's}标识,一些标准类型在此界面中定义。名称的格式可以基于其名称类型的唯一<code> Oid </code>得到。<p>。
 * 
 *  下面包含使用<code> GSSName </code>接口的代码示例。
 * 下面的代码创建一个<code> GSSName </code>,将其转换为MN,执行比较,获取名称的可打印表示,将其导出到字节数组,然后重新导入以获取新的<code> GSSName </code>。
 * <p>。
 * <pre>
 * GSSManager manager = GSSManager.getInstance();
 * 
 *  //创建一个基于主机的服务名称GSSName name = manager.createName("service @ host",GSSName.NT_HOSTBASED_SERVICE);
 * 
 *  Oid krb5 = new Oid("1.2.840.113554.1.2.2");
 * 
 *  GSSName mechName = name.canonicalize(krb5);
 * 
 *  //以上两个步骤等效于以下GSSName mechName = manager.createName("service @ host",GSSName.NT_HOSTBASED_SERVICE,krb
 * 5);。
 * 
 *  //执行名称比较if(name.equals(mechName))print("Names are equal。");
 * 
 *  //获取名称及其可打印的文本表示形式//名称类型print(mechName.toString()+ mechName.getStringNameType()。toString());
 * 
 *  // export and re-import the name byte [] exportName = mechName.export();
 * 
 *  //从导出的缓冲区创建一个新的名称对象GSSName newName = manager.createName(exportName,GSSName.NT_EXPORT_NAME);
 * 
 * </pre>
 * 
 * @see #export()
 * @see #equals(GSSName)
 * @see GSSManager#createName(String, Oid)
 * @see GSSManager#createName(String, Oid, Oid)
 * @see GSSManager#createName(byte[], Oid)
 *
 * @author Mayank Upadhyay
 * @since 1.4
 */
public interface GSSName {

    /**
     * Oid indicating a host-based service name form.  It is used to
     * represent services associated with host computers.  This name form
     * is constructed using two elements, "service" and "hostname", as
     * follows: service@hostname.<p>
     *
     * It represents the following Oid value:<br>
     *  <code>{ iso(1) member-body(2) United
     * States(840) mit(113554) infosys(1) gssapi(2) generic(1) service_name(4)
     * }</code>
     * <p>
     *  Oid指示基于主机的服务名称形式。它用于表示与主机计算机相关的服务。此名称形式使用两个元素"service"和"hostname"构建,如下所示：service @ hostname。<p>
     * 
     *  它代表以下Oid值：<br> <code> {iso(1)member-body(2)United States(840)mit(113554)infosys(1)gssapi(2)generic </code>。
     * 
     */
    public static final Oid NT_HOSTBASED_SERVICE
        = Oid.getInstance("1.2.840.113554.1.2.1.4");

    /**
     * Name type to indicate a named user on a local system.<p>
     * It represents the following Oid value:<br>
     *  <code>{ iso(1) member-body(2) United
     * States(840) mit(113554) infosys(1) gssapi(2) generic(1) user_name(1)
     * }</code>
     * <p>
     *  <p>它代表以下Oid值：<br> <code> {iso(1)member-body(2)United States(840)mit(113554)infosys 1)gssapi(2)general(1)user_name(1)}
     *  </code>。
     * 
     */
    public static final Oid NT_USER_NAME
        = Oid.getInstance("1.2.840.113554.1.2.1.1");

    /**
     * Name type to indicate a numeric user identifier corresponding to a
     * user on a local system. (e.g. Uid).<p>
     *
     *  It represents the following Oid value:<br>
     * <code>{ iso(1) member-body(2) United States(840) mit(113554)
     * infosys(1) gssapi(2) generic(1) machine_uid_name(2) }</code>
     * <p>
     * 名称类型,用于指示与本地系统上的用户对应的数字用户标识符。 (例如Uid)。<p>
     * 
     *  它代表以下Oid值：<br> <code> {iso(1)member-body(2)United States(840)mit(113554)infosys(1)gssapi(2)generic(1)machine_uid_name </code>。
     * 
     */
    public static final Oid NT_MACHINE_UID_NAME
        = Oid.getInstance("1.2.840.113554.1.2.1.2");

    /**
     * Name type to indicate a string of digits representing the numeric
     * user identifier of a user on a local system.<p>
     *
     * It represents the following Oid value:<br>
     * <code>{ iso(1) member-body(2) United
     * States(840) mit(113554) infosys(1) gssapi(2) generic(1)
     * string_uid_name(3) }</code>
     * <p>
     *  名称类型,用于指示表示本地系统上用户的数字用户标识符的数字字符串。<p>
     * 
     *  它代表以下Oid值：<br> <code> {iso(1)member-body(2)United States(840)mit(113554)infosys(1)gssapi(2)generic(1)string_uid_name </code>。
     * 
     */
    public static final Oid NT_STRING_UID_NAME
        = Oid.getInstance("1.2.840.113554.1.2.1.3");

    /**
     * Name type for representing an anonymous entity.<p>
     * It represents the following Oid value:<br>
     * <code>{ 1(iso), 3(org), 6(dod), 1(internet),
     * 5(security), 6(nametypes), 3(gss-anonymous-name) }</code>
     * <p>
     *  表示匿名实体的名称类型。
     * <p>它代表以下Oid值：<br> <code> {1(iso),3(org),6(dod),1(互联网),5 6(nametype),3(gss-anonymous-name)} </code>。
     * 
     */
    public static final Oid NT_ANONYMOUS
        = Oid.getInstance("1.3.6.1.5.6.3");

    /**
     * Name type used to indicate an exported name produced by the export
     * method.<p>
     *
     * It represents the following Oid value:<br> <code>{ 1(iso),
     * 3(org), 6(dod), 1(internet), 5(security), 6(nametypes),
     * 4(gss-api-exported-name) }</code>
     * <p>
     *  用于表示导出方法生成的导出名称的名称类型。<p>
     * 
     *  它代表以下Oid值：<br> <code> {1(iso),3(org),6(dod),1(互联网),5(安全),6(nametype),4(gss- exports-name)} </code>
     * 
     */
    public static final Oid NT_EXPORT_NAME
        = Oid.getInstance("1.3.6.1.5.6.4");

    /**
     * Compares two <code>GSSName</code> objects to determine if they refer to the
     * same entity.
     *
     * <p>
     *  比较两个<code> GSSName </code>对象以确定它们是否引用同一实体。
     * 
     * 
     * @param another the <code>GSSName</code> to compare this name with
     * @return true if the two names contain at least one primitive element
     * in common. If either of the names represents an anonymous entity, the
     * method will return false.
     *
     * @throws GSSException when the names cannot be compared, containing the following
     * major error codes:
     *         {@link GSSException#BAD_NAMETYPE GSSException.BAD_NAMETYPE},
     *         {@link GSSException#FAILURE GSSException.FAILURE}
     */
    public boolean equals(GSSName another) throws GSSException;

    /**
     * Compares this <code>GSSName</code> object to another Object that might be a
     * <code>GSSName</code>. The behaviour is exactly the same as in {@link
     * #equals(GSSName) equals} except that no GSSException is thrown;
     * instead, false will be returned in the situation where an error
     * occurs.
     * <p>
     *  将此<code> GSSName </code>对象与另一个可能是<code> GSSName </code>的对象进行比较。
     * 该行为与{@link #equals(GSSName)equals}中的行为完全相同,只是不会抛出GSSException;相反,在发生错误的情况下将返回false。
     * 
     * 
     * @return true if the object to compare to is also a <code>GSSName</code> and the two
     * names refer to the same entity.
     * @param another the object to compare this name to
     * @see #equals(GSSName)
     */
    public boolean equals(Object another);

    /**
     * Returns a hashcode value for this GSSName.
     *
     * <p>
     *  返回此GSSName的哈希码值。
     * 
     * 
     * @return a hashCode value
     */
    public int hashCode();

    /**
     * Creates a name that is canonicalized for some
     * mechanism.
     *
     * <p>
     *  创建为某些机制规范化的名称。
     * 
     * 
     * @return a <code>GSSName</code> that contains just one primitive
     * element representing this name in a canonicalized form for the desired
     * mechanism.
     * @param mech the oid for the mechanism for which the canonical form of
     * the name is requested.
     *
     * @throws GSSException containing the following
     * major error codes:
     *         {@link GSSException#BAD_MECH GSSException.BAD_MECH},
     *         {@link GSSException#BAD_NAMETYPE GSSException.BAD_NAMETYPE},
     *         {@link GSSException#BAD_NAME GSSException.BAD_NAME},
     *         {@link GSSException#FAILURE GSSException.FAILURE}
     */
    public GSSName canonicalize(Oid mech) throws GSSException;

    /**
     * Returns a canonical contiguous byte representation of a mechanism name
     * (MN), suitable for direct, byte by byte comparison by authorization
     * functions.  If the name is not an MN, implementations may throw a
     * GSSException with the NAME_NOT_MN status code.  If an implementation
     * chooses not to throw an exception, it should use some system specific
     * default mechanism to canonicalize the name and then export
     * it. Structurally, an exported name object consists of a header
     * containing an OID identifying the mechanism that authenticated the
     * name, and a trailer containing the name itself, where the syntax of
     * the trailer is defined by the individual mechanism specification. The
     * format of the header of the output buffer is specified in RFC 2743.<p>
     *
     * The exported name is useful when used in large access control lists
     * where the overhead of creating a <code>GSSName</code> object on each
     * name and invoking the equals method on each name from the ACL may be
     * prohibitive.<p>
     *
     * Exported names may be re-imported by using the byte array factory
     * method {@link GSSManager#createName(byte[], Oid)
     * GSSManager.createName} and specifying the NT_EXPORT_NAME as the name
     * type object identifier. The resulting <code>GSSName</code> name will
     * also be a MN.<p>
     * <p>
     * 返回机制名称(MN)的规范连续字节表示,适用于直接,逐字节比较授权函数。如果名称不是MN,实现可能会抛出带有NAME_NOT_MN状态代码的GSSException。
     * 如果实现选择不抛出异常,它应该使用一些系统特定的默认机制来规范化名称,然后导出它。
     * 在结构上,导出的名称对象由包含OID的头组成,该OID标识认证该名称的机制,以及包含名称本身的尾部,其中尾部的语法由单独的机制规范定义。输出缓冲区的头的格式在RFC 2743中指定。<p>。
     * 
     *  导出的名称在大型访问控制列表中使用时很有用,其中在每个名称上创建<code> GSSName </code>对象并在ACL中对每个名称调用equals方法的开销可能过高。<p>
     * 
     *  可以通过使用字节数组工厂方法{@link GSSManager#createName(byte [],Oid)GSSManager.createName}并指定NT_EXPORT_NAME作为名称类型
     * 对象标识符来重新导入导出的名称。
     * 生成的<code> GSSName </code>名称也将是MN。<p>。
     * 
     * 
     * @return a byte[] containing the exported name. RFC 2743 defines the
     * "Mechanism-Independent Exported Name Object Format" for these bytes.
     *
     * @throws GSSException containing the following
     * major error codes:
     *         {@link GSSException#BAD_NAME GSSException.BAD_NAME},
     *         {@link GSSException#BAD_NAMETYPE GSSException.BAD_NAMETYPE},
     *         {@link GSSException#FAILURE GSSException.FAILURE}
     */
    public byte[] export() throws GSSException;

    /**
     * Returns a textual representation of the <code>GSSName</code> object.  To retrieve
     * the printed name format, which determines the syntax of the returned
     * string, use the {@link #getStringNameType() getStringNameType}
     * method.
     *
     * <p>
     *  返回<code> GSSName </code>对象的文本表示。
     * 要检索打印的名称格式(确定返回的字符串的语法),请使用{@link #getStringNameType()getStringNameType}方法。
     * 
     * 
     * @return a String representing this name in printable form.
     */
    public String toString();

    /**
     * Returns the name type of the printable
     * representation of this name that can be obtained from the <code>
     * toString</code> method.
     *
     * <p>
     * 返回可从<code> toString </code>方法获取的此名称的可打印表示的名称类型。
     * 
     * 
     * @return an Oid representing the namespace of the name returned
     * from the toString method.
     *
     * @throws GSSException containing the following
     * major error codes:
     *         {@link GSSException#FAILURE GSSException.FAILURE}
     */
    public Oid getStringNameType() throws GSSException;

    /**
     * Tests if this name object represents an anonymous entity.
     *
     * <p>
     *  测试此名称对象是否表示匿名实体。
     * 
     * 
     * @return true if this is an anonymous name, false otherwise.
     */
    public boolean isAnonymous();

    /**
     * Tests if this name object represents a Mechanism Name (MN). An MN is
     * a GSSName the contains exactly one mechanism's primitive name
     * element.
     *
     * <p>
     *  测试此名称对象是否表示机制名称(MN)。 MN是一个GSSName,它只包含一个机制的原始名称元素。
     * 
     * @return true if this is an MN, false otherwise.
     */
    public boolean isMN();

}
