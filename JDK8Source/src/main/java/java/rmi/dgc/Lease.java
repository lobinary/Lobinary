/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 1998, Oracle and/or its affiliates. All rights reserved.
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

/**
 * A lease contains a unique VM identifier and a lease duration. A
 * Lease object is used to request and grant leases to remote object
 * references.
 * <p>
 *  租赁包含唯一的VM标识符和租用持续时间。 Lease对象用于请求和授予对远程对象引用的租用。
 * 
 */
public final class Lease implements java.io.Serializable {

    /**
    /* <p>
    /* 
     * @serial Virtual Machine ID with which this Lease is associated.
     * @see #getVMID
     */
    private VMID vmid;

    /**
    /* <p>
    /* 
     * @serial Duration of this lease.
     * @see #getValue
     */
    private long value;
    /** indicate compatibility with JDK 1.1.x version of class */
    private static final long serialVersionUID = -5713411624328831948L;

    /**
     * Constructs a lease with a specific VMID and lease duration. The
     * vmid may be null.
     * <p>
     *  构造具有特定VMID和租期的租期。 vmid可以为null。
     * 
     * 
     * @param id VMID associated with this lease
     * @param duration lease duration
     */
    public Lease(VMID id, long duration)
    {
        vmid = id;
        value = duration;
    }

    /**
     * Returns the client VMID associated with the lease.
     * <p>
     *  返回与租赁关联的客户端VMID。
     * 
     * 
     * @return client VMID
     */
    public VMID getVMID()
    {
        return vmid;
    }

    /**
     * Returns the lease duration.
     * <p>
     *  返回租用持续时间。
     * 
     * @return lease duration
     */
    public long getValue()
    {
        return value;
    }
}
