/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2013, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

package java.awt.dnd;

import java.lang.annotation.Native;

/**
 * This class contains constant values representing
 * the type of action(s) to be performed by a Drag and Drop operation.
 * <p>
 *  此类包含表示拖放操作要执行的操作类型的常量值。
 * 
 * 
 * @since 1.2
 */
public final class DnDConstants {

    private DnDConstants() {} // define null private constructor.

    /**
     * An <code>int</code> representing no action.
     * <p>
     *  代表无操作的<code> int </code>。
     * 
     */
    @Native public static final int ACTION_NONE         = 0x0;

    /**
     * An <code>int</code> representing a &quot;copy&quot; action.
     * <p>
     *  代表"副本"的<code> int </code>行动。
     * 
     */
    @Native public static final int ACTION_COPY         = 0x1;

    /**
     * An <code>int</code> representing a &quot;move&quot; action.
     * <p>
     *  代表"移动"的<code> int </code>行动。
     * 
     */
    @Native public static final int ACTION_MOVE         = 0x2;

    /**
     * An <code>int</code> representing a &quot;copy&quot; or
     * &quot;move&quot; action.
     * <p>
     *  代表"副本"的<code> int </code>或"移动"行动。
     * 
     */
    @Native public static final int ACTION_COPY_OR_MOVE = ACTION_COPY | ACTION_MOVE;

    /**
     * An <code>int</code> representing a &quot;link&quot; action.
     *
     * The link verb is found in many, if not all native DnD platforms, and the
     * actual interpretation of LINK semantics is both platform
     * and application dependent. Broadly speaking, the
     * semantic is "do not copy, or move the operand, but create a reference
     * to it". Defining the meaning of "reference" is where ambiguity is
     * introduced.
     *
     * The verb is provided for completeness, but its use is not recommended
     * for DnD operations between logically distinct applications where
     * misinterpretation of the operations semantics could lead to confusing
     * results for the user.
     * <p>
     *  代表"链接"的<code> int </code>行动。
     * 
     *  链接动词在许多,如果不是所有本地DnD平台中找到,并且LINK语义的实际解释是平台和应用程序相关。一般来说,语义是"不复制或移动操作数,但创建对它的引用"。定义"引用"的含义是引入歧义的地方。
     * 
     *  该动词是为了完整性而提供的,但是它的使用不推荐用于逻辑上不同的应用程序之间的DnD操作,其中操作语义的错误解释可能导致用户的混淆结果。
     */

    @Native public static final int ACTION_LINK         = 0x40000000;

    /**
     * An <code>int</code> representing a &quot;reference&quot;
     * action (synonym for ACTION_LINK).
     * <p>
     * 
     */
    @Native public static final int ACTION_REFERENCE    = ACTION_LINK;

}
