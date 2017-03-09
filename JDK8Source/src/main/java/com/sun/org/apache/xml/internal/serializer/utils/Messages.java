/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 2004 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p>
 *  版权所有2004 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */
/*
 * $Id: Messages.java,v 1.1.4.1 2005/09/08 11:03:10 suresh_emailid Exp $
 * <p>
 *  $ Id：Messages.java,v 1.1.4.1 2005/09/08 11:03:10 suresh_emailid Exp $
 * 
 */
package com.sun.org.apache.xml.internal.serializer.utils;

import com.sun.org.apache.xalan.internal.utils.SecuritySupport;
import java.util.ListResourceBundle;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * A utility class for issuing error messages.
 *
 * A user of this class normally would create a singleton
 * instance of this class, passing the name
 * of the message class on the constructor. For example:
 * <CODE>
 * static Messages x = new Messages("org.package.MyMessages");
 * </CODE>
 * Later the message is typically generated this way if there are no
 * substitution arguments:
 * <CODE>
 * String msg = x.createMessage(org.package.MyMessages.KEY_ONE, null);
 * </CODE>
 * If there are arguments substitutions then something like this:
 * <CODE>
 * String filename = ...;
 * String directory = ...;
 * String msg = x.createMessage(org.package.MyMessages.KEY_TWO,
 *   new Object[] {filename, directory) );
 * </CODE>
 *
 * The constructor of an instance of this class must be given
 * the class name of a class that extends java.util.ListResourceBundle
 * ("org.package.MyMessages" in the example above).
 * The name should not have any language suffix
 * which will be added automatically by this utility class.
 *
 * The message class ("org.package.MyMessages")
 * must define the abstract method getContents() that is
 * declared in its base class, for example:
 * <CODE>
 * public Object[][] getContents() {return contents;}
 * </CODE>
 *
 * It is suggested that the message class expose its
 * message keys like this:
 * <CODE>
 *   public static final String KEY_ONE = "KEY1";
 *   public static final String KEY_TWO = "KEY2";
 *   . . .
 * </CODE>
 * and used through their names (KEY_ONE ...) rather than
 * their values ("KEY1" ...).
 *
 * The field contents (returned by getContents()
 * should be initialized something like this:
 * <CODE>
 * public static final Object[][] contents = {
 * { KEY_ONE, "Something has gone wrong!" },
 * { KEY_TWO, "The file ''{0}'' does not exist in directory ''{1}''." },
 * . . .
 * { KEY_N, "Message N" }  }
 * </CODE>
 *
 * Where that section of code with the KEY to Message mappings
 * (where the message classes 'contents' field is initialized)
 * can have the Message strings translated in an alternate language
 * in a errorResourceClass with a language suffix.
 *
 *
 * This class is not a public API, it is only public because it is
 * used in com.sun.org.apache.xml.internal.serializer.
 *
 *  @xsl.usage internal
 * <p>
 *  用于发出错误消息的实用程序类。
 * 
 *  这个类的用户通常会创建这个类的单例实例,在构造函数上传递消息类的名称。例如：
 * <CODE>
 *  static消息x =新消息("org.package.MyMessages");
 * </CODE>
 *  稍后,如果没有替换参数,则通常以这种方式生成消息：
 * <CODE>
 *  String msg = x.createMessage(org.package.MyMessages.KEY_ONE,null);
 * </CODE>
 *  如果有参数替换,那么这样的：
 * <CODE>
 *  String filename = ...; String directory = ...; String msg = x.createMessage(org.package.MyMessages.K
 * EY_TWO,new Object [] {filename,directory));。
 * </CODE>
 * 
 * 这个类的实例的构造函数必须被赋予一个扩展java.util.ListResourceBundle(上例中的"org.package.MyMessages")的类的类名。
 * 名称不应该有任何语言后缀,将由此实用程序类自动添加。
 * 
 *  消息类("org.package.MyMessages")必须定义在其基类中声明的抽象方法getContents(),例如：
 * <CODE>
 *  public Object [] [] getContents(){return content;}
 * </CODE>
 * 
 *  建议消息类公开其消息键,如下所示：
 * <CODE>
 *  public static final String KEY_ONE ="KEY1"; public static final String KEY_TWO ="KEY2"; 。 。 。
 * </CODE>
 *  并通过他们的名字(KEY_ONE ...)而不是它们的值("KEY1"...)使用。
 * 
 */
public final class Messages
{
    /** The local object to use.  */
    private final Locale m_locale = Locale.getDefault();

    /** The language specific resource object for messages.  */
    private ListResourceBundle m_resourceBundle;

    /** The class name of the error message string table with no language suffix. */
    private String m_resourceBundleName;



    /**
     * Constructor.
     * <p>
     *  字段内容(由getContents()返回)应该初始化为这样：
     * <CODE>
     *  public static final Object [] [] content = {{KEY_ONE,"出了点问题！ },{KEY_TWO,"文件"{0}"不存在于目录"{1}"中。 },。 。
     *  。 {KEY_N,"Message N"}}。
     * </CODE>
     * 
     *  在具有KEY到消息映射(其中消息类的内容字段被初始化)的代码段可以具有以具有语言后缀的errorResourceClass中的替换语言来翻译消息字符串。
     * 
     *  此类不是公共API,它只是公共的,因为它在com.sun.org.apache.xml.internal.serializer中使用。
     * 
     *  @ xsl.usage internal
     * 
     * 
     * @param resourceBundle the class name of the ListResourceBundle
     * that the instance of this class is associated with and will use when
     * creating messages.
     * The class name is without a language suffix. If the value passed
     * is null then loadResourceBundle(errorResourceClass) needs to be called
     * explicitly before any messages are created.
     *
     * @xsl.usage internal
     */
    Messages(String resourceBundle)
    {

        m_resourceBundleName = resourceBundle;
    }


    /**
     * Get the Locale object that is being used.
     *
     * <p>
     *  构造函数。
     * 
     * 
     * @return non-null reference to Locale object.
     * @xsl.usage internal
     */
    private Locale getLocale()
    {
        return m_locale;
    }

    /**
     * Creates a message from the specified key and replacement
     * arguments, localized to the given locale.
     *
     * <p>
     *  获取正在使用的Locale对象。
     * 
     * 
     * @param msgKey  The key for the message text.
     * @param args    The arguments to be used as replacement text
     * in the message created.
     *
     * @return The formatted message string.
     * @xsl.usage internal
     */
    public final String createMessage(String msgKey, Object args[])
    {
        if (m_resourceBundle == null)
            m_resourceBundle = SecuritySupport.getResourceBundle(m_resourceBundleName);

        if (m_resourceBundle != null)
        {
            return createMsg(m_resourceBundle, msgKey, args);
        }
        else
            return "Could not load the resource bundles: "+ m_resourceBundleName;
    }

    /**
     * Creates a message from the specified key and replacement
     * arguments, localized to the given locale.
     *
     * <p>
     * 从指定的键和替换参数创建消息,本地化到给定的语言环境。
     * 
     * 
     * @param errorCode The key for the message text.
     *
     * @param fResourceBundle The resource bundle to use.
     * @param msgKey  The message key to use.
     * @param args      The arguments to be used as replacement text
     *                  in the message created.
     *
     * @return The formatted message string.
     * @xsl.usage internal
     */
    private final String createMsg(
        ListResourceBundle fResourceBundle,
        String msgKey,
        Object args[]) //throws Exception
    {

        String fmsg = null;
        boolean throwex = false;
        String msg = null;

        if (msgKey != null)
            msg = fResourceBundle.getString(msgKey);
        else
            msgKey = "";

        if (msg == null)
        {
            throwex = true;
            /* The message is not in the bundle . . . this is bad,
             * so try to get the message that the message is not in the bundle
             * <p>
             *  从指定的键和替换参数创建消息,本地化到给定的语言环境。
             * 
             */
            try
            {

                msg =
                    java.text.MessageFormat.format(
                        MsgKey.BAD_MSGKEY,
                        new Object[] { msgKey, m_resourceBundleName });
            }
            catch (Exception e)
            {
                /* even the message that the message is not in the bundle is
                 * not there ... this is really bad
                 * <p>
                 *  因此尝试获取消息不在包中的消息
                 * 
                 */
                msg =
                    "The message key '"
                        + msgKey
                        + "' is not in the message class '"
                        + m_resourceBundleName+"'";
            }
        }
        else if (args != null)
        {
            try
            {
                // Do this to keep format from crying.
                // This is better than making a bunch of conditional
                // code all over the place.
                int n = args.length;

                for (int i = 0; i < n; i++)
                {
                    if (null == args[i])
                        args[i] = "";
                }

                fmsg = java.text.MessageFormat.format(msg, args);
                // if we get past the line above we have create the message ... hurray!
            }
            catch (Exception e)
            {
                throwex = true;
                try
                {
                    // Get the message that the format failed.
                    fmsg =
                        java.text.MessageFormat.format(
                            MsgKey.BAD_MSGFORMAT,
                            new Object[] { msgKey, m_resourceBundleName });
                    fmsg += " " + msg;
                }
                catch (Exception formatfailed)
                {
                    // We couldn't even get the message that the format of
                    // the message failed ... so fall back to English.
                    fmsg =
                        "The format of message '"
                            + msgKey
                            + "' in message class '"
                            + m_resourceBundleName
                            + "' failed.";
                }
            }
        }
        else
            fmsg = msg;

        if (throwex)
        {
            throw new RuntimeException(fmsg);
        }

        return fmsg;
    }

}
