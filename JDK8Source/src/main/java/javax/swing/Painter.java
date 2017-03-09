/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2005, 2013, Oracle and/or its affiliates. All rights reserved.
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
package javax.swing;

import java.awt.Graphics2D;

/**
 * <p>A painting delegate. The Painter interface defines exactly one method,
 * <code>paint</code>. It is used in situations where the developer can change
 * the painting routine of a component without having to resort to subclassing
 * the component. It is also generically useful when doing any form of painting
 * delegation.</p>
 *
 * <p><code>Painter</code>s are simply encapsulations of Java2D code and make
 * it fairly trivial to reuse existing <code>Painter</code>s or to combine
 * them together. Implementations of this interface are also trivial to write,
 * such that if you can't find a <code>Painter</code> that does what you need,
 * you can write one with minimal effort. Writing a <code>Painter</code> requires
 * knowledge of Java2D.</p>
 *
 * <p>A <code>Painter</code> may be created with a type parameter. This type will be
 * expected in the <code>paint</code> method. For example, you may wish to write a
 * <code>Painter</code> that only works with subclasses of {@link java.awt.Component}.
 * In that case, when the <code>Painter</code> is declared, you may declare that
 * it requires a <code>Component</code>, allowing the paint method to be type safe. Ex:
 * <pre>
 * {@code
 * Painter<Component> p = new Painter<Component>() {
 *     public void paint(Graphics2D g, Component c, int width, int height) {
 *         g.setColor(c.getBackground());
 *         //and so forth
 *     }
 * }
 * }
 * </pre>
 *
 * <p>This interface makes no guarantees of threadsafety.</p>
 *
 * <p>
 *  <p>绘画代表。 Painter接口只定义一个方法,<code> paint </code>。它用于开发人员可以更改组件的绘制例程而不必求助于对该组件进行子类化的情况。
 * 在进行任何形式的绘画委托时,它也是非常有用的。</p>。
 * 
 *  <p> <code> Painter </code>是Java2D代码的简单封装,使得重用现有<code> Painter </code>或将它们组合在一起变得相当简单。
 * 这个接口的实现也很容易写,所以如果你找不到你需要的<code> Painter </code>,你可以用最少的努力写一个。编写<code> Painter </code>需要了解Java2D。
 * </p>。
 * 
 *  <p>可以使用type参数创建<code> Painter </code>。此类型将在<code> paint </code>方法中预期。
 * 例如,您可能希望编写一个仅适用于{@link java.awt.Component}的子类的<code> Painter </code>。
 * 在这种情况下,当声明<code> Painter </code>时,您可以声明它需要一个<code> Component </code>,允许paint方法是类型安全的。例如：。
 * <pre>
 *  {@code Painter <Component> p = new Painter <Component>(){public void paint(Graphics2D g,Component c,int width,int height){g.setColor(c.getBackground()); //等等}
 * }}。
 * </pre>
 * 
 * <p>此接口不保证线程安全。</p>
 * 
 * 
 * @author rbair
 */
public interface Painter<T> {
    /**
     * <p>Renders to the given {@link java.awt.Graphics2D} object. Implementations
     * of this method <em>may</em> modify state on the <code>Graphics2D</code>, and are not
     * required to restore that state upon completion. In most cases, it is recommended
     * that the caller pass in a scratch graphics object. The <code>Graphics2D</code>
     * must never be null.</p>
     *
     * <p>State on the graphics object may be honored by the <code>paint</code> method,
     * but may not be. For instance, setting the antialiasing rendering hint on the
     * graphics may or may not be respected by the <code>Painter</code> implementation.</p>
     *
     * <p>The supplied object parameter acts as an optional configuration argument.
     * For example, it could be of type <code>Component</code>. A <code>Painter</code>
     * that expected it could then read state from that <code>Component</code> and
     * use the state for painting. For example, an implementation may read the
     * backgroundColor and use that.</p>
     *
     * <p>Generally, to enhance reusability, most standard <code>Painter</code>s ignore
     * this parameter. They can thus be reused in any context. The <code>object</code>
     * may be null. Implementations must not throw a NullPointerException if the object
     * parameter is null.</p>
     *
     * <p>Finally, the <code>width</code> and <code>height</code> arguments specify the
     * width and height that the <code>Painter</code> should paint into. More
     * specifically, the specified width and height instruct the painter that it should
     * paint fully within this width and height. Any specified clip on the
     * <code>g</code> param will further constrain the region.</p>
     *
     * <p>For example, suppose I have a <code>Painter</code> implementation that draws
     * a gradient. The gradient goes from white to black. It "stretches" to fill the
     * painted region. Thus, if I use this <code>Painter</code> to paint a 500 x 500
     * region, the far left would be black, the far right would be white, and a smooth
     * gradient would be painted between. I could then, without modification, reuse the
     * <code>Painter</code> to paint a region that is 20x20 in size. This region would
     * also be black on the left, white on the right, and a smooth gradient painted
     * between.</p>
     *
     * <p>
     *  <p>呈现给定的{@link java.awt.Graphics2D}对象。此方法的实现可以</em>修改<code> Graphics2D </code>上的状态,并且在完成后不需要恢复该状态。
     * 在大多数情况下,建议调用者传递临时图形对象。 <code> Graphics2D </code>不能为空。</p>。
     * 
     *  <p>图形对象上的状态可以通过<code> paint </code>方法来执行,但可能不是。例如,在<code> Painter </code>实现中可以或不可以设置图形上的抗锯齿渲染提示。
     * </p>。
     * 
     *  <p>提供的对象参数用作可选的配置参数。例如,它可以是<code> Component </code>类型。
     * 一个<code> Painter </code>,它期望它可以从<code> Component </code>读取状态,并使用状态绘画。例如,一个实现可以读取backgroundColor并使用它。
     * </p>。
     * 
     * 
     * @param g The Graphics2D to render to. This must not be null.
     * @param object an optional configuration parameter. This may be null.
     * @param width width of the area to paint.
     * @param height height of the area to paint.
     */
    public void paint(Graphics2D g, T object, int width, int height);
}
