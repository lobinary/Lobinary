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

package com.sun.corba.se.spi.oa ;

import org.omg.CORBA.Policy ;

import org.omg.PortableInterceptor.ObjectReferenceTemplate ;
import org.omg.PortableInterceptor.ObjectReferenceFactory ;

import com.sun.corba.se.spi.orb.ORB ;

import com.sun.corba.se.spi.oa.OADestroyed ;

import com.sun.corba.se.spi.ior.IORTemplate ;

// REVISIT: What should the order be?  enter/push...pop/exit?

/** ObjectAdapter represents the abstract model of an object
* adapter that was introduced by ORT.  This means that all
* object adapters must:
* <UL>
* <LI>Have an ORB</LI>
* <LI>Have a name</LI>
* <LI>Have an adapter manager (represented by an ID)</LI>
* <LI>Have an adapter template</LI>
* <LI>Support getting and setting their ObjectReferenceFactory</LI>
* <LI>Provide access to their current state</LI>
* <LI>Support adding components to their profiles expressed in the adapter template</LI>
* </UL>
* Other requirements:
* <UL>
* <LI>All object adapters must invoke ORB.AdapterCreated when they are created.
* </LI>
* <LI>All adapter managers must invoke ORB.AdapterManagerStateChanged when
* their state changes, mapping the internal state to an ORT state.</LI>
* <LI>AdapterStateChanged must be invoked (from somewhere) whenever
* an adapter state changes that is not due to an adapter manager state change.</LI>
* </UL>
* <P>
* Object adapters must also provide mechanisms for:
* <UL>
* <LI>Managing object reference lifecycle</LI>
* <LI>Controlling how servants are associated with object references</LI>
* <LI>Manage the state of the adapter, if the adapter desires to implement such mechanisms</LI>
* </UL>
* Such mechanisms are all object adapter specific, and so we do not attempt to
* create general APIs for these functions here.  The object adapter itself
* must provide these APIs directly to the user, and they do not affect the rest of the
* ORB.  This interface basically makes it possible to plug any object adapter into the
* ORB and have the OA work propertly with portable interceptors, and also have requests
* dispatched properly to the object adapter.
* <P>
* The basic function of an ObjectAdapter is to map object IDs to servants and to support
* the dispatch operation of the subcontract, which dispatches requests to servants.
* This is the purpose of the getInvocationServant method.  In addition, ObjectAdapters must be
* able to change state gracefully in the presence of executing methods.  This
* requires the use of the enter/exit methods.  Finally, ObjectAdapters often
* require access to information about requests.  This is accomodated through the
* OAInvocationInfo class and the thread local stack maintained by push/pop/peekInvocationInfo
* on the ORB.
* <P>
* To be useful, this dispatch cycle must be extremely efficient.  There are several
* scenarios that matter:
* <ol>
* <li>A remote invocation, where the dispatch is handled in the server subcontract.</li>
* <li>A local invocation, where the dispatch is handled in the client subcontract.</li>
* <li>A cached local invocation, where the servant is cached when the IOR is established
* for the client subcontract, and the dispatch is handled in the client subcontract
* to the cached subcontract.<li>
* </ol>
* <p>
* Each of these 3 cases is handled a bit differently.  On each request, assume as known
* ObjectId and ObjectAdapterId, which can be obtained from the object key.
* The ObjectAdaptorFactory is available in the subcontract registry, where it is
* registered under the subcontract ID.  The Subcontract ID is also available in the
* object key.
* <ol>
* <li>The remote pattern:
*   <ol>
*   <li>oa = oaf.find( oaid )</li>
*   <li>oa.enter()</li>
*   <li>info = oa.makeInvocationInfo( oid )</li>
*   <li>info.setOperation( operation )</li>
*   <li>push info</li>
*   <li>oa.getInvocationServant( info )</li>
*   <li>sreq.setExecuteReturnServantInResponseConstructor( true )</li>
*   <li>dispatch to servant</li>
*   <li>oa.returnServant()</li>
*   <li>oa.exit()</li>
*   <li>pop info</li>
*   <ol>
* </li>
* REVISIT: Is this the required order for exit/pop?  Cna they be nested instead?
* Note that getInvocationServant and returnServant may throw exceptions.  In such cases,
* returnServant, exit, and pop must be called in the correct order.
* <li>The local pattern:
*   <ol>
*   <li>oa = oaf.find( oaid )</li>
*   <li>oa.enter()</li>
*   <li>info = oa.makeInvocationInfo( oid )</li>
*   <li>info.setOperation( operation )</li>
*   <li>push info</li>
*   <li>oa.getInvocationServant( info )</li>
*   <li>dispatch to servant</li>
*   <li>oa.returnServant()</li>
*   <li>oa.exit()</li>
*   <li>pop info</li>
*   <ol>
* </li>
* This is the same as the remote case, except that setExecuteReturnServantInResponseConstructor
* is not needed (or possible, since there is no server request).
* <li>The fast local pattern: When delegate is constructed,
*    first extract ObjectKey from IOR in delegate,
*    then get ObjectId, ObjectAdapterId, and ObjectAdapterFactory (oaf). Then:
*    <ol>
*    <li>oa = oaf.find( oaid )</li>
*    <li>info = oa.makeInvocationInfo( oid ) (note: no operation!)</li>
*    <li>push info (needed for the correct functioning of getInvocationServant)</li>
*    <li>oa.getInvocationServant( info )</li>
*    <li>pop info
*    </ol>
*    The info instance (which includes the Servant) is cached in the client subcontract.
*    <p>Then, on each invocation:</p>
*    <ol>
*    <li>newinfo = copy of info (clone)</li>
*    <li>info.setOperation( operation )</li>
*    <li>push newinfo</li>
*    <li>oa.enter()</li>
*    <li>dispatch to servant</li>
*    <li>oa.returnServant()</li>  // XXX This is probably wrong: remove it.
*    <li>oa.exit()</li>
*    <li>pop info</li>
*    </ol>
* </li>
* </ol>
* XXX fast local should not call returnServant: what is correct here?
* <p>
*  适配器由ORT推出。这意味着所有对象适配器必须：
* <UL>
*  <LI>拥有适配器模板</LI> <LI>拥有适配器模板</LI> <LI>拥有适配器模板</LI> <LI> >支持获取和设置其ObjectReferenceFactory </LI> <LI>提供
* 对其当前状态的访问</LI> <LI>支持在适配器模板中表示的配置文件中添加组件</LI>。
* </UL>
*  其他需求：
* <UL>
*  <LI>所有对象适配器在创建时必须调用ORB.AdapterCreated。
* </LI>
*  <LI>所有适配器管理器必须在状态改变时调用ORB.AdapterManagerStateChanged,将内部状态映射到ORT状态。
* </LI> <LI> AdapterStateChanged必须在适配器状态更改时调用(从某处)适配器管理器状态更改。</LI>。
* </UL>
* <P>
*  对象适配器还必须提供以下机制：
* <UL>
*  </li> <li>管理对象引用生命周期</li> <li>如果适配器希望实现此类机制,则控制服务器与对象引用的关联</li> <li>
* </UL>
* 这些机制都是对象适配器特定的,因此我们不尝试在这里为这些函数创建通用API。对象适配器本身必须直接向用户提供这些API,并且它们不会影响ORB的其余部分。
* 这个接口基本上使得可以将任何对象适配器插入到ORB中,并使OA工作与便携式拦截器正常工作,并且还具有正确分派给对象适配器的请求。
* <P>
*  ObjectAdapter的基本功能是将对象ID映射到服务对象,并支持将请求分派给服务对象的合同的调度操作。这是getInvocationServant方法的目的。
* 此外,ObjectAdapters必须能够在执行方法存在的情况下正常改变状态。这需要使用enter / exit方法。最后,ObjectAdapters通常需要访问有关请求的信息。
* 这通过OAInvocationInfo类和由ORB上的push / pop / peekInvocationInfo维护的线程本地堆栈来容纳。
* <P>
*  为了有用,这个调度周期必须非常有效。有几种情况：
* <ol>
* <li>远程调用,其中在服务器转包中处理分发。</li> <li>本地调用,其中在客户端转包中处理分发。
* </li> <li>其中当为客户端子合同建立IOR时对服务器进行高速缓存,并且在客户端子合同中将分发处理到高速缓存的子合同。
* </ol>
* <p>
*  这3种情况中的每一种都有不同的处理。在每个请求中,假设为已知的ObjectId和ObjectAdapterId,它们可以从对象键获取。
*  ObjectAdaptorFactory在转包注册表中可用,并在转包ID下注册。子合同ID也可在对象键中使用。
* <ol>
*  <li>远程模式：
* <ol>
*  <li> oa = oaf.find(oaid)</li> <li> oa.enter()</li> <li> info = oa.makeInvocationInfo(oid)</li> <li> 
* info.setOperation </li> </li> </li> </li> </li> </li> </li> <li> oa.returnServant()</li> <li> oa.exit
* ()</li> <li> pop信息</li>。
* <ol>
* </li>
*  REVISIT：这是退出/ pop所需的顺序吗? Cna它们嵌套?注意,getInvocationServant和returnServant可能会抛出异常。
* 在这种情况下,必须以正确的顺序调用returnServant,exit和pop。 <li>本地模式：。
* <ol>
* <li> oa = oaf.find(oaid)</li> <li> oa.enter()</li> <li> info = oa.makeInvocationInfo(oid)</li> <li> i
* nfo.setOperation )</li> <li>推送信息</li> <li> oa.returnServant()</li> <li> oa.getInvocationServant li> o
* a.exit()</li> <li> pop info </li>。
* <ol>
*/
public interface ObjectAdapter
{
    ////////////////////////////////////////////////////////////////////////////
    // Basic methods for supporting interceptors
    ////////////////////////////////////////////////////////////////////////////

    /** Returns the ORB associated with this adapter.
    /* <p>
    /* </li>
    /*  这与远程情况相同,除了不需要setExecuteReturnServantInResponseConstructor(或者可能,因为没有服务器请求)。
    /*  <li>快速局部模式：构造delegate时,首先在delegate中从IOR中提取ObjectKey,然后获取ObjectId,ObjectAdapterId和ObjectAdapterFactor
    /* y(oaf)。
    /*  这与远程情况相同,除了不需要setExecuteReturnServantInResponseConstructor(或者可能,因为没有服务器请求)。然后：。
    /* <ol>
    /*  <li> oa = oaf.find(oaid)</li> <li> info = oa.makeInvocationInfo(oid)(注意：无操作！)</li> <li>推送信息(getInvoc
    /* ationServant )</li> <li> oa.getInvocationServant(info)</li> <li>弹出信息。
    /* </ol>
    /*  信息实例(包括Servant)被缓存在客户端子合同中。 <p>然后,在每次调用：</p>
    /* <ol>
    /*  <li> newinfo =信息(克隆)的副本</li> <li> info.setOperation(操作)</li> <li> push newinfo </li> li> dispatch to
    /*  servant </li> <li> oa.returnServant()</li> // XXX这可能是错误的：删除它。
    /*  <li> oa.exit()</li> <li>弹出信息</li>。
    /* </ol>
    /* </li>
    /* </ol>
    /*  XXX快速本地不应该调用returnServant：什么是正确的?
    /* 
    */
    ORB getORB() ;

    Policy getEffectivePolicy( int type ) ;

    /** Returns the IOR template of this adapter.  The profiles
    * in this template may be updated only during the AdapterCreated call.
    * After that call completes, the IOR template must be made immutable.
    * Note that the server ID, ORB ID, and adapter name are all available
    * from the IOR template.
    * <p>
    */
    IORTemplate getIORTemplate() ;

    ////////////////////////////////////////////////////////////////////////////
    // Methods needed to support ORT.
    ////////////////////////////////////////////////////////////////////////////

    /** Return the ID of the AdapterManager for this object adapter.
    /* <p>
    /* 在此模板中只能在AdapterCreated调用期间更新。在该调用完成后,IOR模板必须是不可变的。请注意,服务器ID,ORB ID和适配器名称都可从IOR模板获取。
    /* 
    */
    int getManagerId() ;

    /** Return the current state of this object adapter (see
    * org.omg.PortableInterceptors for states.
    * <p>
    */
    short getState() ;

    ObjectReferenceTemplate getAdapterTemplate() ;

    ObjectReferenceFactory getCurrentFactory() ;

    /** Change the current factory.  This may only be called during the
    * AdapterCreated call.
    * <p>
    *  状态的org.omg.PortableInterceptors。
    * 
    */
    void setCurrentFactory( ObjectReferenceFactory factory ) ;

    ////////////////////////////////////////////////////////////////////////////
    // Methods required for dispatching to servants
    ////////////////////////////////////////////////////////////////////////////

    /** Get the servant corresponding to the given objectId, if this is supported.
     * This method is only used for models where the servant is an ObjectImpl,
     * which allows the servant to be used directly as the stub.  This allows an object
     * reference to be replaced by its servant when it is unmarshalled locally.
     * Such objects are not ORB mediated.
     * <p>
     *  AdapterCreated调用。
     * 
     */
    org.omg.CORBA.Object getLocalServant( byte[] objectId ) ;

    /** Get the servant for the request given by the parameters.
    * info must contain a valid objectId in this call.
    * The servant is set in the InvocationInfo argument that is passed into
    * this call.
    * <p>
    *  此方法仅用于其中servant是ObjectImpl的模型,这允许servant直接用作存根。这允许对象引用在其本地解组时由其servant替换。这些对象不是ORB介导的。
    * 
    * 
    * @param info is the InvocationInfo object for the object reference
    * @exception ForwardException (a runtime exception) is thrown if the request
    * is to be handled by a different object reference.
    */
    void getInvocationServant( OAInvocationInfo info ) ;

    /** enter must be called before each request is invoked on a servant.
    /* <p>
    /*  info必须在此调用中包含有效的objectId。 servant在传递到此调用的InvocationInfo参数中设置。
    /* 
    /* 
      * @exception OADestroyed is thrown when an OA has been destroyed, which
      * requires a retry in the case where an AdapterActivator is present.
      */
    void enter( ) throws OADestroyed ;

    /** exit must be called after each request has been completed.  If enter
    * is called, there must always be a corresponding exit.
    * <p>
    */
    void exit( ) ;

    /** Must be called every time getInvocationServant is called after
     * the request has completed.
     * <p>
     *  被调用,必须总是有相应的退出。
     * 
     */
    public void returnServant() ;

    /** Create an instance of InvocationInfo that is appropriate for this
    * Object adapter.
    * <p>
    *  请求已完成。
    * 
    */
    OAInvocationInfo makeInvocationInfo( byte[] objectId ) ;

    /** Return the most derived interface for the given servant and objectId.
    /* <p>
    /*  对象适配器。
    /* 
    */
    String[] getInterfaces( Object servant, byte[] objectId ) ;
}
