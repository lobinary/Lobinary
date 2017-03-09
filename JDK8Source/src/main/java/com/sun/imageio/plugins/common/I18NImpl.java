/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.imageio.plugins.common;

import java.io.InputStream;
import java.util.PropertyResourceBundle;
import java.net.URL;

/**
 * Class to simplify use of internationalization message strings.
 * Property files are constructed in terms of content as for JAI with
 * one "key=value" pair per line. All such files however have the same
 * name "properties". The resource extractor resolves the extraction of
 * the file from the jar as the package name is included automatically.
 *
 * <p>Extenders need only provide a static method
 * <code>getString(String)</code> which calls the static method in this
 * class with the name of the invoking class and returns a
 * <code>String</code>.
 * <p>
 *  类,以简化国际化消息字符串的使用。属性文件按照JAI的内容来构造,每行有一个"key = value"对。然而,所有这些文件具有相同的名称"属性"。
 * 资源提取程序解析从jar中提取文件,因为自动包含包名称。
 * 
 *  <p>扩展程序只需要提供一个静态方法<code> getString(String)</code>,它调用该类中的静态方法,并使用调用类的名称,并返回一个<code> String </code>。
 */
public class I18NImpl {
    /**
     * Returns the message string with the specified key from the
     * "properties" file in the package containing the class with
     * the specified name.
     * <p>
     * 
     */
    protected static final String getString(String className, String resource_name, String key) {
        PropertyResourceBundle bundle = null;
        try {
            InputStream stream =
                Class.forName(className).getResourceAsStream(resource_name);
            bundle = new PropertyResourceBundle(stream);
        } catch(Throwable e) {
            throw new RuntimeException(e); // Chain the exception.
        }

        return (String)bundle.handleGetObject(key);
    }
}
