/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2000, Oracle and/or its affiliates. All rights reserved.
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
package javax.swing.text.rtf;

import java.awt.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.Action;
import javax.swing.text.*;
import javax.swing.*;

/**
 * This is the default implementation of RTF editing
 * functionality.  The RTF support was not written by the
 * Swing team.  In the future we hope to improve the support
 * provided.
 *
 * <p>
 *  这是RTF编辑功能的默认实现。 RTF支持不是由Swing团队编写的。在未来,我们希望改善所提供的支持。
 * 
 * 
 * @author  Timothy Prinzing (of this class, not the package!)
 */
public class RTFEditorKit extends StyledEditorKit {

    /**
     * Constructs an RTFEditorKit.
     * <p>
     *  构造一个RTFEditorKit。
     * 
     */
    public RTFEditorKit() {
        super();
    }

    /**
     * Get the MIME type of the data that this
     * kit represents support for.  This kit supports
     * the type <code>text/rtf</code>.
     *
     * <p>
     *  获取此套件表示支持的数据的MIME类型。此工具包支持<code> text / rtf </code>类型。
     * 
     * 
     * @return the type
     */
    public String getContentType() {
        return "text/rtf";
    }

    /**
     * Insert content from the given stream which is expected
     * to be in a format appropriate for this kind of content
     * handler.
     *
     * <p>
     *  从给定流中插入内容,期望该内容采用适合此类内容处理程序的格式。
     * 
     * 
     * @param in  The stream to read from
     * @param doc The destination for the insertion.
     * @param pos The location in the document to place the
     *   content.
     * @exception IOException on any I/O error
     * @exception BadLocationException if pos represents an invalid
     *   location within the document.
     */
    public void read(InputStream in, Document doc, int pos) throws IOException, BadLocationException {

        if (doc instanceof StyledDocument) {
            // PENDING(prinz) this needs to be fixed to
            // insert to the given position.
            RTFReader rdr = new RTFReader((StyledDocument) doc);
            rdr.readFromStream(in);
            rdr.close();
        } else {
            // treat as text/plain
            super.read(in, doc, pos);
        }
    }

    /**
     * Write content from a document to the given stream
     * in a format appropriate for this kind of content handler.
     *
     * <p>
     *  以适合此类内容处理程序的格式将内容从文档写入给定流。
     * 
     * 
     * @param out  The stream to write to
     * @param doc The source for the write.
     * @param pos The location in the document to fetch the
     *   content.
     * @param len The amount to write out.
     * @exception IOException on any I/O error
     * @exception BadLocationException if pos represents an invalid
     *   location within the document.
     */
    public void write(OutputStream out, Document doc, int pos, int len)
        throws IOException, BadLocationException {

            // PENDING(prinz) this needs to be fixed to
            // use the given document range.
            RTFGenerator.writeDocument(doc, out);
    }

    /**
     * Insert content from the given stream, which will be
     * treated as plain text.
     *
     * <p>
     *  从给定流中插入内容,这将被视为纯文本。
     * 
     * 
     * @param in  The stream to read from
     * @param doc The destination for the insertion.
     * @param pos The location in the document to place the
     *   content.
     * @exception IOException on any I/O error
     * @exception BadLocationException if pos represents an invalid
     *   location within the document.
     */
    public void read(Reader in, Document doc, int pos)
        throws IOException, BadLocationException {

        if (doc instanceof StyledDocument) {
            RTFReader rdr = new RTFReader((StyledDocument) doc);
            rdr.readFromReader(in);
            rdr.close();
        } else {
            // treat as text/plain
            super.read(in, doc, pos);
        }
    }

    /**
     * Write content from a document to the given stream
     * as plain text.
     *
     * <p>
     *  将文档中的内容以纯文本形式写入给定流。
     * 
     * @param out  The stream to write to
     * @param doc The source for the write.
     * @param pos The location in the document to fetch the
     *   content.
     * @param len The amount to write out.
     * @exception IOException on any I/O error
     * @exception BadLocationException if pos represents an invalid
     *   location within the document.
     */
    public void write(Writer out, Document doc, int pos, int len)
        throws IOException, BadLocationException {

        throw new IOException("RTF is an 8-bit format");
    }

}
