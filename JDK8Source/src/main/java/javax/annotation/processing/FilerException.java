/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2005, 2012, Oracle and/or its affiliates. All rights reserved.
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

package javax.annotation.processing;

import java.io.IOException;

/**
 * Indicates a {@link Filer} detected an attempt to open a file that
 * would violate the guarantees provided by the {@code Filer}.  Those
 * guarantees include not creating the same file more than once, not
 * creating multiple files corresponding to the same type, and not
 * creating files for types with invalid names.
 *
 * <p>
 *  表示{@link Filer}检测到尝试打开的文件会违反{@code Filer}提供的保证。这些保证包括不多次创建相同的文件,不创建对应于相同类型的多个文件,并且不为具有无效名称的类型创建文件。
 * 
 * 
 * @author Joseph D. Darcy
 * @author Scott Seligman
 * @author Peter von der Ah&eacute;
 * @since 1.6
 */
public class FilerException extends IOException {
    static final long serialVersionUID = 8426423106453163293L;
    /**
     * Constructs an exception with the specified detail message.
     * <p>
     *  使用指定的详细消息构造异常。
     * 
     * @param s the detail message, which should include the name of
     * the file attempting to be opened; may be {@code null}
     */
    public FilerException(String s) {
        super(s);
    }
}
