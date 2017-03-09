/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2003, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.corba.se.impl.ior ;

import java.util.Collection ;
import java.util.List ;
import java.util.AbstractList ;
import java.util.ListIterator ;
import java.util.Iterator ;

import com.sun.corba.se.spi.ior.MakeImmutable ;

/** Simple class that delegates all List operations to
* another list.  It also can be frozen, which means that
* a number of operations can be performed on the list,
* and then the list can be made immutable, so that no
* further changes are possible.  A FreezableList is frozen
* using the makeImmutable method.
* <p>
*  另一个列表。它也可以被冻结,这意味着可以对列表执行多个操作,然后可以使列表不可变,使得不可能进行进一步的改变。 FreezableList使用makeImmutable方法冻结。
*/
public class FreezableList extends AbstractList {
    private List delegate = null ;
    private boolean immutable = false ;

    public boolean equals( Object obj )
    {
        if (obj == null)
            return false ;

        if (!(obj instanceof FreezableList))
            return false ;

        FreezableList other = (FreezableList)obj ;

        return delegate.equals( other.delegate ) &&
            (immutable == other.immutable) ;
    }

    public int hashCode()
    {
        return delegate.hashCode() ;
    }

    public FreezableList( List delegate, boolean immutable  )
    {
        this.delegate = delegate ;
        this.immutable = immutable ;
    }

    public FreezableList( List delegate )
    {
        this( delegate, false ) ;
    }

    public void makeImmutable()
    {
        immutable = true ;
    }

    public boolean isImmutable()
    {
        return immutable ;
    }

    public void makeElementsImmutable()
    {
        Iterator iter = iterator() ;
        while (iter.hasNext()) {
            Object obj = iter.next() ;
            if (obj instanceof MakeImmutable) {
                MakeImmutable element = (MakeImmutable)obj ;
                element.makeImmutable() ;
            }
        }
    }

    // Methods overridden from AbstractList

    public int size()
    {
        return delegate.size() ;
    }

    public Object get(int index)
    {
        return delegate.get(index) ;
    }

    public Object set(int index, Object element)
    {
        if (immutable)
            throw new UnsupportedOperationException() ;

        return delegate.set(index, element) ;
    }

    public void add(int index, Object element)
    {
        if (immutable)
            throw new UnsupportedOperationException() ;

        delegate.add(index, element) ;
    }

    public Object remove(int index)
    {
        if (immutable)
            throw new UnsupportedOperationException() ;

        return delegate.remove(index) ;
    }

    // We also override subList so that the result is a FreezableList.
    public List subList(int fromIndex, int toIndex)
    {
        List list = delegate.subList(fromIndex, toIndex) ;
        List result = new FreezableList( list, immutable ) ;
        return result ;
    }
}
