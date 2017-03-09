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



import java.io.*;
import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.Dialog;
import java.awt.Window;
import java.awt.Component;
import java.awt.Container;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.awt.event.WindowListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.awt.IllegalComponentStateException;
import java.awt.Point;
import java.awt.Rectangle;
import java.text.*;
import java.util.Locale;
import javax.accessibility.*;
import javax.swing.event.*;
import javax.swing.text.*;


/** A class to monitor the progress of some operation. If it looks
 * like the operation will take a while, a progress dialog will be popped up.
 * When the ProgressMonitor is created it is given a numeric range and a
 * descriptive string. As the operation progresses, call the setProgress method
 * to indicate how far along the [min,max] range the operation is.
 * Initially, there is no ProgressDialog. After the first millisToDecideToPopup
 * milliseconds (default 500) the progress monitor will predict how long
 * the operation will take.  If it is longer than millisToPopup (default 2000,
 * 2 seconds) a ProgressDialog will be popped up.
 * <p>
 * From time to time, when the Dialog box is visible, the progress bar will
 * be updated when setProgress is called.  setProgress won't always update
 * the progress bar, it will only be done if the amount of progress is
 * visibly significant.
 *
 * <p>
 *
 * For further documentation and examples see
 * <a
 href="https://docs.oracle.com/javase/tutorial/uiswing/components/progress.html">How to Monitor Progress</a>,
 * a section in <em>The Java Tutorial.</em>
 *
 * <p>
 *  喜欢的操作将需要一段时间,将弹出一个进度对话框。创建ProgressMonitor时,它会给出一个数字范围和一个描述性字符串。
 * 随着操作的进行,调用setProgress方法来指示操作的[min,max]范围有多远。最初,没有ProgressDialog。
 * 在第一个millisToDecideToPopup毫秒(默认500)后,进度监视器将预测操作将花费多长时间。
 * 如果它超过millisToPopup(默认2000,2秒),将弹出ProgressDialog。
 * <p>
 *  有时,当对话框可见时,进度条将在调用setProgress时更新。 setProgress不会总是更新进度条,它只会在进度数量明显显着时完成。
 * 
 * <p>
 * 
 *  有关其他文档和示例,请参阅<a href="https://docs.oracle.com/javase/tutorial/uiswing/components/progress.html">如何监控
 * 进度</a>,<em>中的一节。
 *  Java教程。</em>。
 * 
 * 
 * @see ProgressMonitorInputStream
 * @author James Gosling
 * @author Lynn Monsanto (accessibility)
 */
public class ProgressMonitor implements Accessible
{
    private ProgressMonitor root;
    private JDialog         dialog;
    private JOptionPane     pane;
    private JProgressBar    myBar;
    private JLabel          noteLabel;
    private Component       parentComponent;
    private String          note;
    private Object[]        cancelOption = null;
    private Object          message;
    private long            T0;
    private int             millisToDecideToPopup = 500;
    private int             millisToPopup = 2000;
    private int             min;
    private int             max;


    /**
     * Constructs a graphic object that shows progress, typically by filling
     * in a rectangular bar as the process nears completion.
     *
     * <p>
     *  构造一个显示进度的图形对象,通常通过在进程接近完成时填充到矩形栏中。
     * 
     * 
     * @param parentComponent the parent component for the dialog box
     * @param message a descriptive message that will be shown
     *        to the user to indicate what operation is being monitored.
     *        This does not change as the operation progresses.
     *        See the message parameters to methods in
     *        {@link JOptionPane#message}
     *        for the range of values.
     * @param note a short note describing the state of the
     *        operation.  As the operation progresses, you can call
     *        setNote to change the note displayed.  This is used,
     *        for example, in operations that iterate through a
     *        list of files to show the name of the file being processes.
     *        If note is initially null, there will be no note line
     *        in the dialog box and setNote will be ineffective
     * @param min the lower bound of the range
     * @param max the upper bound of the range
     * @see JDialog
     * @see JOptionPane
     */
    public ProgressMonitor(Component parentComponent,
                           Object message,
                           String note,
                           int min,
                           int max) {
        this(parentComponent, message, note, min, max, null);
    }


    private ProgressMonitor(Component parentComponent,
                            Object message,
                            String note,
                            int min,
                            int max,
                            ProgressMonitor group) {
        this.min = min;
        this.max = max;
        this.parentComponent = parentComponent;

        cancelOption = new Object[1];
        cancelOption[0] = UIManager.getString("OptionPane.cancelButtonText");

        this.message = message;
        this.note = note;
        if (group != null) {
            root = (group.root != null) ? group.root : group;
            T0 = root.T0;
            dialog = root.dialog;
        }
        else {
            T0 = System.currentTimeMillis();
        }
    }


    private class ProgressOptionPane extends JOptionPane
    {
        ProgressOptionPane(Object messageList) {
            super(messageList,
                  JOptionPane.INFORMATION_MESSAGE,
                  JOptionPane.DEFAULT_OPTION,
                  null,
                  ProgressMonitor.this.cancelOption,
                  null);
        }


        public int getMaxCharactersPerLineCount() {
            return 60;
        }


        // Equivalent to JOptionPane.createDialog,
        // but create a modeless dialog.
        // This is necessary because the Solaris implementation doesn't
        // support Dialog.setModal yet.
        public JDialog createDialog(Component parentComponent, String title) {
            final JDialog dialog;

            Window window = JOptionPane.getWindowForComponent(parentComponent);
            if (window instanceof Frame) {
                dialog = new JDialog((Frame)window, title, false);
            } else {
                dialog = new JDialog((Dialog)window, title, false);
            }
            if (window instanceof SwingUtilities.SharedOwnerFrame) {
                WindowListener ownerShutdownListener =
                        SwingUtilities.getSharedOwnerFrameShutdownListener();
                dialog.addWindowListener(ownerShutdownListener);
            }
            Container contentPane = dialog.getContentPane();

            contentPane.setLayout(new BorderLayout());
            contentPane.add(this, BorderLayout.CENTER);
            dialog.pack();
            dialog.setLocationRelativeTo(parentComponent);
            dialog.addWindowListener(new WindowAdapter() {
                boolean gotFocus = false;

                public void windowClosing(WindowEvent we) {
                    setValue(cancelOption[0]);
                }

                public void windowActivated(WindowEvent we) {
                    // Once window gets focus, set initial focus
                    if (!gotFocus) {
                        selectInitialValue();
                        gotFocus = true;
                    }
                }
            });

            addPropertyChangeListener(new PropertyChangeListener() {
                public void propertyChange(PropertyChangeEvent event) {
                    if(dialog.isVisible() &&
                       event.getSource() == ProgressOptionPane.this &&
                       (event.getPropertyName().equals(VALUE_PROPERTY) ||
                        event.getPropertyName().equals(INPUT_VALUE_PROPERTY))){
                        dialog.setVisible(false);
                        dialog.dispose();
                    }
                }
            });

            return dialog;
        }

        /////////////////
        // Accessibility support for ProgressOptionPane
        ////////////////

        /**
         * Gets the AccessibleContext for the ProgressOptionPane
         *
         * <p>
         *  获取ProgressOptionPane的AccessibleContext
         * 
         * 
         * @return the AccessibleContext for the ProgressOptionPane
         * @since 1.5
         */
        public AccessibleContext getAccessibleContext() {
            return ProgressMonitor.this.getAccessibleContext();
        }

        /*
         * Returns the AccessibleJOptionPane
         * <p>
         *  返回AccessibleJOptionPane
         * 
         */
        private AccessibleContext getAccessibleJOptionPane() {
            return super.getAccessibleContext();
        }
    }


    /**
     * Indicate the progress of the operation being monitored.
     * If the specified value is &gt;= the maximum, the progress
     * monitor is closed.
     * <p>
     *  指示正在监视的操作的进度。如果指定的值>&gt; =最大值,进度监视器将关闭。
     * 
     * 
     * @param nv an int specifying the current value, between the
     *        maximum and minimum specified for this component
     * @see #setMinimum
     * @see #setMaximum
     * @see #close
     */
    public void setProgress(int nv) {
        if (nv >= max) {
            close();
        }
        else {
            if (myBar != null) {
                myBar.setValue(nv);
            }
            else {
                long T = System.currentTimeMillis();
                long dT = (int)(T-T0);
                if (dT >= millisToDecideToPopup) {
                    int predictedCompletionTime;
                    if (nv > min) {
                        predictedCompletionTime = (int)(dT *
                                                        (max - min) /
                                                        (nv - min));
                    }
                    else {
                        predictedCompletionTime = millisToPopup;
                    }
                    if (predictedCompletionTime >= millisToPopup) {
                        myBar = new JProgressBar();
                        myBar.setMinimum(min);
                        myBar.setMaximum(max);
                        myBar.setValue(nv);
                        if (note != null) noteLabel = new JLabel(note);
                        pane = new ProgressOptionPane(new Object[] {message,
                                                                    noteLabel,
                                                                    myBar});
                        dialog = pane.createDialog(parentComponent,
                            UIManager.getString(
                                "ProgressMonitor.progressText"));
                        dialog.show();
                    }
                }
            }
        }
    }


    /**
     * Indicate that the operation is complete.  This happens automatically
     * when the value set by setProgress is &gt;= max, but it may be called
     * earlier if the operation ends early.
     * <p>
     * 指示操作完成。当由setProgress设置的值是&gt; = max时,会自动发生这种情况,但如果操作提前结束,它可能会被更早调用。
     * 
     */
    public void close() {
        if (dialog != null) {
            dialog.setVisible(false);
            dialog.dispose();
            dialog = null;
            pane = null;
            myBar = null;
        }
    }


    /**
     * Returns the minimum value -- the lower end of the progress value.
     *
     * <p>
     *  返回最小值 - 进度值的下限。
     * 
     * 
     * @return an int representing the minimum value
     * @see #setMinimum
     */
    public int getMinimum() {
        return min;
    }


    /**
     * Specifies the minimum value.
     *
     * <p>
     *  指定最小值。
     * 
     * 
     * @param m  an int specifying the minimum value
     * @see #getMinimum
     */
    public void setMinimum(int m) {
        if (myBar != null) {
            myBar.setMinimum(m);
        }
        min = m;
    }


    /**
     * Returns the maximum value -- the higher end of the progress value.
     *
     * <p>
     *  返回最大值 - 进度值的上限。
     * 
     * 
     * @return an int representing the maximum value
     * @see #setMaximum
     */
    public int getMaximum() {
        return max;
    }


    /**
     * Specifies the maximum value.
     *
     * <p>
     *  指定最大值。
     * 
     * 
     * @param m  an int specifying the maximum value
     * @see #getMaximum
     */
    public void setMaximum(int m) {
        if (myBar != null) {
            myBar.setMaximum(m);
        }
        max = m;
    }


    /**
     * Returns true if the user hits the Cancel button in the progress dialog.
     * <p>
     *  如果用户在进度对话框中点击取消按钮,则返回true。
     * 
     */
    public boolean isCanceled() {
        if (pane == null) return false;
        Object v = pane.getValue();
        return ((v != null) &&
                (cancelOption.length == 1) &&
                (v.equals(cancelOption[0])));
    }


    /**
     * Specifies the amount of time to wait before deciding whether or
     * not to popup a progress monitor.
     *
     * <p>
     *  指定在决定是否弹出进度监视器之前等待的时间量。
     * 
     * 
     * @param millisToDecideToPopup  an int specifying the time to wait,
     *        in milliseconds
     * @see #getMillisToDecideToPopup
     */
    public void setMillisToDecideToPopup(int millisToDecideToPopup) {
        this.millisToDecideToPopup = millisToDecideToPopup;
    }


    /**
     * Returns the amount of time this object waits before deciding whether
     * or not to popup a progress monitor.
     *
     * <p>
     *  返回此对象在决定是否弹出进度监视器之前等待的时间量。
     * 
     * 
     * @see #setMillisToDecideToPopup
     */
    public int getMillisToDecideToPopup() {
        return millisToDecideToPopup;
    }


    /**
     * Specifies the amount of time it will take for the popup to appear.
     * (If the predicted time remaining is less than this time, the popup
     * won't be displayed.)
     *
     * <p>
     *  指定弹出窗口显示所需的时间。 (如果预测剩余时间少于此时间,则不会显示弹出窗口。)
     * 
     * 
     * @param millisToPopup  an int specifying the time in milliseconds
     * @see #getMillisToPopup
     */
    public void setMillisToPopup(int millisToPopup) {
        this.millisToPopup = millisToPopup;
    }


    /**
     * Returns the amount of time it will take for the popup to appear.
     *
     * <p>
     *  返回弹出窗口显示所需的时间。
     * 
     * 
     * @see #setMillisToPopup
     */
    public int getMillisToPopup() {
        return millisToPopup;
    }


    /**
     * Specifies the additional note that is displayed along with the
     * progress message. Used, for example, to show which file the
     * is currently being copied during a multiple-file copy.
     *
     * <p>
     *  指定与进度消息一起显示的附加注释。例如,用于显示在多文件复制期间当前正在复制的文件。
     * 
     * 
     * @param note  a String specifying the note to display
     * @see #getNote
     */
    public void setNote(String note) {
        this.note = note;
        if (noteLabel != null) {
            noteLabel.setText(note);
        }
    }


    /**
     * Specifies the additional note that is displayed along with the
     * progress message.
     *
     * <p>
     *  指定与进度消息一起显示的附加注释。
     * 
     * 
     * @return a String specifying the note to display
     * @see #setNote
     */
    public String getNote() {
        return note;
    }

    /////////////////
    // Accessibility support
    ////////////////

    /**
     * The <code>AccessibleContext</code> for the <code>ProgressMonitor</code>
     * <p>
     *  <code> ProgressMonitor </code>的<code> AccessibleContext </code>
     * 
     * 
     * @since 1.5
     */
    protected AccessibleContext accessibleContext = null;

    private AccessibleContext accessibleJOptionPane = null;

    /**
     * Gets the <code>AccessibleContext</code> for the
     * <code>ProgressMonitor</code>
     *
     * <p>
     *  获取<code> ProgressMonitor </code>的<code> AccessibleContext </code>
     * 
     * 
     * @return the <code>AccessibleContext</code> for the
     * <code>ProgressMonitor</code>
     * @since 1.5
     */
    public AccessibleContext getAccessibleContext() {
        if (accessibleContext == null) {
            accessibleContext = new AccessibleProgressMonitor();
        }
        if (pane != null && accessibleJOptionPane == null) {
            // Notify the AccessibleProgressMonitor that the
            // ProgressOptionPane was created. It is necessary
            // to poll for ProgressOptionPane creation because
            // the ProgressMonitor does not have a Component
            // to add a listener to until the ProgressOptionPane
            // is created.
            if (accessibleContext instanceof AccessibleProgressMonitor) {
                ((AccessibleProgressMonitor)accessibleContext).optionPaneCreated();
            }
        }
        return accessibleContext;
    }

    /**
     * <code>AccessibleProgressMonitor</code> implements accessibility
     * support for the <code>ProgressMonitor</code> class.
     * <p>
     *  <code> AccessibleProgressMonitor </code>实现<code> ProgressMonitor </code>类的辅助功能支持。
     * 
     * 
     * @since 1.5
     */
    protected class AccessibleProgressMonitor extends AccessibleContext
        implements AccessibleText, ChangeListener, PropertyChangeListener {

        /*
         * The accessibility hierarchy for ProgressMonitor is a flattened
         * version of the ProgressOptionPane component hierarchy.
         *
         * The ProgressOptionPane component hierarchy is:
         *   JDialog
         *     ProgressOptionPane
         *       JPanel
         *         JPanel
         *           JLabel
         *           JLabel
         *           JProgressBar
         *
         * The AccessibleProgessMonitor accessibility hierarchy is:
         *   AccessibleJDialog
         *     AccessibleProgressMonitor
         *       AccessibleJLabel
         *       AccessibleJLabel
         *       AccessibleJProgressBar
         *
         * The abstraction presented to assitive technologies by
         * the AccessibleProgressMonitor is that a dialog contains a
         * progress monitor with three children: a message, a note
         * label and a progress bar.
         * <p>
         * ProgressMonitor的辅助层次结构是ProgressOptionPane组件层次结构的展平版本。
         * 
         *  ProgressOptionPane组件层次结构是：JDialog ProgressOptionPane JPanel JPanel JLabel JLabel JProgressBar
         * 
         *  AccessibleProgessMonitor可访问性层次结构是：AccessibleJDialog AccessibleProgressMonitor AccessibleJLabel Acces
         * sibleJLabel AccessibleJProgressBar。
         * 
         *  AccessibleProgressMonitor对抽象技术的抽象是一个对话框包含一个进度监视器,有三个子节点：消息,注释标签和进度条。
         * 
         */

        private Object oldModelValue;

        /**
         * AccessibleProgressMonitor constructor
         * <p>
         *  AccessibleProgressMonitor构造函数
         * 
         */
        protected AccessibleProgressMonitor() {
        }

        /*
         * Initializes the AccessibleContext now that the ProgressOptionPane
         * has been created. Because the ProgressMonitor is not a Component
         * implementing the Accessible interface, an AccessibleContext
         * must be synthesized from the ProgressOptionPane and its children.
         *
         * For other AWT and Swing classes, the inner class that implements
         * accessibility for the class extends the inner class that implements
         * implements accessibility for the super class. AccessibleProgressMonitor
         * cannot extend AccessibleJOptionPane and must therefore delegate calls
         * to the AccessibleJOptionPane.
         * <p>
         *  初始化AccessibleContext现在已创建ProgressOptionPane。
         * 因为ProgressMonitor不是实现Accessible接口的组件,AccessibleContext必须从ProgressOptionPane及其子节点合成。
         * 
         *  对于其他AWT和Swing类,实现类的可访问性的内部类扩展了实现对超类的实现可访问性的内部类。
         *  AccessibleProgressMonitor无法扩展AccessibleJOptionPane,因此必须将调用委派给AccessibleJOptionPane。
         * 
         */
        private void optionPaneCreated() {
            accessibleJOptionPane =
                ((ProgressOptionPane)pane).getAccessibleJOptionPane();

            // add a listener for progress bar ChangeEvents
            if (myBar != null) {
                myBar.addChangeListener(this);
            }

            // add a listener for note label PropertyChangeEvents
            if (noteLabel != null) {
                noteLabel.addPropertyChangeListener(this);
            }
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
                return;
            }
            if (myBar != null) {
                // the progress bar value changed
                Object newModelValue = myBar.getValue();
                firePropertyChange(ACCESSIBLE_VALUE_PROPERTY,
                                   oldModelValue,
                                   newModelValue);
                oldModelValue = newModelValue;
            }
        }

        /**
         * This method gets called when a bound property is changed.
         *
         * <p>
         *  当绑定属性更改时,将调用此方法。
         * 
         * 
         * @param e A <code>PropertyChangeEvent</code> object describing
         * the event source and the property that has changed. Must not be null.
         * @throws NullPointerException if the parameter is null.
         */
        public void propertyChange(PropertyChangeEvent e) {
            if (e.getSource() == noteLabel && e.getPropertyName() == "text") {
                // the note label text changed
                firePropertyChange(ACCESSIBLE_TEXT_PROPERTY, null, 0);
            }
        }

        /* ===== Begin AccessileContext ===== */

        /**
         * Gets the accessibleName property of this object.  The accessibleName
         * property of an object is a localized String that designates the purpose
         * of the object.  For example, the accessibleName property of a label
         * or button might be the text of the label or button itself.  In the
         * case of an object that doesn't display its name, the accessibleName
         * should still be set.  For example, in the case of a text field used
         * to enter the name of a city, the accessibleName for the en_US locale
         * could be 'city.'
         *
         * <p>
         * 获取此对象的accessibleName属性。对象的accessibleName属性是一个本地化的字符串,指定对象的用途。例如,标签或按钮的accessibleName属性可以是标签或按钮本身的文本。
         * 在对象不显示其名称的情况下,仍应设置accessibleName。例如,在用于输入城市名称的文本字段的情况下,en_US语言环境的accessibleName可以是"城市"。
         * 
         * 
         * @return the localized name of the object; null if this
         * object does not have a name
         *
         * @see #setAccessibleName
         */
        public String getAccessibleName() {
            if (accessibleName != null) { // defined in AccessibleContext
                return accessibleName;
            } else if (accessibleJOptionPane != null) {
                // delegate to the AccessibleJOptionPane
                return accessibleJOptionPane.getAccessibleName();
            }
            return null;
        }

        /**
         * Gets the accessibleDescription property of this object.  The
         * accessibleDescription property of this object is a short localized
         * phrase describing the purpose of the object.  For example, in the
         * case of a 'Cancel' button, the accessibleDescription could be
         * 'Ignore changes and close dialog box.'
         *
         * <p>
         *  获取此对象的accessibleDescription属性。此对象的accessibleDescription属性是一个简短的本地化短语,用于描述对象的用途。
         * 例如,在"取消"按钮的情况下,accessibleDescription可以是"忽略更改并关闭对话框"。
         * 
         * 
         * @return the localized description of the object; null if
         * this object does not have a description
         *
         * @see #setAccessibleDescription
         */
        public String getAccessibleDescription() {
            if (accessibleDescription != null) { // defined in AccessibleContext
                return accessibleDescription;
            } else if (accessibleJOptionPane != null) {
                // delegate to the AccessibleJOptionPane
                return accessibleJOptionPane.getAccessibleDescription();
            }
            return null;
        }

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
         * 获取此对象的作用。对象的作用是该对象的类的通用目的或使用。例如,按钮的角色是AccessibleRole.PUSH_BUTTON。
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
            return AccessibleRole.PROGRESS_MONITOR;
        }

        /**
         * Gets the state set of this object.  The AccessibleStateSet of an object
         * is composed of a set of unique AccessibleStates.  A change in the
         * AccessibleStateSet of an object will cause a PropertyChangeEvent to
         * be fired for the ACCESSIBLE_STATE_PROPERTY property.
         *
         * <p>
         *  获取此对象的状态集。对象的AccessibleStateSet由一组唯一的AccessibleStates组成。
         * 对象的AccessibleStateSet中的更改将导致针对ACCESSIBLE_STATE_PROPERTY属性触发PropertyChangeEvent。
         * 
         * 
         * @return an instance of AccessibleStateSet containing the
         * current state set of the object
         * @see AccessibleStateSet
         * @see AccessibleState
         * @see #addPropertyChangeListener
         */
        public AccessibleStateSet getAccessibleStateSet() {
            if (accessibleJOptionPane != null) {
                // delegate to the AccessibleJOptionPane
                return accessibleJOptionPane.getAccessibleStateSet();
            }
            return null;
        }

        /**
         * Gets the Accessible parent of this object.
         *
         * <p>
         *  获取此对象的可访问父级。
         * 
         * 
         * @return the Accessible parent of this object; null if this
         * object does not have an Accessible parent
         */
        public Accessible getAccessibleParent() {
            return dialog;
        }

        /*
         * Returns the parent AccessibleContext
         * <p>
         *  返回父AccessibleContext
         * 
         */
        private AccessibleContext getParentAccessibleContext() {
            if (dialog != null) {
                return dialog.getAccessibleContext();
            }
            return null;
        }

        /**
         * Gets the 0-based index of this object in its accessible parent.
         *
         * <p>
         *  获取此对象在其可访问父级中的基于0的索引。
         * 
         * 
         * @return the 0-based index of this object in its parent; -1 if this
         * object does not have an accessible parent.
         *
         * @see #getAccessibleParent
         * @see #getAccessibleChildrenCount
         * @see #getAccessibleChild
         */
        public int getAccessibleIndexInParent() {
            if (accessibleJOptionPane != null) {
                // delegate to the AccessibleJOptionPane
                return accessibleJOptionPane.getAccessibleIndexInParent();
            }
            return -1;
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
            // return the number of children in the JPanel containing
            // the message, note label and progress bar
            AccessibleContext ac = getPanelAccessibleContext();
            if (ac != null) {
                return ac.getAccessibleChildrenCount();
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
         * 返回对象的指定Accessible子项。可访问对象的可访问子对象是基于零的,因此可访问子对象的第一个子对象位于索引0,第二个子对象位于索引1,依此类推。
         * 
         * 
         * @param i zero-based index of child
         * @return the Accessible child of the object
         * @see #getAccessibleChildrenCount
         */
        public Accessible getAccessibleChild(int i) {
            // return a child in the JPanel containing the message, note label
            // and progress bar
            AccessibleContext ac = getPanelAccessibleContext();
            if (ac != null) {
                return ac.getAccessibleChild(i);
            }
            return null;
        }

        /*
         * Returns the AccessibleContext for the JPanel containing the
         * message, note label and progress bar
         * <p>
         *  返回包含消息,注释标签和进度条的JPanel的AccessibleContext
         * 
         */
        private AccessibleContext getPanelAccessibleContext() {
            if (myBar != null) {
                Component c = myBar.getParent();
                if (c instanceof Accessible) {
                    return c.getAccessibleContext();
                }
            }
            return null;
        }

        /**
         * Gets the locale of the component. If the component does not have a
         * locale, then the locale of its parent is returned.
         *
         * <p>
         *  获取组件的语言环境。如果组件没有语言环境,那么将返回其父组件的语言环境。
         * 
         * 
         * @return this component's locale.  If this component does not have
         * a locale, the locale of its parent is returned.
         *
         * @exception IllegalComponentStateException
         * If the Component does not have its own locale and has not yet been
         * added to a containment hierarchy such that the locale can be
         * determined from the containing parent.
         */
        public Locale getLocale() throws IllegalComponentStateException {
            if (accessibleJOptionPane != null) {
                // delegate to the AccessibleJOptionPane
                return accessibleJOptionPane.getLocale();
            }
            return null;
        }

        /* ===== end AccessibleContext ===== */

        /**
         * Gets the AccessibleComponent associated with this object that has a
         * graphical representation.
         *
         * <p>
         *  获取与具有图形表示形式的此对象相关联的AccessibleComponent。
         * 
         * 
         * @return AccessibleComponent if supported by object; else return null
         * @see AccessibleComponent
         */
        public AccessibleComponent getAccessibleComponent() {
            if (accessibleJOptionPane != null) {
                // delegate to the AccessibleJOptionPane
                return accessibleJOptionPane.getAccessibleComponent();
            }
            return null;
        }

        /**
         * Gets the AccessibleValue associated with this object that supports a
         * Numerical value.
         *
         * <p>
         *  获取与支持数值的此对象相关联的AccessibleValue。
         * 
         * 
         * @return AccessibleValue if supported by object; else return null
         * @see AccessibleValue
         */
        public AccessibleValue getAccessibleValue() {
            if (myBar != null) {
                // delegate to the AccessibleJProgressBar
                return myBar.getAccessibleContext().getAccessibleValue();
            }
            return null;
        }

        /**
         * Gets the AccessibleText associated with this object presenting
         * text on the display.
         *
         * <p>
         *  获取与此对象相关联的AccessibleText在显示器上显示文本。
         * 
         * 
         * @return AccessibleText if supported by object; else return null
         * @see AccessibleText
         */
        public AccessibleText getAccessibleText() {
            if (getNoteLabelAccessibleText() != null) {
                return this;
            }
            return null;
        }

        /*
         * Returns the note label AccessibleText
         * <p>
         *  返回注释标签AccessibleText
         * 
         */
        private AccessibleText getNoteLabelAccessibleText() {
            if (noteLabel != null) {
                // AccessibleJLabel implements AccessibleText if the
                // JLabel contains HTML text
                return noteLabel.getAccessibleContext().getAccessibleText();
            }
            return null;
        }

        /* ===== Begin AccessibleText impl ===== */

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
            AccessibleText at = getNoteLabelAccessibleText();
            if (at != null && sameWindowAncestor(pane, noteLabel)) {
                // convert point from the option pane bounds
                // to the note label bounds.
                Point noteLabelPoint = SwingUtilities.convertPoint(pane,
                                                                   p,
                                                                   noteLabel);
                if (noteLabelPoint != null) {
                    return at.getIndexAtPoint(noteLabelPoint);
                }
            }
            return -1;
        }

        /**
         * Determines the bounding box of the character at the given
         * index into the string.  The bounds are returned in local
         * coordinates.  If the index is invalid an empty rectangle is returned.
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
            AccessibleText at = getNoteLabelAccessibleText();
            if (at != null && sameWindowAncestor(pane, noteLabel)) {
                // return rectangle in the option pane bounds
                Rectangle noteLabelRect = at.getCharacterBounds(i);
                if (noteLabelRect != null) {
                    return SwingUtilities.convertRectangle(noteLabel,
                                                           noteLabelRect,
                                                           pane);
                }
            }
            return null;
        }

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
         * Returns the number of characters (valid indicies)
         *
         * <p>
         *  返回字符数(有效的标记)
         * 
         * 
         * @return the number of characters
         */
        public int getCharCount() {
            AccessibleText at = getNoteLabelAccessibleText();
            if (at != null) {   // JLabel contains HTML text
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
         *  注意：插入符右侧的索引值与偏移量相同(插入符号在两个字符之间)。
         * 
         * 
         * @return the zero-based offset of the caret.
         */
        public int getCaretPosition() {
            AccessibleText at = getNoteLabelAccessibleText();
            if (at != null) {   // JLabel contains HTML text
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
            AccessibleText at = getNoteLabelAccessibleText();
            if (at != null) {   // JLabel contains HTML text
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
            AccessibleText at = getNoteLabelAccessibleText();
            if (at != null) {   // JLabel contains HTML text
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
            AccessibleText at = getNoteLabelAccessibleText();
            if (at != null) {   // JLabel contains HTML text
                return at.getBeforeIndex(part, index);
            }
            return null;
        }

        /**
         * Returns the AttributeSet for a given character at a given index
         *
         * <p>
         * 返回给定字符在给定索引的AttributeSet
         * 
         * 
         * @param i the zero-based index into the text
         * @return the AttributeSet of the character
         */
        public AttributeSet getCharacterAttribute(int i) {
            AccessibleText at = getNoteLabelAccessibleText();
            if (at != null) {   // JLabel contains HTML text
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
            AccessibleText at = getNoteLabelAccessibleText();
            if (at != null) {   // JLabel contains HTML text
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
            AccessibleText at = getNoteLabelAccessibleText();
            if (at != null) {   // JLabel contains HTML text
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
         * @return the String portion of the text that is selected
         */
        public String getSelectedText() {
            AccessibleText at = getNoteLabelAccessibleText();
            if (at != null) {   // JLabel contains HTML text
                return at.getSelectedText();
            }
            return null;
        }
        /* ===== End AccessibleText impl ===== */
    }
    // inner class AccessibleProgressMonitor

}
