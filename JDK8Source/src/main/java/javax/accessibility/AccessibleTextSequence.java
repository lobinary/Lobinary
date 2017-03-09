/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, 2013, Oracle and/or its affiliates. All rights reserved.
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
package javax.accessibility;


/**
 * <P>The AccessibleTextSequence provides information about
 * a contiguous sequence of text.
 *
 * <p>
 *  <P> AccessibleTextSequence提供关于文本的连续序列的信息。
 * 
 * 
 * @see Accessible
 * @see Accessible#getAccessibleContext
 * @see AccessibleContext
 * @see AccessibleContext#getAccessibleText
 * @see AccessibleAttributeSequence
 *
 * @author       Lynn Monsanto
 */

/**
 * This class collects together key details of a span of text.  It
 * is used by implementors of the class <code>AccessibleExtendedText</code> in
 * order to return the requested triplet of a <code>String</code>, and the
 * start and end indicies/offsets into a larger body of text that the
 * <code>String</code> comes from.
 *
 * <p>
 *  此类收集一段文本的关键详细信息。
 * 它由类<code> AccessibleExtendedText </code>的实现者使用,以便返回<code> String </code>的请求的三元组,以及开始和结束指示/偏移到更大的文本体中,
 *  <code> String </code>来自。
 *  此类收集一段文本的关键详细信息。
 * 
 * @see javax.accessibility.AccessibleExtendedText
 */
public class AccessibleTextSequence {

    /** The start index of the text sequence */
    public int startIndex;

    /** The end index of the text sequence */
    public int endIndex;

    /** The text */
    public String text;

    /**
     * Constructs an <code>AccessibleTextSequence</code> with the given
     * parameters.
     *
     * <p>
     * 
     * 
     * @param start the beginning index of the span of text
     * @param end the ending index of the span of text
     * @param txt the <code>String</code> shared by this text span
     *
     * @since 1.6
     */
    public AccessibleTextSequence(int start, int end, String txt) {
        startIndex = start;
        endIndex = end;
        text = txt;
    }
};
