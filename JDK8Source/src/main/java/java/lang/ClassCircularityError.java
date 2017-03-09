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
 * Thrown when the Java Virtual Machine detects a circularity in the
 * superclass hierarchy of a class being loaded.
 *
 * <p>
 *  当Java虚拟机检测到正在加载的类的超类层次结构中的圆形时抛出。
 * 
 * 
 * @author     unascribed
 * @since      JDK1.0
 */
public class ClassCircularityError extends LinkageError {
    private static final long serialVersionUID = 1054362542914539689L;

    /**
     * Constructs a {@code ClassCircularityError} with no detail message.
     * <p>
     *  构造一个没有详细消息的{@code ClassCircularityError}。
     * 
     */
    public ClassCircularityError() {
        super();
    }

    /**
     * Constructs a {@code ClassCircularityError} with the specified detail
     * message.
     *
     * <p>
     *  使用指定的详细消息构造{@code ClassCircularityError}。
     * 
     * @param  s
     *         The detail message
     */
    public ClassCircularityError(String s) {
        super(s);
    }
}
