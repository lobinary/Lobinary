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
package javax.swing.text;

import java.io.Serializable;

/**
 * This class encapsulates a single tab stop (basically as tab stops
 * are thought of by RTF). A tab stop is at a specified distance from the
 * left margin, aligns text in a specified way, and has a specified leader.
 * TabStops are immutable, and usually contained in TabSets.
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
 *  这个类封装了单个制表符(基本上由RTF考虑的制表符)。制表位位于距左边距指定距离处,以指定方式对齐文本,并具有指定的首位。 TabStops是不可变的,通常包含在TabSets中。
 * <p>
 *  <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
 *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
 * 
 */
public class TabStop implements Serializable {

    /** Character following tab is positioned at location. */
    public static final int ALIGN_LEFT    = 0;
    /** Characters following tab are positioned such that all following
    /* <p>
    /* 
     * characters up to next tab/newline end at location. */
    public static final int ALIGN_RIGHT   = 1;
    /** Characters following tab are positioned such that all following
     * characters up to next tab/newline are centered around the tabs
     * <p>
     *  最多到下一个标签/换行符的字符以标签为中心
     * 
     * 
     * location. */
    public static final int ALIGN_CENTER  = 2;
    /** Characters following tab are aligned such that next
     * decimal/tab/newline is at the tab location, very similar to
     * RIGHT_TAB, just includes decimal as additional character to look for.
     * <p>
     *  十进制/制表符/换行符是在制表符位置,非常类似于RIGHT_TAB,只是包括十进制作为附加字符寻找。
     * 
     */
    public static final int ALIGN_DECIMAL = 4;
    public static final int ALIGN_BAR     = 5;

    /* Bar tabs (whatever they are) are actually a separate kind of tab
       in the RTF spec. However, being a bar tab and having alignment
       properties are mutually exclusive, so the reader treats barness
    /* <p>
    /*  在RTF规范。然而,作为条形图并且具有对齐属性是相互排斥的,因此读者对待粗糙度
    /* 
    /* 
       as being a kind of alignment. */

    public static final int LEAD_NONE      = 0;
    public static final int LEAD_DOTS      = 1;
    public static final int LEAD_HYPHENS   = 2;
    public static final int LEAD_UNDERLINE = 3;
    public static final int LEAD_THICKLINE = 4;
    public static final int LEAD_EQUALS    = 5;

    /** Tab type. */
    private int alignment;
    /** Location, from the left margin, that tab is at. */
    private float position;
    private int leader;

    /**
     * Creates a tab at position <code>pos</code> with a default alignment
     * and default leader.
     * <p>
     *  在位置<code> pos </code>中使用默认对齐方式和默认前导创建一个标签。
     * 
     */
    public TabStop(float pos) {
        this(pos, ALIGN_LEFT, LEAD_NONE);
    }

    /**
     * Creates a tab with the specified position <code>pos</code>,
     * alignment <code>align</code> and leader <code>leader</code>.
     * <p>
     *  创建具有指定位置<code> pos </code>,对齐<code> align </code>和leader <code> leader </code>的制表符。
     * 
     */
    public TabStop(float pos, int align, int leader) {
        alignment = align;
        this.leader = leader;
        position = pos;
    }

    /**
     * Returns the position, as a float, of the tab.
     * <p>
     *  返回制表符的位置(作为浮点)。
     * 
     * 
     * @return the position of the tab
     */
    public float getPosition() {
        return position;
    }

    /**
     * Returns the alignment, as an integer, of the tab.
     * <p>
     *  返回制表符的整数对齐。
     * 
     * 
     * @return the alignment of the tab
     */
    public int getAlignment() {
        return alignment;
    }

    /**
     * Returns the leader of the tab.
     * <p>
     *  返回制表符的标题。
     * 
     * 
     * @return the leader of the tab
     */
    public int getLeader() {
        return leader;
    }

    /**
     * Returns true if the tabs are equal.
     * <p>
     * 如果选项卡相等,则返回true。
     * 
     * 
     * @return true if the tabs are equal, otherwise false
     */
    public boolean equals(Object other)
    {
        if (other == this) {
            return true;
        }
        if (other instanceof TabStop) {
            TabStop o = (TabStop)other;
            return ( (alignment == o.alignment) &&
                     (leader == o.leader) &&
                     (position == o.position) );  /* TODO: epsilon */
        }
        return false;
    }

    /**
     * Returns the hashCode for the object.  This must be defined
     * here to ensure 100% pure.
     *
     * <p>
     *  返回对象的hashCode。这必须在这里定义,以确保100％纯。
     * 
     * @return the hashCode for the object
     */
    public int hashCode() {
        return alignment ^ leader ^ Math.round(position);
    }

    /* This is for debugging; perhaps it should be removed before release */
    public String toString() {
        String buf;
        switch(alignment) {
          default:
          case ALIGN_LEFT:
            buf = "";
            break;
          case ALIGN_RIGHT:
            buf = "right ";
            break;
          case ALIGN_CENTER:
            buf = "center ";
            break;
          case ALIGN_DECIMAL:
            buf = "decimal ";
            break;
          case ALIGN_BAR:
            buf = "bar ";
            break;
        }
        buf = buf + "tab @" + String.valueOf(position);
        if (leader != LEAD_NONE)
            buf = buf + " (w/leaders)";
        return buf;
    }
}
