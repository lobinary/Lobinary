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

package com.sun.corba.se.pept.transport;

import java.util.Iterator;

/**
 * <p> <code>ContactInfoList</code> contains one or more
 * {@link com.sun.corba.se.pept.transport.ContactInfo ContactInfo}.
 *
 * <p>
 *  <p> <code> ContactInfoList </code>包含一个或多个{@link com.sun.corba.se.pept.transport.ContactInfo ContactInfo}
 * 。
 * 
 * 
 * @author Harold Carr
 */
public interface ContactInfoList
{
    /**
     * Used to get a
     * {@link com.sun.corba.se.pept.transport.ContactInfoListIterator
     * ContactInfoListIterator} to retrieve individual
     * {@link com.sun.corba.se.pept.transport.ContactInfo ContactInfo}
     * from the list.
     *
     * <p>
     *  用于获取{@link com.sun.corba.se.pept.transport.ContactInfoListIterator ContactInfoListIterator}以从列表中检索单个
     * {@link com.sun.corba.se.pept.transport.ContactInfo ContactInfo}。
     * 
     * @return A
     * {@link com.sun.corba.se.pept.transport.ContactInfoListIterator
     * ContactInfoListIterator}.
     */
    public Iterator iterator();
}

// End of file.
