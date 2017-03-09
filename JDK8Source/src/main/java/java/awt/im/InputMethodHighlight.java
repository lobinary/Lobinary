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

package java.awt.im;

import java.awt.font.TextAttribute;
import java.util.Map;

/**
* An InputMethodHighlight is used to describe the highlight
* attributes of text being composed.
* The description can be at two levels:
* at the abstract level it specifies the conversion state and whether the
* text is selected; at the concrete level it specifies style attributes used
* to render the highlight.
* An InputMethodHighlight must provide the description at the
* abstract level; it may or may not provide the description at the concrete
* level.
* If no concrete style is provided, a renderer should use
* {@link java.awt.Toolkit#mapInputMethodHighlight} to map to a concrete style.
* <p>
* The abstract description consists of three fields: <code>selected</code>,
* <code>state</code>, and <code>variation</code>.
* <code>selected</code> indicates whether the text range is the one that the
* input method is currently working on, for example, the segment for which
* conversion candidates are currently shown in a menu.
* <code>state</code> represents the conversion state. State values are defined
* by the input method framework and should be distinguished in all
* mappings from abstract to concrete styles. Currently defined state values
* are raw (unconverted) and converted.
* These state values are recommended for use before and after the
* main conversion step of text composition, say, before and after kana-&gt;kanji
* or pinyin-&gt;hanzi conversion.
* The <code>variation</code> field allows input methods to express additional
* information about the conversion results.
* <p>
*
* InputMethodHighlight instances are typically used as attribute values
* returned from AttributedCharacterIterator for the INPUT_METHOD_HIGHLIGHT
* attribute. They may be wrapped into {@link java.text.Annotation Annotation}
* instances to indicate separate text segments.
*
* <p>
*  InputMethodHighlight用于描述正在编写的文本的突出显示属性。描述可以在两个级别：在抽象级别,它指定转换状态和文本是否被选择;在具体级别,它指定用于渲染突出显示的样式属性。
*  InputMethodHighlight必须在抽象层提供描述;它可能或可能不在具体级别提供描述。
* 如果没有提供具体样式,渲染器应使用{@link java.awt.Toolkit#mapInputMethodHighlight}映射到具体样式。
* <p>
* 抽象描述包括三个字段：<code> selected </code>,<code> state </code>和<code> variant </code>。
*  <code> selected </code>指示文本范围是否为输入法当前正在工作的范围,例如,当前在菜单中显示转换候选的片段。 <code> state </code>表示转换状态。
* 状态值由输入法框架定义,并应在所有映射中从抽象到具体样式区分。当前定义的状态值为raw(未转换)并转换。
* 这些状态值被推荐在文本组成的主要转换步骤之前和之后使用,例如在假名 - 汉字或拼音 - 汉字转换之前和之后使用。 <code>变体</code>字段允许输入法表达关于转换结果的附加信息。
* <p>
* 
*  InputMethodHighlight实例通常用作从AttributedCharacterIterator为INPUT_METHOD_HIGHLIGHT属性返回的属性值。
* 它们可以包装在{@link java.text.Annotation Annotation}实例中,以指示单独的文本段。
* 
* 
* @see java.text.AttributedCharacterIterator
* @since 1.2
*/

public class InputMethodHighlight {

    /**
     * Constant for the raw text state.
     * <p>
     *  原始文本状态的常量。
     * 
     */
    public final static int RAW_TEXT = 0;

    /**
     * Constant for the converted text state.
     * <p>
     *  转换文本状态的常量。
     * 
     */
    public final static int CONVERTED_TEXT = 1;


    /**
     * Constant for the default highlight for unselected raw text.
     * <p>
     *  未选择原始文字的默认高亮的常数。
     * 
     */
    public final static InputMethodHighlight UNSELECTED_RAW_TEXT_HIGHLIGHT =
        new InputMethodHighlight(false, RAW_TEXT);

    /**
     * Constant for the default highlight for selected raw text.
     * <p>
     *  所选原始文本的默认高亮的常量。
     * 
     */
    public final static InputMethodHighlight SELECTED_RAW_TEXT_HIGHLIGHT =
        new InputMethodHighlight(true, RAW_TEXT);

    /**
     * Constant for the default highlight for unselected converted text.
     * <p>
     *  未选择的转换文本的默认高亮的常量。
     * 
     */
    public final static InputMethodHighlight UNSELECTED_CONVERTED_TEXT_HIGHLIGHT =
        new InputMethodHighlight(false, CONVERTED_TEXT);

    /**
     * Constant for the default highlight for selected converted text.
     * <p>
     *  所选转换文本的默认高亮的常数。
     * 
     */
    public final static InputMethodHighlight SELECTED_CONVERTED_TEXT_HIGHLIGHT =
        new InputMethodHighlight(true, CONVERTED_TEXT);


    /**
     * Constructs an input method highlight record.
     * The variation is set to 0, the style to null.
     * <p>
     * 构造输入法高亮记录。变体设置为0,样式为null。
     * 
     * 
     * @param selected Whether the text range is selected
     * @param state The conversion state for the text range - RAW_TEXT or CONVERTED_TEXT
     * @see InputMethodHighlight#RAW_TEXT
     * @see InputMethodHighlight#CONVERTED_TEXT
     * @exception IllegalArgumentException if a state other than RAW_TEXT or CONVERTED_TEXT is given
     */
    public InputMethodHighlight(boolean selected, int state) {
        this(selected, state, 0, null);
    }

    /**
     * Constructs an input method highlight record.
     * The style is set to null.
     * <p>
     *  构造输入法高亮记录。样式设置为null。
     * 
     * 
     * @param selected Whether the text range is selected
     * @param state The conversion state for the text range - RAW_TEXT or CONVERTED_TEXT
     * @param variation The style variation for the text range
     * @see InputMethodHighlight#RAW_TEXT
     * @see InputMethodHighlight#CONVERTED_TEXT
     * @exception IllegalArgumentException if a state other than RAW_TEXT or CONVERTED_TEXT is given
     */
    public InputMethodHighlight(boolean selected, int state, int variation) {
        this(selected, state, variation, null);
    }

    /**
     * Constructs an input method highlight record.
     * The style attributes map provided must be unmodifiable.
     * <p>
     *  构造输入法高亮记录。提供的样式属性映射必须是不可修改的。
     * 
     * 
     * @param selected whether the text range is selected
     * @param state the conversion state for the text range - RAW_TEXT or CONVERTED_TEXT
     * @param variation the variation for the text range
     * @param style the rendering style attributes for the text range, or null
     * @see InputMethodHighlight#RAW_TEXT
     * @see InputMethodHighlight#CONVERTED_TEXT
     * @exception IllegalArgumentException if a state other than RAW_TEXT or CONVERTED_TEXT is given
     * @since 1.3
     */
    public InputMethodHighlight(boolean selected, int state, int variation,
                                Map<TextAttribute,?> style)
    {
        this.selected = selected;
        if (!(state == RAW_TEXT || state == CONVERTED_TEXT)) {
            throw new IllegalArgumentException("unknown input method highlight state");
        }
        this.state = state;
        this.variation = variation;
        this.style = style;
    }

    /**
     * Returns whether the text range is selected.
     * <p>
     *  返回是否选择文本范围。
     * 
     */
    public boolean isSelected() {
        return selected;
    }

    /**
     * Returns the conversion state of the text range.
     * <p>
     *  返回文本范围的转换状态。
     * 
     * 
     * @return The conversion state for the text range - RAW_TEXT or CONVERTED_TEXT.
     * @see InputMethodHighlight#RAW_TEXT
     * @see InputMethodHighlight#CONVERTED_TEXT
     */
    public int getState() {
        return state;
    }

    /**
     * Returns the variation of the text range.
     * <p>
     *  返回文本范围的变化。
     * 
     */
    public int getVariation() {
        return variation;
    }

    /**
     * Returns the rendering style attributes for the text range, or null.
     * <p>
     *  返回文本范围的渲染样式属性,或null。
     * 
     * @since 1.3
     */
    public Map<TextAttribute,?> getStyle() {
        return style;
    }

    private boolean selected;
    private int state;
    private int variation;
    private Map<TextAttribute, ?> style;

};
