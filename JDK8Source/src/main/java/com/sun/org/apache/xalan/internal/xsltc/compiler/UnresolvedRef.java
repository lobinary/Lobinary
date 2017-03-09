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
 */
/*
 * $Id: UnresolvedRef.java,v 1.5 2005/09/28 13:48:17 pvedula Exp $
 * <p>
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */

package com.sun.org.apache.xalan.internal.xsltc.compiler;

import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ClassGenerator;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ErrorMsg;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.MethodGenerator;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.TypeCheckError;

/**
/* <p>
/*  $ Id：UnresolvedRef.java,v 1.5 2005/09/28 13:48:17 pvedula Exp $
/* 
/* 
 * @author Morten Jorgensen
 */
final class UnresolvedRef extends VariableRefBase {

    private QName _variableName = null;
    private VariableRefBase _ref = null;

    public UnresolvedRef(QName name) {
        super();
        _variableName = name;
    }

    public QName getName() {
        return(_variableName);
    }

    private ErrorMsg reportError() {
        ErrorMsg err = new ErrorMsg(ErrorMsg.VARIABLE_UNDEF_ERR,
                                    _variableName, this);
        getParser().reportError(Constants.ERROR, err);
        return(err);
    }

    private VariableRefBase resolve(Parser parser, SymbolTable stable) {
        // At this point the AST is already built and we should be able to
        // find any declared global variable or parameter
        VariableBase ref = parser.lookupVariable(_variableName);
        if (ref == null) {
            ref = (VariableBase)stable.lookupName(_variableName);
        }
        if (ref == null) {
            reportError();
            return null;
        }

        // If in a top-level element, create dependency to the referenced var
        _variable = ref;
        addParentDependency();

        if (ref instanceof Variable) {
            return new VariableRef((Variable) ref);
        }
        else if (ref instanceof Param) {
            return new ParameterRef((Param)ref);
        }
        return null;
    }

    public Type typeCheck(SymbolTable stable) throws TypeCheckError {
        if (_ref != null) {
            final String name = _variableName.toString();
            ErrorMsg err = new ErrorMsg(ErrorMsg.CIRCULAR_VARIABLE_ERR,
                                        name, this);
        }
        if ((_ref = resolve(getParser(), stable)) != null) {
            return (_type = _ref.typeCheck(stable));
        }
        throw new TypeCheckError(reportError());
    }

    public void translate(ClassGenerator classGen, MethodGenerator methodGen) {
        if (_ref != null)
            _ref.translate(classGen, methodGen);
        else
            reportError();
    }

    public String toString() {
        return "unresolved-ref()";
    }

}
