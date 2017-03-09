/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2013, Oracle and/or its affiliates. All rights reserved.
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
 * <code>DocumentFilter</code>, as the name implies, is a filter for the
 * <code>Document</code> mutation methods. When a <code>Document</code>
 * containing a <code>DocumentFilter</code> is modified (either through
 * <code>insert</code> or <code>remove</code>), it forwards the appropriate
 * method invocation to the <code>DocumentFilter</code>. The
 * default implementation allows the modification to
 * occur. Subclasses can filter the modifications by conditionally invoking
 * methods on the superclass, or invoking the necessary methods on
 * the passed in <code>FilterBypass</code>. Subclasses should NOT call back
 * into the Document for the modification
 * instead call into the superclass or the <code>FilterBypass</code>.
 * <p>
 * When <code>remove</code> or <code>insertString</code> is invoked
 * on the <code>DocumentFilter</code>, the <code>DocumentFilter</code>
 * may callback into the
 * <code>FilterBypass</code> multiple times, or for different regions, but
 * it should not callback into the <code>FilterBypass</code> after returning
 * from the <code>remove</code> or <code>insertString</code> method.
 * <p>
 * By default, text related document mutation methods such as
 * <code>insertString</code>, <code>replace</code> and <code>remove</code>
 * in <code>AbstractDocument</code> use <code>DocumentFilter</code> when
 * available, and <code>Element</code> related mutation methods such as
 * <code>create</code>, <code>insert</code> and <code>removeElement</code> in
 * <code>DefaultStyledDocument</code> do not use <code>DocumentFilter</code>.
 * If a method doesn't follow these defaults, this must be explicitly stated
 * in the method documentation.
 *
 * <p>
 *  <code> DocumentFilter </code>,顾名思义,是<code> Document </code>突变方法的过滤器。
 * 当包含<code> DocumentFilter </code>的<code> Document </code>被修改(通过<code> insert </code>或<code> remove </code>
 * )时,它转发适当的方法调用到<code> DocumentFilter </code>。
 *  <code> DocumentFilter </code>,顾名思义,是<code> Document </code>突变方法的过滤器。默认实现允许进行修改。
 * 子类可以通过有条件地调用超类上的方法或者调用在<code> FilterBypass </code>中传递的必要方法来过滤修改。
 * 子类不应该回调到Document中进行修改,而不是调用超类或<code> FilterBypass </code>。
 * <p>
 *  当在<code> DocumentFilter </code>上调用<code> remove </code>或<code> insertString </code>时,<code> Document
 * Filter </code>可以回调到<code> FilterBypass <代码>多次,或者对于不同的区域,但它不应该从<code> remove </code>或<code> insertStri
 * ng </code>方法返回后回调到<code> FilterBypass </code>中。
 * <p>
 * 默认情况下,在<code> AbstractDocument </code>中使用<code> DocumentFilter的文本相关文档变异方法,如<code> insertString </code>
 * ,<code>替换</code>和<code> </code>(如果可用)和<code>插入</code>和<code> removeElement </code>中的<code> create </code>
 *  > DefaultStyledDocument </code>不要使用<code> DocumentFilter </code>。
 * 如果方法不遵循这些默认值,则必须在方法文档中明确说明。
 * 
 * 
 * @see javax.swing.text.Document
 * @see javax.swing.text.AbstractDocument
 * @see javax.swing.text.DefaultStyledDocument
 *
 * @since 1.4
 */
public class DocumentFilter {
    /**
     * Invoked prior to removal of the specified region in the
     * specified Document. Subclasses that want to conditionally allow
     * removal should override this and only call supers implementation as
     * necessary, or call directly into the <code>FilterBypass</code> as
     * necessary.
     *
     * <p>
     *  在删除指定的文档中指定的区域之前调用。想要有条件地允许删除的子类应该覆盖这一点,并且只在必要时调用supers实现,或者根据需要直接调用<code> FilterBypass </code>。
     * 
     * 
     * @param fb FilterBypass that can be used to mutate Document
     * @param offset the offset from the beginning &gt;= 0
     * @param length the number of characters to remove &gt;= 0
     * @exception BadLocationException  some portion of the removal range
     *   was not a valid part of the document.  The location in the exception
     *   is the first bad position encountered.
     */
    public void remove(FilterBypass fb, int offset, int length) throws
                       BadLocationException {
        fb.remove(offset, length);
    }

    /**
     * Invoked prior to insertion of text into the
     * specified Document. Subclasses that want to conditionally allow
     * insertion should override this and only call supers implementation as
     * necessary, or call directly into the FilterBypass.
     *
     * <p>
     *  在将文本插入到指定的文档之前调用。想要有条件地允许插入的子类应该覆盖这个,并且只在必要时调用supers实现,或者直接调用FilterBypass。
     * 
     * 
     * @param fb FilterBypass that can be used to mutate Document
     * @param offset  the offset into the document to insert the content &gt;= 0.
     *    All positions that track change at or after the given location
     *    will move.
     * @param string the string to insert
     * @param attr      the attributes to associate with the inserted
     *   content.  This may be null if there are no attributes.
     * @exception BadLocationException  the given insert position is not a
     *   valid position within the document
     */
    public void insertString(FilterBypass fb, int offset, String string,
                             AttributeSet attr) throws BadLocationException {
        fb.insertString(offset, string, attr);
    }

    /**
     * Invoked prior to replacing a region of text in the
     * specified Document. Subclasses that want to conditionally allow
     * replace should override this and only call supers implementation as
     * necessary, or call directly into the FilterBypass.
     *
     * <p>
     *  在替换指定文档中的文本区域之前调用。想要有条件地允许替换的子类应该覆盖这个,并且只在必要时调用supers实现,或者直接调用FilterBypass。
     * 
     * 
     * @param fb FilterBypass that can be used to mutate Document
     * @param offset Location in Document
     * @param length Length of text to delete
     * @param text Text to insert, null indicates no text to insert
     * @param attrs AttributeSet indicating attributes of inserted text,
     *              null is legal.
     * @exception BadLocationException  the given insert position is not a
     *   valid position within the document
     */
    public void replace(FilterBypass fb, int offset, int length, String text,
                        AttributeSet attrs) throws BadLocationException {
        fb.replace(offset, length, text, attrs);
    }


    /**
     * Used as a way to circumvent calling back into the Document to
     * change it. Document implementations that wish to support
     * a DocumentFilter must provide an implementation that will
     * not callback into the DocumentFilter when the following methods
     * are invoked from the DocumentFilter.
     * <p>
     * 用作一种规避回调到文档中以更改它的方式。希望支持DocumentFilter的文档实现必须提供一个实现,当从DocumentFilter调用以下方法时,它不会回调到DocumentFilter中。
     * 
     * 
     * @since 1.4
     */
    public static abstract class FilterBypass {
        /**
         * Returns the Document the mutation is occurring on.
         *
         * <p>
         *  返回正在发生突变的文档。
         * 
         * 
         * @return Document that remove/insertString will operate on
         */
        public abstract Document getDocument();

        /**
         * Removes the specified region of text, bypassing the
         * DocumentFilter.
         *
         * <p>
         *  删除指定的文本区域,绕过DocumentFilter。
         * 
         * 
         * @param offset the offset from the beginning &gt;= 0
         * @param length the number of characters to remove &gt;= 0
         * @exception BadLocationException some portion of the removal range
         *   was not a valid part of the document.  The location in the
         *   exception is the first bad position encountered.
         */
        public abstract void remove(int offset, int length) throws
                             BadLocationException;

        /**
         * Inserts the specified text, bypassing the
         * DocumentFilter.
         * <p>
         *  插入指定的文本,绕过DocumentFilter。
         * 
         * 
         * @param offset  the offset into the document to insert the
         *   content &gt;= 0. All positions that track change at or after the
         *   given location will move.
         * @param string the string to insert
         * @param attr the attributes to associate with the inserted
         *   content.  This may be null if there are no attributes.
         * @exception BadLocationException  the given insert position is not a
         *   valid position within the document
         */
        public abstract void insertString(int offset, String string,
                                          AttributeSet attr) throws
                                   BadLocationException;

        /**
         * Deletes the region of text from <code>offset</code> to
         * <code>offset + length</code>, and replaces it with
         *  <code>text</code>.
         *
         * <p>
         *  将文本区域从<code> offset </code>删除为<code> offset + length </code>,并将其替换为<code> text </code>。
         * 
         * @param offset Location in Document
         * @param length Length of text to delete
         * @param string Text to insert, null indicates no text to insert
         * @param attrs AttributeSet indicating attributes of inserted text,
         *              null is legal.
         * @exception BadLocationException  the given insert is not a
         *   valid position within the document
         */
        public abstract void replace(int offset, int length, String string,
                                          AttributeSet attrs) throws
                                   BadLocationException;
    }
}
