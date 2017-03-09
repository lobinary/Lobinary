/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 * <p>
 *  根据一个或多个贡献者许可协议授予Apache软件基金会(ASF)。有关版权所有权的其他信息,请参阅随此作品分发的NOTICE文件。
 *  ASF根据Apache许可证2.0版("许可证")向您授予此文件;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本。
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */
package com.sun.org.apache.xml.internal.security.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.SecurityPermission;

/**
 * A collection of different, general-purpose methods for JAVA-specific things
 * <p>
 *  针对JAVA特定事物的不同的通用方法的集合
 * 
 * 
 * @author Christian Geuer-Pollmann
 */
public class JavaUtils {

    /** {@link org.apache.commons.logging} logging facility */
    private static java.util.logging.Logger log =
        java.util.logging.Logger.getLogger(JavaUtils.class.getName());

    private static final SecurityPermission REGISTER_PERMISSION =
        new SecurityPermission(
            "com.sun.org.apache.xml.internal.security.register");

    private JavaUtils() {
        // we don't allow instantiation
    }

    /**
     * Method getBytesFromFile
     *
     * <p>
     *  方法getBytesFromFile
     * 
     * 
     * @param fileName
     * @return the bytes read from the file
     *
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static byte[] getBytesFromFile(String fileName)
        throws FileNotFoundException, IOException {

        byte refBytes[] = null;

        FileInputStream fisRef = null;
        UnsyncByteArrayOutputStream baos = null;
        try {
            fisRef = new FileInputStream(fileName);
            baos = new UnsyncByteArrayOutputStream();
            byte buf[] = new byte[1024];
            int len;

            while ((len = fisRef.read(buf)) > 0) {
                baos.write(buf, 0, len);
            }

            refBytes = baos.toByteArray();
        } finally {
            if (baos != null) {
                baos.close();
            }
            if (fisRef != null) {
                fisRef.close();
            }
        }

        return refBytes;
    }

    /**
     * Method writeBytesToFilename
     *
     * <p>
     *  方法writeBytesToFilename
     * 
     * 
     * @param filename
     * @param bytes
     */
    public static void writeBytesToFilename(String filename, byte[] bytes) {
        FileOutputStream fos = null;
        try {
            if (filename != null && bytes != null) {
                File f = new File(filename);

                fos = new FileOutputStream(f);

                fos.write(bytes);
                fos.close();
            } else {
                if (log.isLoggable(java.util.logging.Level.FINE)) {
                    log.log(java.util.logging.Level.FINE, "writeBytesToFilename got null byte[] pointed");
                }
            }
        } catch (IOException ex) {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException ioe) {
                    if (log.isLoggable(java.util.logging.Level.FINE)) {
                        log.log(java.util.logging.Level.FINE, ioe.getMessage(), ioe);
                    }
                }
            }
        }
    }

    /**
     * This method reads all bytes from the given InputStream till EOF and
     * returns them as a byte array.
     *
     * <p>
     *  此方法从给定的InputStream读取所有字节,直到EOF,并将它们作为字节数组返回。
     * 
     * 
     * @param inputStream
     * @return the bytes read from the stream
     *
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static byte[] getBytesFromStream(InputStream inputStream) throws IOException {
        UnsyncByteArrayOutputStream baos = null;

        byte[] retBytes = null;
        try {
            baos = new UnsyncByteArrayOutputStream();
            byte buf[] = new byte[4 * 1024];
            int len;

            while ((len = inputStream.read(buf)) > 0) {
                baos.write(buf, 0, len);
            }
            retBytes = baos.toByteArray();
        } finally {
            baos.close();
        }

        return retBytes;
    }

    /**
     * Throws a {@code SecurityException} if a security manager is installed
     * and the caller is not allowed to register an implementation of an
     * algorithm, transform, or other security sensitive XML Signature function.
     *
     * <p>
     *  如果安装了安全管理器并且调用方不允许注册算法,转换或其他安全敏感的XML签名函数的实现,则会抛出{@code SecurityException}。
     * 
     * @throws SecurityException if a security manager is installed and the
     *    caller has not been granted the
     *    {@literal "com.sun.org.apache.xml.internal.security.register"}
     *    {@code SecurityPermission}
     */
    public static void checkRegisterPermission() {
        SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            sm.checkPermission(REGISTER_PERMISSION);
        }
    }
}
