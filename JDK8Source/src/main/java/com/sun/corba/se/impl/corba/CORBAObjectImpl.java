/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2002, Oracle and/or its affiliates. All rights reserved.
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
 */

package com.sun.corba.se.impl.corba;

//
// Bare implementation of CORBA Object.
//
public class CORBAObjectImpl extends org.omg.CORBA_2_3.portable.ObjectImpl {
    public String[] _ids() {
        String[] typeids = new String[1];
        typeids[0] = "IDL:omg.org/CORBA/Object:1.0";
        return typeids;
    }
}
