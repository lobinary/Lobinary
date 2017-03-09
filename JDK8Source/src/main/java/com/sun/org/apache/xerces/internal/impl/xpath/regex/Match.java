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

package com.sun.org.apache.xerces.internal.impl.xpath.regex;

import java.text.CharacterIterator;

/**
 * An instance of this class has ranges captured in matching.
 *
 * @xerces.internal
 *
 * <p>
 *  此类的实例具有在匹配中捕获的范围。
 * 
 *  @ xerces.internal
 * 
 * 
 * @see RegularExpression#matches(char[], int, int, Match)
 * @see RegularExpression#matches(char[], Match)
 * @see RegularExpression#matches(java.text.CharacterIterator, Match)
 * @see RegularExpression#matches(java.lang.String, int, int, Match)
 * @see RegularExpression#matches(java.lang.String, Match)
 * @author TAMURA Kent &lt;kent@trl.ibm.co.jp&gt;
 */
public class Match implements Cloneable {
    int[] beginpos = null;
    int[] endpos = null;
    int nofgroups = 0;

    CharacterIterator ciSource = null;
    String strSource = null;
    char[] charSource = null;

    /**
     * Creates an instance.
     * <p>
     *  创建实例。
     * 
     */
    public Match() {
    }

    /**
     *
     * <p>
     */
    public synchronized Object clone() {
        Match ma = new Match();
        if (this.nofgroups > 0) {
            ma.setNumberOfGroups(this.nofgroups);
            if (this.ciSource != null)  ma.setSource(this.ciSource);
            if (this.strSource != null)  ma.setSource(this.strSource);
            for (int i = 0;  i < this.nofgroups;  i ++) {
                ma.setBeginning(i, this.getBeginning(i));
                ma.setEnd(i, this.getEnd(i));
            }
        }
        return ma;
    }

    /**
     *
     * <p>
     */
    protected void setNumberOfGroups(int n) {
        int oldn = this.nofgroups;
        this.nofgroups = n;
        if (oldn <= 0
            || oldn < n || n*2 < oldn) {
            this.beginpos = new int[n];
            this.endpos = new int[n];
        }
        for (int i = 0;  i < n;  i ++) {
            this.beginpos[i] = -1;
            this.endpos[i] = -1;
        }
    }

    /**
     *
     * <p>
     */
    protected void setSource(CharacterIterator ci) {
        this.ciSource = ci;
        this.strSource = null;
        this.charSource = null;
    }
    /**
     *
     * <p>
     */
    protected void setSource(String str) {
        this.ciSource = null;
        this.strSource = str;
        this.charSource = null;
    }
    /**
     *
     * <p>
     */
    protected void setSource(char[] chars) {
        this.ciSource = null;
        this.strSource = null;
        this.charSource = chars;
    }

    /**
     *
     * <p>
     */
    protected void setBeginning(int index, int v) {
        this.beginpos[index] = v;
    }

    /**
     *
     * <p>
     */
    protected void setEnd(int index, int v) {
        this.endpos[index] = v;
    }

    /**
     * Return the number of regular expression groups.
     * This method returns 1 when the regular expression has no capturing-parenthesis.
     * <p>
     *  返回正则表达式组的数量。当正则表达式没有捕获圆括号时,此方法返回1。
     * 
     */
    public int getNumberOfGroups() {
        if (this.nofgroups <= 0)
            throw new IllegalStateException("A result is not set.");
        return this.nofgroups;
    }

    /**
     * Return a start position in the target text matched to specified regular expression group.
     *
     * <p>
     *  返回与指定正则表达式组匹配的目标文本中的起始位置。
     * 
     * 
     * @param index Less than <code>getNumberOfGroups()</code>.
     */
    public int getBeginning(int index) {
        if (this.beginpos == null)
            throw new IllegalStateException("A result is not set.");
        if (index < 0 || this.nofgroups <= index)
            throw new IllegalArgumentException("The parameter must be less than "
                                               +this.nofgroups+": "+index);
        return this.beginpos[index];
    }

    /**
     * Return an end position in the target text matched to specified regular expression group.
     *
     * <p>
     *  返回与指定正则表达式组匹配的目标文本中的结束位置。
     * 
     * 
     * @param index Less than <code>getNumberOfGroups()</code>.
     */
    public int getEnd(int index) {
        if (this.endpos == null)
            throw new IllegalStateException("A result is not set.");
        if (index < 0 || this.nofgroups <= index)
            throw new IllegalArgumentException("The parameter must be less than "
                                               +this.nofgroups+": "+index);
        return this.endpos[index];
    }

    /**
     * Return an substring of the target text matched to specified regular expression group.
     *
     * <p>
     *  返回与指定正则表达式组匹配的目标文本的子字符串。
     * 
     * @param index Less than <code>getNumberOfGroups()</code>.
     */
    public String getCapturedText(int index) {
        if (this.beginpos == null)
            throw new IllegalStateException("match() has never been called.");
        if (index < 0 || this.nofgroups <= index)
            throw new IllegalArgumentException("The parameter must be less than "
                                               +this.nofgroups+": "+index);
        String ret;
        int begin = this.beginpos[index], end = this.endpos[index];
        if (begin < 0 || end < 0)  return null;
        if (this.ciSource != null) {
            ret = REUtil.substring(this.ciSource, begin, end);
        } else if (this.strSource != null) {
            ret = this.strSource.substring(begin, end);
        } else {
            ret = new String(this.charSource, begin, end-begin);
        }
        return ret;
    }
}
