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

/**
 * Delegate class provides the ORB vendor specific implementation
 * of CORBA object.  It extends org.omg.CORBA.portable.Delegate and
 * provides new methods that were defined by CORBA 2.3.
 *
 * <p>
 *  委托类提供ORB供应商特定的CORBA对象实现。它扩展org.omg.CORBA.portable.Delegate并提供由CORBA 2.3定义的新方法。
 * 
 * 
 * @see org.omg.CORBA.portable.Delegate
 * @author  OMG
 * @since   JDK1.2
 */

public abstract class Delegate extends org.omg.CORBA.portable.Delegate {

    /** Returns the codebase for object reference provided.
    /* <p>
    /* 
     * @param self the object reference whose codebase needs to be returned.
     * @return the codebase as a space delimited list of url strings or
     * null if none.
     */
    public java.lang.String get_codebase(org.omg.CORBA.Object self) {
        return null;
    }
}
