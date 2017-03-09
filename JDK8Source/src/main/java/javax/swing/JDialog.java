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

import java.awt.*;
import java.awt.event.*;
import javax.accessibility.*;

/**
 * The main class for creating a dialog window. You can use this class
 * to create a custom dialog, or invoke the many class methods
 * in {@link JOptionPane} to create a variety of standard dialogs.
 * For information about creating dialogs, see
 * <em>The Java Tutorial</em> section
 * <a
 href="https://docs.oracle.com/javase/tutorial/uiswing/components/dialog.html">How
 * to Make Dialogs</a>.
 *
 * <p>
 *
 * The {@code JDialog} component contains a {@code JRootPane}
 * as its only child.
 * The {@code contentPane} should be the parent of any children of the
 * {@code JDialog}.
 * As a convenience, the {@code add}, {@code remove}, and {@code setLayout}
 * methods of this class are overridden, so that they delegate calls
 * to the corresponding methods of the {@code ContentPane}.
 * For example, you can add a child component to a dialog as follows:
 * <pre>
 *       dialog.add(child);
 * </pre>
 * And the child will be added to the contentPane.
 * The {@code contentPane} is always non-{@code null}.
 * Attempting to set it to {@code null} generates an exception.
 * The default {@code contentPane} has a {@code BorderLayout}
 * manager set on it.
 * Refer to {@link javax.swing.RootPaneContainer}
 * for details on adding, removing and setting the {@code LayoutManager}
 * of a {@code JDialog}.
 * <p>
 * Please see the {@code JRootPane} documentation for a complete
 * description of the {@code contentPane}, {@code glassPane},
 * and {@code layeredPane} components.
 * <p>
 * In a multi-screen environment, you can create a {@code JDialog}
 * on a different screen device than its owner.  See {@link java.awt.Frame} for
 * more information.
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
 * has been added to the {@code java.beans} package.
 * Please see {@link java.beans.XMLEncoder}.
 *
 * <p>
 *  创建对话框窗口的主类。您可以使用此类来创建自定义对话框,或者调用{@link JOptionPane}中的许多类方法来创建各种标准对话框。
 * 有关创建对话框的信息,请参阅<em> Java教程</em>部分<a href="https://docs.oracle.com/javase/tutorial/uiswing/components/dialog.html">
 * 如何创建对话框</a>。
 *  创建对话框窗口的主类。您可以使用此类来创建自定义对话框,或者调用{@link JOptionPane}中的许多类方法来创建各种标准对话框。
 * 
 * <p>
 * 
 *  {@code JDialog}组件包含一个{@code JRootPane}作为其唯一的子级。 {@code contentPane}应为{@code JDialog}的所有子级的父级。
 * 为方便起见,此类的{@code add},{@code remove}和{@code setLayout}方法被覆盖,因此它们将调用委派给{@code ContentPane}的相应方法。
 * 例如,您可以将子组件添加到对话框,如下所示：。
 * <pre>
 *  dialog.add(child);
 * </pre>
 *  并且孩子将被添加到contentPane。 {@code contentPane}始终为非 -  {@ code null}。尝试将其设置为{@code null}会生成异常。
 * 默认的{@code contentPane}设置了一个{@code BorderLayout}管理器。
 * 有关添加,删除和设置{@code JDialog}的{@code LayoutManager}的详细信息,请参阅{@link javax.swing.RootPaneContainer}。
 * <p>
 * 有关{@code contentPane},{@code glassPane}和{@code layeredPane}组件的完整说明,请参阅{@code JRootPane}文档。
 * <p>
 *  在多萤幕环境中,您可以在与其拥有者不同的萤幕装置上建立{@code JDialog}。有关详细信息,请参阅{@link java.awt.Frame}。
 * <p>
 *  <strong>警告：</strong> Swing不是线程安全的。有关详情,请参阅<a href="package-summary.html#threading"> Swing的线程策略</a>。
 * <p>
 *  <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
 *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到{@code java.beans}包中。请参阅{@link java.beans.XMLEncoder}。
 * 
 * 
 * @see JOptionPane
 * @see JRootPane
 * @see javax.swing.RootPaneContainer
 *
 * @beaninfo
 *      attribute: isContainer true
 *      attribute: containerDelegate getContentPane
 *    description: A toplevel window for creating dialog boxes.
 *
 * @author David Kloba
 * @author James Gosling
 * @author Scott Violet
 */
public class JDialog extends Dialog implements WindowConstants,
                                               Accessible,
                                               RootPaneContainer,
                               TransferHandler.HasGetTransferHandler
{
    /**
     * Key into the AppContext, used to check if should provide decorations
     * by default.
     * <p>
     *  Key进入AppContext,用于检查是否应该默认提供装饰。
     * 
     */
    private static final Object defaultLookAndFeelDecoratedKey =
            new StringBuffer("JDialog.defaultLookAndFeelDecorated");

    private int defaultCloseOperation = HIDE_ON_CLOSE;

    /**
    /* <p>
    /* 
     * @see #getRootPane
     * @see #setRootPane
     */
    protected JRootPane rootPane;

    /**
     * If true then calls to {@code add} and {@code setLayout}
     * will be forwarded to the {@code contentPane}. This is initially
     * false, but is set to true when the {@code JDialog} is constructed.
     *
     * <p>
     *  如果为true,则对{@code add}和{@code setLayout}的调用将转发到{@code contentPane}。
     * 这最初是false,但是在构建{@code JDialog}时设置为true。
     * 
     * 
     * @see #isRootPaneCheckingEnabled
     * @see #setRootPaneCheckingEnabled
     * @see javax.swing.RootPaneContainer
     */
    protected boolean rootPaneCheckingEnabled = false;

    /**
     * The {@code TransferHandler} for this dialog.
     * <p>
     *  此对话框的{@code TransferHandler}。
     * 
     */
    private TransferHandler transferHandler;

    /**
     * Creates a modeless dialog without a title and without a specified
     * {@code Frame} owner.  A shared, hidden frame will be
     * set as the owner of the dialog.
     * <p>
     * This constructor sets the component's locale property to the value
     * returned by {@code JComponent.getDefaultLocale}.
     * <p>
     * NOTE: This constructor does not allow you to create an unowned
     * {@code JDialog}. To create an unowned {@code JDialog}
     * you must use either the {@code JDialog(Window)} or
     * {@code JDialog(Dialog)} constructor with an argument of
     * {@code null}.
     *
     * <p>
     *  创建一个无标题且没有指定{@code Frame}所有者的无模式对话框。共享的隐藏框架将设置为对话框的所有者。
     * <p>
     *  此构造函数将组件的locale属性设置为{@code JComponent.getDefaultLocale}返回的值。
     * <p>
     * 注意：此构造函数不允许创建无主{@code JDialog}。
     * 要创建无主{@code JDialog},您必须使用{@code JDialog(Window)}或{@code JDialog(Dialog)}构造函数,参数为{@code null}。
     * 
     * 
     * @throws HeadlessException if {@code GraphicsEnvironment.isHeadless()}
     *     returns {@code true}.
     * @see java.awt.GraphicsEnvironment#isHeadless
     * @see JComponent#getDefaultLocale
     */
    public JDialog() {
        this((Frame)null, false);
    }

    /**
     * Creates a modeless dialog with the specified {@code Frame}
     * as its owner and an empty title. If {@code owner}
     * is {@code null}, a shared, hidden frame will be set as the
     * owner of the dialog.
     * <p>
     * This constructor sets the component's locale property to the value
     * returned by {@code JComponent.getDefaultLocale}.
     * <p>
     * NOTE: This constructor does not allow you to create an unowned
     * {@code JDialog}. To create an unowned {@code JDialog}
     * you must use either the {@code JDialog(Window)} or
     * {@code JDialog(Dialog)} constructor with an argument of
     * {@code null}.
     *
     * <p>
     *  创建一个以指定的{@code Frame}作为其所有者和空标题的无模式对话框。如果{@code owner}为{@code null},则共享的隐藏框架将设置为对话框的所有者。
     * <p>
     *  此构造函数将组件的locale属性设置为{@code JComponent.getDefaultLocale}返回的值。
     * <p>
     *  注意：此构造函数不允许创建无主{@code JDialog}。
     * 要创建无主{@code JDialog},您必须使用{@code JDialog(Window)}或{@code JDialog(Dialog)}构造函数,参数为{@code null}。
     * 
     * 
     * @param owner the {@code Frame} from which the dialog is displayed
     * @throws HeadlessException if {@code GraphicsEnvironment.isHeadless()}
     *     returns {@code true}.
     * @see java.awt.GraphicsEnvironment#isHeadless
     * @see JComponent#getDefaultLocale
     */
    public JDialog(Frame owner) {
        this(owner, false);
    }

    /**
     * Creates a dialog with an empty title and the specified modality and
     * {@code Frame} as its owner. If {@code owner} is {@code null},
     * a shared, hidden frame will be set as the owner of the dialog.
     * <p>
     * This constructor sets the component's locale property to the value
     * returned by {@code JComponent.getDefaultLocale}.
     * <p>
     * NOTE: This constructor does not allow you to create an unowned
     * {@code JDialog}. To create an unowned {@code JDialog}
     * you must use either the {@code JDialog(Window)} or
     * {@code JDialog(Dialog)} constructor with an argument of
     * {@code null}.
     *
     * <p>
     *  创建具有空标题和指定模态的对话框,并使用{@code Frame}作为其所有者。如果{@code owner}为{@code null},则共享的隐藏框架将设置为对话框的所有者。
     * <p>
     *  此构造函数将组件的locale属性设置为{@code JComponent.getDefaultLocale}返回的值。
     * <p>
     *  注意：此构造函数不允许创建无主{@code JDialog}。
     * 要创建无主{@code JDialog},您必须使用{@code JDialog(Window)}或{@code JDialog(Dialog)}构造函数,参数为{@code null}。
     * 
     * 
     * @param owner the {@code Frame} from which the dialog is displayed
     * @param modal specifies whether dialog blocks user input to other top-level
     *     windows when shown. If {@code true}, the modality type property is set to
     *     {@code DEFAULT_MODALITY_TYPE}, otherwise the dialog is modeless.
     * @throws HeadlessException if {@code GraphicsEnvironment.isHeadless()}
     *     returns {@code true}.
     * @see java.awt.GraphicsEnvironment#isHeadless
     * @see JComponent#getDefaultLocale
     */
    public JDialog(Frame owner, boolean modal) {
        this(owner, "", modal);
    }

    /**
     * Creates a modeless dialog with the specified title and
     * with the specified owner frame.  If {@code owner}
     * is {@code null}, a shared, hidden frame will be set as the
     * owner of the dialog.
     * <p>
     * This constructor sets the component's locale property to the value
     * returned by {@code JComponent.getDefaultLocale}.
     * <p>
     * NOTE: This constructor does not allow you to create an unowned
     * {@code JDialog}. To create an unowned {@code JDialog}
     * you must use either the {@code JDialog(Window)} or
     * {@code JDialog(Dialog)} constructor with an argument of
     * {@code null}.
     *
     * <p>
     * 创建具有指定标题和指定所有者框架的非模态对话框。如果{@code owner}为{@code null},则共享的隐藏框架将设置为对话框的所有者。
     * <p>
     *  此构造函数将组件的locale属性设置为{@code JComponent.getDefaultLocale}返回的值。
     * <p>
     *  注意：此构造函数不允许创建无主{@code JDialog}。
     * 要创建无主{@code JDialog},您必须使用{@code JDialog(Window)}或{@code JDialog(Dialog)}构造函数,参数为{@code null}。
     * 
     * 
     * @param owner the {@code Frame} from which the dialog is displayed
     * @param title  the {@code String} to display in the dialog's
     *                  title bar
     * @throws HeadlessException if {@code GraphicsEnvironment.isHeadless()}
     *     returns {@code true}.
     * @see java.awt.GraphicsEnvironment#isHeadless
     * @see JComponent#getDefaultLocale
     */
    public JDialog(Frame owner, String title) {
        this(owner, title, false);
    }

    /**
     * Creates a dialog with the specified title, owner {@code Frame}
     * and modality. If {@code owner} is {@code null},
     * a shared, hidden frame will be set as the owner of this dialog.
     * <p>
     * This constructor sets the component's locale property to the value
     * returned by {@code JComponent.getDefaultLocale}.
     * <p>
     * NOTE: Any popup components ({@code JComboBox},
     * {@code JPopupMenu}, {@code JMenuBar})
     * created within a modal dialog will be forced to be lightweight.
     * <p>
     * NOTE: This constructor does not allow you to create an unowned
     * {@code JDialog}. To create an unowned {@code JDialog}
     * you must use either the {@code JDialog(Window)} or
     * {@code JDialog(Dialog)} constructor with an argument of
     * {@code null}.
     *
     * <p>
     *  创建具有指定标题,所有者{@code Frame}和模态的对话框。如果{@code owner}为{@code null},则共享的隐藏框架将设置为此对话框的所有者。
     * <p>
     *  此构造函数将组件的locale属性设置为{@code JComponent.getDefaultLocale}返回的值。
     * <p>
     *  注意：在模态对话框中创建的任何弹出组件({@code JComboBox},{@code JPopupMenu},{@code JMenuBar})将被强制为轻量级。
     * <p>
     *  注意：此构造函数不允许创建无主{@code JDialog}。
     * 要创建无主{@code JDialog},您必须使用{@code JDialog(Window)}或{@code JDialog(Dialog)}构造函数,参数为{@code null}。
     * 
     * 
     * @param owner the {@code Frame} from which the dialog is displayed
     * @param title  the {@code String} to display in the dialog's
     *     title bar
     * @param modal specifies whether dialog blocks user input to other top-level
     *     windows when shown. If {@code true}, the modality type property is set to
     *     {@code DEFAULT_MODALITY_TYPE} otherwise the dialog is modeless
     * @throws HeadlessException if {@code GraphicsEnvironment.isHeadless()}
     *     returns {@code true}.
     *
     * @see java.awt.Dialog.ModalityType
     * @see java.awt.Dialog.ModalityType#MODELESS
     * @see java.awt.Dialog#DEFAULT_MODALITY_TYPE
     * @see java.awt.Dialog#setModal
     * @see java.awt.Dialog#setModalityType
     * @see java.awt.GraphicsEnvironment#isHeadless
     * @see JComponent#getDefaultLocale
     */
    public JDialog(Frame owner, String title, boolean modal) {
        super(owner == null? SwingUtilities.getSharedOwnerFrame() : owner,
              title, modal);
        if (owner == null) {
            WindowListener ownerShutdownListener =
                    SwingUtilities.getSharedOwnerFrameShutdownListener();
            addWindowListener(ownerShutdownListener);
        }
        dialogInit();
    }

    /**
     * Creates a dialog with the specified title,
     * owner {@code Frame}, modality and {@code GraphicsConfiguration}.
     * If {@code owner} is {@code null},
     * a shared, hidden frame will be set as the owner of this dialog.
     * <p>
     * This constructor sets the component's locale property to the value
     * returned by {@code JComponent.getDefaultLocale}.
     * <p>
     * NOTE: Any popup components ({@code JComboBox},
     * {@code JPopupMenu}, {@code JMenuBar})
     * created within a modal dialog will be forced to be lightweight.
     * <p>
     * NOTE: This constructor does not allow you to create an unowned
     * {@code JDialog}. To create an unowned {@code JDialog}
     * you must use either the {@code JDialog(Window)} or
     * {@code JDialog(Dialog)} constructor with an argument of
     * {@code null}.
     *
     * <p>
     *  创建具有指定标题,所有者{@code Frame},模态和{@code GraphicsConfiguration}的对话框。
     * 如果{@code owner}为{@code null},则共享的隐藏框架将设置为此对话框的所有者。
     * <p>
     * 此构造函数将组件的locale属性设置为{@code JComponent.getDefaultLocale}返回的值。
     * <p>
     *  注意：在模态对话框中创建的任何弹出组件({@code JComboBox},{@code JPopupMenu},{@code JMenuBar})将被强制为轻量级。
     * <p>
     *  注意：此构造函数不允许创建无主{@code JDialog}。
     * 要创建无主{@code JDialog},您必须使用{@code JDialog(Window)}或{@code JDialog(Dialog)}构造函数,参数为{@code null}。
     * 
     * 
     * @param owner the {@code Frame} from which the dialog is displayed
     * @param title  the {@code String} to display in the dialog's
     *     title bar
     * @param modal specifies whether dialog blocks user input to other top-level
     *     windows when shown. If {@code true}, the modality type property is set to
     *     {@code DEFAULT_MODALITY_TYPE}, otherwise the dialog is modeless.
     * @param gc the {@code GraphicsConfiguration} of the target screen device;
     *     if {@code null}, the default system {@code GraphicsConfiguration}
     *     is assumed
     * @throws HeadlessException if {@code GraphicsEnvironment.isHeadless()}
     *     returns {@code true}.
     * @see java.awt.Dialog.ModalityType
     * @see java.awt.Dialog.ModalityType#MODELESS
     * @see java.awt.Dialog#DEFAULT_MODALITY_TYPE
     * @see java.awt.Dialog#setModal
     * @see java.awt.Dialog#setModalityType
     * @see java.awt.GraphicsEnvironment#isHeadless
     * @see JComponent#getDefaultLocale
     * @since 1.4
     */
    public JDialog(Frame owner, String title, boolean modal,
                   GraphicsConfiguration gc) {
        super(owner == null? SwingUtilities.getSharedOwnerFrame() : owner,
              title, modal, gc);
        if (owner == null) {
            WindowListener ownerShutdownListener =
                    SwingUtilities.getSharedOwnerFrameShutdownListener();
            addWindowListener(ownerShutdownListener);
        }
        dialogInit();
    }

    /**
     * Creates a modeless dialog with the specified {@code Dialog}
     * as its owner and an empty title.
     * <p>
     * This constructor sets the component's locale property to the value
     * returned by {@code JComponent.getDefaultLocale}.
     *
     * <p>
     *  创建一个以指定的{@code Dialog}作为其所有者和空标题的无模式对话框。
     * <p>
     *  此构造函数将组件的locale属性设置为{@code JComponent.getDefaultLocale}返回的值。
     * 
     * 
     * @param owner the owner {@code Dialog} from which the dialog is displayed
     *     or {@code null} if this dialog has no owner
     * @throws HeadlessException {@code if GraphicsEnvironment.isHeadless()}
     *     returns {@code true}.
     * @see java.awt.GraphicsEnvironment#isHeadless
     * @see JComponent#getDefaultLocale
     */
    public JDialog(Dialog owner) {
        this(owner, false);
    }

    /**
     * Creates a dialog with an empty title and the specified modality and
     * {@code Dialog} as its owner.
     * <p>
     * This constructor sets the component's locale property to the value
     * returned by {@code JComponent.getDefaultLocale}.
     *
     * <p>
     *  创建具有空标题和指定模态的对话框,并使用{@code Dialog}作为其所有者。
     * <p>
     *  此构造函数将组件的locale属性设置为{@code JComponent.getDefaultLocale}返回的值。
     * 
     * 
     * @param owner the owner {@code Dialog} from which the dialog is displayed
     *     or {@code null} if this dialog has no owner
     * @param modal specifies whether dialog blocks user input to other top-level
     *     windows when shown. If {@code true}, the modality type property is set to
     *     {@code DEFAULT_MODALITY_TYPE}, otherwise the dialog is modeless.
     * @throws HeadlessException if {@code GraphicsEnvironment.isHeadless()}
     *     returns {@code true}.
     * @see java.awt.Dialog.ModalityType
     * @see java.awt.Dialog.ModalityType#MODELESS
     * @see java.awt.Dialog#DEFAULT_MODALITY_TYPE
     * @see java.awt.Dialog#setModal
     * @see java.awt.Dialog#setModalityType
     * @see java.awt.GraphicsEnvironment#isHeadless
     * @see JComponent#getDefaultLocale
     */
    public JDialog(Dialog owner, boolean modal) {
        this(owner, "", modal);
    }

    /**
     * Creates a modeless dialog with the specified title and
     * with the specified owner dialog.
     * <p>
     * This constructor sets the component's locale property to the value
     * returned by {@code JComponent.getDefaultLocale}.
     *
     * <p>
     *  创建具有指定标题和指定所有者对话框的无模式对话框。
     * <p>
     *  此构造函数将组件的locale属性设置为{@code JComponent.getDefaultLocale}返回的值。
     * 
     * 
     * @param owner the owner {@code Dialog} from which the dialog is displayed
     *     or {@code null} if this dialog has no owner
     * @param title  the {@code String} to display in the dialog's
     *                  title bar
     * @throws HeadlessException if {@code GraphicsEnvironment.isHeadless()}
     *     returns {@code true}.
     * @see java.awt.GraphicsEnvironment#isHeadless
     * @see JComponent#getDefaultLocale
     */
    public JDialog(Dialog owner, String title) {
        this(owner, title, false);
    }

    /**
     * Creates a dialog with the specified title, modality
     * and the specified owner {@code Dialog}.
     * <p>
     * This constructor sets the component's locale property to the value
     * returned by {@code JComponent.getDefaultLocale}.
     *
     * <p>
     *  创建具有指定标题,模态和指定所有者{@code Dialog}的对话框。
     * <p>
     *  此构造函数将组件的locale属性设置为{@code JComponent.getDefaultLocale}返回的值。
     * 
     * 
     * @param owner the owner {@code Dialog} from which the dialog is displayed
     *     or {@code null} if this dialog has no owner
     * @param title  the {@code String} to display in the dialog's
     *     title bar
     * @param modal specifies whether dialog blocks user input to other top-level
     *     windows when shown. If {@code true}, the modality type property is set to
     *     {@code DEFAULT_MODALITY_TYPE}, otherwise the dialog is modeless
     * @throws HeadlessException if {@code GraphicsEnvironment.isHeadless()}
     *     returns {@code true}.
     * @see java.awt.Dialog.ModalityType
     * @see java.awt.Dialog.ModalityType#MODELESS
     * @see java.awt.Dialog#DEFAULT_MODALITY_TYPE
     * @see java.awt.Dialog#setModal
     * @see java.awt.Dialog#setModalityType
     * @see java.awt.GraphicsEnvironment#isHeadless
     * @see JComponent#getDefaultLocale
     */
    public JDialog(Dialog owner, String title, boolean modal) {
        super(owner, title, modal);
        dialogInit();
    }

    /**
     * Creates a dialog with the specified title, owner {@code Dialog},
     * modality and {@code GraphicsConfiguration}.
     *
     * <p>
     * NOTE: Any popup components ({@code JComboBox},
     * {@code JPopupMenu}, {@code JMenuBar})
     * created within a modal dialog will be forced to be lightweight.
     * <p>
     * This constructor sets the component's locale property to the value
     * returned by {@code JComponent.getDefaultLocale}.
     *
     * <p>
     *  创建具有指定标题,所有者{@code Dialog},模式和{@code GraphicsConfiguration}的对话框。
     * 
     * <p>
     * 注意：在模态对话框中创建的任何弹出组件({@code JComboBox},{@code JPopupMenu},{@code JMenuBar})将被强制为轻量级。
     * <p>
     *  此构造函数将组件的locale属性设置为{@code JComponent.getDefaultLocale}返回的值。
     * 
     * 
     * @param owner the owner {@code Dialog} from which the dialog is displayed
     *     or {@code null} if this dialog has no owner
     * @param title  the {@code String} to display in the dialog's
     *     title bar
     * @param modal specifies whether dialog blocks user input to other top-level
     *     windows when shown. If {@code true}, the modality type property is set to
     *     {@code DEFAULT_MODALITY_TYPE}, otherwise the dialog is modeless
     * @param gc the {@code GraphicsConfiguration} of the target screen device;
     *     if {@code null}, the default system {@code GraphicsConfiguration}
     *     is assumed
     * @throws HeadlessException if {@code GraphicsEnvironment.isHeadless()}
     *     returns {@code true}.
     * @see java.awt.Dialog.ModalityType
     * @see java.awt.Dialog.ModalityType#MODELESS
     * @see java.awt.Dialog#DEFAULT_MODALITY_TYPE
     * @see java.awt.Dialog#setModal
     * @see java.awt.Dialog#setModalityType
     * @see java.awt.GraphicsEnvironment#isHeadless
     * @see JComponent#getDefaultLocale
     * @since 1.4
     */
    public JDialog(Dialog owner, String title, boolean modal,
                   GraphicsConfiguration gc) {
        super(owner, title, modal, gc);
        dialogInit();
    }

    /**
     * Creates a modeless dialog with the specified {@code Window}
     * as its owner and an empty title.
     * <p>
     * This constructor sets the component's locale property to the value
     * returned by {@code JComponent.getDefaultLocale}.
     *
     * <p>
     *  创建一个以指定的{@code Window}作为其所有者和空标题的无模式对话框。
     * <p>
     *  此构造函数将组件的locale属性设置为{@code JComponent.getDefaultLocale}返回的值。
     * 
     * 
     * @param owner the {@code Window} from which the dialog is displayed or
     *     {@code null} if this dialog has no owner
     *
     * @throws IllegalArgumentException
     *     if the {@code owner} is not an instance of {@link java.awt.Dialog Dialog}
     *     or {@link java.awt.Frame Frame}
     * @throws IllegalArgumentException
     *     if the {@code owner}'s {@code GraphicsConfiguration} is not from a screen device
     * @throws HeadlessException
     *     when {@code GraphicsEnvironment.isHeadless()} returns {@code true}
     *
     * @see java.awt.GraphicsEnvironment#isHeadless
     * @see JComponent#getDefaultLocale
     *
     * @since 1.6
     */
    public JDialog(Window owner) {
        this(owner, Dialog.ModalityType.MODELESS);
    }

    /**
     * Creates a dialog with an empty title and the specified modality and
     * {@code Window} as its owner.
     * <p>
     * This constructor sets the component's locale property to the value
     * returned by {@code JComponent.getDefaultLocale}.
     *
     * <p>
     *  创建具有空标题和指定模态的对话框,并使用{@code Window}作为其所有者。
     * <p>
     *  此构造函数将组件的locale属性设置为{@code JComponent.getDefaultLocale}返回的值。
     * 
     * 
     * @param owner the {@code Window} from which the dialog is displayed or
     *     {@code null} if this dialog has no owner
     * @param modalityType specifies whether dialog blocks input to other
     *     windows when shown. {@code null} value and unsupported modality
     *     types are equivalent to {@code MODELESS}
     *
     * @throws IllegalArgumentException
     *     if the {@code owner} is not an instance of {@link java.awt.Dialog Dialog}
     *     or {@link java.awt.Frame Frame}
     * @throws IllegalArgumentException
     *     if the {@code owner}'s {@code GraphicsConfiguration} is not from a screen device
     * @throws HeadlessException
     *     when {@code GraphicsEnvironment.isHeadless()} returns {@code true}
     * @throws SecurityException
     *     if the calling thread does not have permission to create modal dialogs
     *     with the given {@code modalityType}
     *
     * @see java.awt.Dialog.ModalityType
     * @see java.awt.Dialog#setModal
     * @see java.awt.Dialog#setModalityType
     * @see java.awt.GraphicsEnvironment#isHeadless
     * @see JComponent#getDefaultLocale
     *
     * @since 1.6
     */
    public JDialog(Window owner, ModalityType modalityType) {
        this(owner, "", modalityType);
    }

    /**
     * Creates a modeless dialog with the specified title and owner
     * {@code Window}.
     * <p>
     * This constructor sets the component's locale property to the value
     * returned by {@code JComponent.getDefaultLocale}.
     *
     * <p>
     *  创建具有指定标题和所有者{@code Window}的无模式对话框。
     * <p>
     *  此构造函数将组件的locale属性设置为{@code JComponent.getDefaultLocale}返回的值。
     * 
     * 
     * @param owner the {@code Window} from which the dialog is displayed or
     *     {@code null} if this dialog has no owner
     * @param title the {@code String} to display in the dialog's
     *     title bar or {@code null} if the dialog has no title
     *
     * @throws IllegalArgumentException
     *     if the {@code owner} is not an instance of {@link java.awt.Dialog Dialog}
     *     or {@link java.awt.Frame Frame}
     * @throws IllegalArgumentException
     *     if the {@code owner}'s {@code GraphicsConfiguration} is not from a screen device
     * @throws HeadlessException
     *     when {@code GraphicsEnvironment.isHeadless()} returns {@code true}
     *
     * @see java.awt.GraphicsEnvironment#isHeadless
     * @see JComponent#getDefaultLocale
     *
     * @since 1.6
     */
    public JDialog(Window owner, String title) {
        this(owner, title, Dialog.ModalityType.MODELESS);
    }

    /**
     * Creates a dialog with the specified title, owner {@code Window} and
     * modality.
     * <p>
     * This constructor sets the component's locale property to the value
     * returned by {@code JComponent.getDefaultLocale}.
     *
     * <p>
     *  创建具有指定标题,所有者{@code Window}和模态的对话框。
     * <p>
     *  此构造函数将组件的locale属性设置为{@code JComponent.getDefaultLocale}返回的值。
     * 
     * 
     * @param owner the {@code Window} from which the dialog is displayed or
     *     {@code null} if this dialog has no owner
     * @param title the {@code String} to display in the dialog's
     *     title bar or {@code null} if the dialog has no title
     * @param modalityType specifies whether dialog blocks input to other
     *     windows when shown. {@code null} value and unsupported modality
     *     types are equivalent to {@code MODELESS}
     *
     * @throws IllegalArgumentException
     *     if the {@code owner} is not an instance of {@link java.awt.Dialog Dialog}
     *     or {@link java.awt.Frame Frame}
     * @throws IllegalArgumentException
     *     if the {@code owner}'s {@code GraphicsConfiguration} is not from a screen device
     * @throws HeadlessException
     *     when {@code GraphicsEnvironment.isHeadless()} returns {@code true}
     * @throws SecurityException
     *     if the calling thread does not have permission to create modal dialogs
     *     with the given {@code modalityType}
     *
     * @see java.awt.Dialog.ModalityType
     * @see java.awt.Dialog#setModal
     * @see java.awt.Dialog#setModalityType
     * @see java.awt.GraphicsEnvironment#isHeadless
     * @see JComponent#getDefaultLocale
     *
     * @since 1.6
     */
    public JDialog(Window owner, String title, Dialog.ModalityType modalityType) {
        super(owner, title, modalityType);
        dialogInit();
    }

    /**
     * Creates a dialog with the specified title, owner {@code Window},
     * modality and {@code GraphicsConfiguration}.
     * <p>
     * NOTE: Any popup components ({@code JComboBox},
     * {@code JPopupMenu}, {@code JMenuBar})
     * created within a modal dialog will be forced to be lightweight.
     * <p>
     * This constructor sets the component's locale property to the value
     * returned by {@code JComponent.getDefaultLocale}.
     *
     * <p>
     *  创建具有指定标题,所有者{@code Window},模态和{@code GraphicsConfiguration}的对话框。
     * <p>
     *  注意：在模态对话框中创建的任何弹出组件({@code JComboBox},{@code JPopupMenu},{@code JMenuBar})将被强制为轻量级。
     * <p>
     *  此构造函数将组件的locale属性设置为{@code JComponent.getDefaultLocale}返回的值。
     * 
     * 
     * @param owner the {@code Window} from which the dialog is displayed or
     *     {@code null} if this dialog has no owner
     * @param title the {@code String} to display in the dialog's
     *     title bar or {@code null} if the dialog has no title
     * @param modalityType specifies whether dialog blocks input to other
     *     windows when shown. {@code null} value and unsupported modality
     *     types are equivalent to {@code MODELESS}
     * @param gc the {@code GraphicsConfiguration} of the target screen device;
     *     if {@code null}, the default system {@code GraphicsConfiguration}
     *     is assumed
     * @throws IllegalArgumentException
     *     if the {@code owner} is not an instance of {@link java.awt.Dialog Dialog}
     *     or {@link java.awt.Frame Frame}
     * @throws IllegalArgumentException
     *     if the {@code owner}'s {@code GraphicsConfiguration} is not from a screen device
     * @throws HeadlessException
     *     when {@code GraphicsEnvironment.isHeadless()} returns {@code true}
     * @throws SecurityException
     *     if the calling thread does not have permission to create modal dialogs
     *     with the given {@code modalityType}
     *
     * @see java.awt.Dialog.ModalityType
     * @see java.awt.Dialog#setModal
     * @see java.awt.Dialog#setModalityType
     * @see java.awt.GraphicsEnvironment#isHeadless
     * @see JComponent#getDefaultLocale
     *
     * @since 1.6
     */
    public JDialog(Window owner, String title, Dialog.ModalityType modalityType,
                   GraphicsConfiguration gc) {
        super(owner, title, modalityType, gc);
        dialogInit();
    }

    /**
     * Called by the constructors to init the {@code JDialog} properly.
     * <p>
     * 由构造函数调用以正确地初始化{@code JDialog}。
     * 
     */
    protected void dialogInit() {
        enableEvents(AWTEvent.KEY_EVENT_MASK | AWTEvent.WINDOW_EVENT_MASK);
        setLocale( JComponent.getDefaultLocale() );
        setRootPane(createRootPane());
        setBackground(UIManager.getColor("control"));
        setRootPaneCheckingEnabled(true);
        if (JDialog.isDefaultLookAndFeelDecorated()) {
            boolean supportsWindowDecorations =
            UIManager.getLookAndFeel().getSupportsWindowDecorations();
            if (supportsWindowDecorations) {
                setUndecorated(true);
                getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);
            }
        }
        sun.awt.SunToolkit.checkAndSetPolicy(this);
    }

    /**
     * Called by the constructor methods to create the default
     * {@code rootPane}.
     * <p>
     *  通过构造函数方法调用来创建默认的{@code rootPane}。
     * 
     */
    protected JRootPane createRootPane() {
        JRootPane rp = new JRootPane();
        // NOTE: this uses setOpaque vs LookAndFeel.installProperty as there
        // is NO reason for the RootPane not to be opaque. For painting to
        // work the contentPane must be opaque, therefor the RootPane can
        // also be opaque.
        rp.setOpaque(true);
        return rp;
    }

    /**
     * Handles window events depending on the state of the
     * {@code defaultCloseOperation} property.
     *
     * <p>
     *  根据{@code defaultCloseOperation}属性的状态处理窗口事件。
     * 
     * 
     * @see #setDefaultCloseOperation
     */
    protected void processWindowEvent(WindowEvent e) {
        super.processWindowEvent(e);

        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            switch(defaultCloseOperation) {
              case HIDE_ON_CLOSE:
                 setVisible(false);
                 break;
              case DISPOSE_ON_CLOSE:
                 dispose();
                 break;
              case DO_NOTHING_ON_CLOSE:
                 default:
                 break;
            }
        }
    }


    /**
     * Sets the operation that will happen by default when
     * the user initiates a "close" on this dialog.
     * You must specify one of the following choices:
     * <br><br>
     * <ul>
     * <li>{@code DO_NOTHING_ON_CLOSE}
     * (defined in {@code WindowConstants}):
     * Don't do anything; require the
     * program to handle the operation in the {@code windowClosing}
     * method of a registered {@code WindowListener} object.
     *
     * <li>{@code HIDE_ON_CLOSE}
     * (defined in {@code WindowConstants}):
     * Automatically hide the dialog after
     * invoking any registered {@code WindowListener}
     * objects.
     *
     * <li>{@code DISPOSE_ON_CLOSE}
     * (defined in {@code WindowConstants}):
     * Automatically hide and dispose the
     * dialog after invoking any registered {@code WindowListener}
     * objects.
     * </ul>
     * <p>
     * The value is set to {@code HIDE_ON_CLOSE} by default. Changes
     * to the value of this property cause the firing of a property
     * change event, with property name "defaultCloseOperation".
     * <p>
     * <b>Note</b>: When the last displayable window within the
     * Java virtual machine (VM) is disposed of, the VM may
     * terminate.  See <a href="../../java/awt/doc-files/AWTThreadIssues.html">
     * AWT Threading Issues</a> for more information.
     *
     * <p>
     *  设置当用户在此对话框上启动"关闭"时默认发生的操作。您必须指定以下选项之一：<br> <br>
     * <ul>
     *  <li> {@ code DO_NOTHING_ON_CLOSE}(在{@code WindowConstants}中定义)：不要做任何事情;需要程序处理注册的{@code WindowListener}
     * 对象的{@code windowClosing}方法中的操作。
     * 
     *  <li> {@ code HIDE_ON_CLOSE}(在{@code WindowConstants}中定义)：在调用任何已注册的{@code WindowListener}对象后自动隐藏对话框。
     * 
     *  <li> {@ code DISPOSE_ON_CLOSE}(在{@code WindowConstants}中定义)：在调用任何已注册的{@code WindowListener}对象后自动隐藏和处
     * 理对话框。
     * </ul>
     * <p>
     *  默认情况下,该值设置为{@code HIDE_ON_CLOSE}。对此属性的值的更改导致触发属性更改事件,属性名称为"defaultCloseOperation"。
     * <p>
     *  <b>注意</b>：当处理Java虚拟机(VM)中的最后一个可显示窗口时,VM可能终止。
     * 有关详细信息,请参见<a href="../../java/awt/doc-files/AWTThreadIssues.html"> AWT线程问题</a>。
     * 
     * 
     * @param operation the operation which should be performed when the
     *        user closes the dialog
     * @throws IllegalArgumentException if defaultCloseOperation value
     *         isn't one of the above valid values
     * @see #addWindowListener
     * @see #getDefaultCloseOperation
     * @see WindowConstants
     *
     * @beaninfo
     *   preferred: true
     *       bound: true
     *        enum: DO_NOTHING_ON_CLOSE WindowConstants.DO_NOTHING_ON_CLOSE
     *              HIDE_ON_CLOSE       WindowConstants.HIDE_ON_CLOSE
     *              DISPOSE_ON_CLOSE    WindowConstants.DISPOSE_ON_CLOSE
     * description: The dialog's default close operation.
     */
    public void setDefaultCloseOperation(int operation) {
        if (operation != DO_NOTHING_ON_CLOSE &&
            operation != HIDE_ON_CLOSE &&
            operation != DISPOSE_ON_CLOSE) {
            throw new IllegalArgumentException("defaultCloseOperation must be one of: DO_NOTHING_ON_CLOSE, HIDE_ON_CLOSE, or DISPOSE_ON_CLOSE");
        }

        int oldValue = this.defaultCloseOperation;
        this.defaultCloseOperation = operation;
        firePropertyChange("defaultCloseOperation", oldValue, operation);
    }

   /**
    * Returns the operation which occurs when the user
    * initiates a "close" on this dialog.
    *
    * <p>
    *  返回用户在此对话框上启动"关闭"时发生的操作。
    * 
    * 
    * @return an integer indicating the window-close operation
    * @see #setDefaultCloseOperation
    */
    public int getDefaultCloseOperation() {
        return defaultCloseOperation;
    }

    /**
     * Sets the {@code transferHandler} property, which is a mechanism to
     * support transfer of data into this component. Use {@code null}
     * if the component does not support data transfer operations.
     * <p>
     * If the system property {@code suppressSwingDropSupport} is {@code false}
     * (the default) and the current drop target on this component is either
     * {@code null} or not a user-set drop target, this method will change the
     * drop target as follows: If {@code newHandler} is {@code null} it will
     * clear the drop target. If not {@code null} it will install a new
     * {@code DropTarget}.
     * <p>
     * Note: When used with {@code JDialog}, {@code TransferHandler} only
     * provides data import capability, as the data export related methods
     * are currently typed to {@code JComponent}.
     * <p>
     * Please see
     * <a href="https://docs.oracle.com/javase/tutorial/uiswing/dnd/index.html">
     * How to Use Drag and Drop and Data Transfer</a>, a section in
     * <em>The Java Tutorial</em>, for more information.
     *
     * <p>
     * 设置{@code transferHandler}属性,这是一种支持将数据传输到此组件的机制。如果组件不支持数据传输操作,请使用{@code null}。
     * <p>
     *  如果系统属性{@code suppressSwingDropSupport}是{@code false}(默认值),并且此组件上的当前放置目标是{@code null}或不是用户设置放置目标,则此方法
     * 将更改放置目标如下所示：如果{@code newHandler}是{@code null},它将清除放置目标。
     * 如果不是{@code null},它会安装一个新的{@code DropTarget}。
     * <p>
     *  注意：与{@code JDialog}结合使用时,{@code TransferHandler}仅提供数据导入功能,因为数据导出相关方法目前类型为{@code JComponent}。
     * <p>
     *  请参见
     * <a href="https://docs.oracle.com/javase/tutorial/uiswing/dnd/index.html">
     *  如何使用拖放和数据传输</a>,有关更多信息,请参阅<em> Java教程</em>中的一节。
     * 
     * 
     * @param newHandler the new {@code TransferHandler}
     *
     * @see TransferHandler
     * @see #getTransferHandler
     * @see java.awt.Component#setDropTarget
     * @since 1.6
     *
     * @beaninfo
     *        bound: true
     *       hidden: true
     *  description: Mechanism for transfer of data into the component
     */
    public void setTransferHandler(TransferHandler newHandler) {
        TransferHandler oldHandler = transferHandler;
        transferHandler = newHandler;
        SwingUtilities.installSwingDropTargetAsNecessary(this, transferHandler);
        firePropertyChange("transferHandler", oldHandler, newHandler);
    }

    /**
     * Gets the {@code transferHandler} property.
     *
     * <p>
     *  获取{@code transferHandler}属性。
     * 
     * 
     * @return the value of the {@code transferHandler} property
     *
     * @see TransferHandler
     * @see #setTransferHandler
     * @since 1.6
     */
    public TransferHandler getTransferHandler() {
        return transferHandler;
    }

    /**
     * Calls {@code paint(g)}.  This method was overridden to
     * prevent an unnecessary call to clear the background.
     *
     * <p>
     *  调用{@code paint(g)}。此方法被覆盖,以防止不必要的调用清除背景。
     * 
     * 
     * @param g  the {@code Graphics} context in which to paint
     */
    public void update(Graphics g) {
        paint(g);
    }

   /**
    * Sets the menubar for this dialog.
    *
    * <p>
    *  设置此对话框的菜单栏。
    * 
    * 
    * @param menu the menubar being placed in the dialog
    *
    * @see #getJMenuBar
    *
    * @beaninfo
    *      hidden: true
    * description: The menubar for accessing pulldown menus from this dialog.
    */
    public void setJMenuBar(JMenuBar menu) {
        getRootPane().setMenuBar(menu);
    }

   /**
    * Returns the menubar set on this dialog.
    *
    * <p>
    *  返回此对话框上的菜单集。
    * 
    * 
    * @see #setJMenuBar
    */
    public JMenuBar getJMenuBar() {
        return getRootPane().getMenuBar();
    }


    /**
     * Returns whether calls to {@code add} and
     * {@code setLayout} are forwarded to the {@code contentPane}.
     *
     * <p>
     *  返回是否将对{@code add}和{@code setLayout}的调用转发到{@code contentPane}。
     * 
     * 
     * @return true if {@code add} and {@code setLayout}
     *         are forwarded; false otherwise
     *
     * @see #addImpl
     * @see #setLayout
     * @see #setRootPaneCheckingEnabled
     * @see javax.swing.RootPaneContainer
     */
    protected boolean isRootPaneCheckingEnabled() {
        return rootPaneCheckingEnabled;
    }


    /**
     * Sets whether calls to {@code add} and
     * {@code setLayout} are forwarded to the {@code contentPane}.
     *
     * <p>
     *  设置是否将对{@code add}和{@code setLayout}的调用转发到{@code contentPane}。
     * 
     * 
     * @param enabled  true if {@code add} and {@code setLayout}
     *        are forwarded, false if they should operate directly on the
     *        {@code JDialog}.
     *
     * @see #addImpl
     * @see #setLayout
     * @see #isRootPaneCheckingEnabled
     * @see javax.swing.RootPaneContainer
     * @beaninfo
     *      hidden: true
     * description: Whether the add and setLayout methods are forwarded
     */
    protected void setRootPaneCheckingEnabled(boolean enabled) {
        rootPaneCheckingEnabled = enabled;
    }

    /**
     * Adds the specified child {@code Component}.
     * This method is overridden to conditionally forward calls to the
     * {@code contentPane}.
     * By default, children are added to the {@code contentPane} instead
     * of the frame, refer to {@link javax.swing.RootPaneContainer} for
     * details.
     *
     * <p>
     * 添加指定的孩子{@code Component}。此方法将被覆盖,以有条件地将调用转发到{@code contentPane}。
     * 默认情况下,将子级添加到{@code contentPane}而不是框架中,有关详细信息,请参阅{@link javax.swing.RootPaneContainer}。
     * 
     * 
     * @param comp the component to be enhanced
     * @param constraints the constraints to be respected
     * @param index the index
     * @throws IllegalArgumentException if {@code index} is invalid
     * @throws IllegalArgumentException if adding the container's parent
     *                  to itself
     * @throws IllegalArgumentException if adding a window to a container
     *
     * @see #setRootPaneCheckingEnabled
     * @see javax.swing.RootPaneContainer
     */
    protected void addImpl(Component comp, Object constraints, int index)
    {
        if(isRootPaneCheckingEnabled()) {
            getContentPane().add(comp, constraints, index);
        }
        else {
            super.addImpl(comp, constraints, index);
        }
    }

    /**
     * Removes the specified component from the container. If
     * {@code comp} is not the {@code rootPane}, this will forward
     * the call to the {@code contentPane}. This will do nothing if
     * {@code comp} is not a child of the {@code JDialog} or
     * {@code contentPane}.
     *
     * <p>
     *  从容器中删除指定的组件。如果{@code comp}不是{@code rootPane},这会将调用转发到{@code contentPane}。
     * 如果{@code comp}不是{@code JDialog}或{@code contentPane}的子级,则此操作不会生效。
     * 
     * 
     * @param comp the component to be removed
     * @throws NullPointerException if {@code comp} is null
     * @see #add
     * @see javax.swing.RootPaneContainer
     */
    public void remove(Component comp) {
        if (comp == rootPane) {
            super.remove(comp);
        } else {
            getContentPane().remove(comp);
        }
    }


    /**
     * Sets the {@code LayoutManager}.
     * Overridden to conditionally forward the call to the
     * {@code contentPane}.
     * Refer to {@link javax.swing.RootPaneContainer} for
     * more information.
     *
     * <p>
     *  设置{@code LayoutManager}。已覆盖,有条件地将调用转发到{@code contentPane}。
     * 有关详细信息,请参阅{@link javax.swing.RootPaneContainer}。
     * 
     * 
     * @param manager the {@code LayoutManager}
     * @see #setRootPaneCheckingEnabled
     * @see javax.swing.RootPaneContainer
     */
    public void setLayout(LayoutManager manager) {
        if(isRootPaneCheckingEnabled()) {
            getContentPane().setLayout(manager);
        }
        else {
            super.setLayout(manager);
        }
    }


    /**
     * Returns the {@code rootPane} object for this dialog.
     *
     * <p>
     *  返回此对话框的{@code rootPane}对象。
     * 
     * 
     * @see #setRootPane
     * @see RootPaneContainer#getRootPane
     */
    public JRootPane getRootPane() {
        return rootPane;
    }


    /**
     * Sets the {@code rootPane} property.
     * This method is called by the constructor.
     *
     * <p>
     *  设置{@code rootPane}属性。此方法由构造函数调用。
     * 
     * 
     * @param root the {@code rootPane} object for this dialog
     *
     * @see #getRootPane
     *
     * @beaninfo
     *   hidden: true
     * description: the RootPane object for this dialog.
     */
    protected void setRootPane(JRootPane root) {
        if(rootPane != null) {
            remove(rootPane);
        }
        rootPane = root;
        if(rootPane != null) {
            boolean checkingEnabled = isRootPaneCheckingEnabled();
            try {
                setRootPaneCheckingEnabled(false);
                add(rootPane, BorderLayout.CENTER);
            }
            finally {
                setRootPaneCheckingEnabled(checkingEnabled);
            }
        }
    }


    /**
     * Returns the {@code contentPane} object for this dialog.
     *
     * <p>
     *  返回此对话框的{@code contentPane}对象。
     * 
     * 
     * @return the {@code contentPane} property
     *
     * @see #setContentPane
     * @see RootPaneContainer#getContentPane
     */
    public Container getContentPane() {
        return getRootPane().getContentPane();
    }


   /**
     * Sets the {@code contentPane} property.
     * This method is called by the constructor.
     * <p>
     * Swing's painting architecture requires an opaque {@code JComponent}
     * in the containment hierarchy. This is typically provided by the
     * content pane. If you replace the content pane it is recommended you
     * replace it with an opaque {@code JComponent}.
     * <p>
     *  设置{@code contentPane}属性。此方法由构造函数调用。
     * <p>
     *  Swing的绘制架构需要在包含层次结构中有一个不透明的{@code JComponent}。这通常由内容窗格提供。如果替换内容窗格,建议您将其替换为不透明的{@code JComponent}。
     * 
     * 
     * @see JRootPane
     *
     * @param contentPane the {@code contentPane} object for this dialog
     *
     * @throws java.awt.IllegalComponentStateException (a runtime
     *            exception) if the content pane parameter is {@code null}
     * @see #getContentPane
     * @see RootPaneContainer#setContentPane
     *
     * @beaninfo
     *     hidden: true
     *     description: The client area of the dialog where child
     *                  components are normally inserted.
     */
    public void setContentPane(Container contentPane) {
        getRootPane().setContentPane(contentPane);
    }

    /**
     * Returns the {@code layeredPane} object for this dialog.
     *
     * <p>
     *  返回此对话框的{@code layeredPane}对象。
     * 
     * 
     * @return the {@code layeredPane} property
     *
     * @see #setLayeredPane
     * @see RootPaneContainer#getLayeredPane
     */
    public JLayeredPane getLayeredPane() {
        return getRootPane().getLayeredPane();
    }

    /**
     * Sets the {@code layeredPane} property.
     * This method is called by the constructor.
     *
     * <p>
     *  设置{@code layeredPane}属性。此方法由构造函数调用。
     * 
     * 
     * @param layeredPane the new {@code layeredPane} property
     *
     * @throws java.awt.IllegalComponentStateException (a runtime
     *            exception) if the layered pane parameter is null
     * @see #getLayeredPane
     * @see RootPaneContainer#setLayeredPane
     *
     * @beaninfo
     *     hidden: true
     *     description: The pane which holds the various dialog layers.
     */
    public void setLayeredPane(JLayeredPane layeredPane) {
        getRootPane().setLayeredPane(layeredPane);
    }

    /**
     * Returns the {@code glassPane} object for this dialog.
     *
     * <p>
     *  返回此对话框的{@code glassPane}对象。
     * 
     * 
     * @return the {@code glassPane} property
     *
     * @see #setGlassPane
     * @see RootPaneContainer#getGlassPane
     */
    public Component getGlassPane() {
        return getRootPane().getGlassPane();
    }

    /**
     * Sets the {@code glassPane} property.
     * This method is called by the constructor.
     *
     * <p>
     *  设置{@code glassPane}属性。此方法由构造函数调用。
     * 
     * 
     * @param glassPane the {@code glassPane} object for this dialog
     * @see #getGlassPane
     * @see RootPaneContainer#setGlassPane
     *
     * @beaninfo
     *     hidden: true
     *     description: A transparent pane used for menu rendering.
     */
    public void setGlassPane(Component glassPane) {
        getRootPane().setGlassPane(glassPane);
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * {@inheritDoc}
     * 
     * 
     * @since 1.6
     */
    public Graphics getGraphics() {
        JComponent.getGraphicsInvoked(this);
        return super.getGraphics();
    }

    /**
     * Repaints the specified rectangle of this component within
     * {@code time} milliseconds.  Refer to {@code RepaintManager}
     * for details on how the repaint is handled.
     *
     * <p>
     *  在{@code time}毫秒内重新绘制此组件的指定矩形。有关如何处理重绘的详细信息,请参阅{@code RepaintManager}。
     * 
     * 
     * @param     time   maximum time in milliseconds before update
     * @param     x    the <i>x</i> coordinate
     * @param     y    the <i>y</i> coordinate
     * @param     width    the width
     * @param     height   the height
     * @see       RepaintManager
     * @since     1.6
     */
    public void repaint(long time, int x, int y, int width, int height) {
        if (RepaintManager.HANDLE_TOP_LEVEL_PAINT) {
            RepaintManager.currentManager(this).addDirtyRegion(
                              this, x, y, width, height);
        }
        else {
            super.repaint(time, x, y, width, height);
        }
    }

    /**
     * Provides a hint as to whether or not newly created {@code JDialog}s
     * should have their Window decorations (such as borders, widgets to
     * close the window, title...) provided by the current look
     * and feel. If {@code defaultLookAndFeelDecorated} is true,
     * the current {@code LookAndFeel} supports providing window
     * decorations, and the current window manager supports undecorated
     * windows, then newly created {@code JDialog}s will have their
     * Window decorations provided by the current {@code LookAndFeel}.
     * Otherwise, newly created {@code JDialog}s will have their
     * Window decorations provided by the current window manager.
     * <p>
     * You can get the same effect on a single JDialog by doing the following:
     * <pre>
     *    JDialog dialog = new JDialog();
     *    dialog.setUndecorated(true);
     *    dialog.getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);
     * </pre>
     *
     * <p>
     *  提供关于新创建的{@code JDialog}是否应该具有由当前外观和感觉提供的窗口装饰(例如边框,窗口小部件关闭窗口,标题...)的提示。
     * 如果{@code defaultLookAndFeelDecorated}是真的,当前的{@code LookAndFeel}支持提供窗口装饰,并且当前窗口管理器支持未装饰的窗口,然后新创建的{@code JDialog}
     * 将有由当前{代码LookAndFeel}。
     *  提供关于新创建的{@code JDialog}是否应该具有由当前外观和感觉提供的窗口装饰(例如边框,窗口小部件关闭窗口,标题...)的提示。
     * 否则,新创建的{@code JDialog}将具有由当前窗口管理器提供的窗口装饰。
     * <p>
     *  您可以通过执行以下操作,在单个JDialog上获得相同的效果：
     * <pre>
     *  JDialog dialog = new JDialog(); dialog.setUndecorated(true); dialog.getRootPane()。
     * setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);。
     * </pre>
     * 
     * 
     * @param defaultLookAndFeelDecorated A hint as to whether or not current
     *        look and feel should provide window decorations
     * @see javax.swing.LookAndFeel#getSupportsWindowDecorations
     * @since 1.4
     */
    public static void setDefaultLookAndFeelDecorated(boolean defaultLookAndFeelDecorated) {
        if (defaultLookAndFeelDecorated) {
            SwingUtilities.appContextPut(defaultLookAndFeelDecoratedKey, Boolean.TRUE);
        } else {
            SwingUtilities.appContextPut(defaultLookAndFeelDecoratedKey, Boolean.FALSE);
        }
    }

    /**
     * Returns true if newly created {@code JDialog}s should have their
     * Window decorations provided by the current look and feel. This is only
     * a hint, as certain look and feels may not support this feature.
     *
     * <p>
     *  如果新创建的{@code JDialog}应该具有由当前外观提供的窗口装饰,则返回true。这只是一个提示,因为某些外观和感觉可能不支持此功能。
     * 
     * 
     * @return true if look and feel should provide Window decorations.
     * @since 1.4
     */
    public static boolean isDefaultLookAndFeelDecorated() {
        Boolean defaultLookAndFeelDecorated =
            (Boolean) SwingUtilities.appContextGet(defaultLookAndFeelDecoratedKey);
        if (defaultLookAndFeelDecorated == null) {
            defaultLookAndFeelDecorated = Boolean.FALSE;
        }
        return defaultLookAndFeelDecorated.booleanValue();
    }

    /**
     * Returns a string representation of this {@code JDialog}.
     * This method
     * is intended to be used only for debugging purposes, and the
     * content and format of the returned string may vary between
     * implementations. The returned string may be empty but may not
     * be {@code null}.
     *
     * <p>
     *  返回此{@code JDialog}的字符串表示形式。此方法仅用于调试目的,并且返回的字符串的内容和格式可能因实现而异。返回的字符串可能为空,但可能不是{@code null}。
     * 
     * 
     * @return  a string representation of this {@code JDialog}.
     */
    protected String paramString() {
        String defaultCloseOperationString;
        if (defaultCloseOperation == HIDE_ON_CLOSE) {
            defaultCloseOperationString = "HIDE_ON_CLOSE";
        } else if (defaultCloseOperation == DISPOSE_ON_CLOSE) {
            defaultCloseOperationString = "DISPOSE_ON_CLOSE";
        } else if (defaultCloseOperation == DO_NOTHING_ON_CLOSE) {
            defaultCloseOperationString = "DO_NOTHING_ON_CLOSE";
        } else defaultCloseOperationString = "";
        String rootPaneString = (rootPane != null ?
                                 rootPane.toString() : "");
        String rootPaneCheckingEnabledString = (rootPaneCheckingEnabled ?
                                                "true" : "false");

        return super.paramString() +
        ",defaultCloseOperation=" + defaultCloseOperationString +
        ",rootPane=" + rootPaneString +
        ",rootPaneCheckingEnabled=" + rootPaneCheckingEnabledString;
    }


/////////////////
// Accessibility support
////////////////

    protected AccessibleContext accessibleContext = null;

    /**
     * Gets the AccessibleContext associated with this JDialog.
     * For JDialogs, the AccessibleContext takes the form of an
     * AccessibleJDialog.
     * A new AccessibleJDialog instance is created if necessary.
     *
     * <p>
     * 获取与此JDialog关联的AccessibleContext。对于JDialogs,AccessibleContext采用AccessibleJDialog的形式。
     * 如果需要,将创建一个新的AccessibleJDialog实例。
     * 
     * 
     * @return an AccessibleJDialog that serves as the
     *         AccessibleContext of this JDialog
     */
    public AccessibleContext getAccessibleContext() {
        if (accessibleContext == null) {
            accessibleContext = new AccessibleJDialog();
        }
        return accessibleContext;
    }

    /**
     * This class implements accessibility support for the
     * {@code JDialog} class.  It provides an implementation of the
     * Java Accessibility API appropriate to dialog user-interface
     * elements.
     * <p>
     *  此类实现{@code JDialog}类的辅助功能支持。它提供了适用于对话框用户界面元素的Java可访问性API的实现。
     * 
     */
    protected class AccessibleJDialog extends AccessibleAWTDialog {

        // AccessibleContext methods
        //
        /**
         * Get the accessible name of this object.
         *
         * <p>
         *  获取此对象的可访问名称。
         * 
         * 
         * @return the localized name of the object -- can be null if this
         * object does not have a name
         */
        public String getAccessibleName() {
            if (accessibleName != null) {
                return accessibleName;
            } else {
                if (getTitle() == null) {
                    return super.getAccessibleName();
                } else {
                    return getTitle();
                }
            }
        }

        /**
         * Get the state of this object.
         *
         * <p>
         *  获取此对象的状态。
         * 
         * @return an instance of AccessibleStateSet containing the current
         * state set of the object
         * @see AccessibleState
         */
        public AccessibleStateSet getAccessibleStateSet() {
            AccessibleStateSet states = super.getAccessibleStateSet();

            if (isResizable()) {
                states.add(AccessibleState.RESIZABLE);
            }
            if (getFocusOwner() != null) {
                states.add(AccessibleState.ACTIVE);
            }
            if (isModal()) {
                states.add(AccessibleState.MODAL);
            }
            return states;
        }

    } // inner class AccessibleJDialog
}
