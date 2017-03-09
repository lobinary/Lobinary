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

package javax.security.auth.kerberos;

import java.io.*;
import sun.security.krb5.KrbException;
import sun.security.krb5.PrincipalName;
import sun.security.krb5.Realm;
import sun.security.util.*;

/**
 * This class encapsulates a Kerberos principal.
 *
 * <p>
 *  此类封装了Kerberos主体。
 * 
 * 
 * @author Mayank Upadhyay
 * @since 1.4
 */

public final class KerberosPrincipal
    implements java.security.Principal, java.io.Serializable {

    private static final long serialVersionUID = -7374788026156829911L;

    //name types

    /**
     * unknown name type.
     * <p>
     *  未知名称类型。
     * 
     */

    public static final int KRB_NT_UNKNOWN =   0;

    /**
     * user principal name type.
     * <p>
     *  用户主体名称类型。
     * 
     */

    public static final int KRB_NT_PRINCIPAL = 1;

    /**
     * service and other unique instance (krbtgt) name type.
     * <p>
     *  服务和其他唯一实例(krbtgt)名称类型。
     * 
     */
    public static final int KRB_NT_SRV_INST =  2;

    /**
     * service with host name as instance (telnet, rcommands) name type.
     * <p>
     *  服务以主机名作为实例(telnet,rcommands)名称类型。
     * 
     */

    public static final int KRB_NT_SRV_HST =   3;

    /**
     * service with host as remaining components name type.
     * <p>
     *  服务与主机作为其余组件名称类型。
     * 
     */

    public static final int KRB_NT_SRV_XHST =  4;

    /**
     * unique ID name type.
     * <p>
     *  唯一ID名称类型。
     * 
     */

    public static final int KRB_NT_UID = 5;

    private transient String fullName;

    private transient String realm;

    private transient int nameType;


    /**
     * Constructs a KerberosPrincipal from the provided string input. The
     * name type for this  principal defaults to
     * {@link #KRB_NT_PRINCIPAL KRB_NT_PRINCIPAL}
     * This string is assumed to contain a name in the format
     * that is specified in Section 2.1.1. (Kerberos Principal Name Form) of
     * <a href=http://www.ietf.org/rfc/rfc1964.txt> RFC 1964 </a>
     * (for example, <i>duke@FOO.COM</i>, where <i>duke</i>
     * represents a principal, and <i>FOO.COM</i> represents a realm).
     *
     * <p>If the input name does not contain a realm, the default realm
     * is used. The default realm can be specified either in a Kerberos
     * configuration file or via the java.security.krb5.realm
     * system property. For more information,
     * <a href="../../../../../technotes/guides/security/jgss/tutorials/index.html">
     * Kerberos Requirements </a>
     *
     * <p>
     *  从提供的字符串输入构造KerberosPrincipal。
     * 此主体的名称类型默认为{@link #KRB_NT_PRINCIPAL KRB_NT_PRINCIPAL}此字符串假定包含第2.1.1节中指定的格式的名称。
     *  (Kerberos主体名称表格)<a href=http://www.ietf.org/rfc/rfc1964.txt> RFC 1964 </a>(例如,<i> duke@FOO.COM </i>,
     * 其中<i> d ike </i>表示主体,并且</u> FOO.COM </i>表示领域。
     * 此主体的名称类型默认为{@link #KRB_NT_PRINCIPAL KRB_NT_PRINCIPAL}此字符串假定包含第2.1.1节中指定的格式的名称。
     * 
     *  <p>如果输入名称不包含领域,则使用默认领域。可以在Kerberos配置文件中或通过java.security.krb5.realm系统属性指定默认领域。了解更多信息,
     * <a href="../../../../../technotes/guides/security/jgss/tutorials/index.html">
     *  Kerberos要求</a>
     * 
     * 
     * @param name the principal name
     * @throws IllegalArgumentException if name is improperly
     * formatted, if name is null, or if name does not contain
     * the realm to use and the default realm is not specified
     * in either a Kerberos configuration file or via the
     * java.security.krb5.realm system property.
     */
    public KerberosPrincipal(String name) {

        PrincipalName krb5Principal = null;

        try {
            // Appends the default realm if it is missing
            krb5Principal = new PrincipalName(name, KRB_NT_PRINCIPAL);
        } catch (KrbException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        nameType = KRB_NT_PRINCIPAL;  // default name type
        fullName = krb5Principal.toString();
        realm = krb5Principal.getRealmString();
    }

    /**
     * Constructs a KerberosPrincipal from the provided string and
     * name type input.  The string is assumed to contain a name in the
     * format that is specified in Section 2.1 (Mandatory Name Forms) of
     * <a href=http://www.ietf.org/rfc/rfc1964.txt>RFC 1964</a>.
     * Valid name types are specified in Section 6.2 (Principal Names) of
     * <a href=http://www.ietf.org/rfc/rfc4120.txt>RFC 4120</a>.
     * The input name must be consistent with the provided name type.
     * (for example, <i>duke@FOO.COM</i>, is a valid input string for the
     * name type, KRB_NT_PRINCIPAL where <i>duke</i>
     * represents a principal, and <i>FOO.COM</i> represents a realm).

     * <p> If the input name does not contain a realm, the default realm
     * is used. The default realm can be specified either in a Kerberos
     * configuration file or via the java.security.krb5.realm
     * system property. For more information, see
     * <a href="../../../../../technotes/guides/security/jgss/tutorials/index.html">
     * Kerberos Requirements</a>.
     *
     * <p>
     * 从提供的字符串和名称类型输入构造KerberosPrincipal。
     * 假设该字符串包含的格式为<a href=http://www.ietf.org/rfc/rfc1964.txt> RFC 1964 </a>的第2.1节(强制名称表格)中指定的格式。
     * 有效的名称类型在<a href=http://www.ietf.org/rfc/rfc4120.txt> RFC 4120 </a>的第6.2节(主体名称)中指定。输入名称必须与提供的名称类型一致。
     *  (例如<duke@FOO.COM </i>)是名称类型KRB_NT_PRINCIPAL的有效输入字符串,其中<i> duke </i>表示主体,<FOO.COM </i> i>表示领域)。
     * 
     *  <p>如果输入名称不包含领域,则使用默认领域。可以在Kerberos配置文件中或通过java.security.krb5.realm系统属性指定默认领域。有关详细信息,请参阅
     * <a href="../../../../../technotes/guides/security/jgss/tutorials/index.html">
     *  Kerberos要求</a>。
     * 
     * 
     * @param name the principal name
     * @param nameType the name type of the principal
     * @throws IllegalArgumentException if name is improperly
     * formatted, if name is null, if the nameType is not supported,
     * or if name does not contain the realm to use and the default
     * realm is not specified in either a Kerberos configuration
     * file or via the java.security.krb5.realm system property.
     */

    public KerberosPrincipal(String name, int nameType) {

        PrincipalName krb5Principal = null;

        try {
            // Appends the default realm if it is missing
            krb5Principal  = new PrincipalName(name,nameType);
        } catch (KrbException e) {
            throw new IllegalArgumentException(e.getMessage());
        }

        this.nameType = nameType;
        fullName = krb5Principal.toString();
        realm = krb5Principal.getRealmString();
    }
    /**
     * Returns the realm component of this Kerberos principal.
     *
     * <p>
     *  返回此Kerberos主体的领域组件。
     * 
     * 
     * @return the realm component of this Kerberos principal.
     */
    public String getRealm() {
        return realm;
    }

    /**
     * Returns a hashcode for this principal. The hash code is defined to
     * be the result of the following  calculation:
     * <pre>{@code
     *  hashCode = getName().hashCode();
     * }</pre>
     *
     * <p>
     *  返回此主体的哈希码。哈希码定义为以下计算的结果：<pre> {@ code hashCode = getName()。hashCode(); } </pre>
     * 
     * 
     * @return a hashCode() for the {@code KerberosPrincipal}
     */
    public int hashCode() {
        return getName().hashCode();
    }

    /**
     * Compares the specified Object with this Principal for equality.
     * Returns true if the given object is also a
     * {@code KerberosPrincipal} and the two
     * {@code KerberosPrincipal} instances are equivalent.
     * More formally two {@code KerberosPrincipal} instances are equal
     * if the values returned by {@code getName()} are equal.
     *
     * <p>
     *  将指定的对象与此Principal进行比较以确保相等。
     * 如果给定对象也是{@code KerberosPrincipal},而两个{@code KerberosPrincipal}实例是等效的,则返回true。
     * 更正式地,如果{@code getName()}返回的值相等,则两个{@code KerberosPrincipal}实例是相等的。
     * 
     * 
     * @param other the Object to compare to
     * @return true if the Object passed in represents the same principal
     * as this one, false otherwise.
     */
    public boolean equals(Object other) {

        if (other == this)
            return true;

        if (! (other instanceof KerberosPrincipal)) {
            return false;
        }
        String myFullName = getName();
        String otherFullName = ((KerberosPrincipal) other).getName();
        return myFullName.equals(otherFullName);
    }

    /**
     * Save the KerberosPrincipal object to a stream
     *
     * <p>
     *  将KerberosPrincipal对象保存到流
     * 
     * 
     * @serialData this {@code KerberosPrincipal} is serialized
     *          by writing out the PrincipalName and the
     *          realm in their DER-encoded form as specified in Section 5.2.2 of
     *          <a href=http://www.ietf.org/rfc/rfc4120.txt> RFC4120</a>.
     */
    private void writeObject(ObjectOutputStream oos)
            throws IOException {

        PrincipalName krb5Principal;
        try {
            krb5Principal  = new PrincipalName(fullName, nameType);
            oos.writeObject(krb5Principal.asn1Encode());
            oos.writeObject(krb5Principal.getRealm().asn1Encode());
        } catch (Exception e) {
            throw new IOException(e);
        }
    }

    /**
     * Reads this object from a stream (i.e., deserializes it)
     * <p>
     * 从流中读取此对象(即,对其进行反序列化)
     * 
     */
    private void readObject(ObjectInputStream ois)
            throws IOException, ClassNotFoundException {
        byte[] asn1EncPrincipal = (byte [])ois.readObject();
        byte[] encRealm = (byte [])ois.readObject();
        try {
           Realm realmObject = new Realm(new DerValue(encRealm));
           PrincipalName krb5Principal = new PrincipalName(
                   new DerValue(asn1EncPrincipal), realmObject);
           realm = realmObject.toString();
           fullName = krb5Principal.toString();
           nameType = krb5Principal.getNameType();
        } catch (Exception e) {
            throw new IOException(e);
        }
    }

    /**
     * The returned string corresponds to the single-string
     * representation of a Kerberos Principal name as specified in
     * Section 2.1 of <a href=http://www.ietf.org/rfc/rfc1964.txt>RFC 1964</a>.
     *
     * <p>
     *  返回的字符串对应于<a href=http://www.ietf.org/rfc/rfc1964.txt> RFC 1964 </a>第2.1节中指定的Kerberos主体名称的单字符串表示形式。
     * 
     * 
     * @return the principal name.
     */
    public String getName() {
        return fullName;
    }

    /**
     * Returns the name type of the KerberosPrincipal. Valid name types
     * are specified in Section 6.2 of
     * <a href=http://www.ietf.org/rfc/rfc4120.txt> RFC4120</a>.
     *
     * <p>
     *  返回KerberosPrincipal的名称类型。
     * 有效的名称类型在<a href=http://www.ietf.org/rfc/rfc4120.txt> RFC4120 </a>的第6.2节中指定。
     * 
     * @return the name type.
     */
    public int getNameType() {
        return nameType;
    }

    // Inherits javadocs from Object
    public String toString() {
        return getName();
    }
}
