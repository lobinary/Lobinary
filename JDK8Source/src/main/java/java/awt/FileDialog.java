/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1995, 2013, Oracle and/or its affiliates. All rights reserved.
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
package java.awt;

import java.awt.peer.FileDialogPeer;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.File;
import sun.awt.AWTAccessor;

/**
 * The <code>FileDialog</code> class displays a dialog window
 * from which the user can select a file.
 * <p>
 * Since it is a modal dialog, when the application calls
 * its <code>show</code> method to display the dialog,
 * it blocks the rest of the application until the user has
 * chosen a file.
 *
 * <p>
 *  <code> FileDialog </code>类显示一个对话窗口,用户可以从中选择文件。
 * <p>
 *  由于它是一个模式对话框,当应用程序调用其<code> show </code>方法显示对话框时,它会阻止应用程序的其余部分,直到用户选择了一个文件。
 * 
 * 
 * @see Window#show
 *
 * @author      Sami Shaio
 * @author      Arthur van Hoff
 * @since       JDK1.0
 */
public class FileDialog extends Dialog {

    /**
     * This constant value indicates that the purpose of the file
     * dialog window is to locate a file from which to read.
     * <p>
     *  此常量值表示文件对话框窗口的目的是定位要从中读取的文件。
     * 
     */
    public static final int LOAD = 0;

    /**
     * This constant value indicates that the purpose of the file
     * dialog window is to locate a file to which to write.
     * <p>
     *  此常量值表示文件对话框窗口的目的是定位要写入的文件。
     * 
     */
    public static final int SAVE = 1;

    /*
     * There are two <code>FileDialog</code> modes: <code>LOAD</code> and
     * <code>SAVE</code>.
     * This integer will represent one or the other.
     * If the mode is not specified it will default to <code>LOAD</code>.
     *
     * <p>
     *  有两种<code> FileDialog </code>模式：<code> LOAD </code>和<code> SAVE </code>。此整数将表示一个或另一个。
     * 如果没有指定模式,它将默认为<code> LOAD </code>。
     * 
     * 
     * @serial
     * @see getMode()
     * @see setMode()
     * @see java.awt.FileDialog#LOAD
     * @see java.awt.FileDialog#SAVE
     */
    int mode;

    /*
     * The string specifying the directory to display
     * in the file dialog.  This variable may be <code>null</code>.
     *
     * <p>
     *  指定要在文件对话框中显示的目录的字符串。此变量可以是<code> null </code>。
     * 
     * 
     * @serial
     * @see getDirectory()
     * @see setDirectory()
     */
    String dir;

    /*
     * The string specifying the initial value of the
     * filename text field in the file dialog.
     * This variable may be <code>null</code>.
     *
     * <p>
     *  指定文件对话框中文件名文本字段的初始值的字符串。此变量可以是<code> null </code>。
     * 
     * 
     * @serial
     * @see getFile()
     * @see setFile()
     */
    String file;

    /**
     * Contains the File instances for all the files that the user selects.
     *
     * <p>
     *  包含用户选择的所有文件的文件实例。
     * 
     * 
     * @serial
     * @see #getFiles
     * @since 1.7
     */
    private File[] files;

    /**
     * Represents whether the file dialog allows the multiple file selection.
     *
     * <p>
     *  表示文件对话框是否允许多个文件选择。
     * 
     * 
     * @serial
     * @see #setMultipleMode
     * @see #isMultipleMode
     * @since 1.7
     */
    private boolean multipleMode = false;

    /*
     * The filter used as the file dialog's filename filter.
     * The file dialog will only be displaying files whose
     * names are accepted by this filter.
     * This variable may be <code>null</code>.
     *
     * <p>
     *  用作文件对话框的文件名过滤器的过滤器。文件对话框将只显示此过滤器接受其名称的文件。此变量可以是<code> null </code>。
     * 
     * 
     * @serial
     * @see #getFilenameFilter()
     * @see #setFilenameFilter()
     * @see FileNameFilter
     */
    FilenameFilter filter;

    private static final String base = "filedlg";
    private static int nameCounter = 0;

    /*
     * JDK 1.1 serialVersionUID
     * <p>
     *  JDK 1.1 serialVersionUID
     * 
     */
     private static final long serialVersionUID = 5035145889651310422L;


    static {
        /* ensure that the necessary native libraries are loaded */
        Toolkit.loadLibraries();
        if (!GraphicsEnvironment.isHeadless()) {
            initIDs();
        }
    }

    static {
        AWTAccessor.setFileDialogAccessor(
            new AWTAccessor.FileDialogAccessor() {
                public void setFiles(FileDialog fileDialog, File files[]) {
                    fileDialog.setFiles(files);
                }
                public void setFile(FileDialog fileDialog, String file) {
                    fileDialog.file = ("".equals(file)) ? null : file;
                }
                public void setDirectory(FileDialog fileDialog, String directory) {
                    fileDialog.dir = ("".equals(directory)) ? null : directory;
                }
                public boolean isMultipleMode(FileDialog fileDialog) {
                    synchronized (fileDialog.getObjectLock()) {
                        return fileDialog.multipleMode;
                    }
                }
            });
    }

    /**
     * Initialize JNI field and method IDs for fields that may be
       accessed from C.
     * <p>
     * 初始化可从C访问的字段的JNI字段和方法ID。
     * 
     */
    private static native void initIDs();

    /**
     * Creates a file dialog for loading a file.  The title of the
     * file dialog is initially empty.  This is a convenience method for
     * <code>FileDialog(parent, "", LOAD)</code>.
     *
     * <p>
     *  创建用于加载文件的文件对话框。文件对话框的标题最初为空。这是<code> FileDialog(parent,"",LOAD)</code>的一个方便的方法。
     * 
     * 
     * @param parent the owner of the dialog
     * @since JDK1.1
     */
    public FileDialog(Frame parent) {
        this(parent, "", LOAD);
    }

    /**
     * Creates a file dialog window with the specified title for loading
     * a file. The files shown are those in the current directory.
     * This is a convenience method for
     * <code>FileDialog(parent, title, LOAD)</code>.
     *
     * <p>
     *  创建具有用于加载文件的指定标题的文件对话框窗口。显示的文件是当前目录中的文件。这是<code> FileDialog(parent,title,LOAD)</code>的一个方便的方法。
     * 
     * 
     * @param     parent   the owner of the dialog
     * @param     title    the title of the dialog
     */
    public FileDialog(Frame parent, String title) {
        this(parent, title, LOAD);
    }

    /**
     * Creates a file dialog window with the specified title for loading
     * or saving a file.
     * <p>
     * If the value of <code>mode</code> is <code>LOAD</code>, then the
     * file dialog is finding a file to read, and the files shown are those
     * in the current directory.   If the value of
     * <code>mode</code> is <code>SAVE</code>, the file dialog is finding
     * a place to write a file.
     *
     * <p>
     *  创建具有用于加载或保存文件的指定标题的文件对话框窗口。
     * <p>
     *  如果<code> mode </code>的值为<code> LOAD </code>,则文件对话框将找到要读取的文件,并且显示的文件是当前目录中的文件。
     * 如果<code> mode </code>的值为<code> SAVE </code>,则文件对话框找到一个写入文件的位置。
     * 
     * 
     * @param     parent   the owner of the dialog
     * @param     title   the title of the dialog
     * @param     mode   the mode of the dialog; either
     *          <code>FileDialog.LOAD</code> or <code>FileDialog.SAVE</code>
     * @exception  IllegalArgumentException if an illegal file
     *                 dialog mode is supplied
     * @see       java.awt.FileDialog#LOAD
     * @see       java.awt.FileDialog#SAVE
     */
    public FileDialog(Frame parent, String title, int mode) {
        super(parent, title, true);
        this.setMode(mode);
        setLayout(null);
    }

    /**
     * Creates a file dialog for loading a file.  The title of the
     * file dialog is initially empty.  This is a convenience method for
     * <code>FileDialog(parent, "", LOAD)</code>.
     *
     * <p>
     *  创建用于加载文件的文件对话框。文件对话框的标题最初为空。这是<code> FileDialog(parent,"",LOAD)</code>的一个方便的方法。
     * 
     * 
     * @param     parent   the owner of the dialog
     * @exception java.lang.IllegalArgumentException if the <code>parent</code>'s
     *            <code>GraphicsConfiguration</code>
     *            is not from a screen device;
     * @exception java.lang.IllegalArgumentException if <code>parent</code>
     *            is <code>null</code>; this exception is always thrown when
     *            <code>GraphicsEnvironment.isHeadless</code>
     *            returns <code>true</code>
     * @see java.awt.GraphicsEnvironment#isHeadless
     * @since 1.5
     */
    public FileDialog(Dialog parent) {
        this(parent, "", LOAD);
    }

    /**
     * Creates a file dialog window with the specified title for loading
     * a file. The files shown are those in the current directory.
     * This is a convenience method for
     * <code>FileDialog(parent, title, LOAD)</code>.
     *
     * <p>
     *  创建具有用于加载文件的指定标题的文件对话框窗口。显示的文件是当前目录中的文件。这是<code> FileDialog(parent,title,LOAD)</code>的一个方便的方法。
     * 
     * 
     * @param     parent   the owner of the dialog
     * @param     title    the title of the dialog; a <code>null</code> value
     *                     will be accepted without causing a
     *                     <code>NullPointerException</code> to be thrown
     * @exception java.lang.IllegalArgumentException if the <code>parent</code>'s
     *            <code>GraphicsConfiguration</code>
     *            is not from a screen device;
     * @exception java.lang.IllegalArgumentException if <code>parent</code>
     *            is <code>null</code>; this exception is always thrown when
     *            <code>GraphicsEnvironment.isHeadless</code>
     *            returns <code>true</code>
     * @see java.awt.GraphicsEnvironment#isHeadless
     * @since     1.5
     */
    public FileDialog(Dialog parent, String title) {
        this(parent, title, LOAD);
    }

    /**
     * Creates a file dialog window with the specified title for loading
     * or saving a file.
     * <p>
     * If the value of <code>mode</code> is <code>LOAD</code>, then the
     * file dialog is finding a file to read, and the files shown are those
     * in the current directory.   If the value of
     * <code>mode</code> is <code>SAVE</code>, the file dialog is finding
     * a place to write a file.
     *
     * <p>
     *  创建具有用于加载或保存文件的指定标题的文件对话框窗口。
     * <p>
     * 如果<code> mode </code>的值为<code> LOAD </code>,则文件对话框将找到要读取的文件,并且显示的文件是当前目录中的文件。
     * 如果<code> mode </code>的值为<code> SAVE </code>,则文件对话框找到一个写入文件的位置。
     * 
     * 
     * @param     parent   the owner of the dialog
     * @param     title    the title of the dialog; a <code>null</code> value
     *                     will be accepted without causing a
     *                     <code>NullPointerException</code> to be thrown
     * @param     mode     the mode of the dialog; either
     *                     <code>FileDialog.LOAD</code> or <code>FileDialog.SAVE</code>
     * @exception java.lang.IllegalArgumentException if an illegal
     *            file dialog mode is supplied;
     * @exception java.lang.IllegalArgumentException if the <code>parent</code>'s
     *            <code>GraphicsConfiguration</code>
     *            is not from a screen device;
     * @exception java.lang.IllegalArgumentException if <code>parent</code>
     *            is <code>null</code>; this exception is always thrown when
     *            <code>GraphicsEnvironment.isHeadless</code>
     *            returns <code>true</code>
     * @see       java.awt.GraphicsEnvironment#isHeadless
     * @see       java.awt.FileDialog#LOAD
     * @see       java.awt.FileDialog#SAVE
     * @since     1.5
     */
    public FileDialog(Dialog parent, String title, int mode) {
        super(parent, title, true);
        this.setMode(mode);
        setLayout(null);
    }

    /**
     * Constructs a name for this component. Called by <code>getName()</code>
     * when the name is <code>null</code>.
     * <p>
     *  构造此组件的名称。当名称为<code> null </code>时,由<code> getName()</code>调用。
     * 
     */
    String constructComponentName() {
        synchronized (FileDialog.class) {
            return base + nameCounter++;
        }
    }

    /**
     * Creates the file dialog's peer.  The peer allows us to change the look
     * of the file dialog without changing its functionality.
     * <p>
     *  创建文件对话框的对等体。对等体允许我们改变文件对话框的外观,而不改变其功能。
     * 
     */
    public void addNotify() {
        synchronized(getTreeLock()) {
            if (parent != null && parent.getPeer() == null) {
                parent.addNotify();
            }
            if (peer == null)
                peer = getToolkit().createFileDialog(this);
            super.addNotify();
        }
    }

    /**
     * Indicates whether this file dialog box is for loading from a file
     * or for saving to a file.
     *
     * <p>
     *  指示此文件对话框是用于从文件加载还是保存到文件。
     * 
     * 
     * @return   the mode of this file dialog window, either
     *               <code>FileDialog.LOAD</code> or
     *               <code>FileDialog.SAVE</code>
     * @see      java.awt.FileDialog#LOAD
     * @see      java.awt.FileDialog#SAVE
     * @see      java.awt.FileDialog#setMode
     */
    public int getMode() {
        return mode;
    }

    /**
     * Sets the mode of the file dialog.  If <code>mode</code> is not
     * a legal value, an exception will be thrown and <code>mode</code>
     * will not be set.
     *
     * <p>
     *  设置文件对话框的模式。如果<code> mode </code>不是合法值,则会抛出异常,并且不会设置<code> mode </code>。
     * 
     * 
     * @param      mode  the mode for this file dialog, either
     *                 <code>FileDialog.LOAD</code> or
     *                 <code>FileDialog.SAVE</code>
     * @see        java.awt.FileDialog#LOAD
     * @see        java.awt.FileDialog#SAVE
     * @see        java.awt.FileDialog#getMode
     * @exception  IllegalArgumentException if an illegal file
     *                 dialog mode is supplied
     * @since      JDK1.1
     */
    public void setMode(int mode) {
        switch (mode) {
          case LOAD:
          case SAVE:
            this.mode = mode;
            break;
          default:
            throw new IllegalArgumentException("illegal file dialog mode");
        }
    }

    /**
     * Gets the directory of this file dialog.
     *
     * <p>
     *  获取此文件对话框的目录。
     * 
     * 
     * @return  the (potentially <code>null</code> or invalid)
     *          directory of this <code>FileDialog</code>
     * @see       java.awt.FileDialog#setDirectory
     */
    public String getDirectory() {
        return dir;
    }

    /**
     * Sets the directory of this file dialog window to be the
     * specified directory. Specifying a <code>null</code> or an
     * invalid directory implies an implementation-defined default.
     * This default will not be realized, however, until the user
     * has selected a file. Until this point, <code>getDirectory()</code>
     * will return the value passed into this method.
     * <p>
     * Specifying "" as the directory is exactly equivalent to
     * specifying <code>null</code> as the directory.
     *
     * <p>
     *  将此文件对话框窗口的目录设置为指定的目录。指定<code> null </code>或无效目录意味着实现定义的默认值。但是,在用户选择文件之前,不会实现此默认值。
     * 直到这一点,<code> getDirectory()</code>将返回传递到此方法的值。
     * <p>
     *  指定""作为目录等同于将<code> null </code>指定为目录。
     * 
     * 
     * @param     dir   the specified directory
     * @see       java.awt.FileDialog#getDirectory
     */
    public void setDirectory(String dir) {
        this.dir = (dir != null && dir.equals("")) ? null : dir;
        FileDialogPeer peer = (FileDialogPeer)this.peer;
        if (peer != null) {
            peer.setDirectory(this.dir);
        }
    }

    /**
     * Gets the selected file of this file dialog.  If the user
     * selected <code>CANCEL</code>, the returned file is <code>null</code>.
     *
     * <p>
     *  获取此文件对话框的所选文件。如果用户选择<code> CANCEL </code>,则返回的文件为<code> null </code>。
     * 
     * 
     * @return    the currently selected file of this file dialog window,
     *                or <code>null</code> if none is selected
     * @see       java.awt.FileDialog#setFile
     */
    public String getFile() {
        return file;
    }

    /**
     * Returns files that the user selects.
     * <p>
     * If the user cancels the file dialog,
     * then the method returns an empty array.
     *
     * <p>
     *  返回用户选择的文件。
     * <p>
     *  如果用户取消文件对话框,则该方法返回一个空数组。
     * 
     * 
     * @return    files that the user selects or an empty array
     *            if the user cancels the file dialog.
     * @see       #setFile(String)
     * @see       #getFile
     * @since 1.7
     */
    public File[] getFiles() {
        synchronized (getObjectLock()) {
            if (files != null) {
                return files.clone();
            } else {
                return new File[0];
            }
        }
    }

    /**
     * Stores the names of all the files that the user selects.
     *
     * Note that the method is private and it's intended to be used
     * by the peers through the AWTAccessor API.
     *
     * <p>
     * 存储用户选择的所有文件的名称。
     * 
     *  请注意,该方法是私有的,并且打算由对等体通过AWTAccessor API使用。
     * 
     * 
     * @param files     the array that contains the short names of
     *                  all the files that the user selects.
     *
     * @see #getFiles
     * @since 1.7
     */
    private void setFiles(File files[]) {
        synchronized (getObjectLock()) {
            this.files = files;
        }
    }

    /**
     * Sets the selected file for this file dialog window to be the
     * specified file. This file becomes the default file if it is set
     * before the file dialog window is first shown.
     * <p>
     * When the dialog is shown, the specified file is selected. The kind of
     * selection depends on the file existence, the dialog type, and the native
     * platform. E.g., the file could be highlighted in the file list, or a
     * file name editbox could be populated with the file name.
     * <p>
     * This method accepts either a full file path, or a file name with an
     * extension if used together with the {@code setDirectory} method.
     * <p>
     * Specifying "" as the file is exactly equivalent to specifying
     * {@code null} as the file.
     *
     * <p>
     *  将此文件对话框窗口的所选文件设置为指定的文件。如果在首次显示文件对话框窗口之前设置该文件,则该文件将成为默认文件。
     * <p>
     *  显示对话框时,将选择指定的文件。选择的种类取决于文件存在,对话框类型和本机平台。例如,文件可以在文件列表中突出显示,或者文件名编辑框可以用文件名填充。
     * <p>
     *  此方法接受完整的文件路径或带有扩展名的文件名(如果与{@code setDirectory}方法一起使用)。
     * <p>
     *  指定""作为文件完全等同于指定{@code null}作为文件。
     * 
     * 
     * @param    file   the file being set
     * @see      #getFile
     * @see      #getFiles
     */
    public void setFile(String file) {
        this.file = (file != null && file.equals("")) ? null : file;
        FileDialogPeer peer = (FileDialogPeer)this.peer;
        if (peer != null) {
            peer.setFile(this.file);
        }
    }

    /**
     * Enables or disables multiple file selection for the file dialog.
     *
     * <p>
     *  启用或禁用文件对话框的多个文件选择。
     * 
     * 
     * @param enable    if {@code true}, multiple file selection is enabled;
     *                  {@code false} - disabled.
     * @see #isMultipleMode
     * @since 1.7
     */
    public void setMultipleMode(boolean enable) {
        synchronized (getObjectLock()) {
            this.multipleMode = enable;
        }
    }

    /**
     * Returns whether the file dialog allows the multiple file selection.
     *
     * <p>
     *  返回文件对话框是否允许多个文件选择。
     * 
     * 
     * @return          {@code true} if the file dialog allows the multiple
     *                  file selection; {@code false} otherwise.
     * @see #setMultipleMode
     * @since 1.7
     */
    public boolean isMultipleMode() {
        synchronized (getObjectLock()) {
            return multipleMode;
        }
    }

    /**
     * Determines this file dialog's filename filter. A filename filter
     * allows the user to specify which files appear in the file dialog
     * window.  Filename filters do not function in Sun's reference
     * implementation for Microsoft Windows.
     *
     * <p>
     *  确定此文件对话框的文件名过滤器。文件名过滤器允许用户指定哪些文件出现在文件对话框窗口中。文件名过滤器在Sun的Microsoft Windows参考实现中不起作用。
     * 
     * 
     * @return    this file dialog's filename filter
     * @see       java.io.FilenameFilter
     * @see       java.awt.FileDialog#setFilenameFilter
     */
    public FilenameFilter getFilenameFilter() {
        return filter;
    }

    /**
     * Sets the filename filter for this file dialog window to the
     * specified filter.
     * Filename filters do not function in Sun's reference
     * implementation for Microsoft Windows.
     *
     * <p>
     *  将此文件对话框窗口的文件名过滤器设置为指定的过滤器。文件名过滤器在Sun的Microsoft Windows参考实现中不起作用。
     * 
     * 
     * @param   filter   the specified filter
     * @see     java.io.FilenameFilter
     * @see     java.awt.FileDialog#getFilenameFilter
     */
    public synchronized void setFilenameFilter(FilenameFilter filter) {
        this.filter = filter;
        FileDialogPeer peer = (FileDialogPeer)this.peer;
        if (peer != null) {
            peer.setFilenameFilter(filter);
        }
    }

    /**
     * Reads the <code>ObjectInputStream</code> and performs
     * a backwards compatibility check by converting
     * either a <code>dir</code> or a <code>file</code>
     * equal to an empty string to <code>null</code>.
     *
     * <p>
     * 读取<code> ObjectInputStream </code>,并通过将等于空字符串的<code> dir </code>或<code>文件</code>转换为<code> null <代码>。
     * 
     * 
     * @param s the <code>ObjectInputStream</code> to read
     */
    private void readObject(ObjectInputStream s)
        throws ClassNotFoundException, IOException
    {
        s.defaultReadObject();

        // 1.1 Compatibility: "" is not converted to null in 1.1
        if (dir != null && dir.equals("")) {
            dir = null;
        }
        if (file != null && file.equals("")) {
            file = null;
        }
    }

    /**
     * Returns a string representing the state of this <code>FileDialog</code>
     * window. This method is intended to be used only for debugging purposes,
     * and the content and format of the returned string may vary between
     * implementations. The returned string may be empty but may not be
     * <code>null</code>.
     *
     * <p>
     *  返回一个表示此<code> FileDialog </code>窗口状态的字符串。此方法仅用于调试目的,并且返回的字符串的内容和格式可能因实现而异。
     * 返回的字符串可能为空,但可能不是<code> null </code>。
     * 
     * @return  the parameter string of this file dialog window
     */
    protected String paramString() {
        String str = super.paramString();
        str += ",dir= " + dir;
        str += ",file= " + file;
        return str + ((mode == LOAD) ? ",load" : ",save");
    }

    boolean postsOldMouseEvents() {
        return false;
    }
}
