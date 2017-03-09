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
import java.awt.im.InputContext;
import java.io.*;
import java.text.*;
import java.util.*;
import javax.swing.UIManager;
import javax.swing.event.*;
import javax.swing.plaf.UIResource;
import javax.swing.text.*;

/**
 * <code>JFormattedTextField</code> extends <code>JTextField</code> adding
 * support for formatting arbitrary values, as well as retrieving a particular
 * object once the user has edited the text. The following illustrates
 * configuring a <code>JFormattedTextField</code> to edit dates:
 * <pre>
 *   JFormattedTextField ftf = new JFormattedTextField();
 *   ftf.setValue(new Date());
 * </pre>
 * <p>
 * Once a <code>JFormattedTextField</code> has been created, you can
 * listen for editing changes by way of adding
 * a <code>PropertyChangeListener</code> and listening for
 * <code>PropertyChangeEvent</code>s with the property name <code>value</code>.
 * <p>
 * <code>JFormattedTextField</code> allows
 * configuring what action should be taken when focus is lost. The possible
 * configurations are:
 * <table summary="Possible JFormattedTextField configurations and their descriptions">
 * <tr><th><p style="text-align:left">Value</p></th><th><p style="text-align:left">Description</p></th></tr>
 * <tr><td>JFormattedTextField.REVERT
 *            <td>Revert the display to match that of <code>getValue</code>,
 *                possibly losing the current edit.
 *        <tr><td>JFormattedTextField.COMMIT
 *            <td>Commits the current value. If the value being edited
 *                isn't considered a legal value by the
 *                <code>AbstractFormatter</code> that is, a
 *                <code>ParseException</code> is thrown, then the value
 *                will not change, and then edited value will persist.
 *        <tr><td>JFormattedTextField.COMMIT_OR_REVERT
 *            <td>Similar to <code>COMMIT</code>, but if the value isn't
 *                legal, behave like <code>REVERT</code>.
 *        <tr><td>JFormattedTextField.PERSIST
 *            <td>Do nothing, don't obtain a new
 *                <code>AbstractFormatter</code>, and don't update the value.
 * </table>
 * The default is <code>JFormattedTextField.COMMIT_OR_REVERT</code>,
 * refer to {@link #setFocusLostBehavior} for more information on this.
 * <p>
 * <code>JFormattedTextField</code> allows the focus to leave, even if
 * the currently edited value is invalid. To lock the focus down while the
 * <code>JFormattedTextField</code> is an invalid edit state
 * you can attach an <code>InputVerifier</code>. The following code snippet
 * shows a potential implementation of such an <code>InputVerifier</code>:
 * <pre>
 * public class FormattedTextFieldVerifier extends InputVerifier {
 *     public boolean verify(JComponent input) {
 *         if (input instanceof JFormattedTextField) {
 *             JFormattedTextField ftf = (JFormattedTextField)input;
 *             AbstractFormatter formatter = ftf.getFormatter();
 *             if (formatter != null) {
 *                 String text = ftf.getText();
 *                 try {
 *                      formatter.stringToValue(text);
 *                      return true;
 *                  } catch (ParseException pe) {
 *                      return false;
 *                  }
 *              }
 *          }
 *          return true;
 *      }
 *      public boolean shouldYieldFocus(JComponent input) {
 *          return verify(input);
 *      }
 *  }
 * </pre>
 * <p>
 * Alternatively, you could invoke <code>commitEdit</code>, which would also
 * commit the value.
 * <p>
 * <code>JFormattedTextField</code> does not do the formatting it self,
 * rather formatting is done through an instance of
 * <code>JFormattedTextField.AbstractFormatter</code> which is obtained from
 * an instance of <code>JFormattedTextField.AbstractFormatterFactory</code>.
 * Instances of <code>JFormattedTextField.AbstractFormatter</code> are
 * notified when they become active by way of the
 * <code>install</code> method, at which point the
 * <code>JFormattedTextField.AbstractFormatter</code> can install whatever
 * it needs to, typically a <code>DocumentFilter</code>. Similarly when
 * <code>JFormattedTextField</code> no longer
 * needs the <code>AbstractFormatter</code>, it will invoke
 * <code>uninstall</code>.
 * <p>
 * <code>JFormattedTextField</code> typically
 * queries the <code>AbstractFormatterFactory</code> for an
 * <code>AbstractFormat</code> when it gains or loses focus. Although this
 * can change based on the focus lost policy. If the focus lost
 * policy is <code>JFormattedTextField.PERSIST</code>
 * and the <code>JFormattedTextField</code> has been edited, the
 * <code>AbstractFormatterFactory</code> will not be queried until the
 * value has been committed. Similarly if the focus lost policy is
 * <code>JFormattedTextField.COMMIT</code> and an exception
 * is thrown from <code>stringToValue</code>, the
 * <code>AbstractFormatterFactory</code> will not be queried when focus is
 * lost or gained.
 * <p>
 * <code>JFormattedTextField.AbstractFormatter</code>
 * is also responsible for determining when values are committed to
 * the <code>JFormattedTextField</code>. Some
 * <code>JFormattedTextField.AbstractFormatter</code>s will make new values
 * available on every edit, and others will never commit the value. You can
 * force the current value to be obtained
 * from the current <code>JFormattedTextField.AbstractFormatter</code>
 * by way of invoking <code>commitEdit</code>. <code>commitEdit</code> will
 * be invoked whenever return is pressed in the
 * <code>JFormattedTextField</code>.
 * <p>
 * If an <code>AbstractFormatterFactory</code> has not been explicitly
 * set, one will be set based on the <code>Class</code> of the value type after
 * <code>setValue</code> has been invoked (assuming value is non-null).
 * For example, in the following code an appropriate
 * <code>AbstractFormatterFactory</code> and <code>AbstractFormatter</code>
 * will be created to handle formatting of numbers:
 * <pre>
 *   JFormattedTextField tf = new JFormattedTextField();
 *   tf.setValue(new Number(100));
 * </pre>
 * <p>
 * <strong>Warning:</strong> As the <code>AbstractFormatter</code> will
 * typically install a <code>DocumentFilter</code> on the
 * <code>Document</code>, and a <code>NavigationFilter</code> on the
 * <code>JFormattedTextField</code> you should not install your own. If you do,
 * you are likely to see odd behavior in that the editing policy of the
 * <code>AbstractFormatter</code> will not be enforced.
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
 * <p>
 *  <code> JFormattedTextField </code> extends <code> JTextField </code>添加对格式化任意值的支持,以及在用户编辑文本后检索特定对象。
 * 下面说明如何配置<code> JFormattedTextField </code>来编辑日期：。
 * <pre>
 *  JFormattedTextField ftf = new JFormattedTextField(); ftf.setValue(new Date());
 * </pre>
 * <p>
 *  一旦创建了一个<code> JFormattedTextField </code>,您可以通过添加一个<code> PropertyChangeListener </code>来监听编辑更改,并监听<code>
 *  PropertyChangeEvent </code> code> value </code>。
 * <p>
 *  <code> JFormattedTextField </code>允许配置当焦点丢失时应该采取什么操作。可能的配置有：
 * <table summary="Possible JFormattedTextField configurations and their descriptions">
 * <tr> <th> <p style ="text-align：left">值</p> </th> <th> <p style ="text-align：left">说明</p> </th > </tr>
 *  <tr> <td> JFormattedTextField.REVERT <td>还原显示以匹配<code> getValue </code>,可能会丢失当前编辑。
 *  <tr> <td> JFormattedTextField.COMMIT <td>提交当前值。
 * 如果被编辑的值不被<code> AbstractFormatter </code>视为合法值,那么会抛出一个<code> ParseException </code>,那么该值不会改变, 。
 *  <tr> <td> JFormattedTextField.COMMIT_OR_REVERT <td>类似于<code> COMMIT </code>,但如果值不合法,则表现为<code> REVER
 * T </code>。
 * 如果被编辑的值不被<code> AbstractFormatter </code>视为合法值,那么会抛出一个<code> ParseException </code>,那么该值不会改变, 。
 *  <tr> <td> JFormattedTextField.PERSIST <td>不执行任何操作,不获取新的<code> AbstractFormatter </code>,并且不更新值。
 * </table>
 *  默认是<code> JFormattedTextField.COMMIT_OR_REVERT </code>,有关更多信息,请参阅{@link #setFocusLostBehavior}。
 * <p>
 *  <code> JFormattedTextField </code>允许焦点离开,即使当前编辑的值无效。
 * 要在<code> JFormattedTextField </code>是无效的编辑状态时锁定焦点,您可以附加一个<code> InputVerifier </code>。
 * 以下代码片段显示了这种<code> InputVerifier </code>的潜在实现：。
 * <pre>
 * public class FormattedTextFieldVerifier extends InputVerifier {public boolean verify(JComponent input){if(input instanceof JFormattedTextField){JFormattedTextField ftf =(JFormattedTextField)input; AbstractFormatter formatter = ftf.getFormatter(); if(formatter！= null){String text = ftf.getText(); try {formatter.stringToValue(text); return true; }
 *  catch(ParseException pe){return false; }}} return true; } public boolean shouldYieldFocus(JComponent
 *  input){return verify(input); }}。
 * </pre>
 * <p>
 *  或者,您可以调用<code> commitEdit </code>,这也将提交该值。
 * <p>
 *  <code> JFormattedTextField </code>不会自己进行格式化,而是通过<code> JFormattedTextField.AbstractFormatter </code>
 * 实例进行格式化,该实例是从<code> JFormattedTextField.AbstractFormatterFactory </code >。
 * 通过<code> install </code>方法,当<code> JFormattedTextField.AbstractFormatter </code>变为活动时,会通知<code> JForm
 * attedTextField.AbstractFormatter </code>的实例,需要,通常是一个<code> DocumentFilter </code>。
 * 类似地,当<code> JFormattedTextField </code>不再需要<code> AbstractFormatter </code>时,它将调用<code> uninstall </code>
 * 。
 * <p>
 * <code> JFormattedTextField </code>通常在获取或失去焦点时查询<code> AbstractFormatterFactory </code>中的<code> Abstra
 * ctFormat </code>。
 * 虽然这可以根据焦点丢失的政策而改变。
 * 如果焦点丢失策略是<code> JFormattedTextField.PERSIST </code>并且已编辑<code> JFormattedTextField </code>,则在提交值之前不会查
 * 询<code> AbstractFormatterFactory </code>。
 * 虽然这可以根据焦点丢失的政策而改变。
 * 类似地,如果焦点丢失策略是<code> JFormattedTextField.COMMIT </code>并且从<code> stringToValue </code>抛出异常,则当焦点丢失时不会查询
 * <code> AbstractFormatterFactory </code>获得。
 * 虽然这可以根据焦点丢失的政策而改变。
 * <p>
 *  <code> JFormattedTextField.AbstractFormatter </code>还负责确定何时将值提交到<code> JFormattedTextField </code>。
 * 一些<code> JFormattedTextField.AbstractFormatter </code>会在每次编辑时提供新的值,其他人不会提交该值。
 * 您可以通过调用<code> commitEdit </code>强制从当前<code> JFormattedTextField.AbstractFormatter </code>获取当前值。
 *  <code> commitEdit </code>将在<code> JFormattedTextField </code>中按下时被调用。
 * <p>
 * 如果没有显式地设置<code> AbstractFormatterFactory </code>,则将在调用<code> setValue </code>之后基于值类型的<code> Class </code>
 * 是非空)。
 * 例如,在下面的代码中,将创建一个适当的<code> AbstractFormatterFactory </code>和<code> AbstractFormatter </code>来处理数字的格式化：
 * 。
 * <pre>
 *  JFormattedTextField tf = new JFormattedTextField(); tf.setValue(new Number(100));
 * </pre>
 * <p>
 *  <strong>警告：</strong>由于<code> AbstractFormatter </code>通常会在<code>文档</code>上安装<code> DocumentFilter </code>
 * ,并且<code> NavigationFilter <代码>在<code> JFormattedTextField </code>,你不应该安装自己的。
 * 如果你这样做,你可能会看到奇怪的行为,因为<code> AbstractFormatter </code>的编辑策略将不会被强制。
 * <p>
 *  <strong>警告：</strong> Swing不是线程安全的。有关详情,请参阅<a href="package-summary.html#threading"> Swing的线程策略</a>。
 * <p>
 *  <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
 *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
 * 
 * 
 * @since 1.4
 */
public class JFormattedTextField extends JTextField {
    private static final String uiClassID = "FormattedTextFieldUI";
    private static final Action[] defaultActions =
            { new CommitAction(), new CancelAction() };

    /**
     * Constant identifying that when focus is lost,
     * <code>commitEdit</code> should be invoked. If in committing the
     * new value a <code>ParseException</code> is thrown, the invalid
     * value will remain.
     *
     * <p>
     * 常量识别当焦点丢失时,应调用<code> commitEdit </code>。如果在提交新值时抛出一个<code> ParseException </code>,则无效值将保留。
     * 
     * 
     * @see #setFocusLostBehavior
     */
    public static final int COMMIT = 0;

    /**
     * Constant identifying that when focus is lost,
     * <code>commitEdit</code> should be invoked. If in committing the new
     * value a <code>ParseException</code> is thrown, the value will be
     * reverted.
     *
     * <p>
     *  常量识别当焦点丢失时,应调用<code> commitEdit </code>。如果在提交新值时抛出一个<code> ParseException </code>,该值将被还原。
     * 
     * 
     * @see #setFocusLostBehavior
     */
    public static final int COMMIT_OR_REVERT = 1;

    /**
     * Constant identifying that when focus is lost, editing value should
     * be reverted to current value set on the
     * <code>JFormattedTextField</code>.
     *
     * <p>
     *  常量识别当焦点丢失时,编辑值应该还原为在<code> JFormattedTextField </code>上设置的当前值。
     * 
     * 
     * @see #setFocusLostBehavior
     */
    public static final int REVERT = 2;

    /**
     * Constant identifying that when focus is lost, the edited value
     * should be left.
     *
     * <p>
     *  常数识别当焦点丢失时,编辑的值应该保留。
     * 
     * 
     * @see #setFocusLostBehavior
     */
    public static final int PERSIST = 3;


    /**
     * Factory used to obtain an instance of AbstractFormatter.
     * <p>
     *  工厂用于获取AbstractFormatter的一个实例。
     * 
     */
    private AbstractFormatterFactory factory;
    /**
     * Object responsible for formatting the current value.
     * <p>
     *  负责格式化当前值的对象。
     * 
     */
    private AbstractFormatter format;
    /**
     * Last valid value.
     * <p>
     *  最后一个有效值。
     * 
     */
    private Object value;
    /**
     * True while the value being edited is valid.
     * <p>
     *  正在编辑的值有效时为True。
     * 
     */
    private boolean editValid;
    /**
     * Behavior when focus is lost.
     * <p>
     *  焦点丢失时的行为。
     * 
     */
    private int focusLostBehavior;
    /**
     * Indicates the current value has been edited.
     * <p>
     *  表示当前值已编辑。
     * 
     */
    private boolean edited;
    /**
     * Used to set the dirty state.
     * <p>
     *  用于设置脏状态。
     * 
     */
    private DocumentListener documentListener;
    /**
     * Masked used to set the AbstractFormatterFactory.
     * <p>
     *  Masked用于设置AbstractFormatterFactory。
     * 
     */
    private Object mask;
    /**
     * ActionMap that the TextFormatter Actions are added to.
     * <p>
     *  添加了TextFormatter操作的ActionMap。
     * 
     */
    private ActionMap textFormatterActionMap;
    /**
     * Indicates the input method composed text is in the document
     * <p>
     *  表示组成文本的输入法在文档中
     * 
     */
    private boolean composedTextExists = false;
    /**
     * A handler for FOCUS_LOST event
     * <p>
     *  FOCUS_LOST事件的处理程序
     * 
     */
    private FocusLostHandler focusLostHandler;


    /**
     * Creates a <code>JFormattedTextField</code> with no
     * <code>AbstractFormatterFactory</code>. Use <code>setMask</code> or
     * <code>setFormatterFactory</code> to configure the
     * <code>JFormattedTextField</code> to edit a particular type of
     * value.
     * <p>
     *  创建一个没有<code> AbstractFormatterFactory </code>的<code> JFormattedTextField </code>。
     * 使用<code> setMask </code>或<code> setFormatterFactory </code>配置<code> JFormattedTextField </code>以编辑特定类
     * 型的值。
     *  创建一个没有<code> AbstractFormatterFactory </code>的<code> JFormattedTextField </code>。
     * 
     */
    public JFormattedTextField() {
        super();
        enableEvents(AWTEvent.FOCUS_EVENT_MASK);
        setFocusLostBehavior(COMMIT_OR_REVERT);
    }

    /**
     * Creates a JFormattedTextField with the specified value. This will
     * create an <code>AbstractFormatterFactory</code> based on the
     * type of <code>value</code>.
     *
     * <p>
     *  创建具有指定值的JFormattedTextField。这将基于<code> value </code>的类型创建一个<code> AbstractFormatterFactory </code>。
     * 
     * 
     * @param value Initial value for the JFormattedTextField
     */
    public JFormattedTextField(Object value) {
        this();
        setValue(value);
    }

    /**
     * Creates a <code>JFormattedTextField</code>. <code>format</code> is
     * wrapped in an appropriate <code>AbstractFormatter</code> which is
     * then wrapped in an <code>AbstractFormatterFactory</code>.
     *
     * <p>
     * 创建<code> JFormattedTextField </code>。
     *  <code> format </code>包装在一个适当的<code> AbstractFormatter </code>中,然后包装在一个<code> AbstractFormatterFactor
     * y </code>中。
     * 创建<code> JFormattedTextField </code>。
     * 
     * 
     * @param format Format used to look up an AbstractFormatter
     */
    public JFormattedTextField(java.text.Format format) {
        this();
        setFormatterFactory(getDefaultFormatterFactory(format));
    }

    /**
     * Creates a <code>JFormattedTextField</code> with the specified
     * <code>AbstractFormatter</code>. The <code>AbstractFormatter</code>
     * is placed in an <code>AbstractFormatterFactory</code>.
     *
     * <p>
     *  使用指定的<code> AbstractFormatter </code>创建<code> JFormattedTextField </code>。
     *  <code> AbstractFormatter </code>放置在<code> AbstractFormatterFactory </code>中。
     * 
     * 
     * @param formatter AbstractFormatter to use for formatting.
     */
    public JFormattedTextField(AbstractFormatter formatter) {
        this(new DefaultFormatterFactory(formatter));
    }

    /**
     * Creates a <code>JFormattedTextField</code> with the specified
     * <code>AbstractFormatterFactory</code>.
     *
     * <p>
     *  用指定的<code> AbstractFormatterFactory </code>创建<code> JFormattedTextField </code>。
     * 
     * 
     * @param factory AbstractFormatterFactory used for formatting.
     */
    public JFormattedTextField(AbstractFormatterFactory factory) {
        this();
        setFormatterFactory(factory);
    }

    /**
     * Creates a <code>JFormattedTextField</code> with the specified
     * <code>AbstractFormatterFactory</code> and initial value.
     *
     * <p>
     *  使用指定的<code> AbstractFormatterFactory </code>和初始值创建<code> JFormattedTextField </code>。
     * 
     * 
     * @param factory <code>AbstractFormatterFactory</code> used for
     *        formatting.
     * @param currentValue Initial value to use
     */
    public JFormattedTextField(AbstractFormatterFactory factory,
                               Object currentValue) {
        this(currentValue);
        setFormatterFactory(factory);
    }

    /**
     * Sets the behavior when focus is lost. This will be one of
     * <code>JFormattedTextField.COMMIT_OR_REVERT</code>,
     * <code>JFormattedTextField.REVERT</code>,
     * <code>JFormattedTextField.COMMIT</code> or
     * <code>JFormattedTextField.PERSIST</code>
     * Note that some <code>AbstractFormatter</code>s may push changes as
     * they occur, so that the value of this will have no effect.
     * <p>
     * This will throw an <code>IllegalArgumentException</code> if the object
     * passed in is not one of the afore mentioned values.
     * <p>
     * The default value of this property is
     * <code>JFormattedTextField.COMMIT_OR_REVERT</code>.
     *
     * <p>
     *  设置丢失焦点时的行为。
     * 这将是<code> JFormattedTextField.COMMIT_OR_REVERT </code>,<code> JFormattedTextField.REVERT </code>,<code>
     *  JFormattedTextField.COMMIT </code>或<code> JFormattedTextField.PERSIST </code>一些<code> AbstractFormat
     * ter </code>可能会在更改发生时推送更改,以使该值不起作用。
     *  设置丢失焦点时的行为。
     * <p>
     *  如果传入的对象不是上述值之一,这将抛出一个<code> IllegalArgumentException </code>。
     * <p>
     *  此属性的默认值为<code> JFormattedTextField.COMMIT_OR_REVERT </code>。
     * 
     * 
     * @param behavior Identifies behavior when focus is lost
     * @throws IllegalArgumentException if behavior is not one of the known
     *         values
     * @beaninfo
     *  enum: COMMIT         JFormattedTextField.COMMIT
     *        COMMIT_OR_REVERT JFormattedTextField.COMMIT_OR_REVERT
     *        REVERT         JFormattedTextField.REVERT
     *        PERSIST        JFormattedTextField.PERSIST
     *  description: Behavior when component loses focus
     */
    public void setFocusLostBehavior(int behavior) {
        if (behavior != COMMIT && behavior != COMMIT_OR_REVERT &&
            behavior != PERSIST && behavior != REVERT) {
            throw new IllegalArgumentException("setFocusLostBehavior must be one of: JFormattedTextField.COMMIT, JFormattedTextField.COMMIT_OR_REVERT, JFormattedTextField.PERSIST or JFormattedTextField.REVERT");
        }
        focusLostBehavior = behavior;
    }

    /**
     * Returns the behavior when focus is lost. This will be one of
     * <code>COMMIT_OR_REVERT</code>,
     * <code>COMMIT</code>,
     * <code>REVERT</code> or
     * <code>PERSIST</code>
     * Note that some <code>AbstractFormatter</code>s may push changes as
     * they occur, so that the value of this will have no effect.
     *
     * <p>
     *  返回焦点丢失时的行为。
     * 这将是<code> COMMIT_OR_REVERT </code>,<code> COMMIT </code>,<code> REVERT </code>或<code> PERSIST </code>
     * 之一。
     *  返回焦点丢失时的行为。注意一些<code> AbstractFormatter </code > s可能会在更改发生时进行推送,以使此值不起作用。
     * 
     * 
     * @return returns behavior when focus is lost
     */
    public int getFocusLostBehavior() {
        return focusLostBehavior;
    }

    /**
     * Sets the <code>AbstractFormatterFactory</code>.
     * <code>AbstractFormatterFactory</code> is
     * able to return an instance of <code>AbstractFormatter</code> that is
     * used to format a value for display, as well an enforcing an editing
     * policy.
     * <p>
     * If you have not explicitly set an <code>AbstractFormatterFactory</code>
     * by way of this method (or a constructor) an
     * <code>AbstractFormatterFactory</code> and consequently an
     * <code>AbstractFormatter</code> will be used based on the
     * <code>Class</code> of the value. <code>NumberFormatter</code> will
     * be used for <code>Number</code>s, <code>DateFormatter</code> will
     * be used for <code>Dates</code>, otherwise <code>DefaultFormatter</code>
     * will be used.
     * <p>
     * This is a JavaBeans bound property.
     *
     * <p>
     * 设置<code> AbstractFormatterFactory </code>。
     *  <code> AbstractFormatterFactory </code>能够返回用于格式化显示值的<code> AbstractFormatter </code>实例,以及强制执行编辑策略。
     * <p>
     *  如果没有通过此方法(或构造函数)显式设置<code> AbstractFormatterFactory </code>,则将使用<code> AbstractFormatterFactory </code>
     * ,因此将基于<code> AbstractFormatter </code>值的<code> Class </code>。
     *  <code> NumberFormatter </code>将用于<code> Number </code>,<code> DateFormatter </code>将用于<code> Dates </code>
     * ,否则<code> DefaultFormatter <代码>将被使用。
     * <p>
     *  这是一个JavaBeans绑定属性。
     * 
     * 
     * @param tf <code>AbstractFormatterFactory</code> used to lookup
     *          instances of <code>AbstractFormatter</code>
     * @beaninfo
     *       bound: true
     *   attribute: visualUpdate true
     * description: AbstractFormatterFactory, responsible for returning an
     *              AbstractFormatter that can format the current value.
     */
    public void setFormatterFactory(AbstractFormatterFactory tf) {
        AbstractFormatterFactory oldFactory = factory;

        factory = tf;
        firePropertyChange("formatterFactory", oldFactory, tf);
        setValue(getValue(), true, false);
    }

    /**
     * Returns the current <code>AbstractFormatterFactory</code>.
     *
     * <p>
     *  返回当前<code> AbstractFormatterFactory </code>。
     * 
     * 
     * @see #setFormatterFactory
     * @return <code>AbstractFormatterFactory</code> used to determine
     *         <code>AbstractFormatter</code>s
     */
    public AbstractFormatterFactory getFormatterFactory() {
        return factory;
    }

    /**
     * Sets the current <code>AbstractFormatter</code>.
     * <p>
     * You should not normally invoke this, instead set the
     * <code>AbstractFormatterFactory</code> or set the value.
     * <code>JFormattedTextField</code> will
     * invoke this as the state of the <code>JFormattedTextField</code>
     * changes and requires the value to be reset.
     * <code>JFormattedTextField</code> passes in the
     * <code>AbstractFormatter</code> obtained from the
     * <code>AbstractFormatterFactory</code>.
     * <p>
     * This is a JavaBeans bound property.
     *
     * <p>
     *  设置当前的<code> AbstractFormatter </code>。
     * <p>
     *  您通常不应调用此方法,而应设置<code> AbstractFormatterFactory </code>或设置值。
     *  <code> JFormattedTextField </code>会调用此方法作为<code> JFormattedTextField </code>更改的状态,并要求重置该值。
     *  <code> JFormattedTextField </code>传递从<code> AbstractFormatterFactory </code>获取的<code> AbstractFormat
     * ter </code>。
     *  <code> JFormattedTextField </code>会调用此方法作为<code> JFormattedTextField </code>更改的状态,并要求重置该值。
     * <p>
     *  这是一个JavaBeans绑定属性。
     * 
     * 
     * @see #setFormatterFactory
     * @param format AbstractFormatter to use for formatting
     * @beaninfo
     *       bound: true
     *   attribute: visualUpdate true
     * description: TextFormatter, responsible for formatting the current value
     */
    protected void setFormatter(AbstractFormatter format) {
        AbstractFormatter oldFormat = this.format;

        if (oldFormat != null) {
            oldFormat.uninstall();
        }
        setEditValid(true);
        this.format = format;
        if (format != null) {
            format.install(this);
        }
        setEdited(false);
        firePropertyChange("textFormatter", oldFormat, format);
    }

    /**
     * Returns the <code>AbstractFormatter</code> that is used to format and
     * parse the current value.
     *
     * <p>
     *  返回用于格式化和解析当前值的<code> AbstractFormatter </code>。
     * 
     * 
     * @return AbstractFormatter used for formatting
     */
    public AbstractFormatter getFormatter() {
        return format;
    }

    /**
     * Sets the value that will be formatted by an
     * <code>AbstractFormatter</code> obtained from the current
     * <code>AbstractFormatterFactory</code>. If no
     * <code>AbstractFormatterFactory</code> has been specified, this will
     * attempt to create one based on the type of <code>value</code>.
     * <p>
     * The default value of this property is null.
     * <p>
     * This is a JavaBeans bound property.
     *
     * <p>
     * 设置将从当前<code> AbstractFormatterFactory </code>获取的<code> AbstractFormatter </code>格式化的值。
     * 如果没有指定<code> AbstractFormatterFactory </code>,这将尝试基于<code> value </code>的类型创建一个。
     * <p>
     *  此属性的缺省值为null。
     * <p>
     *  这是一个JavaBeans绑定属性。
     * 
     * 
     * @param value Current value to display
     * @beaninfo
     *       bound: true
     *   attribute: visualUpdate true
     * description: The value to be formatted.
     */
    public void setValue(Object value) {
        if (value != null && getFormatterFactory() == null) {
            setFormatterFactory(getDefaultFormatterFactory(value));
        }
        setValue(value, true, true);
    }

    /**
     * Returns the last valid value. Based on the editing policy of
     * the <code>AbstractFormatter</code> this may not return the current
     * value. The currently edited value can be obtained by invoking
     * <code>commitEdit</code> followed by <code>getValue</code>.
     *
     * <p>
     *  返回最后一个有效值。基于<code> AbstractFormatter </code>的编辑策略,这可能不返回当前值。
     * 可以通过调用<code> commitEdit </code>后跟<code> getValue </code>来获取当前编辑的值。
     * 
     * 
     * @return Last valid value
     */
    public Object getValue() {
        return value;
    }

    /**
     * Forces the current value to be taken from the
     * <code>AbstractFormatter</code> and set as the current value.
     * This has no effect if there is no current
     * <code>AbstractFormatter</code> installed.
     *
     * <p>
     *  强制从<code> AbstractFormatter </code>中获取当前值,并将其设置为当前值。
     * 如果没有当前的<code> AbstractFormatter </code>安装,这没有任何效果。
     * 
     * 
     * @throws ParseException if the <code>AbstractFormatter</code> is not able
     *         to format the current value
     */
    public void commitEdit() throws ParseException {
        AbstractFormatter format = getFormatter();

        if (format != null) {
            setValue(format.stringToValue(getText()), false, true);
        }
    }

    /**
     * Sets the validity of the edit on the receiver. You should not normally
     * invoke this. This will be invoked by the
     * <code>AbstractFormatter</code> as the user edits the value.
     * <p>
     * Not all formatters will allow the component to get into an invalid
     * state, and thus this may never be invoked.
     * <p>
     * Based on the look and feel this may visually change the state of
     * the receiver.
     *
     * <p>
     *  设置接收器上编辑的有效性。你通常不应该调用这个。这将由用户编辑值时由<code> AbstractFormatter </code>调用。
     * <p>
     *  并非所有格式化程序都允许组件进入无效状态,因此可能永远不会被调用。
     * <p>
     *  基于外观和感觉,这可以在视觉上改变接收器的状态。
     * 
     * 
     * @param isValid boolean indicating if the currently edited value is
     *        valid.
     * @beaninfo
     *       bound: true
     *   attribute: visualUpdate true
     * description: True indicates the edited value is valid
     */
    private void setEditValid(boolean isValid) {
        if (isValid != editValid) {
            editValid = isValid;
            firePropertyChange("editValid", Boolean.valueOf(!isValid),
                               Boolean.valueOf(isValid));
        }
    }

    /**
     * Returns true if the current value being edited is valid. The value of
     * this is managed by the current <code>AbstractFormatter</code>, as such
     * there is no public setter for it.
     *
     * <p>
     *  如果正在编辑的当前值有效,则返回true。它的值由当前的<code> AbstractFormatter </code>管理,因为它没有公共设置器。
     * 
     * 
     * @return true if the current value being edited is valid.
     */
    public boolean isEditValid() {
        return editValid;
    }

    /**
     * Invoked when the user inputs an invalid value. This gives the
     * component a chance to provide feedback. The default
     * implementation beeps.
     * <p>
     *  当用户输入无效值时调用。这为组件提供了提供反馈的机会。默认实现蜂鸣声。
     * 
     */
    protected void invalidEdit() {
        UIManager.getLookAndFeel().provideErrorFeedback(JFormattedTextField.this);
    }

    /**
     * Processes any input method events, such as
     * <code>InputMethodEvent.INPUT_METHOD_TEXT_CHANGED</code> or
     * <code>InputMethodEvent.CARET_POSITION_CHANGED</code>.
     *
     * <p>
     * 处理任何输入方法事件,例如<code> InputMethodEvent.INPUT_METHOD_TEXT_CHANGED </code>或<code> InputMethodEvent.CARET_
     * POSITION_CHANGED </code>。
     * 
     * 
     * @param e the <code>InputMethodEvent</code>
     * @see InputMethodEvent
     */
    protected void processInputMethodEvent(InputMethodEvent e) {
        AttributedCharacterIterator text = e.getText();
        int commitCount = e.getCommittedCharacterCount();

        // Keep track of the composed text
        if (text != null) {
            int begin = text.getBeginIndex();
            int end = text.getEndIndex();
            composedTextExists = ((end - begin) > commitCount);
        } else {
            composedTextExists = false;
        }

        super.processInputMethodEvent(e);
    }

    /**
     * Processes any focus events, such as
     * <code>FocusEvent.FOCUS_GAINED</code> or
     * <code>FocusEvent.FOCUS_LOST</code>.
     *
     * <p>
     *  处理任何焦点事件,例如<code> FocusEvent.FOCUS_GAINED </code>或<code> FocusEvent.FOCUS_LOST </code>。
     * 
     * 
     * @param e the <code>FocusEvent</code>
     * @see FocusEvent
     */
    protected void processFocusEvent(FocusEvent e) {
        super.processFocusEvent(e);

        // ignore temporary focus event
        if (e.isTemporary()) {
            return;
        }

        if (isEdited() && e.getID() == FocusEvent.FOCUS_LOST) {
            InputContext ic = getInputContext();
            if (focusLostHandler == null) {
                focusLostHandler = new FocusLostHandler();
            }

            // if there is a composed text, process it first
            if ((ic != null) && composedTextExists) {
                ic.endComposition();
                EventQueue.invokeLater(focusLostHandler);
            } else {
                focusLostHandler.run();
            }
        }
        else if (!isEdited()) {
            // reformat
            setValue(getValue(), true, true);
        }
    }

    /**
     * FOCUS_LOST behavior implementation
     * <p>
     *  FOCUS_LOST行为实现
     * 
     */
    private class FocusLostHandler implements Runnable, Serializable {
        public void run() {
            int fb = JFormattedTextField.this.getFocusLostBehavior();
            if (fb == JFormattedTextField.COMMIT ||
                fb == JFormattedTextField.COMMIT_OR_REVERT) {
                try {
                    JFormattedTextField.this.commitEdit();
                    // Give it a chance to reformat.
                    JFormattedTextField.this.setValue(
                        JFormattedTextField.this.getValue(), true, true);
                } catch (ParseException pe) {
                    if (fb == JFormattedTextField.this.COMMIT_OR_REVERT) {
                        JFormattedTextField.this.setValue(
                            JFormattedTextField.this.getValue(), true, true);
                    }
                }
            }
            else if (fb == JFormattedTextField.REVERT) {
                JFormattedTextField.this.setValue(
                    JFormattedTextField.this.getValue(), true, true);
            }
        }
    }

    /**
     * Fetches the command list for the editor.  This is
     * the list of commands supported by the plugged-in UI
     * augmented by the collection of commands that the
     * editor itself supports.  These are useful for binding
     * to events, such as in a keymap.
     *
     * <p>
     *  获取编辑器的命令列表。这是插件UI支持的命令列表,由编辑器本身支持的命令集合增强。这些对于绑定到事件是有用的,例如在键映射中。
     * 
     * 
     * @return the command list
     */
    public Action[] getActions() {
        return TextAction.augmentList(super.getActions(), defaultActions);
    }

    /**
     * Gets the class ID for a UI.
     *
     * <p>
     *  获取UI的类ID。
     * 
     * 
     * @return the string "FormattedTextFieldUI"
     * @see JComponent#getUIClassID
     */
    public String getUIClassID() {
        return uiClassID;
    }

    /**
     * Associates the editor with a text document.
     * The currently registered factory is used to build a view for
     * the document, which gets displayed by the editor after revalidation.
     * A PropertyChange event ("document") is propagated to each listener.
     *
     * <p>
     *  将编辑器与文本文档相关联。当前注册的工厂用于构建文档的视图,在重新验证后由编辑器显示。 PropertyChange事件("文档")传播到每个侦听器。
     * 
     * 
     * @param doc  the document to display/edit
     * @see #getDocument
     * @beaninfo
     *  description: the text document model
     *        bound: true
     *       expert: true
     */
    public void setDocument(Document doc) {
        if (documentListener != null && getDocument() != null) {
            getDocument().removeDocumentListener(documentListener);
        }
        super.setDocument(doc);
        if (documentListener == null) {
            documentListener = new DocumentHandler();
        }
        doc.addDocumentListener(documentListener);
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
     * Resets the Actions that come from the TextFormatter to
     * <code>actions</code>.
     * <p>
     *  将来自TextFormatter的操作重置为<code> actions </code>。
     * 
     */
    private void setFormatterActions(Action[] actions) {
        if (actions == null) {
            if (textFormatterActionMap != null) {
                textFormatterActionMap.clear();
            }
        }
        else {
            if (textFormatterActionMap == null) {
                ActionMap map = getActionMap();

                textFormatterActionMap = new ActionMap();
                while (map != null) {
                    ActionMap parent = map.getParent();

                    if (parent instanceof UIResource || parent == null) {
                        map.setParent(textFormatterActionMap);
                        textFormatterActionMap.setParent(parent);
                        break;
                    }
                    map = parent;
                }
            }
            for (int counter = actions.length - 1; counter >= 0;
                 counter--) {
                Object key = actions[counter].getValue(Action.NAME);

                if (key != null) {
                    textFormatterActionMap.put(key, actions[counter]);
                }
            }
        }
    }

    /**
     * Does the setting of the value. If <code>createFormat</code> is true,
     * this will also obtain a new <code>AbstractFormatter</code> from the
     * current factory. The property change event will be fired if
     * <code>firePC</code> is true.
     * <p>
     *  是否设置值。如果<code> createFormat </code>为true,这也将从当前工厂获取一个新的<code> AbstractFormatter </code>。
     * 如果<code> firePC </code>为true,则属性更改事件将触发。
     * 
     */
    private void setValue(Object value, boolean createFormat, boolean firePC) {
        Object oldValue = this.value;

        this.value = value;

        if (createFormat) {
            AbstractFormatterFactory factory = getFormatterFactory();
            AbstractFormatter atf;

            if (factory != null) {
                atf = factory.getFormatter(this);
            }
            else {
                atf = null;
            }
            setFormatter(atf);
        }
        else {
            // Assumed to be valid
            setEditValid(true);
        }

        setEdited(false);

        if (firePC) {
            firePropertyChange("value", oldValue, value);
        }
    }

    /**
     * Sets the edited state of the receiver.
     * <p>
     *  设置接收机的编辑状态。
     * 
     */
    private void setEdited(boolean edited) {
        this.edited = edited;
    }

    /**
     * Returns true if the receiver has been edited.
     * <p>
     *  如果接收器已编辑,则返回true。
     * 
     */
    private boolean isEdited() {
        return edited;
    }

    /**
     * Returns an AbstractFormatterFactory suitable for the passed in
     * Object type.
     * <p>
     *  返回适用于在对象类型中传递的AbstractFormatterFactory。
     * 
     */
    private AbstractFormatterFactory getDefaultFormatterFactory(Object type) {
        if (type instanceof DateFormat) {
            return new DefaultFormatterFactory(new DateFormatter
                                               ((DateFormat)type));
        }
        if (type instanceof NumberFormat) {
            return new DefaultFormatterFactory(new NumberFormatter(
                                               (NumberFormat)type));
        }
        if (type instanceof Format) {
            return new DefaultFormatterFactory(new InternationalFormatter(
                                               (Format)type));
        }
        if (type instanceof Date) {
            return new DefaultFormatterFactory(new DateFormatter());
        }
        if (type instanceof Number) {
            AbstractFormatter displayFormatter = new NumberFormatter();
            ((NumberFormatter)displayFormatter).setValueClass(type.getClass());
            AbstractFormatter editFormatter = new NumberFormatter(
                                  new DecimalFormat("#.#"));
            ((NumberFormatter)editFormatter).setValueClass(type.getClass());

            return new DefaultFormatterFactory(displayFormatter,
                                               displayFormatter,editFormatter);
        }
        return new DefaultFormatterFactory(new DefaultFormatter());
    }


    /**
     * Instances of <code>AbstractFormatterFactory</code> are used by
     * <code>JFormattedTextField</code> to obtain instances of
     * <code>AbstractFormatter</code> which in turn are used to format values.
     * <code>AbstractFormatterFactory</code> can return different
     * <code>AbstractFormatter</code>s based on the state of the
     * <code>JFormattedTextField</code>, perhaps returning different
     * <code>AbstractFormatter</code>s when the
     * <code>JFormattedTextField</code> has focus vs when it
     * doesn't have focus.
     * <p>
     * <code> JFormattedTextField </code>使用<code> AbstractFormatterFactory </code>实例来获取<code> AbstractFormat
     * ter </code>的实例,这些实例又用于格式化值。
     *  <code> AbstractFormatterFactory </code>可以基于<code> JFormattedTextField </code>的状态返回不同的<code> Abstract
     * Formatter </code>,也许返回不同的<code> AbstractFormatter </code>代码> JFormattedTextField </code>有焦点vs,当它没有焦点。
     * 
     * 
     * @since 1.4
     */
    public static abstract class AbstractFormatterFactory {
        /**
         * Returns an <code>AbstractFormatter</code> that can handle formatting
         * of the passed in <code>JFormattedTextField</code>.
         *
         * <p>
         *  返回一个<code> AbstractFormatter </code>,它可以处理在<code> JFormattedTextField </code>中传递的格式。
         * 
         * 
         * @param tf JFormattedTextField requesting AbstractFormatter
         * @return AbstractFormatter to handle formatting duties, a null
         *         return value implies the JFormattedTextField should behave
         *         like a normal JTextField
         */
        public abstract AbstractFormatter getFormatter(JFormattedTextField tf);
    }


    /**
     * Instances of <code>AbstractFormatter</code> are used by
     * <code>JFormattedTextField</code> to handle the conversion both
     * from an Object to a String, and back from a String to an Object.
     * <code>AbstractFormatter</code>s can also enforce editing policies,
     * or navigation policies, or manipulate the
     * <code>JFormattedTextField</code> in any way it sees fit to
     * enforce the desired policy.
     * <p>
     * An <code>AbstractFormatter</code> can only be active in
     * one <code>JFormattedTextField</code> at a time.
     * <code>JFormattedTextField</code> invokes
     * <code>install</code> when it is ready to use it followed
     * by <code>uninstall</code> when done. Subclasses
     * that wish to install additional state should override
     * <code>install</code> and message super appropriately.
     * <p>
     * Subclasses must override the conversion methods
     * <code>stringToValue</code> and <code>valueToString</code>. Optionally
     * they can override <code>getActions</code>,
     * <code>getNavigationFilter</code> and <code>getDocumentFilter</code>
     * to restrict the <code>JFormattedTextField</code> in a particular
     * way.
     * <p>
     * Subclasses that allow the <code>JFormattedTextField</code> to be in
     * a temporarily invalid state should invoke <code>setEditValid</code>
     * at the appropriate times.
     * <p>
     *  <code> AbstractFormatter </code>的实例由<code> JFormattedTextField </code>用于处理从对象到字符串的转换,以及从字符串回到对象的转换。
     *  <code> AbstractFormatter </code>还可以强制编辑策略或导航策略,或以任何其认为适合强制实施所需策略的方式处理<code> JFormattedTextField </code>
     * 。
     * <p>
     *  <code> AbstractFormatter </code>只能在一个<code> JFormattedTextField </code>中激活。
     *  <code> JFormattedTextField </code>调用<code> install </code>当它准备好使用它,随后<code>卸载</code>完成后。
     * 希望安装附加状态的子类应该覆盖<code> install </code>和消息super。
     * <p>
     * 子类必须覆盖转换方法<code> stringToValue </code>和<code> valueToString </code>。
     * 可选地,他们可以覆盖<code> getActions </code>,<code> getNavigationFilter </code>和<code> getDocumentFilter </code>
     * 以特定方式限制<code> JFormattedTextField </code>。
     * 子类必须覆盖转换方法<code> stringToValue </code>和<code> valueToString </code>。
     * <p>
     *  允许<code> JFormattedTextField </code>处于临时无效状态的子类应在适当的时候调用<code> setEditValid </code>。
     * 
     * 
     * @since 1.4
     */
    public static abstract class AbstractFormatter implements Serializable {
        private JFormattedTextField ftf;

        /**
         * Installs the <code>AbstractFormatter</code> onto a particular
         * <code>JFormattedTextField</code>.
         * This will invoke <code>valueToString</code> to convert the
         * current value from the <code>JFormattedTextField</code> to
         * a String. This will then install the <code>Action</code>s from
         * <code>getActions</code>, the <code>DocumentFilter</code>
         * returned from <code>getDocumentFilter</code> and the
         * <code>NavigationFilter</code> returned from
         * <code>getNavigationFilter</code> onto the
         * <code>JFormattedTextField</code>.
         * <p>
         * Subclasses will typically only need to override this if they
         * wish to install additional listeners on the
         * <code>JFormattedTextField</code>.
         * <p>
         * If there is a <code>ParseException</code> in converting the
         * current value to a String, this will set the text to an empty
         * String, and mark the <code>JFormattedTextField</code> as being
         * in an invalid state.
         * <p>
         * While this is a public method, this is typically only useful
         * for subclassers of <code>JFormattedTextField</code>.
         * <code>JFormattedTextField</code> will invoke this method at
         * the appropriate times when the value changes, or its internal
         * state changes.  You will only need to invoke this yourself if
         * you are subclassing <code>JFormattedTextField</code> and
         * installing/uninstalling <code>AbstractFormatter</code> at a
         * different time than <code>JFormattedTextField</code> does.
         *
         * <p>
         *  将<code> AbstractFormatter </code>安装到特定的<code> JFormattedTextField </code>。
         * 这将调用<code> valueToString </code>将当前值从<code> JFormattedTextField </code>转换为字符串。
         * 这将安装<code> Action </code>从<code> getActions </code>,<code> DocumentFilter </code>从<code> getDocumentF
         * ilter </code>返回,<code> NavigationFilter < / code>从<code> getNavigationFilter </code>返回到<code> JFormat
         * tedTextField </code>。
         * 这将调用<code> valueToString </code>将当前值从<code> JFormattedTextField </code>转换为字符串。
         * <p>
         *  如果子类想要在<code> JFormattedTextField </code>上安装附加的监听器,通常只需要覆盖这个。
         * <p>
         *  如果在将当前值转换为字符串时存在<code> ParseException </code>,则会将文本设置为空字符串,并将<code> JFormattedTextField </code>标记为无效
         * 状态。
         * <p>
         * 虽然这是一个公共方法,但这通常只对<code> JFormattedTextField </code>的子类有用。
         *  <code> JFormattedTextField </code>将在值更改或其内部状态更改的适当时间调用此方法。
         * 如果你在<code> JFormattedTextField </code>之外的其他时间子类化<code> JFormattedTextField </code>并安装/卸载<code> Abstra
         * ctFormatter </code>,你只需要自己调用它。
         *  <code> JFormattedTextField </code>将在值更改或其内部状态更改的适当时间调用此方法。
         * 
         * 
         * @param ftf JFormattedTextField to format for, may be null indicating
         *            uninstall from current JFormattedTextField.
         */
        public void install(JFormattedTextField ftf) {
            if (this.ftf != null) {
                uninstall();
            }
            this.ftf = ftf;
            if (ftf != null) {
                try {
                    ftf.setText(valueToString(ftf.getValue()));
                } catch (ParseException pe) {
                    ftf.setText("");
                    setEditValid(false);
                }
                installDocumentFilter(getDocumentFilter());
                ftf.setNavigationFilter(getNavigationFilter());
                ftf.setFormatterActions(getActions());
            }
        }

        /**
         * Uninstalls any state the <code>AbstractFormatter</code> may have
         * installed on the <code>JFormattedTextField</code>. This resets the
         * <code>DocumentFilter</code>, <code>NavigationFilter</code>
         * and additional <code>Action</code>s installed on the
         * <code>JFormattedTextField</code>.
         * <p>
         *  卸载<code> AbstractFormatter </code>可能已安装在<code> JFormattedTextField </code>上的任何状态。
         * 这将重置<code> DocumentFilter </code>,<code> NavigationFilter </code>和安装在<code> JFormattedTextField </code>
         * 上的附加<code> Action </code>。
         *  卸载<code> AbstractFormatter </code>可能已安装在<code> JFormattedTextField </code>上的任何状态。
         * 
         */
        public void uninstall() {
            if (this.ftf != null) {
                installDocumentFilter(null);
                this.ftf.setNavigationFilter(null);
                this.ftf.setFormatterActions(null);
            }
        }

        /**
         * Parses <code>text</code> returning an arbitrary Object. Some
         * formatters may return null.
         *
         * <p>
         *  Parses <code> text </code>返回任意对象。一些格式化程序可能返回null。
         * 
         * 
         * @throws ParseException if there is an error in the conversion
         * @param text String to convert
         * @return Object representation of text
         */
        public abstract Object stringToValue(String text) throws
                                     ParseException;

        /**
         * Returns the string value to display for <code>value</code>.
         *
         * <p>
         *  返回要显示<code> value </code>的字符串值。
         * 
         * 
         * @throws ParseException if there is an error in the conversion
         * @param value Value to convert
         * @return String representation of value
         */
        public abstract String valueToString(Object value) throws
                        ParseException;

        /**
         * Returns the current <code>JFormattedTextField</code> the
         * <code>AbstractFormatter</code> is installed on.
         *
         * <p>
         *  返回当前安装了<code> AbstractFormatter </code>的<code> JFormattedTextField </code>。
         * 
         * 
         * @return JFormattedTextField formatting for.
         */
        protected JFormattedTextField getFormattedTextField() {
            return ftf;
        }

        /**
         * This should be invoked when the user types an invalid character.
         * This forwards the call to the current JFormattedTextField.
         * <p>
         *  当用户键入无效字符时,应调用此方法。这会将调用转发到当前的JFormattedTextField。
         * 
         */
        protected void invalidEdit() {
            JFormattedTextField ftf = getFormattedTextField();

            if (ftf != null) {
                ftf.invalidEdit();
            }
        }

        /**
         * Invoke this to update the <code>editValid</code> property of the
         * <code>JFormattedTextField</code>. If you an enforce a policy
         * such that the <code>JFormattedTextField</code> is always in a
         * valid state, you will never need to invoke this.
         *
         * <p>
         *  调用此方法以更新<code> JFormattedTextField </code>的<code> editValid </code>属性。
         * 如果你执行一个策略,使<code> JFormattedTextField </code>始终处于有效状态,你永远不需要调用这个。
         * 
         * 
         * @param valid Valid state of the JFormattedTextField
         */
        protected void setEditValid(boolean valid) {
            JFormattedTextField ftf = getFormattedTextField();

            if (ftf != null) {
                ftf.setEditValid(valid);
            }
        }

        /**
         * Subclass and override if you wish to provide a custom set of
         * <code>Action</code>s. <code>install</code> will install these
         * on the <code>JFormattedTextField</code>'s <code>ActionMap</code>.
         *
         * <p>
         * 子类和覆盖,如果你想提供一个自定义的<code> Action </code>集。
         *  <code> install </code>会将这些安装在<code> JFormattedTextField </code>的<code> ActionMap </code>中。
         * 
         * 
         * @return Array of Actions to install on JFormattedTextField
         */
        protected Action[] getActions() {
            return null;
        }

        /**
         * Subclass and override if you wish to provide a
         * <code>DocumentFilter</code> to restrict what can be input.
         * <code>install</code> will install the returned value onto
         * the <code>JFormattedTextField</code>.
         *
         * <p>
         *  子类和覆盖如果你想提供一个<code> DocumentFilter </code>来限制可以输入什么。
         *  <code> install </code>会将返回的值安装到<code> JFormattedTextField </code>。
         * 
         * 
         * @return DocumentFilter to restrict edits
         */
        protected DocumentFilter getDocumentFilter() {
            return null;
        }

        /**
         * Subclass and override if you wish to provide a filter to restrict
         * where the user can navigate to.
         * <code>install</code> will install the returned value onto
         * the <code>JFormattedTextField</code>.
         *
         * <p>
         *  子类和覆盖,如果您想提供一个过滤器来限制用户可以导航到哪里。 <code> install </code>会将返回的值安装到<code> JFormattedTextField </code>。
         * 
         * 
         * @return NavigationFilter to restrict navigation
         */
        protected NavigationFilter getNavigationFilter() {
            return null;
        }

        /**
         * Clones the <code>AbstractFormatter</code>. The returned instance
         * is not associated with a <code>JFormattedTextField</code>.
         *
         * <p>
         *  克隆<code> AbstractFormatter </code>。返回的实例不与<code> JFormattedTextField </code>相关联。
         * 
         * 
         * @return Copy of the AbstractFormatter
         */
        protected Object clone() throws CloneNotSupportedException {
            AbstractFormatter formatter = (AbstractFormatter)super.clone();

            formatter.ftf = null;
            return formatter;
        }

        /**
         * Installs the <code>DocumentFilter</code> <code>filter</code>
         * onto the current <code>JFormattedTextField</code>.
         *
         * <p>
         *  将<code> DocumentFilter </code> <code>过滤器</code>安装到当前<code> JFormattedTextField </code>。
         * 
         * 
         * @param filter DocumentFilter to install on the Document.
         */
        private void installDocumentFilter(DocumentFilter filter) {
            JFormattedTextField ftf = getFormattedTextField();

            if (ftf != null) {
                Document doc = ftf.getDocument();

                if (doc instanceof AbstractDocument) {
                    ((AbstractDocument)doc).setDocumentFilter(filter);
                }
                doc.putProperty(DocumentFilter.class, null);
            }
        }
    }


    /**
     * Used to commit the edit. This extends JTextField.NotifyAction
     * so that <code>isEnabled</code> is true while a JFormattedTextField
     * has focus, and extends <code>actionPerformed</code> to invoke
     * commitEdit.
     * <p>
     *  用于提交编辑。
     * 这扩展了JTextField.NotifyAction,使得<code> isEnabled </code>为true,而JFormattedTextField具有焦点,并扩展<code> action
     * Performed </code>以调用commitEdit。
     *  用于提交编辑。
     * 
     */
    static class CommitAction extends JTextField.NotifyAction {
        public void actionPerformed(ActionEvent e) {
            JTextComponent target = getFocusedComponent();

            if (target instanceof JFormattedTextField) {
                // Attempt to commit the value
                try {
                    ((JFormattedTextField)target).commitEdit();
                } catch (ParseException pe) {
                    ((JFormattedTextField)target).invalidEdit();
                    // value not committed, don't notify ActionListeners
                    return;
                }
            }
            // Super behavior.
            super.actionPerformed(e);
        }

        public boolean isEnabled() {
            JTextComponent target = getFocusedComponent();
            if (target instanceof JFormattedTextField) {
                JFormattedTextField ftf = (JFormattedTextField)target;
                if (!ftf.isEdited()) {
                    return false;
                }
                return true;
            }
            return super.isEnabled();
        }
    }


    /**
     * CancelAction will reset the value in the JFormattedTextField when
     * <code>actionPerformed</code> is invoked. It will only be
     * enabled if the focused component is an instance of
     * JFormattedTextField.
     * <p>
     *  当调用<code> actionPerformed </code>时,CancelAction将重置JFormattedTextField中的值。
     * 仅当聚焦的组件是JFormattedTextField的实例时,它才会启用。
     * 
     */
    private static class CancelAction extends TextAction {
        public CancelAction() {
            super("reset-field-edit");
        }

        public void actionPerformed(ActionEvent e) {
            JTextComponent target = getFocusedComponent();

            if (target instanceof JFormattedTextField) {
                JFormattedTextField ftf = (JFormattedTextField)target;
                ftf.setValue(ftf.getValue());
            }
        }

        public boolean isEnabled() {
            JTextComponent target = getFocusedComponent();
            if (target instanceof JFormattedTextField) {
                JFormattedTextField ftf = (JFormattedTextField)target;
                if (!ftf.isEdited()) {
                    return false;
                }
                return true;
            }
            return super.isEnabled();
        }
    }


    /**
     * Sets the dirty state as the document changes.
     * <p>
     */
    private class DocumentHandler implements DocumentListener, Serializable {
        public void insertUpdate(DocumentEvent e) {
            setEdited(true);
        }
        public void removeUpdate(DocumentEvent e) {
            setEdited(true);
        }
        public void changedUpdate(DocumentEvent e) {}
    }
}
