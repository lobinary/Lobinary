/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.awt.print;

import java.lang.annotation.Native;

/**
 * The <code>Pageable</code> implementation represents a set of
 * pages to be printed. The <code>Pageable</code> object returns
 * the total number of pages in the set as well as the
 * {@link PageFormat} and {@link Printable} for a specified page.
 * <p>
 *  <code> Pageable </code>实现表示一组要打印的页面。
 *  <code> Pageable </code>对象返回集合中的页面总数,以及指定页面的{@link PageFormat}和{@link Printable}页面总数。
 * 
 * 
 * @see java.awt.print.PageFormat
 * @see java.awt.print.Printable
 */
public interface Pageable {

    /**
     * This constant is returned from the
     * {@link #getNumberOfPages() getNumberOfPages}
     * method if a <code>Pageable</code> implementation does not know
     * the number of pages in its set.
     * <p>
     *  如果<code> Pageable </code>实现不知道其集合中的页数,则此常数从{@link #getNumberOfPages()getNumberOfPages}方法返回。
     * 
     */
    @Native int UNKNOWN_NUMBER_OF_PAGES = -1;

    /**
     * Returns the number of pages in the set.
     * To enable advanced printing features,
     * it is recommended that <code>Pageable</code>
     * implementations return the true number of pages
     * rather than the
     * UNKNOWN_NUMBER_OF_PAGES constant.
     * <p>
     *  返回集合中的页数。要启用高级打印功能,建议<code> Pageable </code>实现返回真正的页数,而不是UNKNOWN_NUMBER_OF_PAGES个常数。
     * 
     * 
     * @return the number of pages in this <code>Pageable</code>.
     */
    int getNumberOfPages();

    /**
     * Returns the <code>PageFormat</code> of the page specified by
     * <code>pageIndex</code>.
     * <p>
     *  返回<code> pageIndex </code>指定的页面的<code> PageFormat </code>。
     * 
     * 
     * @param pageIndex the zero based index of the page whose
     *            <code>PageFormat</code> is being requested
     * @return the <code>PageFormat</code> describing the size and
     *          orientation.
     * @throws IndexOutOfBoundsException if
     *          the <code>Pageable</code> does not contain the requested
     *          page.
     */
    PageFormat getPageFormat(int pageIndex)
        throws IndexOutOfBoundsException;

    /**
     * Returns the <code>Printable</code> instance responsible for
     * rendering the page specified by <code>pageIndex</code>.
     * <p>
     *  返回负责呈现<code> pageIndex </code>指定的页面的<code> Printable </code>实例。
     * 
     * @param pageIndex the zero based index of the page whose
     *            <code>Printable</code> is being requested
     * @return the <code>Printable</code> that renders the page.
     * @throws IndexOutOfBoundsException if
     *            the <code>Pageable</code> does not contain the requested
     *            page.
     */
    Printable getPrintable(int pageIndex)
        throws IndexOutOfBoundsException;
}
