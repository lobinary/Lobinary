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

package javax.lang.model.util;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.EnumSet;
import java.util.ArrayList;
import java.util.LinkedHashSet;

import javax.lang.model.element.*;


/**
 * Filters for selecting just the elements of interest from a
 * collection of elements.  The returned sets and lists are new
 * collections and do use the argument as a backing store.  The
 * methods in this class do not make any attempts to guard against
 * concurrent modifications of the arguments.  The returned sets and
 * lists are mutable but unsafe for concurrent access.  A returned set
 * has the same iteration order as the argument set to a method.
 *
 * <p>If iterables and sets containing {@code null} are passed as
 * arguments to methods in this class, a {@code NullPointerException}
 * will be thrown.
 *
 * <p>Note that a <i>static import</i> statement can make the text of
 * calls to the methods in this class more concise; for example:
 *
 * <blockquote><pre>
 *     import static javax.lang.model.util.ElementFilter.*;
 *     ...
 *         {@code List<VariableElement>} fs = fieldsIn(someClass.getEnclosedElements());
 * </pre></blockquote>
 *
 * <p>
 *  用于从元素集合中仅选择感兴趣的元素的过滤器。返回的集合和列表是新集合,并且使用该参数作为后备存储。此类中的方法不会尝试防止同时修改参数。返回的集合和列表是可变的,但对并发访问不安全。
 * 返回的集具有与设置为方法的参数相同的迭代顺序。
 * 
 *  <p>如果包含{@code null}的迭代和集合作为参数传递给此类中的方法,那么将抛出{@code NullPointerException}。
 * 
 *  <p>请注意,<i>静态导入</i>语句可以使调用此类中的方法的文本更简洁;例如：
 * 
 *  <blockquote> <pre> import static javax.lang.model.util.ElementFilter。
 * *; ... {@code List <VariableElement>} fs = fieldsIn(someClass.getEnclosedElements()); </pre> </blockquote>
 * 。
 *  <blockquote> <pre> import static javax.lang.model.util.ElementFilter。
 * 
 * 
 * @author Joseph D. Darcy
 * @author Scott Seligman
 * @author Peter von der Ah&eacute;
 * @author Martin Buchholz
 * @since 1.6
 */
public class ElementFilter {
    private ElementFilter() {} // Do not instantiate.

    private static final Set<ElementKind> CONSTRUCTOR_KIND =
        Collections.unmodifiableSet(EnumSet.of(ElementKind.CONSTRUCTOR));

    private static final Set<ElementKind> FIELD_KINDS =
        Collections.unmodifiableSet(EnumSet.of(ElementKind.FIELD,
                                               ElementKind.ENUM_CONSTANT));
    private static final Set<ElementKind> METHOD_KIND =
        Collections.unmodifiableSet(EnumSet.of(ElementKind.METHOD));

    private static final Set<ElementKind> PACKAGE_KIND =
        Collections.unmodifiableSet(EnumSet.of(ElementKind.PACKAGE));

    private static final Set<ElementKind> TYPE_KINDS =
        Collections.unmodifiableSet(EnumSet.of(ElementKind.CLASS,
                                               ElementKind.ENUM,
                                               ElementKind.INTERFACE,
                                               ElementKind.ANNOTATION_TYPE));
    /**
     * Returns a list of fields in {@code elements}.
     * <p>
     *  返回{@code elements}中的字段列表。
     * 
     * 
     * @return a list of fields in {@code elements}
     * @param elements the elements to filter
     */
    public static List<VariableElement>
            fieldsIn(Iterable<? extends Element> elements) {
        return listFilter(elements, FIELD_KINDS, VariableElement.class);
    }

    /**
     * Returns a set of fields in {@code elements}.
     * <p>
     *  返回{@code elements}中的一组字段。
     * 
     * 
     * @return a set of fields in {@code elements}
     * @param elements the elements to filter
     */
    public static Set<VariableElement>
            fieldsIn(Set<? extends Element> elements) {
        return setFilter(elements, FIELD_KINDS, VariableElement.class);
    }

    /**
     * Returns a list of constructors in {@code elements}.
     * <p>
     *  返回{@code elements}中的构造函数列表。
     * 
     * 
     * @return a list of constructors in {@code elements}
     * @param elements the elements to filter
     */
    public static List<ExecutableElement>
            constructorsIn(Iterable<? extends Element> elements) {
        return listFilter(elements, CONSTRUCTOR_KIND, ExecutableElement.class);
    }

    /**
     * Returns a set of constructors in {@code elements}.
     * <p>
     *  返回一组{@code elements}中的构造函数。
     * 
     * 
     * @return a set of constructors in {@code elements}
     * @param elements the elements to filter
     */
    public static Set<ExecutableElement>
            constructorsIn(Set<? extends Element> elements) {
        return setFilter(elements, CONSTRUCTOR_KIND, ExecutableElement.class);
    }

    /**
     * Returns a list of methods in {@code elements}.
     * <p>
     *  返回{@code elements}中的方法列表。
     * 
     * 
     * @return a list of methods in {@code elements}
     * @param elements the elements to filter
     */
    public static List<ExecutableElement>
            methodsIn(Iterable<? extends Element> elements) {
        return listFilter(elements, METHOD_KIND, ExecutableElement.class);
    }

    /**
     * Returns a set of methods in {@code elements}.
     * <p>
     *  返回{@code elements}中的一组方法。
     * 
     * 
     * @return a set of methods in {@code elements}
     * @param elements the elements to filter
     */
    public static Set<ExecutableElement>
            methodsIn(Set<? extends Element> elements) {
        return setFilter(elements, METHOD_KIND, ExecutableElement.class);
    }

    /**
     * Returns a list of types in {@code elements}.
     * <p>
     *  返回{@code elements}中的类型列表。
     * 
     * 
     * @return a list of types in {@code elements}
     * @param elements the elements to filter
     */
    public static List<TypeElement>
            typesIn(Iterable<? extends Element> elements) {
        return listFilter(elements, TYPE_KINDS, TypeElement.class);
    }

    /**
     * Returns a set of types in {@code elements}.
     * <p>
     *  返回{@code elements}中的一组类型。
     * 
     * 
     * @return a set of types in {@code elements}
     * @param elements the elements to filter
     */
    public static Set<TypeElement>
            typesIn(Set<? extends Element> elements) {
        return setFilter(elements, TYPE_KINDS, TypeElement.class);
    }

    /**
     * Returns a list of packages in {@code elements}.
     * <p>
     *  返回{@code elements}中的软件包列表。
     * 
     * 
     * @return a list of packages in {@code elements}
     * @param elements the elements to filter
     */
    public static List<PackageElement>
            packagesIn(Iterable<? extends Element> elements) {
        return listFilter(elements, PACKAGE_KIND, PackageElement.class);
    }

    /**
     * Returns a set of packages in {@code elements}.
     * <p>
     * 返回{@code elements}中的一组包。
     * 
     * @return a set of packages in {@code elements}
     * @param elements the elements to filter
     */
    public static Set<PackageElement>
            packagesIn(Set<? extends Element> elements) {
        return setFilter(elements, PACKAGE_KIND, PackageElement.class);
    }

    // Assumes targetKinds and E are sensible.
    private static <E extends Element> List<E> listFilter(Iterable<? extends Element> elements,
                                                          Set<ElementKind> targetKinds,
                                                          Class<E> clazz) {
        List<E> list = new ArrayList<E>();
        for (Element e : elements) {
            if (targetKinds.contains(e.getKind()))
                list.add(clazz.cast(e));
        }
        return list;
    }

    // Assumes targetKinds and E are sensible.
    private static <E extends Element> Set<E> setFilter(Set<? extends Element> elements,
                                                        Set<ElementKind> targetKinds,
                                                        Class<E> clazz) {
        // Return set preserving iteration order of input set.
        Set<E> set = new LinkedHashSet<E>();
        for (Element e : elements) {
            if (targetKinds.contains(e.getKind()))
                set.add(clazz.cast(e));
        }
        return set;
    }
}
