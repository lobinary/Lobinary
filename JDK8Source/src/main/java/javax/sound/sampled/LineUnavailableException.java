/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2002, Oracle and/or its affiliates. All rights reserved.
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

package javax.sound.sampled;

/**
 * A <code>LineUnavailableException</code> is an exception indicating that a
 * line cannot be opened because it is unavailable.  This situation
 * arises most commonly when a requested line is already in use
 * by another application.
 *
 * <p>
 *  <code> LineUnavailableException </code>是一个例外,表示无法打开行,因为它不可用。当所请求的行已经被另一应用使用时,这种情况最常见。
 * 
 * 
 * @author Kara Kytle
 * @since 1.3
 */
/*
 * A <code>LinenavailableException</code> is an exception indicating that a
 * line annot be opened because it is unavailable.  This situation
 * arises most commonly when a line is requested when it is already in use
 * by another application.
 *
 * <p>
 *  <code> LinenavailableException </code>是一个异常,指示行注释因为不可用而打开。这种情况最常见的情况是,当一个行被另一个应用程序使用时被请求。
 * 
 * 
 * @author Kara Kytle
 */

public class LineUnavailableException extends Exception {

    /**
     * Constructs a <code>LineUnavailableException</code> that has
     * <code>null</code> as its error detail message.
     * <p>
     *  构造一个具有<code> null </code>作为其错误详细信息的<code> LineUnavailableException </code>。
     * 
     */
    public LineUnavailableException() {

        super();
    }


    /**
     * Constructs a <code>LineUnavailableException</code> that has
     * the specified detail message.
     *
     * <p>
     *  构造具有指定详细消息的<code> LineUnavailableException </code>。
     * 
     * @param message a string containing the error detail message
     */
    public LineUnavailableException(String message) {

        super(message);
    }
}
