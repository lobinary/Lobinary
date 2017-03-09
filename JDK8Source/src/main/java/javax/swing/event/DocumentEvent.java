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
package javax.swing.event;

import javax.swing.undo.*;
import javax.swing.text.*;

/**
 * Interface for document change notifications.  This provides
 * detailed information to Document observers about how the
 * Document changed.  It provides high level information such
 * as type of change and where it occurred, as well as the more
 * detailed structural changes (What Elements were inserted and
 * removed).
 *
 * <p>
 *  文档更改通知接口。这向文档观察员提供了有关文档如何更改的详细信息。它提供高级信息,例如变化类型和发生的地方,以及更详细的结构变化(插入和删除元素)。
 * 
 * 
 * @author  Timothy Prinzing
 * @see javax.swing.text.Document
 * @see DocumentListener
 */
public interface DocumentEvent {

    /**
     * Returns the offset within the document of the start
     * of the change.
     *
     * <p>
     *  返回文档中更改开始处的偏移量。
     * 
     * 
     * @return the offset &gt;= 0
     */
    public int getOffset();

    /**
     * Returns the length of the change.
     *
     * <p>
     *  返回更改的长度。
     * 
     * 
     * @return the length &gt;= 0
     */
    public int getLength();

    /**
     * Gets the document that sourced the change event.
     *
     * <p>
     *  获取发生更改事件的文档。
     * 
     * 
     * @return the document
     */
    public Document getDocument();

    /**
     * Gets the type of event.
     *
     * <p>
     *  获取事件的类型。
     * 
     * 
     * @return the type
     */
    public EventType getType();

    /**
     * Gets the change information for the given element.
     * The change information describes what elements were
     * added and removed and the location.  If there were
     * no changes, null is returned.
     * <p>
     * This method is for observers to discover the structural
     * changes that were made.  This means that only elements
     * that existed prior to the mutation (and still exist after
     * the mutation) need to have ElementChange records.
     * The changes made available need not be recursive.
     * <p>
     * For example, if the an element is removed from it's
     * parent, this method should report that the parent
     * changed and provide an ElementChange implementation
     * that describes the change to the parent.  If the
     * child element removed had children, these elements
     * do not need to be reported as removed.
     * <p>
     * If an child element is insert into a parent element,
     * the parent element should report a change.  If the
     * child element also had elements inserted into it
     * (grandchildren to the parent) these elements need
     * not report change.
     *
     * <p>
     *  获取给定元素的更改信息。更改信息描述添加和删除的元素以及位置。如果没有更改,则返回null。
     * <p>
     *  这种方法是观察者发现所做的结构变化。这意味着只有在突变之前存在的元素(并且仍然存在于突变之后)需要具有ElementChange记录。可用的更改不需要递归。
     * <p>
     *  例如,如果a元素从其父级中删除,则此方法应报告父级更改,并提供一个ElementChange实现来描述对父级的更改。如果删除的子元素有子元素,则不需要将这些元素报告为已删除。
     * <p>
     * 如果子元素插入到父元素中,则父元素应报告更改。如果子元素也有元素插入(孙子孙到父元素),这些元素不需要报告更改。
     * 
     * 
     * @param elem the element
     * @return the change information, or null if the
     *   element was not modified
     */
    public ElementChange getChange(Element elem);

    /**
     * Enumeration for document event types
     * <p>
     *  文档事件类型的枚举
     * 
     */
    public static final class EventType {

        private EventType(String s) {
            typeString = s;
        }

        /**
         * Insert type.
         * <p>
         *  插入类型。
         * 
         */
        public static final EventType INSERT = new EventType("INSERT");

        /**
         * Remove type.
         * <p>
         *  删除类型。
         * 
         */
        public static final EventType REMOVE = new EventType("REMOVE");

        /**
         * Change type.
         * <p>
         *  更改类型。
         * 
         */
        public static final EventType CHANGE = new EventType("CHANGE");

        /**
         * Converts the type to a string.
         *
         * <p>
         *  将类型转换为字符串。
         * 
         * 
         * @return the string
         */
        public String toString() {
            return typeString;
        }

        private String typeString;
    }

    /**
     * Describes changes made to a specific element.
     * <p>
     *  描述对特定元素所做的更改。
     * 
     */
    public interface ElementChange {

        /**
         * Returns the element represented.  This is the element
         * that was changed.
         *
         * <p>
         *  返回所表示的元素。这是被改变的元素。
         * 
         * 
         * @return the element
         */
        public Element getElement();

        /**
         * Fetches the index within the element represented.
         * This is the location that children were added
         * and/or removed.
         *
         * <p>
         *  获取表示的元素中的索引。这是孩子被添加和/或删除的位置。
         * 
         * 
         * @return the index &gt;= 0
         */
        public int getIndex();

        /**
         * Gets the child elements that were removed from the
         * given parent element.  The element array returned is
         * sorted in the order that the elements used to lie in
         * the document, and must be contiguous.
         *
         * <p>
         *  获取从给定父元素中删除的子元素。返回的元素数组按照用于位于文档中的元素的顺序进行排序,并且必须是连续的。
         * 
         * 
         * @return the child elements
         */
        public Element[] getChildrenRemoved();

        /**
         * Gets the child elements that were added to the given
         * parent element.  The element array returned is in the
         * order that the elements lie in the document, and must
         * be contiguous.
         *
         * <p>
         *  获取添加到给定父元素的子元素。返回的元素数组按照元素在文档中的顺序,并且必须是连续的。
         * 
         * @return the child elements
         */
        public Element[] getChildrenAdded();

    }
}
