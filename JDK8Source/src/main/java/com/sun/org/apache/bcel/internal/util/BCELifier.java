/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.sun.org.apache.bcel.internal.util;

/* ====================================================================
 * The Apache Software License, Version 1.1
 *
 * Copyright (c) 2002 The Apache Software Foundation.  All rights
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
 *  版权所有(c)2002 Apache软件基金会。版权所有。
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
 */
import com.sun.org.apache.bcel.internal.classfile.*;
import com.sun.org.apache.bcel.internal.generic.*;
import com.sun.org.apache.bcel.internal.Repository;
import com.sun.org.apache.bcel.internal.Constants;
import java.io.*;

/**
 * This class takes a given JavaClass object and converts it to a
 * Java program that creates that very class using BCEL. This
 * gives new users of BCEL a useful example showing how things
 * are done with BCEL. It does not cover all features of BCEL,
 * but tries to mimic hand-written code as close as possible.
 *
 * <p>
 * 
 *  本软件按"原样"提供,任何明示或默示的保证,包括但不限于适销性和特定用途适用性的默示保证。
 * 在任何情况下,APACHE软件基金会或其捐赠者均不对任何直接,间接,偶发,特殊,惩罚性或后果性损害(包括但不限于替代商品或服务的采购,使用,数据丢失或利润或业务中断),无论是由于任何责任推理原因,无论是
 * 在合同,严格责任或侵权(包括疏忽或其他方式)中,以任何方式使用本软件,即使已被告知此类软件的可能性损伤。
 *  本软件按"原样"提供,任何明示或默示的保证,包括但不限于适销性和特定用途适用性的默示保证。
 *  ================================================== ==================。
 * 
 *  该软件包括许多个人代表Apache软件基金会所做的自愿捐款。有关Apache Software Foundation的更多信息,请参阅<http://www.apache.org/>。
 * 
 * @author <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class BCELifier extends com.sun.org.apache.bcel.internal.classfile.EmptyVisitor {
  private JavaClass         _clazz;
  private PrintWriter       _out;
  private ConstantPoolGen   _cp;

  /** @param clazz Java class to "decompile"
  /* <p>
  /* 
  /* 
   * @param out where to output Java program
   */
  public BCELifier(JavaClass clazz, OutputStream out) {
    _clazz = clazz;
    _out = new PrintWriter(out);
    _cp = new ConstantPoolGen(_clazz.getConstantPool());
  }

  /** Start Java code generation
  /* <p>
  /* 此类接受一个给定的JavaClass对象,并将其转换为使用BCEL创建该类的Java程序。这为BCEL的新用户提供了一个有用的示例,显示如何使用BCEL完成操作。
  /* 它不包括BCEL的所有功能,但尝试模仿手写代码尽可能近。
  /* 
   */
  public void start() {
    visitJavaClass(_clazz);
    _out.flush();
  }

  public void visitJavaClass(JavaClass clazz) {
    String class_name   = clazz.getClassName();
    String super_name   = clazz.getSuperclassName();
    String package_name = clazz.getPackageName();
    String inter        = Utility.printArray(clazz.getInterfaceNames(),
                                             false, true);
    if(!"".equals(package_name)) {
      class_name = class_name.substring(package_name.length() + 1);
      _out.println("package " + package_name + ";\n");
     }

    _out.println("import com.sun.org.apache.bcel.internal.generic.*;");
    _out.println("import com.sun.org.apache.bcel.internal.classfile.*;");
    _out.println("import com.sun.org.apache.bcel.internal.*;");
    _out.println("import java.io.*;\n");

    _out.println("public class " + class_name + "Creator implements Constants {");
    _out.println("  private InstructionFactory _factory;");
    _out.println("  private ConstantPoolGen    _cp;");
    _out.println("  private ClassGen           _cg;\n");

    _out.println("  public " + class_name  + "Creator() {");
    _out.println("    _cg = new ClassGen(\"" +
                 (("".equals(package_name))? class_name :
                  package_name + "." + class_name) +
                 "\", \"" + super_name + "\", " +
                 "\"" + clazz.getSourceFileName() + "\", " +
                 printFlags(clazz.getAccessFlags(), true) + ", " +
                 "new String[] { " + inter + " });\n");

    _out.println("    _cp = _cg.getConstantPool();");
    _out.println("    _factory = new InstructionFactory(_cg, _cp);");
    _out.println("  }\n");

    printCreate();

    Field[] fields = clazz.getFields();

    if(fields.length > 0) {
      _out.println("  private void createFields() {");
      _out.println("    FieldGen field;");

      for(int i=0; i < fields.length; i++) {
        fields[i].accept(this);
      }

      _out.println("  }\n");
    }

    Method[] methods = clazz.getMethods();

    for(int i=0; i < methods.length; i++) {
      _out.println("  private void createMethod_" + i + "() {");

      methods[i].accept(this);
      _out.println("  }\n");
    }

    printMain();
    _out.println("}");
  }

  private void printCreate() {
    _out.println("  public void create(OutputStream out) throws IOException {");

    Field[] fields = _clazz.getFields();
    if(fields.length > 0) {
      _out.println("    createFields();");
    }

    Method[] methods = _clazz.getMethods();
    for(int i=0; i < methods.length; i++) {
      _out.println("    createMethod_" + i + "();");
    }

    _out.println("    _cg.getJavaClass().dump(out);");

    _out.println("  }\n");
  }

  private void printMain() {
    String   class_name   = _clazz.getClassName();

    _out.println("  public static void _main(String[] args) throws Exception {");
    _out.println("    " + class_name + "Creator creator = new " +
                 class_name + "Creator();");
    _out.println("    creator.create(new FileOutputStream(\"" + class_name +
                 ".class\"));");
    _out.println("  }");
  }

  public void visitField(Field field) {
    _out.println("\n    field = new FieldGen(" +
                 printFlags(field.getAccessFlags()) +
                 ", " + printType(field.getSignature()) + ", \"" +
                 field.getName() + "\", _cp);");

    ConstantValue cv = field.getConstantValue();

    if(cv != null) {
      String value = cv.toString();
      _out.println("    field.setInitValue(" + value + ")");
    }

    _out.println("    _cg.addField(field.getField());");
  }

  public void visitMethod(Method method) {
    MethodGen mg = new MethodGen(method, _clazz.getClassName(), _cp);

    Type   result_type = mg.getReturnType();
    Type[] arg_types   = mg.getArgumentTypes();

    _out.println("    InstructionList il = new InstructionList();");
    _out.println("    MethodGen method = new MethodGen(" +
                 printFlags(method.getAccessFlags()) +
                 ", " + printType(result_type) +
                 ", " + printArgumentTypes(arg_types) + ", " +
                 "new String[] { " +
                 Utility.printArray(mg.getArgumentNames(), false, true) +
                 " }, \"" + method.getName() + "\", \"" +
                 _clazz.getClassName() + "\", il, _cp);\n");

    BCELFactory factory = new BCELFactory(mg, _out);
    factory.start();

    _out.println("    method.setMaxStack();");
    _out.println("    method.setMaxLocals();");
    _out.println("    _cg.addMethod(method.getMethod());");
    _out.println("    il.dispose();");
  }

  static String printFlags(int flags) {
    return printFlags(flags, false);
  }

  static String printFlags(int flags, boolean for_class) {
    if(flags == 0)
      return "0";

    StringBuffer buf = new StringBuffer();
    for(int i=0, pow=1; i <= Constants.MAX_ACC_FLAG; i++) {
      if((flags & pow) != 0) {
        if((pow == Constants.ACC_SYNCHRONIZED) && for_class)
          buf.append("ACC_SUPER | ");
        else
          buf.append("ACC_" + Constants.ACCESS_NAMES[i].toUpperCase() + " | ");
      }

      pow <<= 1;
    }

    String str = buf.toString();
    return str.substring(0, str.length() - 3);
  }

  static String printArgumentTypes(Type[] arg_types) {
    if(arg_types.length == 0)
      return "Type.NO_ARGS";

    StringBuffer args = new StringBuffer();

    for(int i=0; i < arg_types.length; i++) {
      args.append(printType(arg_types[i]));

      if(i < arg_types.length - 1)
        args.append(", ");
    }

    return "new Type[] { " + args.toString() + " }";
  }

  static String printType(Type type) {
    return printType(type.getSignature());
  }

  static String printType(String signature) {
    Type type = Type.getType(signature);
    byte t    = type.getType();

    if(t <= Constants.T_VOID) {
      return "Type." + Constants.TYPE_NAMES[t].toUpperCase();
    } else if(type.toString().equals("java.lang.String")) {
      return "Type.STRING";
    } else if(type.toString().equals("java.lang.Object")) {
      return "Type.OBJECT";
    } else if(type.toString().equals("java.lang.StringBuffer")) {
      return "Type.STRINGBUFFER";
    } else if(type instanceof ArrayType) {
      ArrayType at = (ArrayType)type;

      return "new ArrayType(" + printType(at.getBasicType()) +
        ", " + at.getDimensions() + ")";
    } else {
      return "new ObjectType(\"" + Utility.signatureToString(signature, false) +
        "\")";
    }
  }

  /** Default _main method
  /* <p>
   */
  public static void _main(String[] argv) throws Exception {
    JavaClass java_class;
    String    name = argv[0];

    if((java_class = Repository.lookupClass(name)) == null)
      java_class = new ClassParser(name).parse(); // May throw IOException

    BCELifier bcelifier = new BCELifier(java_class, System.out);
    bcelifier.start();
  }
}
