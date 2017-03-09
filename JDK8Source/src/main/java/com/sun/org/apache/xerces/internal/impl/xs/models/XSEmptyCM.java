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
 *      http://www.apache.org/licenses/LICENSE-2.0
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

package com.sun.org.apache.xerces.internal.impl.xs.models;

import com.sun.org.apache.xerces.internal.xni.QName;
import com.sun.org.apache.xerces.internal.impl.xs.SubstitutionGroupHandler;
import com.sun.org.apache.xerces.internal.impl.xs.XMLSchemaException;

import java.util.Vector;
import java.util.ArrayList;

/**
 * XSEmptyCM is a derivative of the abstract content model base class that
 * handles a content model with no chilren (elements).
 *
 * This model validated on the way in.
 *
 * @xerces.internal
 *
 * <p>
 *  XSEmptyCM是抽象内容模型基类的派生类,用于处理没有chilren(元素)的内容模型。
 * 
 *  这个模型在进行中验证。
 * 
 *  @ xerces.internal
 * 
 * 
 * @author Elena Litani, Lisa Martin
 * @author IBM
 * @version $Id: XSEmptyCM.java,v 1.7 2009/07/28 15:18:11 spericas Exp $
 */
public class XSEmptyCM  implements XSCMValidator {

    //
    // Constants
    //

    // start the content model: did not see any children
    private static final short STATE_START = 0;

    private static final Vector EMPTY = new Vector(0);

    //
    // Data
    //

    //
    // XSCMValidator methods
    //

    /**
     * This methods to be called on entering a first element whose type
     * has this content model. It will return the initial state of the content model
     *
     * <p>
     *  在输入类型具有此内容模型的第一个元素时调用此方法。它将返回内容模型的初始状态
     * 
     * 
     * @return Start state of the content model
     */
    public int[] startContentModel(){
        return (new int[] {STATE_START});
    }


    /**
     * The method corresponds to one transaction in the content model.
     *
     * <p>
     *  该方法对应于内容模型中的一个事务。
     * 
     * 
     * @param elementName the qualified name of the element
     * @param currentState Current state
     * @param subGroupHandler the substitution group handler
     * @return element index corresponding to the element from the Schema grammar
     */
    public Object oneTransition (QName elementName, int[] currentState, SubstitutionGroupHandler subGroupHandler){

        // error state
        if (currentState[0] < 0) {
            currentState[0] = XSCMValidator.SUBSEQUENT_ERROR;
            return null;
        }

        currentState[0] = XSCMValidator.FIRST_ERROR;
        return null;
    }


    /**
     * The method indicates the end of list of children
     *
     * <p>
     *  该方法指示子节点列表的结尾
     * 
     * 
     * @param currentState Current state of the content model
     * @return true if the last state was a valid final state
     */
    public boolean endContentModel (int[] currentState){
        boolean isFinal =  false;
        int state = currentState[0];

        // restore content model state:

        // error
        if (state < 0) {
            return false;
        }


        return true;
    }

    /**
     * check whether this content violates UPA constraint.
     *
     * <p>
     *  检查此内容是否违反UPA约束。
     * 
     * 
     * @param subGroupHandler the substitution group handler
     * @return true if this content model contains other or list wildcard
     */
    public boolean checkUniqueParticleAttribution(SubstitutionGroupHandler subGroupHandler) throws XMLSchemaException {
        return false;
    }

    /**
     * Check which elements are valid to appear at this point. This method also
     * works if the state is in error, in which case it returns what should
     * have been seen.
     *
     * <p>
     *  检查哪些元素在此时显示有效。如果状态是错误的,这种方法也工作,在这种情况下,它返回应该已经看到的。
     * 
     * @param state  the current state
     * @return       a Vector whose entries are instances of
     *               either XSWildcardDecl or XSElementDecl.
     */
    public Vector whatCanGoHere(int[] state) {
        return EMPTY;
    }

    public ArrayList checkMinMaxBounds() {
        return null;
    }

} // class XSEmptyCM
