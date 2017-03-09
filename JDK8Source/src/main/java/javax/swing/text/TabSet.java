/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 2013, Oracle and/or its affiliates. All rights reserved.
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

package javax.swing.text;

import java.io.Serializable;

/**
 * A TabSet is comprised of many TabStops. It offers methods for locating the
 * closest TabStop to a given position and finding all the potential TabStops.
 * It is also immutable.
 * <p>
 * <strong>Warning:</strong>
 * Serialized objects of this class will not be compatible with
 * future Swing releases. The current serialization support is
 * appropriate for short term storage or RMI between applications running
 * the same version of Swing.  As of 1.4, support for long term storage
 * of all JavaBeans&trade;
 * has been added to the <code>java.beans</code> package.
 * Please see {@link java.beans.XMLEncoder}.
 *
 * <p>
 *  TabSet由许多TabStops组成。它提供了将最接近的TabStop定位到给定位置并找到所有潜在的TabStop的方法。它也是不可变的。
 * <p>
 *  <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
 *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
 * 
 * 
 * @author  Scott Violet
 */
public class TabSet implements Serializable
{
    /** TabStops this TabSet contains. */
    private TabStop[]              tabs;
    /**
     * Since this class is immutable the hash code could be
     * calculated once. MAX_VALUE means that it was not initialized
     * yet. Hash code shouldn't has MAX_VALUE value.
     * <p>
     *  由于这个类是不可变的,所以可以计算一次哈希码。 MAX_VALUE表示尚未初始化。哈希代码不得有MAX_VALUE个值。
     * 
     */
    private int hashCode = Integer.MAX_VALUE;

    /**
     * Creates and returns an instance of TabSet. The array of Tabs
     * passed in must be sorted in ascending order.
     * <p>
     *  创建并返回TabSet的实例。传入的Tab数组必须按升序排序。
     * 
     */
    public TabSet(TabStop[] tabs) {
        // PENDING(sky): If this becomes a problem, make it sort.
        if(tabs != null) {
            int          tabCount = tabs.length;

            this.tabs = new TabStop[tabCount];
            System.arraycopy(tabs, 0, this.tabs, 0, tabCount);
        }
        else
            this.tabs = null;
    }

    /**
     * Returns the number of Tab instances the receiver contains.
     * <p>
     *  返回接收器包含的Tab实例的数量。
     * 
     */
    public int getTabCount() {
        return (tabs == null) ? 0 : tabs.length;
    }

    /**
     * Returns the TabStop at index <code>index</code>. This will throw an
     * IllegalArgumentException if <code>index</code> is outside the range
     * of tabs.
     * <p>
     *  返回索引<code> index </code>处的TabStop。如果<code> index </code>在标签范围之外,这将抛出IllegalArgumentException。
     * 
     */
    public TabStop getTab(int index) {
        int          numTabs = getTabCount();

        if(index < 0 || index >= numTabs)
            throw new IllegalArgumentException(index +
                                              " is outside the range of tabs");
        return tabs[index];
    }

    /**
     * Returns the Tab instance after <code>location</code>. This will
     * return null if there are no tabs after <code>location</code>.
     * <p>
     *  返回<code> location </code>后的Tab实例。如果<code> location </code>后面没有标签,则会返回null。
     * 
     */
    public TabStop getTabAfter(float location) {
        int     index = getTabIndexAfter(location);

        return (index == -1) ? null : tabs[index];
    }

    /**
    /* <p>
    /* 
     * @return the index of the TabStop <code>tab</code>, or -1 if
     * <code>tab</code> is not contained in the receiver.
     */
    public int getTabIndex(TabStop tab) {
        for(int counter = getTabCount() - 1; counter >= 0; counter--)
            // should this use .equals?
            if(getTab(counter) == tab)
                return counter;
        return -1;
    }

    /**
     * Returns the index of the Tab to be used after <code>location</code>.
     * This will return -1 if there are no tabs after <code>location</code>.
     * <p>
     *  返回要在<code> location </code>之后使用的Tab的索引。如果<code> location </code>后面没有标签,则会返回-1。
     * 
     */
    public int getTabIndexAfter(float location) {
        int     current, min, max;

        min = 0;
        max = getTabCount();
        while(min != max) {
            current = (max - min) / 2 + min;
            if(location > tabs[current].getPosition()) {
                if(min == current)
                    min = max;
                else
                    min = current;
            }
            else {
                if(current == 0 || location > tabs[current - 1].getPosition())
                    return current;
                max = current;
            }
        }
        // no tabs after the passed in location.
        return -1;
    }

    /**
     * Indicates whether this <code>TabSet</code> is equal to another one.
     * <p>
     * 指示此<code> TabSet </code>是否等于另一个。
     * 
     * 
     * @param o the <code>TabSet</code> instance which this instance
     *  should be compared to.
     * @return <code>true</code> if <code>o</code> is the instance of
     * <code>TabSet</code>, has the same number of <code>TabStop</code>s
     * and they are all equal, <code>false</code> otherwise.
     *
     * @since 1.5
     */
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof TabSet) {
            TabSet ts = (TabSet) o;
            int count = getTabCount();
            if (ts.getTabCount() != count) {
                return false;
            }
            for (int i=0; i < count; i++) {
                TabStop ts1 = getTab(i);
                TabStop ts2 = ts.getTab(i);
                if ((ts1 == null && ts2 != null) ||
                        (ts1 != null && !getTab(i).equals(ts.getTab(i)))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Returns a hashcode for this set of TabStops.
     * <p>
     *  返回此TabStops集合的哈希码。
     * 
     * 
     * @return  a hashcode value for this set of TabStops.
     *
     * @since 1.5
     */
    public int hashCode() {
        if (hashCode == Integer.MAX_VALUE) {
            hashCode = 0;
            int len = getTabCount();
            for (int i = 0; i < len; i++) {
                TabStop ts = getTab(i);
                hashCode ^= ts != null ? getTab(i).hashCode() : 0;
            }
            if (hashCode == Integer.MAX_VALUE) {
                hashCode -= 1;
            }
        }
        return hashCode;
    }

    /**
     * Returns the string representation of the set of tabs.
     * <p>
     *  返回选项卡集的字符串表示形式。
     */
    public String toString() {
        int            tabCount = getTabCount();
        StringBuilder buffer = new StringBuilder("[ ");

        for(int counter = 0; counter < tabCount; counter++) {
            if(counter > 0)
                buffer.append(" - ");
            buffer.append(getTab(counter).toString());
        }
        buffer.append(" ]");
        return buffer.toString();
    }
}
