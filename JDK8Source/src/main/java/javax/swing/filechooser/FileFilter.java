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

/**
 * <code>FileFilter</code> is an abstract class used by {@code JFileChooser}
 * for filtering the set of files shown to the user. See
 * {@code FileNameExtensionFilter} for an implementation that filters using
 * the file name extension.
 * <p>
 * A <code>FileFilter</code>
 * can be set on a <code>JFileChooser</code> to
 * keep unwanted files from appearing in the directory listing.
 * For an example implementation of a simple file filter, see
 * <code><i>yourJDK</i>/demo/jfc/FileChooserDemo/ExampleFileFilter.java</code>.
 * For more information and examples see
 * <a href="https://docs.oracle.com/javase/tutorial/uiswing/components/filechooser.html">How to Use File Choosers</a>,
 * a section in <em>The Java Tutorial</em>.
 *
 * <p>
 *  <code> FileFilter </code>是一个抽象类,由{@code JFileChooser}使用,用于过滤显示给用户的一组文件。
 * 有关使用文件扩展名过滤的实现,请参见{@code FileNameExtensionFilter}。
 * <p>
 *  可以在<code> JFileChooser </code>上设置<code> FileFilter </code>,以防止不需要的文件出现在目录列表中。
 * 有关简单文件过滤器的示例实现,请参见<code> <i> yourJDK </i> /demo/jfc/FileChooserDemo/ExampleFileFilter.java </code>。
 * 有关详细信息和示例,请参阅<a href="https://docs.oracle.com/javase/tutorial/uiswing/components/filechooser.html">如何
 * 使用文件选择器</a>,<em> Java教程</em>。
 * 有关简单文件过滤器的示例实现,请参见<code> <i> yourJDK </i> /demo/jfc/FileChooserDemo/ExampleFileFilter.java </code>。
 * 
 * @see FileNameExtensionFilter
 * @see javax.swing.JFileChooser#setFileFilter
 * @see javax.swing.JFileChooser#addChoosableFileFilter
 *
 * @author Jeff Dinkins
 */
public abstract class FileFilter {
    /**
     * Whether the given file is accepted by this filter.
     * <p>
     * 
     */
    public abstract boolean accept(File f);

    /**
     * The description of this filter. For example: "JPG and GIF Images"
     * <p>
     *  该过滤器是否接受给定文件。
     * 
     * 
     * @see FileView#getName
     */
    public abstract String getDescription();
}
