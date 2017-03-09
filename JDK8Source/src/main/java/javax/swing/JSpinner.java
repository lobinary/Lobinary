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

package javax.swing;

import java.awt.*;
import java.awt.event.*;

import javax.swing.event.*;
import javax.swing.text.*;
import javax.swing.plaf.SpinnerUI;

import java.util.*;
import java.beans.*;
import java.text.*;
import java.io.*;
import java.text.spi.DateFormatProvider;
import java.text.spi.NumberFormatProvider;

import javax.accessibility.*;
import sun.util.locale.provider.LocaleProviderAdapter;
import sun.util.locale.provider.LocaleResources;
import sun.util.locale.provider.LocaleServiceProviderPool;


/**
 * A single line input field that lets the user select a
 * number or an object value from an ordered sequence. Spinners typically
 * provide a pair of tiny arrow buttons for stepping through the elements
 * of the sequence. The keyboard up/down arrow keys also cycle through the
 * elements. The user may also be allowed to type a (legal) value directly
 * into the spinner. Although combo boxes provide similar functionality,
 * spinners are sometimes preferred because they don't require a drop down list
 * that can obscure important data.
 * <p>
 * A <code>JSpinner</code>'s sequence value is defined by its
 * <code>SpinnerModel</code>.
 * The <code>model</code> can be specified as a constructor argument and
 * changed with the <code>model</code> property.  <code>SpinnerModel</code>
 * classes for some common types are provided: <code>SpinnerListModel</code>,
 * <code>SpinnerNumberModel</code>, and <code>SpinnerDateModel</code>.
 * <p>
 * A <code>JSpinner</code> has a single child component that's
 * responsible for displaying
 * and potentially changing the current element or <i>value</i> of
 * the model, which is called the <code>editor</code>.  The editor is created
 * by the <code>JSpinner</code>'s constructor and can be changed with the
 * <code>editor</code> property.  The <code>JSpinner</code>'s editor stays
 * in sync with the model by listening for <code>ChangeEvent</code>s. If the
 * user has changed the value displayed by the <code>editor</code> it is
 * possible for the <code>model</code>'s value to differ from that of
 * the <code>editor</code>. To make sure the <code>model</code> has the same
 * value as the editor use the <code>commitEdit</code> method, eg:
 * <pre>
 *   try {
 *       spinner.commitEdit();
 *   }
 *   catch (ParseException pe) {{
 *       // Edited value is invalid, spinner.getValue() will return
 *       // the last valid value, you could revert the spinner to show that:
 *       JComponent editor = spinner.getEditor()
 *       if (editor instanceof DefaultEditor) {
 *           ((DefaultEditor)editor).getTextField().setValue(spinner.getValue();
 *       }
 *       // reset the value to some known value:
 *       spinner.setValue(fallbackValue);
 *       // or treat the last valid value as the current, in which
 *       // case you don't need to do anything.
 *   }
 *   return spinner.getValue();
 * </pre>
 * <p>
 * For information and examples of using spinner see
 * <a href="https://docs.oracle.com/javase/tutorial/uiswing/components/spinner.html">How to Use Spinners</a>,
 * a section in <em>The Java Tutorial.</em>
 * <p>
 * <strong>Warning:</strong> Swing is not thread safe. For more
 * information see <a
 * href="package-summary.html#threading">Swing's Threading
 * Policy</a>.
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
 * @beaninfo
 *   attribute: isContainer false
 * description: A single line input field that lets the user select a
 *     number or an object value from an ordered set.
 *
 * <p>
 *  单行输入字段,允许用户从有序序列中选择数字或对象值。纺纱机通常提供一对微小的箭头按钮,用于穿过顺序的元素。键盘上/下箭头键也循环通过元素。还可以允许用户直接在微调器中键入(合法)值。
 * 尽管组合框提供类似的功能,但是旋转器有时是优选的,因为它们不需要可能模糊重要数据的下拉列表。
 * <p>
 *  <code> JSpinner </code>的序列值由其<code> SpinnerModel </code>定义。
 *  <code>模型</code>可以指定为构造函数参数,并使用<code> model </code>属性更改。
 *  <code> SpinnerModel </code>类提供了一些常见类型：<code> SpinnerListModel </code>,<code> SpinnerNumberModel </code>
 * 和<code> SpinnerDateModel </code>。
 *  <code>模型</code>可以指定为构造函数参数,并使用<code> model </code>属性更改。
 * <p>
 * <code> JSpinner </code>有一个子组件,负责显示和潜在更改模型的当前元素或<i>值</i>,这称为<code>编辑器</code>。
 * 编辑器由<code> JSpinner </code>的构造函数创建,可以使用<code> editor </code>属性更改。
 * 通过侦听<code> ChangeEvent </code>,<code> JSpinner </code>的编辑器与模型保持同步。
 * 如果用户更改了<code> editor </code>显示的值,则<code> model </code>的值可能与<code>编辑器</code>的值不同。
 * 要确保<code>模型</code>与编辑器使用<code> commitEdit </code>方法具有相同的值,例如：。
 * <pre>
 *  try {spinner.commitEdit();你可以还原微调器来显示：JComponent editor = spinner.getEditor()if(editor instanceof DefaultEditor){((DefaultEditor)editor).getTextField()。
 * setValue(spinner.getValue();} //将值重置为某个已知值：spinner.setValue(fallbackValue); //或将最后一个有效值作为current,其中//
 *  case你不需要做任何事情} return spinner.getValue();。
 * </pre>
 * <p>
 *  有关使用微调器的信息和示例,请参阅<a href="https://docs.oracle.com/javase/tutorial/uiswing/components/spinner.html">如
 * 何使用纺纱器</a>,<em > Java教程。
 * </em>。
 * <p>
 * <strong>警告：</strong> Swing不是线程安全的。有关详情,请参阅<a href="package-summary.html#threading"> Swing的线程策略</a>。
 * <p>
 *  <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
 *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
 * 
 *  @beaninfo属性：isContainer false description：一个单行输入字段,允许用户从有序集中选择数字或对象值。
 * 
 * 
 * @see SpinnerModel
 * @see AbstractSpinnerModel
 * @see SpinnerListModel
 * @see SpinnerNumberModel
 * @see SpinnerDateModel
 * @see JFormattedTextField
 *
 * @author Hans Muller
 * @author Lynn Monsanto (accessibility)
 * @since 1.4
 */
public class JSpinner extends JComponent implements Accessible
{
    /**
    /* <p>
    /* 
     * @see #getUIClassID
     * @see #readObject
     */
    private static final String uiClassID = "SpinnerUI";

    private static final Action DISABLED_ACTION = new DisabledAction();

    private SpinnerModel model;
    private JComponent editor;
    private ChangeListener modelListener;
    private transient ChangeEvent changeEvent;
    private boolean editorExplicitlySet = false;


    /**
     * Constructs a spinner for the given model. The spinner has
     * a set of previous/next buttons, and an editor appropriate
     * for the model.
     *
     * <p>
     *  构造给定模型的微调框。微调器具有一组上一个/下一个按钮,以及一个适用于模型的编辑器。
     * 
     * 
     * @throws NullPointerException if the model is {@code null}
     */
    public JSpinner(SpinnerModel model) {
        if (model == null) {
            throw new NullPointerException("model cannot be null");
        }
        this.model = model;
        this.editor = createEditor(model);
        setUIProperty("opaque",true);
        updateUI();
    }


    /**
     * Constructs a spinner with an <code>Integer SpinnerNumberModel</code>
     * with initial value 0 and no minimum or maximum limits.
     * <p>
     *  使用初始值为0且没有最小或最大限制的<code> Integer SpinnerNumberModel </code>构造微调器。
     * 
     */
    public JSpinner() {
        this(new SpinnerNumberModel());
    }


    /**
     * Returns the look and feel (L&amp;F) object that renders this component.
     *
     * <p>
     *  返回呈现此组件的外观和感觉(L&amp; F)对象。
     * 
     * 
     * @return the <code>SpinnerUI</code> object that renders this component
     */
    public SpinnerUI getUI() {
        return (SpinnerUI)ui;
    }


    /**
     * Sets the look and feel (L&amp;F) object that renders this component.
     *
     * <p>
     *  设置呈现此组件的外观和感觉(L&amp; F)对象。
     * 
     * 
     * @param ui  the <code>SpinnerUI</code> L&amp;F object
     * @see UIDefaults#getUI
     */
    public void setUI(SpinnerUI ui) {
        super.setUI(ui);
    }


    /**
     * Returns the suffix used to construct the name of the look and feel
     * (L&amp;F) class used to render this component.
     *
     * <p>
     *  返回用于构造用于渲染此组件的外观和感觉(L&amp; F)类的名称的后缀。
     * 
     * 
     * @return the string "SpinnerUI"
     * @see JComponent#getUIClassID
     * @see UIDefaults#getUI
     */
    public String getUIClassID() {
        return uiClassID;
    }



    /**
     * Resets the UI property with the value from the current look and feel.
     *
     * <p>
     *  使用当前外观的值重置UI属性。
     * 
     * 
     * @see UIManager#getUI
     */
    public void updateUI() {
        setUI((SpinnerUI)UIManager.getUI(this));
        invalidate();
    }


    /**
     * This method is called by the constructors to create the
     * <code>JComponent</code>
     * that displays the current value of the sequence.  The editor may
     * also allow the user to enter an element of the sequence directly.
     * An editor must listen for <code>ChangeEvents</code> on the
     * <code>model</code> and keep the value it displays
     * in sync with the value of the model.
     * <p>
     * Subclasses may override this method to add support for new
     * <code>SpinnerModel</code> classes.  Alternatively one can just
     * replace the editor created here with the <code>setEditor</code>
     * method.  The default mapping from model type to editor is:
     * <ul>
     * <li> <code>SpinnerNumberModel =&gt; JSpinner.NumberEditor</code>
     * <li> <code>SpinnerDateModel =&gt; JSpinner.DateEditor</code>
     * <li> <code>SpinnerListModel =&gt; JSpinner.ListEditor</code>
     * <li> <i>all others</i> =&gt; <code>JSpinner.DefaultEditor</code>
     * </ul>
     *
     * <p>
     * 此方法由构造函数调用以创建显示序列的当前值的<code> JComponent </code>。编辑器还可以允许用户直接输入序列的元素。
     * 编辑器必须监听<code> model </code>上的<code> ChangeEvents </code>,并保持其显示的值与模型的值同步。
     * <p>
     *  子类可以覆盖此方法以添加对新的<code> SpinnerModel </code>类的支持。或者,可以使用<code> setEditor </code>方法替换此处创建的编辑器。
     * 从模型类型到编辑器的默认映射是：。
     * <ul>
     *  <li> <code> SpinnerNumberModel =&gt; JSpinner.NumberEditor </code> <li> <code> SpinnerDateModel =&gt
     * ; JSpinner.DateEditor </code> <li> <code> SpinnerListModel =&gt; JSpinner.ListEditor </code> <li> <i>
     * 所有其他</i> =&gt; <code> JSpinner.DefaultEditor </code>。
     * </ul>
     * 
     * 
     * @return a component that displays the current value of the sequence
     * @param model the value of getModel
     * @see #getModel
     * @see #setEditor
     */
    protected JComponent createEditor(SpinnerModel model) {
        if (model instanceof SpinnerDateModel) {
            return new DateEditor(this);
        }
        else if (model instanceof SpinnerListModel) {
            return new ListEditor(this);
        }
        else if (model instanceof SpinnerNumberModel) {
            return new NumberEditor(this);
        }
        else {
            return new DefaultEditor(this);
        }
    }


    /**
     * Changes the model that represents the value of this spinner.
     * If the editor property has not been explicitly set,
     * the editor property is (implicitly) set after the <code>"model"</code>
     * <code>PropertyChangeEvent</code> has been fired.  The editor
     * property is set to the value returned by <code>createEditor</code>,
     * as in:
     * <pre>
     * setEditor(createEditor(model));
     * </pre>
     *
     * <p>
     *  更改表示此微调框值的模型。
     * 如果编辑器属性没有被显式设置,则编辑器属性在<code>"model"</code> <code> PropertyChangeEvent </code>被触发后(隐式地)设置。
     *  editor属性设置为<code> createEditor </code>返回的值,如：。
     * <pre>
     *  setEditor(createEditor(model));
     * </pre>
     * 
     * 
     * @param model the new <code>SpinnerModel</code>
     * @see #getModel
     * @see #getEditor
     * @see #setEditor
     * @throws IllegalArgumentException if model is <code>null</code>
     *
     * @beaninfo
     *        bound: true
     *    attribute: visualUpdate true
     *  description: Model that represents the value of this spinner.
     */
    public void setModel(SpinnerModel model) {
        if (model == null) {
            throw new IllegalArgumentException("null model");
        }
        if (!model.equals(this.model)) {
            SpinnerModel oldModel = this.model;
            this.model = model;
            if (modelListener != null) {
                oldModel.removeChangeListener(modelListener);
                this.model.addChangeListener(modelListener);
            }
            firePropertyChange("model", oldModel, model);
            if (!editorExplicitlySet) {
                setEditor(createEditor(model)); // sets editorExplicitlySet true
                editorExplicitlySet = false;
            }
            repaint();
            revalidate();
        }
    }


    /**
     * Returns the <code>SpinnerModel</code> that defines
     * this spinners sequence of values.
     *
     * <p>
     *  返回定义这个spinners序列值的<code> SpinnerModel </code>。
     * 
     * 
     * @return the value of the model property
     * @see #setModel
     */
    public SpinnerModel getModel() {
        return model;
    }


    /**
     * Returns the current value of the model, typically
     * this value is displayed by the <code>editor</code>. If the
     * user has changed the value displayed by the <code>editor</code> it is
     * possible for the <code>model</code>'s value to differ from that of
     * the <code>editor</code>, refer to the class level javadoc for examples
     * of how to deal with this.
     * <p>
     * This method simply delegates to the <code>model</code>.
     * It is equivalent to:
     * <pre>
     * getModel().getValue()
     * </pre>
     *
     * <p>
     * 返回模型的当前值,通常此值由<code> editor </code>显示。
     * 如果用户更改了<code> editor </code>显示的值,则<code> model </code>的值可能与<code>编辑器</code>的值不同,请参考到类级别javadoc的例子如何处理
     * 这个。
     * 返回模型的当前值,通常此值由<code> editor </code>显示。
     * <p>
     *  这个方法简单地委托给<code>模型</code>。它等效于：
     * <pre>
     *  getModel()。getValue()
     * </pre>
     * 
     * 
     * @see #setValue
     * @see SpinnerModel#getValue
     */
    public Object getValue() {
        return getModel().getValue();
    }


    /**
     * Changes current value of the model, typically
     * this value is displayed by the <code>editor</code>.
     * If the <code>SpinnerModel</code> implementation
     * doesn't support the specified value then an
     * <code>IllegalArgumentException</code> is thrown.
     * <p>
     * This method simply delegates to the <code>model</code>.
     * It is equivalent to:
     * <pre>
     * getModel().setValue(value)
     * </pre>
     *
     * <p>
     *  更改模型的当前值,通常此值由<code> editor </code>显示。
     * 如果<code> SpinnerModel </code>实现不支持指定的值,那么会抛出<code> IllegalArgumentException </code>。
     * <p>
     *  这个方法简单地委托给<code>模型</code>。它等效于：
     * <pre>
     *  getModel()。setValue(value)
     * </pre>
     * 
     * 
     * @throws IllegalArgumentException if <code>value</code> isn't allowed
     * @see #getValue
     * @see SpinnerModel#setValue
     */
    public void setValue(Object value) {
        getModel().setValue(value);
    }


    /**
     * Returns the object in the sequence that comes after the object returned
     * by <code>getValue()</code>. If the end of the sequence has been reached
     * then return <code>null</code>.
     * Calling this method does not effect <code>value</code>.
     * <p>
     * This method simply delegates to the <code>model</code>.
     * It is equivalent to:
     * <pre>
     * getModel().getNextValue()
     * </pre>
     *
     * <p>
     *  返回在<code> getValue()</code>返回的对象之后的序列中的对象。如果已经到达序列的结尾,则返回<code> null </code>。
     * 调用此方法不会影响<code> value </code>。
     * <p>
     *  这个方法简单地委托给<code>模型</code>。它等效于：
     * <pre>
     *  getModel()。getNextValue()
     * </pre>
     * 
     * 
     * @return the next legal value or <code>null</code> if one doesn't exist
     * @see #getValue
     * @see #getPreviousValue
     * @see SpinnerModel#getNextValue
     */
    public Object getNextValue() {
        return getModel().getNextValue();
    }


    /**
     * We pass <code>Change</code> events along to the listeners with the
     * the slider (instead of the model itself) as the event source.
     * <p>
     *  我们使用滑块(而不是模型本身)将<code>更改</code>事件传递给侦听器作为事件源。
     * 
     */
    private class ModelListener implements ChangeListener, Serializable {
        public void stateChanged(ChangeEvent e) {
            fireStateChanged();
        }
    }


    /**
     * Adds a listener to the list that is notified each time a change
     * to the model occurs.  The source of <code>ChangeEvents</code>
     * delivered to <code>ChangeListeners</code> will be this
     * <code>JSpinner</code>.  Note also that replacing the model
     * will not affect listeners added directly to JSpinner.
     * Applications can add listeners to  the model directly.  In that
     * case is that the source of the event would be the
     * <code>SpinnerModel</code>.
     *
     * <p>
     * 将监听器添加到每次发生模型更改时通知的列表中。
     * 传递到<code> ChangeListeners </code>的<code> ChangeEvents </code>的源将是这个<code> JSpinner </code>。
     * 还要注意,替换模型不会影响直接添加到JSpinner的侦听器。应用程序可以直接向模型添加侦听器。在这种情况下,事件的源是<code> SpinnerModel </code>。
     * 
     * 
     * @param listener the <code>ChangeListener</code> to add
     * @see #removeChangeListener
     * @see #getModel
     */
    public void addChangeListener(ChangeListener listener) {
        if (modelListener == null) {
            modelListener = new ModelListener();
            getModel().addChangeListener(modelListener);
        }
        listenerList.add(ChangeListener.class, listener);
    }



    /**
     * Removes a <code>ChangeListener</code> from this spinner.
     *
     * <p>
     *  从此微调器中删除<code> ChangeListener </code>。
     * 
     * 
     * @param listener the <code>ChangeListener</code> to remove
     * @see #fireStateChanged
     * @see #addChangeListener
     */
    public void removeChangeListener(ChangeListener listener) {
        listenerList.remove(ChangeListener.class, listener);
    }


    /**
     * Returns an array of all the <code>ChangeListener</code>s added
     * to this JSpinner with addChangeListener().
     *
     * <p>
     *  返回使用addChangeListener()添加到此JSpinner的所有<code> ChangeListener </code>数组。
     * 
     * 
     * @return all of the <code>ChangeListener</code>s added or an empty
     *         array if no listeners have been added
     * @since 1.4
     */
    public ChangeListener[] getChangeListeners() {
        return listenerList.getListeners(ChangeListener.class);
    }


    /**
     * Sends a <code>ChangeEvent</code>, whose source is this
     * <code>JSpinner</code>, to each <code>ChangeListener</code>.
     * When a <code>ChangeListener</code> has been added
     * to the spinner, this method method is called each time
     * a <code>ChangeEvent</code> is received from the model.
     *
     * <p>
     *  向每个<code> ChangeListener </code>发送一个<code> ChangeEvent </code>,其源代码是<code> JSpinner </code>。
     * 当将<code> ChangeListener </code>添加到微调器时,每当从模型接收到<code> ChangeEvent </code>时,将调用此方法方法。
     * 
     * 
     * @see #addChangeListener
     * @see #removeChangeListener
     * @see EventListenerList
     */
    protected void fireStateChanged() {
        Object[] listeners = listenerList.getListenerList();
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == ChangeListener.class) {
                if (changeEvent == null) {
                    changeEvent = new ChangeEvent(this);
                }
                ((ChangeListener)listeners[i+1]).stateChanged(changeEvent);
            }
        }
    }


    /**
     * Returns the object in the sequence that comes
     * before the object returned by <code>getValue()</code>.
     * If the end of the sequence has been reached then
     * return <code>null</code>. Calling this method does
     * not effect <code>value</code>.
     * <p>
     * This method simply delegates to the <code>model</code>.
     * It is equivalent to:
     * <pre>
     * getModel().getPreviousValue()
     * </pre>
     *
     * <p>
     *  返回在<code> getValue()</code>返回的对象之前的序列中的对象。如果已经到达序列的结尾,则返回<code> null </code>。
     * 调用此方法不会影响<code> value </code>。
     * <p>
     *  这个方法简单地委托给<code>模型</code>。它等效于：
     * <pre>
     *  getModel()。getPreviousValue()
     * </pre>
     * 
     * 
     * @return the previous legal value or <code>null</code>
     *   if one doesn't exist
     * @see #getValue
     * @see #getNextValue
     * @see SpinnerModel#getPreviousValue
     */
    public Object getPreviousValue() {
        return getModel().getPreviousValue();
    }


    /**
     * Changes the <code>JComponent</code> that displays the current value
     * of the <code>SpinnerModel</code>.  It is the responsibility of this
     * method to <i>disconnect</i> the old editor from the model and to
     * connect the new editor.  This may mean removing the
     * old editors <code>ChangeListener</code> from the model or the
     * spinner itself and adding one for the new editor.
     *
     * <p>
     * 更改显示<code> SpinnerModel </code>当前值的<code> JComponent </code>。
     * 这种方法的责任是<i> </i> </i>将旧编辑器与模型断开连接,并连接新编辑器。
     * 这可能意味着从模型或微调框本身删除旧的编辑器<code> ChangeListener </code>,并为新的编辑器添加一个。
     * 
     * 
     * @param editor the new editor
     * @see #getEditor
     * @see #createEditor
     * @see #getModel
     * @throws IllegalArgumentException if editor is <code>null</code>
     *
     * @beaninfo
     *        bound: true
     *    attribute: visualUpdate true
     *  description: JComponent that displays the current value of the model
     */
    public void setEditor(JComponent editor) {
        if (editor == null) {
            throw new IllegalArgumentException("null editor");
        }
        if (!editor.equals(this.editor)) {
            JComponent oldEditor = this.editor;
            this.editor = editor;
            if (oldEditor instanceof DefaultEditor) {
                ((DefaultEditor)oldEditor).dismiss(this);
            }
            editorExplicitlySet = true;
            firePropertyChange("editor", oldEditor, editor);
            revalidate();
            repaint();
        }
    }


    /**
     * Returns the component that displays and potentially
     * changes the model's value.
     *
     * <p>
     *  返回显示和潜在更改模型值的组件。
     * 
     * 
     * @return the component that displays and potentially
     *    changes the model's value
     * @see #setEditor
     * @see #createEditor
     */
    public JComponent getEditor() {
        return editor;
    }


    /**
     * Commits the currently edited value to the <code>SpinnerModel</code>.
     * <p>
     * If the editor is an instance of <code>DefaultEditor</code>, the
     * call if forwarded to the editor, otherwise this does nothing.
     *
     * <p>
     *  将当前编辑的值提交到<code> SpinnerModel </code>。
     * <p>
     *  如果编辑器是<code> DefaultEditor </code>的一个实例,则调用如果转发到编辑器,否则不做任何操作。
     * 
     * 
     * @throws ParseException if the currently edited value couldn't
     *         be committed.
     */
    public void commitEdit() throws ParseException {
        JComponent editor = getEditor();
        if (editor instanceof DefaultEditor) {
            ((DefaultEditor)editor).commitEdit();
        }
    }


    /*
     * See readObject and writeObject in JComponent for more
     * information about serialization in Swing.
     *
     * <p>
     *  有关Swing中序列化的更多信息,请参阅JComponent中的readObject和writeObject。
     * 
     * 
     * @param s Stream to write to
     */
    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        if (getUIClassID().equals(uiClassID)) {
            byte count = JComponent.getWriteObjCounter(this);
            JComponent.setWriteObjCounter(this, --count);
            if (count == 0 && ui != null) {
                ui.installUI(this);
            }
        }
    }


    /**
     * A simple base class for more specialized editors
     * that displays a read-only view of the model's current
     * value with a <code>JFormattedTextField</code>.  Subclasses
     * can configure the <code>JFormattedTextField</code> to create
     * an editor that's appropriate for the type of model they
     * support and they may want to override
     * the <code>stateChanged</code> and <code>propertyChanged</code>
     * methods, which keep the model and the text field in sync.
     * <p>
     * This class defines a <code>dismiss</code> method that removes the
     * editors <code>ChangeListener</code> from the <code>JSpinner</code>
     * that it's part of.   The <code>setEditor</code> method knows about
     * <code>DefaultEditor.dismiss</code>, so if the developer
     * replaces an editor that's derived from <code>JSpinner.DefaultEditor</code>
     * its <code>ChangeListener</code> connection back to the
     * <code>JSpinner</code> will be removed.  However after that,
     * it's up to the developer to manage their editor listeners.
     * Similarly, if a subclass overrides <code>createEditor</code>,
     * it's up to the subclasser to deal with their editor
     * subsequently being replaced (with <code>setEditor</code>).
     * We expect that in most cases, and in editor installed
     * with <code>setEditor</code> or created by a <code>createEditor</code>
     * override, will not be replaced anyway.
     * <p>
     * This class is the <code>LayoutManager</code> for it's single
     * <code>JFormattedTextField</code> child.   By default the
     * child is just centered with the parents insets.
     * <p>
     *  更专业的编辑器的简单基类,通过<code> JFormattedTextField </code>显示模型当前值的只读视图。
     * 子类可以配置<code> JFormattedTextField </code>来创建一个适合他们支持的模型类型的编辑器,并且他们可以覆盖<code> stateChanged </code>和<code>
     *  propertyChanged </code>方法,使模型和文本字段保持同步。
     *  更专业的编辑器的简单基类,通过<code> JFormattedTextField </code>显示模型当前值的只读视图。
     * <p>
     * 这个类定义了一个<code> dismiss </code>方法,它从它所属的<code> JSpinner </code>中移除编辑器<code> ChangeListener </code>。
     *  <code> setEditor </code>方法知道<code> DefaultEditor.dismiss </code>,所以如果开发人员替换从<code> JSpinner.DefaultE
     * ditor </code>派生的编辑器<code> ChangeListener <代码>连接回到<code> JSpinner </code>将被删除。
     * 这个类定义了一个<code> dismiss </code>方法,它从它所属的<code> JSpinner </code>中移除编辑器<code> ChangeListener </code>。
     * 然后,由开发人员管理他们的编辑器监听器。
     * 类似地,如果一个子类重写<code> createEditor </code>,它由子类处理它们的编辑器随后被替换(用<code> setEditor </code>)。
     * 我们期望在大多数情况下,在安装了<code> setEditor </code>或由<code> createEditor </code>覆盖创建的编辑器中,不会被替换。
     * <p>
     *  这个类是<code> LayoutManager </code>,它是单个<code> JFormattedTextField </code>子对象。默认情况下,孩子只是以父母插入为中心。
     * 
     * 
     * @since 1.4
     */
    public static class DefaultEditor extends JPanel
        implements ChangeListener, PropertyChangeListener, LayoutManager
    {
        /**
         * Constructs an editor component for the specified <code>JSpinner</code>.
         * This <code>DefaultEditor</code> is it's own layout manager and
         * it is added to the spinner's <code>ChangeListener</code> list.
         * The constructor creates a single <code>JFormattedTextField</code> child,
         * initializes it's value to be the spinner model's current value
         * and adds it to <code>this</code> <code>DefaultEditor</code>.
         *
         * <p>
         *  构造指定的<code> JSpinner </code>的编辑器组件。
         * 这个<code> DefaultEditor </code>是它自己的布局管理器,它被添加到微调框的<code> ChangeListener </code>列表中。
         * 构造函数创建单个<code> JFormattedTextField </code>子对象,将其值初始化为微调模型的当前值,并将其添加到<code> this </code> <code> Defaul
         * tEditor </code>。
         * 这个<code> DefaultEditor </code>是它自己的布局管理器,它被添加到微调框的<code> ChangeListener </code>列表中。
         * 
         * 
         * @param spinner the spinner whose model <code>this</code> editor will monitor
         * @see #getTextField
         * @see JSpinner#addChangeListener
         */
        public DefaultEditor(JSpinner spinner) {
            super(null);

            JFormattedTextField ftf = new JFormattedTextField();
            ftf.setName("Spinner.formattedTextField");
            ftf.setValue(spinner.getValue());
            ftf.addPropertyChangeListener(this);
            ftf.setEditable(false);
            ftf.setInheritsPopupMenu(true);

            String toolTipText = spinner.getToolTipText();
            if (toolTipText != null) {
                ftf.setToolTipText(toolTipText);
            }

            add(ftf);

            setLayout(this);
            spinner.addChangeListener(this);

            // We want the spinner's increment/decrement actions to be
            // active vs those of the JFormattedTextField. As such we
            // put disabled actions in the JFormattedTextField's actionmap.
            // A binding to a disabled action is treated as a nonexistant
            // binding.
            ActionMap ftfMap = ftf.getActionMap();

            if (ftfMap != null) {
                ftfMap.put("increment", DISABLED_ACTION);
                ftfMap.put("decrement", DISABLED_ACTION);
            }
        }


        /**
         * Disconnect <code>this</code> editor from the specified
         * <code>JSpinner</code>.  By default, this method removes
         * itself from the spinners <code>ChangeListener</code> list.
         *
         * <p>
         * 从指定的<code> JSpinner </code>断开<code>此</code>编辑器。默认情况下,此方法将自己从旋转器<code> ChangeListener </code>列表中删除。
         * 
         * 
         * @param spinner the <code>JSpinner</code> to disconnect this
         *    editor from; the same spinner as was passed to the constructor.
         */
        public void dismiss(JSpinner spinner) {
            spinner.removeChangeListener(this);
        }


        /**
         * Returns the <code>JSpinner</code> ancestor of this editor or
         * <code>null</code> if none of the ancestors are a
         * <code>JSpinner</code>.
         * Typically the editor's parent is a <code>JSpinner</code> however
         * subclasses of <code>JSpinner</code> may override the
         * the <code>createEditor</code> method and insert one or more containers
         * between the <code>JSpinner</code> and it's editor.
         *
         * <p>
         *  如果没有祖先是<code> JSpinner </code>,则返回此编辑器的<code> JSpinner </code>祖先或<code> null </code>。
         * 通常,编辑器的父类是<code> JSpinner </code>,但是<code> JSpinner </code>的子类可以覆盖<code> createEditor </code>方法,并在<code>
         *  JSpinner </code>和它的编辑器。
         *  如果没有祖先是<code> JSpinner </code>,则返回此编辑器的<code> JSpinner </code>祖先或<code> null </code>。
         * 
         * 
         * @return <code>JSpinner</code> ancestor; <code>null</code>
         *         if none of the ancestors are a <code>JSpinner</code>
         *
         * @see JSpinner#createEditor
         */
        public JSpinner getSpinner() {
            for (Component c = this; c != null; c = c.getParent()) {
                if (c instanceof JSpinner) {
                    return (JSpinner)c;
                }
            }
            return null;
        }


        /**
         * Returns the <code>JFormattedTextField</code> child of this
         * editor.  By default the text field is the first and only
         * child of editor.
         *
         * <p>
         *  返回此编辑器的<code> JFormattedTextField </code>子代。默认情况下,文本字段是编辑器的第一个也是唯一的子项。
         * 
         * 
         * @return the <code>JFormattedTextField</code> that gives the user
         *     access to the <code>SpinnerDateModel's</code> value.
         * @see #getSpinner
         * @see #getModel
         */
        public JFormattedTextField getTextField() {
            return (JFormattedTextField)getComponent(0);
        }


        /**
         * This method is called when the spinner's model's state changes.
         * It sets the <code>value</code> of the text field to the current
         * value of the spinners model.
         *
         * <p>
         *  当微调器的模型的状态改变时,调用此方法。它将文本字段的<code> value </code>设置为旋转模型的当前值。
         * 
         * 
         * @param e the <code>ChangeEvent</code> whose source is the
         * <code>JSpinner</code> whose model has changed.
         * @see #getTextField
         * @see JSpinner#getValue
         */
        public void stateChanged(ChangeEvent e) {
            JSpinner spinner = (JSpinner)(e.getSource());
            getTextField().setValue(spinner.getValue());
        }


        /**
         * Called by the <code>JFormattedTextField</code>
         * <code>PropertyChangeListener</code>.  When the <code>"value"</code>
         * property changes, which implies that the user has typed a new
         * number, we set the value of the spinners model.
         * <p>
         * This class ignores <code>PropertyChangeEvents</code> whose
         * source is not the <code>JFormattedTextField</code>, so subclasses
         * may safely make <code>this</code> <code>DefaultEditor</code> a
         * <code>PropertyChangeListener</code> on other objects.
         *
         * <p>
         *  由<code> JFormattedTextField </code> <code> PropertyChangeListener </code>调用。
         * 当<code>"value"</code>属性改变时,这意味着用户键入了一个新的数字,我们设置了旋转模型的值。
         * <p>
         *  这个类忽略了源代码不是<code> JFormattedTextField </code>的<code> PropertyChangeEvents </code>,所以子类可以安全地使<code> t
         * his </code> <code> DefaultEditor </code> PropertyChangeListener </code>。
         * 
         * 
         * @param e the <code>PropertyChangeEvent</code> whose source is
         *    the <code>JFormattedTextField</code> created by this class.
         * @see #getTextField
         */
        public void propertyChange(PropertyChangeEvent e)
        {
            JSpinner spinner = getSpinner();

            if (spinner == null) {
                // Indicates we aren't installed anywhere.
                return;
            }

            Object source = e.getSource();
            String name = e.getPropertyName();
            if ((source instanceof JFormattedTextField) && "value".equals(name)) {
                Object lastValue = spinner.getValue();

                // Try to set the new value
                try {
                    spinner.setValue(getTextField().getValue());
                } catch (IllegalArgumentException iae) {
                    // SpinnerModel didn't like new value, reset
                    try {
                        ((JFormattedTextField)source).setValue(lastValue);
                    } catch (IllegalArgumentException iae2) {
                        // Still bogus, nothing else we can do, the
                        // SpinnerModel and JFormattedTextField are now out
                        // of sync.
                    }
                }
            }
        }


        /**
         * This <code>LayoutManager</code> method does nothing.  We're
         * only managing a single child and there's no support
         * for layout constraints.
         *
         * <p>
         *  这个<code> LayoutManager </code>方法什么也不做。我们只管理一个孩子,并且不支持布局约束。
         * 
         * 
         * @param name ignored
         * @param child ignored
         */
        public void addLayoutComponent(String name, Component child) {
        }


        /**
         * This <code>LayoutManager</code> method does nothing.  There
         * isn't any per-child state.
         *
         * <p>
         * 这个<code> LayoutManager </code>方法什么也不做。没有任何每个孩子的状态。
         * 
         * 
         * @param child ignored
         */
        public void removeLayoutComponent(Component child) {
        }


        /**
         * Returns the size of the parents insets.
         * <p>
         *  返回父插入的大小。
         * 
         */
        private Dimension insetSize(Container parent) {
            Insets insets = parent.getInsets();
            int w = insets.left + insets.right;
            int h = insets.top + insets.bottom;
            return new Dimension(w, h);
        }


        /**
         * Returns the preferred size of first (and only) child plus the
         * size of the parents insets.
         *
         * <p>
         *  返回首(且仅)子项的首选大小加上父项插入的大小。
         * 
         * 
         * @param parent the Container that's managing the layout
         * @return the preferred dimensions to lay out the subcomponents
         *          of the specified container.
         */
        public Dimension preferredLayoutSize(Container parent) {
            Dimension preferredSize = insetSize(parent);
            if (parent.getComponentCount() > 0) {
                Dimension childSize = getComponent(0).getPreferredSize();
                preferredSize.width += childSize.width;
                preferredSize.height += childSize.height;
            }
            return preferredSize;
        }


        /**
         * Returns the minimum size of first (and only) child plus the
         * size of the parents insets.
         *
         * <p>
         *  返回第一个(且仅)子项的最小大小加上父项插入的大小。
         * 
         * 
         * @param parent the Container that's managing the layout
         * @return  the minimum dimensions needed to lay out the subcomponents
         *          of the specified container.
         */
        public Dimension minimumLayoutSize(Container parent) {
            Dimension minimumSize = insetSize(parent);
            if (parent.getComponentCount() > 0) {
                Dimension childSize = getComponent(0).getMinimumSize();
                minimumSize.width += childSize.width;
                minimumSize.height += childSize.height;
            }
            return minimumSize;
        }


        /**
         * Resize the one (and only) child to completely fill the area
         * within the parents insets.
         * <p>
         *  调整一个(和唯一)的孩子,以完全填充父母内插区域。
         * 
         */
        public void layoutContainer(Container parent) {
            if (parent.getComponentCount() > 0) {
                Insets insets = parent.getInsets();
                int w = parent.getWidth() - (insets.left + insets.right);
                int h = parent.getHeight() - (insets.top + insets.bottom);
                getComponent(0).setBounds(insets.left, insets.top, w, h);
            }
        }

        /**
         * Pushes the currently edited value to the <code>SpinnerModel</code>.
         * <p>
         * The default implementation invokes <code>commitEdit</code> on the
         * <code>JFormattedTextField</code>.
         *
         * <p>
         *  将当前编辑的值推送到<code> SpinnerModel </code>。
         * <p>
         *  默认实现调用<code> JFormattedTextField </code>上的<code> commitEdit </code>。
         * 
         * 
         * @throws ParseException if the edited value is not legal
         */
        public void commitEdit()  throws ParseException {
            // If the value in the JFormattedTextField is legal, this will have
            // the result of pushing the value to the SpinnerModel
            // by way of the <code>propertyChange</code> method.
            JFormattedTextField ftf = getTextField();

            ftf.commitEdit();
        }

        /**
         * Returns the baseline.
         *
         * <p>
         *  返回基线。
         * 
         * 
         * @throws IllegalArgumentException {@inheritDoc}
         * @see javax.swing.JComponent#getBaseline(int,int)
         * @see javax.swing.JComponent#getBaselineResizeBehavior()
         * @since 1.6
         */
        public int getBaseline(int width, int height) {
            // check size.
            super.getBaseline(width, height);
            Insets insets = getInsets();
            width = width - insets.left - insets.right;
            height = height - insets.top - insets.bottom;
            int baseline = getComponent(0).getBaseline(width, height);
            if (baseline >= 0) {
                return baseline + insets.top;
            }
            return -1;
        }

        /**
         * Returns an enum indicating how the baseline of the component
         * changes as the size changes.
         *
         * <p>
         *  返回枚举,指示组件的基准如何随着大小更改而更改。
         * 
         * 
         * @throws NullPointerException {@inheritDoc}
         * @see javax.swing.JComponent#getBaseline(int, int)
         * @since 1.6
         */
        public BaselineResizeBehavior getBaselineResizeBehavior() {
            return getComponent(0).getBaselineResizeBehavior();
        }
    }




    /**
     * This subclass of javax.swing.DateFormatter maps the minimum/maximum
     * properties to te start/end properties of a SpinnerDateModel.
     * <p>
     *  javax.swing.DateFormatter的此子类将最小/最大属性映射到SpinnerDateModel的te start / end属性。
     * 
     */
    private static class DateEditorFormatter extends DateFormatter {
        private final SpinnerDateModel model;

        DateEditorFormatter(SpinnerDateModel model, DateFormat format) {
            super(format);
            this.model = model;
        }

        public void setMinimum(Comparable min) {
            model.setStart(min);
        }

        public Comparable getMinimum() {
            return  model.getStart();
        }

        public void setMaximum(Comparable max) {
            model.setEnd(max);
        }

        public Comparable getMaximum() {
            return model.getEnd();
        }
    }


    /**
     * An editor for a <code>JSpinner</code> whose model is a
     * <code>SpinnerDateModel</code>.  The value of the editor is
     * displayed with a <code>JFormattedTextField</code> whose format
     * is defined by a <code>DateFormatter</code> instance whose
     * <code>minimum</code> and <code>maximum</code> properties
     * are mapped to the <code>SpinnerDateModel</code>.
     * <p>
     *  <code> JSpinner </code>的编辑器,其模型是<code> SpinnerDateModel </code>。
     * 编辑器的值用<code> JFormattedTextField </code>显示,其格式由<code> DateFormatter </code>实例定义,<code>最小</code>属性映射到<code>
     *  SpinnerDateModel </code>。
     *  <code> JSpinner </code>的编辑器,其模型是<code> SpinnerDateModel </code>。
     * 
     * 
     * @since 1.4
     */
    // PENDING(hmuller): more example javadoc
    public static class DateEditor extends DefaultEditor
    {
        // This is here until SimpleDateFormat gets a constructor that
        // takes a Locale: 4923525
        private static String getDefaultPattern(Locale loc) {
            LocaleProviderAdapter adapter = LocaleProviderAdapter.getAdapter(DateFormatProvider.class, loc);
            LocaleResources lr = adapter.getLocaleResources(loc);
            if (lr == null) {
                lr = LocaleProviderAdapter.forJRE().getLocaleResources(loc);
            }
            return lr.getDateTimePattern(DateFormat.SHORT, DateFormat.SHORT, null);
        }

        /**
         * Construct a <code>JSpinner</code> editor that supports displaying
         * and editing the value of a <code>SpinnerDateModel</code>
         * with a <code>JFormattedTextField</code>.  <code>This</code>
         * <code>DateEditor</code> becomes both a <code>ChangeListener</code>
         * on the spinners model and a <code>PropertyChangeListener</code>
         * on the new <code>JFormattedTextField</code>.
         *
         * <p>
         * 构造一个<code> JSpinner </code>编辑器,支持使用<code> JFormattedTextField </code>显示和编辑<code> SpinnerDateModel </code>
         * 的值。
         *  <code>这个</code> <code> DateEditor </code>在新的<code> JFormattedTextField </code>上成为spinners模型上的<code> 
         * ChangeListener </code>和<code> PropertyChangeListener </code>代码>。
         * 
         * 
         * @param spinner the spinner whose model <code>this</code> editor will monitor
         * @exception IllegalArgumentException if the spinners model is not
         *     an instance of <code>SpinnerDateModel</code>
         *
         * @see #getModel
         * @see #getFormat
         * @see SpinnerDateModel
         */
        public DateEditor(JSpinner spinner) {
            this(spinner, getDefaultPattern(spinner.getLocale()));
        }


        /**
         * Construct a <code>JSpinner</code> editor that supports displaying
         * and editing the value of a <code>SpinnerDateModel</code>
         * with a <code>JFormattedTextField</code>.  <code>This</code>
         * <code>DateEditor</code> becomes both a <code>ChangeListener</code>
         * on the spinner and a <code>PropertyChangeListener</code>
         * on the new <code>JFormattedTextField</code>.
         *
         * <p>
         *  构造一个<code> JSpinner </code>编辑器,支持使用<code> JFormattedTextField </code>显示和编辑<code> SpinnerDateModel </code>
         * 的值。
         *  <code>此</code> <code> DateEditor </code>成为微调器上的<code> ChangeListener </code>和新的<code> JFormattedText
         * Field </code上的<code> PropertyChangeListener </code> >。
         * 
         * 
         * @param spinner the spinner whose model <code>this</code> editor will monitor
         * @param dateFormatPattern the initial pattern for the
         *     <code>SimpleDateFormat</code> object that's used to display
         *     and parse the value of the text field.
         * @exception IllegalArgumentException if the spinners model is not
         *     an instance of <code>SpinnerDateModel</code>
         *
         * @see #getModel
         * @see #getFormat
         * @see SpinnerDateModel
         * @see java.text.SimpleDateFormat
         */
        public DateEditor(JSpinner spinner, String dateFormatPattern) {
            this(spinner, new SimpleDateFormat(dateFormatPattern,
                                               spinner.getLocale()));
        }

        /**
         * Construct a <code>JSpinner</code> editor that supports displaying
         * and editing the value of a <code>SpinnerDateModel</code>
         * with a <code>JFormattedTextField</code>.  <code>This</code>
         * <code>DateEditor</code> becomes both a <code>ChangeListener</code>
         * on the spinner and a <code>PropertyChangeListener</code>
         * on the new <code>JFormattedTextField</code>.
         *
         * <p>
         *  构造一个<code> JSpinner </code>编辑器,支持使用<code> JFormattedTextField </code>显示和编辑<code> SpinnerDateModel </code>
         * 的值。
         *  <code>此</code> <code> DateEditor </code>成为微调器上的<code> ChangeListener </code>和新的<code> JFormattedText
         * Field </code上的<code> PropertyChangeListener </code> >。
         * 
         * 
         * @param spinner the spinner whose model <code>this</code> editor
         *        will monitor
         * @param format <code>DateFormat</code> object that's used to display
         *     and parse the value of the text field.
         * @exception IllegalArgumentException if the spinners model is not
         *     an instance of <code>SpinnerDateModel</code>
         *
         * @see #getModel
         * @see #getFormat
         * @see SpinnerDateModel
         * @see java.text.SimpleDateFormat
         */
        private DateEditor(JSpinner spinner, DateFormat format) {
            super(spinner);
            if (!(spinner.getModel() instanceof SpinnerDateModel)) {
                throw new IllegalArgumentException(
                                 "model not a SpinnerDateModel");
            }

            SpinnerDateModel model = (SpinnerDateModel)spinner.getModel();
            DateFormatter formatter = new DateEditorFormatter(model, format);
            DefaultFormatterFactory factory = new DefaultFormatterFactory(
                                                  formatter);
            JFormattedTextField ftf = getTextField();
            ftf.setEditable(true);
            ftf.setFormatterFactory(factory);

            /* TBD - initializing the column width of the text field
             * is imprecise and doing it here is tricky because
             * the developer may configure the formatter later.
             * <p>
             *  是不精确的,这里做的是棘手的,因为开发人员可能以后配置格式化。
             * 
             */
            try {
                String maxString = formatter.valueToString(model.getStart());
                String minString = formatter.valueToString(model.getEnd());
                ftf.setColumns(Math.max(maxString.length(),
                                        minString.length()));
            }
            catch (ParseException e) {
                // PENDING: hmuller
            }
        }

        /**
         * Returns the <code>java.text.SimpleDateFormat</code> object the
         * <code>JFormattedTextField</code> uses to parse and format
         * numbers.
         *
         * <p>
         *  返回<code> java.text.SimpleDateFormat </code>对象<code> JFormattedTextField </code>用于解析和格式化数字。
         * 
         * 
         * @return the value of <code>getTextField().getFormatter().getFormat()</code>.
         * @see #getTextField
         * @see java.text.SimpleDateFormat
         */
        public SimpleDateFormat getFormat() {
            return (SimpleDateFormat)((DateFormatter)(getTextField().getFormatter())).getFormat();
        }


        /**
         * Return our spinner ancestor's <code>SpinnerDateModel</code>.
         *
         * <p>
         *  返回我们的微调器祖先的<code> SpinnerDateModel </code>。
         * 
         * 
         * @return <code>getSpinner().getModel()</code>
         * @see #getSpinner
         * @see #getTextField
         */
        public SpinnerDateModel getModel() {
            return (SpinnerDateModel)(getSpinner().getModel());
        }
    }


    /**
     * This subclass of javax.swing.NumberFormatter maps the minimum/maximum
     * properties to a SpinnerNumberModel and initializes the valueClass
     * of the NumberFormatter to match the type of the initial models value.
     * <p>
     * javax.swing.NumberFormatter的此子类将最小/最大属性映射到SpinnerNumberModel,并初始化NumberFormatter的valueClass以匹配初始模型值的类
     * 型。
     * 
     */
    private static class NumberEditorFormatter extends NumberFormatter {
        private final SpinnerNumberModel model;

        NumberEditorFormatter(SpinnerNumberModel model, NumberFormat format) {
            super(format);
            this.model = model;
            setValueClass(model.getValue().getClass());
        }

        public void setMinimum(Comparable min) {
            model.setMinimum(min);
        }

        public Comparable getMinimum() {
            return  model.getMinimum();
        }

        public void setMaximum(Comparable max) {
            model.setMaximum(max);
        }

        public Comparable getMaximum() {
            return model.getMaximum();
        }
    }



    /**
     * An editor for a <code>JSpinner</code> whose model is a
     * <code>SpinnerNumberModel</code>.  The value of the editor is
     * displayed with a <code>JFormattedTextField</code> whose format
     * is defined by a <code>NumberFormatter</code> instance whose
     * <code>minimum</code> and <code>maximum</code> properties
     * are mapped to the <code>SpinnerNumberModel</code>.
     * <p>
     *  <code> JSpinner </code>的编辑器,其模型是<code> SpinnerNumberModel </code>。
     * 编辑器的值用<code> JFormattedTextField </code>显示,其格式由<code> NumberFormatter </code>实例定义,其<code> minimum </code>
     * 和<code> maximum </code>属性映射到<code> SpinnerNumberModel </code>。
     *  <code> JSpinner </code>的编辑器,其模型是<code> SpinnerNumberModel </code>。
     * 
     * 
     * @since 1.4
     */
    // PENDING(hmuller): more example javadoc
    public static class NumberEditor extends DefaultEditor
    {
        // This is here until DecimalFormat gets a constructor that
        // takes a Locale: 4923525
        private static String getDefaultPattern(Locale locale) {
            // Get the pattern for the default locale.
            LocaleProviderAdapter adapter;
            adapter = LocaleProviderAdapter.getAdapter(NumberFormatProvider.class,
                                                       locale);
            LocaleResources lr = adapter.getLocaleResources(locale);
            if (lr == null) {
                lr = LocaleProviderAdapter.forJRE().getLocaleResources(locale);
            }
            String[] all = lr.getNumberPatterns();
            return all[0];
        }

        /**
         * Construct a <code>JSpinner</code> editor that supports displaying
         * and editing the value of a <code>SpinnerNumberModel</code>
         * with a <code>JFormattedTextField</code>.  <code>This</code>
         * <code>NumberEditor</code> becomes both a <code>ChangeListener</code>
         * on the spinner and a <code>PropertyChangeListener</code>
         * on the new <code>JFormattedTextField</code>.
         *
         * <p>
         *  构造一个<code> JSpinner </code>编辑器,支持使用<code> JFormattedTextField </code>显示和编辑<code> SpinnerNumberModel 
         * </code>的值。
         *  <code>此</code> <code> NumberEditor </code>成为微调器上的<code> ChangeListener </code>和新的<code> JFormattedTe
         * xtField </code上的<code> PropertyChangeListener </code> >。
         * 
         * 
         * @param spinner the spinner whose model <code>this</code> editor will monitor
         * @exception IllegalArgumentException if the spinners model is not
         *     an instance of <code>SpinnerNumberModel</code>
         *
         * @see #getModel
         * @see #getFormat
         * @see SpinnerNumberModel
         */
        public NumberEditor(JSpinner spinner) {
            this(spinner, getDefaultPattern(spinner.getLocale()));
        }

        /**
         * Construct a <code>JSpinner</code> editor that supports displaying
         * and editing the value of a <code>SpinnerNumberModel</code>
         * with a <code>JFormattedTextField</code>.  <code>This</code>
         * <code>NumberEditor</code> becomes both a <code>ChangeListener</code>
         * on the spinner and a <code>PropertyChangeListener</code>
         * on the new <code>JFormattedTextField</code>.
         *
         * <p>
         *  构造一个<code> JSpinner </code>编辑器,支持使用<code> JFormattedTextField </code>显示和编辑<code> SpinnerNumberModel 
         * </code>的值。
         *  <code>此</code> <code> NumberEditor </code>成为微调器上的<code> ChangeListener </code>和新的<code> JFormattedTe
         * xtField </code上的<code> PropertyChangeListener </code> >。
         * 
         * 
         * @param spinner the spinner whose model <code>this</code> editor will monitor
         * @param decimalFormatPattern the initial pattern for the
         *     <code>DecimalFormat</code> object that's used to display
         *     and parse the value of the text field.
         * @exception IllegalArgumentException if the spinners model is not
         *     an instance of <code>SpinnerNumberModel</code> or if
         *     <code>decimalFormatPattern</code> is not a legal
         *     argument to <code>DecimalFormat</code>
         *
         * @see #getTextField
         * @see SpinnerNumberModel
         * @see java.text.DecimalFormat
         */
        public NumberEditor(JSpinner spinner, String decimalFormatPattern) {
            this(spinner, new DecimalFormat(decimalFormatPattern));
        }


        /**
         * Construct a <code>JSpinner</code> editor that supports displaying
         * and editing the value of a <code>SpinnerNumberModel</code>
         * with a <code>JFormattedTextField</code>.  <code>This</code>
         * <code>NumberEditor</code> becomes both a <code>ChangeListener</code>
         * on the spinner and a <code>PropertyChangeListener</code>
         * on the new <code>JFormattedTextField</code>.
         *
         * <p>
         * 构造一个<code> JSpinner </code>编辑器,支持使用<code> JFormattedTextField </code>显示和编辑<code> SpinnerNumberModel </code>
         * 的值。
         *  <code>此</code> <code> NumberEditor </code>成为微调器上的<code> ChangeListener </code>和新的<code> JFormattedTe
         * xtField </code上的<code> PropertyChangeListener </code> >。
         * 
         * 
         * @param spinner the spinner whose model <code>this</code> editor will monitor
         * @param decimalFormatPattern the initial pattern for the
         *     <code>DecimalFormat</code> object that's used to display
         *     and parse the value of the text field.
         * @exception IllegalArgumentException if the spinners model is not
         *     an instance of <code>SpinnerNumberModel</code>
         *
         * @see #getTextField
         * @see SpinnerNumberModel
         * @see java.text.DecimalFormat
         */
        private NumberEditor(JSpinner spinner, DecimalFormat format) {
            super(spinner);
            if (!(spinner.getModel() instanceof SpinnerNumberModel)) {
                throw new IllegalArgumentException(
                          "model not a SpinnerNumberModel");
            }

            SpinnerNumberModel model = (SpinnerNumberModel)spinner.getModel();
            NumberFormatter formatter = new NumberEditorFormatter(model,
                                                                  format);
            DefaultFormatterFactory factory = new DefaultFormatterFactory(
                                                  formatter);
            JFormattedTextField ftf = getTextField();
            ftf.setEditable(true);
            ftf.setFormatterFactory(factory);
            ftf.setHorizontalAlignment(JTextField.RIGHT);

            /* TBD - initializing the column width of the text field
             * is imprecise and doing it here is tricky because
             * the developer may configure the formatter later.
             * <p>
             *  是不精确的,这里做的是棘手的,因为开发人员可能以后配置格式化。
             * 
             */
            try {
                String maxString = formatter.valueToString(model.getMinimum());
                String minString = formatter.valueToString(model.getMaximum());
                ftf.setColumns(Math.max(maxString.length(),
                                        minString.length()));
            }
            catch (ParseException e) {
                // TBD should throw a chained error here
            }

        }


        /**
         * Returns the <code>java.text.DecimalFormat</code> object the
         * <code>JFormattedTextField</code> uses to parse and format
         * numbers.
         *
         * <p>
         *  返回<code> java.text.DecimalFormat </code>对象<code> JFormattedTextField </code>用于解析和格式化数字。
         * 
         * 
         * @return the value of <code>getTextField().getFormatter().getFormat()</code>.
         * @see #getTextField
         * @see java.text.DecimalFormat
         */
        public DecimalFormat getFormat() {
            return (DecimalFormat)((NumberFormatter)(getTextField().getFormatter())).getFormat();
        }


        /**
         * Return our spinner ancestor's <code>SpinnerNumberModel</code>.
         *
         * <p>
         *  返回我们的微调器祖先的<code> SpinnerNumberModel </code>。
         * 
         * 
         * @return <code>getSpinner().getModel()</code>
         * @see #getSpinner
         * @see #getTextField
         */
        public SpinnerNumberModel getModel() {
            return (SpinnerNumberModel)(getSpinner().getModel());
        }
    }


    /**
     * An editor for a <code>JSpinner</code> whose model is a
     * <code>SpinnerListModel</code>.
     * <p>
     *  <code> JSpinner </code>的编辑器,其模型是<code> SpinnerListModel </code>。
     * 
     * 
     * @since 1.4
     */
    public static class ListEditor extends DefaultEditor
    {
        /**
         * Construct a <code>JSpinner</code> editor that supports displaying
         * and editing the value of a <code>SpinnerListModel</code>
         * with a <code>JFormattedTextField</code>.  <code>This</code>
         * <code>ListEditor</code> becomes both a <code>ChangeListener</code>
         * on the spinner and a <code>PropertyChangeListener</code>
         * on the new <code>JFormattedTextField</code>.
         *
         * <p>
         *  构造一个<code> JSpinner </code>编辑器,支持使用<code> JFormattedTextField </code>显示和编辑<code> SpinnerListModel </code>
         * 的值。
         *  <code>此</code> <code> ListEditor </code>成为微调器上的<code> ChangeListener </code>和新的<code> JFormattedText
         * Field </code上的<code> PropertyChangeListener </code> >。
         * 
         * 
         * @param spinner the spinner whose model <code>this</code> editor will monitor
         * @exception IllegalArgumentException if the spinners model is not
         *     an instance of <code>SpinnerListModel</code>
         *
         * @see #getModel
         * @see SpinnerListModel
         */
        public ListEditor(JSpinner spinner) {
            super(spinner);
            if (!(spinner.getModel() instanceof SpinnerListModel)) {
                throw new IllegalArgumentException("model not a SpinnerListModel");
            }
            getTextField().setEditable(true);
            getTextField().setFormatterFactory(new
                              DefaultFormatterFactory(new ListFormatter()));
        }

        /**
         * Return our spinner ancestor's <code>SpinnerNumberModel</code>.
         *
         * <p>
         *  返回我们的微调器祖先的<code> SpinnerNumberModel </code>。
         * 
         * 
         * @return <code>getSpinner().getModel()</code>
         * @see #getSpinner
         * @see #getTextField
         */
        public SpinnerListModel getModel() {
            return (SpinnerListModel)(getSpinner().getModel());
        }


        /**
         * ListFormatter provides completion while text is being input
         * into the JFormattedTextField. Completion is only done if the
         * user is inserting text at the end of the document. Completion
         * is done by way of the SpinnerListModel method findNextMatch.
         * <p>
         *  ListFormatter在文本输入到JFormattedTextField时提供完成。仅在用户在文档末尾插入文本时才完成。
         * 完成是通过SpinnerListModel方法findNextMatch完成的。
         * 
         */
        private class ListFormatter extends
                          JFormattedTextField.AbstractFormatter {
            private DocumentFilter filter;

            public String valueToString(Object value) throws ParseException {
                if (value == null) {
                    return "";
                }
                return value.toString();
            }

            public Object stringToValue(String string) throws ParseException {
                return string;
            }

            protected DocumentFilter getDocumentFilter() {
                if (filter == null) {
                    filter = new Filter();
                }
                return filter;
            }


            private class Filter extends DocumentFilter {
                public void replace(FilterBypass fb, int offset, int length,
                                    String string, AttributeSet attrs) throws
                                           BadLocationException {
                    if (string != null && (offset + length) ==
                                          fb.getDocument().getLength()) {
                        Object next = getModel().findNextMatch(
                                         fb.getDocument().getText(0, offset) +
                                         string);
                        String value = (next != null) ? next.toString() : null;

                        if (value != null) {
                            fb.remove(0, offset + length);
                            fb.insertString(0, value, null);
                            getFormattedTextField().select(offset +
                                                           string.length(),
                                                           value.length());
                            return;
                        }
                    }
                    super.replace(fb, offset, length, string, attrs);
                }

                public void insertString(FilterBypass fb, int offset,
                                     String string, AttributeSet attr)
                       throws BadLocationException {
                    replace(fb, offset, 0, string, attr);
                }
            }
        }
    }


    /**
     * An Action implementation that is always disabled.
     * <p>
     *  始终禁用的Action实现。
     * 
     */
    private static class DisabledAction implements Action {
        public Object getValue(String key) {
            return null;
        }
        public void putValue(String key, Object value) {
        }
        public void setEnabled(boolean b) {
        }
        public boolean isEnabled() {
            return false;
        }
        public void addPropertyChangeListener(PropertyChangeListener l) {
        }
        public void removePropertyChangeListener(PropertyChangeListener l) {
        }
        public void actionPerformed(ActionEvent ae) {
        }
    }

    /////////////////
    // Accessibility support
    ////////////////

    /**
     * Gets the <code>AccessibleContext</code> for the <code>JSpinner</code>
     *
     * <p>
     * 获取<code> JSpinner </code>的<code> AccessibleContext </code>
     * 
     * 
     * @return the <code>AccessibleContext</code> for the <code>JSpinner</code>
     * @since 1.5
     */
    public AccessibleContext getAccessibleContext() {
        if (accessibleContext == null) {
            accessibleContext = new AccessibleJSpinner();
        }
        return accessibleContext;
    }

    /**
     * <code>AccessibleJSpinner</code> implements accessibility
     * support for the <code>JSpinner</code> class.
     * <p>
     *  <code> AccessibleJSpinner </code>实现<code> JSpinner </code>类的辅助功能支持。
     * 
     * 
     * @since 1.5
     */
    protected class AccessibleJSpinner extends AccessibleJComponent
        implements AccessibleValue, AccessibleAction, AccessibleText,
                   AccessibleEditableText, ChangeListener {

        private Object oldModelValue = null;

        /**
         * AccessibleJSpinner constructor
         * <p>
         *  AccessibleJSpinner构造函数
         * 
         */
        protected AccessibleJSpinner() {
            // model is guaranteed to be non-null
            oldModelValue = model.getValue();
            JSpinner.this.addChangeListener(this);
        }

        /**
         * Invoked when the target of the listener has changed its state.
         *
         * <p>
         *  当侦听器的目标已更改其状态时调用。
         * 
         * 
         * @param e  a <code>ChangeEvent</code> object. Must not be null.
         * @throws NullPointerException if the parameter is null.
         */
        public void stateChanged(ChangeEvent e) {
            if (e == null) {
                throw new NullPointerException();
            }
            Object newModelValue = model.getValue();
            firePropertyChange(ACCESSIBLE_VALUE_PROPERTY,
                               oldModelValue,
                               newModelValue);
            firePropertyChange(ACCESSIBLE_TEXT_PROPERTY,
                               null,
                               0); // entire text may have changed

            oldModelValue = newModelValue;
        }

        /* ===== Begin AccessibleContext methods ===== */

        /**
         * Gets the role of this object.  The role of the object is the generic
         * purpose or use of the class of this object.  For example, the role
         * of a push button is AccessibleRole.PUSH_BUTTON.  The roles in
         * AccessibleRole are provided so component developers can pick from
         * a set of predefined roles.  This enables assistive technologies to
         * provide a consistent interface to various tweaked subclasses of
         * components (e.g., use AccessibleRole.PUSH_BUTTON for all components
         * that act like a push button) as well as distinguish between subclasses
         * that behave differently (e.g., AccessibleRole.CHECK_BOX for check boxes
         * and AccessibleRole.RADIO_BUTTON for radio buttons).
         * <p>Note that the AccessibleRole class is also extensible, so
         * custom component developers can define their own AccessibleRole's
         * if the set of predefined roles is inadequate.
         *
         * <p>
         *  获取此对象的作用。对象的作用是该对象的类的通用目的或使用。例如,按钮的角色是AccessibleRole.PUSH_BUTTON。
         * 提供了AccessibleRole中的角色,因此组件开发人员可以从一组预定义角色中进行选择。
         * 这使得辅助技术能够为组件的各种调整子类提供一致的接口(例如,对于像按钮一样操作的所有组件使用AccessibleRole.PUSH_BUTTON),以及区分行为不同的子类(例如,用于复选框的Access
         * ibleRole.CHECK_BOX和AccessibleRole.RADIO_BUTTON的单选按钮)。
         * 提供了AccessibleRole中的角色,因此组件开发人员可以从一组预定义角色中进行选择。
         *  <p>请注意,AccessibleRole类也是可扩展的,因此如果预定义角色集合不足,自定义组件开发人员可以定义自己的AccessibleRole。
         * 
         * 
         * @return an instance of AccessibleRole describing the role of the object
         * @see AccessibleRole
         */
        public AccessibleRole getAccessibleRole() {
            return AccessibleRole.SPIN_BOX;
        }

        /**
         * Returns the number of accessible children of the object.
         *
         * <p>
         *  返回对象的可访问子项数。
         * 
         * 
         * @return the number of accessible children of the object.
         */
        public int getAccessibleChildrenCount() {
            // the JSpinner has one child, the editor
            if (editor.getAccessibleContext() != null) {
                return 1;
            }
            return 0;
        }

        /**
         * Returns the specified Accessible child of the object.  The Accessible
         * children of an Accessible object are zero-based, so the first child
         * of an Accessible child is at index 0, the second child is at index 1,
         * and so on.
         *
         * <p>
         *  返回对象的指定Accessible子项。可访问对象的可访问子对象是基于零的,因此可访问子对象的第一个子对象位于索引0,第二个子对象位于索引1,依此类推。
         * 
         * 
         * @param i zero-based index of child
         * @return the Accessible child of the object
         * @see #getAccessibleChildrenCount
         */
        public Accessible getAccessibleChild(int i) {
            // the JSpinner has one child, the editor
            if (i != 0) {
                return null;
            }
            if (editor.getAccessibleContext() != null) {
                return (Accessible)editor;
            }
            return null;
        }

        /* ===== End AccessibleContext methods ===== */

        /**
         * Gets the AccessibleAction associated with this object that supports
         * one or more actions.
         *
         * <p>
         *  获取与支持一个或多个操作的此对象关联的AccessibleAction。
         * 
         * 
         * @return AccessibleAction if supported by object; else return null
         * @see AccessibleAction
         */
        public AccessibleAction getAccessibleAction() {
            return this;
        }

        /**
         * Gets the AccessibleText associated with this object presenting
         * text on the display.
         *
         * <p>
         * 获取与此对象相关联的AccessibleText在显示器上显示文本。
         * 
         * 
         * @return AccessibleText if supported by object; else return null
         * @see AccessibleText
         */
        public AccessibleText getAccessibleText() {
            return this;
        }

        /*
         * Returns the AccessibleContext for the JSpinner editor
         * <p>
         *  返回JSpinner编辑器的AccessibleContext
         * 
         */
        private AccessibleContext getEditorAccessibleContext() {
            if (editor instanceof DefaultEditor) {
                JTextField textField = ((DefaultEditor)editor).getTextField();
                if (textField != null) {
                    return textField.getAccessibleContext();
                }
            } else if (editor instanceof Accessible) {
                return editor.getAccessibleContext();
            }
            return null;
        }

        /*
         * Returns the AccessibleText for the JSpinner editor
         * <p>
         *  返回JSpinner编辑器的AccessibleText
         * 
         */
        private AccessibleText getEditorAccessibleText() {
            AccessibleContext ac = getEditorAccessibleContext();
            if (ac != null) {
                return ac.getAccessibleText();
            }
            return null;
        }

        /*
         * Returns the AccessibleEditableText for the JSpinner editor
         * <p>
         *  返回JSpinner编辑器的AccessibleEditableText
         * 
         */
        private AccessibleEditableText getEditorAccessibleEditableText() {
            AccessibleText at = getEditorAccessibleText();
            if (at instanceof AccessibleEditableText) {
                return (AccessibleEditableText)at;
            }
            return null;
        }

        /**
         * Gets the AccessibleValue associated with this object.
         *
         * <p>
         *  获取与此对象关联的AccessibleValue。
         * 
         * 
         * @return AccessibleValue if supported by object; else return null
         * @see AccessibleValue
         *
         */
        public AccessibleValue getAccessibleValue() {
            return this;
        }

        /* ===== Begin AccessibleValue impl ===== */

        /**
         * Get the value of this object as a Number.  If the value has not been
         * set, the return value will be null.
         *
         * <p>
         *  获取此对象的值作为数字。如果未设置该值,则返回值将为null。
         * 
         * 
         * @return value of the object
         * @see #setCurrentAccessibleValue
         */
        public Number getCurrentAccessibleValue() {
            Object o = model.getValue();
            if (o instanceof Number) {
                return (Number)o;
            }
            return null;
        }

        /**
         * Set the value of this object as a Number.
         *
         * <p>
         *  将此对象的值设置为Number。
         * 
         * 
         * @param n the value to set for this object
         * @return true if the value was set; else False
         * @see #getCurrentAccessibleValue
         */
        public boolean setCurrentAccessibleValue(Number n) {
            // try to set the new value
            try {
                model.setValue(n);
                return true;
            } catch (IllegalArgumentException iae) {
                // SpinnerModel didn't like new value
            }
            return false;
        }

        /**
         * Get the minimum value of this object as a Number.
         *
         * <p>
         *  获取此对象的最小值作为数字。
         * 
         * 
         * @return Minimum value of the object; null if this object does not
         * have a minimum value
         * @see #getMaximumAccessibleValue
         */
        public Number getMinimumAccessibleValue() {
            if (model instanceof SpinnerNumberModel) {
                SpinnerNumberModel numberModel = (SpinnerNumberModel)model;
                Object o = numberModel.getMinimum();
                if (o instanceof Number) {
                    return (Number)o;
                }
            }
            return null;
        }

        /**
         * Get the maximum value of this object as a Number.
         *
         * <p>
         *  获取此对象的最大值作为数字。
         * 
         * 
         * @return Maximum value of the object; null if this object does not
         * have a maximum value
         * @see #getMinimumAccessibleValue
         */
        public Number getMaximumAccessibleValue() {
            if (model instanceof SpinnerNumberModel) {
                SpinnerNumberModel numberModel = (SpinnerNumberModel)model;
                Object o = numberModel.getMaximum();
                if (o instanceof Number) {
                    return (Number)o;
                }
            }
            return null;
        }

        /* ===== End AccessibleValue impl ===== */

        /* ===== Begin AccessibleAction impl ===== */

        /**
         * Returns the number of accessible actions available in this object
         * If there are more than one, the first one is considered the "default"
         * action of the object.
         *
         * Two actions are supported: AccessibleAction.INCREMENT which
         * increments the spinner value and AccessibleAction.DECREMENT
         * which decrements the spinner value
         *
         * <p>
         *  返回此对象中可用的可用操作数如果有多个,则第一个被视为对象的"默认"操作。
         * 
         *  支持两个操作：AccessibleAction.INCREMENT增加微调框值,AccessibleAction.DECREMENT减少微调框值
         * 
         * 
         * @return the zero-based number of Actions in this object
         */
        public int getAccessibleActionCount() {
            return 2;
        }

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
        public String getAccessibleActionDescription(int i) {
            if (i == 0) {
                return AccessibleAction.INCREMENT;
            } else if (i == 1) {
                return AccessibleAction.DECREMENT;
            }
            return null;
        }

        /**
         * Performs the specified Action on the object
         *
         * <p>
         *  对对象执行指定的Action
         * 
         * 
         * @param i zero-based index of actions. The first action
         * (index 0) is AccessibleAction.INCREMENT and the second
         * action (index 1) is AccessibleAction.DECREMENT.
         * @return true if the action was performed; otherwise false.
         * @see #getAccessibleActionCount
         */
        public boolean doAccessibleAction(int i) {
            if (i < 0 || i > 1) {
                return false;
            }
            Object o;
            if (i == 0) {
                o = getNextValue(); // AccessibleAction.INCREMENT
            } else {
                o = getPreviousValue(); // AccessibleAction.DECREMENT
            }
            // try to set the new value
            try {
                model.setValue(o);
                return true;
            } catch (IllegalArgumentException iae) {
                // SpinnerModel didn't like new value
            }
            return false;
        }

        /* ===== End AccessibleAction impl ===== */

        /* ===== Begin AccessibleText impl ===== */

        /*
         * Returns whether source and destination components have the
         * same window ancestor
         * <p>
         *  返回源组件和目标组件是否具有相同的窗口祖先
         * 
         */
        private boolean sameWindowAncestor(Component src, Component dest) {
            if (src == null || dest == null) {
                return false;
            }
            return SwingUtilities.getWindowAncestor(src) ==
                SwingUtilities.getWindowAncestor(dest);
        }

        /**
         * Given a point in local coordinates, return the zero-based index
         * of the character under that Point.  If the point is invalid,
         * this method returns -1.
         *
         * <p>
         *  给定一个点在局部坐标,返回该点下的字符的从零开始的索引。如果该点无效,则此方法返回-1。
         * 
         * 
         * @param p the Point in local coordinates
         * @return the zero-based index of the character under Point p; if
         * Point is invalid return -1.
         */
        public int getIndexAtPoint(Point p) {
            AccessibleText at = getEditorAccessibleText();
            if (at != null && sameWindowAncestor(JSpinner.this, editor)) {
                // convert point from the JSpinner bounds (source) to
                // editor bounds (destination)
                Point editorPoint = SwingUtilities.convertPoint(JSpinner.this,
                                                                p,
                                                                editor);
                if (editorPoint != null) {
                    return at.getIndexAtPoint(editorPoint);
                }
            }
            return -1;
        }

        /**
         * Determines the bounding box of the character at the given
         * index into the string.  The bounds are returned in local
         * coordinates.  If the index is invalid an empty rectangle is
         * returned.
         *
         * <p>
         *  确定给定索引处字符的边界框到字符串中。边界在本地坐标中返回。如果索引无效,则返回一个空的矩形。
         * 
         * 
         * @param i the index into the String
         * @return the screen coordinates of the character's bounding box,
         * if index is invalid return an empty rectangle.
         */
        public Rectangle getCharacterBounds(int i) {
            AccessibleText at = getEditorAccessibleText();
            if (at != null ) {
                Rectangle editorRect = at.getCharacterBounds(i);
                if (editorRect != null &&
                    sameWindowAncestor(JSpinner.this, editor)) {
                    // return rectangle in the the JSpinner bounds
                    return SwingUtilities.convertRectangle(editor,
                                                           editorRect,
                                                           JSpinner.this);
                }
            }
            return null;
        }

        /**
         * Returns the number of characters (valid indicies)
         *
         * <p>
         *  返回字符数(有效的标记)
         * 
         * 
         * @return the number of characters
         */
        public int getCharCount() {
            AccessibleText at = getEditorAccessibleText();
            if (at != null) {
                return at.getCharCount();
            }
            return -1;
        }

        /**
         * Returns the zero-based offset of the caret.
         *
         * Note: That to the right of the caret will have the same index
         * value as the offset (the caret is between two characters).
         * <p>
         *  返回插入符号的从零开始的偏移量。
         * 
         * 注意：插入符右侧的索引值与偏移量相同(插入符号在两个字符之间)。
         * 
         * 
         * @return the zero-based offset of the caret.
         */
        public int getCaretPosition() {
            AccessibleText at = getEditorAccessibleText();
            if (at != null) {
                return at.getCaretPosition();
            }
            return -1;
        }

        /**
         * Returns the String at a given index.
         *
         * <p>
         *  返回给定索引处的String。
         * 
         * 
         * @param part the CHARACTER, WORD, or SENTENCE to retrieve
         * @param index an index within the text
         * @return the letter, word, or sentence
         */
        public String getAtIndex(int part, int index) {
            AccessibleText at = getEditorAccessibleText();
            if (at != null) {
                return at.getAtIndex(part, index);
            }
            return null;
        }

        /**
         * Returns the String after a given index.
         *
         * <p>
         *  返回给定索引后的String。
         * 
         * 
         * @param part the CHARACTER, WORD, or SENTENCE to retrieve
         * @param index an index within the text
         * @return the letter, word, or sentence
         */
        public String getAfterIndex(int part, int index) {
            AccessibleText at = getEditorAccessibleText();
            if (at != null) {
                return at.getAfterIndex(part, index);
            }
            return null;
        }

        /**
         * Returns the String before a given index.
         *
         * <p>
         *  返回给定索引之前的String。
         * 
         * 
         * @param part the CHARACTER, WORD, or SENTENCE to retrieve
         * @param index an index within the text
         * @return the letter, word, or sentence
         */
        public String getBeforeIndex(int part, int index) {
            AccessibleText at = getEditorAccessibleText();
            if (at != null) {
                return at.getBeforeIndex(part, index);
            }
            return null;
        }

        /**
         * Returns the AttributeSet for a given character at a given index
         *
         * <p>
         *  返回给定字符在给定索引的AttributeSet
         * 
         * 
         * @param i the zero-based index into the text
         * @return the AttributeSet of the character
         */
        public AttributeSet getCharacterAttribute(int i) {
            AccessibleText at = getEditorAccessibleText();
            if (at != null) {
                return at.getCharacterAttribute(i);
            }
            return null;
        }

        /**
         * Returns the start offset within the selected text.
         * If there is no selection, but there is
         * a caret, the start and end offsets will be the same.
         *
         * <p>
         *  返回所选文本内的起始偏移量。如果没有选择,但有一个插入符号,开始和结束偏移将是相同的。
         * 
         * 
         * @return the index into the text of the start of the selection
         */
        public int getSelectionStart() {
            AccessibleText at = getEditorAccessibleText();
            if (at != null) {
                return at.getSelectionStart();
            }
            return -1;
        }

        /**
         * Returns the end offset within the selected text.
         * If there is no selection, but there is
         * a caret, the start and end offsets will be the same.
         *
         * <p>
         *  返回所选文本内的结束偏移量。如果没有选择,但有一个插入符号,开始和结束偏移将是相同的。
         * 
         * 
         * @return the index into the text of the end of the selection
         */
        public int getSelectionEnd() {
            AccessibleText at = getEditorAccessibleText();
            if (at != null) {
                return at.getSelectionEnd();
            }
            return -1;
        }

        /**
         * Returns the portion of the text that is selected.
         *
         * <p>
         *  返回所选文本的部分。
         * 
         * 
         * @return the String portion of the text that is selected
         */
        public String getSelectedText() {
            AccessibleText at = getEditorAccessibleText();
            if (at != null) {
                return at.getSelectedText();
            }
            return null;
        }

        /* ===== End AccessibleText impl ===== */


        /* ===== Begin AccessibleEditableText impl ===== */

        /**
         * Sets the text contents to the specified string.
         *
         * <p>
         *  将文本内容设置为指定的字符串。
         * 
         * 
         * @param s the string to set the text contents
         */
        public void setTextContents(String s) {
            AccessibleEditableText at = getEditorAccessibleEditableText();
            if (at != null) {
                at.setTextContents(s);
            }
        }

        /**
         * Inserts the specified string at the given index/
         *
         * <p>
         *  将指定的字符串插入给定的索引/
         * 
         * 
         * @param index the index in the text where the string will
         * be inserted
         * @param s the string to insert in the text
         */
        public void insertTextAtIndex(int index, String s) {
            AccessibleEditableText at = getEditorAccessibleEditableText();
            if (at != null) {
                at.insertTextAtIndex(index, s);
            }
        }

        /**
         * Returns the text string between two indices.
         *
         * <p>
         *  返回两个索引之间的文本字符串。
         * 
         * 
         * @param startIndex the starting index in the text
         * @param endIndex the ending index in the text
         * @return the text string between the indices
         */
        public String getTextRange(int startIndex, int endIndex) {
            AccessibleEditableText at = getEditorAccessibleEditableText();
            if (at != null) {
                return at.getTextRange(startIndex, endIndex);
            }
            return null;
        }

        /**
         * Deletes the text between two indices
         *
         * <p>
         *  删除两个索引之间的文本
         * 
         * 
         * @param startIndex the starting index in the text
         * @param endIndex the ending index in the text
         */
        public void delete(int startIndex, int endIndex) {
            AccessibleEditableText at = getEditorAccessibleEditableText();
            if (at != null) {
                at.delete(startIndex, endIndex);
            }
        }

        /**
         * Cuts the text between two indices into the system clipboard.
         *
         * <p>
         *  将两个索引之间的文本切换到系统剪贴板。
         * 
         * 
         * @param startIndex the starting index in the text
         * @param endIndex the ending index in the text
         */
        public void cut(int startIndex, int endIndex) {
            AccessibleEditableText at = getEditorAccessibleEditableText();
            if (at != null) {
                at.cut(startIndex, endIndex);
            }
        }

        /**
         * Pastes the text from the system clipboard into the text
         * starting at the specified index.
         *
         * <p>
         *  将系统剪贴板中的文本粘贴到从指定索引开始的文本中。
         * 
         * 
         * @param startIndex the starting index in the text
         */
        public void paste(int startIndex) {
            AccessibleEditableText at = getEditorAccessibleEditableText();
            if (at != null) {
                at.paste(startIndex);
            }
        }

        /**
         * Replaces the text between two indices with the specified
         * string.
         *
         * <p>
         *  用指定的字符串替换两个索引之间的文本。
         * 
         * 
         * @param startIndex the starting index in the text
         * @param endIndex the ending index in the text
         * @param s the string to replace the text between two indices
         */
        public void replaceText(int startIndex, int endIndex, String s) {
            AccessibleEditableText at = getEditorAccessibleEditableText();
            if (at != null) {
                at.replaceText(startIndex, endIndex, s);
            }
        }

        /**
         * Selects the text between two indices.
         *
         * <p>
         *  选择两个索引之间的文本。
         * 
         * 
         * @param startIndex the starting index in the text
         * @param endIndex the ending index in the text
         */
        public void selectText(int startIndex, int endIndex) {
            AccessibleEditableText at = getEditorAccessibleEditableText();
            if (at != null) {
                at.selectText(startIndex, endIndex);
            }
        }

        /**
         * Sets attributes for the text between two indices.
         *
         * <p>
         *  在两个索引之间设置文本的属性。
         * 
         * @param startIndex the starting index in the text
         * @param endIndex the ending index in the text
         * @param as the attribute set
         * @see AttributeSet
         */
        public void setAttributes(int startIndex, int endIndex, AttributeSet as) {
            AccessibleEditableText at = getEditorAccessibleEditableText();
            if (at != null) {
                at.setAttributes(startIndex, endIndex, as);
            }
        }
    }  /* End AccessibleJSpinner */
}
