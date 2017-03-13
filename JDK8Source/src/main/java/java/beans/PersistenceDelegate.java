/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2013, Oracle and/or its affiliates. All rights reserved.
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
package java.beans;

/**
 * The PersistenceDelegate class takes the responsibility
 * for expressing the state of an instance of a given class
 * in terms of the methods in the class's public API. Instead
 * of associating the responsibility of persistence with
 * the class itself as is done, for example, by the
 * <code>readObject</code> and <code>writeObject</code>
 * methods used by the <code>ObjectOutputStream</code>, streams like
 * the <code>XMLEncoder</code> which
 * use this delegation model can have their behavior controlled
 * independently of the classes themselves. Normally, the class
 * is the best place to put such information and conventions
 * can easily be expressed in this delegation scheme to do just that.
 * Sometimes however, it is the case that a minor problem
 * in a single class prevents an entire object graph from
 * being written and this can leave the application
 * developer with no recourse but to attempt to shadow
 * the problematic classes locally or use alternative
 * persistence techniques. In situations like these, the
 * delegation model gives a relatively clean mechanism for
 * the application developer to intervene in all parts of the
 * serialization process without requiring that modifications
 * be made to the implementation of classes which are not part
 * of the application itself.
 * <p>
 * In addition to using a delegation model, this persistence
 * scheme differs from traditional serialization schemes
 * in requiring an analog of the <code>writeObject</code>
 * method without a corresponding <code>readObject</code>
 * method. The <code>writeObject</code> analog encodes each
 * instance in terms of its public API and there is no need to
 * define a <code>readObject</code> analog
 * since the procedure for reading the serialized form
 * is defined by the semantics of method invocation as laid
 * out in the Java Language Specification.
 * Breaking the dependency between <code>writeObject</code>
 * and <code>readObject</code> implementations, which may
 * change from version to version, is the key factor
 * in making the archives produced by this technique immune
 * to changes in the private implementations of the classes
 * to which they refer.
 * <p>
 * A persistence delegate, may take control of all
 * aspects of the persistence of an object including:
 * <ul>
 * <li>
 * Deciding whether or not an instance can be mutated
 * into another instance of the same class.
 * <li>
 * Instantiating the object, either by calling a
 * public constructor or a public factory method.
 * <li>
 * Performing the initialization of the object.
 * </ul>
 * <p>
 * PersistenceDelegate类负责根据类的公共API中的方法表达给定类的实例的状态而不是将持久性的责任与类本身相关联,例如通过<code>由<code> ObjectOutputStream 
 * </code>使用的readObject </code>和<code> writeObject </code>方法,像使用这个委托模型的<code> XMLEncoder </code>的流可以独立于类
 * 本身通常,类是最好的地方放这样的信息和约定可以很容易地表示在这个授权方案做然而,有时候,单个类中的小问题会阻止整个对象图形被写入,这可能使应用程序开发人员无法追索,而是尝试在本地隐藏有问题的类,或者使用
 * 替代的持久化技术。
 * 这些,授权模型为应用程序开发人员提供了一个相对干净的机制来干预序列化过程的所有部分,而不需要修改不是应用程序本身的一部分的类的实现。
 * <p>
 * 除了使用委托模型,该持久化方案不同于传统的串行化方案,需要不具有相应的<code> readObject </code>方法的<code> writeObject </code>方法的模拟<code> 
 * writeObject <代码>根据其公共API对每个实例进行模拟编码,并且不需要定义<code> readObject </code>模拟,因为用于读取序列化形式的过程由方法调用的语义定义,如Java
 * 语言规范破坏<code> writeObject </code>和<code> readObject </code>实现之间的依赖关系是可能随着版本的不同而改变的关键因素,他们所指的类。
 * <p>
 * 持久性委托,可以控制对象的持久性的所有方面,包括：
 * <ul>
 * <li>
 *  决定是否可以将实例变更为同一类别的另一个实例
 * <li>
 *  通过调用公共构造函数或公共工厂方法实例化对象
 * <li>
 *  执行对象的初始化
 * </ul>
 * 
 * @see XMLEncoder
 *
 * @since 1.4
 *
 * @author Philip Milne
 */

public abstract class PersistenceDelegate {

    /**
     * The <code>writeObject</code> is a single entry point to the persistence
     * and is used by a <code>Encoder</code> in the traditional
     * mode of delegation. Although this method is not final,
     * it should not need to be subclassed under normal circumstances.
     * <p>
     * This implementation first checks to see if the stream
     * has already encountered this object. Next the
     * <code>mutatesTo</code> method is called to see if
     * that candidate returned from the stream can
     * be mutated into an accurate copy of <code>oldInstance</code>.
     * If it can, the <code>initialize</code> method is called to
     * perform the initialization. If not, the candidate is removed
     * from the stream, and the <code>instantiate</code> method
     * is called to create a new candidate for this object.
     *
     * <p>
     *  <code> writeObject </code>是对持久性的单个入口点,并由传统的委托模式中的<code> Encoder </code>使用。虽然此方法不是最终的,但它不需要是子类正常情况下
     * <p>
     * 这个实现首先检查流是否已经遇到这个对象接下来调用<code> mutatesTo </code>方法来查看从流返回的候选者是否可以被突变为<code> oldInstance </code>代码>如果可
     * 以,调用<code> initialize </code>方法来执行初始化。
     * 如果没有,则从流中删除候选项,并调用<code> instantiate </code>方法创建一个新的此对象的候选项。
     * 
     * 
     * @param oldInstance The instance that will be created by this expression.
     * @param out The stream to which this expression will be written.
     *
     * @throws NullPointerException if {@code out} is {@code null}
     */
    public void writeObject(Object oldInstance, Encoder out) {
        Object newInstance = out.get(oldInstance);
        if (!mutatesTo(oldInstance, newInstance)) {
            out.remove(oldInstance);
            out.writeExpression(instantiate(oldInstance, out));
        }
        else {
            initialize(oldInstance.getClass(), oldInstance, newInstance, out);
        }
    }

    /**
     * Returns true if an <em>equivalent</em> copy of <code>oldInstance</code> may be
     * created by applying a series of statements to <code>newInstance</code>.
     * In the specification of this method, we mean by equivalent that the modified instance
     * is indistinguishable from <code>oldInstance</code> in the behavior
     * of the relevant methods in its public API. [Note: we use the
     * phrase <em>relevant</em> methods rather than <em>all</em> methods
     * here only because, to be strictly correct, methods like <code>hashCode</code>
     * and <code>toString</code> prevent most classes from producing truly
     * indistinguishable copies of their instances].
     * <p>
     * The default behavior returns <code>true</code>
     * if the classes of the two instances are the same.
     *
     * <p>
     * 如果<code> oldInstance </code>的<em>等效</em>副本可以通过将一系列语句应用于<code> newInstance </code>来创建,则返回true。
     * 在此方法的规范中,等同于修改的实例在其公共API中的相关方法的行为中与<code> oldInstance </code>不可区分[注意：我们使用短语<em>相关</em>方法而不是< / em>方法只
     * 是因为,为了严格正确,像<code> hashCode </code>和<code> toString </code>等方法可以防止大多数类生成其实例的真正无法区分的副本]。
     * 如果<code> oldInstance </code>的<em>等效</em>副本可以通过将一系列语句应用于<code> newInstance </code>来创建,则返回true。
     * <p>
     *  如果两个实例的类相同,则默认行为返回<code> true </code>
     * 
     * 
     * @param oldInstance The instance to be copied.
     * @param newInstance The instance that is to be modified.
     * @return True if an equivalent copy of <code>newInstance</code> may be
     *         created by applying a series of mutations to <code>oldInstance</code>.
     */
    protected boolean mutatesTo(Object oldInstance, Object newInstance) {
        return (newInstance != null && oldInstance != null &&
                oldInstance.getClass() == newInstance.getClass());
    }

    /**
     * Returns an expression whose value is <code>oldInstance</code>.
     * This method is used to characterize the constructor
     * or factory method that should be used to create the given object.
     * For example, the <code>instantiate</code> method of the persistence
     * delegate for the <code>Field</code> class could be defined as follows:
     * <pre>
     * Field f = (Field)oldInstance;
     * return new Expression(f, f.getDeclaringClass(), "getField", new Object[]{f.getName()});
     * </pre>
     * Note that we declare the value of the returned expression so that
     * the value of the expression (as returned by <code>getValue</code>)
     * will be identical to <code>oldInstance</code>.
     *
     * <p>
     * 返回值为<code> oldInstance </code>的表达式此方法用于表征应用于创建给定对象的构造函数或工厂方法。
     * 例如,持久性的<code> instantiate </code>方法<code> Field </code>类的委托可以定义如下：。
     * <pre>
     *  字段f =(Field)oldInstance; return new Expression(f,fgetDeclaringClass(),"getField",new Object [] {fgetName()}
     * );。
     * </pre>
     *  注意,我们声明返回的表达式的值,使得表达式的值(由<code> getValue </code>返回)与<code> oldInstance </code>
     * 
     * 
     * @param oldInstance The instance that will be created by this expression.
     * @param out The stream to which this expression will be written.
     * @return An expression whose value is <code>oldInstance</code>.
     *
     * @throws NullPointerException if {@code out} is {@code null}
     *                              and this value is used in the method
     */
    protected abstract Expression instantiate(Object oldInstance, Encoder out);

    /**
     * Produce a series of statements with side effects on <code>newInstance</code>
     * so that the new instance becomes <em>equivalent</em> to <code>oldInstance</code>.
     * In the specification of this method, we mean by equivalent that, after the method
     * returns, the modified instance is indistinguishable from
     * <code>newInstance</code> in the behavior of all methods in its
     * public API.
     * <p>
     * The implementation typically achieves this goal by producing a series of
     * "what happened" statements involving the <code>oldInstance</code>
     * and its publicly available state. These statements are sent
     * to the output stream using its <code>writeExpression</code>
     * method which returns an expression involving elements in
     * a cloned environment simulating the state of an input stream during
     * reading. Each statement returned will have had all instances
     * the old environment replaced with objects which exist in the new
     * one. In particular, references to the target of these statements,
     * which start out as references to <code>oldInstance</code> are returned
     * as references to the <code>newInstance</code> instead.
     * Executing these statements effects an incremental
     * alignment of the state of the two objects as a series of
     * modifications to the objects in the new environment.
     * By the time the initialize method returns it should be impossible
     * to tell the two instances apart by using their public APIs.
     * Most importantly, the sequence of steps that were used to make
     * these objects appear equivalent will have been recorded
     * by the output stream and will form the actual output when
     * the stream is flushed.
     * <p>
     * The default implementation, calls the <code>initialize</code>
     * method of the type's superclass.
     *
     * <p>
     * 在<code> newInstance </code>上产生一系列带有副作用的语句,以使新实例变为<em>等效于<code> oldInstance </code>。
     * 在此方法的规范中,等效于,在方法返回之后,修改的实例在其公共API中的所有方法的行为中与<code> newInstance </code>不可区分。
     * <p>
     * 实现通常通过产生一系列涉及<code> oldInstance </code>及其公开可用状态的"发生的"语句来实现这个目标。
     * 这些语句使用其<code> writeExpression </code>方法发送到输出流它返回一个涉及克隆环境中的元素的表达式,它在读取期间模拟输入流的状态每个返回的语句都会将旧环境替换为存在于新对象
     * 中的对象。
     * 实现通常通过产生一系列涉及<code> oldInstance </code>及其公开可用状态的"发生的"语句来实现这个目标。
     * 
     * @param type the type of the instances
     * @param oldInstance The instance to be copied.
     * @param newInstance The instance that is to be modified.
     * @param out The stream to which any initialization statements should be written.
     *
     * @throws NullPointerException if {@code out} is {@code null}
     */
    protected void initialize(Class<?> type,
                              Object oldInstance, Object newInstance,
                              Encoder out)
    {
        Class<?> superType = type.getSuperclass();
        PersistenceDelegate info = out.getPersistenceDelegate(superType);
        info.initialize(superType, oldInstance, newInstance, out);
    }
}
