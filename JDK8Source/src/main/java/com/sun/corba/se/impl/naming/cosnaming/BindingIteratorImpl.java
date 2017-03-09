/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 2003, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.corba.se.impl.naming.cosnaming;

// Import general CORBA classes
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;

// Import org.omg.CosNaming classes
import org.omg.CosNaming.Binding;
import org.omg.CosNaming.BindingType;
import org.omg.CosNaming.BindingHolder;
import org.omg.CosNaming.BindingListHolder;
import org.omg.CosNaming.BindingIteratorHolder;
import org.omg.CosNaming.BindingIteratorPOA;
import org.omg.CORBA.BAD_PARAM;

/**
 * Class BindingIteratorImpl implements the org.omg.CosNaming::BindingIterator
 * interface, but does not implement the method to retrieve the next
 * binding in the NamingContext for which it was created. This is left
 * to a subclass, which is why this class is abstract; BindingIteratorImpl
 * provides an implementation of the interface operations on top of two
 * subclass methods, allowing multiple implementations of iterators that
 * differ in storage and access to the contents of a NamingContext
 * implementation.
 * <p>
 * The operation next_one() is implemented by the subclass, whereas
 * next_n() is implemented on top of the next_one() implementation.
 * Destroy must also be implemented by the subclass.
 * <p>
 * A subclass must implement NextOne() and Destroy(); these
 * methods are invoked from synchronized methods and need therefore
 * not be synchronized themselves.
 * <p>
 *  BindingIteratorImpl类实现了org.omg.CosNaming :: BindingIterator接口,但没有实现该方法来检索为其创建的NamingContext中的下一个绑定。
 * 这是留给一个子类,这就是为什么这个类是抽象的; BindingIteratorImpl提供了在两个子类方法之上的接口操作的实现,允许多个实现在存储和访问NamingContext实现的内容不同的迭代器。
 * <p>
 *  操作next_one()由子类实现,而next_n()在next_one()实现的顶部实现。销毁也必须由子类实现。
 * <p>
 *  子类必须实现NextOne()和Destroy();这些方法从同步方法调用,因此不需要自己同步。
 * 
 */
public abstract class BindingIteratorImpl extends BindingIteratorPOA
{
    protected ORB orb ;

    /**
     * Create a binding iterator servant.
     * runs the super constructor.
     * <p>
     *  创建绑定迭代器服务方。运行超级构造函数。
     * 
     * 
     * @param orb an ORB object.
     * @exception java.lang.Exception a Java exception.
     */
    public BindingIteratorImpl(ORB orb)
        throws java.lang.Exception
    {
        super();
        this.orb = orb ;
    }

    /**
     * Return the next binding. It also returns true or false, indicating
     * whether there were more bindings.
     * <p>
     *  返回下一个绑定。它也返回true或false,指示是否有更多的绑定。
     * 
     * 
     * @param b The Binding as an out parameter.
     * @return true if there were more bindings.
     * @exception org.omg.CORBA.SystemException One of a fixed set of CORBA
     * system exceptions.
     * @see NextOne
     */
    public synchronized boolean next_one(org.omg.CosNaming.BindingHolder b)
    {
        // NextOne actually returns the next one
        return NextOne(b);
    }

    /**
     * Return the next n bindings. It also returns true or false, indicating
     * whether there were more bindings.
     * <p>
     *  返回下n个绑定。它也返回true或false,指示是否有更多的绑定。
     * 
     * 
     * @param how_many The number of requested bindings in the BindingList.
     * @param bl The BindingList as an out parameter.
     * @return true if there were more bindings.
     * @exception org.omg.CORBA.SystemException One of a fixed set of CORBA
     * system exceptions.
     * @see NextOne
     */
    public synchronized boolean next_n(int how_many,
        org.omg.CosNaming.BindingListHolder blh)
    {
        if( how_many == 0 ) {
            throw new BAD_PARAM( " 'how_many' parameter is set to 0 which is" +
            " invalid" );
        }
        return list( how_many, blh );
    }

    /**
     * lists next n bindings. It returns true or false, indicating
     * whether there were more bindings. This method has the package private
     * scope, It will be called from NamingContext.list() operation or
     * this.next_n().
     * <p>
     *  列出下n个绑定。它返回true或false,指示是否有更多的绑定。此方法具有包的私有作用域,它将从NamingContext.list()操作或this.next_n()调用。
     * 
     * 
     * @param how_many The number of requested bindings in the BindingList.
     * @param bl The BindingList as an out parameter.
     * @return true if there were more bindings.
     */
    public boolean list( int how_many, org.omg.CosNaming.BindingListHolder blh)
    {
        // Take the smallest of what's left and what's being asked for
        int numberToGet = Math.min(RemainingElements(),how_many);

        // Create a resulting BindingList
        Binding[] bl = new Binding[numberToGet];
        BindingHolder bh = new BindingHolder();
        int i = 0;
        // Keep iterating as long as there are entries
        while (i < numberToGet && this.NextOne(bh) == true) {
            bl[i] = bh.value;
            i++;
        }
        // Found any at all?
        if (i == 0) {
            // No
            blh.value = new Binding[0];
            return false;
        }

        // Set into holder
        blh.value = bl;

        return true;
    }




    /**
     * Destroy this BindingIterator object. The object corresponding to this
     * object reference is destroyed.
     * <p>
     * 销毁此BindingIterator对象。与此对象引用相对应的对象被销毁。
     * 
     * 
     * @exception org.omg.CORBA.SystemException One of a fixed set of CORBA
     * system exceptions.
     * @see Destroy
     */
    public synchronized void destroy()
    {
        // Destroy actually destroys
        this.Destroy();
    }

    /**
     * Abstract method for returning the next binding in the NamingContext
     * for which this BindingIterator was created.
     * <p>
     *  用于返回创建了此BindingIterator的NamingContext中的下一个绑定的抽象方法。
     * 
     * 
     * @param b The Binding as an out parameter.
     * @return true if there were more bindings.
     * @exception org.omg.CORBA.SystemException One of a fixed set of CORBA
     * system exceptions.
     */
    protected abstract boolean NextOne(org.omg.CosNaming.BindingHolder b);

    /**
     * Abstract method for destroying this BindingIterator.
     * <p>
     *  破坏这个BindingIterator的抽象方法。
     * 
     * 
     * @exception org.omg.CORBA.SystemException One of a fixed set of CORBA
     * system exceptions.
     */
    protected abstract void Destroy();

    /**
     * Abstract method for returning the remaining number of elements.
     * <p>
     *  用于返回剩余数量的元素的抽象方法。
     * 
     * @return the remaining number of elements in the iterator.
     */
    protected abstract int RemainingElements();
}
