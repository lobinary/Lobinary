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

import java.util.List;
import java.util.ArrayList;
import sun.util.logging.PlatformLogger;

/**
 * A FocusTraversalPolicy that determines traversal order based on the order
 * of child Components in a Container. From a particular focus cycle root, the
 * policy makes a pre-order traversal of the Component hierarchy, and traverses
 * a Container's children according to the ordering of the array returned by
 * <code>Container.getComponents()</code>. Portions of the hierarchy that are
 * not visible and displayable will not be searched.
 * <p>
 * By default, ContainerOrderFocusTraversalPolicy implicitly transfers focus
 * down-cycle. That is, during normal forward focus traversal, the Component
 * traversed after a focus cycle root will be the focus-cycle-root's default
 * Component to focus. This behavior can be disabled using the
 * <code>setImplicitDownCycleTraversal</code> method.
 * <p>
 * By default, methods of this class will return a Component only if it is
 * visible, displayable, enabled, and focusable. Subclasses can modify this
 * behavior by overriding the <code>accept</code> method.
 * <p>
 * This policy takes into account <a
 * href="doc-files/FocusSpec.html#FocusTraversalPolicyProviders">focus traversal
 * policy providers</a>.  When searching for first/last/next/previous Component,
 * if a focus traversal policy provider is encountered, its focus traversal
 * policy is used to perform the search operation.
 *
 * <p>
 *  一个FocusTraversalPolicy,它根据容器中子组件的顺序确定遍历顺序。
 * 从特定的焦点循环根,策略对组件层次结构进行预订遍历,并根据<code> Container.getComponents()</code>返回的数组的顺序遍历Container的子节点。
 * 不会搜索不可见和可显示的层次结构的部分。
 * <p>
 *  默认情况下,ContainerOrderFocusTraversalPolicy隐式传输焦点下降周期。也就是说,在正常的正向聚焦遍历期间,在聚焦周期根之后遍历的分量将是聚焦周期根的默认分量聚焦。
 * 可以使用<code> setImplicitDownCycleTraversal </code>方法禁用此行为。
 * <p>
 *  默认情况下,只有当它是可见的,可显示的,启用的和可聚焦的,这个类的方法将返回一个组件。子类可以通过覆盖<code> accept </code>方法来修改此行为。
 * <p>
 *  此政策考虑了<a href="doc-files/FocusSpec.html#FocusTraversalPolicyProviders">重点遍历策略提供程序</a>。
 * 当搜索第一/最后/下一个/上一个组件时,如果遇到焦点遍历策略提供器,则其焦点遍历策略用于执行搜索操作。
 * 
 * 
 * @author David Mendenhall
 *
 * @see Container#getComponents
 * @since 1.4
 */
public class ContainerOrderFocusTraversalPolicy extends FocusTraversalPolicy
    implements java.io.Serializable
{
    private static final PlatformLogger log = PlatformLogger.getLogger("java.awt.ContainerOrderFocusTraversalPolicy");

    final private int FORWARD_TRAVERSAL = 0;
    final private int BACKWARD_TRAVERSAL = 1;

    /*
     * JDK 1.4 serialVersionUID
     * <p>
     *  JDK 1.4 serialVersionUID
     * 
     */
    private static final long serialVersionUID = 486933713763926351L;

    private boolean implicitDownCycleTraversal = true;

    /**
     * Used by getComponentAfter and getComponentBefore for efficiency. In
     * order to maintain compliance with the specification of
     * FocusTraversalPolicy, if traversal wraps, we should invoke
     * getFirstComponent or getLastComponent. These methods may be overriden in
     * subclasses to behave in a non-generic way. However, in the generic case,
     * these methods will simply return the first or last Components of the
     * sorted list, respectively. Since getComponentAfter and
     * getComponentBefore have already built the list before determining
     * that they need to invoke getFirstComponent or getLastComponent, the
     * list should be reused if possible.
     * <p>
     * 由getComponentAfter和getComponentBefore使用以提高效率。
     * 为了保持符合FocusTraversalPolicy的规范,如果遍历包装,我们应该调用getFirstComponent或getLastComponent。
     * 这些方法可以在子类中被覆盖,以非通用的方式表现。然而,在通用情况下,这些方法将简单地分别返回排序列表的第一个或最后一个组件。
     * 由于getComponentAfter和getComponentBefore在确定它们需要调用getFirstComponent或getLastComponent之前已经构建了列表,所以如果可能的话,应
     * 该重新使用该列表。
     * 这些方法可以在子类中被覆盖,以非通用的方式表现。然而,在通用情况下,这些方法将简单地分别返回排序列表的第一个或最后一个组件。
     * 
     */
    transient private Container cachedRoot;
    transient private List<Component> cachedCycle;

    /*
     * We suppose to use getFocusTraversalCycle & getComponentIndex methods in order
     * to divide the policy into two parts:
     * 1) Making the focus traversal cycle.
     * 2) Traversing the cycle.
     * The 1st point assumes producing a list of components representing the focus
     * traversal cycle. The two methods mentioned above should implement this logic.
     * The 2nd point assumes implementing the common concepts of operating on the
     * cycle: traversing back and forth, retrieving the initial/default/first/last
     * component. These concepts are described in the AWT Focus Spec and they are
     * applied to the FocusTraversalPolicy in general.
     * Thus, a descendant of this policy may wish to not reimplement the logic of
     * the 2nd point but just override the implementation of the 1st one.
     * A striking example of such a descendant is the javax.swing.SortingFocusTraversalPolicy.
     * <p>
     *  我们假设使用getFocusTraversalCycle&getComponentIndex方法,以将策略分为两部分：1)使焦点遍历循环。 2)移动循环。第一点假设产生表示焦点遍历周期的分量的列表。
     * 上面提到的两种方法应该实现这个逻辑。第二点假定实现在循环上操作的常见概念：来回遍历,检索初始/默认/第一/最后一个组件。
     * 这些概念在AWT Focus Spec中有所描述,它们通常应用于FocusTraversalPolicy。因此,这个策略的后代可能希望不重新实现第二点的逻辑,而是仅仅覆盖第一点的实现。
     * 这样的后代的一个突出的例子是javax.swing.SortingFocusTraversalPolicy。
     * 
     */
    /*protected*/ private List<Component> getFocusTraversalCycle(Container aContainer) {
        List<Component> cycle = new ArrayList<Component>();
        enumerateCycle(aContainer, cycle);
        return cycle;
    }
    /* <p>
    /* List <Component> cycle = new ArrayList <Component>(); enumerateCycle(aContainer,cycle);返回循环; }}
    /* 
    /* 
    /*protected*/ private int getComponentIndex(List<Component> cycle, Component aComponent) {
        return cycle.indexOf(aComponent);
    }

    private void enumerateCycle(Container container, List<Component> cycle) {
        if (!(container.isVisible() && container.isDisplayable())) {
            return;
        }

        cycle.add(container);

        Component[] components = container.getComponents();
        for (int i = 0; i < components.length; i++) {
            Component comp = components[i];
            if (comp instanceof Container) {
                Container cont = (Container)comp;

                if (!cont.isFocusCycleRoot() && !cont.isFocusTraversalPolicyProvider()) {
                    enumerateCycle(cont, cycle);
                    continue;
                }
            }
            cycle.add(comp);
        }
    }

    private Container getTopmostProvider(Container focusCycleRoot, Component aComponent) {
        Container aCont = aComponent.getParent();
        Container ftp = null;
        while (aCont  != focusCycleRoot && aCont != null) {
            if (aCont.isFocusTraversalPolicyProvider()) {
                ftp = aCont;
            }
            aCont = aCont.getParent();
        }
        if (aCont == null) {
            return null;
        }
        return ftp;
    }

    /*
     * Checks if a new focus cycle takes place and returns a Component to traverse focus to.
     * <p>
     *  return cycle.indexOf(aComponent); }}
     * 
     *  private void enumerateCycle(Container container,List <Component> cycle){if(！(container.isVisible()&& container.isDisplayable())){return; }
     * }。
     * 
     *  cycle.add(container);
     * 
     *  Component [] components = container.getComponents(); for(int i = 0; i <components.length; i ++){Component comp = components [i]; if(comp instanceof Container){Container cont =(Container)comp;。
     * 
     *  if(！cont.isFocusCycleRoot()&&！cont.isFocusTraversalPolicyProvider()){enumerateCycle(cont,cycle);继续; }
     * } cycle.add(comp); }}。
     * 
     *  private Container getTopmostProvider(Container focusCycleRoot,Component aComponent){Container aCont = aComponent.getParent(); Container ftp = null; while(aCont！= focusCycleRoot && aCont！= null){if(aCont.isFocusTraversalPolicyProvider()){ftp = aCont; }
     *  aCont = aCont.getParent(); } if(aCont == null){return null; } return ftp; }}。
     * 
     *  / *检查是否发生新的焦点循环,并返回要遍历焦点的组件。
     * 
     * 
     * @param comp a possible focus cycle root or policy provider
     * @param traversalDirection the direction of the traversal
     * @return a Component to traverse focus to if {@code comp} is a root or provider
     *         and implicit down-cycle is set, otherwise {@code null}
     */
    private Component getComponentDownCycle(Component comp, int traversalDirection) {
        Component retComp = null;

        if (comp instanceof Container) {
            Container cont = (Container)comp;

            if (cont.isFocusCycleRoot()) {
                if (getImplicitDownCycleTraversal()) {
                    retComp = cont.getFocusTraversalPolicy().getDefaultComponent(cont);

                    if (retComp != null && log.isLoggable(PlatformLogger.Level.FINE)) {
                        log.fine("### Transfered focus down-cycle to " + retComp +
                                 " in the focus cycle root " + cont);
                    }
                } else {
                    return null;
                }
            } else if (cont.isFocusTraversalPolicyProvider()) {
                retComp = (traversalDirection == FORWARD_TRAVERSAL ?
                           cont.getFocusTraversalPolicy().getDefaultComponent(cont) :
                           cont.getFocusTraversalPolicy().getLastComponent(cont));

                if (retComp != null && log.isLoggable(PlatformLogger.Level.FINE)) {
                    log.fine("### Transfered focus to " + retComp + " in the FTP provider " + cont);
                }
            }
        }
        return retComp;
    }

    /**
     * Returns the Component that should receive the focus after aComponent.
     * aContainer must be a focus cycle root of aComponent or a focus traversal policy provider.
     * <p>
     * By default, ContainerOrderFocusTraversalPolicy implicitly transfers
     * focus down-cycle. That is, during normal forward focus traversal, the
     * Component traversed after a focus cycle root will be the focus-cycle-
     * root's default Component to focus. This behavior can be disabled using
     * the <code>setImplicitDownCycleTraversal</code> method.
     * <p>
     * If aContainer is <a href="doc-files/FocusSpec.html#FocusTraversalPolicyProviders">focus
     * traversal policy provider</a>, the focus is always transferred down-cycle.
     *
     * <p>
     *  返回在aComponent之后应该接收焦点的组件。 aContainer必须是aComponent或焦点遍历策略提供程序的焦点循环根。
     * <p>
     * 默认情况下,ContainerOrderFocusTraversalPolicy隐式传输焦点下降周期。也就是说,在正常向前聚焦遍历期间,在聚焦周期根之后遍历的分量将是聚焦周期根的默认分量聚焦。
     * 可以使用<code> setImplicitDownCycleTraversal </code>方法禁用此行为。
     * <p>
     *  如果aContainer是<a href="doc-files/FocusSpec.html#FocusTraversalPolicyProviders">焦点遍历策略提供程序</a>,则焦点总是向下
     * 传递。
     * 
     * 
     * @param aContainer a focus cycle root of aComponent or a focus traversal policy provider
     * @param aComponent a (possibly indirect) child of aContainer, or
     *        aContainer itself
     * @return the Component that should receive the focus after aComponent, or
     *         null if no suitable Component can be found
     * @throws IllegalArgumentException if aContainer is not a focus cycle
     *         root of aComponent or focus traversal policy provider, or if either aContainer or
     *         aComponent is null
     */
    public Component getComponentAfter(Container aContainer, Component aComponent) {
        if (log.isLoggable(PlatformLogger.Level.FINE)) {
            log.fine("### Searching in " + aContainer + " for component after " + aComponent);
        }

        if (aContainer == null || aComponent == null) {
            throw new IllegalArgumentException("aContainer and aComponent cannot be null");
        }
        if (!aContainer.isFocusTraversalPolicyProvider() && !aContainer.isFocusCycleRoot()) {
            throw new IllegalArgumentException("aContainer should be focus cycle root or focus traversal policy provider");

        } else if (aContainer.isFocusCycleRoot() && !aComponent.isFocusCycleRoot(aContainer)) {
            throw new IllegalArgumentException("aContainer is not a focus cycle root of aComponent");
        }

        synchronized(aContainer.getTreeLock()) {

            if (!(aContainer.isVisible() && aContainer.isDisplayable())) {
                return null;
            }

            // Before all the ckecks below we first see if it's an FTP provider or a focus cycle root.
            // If it's the case just go down cycle (if it's set to "implicit").
            Component comp = getComponentDownCycle(aComponent, FORWARD_TRAVERSAL);
            if (comp != null) {
                return comp;
            }

            // See if the component is inside of policy provider.
            Container provider = getTopmostProvider(aContainer, aComponent);
            if (provider != null) {
                if (log.isLoggable(PlatformLogger.Level.FINE)) {
                    log.fine("### Asking FTP " + provider + " for component after " + aComponent);
                }

                // FTP knows how to find component after the given. We don't.
                FocusTraversalPolicy policy = provider.getFocusTraversalPolicy();
                Component afterComp = policy.getComponentAfter(provider, aComponent);

                // Null result means that we overstepped the limit of the FTP's cycle.
                // In that case we must quit the cycle, otherwise return the component found.
                if (afterComp != null) {
                    if (log.isLoggable(PlatformLogger.Level.FINE)) {
                        log.fine("### FTP returned " + afterComp);
                    }
                    return afterComp;
                }
                aComponent = provider;
            }

            List<Component> cycle = getFocusTraversalCycle(aContainer);

            if (log.isLoggable(PlatformLogger.Level.FINE)) {
                log.fine("### Cycle is " + cycle + ", component is " + aComponent);
            }

            int index = getComponentIndex(cycle, aComponent);

            if (index < 0) {
                if (log.isLoggable(PlatformLogger.Level.FINE)) {
                    log.fine("### Didn't find component " + aComponent + " in a cycle " + aContainer);
                }
                return getFirstComponent(aContainer);
            }

            for (index++; index < cycle.size(); index++) {
                comp = cycle.get(index);
                if (accept(comp)) {
                    return comp;
                } else if ((comp = getComponentDownCycle(comp, FORWARD_TRAVERSAL)) != null) {
                    return comp;
                }
            }

            if (aContainer.isFocusCycleRoot()) {
                this.cachedRoot = aContainer;
                this.cachedCycle = cycle;

                comp = getFirstComponent(aContainer);

                this.cachedRoot = null;
                this.cachedCycle = null;

                return comp;
            }
        }
        return null;
    }

    /**
     * Returns the Component that should receive the focus before aComponent.
     * aContainer must be a focus cycle root of aComponent or a <a
     * href="doc-files/FocusSpec.html#FocusTraversalPolicyProviders">focus traversal policy
     * provider</a>.
     *
     * <p>
     *  返回应在aComponent之前接收焦点的组件。
     *  aContainer必须是aComponent或<a href="doc-files/FocusSpec.html#FocusTraversalPolicyProviders">焦点遍历策略提供程序</a>
     * 的焦点周期根。
     *  返回应在aComponent之前接收焦点的组件。
     * 
     * 
     * @param aContainer a focus cycle root of aComponent or focus traversal policy provider
     * @param aComponent a (possibly indirect) child of aContainer, or
     *        aContainer itself
     * @return the Component that should receive the focus before aComponent,
     *         or null if no suitable Component can be found
     * @throws IllegalArgumentException if aContainer is not a focus cycle
     *         root of aComponent or focus traversal policy provider, or if either aContainer or
     *         aComponent is null
     */
    public Component getComponentBefore(Container aContainer, Component aComponent) {
        if (aContainer == null || aComponent == null) {
            throw new IllegalArgumentException("aContainer and aComponent cannot be null");
        }
        if (!aContainer.isFocusTraversalPolicyProvider() && !aContainer.isFocusCycleRoot()) {
            throw new IllegalArgumentException("aContainer should be focus cycle root or focus traversal policy provider");

        } else if (aContainer.isFocusCycleRoot() && !aComponent.isFocusCycleRoot(aContainer)) {
            throw new IllegalArgumentException("aContainer is not a focus cycle root of aComponent");
        }

        synchronized(aContainer.getTreeLock()) {

            if (!(aContainer.isVisible() && aContainer.isDisplayable())) {
                return null;
            }

            // See if the component is inside of policy provider.
            Container provider = getTopmostProvider(aContainer, aComponent);
            if (provider != null) {
                if (log.isLoggable(PlatformLogger.Level.FINE)) {
                    log.fine("### Asking FTP " + provider + " for component after " + aComponent);
                }

                // FTP knows how to find component after the given. We don't.
                FocusTraversalPolicy policy = provider.getFocusTraversalPolicy();
                Component beforeComp = policy.getComponentBefore(provider, aComponent);

                // Null result means that we overstepped the limit of the FTP's cycle.
                // In that case we must quit the cycle, otherwise return the component found.
                if (beforeComp != null) {
                    if (log.isLoggable(PlatformLogger.Level.FINE)) {
                        log.fine("### FTP returned " + beforeComp);
                    }
                    return beforeComp;
                }
                aComponent = provider;

                // If the provider is traversable it's returned.
                if (accept(aComponent)) {
                    return aComponent;
                }
            }

            List<Component> cycle = getFocusTraversalCycle(aContainer);

            if (log.isLoggable(PlatformLogger.Level.FINE)) {
                log.fine("### Cycle is " + cycle + ", component is " + aComponent);
            }

            int index = getComponentIndex(cycle, aComponent);

            if (index < 0) {
                if (log.isLoggable(PlatformLogger.Level.FINE)) {
                    log.fine("### Didn't find component " + aComponent + " in a cycle " + aContainer);
                }
                return getLastComponent(aContainer);
            }

            Component comp = null;
            Component tryComp = null;

            for (index--; index>=0; index--) {
                comp = cycle.get(index);
                if (comp != aContainer && (tryComp = getComponentDownCycle(comp, BACKWARD_TRAVERSAL)) != null) {
                    return tryComp;
                } else if (accept(comp)) {
                    return comp;
                }
            }

            if (aContainer.isFocusCycleRoot()) {
                this.cachedRoot = aContainer;
                this.cachedCycle = cycle;

                comp = getLastComponent(aContainer);

                this.cachedRoot = null;
                this.cachedCycle = null;

                return comp;
            }
        }
        return null;
    }

    /**
     * Returns the first Component in the traversal cycle. This method is used
     * to determine the next Component to focus when traversal wraps in the
     * forward direction.
     *
     * <p>
     *  返回遍历循环中的第一个组件。此方法用于确定在向前方向上遍历卷绕时要聚焦的下一个组件。
     * 
     * 
     * @param aContainer the focus cycle root or focus traversal policy provider whose first
     *        Component is to be returned
     * @return the first Component in the traversal cycle of aContainer,
     *         or null if no suitable Component can be found
     * @throws IllegalArgumentException if aContainer is null
     */
    public Component getFirstComponent(Container aContainer) {
        List<Component> cycle;

        if (log.isLoggable(PlatformLogger.Level.FINE)) {
            log.fine("### Getting first component in " + aContainer);
        }
        if (aContainer == null) {
            throw new IllegalArgumentException("aContainer cannot be null");

        }

        synchronized(aContainer.getTreeLock()) {

            if (!(aContainer.isVisible() && aContainer.isDisplayable())) {
                return null;
            }

            if (this.cachedRoot == aContainer) {
                cycle = this.cachedCycle;
            } else {
                cycle = getFocusTraversalCycle(aContainer);
            }

            if (cycle.size() == 0) {
                if (log.isLoggable(PlatformLogger.Level.FINE)) {
                    log.fine("### Cycle is empty");
                }
                return null;
            }
            if (log.isLoggable(PlatformLogger.Level.FINE)) {
                log.fine("### Cycle is " + cycle);
            }

            for (Component comp : cycle) {
                if (accept(comp)) {
                    return comp;
                } else if (comp != aContainer &&
                           (comp = getComponentDownCycle(comp, FORWARD_TRAVERSAL)) != null)
                {
                    return comp;
                }
            }
        }
        return null;
    }

    /**
     * Returns the last Component in the traversal cycle. This method is used
     * to determine the next Component to focus when traversal wraps in the
     * reverse direction.
     *
     * <p>
     *  返回遍历循环中的最后一个组件。此方法用于确定下一个要在相反方向上遍历卷绕时要聚焦的组件。
     * 
     * 
     * @param aContainer the focus cycle root or focus traversal policy provider whose last
     *        Component is to be returned
     * @return the last Component in the traversal cycle of aContainer,
     *         or null if no suitable Component can be found
     * @throws IllegalArgumentException if aContainer is null
     */
    public Component getLastComponent(Container aContainer) {
        List<Component> cycle;
        if (log.isLoggable(PlatformLogger.Level.FINE)) {
            log.fine("### Getting last component in " + aContainer);
        }

        if (aContainer == null) {
            throw new IllegalArgumentException("aContainer cannot be null");
        }

        synchronized(aContainer.getTreeLock()) {

            if (!(aContainer.isVisible() && aContainer.isDisplayable())) {
                return null;
            }

            if (this.cachedRoot == aContainer) {
                cycle = this.cachedCycle;
            } else {
                cycle = getFocusTraversalCycle(aContainer);
            }

            if (cycle.size() == 0) {
                if (log.isLoggable(PlatformLogger.Level.FINE)) {
                    log.fine("### Cycle is empty");
                }
                return null;
            }
            if (log.isLoggable(PlatformLogger.Level.FINE)) {
                log.fine("### Cycle is " + cycle);
            }

            for (int i= cycle.size() - 1; i >= 0; i--) {
                Component comp = cycle.get(i);
                if (accept(comp)) {
                    return comp;
                } else if (comp instanceof Container && comp != aContainer) {
                    Container cont = (Container)comp;
                    if (cont.isFocusTraversalPolicyProvider()) {
                        return cont.getFocusTraversalPolicy().getLastComponent(cont);
                    }
                }
            }
        }
        return null;
    }

    /**
     * Returns the default Component to focus. This Component will be the first
     * to receive focus when traversing down into a new focus traversal cycle
     * rooted at aContainer. The default implementation of this method
     * returns the same Component as <code>getFirstComponent</code>.
     *
     * <p>
     *  返回要聚焦的默认组件。这个组件将是第一个接收焦点,当向下移动到一个新的焦点遍历循环根源于一个容器。此方法的默认实现返回与<code> getFirstComponent </code>相同的组件。
     * 
     * 
     * @param aContainer the focus cycle root or focus traversal policy provider whose default
     *        Component is to be returned
     * @return the default Component in the traversal cycle of aContainer,
     *         or null if no suitable Component can be found
     * @see #getFirstComponent
     * @throws IllegalArgumentException if aContainer is null
     */
    public Component getDefaultComponent(Container aContainer) {
        return getFirstComponent(aContainer);
    }

    /**
     * Sets whether this ContainerOrderFocusTraversalPolicy transfers focus
     * down-cycle implicitly. If <code>true</code>, during normal forward focus
     * traversal, the Component traversed after a focus cycle root will be the
     * focus-cycle-root's default Component to focus. If <code>false</code>,
     * the next Component in the focus traversal cycle rooted at the specified
     * focus cycle root will be traversed instead. The default value for this
     * property is <code>true</code>.
     *
     * <p>
     * 设置此ContainerOrderFocusTraversalPolicy是否隐式传输聚焦下降周期。
     * 如果<code> true </code>,则在正常向前聚焦遍历期间,在焦点循环根之后遍历的组件将是焦点循环根的要聚焦的默认组件。
     * 如果<code> false </code>,则将遍历遍历指定焦点循环根的焦点遍历循环中的下一个组件。此属性的默认值为<code> true </code>。
     * 
     * 
     * @param implicitDownCycleTraversal whether this
     *        ContainerOrderFocusTraversalPolicy transfers focus down-cycle
     *        implicitly
     * @see #getImplicitDownCycleTraversal
     * @see #getFirstComponent
     */
    public void setImplicitDownCycleTraversal(boolean implicitDownCycleTraversal) {
        this.implicitDownCycleTraversal = implicitDownCycleTraversal;
    }

    /**
     * Returns whether this ContainerOrderFocusTraversalPolicy transfers focus
     * down-cycle implicitly. If <code>true</code>, during normal forward focus
     * traversal, the Component traversed after a focus cycle root will be the
     * focus-cycle-root's default Component to focus. If <code>false</code>,
     * the next Component in the focus traversal cycle rooted at the specified
     * focus cycle root will be traversed instead.
     *
     * <p>
     *  返回此ContainerOrderFocusTraversalPolicy是否隐式地传输焦点下降周期。
     * 如果<code> true </code>,则在正常向前聚焦遍历期间,在焦点循环根之后遍历的组件将是焦点循环根的要聚焦的默认组件。
     * 如果<code> false </code>,则将遍历遍历指定焦点循环根的焦点遍历循环中的下一个组件。
     * 
     * 
     * @return whether this ContainerOrderFocusTraversalPolicy transfers focus
     *         down-cycle implicitly
     * @see #setImplicitDownCycleTraversal
     * @see #getFirstComponent
     */
    public boolean getImplicitDownCycleTraversal() {
        return implicitDownCycleTraversal;
    }

    /**
     * Determines whether a Component is an acceptable choice as the new
     * focus owner. By default, this method will accept a Component if and
     * only if it is visible, displayable, enabled, and focusable.
     *
     * <p>
     * 
     * @param aComponent the Component whose fitness as a focus owner is to
     *        be tested
     * @return <code>true</code> if aComponent is visible, displayable,
     *         enabled, and focusable; <code>false</code> otherwise
     */
    protected boolean accept(Component aComponent) {
        if (!aComponent.canBeFocusOwner()) {
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

        return true;
    }
}
