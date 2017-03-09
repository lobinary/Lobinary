/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1995, 2014, Oracle and/or its affiliates. All rights reserved.
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
package java.awt.peer;

import java.awt.Dimension;
import java.awt.TextArea;

/**
 * The peer interface for {@link TexTArea}.
 *
 * The peer interfaces are intended only for use in porting
 * the AWT. They are not intended for use by application
 * developers, and developers should not implement peers
 * nor invoke any of the peer methods directly on the peer
 * instances.
 * <p>
 *  {@link TexTArea}的对等接口。
 * 
 *  对等接口仅用于移植AWT。它们不是供应用程序开发人员使用的,并且开发人员不应实现对等体,也不应直接在对等实例上调用任何对等方法。
 * 
 */
public interface TextAreaPeer extends TextComponentPeer {

    /**
     * Inserts the specified text at the specified position in the document.
     *
     * <p>
     *  在文档中指定的位置插入指定的文本。
     * 
     * 
     * @param text the text to insert
     * @param pos the position to insert
     *
     * @see TextArea#insert(String, int)
     */
    void insert(String text, int pos);

    /**
     * Replaces a range of text by the specified string.
     *
     * <p>
     *  用指定的字符串替换文本范围。
     * 
     * 
     * @param text the replacement string
     * @param start the begin of the range to replace
     * @param end the end of the range to replace
     *
     * @see TextArea#replaceRange(String, int, int)
     */
    void replaceRange(String text, int start, int end);

    /**
     * Returns the preferred size of a textarea with the specified number of
     * columns and rows.
     *
     * <p>
     *  返回具有指定数目的列和行的textarea的首选大小。
     * 
     * 
     * @param rows the number of rows
     * @param columns the number of columns
     *
     * @return the preferred size of a textarea
     *
     * @see TextArea#getPreferredSize(int, int)
     */
    Dimension getPreferredSize(int rows, int columns);

    /**
     * Returns the minimum size of a textarea with the specified number of
     * columns and rows.
     *
     * <p>
     *  返回具有指定数目的列和行的textarea的最小大小。
     * 
     * @param rows the number of rows
     * @param columns the number of columns
     *
     * @return the minimum size of a textarea
     *
     * @see TextArea#getMinimumSize(int, int)
     */
    Dimension getMinimumSize(int rows, int columns);

}
