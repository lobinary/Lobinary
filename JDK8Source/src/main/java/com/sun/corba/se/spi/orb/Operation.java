/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2002, Oracle and/or its affiliates. All rights reserved.
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
package com.sun.corba.se.spi.orb ;

/** A generic class representing a function that takes a value and returns
 * a value.  This is a building block for property parsing.
 * <p>
 *  一个值。这是属性解析的构建块。
 * 
 */
public interface Operation{
    /** Apply some function to a value and return the result.
    /* <p>
    */
    Object operate( Object value ) ;
}
