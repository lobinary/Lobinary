/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 1999, Oracle and/or its affiliates. All rights reserved.
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

/**
 * The <code>PrinterGraphics</code> interface is implemented by
 * {@link java.awt.Graphics} objects that are passed to
 * {@link Printable} objects to render a page. It allows an
 * application to find the {@link PrinterJob} object that is
 * controlling the printing.
 * <p>
 *  <code> PrinterGraphics </code>接口由传递给{@link Printable}对象以呈现页面的{@link java.awt.Graphics}对象实现。
 * 它允许应用程序找到控制打印的{@link PrinterJob}对象。
 * 
 */

public interface PrinterGraphics {

    /**
     * Returns the <code>PrinterJob</code> that is controlling the
     * current rendering request.
     * <p>
     * 
     * @return the <code>PrinterJob</code> controlling the current
     * rendering request.
     * @see java.awt.print.Printable
     */
    PrinterJob getPrinterJob();

}
