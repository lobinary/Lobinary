/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2008, Oracle and/or its affiliates. All rights reserved.
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

package javax.swing.plaf;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileView;
import java.io.File;

/**
 * Pluggable look and feel interface for <code>JFileChooser</code>.
 *
 * <p>
 *  <code> JFileChooser </code>的可插拔外观界面。
 * 
 * 
 * @author Jeff Dinkins
 */

public abstract class FileChooserUI extends ComponentUI
{
    public abstract FileFilter getAcceptAllFileFilter(JFileChooser fc);
    public abstract FileView getFileView(JFileChooser fc);

    public abstract String getApproveButtonText(JFileChooser fc);
    public abstract String getDialogTitle(JFileChooser fc);

    public abstract void rescanCurrentDirectory(JFileChooser fc);
    public abstract void ensureFileIsVisible(JFileChooser fc, File f);

    /**
     * Returns default button for current <code>LookAndFeel</code>.
     * <code>JFileChooser</code> will use this button as default button
     * for dialog windows.
     *
     * <p>
     *  返回当前<code> LookAndFeel </code>的默认按钮。 <code> JFileChooser </code>将使用此按钮作为对话框窗口的默认按钮。
     * 
     * @since 1.7
     */
    public JButton getDefaultButton(JFileChooser fc) {
        return null;
    }
}
