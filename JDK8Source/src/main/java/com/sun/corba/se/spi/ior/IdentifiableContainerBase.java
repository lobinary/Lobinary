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

package com.sun.corba.se.spi.ior;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import com.sun.corba.se.impl.ior.FreezableList ;

import com.sun.corba.se.spi.ior.TaggedComponent ;
import com.sun.corba.se.spi.ior.Identifiable ;

/** Convenience class for defining objects that contain lists of Identifiables.
 * Mainly implements iteratorById.  Also note that the constructor creates the
 * list, which here is always an ArrayList, as this is much more efficient overall
 * for short lists.
 * <p>
 *  主要实现iteratorById。还要注意,构造函数创建列表,这里总是一个ArrayList,因为这对于短列表总体上更有效。
 * 
 * 
 * @author  Ken Cavanaugh
 */
public class IdentifiableContainerBase extends FreezableList
{
    /** Create this class with an empty list of identifiables.
     * The current implementation uses an ArrayList.
     * <p>
     *  当前实现使用ArrayList。
     * 
     */
    public IdentifiableContainerBase()
    {
        super( new ArrayList() ) ;
    }

    /** Return an iterator which iterates over all contained Identifiables
     * with type given by id.
     * <p>
     *  类型由id给定。
     */
    public Iterator iteratorById( final int id)
    {
        return new Iterator() {
            Iterator iter = IdentifiableContainerBase.this.iterator() ;
            Object current = advance() ;

            private Object advance()
            {
                while (iter.hasNext()) {
                    Identifiable ide = (Identifiable)(iter.next()) ;
                    if (ide.getId() == id)
                        return ide ;
                }

                return null ;
            }

            public boolean hasNext()
            {
                return current != null ;
            }

            public Object next()
            {
                Object result = current ;
                current = advance() ;
                return result ;
            }

            public void remove()
            {
                iter.remove() ;
            }
        } ;
    }
}
