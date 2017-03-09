/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 2006, Oracle and/or its affiliates. All rights reserved.
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
package javax.swing.plaf.basic;

import java.io.*;
import java.awt.*;
import java.net.URL;

import javax.swing.*;
import javax.swing.text.*;
import javax.swing.text.html.*;

import sun.swing.SwingUtilities2;

/**
 * Support for providing html views for the swing components.
 * This translates a simple html string to a javax.swing.text.View
 * implementation that can render the html and provide the necessary
 * layout semantics.
 *
 * <p>
 *  支持为swing组件提供html视图。这将一个简单的html字符串转换为一个javax.swing.text.View实现,它可以渲染html并提供必要的布局语义。
 * 
 * 
 * @author  Timothy Prinzing
 * @since 1.3
 */
public class BasicHTML {

    /**
     * Create an html renderer for the given component and
     * string of html.
     * <p>
     *  为给定的组件和html的字符串创建一个html渲染器。
     * 
     */
    public static View createHTMLView(JComponent c, String html) {
        BasicEditorKit kit = getFactory();
        Document doc = kit.createDefaultDocument(c.getFont(),
                                                 c.getForeground());
        Object base = c.getClientProperty(documentBaseKey);
        if (base instanceof URL) {
            ((HTMLDocument)doc).setBase((URL)base);
        }
        Reader r = new StringReader(html);
        try {
            kit.read(r, doc, 0);
        } catch (Throwable e) {
        }
        ViewFactory f = kit.getViewFactory();
        View hview = f.create(doc.getDefaultRootElement());
        View v = new Renderer(c, f, hview);
        return v;
    }

    /**
     * Returns the baseline for the html renderer.
     *
     * <p>
     *  返回html渲染器的基线。
     * 
     * 
     * @param view the View to get the baseline for
     * @param w the width to get the baseline for
     * @param h the height to get the baseline for
     * @throws IllegalArgumentException if width or height is &lt; 0
     * @return baseline or a value &lt; 0 indicating there is no reasonable
     *                  baseline
     * @see java.awt.FontMetrics
     * @see javax.swing.JComponent#getBaseline(int,int)
     * @since 1.6
     */
    public static int getHTMLBaseline(View view, int w, int h) {
        if (w < 0 || h < 0) {
            throw new IllegalArgumentException(
                    "Width and height must be >= 0");
        }
        if (view instanceof Renderer) {
            return getBaseline(view.getView(0), w, h);
        }
        return -1;
    }

    /**
     * Gets the baseline for the specified component.  This digs out
     * the View client property, and if non-null the baseline is calculated
     * from it.  Otherwise the baseline is the value <code>y + ascent</code>.
     * <p>
     *  获取指定组件的基线。这将挖掘View客户端属性,如果非空,则从中计算基线。否则,基线是值<code> y + ascent </code>。
     * 
     */
    static int getBaseline(JComponent c, int y, int ascent,
                                  int w, int h) {
        View view = (View)c.getClientProperty(BasicHTML.propertyKey);
        if (view != null) {
            int baseline = getHTMLBaseline(view, w, h);
            if (baseline < 0) {
                return baseline;
            }
            return y + baseline;
        }
        return y + ascent;
    }

    /**
     * Gets the baseline for the specified View.
     * <p>
     *  获取指定视图的基线。
     * 
     */
    static int getBaseline(View view, int w, int h) {
        if (hasParagraph(view)) {
            view.setSize(w, h);
            return getBaseline(view, new Rectangle(0, 0, w, h));
        }
        return -1;
    }

    private static int getBaseline(View view, Shape bounds) {
        if (view.getViewCount() == 0) {
            return -1;
        }
        AttributeSet attributes = view.getElement().getAttributes();
        Object name = null;
        if (attributes != null) {
            name = attributes.getAttribute(StyleConstants.NameAttribute);
        }
        int index = 0;
        if (name == HTML.Tag.HTML && view.getViewCount() > 1) {
            // For html on widgets the header is not visible, skip it.
            index++;
        }
        bounds = view.getChildAllocation(index, bounds);
        if (bounds == null) {
            return -1;
        }
        View child = view.getView(index);
        if (view instanceof javax.swing.text.ParagraphView) {
            Rectangle rect;
            if (bounds instanceof Rectangle) {
                rect = (Rectangle)bounds;
            }
            else {
                rect = bounds.getBounds();
            }
            return rect.y + (int)(rect.height *
                                  child.getAlignment(View.Y_AXIS));
        }
        return getBaseline(child, bounds);
    }

    private static boolean hasParagraph(View view) {
        if (view instanceof javax.swing.text.ParagraphView) {
            return true;
        }
        if (view.getViewCount() == 0) {
            return false;
        }
        AttributeSet attributes = view.getElement().getAttributes();
        Object name = null;
        if (attributes != null) {
            name = attributes.getAttribute(StyleConstants.NameAttribute);
        }
        int index = 0;
        if (name == HTML.Tag.HTML && view.getViewCount() > 1) {
            // For html on widgets the header is not visible, skip it.
            index = 1;
        }
        return hasParagraph(view.getView(index));
    }

    /**
     * Check the given string to see if it should trigger the
     * html rendering logic in a non-text component that supports
     * html rendering.
     * <p>
     *  检查给定的字符串,看看是否应该在支持html呈现的非文本组件中触发html呈现逻辑。
     * 
     */
    public static boolean isHTMLString(String s) {
        if (s != null) {
            if ((s.length() >= 6) && (s.charAt(0) == '<') && (s.charAt(5) == '>')) {
                String tag = s.substring(1,5);
                return tag.equalsIgnoreCase(propertyKey);
            }
        }
        return false;
    }

    /**
     * Stash the HTML render for the given text into the client
     * properties of the given JComponent. If the given text is
     * <em>NOT HTML</em> the property will be cleared of any
     * renderer.
     * <p>
     * This method is useful for ComponentUI implementations
     * that are static (i.e. shared) and get their state
     * entirely from the JComponent.
     * <p>
     *  将给定文本的HTML呈现隐藏到给定JComponent的客户端属性中。如果给定的文本是<em> NOT HTML </em>,该属性将被清除任何渲染器。
     * <p>
     *  此方法对于静态(即共享)的ComponentUI实现非常有用,并且完全从JComponent获取其状态。
     * 
     */
    public static void updateRenderer(JComponent c, String text) {
        View value = null;
        View oldValue = (View)c.getClientProperty(BasicHTML.propertyKey);
        Boolean htmlDisabled = (Boolean) c.getClientProperty(htmlDisable);
        if (htmlDisabled != Boolean.TRUE && BasicHTML.isHTMLString(text)) {
            value = BasicHTML.createHTMLView(c, text);
        }
        if (value != oldValue && oldValue != null) {
            for (int i = 0; i < oldValue.getViewCount(); i++) {
                oldValue.getView(i).setParent(null);
            }
        }
        c.putClientProperty(BasicHTML.propertyKey, value);
    }

    /**
     * If this client property of a JComponent is set to Boolean.TRUE
     * the component's 'text' property is never treated as HTML.
     * <p>
     *  如果JComponent的此客户端属性设置为Boolean.TRUE,组件的"text"属性不会被视为HTML。
     * 
     */
    private static final String htmlDisable = "html.disable";

    /**
     * Key to use for the html renderer when stored as a
     * client property of a JComponent.
     * <p>
     *  存储为JComponent的客户端属性时用于html渲染器的键。
     * 
     */
    public static final String propertyKey = "html";

    /**
     * Key stored as a client property to indicate the base that relative
     * references are resolved against. For example, lets say you keep
     * your images in the directory resources relative to the code path,
     * you would use the following the set the base:
     * <pre>
     *   jComponent.putClientProperty(documentBaseKey,
     *                                xxx.class.getResource("resources/"));
     * </pre>
     * <p>
     * 作为客户端属性存储的键,以指示相对引用被解析的基础。例如,让我们说你把你的图像保存在相对于代码路径的目录资源中,你将使用以下设置基础：
     * <pre>
     *  jComponent.putClientProperty(documentBaseKey,xxx.class.getResource("resources /"));
     * </pre>
     */
    public static final String documentBaseKey = "html.base";

    static BasicEditorKit getFactory() {
        if (basicHTMLFactory == null) {
            basicHTMLViewFactory = new BasicHTMLViewFactory();
            basicHTMLFactory = new BasicEditorKit();
        }
        return basicHTMLFactory;
    }

    /**
     * The source of the html renderers
     * <p>
     *  html渲染器的来源
     * 
     */
    private static BasicEditorKit basicHTMLFactory;

    /**
     * Creates the Views that visually represent the model.
     * <p>
     *  创建可视地表示模型的视图。
     * 
     */
    private static ViewFactory basicHTMLViewFactory;

    /**
     * Overrides to the default stylesheet.  Should consider
     * just creating a completely fresh stylesheet.
     * <p>
     *  覆盖默认样式表。应该考虑创建一个完全新的样式表。
     * 
     */
    private static final String styleChanges =
    "p { margin-top: 0; margin-bottom: 0; margin-left: 0; margin-right: 0 }" +
    "body { margin-top: 0; margin-bottom: 0; margin-left: 0; margin-right: 0 }";

    /**
     * The views produced for the ComponentUI implementations aren't
     * going to be edited and don't need full html support.  This kit
     * alters the HTMLEditorKit to try and trim things down a bit.
     * It does the following:
     * <ul>
     * <li>It doesn't produce Views for things like comments,
     * head, title, unknown tags, etc.
     * <li>It installs a different set of css settings from the default
     * provided by HTMLEditorKit.
     * </ul>
     * <p>
     *  为ComponentUI实现生成的视图不会被编辑,并且不需要完整的html支持。这个工具包改变了HTMLEditorKit,尝试和修剪一些东西。它执行以下操作：
     * <ul>
     *  <li>它不会对注释,标题,标题,未知标记等内容产生观看次数。<li>它会根据HTMLEditorKit提供的默认设置安装不同的CSS设置。
     * </ul>
     */
    static class BasicEditorKit extends HTMLEditorKit {
        /** Shared base style for all documents created by us use. */
        private static StyleSheet defaultStyles;

        /**
         * Overriden to return our own slimmed down style sheet.
         * <p>
         *  覆盖返回我们自己的苗条样式表。
         * 
         */
        public StyleSheet getStyleSheet() {
            if (defaultStyles == null) {
                defaultStyles = new StyleSheet();
                StringReader r = new StringReader(styleChanges);
                try {
                    defaultStyles.loadRules(r, null);
                } catch (Throwable e) {
                    // don't want to die in static initialization...
                    // just display things wrong.
                }
                r.close();
                defaultStyles.addStyleSheet(super.getStyleSheet());
            }
            return defaultStyles;
        }

        /**
         * Sets the async policy to flush everything in one chunk, and
         * to not display unknown tags.
         * <p>
         *  设置异步策略以刷新一个块中的所有内容,并且不显示未知标记。
         * 
         */
        public Document createDefaultDocument(Font defaultFont,
                                              Color foreground) {
            StyleSheet styles = getStyleSheet();
            StyleSheet ss = new StyleSheet();
            ss.addStyleSheet(styles);
            BasicDocument doc = new BasicDocument(ss, defaultFont, foreground);
            doc.setAsynchronousLoadPriority(Integer.MAX_VALUE);
            doc.setPreservesUnknownTags(false);
            return doc;
        }

        /**
         * Returns the ViewFactory that is used to make sure the Views don't
         * load in the background.
         * <p>
         *  返回ViewFactory,用于确保视图不会在后台加载。
         * 
         */
        public ViewFactory getViewFactory() {
            return basicHTMLViewFactory;
        }
    }


    /**
     * BasicHTMLViewFactory extends HTMLFactory to force images to be loaded
     * synchronously.
     * <p>
     *  BasicHTMLViewFactory扩展HTMLFactory以强制同步加载图像。
     * 
     */
    static class BasicHTMLViewFactory extends HTMLEditorKit.HTMLFactory {
        public View create(Element elem) {
            View view = super.create(elem);

            if (view instanceof ImageView) {
                ((ImageView)view).setLoadsSynchronously(true);
            }
            return view;
        }
    }


    /**
     * The subclass of HTMLDocument that is used as the model. getForeground
     * is overridden to return the foreground property from the Component this
     * was created for.
     * <p>
     *  用作模型的HTMLDocument的子类。 getForeground被重写以从创建的组件返回前景属性。
     * 
     */
    static class BasicDocument extends HTMLDocument {
        /** The host, that is where we are rendering. */
        // private JComponent host;

        BasicDocument(StyleSheet s, Font defaultFont, Color foreground) {
            super(s);
            setPreservesUnknownTags(false);
            setFontAndColor(defaultFont, foreground);
        }

        /**
         * Sets the default font and default color. These are set by
         * adding a rule for the body that specifies the font and color.
         * This allows the html to override these should it wish to have
         * a custom font or color.
         * <p>
         * 设置默认字体和默认颜色。这些是通过为指定字体和颜色的正文添加规则来设置的。这允许html重写这些,如果它希望有一个自定义字体或颜色。
         * 
         */
        private void setFontAndColor(Font font, Color fg) {
            getStyleSheet().addRule(sun.swing.SwingUtilities2.
                                    displayPropertiesToCSS(font,fg));
        }
    }


    /**
     * Root text view that acts as an HTML renderer.
     * <p>
     *  充当HTML呈现器的根文本视图。
     * 
     */
    static class Renderer extends View {

        Renderer(JComponent c, ViewFactory f, View v) {
            super(null);
            host = c;
            factory = f;
            view = v;
            view.setParent(this);
            // initially layout to the preferred size
            setSize(view.getPreferredSpan(X_AXIS), view.getPreferredSpan(Y_AXIS));
        }

        /**
         * Fetches the attributes to use when rendering.  At the root
         * level there are no attributes.  If an attribute is resolved
         * up the view hierarchy this is the end of the line.
         * <p>
         *  获取渲染时要使用的属性。在根级别没有属性。如果属性在视图层次结构中被解析,则这是行的结尾。
         * 
         */
        public AttributeSet getAttributes() {
            return null;
        }

        /**
         * Determines the preferred span for this view along an axis.
         *
         * <p>
         *  确定沿着轴的此视图的首选跨度。
         * 
         * 
         * @param axis may be either X_AXIS or Y_AXIS
         * @return the span the view would like to be rendered into.
         *         Typically the view is told to render into the span
         *         that is returned, although there is no guarantee.
         *         The parent may choose to resize or break the view.
         */
        public float getPreferredSpan(int axis) {
            if (axis == X_AXIS) {
                // width currently laid out to
                return width;
            }
            return view.getPreferredSpan(axis);
        }

        /**
         * Determines the minimum span for this view along an axis.
         *
         * <p>
         *  确定沿轴的此视图的最小跨度。
         * 
         * 
         * @param axis may be either X_AXIS or Y_AXIS
         * @return the span the view would like to be rendered into.
         *         Typically the view is told to render into the span
         *         that is returned, although there is no guarantee.
         *         The parent may choose to resize or break the view.
         */
        public float getMinimumSpan(int axis) {
            return view.getMinimumSpan(axis);
        }

        /**
         * Determines the maximum span for this view along an axis.
         *
         * <p>
         *  确定沿轴的此视图的最大跨度。
         * 
         * 
         * @param axis may be either X_AXIS or Y_AXIS
         * @return the span the view would like to be rendered into.
         *         Typically the view is told to render into the span
         *         that is returned, although there is no guarantee.
         *         The parent may choose to resize or break the view.
         */
        public float getMaximumSpan(int axis) {
            return Integer.MAX_VALUE;
        }

        /**
         * Specifies that a preference has changed.
         * Child views can call this on the parent to indicate that
         * the preference has changed.  The root view routes this to
         * invalidate on the hosting component.
         * <p>
         * This can be called on a different thread from the
         * event dispatching thread and is basically unsafe to
         * propagate into the component.  To make this safe,
         * the operation is transferred over to the event dispatching
         * thread for completion.  It is a design goal that all view
         * methods be safe to call without concern for concurrency,
         * and this behavior helps make that true.
         *
         * <p>
         *  指定首选项已更改。子视图可以在父对象上调用此选项来指示首选项已更改。根视图将此路由到主机组件上的invalidate。
         * <p>
         *  这可以在与事件分派线程不同的线程上被调用,并且基本上不安全地传播到组件中。为了使这个安全,操作被转移到事件分派线程完成。
         * 这是一个设计目标,所有的视图方法可以安全地调用而不关心并发,这种行为有助于使真实。
         * 
         * 
         * @param child the child view
         * @param width true if the width preference has changed
         * @param height true if the height preference has changed
         */
        public void preferenceChanged(View child, boolean width, boolean height) {
            host.revalidate();
            host.repaint();
        }

        /**
         * Determines the desired alignment for this view along an axis.
         *
         * <p>
         *  确定沿着轴的该视图的期望对准。
         * 
         * 
         * @param axis may be either X_AXIS or Y_AXIS
         * @return the desired alignment, where 0.0 indicates the origin
         *     and 1.0 the full span away from the origin
         */
        public float getAlignment(int axis) {
            return view.getAlignment(axis);
        }

        /**
         * Renders the view.
         *
         * <p>
         *  渲染视图。
         * 
         * 
         * @param g the graphics context
         * @param allocation the region to render into
         */
        public void paint(Graphics g, Shape allocation) {
            Rectangle alloc = allocation.getBounds();
            view.setSize(alloc.width, alloc.height);
            view.paint(g, allocation);
        }

        /**
         * Sets the view parent.
         *
         * <p>
         *  设置视图父视图。
         * 
         * 
         * @param parent the parent view
         */
        public void setParent(View parent) {
            throw new Error("Can't set parent on root view");
        }

        /**
         * Returns the number of views in this view.  Since
         * this view simply wraps the root of the view hierarchy
         * it has exactly one child.
         *
         * <p>
         *  返回此视图中的视图数。因为这个视图只包含视图层次结构的根,它只有一个孩子。
         * 
         * 
         * @return the number of views
         * @see #getView
         */
        public int getViewCount() {
            return 1;
        }

        /**
         * Gets the n-th view in this container.
         *
         * <p>
         *  获取此容器中的第n个视图。
         * 
         * 
         * @param n the number of the view to get
         * @return the view
         */
        public View getView(int n) {
            return view;
        }

        /**
         * Provides a mapping from the document model coordinate space
         * to the coordinate space of the view mapped to it.
         *
         * <p>
         * 提供从文档模型坐标空间到映射到其的视图的坐标空间的映射。
         * 
         * 
         * @param pos the position to convert
         * @param a the allocated region to render into
         * @return the bounding box of the given position
         */
        public Shape modelToView(int pos, Shape a, Position.Bias b) throws BadLocationException {
            return view.modelToView(pos, a, b);
        }

        /**
         * Provides a mapping from the document model coordinate space
         * to the coordinate space of the view mapped to it.
         *
         * <p>
         *  提供从文档模型坐标空间到映射到其的视图的坐标空间的映射。
         * 
         * 
         * @param p0 the position to convert >= 0
         * @param b0 the bias toward the previous character or the
         *  next character represented by p0, in case the
         *  position is a boundary of two views.
         * @param p1 the position to convert >= 0
         * @param b1 the bias toward the previous character or the
         *  next character represented by p1, in case the
         *  position is a boundary of two views.
         * @param a the allocated region to render into
         * @return the bounding box of the given position is returned
         * @exception BadLocationException  if the given position does
         *   not represent a valid location in the associated document
         * @exception IllegalArgumentException for an invalid bias argument
         * @see View#viewToModel
         */
        public Shape modelToView(int p0, Position.Bias b0, int p1,
                                 Position.Bias b1, Shape a) throws BadLocationException {
            return view.modelToView(p0, b0, p1, b1, a);
        }

        /**
         * Provides a mapping from the view coordinate space to the logical
         * coordinate space of the model.
         *
         * <p>
         *  提供从视图坐标空间到模型的逻辑坐标空间的映射。
         * 
         * 
         * @param x x coordinate of the view location to convert
         * @param y y coordinate of the view location to convert
         * @param a the allocated region to render into
         * @return the location within the model that best represents the
         *    given point in the view
         */
        public int viewToModel(float x, float y, Shape a, Position.Bias[] bias) {
            return view.viewToModel(x, y, a, bias);
        }

        /**
         * Returns the document model underlying the view.
         *
         * <p>
         *  返回视图下的文档模型。
         * 
         * 
         * @return the model
         */
        public Document getDocument() {
            return view.getDocument();
        }

        /**
         * Returns the starting offset into the model for this view.
         *
         * <p>
         *  返回此视图的模型中的起始偏移量。
         * 
         * 
         * @return the starting offset
         */
        public int getStartOffset() {
            return view.getStartOffset();
        }

        /**
         * Returns the ending offset into the model for this view.
         *
         * <p>
         *  返回此视图的模型中的结束偏移量。
         * 
         * 
         * @return the ending offset
         */
        public int getEndOffset() {
            return view.getEndOffset();
        }

        /**
         * Gets the element that this view is mapped to.
         *
         * <p>
         *  获取此视图映射到的元素。
         * 
         * 
         * @return the view
         */
        public Element getElement() {
            return view.getElement();
        }

        /**
         * Sets the view size.
         *
         * <p>
         *  设置视图大小。
         * 
         * 
         * @param width the width
         * @param height the height
         */
        public void setSize(float width, float height) {
            this.width = (int) width;
            view.setSize(width, height);
        }

        /**
         * Fetches the container hosting the view.  This is useful for
         * things like scheduling a repaint, finding out the host
         * components font, etc.  The default implementation
         * of this is to forward the query to the parent view.
         *
         * <p>
         *  获取托管视图的容器。这对于调度重绘,查找主机组件字体等事情非常有用。默认实现是将查询转发到父视图。
         * 
         * 
         * @return the container
         */
        public Container getContainer() {
            return host;
        }

        /**
         * Fetches the factory to be used for building the
         * various view fragments that make up the view that
         * represents the model.  This is what determines
         * how the model will be represented.  This is implemented
         * to fetch the factory provided by the associated
         * EditorKit.
         *
         * <p>
         *  获取用于构建构成表示模型的视图的各种视图片段的工厂。这是决定如何表示模型的方法。这被实现为获取由相关联的EditorKit提供的工厂。
         * 
         * @return the factory
         */
        public ViewFactory getViewFactory() {
            return factory;
        }

        private int width;
        private View view;
        private ViewFactory factory;
        private JComponent host;

    }
}
