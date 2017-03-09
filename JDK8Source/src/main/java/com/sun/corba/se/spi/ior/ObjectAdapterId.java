/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2002, 2003, Oracle and/or its affiliates. All rights reserved.
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

import java.util.Iterator ;

/** This is the object adapter ID for an object adapter.
* Typically this is the path of strings starting from the
* Root POA to get to a POA, but other implementations are possible.
* <p>
*  通常这是从根POA开始到达POA的字符串的路径,但是其他实现也是可能的。
* 
*/
public interface ObjectAdapterId extends Writeable {
    /** Return the number of elements in the adapter ID.
    /* <p>
    */
    int getNumLevels() ;

    /** Return an iterator that iterates over the components
    * of this adapter ID.  Each element is returned as a String.
    * <p>
    *  的此适配器ID。每个元素作为String返回。
    * 
    */
    Iterator iterator() ;

    /** Get the adapter name simply as an array of strings.
    /* <p>
    */
    String[] getAdapterName() ;
}
