/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.sun.org.apache.bcel.internal.util;

/* ====================================================================
 * The Apache Software License, Version 1.1
 *
 * Copyright (c) 2001 The Apache Software Foundation.  All rights
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution,
 *    if any, must include the following acknowledgment:
 *       "This product includes software developed by the
 *        Apache Software Foundation (http://www.apache.org/)."
 *    Alternately, this acknowledgment may appear in the software itself,
 *    if and wherever such third-party acknowledgments normally appear.
 *
 * 4. The names "Apache" and "Apache Software Foundation" and
 *    "Apache BCEL" must not be used to endorse or promote products
 *    derived from this software without prior written permission. For
 *    written permission, please contact apache@apache.org.
 *
 * 5. Products derived from this software may not be called "Apache",
 *    "Apache BCEL", nor may "Apache" appear in their name, without
 *    prior written permission of the Apache Software Foundation.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 * <p>
 *  Apache软件许可证,版本1.1
 * 
 *  版权所有(c)2001 Apache软件基金会。版权所有。
 * 
 *  如果满足以下条件,则允许重新分发和使用源代码和二进制形式(带或不带修改)：
 * 
 *  1.源代码的再分发必须保留上述版权声明,此条件列表和以下免责声明。
 * 
 *  2.二进制形式的再分发必须在分发所提供的文档和/或其他材料中复制上述版权声明,此条件列表和以下免责声明。
 * 
 *  3.包含在重新分发中的最终用户文档(如果有)必须包括以下声明："本产品包括由Apache Software Foundation(http://www.apache.org/)开发的软件。
 * 或者,如果此类第三方确认通常出现,则此确认可能出现在软件本身中。
 * 
 *  4.未经事先书面许可,不得使用名称"Apache"和"Apache Software Foundation"和"Apache BCEL"来认可或推广从本软件衍生的产品。
 * 如需书面许可,请联系apache@apache.org。
 * 
 * 未经Apache软件基金会事先书面许可,从本软件衍生的产品可能不会被称为"Apache","Apache BCEL",也不可能出现在他们的名字中。
 * 
 *  本软件按"原样"提供,任何明示或默示的保证,包括但不限于适销性和特定用途适用性的默示保证。
 * 在任何情况下,APACHE软件基金会或其捐赠者均不对任何直接,间接,偶发,特殊,惩罚性或后果性损害(包括但不限于替代商品或服务的采购,使用,数据丢失或利润或业务中断),无论是由于任何责任推理原因,无论是
 * 在合同,严格责任或侵权(包括疏忽或其他方式)中,以任何方式使用本软件,即使已被告知此类软件的可能性损伤。
 *  本软件按"原样"提供,任何明示或默示的保证,包括但不限于适销性和特定用途适用性的默示保证。
 *  ================================================== ==================。
 * 
 *  该软件包括许多个人代表Apache软件基金会所做的自愿捐款。有关Apache Software Foundation的更多信息,请参阅<http://www.apache.org/>。
 * 
 */

import java.util.*;
import java.util.zip.*;
import java.io.*;

/**
 * Responsible for loading (class) files from the CLASSPATH. Inspired by
 * sun.tools.ClassPath.
 *
 * <p>
 *  负责从CLASSPATH加载(类)文件。启发由sun.tools.ClassPath。
 * 
 * 
 * @version $Id: ClassPath.java,v 1.4 2007-07-19 04:34:52 ofung Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class ClassPath implements Serializable {
  public static final ClassPath SYSTEM_CLASS_PATH = new ClassPath();

  private PathEntry[] paths;
  private String      class_path;

  /**
   * Search for classes in given path.
   * <p>
   *  在给定路径中搜索类。
   * 
   */
  public ClassPath(String class_path) {
    this.class_path = class_path;

    ArrayList vec = new ArrayList();

    for(StringTokenizer tok=new StringTokenizer(class_path,
                            SecuritySupport.getSystemProperty("path.separator"));
        tok.hasMoreTokens();)
    {
      String path = tok.nextToken();

      if(!path.equals("")) {
        File file = new File(path);

        try {
          if(SecuritySupport.getFileExists(file)) {
            if(file.isDirectory())
              vec.add(new Dir(path));
            else
              vec.add(new Zip(new ZipFile(file)));
          }
        } catch(IOException e) {
          System.err.println("CLASSPATH component " + file + ": " + e);
        }
      }
    }

    paths = new PathEntry[vec.size()];
    vec.toArray(paths);
  }

  /**
   * Search for classes in CLASSPATH.
   * <p>
   *  在CLASSPATH中搜索类。
   * 
   * 
   * @deprecated Use SYSTEM_CLASS_PATH constant
   */
  public ClassPath() {
    // this(getClassPath());
    this("");
  }

  /** @return used class path string
  /* <p>
   */
  public String toString() {
    return class_path;
  }

  public int hashCode() {
    return class_path.hashCode();
  }

  public boolean equals(Object o) {
    if(o instanceof ClassPath) {
      return class_path.equals(((ClassPath)o).class_path);
    }

    return false;
  }

  private static final void getPathComponents(String path, ArrayList list) {
    if(path != null) {
      StringTokenizer tok = new StringTokenizer(path, File.pathSeparator);

      while(tok.hasMoreTokens()) {
        String name = tok.nextToken();
        File   file = new File(name);

        if(SecuritySupport.getFileExists(file)) {
          list.add(name);
        }
      }
    }
  }

  /** Checks for class path components in the following properties:
   * "java.class.path", "sun.boot.class.path", "java.ext.dirs"
   *
   * <p>
   *  "java.class.path","sun.boot.class.path","java.ext.dirs"
   * 
   * 
   * @return class path as used by default by BCEL
   */
  public static final String getClassPath() {

    String class_path, boot_path, ext_path;

    try {
      class_path = SecuritySupport.getSystemProperty("java.class.path");
      boot_path  = SecuritySupport.getSystemProperty("sun.boot.class.path");
      ext_path   = SecuritySupport.getSystemProperty("java.ext.dirs");
    }
    catch (SecurityException e) {
        return "";
    }

    ArrayList list = new ArrayList();

    getPathComponents(class_path, list);
    getPathComponents(boot_path, list);

    ArrayList dirs = new ArrayList();
    getPathComponents(ext_path, dirs);

    for(Iterator e = dirs.iterator(); e.hasNext(); ) {
      File ext_dir = new File((String)e.next());
      String[] extensions = SecuritySupport.getFileList(ext_dir, new FilenameFilter() {
        public boolean accept(File dir, String name) {
          name = name.toLowerCase();
          return name.endsWith(".zip") || name.endsWith(".jar");
        }
      });

      if(extensions != null)
        for(int i=0; i < extensions.length; i++)
          list.add(ext_path + File.separatorChar + extensions[i]);
    }

    StringBuffer buf = new StringBuffer();

    for(Iterator e = list.iterator(); e.hasNext(); ) {
      buf.append((String)e.next());

      if(e.hasNext())
        buf.append(File.pathSeparatorChar);
    }

    return buf.toString().intern();
  }

  /**
  /* <p>
  /* 
   * @param name fully qualified class name, e.g. java.lang.String
   * @return input stream for class
   */
  public InputStream getInputStream(String name) throws IOException {
    return getInputStream(name, ".class");
  }

  /**
   * Return stream for class or resource on CLASSPATH.
   *
   * <p>
   *  返回CLASSPATH上类或资源的流。
   * 
   * 
   * @param name fully qualified file name, e.g. java/lang/String
   * @param suffix file name ends with suff, e.g. .java
   * @return input stream for file on class path
   */
  public InputStream getInputStream(String name, String suffix) throws IOException {
    InputStream is = null;

    try {
      is = getClass().getClassLoader().getResourceAsStream(name + suffix);
    } catch(Exception e) { }

    if(is != null)
      return is;

    return getClassFile(name, suffix).getInputStream();
  }

  /**
  /* <p>
  /* 
   * @param name fully qualified file name, e.g. java/lang/String
   * @param suffix file name ends with suff, e.g. .java
   * @return class file for the java class
   */
  public ClassFile getClassFile(String name, String suffix) throws IOException {
    for(int i=0; i < paths.length; i++) {
      ClassFile cf;

      if((cf = paths[i].getClassFile(name, suffix)) != null)
        return cf;
    }

    throw new IOException("Couldn't find: " + name + suffix);
  }

  /**
  /* <p>
  /* 
   * @param name fully qualified class name, e.g. java.lang.String
   * @return input stream for class
   */
  public ClassFile getClassFile(String name) throws IOException {
    return getClassFile(name, ".class");
  }

  /**
  /* <p>
  /* 
   * @param name fully qualified file name, e.g. java/lang/String
   * @param suffix file name ends with suffix, e.g. .java
   * @return byte array for file on class path
   */
  public byte[] getBytes(String name, String suffix) throws IOException {
    InputStream is = getInputStream(name, suffix);

    if(is == null)
      throw new IOException("Couldn't find: " + name + suffix);

    DataInputStream dis   = new DataInputStream(is);
    byte[]          bytes = new byte[is.available()];
    dis.readFully(bytes);
    dis.close(); is.close();

    return bytes;
  }

  /**
  /* <p>
  /* 
   * @return byte array for class
   */
  public byte[] getBytes(String name) throws IOException {
    return getBytes(name, ".class");
  }

  /**
  /* <p>
  /* 
   * @param name name of file to search for, e.g. java/lang/String.java
   * @return full (canonical) path for file
   */
  public String getPath(String name) throws IOException {
    int    index  = name.lastIndexOf('.');
    String suffix = "";

    if(index > 0) {
      suffix = name.substring(index);
      name   = name.substring(0, index);
    }

    return getPath(name, suffix);
  }

  /**
  /* <p>
  /* 
   * @param name name of file to search for, e.g. java/lang/String
   * @param suffix file name suffix, e.g. .java
   * @return full (canonical) path for file, if it exists
   */
  public String getPath(String name, String suffix) throws IOException {
    return getClassFile(name, suffix).getPath();
  }

  private static abstract class PathEntry implements Serializable {
    abstract ClassFile getClassFile(String name, String suffix) throws IOException;
  }

  /** Contains information about file/ZIP entry of the Java class.
  /* <p>
   */
  public interface ClassFile {
    /** @return input stream for class file.
    /* <p>
     */
    public abstract InputStream getInputStream() throws IOException;

    /** @return canonical path to class file.
    /* <p>
     */
    public abstract String getPath();

    /** @return base path of found class, i.e. class is contained relative
     * to that path, which may either denote a directory, or zip file
     * <p>
     * 到该路径,其可以表示目录或zip文件
     * 
     */
    public abstract String getBase();

    /** @return modification time of class file.
    /* <p>
     */
    public abstract long getTime();

    /** @return size of class file.
    /* <p>
     */
    public abstract long getSize();
  }

  private static class Dir extends PathEntry {
    private String dir;

    Dir(String d) { dir = d; }

    ClassFile getClassFile(String name, String suffix) throws IOException {
      final File file = new File(dir + File.separatorChar +
                                 name.replace('.', File.separatorChar) + suffix);

      return SecuritySupport.getFileExists(file)? new ClassFile() {
        public InputStream getInputStream() throws IOException { return new FileInputStream(file); }

        public String      getPath()        { try {
          return file.getCanonicalPath();
        } catch(IOException e) { return null; }

        }
        public long        getTime()        { return file.lastModified(); }
        public long        getSize()        { return file.length(); }
        public String getBase() {  return dir;  }

      } : null;
    }

    public String toString() { return dir; }
  }

  private static class Zip extends PathEntry {
    private ZipFile zip;

    Zip(ZipFile z) { zip = z; }

    ClassFile getClassFile(String name, String suffix) throws IOException {
      final ZipEntry entry = zip.getEntry(name.replace('.', '/') + suffix);

      return (entry != null)? new ClassFile() {
        public InputStream getInputStream() throws IOException { return zip.getInputStream(entry); }
        public String      getPath()        { return entry.toString(); }
        public long        getTime()        { return entry.getTime(); }
        public long        getSize()       { return entry.getSize(); }
        public String getBase() {
          return zip.getName();
        }
      } : null;
    }
  }
}
