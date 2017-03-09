/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2006, Oracle and/or its affiliates. All rights reserved.
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
import java.util.Locale;

/**
 * An implementation of {@code FileFilter} that filters using a
 * specified set of extensions. The extension for a file is the
 * portion of the file name after the last ".". Files whose name does
 * not contain a "." have no file name extension. File name extension
 * comparisons are case insensitive.
 * <p>
 * The following example creates a
 * {@code FileNameExtensionFilter} that will show {@code jpg} files:
 * <pre>
 * FileFilter filter = new FileNameExtensionFilter("JPEG file", "jpg", "jpeg");
 * JFileChooser fileChooser = ...;
 * fileChooser.addChoosableFileFilter(filter);
 * </pre>
 *
 * <p>
 *  {@code FileFilter}的实现,它使用指定的扩展集过滤。文件的扩展名是文件名在最后一个"。"之后的部分。名称不包含"。"的文件。没有文件扩展名。文件扩展名比较不区分大小写。
 * <p>
 *  以下示例创建将显示{@code jpg}文件的{@code FileNameExtensionFilter}：
 * <pre>
 *  FileFilter filter = new FileNameExtensionFilter("JPEG file","jpg","jpeg"); JFileChooser fileChooser 
 * = ...; fileChooser.addChoosableFileFilter(filter);。
 * </pre>
 * 
 * 
 * @see FileFilter
 * @see javax.swing.JFileChooser#setFileFilter
 * @see javax.swing.JFileChooser#addChoosableFileFilter
 * @see javax.swing.JFileChooser#getFileFilter
 *
 * @since 1.6
 */
public final class FileNameExtensionFilter extends FileFilter {
    // Description of this filter.
    private final String description;
    // Known extensions.
    private final String[] extensions;
    // Cached ext
    private final String[] lowerCaseExtensions;

    /**
     * Creates a {@code FileNameExtensionFilter} with the specified
     * description and file name extensions. The returned {@code
     * FileNameExtensionFilter} will accept all directories and any
     * file with a file name extension contained in {@code extensions}.
     *
     * <p>
     *  创建具有指定的描述和文件扩展名的{@code FileNameExtensionFilter}。
     * 返回的{@code FileNameExtensionFilter}将接受所有目录和任何包含在{@code extensions}中的文件扩展名的文件。
     * 
     * 
     * @param description textual description for the filter, may be
     *                    {@code null}
     * @param extensions the accepted file name extensions
     * @throws IllegalArgumentException if extensions is {@code null}, empty,
     *         contains {@code null}, or contains an empty string
     * @see #accept
     */
    public FileNameExtensionFilter(String description, String... extensions) {
        if (extensions == null || extensions.length == 0) {
            throw new IllegalArgumentException(
                    "Extensions must be non-null and not empty");
        }
        this.description = description;
        this.extensions = new String[extensions.length];
        this.lowerCaseExtensions = new String[extensions.length];
        for (int i = 0; i < extensions.length; i++) {
            if (extensions[i] == null || extensions[i].length() == 0) {
                throw new IllegalArgumentException(
                    "Each extension must be non-null and not empty");
            }
            this.extensions[i] = extensions[i];
            lowerCaseExtensions[i] = extensions[i].toLowerCase(Locale.ENGLISH);
        }
    }

    /**
     * Tests the specified file, returning true if the file is
     * accepted, false otherwise. True is returned if the extension
     * matches one of the file name extensions of this {@code
     * FileFilter}, or the file is a directory.
     *
     * <p>
     *  测试指定的文件,如果文件被接受则返回true,否则返回false。如果扩展名与此{@code FileFilter}的文件扩展名之一匹配,或该文件是一个目录,则返回True。
     * 
     * 
     * @param f the {@code File} to test
     * @return true if the file is to be accepted, false otherwise
     */
    public boolean accept(File f) {
        if (f != null) {
            if (f.isDirectory()) {
                return true;
            }
            // NOTE: we tested implementations using Maps, binary search
            // on a sorted list and this implementation. All implementations
            // provided roughly the same speed, most likely because of
            // overhead associated with java.io.File. Therefor we've stuck
            // with the simple lightweight approach.
            String fileName = f.getName();
            int i = fileName.lastIndexOf('.');
            if (i > 0 && i < fileName.length() - 1) {
                String desiredExtension = fileName.substring(i+1).
                        toLowerCase(Locale.ENGLISH);
                for (String extension : lowerCaseExtensions) {
                    if (desiredExtension.equals(extension)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * The description of this filter. For example: "JPG and GIF Images."
     *
     * <p>
     *  此过滤器的说明。例如："JPG和GIF图像"。
     * 
     * 
     * @return the description of this filter
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the set of file name extensions files are tested against.
     *
     * <p>
     *  返回针对测试的文件扩展名文件集。
     * 
     * 
     * @return the set of file name extensions files are tested against
     */
    public String[] getExtensions() {
        String[] result = new String[extensions.length];
        System.arraycopy(extensions, 0, result, 0, extensions.length);
        return result;
    }

    /**
     * Returns a string representation of the {@code FileNameExtensionFilter}.
     * This method is intended to be used for debugging purposes,
     * and the content and format of the returned string may vary
     * between implementations.
     *
     * <p>
     *  返回{@code FileNameExtensionFilter}的字符串表示形式。此方法旨在用于调试目的,并且返回的字符串的内容和格式可能因实现而异。
     * 
     * @return a string representation of this {@code FileNameExtensionFilter}
     */
    public String toString() {
        return super.toString() + "[description=" + getDescription() +
            " extensions=" + java.util.Arrays.asList(getExtensions()) + "]";
    }
}
