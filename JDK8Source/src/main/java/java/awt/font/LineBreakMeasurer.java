/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 2013, Oracle and/or its affiliates. All rights reserved.
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

import java.text.BreakIterator;
import java.text.CharacterIterator;
import java.text.AttributedCharacterIterator;
import java.awt.font.FontRenderContext;

/**
 * The <code>LineBreakMeasurer</code> class allows styled text to be
 * broken into lines (or segments) that fit within a particular visual
 * advance.  This is useful for clients who wish to display a paragraph of
 * text that fits within a specific width, called the <b>wrapping
 * width</b>.
 * <p>
 * <code>LineBreakMeasurer</code> is constructed with an iterator over
 * styled text.  The iterator's range should be a single paragraph in the
 * text.
 * <code>LineBreakMeasurer</code> maintains a position in the text for the
 * start of the next text segment.  Initially, this position is the
 * start of text.  Paragraphs are assigned an overall direction (either
 * left-to-right or right-to-left) according to the bidirectional
 * formatting rules.  All segments obtained from a paragraph have the
 * same direction as the paragraph.
 * <p>
 * Segments of text are obtained by calling the method
 * <code>nextLayout</code>, which returns a {@link TextLayout}
 * representing the text that fits within the wrapping width.
 * The <code>nextLayout</code> method moves the current position
 * to the end of the layout returned from <code>nextLayout</code>.
 * <p>
 * <code>LineBreakMeasurer</code> implements the most commonly used
 * line-breaking policy: Every word that fits within the wrapping
 * width is placed on the line. If the first word does not fit, then all
 * of the characters that fit within the wrapping width are placed on the
 * line.  At least one character is placed on each line.
 * <p>
 * The <code>TextLayout</code> instances returned by
 * <code>LineBreakMeasurer</code> treat tabs like 0-width spaces.  Clients
 * who wish to obtain tab-delimited segments for positioning should use
 * the overload of <code>nextLayout</code> which takes a limiting offset
 * in the text.
 * The limiting offset should be the first character after the tab.
 * The <code>TextLayout</code> objects returned from this method end
 * at the limit provided (or before, if the text between the current
 * position and the limit won't fit entirely within the  wrapping
 * width).
 * <p>
 * Clients who are laying out tab-delimited text need a slightly
 * different line-breaking policy after the first segment has been
 * placed on a line.  Instead of fitting partial words in the
 * remaining space, they should place words which don't fit in the
 * remaining space entirely on the next line.  This change of policy
 * can be requested in the overload of <code>nextLayout</code> which
 * takes a <code>boolean</code> parameter.  If this parameter is
 * <code>true</code>, <code>nextLayout</code> returns
 * <code>null</code> if the first word won't fit in
 * the given space.  See the tab sample below.
 * <p>
 * In general, if the text used to construct the
 * <code>LineBreakMeasurer</code> changes, a new
 * <code>LineBreakMeasurer</code> must be constructed to reflect
 * the change.  (The old <code>LineBreakMeasurer</code> continues to
 * function properly, but it won't be aware of the text change.)
 * Nevertheless, if the text change is the insertion or deletion of a
 * single character, an existing <code>LineBreakMeasurer</code> can be
 * 'updated' by calling <code>insertChar</code> or
 * <code>deleteChar</code>. Updating an existing
 * <code>LineBreakMeasurer</code> is much faster than creating a new one.
 * Clients who modify text based on user typing should take advantage
 * of these methods.
 * <p>
 * <strong>Examples</strong>:<p>
 * Rendering a paragraph in a component
 * <blockquote>
 * <pre>{@code
 * public void paint(Graphics graphics) {
 *
 *     Point2D pen = new Point2D(10, 20);
 *     Graphics2D g2d = (Graphics2D)graphics;
 *     FontRenderContext frc = g2d.getFontRenderContext();
 *
 *     // let styledText be an AttributedCharacterIterator containing at least
 *     // one character
 *
 *     LineBreakMeasurer measurer = new LineBreakMeasurer(styledText, frc);
 *     float wrappingWidth = getSize().width - 15;
 *
 *     while (measurer.getPosition() < fStyledText.length()) {
 *
 *         TextLayout layout = measurer.nextLayout(wrappingWidth);
 *
 *         pen.y += (layout.getAscent());
 *         float dx = layout.isLeftToRight() ?
 *             0 : (wrappingWidth - layout.getAdvance());
 *
 *         layout.draw(graphics, pen.x + dx, pen.y);
 *         pen.y += layout.getDescent() + layout.getLeading();
 *     }
 * }
 * }</pre>
 * </blockquote>
 * <p>
 * Rendering text with tabs.  For simplicity, the overall text
 * direction is assumed to be left-to-right
 * <blockquote>
 * <pre>{@code
 * public void paint(Graphics graphics) {
 *
 *     float leftMargin = 10, rightMargin = 310;
 *     float[] tabStops = { 100, 250 };
 *
 *     // assume styledText is an AttributedCharacterIterator, and the number
 *     // of tabs in styledText is tabCount
 *
 *     int[] tabLocations = new int[tabCount+1];
 *
 *     int i = 0;
 *     for (char c = styledText.first(); c != styledText.DONE; c = styledText.next()) {
 *         if (c == '\t') {
 *             tabLocations[i++] = styledText.getIndex();
 *         }
 *     }
 *     tabLocations[tabCount] = styledText.getEndIndex() - 1;
 *
 *     // Now tabLocations has an entry for every tab's offset in
 *     // the text.  For convenience, the last entry is tabLocations
 *     // is the offset of the last character in the text.
 *
 *     LineBreakMeasurer measurer = new LineBreakMeasurer(styledText);
 *     int currentTab = 0;
 *     float verticalPos = 20;
 *
 *     while (measurer.getPosition() < styledText.getEndIndex()) {
 *
 *         // Lay out and draw each line.  All segments on a line
 *         // must be computed before any drawing can occur, since
 *         // we must know the largest ascent on the line.
 *         // TextLayouts are computed and stored in a Vector;
 *         // their horizontal positions are stored in a parallel
 *         // Vector.
 *
 *         // lineContainsText is true after first segment is drawn
 *         boolean lineContainsText = false;
 *         boolean lineComplete = false;
 *         float maxAscent = 0, maxDescent = 0;
 *         float horizontalPos = leftMargin;
 *         Vector layouts = new Vector(1);
 *         Vector penPositions = new Vector(1);
 *
 *         while (!lineComplete) {
 *             float wrappingWidth = rightMargin - horizontalPos;
 *             TextLayout layout =
 *                     measurer.nextLayout(wrappingWidth,
 *                                         tabLocations[currentTab]+1,
 *                                         lineContainsText);
 *
 *             // layout can be null if lineContainsText is true
 *             if (layout != null) {
 *                 layouts.addElement(layout);
 *                 penPositions.addElement(new Float(horizontalPos));
 *                 horizontalPos += layout.getAdvance();
 *                 maxAscent = Math.max(maxAscent, layout.getAscent());
 *                 maxDescent = Math.max(maxDescent,
 *                     layout.getDescent() + layout.getLeading());
 *             } else {
 *                 lineComplete = true;
 *             }
 *
 *             lineContainsText = true;
 *
 *             if (measurer.getPosition() == tabLocations[currentTab]+1) {
 *                 currentTab++;
 *             }
 *
 *             if (measurer.getPosition() == styledText.getEndIndex())
 *                 lineComplete = true;
 *             else if (horizontalPos >= tabStops[tabStops.length-1])
 *                 lineComplete = true;
 *
 *             if (!lineComplete) {
 *                 // move to next tab stop
 *                 int j;
 *                 for (j=0; horizontalPos >= tabStops[j]; j++) {}
 *                 horizontalPos = tabStops[j];
 *             }
 *         }
 *
 *         verticalPos += maxAscent;
 *
 *         Enumeration layoutEnum = layouts.elements();
 *         Enumeration positionEnum = penPositions.elements();
 *
 *         // now iterate through layouts and draw them
 *         while (layoutEnum.hasMoreElements()) {
 *             TextLayout nextLayout = (TextLayout) layoutEnum.nextElement();
 *             Float nextPosition = (Float) positionEnum.nextElement();
 *             nextLayout.draw(graphics, nextPosition.floatValue(), verticalPos);
 *         }
 *
 *         verticalPos += maxDescent;
 *     }
 * }
 * }</pre>
 * </blockquote>
 * <p>
 *  <code> LineBreakMeasurer </code>类允许将样式文本分成适合特定视觉提示的线(或段)。这对于希望显示适合特定宽度(称为<b>换行宽度</b>)的文本段落的客户很有用。
 * <p>
 *  <code> LineBreakMeasurer </code>是在样式文本上用迭代器构造的。迭代器的范围应该是文本中的单个段落。
 *  <code> LineBreakMeasurer </code>维护文本中下一个文本段开始的位置。最初,这个位置是文本的开始。根据双向格式化规则,为段落分配一个总体方向(从左到右或从右到左)。
 * 从段落获得的所有段具有与段落相同的方向。
 * <p>
 * 通过调用<code> nextLayout </code>方法获取文本段,该方法返回表示适合包装宽度的文本的{@link TextLayout}。
 *  <code> nextLayout </code>方法将当前位置移动到从<code> nextLayout </code>返回的布局的结尾。
 * <p>
 *  <code> LineBreakMeasurer </code>实现最常用的拆线策略：适合包装宽度的每个单词都放在行上。如果第一个单词不合适,则所有符合包装宽度的字符都放在行上。
 * 每行至少放置一个字符。
 * <p>
 *  <code> LineBreakMeasurer </code>返回的<code> TextLayout </code>实例处理诸如0宽度空格的制表符。
 * 希望获得用于定位的制表符分隔的段的客户端应该使用<code> nextLayout </code>的重载,其在文本中具有极限偏移。限制偏移应为选项卡后的第一个字符。
 * 从此方法返回的<code> TextLayout </code>对象在提供的限制处结束(如果当前位置和限制之间的文本不完全适合包装宽度,则在此之前)。
 * <p>
 * 布置制表符分隔文本的客户端在第一个段放置在一行后需要略有不同的换行策略。代替在剩余空间中拟合部分字,它们应该将不适合剩余空间的单词完全放在下一行上。
 * 可以在<code> nextLayout </code>的过载中请求此策略更改,该过载需要一个<code> boolean </code>参数。
 * 如果这个参数是<code> true </code>,则<code> nextLayout </code>如果第一个单词不适合给定空格,则返回<code> null </code>。
 * 请参阅下面的选项卡示例。
 * <p>
 *  一般来说,如果用于构建<code> LineBreakMeasurer </code>的文本发生变化,则必须构造一个新的<code> LineBreakMeasurer </code>以反映更改。
 *  (旧<code> LineBreakMeasurer </code>继续正常工作,但它不会意识到文本更改。
 * )然而,如果文本更改是单个字符的插入或删除,现有的<code> LineBreakMeasurer </code>可以通过调用<code> insertChar </code>或<code> delet
 * eChar </code>来更新。
 *  (旧<code> LineBreakMeasurer </code>继续正常工作,但它不会意识到文本更改。
 * 更新现有的<code> LineBreakMeasurer </code>比创建一个新的更快。基于用户输入修改文本的客户应该利用这些方法。
 * <p>
 *  <strong>示例</strong>：<p>在组件中呈现段落
 * <blockquote>
 *  <pre> {@ code public void paint(Graphics graphics){
 * 
 * Point2D pen = new Point2D(10,20); Graphics2D g2d =(Graphics2D)图形; FontRenderContext frc = g2d.getFont
 * RenderContext();。
 * 
 *  // let styledText是一个包含至少//一个字符的AttributedCharacterIterator
 * 
 *  LineBreakMeasurer measurer = new LineBreakMeasurer(styledText,frc); float wrappingWidth = getSize()。
 * width  -  15;。
 * 
 *  while(measurer.getPosition()<fStyledText.length()){
 * 
 *  TextLayout layout = measurer.nextLayout(wrappingWidth);
 * 
 *  pen.y + =(layout.getAscent()); float dx = layout.isLeftToRight()? 0：(wrappingWidth  -  layout.getAdv
 * ance());。
 * 
 *  layout.draw(graphics,pen.x + dx,pen.y); pen.y + = layout.getDescent()+ layout.getLeading(); }}} </pre>
 * 。
 * </blockquote>
 * <p>
 *  使用制表符渲染文本。为了简单起见,假定整个文本方向是从左到右
 * <blockquote>
 *  <pre> {@ code public void paint(Graphics graphics){
 * 
 *  float leftMargin = 10,rightMargin = 310; float [] tabStops = {100,250};
 * 
 *  //假设styledText是一个AttributedCharacterIterator,并且styledText中的标签数//是tabCount
 * 
 *  int [] tabLocations = new int [tabCount + 1];
 * 
 *  int i = 0; for(char c = styledText.first(); c！= styledText.DONE; c = styledText.next()){if(c =='\ t'){tabLocations [i ++] = styledText.getIndex }
 * } tabLocations [tabCount] = styledText.getEndIndex() -  1;。
 * 
 *  // Now tabLocations有一个条目//每个标签的偏移量//在文本中。为方便起见,最后一个条目是tabLocations //是文本中最后一个字符的偏移量。
 * 
 * 
 * @see TextLayout
 */

public final class LineBreakMeasurer {

    private BreakIterator breakIter;
    private int start;
    private int pos;
    private int limit;
    private TextMeasurer measurer;
    private CharArrayIterator charIter;

    /**
     * Constructs a <code>LineBreakMeasurer</code> for the specified text.
     *
     * <p>
     * LineBreakMeasurer measurer = new LineBreakMeasurer(styledText); int currentTab = 0; float verticalPos
     *  = 20;。
     * 
     *  while(measurer.getPosition()<styledText.getEndIndex()){
     * 
     *  //布局和绘制每一行。线//上的所有线段必须在任何绘图之前计算,因为//我们必须知道线上的最大上升。
     *  // TextLayouts被计算并存储在Vector中; //它们的水平位置存储在一个平行的// Vector中。
     * 
     *  // lineContainsText在第一个段被绘制后为true boolean lineContainsText = false; boolean lineComplete = false; fl
     * oat maxAscent = 0,maxDescent = 0; float horizo​​ntalPos = leftMargin; Vector layouts = new Vector(1);
     * 矢量penPositions =新矢量(1);。
     * 
     *  while(！lineComplete){float wrappingWidth = rightMargin  -  horizo​​ntalPos; TextLayout layout = measurer.nextLayout(wrappingWidth,tabLocations [currentTab] +1,lineContainsText);。
     * 
     *  // layout可以为null如果lineContainsText为true if(layout！= null){layouts.addElement(layout); penPositions.addElement(new Float(horizo​​ntalPos)); horizo​​ntalPos + = layout.getAdvance(); maxAscent = Math.max(maxAscent,layout.getAscent()); maxDescent = Math.max(maxDescent,layout.getDescent()+ layout.getLeading()); }
     *  else {lineComplete = true; }}。
     * 
     *  lineContainsText = true;
     * 
     *  if(measurer.getPosition()== tabLocations [currentTab] +1){currentTab ++; }}
     * 
     *  if(measurer.getPosition()== styledText.getEndIndex())lineComplete = true; else if(horizo​​ntalPos> =
     *  tabStops [tabStops.length-1])lineComplete = true;。
     * 
     * if(！lineComplete){// move to next tab stop int j; for(j = 0; horizo​​ntalPos> = tabStops [j]; j ++){}
     *  horizo​​ntalPos = tabStops [j]; }}。
     * 
     *  verticalPos + = maxAscent;
     * 
     *  枚举layoutEnum = layouts.elements(); Enumeration positionEnum = penPositions.elements();
     * 
     *  //现在迭代布局并绘制它们while(layoutEnum.hasMoreElements()){TextLayout nextLayout =(TextLayout)layoutEnum.nextElement(); Float nextPosition =(Float)positionEnum.nextElement(); nextLayout.draw(graphics,nextPosition.floatValue(),verticalPos); }
     * }。
     * 
     * @param text the text for which this <code>LineBreakMeasurer</code>
     *       produces <code>TextLayout</code> objects; the text must contain
     *       at least one character; if the text available through
     *       <code>iter</code> changes, further calls to this
     *       <code>LineBreakMeasurer</code> instance are undefined (except,
     *       in some cases, when <code>insertChar</code> or
     *       <code>deleteChar</code> are invoked afterward - see below)
     * @param frc contains information about a graphics device which is
     *       needed to measure the text correctly;
     *       text measurements can vary slightly depending on the
     *       device resolution, and attributes such as antialiasing; this
     *       parameter does not specify a translation between the
     *       <code>LineBreakMeasurer</code> and user space
     * @see LineBreakMeasurer#insertChar
     * @see LineBreakMeasurer#deleteChar
     */
    public LineBreakMeasurer(AttributedCharacterIterator text, FontRenderContext frc) {
        this(text, BreakIterator.getLineInstance(), frc);
    }

    /**
     * Constructs a <code>LineBreakMeasurer</code> for the specified text.
     *
     * <p>
     * 
     *  verticalPos + = maxDescent; }}} </pre>
     * </blockquote>
     * 
     * @param text the text for which this <code>LineBreakMeasurer</code>
     *     produces <code>TextLayout</code> objects; the text must contain
     *     at least one character; if the text available through
     *     <code>iter</code> changes, further calls to this
     *     <code>LineBreakMeasurer</code> instance are undefined (except,
     *     in some cases, when <code>insertChar</code> or
     *     <code>deleteChar</code> are invoked afterward - see below)
     * @param breakIter the {@link BreakIterator} which defines line
     *     breaks
     * @param frc contains information about a graphics device which is
     *       needed to measure the text correctly;
     *       text measurements can vary slightly depending on the
     *       device resolution, and attributes such as antialiasing; this
     *       parameter does not specify a translation between the
     *       <code>LineBreakMeasurer</code> and user space
     * @throws IllegalArgumentException if the text has less than one character
     * @see LineBreakMeasurer#insertChar
     * @see LineBreakMeasurer#deleteChar
     */
    public LineBreakMeasurer(AttributedCharacterIterator text,
                             BreakIterator breakIter,
                             FontRenderContext frc) {
        if (text.getEndIndex() - text.getBeginIndex() < 1) {
            throw new IllegalArgumentException("Text must contain at least one character.");
        }

        this.breakIter = breakIter;
        this.measurer = new TextMeasurer(text, frc);
        this.limit = text.getEndIndex();
        this.pos = this.start = text.getBeginIndex();

        charIter = new CharArrayIterator(measurer.getChars(), this.start);
        this.breakIter.setText(charIter);
    }

    /**
     * Returns the position at the end of the next layout.  Does NOT
     * update the current position of this <code>LineBreakMeasurer</code>.
     *
     * <p>
     *  为指定的文本构造一个<code> LineBreakMeasurer </code>。
     * 
     * 
     * @param wrappingWidth the maximum visible advance permitted for
     *    the text in the next layout
     * @return an offset in the text representing the limit of the
     *    next <code>TextLayout</code>.
     */
    public int nextOffset(float wrappingWidth) {
        return nextOffset(wrappingWidth, limit, false);
    }

    /**
     * Returns the position at the end of the next layout.  Does NOT
     * update the current position of this <code>LineBreakMeasurer</code>.
     *
     * <p>
     *  为指定的文本构造一个<code> LineBreakMeasurer </code>。
     * 
     * 
     * @param wrappingWidth the maximum visible advance permitted for
     *    the text in the next layout
     * @param offsetLimit the first character that can not be included
     *    in the next layout, even if the text after the limit would fit
     *    within the wrapping width; <code>offsetLimit</code> must be
     *    greater than the current position
     * @param requireNextWord if <code>true</code>, the current position
     *    that is returned if the entire next word does not fit within
     *    <code>wrappingWidth</code>; if <code>false</code>, the offset
     *    returned is at least one greater than the current position
     * @return an offset in the text representing the limit of the
     *    next <code>TextLayout</code>
     */
    public int nextOffset(float wrappingWidth, int offsetLimit,
                          boolean requireNextWord) {

        int nextOffset = pos;

        if (pos < limit) {
            if (offsetLimit <= pos) {
                    throw new IllegalArgumentException("offsetLimit must be after current position");
            }

            int charAtMaxAdvance =
                            measurer.getLineBreakIndex(pos, wrappingWidth);

            if (charAtMaxAdvance == limit) {
                nextOffset = limit;
            }
            else if (Character.isWhitespace(measurer.getChars()[charAtMaxAdvance-start])) {
                nextOffset = breakIter.following(charAtMaxAdvance);
            }
            else {
            // Break is in a word;  back up to previous break.

                // NOTE:  I think that breakIter.preceding(limit) should be
                // equivalent to breakIter.last(), breakIter.previous() but
                // the authors of BreakIterator thought otherwise...
                // If they were equivalent then the first branch would be
                // unnecessary.
                int testPos = charAtMaxAdvance + 1;
                if (testPos == limit) {
                    breakIter.last();
                    nextOffset = breakIter.previous();
                }
                else {
                    nextOffset = breakIter.preceding(testPos);
                }

                if (nextOffset <= pos) {
                    // first word doesn't fit on line
                    if (requireNextWord) {
                        nextOffset = pos;
                    }
                    else {
                        nextOffset = Math.max(pos+1, charAtMaxAdvance);
                    }
                }
            }
        }

        if (nextOffset > offsetLimit) {
            nextOffset = offsetLimit;
        }

        return nextOffset;
    }

    /**
     * Returns the next layout, and updates the current position.
     *
     * <p>
     *  返回下一个布局结尾处的位置。不更新此<code> LineBreakMeasurer </code>的当前位置。
     * 
     * 
     * @param wrappingWidth the maximum visible advance permitted for
     *     the text in the next layout
     * @return a <code>TextLayout</code>, beginning at the current
     *     position, which represents the next line fitting within
     *     <code>wrappingWidth</code>
     */
    public TextLayout nextLayout(float wrappingWidth) {
        return nextLayout(wrappingWidth, limit, false);
    }

    /**
     * Returns the next layout, and updates the current position.
     *
     * <p>
     *  返回下一个布局结尾处的位置。不更新此<code> LineBreakMeasurer </code>的当前位置。
     * 
     * 
     * @param wrappingWidth the maximum visible advance permitted
     *    for the text in the next layout
     * @param offsetLimit the first character that can not be
     *    included in the next layout, even if the text after the limit
     *    would fit within the wrapping width; <code>offsetLimit</code>
     *    must be greater than the current position
     * @param requireNextWord if <code>true</code>, and if the entire word
     *    at the current position does not fit within the wrapping width,
     *    <code>null</code> is returned. If <code>false</code>, a valid
     *    layout is returned that includes at least the character at the
     *    current position
     * @return a <code>TextLayout</code>, beginning at the current
     *    position, that represents the next line fitting within
     *    <code>wrappingWidth</code>.  If the current position is at the end
     *    of the text used by this <code>LineBreakMeasurer</code>,
     *    <code>null</code> is returned
     */
    public TextLayout nextLayout(float wrappingWidth, int offsetLimit,
                                 boolean requireNextWord) {

        if (pos < limit) {
            int layoutLimit = nextOffset(wrappingWidth, offsetLimit, requireNextWord);
            if (layoutLimit == pos) {
                return null;
            }

            TextLayout result = measurer.getLayout(pos, layoutLimit);
            pos = layoutLimit;

            return result;
        } else {
            return null;
        }
    }

    /**
     * Returns the current position of this <code>LineBreakMeasurer</code>.
     *
     * <p>
     *  返回下一个布局,并更新当前位置。
     * 
     * 
     * @return the current position of this <code>LineBreakMeasurer</code>
     * @see #setPosition
     */
    public int getPosition() {
        return pos;
    }

    /**
     * Sets the current position of this <code>LineBreakMeasurer</code>.
     *
     * <p>
     *  返回下一个布局,并更新当前位置。
     * 
     * 
     * @param newPosition the current position of this
     *    <code>LineBreakMeasurer</code>; the position should be within the
     *    text used to construct this <code>LineBreakMeasurer</code> (or in
     *    the text most recently passed to <code>insertChar</code>
     *    or <code>deleteChar</code>
     * @see #getPosition
     */
    public void setPosition(int newPosition) {
        if (newPosition < start || newPosition > limit) {
            throw new IllegalArgumentException("position is out of range");
        }
        pos = newPosition;
    }

    /**
     * Updates this <code>LineBreakMeasurer</code> after a single
     * character is inserted into the text, and sets the current
     * position to the beginning of the paragraph.
     *
     * <p>
     *  返回此<code> LineBreakMeasurer </code>的当前位置。
     * 
     * 
     * @param newParagraph the text after the insertion
     * @param insertPos the position in the text at which the character
     *    is inserted
     * @throws IndexOutOfBoundsException if <code>insertPos</code> is less
     *         than the start of <code>newParagraph</code> or greater than
     *         or equal to the end of <code>newParagraph</code>
     * @throws NullPointerException if <code>newParagraph</code> is
     *         <code>null</code>
     * @see #deleteChar
     */
    public void insertChar(AttributedCharacterIterator newParagraph,
                           int insertPos) {

        measurer.insertChar(newParagraph, insertPos);

        limit = newParagraph.getEndIndex();
        pos = start = newParagraph.getBeginIndex();

        charIter.reset(measurer.getChars(), newParagraph.getBeginIndex());
        breakIter.setText(charIter);
    }

    /**
     * Updates this <code>LineBreakMeasurer</code> after a single
     * character is deleted from the text, and sets the current
     * position to the beginning of the paragraph.
     * <p>
     *  设置此<code> LineBreakMeasurer </code>的当前位置。
     * 
     * 
     * @param newParagraph the text after the deletion
     * @param deletePos the position in the text at which the character
     *    is deleted
     * @throws IndexOutOfBoundsException if <code>deletePos</code> is
     *         less than the start of <code>newParagraph</code> or greater
     *         than the end of <code>newParagraph</code>
     * @throws NullPointerException if <code>newParagraph</code> is
     *         <code>null</code>
     * @see #insertChar
     */
    public void deleteChar(AttributedCharacterIterator newParagraph,
                           int deletePos) {

        measurer.deleteChar(newParagraph, deletePos);

        limit = newParagraph.getEndIndex();
        pos = start = newParagraph.getBeginIndex();

        charIter.reset(measurer.getChars(), start);
        breakIter.setText(charIter);
    }
}
