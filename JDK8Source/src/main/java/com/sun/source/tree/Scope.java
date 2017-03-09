/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2006, 2013, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.source.tree;

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;

/**
 * Interface for determining locally available program elements, such as
 * local variables and imports.
 * Upon creation, a Scope is associated with a given program position;
 * for example, a {@linkplain Tree tree node}. This position may be used to
 * infer an enclosing method and/or class.
 *
 * <p>A Scope does not itself contain the details of the elements corresponding
 * to the parameters, methods and fields of the methods and classes containing
 * its position. However, these elements can be determined from the enclosing
 * elements.
 *
 * <p>Scopes may be contained in an enclosing scope. The outermost scope contains
 * those elements available via "star import" declarations; the scope within that
 * contains the top level elements of the compilation unit, including any named
 * imports.
 *
 * <p>
 *  用于确定本地可用程序元素的接口,例如局部变量和导入。创建后,范围与给定的程序位置相关联;例如,{@linkplain Tree tree node}。该位置可以用于推断封闭方法和/或类。
 * 
 *  <p>范围本身不包含与包含其位置的方法和类的参数,方法和字段对应的元素的详细信息。然而,这些元件可以从包围元件确定。
 * 
 *  <p>范围可能包含在封闭范围内。最外层的范围包含通过"star import"声明可用的那些元素;其中的范围包含编译单元的顶级元素,包括任何命名的导入。
 * 
 * 
 * @since 1.6
 */
@jdk.Exported
public interface Scope {
    /**
     * Returns the enclosing scope.
     * <p>
     *  返回包围范围。
     * 
     */
    public Scope getEnclosingScope();

    /**
     * Returns the innermost type element containing the position of this scope
     * <p>
     *  返回包含此范围位置的最内层类型元素
     * 
     */
    public TypeElement getEnclosingClass();

    /**
     * Returns the innermost executable element containing the position of this scope.
     * <p>
     *  返回包含此范围位置的最内层可执行元素。
     * 
     */
    public ExecutableElement getEnclosingMethod();

    /**
     * Returns the elements directly contained in this scope.
     * <p>
     *  返回直接包含在此作用域中的元素。
     */
    public Iterable<? extends Element> getLocalElements();
}
