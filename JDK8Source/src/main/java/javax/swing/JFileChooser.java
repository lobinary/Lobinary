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

import javax.swing.event.*;
import javax.swing.filechooser.*;
import javax.swing.plaf.FileChooserUI;

import javax.accessibility.*;

import java.io.File;
import java.io.ObjectOutputStream;
import java.io.IOException;

import java.util.Vector;
import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.Container;
import java.awt.BorderLayout;
import java.awt.Window;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.lang.ref.WeakReference;

/**
 * <code>JFileChooser</code> provides a simple mechanism for the user to
 * choose a file.
 * For information about using <code>JFileChooser</code>, see
 * <a
 href="https://docs.oracle.com/javase/tutorial/uiswing/components/filechooser.html">How to Use File Choosers</a>,
 * a section in <em>The Java Tutorial</em>.
 *
 * <p>
 *
 * The following code pops up a file chooser for the user's home directory that
 * sees only .jpg and .gif images:
 * <pre>
 *    JFileChooser chooser = new JFileChooser();
 *    FileNameExtensionFilter filter = new FileNameExtensionFilter(
 *        "JPG &amp; GIF Images", "jpg", "gif");
 *    chooser.setFileFilter(filter);
 *    int returnVal = chooser.showOpenDialog(parent);
 *    if(returnVal == JFileChooser.APPROVE_OPTION) {
 *       System.out.println("You chose to open this file: " +
 *            chooser.getSelectedFile().getName());
 *    }
 * </pre>
 * <p>
 * <strong>Warning:</strong> Swing is not thread safe. For more
 * information see <a
 * href="package-summary.html#threading">Swing's Threading
 * Policy</a>.
 *
 * @beaninfo
 *   attribute: isContainer false
 * description: A component which allows for the interactive selection of a file.
 *
 * <p>
 *  <code> JFileChooser </code>为用户提供了一个选择文件的简单机制。
 * 有关使用<code> JFileChooser </code>的信息,请参见<a href="https://docs.oracle.com/javase/tutorial/uiswing/components/filechooser.html">
 * 如何使用文件选择器</a >,<em> </em> </em>中的一个部分。
 *  <code> JFileChooser </code>为用户提供了一个选择文件的简单机制。
 * 
 * <p>
 * 
 *  下面的代码为用户的主目录弹出一个文件选择器,它只能看到.jpg和.gif图像：
 * <pre>
 *  JFileChooser chooser = new JFileChooser(); FileNameExtensionFilter filter = new FileNameExtensionFil
 * ter("JPG&amp; GIF Images","jpg","gif"); chooser.setFileFilter(filter); int returnVal = chooser.showOp
 * enDialog(parent); if(returnVal == JFileChooser.APPROVE_OPTION){System.out.println("你选择打开这个文件："+ chooser.getSelectedFile()。
 * getName()); }}。
 * </pre>
 * <p>
 *  <strong>警告：</strong> Swing不是线程安全的。有关详情,请参阅<a href="package-summary.html#threading"> Swing的线程策略</a>。
 * 
 *  @beaninfo属性：isContainer false description：允许交互式选择文件的组件。
 * 
 * 
 * @author Jeff Dinkins
 *
 */
public class JFileChooser extends JComponent implements Accessible {

    /**
    /* <p>
    /* 
     * @see #getUIClassID
     * @see #readObject
     */
    private static final String uiClassID = "FileChooserUI";

    // ************************
    // ***** Dialog Types *****
    // ************************

    /**
     * Type value indicating that the <code>JFileChooser</code> supports an
     * "Open" file operation.
     * <p>
     *  键入指示<code> JFileChooser </code>支持"打开"文件操作的值。
     * 
     */
    public static final int OPEN_DIALOG = 0;

    /**
     * Type value indicating that the <code>JFileChooser</code> supports a
     * "Save" file operation.
     * <p>
     *  键入值指示<code> JFileChooser </code>支持"保存"文件操作。
     * 
     */
    public static final int SAVE_DIALOG = 1;

    /**
     * Type value indicating that the <code>JFileChooser</code> supports a
     * developer-specified file operation.
     * <p>
     *  键入值,指示<code> JFileChooser </code>支持开发人员指定的文件操作。
     * 
     */
    public static final int CUSTOM_DIALOG = 2;


    // ********************************
    // ***** Dialog Return Values *****
    // ********************************

    /**
     * Return value if cancel is chosen.
     * <p>
     * 如果选择取消,返回值。
     * 
     */
    public static final int CANCEL_OPTION = 1;

    /**
     * Return value if approve (yes, ok) is chosen.
     * <p>
     *  选择approve(yes,ok)时返回值。
     * 
     */
    public static final int APPROVE_OPTION = 0;

    /**
     * Return value if an error occurred.
     * <p>
     *  发生错误时返回值。
     * 
     */
    public static final int ERROR_OPTION = -1;


    // **********************************
    // ***** JFileChooser properties *****
    // **********************************


    /** Instruction to display only files. */
    public static final int FILES_ONLY = 0;

    /** Instruction to display only directories. */
    public static final int DIRECTORIES_ONLY = 1;

    /** Instruction to display both files and directories. */
    public static final int FILES_AND_DIRECTORIES = 2;

    /** Instruction to cancel the current selection. */
    public static final String CANCEL_SELECTION = "CancelSelection";

    /**
     * Instruction to approve the current selection
     * (same as pressing yes or ok).
     * <p>
     *  批准当前选择的指令(与按yes或ok相同)。
     * 
     */
    public static final String APPROVE_SELECTION = "ApproveSelection";

    /** Identifies change in the text on the approve (yes, ok) button. */
    public static final String APPROVE_BUTTON_TEXT_CHANGED_PROPERTY = "ApproveButtonTextChangedProperty";

    /**
     * Identifies change in the tooltip text for the approve (yes, ok)
     * button.
     * <p>
     *  标识工具提示文本中批准(是,确定)按钮的更改。
     * 
     */
    public static final String APPROVE_BUTTON_TOOL_TIP_TEXT_CHANGED_PROPERTY = "ApproveButtonToolTipTextChangedProperty";

    /** Identifies change in the mnemonic for the approve (yes, ok) button. */
    public static final String APPROVE_BUTTON_MNEMONIC_CHANGED_PROPERTY = "ApproveButtonMnemonicChangedProperty";

    /** Instruction to display the control buttons. */
    public static final String CONTROL_BUTTONS_ARE_SHOWN_CHANGED_PROPERTY = "ControlButtonsAreShownChangedProperty";

    /** Identifies user's directory change. */
    public static final String DIRECTORY_CHANGED_PROPERTY = "directoryChanged";

    /** Identifies change in user's single-file selection. */
    public static final String SELECTED_FILE_CHANGED_PROPERTY = "SelectedFileChangedProperty";

    /** Identifies change in user's multiple-file selection. */
    public static final String SELECTED_FILES_CHANGED_PROPERTY = "SelectedFilesChangedProperty";

    /** Enables multiple-file selections. */
    public static final String MULTI_SELECTION_ENABLED_CHANGED_PROPERTY = "MultiSelectionEnabledChangedProperty";

    /**
     * Says that a different object is being used to find available drives
     * on the system.
     * <p>
     *  表示使用不同的对象来查找系统上的可用驱动器。
     * 
     */
    public static final String FILE_SYSTEM_VIEW_CHANGED_PROPERTY = "FileSystemViewChanged";

    /**
     * Says that a different object is being used to retrieve file
     * information.
     * <p>
     *  表示正在使用不同的对象来检索文件信息。
     * 
     */
    public static final String FILE_VIEW_CHANGED_PROPERTY = "fileViewChanged";

    /** Identifies a change in the display-hidden-files property. */
    public static final String FILE_HIDING_CHANGED_PROPERTY = "FileHidingChanged";

    /** User changed the kind of files to display. */
    public static final String FILE_FILTER_CHANGED_PROPERTY = "fileFilterChanged";

    /**
     * Identifies a change in the kind of selection (single,
     * multiple, etc.).
     * <p>
     *  标识选择种类的更改(单个,多个等)。
     * 
     */
    public static final String FILE_SELECTION_MODE_CHANGED_PROPERTY = "fileSelectionChanged";

    /**
     * Says that a different accessory component is in use
     * (for example, to preview files).
     * <p>
     *  表示正在使用不同的附件组件(例如,预览文件)。
     * 
     */
    public static final String ACCESSORY_CHANGED_PROPERTY = "AccessoryChangedProperty";

    /**
     * Identifies whether a the AcceptAllFileFilter is used or not.
     * <p>
     *  标识是否使用AcceptAllFileFilter。
     * 
     */
    public static final String ACCEPT_ALL_FILE_FILTER_USED_CHANGED_PROPERTY = "acceptAllFileFilterUsedChanged";

    /** Identifies a change in the dialog title. */
    public static final String DIALOG_TITLE_CHANGED_PROPERTY = "DialogTitleChangedProperty";

    /**
     * Identifies a change in the type of files displayed (files only,
     * directories only, or both files and directories).
     * <p>
     *  标识显示的文件类型的更改(仅文件,仅目录或文件和目录)。
     * 
     */
    public static final String DIALOG_TYPE_CHANGED_PROPERTY = "DialogTypeChangedProperty";

    /**
     * Identifies a change in the list of predefined file filters
     * the user can choose from.
     * <p>
     *  标识用户可以选择的预定义文件过滤器列表中的更改。
     * 
     */
    public static final String CHOOSABLE_FILE_FILTER_CHANGED_PROPERTY = "ChoosableFileFilterChangedProperty";

    // ******************************
    // ***** instance variables *****
    // ******************************

    private String dialogTitle = null;
    private String approveButtonText = null;
    private String approveButtonToolTipText = null;
    private int approveButtonMnemonic = 0;

    private Vector<FileFilter> filters = new Vector<FileFilter>(5);
    private JDialog dialog = null;
    private int dialogType = OPEN_DIALOG;
    private int returnValue = ERROR_OPTION;
    private JComponent accessory = null;

    private FileView fileView = null;

    private boolean controlsShown = true;

    private boolean useFileHiding = true;
    private static final String SHOW_HIDDEN_PROP = "awt.file.showHiddenFiles";

    // Listens to changes in the native setting for showing hidden files.
    // The Listener is removed and the native setting is ignored if
    // setFileHidingEnabled() is ever called.
    private transient PropertyChangeListener showFilesListener = null;

    private int fileSelectionMode = FILES_ONLY;

    private boolean multiSelectionEnabled = false;

    private boolean useAcceptAllFileFilter = true;

    private boolean dragEnabled = false;

    private FileFilter fileFilter = null;

    private FileSystemView fileSystemView = null;

    private File currentDirectory = null;
    private File selectedFile = null;
    private File[] selectedFiles;

    // *************************************
    // ***** JFileChooser Constructors *****
    // *************************************

    /**
     * Constructs a <code>JFileChooser</code> pointing to the user's
     * default directory. This default depends on the operating system.
     * It is typically the "My Documents" folder on Windows, and the
     * user's home directory on Unix.
     * <p>
     *  构造一个指向用户默认目录的<code> JFileChooser </code>。此默认值取决于操作系统。它通常是Windows上的"我的文档"文件夹,以及Unix上用户的主目录。
     * 
     */
    public JFileChooser() {
        this((File) null, (FileSystemView) null);
    }

    /**
     * Constructs a <code>JFileChooser</code> using the given path.
     * Passing in a <code>null</code>
     * string causes the file chooser to point to the user's default directory.
     * This default depends on the operating system. It is
     * typically the "My Documents" folder on Windows, and the user's
     * home directory on Unix.
     *
     * <p>
     *  使用给定的路径构造一个<code> JFileChooser </code>。传入<code> null </code>字符串会导致文件选择器指向用户的默认目录。此默认值取决于操作系统。
     * 它通常是Windows上的"我的文档"文件夹,以及Unix上用户的主目录。
     * 
     * 
     * @param currentDirectoryPath  a <code>String</code> giving the path
     *                          to a file or directory
     */
    public JFileChooser(String currentDirectoryPath) {
        this(currentDirectoryPath, (FileSystemView) null);
    }

    /**
     * Constructs a <code>JFileChooser</code> using the given <code>File</code>
     * as the path. Passing in a <code>null</code> file
     * causes the file chooser to point to the user's default directory.
     * This default depends on the operating system. It is
     * typically the "My Documents" folder on Windows, and the user's
     * home directory on Unix.
     *
     * <p>
     * 使用给定的<code> File </code>作为路径构造一个<code> JFileChooser </code>。
     * 传入<code> null </code>文件会导致文件选择器指向用户的默认目录。此默认值取决于操作系统。它通常是Windows上的"我的文档"文件夹,以及Unix上用户的主目录。
     * 
     * 
     * @param currentDirectory  a <code>File</code> object specifying
     *                          the path to a file or directory
     */
    public JFileChooser(File currentDirectory) {
        this(currentDirectory, (FileSystemView) null);
    }

    /**
     * Constructs a <code>JFileChooser</code> using the given
     * <code>FileSystemView</code>.
     * <p>
     *  使用给定的<code> FileSystemView </code>构造一个<code> JFileChooser </code>。
     * 
     */
    public JFileChooser(FileSystemView fsv) {
        this((File) null, fsv);
    }


    /**
     * Constructs a <code>JFileChooser</code> using the given current directory
     * and <code>FileSystemView</code>.
     * <p>
     *  使用给定的当前目录和<code> FileSystemView </code>构造<code> JFileChooser </code>。
     * 
     */
    public JFileChooser(File currentDirectory, FileSystemView fsv) {
        setup(fsv);
        setCurrentDirectory(currentDirectory);
    }

    /**
     * Constructs a <code>JFileChooser</code> using the given current directory
     * path and <code>FileSystemView</code>.
     * <p>
     *  使用给定的当前目录路径和<code> FileSystemView </code>构造<code> JFileChooser </code>。
     * 
     */
    public JFileChooser(String currentDirectoryPath, FileSystemView fsv) {
        setup(fsv);
        if(currentDirectoryPath == null) {
            setCurrentDirectory(null);
        } else {
            setCurrentDirectory(fileSystemView.createFileObject(currentDirectoryPath));
        }
    }

    /**
     * Performs common constructor initialization and setup.
     * <p>
     *  执行常见的构造函数初始化和设置。
     * 
     */
    protected void setup(FileSystemView view) {
        installShowFilesListener();
        installHierarchyListener();

        if(view == null) {
            view = FileSystemView.getFileSystemView();
        }
        setFileSystemView(view);
        updateUI();
        if(isAcceptAllFileFilterUsed()) {
            setFileFilter(getAcceptAllFileFilter());
        }
        enableEvents(AWTEvent.MOUSE_EVENT_MASK);
    }

    private void installHierarchyListener() {
        addHierarchyListener(new HierarchyListener() {
            @Override
            public void hierarchyChanged(HierarchyEvent e) {
                if ((e.getChangeFlags() & HierarchyEvent.PARENT_CHANGED)
                        == HierarchyEvent.PARENT_CHANGED) {
                    JFileChooser fc = JFileChooser.this;
                    JRootPane rootPane = SwingUtilities.getRootPane(fc);
                    if (rootPane != null) {
                        rootPane.setDefaultButton(fc.getUI().getDefaultButton(fc));
                    }
                }
            }
        });
    }

    private void installShowFilesListener() {
        // Track native setting for showing hidden files
        Toolkit tk = Toolkit.getDefaultToolkit();
        Object showHiddenProperty = tk.getDesktopProperty(SHOW_HIDDEN_PROP);
        if (showHiddenProperty instanceof Boolean) {
            useFileHiding = !((Boolean)showHiddenProperty).booleanValue();
            showFilesListener = new WeakPCL(this);
            tk.addPropertyChangeListener(SHOW_HIDDEN_PROP, showFilesListener);
        }
    }

    /**
     * Sets the <code>dragEnabled</code> property,
     * which must be <code>true</code> to enable
     * automatic drag handling (the first part of drag and drop)
     * on this component.
     * The <code>transferHandler</code> property needs to be set
     * to a non-<code>null</code> value for the drag to do
     * anything.  The default value of the <code>dragEnabled</code>
     * property
     * is <code>false</code>.
     *
     * <p>
     *
     * When automatic drag handling is enabled,
     * most look and feels begin a drag-and-drop operation
     * whenever the user presses the mouse button over an item
     * and then moves the mouse a few pixels.
     * Setting this property to <code>true</code>
     * can therefore have a subtle effect on
     * how selections behave.
     *
     * <p>
     *
     * Some look and feels might not support automatic drag and drop;
     * they will ignore this property.  You can work around such
     * look and feels by modifying the component
     * to directly call the <code>exportAsDrag</code> method of a
     * <code>TransferHandler</code>.
     *
     * <p>
     *  设置<code> dragEnabled </code>属性,必须<code> true </code>才能在此组件上启用自动拖动处理(拖放的第一部分)。
     * 对于拖动执行任何操作,<code> transferHandler </code>属性需要设置为非<code> null </code>值。
     *  <code> dragEnabled </code>属性的默认值为<code> false </code>。
     * 
     * <p>
     * 
     *  当启用自动拖动处理时,每当用户在项目上按鼠标按钮时,大多数外观和感觉开始拖放操作,然后将鼠标移动几个像素。因此将此属性设置为<code> true </code>可以对选择行为产生微妙的影响。
     * 
     * <p>
     * 
     * 一些外观和感觉可能不支持自动拖放;他们将忽略此属性。
     * 你可以通过修改组件来直接调用<code> TransferHandler </code>的<code> exportAsDrag </code>方法来解决这种外观和感觉。
     * 
     * 
     * @param b the value to set the <code>dragEnabled</code> property to
     * @exception HeadlessException if
     *            <code>b</code> is <code>true</code> and
     *            <code>GraphicsEnvironment.isHeadless()</code>
     *            returns <code>true</code>
     * @see java.awt.GraphicsEnvironment#isHeadless
     * @see #getDragEnabled
     * @see #setTransferHandler
     * @see TransferHandler
     * @since 1.4
     *
     * @beaninfo
     *  description: determines whether automatic drag handling is enabled
     *        bound: false
     */
    public void setDragEnabled(boolean b) {
        if (b && GraphicsEnvironment.isHeadless()) {
            throw new HeadlessException();
        }
        dragEnabled = b;
    }

    /**
     * Gets the value of the <code>dragEnabled</code> property.
     *
     * <p>
     *  获取<code> dragEnabled </code>属性的值。
     * 
     * 
     * @return  the value of the <code>dragEnabled</code> property
     * @see #setDragEnabled
     * @since 1.4
     */
    public boolean getDragEnabled() {
        return dragEnabled;
    }

    // *****************************
    // ****** File Operations ******
    // *****************************

    /**
     * Returns the selected file. This can be set either by the
     * programmer via <code>setSelectedFile</code> or by a user action, such as
     * either typing the filename into the UI or selecting the
     * file from a list in the UI.
     *
     * <p>
     *  返回所选文件。这可以由程序员通过<code> setSelectedFile </code>或用户操作来设置,例如在UI中键入文件名或从UI中的列表中选择文件。
     * 
     * 
     * @see #setSelectedFile
     * @return the selected file
     */
    public File getSelectedFile() {
        return selectedFile;
    }

    /**
     * Sets the selected file. If the file's parent directory is
     * not the current directory, changes the current directory
     * to be the file's parent directory.
     *
     * @beaninfo
     *   preferred: true
     *       bound: true
     *
     * <p>
     *  设置所选文件。如果文件的父目录不是当前目录,请将当前目录更改为文件的父目录。
     * 
     *  @beaninfo preferred：true bound：true
     * 
     * 
     * @see #getSelectedFile
     *
     * @param file the selected file
     */
    public void setSelectedFile(File file) {
        File oldValue = selectedFile;
        selectedFile = file;
        if(selectedFile != null) {
            if (file.isAbsolute() && !getFileSystemView().isParent(getCurrentDirectory(), selectedFile)) {
                setCurrentDirectory(selectedFile.getParentFile());
            }
            if (!isMultiSelectionEnabled() || selectedFiles == null || selectedFiles.length == 1) {
                ensureFileIsVisible(selectedFile);
            }
        }
        firePropertyChange(SELECTED_FILE_CHANGED_PROPERTY, oldValue, selectedFile);
    }

    /**
     * Returns a list of selected files if the file chooser is
     * set to allow multiple selection.
     * <p>
     *  如果文件选择器设置为允许多个选择,则返回所选文件的列表。
     * 
     */
    public File[] getSelectedFiles() {
        if(selectedFiles == null) {
            return new File[0];
        } else {
            return selectedFiles.clone();
        }
    }

    /**
     * Sets the list of selected files if the file chooser is
     * set to allow multiple selection.
     *
     * @beaninfo
     *       bound: true
     * description: The list of selected files if the chooser is in multiple selection mode.
     * <p>
     *  如果文件选择器设置为允许多选,则设置所选文件的列表。
     * 
     *  @beaninfo bound：true description：如果选择器处于多重选择模式,则选择的文件的列表。
     * 
     */
    public void setSelectedFiles(File[] selectedFiles) {
        File[] oldValue = this.selectedFiles;
        if (selectedFiles == null || selectedFiles.length == 0) {
            selectedFiles = null;
            this.selectedFiles = null;
            setSelectedFile(null);
        } else {
            this.selectedFiles = selectedFiles.clone();
            setSelectedFile(this.selectedFiles[0]);
        }
        firePropertyChange(SELECTED_FILES_CHANGED_PROPERTY, oldValue, selectedFiles);
    }

    /**
     * Returns the current directory.
     *
     * <p>
     *  返回当前目录。
     * 
     * 
     * @return the current directory
     * @see #setCurrentDirectory
     */
    public File getCurrentDirectory() {
        return currentDirectory;
    }

    /**
     * Sets the current directory. Passing in <code>null</code> sets the
     * file chooser to point to the user's default directory.
     * This default depends on the operating system. It is
     * typically the "My Documents" folder on Windows, and the user's
     * home directory on Unix.
     *
     * If the file passed in as <code>currentDirectory</code> is not a
     * directory, the parent of the file will be used as the currentDirectory.
     * If the parent is not traversable, then it will walk up the parent tree
     * until it finds a traversable directory, or hits the root of the
     * file system.
     *
     * @beaninfo
     *   preferred: true
     *       bound: true
     * description: The directory that the JFileChooser is showing files of.
     *
     * <p>
     *  设置当前目录。传入<code> null </code>将文件选择器设置为指向用户的默认目录。此默认值取决于操作系统。它通常是Windows上的"我的文档"文件夹,以及Unix上用户的主目录。
     * 
     * 如果以<code> currentDirectory </code>形式传递的文件不是目录,则该文件的父目录将用作currentDirectory。
     * 如果父节点不可遍历,则它将向上遍历父树,直到找到可遍历目录,或者命中文件系统的根。
     * 
     *  @beaninfo preferred：true bound：true description：JFileChooser显示文件的目录。
     * 
     * 
     * @param dir the current directory to point to
     * @see #getCurrentDirectory
     */
    public void setCurrentDirectory(File dir) {
        File oldValue = currentDirectory;

        if (dir != null && !dir.exists()) {
            dir = currentDirectory;
        }
        if (dir == null) {
            dir = getFileSystemView().getDefaultDirectory();
        }
        if (currentDirectory != null) {
            /* Verify the toString of object */
            if (this.currentDirectory.equals(dir)) {
                return;
            }
        }

        File prev = null;
        while (!isTraversable(dir) && prev != dir) {
            prev = dir;
            dir = getFileSystemView().getParentDirectory(dir);
        }
        currentDirectory = dir;

        firePropertyChange(DIRECTORY_CHANGED_PROPERTY, oldValue, currentDirectory);
    }

    /**
     * Changes the directory to be set to the parent of the
     * current directory.
     *
     * <p>
     *  将目录更改为要设置为当前目录的父目录。
     * 
     * 
     * @see #getCurrentDirectory
     */
    public void changeToParentDirectory() {
        selectedFile = null;
        File oldValue = getCurrentDirectory();
        setCurrentDirectory(getFileSystemView().getParentDirectory(oldValue));
    }

    /**
     * Tells the UI to rescan its files list from the current directory.
     * <p>
     *  告诉UI从当前目录重新扫描其文件列表。
     * 
     */
    public void rescanCurrentDirectory() {
        getUI().rescanCurrentDirectory(this);
    }

    /**
     * Makes sure that the specified file is viewable, and
     * not hidden.
     *
     * <p>
     *  确保指定的文件是可见的,而不是隐藏的。
     * 
     * 
     * @param f  a File object
     */
    public void ensureFileIsVisible(File f) {
        getUI().ensureFileIsVisible(this, f);
    }

    // **************************************
    // ***** JFileChooser Dialog methods *****
    // **************************************

    /**
     * Pops up an "Open File" file chooser dialog. Note that the
     * text that appears in the approve button is determined by
     * the L&amp;F.
     *
     * <p>
     *  弹出"打开文件"文件选择器对话框。注意,批准按钮中出现的文本由L&amp; F确定。
     * 
     * 
     * @param    parent  the parent component of the dialog,
     *                  can be <code>null</code>;
     *                  see <code>showDialog</code> for details
     * @return   the return state of the file chooser on popdown:
     * <ul>
     * <li>JFileChooser.CANCEL_OPTION
     * <li>JFileChooser.APPROVE_OPTION
     * <li>JFileChooser.ERROR_OPTION if an error occurs or the
     *                  dialog is dismissed
     * </ul>
     * @exception HeadlessException if GraphicsEnvironment.isHeadless()
     * returns true.
     * @see java.awt.GraphicsEnvironment#isHeadless
     * @see #showDialog
     */
    public int showOpenDialog(Component parent) throws HeadlessException {
        setDialogType(OPEN_DIALOG);
        return showDialog(parent, null);
    }

    /**
     * Pops up a "Save File" file chooser dialog. Note that the
     * text that appears in the approve button is determined by
     * the L&amp;F.
     *
     * <p>
     *  弹出"保存文件"文件选择器对话框。注意,批准按钮中出现的文本由L&amp; F确定。
     * 
     * 
     * @param    parent  the parent component of the dialog,
     *                  can be <code>null</code>;
     *                  see <code>showDialog</code> for details
     * @return   the return state of the file chooser on popdown:
     * <ul>
     * <li>JFileChooser.CANCEL_OPTION
     * <li>JFileChooser.APPROVE_OPTION
     * <li>JFileChooser.ERROR_OPTION if an error occurs or the
     *                  dialog is dismissed
     * </ul>
     * @exception HeadlessException if GraphicsEnvironment.isHeadless()
     * returns true.
     * @see java.awt.GraphicsEnvironment#isHeadless
     * @see #showDialog
     */
    public int showSaveDialog(Component parent) throws HeadlessException {
        setDialogType(SAVE_DIALOG);
        return showDialog(parent, null);
    }

    /**
     * Pops a custom file chooser dialog with a custom approve button.
     * For example, the following code
     * pops up a file chooser with a "Run Application" button
     * (instead of the normal "Save" or "Open" button):
     * <pre>
     * filechooser.showDialog(parentFrame, "Run Application");
     * </pre>
     *
     * Alternatively, the following code does the same thing:
     * <pre>
     *    JFileChooser chooser = new JFileChooser(null);
     *    chooser.setApproveButtonText("Run Application");
     *    chooser.showDialog(parentFrame, null);
     * </pre>
     *
     * <!--PENDING(jeff) - the following method should be added to the api:
     *      showDialog(Component parent);-->
     * <!--PENDING(kwalrath) - should specify modality and what
     *      "depends" means.-->
     *
     * <p>
     *
     * The <code>parent</code> argument determines two things:
     * the frame on which the open dialog depends and
     * the component whose position the look and feel
     * should consider when placing the dialog.  If the parent
     * is a <code>Frame</code> object (such as a <code>JFrame</code>)
     * then the dialog depends on the frame and
     * the look and feel positions the dialog
     * relative to the frame (for example, centered over the frame).
     * If the parent is a component, then the dialog
     * depends on the frame containing the component,
     * and is positioned relative to the component
     * (for example, centered over the component).
     * If the parent is <code>null</code>, then the dialog depends on
     * no visible window, and it's placed in a
     * look-and-feel-dependent position
     * such as the center of the screen.
     *
     * <p>
     *  使用自定义批准按钮弹出自定义文件选择器对话框。例如,以下代码使用"运行应用程序"按钮(而不是常规的"保存"或"打开"按钮)弹出文件选择器：
     * <pre>
     *  filechooser.showDialog(parentFrame,"运行应用程序");
     * </pre>
     * 
     *  或者,以下代码执行相同的操作：
     * <pre>
     *  JFileChooser chooser = new JFileChooser(null); chooser.setApproveButtonText("运行应用程序"); chooser.showD
     * ialog(parentFrame,null);。
     * </pre>
     * 
     *  <！ -  PENDING(jeff) - 以下方法应该添加到api：
     * showDialog(Component parent);-->
     *  <！ -  PENDING(kwalrath) - 应该指定模态和什么
     * "depends" means.-->
     * 
     * <p>
     * 
     * <code> parent </code>参数决定了两个事情：打开的对话框所依赖的框架和放置对话框时外观和感觉应该考虑的组件。
     * 如果父对象是<code> Frame </code>对象(例如<code> JFrame </code>),则对话框取决于框架和对象相对于框架的外观和感觉(例如,中心在框架上)。
     * 如果父组件是组件,则对话框取决于包含组件的框架,并且相对于组件定位(例如,在组件上方居中)。
     * 如果父代为<code> null </code>,则对话框依赖于没有可见的窗口,并且它被放置在依赖于外观和感觉的位置,例如屏幕的中心。
     * 
     * 
     * @param   parent  the parent component of the dialog;
     *                  can be <code>null</code>
     * @param   approveButtonText the text of the <code>ApproveButton</code>
     * @return  the return state of the file chooser on popdown:
     * <ul>
     * <li>JFileChooser.CANCEL_OPTION
     * <li>JFileChooser.APPROVE_OPTION
     * <li>JFileChooser.ERROR_OPTION if an error occurs or the
     *                  dialog is dismissed
     * </ul>
     * @exception HeadlessException if GraphicsEnvironment.isHeadless()
     * returns true.
     * @see java.awt.GraphicsEnvironment#isHeadless
     */
    public int showDialog(Component parent, String approveButtonText)
        throws HeadlessException {
        if (dialog != null) {
            // Prevent to show second instance of dialog if the previous one still exists
            return JFileChooser.ERROR_OPTION;
        }

        if(approveButtonText != null) {
            setApproveButtonText(approveButtonText);
            setDialogType(CUSTOM_DIALOG);
        }
        dialog = createDialog(parent);
        dialog.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                returnValue = CANCEL_OPTION;
            }
        });
        returnValue = ERROR_OPTION;
        rescanCurrentDirectory();

        dialog.show();
        firePropertyChange("JFileChooserDialogIsClosingProperty", dialog, null);

        // Remove all components from dialog. The MetalFileChooserUI.installUI() method (and other LAFs)
        // registers AWT listener for dialogs and produces memory leaks. It happens when
        // installUI invoked after the showDialog method.
        dialog.getContentPane().removeAll();
        dialog.dispose();
        dialog = null;
        return returnValue;
    }

    /**
     * Creates and returns a new <code>JDialog</code> wrapping
     * <code>this</code> centered on the <code>parent</code>
     * in the <code>parent</code>'s frame.
     * This method can be overriden to further manipulate the dialog,
     * to disable resizing, set the location, etc. Example:
     * <pre>
     *     class MyFileChooser extends JFileChooser {
     *         protected JDialog createDialog(Component parent) throws HeadlessException {
     *             JDialog dialog = super.createDialog(parent);
     *             dialog.setLocation(300, 200);
     *             dialog.setResizable(false);
     *             return dialog;
     *         }
     *     }
     * </pre>
     *
     * <p>
     *  创建并返回以<code> parent </code>的框架中<code> parent </code>为中心的新<code> JDialog </code>包装<此方法可以覆盖以进一步操作对话框,禁用调整大小,设置位置等。
     * 示例：。
     * <pre>
     *  class MyFileChooser extends JFileChooser {protected JDialog createDialog(Component parent)throws HeadlessException {JDialog dialog = super.createDialog(parent); dialog.setLocation(300,200); dialog.setResizable(false);返回对话框; }
     * }。
     * </pre>
     * 
     * 
     * @param   parent  the parent component of the dialog;
     *                  can be <code>null</code>
     * @return a new <code>JDialog</code> containing this instance
     * @exception HeadlessException if GraphicsEnvironment.isHeadless()
     * returns true.
     * @see java.awt.GraphicsEnvironment#isHeadless
     * @since 1.4
     */
    protected JDialog createDialog(Component parent) throws HeadlessException {
        FileChooserUI ui = getUI();
        String title = ui.getDialogTitle(this);
        putClientProperty(AccessibleContext.ACCESSIBLE_DESCRIPTION_PROPERTY,
                          title);

        JDialog dialog;
        Window window = JOptionPane.getWindowForComponent(parent);
        if (window instanceof Frame) {
            dialog = new JDialog((Frame)window, title, true);
        } else {
            dialog = new JDialog((Dialog)window, title, true);
        }
        dialog.setComponentOrientation(this.getComponentOrientation());

        Container contentPane = dialog.getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(this, BorderLayout.CENTER);

        if (JDialog.isDefaultLookAndFeelDecorated()) {
            boolean supportsWindowDecorations =
            UIManager.getLookAndFeel().getSupportsWindowDecorations();
            if (supportsWindowDecorations) {
                dialog.getRootPane().setWindowDecorationStyle(JRootPane.FILE_CHOOSER_DIALOG);
            }
        }
        dialog.pack();
        dialog.setLocationRelativeTo(parent);

        return dialog;
    }

    // **************************
    // ***** Dialog Options *****
    // **************************

    /**
     * Returns the value of the <code>controlButtonsAreShown</code>
     * property.
     *
     * <p>
     *  返回<code> controlButtonsAreShown </code>属性的值。
     * 
     * 
     * @return   the value of the <code>controlButtonsAreShown</code>
     *     property
     *
     * @see #setControlButtonsAreShown
     * @since 1.3
     */
    public boolean getControlButtonsAreShown() {
        return controlsShown;
    }


    /**
     * Sets the property
     * that indicates whether the <i>approve</i> and <i>cancel</i>
     * buttons are shown in the file chooser.  This property
     * is <code>true</code> by default.  Look and feels
     * that always show these buttons will ignore the value
     * of this property.
     * This method fires a property-changed event,
     * using the string value of
     * <code>CONTROL_BUTTONS_ARE_SHOWN_CHANGED_PROPERTY</code>
     * as the name of the property.
     *
     * <p>
     * 设置指示<i>批准</i>和<i>取消</i>按钮是否显示在文件选择器中的属性。默认情况下,此属性为<code> true </code>。看看和感觉总是显示这些按钮将忽略此属性的值。
     * 此方法触发属性已更改的事件,使用字符串值<code> CONTROL_BUTTONS_ARE_SHOWN_CHANGED_PROPERTY </code>作为属性的名称。
     * 
     * 
     * @param b <code>false</code> if control buttons should not be
     *    shown; otherwise, <code>true</code>
     *
     * @beaninfo
     *   preferred: true
     *       bound: true
     * description: Sets whether the approve &amp; cancel buttons are shown.
     *
     * @see #getControlButtonsAreShown
     * @see #CONTROL_BUTTONS_ARE_SHOWN_CHANGED_PROPERTY
     * @since 1.3
     */
    public void setControlButtonsAreShown(boolean b) {
        if(controlsShown == b) {
            return;
        }
        boolean oldValue = controlsShown;
        controlsShown = b;
        firePropertyChange(CONTROL_BUTTONS_ARE_SHOWN_CHANGED_PROPERTY, oldValue, controlsShown);
    }

    /**
     * Returns the type of this dialog.  The default is
     * <code>JFileChooser.OPEN_DIALOG</code>.
     *
     * <p>
     *  返回此对话框的类型。默认值为<code> JFileChooser.OPEN_DIALOG </code>。
     * 
     * 
     * @return   the type of dialog to be displayed:
     * <ul>
     * <li>JFileChooser.OPEN_DIALOG
     * <li>JFileChooser.SAVE_DIALOG
     * <li>JFileChooser.CUSTOM_DIALOG
     * </ul>
     *
     * @see #setDialogType
     */
    public int getDialogType() {
        return dialogType;
    }

    /**
     * Sets the type of this dialog. Use <code>OPEN_DIALOG</code> when you
     * want to bring up a file chooser that the user can use to open a file.
     * Likewise, use <code>SAVE_DIALOG</code> for letting the user choose
     * a file for saving.
     * Use <code>CUSTOM_DIALOG</code> when you want to use the file
     * chooser in a context other than "Open" or "Save".
     * For instance, you might want to bring up a file chooser that allows
     * the user to choose a file to execute. Note that you normally would not
     * need to set the <code>JFileChooser</code> to use
     * <code>CUSTOM_DIALOG</code>
     * since a call to <code>setApproveButtonText</code> does this for you.
     * The default dialog type is <code>JFileChooser.OPEN_DIALOG</code>.
     *
     * <p>
     *  设置此对话框的类型。当您想要启动用户可以用来打开文件的文件选择器时,使用<code> OPEN_DIALOG </code>。
     * 同样,使用<code> SAVE_DIALOG </code>让用户选择要保存的文件。
     * 当您要在除"打开"或"保存"以外的上下文中使用文件选择器时,请使用<code> CUSTOM_DIALOG </code>。例如,您可能需要启动一个文件选择器,允许用户选择要执行的文件。
     * 注意,通常不需要设置<code> JFileChooser </code>来使用<code> CUSTOM_DIALOG </code>,因为调用<code> setApproveButtonText 
     * </code>默认对话框类型为<code> JFileChooser.OPEN_DIALOG </code>。
     * 当您要在除"打开"或"保存"以外的上下文中使用文件选择器时,请使用<code> CUSTOM_DIALOG </code>。例如,您可能需要启动一个文件选择器,允许用户选择要执行的文件。
     * 
     * 
     * @param dialogType the type of dialog to be displayed:
     * <ul>
     * <li>JFileChooser.OPEN_DIALOG
     * <li>JFileChooser.SAVE_DIALOG
     * <li>JFileChooser.CUSTOM_DIALOG
     * </ul>
     *
     * @exception IllegalArgumentException if <code>dialogType</code> is
     *                          not legal
     * @beaninfo
     *   preferred: true
     *       bound: true
     * description: The type (open, save, custom) of the JFileChooser.
     *        enum:
     *              OPEN_DIALOG JFileChooser.OPEN_DIALOG
     *              SAVE_DIALOG JFileChooser.SAVE_DIALOG
     *              CUSTOM_DIALOG JFileChooser.CUSTOM_DIALOG
     *
     * @see #getDialogType
     * @see #setApproveButtonText
     */
    // PENDING(jeff) - fire button text change property
    public void setDialogType(int dialogType) {
        if(this.dialogType == dialogType) {
            return;
        }
        if(!(dialogType == OPEN_DIALOG || dialogType == SAVE_DIALOG || dialogType == CUSTOM_DIALOG)) {
            throw new IllegalArgumentException("Incorrect Dialog Type: " + dialogType);
        }
        int oldValue = this.dialogType;
        this.dialogType = dialogType;
        if(dialogType == OPEN_DIALOG || dialogType == SAVE_DIALOG) {
            setApproveButtonText(null);
        }
        firePropertyChange(DIALOG_TYPE_CHANGED_PROPERTY, oldValue, dialogType);
    }

    /**
     * Sets the string that goes in the <code>JFileChooser</code> window's
     * title bar.
     *
     * <p>
     *  设置<code> JFileChooser </code>窗口标题栏中的字符串。
     * 
     * 
     * @param dialogTitle the new <code>String</code> for the title bar
     *
     * @beaninfo
     *   preferred: true
     *       bound: true
     * description: The title of the JFileChooser dialog window.
     *
     * @see #getDialogTitle
     *
     */
    public void setDialogTitle(String dialogTitle) {
        String oldValue = this.dialogTitle;
        this.dialogTitle = dialogTitle;
        if(dialog != null) {
            dialog.setTitle(dialogTitle);
        }
        firePropertyChange(DIALOG_TITLE_CHANGED_PROPERTY, oldValue, dialogTitle);
    }

    /**
     * Gets the string that goes in the <code>JFileChooser</code>'s titlebar.
     *
     * <p>
     *  获取<code> JFileChooser </code>的标题栏中的字符串。
     * 
     * 
     * @see #setDialogTitle
     */
    public String getDialogTitle() {
        return dialogTitle;
    }

    // ************************************
    // ***** JFileChooser View Options *****
    // ************************************



    /**
     * Sets the tooltip text used in the <code>ApproveButton</code>.
     * If <code>null</code>, the UI object will determine the button's text.
     *
     * @beaninfo
     *   preferred: true
     *       bound: true
     * description: The tooltip text for the ApproveButton.
     *
     * <p>
     *  设置<code> ApproveButton </code>中使用的工具提示文本。如果<code> null </code>,UI对象将确定按钮的文本。
     * 
     * @beaninfo preferred：true bound：true description：ApproveButton的工具提示文本。
     * 
     * 
     * @param toolTipText the tooltip text for the approve button
     * @see #setApproveButtonText
     * @see #setDialogType
     * @see #showDialog
     */
    public void setApproveButtonToolTipText(String toolTipText) {
        if(approveButtonToolTipText == toolTipText) {
            return;
        }
        String oldValue = approveButtonToolTipText;
        approveButtonToolTipText = toolTipText;
        firePropertyChange(APPROVE_BUTTON_TOOL_TIP_TEXT_CHANGED_PROPERTY, oldValue, approveButtonToolTipText);
    }


    /**
     * Returns the tooltip text used in the <code>ApproveButton</code>.
     * If <code>null</code>, the UI object will determine the button's text.
     *
     * <p>
     *  返回<code> ApproveButton </code>中使用的工具提示文本。如果<code> null </code>,UI对象将确定按钮的文本。
     * 
     * 
     * @return the tooltip text used for the approve button
     *
     * @see #setApproveButtonText
     * @see #setDialogType
     * @see #showDialog
     */
    public String getApproveButtonToolTipText() {
        return approveButtonToolTipText;
    }

    /**
     * Returns the approve button's mnemonic.
     * <p>
     *  返回批准按钮的助记符。
     * 
     * 
     * @return an integer value for the mnemonic key
     *
     * @see #setApproveButtonMnemonic
     */
    public int getApproveButtonMnemonic() {
        return approveButtonMnemonic;
    }

    /**
     * Sets the approve button's mnemonic using a numeric keycode.
     *
     * <p>
     *  使用数字键编码设置批准按钮的助记符。
     * 
     * 
     * @param mnemonic  an integer value for the mnemonic key
     *
     * @beaninfo
     *   preferred: true
     *       bound: true
     * description: The mnemonic key accelerator for the ApproveButton.
     *
     * @see #getApproveButtonMnemonic
     */
    public void setApproveButtonMnemonic(int mnemonic) {
        if(approveButtonMnemonic == mnemonic) {
           return;
        }
        int oldValue = approveButtonMnemonic;
        approveButtonMnemonic = mnemonic;
        firePropertyChange(APPROVE_BUTTON_MNEMONIC_CHANGED_PROPERTY, oldValue, approveButtonMnemonic);
    }

    /**
     * Sets the approve button's mnemonic using a character.
     * <p>
     *  使用字符设置批准按钮的助记符。
     * 
     * 
     * @param mnemonic  a character value for the mnemonic key
     *
     * @see #getApproveButtonMnemonic
     */
    public void setApproveButtonMnemonic(char mnemonic) {
        int vk = (int) mnemonic;
        if(vk >= 'a' && vk <='z') {
            vk -= ('a' - 'A');
        }
        setApproveButtonMnemonic(vk);
    }


    /**
     * Sets the text used in the <code>ApproveButton</code> in the
     * <code>FileChooserUI</code>.
     *
     * @beaninfo
     *   preferred: true
     *       bound: true
     * description: The text that goes in the ApproveButton.
     *
     * <p>
     *  设置<code> FileChooserUI </code>中<code> ApproveButton </code>中使用的文本。
     * 
     *  @beaninfo preferred：true bound：true description：在ApproveButton中的文本。
     * 
     * 
     * @param approveButtonText the text used in the <code>ApproveButton</code>
     *
     * @see #getApproveButtonText
     * @see #setDialogType
     * @see #showDialog
     */
    // PENDING(jeff) - have ui set this on dialog type change
    public void setApproveButtonText(String approveButtonText) {
        if(this.approveButtonText == approveButtonText) {
            return;
        }
        String oldValue = this.approveButtonText;
        this.approveButtonText = approveButtonText;
        firePropertyChange(APPROVE_BUTTON_TEXT_CHANGED_PROPERTY, oldValue, approveButtonText);
    }

    /**
     * Returns the text used in the <code>ApproveButton</code> in the
     * <code>FileChooserUI</code>.
     * If <code>null</code>, the UI object will determine the button's text.
     *
     * Typically, this would be "Open" or "Save".
     *
     * <p>
     *  返回<code> FileChooserUI </code>中<code> ApproveButton </code>中使用的文本。
     * 如果<code> null </code>,UI对象将确定按钮的文本。
     * 
     *  通常,这将是"打开"或"保存"。
     * 
     * 
     * @return the text used in the <code>ApproveButton</code>
     *
     * @see #setApproveButtonText
     * @see #setDialogType
     * @see #showDialog
     */
    public String getApproveButtonText() {
        return approveButtonText;
    }

    /**
     * Gets the list of user choosable file filters.
     *
     * <p>
     *  获取用户可选择的文件过滤器列表。
     * 
     * 
     * @return a <code>FileFilter</code> array containing all the choosable
     *         file filters
     *
     * @see #addChoosableFileFilter
     * @see #removeChoosableFileFilter
     * @see #resetChoosableFileFilters
     */
    public FileFilter[] getChoosableFileFilters() {
        FileFilter[] filterArray = new FileFilter[filters.size()];
        filters.copyInto(filterArray);
        return filterArray;
    }

    /**
     * Adds a filter to the list of user choosable file filters.
     * For information on setting the file selection mode, see
     * {@link #setFileSelectionMode setFileSelectionMode}.
     *
     * <p>
     *  将过滤器添加到用户可选文件过滤器列表。有关设置文件选择模式的信息,请参阅{@link #setFileSelectionMode setFileSelectionMode}。
     * 
     * 
     * @param filter the <code>FileFilter</code> to add to the choosable file
     *               filter list
     *
     * @beaninfo
     *   preferred: true
     *       bound: true
     * description: Adds a filter to the list of user choosable file filters.
     *
     * @see #getChoosableFileFilters
     * @see #removeChoosableFileFilter
     * @see #resetChoosableFileFilters
     * @see #setFileSelectionMode
     */
    public void addChoosableFileFilter(FileFilter filter) {
        if(filter != null && !filters.contains(filter)) {
            FileFilter[] oldValue = getChoosableFileFilters();
            filters.addElement(filter);
            firePropertyChange(CHOOSABLE_FILE_FILTER_CHANGED_PROPERTY, oldValue, getChoosableFileFilters());
            if (fileFilter == null && filters.size() == 1) {
                setFileFilter(filter);
            }
        }
    }

    /**
     * Removes a filter from the list of user choosable file filters. Returns
     * true if the file filter was removed.
     *
     * <p>
     *  从用户可选择的文件过滤器列表中删除过滤器。如果文件过滤器已删除,则返回true。
     * 
     * 
     * @see #addChoosableFileFilter
     * @see #getChoosableFileFilters
     * @see #resetChoosableFileFilters
     */
    public boolean removeChoosableFileFilter(FileFilter f) {
        int index = filters.indexOf(f);
        if (index >= 0) {
            if(getFileFilter() == f) {
                FileFilter aaff = getAcceptAllFileFilter();
                if (isAcceptAllFileFilterUsed() && (aaff != f)) {
                    // choose default filter if it is used
                    setFileFilter(aaff);
                }
                else if (index > 0) {
                    // choose the first filter, because it is not removed
                    setFileFilter(filters.get(0));
                }
                else if (filters.size() > 1) {
                    // choose the second filter, because the first one is removed
                    setFileFilter(filters.get(1));
                }
                else {
                    // no more filters
                    setFileFilter(null);
                }
            }
            FileFilter[] oldValue = getChoosableFileFilters();
            filters.removeElement(f);
            firePropertyChange(CHOOSABLE_FILE_FILTER_CHANGED_PROPERTY, oldValue, getChoosableFileFilters());
            return true;
        } else {
            return false;
        }
    }

    /**
     * Resets the choosable file filter list to its starting state. Normally,
     * this removes all added file filters while leaving the
     * <code>AcceptAll</code> file filter.
     *
     * <p>
     *  将可选择的文件过滤器列表重置为其开始状态。通常,这会删除所有添加的文件过滤器,而保留<code> AcceptAll </code>文件过滤器。
     * 
     * 
     * @see #addChoosableFileFilter
     * @see #getChoosableFileFilters
     * @see #removeChoosableFileFilter
     */
    public void resetChoosableFileFilters() {
        FileFilter[] oldValue = getChoosableFileFilters();
        setFileFilter(null);
        filters.removeAllElements();
        if(isAcceptAllFileFilterUsed()) {
           addChoosableFileFilter(getAcceptAllFileFilter());
        }
        firePropertyChange(CHOOSABLE_FILE_FILTER_CHANGED_PROPERTY, oldValue, getChoosableFileFilters());
    }

    /**
     * Returns the <code>AcceptAll</code> file filter.
     * For example, on Microsoft Windows this would be All Files (*.*).
     * <p>
     *  返回<code> AcceptAll </code>文件过滤器。例如,在Microsoft Windows上,这将是所有文件(*。*)。
     * 
     */
    public FileFilter getAcceptAllFileFilter() {
        FileFilter filter = null;
        if(getUI() != null) {
            filter = getUI().getAcceptAllFileFilter(this);
        }
        return filter;
    }

   /**
    * Returns whether the <code>AcceptAll FileFilter</code> is used.
    * <p>
    *  返回是否使用了<code> AcceptAll FileFilter </code>。
    * 
    * 
    * @return true if the <code>AcceptAll FileFilter</code> is used
    * @see #setAcceptAllFileFilterUsed
    * @since 1.3
    */
    public boolean isAcceptAllFileFilterUsed() {
        return useAcceptAllFileFilter;
    }

   /**
    * Determines whether the <code>AcceptAll FileFilter</code> is used
    * as an available choice in the choosable filter list.
    * If false, the <code>AcceptAll</code> file filter is removed from
    * the list of available file filters.
    * If true, the <code>AcceptAll</code> file filter will become the
    * the actively used file filter.
    *
    * @beaninfo
    *   preferred: true
    *       bound: true
    * description: Sets whether the AcceptAll FileFilter is used as an available choice in the choosable filter list.
    *
    * <p>
    * 确定是否将<code> AcceptAll FileFilter </code>用作可选择过滤器列表中的可用选项。
    * 如果为false,则从可用文件过滤器列表中删除<code> AcceptAll </code>文件过滤器。
    * 如果为true,则<code> AcceptAll </code>文件过滤器将成为主动使用的文件过滤器。
    * 
    *  @beaninfo preferred：true bound：true描述：设置AcceptAll FileFilter是否用作可选择过滤器列表中的可用选项。
    * 
    * 
    * @see #isAcceptAllFileFilterUsed
    * @see #getAcceptAllFileFilter
    * @see #setFileFilter
    * @since 1.3
    */
    public void setAcceptAllFileFilterUsed(boolean b) {
        boolean oldValue = useAcceptAllFileFilter;
        useAcceptAllFileFilter = b;
        if(!b) {
            removeChoosableFileFilter(getAcceptAllFileFilter());
        } else {
            removeChoosableFileFilter(getAcceptAllFileFilter());
            addChoosableFileFilter(getAcceptAllFileFilter());
        }
        firePropertyChange(ACCEPT_ALL_FILE_FILTER_USED_CHANGED_PROPERTY, oldValue, useAcceptAllFileFilter);
    }

    /**
     * Returns the accessory component.
     *
     * <p>
     *  返回辅助组件。
     * 
     * 
     * @return this JFileChooser's accessory component, or null
     * @see #setAccessory
     */
    public JComponent getAccessory() {
        return accessory;
    }

    /**
     * Sets the accessory component. An accessory is often used to show a
     * preview image of the selected file; however, it can be used for anything
     * that the programmer wishes, such as extra custom file chooser controls.
     *
     * <p>
     * Note: if there was a previous accessory, you should unregister
     * any listeners that the accessory might have registered with the
     * file chooser.
     *
     * @beaninfo
     *   preferred: true
     *       bound: true
     * description: Sets the accessory component on the JFileChooser.
     * <p>
     *  设置辅助组件。附件通常用于显示所选文件的预览图像;然而,它可以用于程序员所希望的任何东西,例如额外的自定义文件选择器控件。
     * 
     * <p>
     *  注意：如果有以前的附件,您应该注销附件可能已使用文件选择器注册的任何侦听器。
     * 
     *  @beaninfo preferred：true bound：true description：设置JFileChooser上的辅助组件。
     * 
     */
    public void setAccessory(JComponent newAccessory) {
        JComponent oldValue = accessory;
        accessory = newAccessory;
        firePropertyChange(ACCESSORY_CHANGED_PROPERTY, oldValue, accessory);
    }

    /**
     * Sets the <code>JFileChooser</code> to allow the user to just
     * select files, just select
     * directories, or select both files and directories.  The default is
     * <code>JFilesChooser.FILES_ONLY</code>.
     *
     * <p>
     *  设置<code> JFileChooser </code>允许用户只选择文件,只选择目录,或选择文件和目录。默认值为<code> JFilesChooser.FILES_ONLY </code>。
     * 
     * 
     * @param mode the type of files to be displayed:
     * <ul>
     * <li>JFileChooser.FILES_ONLY
     * <li>JFileChooser.DIRECTORIES_ONLY
     * <li>JFileChooser.FILES_AND_DIRECTORIES
     * </ul>
     *
     * @exception IllegalArgumentException  if <code>mode</code> is an
     *                          illegal file selection mode
     * @beaninfo
     *   preferred: true
     *       bound: true
     * description: Sets the types of files that the JFileChooser can choose.
     *        enum: FILES_ONLY JFileChooser.FILES_ONLY
     *              DIRECTORIES_ONLY JFileChooser.DIRECTORIES_ONLY
     *              FILES_AND_DIRECTORIES JFileChooser.FILES_AND_DIRECTORIES
     *
     *
     * @see #getFileSelectionMode
     */
    public void setFileSelectionMode(int mode) {
        if(fileSelectionMode == mode) {
            return;
        }

        if ((mode == FILES_ONLY) || (mode == DIRECTORIES_ONLY) || (mode == FILES_AND_DIRECTORIES)) {
           int oldValue = fileSelectionMode;
           fileSelectionMode = mode;
           firePropertyChange(FILE_SELECTION_MODE_CHANGED_PROPERTY, oldValue, fileSelectionMode);
        } else {
           throw new IllegalArgumentException("Incorrect Mode for file selection: " + mode);
        }
    }

    /**
     * Returns the current file-selection mode.  The default is
     * <code>JFilesChooser.FILES_ONLY</code>.
     *
     * <p>
     *  返回当前文件选择模式。默认值为<code> JFilesChooser.FILES_ONLY </code>。
     * 
     * 
     * @return the type of files to be displayed, one of the following:
     * <ul>
     * <li>JFileChooser.FILES_ONLY
     * <li>JFileChooser.DIRECTORIES_ONLY
     * <li>JFileChooser.FILES_AND_DIRECTORIES
     * </ul>
     * @see #setFileSelectionMode
     */
    public int getFileSelectionMode() {
        return fileSelectionMode;
    }

    /**
     * Convenience call that determines if files are selectable based on the
     * current file selection mode.
     *
     * <p>
     *  根据当前文件选择模式确定文件是否可选的方便调用。
     * 
     * 
     * @see #setFileSelectionMode
     * @see #getFileSelectionMode
     */
    public boolean isFileSelectionEnabled() {
        return ((fileSelectionMode == FILES_ONLY) || (fileSelectionMode == FILES_AND_DIRECTORIES));
    }

    /**
     * Convenience call that determines if directories are selectable based
     * on the current file selection mode.
     *
     * <p>
     *  基于当前文件选择模式确定目录是否可选的方便调用。
     * 
     * 
     * @see #setFileSelectionMode
     * @see #getFileSelectionMode
     */
    public boolean isDirectorySelectionEnabled() {
        return ((fileSelectionMode == DIRECTORIES_ONLY) || (fileSelectionMode == FILES_AND_DIRECTORIES));
    }

    /**
     * Sets the file chooser to allow multiple file selections.
     *
     * <p>
     * 将文件选择器设置为允许多个文件选择。
     * 
     * 
     * @param b true if multiple files may be selected
     * @beaninfo
     *       bound: true
     * description: Sets multiple file selection mode.
     *
     * @see #isMultiSelectionEnabled
     */
    public void setMultiSelectionEnabled(boolean b) {
        if(multiSelectionEnabled == b) {
            return;
        }
        boolean oldValue = multiSelectionEnabled;
        multiSelectionEnabled = b;
        firePropertyChange(MULTI_SELECTION_ENABLED_CHANGED_PROPERTY, oldValue, multiSelectionEnabled);
    }

    /**
     * Returns true if multiple files can be selected.
     * <p>
     *  如果可以选择多个文件,则返回true。
     * 
     * 
     * @return true if multiple files can be selected
     * @see #setMultiSelectionEnabled
     */
    public boolean isMultiSelectionEnabled() {
        return multiSelectionEnabled;
    }


    /**
     * Returns true if hidden files are not shown in the file chooser;
     * otherwise, returns false.
     *
     * <p>
     *  如果隐藏文件未显示在文件选择器中,则返回true;否则返回false。
     * 
     * 
     * @return the status of the file hiding property
     * @see #setFileHidingEnabled
     */
    public boolean isFileHidingEnabled() {
        return useFileHiding;
    }

    /**
     * Sets file hiding on or off. If true, hidden files are not shown
     * in the file chooser. The job of determining which files are
     * shown is done by the <code>FileView</code>.
     *
     * @beaninfo
     *   preferred: true
     *       bound: true
     * description: Sets file hiding on or off.
     *
     * <p>
     *  设置文件隐藏开或关。如果为true,则隐藏文件不会显示在文件选择器中。确定显示哪些文件的工作由<code> FileView </code>完成。
     * 
     *  @beaninfo preferred：true bound：true描述：设置文件隐藏开或关。
     * 
     * 
     * @param b the boolean value that determines whether file hiding is
     *          turned on
     * @see #isFileHidingEnabled
     */
    public void setFileHidingEnabled(boolean b) {
        // Dump showFilesListener since we'll ignore it from now on
        if (showFilesListener != null) {
            Toolkit.getDefaultToolkit().removePropertyChangeListener(SHOW_HIDDEN_PROP, showFilesListener);
            showFilesListener = null;
        }
        boolean oldValue = useFileHiding;
        useFileHiding = b;
        firePropertyChange(FILE_HIDING_CHANGED_PROPERTY, oldValue, useFileHiding);
    }

    /**
     * Sets the current file filter. The file filter is used by the
     * file chooser to filter out files from the user's view.
     *
     * @beaninfo
     *   preferred: true
     *       bound: true
     * description: Sets the File Filter used to filter out files of type.
     *
     * <p>
     *  设置当前文件过滤器。文件过滤器由文件选择器用于从用户视图中过滤出文件。
     * 
     *  @beaninfo preference：true bound：true description：设置用于过滤类型文件的文件过滤器。
     * 
     * 
     * @param filter the new current file filter to use
     * @see #getFileFilter
     */
    public void setFileFilter(FileFilter filter) {
        FileFilter oldValue = fileFilter;
        fileFilter = filter;
        if (filter != null) {
            if (isMultiSelectionEnabled() && selectedFiles != null && selectedFiles.length > 0) {
                Vector<File> fList = new Vector<File>();
                boolean failed = false;
                for (File file : selectedFiles) {
                    if (filter.accept(file)) {
                        fList.add(file);
                    } else {
                        failed = true;
                    }
                }
                if (failed) {
                    setSelectedFiles((fList.size() == 0) ? null : fList.toArray(new File[fList.size()]));
                }
            } else if (selectedFile != null && !filter.accept(selectedFile)) {
                setSelectedFile(null);
            }
        }
        firePropertyChange(FILE_FILTER_CHANGED_PROPERTY, oldValue, fileFilter);
    }


    /**
     * Returns the currently selected file filter.
     *
     * <p>
     *  返回当前选择的文件过滤器。
     * 
     * 
     * @return the current file filter
     * @see #setFileFilter
     * @see #addChoosableFileFilter
     */
    public FileFilter getFileFilter() {
        return fileFilter;
    }

    /**
     * Sets the file view to used to retrieve UI information, such as
     * the icon that represents a file or the type description of a file.
     *
     * @beaninfo
     *   preferred: true
     *       bound: true
     * description: Sets the File View used to get file type information.
     *
     * <p>
     *  设置用于检索UI信息的文件视图,例如表示文件的图标或文件的类型描述。
     * 
     *  @beaninfo preferred：true bound：true description：设置用于获取文件类型信息的文件视图。
     * 
     * 
     * @see #getFileView
     */
    public void setFileView(FileView fileView) {
        FileView oldValue = this.fileView;
        this.fileView = fileView;
        firePropertyChange(FILE_VIEW_CHANGED_PROPERTY, oldValue, fileView);
    }

    /**
     * Returns the current file view.
     *
     * <p>
     *  返回当前文件视图。
     * 
     * 
     * @see #setFileView
     */
    public FileView getFileView() {
        return fileView;
    }

    // ******************************
    // *****FileView delegation *****
    // ******************************

    // NOTE: all of the following methods attempt to delegate
    // first to the client set fileView, and if <code>null</code> is returned
    // (or there is now client defined fileView) then calls the
    // UI's default fileView.

    /**
     * Returns the filename.
     * <p>
     *  返回文件名。
     * 
     * 
     * @param f the <code>File</code>
     * @return the <code>String</code> containing the filename for
     *          <code>f</code>
     * @see FileView#getName
     */
    public String getName(File f) {
        String filename = null;
        if(f != null) {
            if(getFileView() != null) {
                filename = getFileView().getName(f);
            }

            FileView uiFileView = getUI().getFileView(this);

            if(filename == null && uiFileView != null) {
                filename = uiFileView.getName(f);
            }
        }
        return filename;
    }

    /**
     * Returns the file description.
     * <p>
     *  返回文件描述。
     * 
     * 
     * @param f the <code>File</code>
     * @return the <code>String</code> containing the file description for
     *          <code>f</code>
     * @see FileView#getDescription
     */
    public String getDescription(File f) {
        String description = null;
        if(f != null) {
            if(getFileView() != null) {
                description = getFileView().getDescription(f);
            }

            FileView uiFileView = getUI().getFileView(this);

            if(description == null && uiFileView != null) {
                description = uiFileView.getDescription(f);
            }
        }
        return description;
    }

    /**
     * Returns the file type.
     * <p>
     *  返回文件类型。
     * 
     * 
     * @param f the <code>File</code>
     * @return the <code>String</code> containing the file type description for
     *          <code>f</code>
     * @see FileView#getTypeDescription
     */
    public String getTypeDescription(File f) {
        String typeDescription = null;
        if(f != null) {
            if(getFileView() != null) {
                typeDescription = getFileView().getTypeDescription(f);
            }

            FileView uiFileView = getUI().getFileView(this);

            if(typeDescription == null && uiFileView != null) {
                typeDescription = uiFileView.getTypeDescription(f);
            }
        }
        return typeDescription;
    }

    /**
     * Returns the icon for this file or type of file, depending
     * on the system.
     * <p>
     *  根据系统,返回此文件的图标或文件类型。
     * 
     * 
     * @param f the <code>File</code>
     * @return the <code>Icon</code> for this file, or type of file
     * @see FileView#getIcon
     */
    public Icon getIcon(File f) {
        Icon icon = null;
        if (f != null) {
            if(getFileView() != null) {
                icon = getFileView().getIcon(f);
            }

            FileView uiFileView = getUI().getFileView(this);

            if(icon == null && uiFileView != null) {
                icon = uiFileView.getIcon(f);
            }
        }
        return icon;
    }

    /**
     * Returns true if the file (directory) can be visited.
     * Returns false if the directory cannot be traversed.
     * <p>
     *  如果可以访问文件(目录),则返回true。如果无法遍历目录,则返回false。
     * 
     * 
     * @param f the <code>File</code>
     * @return true if the file/directory can be traversed, otherwise false
     * @see FileView#isTraversable
     */
    public boolean isTraversable(File f) {
        Boolean traversable = null;
        if (f != null) {
            if (getFileView() != null) {
                traversable = getFileView().isTraversable(f);
            }

            FileView uiFileView = getUI().getFileView(this);

            if (traversable == null && uiFileView != null) {
                traversable = uiFileView.isTraversable(f);
            }
            if (traversable == null) {
                traversable = getFileSystemView().isTraversable(f);
            }
        }
        return (traversable != null && traversable.booleanValue());
    }

    /**
     * Returns true if the file should be displayed.
     * <p>
     *  如果应显示文件,则返回true。
     * 
     * 
     * @param f the <code>File</code>
     * @return true if the file should be displayed, otherwise false
     * @see FileFilter#accept
     */
    public boolean accept(File f) {
        boolean shown = true;
        if(f != null && fileFilter != null) {
            shown = fileFilter.accept(f);
        }
        return shown;
    }

    /**
     * Sets the file system view that the <code>JFileChooser</code> uses for
     * accessing and creating file system resources, such as finding
     * the floppy drive and getting a list of root drives.
     * <p>
     *  设置<code> JFileChooser </code>用于访问和创建文件系统资源的文件系统视图,例如查找软盘驱动器和获取根驱动器列表。
     * 
     * 
     * @param fsv  the new <code>FileSystemView</code>
     *
     * @beaninfo
     *      expert: true
     *       bound: true
     * description: Sets the FileSytemView used to get filesystem information.
     *
     * @see FileSystemView
     */
    public void setFileSystemView(FileSystemView fsv) {
        FileSystemView oldValue = fileSystemView;
        fileSystemView = fsv;
        firePropertyChange(FILE_SYSTEM_VIEW_CHANGED_PROPERTY, oldValue, fileSystemView);
    }

    /**
     * Returns the file system view.
     * <p>
     * 返回文件系统视图。
     * 
     * 
     * @return the <code>FileSystemView</code> object
     * @see #setFileSystemView
     */
    public FileSystemView getFileSystemView() {
        return fileSystemView;
    }

    // **************************
    // ***** Event Handling *****
    // **************************

    /**
     * Called by the UI when the user hits the Approve button
     * (labeled "Open" or "Save", by default). This can also be
     * called by the programmer.
     * This method causes an action event to fire
     * with the command string equal to
     * <code>APPROVE_SELECTION</code>.
     *
     * <p>
     *  当用户点击批准按钮(默认情况下标记为"打开"或"保存")时由UI调用。这也可以由程序员调用。此方法会使用等于<code> APPROVE_SELECTION </code>的命令字符串触发操作事件。
     * 
     * 
     * @see #APPROVE_SELECTION
     */
    public void approveSelection() {
        returnValue = APPROVE_OPTION;
        if(dialog != null) {
            dialog.setVisible(false);
        }
        fireActionPerformed(APPROVE_SELECTION);
    }

    /**
     * Called by the UI when the user chooses the Cancel button.
     * This can also be called by the programmer.
     * This method causes an action event to fire
     * with the command string equal to
     * <code>CANCEL_SELECTION</code>.
     *
     * <p>
     *  当用户选择"取消"按钮时由UI调用。这也可以由程序员调用。此方法会使用等于<code> CANCEL_SELECTION </code>的命令字符串触发操作事件。
     * 
     * 
     * @see #CANCEL_SELECTION
     */
    public void cancelSelection() {
        returnValue = CANCEL_OPTION;
        if(dialog != null) {
            dialog.setVisible(false);
        }
        fireActionPerformed(CANCEL_SELECTION);
    }

    /**
     * Adds an <code>ActionListener</code> to the file chooser.
     *
     * <p>
     *  向文件选择器添加<code> ActionListener </code>。
     * 
     * 
     * @param l  the listener to be added
     *
     * @see #approveSelection
     * @see #cancelSelection
     */
    public void addActionListener(ActionListener l) {
        listenerList.add(ActionListener.class, l);
    }

    /**
     * Removes an <code>ActionListener</code> from the file chooser.
     *
     * <p>
     *  从文件选择器中删除<code> ActionListener </code>。
     * 
     * 
     * @param l  the listener to be removed
     *
     * @see #addActionListener
     */
    public void removeActionListener(ActionListener l) {
        listenerList.remove(ActionListener.class, l);
    }

    /**
     * Returns an array of all the action listeners
     * registered on this file chooser.
     *
     * <p>
     *  返回在此文件选择器上注册的所有操作侦听器的数组。
     * 
     * 
     * @return all of this file chooser's <code>ActionListener</code>s
     *         or an empty
     *         array if no action listeners are currently registered
     *
     * @see #addActionListener
     * @see #removeActionListener
     *
     * @since 1.4
     */
    public ActionListener[] getActionListeners() {
        return listenerList.getListeners(ActionListener.class);
    }

    /**
     * Notifies all listeners that have registered interest for
     * notification on this event type. The event instance
     * is lazily created using the <code>command</code> parameter.
     *
     * <p>
     *  通知所有已注册有关此事件类型的通知的收件人。事件实例使用<code>命令</code>参数进行延迟创建。
     * 
     * 
     * @see EventListenerList
     */
    protected void fireActionPerformed(String command) {
        // Guaranteed to return a non-null array
        Object[] listeners = listenerList.getListenerList();
        long mostRecentEventTime = EventQueue.getMostRecentEventTime();
        int modifiers = 0;
        AWTEvent currentEvent = EventQueue.getCurrentEvent();
        if (currentEvent instanceof InputEvent) {
            modifiers = ((InputEvent)currentEvent).getModifiers();
        } else if (currentEvent instanceof ActionEvent) {
            modifiers = ((ActionEvent)currentEvent).getModifiers();
        }
        ActionEvent e = null;
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length-2; i>=0; i-=2) {
            if (listeners[i]==ActionListener.class) {
                // Lazily create the event:
                if (e == null) {
                    e = new ActionEvent(this, ActionEvent.ACTION_PERFORMED,
                                        command, mostRecentEventTime,
                                        modifiers);
                }
                ((ActionListener)listeners[i+1]).actionPerformed(e);
            }
        }
    }

    private static class WeakPCL implements PropertyChangeListener {
        WeakReference<JFileChooser> jfcRef;

        public WeakPCL(JFileChooser jfc) {
            jfcRef = new WeakReference<JFileChooser>(jfc);
        }
        public void propertyChange(PropertyChangeEvent ev) {
            assert ev.getPropertyName().equals(SHOW_HIDDEN_PROP);
            JFileChooser jfc = jfcRef.get();
            if (jfc == null) {
                // Our JFileChooser is no longer around, so we no longer need to
                // listen for PropertyChangeEvents.
                Toolkit.getDefaultToolkit().removePropertyChangeListener(SHOW_HIDDEN_PROP, this);
            }
            else {
                boolean oldValue = jfc.useFileHiding;
                jfc.useFileHiding = !((Boolean)ev.getNewValue()).booleanValue();
                jfc.firePropertyChange(FILE_HIDING_CHANGED_PROPERTY, oldValue, jfc.useFileHiding);
            }
        }
    }

    // *********************************
    // ***** Pluggable L&F methods *****
    // *********************************

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
        if (isAcceptAllFileFilterUsed()) {
            removeChoosableFileFilter(getAcceptAllFileFilter());
        }
        FileChooserUI ui = ((FileChooserUI)UIManager.getUI(this));
        if (fileSystemView == null) {
            // We were probably deserialized
            setFileSystemView(FileSystemView.getFileSystemView());
        }
        setUI(ui);

        if(isAcceptAllFileFilterUsed()) {
            addChoosableFileFilter(getAcceptAllFileFilter());
        }
    }

    /**
     * Returns a string that specifies the name of the L&amp;F class
     * that renders this component.
     *
     * <p>
     *  返回一个字符串,指定呈现此组件的L&amp; F类的名称。
     * 
     * 
     * @return the string "FileChooserUI"
     * @see JComponent#getUIClassID
     * @see UIDefaults#getUI
     * @beaninfo
     *        expert: true
     *   description: A string that specifies the name of the L&amp;F class.
     */
    public String getUIClassID() {
        return uiClassID;
    }

    /**
     * Gets the UI object which implements the L&amp;F for this component.
     *
     * <p>
     *  获取实现此组件的L&amp; F的UI对象。
     * 
     * 
     * @return the FileChooserUI object that implements the FileChooserUI L&amp;F
     */
    public FileChooserUI getUI() {
        return (FileChooserUI) ui;
    }

    /**
     * See <code>readObject</code> and <code>writeObject</code> in
     * <code>JComponent</code> for more
     * information about serialization in Swing.
     * <p>
     *  有关Swing中序列化的更多信息,请参阅<code> readComponent </code>中的<code> readObject </code>和<code> writeObject </code>
     * 。
     * 
     */
    private void readObject(java.io.ObjectInputStream in)
            throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        installShowFilesListener();
    }

    /**
     * See <code>readObject</code> and <code>writeObject</code> in
     * <code>JComponent</code> for more
     * information about serialization in Swing.
     * <p>
     *  有关Swing中序列化的更多信息,请参阅<code> readComponent </code>中的<code> readObject </code>和<code> writeObject </code>
     * 。
     * 
     */
    private void writeObject(ObjectOutputStream s) throws IOException {
        FileSystemView fsv = null;

        if (isAcceptAllFileFilterUsed()) {
            //The AcceptAllFileFilter is UI specific, it will be reset by
            //updateUI() after deserialization
            removeChoosableFileFilter(getAcceptAllFileFilter());
        }
        if (fileSystemView.equals(FileSystemView.getFileSystemView())) {
            //The default FileSystemView is platform specific, it will be
            //reset by updateUI() after deserialization
            fsv = fileSystemView;
            fileSystemView = null;
        }
        s.defaultWriteObject();
        if (fsv != null) {
            fileSystemView = fsv;
        }
        if (isAcceptAllFileFilterUsed()) {
            addChoosableFileFilter(getAcceptAllFileFilter());
        }
        if (getUIClassID().equals(uiClassID)) {
            byte count = JComponent.getWriteObjCounter(this);
            JComponent.setWriteObjCounter(this, --count);
            if (count == 0 && ui != null) {
                ui.installUI(this);
            }
        }
    }


    /**
     * Returns a string representation of this <code>JFileChooser</code>.
     * This method
     * is intended to be used only for debugging purposes, and the
     * content and format of the returned string may vary between
     * implementations. The returned string may be empty but may not
     * be <code>null</code>.
     *
     * <p>
     * 返回此<code> JFileChooser </code>的字符串表示形式。此方法仅用于调试目的,并且返回的字符串的内容和格式可能因实现而异。
     * 返回的字符串可能为空,但可能不是<code> null </code>。
     * 
     * 
     * @return  a string representation of this <code>JFileChooser</code>
     */
    protected String paramString() {
        String approveButtonTextString = (approveButtonText != null ?
                                          approveButtonText: "");
        String dialogTitleString = (dialogTitle != null ?
                                    dialogTitle: "");
        String dialogTypeString;
        if (dialogType == OPEN_DIALOG) {
            dialogTypeString = "OPEN_DIALOG";
        } else if (dialogType == SAVE_DIALOG) {
            dialogTypeString = "SAVE_DIALOG";
        } else if (dialogType == CUSTOM_DIALOG) {
            dialogTypeString = "CUSTOM_DIALOG";
        } else dialogTypeString = "";
        String returnValueString;
        if (returnValue == CANCEL_OPTION) {
            returnValueString = "CANCEL_OPTION";
        } else if (returnValue == APPROVE_OPTION) {
            returnValueString = "APPROVE_OPTION";
        } else if (returnValue == ERROR_OPTION) {
            returnValueString = "ERROR_OPTION";
        } else returnValueString = "";
        String useFileHidingString = (useFileHiding ?
                                    "true" : "false");
        String fileSelectionModeString;
        if (fileSelectionMode == FILES_ONLY) {
            fileSelectionModeString = "FILES_ONLY";
        } else if (fileSelectionMode == DIRECTORIES_ONLY) {
            fileSelectionModeString = "DIRECTORIES_ONLY";
        } else if (fileSelectionMode == FILES_AND_DIRECTORIES) {
            fileSelectionModeString = "FILES_AND_DIRECTORIES";
        } else fileSelectionModeString = "";
        String currentDirectoryString = (currentDirectory != null ?
                                         currentDirectory.toString() : "");
        String selectedFileString = (selectedFile != null ?
                                     selectedFile.toString() : "");

        return super.paramString() +
        ",approveButtonText=" + approveButtonTextString +
        ",currentDirectory=" + currentDirectoryString +
        ",dialogTitle=" + dialogTitleString +
        ",dialogType=" + dialogTypeString +
        ",fileSelectionMode=" + fileSelectionModeString +
        ",returnValue=" + returnValueString +
        ",selectedFile=" + selectedFileString +
        ",useFileHiding=" + useFileHidingString;
    }

/////////////////
// Accessibility support
////////////////

    protected AccessibleContext accessibleContext = null;

    /**
     * Gets the AccessibleContext associated with this JFileChooser.
     * For file choosers, the AccessibleContext takes the form of an
     * AccessibleJFileChooser.
     * A new AccessibleJFileChooser instance is created if necessary.
     *
     * <p>
     *  获取与此JFileChooser相关联的AccessibleContext。对于文件选择器,AccessibleContext采用AccessibleJFileChooser的形式。
     * 如果需要,将创建一个新的AccessibleJFileChooser实例。
     * 
     * 
     * @return an AccessibleJFileChooser that serves as the
     *         AccessibleContext of this JFileChooser
     */
    public AccessibleContext getAccessibleContext() {
        if (accessibleContext == null) {
            accessibleContext = new AccessibleJFileChooser();
        }
        return accessibleContext;
    }

    /**
     * This class implements accessibility support for the
     * <code>JFileChooser</code> class.  It provides an implementation of the
     * Java Accessibility API appropriate to file chooser user-interface
     * elements.
     * <p>
     *  此类实现<code> JFileChooser </code>类的辅助功能支持。它提供了适合文件选择器用户界面元素的Java可访问性API的实现。
     * 
     */
    protected class AccessibleJFileChooser extends AccessibleJComponent {

        /**
         * Gets the role of this object.
         *
         * <p>
         *  获取此对象的作用。
         * 
         * @return an instance of AccessibleRole describing the role of the
         * object
         * @see AccessibleRole
         */
        public AccessibleRole getAccessibleRole() {
            return AccessibleRole.FILE_CHOOSER;
        }

    } // inner class AccessibleJFileChooser

}
