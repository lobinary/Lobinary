/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2005, Oracle and/or its affiliates. All rights reserved.
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

package javax.xml.transform;

/**
 * <p>To provide customized error handling, implement this interface and
 * use the <code>setErrorListener</code> method to register an instance of the
 * implmentation with the {@link javax.xml.transform.Transformer}.  The
 * <code>Transformer</code> then reports all errors and warnings through this
 * interface.</p>
 *
 * <p>If an application does <em>not</em> register its own custom
 * <code>ErrorListener</code>, the default <code>ErrorListener</code>
 * is used which reports all warnings and errors to <code>System.err</code>
 * and does not throw any <code>Exception</code>s.
 * Applications are <em>strongly</em> encouraged to register and use
 * <code>ErrorListener</code>s that insure proper behavior for warnings and
 * errors.</p>
 *
 * <p>For transformation errors, a <code>Transformer</code> must use this
 * interface instead of throwing an <code>Exception</code>: it is up to the
 * application to decide whether to throw an <code>Exception</code> for
 * different types of errors and warnings.  Note however that the
 * <code>Transformer</code> is not required to continue with the transformation
 * after a call to {@link #fatalError(TransformerException exception)}.</p>
 *
 * <p><code>Transformer</code>s may use this mechanism to report XML parsing
 * errors as well as transformation errors.</p>
 * <p>
 *  <p>要提供定制的错误处理,请实现此接口并使用<code> setErrorListener </code>方法向{@link javax.xml.transform.Transformer}注册一个
 * 实现。
 *  <code> Transformer </code>然后通过此界面报告所有错误和警告。</p>。
 * 
 *  <p>如果应用程序不</em>注册自己的自定义<code> ErrorListener </code>,则会使用默认的<code> ErrorListener </code>,它会将所有警告和错误报告
 * 给<code> System.err </code>,不会抛出任何<code>异常</code>。
 * 应用程序被强烈地</em>注册并使用<code> ErrorListener </code>,以确保警告和错误的正确行为。</p>。
 * 
 *  <p>对于转换错误,<code> Transformer </code>必须使用此接口,而不是抛出一个<code>异常</code>：由应用程序决定是否抛出<code> / code>用于不同类型的错
 * 误和警告。
 * 但请注意,在调用{@link #fatalError(TransformerException exception)}后,不需要<code> Transformer </code>来继续转换。
 * </p>。
 * 
 *  <p> <code> Transformer </code>可能会使用此机制报告XML解析错误以及转换错误。</p>
 * 
 */
public interface ErrorListener {

    /**
     * Receive notification of a warning.
     *
     * <p>{@link javax.xml.transform.Transformer} can use this method to report
     * conditions that are not errors or fatal errors.  The default behaviour
     * is to take no action.</p>
     *
     * <p>After invoking this method, the Transformer must continue with
     * the transformation. It should still be possible for the
     * application to process the document through to the end.</p>
     *
     * <p>
     *  接收警告通知。
     * 
     * <p> {@ link javax.xml.transform.Transformer}可以使用此方法报告不是错误或致命错误的情况。默认行为是不采取任何操作。</p>
     * 
     *  <p>调用此方法后,Transformer必须继续进行转换。应用程序应该仍然可以处理文档,直到结束。</p>
     * 
     * 
     * @param exception The warning information encapsulated in a
     *                  transformer exception.
     *
     * @throws javax.xml.transform.TransformerException if the application
     * chooses to discontinue the transformation.
     *
     * @see javax.xml.transform.TransformerException
     */
    public abstract void warning(TransformerException exception)
        throws TransformerException;

    /**
     * Receive notification of a recoverable error.
     *
     * <p>The transformer must continue to try and provide normal transformation
     * after invoking this method.  It should still be possible for the
     * application to process the document through to the end if no other errors
     * are encountered.</p>
     *
     * <p>
     *  接收可恢复错误的通知。
     * 
     *  <p>变压器必须在调用此方法后继续尝试并提供正常变换。如果没有遇到其他错误,应用程序仍然可以处理文档直到结束。</p>
     * 
     * 
     * @param exception The error information encapsulated in a
     *                  transformer exception.
     *
     * @throws javax.xml.transform.TransformerException if the application
     * chooses to discontinue the transformation.
     *
     * @see javax.xml.transform.TransformerException
     */
    public abstract void error(TransformerException exception)
        throws TransformerException;

    /**
     * <p>Receive notification of a non-recoverable error.</p>
     *
     * <p>The processor may choose to continue, but will not normally
     * proceed to a successful completion.</p>
     *
     * <p>The method should throw an exception if it is unable to
     * process the error, or if it wishes execution to terminate
     * immediately. The processor will not necessarily honor this
     * request.</p>
     *
     * <p>
     *  <p>接收到不可恢复错误的通知。</p>
     * 
     *  <p>处理器可以选择继续,但通常不会成功完成。</p>
     * 
     * @param exception The error information encapsulated in a
     *    <code>TransformerException</code>.
     *
     * @throws javax.xml.transform.TransformerException if the application
     * chooses to discontinue the transformation.
     *
     * @see javax.xml.transform.TransformerException
     */
    public abstract void fatalError(TransformerException exception)
        throws TransformerException;
}
