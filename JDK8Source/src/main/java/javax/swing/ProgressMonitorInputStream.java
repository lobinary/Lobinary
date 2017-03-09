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



package javax.swing;



import java.io.*;
import java.awt.Component;



/**
 * Monitors the progress of reading from some InputStream. This ProgressMonitor
 * is normally invoked in roughly this form:
 * <pre>
 * InputStream in = new BufferedInputStream(
 *                          new ProgressMonitorInputStream(
 *                                  parentComponent,
 *                                  "Reading " + fileName,
 *                                  new FileInputStream(fileName)));
 * </pre><p>
 * This creates a progress monitor to monitor the progress of reading
 * the input stream.  If it's taking a while, a ProgressDialog will
 * be popped up to inform the user.  If the user hits the Cancel button
 * an InterruptedIOException will be thrown on the next read.
 * All the right cleanup is done when the stream is closed.
 *
 *
 * <p>
 *
 * For further documentation and examples see
 * <a href="https://docs.oracle.com/javase/tutorial/uiswing/components/progress.html">How to Monitor Progress</a>,
 * a section in <em>The Java Tutorial.</em>
 *
 * <p>
 *  监视从某个InputStream读取的进度。这个ProgressMonitor通常以这种形式调用：
 * <pre>
 *  InputStream in = new BufferedInputStream(new ProgressMonitorInputStream(parentComponent,"Reading"+ f
 * ileName,new FileInputStream(fileName))); </pre> <p>这会创建一个进度监视器来监视读取输入流的进度。
 * 如果它需要一段时间,一个ProgressDialog会弹出来通知用户。如果用户点击取消按钮,下一次读取将抛出InterruptedIOException。当流关闭时,所有正确的清除都会完成。
 * 
 * <p>
 * 
 *  有关其他文档和示例,请参阅<a href="https://docs.oracle.com/javase/tutorial/uiswing/components/progress.html">如何监控
 * 进度</a>,<em>中的一节。
 *  Java教程。</em>。
 * 
 * 
 * @see ProgressMonitor
 * @see JOptionPane
 * @author James Gosling
 */
public class ProgressMonitorInputStream extends FilterInputStream
{
    private ProgressMonitor monitor;
    private int             nread = 0;
    private int             size = 0;


    /**
     * Constructs an object to monitor the progress of an input stream.
     *
     * <p>
     *  构造一个对象以监视输入流的进度。
     * 
     * 
     * @param message Descriptive text to be placed in the dialog box
     *                if one is popped up.
     * @param parentComponent The component triggering the operation
     *                        being monitored.
     * @param in The input stream to be monitored.
     */
    public ProgressMonitorInputStream(Component parentComponent,
                                      Object message,
                                      InputStream in) {
        super(in);
        try {
            size = in.available();
        }
        catch(IOException ioe) {
            size = 0;
        }
        monitor = new ProgressMonitor(parentComponent, message, null, 0, size);
    }


    /**
     * Get the ProgressMonitor object being used by this stream. Normally
     * this isn't needed unless you want to do something like change the
     * descriptive text partway through reading the file.
     * <p>
     *  获取此流使用的ProgressMonitor对象。通常这不是必需的,除非你想做一些事情,例如通过阅读文件来改变描述性文本。
     * 
     * 
     * @return the ProgressMonitor object used by this object
     */
    public ProgressMonitor getProgressMonitor() {
        return monitor;
    }


    /**
     * Overrides <code>FilterInputStream.read</code>
     * to update the progress monitor after the read.
     * <p>
     *  覆盖<code> FilterInputStream.read </code>以在读取后更新进度监视器。
     * 
     */
    public int read() throws IOException {
        int c = in.read();
        if (c >= 0) monitor.setProgress(++nread);
        if (monitor.isCanceled()) {
            InterruptedIOException exc =
                                    new InterruptedIOException("progress");
            exc.bytesTransferred = nread;
            throw exc;
        }
        return c;
    }


    /**
     * Overrides <code>FilterInputStream.read</code>
     * to update the progress monitor after the read.
     * <p>
     *  覆盖<code> FilterInputStream.read </code>以在读取后更新进度监视器。
     * 
     */
    public int read(byte b[]) throws IOException {
        int nr = in.read(b);
        if (nr > 0) monitor.setProgress(nread += nr);
        if (monitor.isCanceled()) {
            InterruptedIOException exc =
                                    new InterruptedIOException("progress");
            exc.bytesTransferred = nread;
            throw exc;
        }
        return nr;
    }


    /**
     * Overrides <code>FilterInputStream.read</code>
     * to update the progress monitor after the read.
     * <p>
     *  覆盖<code> FilterInputStream.read </code>以在读取后更新进度监视器。
     * 
     */
    public int read(byte b[],
                    int off,
                    int len) throws IOException {
        int nr = in.read(b, off, len);
        if (nr > 0) monitor.setProgress(nread += nr);
        if (monitor.isCanceled()) {
            InterruptedIOException exc =
                                    new InterruptedIOException("progress");
            exc.bytesTransferred = nread;
            throw exc;
        }
        return nr;
    }


    /**
     * Overrides <code>FilterInputStream.skip</code>
     * to update the progress monitor after the skip.
     * <p>
     * 覆盖<code> FilterInputStream.skip </code>以在跳过后更新进度监视器。
     * 
     */
    public long skip(long n) throws IOException {
        long nr = in.skip(n);
        if (nr > 0) monitor.setProgress(nread += nr);
        return nr;
    }


    /**
     * Overrides <code>FilterInputStream.close</code>
     * to close the progress monitor as well as the stream.
     * <p>
     *  覆盖<code> FilterInputStream.close </code>以关闭进度监视器以及流。
     * 
     */
    public void close() throws IOException {
        in.close();
        monitor.close();
    }


    /**
     * Overrides <code>FilterInputStream.reset</code>
     * to reset the progress monitor as well as the stream.
     * <p>
     *  覆盖<code> FilterInputStream.reset </code>以重置进度监视器以及流。
     */
    public synchronized void reset() throws IOException {
        in.reset();
        nread = size - in.available();
        monitor.setProgress(nread);
    }
}
