/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2013, Oracle and/or its affiliates. All rights reserved.
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

package javax.accessibility;

/**
 * The AccessibleAction interface should be supported by any object
 * that can perform one or more actions.  This interface
 * provides the standard mechanism for an assistive technology to determine
 * what those actions are as well as tell the object to perform them.
 * Any object that can be manipulated should support this
 * interface.  Applications can determine if an object supports the
 * AccessibleAction interface by first obtaining its AccessibleContext (see
 * {@link Accessible}) and then calling the {@link AccessibleContext#getAccessibleAction}
 * method.  If the return value is not null, the object supports this interface.
 *
 * <p>
 *  AccessibleAction接口应该由可以执行一个或多个操作的任何对象支持。此接口为辅助技术提供标准机制,以确定这些操作是什么,并告诉对象执行它们。任何可以操作的对象都应该支持这个接口。
 * 应用程序可以通过首先获取其AccessibleContext(参见{@link Accessible})然后调用{@link AccessibleContext#getAccessibleAction}
 * 方法来确定对象是否支持AccessibleAction接口。
 *  AccessibleAction接口应该由可以执行一个或多个操作的任何对象支持。此接口为辅助技术提供标准机制,以确定这些操作是什么,并告诉对象执行它们。任何可以操作的对象都应该支持这个接口。
 * 如果返回值不为null,则对象支持此接口。
 * 
 * 
 * @see Accessible
 * @see Accessible#getAccessibleContext
 * @see AccessibleContext
 * @see AccessibleContext#getAccessibleAction
 *
 * @author      Peter Korn
 * @author      Hans Muller
 * @author      Willie Walker
 * @author      Lynn Monsanto
 */
public interface AccessibleAction {

    /**
     * An action which causes a tree node to
     * collapse if expanded and expand if collapsed.
     * <p>
     *  一个操作,它导致树节点展开时合拢,如果折叠则展开。
     * 
     * 
     * @since 1.5
     */
    public static final String TOGGLE_EXPAND =
        new String ("toggleexpand");

    /**
     * An action which increments a value.
     * <p>
     *  使值递增的操作。
     * 
     * 
     * @since 1.5
     */
    public static final String INCREMENT =
        new String ("increment");


    /**
     * An action which decrements a value.
     * <p>
     *  使值递减的动作。
     * 
     * 
     * @since 1.5
     */
    public static final String DECREMENT =
        new String ("decrement");

    /**
     * An action which causes a component to execute its default action.
     * <p>
     *  使组件执行其默认操作的操作。
     * 
     * 
     * @since 1.6
     */
    public static final String CLICK = new String("click");

    /**
     * An action which causes a popup to become visible if it is hidden and
     * hidden if it is visible.
     * <p>
     *  一个动作,使弹出窗口在隐藏时可见,如果可见。
     * 
     * 
     * @since 1.6
     */
    public static final String TOGGLE_POPUP = new String("toggle popup");

    /**
     * Returns the number of accessible actions available in this object
     * If there are more than one, the first one is considered the "default"
     * action of the object.
     *
     * <p>
     *  返回此对象中可用的可用操作数如果有多个,则第一个被视为对象的"默认"操作。
     * 
     * 
     * @return the zero-based number of Actions in this object
     */
    public int getAccessibleActionCount();

    /**
     * Returns a description of the specified action of the object.
     *
     * <p>
     *  返回对象的指定操作的描述。
     * 
     * 
     * @param i zero-based index of the actions
     * @return a String description of the action
     * @see #getAccessibleActionCount
     */
    public String getAccessibleActionDescription(int i);

    /**
     * Performs the specified Action on the object
     *
     * <p>
     *  对对象执行指定的Action
     * 
     * @param i zero-based index of actions
     * @return true if the action was performed; otherwise false.
     * @see #getAccessibleActionCount
     */
    public boolean doAccessibleAction(int i);
}
