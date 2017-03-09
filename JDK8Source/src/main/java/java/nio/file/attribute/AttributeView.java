/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.nio.file.attribute;

/**
 * An object that provides a read-only or updatable <em>view</em> of non-opaque
 * values associated with an object in a filesystem. This interface is extended
 * or implemented by specific attribute views that define the attributes
 * supported by the view. A specific attribute view will typically define
 * type-safe methods to read or update the attributes that it supports.
 *
 * <p>
 *  提供与文件系统中的对象相关联的非不透明值的只读或可更新<em>视图</em>的对象。此接口由定义视图支持的属性的特定属性视图扩展或实现。
 * 特定的属性视图通常会定义类型安全的方法来读取或更新它支持的属性。
 * 
 * 
 * @since 1.7
 */

public interface AttributeView {
    /**
     * Returns the name of the attribute view.
     *
     * <p>
     * 
     * @return the name of the attribute view
     */
    String name();
}
