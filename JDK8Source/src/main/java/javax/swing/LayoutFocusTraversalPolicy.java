/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2008, Oracle and/or its affiliates. All rights reserved.
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

import java.awt.Component;
import java.awt.Container;
import java.awt.ComponentOrientation;
import java.util.Comparator;
import java.io.*;
import sun.awt.SunToolkit;


/**
 * A SortingFocusTraversalPolicy which sorts Components based on their size,
 * position, and orientation. Based on their size and position, Components are
 * roughly categorized into rows and columns. For a Container with horizontal
 * orientation, columns run left-to-right or right-to-left, and rows run top-
 * to-bottom. For a Container with vertical orientation, columns run top-to-
 * bottom and rows run left-to-right or right-to-left. See
 * <code>ComponentOrientation</code> for more information. All columns in a
 * row are fully traversed before proceeding to the next row.
 *
 * <p>
 *  SortingFocusTraversalPolicy,用于根据组件的大小,位置和方向对组件进行排序根据组件的大小和位置,组件大致分为行和列。
 * 对于具有水平方向的容器,列从左到右或从右到左,和行从上到下运行对于具有垂直方向的容器,列从上到下运行,行从左到右或从右到左运行有关详细信息,请参见<code> ComponentOrientation 
 * </code>在进行到下一行之前完全遍历。
 *  SortingFocusTraversalPolicy,用于根据组件的大小,位置和方向对组件进行排序根据组件的大小和位置,组件大致分为行和列。
 * 
 * 
 * @author David Mendenhall
 *
 * @see java.awt.ComponentOrientation
 * @since 1.4
 */
public class LayoutFocusTraversalPolicy extends SortingFocusTraversalPolicy
    implements Serializable
{
    // Delegate most of our fitness test to Default so that we only have to
    // code the algorithm once.
    private static final SwingDefaultFocusTraversalPolicy fitnessTestPolicy =
        new SwingDefaultFocusTraversalPolicy();

    /**
     * Constructs a LayoutFocusTraversalPolicy.
     * <p>
     *  构造LayoutFocusTraversalPolicy
     * 
     */
    public LayoutFocusTraversalPolicy() {
        super(new LayoutComparator());
    }

    /**
     * Constructs a LayoutFocusTraversalPolicy with the passed in
     * <code>Comparator</code>.
     * <p>
     * 使用<code> Comparator </code>中传递的构造LayoutFocusTraversalPolicy
     * 
     */
    LayoutFocusTraversalPolicy(Comparator<? super Component> c) {
        super(c);
    }

    /**
     * Returns the Component that should receive the focus after aComponent.
     * aContainer must be a focus cycle root of aComponent.
     * <p>
     * By default, LayoutFocusTraversalPolicy implicitly transfers focus down-
     * cycle. That is, during normal focus traversal, the Component
     * traversed after a focus cycle root will be the focus-cycle-root's
     * default Component to focus. This behavior can be disabled using the
     * <code>setImplicitDownCycleTraversal</code> method.
     * <p>
     * If aContainer is <a href="../../java/awt/doc-files/FocusSpec.html#FocusTraversalPolicyProviders">focus
     * traversal policy provider</a>, the focus is always transferred down-cycle.
     *
     * <p>
     *  返回在aComponent aContainer必须是aComponent的焦点循环根之后应该接收焦点的组件
     * <p>
     *  默认情况下,LayoutFocusTraversalPolicy隐式传输焦点下循环。
     * 也就是说,在正常焦点遍历期间,在焦点循环根之后遍历的组件将是焦点循环根的默认组件焦点此行为可以使用<code> setImplicitDownCycleTraversal < / code>方法。
     * <p>
     *  如果aContainer是<a href=\"//java/awt/doc-files/FocusSpechtml#FocusTraversalPolicyProviders\">焦点遍历策略提供程序
     * </a>,焦点总是传递下行周期。
     * 
     * 
     * @param aContainer a focus cycle root of aComponent or a focus traversal policy provider
     * @param aComponent a (possibly indirect) child of aContainer, or
     *        aContainer itself
     * @return the Component that should receive the focus after aComponent, or
     *         null if no suitable Component can be found
     * @throws IllegalArgumentException if aContainer is not a focus cycle
     *         root of aComponent or a focus traversal policy provider, or if either aContainer or
     *         aComponent is null
     */
    public Component getComponentAfter(Container aContainer,
                                       Component aComponent) {
        if (aContainer == null || aComponent == null) {
            throw new IllegalArgumentException("aContainer and aComponent cannot be null");
        }
        Comparator comparator = getComparator();
        if (comparator instanceof LayoutComparator) {
            ((LayoutComparator)comparator).
                setComponentOrientation(aContainer.
                                        getComponentOrientation());
        }
        return super.getComponentAfter(aContainer, aComponent);
    }

    /**
     * Returns the Component that should receive the focus before aComponent.
     * aContainer must be a focus cycle root of aComponent.
     * <p>
     * By default, LayoutFocusTraversalPolicy implicitly transfers focus down-
     * cycle. That is, during normal focus traversal, the Component
     * traversed after a focus cycle root will be the focus-cycle-root's
     * default Component to focus. This behavior can be disabled using the
     * <code>setImplicitDownCycleTraversal</code> method.
     * <p>
     * If aContainer is <a href="../../java/awt/doc-files/FocusSpec.html#FocusTraversalPolicyProviders">focus
     * traversal policy provider</a>, the focus is always transferred down-cycle.
     *
     * <p>
     * 返回在aComponent aContainer必须是aComponent的焦点循环根之前应该接收焦点的组件
     * <p>
     *  默认情况下,LayoutFocusTraversalPolicy隐式传输焦点下循环。
     * 也就是说,在正常焦点遍历期间,在焦点循环根之后遍历的组件将是焦点循环根的默认组件焦点此行为可以使用<code> setImplicitDownCycleTraversal < / code>方法。
     * <p>
     *  如果aContainer是<a href=\"//java/awt/doc-files/FocusSpechtml#FocusTraversalPolicyProviders\">焦点遍历策略提供程序
     * </a>,焦点总是传递下行周期。
     * 
     * 
     * @param aContainer a focus cycle root of aComponent or a focus traversal policy provider
     * @param aComponent a (possibly indirect) child of aContainer, or
     *        aContainer itself
     * @return the Component that should receive the focus before aComponent,
     *         or null if no suitable Component can be found
     * @throws IllegalArgumentException if aContainer is not a focus cycle
     *         root of aComponent or a focus traversal policy provider, or if either aContainer or
     *         aComponent is null
     */
    public Component getComponentBefore(Container aContainer,
                                        Component aComponent) {
        if (aContainer == null || aComponent == null) {
            throw new IllegalArgumentException("aContainer and aComponent cannot be null");
        }
        Comparator comparator = getComparator();
        if (comparator instanceof LayoutComparator) {
            ((LayoutComparator)comparator).
                setComponentOrientation(aContainer.
                                        getComponentOrientation());
        }
        return super.getComponentBefore(aContainer, aComponent);
    }

    /**
     * Returns the first Component in the traversal cycle. This method is used
     * to determine the next Component to focus when traversal wraps in the
     * forward direction.
     *
     * <p>
     *  返回遍历循环中的第一个组件此方法用于确定在向前方向上遍历卷绕时要聚焦的下一个组件
     * 
     * 
     * @param aContainer a focus cycle root of aComponent or a focus traversal policy provider whose
     *        first Component is to be returned
     * @return the first Component in the traversal cycle of aContainer,
     *         or null if no suitable Component can be found
     * @throws IllegalArgumentException if aContainer is null
     */
    public Component getFirstComponent(Container aContainer) {
        if (aContainer == null) {
            throw new IllegalArgumentException("aContainer cannot be null");
        }
        Comparator comparator = getComparator();
        if (comparator instanceof LayoutComparator) {
            ((LayoutComparator)comparator).
                setComponentOrientation(aContainer.
                                        getComponentOrientation());
        }
        return super.getFirstComponent(aContainer);
    }

    /**
     * Returns the last Component in the traversal cycle. This method is used
     * to determine the next Component to focus when traversal wraps in the
     * reverse direction.
     *
     * <p>
     * 返回遍历循环中的最后一个组件此方法用于确定在相反方向上遍历换行时要聚焦的下一个组件
     * 
     * 
     * @param aContainer a focus cycle root of aComponent or a focus traversal policy provider whose
     *        last Component is to be returned
     * @return the last Component in the traversal cycle of aContainer,
     *         or null if no suitable Component can be found
     * @throws IllegalArgumentException if aContainer is null
     */
    public Component getLastComponent(Container aContainer) {
        if (aContainer == null) {
            throw new IllegalArgumentException("aContainer cannot be null");
        }
        Comparator comparator = getComparator();
        if (comparator instanceof LayoutComparator) {
            ((LayoutComparator)comparator).
                setComponentOrientation(aContainer.
                                        getComponentOrientation());
        }
        return super.getLastComponent(aContainer);
    }

    /**
     * Determines whether the specified <code>Component</code>
     * is an acceptable choice as the new focus owner.
     * This method performs the following sequence of operations:
     * <ol>
     * <li>Checks whether <code>aComponent</code> is visible, displayable,
     *     enabled, and focusable.  If any of these properties is
     *     <code>false</code>, this method returns <code>false</code>.
     * <li>If <code>aComponent</code> is an instance of <code>JTable</code>,
     *     returns <code>true</code>.
     * <li>If <code>aComponent</code> is an instance of <code>JComboBox</code>,
     *     then returns the value of
     *     <code>aComponent.getUI().isFocusTraversable(aComponent)</code>.
     * <li>If <code>aComponent</code> is a <code>JComponent</code>
     *     with a <code>JComponent.WHEN_FOCUSED</code>
     *     <code>InputMap</code> that is neither <code>null</code>
     *     nor empty, returns <code>true</code>.
     * <li>Returns the value of
     *     <code>DefaultFocusTraversalPolicy.accept(aComponent)</code>.
     * </ol>
     *
     * <p>
     *  确定指定的<code> Component </code>是否为新焦点所有者可接受的选择此方法执行以下操作序列：
     * <ol>
     * <li>检查<code> aComponent </code>是否可见,可显示,启用和可聚焦如果任何这些属性是<code> false </code>,此方法将返回<code> false </code>
     *  <li >如果<code> aComponent </code>是<code> JTable </code>的一个实例,则返回<code> true </code> <li> > JComboBox 
     * </code>,然后返回<code> aComponentgetUI()isFocusTraversable(aComponent)的值</code> <li>如果<code> aComponent </code>
     * 
     * @param aComponent the <code>Component</code> whose fitness
     *                   as a focus owner is to be tested
     * @see java.awt.Component#isVisible
     * @see java.awt.Component#isDisplayable
     * @see java.awt.Component#isEnabled
     * @see java.awt.Component#isFocusable
     * @see javax.swing.plaf.ComboBoxUI#isFocusTraversable
     * @see javax.swing.JComponent#getInputMap
     * @see java.awt.DefaultFocusTraversalPolicy#accept
     * @return <code>true</code> if <code>aComponent</code> is a valid choice
     *         for a focus owner;
     *         otherwise <code>false</code>
     */
     protected boolean accept(Component aComponent) {
        if (!super.accept(aComponent)) {
            return false;
        } else if (SunToolkit.isInstanceOf(aComponent, "javax.swing.JTable")) {
            // JTable only has ancestor focus bindings, we thus force it
            // to be focusable by returning true here.
            return true;
        } else if (SunToolkit.isInstanceOf(aComponent, "javax.swing.JComboBox")) {
            JComboBox box = (JComboBox)aComponent;
            return box.getUI().isFocusTraversable(box);
        } else if (aComponent instanceof JComponent) {
            JComponent jComponent = (JComponent)aComponent;
            InputMap inputMap = jComponent.getInputMap(JComponent.WHEN_FOCUSED,
                                                       false);
            while (inputMap != null && inputMap.size() == 0) {
                inputMap = inputMap.getParent();
            }
            if (inputMap != null) {
                return true;
            }
            // Delegate to the fitnessTestPolicy, this will test for the
            // case where the developer has overriden isFocusTraversable to
            // return true.
        }
        return fitnessTestPolicy.accept(aComponent);
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.writeObject(getComparator());
        out.writeBoolean(getImplicitDownCycleTraversal());
    }
    private void readObject(ObjectInputStream in)
        throws IOException, ClassNotFoundException
    {
        setComparator((Comparator)in.readObject());
        setImplicitDownCycleTraversal(in.readBoolean());
    }
}

// Create our own subclass and change accept to public so that we can call
// accept.
class SwingDefaultFocusTraversalPolicy
    extends java.awt.DefaultFocusTraversalPolicy
{
    public boolean accept(Component aComponent) {
        return super.accept(aComponent);
    }
}
