/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 2001, 2002,2004 The Apache Software Foundation.
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
 *  版权所有2001,2002,2004 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */

package com.sun.org.apache.xerces.internal.impl.xs;

import com.sun.org.apache.xerces.internal.impl.xs.util.XSObjectListImpl;
import com.sun.org.apache.xerces.internal.xs.XSConstants;
import com.sun.org.apache.xerces.internal.xs.XSNamespaceItem;
import com.sun.org.apache.xerces.internal.xs.XSObjectList;
import com.sun.org.apache.xerces.internal.xs.XSParticle;
import com.sun.org.apache.xerces.internal.xs.XSTerm;

/**
 * Store schema particle declaration.
 *
 * @xerces.internal
 *
 * <p>
 *  存储模式粒子声明。
 * 
 *  @ xerces.internal
 * 
 * 
 * @author Sandy Gao, IBM
 *
 * @version $Id: XSParticleDecl.java,v 1.7 2010-11-01 04:39:55 joehw Exp $
 */
public class XSParticleDecl implements XSParticle {

    // types of particles
    public static final short PARTICLE_EMPTY        = 0;
    public static final short PARTICLE_ELEMENT      = 1;
    public static final short PARTICLE_WILDCARD     = 2;
    public static final short PARTICLE_MODELGROUP   = 3;
    public static final short PARTICLE_ZERO_OR_MORE = 4;
    public static final short PARTICLE_ZERO_OR_ONE  = 5;
    public static final short PARTICLE_ONE_OR_MORE  = 6;

    // type of the particle
    public short fType = PARTICLE_EMPTY;

    // term of the particle
    // for PARTICLE_ELEMENT : the element decl
    // for PARTICLE_WILDCARD: the wildcard decl
    // for PARTICLE_MODELGROUP: the model group
    public XSTerm fValue = null;

    // minimum occurrence of this particle
    public int fMinOccurs = 1;
    // maximum occurrence of this particle
    public int fMaxOccurs = 1;
    // optional annotation
    public XSObjectList fAnnotations = null;

    // clone this decl
    public XSParticleDecl makeClone() {
        XSParticleDecl particle = new XSParticleDecl();
        particle.fType = fType;
        particle.fMinOccurs = fMinOccurs;
        particle.fMaxOccurs = fMaxOccurs;
        particle.fDescription = fDescription;
        particle.fValue = fValue;
        particle.fAnnotations = fAnnotations;
        return particle;
    }

    /**
     * 3.9.6 Schema Component Constraint: Particle Emptiable
     * whether this particle is emptible
     * <p>
     *  3.9.6模式组件约束：粒子显示此粒子是否可空
     * 
     */
    public boolean emptiable() {
        return minEffectiveTotalRange() == 0;
    }

    // whether this particle contains nothing
    public boolean isEmpty() {
        if (fType == PARTICLE_EMPTY)
             return true;
        if (fType == PARTICLE_ELEMENT || fType == PARTICLE_WILDCARD)
            return false;

        return ((XSModelGroupImpl)fValue).isEmpty();
    }

    /**
     * 3.8.6 Effective Total Range (all and sequence) and
     *       Effective Total Range (choice)
     * The following methods are used to return min/max range for a particle.
     * They are not exactly the same as it's described in the spec, but all the
     * values from the spec are retrievable by these methods.
     * <p>
     *  3.8.6有效总范围(全部和序列)和有效总范围(选择)以下方法用于返回粒子的最小/最大范围。它们与规范中描述的不完全相同,但规范中的所有值都可以通过这些方法检索。
     * 
     */
    public int minEffectiveTotalRange() {
        if (fType == XSParticleDecl.PARTICLE_EMPTY) {
            return 0;
        }
        if (fType == PARTICLE_MODELGROUP) {
            return ((XSModelGroupImpl)fValue).minEffectiveTotalRange() * fMinOccurs;
        }
        return fMinOccurs;
    }

    public int maxEffectiveTotalRange() {
        if (fType == XSParticleDecl.PARTICLE_EMPTY) {
            return 0;
        }
        if (fType == PARTICLE_MODELGROUP) {
            int max = ((XSModelGroupImpl)fValue).maxEffectiveTotalRange();
            if (max == SchemaSymbols.OCCURRENCE_UNBOUNDED)
                return SchemaSymbols.OCCURRENCE_UNBOUNDED;
            if (max != 0 && fMaxOccurs == SchemaSymbols.OCCURRENCE_UNBOUNDED)
                return SchemaSymbols.OCCURRENCE_UNBOUNDED;
            return max * fMaxOccurs;
        }
        return fMaxOccurs;
    }

    /**
     * get the string description of this particle
     * <p>
     *  获取此粒子的字符串描述
     * 
     */
    private String fDescription = null;
    public String toString() {
        if (fDescription == null) {
            StringBuffer buffer = new StringBuffer();
            appendParticle(buffer);
            if (!(fMinOccurs == 0 && fMaxOccurs == 0 ||
                  fMinOccurs == 1 && fMaxOccurs == 1)) {
                buffer.append('{').append(fMinOccurs);
                if (fMaxOccurs == SchemaSymbols.OCCURRENCE_UNBOUNDED)
                    buffer.append("-UNBOUNDED");
                else if (fMinOccurs != fMaxOccurs)
                    buffer.append('-').append(fMaxOccurs);
                buffer.append('}');
            }
            fDescription = buffer.toString();
        }
        return fDescription;
    }

    /**
     * append the string description of this particle to the string buffer
     * this is for error message.
     * <p>
     *  将此粒子的字符串描述附加到字符串缓冲区,这是为错误消息。
     * 
     */
    void appendParticle(StringBuffer buffer) {
        switch (fType) {
        case PARTICLE_EMPTY:
            buffer.append("EMPTY");
            break;
        case PARTICLE_ELEMENT:
            buffer.append(fValue.toString());
            break;
        case PARTICLE_WILDCARD:
            buffer.append('(');
            buffer.append(fValue.toString());
            buffer.append(')');
            break;
        case PARTICLE_MODELGROUP:
            buffer.append(fValue.toString());
            break;
        }
    }

    public void reset(){
        fType = PARTICLE_EMPTY;
        fValue = null;
        fMinOccurs = 1;
        fMaxOccurs = 1;
        fDescription = null;
        fAnnotations = null;
    }

    /**
     * Get the type of the object, i.e ELEMENT_DECLARATION.
     * <p>
     *  获取对象的类型,即ELEMENT_DECLARATION。
     * 
     */
    public short getType() {
        return XSConstants.PARTICLE;
    }

    /**
     * The <code>name</code> of this <code>XSObject</code> depending on the
     * <code>XSObject</code> type.
     * <p>
     *  取决于<code> XSObject </code>类型的<code> XSObject </code>的<code> name </code>
     * 
     */
    public String getName() {
        return null;
    }

    /**
     * The namespace URI of this node, or <code>null</code> if it is
     * unspecified.  defines how a namespace URI is attached to schema
     * components.
     * <p>
     * 此节点的名称空间URI,或<code> null </code>(如果未指定)。定义命名空间URI如何附加到模式组件。
     * 
     */
    public String getNamespace() {
        return null;
    }

    /**
     * {min occurs} determines the minimum number of terms that can occur.
     * <p>
     *  {min occur}确定可能发生的最小数量的术语。
     * 
     */
    public int getMinOccurs() {
        return fMinOccurs;
    }

    /**
     * {max occurs} whether the maxOccurs value is unbounded.
     * <p>
     *  {max occur} maxOccurs值是否无界。
     * 
     */
    public boolean getMaxOccursUnbounded() {
        return fMaxOccurs == SchemaSymbols.OCCURRENCE_UNBOUNDED;
    }

    /**
     * {max occurs} determines the maximum number of terms that can occur.
     * <p>
     *  {max occur}决定可能发生的字词数量上限。
     * 
     */
    public int getMaxOccurs() {
        return fMaxOccurs;
    }

    /**
     * {term} One of a model group, a wildcard, or an element declaration.
     * <p>
     *  {term}模型组,通配符或元素声明之一。
     * 
     */
    public XSTerm getTerm() {
        return fValue;
    }

        /**
        /* <p>
        /* 
         * @see org.apache.xerces.xs.XSObject#getNamespaceItem()
         */
        public XSNamespaceItem getNamespaceItem() {
                return null;
        }

    /**
     * Optional. Annotations.
     * <p>
     *  可选的。注释。
     */
    public XSObjectList getAnnotations() {
        return (fAnnotations != null) ? fAnnotations : XSObjectListImpl.EMPTY_LIST;
    }

} // class XSParticleDecl
