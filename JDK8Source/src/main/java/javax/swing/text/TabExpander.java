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
 * Simple interface to allow for different types of
 * implementations of tab expansion.
 *
 * <p>
 *  简单的界面,允许不同类型的tab扩展实现。
 * 
 * 
 * @author  Timothy Prinzing
 */
public interface TabExpander {

    /**
     * Returns the next tab stop position given a reference
     * position.  Values are expressed in points.
     *
     * <p>
     *  返回给定参考位置的下一个制表位停止位置。值以点表示。
     * 
     * @param x the position in points &gt;= 0
     * @param tabOffset the position within the text stream
     *   that the tab occurred at &gt;= 0.
     * @return the next tab stop &gt;= 0
     */
    float nextTabStop(float x, int tabOffset);

}
