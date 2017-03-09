/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 1999-2002,2004 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p>
 *  版权所有1999-2002,2004 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */

package com.sun.org.apache.xerces.internal.impl.validation;

import java.util.Vector;

/**
 * ValidationManager is a coordinator property for validators in the
 * pipeline. Each validator must know how to interact with
 * this property. Validators are not required to know what kind of
 * other validators present in the pipeline, but should understand
 * that there are others and that some coordination is required.
 *
 * @xerces.internal
 *
 * <p>
 *  ValidationManager是管道中验证器的协调器属性。每个验证器必须知道如何与此属性交互。验证器不需要知道在流水线中存在什么类型的其他验证器,但应该理解有其他验证器,并且需要一些协调。
 * 
 *  @ xerces.internal
 * 
 * 
 * @author Elena Litani, IBM
 */
public class ValidationManager {

    protected final Vector fVSs = new Vector();
    protected boolean fGrammarFound = false;

    // used by the DTD validator to tell other components that it has a
    // cached DTD in hand so there's no reason to
    // scan external subset or entity decls.
    protected boolean fCachedDTD = false;

    /**
     * Each validator should call this method to add its ValidationState into
     * the validation manager.
     * <p>
     *  每个验证器应调用此方法以将其ValidationState添加到验证管理器中。
     * 
     */
    public final void addValidationState(ValidationState vs) {
        fVSs.addElement(vs);
    }

    /**
     * Set the information required to validate entity values.
     * <p>
     *  设置验证实体值所需的信息。
     */
    public final void setEntityState(EntityState state) {
        for (int i = fVSs.size()-1; i >= 0; i--) {
            ((ValidationState)fVSs.elementAt(i)).setEntityState(state);
        }
    }

    public final void setGrammarFound(boolean grammar){
        fGrammarFound = grammar;
    }

    public final boolean isGrammarFound(){
        return fGrammarFound;
    }

    public final void setCachedDTD(boolean cachedDTD) {
        fCachedDTD = cachedDTD;
    } // setCachedDTD(boolean)

    public final boolean isCachedDTD() {
        return fCachedDTD;
    } // isCachedDTD():  boolean


    public final void reset (){
        fVSs.removeAllElements();
        fGrammarFound = false;
        fCachedDTD = false;
    }
}
