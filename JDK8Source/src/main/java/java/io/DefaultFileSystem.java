/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.
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

package java.io;

/**
 *
 * <p>
 * 
 * @since 1.8
 */
class DefaultFileSystem {

    /**
     * Return the FileSystem object for Windows platform.
     * <p>
     *  返回Windows平台的FileSystem对象。
     */
    public static FileSystem getFileSystem() {
        return new WinNTFileSystem();
    }
}
