/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 2013, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.corba.se.spi.servicecontext;

import org.omg.CORBA.SystemException;
import org.omg.CORBA.INTERNAL;
import org.omg.CORBA_2_3.portable.InputStream ;
import org.omg.CORBA_2_3.portable.OutputStream ;
import com.sun.corba.se.spi.ior.iiop.GIOPVersion;
import com.sun.corba.se.spi.orb.ORB ;
import com.sun.corba.se.impl.encoding.CDRInputStream ;
import com.sun.corba.se.impl.encoding.EncapsInputStream ;
import com.sun.corba.se.impl.encoding.EncapsOutputStream ;
import com.sun.corba.se.impl.orbutil.ORBUtility ;

/** Base class for all ServiceContext classes.
* There is a derived ServiceContext class for each service context that
* the ORB supports.  Each subclass encapsulates the representation of
* the service context and provides any needed methods for manipulating
* the service context.  Each subclass must provide the following
* members:
* <p>
* <ul>
* </li>a public static final int SERVICE_CONTEXT_ID that gives the OMG
* (or other) defined id for the service context.  This is needed for the
* registration mechanism defined in ServiceContexts. OMG defined
* service context ids are taken from section 13.6.7 of ptc/98-12-04.</li>
* <li>a public constructor that takes an InputStream as its argument.</li>
* <li>Appropriate definitions of getId() and writeData().  getId() must
* return SERVICE_CONTEXT_ID.</li>
* </ul>
* <p>
* The subclass can be constructed either directly from the service context
* representation, or by reading the representation from an input stream.
* These cases are needed when the service context is created and written to
* the request or reply, and when the service context is read from the
* received request or reply.
* <p>
*  对于ORB支持的每个服务上下文,都有一个派生的ServiceContext类。每个子类封装服务上下文的表示,并提供操作服务上下文所需的任何方法。每个子类必须提供以下成员：
* <p>
* <ul>
*  </li> public static final int SERVICE_CONTEXT_ID,它为服务上下文提供OMG(或其他)定义的ID。
* 这是ServiceContexts中定义的注册机制所需要的。 OMG定义的服务上下文id来自ptc / 98-12-04的第13.6.7节。
* </li> <li>一个公共构造函数,以InputStream作为参数。</li> <li> getId ()和writeData()。 getId()必须返回SERVICE_CONTEXT_ID。
* </li>。
* </ul>
* <p>
*  子类可以直接从服务上下文表示构造,或者通过从输入流读取表示来构造。当创建服务上下文并将其写入请求或回复时,以及从接收到的请求或回复中读取服务上下文时,需要这些情况。
* 
*/
public abstract class ServiceContext {
    /** Simple default constructor used when subclass is constructed
     * from its representation.
     * <p>
     *  从它的表示。
     * 
     */
    protected ServiceContext() { }

    private void dprint( String msg )
    {
        ORBUtility.dprint( this, msg ) ;
    }

    /** Stream constructor used when subclass is constructed from an
     * InputStream.  This constructor must be called by super( stream )
     * in the subclass.  After this constructor completes, the service
     * context representation can be read from in.
     * Note that the service context id has been consumed from the input
     * stream before this object is constructed.
     * <p>
     *  InputStream。这个构造函数必须在子类中被super(stream)调用。在此构造函数完成后,可以从中读取服务上下文表示。请注意,在构建此对象之前,服务上下文标识已从输入流中消耗。
     * 
     */
    protected ServiceContext(InputStream s, GIOPVersion gv) throws SystemException
    {
        in = s;
    }

    /** Returns Service context id.  Must be overloaded in subclass.
    /* <p>
     */
    public abstract int getId() ;

    /** Write the service context to an output stream.  This method
     * must be used for writing the service context to a request or reply
     * header.
     * <p>
     * 必须用于将服务上下文写入请求或应答标头。
     * 
     */
    public void write(OutputStream s, GIOPVersion gv) throws SystemException
    {
        EncapsOutputStream os =
            sun.corba.OutputStreamFactory.newEncapsOutputStream((ORB)(s.orb()), gv);
        os.putEndian() ;
        writeData( os ) ;
        byte[] data = os.toByteArray() ;

        s.write_long(getId());
        s.write_long(data.length);
        s.write_octet_array(data, 0, data.length);
    }

    /** Writes the data used to represent the subclasses service context
     * into an encapsulation stream.  Must be overloaded in subclass.
     * <p>
     *  到封装流中。必须在子类中重载。
     * 
     */
    protected abstract void writeData( OutputStream os ) ;

    /** in is the stream containing the service context representation.
     * It is constructed by the stream constructor, and available for use
     * in the subclass stream constructor.
     * <p>
     *  它由流构造器构造,并可用于子类流构造器。
     */
    protected InputStream in = null ;

    public String toString()
    {
        return "ServiceContext[ id=" + getId() + " ]" ;
    }
}
