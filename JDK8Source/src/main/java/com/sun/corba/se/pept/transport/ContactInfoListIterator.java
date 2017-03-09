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
 * <code>ContactInfoIterator</code> is used to retrieve individual
 * {@link com.sun.corba.se.pept.transport.ContactInfo ContactInfo}.
 *
 * <p>
 *  <code> ContactInfoIterator </code>用于检索个别{@link com.sun.corba.se.pept.transport.ContactInfo ContactInfo}
 * 。
 * 
 * 
 * @author Harold Carr
 */
public interface ContactInfoListIterator
    extends
        Iterator
{
    /**
     * The underlying list for this iterator.
     *
     * <p>
     *  此迭代器的基本列表。
     * 
     * 
     * @return The underlying list for this iterator.
     */
    public ContactInfoList getContactInfoList();

    /**
     * Used to report information to the iterator to be used
     * in future invocations.
     *
     * <p>
     *  用于向迭代器报告信息,以便在将来的调用中使用。
     * 
     * 
     * @param contactInfo The
     * {@link com.sun.corba.se.pept.transport.ContactInfo ContactInfo}
     * obtained from this iterator which resulted in a successful invocation.
     */
    public void reportSuccess(ContactInfo contactInfo);

    /**
     * Used to report information to the iterator to be used
     * in future invocations.
     *
     * <p>
     *  用于向迭代器报告信息,以便在将来的调用中使用。
     * 
     * 
     * @param contactInfo The
     * {@link com.sun.corba.se.pept.transport.ContactInfo ContactInfo}
     * in effect when an invocation exception occurs.
     * @param exception The
     * {@link java.lang.RuntimeException RuntimeException}.
     *
     * @return Returns true if the request should be retried.
     */
    public boolean reportException(ContactInfo contactInfo,
                                   RuntimeException exception);

    /**
     * The exception to report to the presentation block.
     *
     * <p>
     *  向报告块报告的异常。
     * 
     * @return If the iterator reaches the end before the invocation
     * is successful one returns this exception (previously reported to
     * the iterator via {@link #reportException}).

     */
    public RuntimeException getFailureException();
}

// End of file.
