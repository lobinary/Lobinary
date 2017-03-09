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
 * $Id: FunctionAvailableCall.java,v 1.2.4.1 2005/09/01 15:30:25 pvedula Exp $
 * <p>
 *  $ Id：FunctionAvailableCall.java,v 1.2.4.1 2005/09/01 15:30:25 pvedula Exp $
 * 
 */

package com.sun.org.apache.xalan.internal.xsltc.compiler;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Vector;

import com.sun.org.apache.bcel.internal.generic.ConstantPoolGen;
import com.sun.org.apache.bcel.internal.generic.PUSH;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ClassGenerator;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ErrorMsg;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.MethodGenerator;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.TypeCheckError;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Util;
import com.sun.org.apache.xalan.internal.utils.ObjectFactory;

/**
/* <p>
/* 
 * @author G. Todd Miller
 * @author Santiago Pericas-Geertsen
 */
final class FunctionAvailableCall extends FunctionCall {

    private Expression _arg;
    private String     _nameOfFunct = null;
    private String     _namespaceOfFunct = null;
    private boolean    _isFunctionAvailable = false;

    /**
     * Constructs a FunctionAvailableCall FunctionCall. Takes the
     * function name qname, for example, 'function-available', and
     * a list of arguments where the arguments must be instances of
     * LiteralExpression.
     * <p>
     *  构造FunctionAvailableCall函数调用。使用函数名称qname,例如'function-available',以及参数列表,其中参数必须是LiteralExpression的实例。
     * 
     */
    public FunctionAvailableCall(QName fname, Vector arguments) {
        super(fname, arguments);
        _arg = (Expression)arguments.elementAt(0);
        _type = null;

        if (_arg instanceof LiteralExpr) {
            LiteralExpr arg = (LiteralExpr) _arg;
            _namespaceOfFunct = arg.getNamespace();
            _nameOfFunct = arg.getValue();

            if (!isInternalNamespace()) {
              _isFunctionAvailable = hasMethods();
            }
        }
    }

    /**
     * Argument of function-available call must be literal, typecheck
     * returns the type of function-available to be boolean.
     * <p>
     *  函数可用调用的参数必须是字面量,typecheck返回函数的类型 - 可用的是boolean。
     * 
     */
    public Type typeCheck(SymbolTable stable) throws TypeCheckError {
        if (_type != null) {
           return _type;
        }
        if (_arg instanceof LiteralExpr) {
            return _type = Type.Boolean;
        }
        ErrorMsg err = new ErrorMsg(ErrorMsg.NEED_LITERAL_ERR,
                        "function-available", this);
        throw new TypeCheckError(err);
    }

    /**
     * Returns an object representing the compile-time evaluation
     * of an expression. We are only using this for function-available
     * and element-available at this time.
     * <p>
     *  返回表示表达式的编译时评估的对象。我们现在只使用这个功能可用和元素可用。
     * 
     */
    public Object evaluateAtCompileTime() {
        return getResult() ? Boolean.TRUE : Boolean.FALSE;
    }

    /**
     * for external java functions only: reports on whether or not
     * the specified method is found in the specifed class.
     * <p>
     *  仅适用于外部Java函数：报告指定的方法是否在指定类中找到。
     * 
     */
    private boolean hasMethods() {
        LiteralExpr arg = (LiteralExpr)_arg;

        // Get the class name from the namespace uri
        String className = getClassNameFromUri(_namespaceOfFunct);

        // Get the method name from the argument to function-available
        String methodName = null;
        int colonIndex = _nameOfFunct.indexOf(":");
        if (colonIndex > 0) {
          String functionName = _nameOfFunct.substring(colonIndex+1);
          int lastDotIndex = functionName.lastIndexOf('.');
          if (lastDotIndex > 0) {
            methodName = functionName.substring(lastDotIndex+1);
            if (className != null && !className.equals(""))
              className = className + "." + functionName.substring(0, lastDotIndex);
            else
              className = functionName.substring(0, lastDotIndex);
          }
          else
            methodName = functionName;
        }
        else
          methodName = _nameOfFunct;

        if (className == null || methodName == null) {
            return false;
        }

        // Replace the '-' characters in the method name
        if (methodName.indexOf('-') > 0)
          methodName = replaceDash(methodName);

        try {
            final Class clazz = ObjectFactory.findProviderClass(className, true);

            if (clazz == null) {
                return false;
            }

            final Method[] methods = clazz.getMethods();

            for (int i = 0; i < methods.length; i++) {
                final int mods = methods[i].getModifiers();

                if (Modifier.isPublic(mods) && Modifier.isStatic(mods)
                        && methods[i].getName().equals(methodName))
                {
                    return true;
                }
            }
        }
        catch (ClassNotFoundException e) {
          return false;
        }
        return false;
    }

    /**
     * Reports on whether the function specified in the argument to
     * xslt function 'function-available' was found.
     * <p>
     *  关于是否找到在xslt函数的参数中指定的函数"function-available"的报告。
     * 
     */
    public boolean getResult() {
        if (_nameOfFunct == null) {
            return false;
        }

        if (isInternalNamespace()) {
            final Parser parser = getParser();
            _isFunctionAvailable =
                parser.functionSupported(Util.getLocalName(_nameOfFunct));
        }
        return _isFunctionAvailable;
    }

    /**
     * Return true if the namespace uri is null or it is the XSLTC translet uri.
     * <p>
     * 如果命名空间uri为null或者是XSLTC translet uri,则返回true。
     * 
     */
    private boolean isInternalNamespace() {
        return (_namespaceOfFunct == null ||
            _namespaceOfFunct.equals(EMPTYSTRING) ||
            _namespaceOfFunct.equals(TRANSLET_URI));
    }

    /**
     * Calls to 'function-available' are resolved at compile time since
     * the namespaces declared in the stylsheet are not available at run
     * time. Consequently, arguments to this function must be literals.
     * <p>
     *  对'function-available'的调用在编译时解析,因为在运行时,在样式表中声明的命名空间不可用。因此,此函数的参数必须是文字。
     */
    public void translate(ClassGenerator classGen, MethodGenerator methodGen) {
        final ConstantPoolGen cpg = classGen.getConstantPool();
        methodGen.getInstructionList().append(new PUSH(cpg, getResult()));
    }

}
