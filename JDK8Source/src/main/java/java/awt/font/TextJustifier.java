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
 *
 * <p>
 *  (C)版权所有Taligent,Inc. 1996  -  1997,保留所有权利(C)版权所有IBM Corp. 1996  -  1998,保留所有权利
 * 
 *  此源代码和文档的原始版本由IBM的全资子公司Taligent,Inc.拥有版权和所有权。这些材料是根据Taligent和Sun之间的许可协议的条款提供的。该技术受多项美国和国际专利保护。
 * 
 *  此通知和归因于Taligent不得删除。 Taligent是Taligent,Inc.的注册商标。
 * 
 */

package java.awt.font;

/*
 * one info for each side of each glyph
 * separate infos for grow and shrink case
 * !!! this doesn't really need to be a separate class.  If we keep it
 * separate, probably the newJustify code from TextLayout belongs here as well.
 * <p>
 *  一个信息为每个字形的每一侧单独的信息增长和收缩情况！这不需要是一个单独的类。如果我们保持分离,可能来自TextLayout的newJustify代码也在这里。
 * 
 */

class TextJustifier {
    private GlyphJustificationInfo[] info;
    private int start;
    private int limit;

    static boolean DEBUG = false;

    /**
     * Initialize the justifier with an array of infos corresponding to each
     * glyph. Start and limit indicate the range of the array to examine.
     * <p>
     *  使用与每个字形对应的信息数组初始化对齐方式。开始和限制指示要检查的数组的范围。
     * 
     */
    TextJustifier(GlyphJustificationInfo[] info, int start, int limit) {
        this.info = info;
        this.start = start;
        this.limit = limit;

        if (DEBUG) {
            System.out.println("start: " + start + ", limit: " + limit);
            for (int i = start; i < limit; i++) {
                GlyphJustificationInfo gji = info[i];
                System.out.println("w: " + gji.weight + ", gp: " +
                                   gji.growPriority + ", gll: " +
                                   gji.growLeftLimit + ", grl: " +
                                   gji.growRightLimit);
            }
        }
    }

    public static final int MAX_PRIORITY = 3;

    /**
     * Return an array of deltas twice as long as the original info array,
     * indicating the amount by which each side of each glyph should grow
     * or shrink.
     *
     * Delta should be positive to expand the line, and negative to compress it.
     * <p>
     *  返回一个三角形数组,它的长度是原始信息数组的两倍,指示每个字形的每一边应该增长或缩小的量。
     * 
     *  Delta应该是正的以扩展线,而负的来压缩它。
     * 
     */
    public float[] justify(float delta) {
        float[] deltas = new float[info.length * 2];

        boolean grow = delta > 0;

        if (DEBUG)
            System.out.println("delta: " + delta);

        // make separate passes through glyphs in order of decreasing priority
        // until justifyDelta is zero or we run out of priorities.
        int fallbackPriority = -1;
        for (int p = 0; delta != 0; p++) {
            /*
             * special case 'fallback' iteration, set flag and recheck
             * highest priority
             * <p>
             */
            boolean lastPass = p > MAX_PRIORITY;
            if (lastPass)
                p = fallbackPriority;

            // pass through glyphs, first collecting weights and limits
            float weight = 0;
            float gslimit = 0;
            float absorbweight = 0;
            for (int i = start; i < limit; i++) {
                GlyphJustificationInfo gi = info[i];
                if ((grow ? gi.growPriority : gi.shrinkPriority) == p) {
                    if (fallbackPriority == -1) {
                        fallbackPriority = p;
                    }

                    if (i != start) { // ignore left of first character
                        weight += gi.weight;
                        if (grow) {
                            gslimit += gi.growLeftLimit;
                            if (gi.growAbsorb) {
                                absorbweight += gi.weight;
                            }
                        } else {
                            gslimit += gi.shrinkLeftLimit;
                            if (gi.shrinkAbsorb) {
                                absorbweight += gi.weight;
                            }
                        }
                    }

                    if (i + 1 != limit) { // ignore right of last character
                        weight += gi.weight;
                        if (grow) {
                            gslimit += gi.growRightLimit;
                            if (gi.growAbsorb) {
                                absorbweight += gi.weight;
                            }
                        } else {
                            gslimit += gi.shrinkRightLimit;
                            if (gi.shrinkAbsorb) {
                                absorbweight += gi.weight;
                            }
                        }
                    }
                }
            }

            // did we hit the limit?
            if (!grow) {
                gslimit = -gslimit; // negative for negative deltas
            }
            boolean hitLimit = (weight == 0) || (!lastPass && ((delta < 0) == (delta < gslimit)));
            boolean absorbing = hitLimit && absorbweight > 0;

            // predivide delta by weight
            float weightedDelta = delta / weight; // not used if weight == 0

            float weightedAbsorb = 0;
            if (hitLimit && absorbweight > 0) {
                weightedAbsorb = (delta - gslimit) / absorbweight;
            }

            if (DEBUG) {
                System.out.println("pass: " + p +
                    ", d: " + delta +
                    ", l: " + gslimit +
                    ", w: " + weight +
                    ", aw: " + absorbweight +
                    ", wd: " + weightedDelta +
                    ", wa: " + weightedAbsorb +
                    ", hit: " + (hitLimit ? "y" : "n"));
            }

            // now allocate this based on ratio of weight to total weight
            int n = start * 2;
            for (int i = start; i < limit; i++) {
                GlyphJustificationInfo gi = info[i];
                if ((grow ? gi.growPriority : gi.shrinkPriority) == p) {
                    if (i != start) { // ignore left
                        float d;
                        if (hitLimit) {
                            // factor in sign
                            d = grow ? gi.growLeftLimit : -gi.shrinkLeftLimit;
                            if (absorbing) {
                                // sign factored in already
                               d += gi.weight * weightedAbsorb;
                            }
                        } else {
                            // sign factored in already
                            d = gi.weight * weightedDelta;
                        }

                        deltas[n] += d;
                    }
                    n++;

                    if (i + 1 != limit) { // ignore right
                        float d;
                        if (hitLimit) {
                            d = grow ? gi.growRightLimit : -gi.shrinkRightLimit;
                            if (absorbing) {
                                d += gi.weight * weightedAbsorb;
                            }
                        } else {
                            d = gi.weight * weightedDelta;
                        }

                        deltas[n] += d;
                    }
                    n++;
                } else {
                    n += 2;
                }
            }

            if (!lastPass && hitLimit && !absorbing) {
                delta -= gslimit;
            } else {
                delta = 0; // stop iteration
            }
        }

        if (DEBUG) {
            float total = 0;
            for (int i = 0; i < deltas.length; i++) {
                total += deltas[i];
                System.out.print(deltas[i] + ", ");
                if (i % 20 == 9) {
                    System.out.println();
                }
            }
            System.out.println("\ntotal: " + total);
            System.out.println();
        }

        return deltas;
    }
}
