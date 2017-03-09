/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 1998, Oracle and/or its affiliates. All rights reserved.
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
 * This exception is to report the failure of state invarient
 * assertion that was made.  This indicates an internal error
 * has occurred.
 *
 * <p>
 *  这个异常是报告所做的状态不明确断言的失败。这表示发生了内部错误。
 * 
 * 
 * @author  Timothy Prinzing
 */
class StateInvariantError extends Error
{
    /**
     * Creates a new StateInvariantFailure object.
     *
     * <p>
     *  创建一个新的StateInvariantFailure对象。
     * 
     * @param s         a string indicating the assertion that failed
     */
    public StateInvariantError(String s) {
        super(s);
    }

}
