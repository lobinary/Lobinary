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
package java.awt;

interface EventFilter {

    /**
     * Enumeration for possible values for <code>acceptEvent(AWTEvent ev)</code> method.
     * <p>
     *  枚举<code> acceptEvent(AWTEvent ev)</code>方法的可能值。
     * 
     * 
     * @see EventDispatchThread#pumpEventsForFilter
     */
    static enum FilterAction {
        /**
         * ACCEPT means that this filter do not filter the event and allowes other
         * active filters to proceed it. If all the active filters accept the event, it
         * is dispatched by the <code>EventDispatchThread</code>
         * <p>
         *  ACCEPT表示此过滤器不过滤事件,并允许其他活动过滤器继续处理。如果所有活动过滤器都接受事件,则由<code> EventDispatchThread </code>分派
         * 
         * 
         * @see EventDispatchThread#pumpEventsForFilter
         */
        ACCEPT,
        /**
         * REJECT means that this filter filter the event. No other filters are queried,
         * and the event is not dispatched by the <code>EventDispatchedThread</code>
         * <p>
         *  REJECT表示此过滤器过滤事件。不会查询其他过滤器,并且事件不会由<code> EventDispatchedThread </code>
         * 
         * 
         * @see EventDispatchThread#pumpEventsForFilter
         */
        REJECT,
        /**
         * ACCEPT_IMMEDIATELY means that this filter do not filter the event, no other
         * filters are queried and to proceed it, and it is dispatched by the
         * <code>EventDispatchThread</code>
         * It is not recommended to use ACCEPT_IMMEDIATELY as there may be some active
         * filters not queried yet that do not accept this event. It is primarily used
         * by modal filters.
         * <p>
         *  ACCEPT_IMMEDIATELY意味着这个过滤器不过滤事件,没有其他过滤器被查询和继续它,它由<code> EventDispatchThread </code>分派。
         * 不建议使用ACCEPT_IMMEDIATELY,因为可能有一些活动过滤器不查询但不接受此事件。它主要用于模态滤波器。
         * 
         * @see EventDispatchThread#pumpEventsForFilter
         * @see ModalEventFilter
         */
        ACCEPT_IMMEDIATELY
    };

    FilterAction acceptEvent(AWTEvent ev);
}
