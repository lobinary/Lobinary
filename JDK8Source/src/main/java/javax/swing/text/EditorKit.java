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

import java.io.*;
import javax.swing.Action;
import javax.swing.JEditorPane;

/**
 * Establishes the set of things needed by a text component
 * to be a reasonably functioning editor for some <em>type</em>
 * of text content.  The EditorKit acts as a factory for some
 * kind of policy.  For example, an implementation
 * of html and rtf can be provided that is replaceable
 * with other implementations.
 * <p>
 * A kit can safely store editing state as an instance
 * of the kit will be dedicated to a text component.
 * New kits will normally be created by cloning a
 * prototype kit.  The kit will have it's
 * <code>setComponent</code> method called to establish
 * it's relationship with a JTextComponent.
 *
 * <p>
 *  将文本组件所需的一组事物设置为某些<em>类型</em>文本内容的合理功能的编辑器。 EditorKit作为某种策略的工厂。例如,可以提供可以用其他实现替换的html和rtf的实现。
 * <p>
 *  套件可以安全地存储编辑状态,因为套件的实例将专用于文本组件。通常通过克隆原型试剂盒来创建新试剂盒。
 * 该工具包将具有<code> setComponent </code>方法,以建立它与JTextComponent的关系。
 * 
 * 
 * @author  Timothy Prinzing
 */
public abstract class EditorKit implements Cloneable, Serializable {

    /**
     * Construct an EditorKit.
     * <p>
     *  构造EditorKit。
     * 
     */
    public EditorKit() {
    }

    /**
     * Creates a copy of the editor kit.  This is implemented
     * to use <code>Object.clone()</code>.  If the kit cannot be cloned,
     * null is returned.
     *
     * <p>
     *  创建编辑器工具包的副本。这是实现使用<code> Object.clone()</code>。如果无法克隆该套件,则返回null。
     * 
     * 
     * @return the copy
     */
    public Object clone() {
        Object o;
        try {
            o = super.clone();
        } catch (CloneNotSupportedException cnse) {
            o = null;
        }
        return o;
    }

    /**
     * Called when the kit is being installed into the
     * a JEditorPane.
     *
     * <p>
     *  在将工具包安装到JEditorPane中时调用。
     * 
     * 
     * @param c the JEditorPane
     */
    public void install(JEditorPane c) {
    }

    /**
     * Called when the kit is being removed from the
     * JEditorPane.  This is used to unregister any
     * listeners that were attached.
     *
     * <p>
     *  在从JEditorPane中删除工具包时调用。这用于注销附加的任何侦听器。
     * 
     * 
     * @param c the JEditorPane
     */
    public void deinstall(JEditorPane c) {
    }

    /**
     * Gets the MIME type of the data that this
     * kit represents support for.
     *
     * <p>
     *  获取此套件表示支持的数据的MIME类型。
     * 
     * 
     * @return the type
     */
    public abstract String getContentType();

    /**
     * Fetches a factory that is suitable for producing
     * views of any models that are produced by this
     * kit.
     *
     * <p>
     *  获取适合于生产此套件生产的任何型号视图的工厂。
     * 
     * 
     * @return the factory
     */
    public abstract ViewFactory getViewFactory();

    /**
     * Fetches the set of commands that can be used
     * on a text component that is using a model and
     * view produced by this kit.
     *
     * <p>
     *  获取可以在使用此套件生成的模型和视图的文本组件上使用的一组命令。
     * 
     * 
     * @return the set of actions
     */
    public abstract Action[] getActions();

    /**
     * Fetches a caret that can navigate through views
     * produced by the associated ViewFactory.
     *
     * <p>
     *  获取可以浏览相关ViewFactory生成的视图的插入符号。
     * 
     * 
     * @return the caret
     */
    public abstract Caret createCaret();

    /**
     * Creates an uninitialized text storage model
     * that is appropriate for this type of editor.
     *
     * <p>
     * 创建适合此类型编辑器的未初始化文本存储模型。
     * 
     * 
     * @return the model
     */
    public abstract Document createDefaultDocument();

    /**
     * Inserts content from the given stream which is expected
     * to be in a format appropriate for this kind of content
     * handler.
     *
     * <p>
     *  插入来自给定流的内容,期望该内容是适合于这种类型的内容处理器的格式。
     * 
     * 
     * @param in  The stream to read from
     * @param doc The destination for the insertion.
     * @param pos The location in the document to place the
     *   content &gt;= 0.
     * @exception IOException on any I/O error
     * @exception BadLocationException if pos represents an invalid
     *   location within the document.
     */
    public abstract void read(InputStream in, Document doc, int pos)
        throws IOException, BadLocationException;

    /**
     * Writes content from a document to the given stream
     * in a format appropriate for this kind of content handler.
     *
     * <p>
     *  以适合此类内容处理程序的格式将文档中的内容写入给定流。
     * 
     * 
     * @param out  The stream to write to
     * @param doc The source for the write.
     * @param pos The location in the document to fetch the
     *   content from &gt;= 0.
     * @param len The amount to write out &gt;= 0.
     * @exception IOException on any I/O error
     * @exception BadLocationException if pos represents an invalid
     *   location within the document.
     */
    public abstract void write(OutputStream out, Document doc, int pos, int len)
        throws IOException, BadLocationException;

    /**
     * Inserts content from the given stream which is expected
     * to be in a format appropriate for this kind of content
     * handler.
     * <p>
     * Since actual text editing is unicode based, this would
     * generally be the preferred way to read in the data.
     * Some types of content are stored in an 8-bit form however,
     * and will favor the InputStream.
     *
     * <p>
     *  插入来自给定流的内容,期望该内容是适合于这种类型的内容处理器的格式。
     * <p>
     *  由于实际的文本编辑是基于unicode的,这通常是读取数据的首选方式。然而,一些类型的内容以8位形式存储,并且将有利于InputStream。
     * 
     * 
     * @param in  The stream to read from
     * @param doc The destination for the insertion.
     * @param pos The location in the document to place the
     *   content &gt;= 0.
     * @exception IOException on any I/O error
     * @exception BadLocationException if pos represents an invalid
     *   location within the document.
     */
    public abstract void read(Reader in, Document doc, int pos)
        throws IOException, BadLocationException;

    /**
     * Writes content from a document to the given stream
     * in a format appropriate for this kind of content handler.
     * <p>
     * Since actual text editing is unicode based, this would
     * generally be the preferred way to write the data.
     * Some types of content are stored in an 8-bit form however,
     * and will favor the OutputStream.
     *
     * <p>
     *  以适合此类内容处理程序的格式将文档中的内容写入给定流。
     * <p>
     * 
     * @param out  The stream to write to
     * @param doc The source for the write.
     * @param pos The location in the document to fetch the
     *   content &gt;= 0.
     * @param len The amount to write out &gt;= 0.
     * @exception IOException on any I/O error
     * @exception BadLocationException if pos represents an invalid
     *   location within the document.
     */
    public abstract void write(Writer out, Document doc, int pos, int len)
        throws IOException, BadLocationException;

}
