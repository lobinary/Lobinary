/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 1997, Oracle and/or its affiliates. All rights reserved.
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

package java.awt;

/**
 * An abstract class which provides a print graphics context for a page.
 *
 * <p>
 *  一个为页面提供打印图形上下文的抽象类。
 * 
 * 
 * @author      Amy Fowler
 */
public interface PrintGraphics {

    /**
     * Returns the PrintJob object from which this PrintGraphics
     * object originated.
     * <p>
     *  返回此PrintGraphics对象所源自的PrintJob对象。
     */
    public PrintJob getPrintJob();

}
