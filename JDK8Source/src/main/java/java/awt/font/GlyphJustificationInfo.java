/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 1999, Oracle and/or its affiliates. All rights reserved.
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

/*
 * (C) Copyright Taligent, Inc. 1996 - 1997, All Rights Reserved
 * (C) Copyright IBM Corp. 1996 - 1998, All Rights Reserved
 *
 * The original version of this source code and documentation is
 * copyrighted and owned by Taligent, Inc., a wholly-owned subsidiary
 * of IBM. These materials are provided under terms of a License
 * Agreement between Taligent and Sun. This technology is protected
 * by multiple US and International patents.
 *
 * This notice and attribution to Taligent may not be removed.
 * Taligent is a registered trademark of Taligent, Inc.
 * <p>
 *  (C)版权所有Taligent,Inc. 1996  -  1997,保留所有权利(C)版权所有IBM Corp. 1996  -  1998,保留所有权利
 * 
 *  此源代码和文档的原始版本由IBM的全资子公司Taligent,Inc.拥有版权和所有权。这些材料是根据Taligent和Sun之间的许可协议的条款提供的。该技术受多项美国和国际专利保护。
 * 
 *  此通知和归因于Taligent不得删除。 Taligent是Taligent,Inc.的注册商标。
 * 
 */

package java.awt.font;

/**
 * The <code>GlyphJustificationInfo</code> class represents information
 * about the justification properties of a glyph.  A glyph is the visual
 * representation of one or more characters.  Many different glyphs can
 * be used to represent a single character or combination of characters.
 * The four justification properties represented by
 * <code>GlyphJustificationInfo</code> are weight, priority, absorb and
 * limit.
 * <p>
 * Weight is the overall 'weight' of the glyph in the line.  Generally it is
 * proportional to the size of the font.  Glyphs with larger weight are
 * allocated a correspondingly larger amount of the change in space.
 * <p>
 * Priority determines the justification phase in which this glyph is used.
 * All glyphs of the same priority are examined before glyphs of the next
 * priority.  If all the change in space can be allocated to these glyphs
 * without exceeding their limits, then glyphs of the next priority are not
 * examined. There are four priorities, kashida, whitespace, interchar,
 * and none.  KASHIDA is the first priority examined. NONE is the last
 * priority examined.
 * <p>
 * Absorb determines whether a glyph absorbs all change in space.  Within a
 * given priority, some glyphs may absorb all the change in space.  If any of
 * these glyphs are present, no glyphs of later priority are examined.
 * <p>
 * Limit determines the maximum or minimum amount by which the glyph can
 * change. Left and right sides of the glyph can have different limits.
 * <p>
 * Each <code>GlyphJustificationInfo</code> represents two sets of
 * metrics, which are <i>growing</i> and <i>shrinking</i>.  Growing
 * metrics are used when the glyphs on a line are to be
 * spread apart to fit a larger width.  Shrinking metrics are used when
 * the glyphs are to be moved together to fit a smaller width.
 * <p>
 *  <code> GlyphJustificationInfo </code>类表示关于字形的对齐属性的信息。字形是一个或多个字符的视觉表示。许多不同的字形可以用于表示单个字符或字符的组合。
 * 由<code> GlyphJustificationInfo </code>表示的四个对齐属性是权重,优先级,吸收和限制。
 * <p>
 *  重量是行中字形的总体"重量"。通常它与字体的大小成比例。具有较大权重的字形被分配相应较大量的空间变化。
 * <p>
 * 优先级确定使用此字形的调整阶段。在下一个优先级的字形之前检查相同优先级的所有字形。如果空间中的所有变化可以被分配给这些字形而不超过它们的限制,则不检查下一优先级的字形。
 * 有四个优先级,kashida,whitespace,interchar和none。 KASHIDA是审查的第一优先。 NONE是所检查的最后一个优先级。
 * <p>
 *  Absorb确定字形是否吸收空间中的所有变化。在给定的优先级内,一些字形可以吸收空间中的所有变化。如果存在这些字形中的任一个,则不检查稍后优先级的字形。
 * <p>
 *  限制确定字形可以更改的最大或最小量。字形的左侧和右侧可以有不同的限制。
 * <p>
 *  每个<code> GlyphJustificationInfo </code>表示两组度量,其分别是</i>和<i>缩小</i>。当一行上的字形要分开以适应更大的宽度时,使用生长度量。
 * 缩小指标在字形要一起移动以适应较小宽度时使用。
 * 
 */

public final class GlyphJustificationInfo {

    /**
     * Constructs information about the justification properties of a
     * glyph.
     * <p>
     *  构造关于字形的对齐属性的信息。
     * 
     * 
     * @param weight the weight of this glyph when allocating space.  Must be non-negative.
     * @param growAbsorb if <code>true</code> this glyph absorbs
     * all extra space at this priority and lower priority levels when it
     * grows
     * @param growPriority the priority level of this glyph when it
     * grows
     * @param growLeftLimit the maximum amount by which the left side of this
     * glyph can grow.  Must be non-negative.
     * @param growRightLimit the maximum amount by which the right side of this
     * glyph can grow.  Must be non-negative.
     * @param shrinkAbsorb if <code>true</code>, this glyph absorbs all
     * remaining shrinkage at this and lower priority levels when it
     * shrinks
     * @param shrinkPriority the priority level of this glyph when
     * it shrinks
     * @param shrinkLeftLimit the maximum amount by which the left side of this
     * glyph can shrink.  Must be non-negative.
     * @param shrinkRightLimit the maximum amount by which the right side
     * of this glyph can shrink.  Must be non-negative.
     */
     public GlyphJustificationInfo(float weight,
                                  boolean growAbsorb,
                                  int growPriority,
                                  float growLeftLimit,
                                  float growRightLimit,
                                  boolean shrinkAbsorb,
                                  int shrinkPriority,
                                  float shrinkLeftLimit,
                                  float shrinkRightLimit)
    {
        if (weight < 0) {
            throw new IllegalArgumentException("weight is negative");
        }

        if (!priorityIsValid(growPriority)) {
            throw new IllegalArgumentException("Invalid grow priority");
        }
        if (growLeftLimit < 0) {
            throw new IllegalArgumentException("growLeftLimit is negative");
        }
        if (growRightLimit < 0) {
            throw new IllegalArgumentException("growRightLimit is negative");
        }

        if (!priorityIsValid(shrinkPriority)) {
            throw new IllegalArgumentException("Invalid shrink priority");
        }
        if (shrinkLeftLimit < 0) {
            throw new IllegalArgumentException("shrinkLeftLimit is negative");
        }
        if (shrinkRightLimit < 0) {
            throw new IllegalArgumentException("shrinkRightLimit is negative");
        }

        this.weight = weight;
        this.growAbsorb = growAbsorb;
        this.growPriority = growPriority;
        this.growLeftLimit = growLeftLimit;
        this.growRightLimit = growRightLimit;
        this.shrinkAbsorb = shrinkAbsorb;
        this.shrinkPriority = shrinkPriority;
        this.shrinkLeftLimit = shrinkLeftLimit;
        this.shrinkRightLimit = shrinkRightLimit;
    }

    private static boolean priorityIsValid(int priority) {

        return priority >= PRIORITY_KASHIDA && priority <= PRIORITY_NONE;
    }

    /** The highest justification priority. */
    public static final int PRIORITY_KASHIDA = 0;

    /** The second highest justification priority. */
    public static final int PRIORITY_WHITESPACE = 1;

    /** The second lowest justification priority. */
    public static final int PRIORITY_INTERCHAR = 2;

    /** The lowest justification priority. */
    public static final int PRIORITY_NONE = 3;

    /**
     * The weight of this glyph.
     * <p>
     *  这个字形的重量。
     * 
     */
    public final float weight;

    /**
     * The priority level of this glyph as it is growing.
     * <p>
     *  这个字形的优先级,因为它在增长。
     * 
     */
    public final int growPriority;

    /**
     * If <code>true</code>, this glyph absorbs all extra
     * space at this and lower priority levels when it grows.
     * <p>
     *  如果<code> true </code>,则此字形在其增长时吸收此和较低优先级的所有额外空间。
     * 
     */
    public final boolean growAbsorb;

    /**
     * The maximum amount by which the left side of this glyph can grow.
     * <p>
     *  本字形左侧可以生长的最大量。
     * 
     */
    public final float growLeftLimit;

    /**
     * The maximum amount by which the right side of this glyph can grow.
     * <p>
     * 此字形右侧可以生长的最大量。
     * 
     */
    public final float growRightLimit;

    /**
     * The priority level of this glyph as it is shrinking.
     * <p>
     *  这个字形的优先级,因为它缩小。
     * 
     */
    public final int shrinkPriority;

    /**
     * If <code>true</code>,this glyph absorbs all remaining shrinkage at
     * this and lower priority levels as it shrinks.
     * <p>
     *  如果<code> true </code>,此字形在收缩时会吸收此处的所有剩余收缩以及较低的优先级。
     * 
     */
    public final boolean shrinkAbsorb;

    /**
     * The maximum amount by which the left side of this glyph can shrink
     * (a positive number).
     * <p>
     *  这个字形的左边可以收缩的最大量(一个正数)。
     * 
     */
    public final float shrinkLeftLimit;

    /**
     * The maximum amount by which the right side of this glyph can shrink
     * (a positive number).
     * <p>
     *  此字形右侧可收缩的最大量(正数)。
     */
    public final float shrinkRightLimit;
}
