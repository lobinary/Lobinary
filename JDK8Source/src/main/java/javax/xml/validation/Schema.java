/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, 2005, Oracle and/or its affiliates. All rights reserved.
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

package javax.xml.validation;

/**
 * Immutable in-memory representation of grammar.
 *
 * <p>
 * This object represents a set of constraints that can be checked/
 * enforced against an XML document.
 *
 * <p>
 * A {@link Schema} object is thread safe and applications are
 * encouraged to share it across many parsers in many threads.
 *
 * <p>
 * A {@link Schema} object is immutable in the sense that it shouldn't
 * change the set of constraints once it is created. In other words,
 * if an application validates the same document twice against the same
 * {@link Schema}, it must always produce the same result.
 *
 * <p>
 * A {@link Schema} object is usually created from {@link SchemaFactory}.
 *
 * <p>
 * Two kinds of validators can be created from a {@link Schema} object.
 * One is {@link Validator}, which provides highly-level validation
 * operations that cover typical use cases. The other is
 * {@link ValidatorHandler}, which works on top of SAX for better
 * modularity.
 *
 * <p>
 * This specification does not refine
 * the {@link java.lang.Object#equals(java.lang.Object)} method.
 * In other words, if you parse the same schema twice, you may
 * still get <code>!schemaA.equals(schemaB)</code>.
 *
 * <p>
 *  不可变的内存中的语法表示。
 * 
 * <p>
 *  此对象表示可针对XML文档检查/实施的一组约束。
 * 
 * <p>
 *  {@link Schema}对象是线程安全的,并且鼓励应用程序在许多线程中的许多解析器之间共享它。
 * 
 * <p>
 *  {@link Schema}对象在创建之后不应更改约束集合的意义上是不可变的。换句话说,如果应用程序对同一{@link Schema}验证同一文档两次,它必须总是产生相同的结果。
 * 
 * <p>
 *  {@link Schema}对象通常是从{@link SchemaFactory}创建的。
 * 
 * <p>
 *  可以从{@link Schema}对象创建两种验证器。一个是{@link Validator},它提供了涵盖典型用例的高级验证操作。
 * 另一个是{@link ValidatorHandler},它在SAX的顶层工作,以实现更好的模块化。
 * 
 * <p>
 *  此规范不会细化{@link java.lang.Object#equals(java.lang.Object)}方法。
 * 
 * @author <a href="mailto:Kohsuke.Kawaguchi@Sun.com">Kohsuke Kawaguchi</a>
 * @see <a href="http://www.w3.org/TR/xmlschema-1/">XML Schema Part 1: Structures</a>
 * @see <a href="http://www.w3.org/TR/xml11/">Extensible Markup Language (XML) 1.1</a>
 * @see <a href="http://www.w3.org/TR/REC-xml">Extensible Markup Language (XML) 1.0 (Second Edition)</a>
 * @since 1.5
 */
public abstract class Schema {

    /**
     * Constructor for the derived class.
     *
     * <p>
     * The constructor does nothing.
     * <p>
     * 换句话说,如果你解析相同的模式两次,你仍然可以得到<code>！schemaA.equals(schemaB)</code>。
     * 
     */
    protected Schema() {
    }

    /**
     * Creates a new {@link Validator} for this {@link Schema}.
     *
     * <p>A validator enforces/checks the set of constraints this object
     * represents.</p>
     *
     * <p>Implementors should assure that the properties set on the
     * {@link SchemaFactory} that created this {@link Schema} are also
     * set on the {@link Validator} constructed.</p>
     *
     * <p>
     *  派生类的构造方法。
     * 
     * <p>
     *  构造函数什么也不做。
     * 
     * 
     * @return
     *      Always return a non-null valid object.
     */
    public abstract Validator newValidator();

    /**
     * Creates a new {@link ValidatorHandler} for this {@link Schema}.
     *
     * <p>Implementors should assure that the properties set on the
     * {@link SchemaFactory} that created this {@link Schema} are also
     * set on the {@link ValidatorHandler} constructed.</p>
     *
     * <p>
     *  为此{@link架构}创建一个新的{@link Validator}。
     * 
     *  <p>验证器强制/检查此对象所代表的约束集。</p>
     * 
     * <p>实施者应确保在{@link SchemaFactory}上创建此{@link Schema}的属性集也在{@link Validator}构建。</p>
     * 
     * 
     * @return
     *      Always return a non-null valid object.
     */
    public abstract ValidatorHandler newValidatorHandler();
}
