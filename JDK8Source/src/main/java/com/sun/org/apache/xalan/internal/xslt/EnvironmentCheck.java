/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 2001-2004 The Apache Software Foundation.
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
 *  版权所有2001-2004 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */
/*
 * $Id: EnvironmentCheck.java,v 1.2.4.1 2005/09/09 07:13:59 pvedula Exp $
 * <p>
 *  $ Id：EnvironmentCheck.java,v 1.2.4.1 2005/09/09 07:13:59 pvedula Exp $
 * 
 */
package com.sun.org.apache.xalan.internal.xslt;

import com.sun.org.apache.xalan.internal.utils.ObjectFactory;
import com.sun.org.apache.xalan.internal.utils.SecuritySupport;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Utility class to report simple information about the environment.
 * Simplistic reporting about certain classes found in your JVM may
 * help answer some FAQs for simple problems.
 *
 * <p>Usage-command line:
 * <code>
 * java com.sun.org.apache.xalan.internal.xslt.EnvironmentCheck [-out outFile]
 * </code></p>
 *
 * <p>Usage-from program:
 * <code>
 * boolean environmentOK =
 * (new EnvironmentCheck()).checkEnvironment(yourPrintWriter);
 * </code></p>
 *
 * <p>Usage-from stylesheet:
 * <code><pre>
 *    &lt;?xml version="1.0"?&gt;
 *    &lt;xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
 *        xmlns:xalan="http://xml.apache.org/xalan"
 *        exclude-result-prefixes="xalan"&gt;
 *    &lt;xsl:output indent="yes"/&gt;
 *    &lt;xsl:template match="/"&gt;
 *      &lt;xsl:copy-of select="xalan:checkEnvironment()"/&gt;
 *    &lt;/xsl:template&gt;
 *    &lt;/xsl:stylesheet&gt;
 * </pre></code></p>
 *
 * <p>Xalan users reporting problems are encouraged to use this class
 * to see if there are potential problems with their actual
 * Java environment <b>before</b> reporting a bug.  Note that you
 * should both check from the JVM/JRE's command line as well as
 * temporarily calling checkEnvironment() directly from your code,
 * since the classpath may differ (especially for servlets, etc).</p>
 *
 * <p>Also see http://xml.apache.org/xalan-j/faq.html</p>
 *
 * <p>Note: This class is pretty simplistic:
 * results are not necessarily definitive nor will it find all
 * problems related to environment setup.  Also, you should avoid
 * calling this in deployed production code, both because it is
 * quite slow and because it forces classes to get loaded.</p>
 *
 * <p>Note: This class explicitly has very limited compile-time
 * dependencies to enable easy compilation and usage even when
 * Xalan, DOM/SAX/JAXP, etc. are not present.</p>
 *
 * <p>Note: for an improved version of this utility, please see
 * the xml-commons' project Which utility which does the same kind
 * of thing but in a much simpler manner.</p>
 *
 * <p>
 *  实用程序类,用于报告有关环境的简单信息。在JVM中发现的某些类的简单报告可能有助于回答一些简单问题的常见问题。
 * 
 *  <p>用法 - 命令行：
 * <code>
 *  java com.sun.org.apache.xalan.internal.xslt.EnvironmentCheck [-out outFile] </code> </p>
 * 
 *  <p>使用方法：
 * <code>
 *  boolean environmentOK =(new EnvironmentCheck())。checkEnvironment(yourPrintWriter); </code> </p>
 * 
 * <p> Usage-from stylesheet：<code> <pre>&lt;?xml version ="1.0"?&gt; &lt; xsl：stylesheet xmlns：xsl ="ht
 * tp://www.w3.org/1999/XSL/Transform"version ="1.0"xmlns：xalan ="http://xml.apache.org/xalan"exclude- r
 * esult-prefixes ="xalan"&gt; &lt; xsl：output indent ="yes"/&gt; &lt; xsl：template match ="/"&gt; &lt; 
 * xsl：copy-of select ="xalan：checkEnvironment()"/&gt; &lt; / xsl：template&gt; &lt; / xsl：stylesheet&gt;
 *  </pre> </code> </p>。
 * 
 *  <p>我们鼓励Xalan用户报告问题,以便在报告错误之前<b> </b>之前使用此类查看其实际Java环境是否存在潜在问题。
 * 注意,你应该从JVM / JRE的命令行检查,以及直接从你的代码中临时调用checkEnvironment(),因为类路径可能不同(特别是servlet等)。</p>。
 * 
 *  <p>另请参阅http://xml.apache.org/xalan-j/faq.html </p>
 * 
 *  <p>注意：这个类很简单：结果不一定是明确的,也不会找到所有与环境设置相关的问题。此外,您应该避免在部署的生产代码中调用此方法,因为它非常慢,并且会强制类加载。</p>
 * 
 *  <p>注意：这个类显式地具有非常有限的编译时依赖性,以便即使在不存在Xalan,DOM / SAX / JAXP等时也能够容易编译和使用。</p>
 * 
 *  <p>注意：对于这个实用程序的改进版本,请参见xml-commons的项目哪个实用程序做同样的事情,但在一个更简单的方式。</p>
 * 
 * 
 * @author Shane_Curcuru@us.ibm.com
 * @version $Id: EnvironmentCheck.java,v 1.10 2010-11-01 04:34:13 joehw Exp $
 */
public class EnvironmentCheck
{

  /**
   * Command line runnability: checks for [-out outFilename] arg.
   * <p>Command line entrypoint; Sets output and calls
   * {@link #checkEnvironment(PrintWriter)}.</p>
   * <p>
   * 命令行运行性：检查[-out outFilename] arg。 <p>命令行入口;设置输出和调用{@link #checkEnvironment(PrintWriter)}。</p>
   * 
   * 
   * @param args command line args
   */
  public static void main(String[] args)
  {
    // Default to System.out, autoflushing
    PrintWriter sendOutputTo = new PrintWriter(System.out, true);

    // Read our simplistic input args, if supplied
    for (int i = 0; i < args.length; i++)
    {
      if ("-out".equalsIgnoreCase(args[i]))
      {
        i++;

        if (i < args.length)
        {
          try
          {
            sendOutputTo = new PrintWriter(new FileWriter(args[i], true));
          }
          catch (Exception e)
          {
            System.err.println("# WARNING: -out " + args[i] + " threw "
                               + e.toString());
          }
        }
        else
        {
          System.err.println(
            "# WARNING: -out argument should have a filename, output sent to console");
        }
      }
    }

    EnvironmentCheck app = new EnvironmentCheck();
    app.checkEnvironment(sendOutputTo);
  }

  /**
   * Programmatic entrypoint: Report on basic Java environment
   * and CLASSPATH settings that affect Xalan.
   *
   * <p>Note that this class is not advanced enough to tell you
   * everything about the environment that affects Xalan, and
   * sometimes reports errors that will not actually affect
   * Xalan's behavior.  Currently, it very simplistically
   * checks the JVM's environment for some basic properties and
   * logs them out; it will report a problem if it finds a setting
   * or .jar file that is <i>likely</i> to cause problems.</p>
   *
   * <p>Advanced users can peruse the code herein to help them
   * investigate potential environment problems found; other users
   * may simply send the output from this tool along with any bugs
   * they submit to help us in the debugging process.</p>
   *
   * <p>
   *  编程入口点：报告影响Xalan的基本Java环境和CLASSPATH设置。
   * 
   *  <p>请注意,此类不够高级,无法告诉您有关影响Xalan的环境的一切,有时报告实际上不会影响Xalan行为的错误。
   * 目前,它非常简单地检查JVM的环境中的一些基本属性并将其注销;如果找到<i>可能</i>的设置或.jar文件导致问题,它将报告问题。</p>。
   * 
   *  <p>高级用户可以仔细阅读本文中的代码,以帮助他们调查发现的潜在环境问题;其他用户可能只是发送此工具的输出以及他们提交的任何错误,以帮助我们在调试过程中。</p>
   * 
   * 
   * @param pw PrintWriter to send output to; can be sent to a
   * file that will look similar to a Properties file; defaults
   * to System.out if null
   * @return true if your environment appears to have no major
   * problems; false if potential environment problems found
   * @see #getEnvironmentHash()
   */
  public boolean checkEnvironment(PrintWriter pw)
  {

    // Use user-specified output writer if non-null
    if (null != pw)
      outWriter = pw;

    // Setup a hash to store various environment information in
    Hashtable hash = getEnvironmentHash();

    // Check for ERROR keys in the hashtable, and print report
    boolean environmentHasErrors = writeEnvironmentReport(hash);

    if (environmentHasErrors)
    {
      // Note: many logMsg calls have # at the start to
      //  fake a property-file like output
      logMsg("# WARNING: Potential problems found in your environment!");
      logMsg("#    Check any 'ERROR' items above against the Xalan FAQs");
      logMsg("#    to correct potential problems with your classes/jars");
      logMsg("#    http://xml.apache.org/xalan-j/faq.html");
      if (null != outWriter)
        outWriter.flush();
      return false;
    }
    else
    {
      logMsg("# YAHOO! Your environment seems to be OK.");
      if (null != outWriter)
        outWriter.flush();
      return true;
    }
  }

  /**
   * Fill a hash with basic environment settings that affect Xalan.
   *
   * <p>Worker method called from various places.</p>
   * <p>Various system and CLASSPATH, etc. properties are put into
   * the hash as keys with a brief description of the current state
   * of that item as the value.  Any serious problems will be put in
   * with a key that is prefixed with {@link #ERROR 'ERROR.'} so it
   * stands out in any resulting report; also a key with just that
   * constant will be set as well for any error.</p>
   * <p>Note that some legitimate cases are flaged as potential
   * errors - namely when a developer recompiles xalan.jar on their
   * own - and even a non-error state doesn't guaruntee that
   * everything in the environment is correct.  But this will help
   * point out the most common classpath and system property
   * problems that we've seen.</p>
   *
   * <p>
   *  使用影响Xalan的基本环境设置填充散列。
   * 
   * <p>从各个地方调用的Worker方法。</p> <p>各种系统和CLASSPATH等属性都作为键值放入哈希中,并将该项目的当前状态的简要描述作为值。
   * 任何严重的问题将被放在一个以{@link #ERROR'ERROR。
   * '}为前缀的键,因此它在任何生成的报告中都是突出的; </p> <p>请注意,一些合法的情况被划分为潜在的错误 - 即当开发人员自己重新编译xalan.jar  - 甚至是一个非法的 - 错误状态不保证
   * 环境中的一切是正确的。
   * 任何严重的问题将被放在一个以{@link #ERROR'ERROR。但这将有助于指出我们见过的最常见的类路径和系统属性问题。</p>。
   * 
   * 
   * @return Hashtable full of useful environment info about Xalan
   * and related system properties, etc.
   */
  public Hashtable getEnvironmentHash()
  {
    // Setup a hash to store various environment information in
    Hashtable hash = new Hashtable();

    // Call various worker methods to fill in the hash
    //  These are explicitly separate for maintenance and so
    //  advanced users could call them standalone
    checkJAXPVersion(hash);
    checkProcessorVersion(hash);
    checkParserVersion(hash);
    checkAntVersion(hash);
    if (!checkDOML3(hash)) {
    checkDOMVersion(hash);
    }
    checkSAXVersion(hash);
    checkSystemProperties(hash);

    return hash;
  }

  /**
   * Dump a basic Xalan environment report to outWriter.
   *
   * <p>This dumps a simple header and then each of the entries in
   * the Hashtable to our PrintWriter; it does special processing
   * for entries that are .jars found in the classpath.</p>
   *
   * <p>
   *  将基本的Xalan环境报告转储到outWriter。
   * 
   *  <p>这将一个简单的标题,然后每个Hashtable的条目转储到我们的PrintWriter;它对在类路径中找到的.jars条目进行特殊处理。</p>
   * 
   * 
   * @param h Hashtable of items to report on; presumably
   * filled in by our various check*() methods
   * @return true if your environment appears to have no major
   * problems; false if potential environment problems found
   * @see #appendEnvironmentReport(Node, Document, Hashtable)
   * for an equivalent that appends to a Node instead
   */
  protected boolean writeEnvironmentReport(Hashtable h)
  {

    if (null == h)
    {
      logMsg("# ERROR: writeEnvironmentReport called with null Hashtable");
      return false;
    }

    boolean errors = false;

    logMsg(
      "#---- BEGIN writeEnvironmentReport($Revision: 1.10 $): Useful stuff found: ----");

    // Fake the Properties-like output
    for (Enumeration keys = h.keys();
         keys.hasMoreElements();
        /* no increment portion */
        )
    {
      Object key = keys.nextElement();
      String keyStr = (String) key;
      try
      {
        // Special processing for classes found..
        if (keyStr.startsWith(FOUNDCLASSES))
        {
          Vector v = (Vector) h.get(keyStr);
          errors |= logFoundJars(v, keyStr);
        }
        // ..normal processing for all other entries
        else
        {
          // Note: we could just check for the ERROR key by itself,
          //    since we now set that, but since we have to go
          //    through the whole hash anyway, do it this way,
          //    which is safer for maintenance
          if (keyStr.startsWith(ERROR))
          {
            errors = true;
          }
          logMsg(keyStr + "=" + h.get(keyStr));
        }
      }
      catch (Exception e)
      {
        logMsg("Reading-" + key + "= threw: " + e.toString());
      }
    }

    logMsg(
      "#----- END writeEnvironmentReport: Useful properties found: -----");

    return errors;
  }

  /** Prefixed to hash keys that signify serious problems.  */
  public static final String ERROR = "ERROR.";

  /** Added to descriptions that signify potential problems.  */
  public static final String WARNING = "WARNING.";

  /** Value for any error found.  */
  public static final String ERROR_FOUND = "At least one error was found!";

  /** Prefixed to hash keys that signify version numbers.  */
  public static final String VERSION = "version.";

  /** Prefixed to hash keys that signify .jars found in classpath.  */
  public static final String FOUNDCLASSES = "foundclasses.";

  /** Marker that a class or .jar was found.  */
  public static final String CLASS_PRESENT = "present-unknown-version";

  /** Marker that a class or .jar was not found.  */
  public static final String CLASS_NOTPRESENT = "not-present";

  /** Listing of common .jar files that include Xalan-related classes.  */
  public String[] jarNames =
  {
    "xalan.jar", "xalansamples.jar", "xalanj1compat.jar", "xalanservlet.jar",
    "serializer.jar",   // Serializer (shared between Xalan & Xerces)
    "xerces.jar",       // Xerces-J 1.x
    "xercesImpl.jar",   // Xerces-J 2.x
    "testxsl.jar",
    "crimson.jar",
    "lotusxsl.jar",
    "jaxp.jar", "parser.jar", "dom.jar", "sax.jar", "xml.jar",
    "xml-apis.jar",
    "xsltc.jar"
  };

  /**
   * Print out report of .jars found in a classpath.
   *
   * Takes the information encoded from a checkPathForJars()
   * call and dumps it out to our PrintWriter.
   *
   * <p>
   *  打印在类路径中找到的.jars的报告。
   * 
   *  从checkPathForJars()调用编码的信息,并将其转储到我们的PrintWriter。
   * 
   * 
   * @param v Vector of Hashtables of .jar file info
   * @param desc description to print out in header
   *
   * @return false if OK, true if any .jars were reported
   * as having errors
   * @see #checkPathForJars(String, String[])
   */
  protected boolean logFoundJars(Vector v, String desc)
  {

    if ((null == v) || (v.size() < 1))
      return false;

    boolean errors = false;

    logMsg("#---- BEGIN Listing XML-related jars in: " + desc + " ----");

    for (int i = 0; i < v.size(); i++)
    {
      Hashtable subhash = (Hashtable) v.elementAt(i);

      for (Enumeration keys = subhash.keys();
           keys.hasMoreElements();
           /* no increment portion */
          )
      {
        Object key = keys.nextElement();
        String keyStr = (String) key;
        try
        {
          if (keyStr.startsWith(ERROR))
          {
            errors = true;
          }
          logMsg(keyStr + "=" + subhash.get(keyStr));

        }
        catch (Exception e)
        {
          errors = true;
          logMsg("Reading-" + key + "= threw: " + e.toString());
        }
      }
    }

    logMsg("#----- END Listing XML-related jars in: " + desc + " -----");

    return errors;
  }

  /**
   * Stylesheet extension entrypoint: Dump a basic Xalan
   * environment report from getEnvironmentHash() to a Node.
   *
   * <p>Copy of writeEnvironmentReport that creates a Node suitable
   * for other processing instead of a properties-like text output.
   * </p>
   * <p>
   *  样式表扩展entrypoint：将基本的Xalan环境报告从getEnvironmentHash()转储到节点。
   * 
   *  <p> writeEnvironmentReport的副本,创建适合于其他处理的节点,而不是类属性文本输出。
   * </p>
   * 
   * @param container Node to append our report to
   * @param factory Document providing createElement, etc. services
   * @param h Hash presumably from {@link #getEnvironmentHash()}
   * @see #writeEnvironmentReport(Hashtable)
   * for an equivalent that writes to a PrintWriter instead
   */
  public void appendEnvironmentReport(Node container, Document factory, Hashtable h)
  {
    if ((null == container) || (null == factory))
    {
      return;
    }

    try
    {
      Element envCheckNode = factory.createElement("EnvironmentCheck");
      envCheckNode.setAttribute("version", "$Revision: 1.10 $");
      container.appendChild(envCheckNode);

      if (null == h)
      {
        Element statusNode = factory.createElement("status");
        statusNode.setAttribute("result", "ERROR");
        statusNode.appendChild(factory.createTextNode("appendEnvironmentReport called with null Hashtable!"));
        envCheckNode.appendChild(statusNode);
        return;
      }

      boolean errors = false;

      Element hashNode = factory.createElement("environment");
      envCheckNode.appendChild(hashNode);

      for (Enumeration keys = h.keys();
           keys.hasMoreElements();
          /* no increment portion */
          )
      {
        Object key = keys.nextElement();
        String keyStr = (String) key;
        try
        {
          // Special processing for classes found..
          if (keyStr.startsWith(FOUNDCLASSES))
          {
            Vector v = (Vector) h.get(keyStr);
            // errors |= logFoundJars(v, keyStr);
            errors |= appendFoundJars(hashNode, factory, v, keyStr);
          }
          // ..normal processing for all other entries
          else
          {
            // Note: we could just check for the ERROR key by itself,
            //    since we now set that, but since we have to go
            //    through the whole hash anyway, do it this way,
            //    which is safer for maintenance
            if (keyStr.startsWith(ERROR))
            {
              errors = true;
            }
            Element node = factory.createElement("item");
            node.setAttribute("key", keyStr);
            node.appendChild(factory.createTextNode((String)h.get(keyStr)));
            hashNode.appendChild(node);
          }
        }
        catch (Exception e)
        {
          errors = true;
          Element node = factory.createElement("item");
          node.setAttribute("key", keyStr);
          node.appendChild(factory.createTextNode(ERROR + " Reading " + key + " threw: " + e.toString()));
          hashNode.appendChild(node);
        }
      } // end of for...

      Element statusNode = factory.createElement("status");
      statusNode.setAttribute("result", (errors ? "ERROR" : "OK" ));
      envCheckNode.appendChild(statusNode);
    }
    catch (Exception e2)
    {
      System.err.println("appendEnvironmentReport threw: " + e2.toString());
      e2.printStackTrace();
    }
  }

  /**
   * Print out report of .jars found in a classpath.
   *
   * Takes the information encoded from a checkPathForJars()
   * call and dumps it out to our PrintWriter.
   *
   * <p>
   *  打印在类路径中找到的.jars的报告。
   * 
   * 从checkPathForJars()调用编码的信息,并将其转储到我们的PrintWriter。
   * 
   * 
   * @param container Node to append our report to
   * @param factory Document providing createElement, etc. services
   * @param v Vector of Hashtables of .jar file info
   * @param desc description to print out in header
   *
   * @return false if OK, true if any .jars were reported
   * as having errors
   * @see #checkPathForJars(String, String[])
   */
  protected boolean appendFoundJars(Node container, Document factory,
        Vector v, String desc)
  {

    if ((null == v) || (v.size() < 1))
      return false;

    boolean errors = false;

    for (int i = 0; i < v.size(); i++)
    {
      Hashtable subhash = (Hashtable) v.elementAt(i);

      for (Enumeration keys = subhash.keys();
           keys.hasMoreElements();
           /* no increment portion */
          )
      {
        Object key = keys.nextElement();
        try
        {
          String keyStr = (String) key;
          if (keyStr.startsWith(ERROR))
          {
            errors = true;
          }
          Element node = factory.createElement("foundJar");
          node.setAttribute("name", keyStr.substring(0, keyStr.indexOf("-")));
          node.setAttribute("desc", keyStr.substring(keyStr.indexOf("-") + 1));
          node.appendChild(factory.createTextNode((String)subhash.get(keyStr)));
          container.appendChild(node);
        }
        catch (Exception e)
        {
          errors = true;
          Element node = factory.createElement("foundJar");
          node.appendChild(factory.createTextNode(ERROR + " Reading " + key + " threw: " + e.toString()));
          container.appendChild(node);
        }
      }
    }
    return errors;
  }

  /**
   * Fillin hash with info about SystemProperties.
   *
   * Logs java.class.path and other likely paths; then attempts
   * to search those paths for .jar files with Xalan-related classes.
   *
   * //@todo NOTE: We don't actually search java.ext.dirs for
   * //  *.jar files therein! This should be updated
   *
   * <p>
   *  使用有关SystemProperties的信息的Fillin散列。
   * 
   *  日志java.class.path和其他可能的路径;然后尝试使用与Xalan相关的类搜索这些路径.jar文件。
   * 
   *  // @ todo注意：我们实际上不搜索java.ext.dirs中的// * .jar文件！这应该更新
   * 
   * 
   * @param h Hashtable to put information in
   * @see #jarNames
   * @see #checkPathForJars(String, String[])
   */
  protected void checkSystemProperties(Hashtable h)
  {

    if (null == h)
      h = new Hashtable();

    // Grab java version for later use
    try
    {
      String javaVersion = SecuritySupport.getSystemProperty("java.version");

      h.put("java.version", javaVersion);
    }
    catch (SecurityException se)
    {

      // For applet context, etc.
      h.put(
        "java.version",
        "WARNING: SecurityException thrown accessing system version properties");
    }

    // Printout jar files on classpath(s) that may affect operation
    //  Do this in order
    try
    {

      // This is present in all JVM's
      String cp = SecuritySupport.getSystemProperty("java.class.path");

      h.put("java.class.path", cp);

      Vector classpathJars = checkPathForJars(cp, jarNames);

      if (null != classpathJars)
        h.put(FOUNDCLASSES + "java.class.path", classpathJars);

      // Also check for JDK 1.2+ type classpaths
      String othercp = SecuritySupport.getSystemProperty("sun.boot.class.path");

      if (null != othercp)
      {
        h.put("sun.boot.class.path", othercp);

        classpathJars = checkPathForJars(othercp, jarNames);

        if (null != classpathJars)
          h.put(FOUNDCLASSES + "sun.boot.class.path", classpathJars);
      }

      //@todo NOTE: We don't actually search java.ext.dirs for
      //  *.jar files therein! This should be updated
      othercp = SecuritySupport.getSystemProperty("java.ext.dirs");

      if (null != othercp)
      {
        h.put("java.ext.dirs", othercp);

        classpathJars = checkPathForJars(othercp, jarNames);

        if (null != classpathJars)
          h.put(FOUNDCLASSES + "java.ext.dirs", classpathJars);
      }

      //@todo also check other System properties' paths?
      //  v2 = checkPathForJars(System.getProperty("sun.boot.library.path"), jarNames);   // ?? may not be needed
      //  v3 = checkPathForJars(System.getProperty("java.library.path"), jarNames);   // ?? may not be needed
    }
    catch (SecurityException se2)
    {
      // For applet context, etc.
      h.put(
        "java.class.path",
        "WARNING: SecurityException thrown accessing system classpath properties");
    }
  }

  /**
   * Cheap-o listing of specified .jars found in the classpath.
   *
   * cp should be separated by the usual File.pathSeparator.  We
   * then do a simplistic search of the path for any requested
   * .jar filenames, and return a listing of their names and
   * where (apparently) they came from.
   *
   * <p>
   *  在类路径中找到的指定.jars的Cheap-o列表。
   * 
   *  cp应该由通常的File.pathSeparator分隔。然后,我们对任何请求的.jar文件名的路径进行一个简单的搜索,并返回他们的名字列表,以及他们来自哪里(显然)。
   * 
   * 
   * @param cp classpath to search
   * @param jars array of .jar base filenames to look for
   *
   * @return Vector of Hashtables filled with info about found .jars
   * @see #jarNames
   * @see #logFoundJars(Vector, String)
   * @see #appendFoundJars(Node, Document, Vector, String )
   * @see #getApparentVersion(String, long)
   */
  protected Vector checkPathForJars(String cp, String[] jars)
  {

    if ((null == cp) || (null == jars) || (0 == cp.length())
            || (0 == jars.length))
      return null;

    Vector v = new Vector();
    StringTokenizer st = new StringTokenizer(cp, File.pathSeparator);

    while (st.hasMoreTokens())
    {

      // Look at each classpath entry for each of our requested jarNames
      String filename = st.nextToken();

      for (int i = 0; i < jars.length; i++)
      {
        if (filename.indexOf(jars[i]) > -1)
        {
          File f = new File(filename);

          if (f.exists())
          {

            // If any requested jarName exists, report on
            //  the details of that .jar file
            try
            {
              Hashtable h = new Hashtable(2);
              // Note "-" char is looked for in appendFoundJars
              h.put(jars[i] + "-path", f.getAbsolutePath());

              // We won't bother reporting on the xalan.jar apparent version
              // since this requires knowing the jar size of the xalan.jar
              // before we build it.
              // For other jars, eg. xml-apis.jar and xercesImpl.jar, we
              // report the apparent version of the file we've found
              if (!("xalan.jar".equalsIgnoreCase(jars[i]))) {
                h.put(jars[i] + "-apparent.version",
                    getApparentVersion(jars[i], f.length()));
              }
              v.addElement(h);
            }
            catch (Exception e)
            {

              /* no-op, don't add it  */
            }
          }
          else
          {
            Hashtable h = new Hashtable(2);
            // Note "-" char is looked for in appendFoundJars
            h.put(jars[i] + "-path", WARNING + " Classpath entry: "
                  + filename + " does not exist");
            h.put(jars[i] + "-apparent.version", CLASS_NOTPRESENT);
            v.addElement(h);
          }
        }
      }
    }

    return v;
  }

  /**
   * Cheap-o method to determine the product version of a .jar.
   *
   * Currently does a lookup into a local table of some recent
   * shipped Xalan builds to determine where the .jar probably
   * came from.  Note that if you recompile Xalan or Xerces
   * yourself this will likely report a potential error, since
   * we can't certify builds other than the ones we ship.
   * Only reports against selected posted Xalan-J builds.
   *
   * //@todo actually look up version info in manifests
   *
   * <p>
   *  Cheap-o方法来确定.jar的产品版本。
   * 
   *  目前正在查找一些最近运输的Xalan版本的本地表,以确定.jar可能来自哪里。请注意,如果您自己重新编译Xalan或Xerces,这可能会报告一个潜在的错误,因为我们不能证明除了我们发货的版本。
   * 只有针对选定的已发布Xalan-J的报告生成。
   * 
   *  // @ todo实际上在清单中查找版本信息
   * 
   * 
   * @param jarName base filename of the .jarfile
   * @param jarSize size of the .jarfile
   *
   * @return String describing where the .jar file probably
   * came from
   */
  protected String getApparentVersion(String jarName, long jarSize)
  {
    // If we found a matching size and it's for our
    //  jar, then return it's description
    // Lookup in static jarVersions Hashtable
    String foundSize = (String) jarVersions.get(new Long(jarSize));

    if ((null != foundSize) && (foundSize.startsWith(jarName)))
    {
      return foundSize;
    }
    else
    {
      if ("xerces.jar".equalsIgnoreCase(jarName)
              || "xercesImpl.jar".equalsIgnoreCase(jarName))
//              || "xalan.jar".equalsIgnoreCase(jarName))
      {

        // For xalan.jar and xerces.jar/xercesImpl.jar, which we ship together:
        // The jar is not from a shipped copy of xalan-j, so
        //  it's up to the user to ensure that it's compatible
        return jarName + " " + WARNING + CLASS_PRESENT;
      }
      else
      {

        // Otherwise, it's just a jar we don't have the version info calculated for
        return jarName + " " + CLASS_PRESENT;
      }
    }
  }

  /**
   * Report version information about JAXP interfaces.
   *
   * Currently distinguishes between JAXP 1.0.1 and JAXP 1.1,
   * and not found; only tests the interfaces, and does not
   * check for reference implementation versions.
   *
   * <p>
   *  报告有关JAXP接口的版本信息。
   * 
   *  目前区分JAXP 1.0.1和JAXP 1.1,未找到;只测试接口,并且不检查参考实现版本。
   * 
   * 
   * @param h Hashtable to put information in
   */
  protected void checkJAXPVersion(Hashtable h)
  {

    if (null == h)
      h = new Hashtable();

    Class clazz = null;

    try
    {
      final String JAXP1_CLASS = "javax.xml.stream.XMLStreamConstants";

      clazz = ObjectFactory.findProviderClass(JAXP1_CLASS, true);

      // If we succeeded, we have JAXP 1.4 available
      h.put(VERSION + "JAXP", "1.4");
    }
    catch (Exception e)
    {
        h.put(ERROR + VERSION + "JAXP", "1.3");
        h.put(ERROR, ERROR_FOUND);
      }
      }

  /**
   * Report product version information from Xalan-J.
   *
   * Looks for version info in xalan.jar from Xalan-J products.
   *
   * <p>
   *  从Xalan-J报告产品版本信息。
   * 
   *  在Xalan-J产品中查找xalan.jar中的版本信息。
   * 
   * 
   * @param h Hashtable to put information in
   */
  protected void checkProcessorVersion(Hashtable h)
  {

    if (null == h)
      h = new Hashtable();

    try
    {
      final String XALAN1_VERSION_CLASS =
        "com.sun.org.apache.xalan.internal.xslt.XSLProcessorVersion";

      Class clazz = ObjectFactory.findProviderClass(XALAN1_VERSION_CLASS, true);

      // Found Xalan-J 1.x, grab it's version fields
      StringBuffer buf = new StringBuffer();
      Field f = clazz.getField("PRODUCT");

      buf.append(f.get(null));
      buf.append(';');

      f = clazz.getField("LANGUAGE");

      buf.append(f.get(null));
      buf.append(';');

      f = clazz.getField("S_VERSION");

      buf.append(f.get(null));
      buf.append(';');
      h.put(VERSION + "xalan1", buf.toString());
    }
    catch (Exception e1)
    {
      h.put(VERSION + "xalan1", CLASS_NOTPRESENT);
    }

    try
    {
      // NOTE: This is the old Xalan 2.0, 2.1, 2.2 version class,
      //    is being replaced by class below
      final String XALAN2_VERSION_CLASS =
        "com.sun.org.apache.xalan.internal.processor.XSLProcessorVersion";

      Class clazz = ObjectFactory.findProviderClass(XALAN2_VERSION_CLASS, true);

      // Found Xalan-J 2.x, grab it's version fields
      StringBuffer buf = new StringBuffer();
      Field f = clazz.getField("S_VERSION");
      buf.append(f.get(null));

      h.put(VERSION + "xalan2x", buf.toString());
    }
    catch (Exception e2)
    {
      h.put(VERSION + "xalan2x", CLASS_NOTPRESENT);
    }
    try
    {
      // NOTE: This is the new Xalan 2.2+ version class
      final String XALAN2_2_VERSION_CLASS =
        "com.sun.org.apache.xalan.internal.Version";
      final String XALAN2_2_VERSION_METHOD = "getVersion";
      final Class noArgs[] = new Class[0];

      Class clazz = ObjectFactory.findProviderClass(XALAN2_2_VERSION_CLASS, true);

      Method method = clazz.getMethod(XALAN2_2_VERSION_METHOD, noArgs);
      Object returnValue = method.invoke(null, new Object[0]);

      h.put(VERSION + "xalan2_2", (String)returnValue);
    }
    catch (Exception e2)
    {
      h.put(VERSION + "xalan2_2", CLASS_NOTPRESENT);
    }
  }

  /**
   * Report product version information from common parsers.
   *
   * Looks for version info in xerces.jar/xercesImpl.jar/crimson.jar.
   *
   * //@todo actually look up version info in crimson manifest
   *
   * <p>
   *  从常见解析器报告产品版本信息。
   * 
   * 在xerces.jar / xercesImpl.jar / crimson.jar中查找版本信息。
   * 
   *  // @ todo实际上在深红色清单中查找版本信息
   * 
   * 
   * @param h Hashtable to put information in
   */
  protected void checkParserVersion(Hashtable h)
  {

    if (null == h)
      h = new Hashtable();

    try
    {
      final String XERCES1_VERSION_CLASS = "com.sun.org.apache.xerces.internal.framework.Version";

      Class clazz = ObjectFactory.findProviderClass(XERCES1_VERSION_CLASS, true);

      // Found Xerces-J 1.x, grab it's version fields
      Field f = clazz.getField("fVersion");
      String parserVersion = (String) f.get(null);

      h.put(VERSION + "xerces1", parserVersion);
    }
    catch (Exception e)
    {
      h.put(VERSION + "xerces1", CLASS_NOTPRESENT);
    }

    // Look for xerces1 and xerces2 parsers separately
    try
    {
      final String XERCES2_VERSION_CLASS = "com.sun.org.apache.xerces.internal.impl.Version";

      Class clazz = ObjectFactory.findProviderClass(XERCES2_VERSION_CLASS, true);

      // Found Xerces-J 2.x, grab it's version fields
      Field f = clazz.getField("fVersion");
      String parserVersion = (String) f.get(null);

      h.put(VERSION + "xerces2", parserVersion);
    }
    catch (Exception e)
    {
      h.put(VERSION + "xerces2", CLASS_NOTPRESENT);
    }

    try
    {
      final String CRIMSON_CLASS = "org.apache.crimson.parser.Parser2";

      Class clazz = ObjectFactory.findProviderClass(CRIMSON_CLASS, true);

      //@todo determine specific crimson version
      h.put(VERSION + "crimson", CLASS_PRESENT);
    }
    catch (Exception e)
    {
      h.put(VERSION + "crimson", CLASS_NOTPRESENT);
    }
  }

  /**
   * Report product version information from Ant.
   *
   * <p>
   *  从Ant报告产品版本信息。
   * 
   * 
   * @param h Hashtable to put information in
   */
  protected void checkAntVersion(Hashtable h)
  {

    if (null == h)
      h = new Hashtable();

    try
    {
      final String ANT_VERSION_CLASS = "org.apache.tools.ant.Main";
      final String ANT_VERSION_METHOD = "getAntVersion"; // noArgs
      final Class noArgs[] = new Class[0];

      Class clazz = ObjectFactory.findProviderClass(ANT_VERSION_CLASS, true);

      Method method = clazz.getMethod(ANT_VERSION_METHOD, noArgs);
      Object returnValue = method.invoke(null, new Object[0]);

      h.put(VERSION + "ant", (String)returnValue);
    }
    catch (Exception e)
    {
      h.put(VERSION + "ant", CLASS_NOTPRESENT);
    }
  }

  /**
   * Report version info from DOM interfaces.
   *
   * <p>
   *  从DOM接口报告版本信息。
   * 
   * 
   * @param h Hashtable to put information in
   */
  protected boolean checkDOML3(Hashtable h)
  {

    if (null == h)
      h = new Hashtable();

    final String DOM_CLASS = "org.w3c.dom.Document";
    final String DOM_LEVEL3_METHOD = "getDoctype";  // no parameter

    try
    {
      Class clazz = ObjectFactory.findProviderClass(DOM_CLASS, true);

      Method method = clazz.getMethod(DOM_LEVEL3_METHOD, (Class<?>[])null);

      // If we succeeded, we have loaded interfaces from a
      //  level 3 DOM somewhere
      h.put(VERSION + "DOM", "3.0");
      return true;
    }
    catch (Exception e)
    {
      return false;
    }
  }

  /**
   * Report version info from DOM interfaces.
   *
   * Currently distinguishes between pre-DOM level 2, the DOM
   * level 2 working draft, the DOM level 2 final draft,
   * and not found.
   *
   * <p>
   *  从DOM接口报告版本信息。
   * 
   *  目前区分前DOM级别2,DOM级别2工作草案,DOM级别2最终草案,并未找到。
   * 
   * 
   * @param h Hashtable to put information in
   */
  protected void checkDOMVersion(Hashtable h)
  {

    if (null == h)
      h = new Hashtable();

    final String DOM_LEVEL2_CLASS = "org.w3c.dom.Document";
    final String DOM_LEVEL2_METHOD = "createElementNS";  // String, String
    final String DOM_LEVEL3_METHOD = "getDoctype";  // no parameter
    final String DOM_LEVEL2WD_CLASS = "org.w3c.dom.Node";
    final String DOM_LEVEL2WD_METHOD = "supported";  // String, String
    final String DOM_LEVEL2FD_CLASS = "org.w3c.dom.Node";
    final String DOM_LEVEL2FD_METHOD = "isSupported";  // String, String
    final Class twoStringArgs[] = { java.lang.String.class,
                                    java.lang.String.class };

    try
    {
      Class clazz = ObjectFactory.findProviderClass(DOM_LEVEL2_CLASS, true);

      Method method = clazz.getMethod(DOM_LEVEL2_METHOD, twoStringArgs);

      // If we succeeded, we have loaded interfaces from a
      //  level 2 DOM somewhere
      h.put(VERSION + "DOM", "2.0");

      try
      {
        // Check for the working draft version, which is
        //  commonly found, but won't work anymore
        clazz = ObjectFactory.findProviderClass(DOM_LEVEL2WD_CLASS, true);

        method = clazz.getMethod(DOM_LEVEL2WD_METHOD, twoStringArgs);

        h.put(ERROR + VERSION + "DOM.draftlevel", "2.0wd");
        h.put(ERROR, ERROR_FOUND);
      }
      catch (Exception e2)
      {
        try
        {
          // Check for the final draft version as well
          clazz = ObjectFactory.findProviderClass(DOM_LEVEL2FD_CLASS, true);

          method = clazz.getMethod(DOM_LEVEL2FD_METHOD, twoStringArgs);

          h.put(VERSION + "DOM.draftlevel", "2.0fd");
        }
        catch (Exception e3)
        {
          h.put(ERROR + VERSION + "DOM.draftlevel", "2.0unknown");
          h.put(ERROR, ERROR_FOUND);
        }
      }
    }
    catch (Exception e)
    {
      h.put(ERROR + VERSION + "DOM",
            "ERROR attempting to load DOM level 2 class: " + e.toString());
      h.put(ERROR, ERROR_FOUND);
    }

    //@todo load an actual DOM implmementation and query it as well
    //@todo load an actual DOM implmementation and check if
    //  isNamespaceAware() == true, which is needed to parse
    //  xsl stylesheet files into a DOM
  }

  /**
   * Report version info from SAX interfaces.
   *
   * Currently distinguishes between SAX 2, SAX 2.0beta2,
   * SAX1, and not found.
   *
   * <p>
   *  从SAX接口报告版本信息。
   * 
   *  目前区分SAX 2,SAX 2.0beta2,SAX1,并未找到。
   * 
   * 
   * @param h Hashtable to put information in
   */
  protected void checkSAXVersion(Hashtable h)
  {

    if (null == h)
      h = new Hashtable();

    final String SAX_VERSION1_CLASS = "org.xml.sax.Parser";
    final String SAX_VERSION1_METHOD = "parse";  // String
    final String SAX_VERSION2_CLASS = "org.xml.sax.XMLReader";
    final String SAX_VERSION2_METHOD = "parse";  // String
    final String SAX_VERSION2BETA_CLASSNF = "org.xml.sax.helpers.AttributesImpl";
    final String SAX_VERSION2BETA_METHODNF = "setAttributes";  // Attributes
    final Class oneStringArg[] = { java.lang.String.class };
    // Note this introduces a minor compile dependency on SAX...
    final Class attributesArg[] = { org.xml.sax.Attributes.class };

    try
    {
      // This method was only added in the final SAX 2.0 release;
      //  see changes.html "Changes from SAX 2.0beta2 to SAX 2.0prerelease"
      Class clazz = ObjectFactory.findProviderClass(SAX_VERSION2BETA_CLASSNF, true);

      Method method = clazz.getMethod(SAX_VERSION2BETA_METHODNF, attributesArg);

      // If we succeeded, we have loaded interfaces from a
      //  real, final SAX version 2.0 somewhere
      h.put(VERSION + "SAX", "2.0");
    }
    catch (Exception e)
    {
      // If we didn't find the SAX 2.0 class, look for a 2.0beta2
      h.put(ERROR + VERSION + "SAX",
            "ERROR attempting to load SAX version 2 class: " + e.toString());
      h.put(ERROR, ERROR_FOUND);

      try
      {
        Class clazz = ObjectFactory.findProviderClass(SAX_VERSION2_CLASS, true);

        Method method = clazz.getMethod(SAX_VERSION2_METHOD, oneStringArg);

        // If we succeeded, we have loaded interfaces from a
        //  SAX version 2.0beta2 or earlier; these might work but
        //  you should really have the final SAX 2.0
        h.put(VERSION + "SAX-backlevel", "2.0beta2-or-earlier");
      }
      catch (Exception e2)
      {
        // If we didn't find the SAX 2.0beta2 class, look for a 1.0 one
        h.put(ERROR + VERSION + "SAX",
              "ERROR attempting to load SAX version 2 class: " + e.toString());
        h.put(ERROR, ERROR_FOUND);

        try
        {
          Class clazz = ObjectFactory.findProviderClass(SAX_VERSION1_CLASS, true);

          Method method = clazz.getMethod(SAX_VERSION1_METHOD, oneStringArg);

          // If we succeeded, we have loaded interfaces from a
          //  SAX version 1.0 somewhere; which won't work very
          //  well for JAXP 1.1 or beyond!
          h.put(VERSION + "SAX-backlevel", "1.0");
        }
        catch (Exception e3)
        {
          // If we didn't find the SAX 2.0 class, look for a 1.0 one
          // Note that either 1.0 or no SAX are both errors
          h.put(ERROR + VERSION + "SAX-backlevel",
                "ERROR attempting to load SAX version 1 class: " + e3.toString());

        }
      }
    }
  }

  /**
   * Manual table of known .jar sizes.
   * Only includes shipped versions of certain projects.
   * key=jarsize, value=jarname ' from ' distro name
   * Note assumption: two jars cannot have the same size!
   *
   * <p>
   *  已知.jar尺寸的手动表。仅包括某些项目的已发布版本。 key = jarsize,value = jarname'from'distro name注意假设：两个jars不能有相同的大小！
   * 
   * 
   * @see #getApparentVersion(String, long)
   */
  private static Hashtable jarVersions = new Hashtable();

  /**
   * Static initializer for jarVersions table.
   * Doing this just once saves time and space.
   *
   * <p>
   *  jarVersions表的静态初始值设置。这只是一次节省时间和空间。
   * 
   * 
   * @see #getApparentVersion(String, long)
   */
  static
  {
    // Note: hackish Hashtable, this could use improvement
    jarVersions.put(new Long(857192), "xalan.jar from xalan-j_1_1");
    jarVersions.put(new Long(440237), "xalan.jar from xalan-j_1_2");
    jarVersions.put(new Long(436094), "xalan.jar from xalan-j_1_2_1");
    jarVersions.put(new Long(426249), "xalan.jar from xalan-j_1_2_2");
    jarVersions.put(new Long(702536), "xalan.jar from xalan-j_2_0_0");
    jarVersions.put(new Long(720930), "xalan.jar from xalan-j_2_0_1");
    jarVersions.put(new Long(732330), "xalan.jar from xalan-j_2_1_0");
    jarVersions.put(new Long(872241), "xalan.jar from xalan-j_2_2_D10");
    jarVersions.put(new Long(882739), "xalan.jar from xalan-j_2_2_D11");
    jarVersions.put(new Long(923866), "xalan.jar from xalan-j_2_2_0");
    jarVersions.put(new Long(905872), "xalan.jar from xalan-j_2_3_D1");
    jarVersions.put(new Long(906122), "xalan.jar from xalan-j_2_3_0");
    jarVersions.put(new Long(906248), "xalan.jar from xalan-j_2_3_1");
    jarVersions.put(new Long(983377), "xalan.jar from xalan-j_2_4_D1");
    jarVersions.put(new Long(997276), "xalan.jar from xalan-j_2_4_0");
    jarVersions.put(new Long(1031036), "xalan.jar from xalan-j_2_4_1");
    // Stop recording xalan.jar sizes as of Xalan Java 2.5.0

    jarVersions.put(new Long(596540), "xsltc.jar from xalan-j_2_2_0");
    jarVersions.put(new Long(590247), "xsltc.jar from xalan-j_2_3_D1");
    jarVersions.put(new Long(589914), "xsltc.jar from xalan-j_2_3_0");
    jarVersions.put(new Long(589915), "xsltc.jar from xalan-j_2_3_1");
    jarVersions.put(new Long(1306667), "xsltc.jar from xalan-j_2_4_D1");
    jarVersions.put(new Long(1328227), "xsltc.jar from xalan-j_2_4_0");
    jarVersions.put(new Long(1344009), "xsltc.jar from xalan-j_2_4_1");
    jarVersions.put(new Long(1348361), "xsltc.jar from xalan-j_2_5_D1");
    // Stop recording xsltc.jar sizes as of Xalan Java 2.5.0

    jarVersions.put(new Long(1268634), "xsltc.jar-bundled from xalan-j_2_3_0");

    jarVersions.put(new Long(100196), "xml-apis.jar from xalan-j_2_2_0 or xalan-j_2_3_D1");
    jarVersions.put(new Long(108484), "xml-apis.jar from xalan-j_2_3_0, or xalan-j_2_3_1 from xml-commons-1.0.b2");
    jarVersions.put(new Long(109049), "xml-apis.jar from xalan-j_2_4_0 from xml-commons RIVERCOURT1 branch");
    jarVersions.put(new Long(113749), "xml-apis.jar from xalan-j_2_4_1 from factoryfinder-build of xml-commons RIVERCOURT1");
    jarVersions.put(new Long(124704), "xml-apis.jar from tck-jaxp-1_2_0 branch of xml-commons");
    jarVersions.put(new Long(124724), "xml-apis.jar from tck-jaxp-1_2_0 branch of xml-commons, tag: xml-commons-external_1_2_01");
    jarVersions.put(new Long(194205), "xml-apis.jar from head branch of xml-commons, tag: xml-commons-external_1_3_02");

    // If the below were more common I would update it to report
    //  errors better; but this is so old hardly anyone has it
    jarVersions.put(new Long(424490), "xalan.jar from Xerces Tools releases - ERROR:DO NOT USE!");

    jarVersions.put(new Long(1591855), "xerces.jar from xalan-j_1_1 from xerces-1...");
    jarVersions.put(new Long(1498679), "xerces.jar from xalan-j_1_2 from xerces-1_2_0.bin");
    jarVersions.put(new Long(1484896), "xerces.jar from xalan-j_1_2_1 from xerces-1_2_1.bin");
    jarVersions.put(new Long(804460),  "xerces.jar from xalan-j_1_2_2 from xerces-1_2_2.bin");
    jarVersions.put(new Long(1499244), "xerces.jar from xalan-j_2_0_0 from xerces-1_2_3.bin");
    jarVersions.put(new Long(1605266), "xerces.jar from xalan-j_2_0_1 from xerces-1_3_0.bin");
    jarVersions.put(new Long(904030), "xerces.jar from xalan-j_2_1_0 from xerces-1_4.bin");
    jarVersions.put(new Long(904030), "xerces.jar from xerces-1_4_0.bin");
    jarVersions.put(new Long(1802885), "xerces.jar from xerces-1_4_2.bin");
    jarVersions.put(new Long(1734594), "xerces.jar from Xerces-J-bin.2.0.0.beta3");
    jarVersions.put(new Long(1808883), "xerces.jar from xalan-j_2_2_D10,D11,D12 or xerces-1_4_3.bin");
    jarVersions.put(new Long(1812019), "xerces.jar from xalan-j_2_2_0");
    jarVersions.put(new Long(1720292), "xercesImpl.jar from xalan-j_2_3_D1");
    jarVersions.put(new Long(1730053), "xercesImpl.jar from xalan-j_2_3_0 or xalan-j_2_3_1 from xerces-2_0_0");
    jarVersions.put(new Long(1728861), "xercesImpl.jar from xalan-j_2_4_D1 from xerces-2_0_1");
    jarVersions.put(new Long(972027), "xercesImpl.jar from xalan-j_2_4_0 from xerces-2_1");
    jarVersions.put(new Long(831587), "xercesImpl.jar from xalan-j_2_4_1 from xerces-2_2");
    jarVersions.put(new Long(891817), "xercesImpl.jar from xalan-j_2_5_D1 from xerces-2_3");
    jarVersions.put(new Long(895924), "xercesImpl.jar from xerces-2_4");
    jarVersions.put(new Long(1010806), "xercesImpl.jar from Xerces-J-bin.2.6.2");
    jarVersions.put(new Long(1203860), "xercesImpl.jar from Xerces-J-bin.2.7.1");

    jarVersions.put(new Long(37485), "xalanj1compat.jar from xalan-j_2_0_0");
    jarVersions.put(new Long(38100), "xalanj1compat.jar from xalan-j_2_0_1");

    jarVersions.put(new Long(18779), "xalanservlet.jar from xalan-j_2_0_0");
    jarVersions.put(new Long(21453), "xalanservlet.jar from xalan-j_2_0_1");
    jarVersions.put(new Long(24826), "xalanservlet.jar from xalan-j_2_3_1 or xalan-j_2_4_1");
    jarVersions.put(new Long(24831), "xalanservlet.jar from xalan-j_2_4_1");
    // Stop recording xalanservlet.jar sizes as of Xalan Java 2.5.0; now a .war file

    // For those who've downloaded JAXP from sun
    jarVersions.put(new Long(5618), "jaxp.jar from jaxp1.0.1");
    jarVersions.put(new Long(136133), "parser.jar from jaxp1.0.1");
    jarVersions.put(new Long(28404), "jaxp.jar from jaxp-1.1");
    jarVersions.put(new Long(187162), "crimson.jar from jaxp-1.1");
    jarVersions.put(new Long(801714), "xalan.jar from jaxp-1.1");
    jarVersions.put(new Long(196399), "crimson.jar from crimson-1.1.1");
    jarVersions.put(new Long(33323), "jaxp.jar from crimson-1.1.1 or jakarta-ant-1.4.1b1");
    jarVersions.put(new Long(152717), "crimson.jar from crimson-1.1.2beta2");
    jarVersions.put(new Long(88143), "xml-apis.jar from crimson-1.1.2beta2");
    jarVersions.put(new Long(206384), "crimson.jar from crimson-1.1.3 or jakarta-ant-1.4.1b1");

    // jakarta-ant: since many people use ant these days
    jarVersions.put(new Long(136198), "parser.jar from jakarta-ant-1.3 or 1.2");
    jarVersions.put(new Long(5537), "jaxp.jar from jakarta-ant-1.3 or 1.2");
  }

  /** Simple PrintWriter we send output to; defaults to System.out.  */
  protected PrintWriter outWriter = new PrintWriter(System.out, true);

  /**
   * Bottleneck output: calls outWriter.println(s).
   * <p>
   *  瓶颈输出：调用outWriter.println(s)。
   * 
   * @param s String to print
   */
  protected void logMsg(String s)
  {
    outWriter.println(s);
  }
}
