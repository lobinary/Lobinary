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
 * Represents a supplier of {@code int}-valued results.  This is the
 * {@code int}-producing primitive specialization of {@link Supplier}.
 *
 * <p>There is no requirement that a distinct result be returned each
 * time the supplier is invoked.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #getAsInt()}.
 *
 * <p>
 *  表示{@code int}值结果的供应商。这是{@link供应商}的{@code int}生成原语专业化。
 * 
 *  <p>不需要在每次调用供应商时返回不同的结果。
 * 
 *  <p>这是一个<a href="package-summary.html">功能介面</a>,其功能方法为{@link #getAsInt()}。
 * 
 * @see Supplier
 * @since 1.8
 */
@FunctionalInterface
public interface IntSupplier {

    /**
     * Gets a result.
     *
     * <p>
     * 
     * 
     * @return a result
     */
    int getAsInt();
}
