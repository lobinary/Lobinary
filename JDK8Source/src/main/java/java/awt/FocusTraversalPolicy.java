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

/**
 * A FocusTraversalPolicy defines the order in which Components with a
 * particular focus cycle root are traversed. Instances can apply the policy to
 * arbitrary focus cycle roots, allowing themselves to be shared across
 * Containers. They do not need to be reinitialized when the focus cycle roots
 * of a Component hierarchy change.
 * <p>
 * The core responsibility of a FocusTraversalPolicy is to provide algorithms
 * determining the next and previous Components to focus when traversing
 * forward or backward in a UI. Each FocusTraversalPolicy must also provide
 * algorithms for determining the first, last, and default Components in a
 * traversal cycle. First and last Components are used when normal forward and
 * backward traversal, respectively, wraps. The default Component is the first
 * to receive focus when traversing down into a new focus traversal cycle.
 * A FocusTraversalPolicy can optionally provide an algorithm for determining
 * a Window's initial Component. The initial Component is the first to receive
 * focus when a Window is first made visible.
 * <p>
 * FocusTraversalPolicy takes into account <a
 * href="doc-files/FocusSpec.html#FocusTraversalPolicyProviders">focus traversal
 * policy providers</a>.  When searching for first/last/next/previous Component,
 * if a focus traversal policy provider is encountered, its focus traversal
 * policy is used to perform the search operation.
 * <p>
 * Please see
 * <a href="https://docs.oracle.com/javase/tutorial/uiswing/misc/focus.html">
 * How to Use the Focus Subsystem</a>,
 * a section in <em>The Java Tutorial</em>, and the
 * <a href="../../java/awt/doc-files/FocusSpec.html">Focus Specification</a>
 * for more information.
 *
 * <p>
 *  FocusTraversalPolicy定义具有特定焦点周期根的组件的遍历顺序。实例可以将策略应用于任意焦点循环根,以允许它们在容器之间共享。当组件层次结构的焦点循环根更改时,不需要重新初始化它们。
 * <p>
 *  FocusTraversalPolicy的核心职责是提供算法,确定在UI中向前或向后遍历时要聚焦的下一个和前一个组件。
 * 每个FocusTraversalPolicy还必须提供用于在遍历周期中确定第一个,最后一个和默认组件的算法。当正常向前和向后遍历时,分别使用第一个和最后一个分量。
 * 默认组件是在向下移动到新的焦点遍历周期时首先接收焦点的组件。 FocusTraversalPolicy可以可选地提供用于确定窗口的初始组件的算法。初始组件是首先使窗口可见时首先接收焦点的组件。
 * <p>
 *  FocusTraversalPolicy会考虑<a href="doc-files/FocusSpec.html#FocusTraversalPolicyProviders">重点遍历策略提供程序</a>
 * 。
 * 当搜索第一/最后/下一个/上一个组件时,如果遇到焦点遍历策略提供器,则其焦点遍历策略用于执行搜索操作。
 * <p>
 * 请参见
 * <a href="https://docs.oracle.com/javase/tutorial/uiswing/misc/focus.html">
 *  如何使用焦点子系统</a>,<em> Java教程</em>和<a href ="../../ java / awt / doc-files / FocusSpec.html" >聚焦规格</a>了解
 * 更多信息。
 * 
 * 
 * @author David Mendenhall
 *
 * @see Container#setFocusTraversalPolicy
 * @see Container#getFocusTraversalPolicy
 * @see Container#setFocusCycleRoot
 * @see Container#isFocusCycleRoot
 * @see Container#setFocusTraversalPolicyProvider
 * @see Container#isFocusTraversalPolicyProvider
 * @see KeyboardFocusManager#setDefaultFocusTraversalPolicy
 * @see KeyboardFocusManager#getDefaultFocusTraversalPolicy
 * @since 1.4
 */
public abstract class FocusTraversalPolicy {

    /**
     * Returns the Component that should receive the focus after aComponent.
     * aContainer must be a focus cycle root of aComponent or a focus traversal
     * policy provider.
     *
     * <p>
     *  返回在aComponent之后应该接收焦点的组件。 aContainer必须是aComponent或焦点遍历策略提供程序的焦点循环根。
     * 
     * 
     * @param aContainer a focus cycle root of aComponent or focus traversal
     *        policy provider
     * @param aComponent a (possibly indirect) child of aContainer, or
     *        aContainer itself
     * @return the Component that should receive the focus after aComponent, or
     *         null if no suitable Component can be found
     * @throws IllegalArgumentException if aContainer is not a focus cycle
     *         root of aComponent or a focus traversal policy provider, or if
     *         either aContainer or aComponent is null
     */
    public abstract Component getComponentAfter(Container aContainer,
                                                Component aComponent);

    /**
     * Returns the Component that should receive the focus before aComponent.
     * aContainer must be a focus cycle root of aComponent or a focus traversal
     * policy provider.
     *
     * <p>
     *  返回应在aComponent之前接收焦点的组件。 aContainer必须是aComponent或焦点遍历策略提供程序的焦点循环根。
     * 
     * 
     * @param aContainer a focus cycle root of aComponent or focus traversal
     *        policy provider
     * @param aComponent a (possibly indirect) child of aContainer, or
     *        aContainer itself
     * @return the Component that should receive the focus before aComponent,
     *         or null if no suitable Component can be found
     * @throws IllegalArgumentException if aContainer is not a focus cycle
     *         root of aComponent or a focus traversal policy provider, or if
     *         either aContainer or aComponent is null
     */
    public abstract Component getComponentBefore(Container aContainer,
                                                 Component aComponent);

    /**
     * Returns the first Component in the traversal cycle. This method is used
     * to determine the next Component to focus when traversal wraps in the
     * forward direction.
     *
     * <p>
     *  返回遍历循环中的第一个组件。此方法用于确定在向前方向上遍历卷绕时要聚焦的下一个组件。
     * 
     * 
     * @param aContainer the focus cycle root or focus traversal policy provider
     *        whose first Component is to be returned
     * @return the first Component in the traversal cycle of aContainer,
     *         or null if no suitable Component can be found
     * @throws IllegalArgumentException if aContainer is null
     */
    public abstract Component getFirstComponent(Container aContainer);

    /**
     * Returns the last Component in the traversal cycle. This method is used
     * to determine the next Component to focus when traversal wraps in the
     * reverse direction.
     *
     * <p>
     *  返回遍历循环中的最后一个组件。此方法用于确定下一个要在相反方向上遍历卷绕时要聚焦的组件。
     * 
     * 
     * @param aContainer the focus cycle root or focus traversal policy
     *        provider whose last Component is to be returned
     * @return the last Component in the traversal cycle of aContainer,
     *         or null if no suitable Component can be found
     * @throws IllegalArgumentException if aContainer is null
     */
    public abstract Component getLastComponent(Container aContainer);

    /**
     * Returns the default Component to focus. This Component will be the first
     * to receive focus when traversing down into a new focus traversal cycle
     * rooted at aContainer.
     *
     * <p>
     *  返回要聚焦的默认组件。这个组件将是第一个接收焦点,当向下移动到一个新的焦点遍历循环根源于一个容器。
     * 
     * 
     * @param aContainer the focus cycle root or focus traversal policy
     *        provider whose default Component is to be returned
     * @return the default Component in the traversal cycle of aContainer,
     *         or null if no suitable Component can be found
     * @throws IllegalArgumentException if aContainer is null
     */
    public abstract Component getDefaultComponent(Container aContainer);

    /**
     * Returns the Component that should receive the focus when a Window is
     * made visible for the first time. Once the Window has been made visible
     * by a call to <code>show()</code> or <code>setVisible(true)</code>, the
     * initial Component will not be used again. Instead, if the Window loses
     * and subsequently regains focus, or is made invisible or undisplayable
     * and subsequently made visible and displayable, the Window's most
     * recently focused Component will become the focus owner. The default
     * implementation of this method returns the default Component.
     *
     * <p>
     * 返回当窗口第一次可见时应接收焦点的组件。一旦通过调用<code> show()</code>或<code> setVisible(true)</code>使窗口可见,初始组件将不会再次使用。
     * 相反,如果窗口失去并随后重新获得焦点,或使其不可见或不可显示,随后变得可见和可显示,则窗口的最近聚焦的组件将成为焦点所有者。此方法的默认实现返回默认组件。
     * 
     * @param window the Window whose initial Component is to be returned
     * @return the Component that should receive the focus when window is made
     *         visible for the first time, or null if no suitable Component can
     *         be found
     * @see #getDefaultComponent
     * @see Window#getMostRecentFocusOwner
     * @throws IllegalArgumentException if window is null
     */
    public Component getInitialComponent(Window window) {
        if ( window == null ){
            throw new IllegalArgumentException("window cannot be equal to null.");
        }
        Component def = getDefaultComponent(window);
        if (def == null && window.isFocusableWindow()) {
            def = window;
        }
        return def;
    }
}
