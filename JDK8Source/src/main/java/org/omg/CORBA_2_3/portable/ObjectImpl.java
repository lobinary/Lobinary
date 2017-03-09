/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, Oracle and/or its affiliates. All rights reserved.
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
/*
 * Licensed Materials - Property of IBM
 * RMI-IIOP v1.0
 * Copyright IBM Corp. 1998 1999  All Rights Reserved
 *
 * <p>
 *  许可的材料 -  IBM RMI-IIOP v1.0的属性版权所有IBM Corp. 1998 1999保留所有权利
 * 
 */

package org.omg.CORBA_2_3.portable;

import org.omg.CORBA_2_3.portable.Delegate;

/**
 * ObjectImpl class is the base class for all stubs.  It provides the
 * basic delegation mechanism.  It extends org.omg.CORBA.portable.ObjectImpl
 * and provides new methods defined by CORBA 2.3.
 *
 * <p>
 *  ObjectImpl类是所有存根的基类。它提供了基本的委托机制。它扩展org.omg.CORBA.portable.ObjectImpl并提供由CORBA 2.3定义的新方法。
 * 
 * 
 * @see org.omg.CORBA.portable.ObjectImpl
 * @author  OMG
 * @since   JDK1.2
 */


public abstract class ObjectImpl extends org.omg.CORBA.portable.ObjectImpl {

    /** Returns the codebase for this object reference.
    /* <p>
    /* 
     * @return the codebase as a space delimited list of url strings or
     * null if none.
     */
    public java.lang.String _get_codebase() {
        org.omg.CORBA.portable.Delegate delegate = _get_delegate();
        if (delegate instanceof Delegate)
            return ((Delegate) delegate).get_codebase(this);
        return null;
    }
}
