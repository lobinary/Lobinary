/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2012, 2013, Oracle and/or its affiliates. All rights reserved.
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
package java.util.function;


/**
 * Represents a supplier of {@code boolean}-valued results.  This is the
 * {@code boolean}-producing primitive specialization of {@link Supplier}.
 *
 * <p>There is no requirement that a new or distinct result be returned each
 * time the supplier is invoked.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #getAsBoolean()}.
 *
 * <p>
 *  表示{@code boolean}值结果的供应商。这是{@link Boolean}  - 生成{@link Supplier}的原始专业化。
 * 
 *  <p>不要求每次调用供应商时返回新的或不同的结果。
 * 
 *  <p>这是一个<a href="package-summary.html">功能介面</a>,其功能方法为{@link #getAsBoolean()}。
 * 
 * @see Supplier
 * @since 1.8
 */
@FunctionalInterface
public interface BooleanSupplier {

    /**
     * Gets a result.
     *
     * <p>
     * 
     * 
     * @return a result
     */
    boolean getAsBoolean();
}
