/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.lang.reflect;

/**
 * Thrown when {@link java.lang.reflect.Executable#getParameters the
 * java.lang.reflect package} attempts to read method parameters from
 * a class file and determines that one or more parameters are
 * malformed.
 *
 * <p>The following is a list of conditions under which this exception
 * can be thrown:
 * <ul>
 * <li> The number of parameters (parameter_count) is wrong for the method
 * <li> A constant pool index is out of bounds.
 * <li> A constant pool index does not refer to a UTF-8 entry
 * <li> A parameter's name is "", or contains an illegal character
 * <li> The flags field contains an illegal flag (something other than
 *     FINAL, SYNTHETIC, or MANDATED)
 * </ul>
 *
 * See {@link java.lang.reflect.Executable#getParameters} for more
 * information.
 *
 * <p>
 *  当{@link java.lang.reflect.Executable#getParameters the java.lang.reflect package}尝试从类文件中读取方法参数并确定一个或
 * 多个参数格式不正确时抛出。
 * 
 *  <p>以下是可以抛出此异常的条件列表：
 * <ul>
 *  <li>方法的参数数(parameter_count)错误<li>常量池索引超出范围。
 *  <li>常量池索引不引用UTF-8条目<li>参数的名称为"",或包含非法字符<li> flags字段包含非法标志(非FINAL,SYNTHETIC或MANDATED)。
 * </ul>
 * 
 *  有关详细信息,请参阅{@link java.lang.reflect.Executable#getParameters}。
 * 
 * @see java.lang.reflect.Executable#getParameters
 * @since 1.8
 */
public class MalformedParametersException extends RuntimeException {

    /**
     * Version for serialization.
     * <p>
     * 
     */
    private static final long serialVersionUID = 20130919L;

    /**
     * Create a {@code MalformedParametersException} with an empty
     * reason.
     * <p>
     *  序列化版本。
     * 
     */
    public MalformedParametersException() {}

    /**
     * Create a {@code MalformedParametersException}.
     *
     * <p>
     *  使用空原因创建{@code MalformedParametersException}。
     * 
     * 
     * @param reason The reason for the exception.
     */
    public MalformedParametersException(String reason) {
        super(reason);
    }
}
