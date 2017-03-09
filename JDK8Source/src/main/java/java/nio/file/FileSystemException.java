/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2009, Oracle and/or its affiliates. All rights reserved.
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

package java.nio.file;

import java.io.IOException;

/**
 * Thrown when a file system operation fails on one or two files. This class is
 * the general class for file system exceptions.
 *
 * <p>
 *  在一个或两个文件上的文件系统操作失败时引发。这个类是文件系统异常的一般类。
 * 
 * 
 * @since 1.7
 */

public class FileSystemException
    extends IOException
{
    static final long serialVersionUID = -3055425747967319812L;

    private final String file;
    private final String other;

    /**
     * Constructs an instance of this class. This constructor should be used
     * when an operation involving one file fails and there isn't any additional
     * information to explain the reason.
     *
     * <p>
     *  构造此类的实例。当涉及一个文件的操作失败并且没有任何其他信息来解释原因时,应使用此构造函数。
     * 
     * 
     * @param   file
     *          a string identifying the file or {@code null} if not known.
     */
    public FileSystemException(String file) {
        super((String)null);
        this.file = file;
        this.other = null;
    }

    /**
     * Constructs an instance of this class. This constructor should be used
     * when an operation involving two files fails, or there is additional
     * information to explain the reason.
     *
     * <p>
     *  构造此类的实例。当涉及两个文件的操作失败时,或者有其他信息来解释原因时,应使用此构造函数。
     * 
     * 
     * @param   file
     *          a string identifying the file or {@code null} if not known.
     * @param   other
     *          a string identifying the other file or {@code null} if there
     *          isn't another file or if not known
     * @param   reason
     *          a reason message with additional information or {@code null}
     */
    public FileSystemException(String file, String other, String reason) {
        super(reason);
        this.file = file;
        this.other = other;
    }

    /**
     * Returns the file used to create this exception.
     *
     * <p>
     *  返回用于创建此异常的文件。
     * 
     * 
     * @return  the file (can be {@code null})
     */
    public String getFile() {
        return file;
    }

    /**
     * Returns the other file used to create this exception.
     *
     * <p>
     *  返回用于创建此异常的其他文件。
     * 
     * 
     * @return  the other file (can be {@code null})
     */
    public String getOtherFile() {
        return other;
    }

    /**
     * Returns the string explaining why the file system operation failed.
     *
     * <p>
     *  返回说明文件系统操作失败的字符串。
     * 
     * 
     * @return  the string explaining why the file system operation failed
     */
    public String getReason() {
        return super.getMessage();
    }

    /**
     * Returns the detail message string.
     * <p>
     *  返回详细消息字符串。
     */
    @Override
    public String getMessage() {
        if (file == null && other == null)
            return getReason();
        StringBuilder sb = new StringBuilder();
        if (file != null)
            sb.append(file);
        if (other != null) {
            sb.append(" -> ");
            sb.append(other);
        }
        if (getReason() != null) {
            sb.append(": ");
            sb.append(getReason());
        }
        return sb.toString();
    }
}
