/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2001, 2002, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.javadoc;

import java.io.File;

/**
 * This interface describes a source position: filename, line number,
 * and column number.
 *
 * <p>
 *  此接口描述源位置：文件名,行号和列号。
 * 
 * 
 * @since 1.4
 * @author Neal M Gafter
 */
public interface SourcePosition {
    /** The source file. Returns null if no file information is
    /* <p>
    /* 
     *  available. */
    File file();

    /** The line in the source file. The first line is numbered 1;
    /* <p>
    /* 
     *  0 means no line number information is available. */
    int line();

    /** The column in the source file. The first column is
     *  numbered 1; 0 means no column information is available.
     *  Columns count characters in the input stream; a tab
     *  advances the column number to the next 8-column tab stop.
     * <p>
     *  编号1; 0表示没有列信息可用。列计算输入流中的字符数;选项卡将列号提升到下一个8列制表位。
     */
    int column();

    /** Convert the source position to the form "Filename:line". */
    String toString();
}
