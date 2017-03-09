/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, Oracle and/or its affiliates. All rights reserved.
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

package java.awt;

/**
 * Conditional is used by the EventDispatchThread's message pumps to
 * determine if a given pump should continue to run, or should instead exit
 * and yield control to the parent pump.
 *
 * <p>
 *  EventDispatchThread的消息泵使用条件来确定给定的泵是否应继续运行,或者应该退出并对父泵进行控制。
 * 
 * @author David Mendenhall
 */
interface Conditional {
    boolean evaluate();
}
