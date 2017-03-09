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
package java.awt;

import java.awt.peer.ComponentPeer;


/**
 * A FocusTraversalPolicy that determines traversal order based on the order
 * of child Components in a Container. From a particular focus cycle root, the
 * policy makes a pre-order traversal of the Component hierarchy, and traverses
 * a Container's children according to the ordering of the array returned by
 * <code>Container.getComponents()</code>. Portions of the hierarchy that are
 * not visible and displayable will not be searched.
 * <p>
 * If client code has explicitly set the focusability of a Component by either
 * overriding <code>Component.isFocusTraversable()</code> or
 * <code>Component.isFocusable()</code>, or by calling
 * <code>Component.setFocusable()</code>, then a DefaultFocusTraversalPolicy
 * behaves exactly like a ContainerOrderFocusTraversalPolicy. If, however, the
 * Component is relying on default focusability, then a
 * DefaultFocusTraversalPolicy will reject all Components with non-focusable
 * peers. This is the default FocusTraversalPolicy for all AWT Containers.
 * <p>
 * The focusability of a peer is implementation-dependent. Sun recommends that
 * all implementations for a particular native platform construct peers with
 * the same focusability. The recommendations for Windows and Unix are that
 * Canvases, Labels, Panels, Scrollbars, ScrollPanes, Windows, and lightweight
 * Components have non-focusable peers, and all other Components have focusable
 * peers. These recommendations are used in the Sun AWT implementations. Note
 * that the focusability of a Component's peer is different from, and does not
 * impact, the focusability of the Component itself.
 * <p>
 * Please see
 * <a href="https://docs.oracle.com/javase/tutorial/uiswing/misc/focus.html">
 * How to Use the Focus Subsystem</a>,
 * a section in <em>The Java Tutorial</em>, and the
 * <a href="../../java/awt/doc-files/FocusSpec.html">Focus Specification</a>
 * for more information.
 *
 * <p>
 *  一个FocusTraversalPolicy,它根据容器中子组件的顺序确定遍历顺序。
 * 从特定的焦点循环根,策略对组件层次结构进行预订遍历,并根据<code> Container.getComponents()</code>返回的数组的顺序遍历Container的子节点。
 * 不会搜索不可见和可显示的层次结构的部分。
 * <p>
 *  如果客户端代码通过覆盖<code> Component.isFocusTraversable()</code>或<code> Component.isFocusable()</code>或通过调用<code>
 *  Component.setFocusable )</code>,那么DefaultFocusTraversalPolicy的行为完全类似于ContainerOrderFocusTraversalPol
 * icy。
 * 然而,如果组件依赖于默认可聚焦性,则DefaultFocusTraversalPolicy将拒绝具有不可聚焦对等体的所有组件。这是所有AWT容器的默认FocusTraversalPolicy。
 * <p>
 * 对等体的可聚焦性取决于实现。 Sun建议特定原生平台的所有实现构建具有相同可聚焦性的对等体。
 * 对于Windows和Unix的建议是,画布,标签,面板,滚动条,ScrollPanes,Windows和轻量级组件具有不可聚焦的对等体,所有其他组件具有可聚焦的对等体。
 * 这些建议在Sun AWT实现中使用。注意,组件对等体的可聚焦性不同于组件自身的可聚焦性,并且不影响组件自身的可聚焦性。
 * <p>
 *  请参见
 * 
 * @author David Mendenhall
 *
 * @see Container#getComponents
 * @see Component#isFocusable
 * @see Component#setFocusable
 * @since 1.4
 */
public class DefaultFocusTraversalPolicy
    extends ContainerOrderFocusTraversalPolicy
{
    /*
     * serialVersionUID
     * <p>
     * <a href="https://docs.oracle.com/javase/tutorial/uiswing/misc/focus.html">
     *  如何使用焦点子系统</a>,<em> Java教程</em>和<a href ="../../ java / awt / doc-files / FocusSpec.html" >聚焦规格</a>了解
     * 更多信息。
     * 
     */
    private static final long serialVersionUID = 8876966522510157497L;

    /**
     * Determines whether a Component is an acceptable choice as the new
     * focus owner. The Component must be visible, displayable, and enabled
     * to be accepted. If client code has explicitly set the focusability
     * of the Component by either overriding
     * <code>Component.isFocusTraversable()</code> or
     * <code>Component.isFocusable()</code>, or by calling
     * <code>Component.setFocusable()</code>, then the Component will be
     * accepted if and only if it is focusable. If, however, the Component is
     * relying on default focusability, then all Canvases, Labels, Panels,
     * Scrollbars, ScrollPanes, Windows, and lightweight Components will be
     * rejected.
     *
     * <p>
     *  serialVersionUID
     * 
     * 
     * @param aComponent the Component whose fitness as a focus owner is to
     *        be tested
     * @return <code>true</code> if aComponent meets the above requirements;
     *         <code>false</code> otherwise
     */
    protected boolean accept(Component aComponent) {
        if (!(aComponent.isVisible() && aComponent.isDisplayable() &&
              aComponent.isEnabled()))
        {
            return false;
        }

        // Verify that the Component is recursively enabled. Disabling a
        // heavyweight Container disables its children, whereas disabling
        // a lightweight Container does not.
        if (!(aComponent instanceof Window)) {
            for (Container enableTest = aComponent.getParent();
                 enableTest != null;
                 enableTest = enableTest.getParent())
            {
                if (!(enableTest.isEnabled() || enableTest.isLightweight())) {
                    return false;
                }
                if (enableTest instanceof Window) {
                    break;
                }
            }
        }

        boolean focusable = aComponent.isFocusable();
        if (aComponent.isFocusTraversableOverridden()) {
            return focusable;
        }

        ComponentPeer peer = aComponent.getPeer();
        return (peer != null && peer.isFocusable());
    }
}
