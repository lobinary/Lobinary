/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 1999-2004 The Apache Software Foundation.
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
 *  版权所有1999-2004 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */
/*
 * $Id: XMLMessages.java,v 1.2.4.1 2005/09/15 07:45:48 suresh_emailid Exp $
 * <p>
 *  $ Id：XMLMessages.java,v 1.2.4.1 2005/09/15 07:45:48 suresh_emailid Exp $
 * 
 */
package com.sun.org.apache.xml.internal.res;

import com.sun.org.apache.xalan.internal.utils.SecuritySupport;
import java.util.ListResourceBundle;
import java.util.Locale;

/**
 * A utility class for issuing XML error messages.
 * @xsl.usage internal
 * <p>
 *  用于发出XML错误消息的实用程序类。 @ xsl.usage internal
 * 
 */
public class XMLMessages
{

  /** The local object to use.  */
  protected Locale fLocale = Locale.getDefault();

  /** The language specific resource object for XML messages.  */
  private static ListResourceBundle XMLBundle = null;

  /** The class name of the XML error message string table.    */
  private static final String XML_ERROR_RESOURCES =
    "com.sun.org.apache.xml.internal.res.XMLErrorResources";

  /** String to use if a bad message code is used. */
  protected static final String BAD_CODE = "BAD_CODE";

  /** String to use if the message format operation failed.  */
  protected static final String FORMAT_FAILED = "FORMAT_FAILED";

  /**
   * Set the Locale object to use.
   *
   * <p>
   *  设置要使用的Locale对象。
   * 
   * 
   * @param locale non-null reference to Locale object.
   */
   public void setLocale(Locale locale)
  {
    fLocale = locale;
  }

  /**
   * Get the Locale object that is being used.
   *
   * <p>
   *  获取正在使用的Locale对象。
   * 
   * 
   * @return non-null reference to Locale object.
   */
  public Locale getLocale()
  {
    return fLocale;
  }

  /**
   * Creates a message from the specified key and replacement
   * arguments, localized to the given locale.
   *
   * <p>
   *  从指定的键和替换参数创建消息,本地化到给定的语言环境。
   * 
   * 
   * @param msgKey    The key for the message text.
   * @param args      The arguments to be used as replacement text
   *                  in the message created.
   *
   * @return The formatted message string.
   */
  public static final String createXMLMessage(String msgKey, Object args[])
  {
    if (XMLBundle == null) {
        XMLBundle = SecuritySupport.getResourceBundle(XML_ERROR_RESOURCES);
    }

    if (XMLBundle != null)
    {
      return createMsg(XMLBundle, msgKey, args);
    }
    else
      return "Could not load any resource bundles.";
  }

  /**
   * Creates a message from the specified key and replacement
   * arguments, localized to the given locale.
   *
   * <p>
   *  从指定的键和替换参数创建消息,本地化到给定的语言环境。
   * 
   * @param fResourceBundle The resource bundle to use.
   * @param msgKey  The message key to use.
   * @param args      The arguments to be used as replacement text
   *                  in the message created.
   *
   * @return The formatted message string.
   */
  public static final String createMsg(ListResourceBundle fResourceBundle,
        String msgKey, Object args[])  //throws Exception
  {

    String fmsg = null;
    boolean throwex = false;
    String msg = null;

    if (msgKey != null)
      msg = fResourceBundle.getString(msgKey);

    if (msg == null)
    {
      msg = fResourceBundle.getString(BAD_CODE);
      throwex = true;
    }

    if (args != null)
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
      }
      catch (Exception e)
      {
        fmsg = fResourceBundle.getString(FORMAT_FAILED);
        fmsg += " " + msg;
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
