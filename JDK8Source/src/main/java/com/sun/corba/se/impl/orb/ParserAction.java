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

package com.sun.corba.se.impl.orb ;

import java.util.Properties ;

public interface ParserAction {
    /** Return the property name or prefix for which this action
     * is applied.
     * <p>
     *  被申请;被应用。
     * 
     */
    String getPropertyName() ;

    /** Return whether this action is for an exact match or a prefix
     * match (true).
     * <p>
     *  match(true)。
     * 
     */
    boolean isPrefix() ;

    /** Return the field name in an object that is set with the result
    /* <p>
     */
    String getFieldName() ;

    /** Apply this action to props and return the result.
    /* <p>
    */
    Object apply( Properties props ) ;
}
