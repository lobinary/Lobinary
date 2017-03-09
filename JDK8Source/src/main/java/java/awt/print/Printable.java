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

import java.awt.Graphics;


/**
 * The <code>Printable</code> interface is implemented
 * by the <code>print</code> methods of the current
 * page painter, which is called by the printing
 * system to render a page.  When building a
 * {@link Pageable}, pairs of {@link PageFormat}
 * instances and instances that implement
 * this interface are used to describe each page. The
 * instance implementing <code>Printable</code> is called to
 * print the page's graphics.
 * <p>
 * A <code>Printable(..)</code> may be set on a <code>PrinterJob</code>.
 * When the client subsequently initiates printing by calling
 * <code>PrinterJob.print(..)</code> control
 * <p>
 * is handed to the printing system until all pages have been printed.
 * It does this by calling <code>Printable.print(..)</code> until
 * all pages in the document have been printed.
 * In using the <code>Printable</code> interface the printing
 * commits to image the contents of a page whenever
 * requested by the printing system.
 * <p>
 * The parameters to <code>Printable.print(..)</code> include a
 * <code>PageFormat</code> which describes the printable area of
 * the page, needed for calculating the contents that will fit the
 * page, and the page index, which specifies the zero-based print
 * stream index of the requested page.
 * <p>
 * For correct printing behaviour, the following points should be
 * observed:
 * <ul>
 * <li> The printing system may request a page index more than once.
 * On each occasion equal PageFormat parameters will be supplied.
 *
 * <li>The printing system will call <code>Printable.print(..)</code>
 * with page indexes which increase monotonically, although as noted above,
 * the <code>Printable</code> should expect multiple calls for a page index
 * and that page indexes may be skipped, when page ranges are specified
 * by the client, or by a user through a print dialog.
 *
 * <li>If multiple collated copies of a document are requested, and the
 * printer cannot natively support this, then the document may be imaged
 * multiple times. Printing will start each copy from the lowest print
 * stream page index page.
 *
 * <li>With the exception of re-imaging an entire document for multiple
 * collated copies, the increasing page index order means that when
 * page N is requested if a client needs to calculate page break position,
 * it may safely discard any state related to pages &lt; N, and make current
 * that for page N. "State" usually is just the calculated position in the
 * document that corresponds to the start of the page.
 *
 * <li>When called by the printing system the <code>Printable</code> must
 * inspect and honour the supplied PageFormat parameter as well as the
 * page index.  The format of the page to be drawn is specified by the
 * supplied PageFormat. The size, orientation and imageable area of the page
 * is therefore already determined and rendering must be within this
 * imageable area.
 * This is key to correct printing behaviour, and it has the
 * implication that the client has the responsibility of tracking
 * what content belongs on the specified page.
 *
 * <li>When the <code>Printable</code> is obtained from a client-supplied
 * <code>Pageable</code> then the client may provide different PageFormats
 * for each page index. Calculations of page breaks must account for this.
 * </ul>
 * <p>
 * <p>
 *  <code> Printable </code>接口由当前页面绘图器的<code> print </code>方法实现,该方法由打印系统调用以呈现页面。
 * 当构建{@link Pageable}时,使用成对的{@link PageFormat}实例和实现此接口的实例来描述每个页面。
 * 调用实现<code> Printable </code>的实例来打印页面的图形。
 * <p>
 *  可以在<code> PrinterJob </code>上设置<code> Printable(..)</code>。
 * 当客户机随后通过调用<code> PrinterJob.print(..)</code>控件启动打印时。
 * <p>
 *  被交给打印系统,直到所有页面都打印完毕。它通过调用<code> Printable.print(..)</code>来执行此操作,直到文档中的所有页面都已打印。
 * 在使用<code>可打印</code>接口时,打印提交每当打印系统请求时就对页面的内容进行成像。
 * <p>
 *  <code> Printable.print(..)</code>的参数包括<code> PageFormat </code>,其描述了计算适合页面的内容所需的页面的可打印区域,以及页面索引,其指定所
 * 请求的页的基于零的打印流索引。
 * <p>
 *  对于正确的打印行为,应遵守以下几点：
 * <ul>
 * <li>打印系统可以多次请求页索引。每次都会提供相等的PageFormat参数。
 * 
 *  <li>打印系统将使用单调增加的页索引调用<code> Printable.print(..)</code>,虽然如上所述,<code> Printable </code>应该预期多次调用页面索引,并
 * 且当页面范围由客户端指定或者由用户通过打印对话框指定时,页面索引可以被跳过。
 * 
 *  <li>如果请求了文档的多个整理的副本,并且打印机无法原生地支持此操作,则文档可能会被多次成像。打印将从最低打印流页索引页开始每个副本。
 * 
 *  <li>除了为多个整理的副本重新映像整个文档之外,增加的页索引顺序意味着,如果客户端需要计算分页符位置,则请求页N时,它可以安全地丢弃与页&lt; ; N,并产生页N的电流。
 * 
 * @see java.awt.print.Pageable
 * @see java.awt.print.PageFormat
 * @see java.awt.print.PrinterJob
 */
public interface Printable {

    /**
     * Returned from {@link #print(Graphics, PageFormat, int)}
     * to signify that the requested page was rendered.
     * <p>
     * "状态"通常只是文档中对应于页面开始的计算位置。
     * 
     * <li>当打印系统调用时,<code> Printable </code>必须检查并遵守提供的PageFormat参数以及页面索引。要绘制的页面的格式由提供的PageFormat指定。
     * 因此,页面的大小,方向和可成像区域已经确定,并且渲染必须在该可成像区域内。这是正确的打印行为的关键,并且它暗示,客户端有责任跟踪什么内容属于指定的页面。
     * 
     *  <li>当从客户端提供的<code> Pageable </code>获取<code>可打印</code>时,客户端可以为每个页面索引提供不同的PageFormats。分页的计算必须考虑到这一点。
     * </ul>
     * <p>
     */
    int PAGE_EXISTS = 0;

    /**
     * Returned from <code>print</code> to signify that the
     * <code>pageIndex</code> is too large and that the requested page
     * does not exist.
     * <p>
     *  从{@link #print(Graphics,PageFormat,int)}返回,表示请求的网页已呈现。
     * 
     */
    int NO_SUCH_PAGE = 1;

    /**
     * Prints the page at the specified index into the specified
     * {@link Graphics} context in the specified
     * format.  A <code>PrinterJob</code> calls the
     * <code>Printable</code> interface to request that a page be
     * rendered into the context specified by
     * <code>graphics</code>.  The format of the page to be drawn is
     * specified by <code>pageFormat</code>.  The zero based index
     * of the requested page is specified by <code>pageIndex</code>.
     * If the requested page does not exist then this method returns
     * NO_SUCH_PAGE; otherwise PAGE_EXISTS is returned.
     * The <code>Graphics</code> class or subclass implements the
     * {@link PrinterGraphics} interface to provide additional
     * information.  If the <code>Printable</code> object
     * aborts the print job then it throws a {@link PrinterException}.
     * <p>
     *  从<code> print </code>返回,表示<code> pageIndex </code>太大,并且请求的网页不存在。
     * 
     * 
     * @param graphics the context into which the page is drawn
     * @param pageFormat the size and orientation of the page being drawn
     * @param pageIndex the zero based index of the page to be drawn
     * @return PAGE_EXISTS if the page is rendered successfully
     *         or NO_SUCH_PAGE if <code>pageIndex</code> specifies a
     *         non-existent page.
     * @exception java.awt.print.PrinterException
     *         thrown when the print job is terminated.
     */
    int print(Graphics graphics, PageFormat pageFormat, int pageIndex)
                 throws PrinterException;

}
