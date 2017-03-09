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
package javax.swing.text.html;

import sun.swing.SwingUtilities2;
import java.util.*;
import java.awt.*;
import java.io.*;
import java.net.*;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.UIManager;
import javax.swing.border.*;
import javax.swing.event.ChangeListener;
import javax.swing.text.*;

/**
 * Support for defining the visual characteristics of
 * HTML views being rendered.  The StyleSheet is used to
 * translate the HTML model into visual characteristics.
 * This enables views to be customized by a look-and-feel,
 * multiple views over the same model can be rendered
 * differently, etc.  This can be thought of as a CSS
 * rule repository.  The key for CSS attributes is an
 * object of type CSS.Attribute.  The type of the value
 * is up to the StyleSheet implementation, but the
 * <code>toString</code> method is required
 * to return a string representation of CSS value.
 * <p>
 * The primary entry point for HTML View implementations
 * to get their attributes is the
 * {@link #getViewAttributes getViewAttributes}
 * method.  This should be implemented to establish the
 * desired policy used to associate attributes with the view.
 * Each HTMLEditorKit (i.e. and therefore each associated
 * JEditorPane) can have its own StyleSheet, but by default one
 * sheet will be shared by all of the HTMLEditorKit instances.
 * HTMLDocument instance can also have a StyleSheet, which
 * holds the document-specific CSS specifications.
 * <p>
 * In order for Views to store less state and therefore be
 * more lightweight, the StyleSheet can act as a factory for
 * painters that handle some of the rendering tasks.  This allows
 * implementations to determine what they want to cache
 * and have the sharing potentially at the level that a
 * selector is common to multiple views.  Since the StyleSheet
 * may be used by views over multiple documents and typically
 * the HTML attributes don't effect the selector being used,
 * the potential for sharing is significant.
 * <p>
 * The rules are stored as named styles, and other information
 * is stored to translate the context of an element to a
 * rule quickly.  The following code fragment will display
 * the named styles, and therefore the CSS rules contained.
 * <pre><code>
 * &nbsp;
 * &nbsp; import java.util.*;
 * &nbsp; import javax.swing.text.*;
 * &nbsp; import javax.swing.text.html.*;
 * &nbsp;
 * &nbsp; public class ShowStyles {
 * &nbsp;
 * &nbsp;     public static void main(String[] args) {
 * &nbsp;       HTMLEditorKit kit = new HTMLEditorKit();
 * &nbsp;       HTMLDocument doc = (HTMLDocument) kit.createDefaultDocument();
 * &nbsp;       StyleSheet styles = doc.getStyleSheet();
 * &nbsp;
 * &nbsp;       Enumeration rules = styles.getStyleNames();
 * &nbsp;       while (rules.hasMoreElements()) {
 * &nbsp;           String name = (String) rules.nextElement();
 * &nbsp;           Style rule = styles.getStyle(name);
 * &nbsp;           System.out.println(rule.toString());
 * &nbsp;       }
 * &nbsp;       System.exit(0);
 * &nbsp;     }
 * &nbsp; }
 * &nbsp;
 * </code></pre>
 * <p>
 * The semantics for when a CSS style should overide visual attributes
 * defined by an element are not well defined. For example, the html
 * <code>&lt;body bgcolor=red&gt;</code> makes the body have a red
 * background. But if the html file also contains the CSS rule
 * <code>body { background: blue }</code> it becomes less clear as to
 * what color the background of the body should be. The current
 * implementation gives visual attributes defined in the element the
 * highest precedence, that is they are always checked before any styles.
 * Therefore, in the previous example the background would have a
 * red color as the body element defines the background color to be red.
 * <p>
 * As already mentioned this supports CSS. We don't support the full CSS
 * spec. Refer to the javadoc of the CSS class to see what properties
 * we support. The two major CSS parsing related
 * concepts we do not currently
 * support are pseudo selectors, such as <code>A:link { color: red }</code>,
 * and the <code>important</code> modifier.
 * <p>
 * <font color="red">Note: This implementation is currently
 * incomplete.  It can be replaced with alternative implementations
 * that are complete.  Future versions of this class will provide
 * better CSS support.</font>
 *
 * <p>
 *  支持定义正在呈现的HTML视图的视觉特性。 StyleSheet用于将HTML模型转换为视觉特性。这使得可以通过外观来定制视图,可以不同地呈现相同模型上的多个视图等。这可以被认为是CSS规则存储库。
 *  CSS属性的键是CSS.Attribute类型的对象。值的类型取决于StyleSheet实现,但是需要<code> toString </code>方法来返回CSS值的字符串表示。
 * <p>
 *  HTML视图实现获取其属性的主要入口点是{@link #getViewAttributes getViewAttributes}方法。这应该被实现以建立用于将属性与视图相关联的期望的策略。
 * 每个HTMLEditorKit(即每个相关联的JEditorPane)可以有自己的StyleSheet,但是默认情况下,所有HTMLEditorKit实例都将共享一个工作表。
 *  HTMLDocument实例还可以有一个StyleSheet,它保存特定于文档的CSS规范。
 * <p>
 * 为了使View存储更少的状态,因此更轻量级,StyleSheet可以作为处理一些渲染任务的画家的工厂。这允许实现确定他们想要缓存什么,并且共享可能处于选择器对于多个视图共同的水平。
 * 由于StyleSheet可以被多个文档的视图使用,并且通常HTML属性不影响正在使用的选择器,因此共享的可能性很大。
 * <p>
 *  规则被存储为命名样式,并且存储其他信息以快速地将元素的上下文翻译为规则。以下代码片段将显示已命名的样式,因此包含的CSS规则。
 *  <pre> <code>&nbsp; &nbsp; import java.util。*; &nbsp; import javax.swing.text。
 * *; &nbsp; import javax.swing.text.html。
 * *; &nbsp; &nbsp; public class ShowStyles {&nbsp; &nbsp; public static void main(String [] args){&nbsp; HTMLEditorKit kit = new HTMLEditorKit(); &nbsp; HTMLDocument doc =(HTMLDocument)kit.createDefaultDocument(); &nbsp; StyleSheet styles = doc.getStyleSheet(); &nbsp; &nbsp;枚举规则= styles.getStyleNames(); &nbsp; while(rules.hasMoreElements()){&nbsp; String name =(String)rules.nextElement(); &nbsp; style rule = styles.getStyle(name); &nbsp; System.out.println(rule.toString()); &nbsp; }
 * &nbsp; System.exit(0); &nbsp; }&nbsp; }&nbsp; </code> </pre>。
 * *; &nbsp; import javax.swing.text.html。
 * <p>
 * 当CSS样式应该覆盖由元素定义的视觉属性时,语义不是很好定义。例如,html <code>&lt; body bgcolor = red&gt; </code>使得正文具有红色背景。
 * 但是如果html文件还包含CSS规则<code> body {background：blue} </code>,它对于body的背景应该是什么颜色变得不太清楚。
 * 当前实现提供了在元素中定义的视觉属性的最高优先级,即它们总是在任何样式之前被检查。因此,在前面的示例中,背景将具有红色,因为主体元素将背景颜色定义为红色。
 * <p>
 *  如前所述,这支持CSS。我们不支持完整的CSS规范。请参阅CSS类的javadoc以查看我们支持的属性。
 * 我们目前不支持的两个主要的CSS解析相关概念是伪选择器,例如<code> A：link {color：red} </code>和<code> important </code>修饰符。
 * <p>
 *  <font color ="red">注意：此实现目前不完整。它可以替换为完成的替代实现。此类的未来版本将提供更好的CSS支持。</font>
 * 
 * 
 * @author  Timothy Prinzing
 * @author  Sunita Mani
 * @author  Sara Swanson
 * @author  Jill Nakata
 */
public class StyleSheet extends StyleContext {
    // As the javadoc states, this class maintains a mapping between
    // a CSS selector (such as p.bar) and a Style.
    // This consists of a number of parts:
    // . Each selector is broken down into its constituent simple selectors,
    //   and stored in an inverted graph, for example:
    //     p { color: red } ol p { font-size: 10pt } ul p { font-size: 12pt }
    //   results in the graph:
    //          root
    //           |
    //           p
    //          / \
    //         ol ul
    //   each node (an instance of SelectorMapping) has an associated
    //   specificity and potentially a Style.
    // . Every rule that is asked for (either by way of getRule(String) or
    //   getRule(HTML.Tag, Element)) results in a unique instance of
    //   ResolvedStyle. ResolvedStyles contain the AttributeSets from the
    //   SelectorMapping.
    // . When a new rule is created it is inserted into the graph, and
    //   the AttributeSets of each ResolvedStyles are updated appropriately.
    // . This class creates special AttributeSets, LargeConversionSet and
    //   SmallConversionSet, that maintain a mapping between StyleConstants
    //   and CSS so that developers that wish to use the StyleConstants
    //   methods can do so.
    // . When one of the AttributeSets is mutated by way of a
    //   StyleConstants key, all the associated CSS keys are removed. This is
    //   done so that the two representations don't get out of sync. For
    //   example, if the developer adds StyleConsants.BOLD, FALSE to an
    //   AttributeSet that contains HTML.Tag.B, the HTML.Tag.B entry will
    //   be removed.

    /**
     * Construct a StyleSheet
     * <p>
     *  构造StyleSheet
     * 
     */
    public StyleSheet() {
        super();
        selectorMapping = new SelectorMapping(0);
        resolvedStyles = new Hashtable<String, ResolvedStyle>();
        if (css == null) {
            css = new CSS();
        }
    }

    /**
     * Fetches the style to use to render the given type
     * of HTML tag.  The element given is representing
     * the tag and can be used to determine the nesting
     * for situations where the attributes will differ
     * if nesting inside of elements.
     *
     * <p>
     *  获取用于呈现给定类型的HTML标记的样式。给定的元素表示标记,并且可以用于确定在嵌套在元素内部时属性将不同的情况下的嵌套。
     * 
     * 
     * @param t the type to translate to visual attributes
     * @param e the element representing the tag; the element
     *  can be used to determine the nesting for situations where
     *  the attributes will differ if nested inside of other
     *  elements
     * @return the set of CSS attributes to use to render
     *  the tag
     */
    public Style getRule(HTML.Tag t, Element e) {
        SearchBuffer sb = SearchBuffer.obtainSearchBuffer();

        try {
            // Build an array of all the parent elements.
            Vector<Element> searchContext = sb.getVector();

            for (Element p = e; p != null; p = p.getParentElement()) {
                searchContext.addElement(p);
            }

            // Build a fully qualified selector.
            int              n = searchContext.size();
            StringBuffer     cacheLookup = sb.getStringBuffer();
            AttributeSet     attr;
            String           eName;
            Object           name;

            // >= 1 as the HTML.Tag for the 0th element is passed in.
            for (int counter = n - 1; counter >= 1; counter--) {
                e = searchContext.elementAt(counter);
                attr = e.getAttributes();
                name = attr.getAttribute(StyleConstants.NameAttribute);
                eName = name.toString();
                cacheLookup.append(eName);
                if (attr != null) {
                    if (attr.isDefined(HTML.Attribute.ID)) {
                        cacheLookup.append('#');
                        cacheLookup.append(attr.getAttribute
                                           (HTML.Attribute.ID));
                    }
                    else if (attr.isDefined(HTML.Attribute.CLASS)) {
                        cacheLookup.append('.');
                        cacheLookup.append(attr.getAttribute
                                           (HTML.Attribute.CLASS));
                    }
                }
                cacheLookup.append(' ');
            }
            cacheLookup.append(t.toString());
            e = searchContext.elementAt(0);
            attr = e.getAttributes();
            if (e.isLeaf()) {
                // For leafs, we use the second tier attributes.
                Object testAttr = attr.getAttribute(t);
                if (testAttr instanceof AttributeSet) {
                    attr = (AttributeSet)testAttr;
                }
                else {
                    attr = null;
                }
            }
            if (attr != null) {
                if (attr.isDefined(HTML.Attribute.ID)) {
                    cacheLookup.append('#');
                    cacheLookup.append(attr.getAttribute(HTML.Attribute.ID));
                }
                else if (attr.isDefined(HTML.Attribute.CLASS)) {
                    cacheLookup.append('.');
                    cacheLookup.append(attr.getAttribute
                                       (HTML.Attribute.CLASS));
                }
            }

            Style style = getResolvedStyle(cacheLookup.toString(),
                                           searchContext, t);
            return style;
        }
        finally {
            SearchBuffer.releaseSearchBuffer(sb);
        }
    }

    /**
     * Fetches the rule that best matches the selector given
     * in string form. Where <code>selector</code> is a space separated
     * String of the element names. For example, <code>selector</code>
     * might be 'html body tr td''<p>
     * The attributes of the returned Style will change
     * as rules are added and removed. That is if you to ask for a rule
     * with a selector "table p" and a new rule was added with a selector
     * of "p" the returned Style would include the new attributes from
     * the rule "p".
     * <p>
     * 获取与字符串形式的选择器最匹配的规则。其中<code> selector </code>是元素名称的空格分隔的String。
     * 例如,<code> selector </code>可能是'html body tr td''<p>返回的样式的属性将随着添加和删除规则而改变。
     * 也就是说,如果您要求一个带有选择器"table p"的规则,并且新规则添加了一个选择器"p",则返回的样式将包括规则"p"的新属性。
     * 
     */
    public Style getRule(String selector) {
        selector = cleanSelectorString(selector);
        if (selector != null) {
            Style style = getResolvedStyle(selector);
            return style;
        }
        return null;
    }

    /**
     * Adds a set of rules to the sheet.  The rules are expected to
     * be in valid CSS format.  Typically this would be called as
     * a result of parsing a &lt;style&gt; tag.
     * <p>
     *  向工作表添加一组规则。规则应为有效的CSS格式。通常,这将被作为解析&lt; style&gt;标签。
     * 
     */
    public void addRule(String rule) {
        if (rule != null) {
            //tweaks to control display properties
            //see BasicEditorPaneUI
            final String baseUnitsDisable = "BASE_SIZE_DISABLE";
            final String baseUnits = "BASE_SIZE ";
            final String w3cLengthUnitsEnable = "W3C_LENGTH_UNITS_ENABLE";
            final String w3cLengthUnitsDisable = "W3C_LENGTH_UNITS_DISABLE";
            if (rule == baseUnitsDisable) {
                sizeMap = sizeMapDefault;
            } else if (rule.startsWith(baseUnits)) {
                rebaseSizeMap(Integer.
                              parseInt(rule.substring(baseUnits.length())));
            } else if (rule == w3cLengthUnitsEnable) {
                w3cLengthUnits = true;
            } else if (rule == w3cLengthUnitsDisable) {
                w3cLengthUnits = false;
            } else {
                CssParser parser = new CssParser();
                try {
                    parser.parse(getBase(), new StringReader(rule), false, false);
                } catch (IOException ioe) { }
            }
        }
    }

    /**
     * Translates a CSS declaration to an AttributeSet that represents
     * the CSS declaration.  Typically this would be called as a
     * result of encountering an HTML style attribute.
     * <p>
     *  将CSS声明转换为表示CSS声明的AttributeSet。通常,这将作为遇到HTML样式属性的结果来调用。
     * 
     */
    public AttributeSet getDeclaration(String decl) {
        if (decl == null) {
            return SimpleAttributeSet.EMPTY;
        }
        CssParser parser = new CssParser();
        return parser.parseDeclaration(decl);
    }

    /**
     * Loads a set of rules that have been specified in terms of
     * CSS1 grammar.  If there are collisions with existing rules,
     * the newly specified rule will win.
     *
     * <p>
     *  加载根据CSS1语法指定的一组规则。如果与现有规则存在冲突,则新指定的规则将获胜。
     * 
     * 
     * @param in the stream to read the CSS grammar from
     * @param ref the reference URL.  This value represents the
     *  location of the stream and may be null.  All relative
     *  URLs specified in the stream will be based upon this
     *  parameter.
     */
    public void loadRules(Reader in, URL ref) throws IOException {
        CssParser parser = new CssParser();
        parser.parse(ref, in, false, false);
    }

    /**
     * Fetches a set of attributes to use in the view for
     * displaying.  This is basically a set of attributes that
     * can be used for View.getAttributes.
     * <p>
     *  获取要在视图中使用以显示的一组属性。这基本上是一组可以用于View.getAttributes的属性。
     * 
     */
    public AttributeSet getViewAttributes(View v) {
        return new ViewAttributeSet(v);
    }

    /**
     * Removes a named style previously added to the document.
     *
     * <p>
     *  删除先前添加到文档中的命名样式。
     * 
     * 
     * @param nm  the name of the style to remove
     */
    public void removeStyle(String nm) {
        Style       aStyle = getStyle(nm);

        if (aStyle != null) {
            String selector = cleanSelectorString(nm);
            String[] selectors = getSimpleSelectors(selector);
            synchronized(this) {
                SelectorMapping mapping = getRootSelectorMapping();
                for (int i = selectors.length - 1; i >= 0; i--) {
                    mapping = mapping.getChildSelectorMapping(selectors[i],
                                                              true);
                }
                Style rule = mapping.getStyle();
                if (rule != null) {
                    mapping.setStyle(null);
                    if (resolvedStyles.size() > 0) {
                        Enumeration<ResolvedStyle> values = resolvedStyles.elements();
                        while (values.hasMoreElements()) {
                            ResolvedStyle style = values.nextElement();
                            style.removeStyle(rule);
                        }
                    }
                }
            }
        }
        super.removeStyle(nm);
    }

    /**
     * Adds the rules from the StyleSheet <code>ss</code> to those of
     * the receiver. <code>ss's</code> rules will override the rules of
     * any previously added style sheets. An added StyleSheet will never
     * override the rules of the receiving style sheet.
     *
     * <p>
     *  将StyleSheet <code> ss </code>中的规则添加到接收方的规则。 <code> ss </code>规则将覆盖之前添加的任何样式表的规则。
     * 添加的StyleSheet将永远不会覆盖接收样式表的规则。
     * 
     * 
     * @since 1.3
     */
    public void addStyleSheet(StyleSheet ss) {
        synchronized(this) {
            if (linkedStyleSheets == null) {
                linkedStyleSheets = new Vector<StyleSheet>();
            }
            if (!linkedStyleSheets.contains(ss)) {
                int index = 0;
                if (ss instanceof javax.swing.plaf.UIResource
                    && linkedStyleSheets.size() > 1) {
                    index = linkedStyleSheets.size() - 1;
                }
                linkedStyleSheets.insertElementAt(ss, index);
                linkStyleSheetAt(ss, index);
            }
        }
    }

    /**
     * Removes the StyleSheet <code>ss</code> from those of the receiver.
     *
     * <p>
     *  删除接收器的StyleSheet <code> ss </code>。
     * 
     * 
     * @since 1.3
     */
    public void removeStyleSheet(StyleSheet ss) {
        synchronized(this) {
            if (linkedStyleSheets != null) {
                int index = linkedStyleSheets.indexOf(ss);
                if (index != -1) {
                    linkedStyleSheets.removeElementAt(index);
                    unlinkStyleSheet(ss, index);
                    if (index == 0 && linkedStyleSheets.size() == 0) {
                        linkedStyleSheets = null;
                    }
                }
            }
        }
    }

    //
    // The following is used to import style sheets.
    //

    /**
     * Returns an array of the linked StyleSheets. Will return null
     * if there are no linked StyleSheets.
     *
     * <p>
     * 返回链接的StyleSheets的数组。如果没有链接的StyleSheets将返回null。
     * 
     * 
     * @since 1.3
     */
    public StyleSheet[] getStyleSheets() {
        StyleSheet[] retValue;

        synchronized(this) {
            if (linkedStyleSheets != null) {
                retValue = new StyleSheet[linkedStyleSheets.size()];
                linkedStyleSheets.copyInto(retValue);
            }
            else {
                retValue = null;
            }
        }
        return retValue;
    }

    /**
     * Imports a style sheet from <code>url</code>. The resulting rules
     * are directly added to the receiver. If you do not want the rules
     * to become part of the receiver, create a new StyleSheet and use
     * addStyleSheet to link it in.
     *
     * <p>
     *  从<code> url </code>导入样式表。生成的规则直接添加到接收器。如果您不希望规则成为接收者的一部分,请创建一个新的StyleSheet并使用addStyleSheet链接它。
     * 
     * 
     * @since 1.3
     */
    public void importStyleSheet(URL url) {
        try {
            InputStream is;

            is = url.openStream();
            Reader r = new BufferedReader(new InputStreamReader(is));
            CssParser parser = new CssParser();
            parser.parse(url, r, false, true);
            r.close();
            is.close();
        } catch (Throwable e) {
            // on error we simply have no styles... the html
            // will look mighty wrong but still function.
        }
    }

    /**
     * Sets the base. All import statements that are relative, will be
     * relative to <code>base</code>.
     *
     * <p>
     *  设置基数。所有相对的import语句都将相对于<code> base </code>。
     * 
     * 
     * @since 1.3
     */
    public void setBase(URL base) {
        this.base = base;
    }

    /**
     * Returns the base.
     *
     * <p>
     *  返回基数。
     * 
     * 
     * @since 1.3
     */
    public URL getBase() {
        return base;
    }

    /**
     * Adds a CSS attribute to the given set.
     *
     * <p>
     *  将CSS属性添加到给定集合。
     * 
     * 
     * @since 1.3
     */
    public void addCSSAttribute(MutableAttributeSet attr, CSS.Attribute key,
                                String value) {
        css.addInternalCSSValue(attr, key, value);
    }

    /**
     * Adds a CSS attribute to the given set.
     *
     * <p>
     *  将CSS属性添加到给定集合。
     * 
     * 
     * @since 1.3
     */
    public boolean addCSSAttributeFromHTML(MutableAttributeSet attr,
                                           CSS.Attribute key, String value) {
        Object iValue = css.getCssValue(key, value);
        if (iValue != null) {
            attr.addAttribute(key, iValue);
            return true;
        }
        return false;
    }

    // ---- Conversion functionality ---------------------------------

    /**
     * Converts a set of HTML attributes to an equivalent
     * set of CSS attributes.
     *
     * <p>
     *  将一组HTML属性转换为等效的CSS属性集。
     * 
     * 
     * @param htmlAttrSet AttributeSet containing the HTML attributes.
     */
    public AttributeSet translateHTMLToCSS(AttributeSet htmlAttrSet) {
        AttributeSet cssAttrSet = css.translateHTMLToCSS(htmlAttrSet);

        MutableAttributeSet cssStyleSet = addStyle(null, null);
        cssStyleSet.addAttributes(cssAttrSet);

        return cssStyleSet;
    }

    /**
     * Adds an attribute to the given set, and returns
     * the new representative set.  This is reimplemented to
     * convert StyleConstant attributes to CSS prior to forwarding
     * to the superclass behavior.  The StyleConstants attribute
     * has no corresponding CSS entry, the StyleConstants attribute
     * is stored (but will likely be unused).
     *
     * <p>
     *  向给定集合添加属性,并返回新的代表集。这将重新实现以将StyleConstant属性转换为CSS,然后转发到超类行为。
     *  StyleConstants属性没有对应的CSS条目,StyleConstants属性存储(但可能未使用)。
     * 
     * 
     * @param old the old attribute set
     * @param key the non-null attribute key
     * @param value the attribute value
     * @return the updated attribute set
     * @see MutableAttributeSet#addAttribute
     */
    public AttributeSet addAttribute(AttributeSet old, Object key,
                                     Object value) {
        if (css == null) {
            // supers constructor will call this before returning,
            // and we need to make sure CSS is non null.
            css = new CSS();
        }
        if (key instanceof StyleConstants) {
            HTML.Tag tag = HTML.getTagForStyleConstantsKey(
                                (StyleConstants)key);

            if (tag != null && old.isDefined(tag)) {
                old = removeAttribute(old, tag);
            }

            Object cssValue = css.styleConstantsValueToCSSValue
                              ((StyleConstants)key, value);
            if (cssValue != null) {
                Object cssKey = css.styleConstantsKeyToCSSKey
                                    ((StyleConstants)key);
                if (cssKey != null) {
                    return super.addAttribute(old, cssKey, cssValue);
                }
            }
        }
        return super.addAttribute(old, key, value);
    }

    /**
     * Adds a set of attributes to the element.  If any of these attributes
     * are StyleConstants attributes, they will be converted to CSS prior
     * to forwarding to the superclass behavior.
     *
     * <p>
     *  向元素添加一组属性。如果这些属性中的任何一个属性为StyleConstants属性,那么在转发到超类行为之前,它们将被转换为CSS。
     * 
     * 
     * @param old the old attribute set
     * @param attr the attributes to add
     * @return the updated attribute set
     * @see MutableAttributeSet#addAttribute
     */
    public AttributeSet addAttributes(AttributeSet old, AttributeSet attr) {
        if (!(attr instanceof HTMLDocument.TaggedAttributeSet)) {
            old = removeHTMLTags(old, attr);
        }
        return super.addAttributes(old, convertAttributeSet(attr));
    }

    /**
     * Removes an attribute from the set.  If the attribute is a StyleConstants
     * attribute, the request will be converted to a CSS attribute prior to
     * forwarding to the superclass behavior.
     *
     * <p>
     *  从集合中删除属性。如果属性是StyleConstants属性,则在转发到超类行为之前,请求将转换为CSS属性。
     * 
     * 
     * @param old the old set of attributes
     * @param key the non-null attribute name
     * @return the updated attribute set
     * @see MutableAttributeSet#removeAttribute
     */
    public AttributeSet removeAttribute(AttributeSet old, Object key) {
        if (key instanceof StyleConstants) {
            HTML.Tag tag = HTML.getTagForStyleConstantsKey(
                                   (StyleConstants)key);
            if (tag != null) {
                old = super.removeAttribute(old, tag);
            }

            Object cssKey = css.styleConstantsKeyToCSSKey((StyleConstants)key);
            if (cssKey != null) {
                return super.removeAttribute(old, cssKey);
            }
        }
        return super.removeAttribute(old, key);
    }

    /**
     * Removes a set of attributes for the element.  If any of the attributes
     * is a StyleConstants attribute, the request will be converted to a CSS
     * attribute prior to forwarding to the superclass behavior.
     *
     * <p>
     *  删除元素的一组属性。如果任何属性是StyleConstants属性,则在转发到超类行为之前,请求将转换为CSS属性。
     * 
     * 
     * @param old the old attribute set
     * @param names the attribute names
     * @return the updated attribute set
     * @see MutableAttributeSet#removeAttributes
     */
    public AttributeSet removeAttributes(AttributeSet old, Enumeration<?> names) {
        // PENDING: Should really be doing something similar to
        // removeHTMLTags here, but it is rather expensive to have to
        // clone names
        return super.removeAttributes(old, names);
    }

    /**
     * Removes a set of attributes. If any of the attributes
     * is a StyleConstants attribute, the request will be converted to a CSS
     * attribute prior to forwarding to the superclass behavior.
     *
     * <p>
     * 删除一组属性。如果任何属性是StyleConstants属性,则在转发到超类行为之前,请求将转换为CSS属性。
     * 
     * 
     * @param old the old attribute set
     * @param attrs the attributes
     * @return the updated attribute set
     * @see MutableAttributeSet#removeAttributes
     */
    public AttributeSet removeAttributes(AttributeSet old, AttributeSet attrs) {
        if (old != attrs) {
            old = removeHTMLTags(old, attrs);
        }
        return super.removeAttributes(old, convertAttributeSet(attrs));
    }

    /**
     * Creates a compact set of attributes that might be shared.
     * This is a hook for subclasses that want to alter the
     * behavior of SmallAttributeSet.  This can be reimplemented
     * to return an AttributeSet that provides some sort of
     * attribute conversion.
     *
     * <p>
     *  创建可能共享的一组紧凑属性。这是一个钩子,想要改变SmallAttributeSet的行为的子类。这可以重新实现以返回一个提供某种属性转换的AttributeSet。
     * 
     * 
     * @param a The set of attributes to be represented in the
     *  the compact form.
     */
    protected SmallAttributeSet createSmallAttributeSet(AttributeSet a) {
        return new SmallConversionSet(a);
    }

    /**
     * Creates a large set of attributes that should trade off
     * space for time.  This set will not be shared.  This is
     * a hook for subclasses that want to alter the behavior
     * of the larger attribute storage format (which is
     * SimpleAttributeSet by default).   This can be reimplemented
     * to return a MutableAttributeSet that provides some sort of
     * attribute conversion.
     *
     * <p>
     *  创建一组大的属性,应该在时间上折衷空间。此设置不会共享。这是一个钩子,希望改变更大的属性存储格式(默认为SimpleAttributeSet)的行为。
     * 这可以重新实现,返回一个MutableAttributeSet,提供某种类型的属性转换。
     * 
     * 
     * @param a The set of attributes to be represented in the
     *  the larger form.
     */
    protected MutableAttributeSet createLargeAttributeSet(AttributeSet a) {
        return new LargeConversionSet(a);
    }

    /**
     * For any StyleConstants key in attr that has an associated HTML.Tag,
     * it is removed from old. The resulting AttributeSet is then returned.
     * <p>
     *  对于attr中具有关联的HTML.Tag的任何StyleConstants键,它将从旧的中删除。然后返回生成的AttributeSet。
     * 
     */
    private AttributeSet removeHTMLTags(AttributeSet old, AttributeSet attr) {
        if (!(attr instanceof LargeConversionSet) &&
            !(attr instanceof SmallConversionSet)) {
            Enumeration names = attr.getAttributeNames();

            while (names.hasMoreElements()) {
                Object key = names.nextElement();

                if (key instanceof StyleConstants) {
                    HTML.Tag tag = HTML.getTagForStyleConstantsKey(
                        (StyleConstants)key);

                    if (tag != null && old.isDefined(tag)) {
                        old = super.removeAttribute(old, tag);
                    }
                }
            }
        }
        return old;
    }

    /**
     * Converts a set of attributes (if necessary) so that
     * any attributes that were specified as StyleConstants
     * attributes and have a CSS mapping, will be converted
     * to CSS attributes.
     * <p>
     *  转换一组属性(如果需要),以便将指定为StyleConstants属性并具有CSS映射的任何属性转换为CSS属性。
     * 
     */
    AttributeSet convertAttributeSet(AttributeSet a) {
        if ((a instanceof LargeConversionSet) ||
            (a instanceof SmallConversionSet)) {
            // known to be converted.
            return a;
        }
        // in most cases, there are no StyleConstants attributes
        // so we iterate the collection of keys to avoid creating
        // a new set.
        Enumeration names = a.getAttributeNames();
        while (names.hasMoreElements()) {
            Object name = names.nextElement();
            if (name instanceof StyleConstants) {
                // we really need to do a conversion, iterate again
                // building a new set.
                MutableAttributeSet converted = new LargeConversionSet();
                Enumeration keys = a.getAttributeNames();
                while (keys.hasMoreElements()) {
                    Object key = keys.nextElement();
                    Object cssValue = null;
                    if (key instanceof StyleConstants) {
                        // convert the StyleConstants attribute if possible
                        Object cssKey = css.styleConstantsKeyToCSSKey
                                            ((StyleConstants)key);
                        if (cssKey != null) {
                            Object value = a.getAttribute(key);
                            cssValue = css.styleConstantsValueToCSSValue
                                           ((StyleConstants)key, value);
                            if (cssValue != null) {
                                converted.addAttribute(cssKey, cssValue);
                            }
                        }
                    }
                    if (cssValue == null) {
                        converted.addAttribute(key, a.getAttribute(key));
                    }
                }
                return converted;
            }
        }
        return a;
    }

    /**
     * Large set of attributes that does conversion of requests
     * for attributes of type StyleConstants.
     * <p>
     *  对属性类型为StyleConstants的请求进行转换的大量属性集。
     * 
     */
    class LargeConversionSet extends SimpleAttributeSet {

        /**
         * Creates a new attribute set based on a supplied set of attributes.
         *
         * <p>
         *  基于提供的一组属性创建新的属性集。
         * 
         * 
         * @param source the set of attributes
         */
        public LargeConversionSet(AttributeSet source) {
            super(source);
        }

        public LargeConversionSet() {
            super();
        }

        /**
         * Checks whether a given attribute is defined.
         *
         * <p>
         *  检查是否定义了给定属性。
         * 
         * 
         * @param key the attribute key
         * @return true if the attribute is defined
         * @see AttributeSet#isDefined
         */
        public boolean isDefined(Object key) {
            if (key instanceof StyleConstants) {
                Object cssKey = css.styleConstantsKeyToCSSKey
                                    ((StyleConstants)key);
                if (cssKey != null) {
                    return super.isDefined(cssKey);
                }
            }
            return super.isDefined(key);
        }

        /**
         * Gets the value of an attribute.
         *
         * <p>
         *  获取属性的值。
         * 
         * 
         * @param key the attribute name
         * @return the attribute value
         * @see AttributeSet#getAttribute
         */
        public Object getAttribute(Object key) {
            if (key instanceof StyleConstants) {
                Object cssKey = css.styleConstantsKeyToCSSKey
                                    ((StyleConstants)key);
                if (cssKey != null) {
                    Object value = super.getAttribute(cssKey);
                    if (value != null) {
                        return css.cssValueToStyleConstantsValue
                                           ((StyleConstants)key, value);
                    }
                }
            }
            return super.getAttribute(key);
        }
    }

    /**
     * Small set of attributes that does conversion of requests
     * for attributes of type StyleConstants.
     * <p>
     *  小属性集,用于转换StyleConstants类型的属性的请求。
     * 
     */
    class SmallConversionSet extends SmallAttributeSet {

        /**
         * Creates a new attribute set based on a supplied set of attributes.
         *
         * <p>
         * 基于提供的一组属性创建新的属性集。
         * 
         * 
         * @param attrs the set of attributes
         */
        public SmallConversionSet(AttributeSet attrs) {
            super(attrs);
        }

        /**
         * Checks whether a given attribute is defined.
         *
         * <p>
         *  检查是否定义了给定属性。
         * 
         * 
         * @param key the attribute key
         * @return true if the attribute is defined
         * @see AttributeSet#isDefined
         */
        public boolean isDefined(Object key) {
            if (key instanceof StyleConstants) {
                Object cssKey = css.styleConstantsKeyToCSSKey
                                    ((StyleConstants)key);
                if (cssKey != null) {
                    return super.isDefined(cssKey);
                }
            }
            return super.isDefined(key);
        }

        /**
         * Gets the value of an attribute.
         *
         * <p>
         *  获取属性的值。
         * 
         * 
         * @param key the attribute name
         * @return the attribute value
         * @see AttributeSet#getAttribute
         */
        public Object getAttribute(Object key) {
            if (key instanceof StyleConstants) {
                Object cssKey = css.styleConstantsKeyToCSSKey
                                    ((StyleConstants)key);
                if (cssKey != null) {
                    Object value = super.getAttribute(cssKey);
                    if (value != null) {
                        return css.cssValueToStyleConstantsValue
                                           ((StyleConstants)key, value);
                    }
                }
            }
            return super.getAttribute(key);
        }
    }

    // ---- Resource handling ----------------------------------------

    /**
     * Fetches the font to use for the given set of attributes.
     * <p>
     *  获取用于给定属性集的字体。
     * 
     */
    public Font getFont(AttributeSet a) {
        return css.getFont(this, a, 12, this);
    }

    /**
     * Takes a set of attributes and turn it into a foreground color
     * specification.  This might be used to specify things
     * like brighter, more hue, etc.
     *
     * <p>
     *  获取一组属性并将其转换为前景颜色规范。这可能用于指定更亮,更多的色调等。
     * 
     * 
     * @param a the set of attributes
     * @return the color
     */
    public Color getForeground(AttributeSet a) {
        Color c = css.getColor(a, CSS.Attribute.COLOR);
        if (c == null) {
            return Color.black;
        }
        return c;
    }

    /**
     * Takes a set of attributes and turn it into a background color
     * specification.  This might be used to specify things
     * like brighter, more hue, etc.
     *
     * <p>
     *  获取一组属性并将其转换为背景颜色规范。这可能用于指定更亮,更多的色调等。
     * 
     * 
     * @param a the set of attributes
     * @return the color
     */
    public Color getBackground(AttributeSet a) {
        return css.getColor(a, CSS.Attribute.BACKGROUND_COLOR);
    }

    /**
     * Fetches the box formatter to use for the given set
     * of CSS attributes.
     * <p>
     *  获取用于给定的CSS属性集的框格式化程序。
     * 
     */
    public BoxPainter getBoxPainter(AttributeSet a) {
        return new BoxPainter(a, css, this);
    }

    /**
     * Fetches the list formatter to use for the given set
     * of CSS attributes.
     * <p>
     *  获取用于给定的CSS属性集的列表格式化程序。
     * 
     */
    public ListPainter getListPainter(AttributeSet a) {
        return new ListPainter(a, this);
    }

    /**
     * Sets the base font size, with valid values between 1 and 7.
     * <p>
     *  设置基本字体大小,有效值介于1和7之间。
     * 
     */
    public void setBaseFontSize(int sz) {
        css.setBaseFontSize(sz);
    }

    /**
     * Sets the base font size from the passed in String. The string
     * can either identify a specific font size, with legal values between
     * 1 and 7, or identify a relative font size such as +1 or -2.
     * <p>
     *  从传入的String中设置基本字体大小。字符串可以标识特定字体大小,合法值介于1和7之间,或标识相对字体大小,例如+1或-2。
     * 
     */
    public void setBaseFontSize(String size) {
        css.setBaseFontSize(size);
    }

    public static int getIndexOfSize(float pt) {
        return CSS.getIndexOfSize(pt, sizeMapDefault);
    }

    /**
     * Returns the point size, given a size index.
     * <p>
     *  给定一个大小索引,返回点大小。
     * 
     */
    public float getPointSize(int index) {
        return css.getPointSize(index, this);
    }

    /**
     *  Given a string such as "+2", "-2", or "2",
     *  returns a point size value.
     * <p>
     *  给定诸如"+2","-2"或"2"的字符串,返回点大小值。
     * 
     */
    public float getPointSize(String size) {
        return css.getPointSize(size, this);
    }

    /**
     * Converts a color string such as "RED" or "#NNNNNN" to a Color.
     * Note: This will only convert the HTML3.2 color strings
     *       or a string of length 7;
     *       otherwise, it will return null.
     * <p>
     *  将颜色字符串(如"RED"或"#NNNNNN")转换为颜色。注意：这只会转换HTML3.2颜色字符串或长度为7的字符串;否则,它将返回null。
     * 
     */
    public Color stringToColor(String string) {
        return CSS.stringToColor(string);
    }

    /**
     * Returns the ImageIcon to draw in the background for
     * <code>attr</code>.
     * <p>
     *  返回ImageIcon以在<code> attr </code>的后台中绘制。
     * 
     */
    ImageIcon getBackgroundImage(AttributeSet attr) {
        Object value = attr.getAttribute(CSS.Attribute.BACKGROUND_IMAGE);

        if (value != null) {
            return ((CSS.BackgroundImage)value).getImage(getBase());
        }
        return null;
    }

    /**
     * Adds a rule into the StyleSheet.
     *
     * <p>
     *  在StyleSheet中添加一个规则。
     * 
     * 
     * @param selector the selector to use for the rule.
     *  This will be a set of simple selectors, and must
     *  be a length of 1 or greater.
     * @param declaration the set of CSS attributes that
     *  make up the rule.
     */
    void addRule(String[] selector, AttributeSet declaration,
                 boolean isLinked) {
        int n = selector.length;
        StringBuilder sb = new StringBuilder();
        sb.append(selector[0]);
        for (int counter = 1; counter < n; counter++) {
            sb.append(' ');
            sb.append(selector[counter]);
        }
        String selectorName = sb.toString();
        Style rule = getStyle(selectorName);
        if (rule == null) {
            // Notice how the rule is first created, and it not part of
            // the synchronized block. It is done like this as creating
            // a new rule will fire a ChangeEvent. We do not want to be
            // holding the lock when calling to other objects, it can
            // result in deadlock.
            Style altRule = addStyle(selectorName, null);
            synchronized(this) {
                SelectorMapping mapping = getRootSelectorMapping();
                for (int i = n - 1; i >= 0; i--) {
                    mapping = mapping.getChildSelectorMapping
                                      (selector[i], true);
                }
                rule = mapping.getStyle();
                if (rule == null) {
                    rule = altRule;
                    mapping.setStyle(rule);
                    refreshResolvedRules(selectorName, selector, rule,
                                         mapping.getSpecificity());
                }
            }
        }
        if (isLinked) {
            rule = getLinkedStyle(rule);
        }
        rule.addAttributes(declaration);
    }

    //
    // The following gaggle of methods is used in maintaining the rules from
    // the sheet.
    //

    /**
     * Updates the attributes of the rules to reference any related
     * rules in <code>ss</code>.
     * <p>
     *  更新规则的属性以引用<code> ss </code>中的任何相关规则。
     * 
     */
    private synchronized void linkStyleSheetAt(StyleSheet ss, int index) {
        if (resolvedStyles.size() > 0) {
            Enumeration<ResolvedStyle> values = resolvedStyles.elements();
            while (values.hasMoreElements()) {
                ResolvedStyle rule = values.nextElement();
                rule.insertExtendedStyleAt(ss.getRule(rule.getName()),
                                           index);
            }
        }
    }

    /**
     * Removes references to the rules in <code>ss</code>.
     * <code>index</code> gives the index the StyleSheet was at, that is
     * how many StyleSheets had been added before it.
     * <p>
     * 删除对<code> ss </code>中的规则的引用。 <code> index </code>给出StyleSheet所在的索引,即在它之前添加了多少StyleSheets。
     * 
     */
    private synchronized void unlinkStyleSheet(StyleSheet ss, int index) {
        if (resolvedStyles.size() > 0) {
            Enumeration<ResolvedStyle> values = resolvedStyles.elements();
            while (values.hasMoreElements()) {
                ResolvedStyle rule = values.nextElement();
                rule.removeExtendedStyleAt(index);
            }
        }
    }

    /**
     * Returns the simple selectors that comprise selector.
     * <p>
     *  返回包含selector的简单选择器。
     * 
     */
    /* protected */
    String[] getSimpleSelectors(String selector) {
        selector = cleanSelectorString(selector);
        SearchBuffer sb = SearchBuffer.obtainSearchBuffer();
        Vector<String> selectors = sb.getVector();
        int lastIndex = 0;
        int length = selector.length();
        while (lastIndex != -1) {
            int newIndex = selector.indexOf(' ', lastIndex);
            if (newIndex != -1) {
                selectors.addElement(selector.substring(lastIndex, newIndex));
                if (++newIndex == length) {
                    lastIndex = -1;
                }
                else {
                    lastIndex = newIndex;
                }
            }
            else {
                selectors.addElement(selector.substring(lastIndex));
                lastIndex = -1;
            }
        }
        String[] retValue = new String[selectors.size()];
        selectors.copyInto(retValue);
        SearchBuffer.releaseSearchBuffer(sb);
        return retValue;
    }

    /**
     * Returns a string that only has one space between simple selectors,
     * which may be the passed in String.
     * <p>
     *  返回在简单选择器之间只有一个空格的字符串,这可能是在String中传递的。
     * 
     */
    /*protected*/ String cleanSelectorString(String selector) {
        boolean lastWasSpace = true;
        for (int counter = 0, maxCounter = selector.length();
             counter < maxCounter; counter++) {
            switch(selector.charAt(counter)) {
            case ' ':
                if (lastWasSpace) {
                    return _cleanSelectorString(selector);
                }
                lastWasSpace = true;
                break;
            case '\n':
            case '\r':
            case '\t':
                return _cleanSelectorString(selector);
            default:
                lastWasSpace = false;
            }
        }
        if (lastWasSpace) {
            return _cleanSelectorString(selector);
        }
        // It was fine.
        return selector;
    }

    /**
     * Returns a new String that contains only one space between non
     * white space characters.
     * <p>
     *  boolean lastWasSpace = true; for(int counter = 0,maxCounter = selector.length(); counter <maxCounter; counter ++){switch(selector.charAt(counter)){case''：if(lastWasSpace){return _cleanSelectorString(selector); } lastWasSpace = true;打破; case'\ n'：case'\ r'：case'\ t'：return _cleanSelectorString(selector); default：lastWasSpace = false; }} if(lastWasSpace){return _cleanSelectorString(selector); } //很好。
     * 返回选择器; }}。
     * 
     *  / **返回一个新的字符串,它在非空格字符之间只包含一个空格。
     * 
     */
    private String _cleanSelectorString(String selector) {
        SearchBuffer sb = SearchBuffer.obtainSearchBuffer();
        StringBuffer buff = sb.getStringBuffer();
        boolean lastWasSpace = true;
        int lastIndex = 0;
        char[] chars = selector.toCharArray();
        int numChars = chars.length;
        String retValue = null;
        try {
            for (int counter = 0; counter < numChars; counter++) {
                switch(chars[counter]) {
                case ' ':
                    if (!lastWasSpace) {
                        lastWasSpace = true;
                        if (lastIndex < counter) {
                            buff.append(chars, lastIndex,
                                        1 + counter - lastIndex);
                        }
                    }
                    lastIndex = counter + 1;
                    break;
                case '\n':
                case '\r':
                case '\t':
                    if (!lastWasSpace) {
                        lastWasSpace = true;
                        if (lastIndex < counter) {
                            buff.append(chars, lastIndex,
                                        counter - lastIndex);
                            buff.append(' ');
                        }
                    }
                    lastIndex = counter + 1;
                    break;
                default:
                    lastWasSpace = false;
                    break;
                }
            }
            if (lastWasSpace && buff.length() > 0) {
                // Remove last space.
                buff.setLength(buff.length() - 1);
            }
            else if (lastIndex < numChars) {
                buff.append(chars, lastIndex, numChars - lastIndex);
            }
            retValue = buff.toString();
        }
        finally {
            SearchBuffer.releaseSearchBuffer(sb);
        }
        return retValue;
    }

    /**
     * Returns the root selector mapping that all selectors are relative
     * to. This is an inverted graph of the selectors.
     * <p>
     *  返回所有选择器相对的根选择器映射。这是选择器的反转图。
     * 
     */
    private SelectorMapping getRootSelectorMapping() {
        return selectorMapping;
    }

    /**
     * Returns the specificity of the passed in String. It assumes the
     * passed in string doesn't contain junk, that is each selector is
     * separated by a space and each selector at most contains one . or one
     * #. A simple selector has a weight of 1, an id selector has a weight
     * of 100, and a class selector has a weight of 10000.
     * <p>
     *  返回在String中传递的特殊性。它假定传入的字符串不包含垃圾,即每个选择器由一个空格分隔,每个选择器最多包含一个。或一个#。
     * 简单选择器具有1的权重,id选择器具有100的权重,并且类选择器具有10000的权重。
     * 
     */
    /*protected*/ static int getSpecificity(String selector) {
        int specificity = 0;
        boolean lastWasSpace = true;

        for (int counter = 0, maxCounter = selector.length();
             counter < maxCounter; counter++) {
            switch(selector.charAt(counter)) {
            case '.':
                specificity += 100;
                break;
            case '#':
                specificity += 10000;
                break;
            case ' ':
                lastWasSpace = true;
                break;
            default:
                if (lastWasSpace) {
                    lastWasSpace = false;
                    specificity += 1;
                }
            }
        }
        return specificity;
    }

    /**
     * Returns the style that linked attributes should be added to. This
     * will create the style if necessary.
     * <p>
     *  int specificity = 0; boolean lastWasSpace = true;
     * 
     * for(int counter = 0,maxCounter = selector.length(); counter <maxCounter; counter ++){switch(selector.charAt(counter)){case''：specificity + = 100;打破; case'#'：specificity + = 10000;打破; case''：lastWasSpace = true;打破;默认值：if(lastWasSpace){lastWasSpace = false;特异性+ = 1; }}} return specificity; }}。
     * 
     *  / **返回链接属性应添加到的样式。如果需要,这将创建样式。
     * 
     */
    private Style getLinkedStyle(Style localStyle) {
        // NOTE: This is not synchronized, and the caller of this does
        // not synchronize. There is the chance for one of the callers to
        // overwrite the existing resolved parent, but it is quite rare.
        // The reason this is left like this is because setResolveParent
        // will fire a ChangeEvent. It is really, REALLY bad for us to
        // hold a lock when calling outside of us, it may cause a deadlock.
        Style retStyle = (Style)localStyle.getResolveParent();
        if (retStyle == null) {
            retStyle = addStyle(null, null);
            localStyle.setResolveParent(retStyle);
        }
        return retStyle;
    }

    /**
     * Returns the resolved style for <code>selector</code>. This will
     * create the resolved style, if necessary.
     * <p>
     *  返回<code> selector </code>的解析样式。如果需要,这将创建已解决的样式。
     * 
     */
    private synchronized Style getResolvedStyle(String selector,
                                                Vector elements,
                                                HTML.Tag t) {
        Style retStyle = resolvedStyles.get(selector);
        if (retStyle == null) {
            retStyle = createResolvedStyle(selector, elements, t);
        }
        return retStyle;
    }

    /**
     * Returns the resolved style for <code>selector</code>. This will
     * create the resolved style, if necessary.
     * <p>
     *  返回<code> selector </code>的解析样式。如果需要,这将创建已解决的样式。
     * 
     */
    private synchronized Style getResolvedStyle(String selector) {
        Style retStyle = resolvedStyles.get(selector);
        if (retStyle == null) {
            retStyle = createResolvedStyle(selector);
        }
        return retStyle;
    }

    /**
     * Adds <code>mapping</code> to <code>elements</code>. It is added
     * such that <code>elements</code> will remain ordered by
     * specificity.
     * <p>
     *  将<code>映射</code>添加到<code>元素</code>。添加它使得<code>元素</code>将通过特异性保持有序。
     * 
     */
    private void addSortedStyle(SelectorMapping mapping, Vector<SelectorMapping> elements) {
        int       size = elements.size();

        if (size > 0) {
            int     specificity = mapping.getSpecificity();

            for (int counter = 0; counter < size; counter++) {
                if (specificity >= elements.elementAt(counter).getSpecificity()) {
                    elements.insertElementAt(mapping, counter);
                    return;
                }
            }
        }
        elements.addElement(mapping);
    }

    /**
     * Adds <code>parentMapping</code> to <code>styles</code>, and
     * recursively calls this method if <code>parentMapping</code> has
     * any child mappings for any of the Elements in <code>elements</code>.
     * <p>
     *  将<code> parentMapping </code>添加到<code> styles </code>,如果<code> parentMapping </code>对<code>元素</code>
     * 中的任何元素有任何子映射,则递归调用此方法>。
     * 
     */
    private synchronized void getStyles(SelectorMapping parentMapping,
                           Vector<SelectorMapping> styles,
                           String[] tags, String[] ids, String[] classes,
                           int index, int numElements,
                           Hashtable<SelectorMapping, SelectorMapping> alreadyChecked) {
        // Avoid desending the same mapping twice.
        if (alreadyChecked.contains(parentMapping)) {
            return;
        }
        alreadyChecked.put(parentMapping, parentMapping);
        Style style = parentMapping.getStyle();
        if (style != null) {
            addSortedStyle(parentMapping, styles);
        }
        for (int counter = index; counter < numElements; counter++) {
            String tagString = tags[counter];
            if (tagString != null) {
                SelectorMapping childMapping = parentMapping.
                                getChildSelectorMapping(tagString, false);
                if (childMapping != null) {
                    getStyles(childMapping, styles, tags, ids, classes,
                              counter + 1, numElements, alreadyChecked);
                }
                if (classes[counter] != null) {
                    String className = classes[counter];
                    childMapping = parentMapping.getChildSelectorMapping(
                                         tagString + "." + className, false);
                    if (childMapping != null) {
                        getStyles(childMapping, styles, tags, ids, classes,
                                  counter + 1, numElements, alreadyChecked);
                    }
                    childMapping = parentMapping.getChildSelectorMapping(
                                         "." + className, false);
                    if (childMapping != null) {
                        getStyles(childMapping, styles, tags, ids, classes,
                                  counter + 1, numElements, alreadyChecked);
                    }
                }
                if (ids[counter] != null) {
                    String idName = ids[counter];
                    childMapping = parentMapping.getChildSelectorMapping(
                                         tagString + "#" + idName, false);
                    if (childMapping != null) {
                        getStyles(childMapping, styles, tags, ids, classes,
                                  counter + 1, numElements, alreadyChecked);
                    }
                    childMapping = parentMapping.getChildSelectorMapping(
                                   "#" + idName, false);
                    if (childMapping != null) {
                        getStyles(childMapping, styles, tags, ids, classes,
                                  counter + 1, numElements, alreadyChecked);
                    }
                }
            }
        }
    }

    /**
     * Creates and returns a Style containing all the rules that match
     *  <code>selector</code>.
     * <p>
     *  创建并返回包含匹配<code> selector </code>的所有规则的样式。
     * 
     */
    private synchronized Style createResolvedStyle(String selector,
                                      String[] tags,
                                      String[] ids, String[] classes) {
        SearchBuffer sb = SearchBuffer.obtainSearchBuffer();
        Vector<SelectorMapping> tempVector = sb.getVector();
        Hashtable<SelectorMapping, SelectorMapping> tempHashtable = sb.getHashtable();
        // Determine all the Styles that are appropriate, placing them
        // in tempVector
        try {
            SelectorMapping mapping = getRootSelectorMapping();
            int numElements = tags.length;
            String tagString = tags[0];
            SelectorMapping childMapping = mapping.getChildSelectorMapping(
                                                   tagString, false);
            if (childMapping != null) {
                getStyles(childMapping, tempVector, tags, ids, classes, 1,
                          numElements, tempHashtable);
            }
            if (classes[0] != null) {
                String className = classes[0];
                childMapping = mapping.getChildSelectorMapping(
                                       tagString + "." + className, false);
                if (childMapping != null) {
                    getStyles(childMapping, tempVector, tags, ids, classes, 1,
                              numElements, tempHashtable);
                }
                childMapping = mapping.getChildSelectorMapping(
                                       "." + className, false);
                if (childMapping != null) {
                    getStyles(childMapping, tempVector, tags, ids, classes,
                              1, numElements, tempHashtable);
                }
            }
            if (ids[0] != null) {
                String idName = ids[0];
                childMapping = mapping.getChildSelectorMapping(
                                       tagString + "#" + idName, false);
                if (childMapping != null) {
                    getStyles(childMapping, tempVector, tags, ids, classes,
                              1, numElements, tempHashtable);
                }
                childMapping = mapping.getChildSelectorMapping(
                                       "#" + idName, false);
                if (childMapping != null) {
                    getStyles(childMapping, tempVector, tags, ids, classes,
                              1, numElements, tempHashtable);
                }
            }
            // Create a new Style that will delegate to all the matching
            // Styles.
            int numLinkedSS = (linkedStyleSheets != null) ?
                              linkedStyleSheets.size() : 0;
            int numStyles = tempVector.size();
            AttributeSet[] attrs = new AttributeSet[numStyles + numLinkedSS];
            for (int counter = 0; counter < numStyles; counter++) {
                attrs[counter] = tempVector.elementAt(counter).getStyle();
            }
            // Get the AttributeSet from linked style sheets.
            for (int counter = 0; counter < numLinkedSS; counter++) {
                AttributeSet attr = linkedStyleSheets.elementAt(counter).getRule(selector);
                if (attr == null) {
                    attrs[counter + numStyles] = SimpleAttributeSet.EMPTY;
                }
                else {
                    attrs[counter + numStyles] = attr;
                }
            }
            ResolvedStyle retStyle = new ResolvedStyle(selector, attrs,
                                                       numStyles);
            resolvedStyles.put(selector, retStyle);
            return retStyle;
        }
        finally {
            SearchBuffer.releaseSearchBuffer(sb);
        }
    }

    /**
     * Creates and returns a Style containing all the rules that
     * matches <code>selector</code>.
     *
     * <p>
     *  创建并返回包含匹配<code> selector </code>的所有规则的样式。
     * 
     * 
     * @param elements  a Vector of all the Elements
     *                  the style is being asked for. The
     *                  first Element is the deepest Element, with the last Element
     *                  representing the root.
     * @param t         the Tag to use for
     *                  the first Element in <code>elements</code>
     */
    private Style createResolvedStyle(String selector, Vector elements,
                                      HTML.Tag t) {
        int numElements = elements.size();
        // Build three arrays, one for tags, one for class's, and one for
        // id's
        String tags[] = new String[numElements];
        String ids[] = new String[numElements];
        String classes[] = new String[numElements];
        for (int counter = 0; counter < numElements; counter++) {
            Element e = (Element)elements.elementAt(counter);
            AttributeSet attr = e.getAttributes();
            if (counter == 0 && e.isLeaf()) {
                // For leafs, we use the second tier attributes.
                Object testAttr = attr.getAttribute(t);
                if (testAttr instanceof AttributeSet) {
                    attr = (AttributeSet)testAttr;
                }
                else {
                    attr = null;
                }
            }
            if (attr != null) {
                HTML.Tag tag = (HTML.Tag)attr.getAttribute(StyleConstants.
                                                           NameAttribute);
                if (tag != null) {
                    tags[counter] = tag.toString();
                }
                else {
                    tags[counter] = null;
                }
                if (attr.isDefined(HTML.Attribute.CLASS)) {
                    classes[counter] = attr.getAttribute
                                      (HTML.Attribute.CLASS).toString();
                }
                else {
                    classes[counter] = null;
                }
                if (attr.isDefined(HTML.Attribute.ID)) {
                    ids[counter] = attr.getAttribute(HTML.Attribute.ID).
                                        toString();
                }
                else {
                    ids[counter] = null;
                }
            }
            else {
                tags[counter] = ids[counter] = classes[counter] = null;
            }
        }
        tags[0] = t.toString();
        return createResolvedStyle(selector, tags, ids, classes);
    }

    /**
     * Creates and returns a Style containing all the rules that match
     *  <code>selector</code>. It is assumed that each simple selector
     * in <code>selector</code> is separated by a space.
     * <p>
     *  创建并返回包含匹配<code> selector </code>的所有规则的样式。假设<code> selector </code>中的每个简单选择器由一个空格分隔。
     * 
     */
    private Style createResolvedStyle(String selector) {
        SearchBuffer sb = SearchBuffer.obtainSearchBuffer();
        // Will contain the tags, ids, and classes, in that order.
        Vector<String> elements = sb.getVector();
        try {
            boolean done;
            int dotIndex = 0;
            int spaceIndex;
            int poundIndex = 0;
            int lastIndex = 0;
            int length = selector.length();
            while (lastIndex < length) {
                if (dotIndex == lastIndex) {
                    dotIndex = selector.indexOf('.', lastIndex);
                }
                if (poundIndex == lastIndex) {
                    poundIndex = selector.indexOf('#', lastIndex);
                }
                spaceIndex = selector.indexOf(' ', lastIndex);
                if (spaceIndex == -1) {
                    spaceIndex = length;
                }
                if (dotIndex != -1 && poundIndex != -1 &&
                    dotIndex < spaceIndex && poundIndex < spaceIndex) {
                    if (poundIndex < dotIndex) {
                        // #.
                        if (lastIndex == poundIndex) {
                            elements.addElement("");
                        }
                        else {
                            elements.addElement(selector.substring(lastIndex,
                                                                  poundIndex));
                        }
                        if ((dotIndex + 1) < spaceIndex) {
                            elements.addElement(selector.substring
                                                (dotIndex + 1, spaceIndex));
                        }
                        else {
                            elements.addElement(null);
                        }
                        if ((poundIndex + 1) == dotIndex) {
                            elements.addElement(null);
                        }
                        else {
                            elements.addElement(selector.substring
                                                (poundIndex + 1, dotIndex));
                        }
                    }
                    else if(poundIndex < spaceIndex) {
                        // .#
                        if (lastIndex == dotIndex) {
                            elements.addElement("");
                        }
                        else {
                            elements.addElement(selector.substring(lastIndex,
                                                                  dotIndex));
                        }
                        if ((dotIndex + 1) < poundIndex) {
                            elements.addElement(selector.substring
                                                (dotIndex + 1, poundIndex));
                        }
                        else {
                            elements.addElement(null);
                        }
                        if ((poundIndex + 1) == spaceIndex) {
                            elements.addElement(null);
                        }
                        else {
                            elements.addElement(selector.substring
                                                (poundIndex + 1, spaceIndex));
                        }
                    }
                    dotIndex = poundIndex = spaceIndex + 1;
                }
                else if (dotIndex != -1 && dotIndex < spaceIndex) {
                    // .
                    if (dotIndex == lastIndex) {
                        elements.addElement("");
                    }
                    else {
                        elements.addElement(selector.substring(lastIndex,
                                                               dotIndex));
                    }
                    if ((dotIndex + 1) == spaceIndex) {
                        elements.addElement(null);
                    }
                    else {
                        elements.addElement(selector.substring(dotIndex + 1,
                                                               spaceIndex));
                    }
                    elements.addElement(null);
                    dotIndex = spaceIndex + 1;
                }
                else if (poundIndex != -1 && poundIndex < spaceIndex) {
                    // #
                    if (poundIndex == lastIndex) {
                        elements.addElement("");
                    }
                    else {
                        elements.addElement(selector.substring(lastIndex,
                                                               poundIndex));
                    }
                    elements.addElement(null);
                    if ((poundIndex + 1) == spaceIndex) {
                        elements.addElement(null);
                    }
                    else {
                        elements.addElement(selector.substring(poundIndex + 1,
                                                               spaceIndex));
                    }
                    poundIndex = spaceIndex + 1;
                }
                else {
                    // id
                    elements.addElement(selector.substring(lastIndex,
                                                           spaceIndex));
                    elements.addElement(null);
                    elements.addElement(null);
                }
                lastIndex = spaceIndex + 1;
            }
            // Create the tag, id, and class arrays.
            int total = elements.size();
            int numTags = total / 3;
            String[] tags = new String[numTags];
            String[] ids = new String[numTags];
            String[] classes = new String[numTags];
            for (int index = 0, eIndex = total - 3; index < numTags;
                 index++, eIndex -= 3) {
                tags[index] = elements.elementAt(eIndex);
                classes[index] = elements.elementAt(eIndex + 1);
                ids[index] = elements.elementAt(eIndex + 2);
            }
            return createResolvedStyle(selector, tags, ids, classes);
        }
        finally {
            SearchBuffer.releaseSearchBuffer(sb);
        }
    }

    /**
     * Should be invoked when a new rule is added that did not previously
     * exist. Goes through and refreshes the necessary resolved
     * rules.
     * <p>
     *  添加新规则以前不存在时应调用。浏览和刷新必要的解决规则。
     * 
     */
    private synchronized void refreshResolvedRules(String selectorName,
                                                   String[] selector,
                                                   Style newStyle,
                                                   int specificity) {
        if (resolvedStyles.size() > 0) {
            Enumeration<ResolvedStyle> values = resolvedStyles.elements();
            while (values.hasMoreElements()) {
                ResolvedStyle style = values.nextElement();
                if (style.matches(selectorName)) {
                    style.insertStyle(newStyle, specificity);
                }
            }
        }
    }


    /**
     * A temporary class used to hold a Vector, a StringBuffer and a
     * Hashtable. This is used to avoid allocing a lot of garbage when
     * searching for rules. Use the static method obtainSearchBuffer and
     * releaseSearchBuffer to get a SearchBuffer, and release it when
     * done.
     * <p>
     * 一个临时类,用于保存一个Vector,一个StringBuffer和一个Hashtable。这用于避免在搜索规则时分配大量垃圾。
     * 使用静态方法getsSearchBuffer和releaseSearchBuffer来获取SearchBuffer,并在完成后释放它。
     * 
     */
    private static class SearchBuffer {
        /** A stack containing instances of SearchBuffer. Used in getting
        /* <p>
        /* 
         * rules. */
        static Stack<SearchBuffer> searchBuffers = new Stack<SearchBuffer>();
        // A set of temporary variables that can be used in whatever way.
        Vector vector = null;
        StringBuffer stringBuffer = null;
        Hashtable hashtable = null;

        /**
         * Returns an instance of SearchBuffer. Be sure and issue
         * a releaseSearchBuffer when done with it.
         * <p>
         *  返回SearchBuffer的实例。确保并发布releaseSearchBuffer完成它。
         * 
         */
        static SearchBuffer obtainSearchBuffer() {
            SearchBuffer sb;
            try {
                if(!searchBuffers.empty()) {
                   sb = searchBuffers.pop();
                } else {
                   sb = new SearchBuffer();
                }
            } catch (EmptyStackException ese) {
                sb = new SearchBuffer();
            }
            return sb;
        }

        /**
         * Adds <code>sb</code> to the stack of SearchBuffers that can
         * be used.
         * <p>
         *  将<code> sb </code>添加到可以使用的SearchBuffers堆栈。
         * 
         */
        static void releaseSearchBuffer(SearchBuffer sb) {
            sb.empty();
            searchBuffers.push(sb);
        }

        StringBuffer getStringBuffer() {
            if (stringBuffer == null) {
                stringBuffer = new StringBuffer();
            }
            return stringBuffer;
        }

        Vector getVector() {
            if (vector == null) {
                vector = new Vector();
            }
            return vector;
        }

        Hashtable getHashtable() {
            if (hashtable == null) {
                hashtable = new Hashtable();
            }
            return hashtable;
        }

        void empty() {
            if (stringBuffer != null) {
                stringBuffer.setLength(0);
            }
            if (vector != null) {
                vector.removeAllElements();
            }
            if (hashtable != null) {
                hashtable.clear();
            }
        }
    }


    static final Border noBorder = new EmptyBorder(0,0,0,0);

    /**
     * Class to carry out some of the duties of
     * CSS formatting.  Implementations of this
     * class enable views to present the CSS formatting
     * while not knowing anything about how the CSS values
     * are being cached.
     * <p>
     * As a delegate of Views, this object is responsible for
     * the insets of a View and making sure the background
     * is maintained according to the CSS attributes.
     * <p>
     *  类执行CSS格式化的一些职责。此类的实现使视图能够呈现CSS格式,同时不知道如何缓存CSS值。
     * <p>
     *  作为视图的委托,此对象负责视图的插入,并确保根据CSS属性维护背景。
     * 
     */
    public static class BoxPainter implements Serializable {

        BoxPainter(AttributeSet a, CSS css, StyleSheet ss) {
            this.ss = ss;
            this.css = css;
            border = getBorder(a);
            binsets = border.getBorderInsets(null);
            topMargin = getLength(CSS.Attribute.MARGIN_TOP, a);
            bottomMargin = getLength(CSS.Attribute.MARGIN_BOTTOM, a);
            leftMargin = getLength(CSS.Attribute.MARGIN_LEFT, a);
            rightMargin = getLength(CSS.Attribute.MARGIN_RIGHT, a);
            bg = ss.getBackground(a);
            if (ss.getBackgroundImage(a) != null) {
                bgPainter = new BackgroundImagePainter(a, css, ss);
            }
        }

        /**
         * Fetches a border to render for the given attributes.
         * PENDING(prinz) This is pretty badly hacked at the
         * moment.
         * <p>
         *  获取为给定属性呈现的边框。 PENDING(prinz)这是非常严重的黑客入侵。
         * 
         */
        Border getBorder(AttributeSet a) {
            return new CSSBorder(a);
        }

        /**
         * Fetches the color to use for borders.  This will either be
         * the value specified by the border-color attribute (which
         * is not inherited), or it will default to the color attribute
         * (which is inherited).
         * <p>
         *  获取用于边框的颜色。这将是由border-color属性(不继承)指定的值,或者它将默认为color属性(它是继承的)。
         * 
         */
        Color getBorderColor(AttributeSet a) {
            Color color = css.getColor(a, CSS.Attribute.BORDER_COLOR);
            if (color == null) {
                color = css.getColor(a, CSS.Attribute.COLOR);
                if (color == null) {
                    return Color.black;
                }
            }
            return color;
        }

        /**
         * Fetches the inset needed on a given side to
         * account for the margin, border, and padding.
         *
         * <p>
         *  获取给定边上所需的插入,以考虑边距,边框和填充。
         * 
         * 
         * @param side The size of the box to fetch the
         *  inset for.  This can be View.TOP,
         *  View.LEFT, View.BOTTOM, or View.RIGHT.
         * @param v the view making the request.  This is
         *  used to get the AttributeSet, and may be used to
         *  resolve percentage arguments.
         * @exception IllegalArgumentException for an invalid direction
         */
        public float getInset(int side, View v) {
            AttributeSet a = v.getAttributes();
            float inset = 0;
            switch(side) {
            case View.LEFT:
                inset += getOrientationMargin(HorizontalMargin.LEFT,
                                              leftMargin, a, isLeftToRight(v));
                inset += binsets.left;
                inset += getLength(CSS.Attribute.PADDING_LEFT, a);
                break;
            case View.RIGHT:
                inset += getOrientationMargin(HorizontalMargin.RIGHT,
                                              rightMargin, a, isLeftToRight(v));
                inset += binsets.right;
                inset += getLength(CSS.Attribute.PADDING_RIGHT, a);
                break;
            case View.TOP:
                inset += topMargin;
                inset += binsets.top;
                inset += getLength(CSS.Attribute.PADDING_TOP, a);
                break;
            case View.BOTTOM:
                inset += bottomMargin;
                inset += binsets.bottom;
                inset += getLength(CSS.Attribute.PADDING_BOTTOM, a);
                break;
            default:
                throw new IllegalArgumentException("Invalid side: " + side);
            }
            return inset;
        }

        /**
         * Paints the CSS box according to the attributes
         * given.  This should paint the border, padding,
         * and background.
         *
         * <p>
         *  根据给定的属性绘制CSS框。这应该绘制边框,填充和背景。
         * 
         * 
         * @param g the rendering surface.
         * @param x the x coordinate of the allocated area to
         *  render into.
         * @param y the y coordinate of the allocated area to
         *  render into.
         * @param w the width of the allocated area to render into.
         * @param h the height of the allocated area to render into.
         * @param v the view making the request.  This is
         *  used to get the AttributeSet, and may be used to
         *  resolve percentage arguments.
         */
        public void paint(Graphics g, float x, float y, float w, float h, View v) {
            // PENDING(prinz) implement real rendering... which would
            // do full set of border and background capabilities.
            // remove margin

            float dx = 0;
            float dy = 0;
            float dw = 0;
            float dh = 0;
            AttributeSet a = v.getAttributes();
            boolean isLeftToRight = isLeftToRight(v);
            float localLeftMargin = getOrientationMargin(HorizontalMargin.LEFT,
                                                         leftMargin,
                                                         a, isLeftToRight);
            float localRightMargin = getOrientationMargin(HorizontalMargin.RIGHT,
                                                          rightMargin,
                                                          a, isLeftToRight);
            if (!(v instanceof HTMLEditorKit.HTMLFactory.BodyBlockView)) {
                dx = localLeftMargin;
                dy = topMargin;
                dw = -(localLeftMargin + localRightMargin);
                dh = -(topMargin + bottomMargin);
            }
            if (bg != null) {
                g.setColor(bg);
                g.fillRect((int) (x + dx),
                           (int) (y + dy),
                           (int) (w + dw),
                           (int) (h + dh));
            }
            if (bgPainter != null) {
                bgPainter.paint(g, x + dx, y + dy, w + dw, h + dh, v);
            }
            x += localLeftMargin;
            y += topMargin;
            w -= localLeftMargin + localRightMargin;
            h -= topMargin + bottomMargin;
            if (border instanceof BevelBorder) {
                //BevelBorder does not support border width
                int bw = (int) getLength(CSS.Attribute.BORDER_TOP_WIDTH, a);
                for (int i = bw - 1; i >= 0; i--) {
                    border.paintBorder(null, g, (int) x + i, (int) y + i,
                                       (int) w - 2 * i, (int) h - 2 * i);
                }
            } else {
                border.paintBorder(null, g, (int) x, (int) y, (int) w, (int) h);
            }
        }

        float getLength(CSS.Attribute key, AttributeSet a) {
            return css.getLength(a, key, ss);
        }

        static boolean isLeftToRight(View v) {
            boolean ret = true;
            if (isOrientationAware(v)) {
                Container container;
                if (v != null && (container = v.getContainer()) != null) {
                    ret = container.getComponentOrientation().isLeftToRight();
                }
            }
            return ret;
        }

        /*
         * only certain tags are concerned about orientation
         * <dir>, <menu>, <ul>, <ol>
         * for all others we return true. It is implemented this way
         * for performance purposes
         * <p>
         *  只有某些标签关心的方向<dir>,<menu>,<ul>,<ol>对于所有其他我们返回true。这是为了性能目的实现
         * 
         */
        static boolean isOrientationAware(View v) {
            boolean ret = false;
            AttributeSet attr;
            Object obj;
            if (v != null
                && (attr = v.getElement().getAttributes()) != null
                && (obj = attr.getAttribute(StyleConstants.NameAttribute)) instanceof HTML.Tag
                && (obj == HTML.Tag.DIR
                    || obj == HTML.Tag.MENU
                    || obj == HTML.Tag.UL
                    || obj == HTML.Tag.OL)) {
                ret = true;
            }

            return ret;
        }

        static enum HorizontalMargin { LEFT, RIGHT }

        /**
         * for <dir>, <menu>, <ul> etc.
         * margins are Left-To-Right/Right-To-Left depended.
         * see 5088268 for more details
         * margin-(left|right)-(ltr|rtl) were introduced to describe it
         * if margin-(left|right) is present we are to use it.
         *
         * <p>
         * 对于<dir>,<menu>,<ul>等边距是从左到右/从右到左依赖。
         * 见5088268更多细节margin-(左|右) - (ltr | rtl)被介绍来描述如果margin-(left | right)存在,我们将使用它。
         * 
         * 
         * @param side The horizontal side to fetch margin for
         *  This can be HorizontalMargin.LEFT or HorizontalMargin.RIGHT
         * @param cssMargin margin from css
         * @param a AttributeSet for the View we getting margin for
         * @param isLeftToRight
         * @return orientation depended margin
         */
        float getOrientationMargin(HorizontalMargin side, float cssMargin,
                                   AttributeSet a, boolean isLeftToRight) {
            float margin = cssMargin;
            float orientationMargin = cssMargin;
            Object cssMarginValue = null;
            switch (side) {
            case RIGHT:
                {
                    orientationMargin = (isLeftToRight) ?
                        getLength(CSS.Attribute.MARGIN_RIGHT_LTR, a) :
                        getLength(CSS.Attribute.MARGIN_RIGHT_RTL, a);
                    cssMarginValue = a.getAttribute(CSS.Attribute.MARGIN_RIGHT);
                }
                break;
            case LEFT :
                {
                    orientationMargin = (isLeftToRight) ?
                        getLength(CSS.Attribute.MARGIN_LEFT_LTR, a) :
                        getLength(CSS.Attribute.MARGIN_LEFT_RTL, a);
                    cssMarginValue = a.getAttribute(CSS.Attribute.MARGIN_LEFT);
                }
                break;
            }

            if (cssMarginValue == null
                && orientationMargin != Integer.MIN_VALUE) {
                margin = orientationMargin;
            }
            return margin;
        }

        float topMargin;
        float bottomMargin;
        float leftMargin;
        float rightMargin;
        // Bitmask, used to indicate what margins are relative:
        // bit 0 for top, 1 for bottom, 2 for left and 3 for right.
        short marginFlags;
        Border border;
        Insets binsets;
        CSS css;
        StyleSheet ss;
        Color bg;
        BackgroundImagePainter bgPainter;
    }

    /**
     * Class to carry out some of the duties of CSS list
     * formatting.  Implementations of this
     * class enable views to present the CSS formatting
     * while not knowing anything about how the CSS values
     * are being cached.
     * <p>
     *  类执行CSS列表格式化的一些职责。此类的实现使视图能够呈现CSS格式,同时不知道如何缓存CSS值。
     * 
     */
    public static class ListPainter implements Serializable {

        ListPainter(AttributeSet attr, StyleSheet ss) {
            this.ss = ss;
            /* Get the image to use as a list bullet */
            String imgstr = (String)attr.getAttribute(CSS.Attribute.
                                                      LIST_STYLE_IMAGE);
            type = null;
            if (imgstr != null && !imgstr.equals("none")) {
                String tmpstr = null;
                try {
                    StringTokenizer st = new StringTokenizer(imgstr, "()");
                    if (st.hasMoreTokens())
                        tmpstr = st.nextToken();
                    if (st.hasMoreTokens())
                        tmpstr = st.nextToken();
                    URL u = new URL(tmpstr);
                    img = new ImageIcon(u);
                } catch (MalformedURLException e) {
                    if (tmpstr != null && ss != null && ss.getBase() != null) {
                        try {
                            URL u = new URL(ss.getBase(), tmpstr);
                            img = new ImageIcon(u);
                        } catch (MalformedURLException murle) {
                            img = null;
                        }
                    }
                    else {
                        img = null;
                    }
                }
            }

            /* Get the type of bullet to use in the list */
            if (img == null) {
                type = (CSS.Value)attr.getAttribute(CSS.Attribute.
                                                    LIST_STYLE_TYPE);
            }
            start = 1;

            paintRect = new Rectangle();
        }

        /**
         * Returns a string that represents the value
         * of the HTML.Attribute.TYPE attribute.
         * If this attributes is not defined, then
         * then the type defaults to "disc" unless
         * the tag is on Ordered list.  In the case
         * of the latter, the default type is "decimal".
         * <p>
         *  返回表示HTML.Attribute.TYPE属性值的字符串。如果未定义此属性,则类型默认为"disc",除非标记位于有序列表中。在后者的情况下,默认类型是"十进制"。
         * 
         */
        private CSS.Value getChildType(View childView) {
            CSS.Value childtype = (CSS.Value)childView.getAttributes().
                                  getAttribute(CSS.Attribute.LIST_STYLE_TYPE);

            if (childtype == null) {
                if (type == null) {
                    // Parent view.
                    View v = childView.getParent();
                    HTMLDocument doc = (HTMLDocument)v.getDocument();
                    if (doc.matchNameAttribute(v.getElement().getAttributes(),
                                               HTML.Tag.OL)) {
                        childtype = CSS.Value.DECIMAL;
                    } else {
                        childtype = CSS.Value.DISC;
                    }
                } else {
                    childtype = type;
                }
            }
            return childtype;
        }

        /**
         * Obtains the starting index from <code>parent</code>.
         * <p>
         *  从<code> parent </code>获取起始索引。
         * 
         */
        private void getStart(View parent) {
            checkedForStart = true;
            Element element = parent.getElement();
            if (element != null) {
                AttributeSet attr = element.getAttributes();
                Object startValue;
                if (attr != null && attr.isDefined(HTML.Attribute.START) &&
                    (startValue = attr.getAttribute
                     (HTML.Attribute.START)) != null &&
                    (startValue instanceof String)) {

                    try {
                        start = Integer.parseInt((String)startValue);
                    }
                    catch (NumberFormatException nfe) {}
                }
            }
        }

        /**
         * Returns an integer that should be used to render the child at
         * <code>childIndex</code> with. The retValue will usually be
         * <code>childIndex</code> + 1, unless <code>parentView</code>
         * has some Views that do not represent LI's, or one of the views
         * has a HTML.Attribute.START specified.
         * <p>
         *  返回在<code> childIndex </code>中用于渲染子元素的整数。
         *  retValue通常为<code> childIndex </code> + 1,除非<code> parentView </code>有一些视图不代表LI,或其中一个视图指定了HTML.Attrib
         * ute.START。
         *  返回在<code> childIndex </code>中用于渲染子元素的整数。
         * 
         */
        private int getRenderIndex(View parentView, int childIndex) {
            if (!checkedForStart) {
                getStart(parentView);
            }
            int retIndex = childIndex;
            for (int counter = childIndex; counter >= 0; counter--) {
                AttributeSet as = parentView.getElement().getElement(counter).
                                  getAttributes();
                if (as.getAttribute(StyleConstants.NameAttribute) !=
                    HTML.Tag.LI) {
                    retIndex--;
                } else if (as.isDefined(HTML.Attribute.VALUE)) {
                    Object value = as.getAttribute(HTML.Attribute.VALUE);
                    if (value != null &&
                        (value instanceof String)) {
                        try {
                            int iValue = Integer.parseInt((String)value);
                            return retIndex - counter + iValue;
                        }
                        catch (NumberFormatException nfe) {}
                    }
                }
            }
            return retIndex + start;
        }

        /**
         * Paints the CSS list decoration according to the
         * attributes given.
         *
         * <p>
         *  根据给定的属性绘制CSS列表装饰。
         * 
         * 
         * @param g the rendering surface.
         * @param x the x coordinate of the list item allocation
         * @param y the y coordinate of the list item allocation
         * @param w the width of the list item allocation
         * @param h the height of the list item allocation
         * @param v the allocated area to paint into.
         * @param item which list item is being painted.  This
         *  is a number greater than or equal to 0.
         */
        public void paint(Graphics g, float x, float y, float w, float h, View v, int item) {
            View cv = v.getView(item);
            Container host = v.getContainer();
            Object name = cv.getElement().getAttributes().getAttribute
                         (StyleConstants.NameAttribute);
            // Only draw something if the View is a list item. This won't
            // be the case for comments.
            if (!(name instanceof HTML.Tag) ||
                name != HTML.Tag.LI) {
                return;
            }
            // deside on what side draw bullets, etc.
            isLeftToRight =
                host.getComponentOrientation().isLeftToRight();

            // How the list indicator is aligned is not specified, it is
            // left up to the UA. IE and NS differ on this behavior.
            // This is closer to NS where we align to the first line of text.
            // If the child is not text we draw the indicator at the
            // origin (0).
            float align = 0;
            if (cv.getViewCount() > 0) {
                View pView = cv.getView(0);
                Object cName = pView.getElement().getAttributes().
                               getAttribute(StyleConstants.NameAttribute);
                if ((cName == HTML.Tag.P || cName == HTML.Tag.IMPLIED) &&
                              pView.getViewCount() > 0) {
                    paintRect.setBounds((int)x, (int)y, (int)w, (int)h);
                    Shape shape = cv.getChildAllocation(0, paintRect);
                    if (shape != null && (shape = pView.getView(0).
                                 getChildAllocation(0, shape)) != null) {
                        Rectangle rect = (shape instanceof Rectangle) ?
                                         (Rectangle)shape : shape.getBounds();

                        align = pView.getView(0).getAlignment(View.Y_AXIS);
                        y = rect.y;
                        h = rect.height;
                    }
                }
            }

            // set the color of a decoration
            Color c = (host.isEnabled()
                ? (ss != null
                    ? ss.getForeground(cv.getAttributes())
                    : host.getForeground())
                : UIManager.getColor("textInactiveText"));
            g.setColor(c);

            if (img != null) {
                drawIcon(g, (int) x, (int) y, (int) w, (int) h, align, host);
                return;
            }
            CSS.Value childtype = getChildType(cv);
            Font font = ((StyledDocument)cv.getDocument()).
                                         getFont(cv.getAttributes());
            if (font != null) {
                g.setFont(font);
            }
            if (childtype == CSS.Value.SQUARE || childtype == CSS.Value.CIRCLE
                || childtype == CSS.Value.DISC) {
                drawShape(g, childtype, (int) x, (int) y,
                          (int) w, (int) h, align);
            } else if (childtype == CSS.Value.DECIMAL) {
                drawLetter(g, '1', (int) x, (int) y, (int) w, (int) h, align,
                           getRenderIndex(v, item));
            } else if (childtype == CSS.Value.LOWER_ALPHA) {
                drawLetter(g, 'a', (int) x, (int) y, (int) w, (int) h, align,
                           getRenderIndex(v, item));
            } else if (childtype == CSS.Value.UPPER_ALPHA) {
                drawLetter(g, 'A', (int) x, (int) y, (int) w, (int) h, align,
                           getRenderIndex(v, item));
            } else if (childtype == CSS.Value.LOWER_ROMAN) {
                drawLetter(g, 'i', (int) x, (int) y, (int) w, (int) h, align,
                           getRenderIndex(v, item));
            } else if (childtype == CSS.Value.UPPER_ROMAN) {
                drawLetter(g, 'I', (int) x, (int) y, (int) w, (int) h, align,
                           getRenderIndex(v, item));
            }
        }

        /**
         * Draws the bullet icon specified by the list-style-image argument.
         *
         * <p>
         *  绘制由list-style-image参数指定的项目符号图标。
         * 
         * 
         * @param g     the graphics context
         * @param ax    x coordinate to place the bullet
         * @param ay    y coordinate to place the bullet
         * @param aw    width of the container the bullet is placed in
         * @param ah    height of the container the bullet is placed in
         * @param align preferred alignment factor for the child view
         */
        void drawIcon(Graphics g, int ax, int ay, int aw, int ah,
                      float align, Component c) {
            // Align to bottom of icon.
            int gap = isLeftToRight ? - (img.getIconWidth() + bulletgap) :
                                        (aw + bulletgap);
            int x = ax + gap;
            int y = Math.max(ay, ay + (int)(align * ah) -img.getIconHeight());

            img.paintIcon(c, g, x, y);
        }

        /**
         * Draws the graphical bullet item specified by the type argument.
         *
         * <p>
         *  绘制由type参数指定的图形项目符号项。
         * 
         * 
         * @param g     the graphics context
         * @param type  type of bullet to draw (circle, square, disc)
         * @param ax    x coordinate to place the bullet
         * @param ay    y coordinate to place the bullet
         * @param aw    width of the container the bullet is placed in
         * @param ah    height of the container the bullet is placed in
         * @param align preferred alignment factor for the child view
         */
        void drawShape(Graphics g, CSS.Value type, int ax, int ay, int aw,
                       int ah, float align) {
            // Align to bottom of shape.
            int gap = isLeftToRight ? - (bulletgap + 8) : (aw + bulletgap);
            int x = ax + gap;
            int y = Math.max(ay, ay + (int)(align * ah) - 8);

            if (type == CSS.Value.SQUARE) {
                g.drawRect(x, y, 8, 8);
            } else if (type == CSS.Value.CIRCLE) {
                g.drawOval(x, y, 8, 8);
            } else {
                g.fillOval(x, y, 8, 8);
            }
        }

        /**
         * Draws the letter or number for an ordered list.
         *
         * <p>
         *  绘制有序列表的字母或数字。
         * 
         * 
         * @param g     the graphics context
         * @param letter type of ordered list to draw
         * @param ax    x coordinate to place the bullet
         * @param ay    y coordinate to place the bullet
         * @param aw    width of the container the bullet is placed in
         * @param ah    height of the container the bullet is placed in
         * @param index position of the list item in the list
         */
        void drawLetter(Graphics g, char letter, int ax, int ay, int aw,
                        int ah, float align, int index) {
            String str = formatItemNum(index, letter);
            str = isLeftToRight ? str + "." : "." + str;
            FontMetrics fm = SwingUtilities2.getFontMetrics(null, g);
            int stringwidth = SwingUtilities2.stringWidth(null, fm, str);
            int gap = isLeftToRight ? - (stringwidth + bulletgap) :
                                        (aw + bulletgap);
            int x = ax + gap;
            int y = Math.max(ay + fm.getAscent(), ay + (int)(ah * align));
            SwingUtilities2.drawString(null, g, str, x, y);
        }

        /**
         * Converts the item number into the ordered list number
         * (i.e.  1 2 3, i ii iii, a b c, etc.
         *
         * <p>
         *  将项目号转换成有序列表号(即1 2 3,i ii iii,a b c等
         * 
         * 
         * @param itemNum number to format
         * @param type    type of ordered list
         */
        String formatItemNum(int itemNum, char type) {
            String numStyle = "1";

            boolean uppercase = false;

            String formattedNum;

            switch (type) {
            case '1':
            default:
                formattedNum = String.valueOf(itemNum);
                break;

            case 'A':
                uppercase = true;
                // fall through
            case 'a':
                formattedNum = formatAlphaNumerals(itemNum);
                break;

            case 'I':
                uppercase = true;
                // fall through
            case 'i':
                formattedNum = formatRomanNumerals(itemNum);
            }

            if (uppercase) {
                formattedNum = formattedNum.toUpperCase();
            }

            return formattedNum;
        }

        /**
         * Converts the item number into an alphabetic character
         *
         * <p>
         *  将项目编号转换为字母字符
         * 
         * 
         * @param itemNum number to format
         */
        String formatAlphaNumerals(int itemNum) {
            String result;

            if (itemNum > 26) {
                result = formatAlphaNumerals(itemNum / 26) +
                    formatAlphaNumerals(itemNum % 26);
            } else {
                // -1 because item is 1 based.
                result = String.valueOf((char)('a' + itemNum - 1));
            }

            return result;
        }

        /* list of roman numerals */
        static final char romanChars[][] = {
            {'i', 'v'},
            {'x', 'l' },
            {'c', 'd' },
            {'m', '?' },
        };

        /**
         * Converts the item number into a roman numeral
         *
         * <p>
         *  将项目编号转换为罗马数字
         * 
         * 
         * @param num  number to format
         */
        String formatRomanNumerals(int num) {
            return formatRomanNumerals(0, num);
        }

        /**
         * Converts the item number into a roman numeral
         *
         * <p>
         * 将项目编号转换为罗马数字
         * 
         * 
         * @param num  number to format
         */
        String formatRomanNumerals(int level, int num) {
            if (num < 10) {
                return formatRomanDigit(level, num);
            } else {
                return formatRomanNumerals(level + 1, num / 10) +
                    formatRomanDigit(level, num % 10);
            }
        }


        /**
         * Converts the item number into a roman numeral
         *
         * <p>
         *  将项目编号转换为罗马数字
         * 
         * 
         * @param level position
         * @param digit digit to format
         */
        String formatRomanDigit(int level, int digit) {
            String result = "";
            if (digit == 9) {
                result = result + romanChars[level][0];
                result = result + romanChars[level + 1][0];
                return result;
            } else if (digit == 4) {
                result = result + romanChars[level][0];
                result = result + romanChars[level][1];
                return result;
            } else if (digit >= 5) {
                result = result + romanChars[level][1];
                digit -= 5;
            }

            for (int i = 0; i < digit; i++) {
                result = result + romanChars[level][0];
            }

            return result;
        }

        private Rectangle paintRect;
        private boolean checkedForStart;
        private int start;
        private CSS.Value type;
        URL imageurl;
        private StyleSheet ss = null;
        Icon img = null;
        private int bulletgap = 5;
        private boolean isLeftToRight;
    }


    /**
     * Paints the background image.
     * <p>
     *  绘制背景图像。
     * 
     */
    static class BackgroundImagePainter implements Serializable {
        ImageIcon   backgroundImage;
        float       hPosition;
        float       vPosition;
        // bit mask: 0 for repeat x, 1 for repeat y, 2 for horiz relative,
        // 3 for vert relative
        short       flags;
        // These are used when painting, updatePaintCoordinates updates them.
        private int paintX;
        private int paintY;
        private int paintMaxX;
        private int paintMaxY;

        BackgroundImagePainter(AttributeSet a, CSS css, StyleSheet ss) {
            backgroundImage = ss.getBackgroundImage(a);
            // Determine the position.
            CSS.BackgroundPosition pos = (CSS.BackgroundPosition)a.getAttribute
                                           (CSS.Attribute.BACKGROUND_POSITION);
            if (pos != null) {
                hPosition = pos.getHorizontalPosition();
                vPosition = pos.getVerticalPosition();
                if (pos.isHorizontalPositionRelativeToSize()) {
                    flags |= 4;
                }
                else if (pos.isHorizontalPositionRelativeToSize()) {
                    hPosition *= css.getFontSize(a, 12, ss);
                }
                if (pos.isVerticalPositionRelativeToSize()) {
                    flags |= 8;
                }
                else if (pos.isVerticalPositionRelativeToFontSize()) {
                    vPosition *= css.getFontSize(a, 12, ss);
                }
            }
            // Determine any repeating values.
            CSS.Value repeats = (CSS.Value)a.getAttribute(CSS.Attribute.
                                                          BACKGROUND_REPEAT);
            if (repeats == null || repeats == CSS.Value.BACKGROUND_REPEAT) {
                flags |= 3;
            }
            else if (repeats == CSS.Value.BACKGROUND_REPEAT_X) {
                flags |= 1;
            }
            else if (repeats == CSS.Value.BACKGROUND_REPEAT_Y) {
                flags |= 2;
            }
        }

        void paint(Graphics g, float x, float y, float w, float h, View v) {
            Rectangle clip = g.getClipRect();
            if (clip != null) {
                // Constrain the clip so that images don't draw outside the
                // legal bounds.
                g.clipRect((int)x, (int)y, (int)w, (int)h);
            }
            if ((flags & 3) == 0) {
                // no repeating
                int width = backgroundImage.getIconWidth();
                int height = backgroundImage.getIconWidth();
                if ((flags & 4) == 4) {
                    paintX = (int)(x + w * hPosition -
                                  (float)width * hPosition);
                }
                else {
                    paintX = (int)x + (int)hPosition;
                }
                if ((flags & 8) == 8) {
                    paintY = (int)(y + h * vPosition -
                                  (float)height * vPosition);
                }
                else {
                    paintY = (int)y + (int)vPosition;
                }
                if (clip == null ||
                    !((paintX + width <= clip.x) ||
                      (paintY + height <= clip.y) ||
                      (paintX >= clip.x + clip.width) ||
                      (paintY >= clip.y + clip.height))) {
                    backgroundImage.paintIcon(null, g, paintX, paintY);
                }
            }
            else {
                int width = backgroundImage.getIconWidth();
                int height = backgroundImage.getIconHeight();
                if (width > 0 && height > 0) {
                    paintX = (int)x;
                    paintY = (int)y;
                    paintMaxX = (int)(x + w);
                    paintMaxY = (int)(y + h);
                    if (updatePaintCoordinates(clip, width, height)) {
                        while (paintX < paintMaxX) {
                            int ySpot = paintY;
                            while (ySpot < paintMaxY) {
                                backgroundImage.paintIcon(null, g, paintX,
                                                          ySpot);
                                ySpot += height;
                            }
                            paintX += width;
                        }
                    }
                }
            }
            if (clip != null) {
                // Reset clip.
                g.setClip(clip.x, clip.y, clip.width, clip.height);
            }
        }

        private boolean updatePaintCoordinates
                 (Rectangle clip, int width, int height){
            if ((flags & 3) == 1) {
                paintMaxY = paintY + 1;
            }
            else if ((flags & 3) == 2) {
                paintMaxX = paintX + 1;
            }
            if (clip != null) {
                if ((flags & 3) == 1 && ((paintY + height <= clip.y) ||
                                         (paintY > clip.y + clip.height))) {
                    // not visible.
                    return false;
                }
                if ((flags & 3) == 2 && ((paintX + width <= clip.x) ||
                                         (paintX > clip.x + clip.width))) {
                    // not visible.
                    return false;
                }
                if ((flags & 1) == 1) {
                    if ((clip.x + clip.width) < paintMaxX) {
                        if ((clip.x + clip.width - paintX) % width == 0) {
                            paintMaxX = clip.x + clip.width;
                        }
                        else {
                            paintMaxX = ((clip.x + clip.width - paintX) /
                                         width + 1) * width + paintX;
                        }
                    }
                    if (clip.x > paintX) {
                        paintX = (clip.x - paintX) / width * width + paintX;
                    }
                }
                if ((flags & 2) == 2) {
                    if ((clip.y + clip.height) < paintMaxY) {
                        if ((clip.y + clip.height - paintY) % height == 0) {
                            paintMaxY = clip.y + clip.height;
                        }
                        else {
                            paintMaxY = ((clip.y + clip.height - paintY) /
                                         height + 1) * height + paintY;
                        }
                    }
                    if (clip.y > paintY) {
                        paintY = (clip.y - paintY) / height * height + paintY;
                    }
                }
            }
            // Valid
            return true;
        }
    }


    /**
     * A subclass of MuxingAttributeSet that translates between
     * CSS and HTML and StyleConstants. The AttributeSets used are
     * the CSS rules that match the Views Elements.
     * <p>
     *  MuxingAttributeSet的子类,在CSS和HTML和StyleConstants之间转换。使用的AttributeSets是与视图元素匹配的CSS规则。
     * 
     */
    class ViewAttributeSet extends MuxingAttributeSet {
        ViewAttributeSet(View v) {
            host = v;

            // PENDING(prinz) fix this up to be a more realistic
            // implementation.
            Document doc = v.getDocument();
            SearchBuffer sb = SearchBuffer.obtainSearchBuffer();
            Vector<AttributeSet> muxList = sb.getVector();
            try {
                if (doc instanceof HTMLDocument) {
                    StyleSheet styles = StyleSheet.this;
                    Element elem = v.getElement();
                    AttributeSet a = elem.getAttributes();
                    AttributeSet htmlAttr = styles.translateHTMLToCSS(a);

                    if (htmlAttr.getAttributeCount() != 0) {
                        muxList.addElement(htmlAttr);
                    }
                    if (elem.isLeaf()) {
                        Enumeration keys = a.getAttributeNames();
                        while (keys.hasMoreElements()) {
                            Object key = keys.nextElement();
                            if (key instanceof HTML.Tag) {
                                if (key == HTML.Tag.A) {
                                    Object o = a.getAttribute(key);
                                /**
                                   In the case of an A tag, the css rules
                                   apply only for tags that have their
                                   href attribute defined and not for
                                   anchors that only have their name attributes
                                   defined, i.e anchors that function as
                                   destinations.  Hence we do not add the
                                   attributes for that latter kind of
                                   anchors.  When CSS2 support is added,
                                   it will be possible to specificity this
                                   kind of conditional behaviour in the
                                   stylesheet.
                                /* <p>
                                /*  在A标记的情况下,css规则仅适用于定义了href属性的标记,而不适用于仅定义其名称属性的锚点(即用作目标的锚点)。因此,我们不添加后一种锚的属性。
                                /* 当添加CSS2支持时,可以在样式表中指定这种条件行为。
                                /* 
                                /* 
                                 **/
                                    if (o != null && o instanceof AttributeSet) {
                                        AttributeSet attr = (AttributeSet)o;
                                        if (attr.getAttribute(HTML.Attribute.HREF) == null) {
                                            continue;
                                        }
                                    }
                                }
                                AttributeSet cssRule = styles.getRule((HTML.Tag) key, elem);
                                if (cssRule != null) {
                                    muxList.addElement(cssRule);
                                }
                            }
                        }
                    } else {
                        HTML.Tag t = (HTML.Tag) a.getAttribute
                                     (StyleConstants.NameAttribute);
                        AttributeSet cssRule = styles.getRule(t, elem);
                        if (cssRule != null) {
                            muxList.addElement(cssRule);
                        }
                    }
                }
                AttributeSet[] attrs = new AttributeSet[muxList.size()];
                muxList.copyInto(attrs);
                setAttributes(attrs);
            }
            finally {
                SearchBuffer.releaseSearchBuffer(sb);
            }
        }

        //  --- AttributeSet methods ----------------------------

        /**
         * Checks whether a given attribute is defined.
         * This will convert the key over to CSS if the
         * key is a StyleConstants key that has a CSS
         * mapping.
         *
         * <p>
         *  检查是否定义了给定属性。如果键是具有CSS映射的StyleConstants键,这将把键转换为CSS。
         * 
         * 
         * @param key the attribute key
         * @return true if the attribute is defined
         * @see AttributeSet#isDefined
         */
        public boolean isDefined(Object key) {
            if (key instanceof StyleConstants) {
                Object cssKey = css.styleConstantsKeyToCSSKey
                                    ((StyleConstants)key);
                if (cssKey != null) {
                    key = cssKey;
                }
            }
            return super.isDefined(key);
        }

        /**
         * Gets the value of an attribute.  If the requested
         * attribute is a StyleConstants attribute that has
         * a CSS mapping, the request will be converted.
         *
         * <p>
         *  获取属性的值。如果请求的属性是具有CSS映射的StyleConstants属性,请求将被转换。
         * 
         * 
         * @param key the attribute name
         * @return the attribute value
         * @see AttributeSet#getAttribute
         */
        public Object getAttribute(Object key) {
            if (key instanceof StyleConstants) {
                Object cssKey = css.styleConstantsKeyToCSSKey
                               ((StyleConstants)key);
                if (cssKey != null) {
                    Object value = doGetAttribute(cssKey);
                    if (value instanceof CSS.CssValue) {
                        return ((CSS.CssValue)value).toStyleConstants
                                     ((StyleConstants)key, host);
                    }
                }
            }
            return doGetAttribute(key);
        }

        Object doGetAttribute(Object key) {
            Object retValue = super.getAttribute(key);
            if (retValue != null) {
                return retValue;
            }
            // didn't find it... try parent if it's a css attribute
            // that is inherited.
            if (key instanceof CSS.Attribute) {
                CSS.Attribute css = (CSS.Attribute) key;
                if (css.isInherited()) {
                    AttributeSet parent = getResolveParent();
                    if (parent != null)
                        return parent.getAttribute(key);
                }
            }
            return null;
        }

        /**
         * If not overriden, the resolving parent defaults to
         * the parent element.
         *
         * <p>
         *  如果不覆盖,则解析父代方默认为父元素。
         * 
         * 
         * @return the attributes from the parent
         * @see AttributeSet#getResolveParent
         */
        public AttributeSet getResolveParent() {
            if (host == null) {
                return null;
            }
            View parent = host.getParent();
            return (parent != null) ? parent.getAttributes() : null;
        }

        /** View created for. */
        View host;
    }


    /**
     * A subclass of MuxingAttributeSet that implements Style. Currently
     * the MutableAttributeSet methods are unimplemented, that is they
     * do nothing.
     * <p>
     *  实现Style的MuxingAttributeSet的子类。当前的MutableAttributeSet方法是未实现的,那就是它们什么都不做。
     * 
     */
    // PENDING(sky): Decide what to do with this. Either make it
    // contain a SimpleAttributeSet that modify methods are delegated to,
    // or change getRule to return an AttributeSet and then don't make this
    // implement Style.
    static class ResolvedStyle extends MuxingAttributeSet implements
                  Serializable, Style {
        ResolvedStyle(String name, AttributeSet[] attrs, int extendedIndex) {
            super(attrs);
            this.name = name;
            this.extendedIndex = extendedIndex;
        }

        /**
         * Inserts a Style into the receiver so that the styles the
         * receiver represents are still ordered by specificity.
         * <code>style</code> will be added before any extended styles, that
         * is before extendedIndex.
         * <p>
         *  在接收器中插入样式,以便接收器表示的样式仍然按照特异性排序。 <code> style </code>将在任何扩展样式之前添加,即在extendedIndex之前。
         * 
         */
        synchronized void insertStyle(Style style, int specificity) {
            AttributeSet[] attrs = getAttributes();
            int maxCounter = attrs.length;
            int counter = 0;
            for (;counter < extendedIndex; counter++) {
                if (specificity > getSpecificity(((Style)attrs[counter]).
                                                 getName())) {
                    break;
                }
            }
            insertAttributeSetAt(style, counter);
            extendedIndex++;
        }

        /**
         * Removes a previously added style. This will do nothing if
         * <code>style</code> is not referenced by the receiver.
         * <p>
         * 删除先前添加的样式。如果<code> style </code>没有被接收器引用,这将不会执行任何操作。
         * 
         */
        synchronized void removeStyle(Style style) {
            AttributeSet[] attrs = getAttributes();

            for (int counter = attrs.length - 1; counter >= 0; counter--) {
                if (attrs[counter] == style) {
                    removeAttributeSetAt(counter);
                    if (counter < extendedIndex) {
                        extendedIndex--;
                    }
                    break;
                }
            }
        }

        /**
         * Adds <code>s</code> as one of the Attributesets to look up
         * attributes in.
         * <p>
         *  添加<code> s </code>作为查找属性的属性集之一。
         * 
         */
        synchronized void insertExtendedStyleAt(Style attr, int index) {
            insertAttributeSetAt(attr, extendedIndex + index);
        }

        /**
         * Adds <code>s</code> as one of the AttributeSets to look up
         * attributes in. It will be the AttributeSet last checked.
         * <p>
         *  添加<code> s </code>作为AttributeSet之一来查找属性。它将是上次检查的AttributeSet。
         * 
         */
        synchronized void addExtendedStyle(Style attr) {
            insertAttributeSetAt(attr, getAttributes().length);
        }

        /**
         * Removes the style at <code>index</code> +
         * <code>extendedIndex</code>.
         * <p>
         *  删除<code> index </code> + <code> extendedIndex </code>中的样式。
         * 
         */
        synchronized void removeExtendedStyleAt(int index) {
            removeAttributeSetAt(extendedIndex + index);
        }

        /**
         * Returns true if the receiver matches <code>selector</code>, where
         * a match is defined by the CSS rule matching.
         * Each simple selector must be separated by a single space.
         * <p>
         *  如果接收器匹配<code> selector </code>,则返回true,其中匹配由CSS规则匹配定义。每个简单选择器必须由单个空格分隔。
         * 
         */
        protected boolean matches(String selector) {
            int sLast = selector.length();

            if (sLast == 0) {
                return false;
            }
            int thisLast = name.length();
            int sCurrent = selector.lastIndexOf(' ');
            int thisCurrent = name.lastIndexOf(' ');
            if (sCurrent >= 0) {
                sCurrent++;
            }
            if (thisCurrent >= 0) {
                thisCurrent++;
            }
            if (!matches(selector, sCurrent, sLast, thisCurrent, thisLast)) {
                return false;
            }
            while (sCurrent != -1) {
                sLast = sCurrent - 1;
                sCurrent = selector.lastIndexOf(' ', sLast - 1);
                if (sCurrent >= 0) {
                    sCurrent++;
                }
                boolean match = false;
                while (!match && thisCurrent != -1) {
                    thisLast = thisCurrent - 1;
                    thisCurrent = name.lastIndexOf(' ', thisLast - 1);
                    if (thisCurrent >= 0) {
                        thisCurrent++;
                    }
                    match = matches(selector, sCurrent, sLast, thisCurrent,
                                    thisLast);
                }
                if (!match) {
                    return false;
                }
            }
            return true;
        }

        /**
         * Returns true if the substring of the receiver, in the range
         * thisCurrent, thisLast matches the substring of selector in
         * the ranme sCurrent to sLast based on CSS selector matching.
         * <p>
         *  如果接收器的子串,在thisCurrent范围内,thisLast匹配选择器在ranme sCurrent中的子字符串sLast基于CSS选择器匹配返回true。
         * 
         */
        boolean matches(String selector, int sCurrent, int sLast,
                       int thisCurrent, int thisLast) {
            sCurrent = Math.max(sCurrent, 0);
            thisCurrent = Math.max(thisCurrent, 0);
            int thisDotIndex = boundedIndexOf(name, '.', thisCurrent,
                                              thisLast);
            int thisPoundIndex = boundedIndexOf(name, '#', thisCurrent,
                                                thisLast);
            int sDotIndex = boundedIndexOf(selector, '.', sCurrent, sLast);
            int sPoundIndex = boundedIndexOf(selector, '#', sCurrent, sLast);
            if (sDotIndex != -1) {
                // Selector has a '.', which indicates name must match it,
                // or if the '.' starts the selector than name must have
                // the same class (doesn't matter what element name).
                if (thisDotIndex == -1) {
                    return false;
                }
                if (sCurrent == sDotIndex) {
                    if ((thisLast - thisDotIndex) != (sLast - sDotIndex) ||
                        !selector.regionMatches(sCurrent, name, thisDotIndex,
                                                (thisLast - thisDotIndex))) {
                        return false;
                    }
                }
                else {
                    // Has to fully match.
                    if ((sLast - sCurrent) != (thisLast - thisCurrent) ||
                        !selector.regionMatches(sCurrent, name, thisCurrent,
                                                (thisLast - thisCurrent))) {
                        return false;
                    }
                }
                return true;
            }
            if (sPoundIndex != -1) {
                // Selector has a '#', which indicates name must match it,
                // or if the '#' starts the selector than name must have
                // the same id (doesn't matter what element name).
                if (thisPoundIndex == -1) {
                    return false;
                }
                if (sCurrent == sPoundIndex) {
                    if ((thisLast - thisPoundIndex) !=(sLast - sPoundIndex) ||
                        !selector.regionMatches(sCurrent, name, thisPoundIndex,
                                                (thisLast - thisPoundIndex))) {
                        return false;
                    }
                }
                else {
                    // Has to fully match.
                    if ((sLast - sCurrent) != (thisLast - thisCurrent) ||
                        !selector.regionMatches(sCurrent, name, thisCurrent,
                                               (thisLast - thisCurrent))) {
                        return false;
                    }
                }
                return true;
            }
            if (thisDotIndex != -1) {
                // Receiver references a class, just check element name.
                return (((thisDotIndex - thisCurrent) == (sLast - sCurrent)) &&
                        selector.regionMatches(sCurrent, name, thisCurrent,
                                               thisDotIndex - thisCurrent));
            }
            if (thisPoundIndex != -1) {
                // Receiver references an id, just check element name.
                return (((thisPoundIndex - thisCurrent) ==(sLast - sCurrent))&&
                        selector.regionMatches(sCurrent, name, thisCurrent,
                                               thisPoundIndex - thisCurrent));
            }
            // Fail through, no classes or ides, just check string.
            return (((thisLast - thisCurrent) == (sLast - sCurrent)) &&
                    selector.regionMatches(sCurrent, name, thisCurrent,
                                           thisLast - thisCurrent));
        }

        /**
         * Similar to String.indexOf, but allows an upper bound
         * (this is slower in that it will still check string starting at
         * start.
         * <p>
         *  类似于String.indexOf,但允许上限(这是慢的,因为它仍然检查字符串开始从开始。
         * 
         */
        int boundedIndexOf(String string, char search, int start,
                           int end) {
            int retValue = string.indexOf(search, start);
            if (retValue >= end) {
                return -1;
            }
            return retValue;
        }

        public void addAttribute(Object name, Object value) {}
        public void addAttributes(AttributeSet attributes) {}
        public void removeAttribute(Object name) {}
        public void removeAttributes(Enumeration<?> names) {}
        public void removeAttributes(AttributeSet attributes) {}
        public void setResolveParent(AttributeSet parent) {}
        public String getName() {return name;}
        public void addChangeListener(ChangeListener l) {}
        public void removeChangeListener(ChangeListener l) {}
        public ChangeListener[] getChangeListeners() {
            return new ChangeListener[0];
        }

        /** The name of the Style, which is the selector.
         * This will NEVER change!
         * <p>
         *  这将永远不会改变！
         * 
         */
        String name;
        /** Start index of styles coming from other StyleSheets. */
        private int extendedIndex;
    }


    /**
     * SelectorMapping contains a specifitiy, as an integer, and an associated
     * Style. It can also reference children <code>SelectorMapping</code>s,
     * so that it behaves like a tree.
     * <p>
     * This is not thread safe, it is assumed the caller will take the
     * necessary precations if this is to be used in a threaded environment.
     * <p>
     *  SelectorMapping包含一个作为整数的规范和一个关联的样式。它还可以引用children <code> SelectorMapping </code>,以便它的行为像树。
     * <p>
     *  这不是线程安全的,假定调用者将采取必要的预测,如果这是要在线程环境中使用。
     * 
     */
    static class SelectorMapping implements Serializable {
        public SelectorMapping(int specificity) {
            this.specificity = specificity;
        }

        /**
         * Returns the specificity this mapping represents.
         * <p>
         *  返回此映射表示的特异性。
         * 
         */
        public int getSpecificity() {
            return specificity;
        }

        /**
         * Sets the Style associated with this mapping.
         * <p>
         *  设置与此映射关联的样式。
         * 
         */
        public void setStyle(Style style) {
            this.style = style;
        }

        /**
         * Returns the Style associated with this mapping.
         * <p>
         *  返回与此映射关联的样式。
         * 
         */
        public Style getStyle() {
            return style;
        }

        /**
         * Returns the child mapping identified by the simple selector
         * <code>selector</code>. If a child mapping does not exist for
         *<code>selector</code>, and <code>create</code> is true, a new
         * one will be created.
         * <p>
         * 返回由简单选择器<code> selector </code>标识的子映射。
         * 如果代码> selector </code>不存在子映射,并且<code> create </code>为true,则将创建一个新映射。
         * 
         */
        public SelectorMapping getChildSelectorMapping(String selector,
                                                       boolean create) {
            SelectorMapping retValue = null;

            if (children != null) {
                retValue = children.get(selector);
            }
            else if (create) {
                children = new HashMap<String, SelectorMapping>(7);
            }
            if (retValue == null && create) {
                int specificity = getChildSpecificity(selector);

                retValue = createChildSelectorMapping(specificity);
                children.put(selector, retValue);
            }
            return retValue;
        }

        /**
         * Creates a child <code>SelectorMapping</code> with the specified
         * <code>specificity</code>.
         * <p>
         *  创建具有指定的<code>特殊性</code>的<code> SelectorMapping </code>子元素。
         * 
         */
        protected SelectorMapping createChildSelectorMapping(int specificity) {
            return new SelectorMapping(specificity);
        }

        /**
         * Returns the specificity for the child selector
         * <code>selector</code>.
         * <p>
         *  返回子选择器<code> selector </code>的特殊性。
         * 
         */
        protected int getChildSpecificity(String selector) {
            // class (.) 100
            // id (#)    10000
            char    firstChar = selector.charAt(0);
            int     specificity = getSpecificity();

            if (firstChar == '.') {
                specificity += 100;
            }
            else if (firstChar == '#') {
                specificity += 10000;
            }
            else {
                specificity += 1;
                if (selector.indexOf('.') != -1) {
                    specificity += 100;
                }
                if (selector.indexOf('#') != -1) {
                    specificity += 10000;
                }
            }
            return specificity;
        }

        /**
         * The specificity for this selector.
         * <p>
         *  此选择器的特殊性。
         * 
         */
        private int specificity;
        /**
         * Style for this selector.
         * <p>
         *  此选择器的样式。
         * 
         */
        private Style style;
        /**
         * Any sub selectors. Key will be String, and value will be
         * another SelectorMapping.
         * <p>
         *  任何子选择器。键将是String,值将是另一个SelectorMapping。
         * 
         */
        private HashMap<String, SelectorMapping> children;
    }


    // ---- Variables ---------------------------------------------

    final static int DEFAULT_FONT_SIZE = 3;

    private CSS css;

    /**
     * An inverted graph of the selectors.
     * <p>
     *  选择器的反向图。
     * 
     */
    private SelectorMapping selectorMapping;

    /** Maps from selector (as a string) to Style that includes all
    /* <p>
    /* 
     * relevant styles. */
    private Hashtable<String, ResolvedStyle> resolvedStyles;

    /** Vector of StyleSheets that the rules are to reference.
    /* <p>
     */
    private Vector<StyleSheet> linkedStyleSheets;

    /** Where the style sheet was found. Used for relative imports. */
    private URL base;


    /**
     * Default parser for CSS specifications that get loaded into
     * the StyleSheet.<p>
     * This class is NOT thread safe, do not ask it to parse while it is
     * in the middle of parsing.
     * <p>
     *  默认解析器为CSS规范被加载到StyleSheet。<p>这个类不是线程安全的,不要求它解析,而它正在解析。
     * 
     */
    class CssParser implements CSSParser.CSSParserCallback {

        /**
         * Parses the passed in CSS declaration into an AttributeSet.
         * <p>
         *  将在CSS声明中传递的值解析为AttributeSet。
         * 
         */
        public AttributeSet parseDeclaration(String string) {
            try {
                return parseDeclaration(new StringReader(string));
            } catch (IOException ioe) {}
            return null;
        }

        /**
         * Parses the passed in CSS declaration into an AttributeSet.
         * <p>
         *  将在CSS声明中传递的值解析为AttributeSet。
         * 
         */
        public AttributeSet parseDeclaration(Reader r) throws IOException {
            parse(base, r, true, false);
            return declaration.copyAttributes();
        }

        /**
         * Parse the given CSS stream
         * <p>
         *  解析给定的CSS流
         * 
         */
        public void parse(URL base, Reader r, boolean parseDeclaration,
                          boolean isLink) throws IOException {
            this.base = base;
            this.isLink = isLink;
            this.parsingDeclaration = parseDeclaration;
            declaration.removeAttributes(declaration);
            selectorTokens.removeAllElements();
            selectors.removeAllElements();
            propertyName = null;
            parser.parse(r, this, parseDeclaration);
        }

        //
        // CSSParserCallback methods, public to implement the interface.
        //

        /**
         * Invoked when a valid @import is encountered, will call
         * <code>importStyleSheet</code> if a
         * <code>MalformedURLException</code> is not thrown in creating
         * the URL.
         * <p>
         *  在遇到有效的@import时调用,如果在创建URL时未引发<code> MalformedURLException </code>,则将调用<code> importStyleSheet </code>
         * 。
         * 
         */
        public void handleImport(String importString) {
            URL url = CSS.getURL(base, importString);
            if (url != null) {
                importStyleSheet(url);
            }
        }

        /**
         * A selector has been encountered.
         * <p>
         *  遇到选择器。
         * 
         */
        public void handleSelector(String selector) {
            //class and index selectors are case sensitive
            if (!(selector.startsWith(".")
                  || selector.startsWith("#"))) {
                selector = selector.toLowerCase();
            }
            int length = selector.length();

            if (selector.endsWith(",")) {
                if (length > 1) {
                    selector = selector.substring(0, length - 1);
                    selectorTokens.addElement(selector);
                }
                addSelector();
            }
            else if (length > 0) {
                selectorTokens.addElement(selector);
            }
        }

        /**
         * Invoked when the start of a rule is encountered.
         * <p>
         *  在遇到规则的开始时调用。
         * 
         */
        public void startRule() {
            if (selectorTokens.size() > 0) {
                addSelector();
            }
            propertyName = null;
        }

        /**
         * Invoked when a property name is encountered.
         * <p>
         *  在遇到属性名称时调用。
         * 
         */
        public void handleProperty(String property) {
            propertyName = property;
        }

        /**
         * Invoked when a property value is encountered.
         * <p>
         *  在遇到属性值时调用。
         * 
         */
        public void handleValue(String value) {
            if (propertyName != null && value != null && value.length() > 0) {
                CSS.Attribute cssKey = CSS.getAttribute(propertyName);
                if (cssKey != null) {
                    // There is currently no mechanism to determine real
                    // base that style sheet was loaded from. For the time
                    // being, this maps for LIST_STYLE_IMAGE, which appear
                    // to be the only one that currently matters. A more
                    // general mechanism is definately needed.
                    if (cssKey == CSS.Attribute.LIST_STYLE_IMAGE) {
                        if (value != null && !value.equals("none")) {
                            URL url = CSS.getURL(base, value);

                            if (url != null) {
                                value = url.toString();
                            }
                        }
                    }
                    addCSSAttribute(declaration, cssKey, value);
                }
                propertyName = null;
            }
        }

        /**
         * Invoked when the end of a rule is encountered.
         * <p>
         *  在遇到规则的结尾时调用。
         * 
         */
        public void endRule() {
            int n = selectors.size();
            for (int i = 0; i < n; i++) {
                String[] selector = selectors.elementAt(i);
                if (selector.length > 0) {
                    StyleSheet.this.addRule(selector, declaration, isLink);
                }
            }
            declaration.removeAttributes(declaration);
            selectors.removeAllElements();
        }

        private void addSelector() {
            String[] selector = new String[selectorTokens.size()];
            selectorTokens.copyInto(selector);
            selectors.addElement(selector);
            selectorTokens.removeAllElements();
        }


        Vector<String[]> selectors = new Vector<String[]>();
        Vector<String> selectorTokens = new Vector<String>();
        /** Name of the current property. */
        String propertyName;
        MutableAttributeSet declaration = new SimpleAttributeSet();
        /** True if parsing a declaration, that is the Reader will not
        /* <p>
        /* 
         * contain a selector. */
        boolean parsingDeclaration;
        /** True if the attributes are coming from a linked/imported style. */
        boolean isLink;
        /** Where the CSS stylesheet lives. */
        URL base;
        CSSParser parser = new CSSParser();
    }

    void rebaseSizeMap(int base) {
        final int minimalFontSize = 4;
        sizeMap = new int[sizeMapDefault.length];
        for (int i = 0; i < sizeMapDefault.length; i++) {
            sizeMap[i] = Math.max(base * sizeMapDefault[i] /
                                  sizeMapDefault[CSS.baseFontSizeIndex],
                                  minimalFontSize);
        }

    }

    int[] getSizeMap() {
        return sizeMap;
    }
    boolean isW3CLengthUnits() {
        return w3cLengthUnits;
    }

    /**
     * The HTML/CSS size model has seven slots
     * that one can assign sizes to.
     * <p>
     *  HTML / CSS大小模型有七个插槽,可以分配大小。
     */
    static final int sizeMapDefault[] = { 8, 10, 12, 14, 18, 24, 36 };

    private int sizeMap[] = sizeMapDefault;
    private boolean w3cLengthUnits = false;
}
