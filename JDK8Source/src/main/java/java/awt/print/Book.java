/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2013, Oracle and/or its affiliates. All rights reserved.
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

import java.util.Vector;

/**
 * The <code>Book</code> class provides a representation of a document in
 * which pages may have different page formats and page painters. This
 * class uses the {@link Pageable} interface to interact with a
 * {@link PrinterJob}.
 * <p>
 *  <code> Book </code>类提供了文档的表示形式,其中页面可能具有不同的页面格式和页面画家。此类使用{@link Pageable}接口与{@link PrinterJob}交互。
 * 
 * 
 * @see Pageable
 * @see PrinterJob
*/

public class Book implements Pageable {

 /* Class Constants */

 /* Class Variables */

 /* Instance Variables */

    /**
     * The set of pages that make up the Book.
     * <p>
     *  组成书的一组页面。
     * 
     */
    private Vector mPages;

 /* Instance Methods */

    /**
     *  Creates a new, empty <code>Book</code>.
     * <p>
     *  创建新的,空的<code>图书</code>。
     * 
     */
    public Book() {
        mPages = new Vector();
    }

    /**
     * Returns the number of pages in this <code>Book</code>.
     * <p>
     *  返回此<code> Book </code>中的页数。
     * 
     * 
     * @return the number of pages this <code>Book</code> contains.
     */
    public int getNumberOfPages(){
        return mPages.size();
    }

    /**
     * Returns the {@link PageFormat} of the page specified by
     * <code>pageIndex</code>.
     * <p>
     *  返回由<code> pageIndex </code>指定的页面的{@link PageFormat}。
     * 
     * 
     * @param pageIndex the zero based index of the page whose
     *            <code>PageFormat</code> is being requested
     * @return the <code>PageFormat</code> describing the size and
     *          orientation of the page.
     * @throws IndexOutOfBoundsException if the <code>Pageable</code>
     *          does not contain the requested page
     */
    public PageFormat getPageFormat(int pageIndex)
        throws IndexOutOfBoundsException
    {
        return getPage(pageIndex).getPageFormat();
    }

    /**
     * Returns the {@link Printable} instance responsible for rendering
     * the page specified by <code>pageIndex</code>.
     * <p>
     *  返回负责呈现由<code> pageIndex </code>指定的页面的{@link Printable}实例。
     * 
     * 
     * @param pageIndex the zero based index of the page whose
     *                  <code>Printable</code> is being requested
     * @return the <code>Printable</code> that renders the page.
     * @throws IndexOutOfBoundsException if the <code>Pageable</code>
     *            does not contain the requested page
     */
    public Printable getPrintable(int pageIndex)
        throws IndexOutOfBoundsException
    {
        return getPage(pageIndex).getPrintable();
    }

    /**
     * Sets the <code>PageFormat</code> and the <code>Painter</code> for a
     * specified page number.
     * <p>
     *  为指定的页码设置<code> PageFormat </code>和<code> Painter </code>。
     * 
     * 
     * @param pageIndex the zero based index of the page whose
     *                  painter and format is altered
     * @param painter   the <code>Printable</code> instance that
     *                  renders the page
     * @param page      the size and orientation of the page
     * @throws IndexOutOfBoundsException if the specified
     *          page is not already in this <code>Book</code>
     * @throws NullPointerException if the <code>painter</code> or
     *          <code>page</code> argument is <code>null</code>
     */
    public void setPage(int pageIndex, Printable painter, PageFormat page)
        throws IndexOutOfBoundsException
    {
        if (painter == null) {
            throw new NullPointerException("painter is null");
        }

        if (page == null) {
            throw new NullPointerException("page is null");
        }

        mPages.setElementAt(new BookPage(painter, page), pageIndex);
    }

    /**
     * Appends a single page to the end of this <code>Book</code>.
     * <p>
     *  将单个页面附加到此<code>图书</code>的末尾。
     * 
     * 
     * @param painter   the <code>Printable</code> instance that
     *                  renders the page
     * @param page      the size and orientation of the page
     * @throws NullPointerException
     *          If the <code>painter</code> or <code>page</code>
     *          argument is <code>null</code>
     */
    public void append(Printable painter, PageFormat page) {
        mPages.addElement(new BookPage(painter, page));
    }

    /**
     * Appends <code>numPages</code> pages to the end of this
     * <code>Book</code>.  Each of the pages is associated with
     * <code>page</code>.
     * <p>
     *  将<code> numPages </code>页附加到此<code> Book </code>的末尾。每个页面与<code> page </code>相关联。
     * 
     * 
     * @param painter   the <code>Printable</code> instance that renders
     *                  the page
     * @param page      the size and orientation of the page
     * @param numPages  the number of pages to be added to the
     *                  this <code>Book</code>.
     * @throws NullPointerException
     *          If the <code>painter</code> or <code>page</code>
     *          argument is <code>null</code>
     */
    public void append(Printable painter, PageFormat page, int numPages) {
        BookPage bookPage = new BookPage(painter, page);
        int pageIndex = mPages.size();
        int newSize = pageIndex + numPages;

        mPages.setSize(newSize);
        for(int i = pageIndex; i < newSize; i++){
            mPages.setElementAt(bookPage, i);
        }
    }

    /**
     * Return the BookPage for the page specified by 'pageIndex'.
     * <p>
     *  返回由pageIndex指定的页面的BookPage。
     * 
     */
    private BookPage getPage(int pageIndex)
        throws ArrayIndexOutOfBoundsException
    {
        return (BookPage) mPages.elementAt(pageIndex);
    }

    /**
     * The BookPage inner class describes an individual
     * page in a Book through a PageFormat-Printable pair.
     * <p>
     *  BookPage内部类通过PageFormat-Printable对来描述书中的单个页面。
     * 
     */
    private class BookPage {
        /**
         *  The size and orientation of the page.
         * <p>
         *  页面的大小和方向。
         * 
         */
        private PageFormat mFormat;

        /**
         * The instance that will draw the page.
         * <p>
         *  将绘制页面的实例。
         * 
         */
        private Printable mPainter;

        /**
         * A new instance where 'format' describes the page's
         * size and orientation and 'painter' is the instance
         * that will draw the page's graphics.
         * <p>
         *  一个新的实例,其中"format"描述页面的大小和方向,"painter"是将绘制页面图形的实例。
         * 
         * 
         * @throws  NullPointerException
         *          If the <code>painter</code> or <code>format</code>
         *          argument is <code>null</code>
         */
        BookPage(Printable painter, PageFormat format) {

            if (painter == null || format == null) {
                throw new NullPointerException();
            }

            mFormat = format;
            mPainter = painter;
        }

        /**
         * Return the instance that paints the
         * page.
         * <p>
         *  返回描绘页面的实例。
         * 
         */
        Printable getPrintable() {
            return mPainter;
        }

        /**
         * Return the format of the page.
         * <p>
         *  返回页面的格式。
         */
        PageFormat getPageFormat() {
            return mFormat;
        }
    }
}
