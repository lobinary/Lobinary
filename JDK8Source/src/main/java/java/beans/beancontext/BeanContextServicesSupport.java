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

package java.beans.beancontext;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import java.util.TooManyListenersException;

import java.util.Locale;

/**
 * <p>
 * This helper class provides a utility implementation of the
 * java.beans.beancontext.BeanContextServices interface.
 * </p>
 * <p>
 * Since this class directly implements the BeanContextServices interface,
 * the class can, and is intended to be used either by subclassing this
 * implementation, or via delegation of an instance of this class
 * from another through the BeanContextProxy interface.
 * </p>
 *
 * <p>
 * <p>
 *  此助手类提供了java.beans.beancontext.BeanContextServices接口的实用程序实现。
 * </p>
 * <p>
 *  因为这个类直接实现了BeanContextServices接口,所以这个类可以,也可以通过子类化这个实现来使用,或者通过BeanContextProxy接口从另一个实例委托这个类的实例。
 * </p>
 * 
 * 
 * @author Laurence P. G. Cable
 * @since 1.2
 */

public class      BeanContextServicesSupport extends BeanContextSupport
       implements BeanContextServices {
    private static final long serialVersionUID = -8494482757288719206L;

    /**
     * <p>
     * Construct a BeanContextServicesSupport instance
     * </p>
     *
     * <p>
     * <p>
     *  构造BeanContextServicesSupport实例
     * </p>
     * 
     * 
     * @param peer      The peer BeanContext we are supplying an implementation for, if null the this object is its own peer
     * @param lcle      The current Locale for this BeanContext.
     * @param dTime     The initial state, true if in design mode, false if runtime.
     * @param visible   The initial visibility.
     *
     */

    public BeanContextServicesSupport(BeanContextServices peer, Locale lcle, boolean dTime, boolean visible) {
        super(peer, lcle, dTime, visible);
    }

    /**
     * Create an instance using the specified Locale and design mode.
     *
     * <p>
     *  使用指定的区域设置和设计模式创建实例。
     * 
     * 
     * @param peer      The peer BeanContext we are supplying an implementation for, if null the this object is its own peer
     * @param lcle      The current Locale for this BeanContext.
     * @param dtime     The initial state, true if in design mode, false if runtime.
     */

    public BeanContextServicesSupport(BeanContextServices peer, Locale lcle, boolean dtime) {
        this (peer, lcle, dtime, true);
    }

    /**
     * Create an instance using the specified locale
     *
     * <p>
     *  使用指定的区域设置创建实例
     * 
     * 
     * @param peer      The peer BeanContext we are supplying an implementation for, if null the this object is its own peer
     * @param lcle      The current Locale for this BeanContext.
     */

    public BeanContextServicesSupport(BeanContextServices peer, Locale lcle) {
        this (peer, lcle, false, true);
    }

    /**
     * Create an instance with a peer
     *
     * <p>
     *  使用对等体创建实例
     * 
     * 
     * @param peer      The peer BeanContext we are supplying an implementation for, if null the this object is its own peer
     */

    public BeanContextServicesSupport(BeanContextServices peer) {
        this (peer, null, false, true);
    }

    /**
     * Create an instance that is not a delegate of another object
     * <p>
     *  创建不是另一个对象的委托的实例
     * 
     */

    public BeanContextServicesSupport() {
        this (null, null, false, true);
    }

    /**
     * called by BeanContextSupport superclass during construction and
     * deserialization to initialize subclass transient state.
     *
     * subclasses may envelope this method, but should not override it or
     * call it directly.
     * <p>
     *  在构造和反序列化期间由BeanContextSupport超类调用以初始化子类瞬态状态。
     * 
     *  子类可以包装此方法,但不应覆盖它或直接调用它。
     * 
     */

    public void initialize() {
        super.initialize();

        services     = new HashMap(serializable + 1);
        bcsListeners = new ArrayList(1);
    }

    /**
     * Gets the <tt>BeanContextServices</tt> associated with this
     * <tt>BeanContextServicesSupport</tt>.
     *
     * <p>
     *  获取与此<tt> BeanContextServicesSupport </tt>关联的<tt> BeanContextServices </tt>。
     * 
     * 
     * @return the instance of <tt>BeanContext</tt>
     * this object is providing the implementation for.
     */
    public BeanContextServices getBeanContextServicesPeer() {
        return (BeanContextServices)getBeanContextChildPeer();
    }

    /************************************************************************/

    /*
     * protected nested class containing per child information, an instance
     * of which is associated with each child in the "children" hashtable.
     * subclasses can extend this class to include their own per-child state.
     *
     * Note that this 'value' is serialized with the corresponding child 'key'
     * when the BeanContextSupport is serialized.
     * <p>
     *  受保护的嵌套类包含每个子项信息,其实例与"子项"散列表中的每个子项相关联。子类可以扩展这个类以包括它们自己的每个子状态。
     * 
     *  注意,当BeanContextSupport序列化时,这个'value'被序列化为相应的子键'key'。
     * 
     */

    protected class BCSSChild extends BeanContextSupport.BCSChild  {

        private static final long serialVersionUID = -3263851306889194873L;

        /*
         * private nested class to map serviceClass to Provider and requestors
         * listeners.
         * <p>
         *  私有嵌套类将serviceClass映射到提供者和请求者侦听器。
         * 
         */

        class BCSSCServiceClassRef {

            // create an instance of a service ref

            BCSSCServiceClassRef(Class sc, BeanContextServiceProvider bcsp, boolean delegated) {
                super();

                serviceClass     = sc;

                if (delegated)
                    delegateProvider = bcsp;
                else
                    serviceProvider  = bcsp;
            }

            // add a requestor and assoc listener

            void addRequestor(Object requestor, BeanContextServiceRevokedListener bcsrl) throws TooManyListenersException {
                BeanContextServiceRevokedListener cbcsrl = (BeanContextServiceRevokedListener)requestors.get(requestor);

                if (cbcsrl != null && !cbcsrl.equals(bcsrl))
                    throw new TooManyListenersException();

                requestors.put(requestor, bcsrl);
            }

            // remove a requestor

            void removeRequestor(Object requestor) {
                requestors.remove(requestor);
            }

            // check a requestors listener

            void verifyRequestor(Object requestor, BeanContextServiceRevokedListener bcsrl) throws TooManyListenersException {
                BeanContextServiceRevokedListener cbcsrl = (BeanContextServiceRevokedListener)requestors.get(requestor);

                if (cbcsrl != null && !cbcsrl.equals(bcsrl))
                    throw new TooManyListenersException();
            }

            void verifyAndMaybeSetProvider(BeanContextServiceProvider bcsp, boolean isDelegated) {
                BeanContextServiceProvider current;

                if (isDelegated) { // the provider is delegated
                    current = delegateProvider;

                    if (current == null || bcsp == null) {
                        delegateProvider = bcsp;
                        return;
                    }
                } else { // the provider is registered with this BCS
                    current = serviceProvider;

                    if (current == null || bcsp == null) {
                        serviceProvider = bcsp;
                        return;
                    }
                }

                if (!current.equals(bcsp))
                    throw new UnsupportedOperationException("existing service reference obtained from different BeanContextServiceProvider not supported");

            }

            Iterator cloneOfEntries() {
                return ((HashMap)requestors.clone()).entrySet().iterator();
            }

            Iterator entries() { return requestors.entrySet().iterator(); }

            boolean isEmpty() { return requestors.isEmpty(); }

            Class getServiceClass() { return serviceClass; }

            BeanContextServiceProvider getServiceProvider() {
                return serviceProvider;
            }

            BeanContextServiceProvider getDelegateProvider() {
                return delegateProvider;
            }

            boolean isDelegated() { return delegateProvider != null; }

            void addRef(boolean delegated) {
                if (delegated) {
                    delegateRefs++;
                } else {
                    serviceRefs++;
                }
            }


            void releaseRef(boolean delegated) {
                if (delegated) {
                    if (--delegateRefs == 0) {
                        delegateProvider = null;
                    }
                } else {
                    if (--serviceRefs  <= 0) {
                        serviceProvider = null;
                    }
                }
            }

            int getRefs() { return serviceRefs + delegateRefs; }

            int getDelegateRefs() { return delegateRefs; }

            int getServiceRefs() { return serviceRefs; }

            /*
             * fields
             * <p>
             *  字段
             * 
             */

            Class                               serviceClass;

            BeanContextServiceProvider          serviceProvider;
            int                                 serviceRefs;

            BeanContextServiceProvider          delegateProvider; // proxy
            int                                 delegateRefs;

            HashMap                             requestors = new HashMap(1);
        }

        /*
         * per service reference info ...
         * <p>
         * 每个服务参考信息...
         * 
         */

        class BCSSCServiceRef {
            BCSSCServiceRef(BCSSCServiceClassRef scref, boolean isDelegated) {
                serviceClassRef = scref;
                delegated       = isDelegated;
            }

            void addRef()  { refCnt++;        }
            int  release() { return --refCnt; }

            BCSSCServiceClassRef getServiceClassRef() { return serviceClassRef; }

            boolean isDelegated() { return delegated; }

            /*
             * fields
             * <p>
             *  字段
             * 
             */

            BCSSCServiceClassRef serviceClassRef;
            int                  refCnt    = 1;
            boolean              delegated = false;
        }

        BCSSChild(Object bcc, Object peer) { super(bcc, peer); }

        // note usage of service per requestor, per service

        synchronized void usingService(Object requestor, Object service, Class serviceClass, BeanContextServiceProvider bcsp, boolean isDelegated, BeanContextServiceRevokedListener bcsrl)  throws TooManyListenersException, UnsupportedOperationException {

            // first, process mapping from serviceClass to requestor(s)

            BCSSCServiceClassRef serviceClassRef = null;

            if (serviceClasses == null)
                serviceClasses = new HashMap(1);
            else
                serviceClassRef = (BCSSCServiceClassRef)serviceClasses.get(serviceClass);

            if (serviceClassRef == null) { // new service being used ...
                serviceClassRef = new BCSSCServiceClassRef(serviceClass, bcsp, isDelegated);
                serviceClasses.put(serviceClass, serviceClassRef);

            } else { // existing service ...
                serviceClassRef.verifyAndMaybeSetProvider(bcsp, isDelegated); // throws
                serviceClassRef.verifyRequestor(requestor, bcsrl); // throws
            }

            serviceClassRef.addRequestor(requestor, bcsrl);
            serviceClassRef.addRef(isDelegated);

            // now handle mapping from requestor to service(s)

            BCSSCServiceRef serviceRef = null;
            Map             services   = null;

            if (serviceRequestors == null) {
                serviceRequestors = new HashMap(1);
            } else {
                services = (Map)serviceRequestors.get(requestor);
            }

            if (services == null) {
                services = new HashMap(1);

                serviceRequestors.put(requestor, services);
            } else
                serviceRef = (BCSSCServiceRef)services.get(service);

            if (serviceRef == null) {
                serviceRef = new BCSSCServiceRef(serviceClassRef, isDelegated);

                services.put(service, serviceRef);
            } else {
                serviceRef.addRef();
            }
        }

        // release a service reference

        synchronized void releaseService(Object requestor, Object service) {
            if (serviceRequestors == null) return;

            Map services = (Map)serviceRequestors.get(requestor);

            if (services == null) return; // oops its not there anymore!

            BCSSCServiceRef serviceRef = (BCSSCServiceRef)services.get(service);

            if (serviceRef == null) return; // oops its not there anymore!

            BCSSCServiceClassRef serviceClassRef = serviceRef.getServiceClassRef();
            boolean                    isDelegated = serviceRef.isDelegated();
            BeanContextServiceProvider bcsp        = isDelegated ? serviceClassRef.getDelegateProvider() : serviceClassRef.getServiceProvider();

            bcsp.releaseService(BeanContextServicesSupport.this.getBeanContextServicesPeer(), requestor, service);

            serviceClassRef.releaseRef(isDelegated);
            serviceClassRef.removeRequestor(requestor);

            if (serviceRef.release() == 0) {

                services.remove(service);

                if (services.isEmpty()) {
                    serviceRequestors.remove(requestor);
                    serviceClassRef.removeRequestor(requestor);
                }

                if (serviceRequestors.isEmpty()) {
                    serviceRequestors = null;
                }

                if (serviceClassRef.isEmpty()) {
                    serviceClasses.remove(serviceClassRef.getServiceClass());
                }

                if (serviceClasses.isEmpty())
                    serviceClasses = null;
            }
        }

        // revoke a service

        synchronized void revokeService(Class serviceClass, boolean isDelegated, boolean revokeNow) {
            if (serviceClasses == null) return;

            BCSSCServiceClassRef serviceClassRef = (BCSSCServiceClassRef)serviceClasses.get(serviceClass);

            if (serviceClassRef == null) return;

            Iterator i = serviceClassRef.cloneOfEntries();

            BeanContextServiceRevokedEvent bcsre       = new BeanContextServiceRevokedEvent(BeanContextServicesSupport.this.getBeanContextServicesPeer(), serviceClass, revokeNow);
            boolean                        noMoreRefs  = false;

            while (i.hasNext() && serviceRequestors != null) {
                Map.Entry                         entry    = (Map.Entry)i.next();
                BeanContextServiceRevokedListener listener = (BeanContextServiceRevokedListener)entry.getValue();

                if (revokeNow) {
                    Object  requestor = entry.getKey();
                    Map     services  = (Map)serviceRequestors.get(requestor);

                    if (services != null) {
                        Iterator i1 = services.entrySet().iterator();

                        while (i1.hasNext()) {
                            Map.Entry       tmp        = (Map.Entry)i1.next();

                            BCSSCServiceRef serviceRef = (BCSSCServiceRef)tmp.getValue();
                            if (serviceRef.getServiceClassRef().equals(serviceClassRef) && isDelegated == serviceRef.isDelegated()) {
                                i1.remove();
                            }
                        }

                        if (noMoreRefs = services.isEmpty()) {
                            serviceRequestors.remove(requestor);
                        }
                    }

                    if (noMoreRefs) serviceClassRef.removeRequestor(requestor);
                }

                listener.serviceRevoked(bcsre);
            }

            if (revokeNow && serviceClasses != null) {
                if (serviceClassRef.isEmpty())
                    serviceClasses.remove(serviceClass);

                if (serviceClasses.isEmpty())
                    serviceClasses = null;
            }

            if (serviceRequestors != null && serviceRequestors.isEmpty())
                serviceRequestors = null;
        }

        // release all references for this child since it has been unnested.

        void cleanupReferences() {

            if (serviceRequestors == null) return;

            Iterator requestors = serviceRequestors.entrySet().iterator();

            while(requestors.hasNext()) {
                Map.Entry            tmp       = (Map.Entry)requestors.next();
                Object               requestor = tmp.getKey();
                Iterator             services  = ((Map)tmp.getValue()).entrySet().iterator();

                requestors.remove();

                while (services.hasNext()) {
                    Map.Entry       entry   = (Map.Entry)services.next();
                    Object          service = entry.getKey();
                    BCSSCServiceRef sref    = (BCSSCServiceRef)entry.getValue();

                    BCSSCServiceClassRef       scref = sref.getServiceClassRef();

                    BeanContextServiceProvider bcsp  = sref.isDelegated() ? scref.getDelegateProvider() : scref.getServiceProvider();

                    scref.removeRequestor(requestor);
                    services.remove();

                    while (sref.release() >= 0) {
                        bcsp.releaseService(BeanContextServicesSupport.this.getBeanContextServicesPeer(), requestor, service);
                    }
                }
            }

            serviceRequestors = null;
            serviceClasses    = null;
        }

        void revokeAllDelegatedServicesNow() {
            if (serviceClasses == null) return;

            Iterator serviceClassRefs  =
                new HashSet(serviceClasses.values()).iterator();

            while (serviceClassRefs.hasNext()) {
                BCSSCServiceClassRef serviceClassRef = (BCSSCServiceClassRef)serviceClassRefs.next();

                if (!serviceClassRef.isDelegated()) continue;

                Iterator i = serviceClassRef.cloneOfEntries();
                BeanContextServiceRevokedEvent bcsre       = new BeanContextServiceRevokedEvent(BeanContextServicesSupport.this.getBeanContextServicesPeer(), serviceClassRef.getServiceClass(), true);
                boolean                        noMoreRefs  = false;

                while (i.hasNext()) {
                    Map.Entry                         entry     = (Map.Entry)i.next();
                    BeanContextServiceRevokedListener listener  = (BeanContextServiceRevokedListener)entry.getValue();

                    Object                            requestor = entry.getKey();
                    Map                               services  = (Map)serviceRequestors.get(requestor);

                    if (services != null) {
                        Iterator i1 = services.entrySet().iterator();

                        while (i1.hasNext()) {
                            Map.Entry       tmp        = (Map.Entry)i1.next();

                            BCSSCServiceRef serviceRef = (BCSSCServiceRef)tmp.getValue();
                            if (serviceRef.getServiceClassRef().equals(serviceClassRef) && serviceRef.isDelegated()) {
                                i1.remove();
                            }
                        }

                        if (noMoreRefs = services.isEmpty()) {
                            serviceRequestors.remove(requestor);
                        }
                    }

                    if (noMoreRefs) serviceClassRef.removeRequestor(requestor);

                    listener.serviceRevoked(bcsre);

                    if (serviceClassRef.isEmpty())
                        serviceClasses.remove(serviceClassRef.getServiceClass());
                }
            }

            if (serviceClasses.isEmpty()) serviceClasses = null;

            if (serviceRequestors != null && serviceRequestors.isEmpty())
                serviceRequestors = null;
        }

        /*
         * fields
         * <p>
         *  字段
         * 
         */

        private transient HashMap       serviceClasses;
        private transient HashMap       serviceRequestors;
    }

    /**
     * <p>
     * Subclasses can override this method to insert their own subclass
     * of Child without having to override add() or the other Collection
     * methods that add children to the set.
     * </p>
     *
     * <p>
     * <p>
     *  子类可以覆盖此方法以插入他们自己的子类的子类,而无需重写add()或其他将子项添加到集合的集合方法。
     * </p>
     * 
     * 
     * @param targetChild the child to create the Child on behalf of
     * @param peer        the peer if the targetChild and peer are related by BeanContextProxy
     */

    protected BCSChild createBCSChild(Object targetChild, Object peer) {
        return new BCSSChild(targetChild, peer);
    }

    /************************************************************************/

        /**
         * subclasses may subclass this nested class to add behaviors for
         * each BeanContextServicesProvider.
         * <p>
         *  子类可以对这个嵌套类进行子类化,为每个BeanContextServicesProvider添加行为。
         * 
         */

        protected static class BCSSServiceProvider implements Serializable {
            private static final long serialVersionUID = 861278251667444782L;

            BCSSServiceProvider(Class sc, BeanContextServiceProvider bcsp) {
                super();

                serviceProvider = bcsp;
            }

            /**
             * Returns the service provider.
             * <p>
             *  返回服务提供程序。
             * 
             * 
             * @return the service provider
             */
            protected BeanContextServiceProvider getServiceProvider() {
                return serviceProvider;
            }

            /**
             * The service provider.
             * <p>
             *  服务提供商。
             * 
             */

            protected BeanContextServiceProvider serviceProvider;
        }

        /**
         * subclasses can override this method to create new subclasses of
         * BCSSServiceProvider without having to override addService() in
         * order to instantiate.
         * <p>
         *  子类可以覆盖此方法以创建BCSSServiceProvider的新子类,而无需重写addService()以便实例化。
         * 
         * 
         * @param sc the class
         * @param bcsp the service provider
         * @return a service provider without overriding addService()
         */

        protected BCSSServiceProvider createBCSSServiceProvider(Class sc, BeanContextServiceProvider bcsp) {
            return new BCSSServiceProvider(sc, bcsp);
        }

    /************************************************************************/

    /**
     * add a BeanContextServicesListener
     *
     * <p>
     *  添加一个BeanContextServicesListener
     * 
     * 
     * @throws NullPointerException if the argument is null
     */

    public void addBeanContextServicesListener(BeanContextServicesListener bcsl) {
        if (bcsl == null) throw new NullPointerException("bcsl");

        synchronized(bcsListeners) {
            if (bcsListeners.contains(bcsl))
                return;
            else
                bcsListeners.add(bcsl);
        }
    }

    /**
     * remove a BeanContextServicesListener
     * <p>
     *  删除BeanContextServicesListener
     * 
     */

    public void removeBeanContextServicesListener(BeanContextServicesListener bcsl) {
        if (bcsl == null) throw new NullPointerException("bcsl");

        synchronized(bcsListeners) {
            if (!bcsListeners.contains(bcsl))
                return;
            else
                bcsListeners.remove(bcsl);
        }
    }

    /**
     * add a service
     * <p>
     *  添加服务
     * 
     * 
     * @param serviceClass the service class
     * @param bcsp the service provider
     */

    public boolean addService(Class serviceClass, BeanContextServiceProvider bcsp) {
        return addService(serviceClass, bcsp, true);
    }

    /**
     * add a service
     * <p>
     *  添加服务
     * 
     * 
     * @param serviceClass the service class
     * @param bcsp the service provider
     * @param fireEvent whether or not an event should be fired
     * @return true if the service was successfully added
     */

    protected boolean addService(Class serviceClass, BeanContextServiceProvider bcsp, boolean fireEvent) {

        if (serviceClass == null) throw new NullPointerException("serviceClass");
        if (bcsp         == null) throw new NullPointerException("bcsp");

        synchronized(BeanContext.globalHierarchyLock) {
            if (services.containsKey(serviceClass))
                return false;
            else {
                services.put(serviceClass,  createBCSSServiceProvider(serviceClass, bcsp));

                if (bcsp instanceof Serializable) serializable++;

                if (!fireEvent) return true;


                BeanContextServiceAvailableEvent bcssae = new BeanContextServiceAvailableEvent(getBeanContextServicesPeer(), serviceClass);

                fireServiceAdded(bcssae);

                synchronized(children) {
                    Iterator i = children.keySet().iterator();

                    while (i.hasNext()) {
                        Object c = i.next();

                        if (c instanceof BeanContextServices) {
                            ((BeanContextServicesListener)c).serviceAvailable(bcssae);
                        }
                    }
                }

                return true;
            }
        }
    }

    /**
     * remove a service
     * <p>
     *  删除服务
     * 
     * 
     * @param serviceClass the service class
     * @param bcsp the service provider
     * @param revokeCurrentServicesNow whether or not to revoke the service
     */

    public void revokeService(Class serviceClass, BeanContextServiceProvider bcsp, boolean revokeCurrentServicesNow) {

        if (serviceClass == null) throw new NullPointerException("serviceClass");
        if (bcsp         == null) throw new NullPointerException("bcsp");

        synchronized(BeanContext.globalHierarchyLock) {
            if (!services.containsKey(serviceClass)) return;

            BCSSServiceProvider bcsssp = (BCSSServiceProvider)services.get(serviceClass);

            if (!bcsssp.getServiceProvider().equals(bcsp))
                throw new IllegalArgumentException("service provider mismatch");

            services.remove(serviceClass);

            if (bcsp instanceof Serializable) serializable--;

            Iterator i = bcsChildren(); // get the BCSChild values.

            while (i.hasNext()) {
                ((BCSSChild)i.next()).revokeService(serviceClass, false, revokeCurrentServicesNow);
            }

            fireServiceRevoked(serviceClass, revokeCurrentServicesNow);
        }
    }

    /**
     * has a service, which may be delegated
     * <p>
     *  有一个服务,可以委托
     * 
     */

    public synchronized boolean hasService(Class serviceClass) {
        if (serviceClass == null) throw new NullPointerException("serviceClass");

        synchronized(BeanContext.globalHierarchyLock) {
            if (services.containsKey(serviceClass)) return true;

            BeanContextServices bcs = null;

            try {
                bcs = (BeanContextServices)getBeanContext();
            } catch (ClassCastException cce) {
                return false;
            }

            return bcs == null ? false : bcs.hasService(serviceClass);
        }
    }

    /************************************************************************/

    /*
     * a nested subclass used to represent a proxy for serviceClasses delegated
     * to an enclosing BeanContext.
     * <p>
     *  一个嵌套子类,用于表示委托给一个封闭BeanContext的serviceClasses的代理。
     * 
     */

    protected class BCSSProxyServiceProvider implements BeanContextServiceProvider, BeanContextServiceRevokedListener {

        BCSSProxyServiceProvider(BeanContextServices bcs) {
            super();

            nestingCtxt = bcs;
        }

        public Object getService(BeanContextServices bcs, Object requestor, Class serviceClass, Object serviceSelector) {
            Object service = null;

            try {
                service = nestingCtxt.getService(bcs, requestor, serviceClass, serviceSelector, this);
            } catch (TooManyListenersException tmle) {
                return null;
            }

            return service;
        }

        public void releaseService(BeanContextServices bcs, Object requestor, Object service) {
            nestingCtxt.releaseService(bcs, requestor, service);
        }

        public Iterator getCurrentServiceSelectors(BeanContextServices bcs, Class serviceClass) {
            return nestingCtxt.getCurrentServiceSelectors(serviceClass);
        }

        public void serviceRevoked(BeanContextServiceRevokedEvent bcsre) {
            Iterator i = bcsChildren(); // get the BCSChild values.

            while (i.hasNext()) {
                ((BCSSChild)i.next()).revokeService(bcsre.getServiceClass(), true, bcsre.isCurrentServiceInvalidNow());
            }
        }

        /*
         * fields
         * <p>
         *  字段
         * 
         */

        private BeanContextServices nestingCtxt;
    }

    /************************************************************************/

    /**
     * obtain a service which may be delegated
     * <p>
     *  获得可以委托的服务
     * 
     */

     public Object getService(BeanContextChild child, Object requestor, Class serviceClass, Object serviceSelector, BeanContextServiceRevokedListener bcsrl) throws TooManyListenersException {
        if (child        == null) throw new NullPointerException("child");
        if (serviceClass == null) throw new NullPointerException("serviceClass");
        if (requestor    == null) throw new NullPointerException("requestor");
        if (bcsrl        == null) throw new NullPointerException("bcsrl");

        Object              service = null;
        BCSSChild           bcsc;
        BeanContextServices bcssp   = getBeanContextServicesPeer();

        synchronized(BeanContext.globalHierarchyLock) {
            synchronized(children) { bcsc = (BCSSChild)children.get(child); }

            if (bcsc == null) throw new IllegalArgumentException("not a child of this context"); // not a child ...

            BCSSServiceProvider bcsssp = (BCSSServiceProvider)services.get(serviceClass);

            if (bcsssp != null) {
                BeanContextServiceProvider bcsp = bcsssp.getServiceProvider();
                service = bcsp.getService(bcssp, requestor, serviceClass, serviceSelector);
                if (service != null) { // do bookkeeping ...
                    try {
                        bcsc.usingService(requestor, service, serviceClass, bcsp, false, bcsrl);
                    } catch (TooManyListenersException tmle) {
                        bcsp.releaseService(bcssp, requestor, service);
                        throw tmle;
                    } catch (UnsupportedOperationException uope) {
                        bcsp.releaseService(bcssp, requestor, service);
                        throw uope; // unchecked rt exception
                    }

                    return service;
                }
            }


            if (proxy != null) {

                // try to delegate ...

                service = proxy.getService(bcssp, requestor, serviceClass, serviceSelector);

                if (service != null) { // do bookkeeping ...
                    try {
                        bcsc.usingService(requestor, service, serviceClass, proxy, true, bcsrl);
                    } catch (TooManyListenersException tmle) {
                        proxy.releaseService(bcssp, requestor, service);
                        throw tmle;
                    } catch (UnsupportedOperationException uope) {
                        proxy.releaseService(bcssp, requestor, service);
                        throw uope; // unchecked rt exception
                    }

                    return service;
                }
            }
        }

        return null;
    }

    /**
     * release a service
     * <p>
     *  释放服务
     * 
     */

    public void releaseService(BeanContextChild child, Object requestor, Object service) {
        if (child     == null) throw new NullPointerException("child");
        if (requestor == null) throw new NullPointerException("requestor");
        if (service   == null) throw new NullPointerException("service");

        BCSSChild bcsc;

        synchronized(BeanContext.globalHierarchyLock) {
                synchronized(children) { bcsc = (BCSSChild)children.get(child); }

                if (bcsc != null)
                    bcsc.releaseService(requestor, service);
                else
                   throw new IllegalArgumentException("child actual is not a child of this BeanContext");
        }
    }

    /**
    /* <p>
    /* 
     * @return an iterator for all the currently registered service classes.
     */

    public Iterator getCurrentServiceClasses() {
        return new BCSIterator(services.keySet().iterator());
    }

    /**
    /* <p>
    /* 
     * @return an iterator for all the currently available service selectors
     * (if any) available for the specified service.
     */

    public Iterator getCurrentServiceSelectors(Class serviceClass) {

        BCSSServiceProvider bcsssp = (BCSSServiceProvider)services.get(serviceClass);

        return bcsssp != null ? new BCSIterator(bcsssp.getServiceProvider().getCurrentServiceSelectors(getBeanContextServicesPeer(), serviceClass)) : null;
    }

    /**
     * BeanContextServicesListener callback, propagates event to all
     * currently registered listeners and BeanContextServices children,
     * if this BeanContextService does not already implement this service
     * itself.
     *
     * subclasses may override or envelope this method to implement their
     * own propagation semantics.
     * <p>
     *  BeanContextServicesListener回调,将事件传播到所有当前注册的侦听器和BeanContextServices子节点,如果此BeanContextService尚未实现此服务本身
     * 。
     * 
     *  子类可以覆盖或包络该方法以实现它们自己的传播语义。
     * 
     */

     public void serviceAvailable(BeanContextServiceAvailableEvent bcssae) {
        synchronized(BeanContext.globalHierarchyLock) {
            if (services.containsKey(bcssae.getServiceClass())) return;

            fireServiceAdded(bcssae);

            Iterator i;

            synchronized(children) {
                i = children.keySet().iterator();
            }

            while (i.hasNext()) {
                Object c = i.next();

                if (c instanceof BeanContextServices) {
                    ((BeanContextServicesListener)c).serviceAvailable(bcssae);
                }
            }
        }
     }

    /**
     * BeanContextServicesListener callback, propagates event to all
     * currently registered listeners and BeanContextServices children,
     * if this BeanContextService does not already implement this service
     * itself.
     *
     * subclasses may override or envelope this method to implement their
     * own propagation semantics.
     * <p>
     *  BeanContextServicesListener回调,将事件传播到所有当前注册的侦听器和BeanContextServices子节点,如果此BeanContextService尚未实现此服务本身
     * 。
     * 
     *  子类可以覆盖或包络该方法以实现它们自己的传播语义。
     * 
     */

    public void serviceRevoked(BeanContextServiceRevokedEvent bcssre) {
        synchronized(BeanContext.globalHierarchyLock) {
            if (services.containsKey(bcssre.getServiceClass())) return;

            fireServiceRevoked(bcssre);

            Iterator i;

            synchronized(children) {
                i = children.keySet().iterator();
            }

            while (i.hasNext()) {
                Object c = i.next();

                if (c instanceof BeanContextServices) {
                    ((BeanContextServicesListener)c).serviceRevoked(bcssre);
                }
            }
        }
    }

    /**
     * Gets the <tt>BeanContextServicesListener</tt> (if any) of the specified
     * child.
     *
     * <p>
     * 获取指定子项的<tt> BeanContextServicesListener </tt>(如果有)。
     * 
     * 
     * @param child the specified child
     * @return the BeanContextServicesListener (if any) of the specified child
     */
    protected static final BeanContextServicesListener getChildBeanContextServicesListener(Object child) {
        try {
            return (BeanContextServicesListener)child;
        } catch (ClassCastException cce) {
            return null;
        }
    }

    /**
     * called from superclass child removal operations after a child
     * has been successfully removed. called with child synchronized.
     *
     * This subclass uses this hook to immediately revoke any services
     * being used by this child if it is a BeanContextChild.
     *
     * subclasses may envelope this method in order to implement their
     * own child removal side-effects.
     * <p>
     *  在子类已成功删除后从超类子级删除操作调用。调用与子同步。
     * 
     *  这个子类使用这个钩子立即撤销这个子类使用的任何服务,如果它是一个BeanContextChild。
     * 
     *  子类可以包装这个方法,以实现自己的子删除副作用。
     * 
     */

    protected void childJustRemovedHook(Object child, BCSChild bcsc) {
        BCSSChild bcssc = (BCSSChild)bcsc;

        bcssc.cleanupReferences();
    }

    /**
     * called from setBeanContext to notify a BeanContextChild
     * to release resources obtained from the nesting BeanContext.
     *
     * This method revokes any services obtained from its parent.
     *
     * subclasses may envelope this method to implement their own semantics.
     * <p>
     *  从setBeanContext调用以通知BeanContextChild释放从嵌套BeanContext获取的资源。
     * 
     *  此方法将撤销从其父级获取的任何服务。
     * 
     *  子类可以包装这个方法来实现自己的语义。
     * 
     */

    protected synchronized void releaseBeanContextResources() {
        Object[] bcssc;

        super.releaseBeanContextResources();

        synchronized(children) {
            if (children.isEmpty()) return;

            bcssc = children.values().toArray();
        }


        for (int i = 0; i < bcssc.length; i++) {
            ((BCSSChild)bcssc[i]).revokeAllDelegatedServicesNow();
        }

        proxy = null;
    }

    /**
     * called from setBeanContext to notify a BeanContextChild
     * to allocate resources obtained from the nesting BeanContext.
     *
     * subclasses may envelope this method to implement their own semantics.
     * <p>
     *  从setBeanContext调用以通知BeanContextChild分配从嵌套BeanContext获取的资源。
     * 
     *  子类可以包装这个方法来实现自己的语义。
     * 
     */

    protected synchronized void initializeBeanContextResources() {
        super.initializeBeanContextResources();

        BeanContext nbc = getBeanContext();

        if (nbc == null) return;

        try {
            BeanContextServices bcs = (BeanContextServices)nbc;

            proxy = new BCSSProxyServiceProvider(bcs);
        } catch (ClassCastException cce) {
            // do nothing ...
        }
    }

    /**
     * Fires a <tt>BeanContextServiceEvent</tt> notifying of a new service.
     * <p>
     *  触发<tt> BeanContextServiceEvent </tt>通知新服务。
     * 
     * 
     * @param serviceClass the service class
     */
    protected final void fireServiceAdded(Class serviceClass) {
        BeanContextServiceAvailableEvent bcssae = new BeanContextServiceAvailableEvent(getBeanContextServicesPeer(), serviceClass);

        fireServiceAdded(bcssae);
    }

    /**
     * Fires a <tt>BeanContextServiceAvailableEvent</tt> indicating that a new
     * service has become available.
     *
     * <p>
     *  触发指示新服务已可用的<tt> BeanContextServiceAvailableEvent </tt>。
     * 
     * 
     * @param bcssae the <tt>BeanContextServiceAvailableEvent</tt>
     */
    protected final void fireServiceAdded(BeanContextServiceAvailableEvent bcssae) {
        Object[]                         copy;

        synchronized (bcsListeners) { copy = bcsListeners.toArray(); }

        for (int i = 0; i < copy.length; i++) {
            ((BeanContextServicesListener)copy[i]).serviceAvailable(bcssae);
        }
    }

    /**
     * Fires a <tt>BeanContextServiceEvent</tt> notifying of a service being revoked.
     *
     * <p>
     *  触发<tt> BeanContextServiceEvent </tt>通知要撤消的服务。
     * 
     * 
     * @param bcsre the <tt>BeanContextServiceRevokedEvent</tt>
     */
    protected final void fireServiceRevoked(BeanContextServiceRevokedEvent bcsre) {
        Object[]                         copy;

        synchronized (bcsListeners) { copy = bcsListeners.toArray(); }

        for (int i = 0; i < copy.length; i++) {
            ((BeanContextServiceRevokedListener)copy[i]).serviceRevoked(bcsre);
        }
    }

    /**
     * Fires a <tt>BeanContextServiceRevokedEvent</tt>
     * indicating that a particular service is
     * no longer available.
     * <p>
     *  触发指示特定服务不再可用的<tt> BeanContextServiceRevokedEvent </tt>。
     * 
     * 
     * @param serviceClass the service class
     * @param revokeNow whether or not the event should be revoked now
     */
    protected final void fireServiceRevoked(Class serviceClass, boolean revokeNow) {
        Object[]                       copy;
        BeanContextServiceRevokedEvent bcsre = new BeanContextServiceRevokedEvent(getBeanContextServicesPeer(), serviceClass, revokeNow);

        synchronized (bcsListeners) { copy = bcsListeners.toArray(); }

        for (int i = 0; i < copy.length; i++) {
            ((BeanContextServicesListener)copy[i]).serviceRevoked(bcsre);
        }
   }

    /**
     * called from BeanContextSupport writeObject before it serializes the
     * children ...
     *
     * This class will serialize any Serializable BeanContextServiceProviders
     * herein.
     *
     * subclasses may envelope this method to insert their own serialization
     * processing that has to occur prior to serialization of the children
     * <p>
     *  从BeanContextSupport writeObject调用之前序列化的孩子...
     * 
     *  这个类将序列化任何可序列化的BeanContextServiceProvider。
     * 
     * 子类可以包装该方法以插入他们自己的序列化处理,该序列化处理必须在子序列化之前发生
     * 
     */

    protected synchronized void bcsPreSerializationHook(ObjectOutputStream oos) throws IOException {

        oos.writeInt(serializable);

        if (serializable <= 0) return;

        int count = 0;

        Iterator i = services.entrySet().iterator();

        while (i.hasNext() && count < serializable) {
            Map.Entry           entry = (Map.Entry)i.next();
            BCSSServiceProvider bcsp  = null;

             try {
                bcsp = (BCSSServiceProvider)entry.getValue();
             } catch (ClassCastException cce) {
                continue;
             }

             if (bcsp.getServiceProvider() instanceof Serializable) {
                oos.writeObject(entry.getKey());
                oos.writeObject(bcsp);
                count++;
             }
        }

        if (count != serializable)
            throw new IOException("wrote different number of service providers than expected");
    }

    /**
     * called from BeanContextSupport readObject before it deserializes the
     * children ...
     *
     * This class will deserialize any Serializable BeanContextServiceProviders
     * serialized earlier thus making them available to the children when they
     * deserialized.
     *
     * subclasses may envelope this method to insert their own serialization
     * processing that has to occur prior to serialization of the children
     * <p>
     *  从BeanContextSupport readObject之前,它反序列化的孩子...
     * 
     *  这个类将反序列化任何可序列化的BeanContextServiceProviders序列化早期,因此使他们可用的孩子,当他们反序列化。
     * 
     *  子类可以包装该方法以插入他们自己的序列化处理,该序列化处理必须在子序列化之前发生
     * 
     */

    protected synchronized void bcsPreDeserializationHook(ObjectInputStream ois) throws IOException, ClassNotFoundException {

        serializable = ois.readInt();

        int count = serializable;

        while (count > 0) {
            services.put(ois.readObject(), ois.readObject());
            count--;
        }
    }

    /**
     * serialize the instance
     * <p>
     *  序列化实例
     * 
     */

    private synchronized void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();

        serialize(oos, (Collection)bcsListeners);
    }

    /**
     * deserialize the instance
     * <p>
     *  反序列化实例
     * 
     */

    private synchronized void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {

        ois.defaultReadObject();

        deserialize(ois, (Collection)bcsListeners);
    }


    /*
     * fields
     * <p>
     *  字段
     * 
     */

    /**
     * all accesses to the <code> protected transient HashMap services </code>
     * field should be synchronized on that object
     * <p>
     *  所有对<code> protected瞬态HashMap服务</code>字段的访问都应该在该对象上同步
     * 
     */
    protected transient HashMap                  services;

    /**
     * The number of instances of a serializable <tt>BeanContextServceProvider</tt>.
     * <p>
     *  可序列化<tt> BeanContextServceProvider </tt>的实例数。
     * 
     */
    protected transient int                      serializable = 0;


    /**
     * Delegate for the <tt>BeanContextServiceProvider</tt>.
     * <p>
     *  代表<tt> BeanContextServiceProvider </tt>。
     * 
     */
    protected transient BCSSProxyServiceProvider proxy;


    /**
     * List of <tt>BeanContextServicesListener</tt> objects.
     * <p>
     *  <tt> BeanContextServicesListener </tt>对象的列表。
     */
    protected transient ArrayList                bcsListeners;
}
