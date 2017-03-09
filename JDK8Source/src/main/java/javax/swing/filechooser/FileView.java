/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 2013, Oracle and/or its affiliates. All rights reserved.
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

package javax.swing.filechooser;

import java.io.File;
import javax.swing.*;

/**
 * <code>FileView</code> defines an abstract class that can be implemented
 * to provide the filechooser with UI information for a <code>File</code>.
 * Each L&amp;F <code>JFileChooserUI</code> object implements this
 * class to pass back the correct icons and type descriptions specific to
 * that L&amp;F. For example, the Microsoft Windows L&amp;F returns the
 * generic Windows icons for directories and generic files.
 * Additionally, you may want to provide your own <code>FileView</code> to
 * <code>JFileChooser</code> to return different icons or additional
 * information using {@link javax.swing.JFileChooser#setFileView}.
 *
 * <p>
 *
 * <code>JFileChooser</code> first looks to see if there is a user defined
 * <code>FileView</code>, if there is, it gets type information from
 * there first. If <code>FileView</code> returns <code>null</code> for
 * any method, <code>JFileChooser</code> then uses the L&amp;F specific
 * view to get the information.
 * So, for example, if you provide a <code>FileView</code> class that
 * returns an <code>Icon</code> for JPG files, and returns <code>null</code>
 * icons for all other files, the UI's <code>FileView</code> will provide
 * default icons for all other files.
 *
 * <p>
 *
 * For an example implementation of a simple file view, see
 * <code><i>yourJDK</i>/demo/jfc/FileChooserDemo/ExampleFileView.java</code>.
 * For more information and examples see
 * <a
 href="https://docs.oracle.com/javase/tutorial/uiswing/components/filechooser.html">How to Use File Choosers</a>,
 * a section in <em>The Java Tutorial</em>.
 *
 * <p>
 *  <code> FileView </code>定义了一个抽象类,可以实现该类来为文件选择器提供<code> File </code>的UI信息。
 * 每个L&amp; F <code> JFileChooserUI </code>对象实现此类以传回特定于该L&amp; F的正确图标和类型描述。
 * 例如,Microsoft Windows L&amp; F返回目录和通用文件的通用Windows图标。
 * 此外,您可能需要向<code> JFileChooser </code>提供您自己的<code> FileView </code>,以使用{@link javax.swing.JFileChooser#setFileView}
 * 返回不同的图标或其他信息。
 * 例如,Microsoft Windows L&amp; F返回目录和通用文件的通用Windows图标。
 * 
 * <p>
 * 
 *  <code> JFileChooser </code>首先查看是否有用户定义的<code> FileView </code>,如果存在,它首先从那里获取类型信息。
 * 如果<code> FileView </code>对于任何方法返回<code> null </code>,则<code> JFileChooser </code>然后使用L&amp; F特定视图来获取信
 * 息。
 *  <code> JFileChooser </code>首先查看是否有用户定义的<code> FileView </code>,如果存在,它首先从那里获取类型信息。
 * 因此,例如,如果您提供一个返回用于JPG文件的<code> Icon </code>的<code> FileView </code>类,并为所有其他文件返回<code> null </code>图标, 
 * UI的<code> FileView </code>将为所有其他文件提供默认图标。
 *  <code> JFileChooser </code>首先查看是否有用户定义的<code> FileView </code>,如果存在,它首先从那里获取类型信息。
 * 
 * <p>
 * 
 * 有关简单文件视图的示例实现,请参见<code> <i> yourJDK </i> /demo/jfc/FileChooserDemo/ExampleFileView.java </code>。
 * 
 * @see javax.swing.JFileChooser
 *
 * @author Jeff Dinkins
 *
 */
public abstract class FileView {
    /**
     * The name of the file. Normally this would be simply
     * <code>f.getName()</code>.
     * <p>
     * 有关详细信息和示例,请参阅<a href="https://docs.oracle.com/javase/tutorial/uiswing/components/filechooser.html">如何
     * 使用文件选择器</a>,<em> Java教程</em>。
     * 有关简单文件视图的示例实现,请参见<code> <i> yourJDK </i> /demo/jfc/FileChooserDemo/ExampleFileView.java </code>。
     * 
     */
    public String getName(File f) {
        return null;
    };

    /**
     * A human readable description of the file. For example,
     * a file named <i>jag.jpg</i> might have a description that read:
     * "A JPEG image file of James Gosling's face".
     * <p>
     *  文件的名称。通常这将是简单的<code> f.getName()</code>。
     * 
     */
    public String getDescription(File f) {
        return null;
    }

    /**
     * A human readable description of the type of the file. For
     * example, a <code>jpg</code> file might have a type description of:
     * "A JPEG Compressed Image File"
     * <p>
     *  文件的人可读的描述。例如,名为<i> jag.jpg </i>的文件的描述可能是："James Gosling的脸的JPEG图像文件"。
     * 
     */
    public String getTypeDescription(File f) {
        return null;
    }

    /**
     * The icon that represents this file in the <code>JFileChooser</code>.
     * <p>
     *  文件类型的可读描述。例如,<code> jpg </code>文件可能具有以下类型描述："A JPEG Compressed Image File"
     * 
     */
    public Icon getIcon(File f) {
        return null;
    }

    /**
     * Whether the directory is traversable or not. This might be
     * useful, for example, if you want a directory to represent
     * a compound document and don't want the user to descend into it.
     * <p>
     *  在<code> JFileChooser </code>中表示此文件的图标。
     * 
     */
    public Boolean isTraversable(File f) {
        return null;
    }

}
