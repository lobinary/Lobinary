/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2001, 2003, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.corba.se.spi.extension ;

import org.omg.CORBA.Policy ;
import org.omg.CORBA.LocalObject ;
import com.sun.corba.se.impl.orbutil.ORBConstants ;

/** Policy used to implement servant caching optimization in the POA.
* Creating a POA with an instance pol of this policy where
* pol.getType() &gt; NO_SERVANT_CACHING will cause the servant to be
* looked up in the POA and cached in the LocalClientRequestDispatcher when
* the ClientRequestDispatcher is colocated with the implementation of the
* objref.  This greatly speeds up invocations at the cost of violating the
* POA semantics.  In particular, every request to a particular objref
* must be handled by the same servant.  Note that this is typically the
* case for EJB implementations.
* <p>
* If servant caching is used, there are two different additional
* features of the POA that are expensive:
* <ol>
* <li>POA current semantics
* <li>Proper handling of POA destroy.
* <ol>
* POA current semantics requires maintaining a ThreadLocal stack of
* invocation information that is always available for POACurrent operations.
* Maintaining this stack is expensive on the timescale of optimized co-located
* calls, so the option is provided to turn it off.  Similarly, causing
* POA.destroy() calls to wait for all active calls in the POA to complete
* requires careful tracking of the entry and exit of invocations in the POA.
* Again, tracking this is somewhat expensive.
* <p>
*  使用此策略的实例pol创建POA,其中pol.getType()&gt;当ClientRequestDispatcher与objref的实现共同定位时,NO_SERVANT_CACHING将导致在PO
* A中查找servant并缓存在LocalClientRequestDispatcher中。
* 这大大加快了调用的代价,违反了POA语义。特别地,对特定对象的每个请求必须由相同的服务方处理。注意,这通常是EJB实现的情况。
* <p>
*  如果使用服务方高速缓存,则POA的两个不同的附加特征是昂贵的：
* <ol>
*  <li> POA当前语义<li>正确处理POA销毁。
* <ol>
*  POA当前语义需要维护一个ThreadLocal栈的调用信息,它总是可用于POACurrent操作。在优化的共存呼叫的时间尺度上维护该堆栈是昂贵的,因此提供该选项以将其关闭。
* 类似地,使POA.destroy()调用等待POA中的所有活动调用完成需要仔细跟踪POA中调用的进入和退出。再次,跟踪这是有点昂贵。
* 
*/
public class ServantCachingPolicy extends LocalObject implements Policy
{
    /** Do not cache servants in the ClientRequestDispatcher.  This will
     * always support the full POA semantics, including changing the
     * servant that handles requests on a particular objref.
     * <p>
     *  总是支持完整的POA语义,包括更改处理特定objref请求的servant。
     * 
     */
    public static final int NO_SERVANT_CACHING = 0 ;

    /** Perform servant caching, preserving POA current and POA destroy semantics.
    * We will use this as the new default, as the app server is making heavier use
    * now of POA facilities.
    * <p>
    * 我们将使用它作为新的默认值,因为应用服务器现在对POA设施使用更重。
    * 
    */
    public static final int FULL_SEMANTICS = 1 ;

    /** Perform servant caching, preservent only POA current semantics.
    * At least this level is required in order to support selection of ObjectCopiers
    * for co-located RMI-IIOP calls, as the current copier is stored in
    * OAInvocationInfo, which must be present on the stack inside the call.
    * <p>
    *  至少需要这个级别以便支持选择用于共同定位的RMI-IIOP调用的ObjectCopier,因为当前复印机存储在OAInvocationInfo中,该OAInvocationInfo必须存在于调用内的栈
    * 上。
    * 
    */
    public static final int INFO_ONLY_SEMANTICS =  2 ;

    /** Perform servant caching, not preserving POA current or POA destroy semantics.
    /* <p>
    */
    public static final int MINIMAL_SEMANTICS = 3 ;

    private static ServantCachingPolicy policy = null ;
    private static ServantCachingPolicy infoOnlyPolicy = null ;
    private static ServantCachingPolicy minimalPolicy = null ;

    private int type ;

    public String typeToName()
    {
        switch (type) {
            case FULL_SEMANTICS:
                return "FULL" ;
            case INFO_ONLY_SEMANTICS:
                return "INFO_ONLY" ;
            case MINIMAL_SEMANTICS:
                return "MINIMAL" ;
            default:
                return "UNKNOWN(" + type + ")" ;
        }
    }

    public String toString()
    {
        return "ServantCachingPolicy[" + typeToName() + "]" ;
    }

    private ServantCachingPolicy( int type )
    {
        this.type = type ;
    }

    public int getType()
    {
        return type ;
    }

    /** Return the default servant caching policy.
    /* <p>
    */
    public synchronized static ServantCachingPolicy getPolicy()
    {
        return getFullPolicy() ;
    }

    public synchronized static ServantCachingPolicy getFullPolicy()
    {
        if (policy == null)
            policy = new ServantCachingPolicy( FULL_SEMANTICS ) ;

        return policy ;
    }

    public synchronized static ServantCachingPolicy getInfoOnlyPolicy()
    {
        if (infoOnlyPolicy == null)
            infoOnlyPolicy = new ServantCachingPolicy( INFO_ONLY_SEMANTICS ) ;

        return infoOnlyPolicy ;
    }

    public synchronized static ServantCachingPolicy getMinimalPolicy()
    {
        if (minimalPolicy == null)
            minimalPolicy = new ServantCachingPolicy( MINIMAL_SEMANTICS ) ;

        return minimalPolicy ;
    }

    public int policy_type ()
    {
        return ORBConstants.SERVANT_CACHING_POLICY ;
    }

    public org.omg.CORBA.Policy copy ()
    {
        return this ;
    }

    public void destroy ()
    {
        // NO-OP
    }
}
