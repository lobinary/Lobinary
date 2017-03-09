/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2004, 2008, Oracle and/or its affiliates. All rights reserved.
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

package java.lang.annotation;

/**
 * Thrown when the annotation parser attempts to read an annotation
 * from a class file and determines that the annotation is malformed.
 * This error can be thrown by the {@linkplain
 * java.lang.reflect.AnnotatedElement API used to read annotations
 * reflectively}.
 *
 * <p>
 *  当注释解析器尝试从类文件读取注释并确定注释格式不正确时抛出。此错误可由{@linkplain java.lang.reflect.AnnotatedElement API用于反映读取注释}抛出。
 * 
 * 
 * @author  Josh Bloch
 * @see     java.lang.reflect.AnnotatedElement
 * @since   1.5
 */
public class AnnotationFormatError extends Error {
    private static final long serialVersionUID = -4256701562333669892L;

    /**
     * Constructs a new <tt>AnnotationFormatError</tt> with the specified
     * detail message.
     *
     * <p>
     *  使用指定的详细消息构造新的<tt> AnnotationFormatError </tt>。
     * 
     * 
     * @param   message   the detail message.
     */
    public AnnotationFormatError(String message) {
        super(message);
    }

    /**
     * Constructs a new <tt>AnnotationFormatError</tt> with the specified
     * detail message and cause.  Note that the detail message associated
     * with <code>cause</code> is <i>not</i> automatically incorporated in
     * this error's detail message.
     *
     * <p>
     *  使用指定的详细消息和原因构造新的<tt> AnnotationFormatError </tt>。
     * 请注意,与<code> cause </code>关联的详细信息</i>不会自动并入此错误的详细信息中。
     * 
     * 
     * @param  message the detail message
     * @param  cause the cause (A <tt>null</tt> value is permitted, and
     *     indicates that the cause is nonexistent or unknown.)
     */
    public AnnotationFormatError(String message, Throwable cause) {
        super(message, cause);
    }


    /**
     * Constructs a new <tt>AnnotationFormatError</tt> with the specified
     * cause and a detail message of
     * <tt>(cause == null ? null : cause.toString())</tt> (which
     * typically contains the class and detail message of <tt>cause</tt>).
     *
     * <p>
     *  使用指定的原因和<tt>(cause == null?null：cause.toString())</tt>(通常包含类和详细信息)的详细消息构造新的<tt> AnnotationFormatErro
     * r </tt>的<tt>原因</tt>)。
     * 
     * @param  cause the cause (A <tt>null</tt> value is permitted, and
     *     indicates that the cause is nonexistent or unknown.)
     */
    public AnnotationFormatError(Throwable cause) {
        super(cause);
    }
}
