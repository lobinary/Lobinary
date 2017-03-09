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

package javax.lang.model.element;

import java.util.List;

/**
 * Represents a package program element.  Provides access to information
 * about the package and its members.
 *
 * <p>
 *  表示包程序元素。提供对包及其成员的信息的访问。
 * 
 * 
 * @author Joseph D. Darcy
 * @author Scott Seligman
 * @author Peter von der Ah&eacute;
 * @see javax.lang.model.util.Elements#getPackageOf
 * @since 1.6
 */
public interface PackageElement extends Element, QualifiedNameable {

    /**
     * Returns the fully qualified name of this package.
     * This is also known as the package's <i>canonical</i> name.
     *
     * <p>
     *  返回此软件包的完全限定名称。这也称为包的<i>规范</i>名称。
     * 
     * 
     * @return the fully qualified name of this package, or an
     * empty name if this is an unnamed package
     * @jls 6.7 Fully Qualified Names and Canonical Names
     */
    Name getQualifiedName();

    /**
     * Returns the simple name of this package.  For an unnamed
     * package, an empty name is returned.
     *
     * <p>
     *  返回此包的简单名称。对于未命名的包,将返回一个空名称。
     * 
     * 
     * @return the simple name of this package or an empty name if
     * this is an unnamed package
     */
    @Override
    Name getSimpleName();

    /**
     * Returns the {@linkplain NestingKind#TOP_LEVEL top-level}
     * classes and interfaces within this package.  Note that
     * subpackages are <em>not</em> considered to be enclosed by a
     * package.
     *
     * <p>
     *  返回此包中的{@linkplain NestingKind#TOP_LEVEL顶级}类和接口。请注意,子包不是</em>视为包含在包中。
     * 
     * 
     * @return the top-level classes and interfaces within this
     * package
     */
    @Override
    List<? extends Element> getEnclosedElements();

    /**
     * Returns {@code true} is this is an unnamed package and {@code
     * false} otherwise.
     *
     * <p>
     *  返回{@code true}这是一个未命名的包,否则为{@code false}。
     * 
     * 
     * @return {@code true} is this is an unnamed package and {@code
     * false} otherwise
     * @jls 7.4.2 Unnamed Packages
     */
    boolean isUnnamed();

    /**
     * Returns {@code null} since a package is not enclosed by another
     * element.
     *
     * <p>
     *  返回{@code null},因为包没有被另一个元素包围。
     * 
     * @return {@code null}
     */
    @Override
    Element getEnclosingElement();
}
