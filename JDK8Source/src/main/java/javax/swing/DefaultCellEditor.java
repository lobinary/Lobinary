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

package javax.swing;

import java.awt.Component;
import java.awt.event.*;
import java.beans.ConstructorProperties;
import java.lang.Boolean;
import javax.swing.table.*;
import javax.swing.event.*;
import java.util.EventObject;
import javax.swing.tree.*;
import java.io.Serializable;

/**
 * The default editor for table and tree cells.
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
 *  表和树单元格的默认编辑器
 * <p>
 *  <strong>警告：</strong>此类的序列化对象将不与未来的Swing版本兼容当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
 * 支持长期存储所有JavaBeans&trade;已添加到<code> javabeans </code>包中请参见{@link javabeansXMLEncoder}。
 * 
 * 
 * @author Alan Chung
 * @author Philip Milne
 */

public class DefaultCellEditor extends AbstractCellEditor
    implements TableCellEditor, TreeCellEditor {

//
//  Instance Variables
//

    /** The Swing component being edited. */
    protected JComponent editorComponent;
    /**
     * The delegate class which handles all methods sent from the
     * <code>CellEditor</code>.
     * <p>
     *  代理类,处理从<code> CellEditor </code>发送的所有方法
     * 
     */
    protected EditorDelegate delegate;
    /**
     * An integer specifying the number of clicks needed to start editing.
     * Even if <code>clickCountToStart</code> is defined as zero, it
     * will not initiate until a click occurs.
     * <p>
     * 指定开始编辑所需的点击次数的整数即使<code> clickCountToStart </code>被定义为零,它也不会启动,直到发生点击
     * 
     */
    protected int clickCountToStart = 1;

//
//  Constructors
//

    /**
     * Constructs a <code>DefaultCellEditor</code> that uses a text field.
     *
     * <p>
     *  构造使用文本字段的<code> DefaultCellEditor </code>
     * 
     * 
     * @param textField  a <code>JTextField</code> object
     */
    @ConstructorProperties({"component"})
    public DefaultCellEditor(final JTextField textField) {
        editorComponent = textField;
        this.clickCountToStart = 2;
        delegate = new EditorDelegate() {
            public void setValue(Object value) {
                textField.setText((value != null) ? value.toString() : "");
            }

            public Object getCellEditorValue() {
                return textField.getText();
            }
        };
        textField.addActionListener(delegate);
    }

    /**
     * Constructs a <code>DefaultCellEditor</code> object that uses a check box.
     *
     * <p>
     *  构造使用复选框的<code> DefaultCellEditor </code>对象
     * 
     * 
     * @param checkBox  a <code>JCheckBox</code> object
     */
    public DefaultCellEditor(final JCheckBox checkBox) {
        editorComponent = checkBox;
        delegate = new EditorDelegate() {
            public void setValue(Object value) {
                boolean selected = false;
                if (value instanceof Boolean) {
                    selected = ((Boolean)value).booleanValue();
                }
                else if (value instanceof String) {
                    selected = value.equals("true");
                }
                checkBox.setSelected(selected);
            }

            public Object getCellEditorValue() {
                return Boolean.valueOf(checkBox.isSelected());
            }
        };
        checkBox.addActionListener(delegate);
        checkBox.setRequestFocusEnabled(false);
    }

    /**
     * Constructs a <code>DefaultCellEditor</code> object that uses a
     * combo box.
     *
     * <p>
     *  构造使用组合框的<code> DefaultCellEditor </code>对象
     * 
     * 
     * @param comboBox  a <code>JComboBox</code> object
     */
    public DefaultCellEditor(final JComboBox comboBox) {
        editorComponent = comboBox;
        comboBox.putClientProperty("JComboBox.isTableCellEditor", Boolean.TRUE);
        delegate = new EditorDelegate() {
            public void setValue(Object value) {
                comboBox.setSelectedItem(value);
            }

            public Object getCellEditorValue() {
                return comboBox.getSelectedItem();
            }

            public boolean shouldSelectCell(EventObject anEvent) {
                if (anEvent instanceof MouseEvent) {
                    MouseEvent e = (MouseEvent)anEvent;
                    return e.getID() != MouseEvent.MOUSE_DRAGGED;
                }
                return true;
            }
            public boolean stopCellEditing() {
                if (comboBox.isEditable()) {
                    // Commit edited value.
                    comboBox.actionPerformed(new ActionEvent(
                                     DefaultCellEditor.this, 0, ""));
                }
                return super.stopCellEditing();
            }
        };
        comboBox.addActionListener(delegate);
    }

    /**
     * Returns a reference to the editor component.
     *
     * <p>
     *  返回对编辑器组件的引用
     * 
     * 
     * @return the editor <code>Component</code>
     */
    public Component getComponent() {
        return editorComponent;
    }

//
//  Modifying
//

    /**
     * Specifies the number of clicks needed to start editing.
     *
     * <p>
     *  指定开始编辑所需的点击次数
     * 
     * 
     * @param count  an int specifying the number of clicks needed to start editing
     * @see #getClickCountToStart
     */
    public void setClickCountToStart(int count) {
        clickCountToStart = count;
    }

    /**
     * Returns the number of clicks needed to start editing.
     * <p>
     *  返回开始编辑所需的点击次数
     * 
     * 
     * @return the number of clicks needed to start editing
     */
    public int getClickCountToStart() {
        return clickCountToStart;
    }

//
//  Override the implementations of the superclass, forwarding all methods
//  from the CellEditor interface to our delegate.
//

    /**
     * Forwards the message from the <code>CellEditor</code> to
     * the <code>delegate</code>.
     * <p>
     *  将消息从<code> CellEditor </code>转发到<code>委托</code>
     * 
     * 
     * @see EditorDelegate#getCellEditorValue
     */
    public Object getCellEditorValue() {
        return delegate.getCellEditorValue();
    }

    /**
     * Forwards the message from the <code>CellEditor</code> to
     * the <code>delegate</code>.
     * <p>
     *  将消息从<code> CellEditor </code>转发到<code>委托</code>
     * 
     * 
     * @see EditorDelegate#isCellEditable(EventObject)
     */
    public boolean isCellEditable(EventObject anEvent) {
        return delegate.isCellEditable(anEvent);
    }

    /**
     * Forwards the message from the <code>CellEditor</code> to
     * the <code>delegate</code>.
     * <p>
     * 将消息从<code> CellEditor </code>转发到<code>委托</code>
     * 
     * 
     * @see EditorDelegate#shouldSelectCell(EventObject)
     */
    public boolean shouldSelectCell(EventObject anEvent) {
        return delegate.shouldSelectCell(anEvent);
    }

    /**
     * Forwards the message from the <code>CellEditor</code> to
     * the <code>delegate</code>.
     * <p>
     *  将消息从<code> CellEditor </code>转发到<code>委托</code>
     * 
     * 
     * @see EditorDelegate#stopCellEditing
     */
    public boolean stopCellEditing() {
        return delegate.stopCellEditing();
    }

    /**
     * Forwards the message from the <code>CellEditor</code> to
     * the <code>delegate</code>.
     * <p>
     *  将消息从<code> CellEditor </code>转发到<code>委托</code>
     * 
     * 
     * @see EditorDelegate#cancelCellEditing
     */
    public void cancelCellEditing() {
        delegate.cancelCellEditing();
    }

//
//  Implementing the TreeCellEditor Interface
//

    /** Implements the <code>TreeCellEditor</code> interface. */
    public Component getTreeCellEditorComponent(JTree tree, Object value,
                                                boolean isSelected,
                                                boolean expanded,
                                                boolean leaf, int row) {
        String         stringValue = tree.convertValueToText(value, isSelected,
                                            expanded, leaf, row, false);

        delegate.setValue(stringValue);
        return editorComponent;
    }

//
//  Implementing the CellEditor Interface
//
    /** Implements the <code>TableCellEditor</code> interface. */
    public Component getTableCellEditorComponent(JTable table, Object value,
                                                 boolean isSelected,
                                                 int row, int column) {
        delegate.setValue(value);
        if (editorComponent instanceof JCheckBox) {
            //in order to avoid a "flashing" effect when clicking a checkbox
            //in a table, it is important for the editor to have as a border
            //the same border that the renderer has, and have as the background
            //the same color as the renderer has. This is primarily only
            //needed for JCheckBox since this editor doesn't fill all the
            //visual space of the table cell, unlike a text field.
            TableCellRenderer renderer = table.getCellRenderer(row, column);
            Component c = renderer.getTableCellRendererComponent(table, value,
                    isSelected, true, row, column);
            if (c != null) {
                editorComponent.setOpaque(true);
                editorComponent.setBackground(c.getBackground());
                if (c instanceof JComponent) {
                    editorComponent.setBorder(((JComponent)c).getBorder());
                }
            } else {
                editorComponent.setOpaque(false);
            }
        }
        return editorComponent;
    }


//
//  Protected EditorDelegate class
//

    /**
     * The protected <code>EditorDelegate</code> class.
     * <p>
     *  受保护的<code> EditorDelegate </code>类
     * 
     */
    protected class EditorDelegate implements ActionListener, ItemListener, Serializable {

        /**  The value of this cell. */
        protected Object value;

       /**
        * Returns the value of this cell.
        * <p>
        *  返回此单元格的值
        * 
        * 
        * @return the value of this cell
        */
        public Object getCellEditorValue() {
            return value;
        }

       /**
        * Sets the value of this cell.
        * <p>
        *  设置此单元格的值
        * 
        * 
        * @param value the new value of this cell
        */
        public void setValue(Object value) {
            this.value = value;
        }

       /**
        * Returns true if <code>anEvent</code> is <b>not</b> a
        * <code>MouseEvent</code>.  Otherwise, it returns true
        * if the necessary number of clicks have occurred, and
        * returns false otherwise.
        *
        * <p>
        *  如果<code> anEvent </code> <b>不是</b>,则返回true;否则,如果发生了必要的点击次数,则返回true,否则返回false
        * 
        * 
        * @param   anEvent         the event
        * @return  true  if cell is ready for editing, false otherwise
        * @see #setClickCountToStart
        * @see #shouldSelectCell
        */
        public boolean isCellEditable(EventObject anEvent) {
            if (anEvent instanceof MouseEvent) {
                return ((MouseEvent)anEvent).getClickCount() >= clickCountToStart;
            }
            return true;
        }

       /**
        * Returns true to indicate that the editing cell may
        * be selected.
        *
        * <p>
        *  返回true表示可以选择编辑单元格
        * 
        * 
        * @param   anEvent         the event
        * @return  true
        * @see #isCellEditable
        */
        public boolean shouldSelectCell(EventObject anEvent) {
            return true;
        }

       /**
        * Returns true to indicate that editing has begun.
        *
        * <p>
        *  返回true表示编辑已开始
        * 
        * 
        * @param anEvent          the event
        */
        public boolean startCellEditing(EventObject anEvent) {
            return true;
        }

       /**
        * Stops editing and
        * returns true to indicate that editing has stopped.
        * This method calls <code>fireEditingStopped</code>.
        *
        * <p>
        *  停止编辑并返回true以指示编辑已停止此方法调用<code> fireEditingStopped </code>
        * 
        * 
        * @return  true
        */
        public boolean stopCellEditing() {
            fireEditingStopped();
            return true;
        }

       /**
        * Cancels editing.  This method calls <code>fireEditingCanceled</code>.
        * <p>
        * 取消编辑此方法调用<code> fireEditingCanceled </code>
        * 
        */
       public void cancelCellEditing() {
           fireEditingCanceled();
       }

       /**
        * When an action is performed, editing is ended.
        * <p>
        *  当执行动作时,编辑结束
        * 
        * 
        * @param e the action event
        * @see #stopCellEditing
        */
        public void actionPerformed(ActionEvent e) {
            DefaultCellEditor.this.stopCellEditing();
        }

       /**
        * When an item's state changes, editing is ended.
        * <p>
        *  当项目的状态改变时,编辑结束
        * 
        * @param e the action event
        * @see #stopCellEditing
        */
        public void itemStateChanged(ItemEvent e) {
            DefaultCellEditor.this.stopCellEditing();
        }
    }

} // End of class JCellEditor
