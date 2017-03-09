/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2004, Oracle and/or its affiliates. All rights reserved.
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

package java.awt.datatransfer;

import java.util.List;


/**
 * A FlavorMap which relaxes the traditional 1-to-1 restriction of a Map. A
 * flavor is permitted to map to any number of natives, and likewise a native
 * is permitted to map to any number of flavors. FlavorTables need not be
 * symmetric, but typically are.
 *
 * <p>
 *  A FlavorMap放松了地图的传统1对1限制。口味允许映射到任何数量的本地人,同样地,允许本地人映射到任何数量的口味。风味表不需要是对称的,但通常是。
 * 
 * 
 * @author David Mendenhall
 *
 * @since 1.4
 */
public interface FlavorTable extends FlavorMap {

    /**
     * Returns a <code>List</code> of <code>String</code> natives to which the
     * specified <code>DataFlavor</code> corresponds. The <code>List</code>
     * will be sorted from best native to worst. That is, the first native will
     * best reflect data in the specified flavor to the underlying native
     * platform. The returned <code>List</code> is a modifiable copy of this
     * <code>FlavorTable</code>'s internal data. Client code is free to modify
     * the <code>List</code> without affecting this object.
     *
     * <p>
     *  返回指定的<code> DataFlavor </code>对应的<code> String </code>本地代码的<code> List </code> <code> List </code>将从
     * 最好的本机到最差。
     * 也就是说,第一本地人将最好地将指定风格中的数据反映到底层本地平台。返回的<code> List </code>是此<code> FlavorTable </code>的内部数据的可修改副本。
     * 客户端代码可以自由修改<code> List </code>,而不会影响此对象。
     * 
     * 
     * @param flav the <code>DataFlavor</code> whose corresponding natives
     *        should be returned. If <code>null</code> is specified, all
     *        natives currently known to this <code>FlavorTable</code> are
     *        returned in a non-deterministic order.
     * @return a <code>java.util.List</code> of <code>java.lang.String</code>
     *         objects which are platform-specific representations of platform-
     *         specific data formats
     */
    List<String> getNativesForFlavor(DataFlavor flav);

    /**
     * Returns a <code>List</code> of <code>DataFlavor</code>s to which the
     * specified <code>String</code> corresponds. The <code>List</code> will be
     * sorted from best <code>DataFlavor</code> to worst. That is, the first
     * <code>DataFlavor</code> will best reflect data in the specified
     * native to a Java application. The returned <code>List</code> is a
     * modifiable copy of this <code>FlavorTable</code>'s internal data.
     * Client code is free to modify the <code>List</code> without affecting
     * this object.
     *
     * <p>
     *  返回指定的<code> String </code>对应的<code> DataFlavor </code>的<code> List </code> <code> List </code>将从最佳<code>
     *  DataFlavor </code>中排序到最差。
     * 也就是说,第一个<code> DataFlavor </code>将最好地将指定本机中的数据反映到Java应用程序中。
     * 
     * @param nat the native whose corresponding <code>DataFlavor</code>s
     *        should be returned. If <code>null</code> is specified, all
     *        <code>DataFlavor</code>s currently known to this
     *        <code>FlavorTable</code> are returned in a non-deterministic
     *        order.
     * @return a <code>java.util.List</code> of <code>DataFlavor</code>
     *         objects into which platform-specific data in the specified,
     *         platform-specific native can be translated
     */
    List<DataFlavor> getFlavorsForNative(String nat);
}
