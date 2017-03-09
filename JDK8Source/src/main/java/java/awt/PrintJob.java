/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, Oracle and/or its affiliates. All rights reserved.
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
 * An abstract class which initiates and executes a print job.
 * It provides access to a print graphics object which renders
 * to an appropriate print device.
 *
 * <p>
 *  启动并执行打印作业的抽象类。它提供对打印图形对象的访问,该对象呈现给适当的打印设备。
 * 
 * 
 * @see Toolkit#getPrintJob
 *
 * @author      Amy Fowler
 */
public abstract class PrintJob {

    /**
     * Gets a Graphics object that will draw to the next page.
     * The page is sent to the printer when the graphics
     * object is disposed.  This graphics object will also implement
     * the PrintGraphics interface.
     * <p>
     *  获取将绘制到下一页的Graphics对象。当布置图形对象时,页面被发送到打印机。这个图形对象也将实现PrintGraphics接口。
     * 
     * 
     * @see PrintGraphics
     */
    public abstract Graphics getGraphics();

    /**
     * Returns the dimensions of the page in pixels.
     * The resolution of the page is chosen so that it
     * is similar to the screen resolution.
     * <p>
     *  返回网页的尺寸(以像素为单位)。选择页面的分辨率以使其类似于屏幕分辨率。
     * 
     */
    public abstract Dimension getPageDimension();

    /**
     * Returns the resolution of the page in pixels per inch.
     * Note that this doesn't have to correspond to the physical
     * resolution of the printer.
     * <p>
     *  以英寸为单位返回页面的分辨率。请注意,这不必对应于打印机的物理分辨率。
     * 
     */
    public abstract int getPageResolution();

    /**
     * Returns true if the last page will be printed first.
     * <p>
     *  如果最后一个页面将首先打印,则返回true。
     * 
     */
    public abstract boolean lastPageFirst();

    /**
     * Ends the print job and does any necessary cleanup.
     * <p>
     *  结束打印作业,并进行任何必要的清理。
     * 
     */
    public abstract void end();

    /**
     * Ends this print job once it is no longer referenced.
     * <p>
     *  一旦不再引用此打印作业,则结束此打印作业。
     * 
     * @see #end
     */
    public void finalize() {
        end();
    }

}
