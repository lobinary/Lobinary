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
package javax.swing.plaf.basic;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;

import java.awt.*;

import java.io.Serializable;


/**
 * ComboBox renderer
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
 *  ComboBox渲染器
 * <p>
 *  <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
 *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
 * 
 * 
 * @author Arnaud Weber
 */
public class BasicComboBoxRenderer extends JLabel
implements ListCellRenderer, Serializable {

   /**
    * An empty <code>Border</code>. This field might not be used. To change the
    * <code>Border</code> used by this renderer directly set it using
    * the <code>setBorder</code> method.
    * <p>
    *  一个空的<code> Border </code>。可能不使用此字段。
    * 要更改此渲染器使用的<code> Border </code>,请使用<code> setBorder </code>方法直接设置。
    * 
    */
    protected static Border noFocusBorder = new EmptyBorder(1, 1, 1, 1);
    private final static Border SAFE_NO_FOCUS_BORDER = new EmptyBorder(1, 1, 1, 1);

    public BasicComboBoxRenderer() {
        super();
        setOpaque(true);
        setBorder(getNoFocusBorder());
    }

    private static Border getNoFocusBorder() {
        if (System.getSecurityManager() != null) {
            return SAFE_NO_FOCUS_BORDER;
        } else {
            return noFocusBorder;
        }
    }

    public Dimension getPreferredSize() {
        Dimension size;

        if ((this.getText() == null) || (this.getText().equals( "" ))) {
            setText( " " );
            size = super.getPreferredSize();
            setText( "" );
        }
        else {
            size = super.getPreferredSize();
        }

        return size;
    }

    public Component getListCellRendererComponent(
                                                 JList list,
                                                 Object value,
                                                 int index,
                                                 boolean isSelected,
                                                 boolean cellHasFocus)
    {

        /**if (isSelected) {
            setBackground(UIManager.getColor("ComboBox.selectionBackground"));
            setForeground(UIManager.getColor("ComboBox.selectionForeground"));
        } else {
            setBackground(UIManager.getColor("ComboBox.background"));
            setForeground(UIManager.getColor("ComboBox.foreground"));
        /* <p>
        /*  setBackground(UIManager.getColor("ComboBox.selectionBackground")); setForeground(UIManager.getColor(
        /* "ComboBox.selectionForeground")); } else {setBackground(UIManager.getColor("ComboBox.background")); setForeground(UIManager.getColor("ComboBox.foreground"));。
        /* 
        /* 
        }**/

        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        }
        else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }

        setFont(list.getFont());

        if (value instanceof Icon) {
            setIcon((Icon)value);
        }
        else {
            setText((value == null) ? "" : value.toString());
        }
        return this;
    }


    /**
     * A subclass of BasicComboBoxRenderer that implements UIResource.
     * BasicComboBoxRenderer doesn't implement UIResource
     * directly so that applications can safely override the
     * cellRenderer property with BasicListCellRenderer subclasses.
     * <p>
     * <strong>Warning:</strong>
     * Serialized objects of this class will not be compatible with
     * future Swing releases. The current serialization support is
     * appropriate for short term storage or RMI between applications running
     * the same version of Swing.  As of 1.4, support for long term storage
     * of all JavaBeans&trade;
     * has been added to the <code>java.beans</code> package.
     * Please see {@link java.beans.XMLEncoder}.
     * <p>
     *  实现UIResource的BasicComboBoxRenderer的子类。
     *  BasicComboBoxRenderer不直接实现UIResource,以便应用程序可以安全地覆盖与BasicListCellRenderer子类的cellRenderer属性。
     * <p>
     */
    public static class UIResource extends BasicComboBoxRenderer implements javax.swing.plaf.UIResource {
    }
}
