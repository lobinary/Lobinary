/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.rmi.dgc;

import java.rmi.server.UID;
import java.security.SecureRandom;

/**
 * A VMID is a identifier that is unique across all Java virtual
 * machines.  VMIDs are used by the distributed garbage collector
 * to identify client VMs.
 *
 * <p>
 *  VMID是跨所有Java虚拟机唯一的标识符。 VMID由分布式垃圾收集器用于标识客户端VM。
 * 
 * 
 * @author      Ann Wollrath
 * @author      Peter Jones
 */
public final class VMID implements java.io.Serializable {
    /** Array of bytes uniquely identifying this host */
    private static final byte[] randomBytes;

    /**
    /* <p>
    /* 
     * @serial array of bytes uniquely identifying host created on
     */
    private byte[] addr;

    /**
    /* <p>
    /* 
     * @serial unique identifier with respect to host created on
     */
    private UID uid;

    /** indicate compatibility with JDK 1.1.x version of class */
    private static final long serialVersionUID = -538642295484486218L;

    static {
        // Generate 8 bytes of random data.
        SecureRandom secureRandom = new SecureRandom();
        byte bytes[] = new byte[8];
        secureRandom.nextBytes(bytes);
        randomBytes = bytes;
    }

    /**
     * Create a new VMID.  Each new VMID returned from this constructor
     * is unique for all Java virtual machines under the following
     * conditions: a) the conditions for uniqueness for objects of
     * the class <code>java.rmi.server.UID</code> are satisfied, and b) an
     * address can be obtained for this host that is unique and constant
     * for the lifetime of this object.
     * <p>
     *  创建新的VMID。
     * 从这个构造函数返回的每个新VMID在以下条件下对于所有Java虚拟机是唯一的：a)满足类<java.rmi.server.UID </code>的对象的唯一性的条件,以及b)可以针对该主机获得对于该对象
     * 的生存期是唯一且恒定的地址。
     *  创建新的VMID。
     * 
     */
    public VMID() {
        addr = randomBytes;
        uid = new UID();
    }

    /**
     * Return true if an accurate address can be determined for this
     * host.  If false, reliable VMID cannot be generated from this host
     * <p>
     *  如果可以为此主机确定准确的地址,则返回true。如果为false,则无法从此主机生成可靠的VMID
     * 
     * 
     * @return true if host address can be determined, false otherwise
     * @deprecated
     */
    @Deprecated
    public static boolean isUnique() {
        return true;
    }

    /**
     * Compute hash code for this VMID.
     * <p>
     *  计算此VMID的哈希码。
     * 
     */
    public int hashCode() {
        return uid.hashCode();
    }

    /**
     * Compare this VMID to another, and return true if they are the
     * same identifier.
     * <p>
     *  将此VMID与另一个比较,如果它们是相同的标识符,则返回true。
     * 
     */
    public boolean equals(Object obj) {
        if (obj instanceof VMID) {
            VMID vmid = (VMID) obj;
            if (!uid.equals(vmid.uid))
                return false;
            if ((addr == null) ^ (vmid.addr == null))
                return false;
            if (addr != null) {
                if (addr.length != vmid.addr.length)
                    return false;
                for (int i = 0; i < addr.length; ++ i)
                    if (addr[i] != vmid.addr[i])
                        return false;
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * Return string representation of this VMID.
     * <p>
     *  返回此VMID的字符串表示形式。
     */
    public String toString() {
        StringBuffer result = new StringBuffer();
        if (addr != null)
            for (int i = 0; i < addr.length; ++ i) {
                int x = addr[i] & 0xFF;
                result.append((x < 0x10 ? "0" : "") +
                              Integer.toString(x, 16));
            }
        result.append(':');
        result.append(uid.toString());
        return result.toString();
    }
}
