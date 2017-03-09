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

package javax.tools;

import javax.lang.model.element.NestingKind;
import javax.lang.model.element.Modifier;

/**
 * File abstraction for tools operating on Java&trade; programming language
 * source and class files.
 *
 * <p>All methods in this interface might throw a SecurityException if
 * a security exception occurs.
 *
 * <p>Unless explicitly allowed, all methods in this interface might
 * throw a NullPointerException if given a {@code null} argument.
 *
 * <p>
 *  Java和贸易上运行的工具的文件抽象;编程语言源和类文件。
 * 
 *  <p>如果发生安全性异常,此接口中的所有方法都可能抛出SecurityException。
 * 
 *  <p>除非明确允许,否则如果给定{@code null}参数,此接口中的所有方法都可能抛出NullPointerException。
 * 
 * 
 * @author Peter von der Ah&eacute;
 * @author Jonathan Gibbons
 * @see JavaFileManager
 * @since 1.6
 */
public interface JavaFileObject extends FileObject {

    /**
     * Kinds of JavaFileObjects.
     * <p>
     *  种类的JavaFileObjects。
     * 
     */
    enum Kind {
        /**
         * Source files written in the Java programming language.  For
         * example, regular files ending with {@code .java}.
         * <p>
         *  用Java编程语言编写的源文件。例如,以{@code .java}结尾的常规文件。
         * 
         */
        SOURCE(".java"),

        /**
         * Class files for the Java Virtual Machine.  For example,
         * regular files ending with {@code .class}.
         * <p>
         *  Java虚拟机的类文件。例如,以{@code .class}结尾的常规文件。
         * 
         */
        CLASS(".class"),

        /**
         * HTML files.  For example, regular files ending with {@code
         * .html}.
         * <p>
         *  HTML文件。例如,以{@code .html}结尾的常规文件。
         * 
         */
        HTML(".html"),

        /**
         * Any other kind.
         * <p>
         *  任何其他种类。
         * 
         */
        OTHER("");
        /**
         * The extension which (by convention) is normally used for
         * this kind of file object.  If no convention exists, the
         * empty string ({@code ""}) is used.
         * <p>
         *  (按惯例)通常用于这种文件对象的扩展。如果不存在约定,则使用空字符串({@code""})。
         * 
         */
        public final String extension;
        private Kind(String extension) {
            extension.getClass(); // null check
            this.extension = extension;
        }
    };

    /**
     * Gets the kind of this file object.
     *
     * <p>
     *  获取此文件对象的种类。
     * 
     * 
     * @return the kind
     */
    Kind getKind();

    /**
     * Checks if this file object is compatible with the specified
     * simple name and kind.  A simple name is a single identifier
     * (not qualified) as defined in
     * <cite>The Java&trade; Language Specification</cite>,
     * section 6.2 "Names and Identifiers".
     *
     * <p>
     *  检查此文件对象是否与指定的简单名称和类型兼容。一个简单的名字是一个单一的标识符(不合格)在<cite> Java&trade;语言规范</cite>,第6.2节"名称和标识符"。
     * 
     * 
     * @param simpleName a simple name of a class
     * @param kind a kind
     * @return {@code true} if this file object is compatible; false
     * otherwise
     */
    boolean isNameCompatible(String simpleName, Kind kind);

    /**
     * Provides a hint about the nesting level of the class
     * represented by this file object.  This method may return
     * {@link NestingKind#MEMBER} to mean
     * {@link NestingKind#LOCAL} or {@link NestingKind#ANONYMOUS}.
     * If the nesting level is not known or this file object does not
     * represent a class file this method returns {@code null}.
     *
     * <p>
     * 提供关于此文件对象表示的类的嵌套级别的提示。
     * 此方法可能会传回{@link NestingKind#MEMBER},表示{@link NestingKind#LOCAL}或{@link NestingKind#ANONYMOUS}。
     * 如果嵌套级别未知或此文件对象不表示类文件,此方法将返回{@code null}。
     * 
     * 
     * @return the nesting kind, or {@code null} if the nesting kind
     * is not known
     */
    NestingKind getNestingKind();

    /**
     * Provides a hint about the access level of the class represented
     * by this file object.  If the access level is not known or if
     * this file object does not represent a class file this method
     * returns {@code null}.
     *
     * <p>
     * 
     * @return the access level
     */
    Modifier getAccessLevel();

}
