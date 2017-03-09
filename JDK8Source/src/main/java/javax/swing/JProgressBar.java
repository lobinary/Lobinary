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

import java.awt.Color;
import java.awt.Graphics;

import java.text.Format;
import java.text.NumberFormat;

import java.io.Serializable;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;

import javax.swing.event.*;
import javax.accessibility.*;
import javax.swing.plaf.ProgressBarUI;


/**
 * A component that visually displays the progress of some task.  As the task
 * progresses towards completion, the progress bar displays the
 * task's percentage of completion.
 * This percentage is typically represented visually by a rectangle which
 * starts out empty and gradually becomes filled in as the task progresses.
 * In addition, the progress bar can display a textual representation of this
 * percentage.
 * <p>
 * {@code JProgressBar} uses a {@code BoundedRangeModel} as its data model,
 * with the {@code value} property representing the "current" state of the task,
 * and the {@code minimum} and {@code maximum} properties representing the
 * beginning and end points, respectively.
 * <p>
 * To indicate that a task of unknown length is executing,
 * you can put a progress bar into indeterminate mode.
 * While the bar is in indeterminate mode,
 * it animates constantly to show that work is occurring.
 * As soon as you can determine the task's length and amount of progress,
 * you should update the progress bar's value
 * and switch it back to determinate mode.
 *
 * <p>
 *
 * Here is an example of creating a progress bar,
 * where <code>task</code> is an object (representing some piece of work)
 * which returns information about the progress of the task:
 *
 *<pre>
 *progressBar = new JProgressBar(0, task.getLengthOfTask());
 *progressBar.setValue(0);
 *progressBar.setStringPainted(true);
 *</pre>
 *
 * Here is an example of querying the current state of the task, and using
 * the returned value to update the progress bar:
 *
 *<pre>
 *progressBar.setValue(task.getCurrent());
 *</pre>
 *
 * Here is an example of putting a progress bar into
 * indeterminate mode,
 * and then switching back to determinate mode
 * once the length of the task is known:
 *
 *<pre>
 *progressBar = new JProgressBar();
 *<em>...//when the task of (initially) unknown length begins:</em>
 *progressBar.setIndeterminate(true);
 *<em>...//do some work; get length of task...</em>
 *progressBar.setMaximum(newLength);
 *progressBar.setValue(newValue);
 *progressBar.setIndeterminate(false);
 *</pre>
 *
 * <p>
 *
 * For complete examples and further documentation see
 * <a href="https://docs.oracle.com/javase/tutorial/uiswing/components/progress.html" target="_top">How to Monitor Progress</a>,
 * a section in <em>The Java Tutorial.</em>
 *
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
 *  可视地显示某些任务进度的组件。随着任务进度完成,进度条显示任务的完成百分比。该百分比通常由从空白开始并且随着任务进行逐渐填充的矩形可视地表示。此外,进度条可以显示此百分比的文本表示。
 * <p>
 *  {@code JProgressBar}使用{@code BoundedRangeModel}作为其数据模型,{@code value}属性表示任务的"当前"状态,{@code minimum}和{@code maximum}
 * 属性表示分别为起点和终点。
 * <p>
 *  要指示执行未知长度的任务,可以将进度条放入不确定模式。当条形图处于不确定模式时,它不断地动画显示工作正在发生。一旦你可以确定任务的长度和进度,你应该更新进度条的值,并切换回确定模式。
 * 
 * <p>
 * 
 *  下面是创建进度条的示例,其中<code> task </code>是一个对象(表示某些工作),它返回有关任务进度的信息：
 * 
 * pre>
 *  rogressBar = new JProgressBar(0,task.getLengthOfTask()); rogressBar.setValue(0); rogressBar.setStrin
 * gPainted(true);。
 * /pre>
 * 
 * 以下是查询任务当前状态的示例,并使用返回的值更新进度条：
 * 
 * pre>
 *  rogressBar.setValue(task.getCurrent());
 * /pre>
 * 
 *  下面是一个将进度条放入不确定模式,然后在任务长度已知时切换回确定模式的示例：
 * 
 * pre>
 *  rogressBar = new JProgressBar(); em> ... //当(初始)未知长度的任务开始时：</em> rogressBar.setIndeterminate(true); 
 * em> ... //做一些工作;获取任务的长度... </em> rogressBar.setMaximum(newLength); rogressBar.setValue(newValue); rog
 * ressBar.setIndeterminate(false);。
 * /pre>
 * 
 * <p>
 * 
 *  有关完整示例和其他文档,请参见<a href="https://docs.oracle.com/javase/tutorial/uiswing/components/progress.html" target="_top">
 * 如何监控进度</a>, Java教程</em>中的<em>部分。
 * 
 * <p>
 *  <strong>警告：</strong> Swing不是线程安全的。有关详情,请参阅<a href="package-summary.html#threading"> Swing的线程策略</a>。
 * <p>
 *  <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
 *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
 * 
 * 
 * @see javax.swing.plaf.basic.BasicProgressBarUI
 * @see javax.swing.BoundedRangeModel
 * @see javax.swing.SwingWorker
 *
 * @beaninfo
 *      attribute: isContainer false
 *    description: A component that displays an integer value.
 *
 * @author Michael C. Albers
 * @author Kathy Walrath
 */
public class JProgressBar extends JComponent implements SwingConstants, Accessible
{
    /**
    /* <p>
    /* 
     * @see #getUIClassID
     */
    private static final String uiClassID = "ProgressBarUI";

    /**
     * Whether the progress bar is horizontal or vertical.
     * The default is <code>HORIZONTAL</code>.
     *
     * <p>
     *  进度条是水平还是垂直。默认值为<code> HORIZONTAL </code>。
     * 
     * 
     * @see #setOrientation
     */
    protected int orientation;

    /**
     * Whether to display a border around the progress bar.
     * The default is <code>true</code>.
     *
     * <p>
     * 是否在进度条周围显示边框。默认值为<code> true </code>。
     * 
     * 
     * @see #setBorderPainted
     */
    protected boolean paintBorder;

    /**
     * The object that holds the data for the progress bar.
     *
     * <p>
     *  保存进度条数据的对象。
     * 
     * 
     * @see #setModel
     */
    protected BoundedRangeModel model;

    /**
     * An optional string that can be displayed on the progress bar.
     * The default is <code>null</code>. Setting this to a non-<code>null</code>
     * value does not imply that the string will be displayed.
     * To display the string, {@code paintString} must be {@code true}.
     *
     * <p>
     *  可以显示在进度条上的可选字符串。默认值为<code> null </code>。将此设置为非<code> null </code>值并不意味着将显示字符串。
     * 要显示字符串,{@code paintString}必须是{@code true}。
     * 
     * 
     * @see #setString
     * @see #setStringPainted
     */
    protected String progressString;

    /**
     * Whether to display a string of text on the progress bar.
     * The default is <code>false</code>.
     * Setting this to <code>true</code> causes a textual
     * display of the progress to be rendered on the progress bar. If
     * the <code>progressString</code> is <code>null</code>,
     * the percentage of completion is displayed on the progress bar.
     * Otherwise, the <code>progressString</code> is
     * rendered on the progress bar.
     *
     * <p>
     *  是否在进度条上显示一串文本。默认值为<code> false </code>。将此设置为<code> true </code>会导致在进度条上呈现进度的文本显示。
     * 如果<code> progressString </code>是<code> null </code>,则完成百分比将显示在进度条上。
     * 否则,在进度条上呈现<code> progressString </code>。
     * 
     * 
     * @see #setStringPainted
     * @see #setString
     */
    protected boolean paintString;

    /**
     * The default minimum for a progress bar is 0.
     * <p>
     *  进度条的默认最小值为0。
     * 
     */
    static final private int defaultMinimum = 0;
    /**
     * The default maximum for a progress bar is 100.
     * <p>
     *  进度条的默认最大值为100。
     * 
     */
    static final private int defaultMaximum = 100;
    /**
     * The default orientation for a progress bar is <code>HORIZONTAL</code>.
     * <p>
     *  进度条的默认方向为<code> HORIZONTAL </code>。
     * 
     */
    static final private int defaultOrientation = HORIZONTAL;

    /**
     * Only one <code>ChangeEvent</code> is needed per instance since the
     * event's only interesting property is the immutable source, which
     * is the progress bar.
     * The event is lazily created the first time that an
     * event notification is fired.
     *
     * <p>
     *  每个实例只需要一个<code> ChangeEvent </code>,因为事件的唯一有趣的属性是不可变的源,这是进度条。事件是在第一次触发事件通知时延迟创建的。
     * 
     * 
     * @see #fireStateChanged
     */
    protected transient ChangeEvent changeEvent = null;

    /**
     * Listens for change events sent by the progress bar's model,
     * redispatching them
     * to change-event listeners registered upon
     * this progress bar.
     *
     * <p>
     *  侦听由进度条的模型发送的更改事件,将它们重新分派到在此进度条上注册的更改事件侦听器。
     * 
     * 
     * @see #createChangeListener
     */
    protected ChangeListener changeListener = null;

    /**
     * Format used when displaying percent complete.
     * <p>
     *  显示百分比完成时使用的格式。
     * 
     */
    private transient Format format;

    /**
     * Whether the progress bar is indeterminate (<code>true</code>) or
     * normal (<code>false</code>); the default is <code>false</code>.
     *
     * <p>
     * 进度条是否不确定(<code> true </code>)或正常(<code> false </code>);默认值为<code> false </code>。
     * 
     * 
     * @see #setIndeterminate
     * @since 1.4
     */
    private boolean indeterminate;


   /**
     * Creates a horizontal progress bar
     * that displays a border but no progress string.
     * The initial and minimum values are 0,
     * and the maximum is 100.
     *
     * <p>
     *  创建显示边框但没有进度字符串的水平进度条。初始值和最小值为0,最大值为100。
     * 
     * 
     * @see #setOrientation
     * @see #setBorderPainted
     * @see #setStringPainted
     * @see #setString
     * @see #setIndeterminate
     */
    public JProgressBar()
    {
        this(defaultOrientation);
    }

   /**
     * Creates a progress bar with the specified orientation,
     * which can be
     * either {@code SwingConstants.VERTICAL} or
     * {@code SwingConstants.HORIZONTAL}.
     * By default, a border is painted but a progress string is not.
     * The initial and minimum values are 0,
     * and the maximum is 100.
     *
     * <p>
     *  创建具有指定方向的进度条,可以是{@code SwingConstants.VERTICAL}或{@code SwingConstants.HORIZONTAL}。
     * 默认情况下,绘制边框,但不绘制进度字符串。初始值和最小值为0,最大值为100。
     * 
     * 
     * @param orient  the desired orientation of the progress bar
     * @throws IllegalArgumentException if {@code orient} is an illegal value
     *
     * @see #setOrientation
     * @see #setBorderPainted
     * @see #setStringPainted
     * @see #setString
     * @see #setIndeterminate
     */
    public JProgressBar(int orient)
    {
        this(orient, defaultMinimum, defaultMaximum);
    }


    /**
     * Creates a horizontal progress bar
     * with the specified minimum and maximum.
     * Sets the initial value of the progress bar to the specified minimum.
     * By default, a border is painted but a progress string is not.
     * <p>
     * The <code>BoundedRangeModel</code> that holds the progress bar's data
     * handles any issues that may arise from improperly setting the
     * minimum, initial, and maximum values on the progress bar.
     * See the {@code BoundedRangeModel} documentation for details.
     *
     * <p>
     *  创建具有指定的最小值和最大值的水平进度条。将进度条的初始值设置为指定的最小值。默认情况下,绘制边框,但不绘制进度字符串。
     * <p>
     *  保存进度条数据的<code> BoundedRangeModel </code>处理由于在进度条上不正确设置最小值,初始值和最大值而可能产生的任何问题。
     * 有关详细信息,请参阅{@code BoundedRangeModel}文档。
     * 
     * 
     * @param min  the minimum value of the progress bar
     * @param max  the maximum value of the progress bar
     *
     * @see BoundedRangeModel
     * @see #setOrientation
     * @see #setBorderPainted
     * @see #setStringPainted
     * @see #setString
     * @see #setIndeterminate
     */
    public JProgressBar(int min, int max)
    {
        this(defaultOrientation, min, max);
    }


    /**
     * Creates a progress bar using the specified orientation,
     * minimum, and maximum.
     * By default, a border is painted but a progress string is not.
     * Sets the initial value of the progress bar to the specified minimum.
     * <p>
     * The <code>BoundedRangeModel</code> that holds the progress bar's data
     * handles any issues that may arise from improperly setting the
     * minimum, initial, and maximum values on the progress bar.
     * See the {@code BoundedRangeModel} documentation for details.
     *
     * <p>
     *  使用指定的方向,最小值和最大值创建进度条。默认情况下,绘制边框,但不绘制进度字符串。将进度条的初始值设置为指定的最小值。
     * <p>
     *  保存进度条数据的<code> BoundedRangeModel </code>处理由于在进度条上不正确设置最小值,初始值和最大值而可能产生的任何问题。
     * 有关详细信息,请参阅{@code BoundedRangeModel}文档。
     * 
     * 
     * @param orient  the desired orientation of the progress bar
     * @param min  the minimum value of the progress bar
     * @param max  the maximum value of the progress bar
     * @throws IllegalArgumentException if {@code orient} is an illegal value
     *
     * @see BoundedRangeModel
     * @see #setOrientation
     * @see #setBorderPainted
     * @see #setStringPainted
     * @see #setString
     * @see #setIndeterminate
     */
    public JProgressBar(int orient, int min, int max)
    {
        // Creating the model this way is a bit simplistic, but
        //  I believe that it is the the most common usage of this
        //  component - it's what people will expect.
        setModel(new DefaultBoundedRangeModel(min, 0, min, max));
        updateUI();

        setOrientation(orient);      // documented with set/getOrientation()
        setBorderPainted(true);      // documented with is/setBorderPainted()
        setStringPainted(false);     // see setStringPainted
        setString(null);             // see getString
        setIndeterminate(false);     // see setIndeterminate
    }


    /**
     * Creates a horizontal progress bar
     * that uses the specified model
     * to hold the progress bar's data.
     * By default, a border is painted but a progress string is not.
     *
     * <p>
     * 创建使用指定模型保存进度条数据的水平进度条。默认情况下,绘制边框,但不绘制进度字符串。
     * 
     * 
     * @param newModel  the data model for the progress bar
     *
     * @see #setOrientation
     * @see #setBorderPainted
     * @see #setStringPainted
     * @see #setString
     * @see #setIndeterminate
     */
    public JProgressBar(BoundedRangeModel newModel)
    {
        setModel(newModel);
        updateUI();

        setOrientation(defaultOrientation);  // see setOrientation()
        setBorderPainted(true);              // see setBorderPainted()
        setStringPainted(false);             // see setStringPainted
        setString(null);                     // see getString
        setIndeterminate(false);             // see setIndeterminate
    }


    /**
     * Returns {@code SwingConstants.VERTICAL} or
     * {@code SwingConstants.HORIZONTAL}, depending on the orientation
     * of the progress bar. The default orientation is
     * {@code SwingConstants.HORIZONTAL}.
     *
     * <p>
     *  返回{@code SwingConstants.VERTICAL}或{@code SwingConstants.HORIZONTAL},具体取决于进度条的方向。
     * 默认方向是{@code SwingConstants.HORIZONTAL}。
     * 
     * 
     * @return <code>HORIZONTAL</code> or <code>VERTICAL</code>
     * @see #setOrientation
     */
    public int getOrientation() {
        return orientation;
    }


   /**
     * Sets the progress bar's orientation to <code>newOrientation</code>,
     * which must be {@code SwingConstants.VERTICAL} or
     * {@code SwingConstants.HORIZONTAL}. The default orientation
     * is {@code SwingConstants.HORIZONTAL}.
     *
     * <p>
     *  将进度栏的方向设置为<code> newOrientation </code>,它必须是{@code SwingConstants.VERTICAL}或{@code SwingConstants.HORIZONTAL}
     * 。
     * 默认方向是{@code SwingConstants.HORIZONTAL}。
     * 
     * 
     * @param  newOrientation  <code>HORIZONTAL</code> or <code>VERTICAL</code>
     * @exception      IllegalArgumentException    if <code>newOrientation</code>
     *                                              is an illegal value
     * @see #getOrientation
     *
     * @beaninfo
     *    preferred: true
     *        bound: true
     *    attribute: visualUpdate true
     *  description: Set the progress bar's orientation.
     */
    public void setOrientation(int newOrientation) {
        if (orientation != newOrientation) {
            switch (newOrientation) {
            case VERTICAL:
            case HORIZONTAL:
                int oldOrientation = orientation;
                orientation = newOrientation;
                firePropertyChange("orientation", oldOrientation, newOrientation);
                if (accessibleContext != null) {
                    accessibleContext.firePropertyChange(
                            AccessibleContext.ACCESSIBLE_STATE_PROPERTY,
                            ((oldOrientation == VERTICAL)
                             ? AccessibleState.VERTICAL
                             : AccessibleState.HORIZONTAL),
                            ((orientation == VERTICAL)
                             ? AccessibleState.VERTICAL
                             : AccessibleState.HORIZONTAL));
                }
                break;
            default:
                throw new IllegalArgumentException(newOrientation +
                                             " is not a legal orientation");
            }
            revalidate();
        }
    }


    /**
     * Returns the value of the <code>stringPainted</code> property.
     *
     * <p>
     *  返回<code> stringPainted </code>属性的值。
     * 
     * 
     * @return the value of the <code>stringPainted</code> property
     * @see    #setStringPainted
     * @see    #setString
     */
    public boolean isStringPainted() {
        return paintString;
    }


    /**
     * Sets the value of the <code>stringPainted</code> property,
     * which determines whether the progress bar
     * should render a progress string.
     * The default is <code>false</code>, meaning
     * no string is painted.
     * Some look and feels might not support progress strings
     * or might support them only when the progress bar is in determinate mode.
     *
     * <p>
     *  设置<code> stringPainted </code>属性的值,该属性确定进度条是否应呈现进度字符串。默认值为<code> false </code>,表示没有字符串。
     * 一些外观和感觉可能不支持进度字符串,或者可能仅当进度条处于确定模式时才支持它们。
     * 
     * 
     * @param   b       <code>true</code> if the progress bar should render a string
     * @see     #isStringPainted
     * @see     #setString
     * @beaninfo
     *        bound: true
     *    attribute: visualUpdate true
     *  description: Whether the progress bar should render a string.
     */
    public void setStringPainted(boolean b) {
        //PENDING: specify that string not painted when in indeterminate mode?
        //         or just leave that to the L&F?
        boolean oldValue = paintString;
        paintString = b;
        firePropertyChange("stringPainted", oldValue, paintString);
        if (paintString != oldValue) {
            revalidate();
            repaint();
        }
    }


    /**
     * Returns a {@code String} representation of the current progress.
     * By default, this returns a simple percentage {@code String} based on
     * the value returned from {@code getPercentComplete}.  An example
     * would be the "42%".  You can change this by calling {@code setString}.
     *
     * <p>
     *  返回当前进度的{@code String}表示。默认情况下,这会根据从{@code getPercentComplete}返回的值返回一个简单的百分比{@code String}。
     * 一个例子是"42％"。您可以通过调用{@code setString}更改此设置。
     * 
     * 
     * @return the value of the progress string, or a simple percentage string
     *         if the progress string is {@code null}
     * @see    #setString
     */
    public String getString(){
        if (progressString != null) {
            return progressString;
        } else {
            if (format == null) {
                format = NumberFormat.getPercentInstance();
            }
            return format.format(new Double(getPercentComplete()));
        }
    }

    /**
     * Sets the value of the progress string. By default,
     * this string is <code>null</code>, implying the built-in behavior of
     * using a simple percent string.
     * If you have provided a custom progress string and want to revert to
     * the built-in behavior, set the string back to <code>null</code>.
     * <p>
     * The progress string is painted only if
     * the <code>isStringPainted</code> method returns <code>true</code>.
     *
     * <p>
     * 设置进度字符串的值。默认情况下,此字符串为<code> null </code>,表示使用简单百分比字符串的内置行为。
     * 如果您提供了自定义进度字符串并且想要恢复为内置行为,请将该字符串设置为<code> null </code>。
     * <p>
     *  仅当<code> isStringPainted </code>方法返回<code> true </code>时,才绘制进度字符串。
     * 
     * 
     * @param  s       the value of the progress string
     * @see    #getString
     * @see    #setStringPainted
     * @see    #isStringPainted
     * @beaninfo
     *        bound: true
     *    attribute: visualUpdate true
     *  description: Specifies the progress string to paint
     */
    public void setString(String s){
        String oldValue = progressString;
        progressString = s;
        firePropertyChange("string", oldValue, progressString);
        if (progressString == null || oldValue == null || !progressString.equals(oldValue)) {
            repaint();
        }
    }

    /**
     * Returns the percent complete for the progress bar.
     * Note that this number is between 0.0 and 1.0.
     *
     * <p>
     *  返回进度条的完成百分比。请注意,此数字介于0.0和1.0之间。
     * 
     * 
     * @return the percent complete for this progress bar
     */
    public double getPercentComplete() {
        long span = model.getMaximum() - model.getMinimum();
        double currentValue = model.getValue();
        double pc = (currentValue - model.getMinimum()) / span;
        return pc;
    }

    /**
     * Returns the <code>borderPainted</code> property.
     *
     * <p>
     *  返回<code> borderPainted </code>属性。
     * 
     * 
     * @return the value of the <code>borderPainted</code> property
     * @see    #setBorderPainted
     * @beaninfo
     *  description: Does the progress bar paint its border
     */
    public boolean isBorderPainted() {
        return paintBorder;
    }

    /**
     * Sets the <code>borderPainted</code> property, which is
     * <code>true</code> if the progress bar should paint its border.
     * The default value for this property is <code>true</code>.
     * Some look and feels might not implement painted borders;
     * they will ignore this property.
     *
     * <p>
     *  设置<code> borderPainted </code>属性,即<code> true </code>如果进度条应该绘制其边框。此属性的默认值为<code> true </code>。
     * 一些外观和感觉可能不实现画边界;他们将忽略此属性。
     * 
     * 
     * @param   b       <code>true</code> if the progress bar
     *                  should paint its border;
     *                  otherwise, <code>false</code>
     * @see     #isBorderPainted
     * @beaninfo
     *        bound: true
     *    attribute: visualUpdate true
     *  description: Whether the progress bar should paint its border.
     */
    public void setBorderPainted(boolean b) {
        boolean oldValue = paintBorder;
        paintBorder = b;
        firePropertyChange("borderPainted", oldValue, paintBorder);
        if (paintBorder != oldValue) {
            repaint();
        }
    }

    /**
     * Paints the progress bar's border if the <code>borderPainted</code>
     * property is <code>true</code>.
     *
     * <p>
     *  如果<code> borderPainted </code>属性为<code> true </code>,则绘制进度条的边框。
     * 
     * 
     * @param g  the <code>Graphics</code> context within which to paint the border
     * @see #paint
     * @see #setBorder
     * @see #isBorderPainted
     * @see #setBorderPainted
     */
    protected void paintBorder(Graphics g) {
        if (isBorderPainted()) {
            super.paintBorder(g);
        }
    }


    /**
     * Returns the look-and-feel object that renders this component.
     *
     * <p>
     *  返回呈现此组件的外观对象。
     * 
     * 
     * @return the <code>ProgressBarUI</code> object that renders this component
     */
    public ProgressBarUI getUI() {
        return (ProgressBarUI)ui;
    }

    /**
     * Sets the look-and-feel object that renders this component.
     *
     * <p>
     *  设置呈现此组件的外观和对象对象。
     * 
     * 
     * @param ui  a <code>ProgressBarUI</code> object
     * @see UIDefaults#getUI
     * @beaninfo
     *        bound: true
     *       hidden: true
     *    attribute: visualUpdate true
     *  description: The UI object that implements the Component's LookAndFeel.
     */
    public void setUI(ProgressBarUI ui) {
        super.setUI(ui);
    }


    /**
     * Resets the UI property to a value from the current look and feel.
     *
     * <p>
     *  将UI属性重置为当前外观的值。
     * 
     * 
     * @see JComponent#updateUI
     */
    public void updateUI() {
        setUI((ProgressBarUI)UIManager.getUI(this));
    }


    /**
     * Returns the name of the look-and-feel class that renders this component.
     *
     * <p>
     *  返回呈现此组件的look-and-feel类的名称。
     * 
     * 
     * @return the string "ProgressBarUI"
     * @see JComponent#getUIClassID
     * @see UIDefaults#getUI
     * @beaninfo
     *        expert: true
     *   description: A string that specifies the name of the look-and-feel class.
     */
    public String getUIClassID() {
        return uiClassID;
    }


    /* We pass each Change event to the listeners with the
     * the progress bar as the event source.
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
     *  进度条作为事件源。
     * <p>
     * <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
     *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
     * 
     */
    private class ModelListener implements ChangeListener, Serializable {
        public void stateChanged(ChangeEvent e) {
            fireStateChanged();
        }
    }

    /**
     * Subclasses that want to handle change events
     * from the model differently
     * can override this to return
     * an instance of a custom <code>ChangeListener</code> implementation.
     * The default {@code ChangeListener} simply calls the
     * {@code fireStateChanged} method to forward {@code ChangeEvent}s
     * to the {@code ChangeListener}s that have been added directly to the
     * progress bar.
     *
     * <p>
     *  想要以不同方式处理来自模型的更改事件的子类可以覆盖此类,以返回自定义<code> ChangeListener </code>实现的实例。
     * 默认的{@code ChangeListener}只是调用{@code fireStateChanged}方法将{@code ChangeEvent}转发到直接添加到进度条的{@code ChangeListener}
     * 。
     *  想要以不同方式处理来自模型的更改事件的子类可以覆盖此类,以返回自定义<code> ChangeListener </code>实现的实例。
     * 
     * 
     * @see #changeListener
     * @see #fireStateChanged
     * @see javax.swing.event.ChangeListener
     * @see javax.swing.BoundedRangeModel
     */
    protected ChangeListener createChangeListener() {
        return new ModelListener();
    }

    /**
     * Adds the specified <code>ChangeListener</code> to the progress bar.
     *
     * <p>
     *  将指定的<code> ChangeListener </code>添加到进度条。
     * 
     * 
     * @param l the <code>ChangeListener</code> to add
     */
    public void addChangeListener(ChangeListener l) {
        listenerList.add(ChangeListener.class, l);
    }

    /**
     * Removes a <code>ChangeListener</code> from the progress bar.
     *
     * <p>
     *  从进度栏中删除<code> ChangeListener </code>。
     * 
     * 
     * @param l the <code>ChangeListener</code> to remove
     */
    public void removeChangeListener(ChangeListener l) {
        listenerList.remove(ChangeListener.class, l);
    }

    /**
     * Returns an array of all the <code>ChangeListener</code>s added
     * to this progress bar with <code>addChangeListener</code>.
     *
     * <p>
     *  返回通过<code> addChangeListener </code>添加到此进度条的所有<code> ChangeListener </code>数组。
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
     * Send a {@code ChangeEvent}, whose source is this {@code JProgressBar}, to
     * all {@code ChangeListener}s that have registered interest in
     * {@code ChangeEvent}s.
     * This method is called each time a {@code ChangeEvent} is received from
     * the model.
     * <p>
     *
     * The event instance is created if necessary, and stored in
     * {@code changeEvent}.
     *
     * <p>
     *  向所有对{@code ChangeEvent}感兴趣的{@code ChangeListener}发送{@code ChangeEvent}(其源代码为{@code JProgressBar})。
     * 每当从模型接收到{@code ChangeEvent}时,将调用此方法。
     * <p>
     * 
     *  必要时创建事件实例,并存储在{@code changeEvent}中。
     * 
     * 
     * @see #addChangeListener
     * @see EventListenerList
     */
    protected void fireStateChanged() {
        // Guaranteed to return a non-null array
        Object[] listeners = listenerList.getListenerList();
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length-2; i>=0; i-=2) {
            if (listeners[i]==ChangeListener.class) {
                // Lazily create the event:
                if (changeEvent == null)
                    changeEvent = new ChangeEvent(this);
                ((ChangeListener)listeners[i+1]).stateChanged(changeEvent);
            }
        }
    }

    /**
     * Returns the data model used by this progress bar.
     *
     * <p>
     *  返回此进度条使用的数据模型。
     * 
     * 
     * @return the <code>BoundedRangeModel</code> currently in use
     * @see #setModel
     * @see    BoundedRangeModel
     */
    public BoundedRangeModel getModel() {
        return model;
    }

    /**
     * Sets the data model used by the <code>JProgressBar</code>.
     * Note that the {@code BoundedRangeModel}'s {@code extent} is not used,
     * and is set to {@code 0}.
     *
     * <p>
     * 设置<code> JProgressBar </code>使用的数据模型。请注意,{@code BoundedRangeModel}的{@code extent}未使用,并设置为{@code 0}。
     * 
     * 
     * @param  newModel the <code>BoundedRangeModel</code> to use
     *
     * @beaninfo
     *    expert: true
     * description: The data model used by the JProgressBar.
     */
    public void setModel(BoundedRangeModel newModel) {
        // PENDING(???) setting the same model to multiple bars is broken; listeners
        BoundedRangeModel oldModel = getModel();

        if (newModel != oldModel) {
            if (oldModel != null) {
                oldModel.removeChangeListener(changeListener);
                changeListener = null;
            }

            model = newModel;

            if (newModel != null) {
                changeListener = createChangeListener();
                newModel.addChangeListener(changeListener);
            }

            if (accessibleContext != null) {
                accessibleContext.firePropertyChange(
                        AccessibleContext.ACCESSIBLE_VALUE_PROPERTY,
                        (oldModel== null
                         ? null : Integer.valueOf(oldModel.getValue())),
                        (newModel== null
                         ? null : Integer.valueOf(newModel.getValue())));
            }

            if (model != null) {
                model.setExtent(0);
            }
            repaint();
        }
    }


    /* All of the model methods are implemented by delegation. */

    /**
     * Returns the progress bar's current {@code value}
     * from the <code>BoundedRangeModel</code>.
     * The value is always between the
     * minimum and maximum values, inclusive.
     *
     * <p>
     *  从<code> BoundedRangeModel </code>中返回进度条的当前{@code value}。该值始终介于最小值和最大值之间(包括最小值和最大值)。
     * 
     * 
     * @return  the current value of the progress bar
     * @see     #setValue
     * @see     BoundedRangeModel#getValue
     */
    public int getValue() { return getModel().getValue(); }

    /**
     * Returns the progress bar's {@code minimum} value
     * from the <code>BoundedRangeModel</code>.
     *
     * <p>
     *  从<code> BoundedRangeModel </code>中返回进度条的{@code minimum}值。
     * 
     * 
     * @return  the progress bar's minimum value
     * @see     #setMinimum
     * @see     BoundedRangeModel#getMinimum
     */
    public int getMinimum() { return getModel().getMinimum(); }

    /**
     * Returns the progress bar's {@code maximum} value
     * from the <code>BoundedRangeModel</code>.
     *
     * <p>
     *  从<code> BoundedRangeModel </code>中返回进度条的{@code maximum}值。
     * 
     * 
     * @return  the progress bar's maximum value
     * @see     #setMaximum
     * @see     BoundedRangeModel#getMaximum
     */
    public int getMaximum() { return getModel().getMaximum(); }

    /**
     * Sets the progress bar's current value to {@code n}.  This method
     * forwards the new value to the model.
     * <p>
     * The data model (an instance of {@code BoundedRangeModel})
     * handles any mathematical
     * issues arising from assigning faulty values.  See the
     * {@code BoundedRangeModel} documentation for details.
     * <p>
     * If the new value is different from the previous value,
     * all change listeners are notified.
     *
     * <p>
     *  将进度条的当前值设置为{@code n}。此方法将新值转发给模型。
     * <p>
     *  数据模型({@code BoundedRangeModel}的一个实例)处理由分配错误值引起的任何数学问题。有关详细信息,请参阅{@code BoundedRangeModel}文档。
     * <p>
     *  如果新值与先前值不同,则通知所有更改侦听器。
     * 
     * 
     * @param   n       the new value
     * @see     #getValue
     * @see     #addChangeListener
     * @see     BoundedRangeModel#setValue
     * @beaninfo
     *    preferred: true
     *  description: The progress bar's current value.
     */
    public void setValue(int n) {
        BoundedRangeModel brm = getModel();
        int oldValue = brm.getValue();
        brm.setValue(n);

        if (accessibleContext != null) {
            accessibleContext.firePropertyChange(
                    AccessibleContext.ACCESSIBLE_VALUE_PROPERTY,
                    Integer.valueOf(oldValue),
                    Integer.valueOf(brm.getValue()));
        }
    }

    /**
     * Sets the progress bar's minimum value
     * (stored in the progress bar's data model) to <code>n</code>.
     * <p>
     * The data model (a <code>BoundedRangeModel</code> instance)
     * handles any mathematical
     * issues arising from assigning faulty values.
     * See the {@code BoundedRangeModel} documentation for details.
     * <p>
     * If the minimum value is different from the previous minimum,
     * all change listeners are notified.
     *
     * <p>
     *  将进度条的最小值(存储在进度条的数据模型中)设置为<code> n </code>。
     * <p>
     *  数据模型(<code> BoundedRangeModel </code>实例)处理由分配错误值引起的任何数学问题。有关详细信息,请参阅{@code BoundedRangeModel}文档。
     * <p>
     *  如果最小值与先前的最小值不同,则通知所有更改侦听器。
     * 
     * 
     * @param  n       the new minimum
     * @see    #getMinimum
     * @see    #addChangeListener
     * @see    BoundedRangeModel#setMinimum
     * @beaninfo
     *  preferred: true
     * description: The progress bar's minimum value.
     */
    public void setMinimum(int n) { getModel().setMinimum(n); }

    /**
     * Sets the progress bar's maximum value
     * (stored in the progress bar's data model) to <code>n</code>.
     * <p>
     * The underlying <code>BoundedRangeModel</code> handles any mathematical
     * issues arising from assigning faulty values.
     * See the {@code BoundedRangeModel} documentation for details.
     * <p>
     * If the maximum value is different from the previous maximum,
     * all change listeners are notified.
     *
     * <p>
     *  将进度条的最大值(存储在进度条的数据模型中)设置为<code> n </code>。
     * <p>
     * 底层的<code> BoundedRangeModel </code>处理由分配错误值引起的任何数学问题。有关详细信息,请参阅{@code BoundedRangeModel}文档。
     * <p>
     *  如果最大值与先前的最大值不同,则通知所有更改侦听器。
     * 
     * 
     * @param  n       the new maximum
     * @see    #getMaximum
     * @see    #addChangeListener
     * @see    BoundedRangeModel#setMaximum
     * @beaninfo
     *    preferred: true
     *  description: The progress bar's maximum value.
     */
    public void setMaximum(int n) { getModel().setMaximum(n); }

    /**
     * Sets the <code>indeterminate</code> property of the progress bar,
     * which determines whether the progress bar is in determinate
     * or indeterminate mode.
     * An indeterminate progress bar continuously displays animation
     * indicating that an operation of unknown length is occurring.
     * By default, this property is <code>false</code>.
     * Some look and feels might not support indeterminate progress bars;
     * they will ignore this property.
     *
     * <p>
     *
     * See
     * <a href="https://docs.oracle.com/javase/tutorial/uiswing/components/progress.html" target="_top">How to Monitor Progress</a>
     * for examples of using indeterminate progress bars.
     *
     * <p>
     *  设置进度条的<code> indeterminate </code>属性,它确定进度条是处于确定模式还是不确定模式。不确定的进度条连续地显示表示正在发生未知长度的操作的动画。
     * 默认情况下,此属性为<code> false </code>。一些外观和感觉可能不支持不确定的进度条;他们将忽略此属性。
     * 
     * <p>
     * 
     *  有关使用不确定进度条的示例,请参见<a href="https://docs.oracle.com/javase/tutorial/uiswing/components/proutorial.html" target="_top">
     * 如何监控进度</a>。
     * 
     * 
     * @param newValue  <code>true</code> if the progress bar
     *                  should change to indeterminate mode;
     *                  <code>false</code> if it should revert to normal.
     *
     * @see #isIndeterminate
     * @see javax.swing.plaf.basic.BasicProgressBarUI
     *
     * @since 1.4
     *
     * @beaninfo
     *        bound: true
     *    attribute: visualUpdate true
     *  description: Set whether the progress bar is indeterminate (true)
     *               or normal (false).
     */
    public void setIndeterminate(boolean newValue) {
        boolean oldValue = indeterminate;
        indeterminate = newValue;
        firePropertyChange("indeterminate", oldValue, indeterminate);
    }

    /**
     * Returns the value of the <code>indeterminate</code> property.
     *
     * <p>
     *  返回<code> indeterminate </code>属性的值。
     * 
     * 
     * @return the value of the <code>indeterminate</code> property
     * @see    #setIndeterminate
     *
     * @since 1.4
     *
     * @beaninfo
     *  description: Is the progress bar indeterminate (true)
     *               or normal (false)?
     */
    public boolean isIndeterminate() {
        return indeterminate;
    }


    /**
     * See readObject() and writeObject() in JComponent for more
     * information about serialization in Swing.
     * <p>
     *  有关Swing中序列化的更多信息,请参阅JComponent中的readObject()和writeObject()。
     * 
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
     * Returns a string representation of this <code>JProgressBar</code>.
     * This method is intended to be used only for debugging purposes. The
     * content and format of the returned string may vary between
     * implementations. The returned string may be empty but may not
     * be <code>null</code>.
     *
     * <p>
     *  返回此<code> JProgressBar </code>的字符串表示形式。此方法仅用于调试目的。返回的字符串的内容和格式可能因实现而异。
     * 返回的字符串可能为空,但可能不是<code> null </code>。
     * 
     * 
     * @return  a string representation of this <code>JProgressBar</code>
     */
    protected String paramString() {
        String orientationString = (orientation == HORIZONTAL ?
                                    "HORIZONTAL" : "VERTICAL");
        String paintBorderString = (paintBorder ?
                                    "true" : "false");
        String progressStringString = (progressString != null ?
                                       progressString : "");
        String paintStringString = (paintString ?
                                    "true" : "false");
        String indeterminateString = (indeterminate ?
                                    "true" : "false");

        return super.paramString() +
        ",orientation=" + orientationString +
        ",paintBorder=" + paintBorderString +
        ",paintString=" + paintStringString +
        ",progressString=" + progressStringString +
        ",indeterminateString=" + indeterminateString;
    }

/////////////////
// Accessibility support
////////////////

    /**
     * Gets the <code>AccessibleContext</code> associated with this
     * <code>JProgressBar</code>. For progress bars, the
     * <code>AccessibleContext</code> takes the form of an
     * <code>AccessibleJProgressBar</code>.
     * A new <code>AccessibleJProgressBar</code> instance is created if necessary.
     *
     * <p>
     * 获取与此<code> JProgressBar </code>关联的<code> AccessibleContext </code>。
     * 对于进度条,<code> AccessibleContext </code>采用<code> AccessibleJProgressBar </code>的形式。
     * 如果需要,将创建一个新的<code> AccessibleJProgressBar </code>实例。
     * 
     * 
     * @return an <code>AccessibleJProgressBar</code> that serves as the
     *         <code>AccessibleContext</code> of this <code>JProgressBar</code>
     * @beaninfo
     *       expert: true
     *  description: The AccessibleContext associated with this ProgressBar.
     */
    public AccessibleContext getAccessibleContext() {
        if (accessibleContext == null) {
            accessibleContext = new AccessibleJProgressBar();
        }
        return accessibleContext;
    }

    /**
     * This class implements accessibility support for the
     * <code>JProgressBar</code> class.  It provides an implementation of the
     * Java Accessibility API appropriate to progress bar user-interface
     * elements.
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
     *  此类实现<code> JProgressBar </code>类的辅助功能支持。它提供了适用于进度条用户界面元素的Java可访问性API的实现。
     * <p>
     *  <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
     *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
     * 
     */
    protected class AccessibleJProgressBar extends AccessibleJComponent
        implements AccessibleValue {

        /**
         * Gets the state set of this object.
         *
         * <p>
         *  获取此对象的状态集。
         * 
         * 
         * @return an instance of AccessibleState containing the current state
         * of the object
         * @see AccessibleState
         */
        public AccessibleStateSet getAccessibleStateSet() {
            AccessibleStateSet states = super.getAccessibleStateSet();
            if (getModel().getValueIsAdjusting()) {
                states.add(AccessibleState.BUSY);
            }
            if (getOrientation() == VERTICAL) {
                states.add(AccessibleState.VERTICAL);
            } else {
                states.add(AccessibleState.HORIZONTAL);
            }
            return states;
        }

        /**
         * Gets the role of this object.
         *
         * <p>
         *  获取此对象的作用。
         * 
         * 
         * @return an instance of AccessibleRole describing the role of the
         * object
         */
        public AccessibleRole getAccessibleRole() {
            return AccessibleRole.PROGRESS_BAR;
        }

        /**
         * Gets the <code>AccessibleValue</code> associated with this object.  In the
         * implementation of the Java Accessibility API for this class,
         * returns this object, which is responsible for implementing the
         * <code>AccessibleValue</code> interface on behalf of itself.
         *
         * <p>
         *  获取与此对象关联的<code> AccessibleValue </code>。
         * 在为该类实现Java Accessibility API时,返回此对象,该对象负责代表自身实现<code> AccessibleValue </code>接口。
         * 
         * 
         * @return this object
         */
        public AccessibleValue getAccessibleValue() {
            return this;
        }

        /**
         * Gets the accessible value of this object.
         *
         * <p>
         *  获取此对象的可访问值。
         * 
         * 
         * @return the current value of this object
         */
        public Number getCurrentAccessibleValue() {
            return Integer.valueOf(getValue());
        }

        /**
         * Sets the value of this object as a <code>Number</code>.
         *
         * <p>
         *  将此对象的值设置为<code> Number </code>。
         * 
         * 
         * @return <code>true</code> if the value was set
         */
        public boolean setCurrentAccessibleValue(Number n) {
            // TIGER- 4422535
            if (n == null) {
                return false;
            }
            setValue(n.intValue());
            return true;
        }

        /**
         * Gets the minimum accessible value of this object.
         *
         * <p>
         *  获取此对象的最小可访问值。
         * 
         * 
         * @return the minimum value of this object
         */
        public Number getMinimumAccessibleValue() {
            return Integer.valueOf(getMinimum());
        }

        /**
         * Gets the maximum accessible value of this object.
         *
         * <p>
         *  获取此对象的最大可访问值。
         * 
         * @return the maximum value of this object
         */
        public Number getMaximumAccessibleValue() {
            // TIGER - 4422362
            return Integer.valueOf(model.getMaximum() - model.getExtent());
        }

    } // AccessibleJProgressBar
}
