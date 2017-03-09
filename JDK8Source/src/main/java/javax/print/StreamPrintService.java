/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2001, Oracle and/or its affiliates. All rights reserved.
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

package javax.print;

import java.io.OutputStream;

/**
 * This class extends {@link PrintService} and represents a
 * print service that prints data in different formats to a
 * client-provided output stream.
 * This is principally intended for services where
 * the output format is a document type suitable for viewing
 * or archiving.
 * The output format must be declared as a mime type.
 * This is equivalent to an output document flavor where the
 * representation class is always "java.io.OutputStream"
 * An instance of the <code>StreamPrintService</code> class is
 * obtained from a {@link StreamPrintServiceFactory} instance.
 * <p>
 * Note that a <code>StreamPrintService</code> is different from a
 * <code>PrintService</code>, which supports a
 * {@link javax.print.attribute.standard.Destination Destination}
 * attribute.  A <code>StreamPrintService</code> always requires an output
 * stream, whereas a <code>PrintService</code> optionally accepts a
 * <code>Destination</code>. A <code>StreamPrintService</code>
 * has no default destination for its formatted output.
 * Additionally a <code>StreamPrintService</code> is expected to generate
output in
 * a format useful in other contexts.
 * StreamPrintService's are not expected to support the Destination attribute.
 * <p>
 *  这个类扩展了{@link PrintService}并且代表一个打印服务,它以不同的格式将数据打印到客户提供的输出流。这主要针对输出格式是适合查看或归档的文档类型的服务。
 * 输出格式必须声明为mime类型。
 * 这相当于一个输出文档风格,其中表示类始终是"java.io.OutputStream"<code> StreamPrintService </code>类的实例是从{@link StreamPrintServiceFactory}
 * 实例获取的。
 * 输出格式必须声明为mime类型。
 * <p>
 *  请注意,<code> StreamPrintService </code>与支持{@link javax.print.attribute.standard.Destination Destination}
 * 属性的<code> PrintService </code>不同。
 *  <code> StreamPrintService </code>始终需要输出流,而<code> PrintService </code>可选地接受<code>目标</code>。
 *  <code> StreamPrintService </code>没有其格式化输出的默认目的地。
 * 另外,期望一个<code> StreamPrintService </code>以一种在其他上下文中有用的格式生成输出。 StreamPrintService不希望支持Destination属性。
 * 
 */

public abstract class StreamPrintService implements PrintService {

    private OutputStream outStream;
    private boolean disposed = false;

    private StreamPrintService() {
    };

    /**
     * Constructs a StreamPrintService object.
     *
     * <p>
     *  构造StreamPrintService对象。
     * 
     * 
     * @param out  stream to which to send formatted print data.
     */
    protected StreamPrintService(OutputStream out) {
        this.outStream = out;
    }

    /**
     * Gets the output stream.
     *
     * <p>
     *  获取输出流。
     * 
     * 
     * @return the stream to which this service will send formatted print data.
     */
    public OutputStream getOutputStream() {
        return outStream;
    }

    /**
     * Returns the document format emitted by this print service.
     * Must be in mimetype format, compatible with the mime type
     * <p>
     *  返回此打印服务发出的文档格式。必须是mimetype格式,与mime类型兼容
     * 
     * 
     * components of DocFlavors @see DocFlavor.
     * @return mime type identifying the output format.
     */
    public abstract String getOutputFormat();

    /**
     * Disposes this <code>StreamPrintService</code>.
     * If a stream service cannot be re-used, it must be disposed
     * to indicate this. Typically the client will call this method.
     * Services which write data which cannot meaningfully be appended to
     * may also dispose the stream. This does not close the stream. It
     * just marks it as not for further use by this service.
     * <p>
     * 处理此<code> StreamPrintService </code>。如果流服务不能被重新使用,则必须处理它以指示这一点。通常客户端将调用此方法。写入不能有意义地附加的数据的服务也可以配置流。
     * 这不关闭流。它只是标记为不进一步使用这项服务。
     * 
     */
    public void dispose() {
        disposed = true;
    }

    /**
     * Returns a <code>boolean</code> indicating whether or not
     * this <code>StreamPrintService</code> has been disposed.
     * If this object has been disposed, will return true.
     * Used by services and client applications to recognize streams
     * to which no further data should be written.
     * <p>
     *  返回一个<code> boolean </code>,指示此<> StreamPrintService </code>是否已处理。如果这个对象被处理,将返回true。
     * 由服务和客户端应用程序用于识别不应向其写入更多数据的流。
     * 
     * @return if this <code>StreamPrintService</code> has been disposed
     */
    public boolean isDisposed() {
        return disposed;
    }

}
