/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 2004,2005 The Apache Software Foundation.
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
 *  版权所有2004,2005 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */

package com.sun.org.apache.xerces.internal.util;

/**
 * <p>A structure that represents an error code, characterized by
 * a domain and a message key.</p>
 *
 * <p>
 *  <p>代表错误代码的结构,以域和消息键为特征。</p>
 * 
 * 
 * @author Naela Nissar, IBM
 *
 */
final class XMLErrorCode {

    //
    // Data
    //

    /** error domain **/
    private String fDomain;

    /** message key **/
    private String fKey;

    /**
     * <p>Constructs an XMLErrorCode with the given domain and key.</p>
     *
     * <p>
     *  <p>使用给定的域和键构造一个XMLErrorCode。</p>
     * 
     * 
     * @param domain The error domain.
     * @param key The key of the error message.
     */
    public XMLErrorCode(String domain, String key) {
        fDomain = domain;
        fKey = key;
    }

    /**
     * <p>Convenience method to set the values of an XMLErrorCode.</p>
     *
     * <p>
     *  <p>方便设置XMLErrorCode的值的方法。</p>
     * 
     * 
     * @param domain The error domain.
     * @param key The key of the error message.
     */
    public void setValues(String domain, String key) {
        fDomain = domain;
        fKey = key;
    }

    /**
     * <p>Indicates whether some other object is equal to this XMLErrorCode.</p>
     *
     * <p>
     *  <p>表示某个其他对象是否等于此XMLErrorCode。</p>
     * 
     * 
     * @param obj the object with which to compare.
     */
    public boolean equals(Object obj) {
        if (!(obj instanceof XMLErrorCode))
            return false;
        XMLErrorCode err = (XMLErrorCode) obj;
        return (fDomain.equals(err.fDomain) && fKey.equals(err.fKey));
    }

    /**
     * <p>Returns a hash code value for this XMLErrorCode.</p>
     *
     * <p>
     *  <p>返回此XMLErrorCode的哈希码值。</p>
     * 
     * @return a hash code value for this XMLErrorCode.
     */
    public int hashCode() {
        return fDomain.hashCode() + fKey.hashCode();
    }
}
