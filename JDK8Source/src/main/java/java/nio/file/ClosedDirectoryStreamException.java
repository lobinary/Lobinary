/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2009, Oracle and/or its affiliates. All rights reserved.
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

package java.nio.file;

/**
 * Unchecked exception thrown when an attempt is made to invoke an operation on
 * a directory stream that is closed.
 *
 * <p>
 *  尝试调用已关闭的目录流上的操作时抛出未经检查的异常。
 * 
 * 
 * @since 1.7
 */

public class ClosedDirectoryStreamException
    extends IllegalStateException
{
    static final long serialVersionUID = 4228386650900895400L;

    /**
     * Constructs an instance of this class.
     * <p>
     *  构造此类的实例。
     */
    public ClosedDirectoryStreamException() {
    }
}
