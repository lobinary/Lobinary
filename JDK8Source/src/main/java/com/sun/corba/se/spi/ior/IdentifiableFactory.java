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

package com.sun.corba.se.spi.ior ;

import org.omg.CORBA_2_3.portable.InputStream ;

import com.sun.corba.se.spi.ior.Identifiable ;

/** Factory interface for creating Identifiables.
/* <p>
 */
public interface IdentifiableFactory {
    /** Return the id of this factory, which is the id of the result
     * of any create call.
     * <p>
     *  的任何创建调用。
     * 
     */
    public int getId() ;

    /** Construct the appropriate Identifiable object with the
     * given id from the InputStream is.
     * <p>
     *  从InputStream给定的id是。
     */
    public Identifiable create( InputStream in ) ;
}
