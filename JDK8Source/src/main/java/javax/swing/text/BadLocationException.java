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
package javax.swing.text;

/**
 * This exception is to report bad locations within a document model
 * (that is, attempts to reference a location that doesn't exist).
 * <p>
 * <strong>Warning:</strong>
 * Serialized objects of this class will not be compatible with
 * future Swing releases. The current serialization support is
 * appropriate for short term storage or RMI between applications running
 * the same version of Swing.  As of 1.4, support for long term storage
 * of all JavaBeans&trade;
 * has been added to the <code>java.beans</code> package.
 * Please see {@link java.beans.XMLEncoder}.
 *
 * <p>
 *  此异常用于报告文档模型中的错误位置(即尝试引用不存在的位置)。
 * <p>
 *  <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
 *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
 * 
 * 
 * @author  Timothy Prinzing
 */
@SuppressWarnings("serial")
public class BadLocationException extends Exception
{
    /**
     * Creates a new BadLocationException object.
     *
     * <p>
     *  创建一个新的BadLocationException对象。
     * 
     * 
     * @param s         a string indicating what was wrong with the arguments
     * @param offs      offset within the document that was requested &gt;= 0
     */
    public BadLocationException(String s, int offs) {
        super(s);
        this.offs = offs;
    }

    /**
     * Returns the offset into the document that was not legal.
     *
     * <p>
     *  返回文档中不合法的偏移量。
     * 
     * @return the offset &gt;= 0
     */
    public int offsetRequested() {
        return offs;
    }

    private int offs;
}
