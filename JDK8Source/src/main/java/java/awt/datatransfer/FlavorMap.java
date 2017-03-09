/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2013, Oracle and/or its affiliates. All rights reserved.
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

import java.util.Map;


/**
 * A two-way Map between "natives" (Strings), which correspond to platform-
 * specific data formats, and "flavors" (DataFlavors), which correspond to
 * platform-independent MIME types. FlavorMaps need not be symmetric, but
 * typically are.
 *
 *
 * <p>
 *  对应于平台特定数据格式的"本地"(Strings)和对应于平台无关MIME类型的"flavor"(DataFlavors)之间的双向映射。 FlavorMaps不需要是对称的,但通常是。
 * 
 * 
 * @since 1.2
 */
public interface FlavorMap {

    /**
     * Returns a <code>Map</code> of the specified <code>DataFlavor</code>s to
     * their corresponding <code>String</code> native. The returned
     * <code>Map</code> is a modifiable copy of this <code>FlavorMap</code>'s
     * internal data. Client code is free to modify the <code>Map</code>
     * without affecting this object.
     *
     * <p>
     *  将指定的<code> DataFlavor </code>的<code> Map </code>返回到其对应的<code> String </code>本机。
     * 返回的<code> Map </code>是这个<code> FlavorMap </code>的内部数据的可修改副本。客户端代码可以自由修改<code> Map </code>,而不影响此对象。
     * 
     * 
     * @param flavors an array of <code>DataFlavor</code>s which will be the
     *        key set of the returned <code>Map</code>. If <code>null</code> is
     *        specified, a mapping of all <code>DataFlavor</code>s currently
     *        known to this <code>FlavorMap</code> to their corresponding
     *        <code>String</code> natives will be returned.
     * @return a <code>java.util.Map</code> of <code>DataFlavor</code>s to
     *         <code>String</code> natives
     */
    Map<DataFlavor,String> getNativesForFlavors(DataFlavor[] flavors);

    /**
     * Returns a <code>Map</code> of the specified <code>String</code> natives
     * to their corresponding <code>DataFlavor</code>. The returned
     * <code>Map</code> is a modifiable copy of this <code>FlavorMap</code>'s
     * internal data. Client code is free to modify the <code>Map</code>
     * without affecting this object.
     *
     * <p>
     *  将指定的<code> String </code> natives的<code> Map </code>返回到其相应的<code> DataFlavor </code>。
     * 返回的<code> Map </code>是这个<code> FlavorMap </code>的内部数据的可修改副本。客户端代码可以自由修改<code> Map </code>,而不影响此对象。
     * 
     * @param natives an array of <code>String</code>s which will be the
     *        key set of the returned <code>Map</code>. If <code>null</code> is
     *        specified, a mapping of all <code>String</code> natives currently
     *        known to this <code>FlavorMap</code> to their corresponding
     *        <code>DataFlavor</code>s will be returned.
     * @return a <code>java.util.Map</code> of <code>String</code> natives to
     *         <code>DataFlavor</code>s
     */
    Map<String,DataFlavor> getFlavorsForNatives(String[] natives);
}
