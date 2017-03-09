/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1995, 2007, Oracle and/or its affiliates. All rights reserved.
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

package java.awt.peer;

import java.awt.FileDialog;
import java.io.FilenameFilter;

/**
 * The peer interface for {@link FileDialog}.
 *
 * The peer interfaces are intended only for use in porting
 * the AWT. They are not intended for use by application
 * developers, and developers should not implement peers
 * nor invoke any of the peer methods directly on the peer
 * instances.
 * <p>
 *  {@link FileDialog}的对等接口。
 * 
 *  对等接口仅用于移植AWT。它们不是供应用程序开发人员使用的,并且开发人员不应实现对等体,也不应直接在对等实例上调用任何对等方法。
 * 
 */
public interface FileDialogPeer extends DialogPeer {

    /**
     * Sets the selected file for this file dialog.
     *
     * <p>
     *  设置此文件对话框的所选文件。
     * 
     * 
     * @param file the file to set as selected file, or {@code null} for
     *        no selected file
     *
     * @see FileDialog#setFile(String)
     */
    void setFile(String file);

    /**
     * Sets the current directory for this file dialog.
     *
     * <p>
     *  设置此文件对话框的当前目录。
     * 
     * 
     * @param dir the directory to set
     *
     * @see FileDialog#setDirectory(String)
     */
    void setDirectory(String dir);

    /**
     * Sets the filename filter for filtering the displayed files.
     *
     * <p>
     *  设置用于过滤显示的文件的文件名过滤器。
     * 
     * @param filter the filter to set
     *
     * @see FileDialog#setFilenameFilter(FilenameFilter)
     */
    void setFilenameFilter(FilenameFilter filter);
}
