/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 2001,2002,2004 The Apache Software Foundation.
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

package com.sun.org.apache.xerces.internal.impl.xs.identity;


/**
 * Interface for a field activator. The field activator is responsible
 * for activating fields within a specific scope; the caller merely
 * requests the fields to be activated.
 *
 * @xerces.internal
 *
 * <p>
 *  场激活器的接口。场激活器负责在特定范围内激活场;呼叫者仅请求要激活的字段。
 * 
 *  @ xerces.internal
 * 
 * 
 * @author Andy Clark, IBM
 *
 */
public interface FieldActivator {

    //
    // FieldActivator methods
    //

    /**
     * Start the value scope for the specified identity constraint. This
     * method is called when the selector matches in order to initialize
     * the value store.
     *
     * <p>
     *  启动指定标识约束的值范围。当选择器匹配以便初始化值存储时调用此方法。
     * 
     * 
     * @param identityConstraint The identity constraint.
     * @param initialDepth  the depth at which the selector began matching
     */
    public void startValueScopeFor(IdentityConstraint identityConstraint,
            int initialDepth);

    /**
     * Request to activate the specified field. This method returns the
     * matcher for the field.
     * It's also important for the implementor to ensure that it marks whether a Field
     * is permitted to match a value--that is, to call the setMayMatch(Field, Boolean) method.
     *
     * <p>
     *  请求激活指定的字段。此方法返回字段的匹配器。对于实现者来说,确保它标记字段是否被允许匹配一个值也很重要,也就是调用setMayMatch(Field,Boolean)方法。
     * 
     * 
     * @param field The field to activate.
     * @param initialDepth the 0-indexed depth in the instance document at which the Selector began to match.
     */
    public XPathMatcher activateField(Field field, int initialDepth);

    /**
     * Sets whether the given field is permitted to match a value.
     * This should be used to catch instance documents that try
     * and match a field several times in the same scope.
     *
     * <p>
     *  设置是否允许给定字段与值匹配。这应该用于捕获在同一范围内尝试和匹配一个字段多次的实例文档。
     * 
     * 
     * @param field The field that may be permitted to be matched.
     * @param state Boolean indiciating whether the field may be matched.
     */
    public void setMayMatch(Field field, Boolean state);

    /**
     * Returns whether the given field is permitted to match a value.
     *
     * <p>
     * 返回是否允许给定字段与值匹配。
     * 
     * 
     * @param field The field that may be permitted to be matched.
     * @return Boolean indicating whether the field may be matched.
     */
    public Boolean mayMatch(Field field);

    /**
     * Ends the value scope for the specified identity constraint.
     *
     * <p>
     *  结束指定的标识约束的值作用域。
     * 
     * @param identityConstraint The identity constraint.
     * @param initialDepth  the 0-indexed depth where the Selector began to match.
     */
    public void endValueScopeFor(IdentityConstraint identityConstraint, int initialDepth);

} // interface FieldActivator
