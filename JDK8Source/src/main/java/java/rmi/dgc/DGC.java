/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 1999, Oracle and/or its affiliates. All rights reserved.
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

import java.rmi.*;
import java.rmi.server.ObjID;

/**
 * The DGC abstraction is used for the server side of the distributed
 * garbage collection algorithm. This interface contains the two
 * methods: dirty and clean. A dirty call is made when a remote
 * reference is unmarshaled in a client (the client is indicated by
 * its VMID). A corresponding clean call is made when no more
 * references to the remote reference exist in the client. A failed
 * dirty call must schedule a strong clean call so that the call's
 * sequence number can be retained in order to detect future calls
 * received out of order by the distributed garbage collector.
 *
 * A reference to a remote object is leased for a period of time by
 * the client holding the reference. The lease period starts when the
 * dirty call is received. It is the client's responsibility to renew
 * the leases, by making additional dirty calls, on the remote
 * references it holds before such leases expire. If the client does
 * not renew the lease before it expires, the distributed garbage
 * collector assumes that the remote object is no longer referenced by
 * that client.
 *
 * <p>
 *  DGC抽象用于分布式垃圾收集算法的服务器端。此接口包含两种方法：dirty和clean。当在客户端中对远程引用进行解组时(客户端由其VMID指示),进行脏调用。
 * 当在客户端中不存在对远程引用的更多引用时,进行相应的清除调用。失败的脏调用必须调度强干净调用,以便可以保留调用的序列号,以便检测由分布式垃圾收集器无序接收的未来调用。
 * 
 *  对远程对象的引用由持有引用的客户端租用一段时间。租期在收到脏呼叫时开始。客户有责任通过在这些租约过期之前对其进行附加的脏调用来更新租约。
 * 如果客户端在到期之前未更新租约,则分布式垃圾收集器假定远程对象不再由该客户端引用。
 * 
 * 
 * @author Ann Wollrath
 */
public interface DGC extends Remote {

    /**
     * The dirty call requests leases for the remote object references
     * associated with the object identifiers contained in the array
     * 'ids'. The 'lease' contains a client's unique VM identifier (VMID)
     * and a requested lease period. For each remote object exported
     * in the local VM, the garbage collector maintains a reference
     * list-a list of clients that hold references to it. If the lease
     * is granted, the garbage collector adds the client's VMID to the
     * reference list for each remote object indicated in 'ids'. The
     * 'sequenceNum' parameter is a sequence number that is used to
     * detect and discard late calls to the garbage collector. The
     * sequence number should always increase for each subsequent call
     * to the garbage collector.
     *
     * Some clients are unable to generate a VMID, since a VMID is a
     * universally unique identifier that contains a host address
     * which some clients are unable to obtain due to security
     * restrictions. In this case, a client can use a VMID of null,
     * and the distributed garbage collector will assign a VMID for
     * the client.
     *
     * The dirty call returns a Lease object that contains the VMID
     * used and the lease period granted for the remote references (a
     * server may decide to grant a smaller lease period than the
     * client requests). A client must use the VMID the garbage
     * collector uses in order to make corresponding clean calls when
     * the client drops remote object references.
     *
     * A client VM need only make one initial dirty call for each
     * remote reference referenced in the VM (even if it has multiple
     * references to the same remote object). The client must also
     * make a dirty call to renew leases on remote references before
     * such leases expire. When the client no longer has any
     * references to a specific remote object, it must schedule a
     * clean call for the object ID associated with the reference.
     *
     * <p>
     * 对于与包含在数组"ids"中的对象标识符相关联的远程对象引用,脏调用请求租用。 "租赁"包含客户端的唯一VM标识符(VMID)和请求的租期。
     * 对于在本地VM中导出的每个远程对象,垃圾收集器维护一个引用列表 - 保存对它的引用的客户端的列表。如果授予租约,垃圾回收器将客户端的VMID添加到"ids"中指示的每个远程对象的引用列表。
     *  'sequenceNum'参数是用于检测和丢弃对垃圾收集器的后期调用的序列号。对于每次对垃圾收集器的后续调用,序列号应该总是增加。
     * 
     *  某些客户端无法生成VMID,因为VMID是包含某些客户端由于安全限制无法获取的主机地址的通用唯一标识符。在这种情况下,客户端可以使用VMID为null,分布式垃圾回收器将为客户端分配VMID。
     * 
     *  脏调用返回一个Lease对象,其中包含所使用的VMID和为远程引用授予的租期(服务器可能决定授予比客户端请求更小的租期)。
     * 客户端必须使用垃圾收集器使用的VMID,以便在客户端删除远程对象引用时进行相应的清除调用。
     * 
     * @param ids IDs of objects to mark as referenced by calling client
     * @param sequenceNum sequence number
     * @param lease requested lease
     * @return granted lease
     * @throws RemoteException if dirty call fails
     */
    Lease dirty(ObjID[] ids, long sequenceNum, Lease lease)
        throws RemoteException;

    /**
     * The clean call removes the 'vmid' from the reference list of
     * each remote object indicated in 'id's.  The sequence number is
     * used to detect late clean calls.  If the argument 'strong' is
     * true, then the clean call is a result of a failed dirty call,
     * thus the sequence number for the client 'vmid' needs to be
     * remembered.
     *
     * <p>
     * 
     * 客户端VM只需对VM中引用的每个远程引用进行一次初始脏调用(即使它具有对同一远程对象的多个引用)。客户端还必须在此类租约过期之前进行脏呼叫以更新远程引用上的租约。
     * 当客户端不再具有对特定远程对象的任何引用时,它必须调度与引用相关联的对象ID的干净调用。
     * 
     * 
     * @param ids IDs of objects to mark as unreferenced by calling client
     * @param sequenceNum sequence number
     * @param vmid client VMID
     * @param strong make 'strong' clean call
     * @throws RemoteException if clean call fails
     */
    void clean(ObjID[] ids, long sequenceNum, VMID vmid, boolean strong)
        throws RemoteException;
}
