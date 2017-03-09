/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.sun.org.apache.bcel.internal.generic;

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
 */

/**
 * SWITCH - Branch depending on int value, generates either LOOKUPSWITCH or
 * TABLESWITCH instruction, depending on whether the match values (int[]) can be
 * sorted with no gaps between the numbers.
 *
 * <p>
 * 在任何情况下,APACHE软件基金会或其捐赠者均不对任何直接,间接,偶发,特殊,惩罚性或后果性损害(包括但不限于替代商品或服务的采购,使用,数据丢失或利润或业务中断),无论是由于任何责任推理原因,无论是
 * 在合同,严格责任或侵权(包括疏忽或其他方式)中,以任何方式使用本软件,即使已被告知此类软件的可能性损伤。
 *  本软件按"原样"提供,任何明示或默示的保证,包括但不限于适销性和特定用途适用性的默示保证。
 *  ================================================== ==================。
 * 
 *  该软件包括许多个人代表Apache软件基金会所做的自愿捐款。有关Apache Software Foundation的更多信息,请参阅<http://www.apache.org/>。
 * 
 * 
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public final class SWITCH implements CompoundInstruction {
  private int[]               match;
  private InstructionHandle[] targets;
  private Select              instruction;
  private int                 match_length;

  /**
   * Template for switch() constructs. If the match array can be
   * sorted in ascending order with gaps no larger than max_gap
   * between the numbers, a TABLESWITCH instruction is generated, and
   * a LOOKUPSWITCH otherwise. The former may be more efficient, but
   * needs more space.
   *
   * Note, that the key array always will be sorted, though we leave
   * the original arrays unaltered.
   *
   * <p>
   *  SWITCH  - 根据int值分支,生成LOOKUPSWITCH或TABLESWITCH指令,这取决于匹配值(int [])是否可以排序,数字之间没有间隙。
   * 
   * 
   * @param match array of match values (case 2: ... case 7: ..., etc.)
   * @param targets the instructions to be branched to for each case
   * @param target the default target
   * @param max_gap maximum gap that may between case branches
   */
  public SWITCH(int[] match, InstructionHandle[] targets,
                InstructionHandle target, int max_gap) {
    this.match   = (int[])match.clone();
    this.targets = (InstructionHandle[])targets.clone();

    if((match_length = match.length) < 2) // (almost) empty switch, or just default
      instruction = new TABLESWITCH(match, targets, target);
    else {
      sort(0, match_length - 1);

      if(matchIsOrdered(max_gap)) {
        fillup(max_gap, target);

        instruction = new TABLESWITCH(this.match, this.targets, target);
      }
      else
        instruction = new LOOKUPSWITCH(this.match, this.targets, target);
    }
  }

  public SWITCH(int[] match, InstructionHandle[] targets,
                InstructionHandle target) {
    this(match, targets, target, 1);
  }

  private final void fillup(int max_gap, InstructionHandle target) {
    int                 max_size = match_length + match_length * max_gap;
    int[]               m_vec    = new int[max_size];
    InstructionHandle[] t_vec    = new InstructionHandle[max_size];
    int                 count    = 1;

    m_vec[0] = match[0];
    t_vec[0] = targets[0];

    for(int i=1; i < match_length; i++) {
      int prev = match[i-1];
      int gap  = match[i] - prev;

      for(int j=1; j < gap; j++) {
        m_vec[count] = prev + j;
        t_vec[count] = target;
        count++;
      }

      m_vec[count] = match[i];
      t_vec[count] = targets[i];
      count++;
    }

    match   = new int[count];
    targets = new InstructionHandle[count];

    System.arraycopy(m_vec, 0, match, 0, count);
    System.arraycopy(t_vec, 0, targets, 0, count);
  }

  /**
   * Sort match and targets array with QuickSort.
   * <p>
   * switch()构造的模板。如果匹配数组可以以数字之间的间隙不大于max_gap的升序排序,则生成TABLESWITCH指令,否则产生LOOKUPSWITCH。前者可能更有效率,但需要更多的空间。
   * 
   *  注意,键数组总是被排序,虽然我们保留原始数组不变。
   * 
   */
  private final void sort(int l, int r) {
    int i = l, j = r;
    int h, m = match[(l + r) / 2];
    InstructionHandle h2;

    do {
      while(match[i] < m) i++;
      while(m < match[j]) j--;

      if(i <= j) {
        h=match[i]; match[i]=match[j]; match[j]=h; // Swap elements
        h2=targets[i]; targets[i]=targets[j]; targets[j]=h2; // Swap instructions, too
        i++; j--;
      }
    } while(i <= j);

    if(l < j) sort(l, j);
    if(i < r) sort(i, r);
  }

  /**
  /* <p>
  /* 
   * @return match is sorted in ascending order with no gap bigger than max_gap?
   */
  private final boolean matchIsOrdered(int max_gap) {
    for(int i=1; i < match_length; i++)
      if(match[i] - match[i-1] > max_gap)
        return false;

    return true;
  }

  public final InstructionList getInstructionList() {
    return new InstructionList(instruction);
  }

  public final Instruction getInstruction() {
    return instruction;
  }
}
