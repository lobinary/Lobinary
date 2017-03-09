/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2001, 2002, Oracle and/or its affiliates. All rights reserved.
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

import java.util.Iterator ;
import org.omg.CORBA_2_3.portable.OutputStream ;

/** ObjectAdapterIdNumber is used to represent pre-JDK 1.4 POA adapter
 * IDs.  The POA ID was simply represented as a single integer, which was
 * mapped to the actual POA instance.  Here, we just represent these
 * internally as arrays of the form { "OldRootPOA", "<number>" },
 * and provide an extra method to get the number back.
 * <p>
 *  ID。 POA ID简单地表示为一个整数,它被映射到实际的POA实例。这里,我们只是将这些内部表示为{"OldRootPOA","<number>"}形式的数组,并提供一个额外的方法来返回数字。
 */
public class ObjectAdapterIdNumber extends ObjectAdapterIdArray {
    private int poaid ;

    public ObjectAdapterIdNumber( int poaid )
    {
        super( "OldRootPOA", Integer.toString( poaid ) ) ;
        this.poaid = poaid ;
    }

    public int getOldPOAId()
    {
        return poaid ;
    }
}
