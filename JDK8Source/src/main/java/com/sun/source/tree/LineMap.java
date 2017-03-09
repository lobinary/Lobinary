/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2006, 2013, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.source.tree;

/**
 * Provides methods to convert between character positions and line numbers
 * for a compilation unit.
 *
 * <p>
 *  提供在编译单元的字符位置和行号之间转换的方法。
 * 
 * 
 * @since 1.6
 */
@jdk.Exported
public interface LineMap {
    /**
     * Find the start position of a line.
     *
     * <p>
     *  查找线的起始位置。
     * 
     * 
     * @param line line number (beginning at 1)
     * @return     position of first character in line
     * @throws  IndexOutOfBoundsException
     *           if {@code lineNumber < 1}
     *           if {@code lineNumber > no. of lines}
     */
    long getStartPosition(long line);

    /**
     * Find the position corresponding to a (line,column).
     *
     * <p>
     *  找到与(线,列)对应的位置。
     * 
     * 
     * @param   line    line number (beginning at 1)
     * @param   column  tab-expanded column number (beginning 1)
     *
     * @return  position of character
     * @throws  IndexOutOfBoundsException
     *           if {@code line < 1}
     *           if {@code line > no. of lines}
     */
    long getPosition(long line, long column);

    /**
     * Find the line containing a position; a line termination
     * character is on the line it terminates.
     *
     * <p>
     *  查找包含位置的行;线终止字符在它终止的线上。
     * 
     * 
     * @param   pos  character offset of the position
     * @return the line number of pos (first line is 1)
     */
    long getLineNumber(long pos);

    /**
     * Find the column for a character position.
     * Tab characters preceding the position on the same line
     * will be expanded when calculating the column number.
     *
     * <p>
     *  查找字符位置的列。计算列号时,同一行上位置之前的制表符将被扩展。
     * 
     * @param  pos   character offset of the position
     * @return       the tab-expanded column number of pos (first column is 1)
     */
    long getColumnNumber(long pos);

}
