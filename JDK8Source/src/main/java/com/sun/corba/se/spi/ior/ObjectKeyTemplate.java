/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2002, 2003, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.corba.se.spi.ior;

import org.omg.CORBA_2_3.portable.OutputStream ;

import com.sun.corba.se.spi.orb.ORBVersion ;
import com.sun.corba.se.spi.orb.ORB ;

import com.sun.corba.se.spi.protocol.CorbaServerRequestDispatcher ;

/** An ObjectKeyTemplate represents the part of an Object Key
 * that corresponds to the object adapter used to create an
 * object reference.  The template is shared between many
 * object references.
 * <p>
 *  对应于用于创建对象引用的对象适配器。模板在许多对象引用之间共享。
 * 
 */
public interface ObjectKeyTemplate extends Writeable
{
    public ORBVersion getORBVersion() ;

    /** An ID used to determine how to perform operations on this
     * ObjectKeyTemplate.  This id determines how to process requests
     * on this object reference, and what object adapter type to use.
     * <p>
     *  ObjectKeyTemplate。此ID确定如何处理对此对象引用的请求以及要使用的对象适配器类型。
     * 
     */
    public int getSubcontractId();

    /** Return the server ID for this template.
    * For CORBA 3.0, this should be a String, but it is currently
    * an int in the object key template.
    * <p>
    *  对于CORBA 3.0,这应该是一个字符串,但它目前是一个int在对象键模板。
    * 
    */
    public int getServerId() ;

    /** Return the ORB ID for this template.
    /* <p>
    */
    public String getORBId() ;

    /** Return the object adapter ID for this template.
    /* <p>
    */
    public ObjectAdapterId getObjectAdapterId() ;

    /** Compute an adapter ID for this template than includes
    * all of the template information.
    * This value is cached to avoid the expense of recomputing
    * it.
    * <p>
    *  所有的模板信息。此值将被缓存,以避免重新计算它的开销。
    */
    public byte[] getAdapterId() ;

    public void write(ObjectId objectId, OutputStream os);

    public CorbaServerRequestDispatcher getServerRequestDispatcher( ORB orb, ObjectId id ) ;
}
