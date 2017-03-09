/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.sun.org.apache.bcel.internal.classfile;

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

import  com.sun.org.apache.bcel.internal.Constants;
import  com.sun.org.apache.bcel.internal.util.SyntheticRepository;
import  com.sun.org.apache.bcel.internal.util.ClassVector;
import  com.sun.org.apache.bcel.internal.util.ClassQueue;
import  com.sun.org.apache.bcel.internal.generic.Type;
import com.sun.org.apache.xalan.internal.utils.SecuritySupport;

import  java.io.*;
import  java.util.StringTokenizer;

/**
 * Represents a Java class, i.e., the data structures, constant pool,
 * fields, methods and commands contained in a Java .class file.
 * See <a href="ftp://java.sun.com/docs/specs/">JVM
 * specification</a> for details.

 * The intent of this class is to represent a parsed or otherwise existing
 * class file.  Those interested in programatically generating classes
 * should see the <a href="../generic/ClassGen.html">ClassGen</a> class.

 * <p>
 *  表示Java类,即包含在Java .class文件中的数据结构,常量池,字段,方法和命令。
 * 有关详细信息,请参见<a href="ftp://java.sun.com/docs/specs/"> JVM规范</a>。
 * 
 * 这个类的意图是表示一个解析的或其他存在的类文件。对程序生成类感兴趣的用户应该看到<a href="../generic/ClassGen.html"> ClassGen </a>类。
 * 
 * 
 * @version $Id: JavaClass.java,v 1.4 2007-07-19 04:34:42 ofung Exp $
 * @see com.sun.org.apache.bcel.internal.generic.ClassGen
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class JavaClass extends AccessFlags implements Cloneable, Node {
  private String       file_name;
  private String       package_name;
  private String       source_file_name = "<Unknown>";
  private int          class_name_index;
  private int          superclass_name_index;
  private String       class_name;
  private String       superclass_name;
  private int          major, minor;  // Compiler version
  private ConstantPool constant_pool; // Constant pool
  private int[]        interfaces;    // implemented interfaces
  private String[]     interface_names;
  private Field[]      fields;        // Fields, i.e., variables of class
  private Method[]     methods;       // methods defined in the class
  private Attribute[]  attributes;    // attributes defined in the class
  private byte         source = HEAP; // Generated in memory

  public static final byte HEAP = 1;
  public static final byte FILE = 2;
  public static final byte ZIP  = 3;

  static boolean debug = false; // Debugging on/off
  static char    sep   = '/';   // directory separator

  /**
   * In cases where we go ahead and create something,
   * use the default SyntheticRepository, because we
   * don't know any better.
   * <p>
   *  在我们继续创建的情况下,使用默认的SyntheticRepository,因为我们不知道任何更好。
   * 
   */
  private transient com.sun.org.apache.bcel.internal.util.Repository repository =
    SyntheticRepository.getInstance();

  /**
   * Constructor gets all contents as arguments.
   *
   * <p>
   *  构造函数获取所有内容作为参数。
   * 
   * 
   * @param class_name_index Index into constant pool referencing a
   * ConstantClass that represents this class.
   * @param superclass_name_index Index into constant pool referencing a
   * ConstantClass that represents this class's superclass.
   * @param file_name File name
   * @param major Major compiler version
   * @param minor Minor compiler version
   * @param access_flags Access rights defined by bit flags
   * @param constant_pool Array of constants
   * @param interfaces Implemented interfaces
   * @param fields Class fields
   * @param methods Class methods
   * @param attributes Class attributes
   * @param source Read from file or generated in memory?
   */
  public JavaClass(int        class_name_index,
                   int        superclass_name_index,
                   String     file_name,
                   int        major,
                   int        minor,
                   int        access_flags,
                   ConstantPool constant_pool,
                   int[]      interfaces,
                   Field[]      fields,
                   Method[]     methods,
                   Attribute[]  attributes,
                   byte          source)
  {
    if(interfaces == null) // Allowed for backward compatibility
      interfaces = new int[0];
    if(attributes == null)
      this.attributes = new Attribute[0];
    if(fields == null)
      fields = new Field[0];
    if(methods == null)
      methods = new Method[0];

    this.class_name_index      = class_name_index;
    this.superclass_name_index = superclass_name_index;
    this.file_name             = file_name;
    this.major                 = major;
    this.minor                 = minor;
    this.access_flags          = access_flags;
    this.constant_pool         = constant_pool;
    this.interfaces            = interfaces;
    this.fields                = fields;
    this.methods               = methods;
    this.attributes            = attributes;
    this.source                = source;

    // Get source file name if available
    for(int i=0; i < attributes.length; i++) {
      if(attributes[i] instanceof SourceFile) {
        source_file_name = ((SourceFile)attributes[i]).getSourceFileName();
        break;
      }
    }

    /* According to the specification the following entries must be of type
     * `ConstantClass' but we check that anyway via the
     * `ConstPool.getConstant' method.
     * <p>
     *  `ConstantClass',但是我们通过`ConstPool.getConstant'方法检查。
     * 
     */
    class_name = constant_pool.getConstantString(class_name_index,
                                                 Constants.CONSTANT_Class);
    class_name = Utility.compactClassName(class_name, false);

    int index = class_name.lastIndexOf('.');
    if(index < 0)
      package_name = "";
    else
      package_name = class_name.substring(0, index);

    if(superclass_name_index > 0) { // May be zero -> class is java.lang.Object
      superclass_name = constant_pool.getConstantString(superclass_name_index,
                                                        Constants.CONSTANT_Class);
      superclass_name = Utility.compactClassName(superclass_name, false);
    }
    else
      superclass_name = "java.lang.Object";

    interface_names = new String[interfaces.length];
    for(int i=0; i < interfaces.length; i++) {
      String str = constant_pool.getConstantString(interfaces[i], Constants.CONSTANT_Class);
      interface_names[i] = Utility.compactClassName(str, false);
    }
  }

  /**
   * Constructor gets all contents as arguments.
   *
   * <p>
   *  构造函数获取所有内容作为参数。
   * 
   * 
   * @param class_name_index Class name
   * @param superclass_name_index Superclass name
   * @param file_name File name
   * @param major Major compiler version
   * @param minor Minor compiler version
   * @param access_flags Access rights defined by bit flags
   * @param constant_pool Array of constants
   * @param interfaces Implemented interfaces
   * @param fields Class fields
   * @param methods Class methods
   * @param attributes Class attributes
   */
  public JavaClass(int        class_name_index,
                   int        superclass_name_index,
                   String     file_name,
                   int        major,
                   int        minor,
                   int        access_flags,
                   ConstantPool constant_pool,
                   int[]      interfaces,
                   Field[]      fields,
                   Method[]     methods,
                   Attribute[]  attributes) {
    this(class_name_index, superclass_name_index, file_name, major, minor, access_flags,
         constant_pool, interfaces, fields, methods, attributes, HEAP);
  }


  /**
   * Called by objects that are traversing the nodes of the tree implicitely
   * defined by the contents of a Java class. I.e., the hierarchy of methods,
   * fields, attributes, etc. spawns a tree of objects.
   *
   * <p>
   *  由遍历由Java类的内容隐含地定义的树的节点的对象调用。即,方法,字段,属性等的层次结构产生对象树。
   * 
   * 
   * @param v Visitor object
   */
  public void accept(Visitor v) {
    v.visitJavaClass(this);
  }

  /* Print debug information depending on `JavaClass.debug'
  /* <p>
   */
  static final void Debug(String str) {
    if(debug)
      System.out.println(str);
  }

  /**
   * Dump class to a file.
   *
   * <p>
   *  将类转储到文件。
   * 
   * 
   * @param file Output file
   * @throws IOException
   */
  public void dump(File file) throws IOException
  {
    String parent = file.getParent();

    if(parent != null) {
      File dir = new File(parent);

      if(dir != null)
        dir.mkdirs();
    }

    dump(new DataOutputStream(new FileOutputStream(file)));
  }

  /**
   * Dump class to a file named file_name.
   *
   * <p>
   *  将类转储到名为file_name的文件。
   * 
   * 
   * @param file_name Output file name
   * @exception IOException
   */
  public void dump(String file_name) throws IOException
  {
    dump(new File(file_name));
  }

  /**
  /* <p>
  /* 
   * @return class in binary format
   */
  public byte[] getBytes() {
    ByteArrayOutputStream s  = new ByteArrayOutputStream();
    DataOutputStream      ds = new DataOutputStream(s);

    try {
      dump(ds);
    } catch(IOException e) {
      e.printStackTrace();
    } finally {
      try { ds.close(); } catch(IOException e2) { e2.printStackTrace(); }
    }

    return s.toByteArray();
  }

  /**
   * Dump Java class to output stream in binary format.
   *
   * <p>
   *  将二进制格式的Java类转储为输出流。
   * 
   * 
   * @param file Output stream
   * @exception IOException
   */
  public void dump(OutputStream file) throws IOException {
    dump(new DataOutputStream(file));
  }

  /**
   * Dump Java class to output stream in binary format.
   *
   * <p>
   *  将二进制格式的Java类转储为输出流。
   * 
   * 
   * @param file Output stream
   * @exception IOException
   */
  public void dump(DataOutputStream file) throws IOException
  {
    file.writeInt(0xcafebabe);
    file.writeShort(minor);
    file.writeShort(major);

    constant_pool.dump(file);

    file.writeShort(access_flags);
    file.writeShort(class_name_index);
    file.writeShort(superclass_name_index);

    file.writeShort(interfaces.length);
    for(int i=0; i < interfaces.length; i++)
      file.writeShort(interfaces[i]);

    file.writeShort(fields.length);
    for(int i=0; i < fields.length; i++)
      fields[i].dump(file);

    file.writeShort(methods.length);
    for(int i=0; i < methods.length; i++)
      methods[i].dump(file);

    if(attributes != null) {
      file.writeShort(attributes.length);
      for(int i=0; i < attributes.length; i++)
        attributes[i].dump(file);
    }
    else
      file.writeShort(0);

    file.close();
  }

  /**
  /* <p>
  /* 
   * @return Attributes of the class.
   */
  public Attribute[] getAttributes() { return attributes; }

  /**
  /* <p>
  /* 
   * @return Class name.
   */
  public String getClassName()       { return class_name; }

  /**
  /* <p>
  /* 
   * @return Package name.
   */
  public String getPackageName()       { return package_name; }

  /**
  /* <p>
  /* 
   * @return Class name index.
   */
  public int getClassNameIndex()   { return class_name_index; }

  /**
  /* <p>
  /* 
   * @return Constant pool.
   */
  public ConstantPool getConstantPool() { return constant_pool; }

  /**
  /* <p>
  /* 
   * @return Fields, i.e., variables of the class. Like the JVM spec
   * mandates for the classfile format, these fields are those specific to
   * this class, and not those of the superclass or superinterfaces.
   */
  public Field[] getFields()         { return fields; }

  /**
  /* <p>
  /* 
   * @return File name of class, aka SourceFile attribute value
   */
  public String getFileName()        { return file_name; }

  /**
  /* <p>
  /* 
   * @return Names of implemented interfaces.
   */
  public String[] getInterfaceNames()  { return interface_names; }

  /**
  /* <p>
  /* 
   * @return Indices in constant pool of implemented interfaces.
   */
  public int[] getInterfaceIndices()     { return interfaces; }

  /**
  /* <p>
  /* 
   * @return Major number of class file version.
   */
  public int  getMajor()           { return major; }

  /**
  /* <p>
  /* 
   * @return Methods of the class.
   */
  public Method[] getMethods()       { return methods; }

  /**
  /* <p>
  /* 
   * @return A com.sun.org.apache.bcel.internal.classfile.Method corresponding to
   * java.lang.reflect.Method if any
   */
  public Method getMethod(java.lang.reflect.Method m) {
    for(int i = 0; i < methods.length; i++) {
      Method method = methods[i];

      if(m.getName().equals(method.getName()) &&
         (m.getModifiers() == method.getModifiers()) &&
         Type.getSignature(m).equals(method.getSignature())) {
        return method;
      }
    }

    return null;
  }

  /**
  /* <p>
  /* 
   * @return Minor number of class file version.
   */
  public int  getMinor()           { return minor; }

  /**
  /* <p>
  /* 
   * @return sbsolute path to file where this class was read from
   */
  public String getSourceFileName()  { return source_file_name; }

  /**
  /* <p>
  /* 
   * @return Superclass name.
   */
  public String getSuperclassName()  { return superclass_name; }

  /**
  /* <p>
  /* 
   * @return Class name index.
   */
  public int getSuperclassNameIndex() { return superclass_name_index; }

  static {
    // Debugging ... on/off
    String debug = null, sep = null;

    try {
      debug = SecuritySupport.getSystemProperty("JavaClass.debug");
      // Get path separator either / or \ usually
      sep = SecuritySupport.getSystemProperty("file.separator");
    }
    catch (SecurityException e) {
        // falls through
    }

    if(debug != null)
      JavaClass.debug = new Boolean(debug).booleanValue();

    if(sep != null)
      try {
        JavaClass.sep = sep.charAt(0);
      } catch(StringIndexOutOfBoundsException e) {} // Never reached
  }

  /**
  /* <p>
  /* 
   * @param attributes .
   */
  public void setAttributes(Attribute[] attributes) {
    this.attributes = attributes;
  }

  /**
  /* <p>
  /* 
   * @param class_name .
   */
  public void setClassName(String class_name) {
    this.class_name = class_name;
  }

  /**
  /* <p>
  /* 
   * @param class_name_index .
   */
  public void setClassNameIndex(int class_name_index) {
    this.class_name_index = class_name_index;
  }

  /**
  /* <p>
  /* 
   * @param constant_pool .
   */
  public void setConstantPool(ConstantPool constant_pool) {
    this.constant_pool = constant_pool;
  }

  /**
  /* <p>
  /* 
   * @param fields .
   */
  public void setFields(Field[] fields) {
    this.fields = fields;
  }

  /**
   * Set File name of class, aka SourceFile attribute value
   * <p>
   *  设置类的文件名,也称为SourceFile属性值
   * 
   */
  public void setFileName(String file_name) {
    this.file_name = file_name;
  }

  /**
  /* <p>
  /* 
   * @param interface_names .
   */
  public void setInterfaceNames(String[] interface_names) {
    this.interface_names = interface_names;
  }

  /**
  /* <p>
  /* 
   * @param interfaces .
   */
  public void setInterfaces(int[] interfaces) {
    this.interfaces = interfaces;
  }

  /**
  /* <p>
  /* 
   * @param major .
   */
  public void setMajor(int major) {
    this.major = major;
  }

  /**
  /* <p>
  /* 
   * @param methods .
   */
  public void setMethods(Method[] methods) {
    this.methods = methods;
  }

  /**
  /* <p>
  /* 
   * @param minor .
   */
  public void setMinor(int minor) {
    this.minor = minor;
  }

  /**
   * Set absolute path to file this class was read from.
   * <p>
   *  设置从此类读取的文件的绝对路径。
   * 
   */
  public void setSourceFileName(String source_file_name) {
    this.source_file_name = source_file_name;
  }

  /**
  /* <p>
  /* 
   * @param superclass_name .
   */
  public void setSuperclassName(String superclass_name) {
    this.superclass_name = superclass_name;
  }

  /**
  /* <p>
  /* 
   * @param superclass_name_index .
   */
  public void setSuperclassNameIndex(int superclass_name_index) {
    this.superclass_name_index = superclass_name_index;
  }

  /**
  /* <p>
  /* 
   * @return String representing class contents.
   */
  public String toString() {
    String access = Utility.accessToString(access_flags, true);
    access = access.equals("")? "" : (access + " ");

    StringBuffer buf = new StringBuffer(access +
                                        Utility.classOrInterface(access_flags) +
                                        " " +
                                        class_name + " extends " +
                                        Utility.compactClassName(superclass_name,
                                                                 false) + '\n');
    int size = interfaces.length;

    if(size > 0) {
      buf.append("implements\t\t");

      for(int i=0; i < size; i++) {
        buf.append(interface_names[i]);
        if(i < size - 1)
          buf.append(", ");
      }

      buf.append('\n');
    }

    buf.append("filename\t\t" + file_name + '\n');
    buf.append("compiled from\t\t" + source_file_name + '\n');
    buf.append("compiler version\t" + major + "." + minor + '\n');
    buf.append("access flags\t\t" + access_flags + '\n');
    buf.append("constant pool\t\t" + constant_pool.getLength() + " entries\n");
    buf.append("ACC_SUPER flag\t\t" + isSuper() + "\n");

    if(attributes.length > 0) {
      buf.append("\nAttribute(s):\n");
      for(int i=0; i < attributes.length; i++)
        buf.append(indent(attributes[i]));
    }

    if(fields.length > 0) {
      buf.append("\n" + fields.length + " fields:\n");
      for(int i=0; i < fields.length; i++)
        buf.append("\t" + fields[i] + '\n');
    }

    if(methods.length > 0) {
      buf.append("\n" + methods.length + " methods:\n");
      for(int i=0; i < methods.length; i++)
        buf.append("\t" + methods[i] + '\n');
    }

    return buf.toString();
  }

  private static final String indent(Object obj) {
    StringTokenizer tok = new StringTokenizer(obj.toString(), "\n");
    StringBuffer buf = new StringBuffer();

    while(tok.hasMoreTokens())
      buf.append("\t" + tok.nextToken() + "\n");

    return buf.toString();
  }

  /**
  /* <p>
  /* 
   * @return deep copy of this class
   */
  public JavaClass copy() {
    JavaClass c = null;

    try {
      c = (JavaClass)clone();
    } catch(CloneNotSupportedException e) {}

    c.constant_pool   = constant_pool.copy();
    c.interfaces      = (int[])interfaces.clone();
    c.interface_names = (String[])interface_names.clone();

    c.fields = new Field[fields.length];
    for(int i=0; i < fields.length; i++)
      c.fields[i] = fields[i].copy(c.constant_pool);

    c.methods = new Method[methods.length];
    for(int i=0; i < methods.length; i++)
      c.methods[i] = methods[i].copy(c.constant_pool);

    c.attributes = new Attribute[attributes.length];
    for(int i=0; i < attributes.length; i++)
      c.attributes[i] = attributes[i].copy(c.constant_pool);

    return c;
  }

  public final boolean isSuper() {
    return (access_flags & Constants.ACC_SUPER) != 0;
  }

  public final boolean isClass() {
    return (access_flags & Constants.ACC_INTERFACE) == 0;
  }

  /** @return returns either HEAP (generated), FILE, or ZIP
  /* <p>
   */
  public final byte getSource() {
    return source;
  }

  /********************* New repository functionality *********************/

  /**
   * Gets the ClassRepository which holds its definition. By default
   * this is the same as SyntheticRepository.getInstance();
   * <p>
   *  获取保存其定义的ClassRepository。默认情况下,这与SyntheticRepository.getInstance()相同;
   * 
   */
  public com.sun.org.apache.bcel.internal.util.Repository getRepository() {
    return repository;
  }

  /**
   * Sets the ClassRepository which loaded the JavaClass.
   * Should be called immediately after parsing is done.
   * <p>
   *  设置加载JavaClass的ClassRepository。应该在解析完​​成后立即调用。
   * 
   */
  public void setRepository(com.sun.org.apache.bcel.internal.util.Repository repository) {
    this.repository = repository;
  }

  /** Equivalent to runtime "instanceof" operator.
   *
   * <p>
   * 
   * @return true if this JavaClass is derived from teh super class
   */
  public final boolean instanceOf(JavaClass super_class) {
    if(this.equals(super_class))
      return true;

    JavaClass[] super_classes = getSuperClasses();

    for(int i=0; i < super_classes.length; i++) {
      if(super_classes[i].equals(super_class)) {
        return true;
      }
    }

    if(super_class.isInterface()) {
      return implementationOf(super_class);
    }

    return false;
  }

  /**
  /* <p>
  /* 
   * @return true, if clazz is an implementation of interface inter
   */
  public boolean implementationOf(JavaClass inter) {
    if(!inter.isInterface()) {
      throw new IllegalArgumentException(inter.getClassName() + " is no interface");
    }

    if(this.equals(inter)) {
      return true;
    }

    JavaClass[] super_interfaces = getAllInterfaces();

    for(int i=0; i < super_interfaces.length; i++) {
      if(super_interfaces[i].equals(inter)) {
        return true;
      }
    }

    return false;
  }

  /**
  /* <p>
  /* 
   * @return the superclass for this JavaClass object, or null if this
   * is java.lang.Object
   */
  public JavaClass getSuperClass() {
    if("java.lang.Object".equals(getClassName())) {
      return null;
    }

    try {
      return repository.loadClass(getSuperclassName());
    } catch(ClassNotFoundException e) {
      System.err.println(e);
      return null;
    }
  }

  /**
  /* <p>
  /* 
   * @return list of super classes of this class in ascending order, i.e.,
   * java.lang.Object is always the last element
   */
  public JavaClass[] getSuperClasses() {
    JavaClass   clazz = this;
    ClassVector vec   = new ClassVector();

    for(clazz = clazz.getSuperClass(); clazz != null;
        clazz = clazz.getSuperClass())
    {
      vec.addElement(clazz);
    }

    return vec.toArray();
  }

  /**
   * Get interfaces directly implemented by this JavaClass.
   * <p>
   *  获取由此JavaClass直接实现的接口。
   * 
   */
  public JavaClass[] getInterfaces() {
    String[]    interfaces = getInterfaceNames();
    JavaClass[] classes    = new JavaClass[interfaces.length];

    try {
      for(int i = 0; i < interfaces.length; i++) {
        classes[i] = repository.loadClass(interfaces[i]);
      }
    } catch(ClassNotFoundException e) {
      System.err.println(e);
      return null;
    }

    return classes;
  }

  /**
   * Get all interfaces implemented by this JavaClass (transitively).
   * <p>
   *  获取由此JavaClass实现的所有接口(传递)。
   */
  public JavaClass[] getAllInterfaces() {
    ClassQueue  queue = new ClassQueue();
    ClassVector vec   = new ClassVector();

    queue.enqueue(this);

    while(!queue.empty()) {
      JavaClass clazz = queue.dequeue();

      JavaClass   souper     = clazz.getSuperClass();
      JavaClass[] interfaces = clazz.getInterfaces();

      if(clazz.isInterface()) {
        vec.addElement(clazz);
      } else {
        if(souper != null) {
          queue.enqueue(souper);
        }
      }

      for(int i = 0; i < interfaces.length; i++) {
        queue.enqueue(interfaces[i]);
      }
    }

    return vec.toArray();
  }
}
