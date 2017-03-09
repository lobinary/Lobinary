/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2011, Oracle and/or its affiliates. All rights reserved.
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

import java.io.InputStream;
import java.io.IOException;
import sun.security.util.DerValue;
import sun.security.util.DerOutputStream;
import sun.security.util.ObjectIdentifier;

/**
 * This class represents Universal Object Identifiers (Oids) and their
 * associated operations.<p>
 *
 * Oids are hierarchically globally-interpretable identifiers used
 * within the GSS-API framework to identify mechanisms and name formats.<p>
 *
 * The structure and encoding of Oids is defined in ISOIEC-8824 and
 * ISOIEC-8825.  For example the Oid representation of Kerberos V5
 * mechanism is "1.2.840.113554.1.2.2"<p>
 *
 * The GSSName name class contains public static Oid objects
 * representing the standard name types defined in GSS-API.
 *
 * <p>
 *  此类表示通用对象标识符(Oids)及其关联的操作。<p>
 * 
 *  Oids是在GSS-API框架内用于识别机制和名称格式的层次化全局可解释标识符。<p>
 * 
 *  Oid的结构和编码在ISOIEC-8824和ISOIEC-8825中定义。例如,Kerberos V5机制的Oid表示是"1.2.840.113554.1.2.2"<p>
 * 
 *  GSSName名称类包含表示GSS-API中定义的标准名称类型的公共静态Oid对象。
 * 
 * 
 * @author Mayank Upadhyay
 * @since 1.4
 */
public class Oid {

    private ObjectIdentifier oid;
    private byte[] derEncoding;

    /**
     * Constructs an Oid object from a string representation of its
     * integer components.
     *
     * <p>
     *  从其整数组件的字符串表示构造Oid对象。
     * 
     * 
     * @param strOid the dot separated string representation of the oid.
     * For instance, "1.2.840.113554.1.2.2".
     * @exception GSSException may be thrown when the string is incorrectly
     *     formatted
     */
    public Oid(String strOid) throws GSSException {

        try {
            oid = new ObjectIdentifier(strOid);
            derEncoding = null;
        } catch (Exception e) {
            throw new GSSException(GSSException.FAILURE,
                          "Improperly formatted Object Identifier String - "
                          + strOid);
        }
    }

    /**
     * Creates an Oid object from its ASN.1 DER encoding.  This refers to
     * the full encoding including tag and length.  The structure and
     * encoding of Oids is defined in ISOIEC-8824 and ISOIEC-8825.  This
     * method is identical in functionality to its byte array counterpart.
     *
     * <p>
     *  从其ASN.1 DER编码创建Oid对象。这是指包括标签和长度的完整编码。 Oid的结构和编码在ISOIEC-8824和ISOIEC-8825中定义。此方法在功能上与其字节数组对应相同。
     * 
     * 
     * @param derOid stream containing the DER encoded oid
     * @exception GSSException may be thrown when the DER encoding does not
     *  follow the prescribed format.
     */
    public Oid(InputStream derOid) throws GSSException {
        try {
            DerValue derVal = new DerValue(derOid);
            derEncoding = derVal.toByteArray();
            oid = derVal.getOID();
        } catch (IOException e) {
            throw new GSSException(GSSException.FAILURE,
                          "Improperly formatted ASN.1 DER encoding for Oid");
        }
    }


    /**
     * Creates an Oid object from its ASN.1 DER encoding.  This refers to
     * the full encoding including tag and length.  The structure and
     * encoding of Oids is defined in ISOIEC-8824 and ISOIEC-8825.  This
     * method is identical in functionality to its InputStream conterpart.
     *
     * <p>
     *  从其ASN.1 DER编码创建Oid对象。这是指包括标签和长度的完整编码。 Oid的结构和编码在ISOIEC-8824和ISOIEC-8825中定义。
     * 此方法在功能上与其InputStream conterpart相同。
     * 
     * 
     * @param data byte array containing the DER encoded oid
     * @exception GSSException may be thrown when the DER encoding does not
     *     follow the prescribed format.
     */
    public Oid(byte [] data) throws GSSException {
        try {
            DerValue derVal = new DerValue(data);
            derEncoding = derVal.toByteArray();
            oid = derVal.getOID();
        } catch (IOException e) {
            throw new GSSException(GSSException.FAILURE,
                          "Improperly formatted ASN.1 DER encoding for Oid");
        }
    }

    /**
     * Only for calling by initializators used with declarations.
     *
     * <p>
     *  只有通过与声明一起使用的初始化器调用。
     * 
     * 
     * @param strOid
     */
    static Oid getInstance(String strOid) {
        Oid retVal = null;
        try {
            retVal =  new Oid(strOid);
        } catch (GSSException e) {
            // squelch it!
        }
        return retVal;
    }

    /**
     * Returns a string representation of the oid's integer components
     * in dot separated notation.
     *
     * <p>
     *  以点分隔符号形式返回oid的整数组件的字符串表示形式。
     * 
     * 
     * @return string representation in the following format: "1.2.3.4.5"
     */
    public String toString() {
        return oid.toString();
    }

    /**
     * Tests if two Oid objects represent the same Object identifier
     * value.
     *
     * <p>
     *  测试两个Oid对象是否表示相同的对象标识符值。
     * 
     * 
     * @return <code>true</code> if the two Oid objects represent the same
     * value, <code>false</code> otherwise.
     * @param other the Oid object that has to be compared to this one
     */
    public boolean equals(Object other) {

        //check if both reference the same object
        if (this == other)
            return (true);

        if (other instanceof Oid)
            return this.oid.equals((Object)((Oid) other).oid);
        else if (other instanceof ObjectIdentifier)
            return this.oid.equals(other);
        else
            return false;
    }


    /**
     * Returns the full ASN.1 DER encoding for this oid object, which
     * includes the tag and length.
     *
     * <p>
     * 返回此oid对象的完整ASN.1 DER编码,其中包括标记和长度。
     * 
     * 
     * @return byte array containing the DER encoding of this oid object.
     * @exception GSSException may be thrown when the oid can't be encoded
     */
    public byte[] getDER() throws GSSException {

        if (derEncoding == null) {
            DerOutputStream dout = new DerOutputStream();
            try {
                dout.putOID(oid);
            } catch (IOException e) {
                throw new GSSException(GSSException.FAILURE, e.getMessage());
            }
            derEncoding = dout.toByteArray();
        }

        return derEncoding.clone();
    }

    /**
     * A utility method to test if this Oid value is contained within the
     * supplied Oid array.
     *
     * <p>
     *  用于测试此Oid值是否包含在提供的Oid数组中的实用程序方法。
     * 
     * 
     * @param oids the array of Oid's to search
     * @return true if the array contains this Oid value, false otherwise
     */
    public boolean containedIn(Oid[] oids) {

        for (int i = 0; i < oids.length; i++) {
            if (oids[i].equals(this))
                return (true);
        }

        return (false);
    }


    /**
     * Returns a hashcode value for this Oid.
     *
     * <p>
     *  返回此Oid的哈希码值。
     * 
     * @return a hashCode value
     */
    public int hashCode() {
        return oid.hashCode();
    }
}
