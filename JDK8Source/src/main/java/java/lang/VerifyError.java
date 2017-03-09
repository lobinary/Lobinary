/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
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

package java.lang;

/**
 * Thrown when the "verifier" detects that a class file,
 * though well formed, contains some sort of internal inconsistency
 * or security problem.
 *
 * <p>
 *  当"验证程序"检测到类文件虽然格式良好,但包含某种内部不一致或安全问题时抛出。
 * 
 * 
 * @author  unascribed
 * @since   JDK1.0
 */
public
class VerifyError extends LinkageError {
    private static final long serialVersionUID = 7001962396098498785L;

    /**
     * Constructs an <code>VerifyError</code> with no detail message.
     * <p>
     *  构造一个没有详细消息的<code> VerifyError </code>。
     * 
     */
    public VerifyError() {
        super();
    }

    /**
     * Constructs an <code>VerifyError</code> with the specified detail message.
     *
     * <p>
     *  使用指定的详细消息构造<code> VerifyError </code>。
     * 
     * @param   s   the detail message.
     */
    public VerifyError(String s) {
        super(s);
    }
}
