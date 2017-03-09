/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2014, Oracle and/or its affiliates. All rights reserved.
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

import java.util.EventObject;
import javax.swing.event.*;

/**
 * This interface defines the methods any general editor should be able
 * to implement. <p>
 *
 * Having this interface enables complex components (the client of the
 * editor) such as <code>JTree</code> and
 * <code>JTable</code> to allow any generic editor to
 * edit values in a table cell, or tree cell, etc.  Without this generic
 * editor interface, <code>JTable</code> would have to know about specific editors,
 * such as <code>JTextField</code>, <code>JCheckBox</code>, <code>JComboBox</code>,
 * etc.  In addition, without this interface, clients of editors such as
 * <code>JTable</code> would not be able
 * to work with any editors developed in the future by the user
 * or a 3rd party ISV. <p>
 *
 * To use this interface, a developer creating a new editor can have the
 * new component implement the interface.  Or the developer can
 * choose a wrapper based approach and provide a companion object which
 * implements the <code>CellEditor</code> interface (See
 * <code>DefaultCellEditor</code> for example).  The wrapper approach
 * is particularly useful if the user want to use a 3rd party ISV
 * editor with <code>JTable</code>, but the ISV didn't implement the
 * <code>CellEditor</code> interface.  The user can simply create an object
 * that contains an instance of the 3rd party editor object and "translate"
 * the <code>CellEditor</code> API into the 3rd party editor's API.
 *
 * <p>
 *  此接口定义任何通用编辑器应能够实现的方法。 <p>
 * 
 *  具有此接口使得诸如<code> JTree </code>和<code> JTable </code>的复杂组件(编辑器的客户端)允许任何通用编辑器编辑表单元格或树单元格等中的值没有这个通用编辑器接口
 * ,<code> JTable </code>必须知道特定的编辑器,如<code> JTextField </code>,<code> JCheckBox </code>,<code> JComboBox
 *  </code >等。
 * 此外,没有这个接口,编辑器的客户端例如<code> JTable </code>将不能够与未来由用户或第三方ISV开发的任何编辑器一起工作。 <p>。
 * 
 *  要使用此接口,创建新编辑器的开发人员可以使新组件实现接口。
 * 或者开发人员可以选择基于包装器的方法,并提供实现<code> CellEditor </code>接口的配套对象(例如参见<code> DefaultCellEditor </code>)。
 * 如果用户想要使用带有<code> JTable </code>的第三方ISV编辑器,但是ISV没有实现<code> CellEditor </code>接口,则封装器方法特别有用。
 * 用户可以简单地创建一个对象,其中包含第三方编辑器对象的实例,并将<code> CellEditor </code> API转换为第三方编辑器的API。
 * 
 * 
 * @see javax.swing.event.CellEditorListener
 *
 * @author Alan Chung
 */
public interface CellEditor {

    /**
     * Returns the value contained in the editor.
     * <p>
     * 返回编辑器中包含的值。
     * 
     * 
     * @return the value contained in the editor
     */
    public Object getCellEditorValue();

    /**
     * Asks the editor if it can start editing using <code>anEvent</code>.
     * <code>anEvent</code> is in the invoking component coordinate system.
     * The editor can not assume the Component returned by
     * <code>getCellEditorComponent</code> is installed.  This method
     * is intended for the use of client to avoid the cost of setting up
     * and installing the editor component if editing is not possible.
     * If editing can be started this method returns true.
     *
     * <p>
     *  询问编辑器是否可以使用<code> anEvent </code>开始编辑。 <code> anEvent </code>在调用组件坐标系中。
     * 编辑器不能假定由<code> getCellEditorComponent </code>返回的组件已安装。此方法适用于客户端的使用,以避免在无法编辑的情况下设置和安装编辑器组件的成本。
     * 如果可以启动编辑,此方法将返回true。
     * 
     * 
     * @param   anEvent         the event the editor should use to consider
     *                          whether to begin editing or not
     * @return  true if editing can be started
     * @see #shouldSelectCell
     */
    public boolean isCellEditable(EventObject anEvent);

    /**
     * Returns true if the editing cell should be selected, false otherwise.
     * Typically, the return value is true, because is most cases the editing
     * cell should be selected.  However, it is useful to return false to
     * keep the selection from changing for some types of edits.
     * eg. A table that contains a column of check boxes, the user might
     * want to be able to change those checkboxes without altering the
     * selection.  (See Netscape Communicator for just such an example)
     * Of course, it is up to the client of the editor to use the return
     * value, but it doesn't need to if it doesn't want to.
     *
     * <p>
     *  如果应选择编辑单元格,则返回true,否则返回false。通常,返回值为true,因为大多数情况下应选择编辑单元格。但是,返回false以防止对某些类型的编辑更改选择非常有用。例如。
     * 包含一列复选框的表,用户可能希望能够更改这些复选框而不更改选择。 (对于这样的示例,请参阅Netscape Communicator)当然,由编辑器的客户端使用返回值,但它不需要,如果它不想要。
     * 
     * 
     * @param   anEvent         the event the editor should use to start
     *                          editing
     * @return  true if the editor would like the editing cell to be selected;
     *    otherwise returns false
     * @see #isCellEditable
     */
    public boolean shouldSelectCell(EventObject anEvent);

    /**
     * Tells the editor to stop editing and accept any partially edited
     * value as the value of the editor.  The editor returns false if
     * editing was not stopped; this is useful for editors that validate
     * and can not accept invalid entries.
     *
     * <p>
     *  指示编辑器停止编辑并接受任何部分编辑的值作为编辑器的值。如果编辑未停止,编辑器返回false;这对于验证并且不能接受无效条目的编辑者很有用。
     * 
     * 
     * @return  true if editing was stopped; false otherwise
     */
    public boolean stopCellEditing();

    /**
     * Tells the editor to cancel editing and not accept any partially
     * edited value.
     * <p>
     *  指示编辑器取消编辑,不接受任何部分编辑的值。
     * 
     */
    public void cancelCellEditing();

    /**
     * Adds a listener to the list that's notified when the editor
     * stops, or cancels editing.
     *
     * <p>
     *  将编辑器添加到编辑器停止时通知的列表中,或者取消编辑。
     * 
     * 
     * @param   l               the CellEditorListener
     */
    public void addCellEditorListener(CellEditorListener l);

    /**
     * Removes a listener from the list that's notified
     *
     * <p>
     * 从通知的列表中删除侦听器
     * 
     * @param   l               the CellEditorListener
     */
    public void removeCellEditorListener(CellEditorListener l);
}
