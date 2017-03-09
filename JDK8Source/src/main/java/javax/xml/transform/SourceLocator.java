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
 * This interface is primarily for the purposes of reporting where
 * an error occurred in the XML source or transformation instructions.
 * <p>
 *  此接口主要用于报告XML源或转换指令中发生错误的位置。
 * 
 */
public interface SourceLocator {

    /**
     * Return the public identifier for the current document event.
     *
     * <p>The return value is the public identifier of the document
     * entity or of the external parsed entity in which the markup that
     * triggered the event appears.</p>
     *
     * <p>
     *  返回当前文档事件的公共标识符。
     * 
     *  <p>返回值是触发事件的标记出现的文档实体或外部解析实体的公共标识符。</p>
     * 
     * 
     * @return A string containing the public identifier, or
     *         null if none is available.
     * @see #getSystemId
     */
    public String getPublicId();

    /**
     * Return the system identifier for the current document event.
     *
     * <p>The return value is the system identifier of the document
     * entity or of the external parsed entity in which the markup that
     * triggered the event appears.</p>
     *
     * <p>If the system identifier is a URL, the parser must resolve it
     * fully before passing it to the application.</p>
     *
     * <p>
     *  返回当前文档事件的系统标识符。
     * 
     *  <p>返回值是触发事件的标记出现的文档实体或外部解析实体的系统标识符。</p>
     * 
     *  <p>如果系统标识符是URL,则解析器必须在将其传递给应用程序之前完全解析。</p>
     * 
     * 
     * @return A string containing the system identifier, or null
     *         if none is available.
     * @see #getPublicId
     */
    public String getSystemId();

    /**
     * Return the line number where the current document event ends.
     *
     * <p><strong>Warning:</strong> The return value from the method
     * is intended only as an approximation for the sake of error
     * reporting; it is not intended to provide sufficient information
     * to edit the character content of the original XML document.</p>
     *
     * <p>The return value is an approximation of the line number
     * in the document entity or external parsed entity where the
     * markup that triggered the event appears.</p>
     *
     * <p>
     *  返回当前文档事件结束的行号。
     * 
     *  <p> <strong>警告：</strong>该方法的返回值仅用作误差报告的近似值;它不打算提供足够的信息来编辑原始XML文档的字符内容。</p>
     * 
     *  <p>返回值是文档实体或外部解析实体中出现触发事件的标记的行号的近似值。</p>
     * 
     * 
     * @return The line number, or -1 if none is available.
     * @see #getColumnNumber
     */
    public int getLineNumber();

    /**
     * Return the character position where the current document event ends.
     *
     * <p><strong>Warning:</strong> The return value from the method
     * is intended only as an approximation for the sake of error
     * reporting; it is not intended to provide sufficient information
     * to edit the character content of the original XML document.</p>
     *
     * <p>The return value is an approximation of the column number
     * in the document entity or external parsed entity where the
     * markup that triggered the event appears.</p>
     *
     * <p>
     *  返回当前文档事件结束的字符位置。
     * 
     * <p> <strong>警告：</strong>该方法的返回值仅用作误差报告的近似值;它不打算提供足够的信息来编辑原始XML文档的字符内容。</p>
     * 
     * @return The column number, or -1 if none is available.
     * @see #getLineNumber
     */
    public int getColumnNumber();
}
