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
 * Represents a supplier of {@code double}-valued results.  This is the
 * {@code double}-producing primitive specialization of {@link Supplier}.
 *
 * <p>There is no requirement that a distinct result be returned each
 * time the supplier is invoked.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #getAsDouble()}.
 *
 * <p>
 *  表示{@code double}值结果的供应商。这是{@link Supplier}的{@code double}生成原语专业化。
 * 
 *  <p>不需要在每次调用供应商时返回不同的结果。
 * 
 *  <p>这是一个<a href="package-summary.html">功能介面</a>,其功能方法是{@link #getAsDouble()}。
 * 
 * @see Supplier
 * @since 1.8
 */
@FunctionalInterface
public interface DoubleSupplier {

    /**
     * Gets a result.
     *
     * <p>
     * 
     * 
     * @return a result
     */
    double getAsDouble();
}
