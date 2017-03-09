/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2000, Oracle and/or its affiliates. All rights reserved.
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

package javax.rmi.CORBA;

/**
 * This class is used to marshal java.lang.Class objects over IIOP.
 * <p>
 *  这个类用于通过IIOP封送java.lang.Class对象。
 * 
 */
public class ClassDesc implements java.io.Serializable {

    /**
    /* <p>
    /* 
     * @serial The class's RepositoryId.
     */
    private String repid;

    /**
    /* <p>
    /* 
     * @serial A space-separated list of codebase URLs.
     */
    private String codebase;
}
