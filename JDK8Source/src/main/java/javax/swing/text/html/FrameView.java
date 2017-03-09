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
package javax.swing.text.html;

import java.awt.*;
import java.util.*;
import java.net.*;
import java.io.*;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.event.*;

import sun.swing.text.html.FrameEditorPaneTag;

/**
 * Implements a FrameView, intended to support the HTML
 * &lt;FRAME&gt; tag.  Supports the frameborder, scrolling,
 * marginwidth and marginheight attributes.
 *
 * <p>
 *  实现FrameView,旨在支持HTML&lt; FRAME&gt;标签。支持frameborder,scrolling,marginwidth和marginheight属性。
 * 
 * 
 * @author    Sunita Mani
 */

class FrameView extends ComponentView implements HyperlinkListener {


    JEditorPane htmlPane;
    JScrollPane scroller;
    boolean editable;
    float width;
    float height;
    URL src;
    /** Set to true when the component has been created. */
    private boolean createdComponent;

    /**
     * Creates a new Frame.
     *
     * <p>
     *  创建新的帧。
     * 
     * 
     * @param elem the element to represent.
     */
    public FrameView(Element elem) {
        super(elem);
    }

    protected Component createComponent() {

        Element elem = getElement();
        AttributeSet attributes = elem.getAttributes();
        String srcAtt = (String)attributes.getAttribute(HTML.Attribute.SRC);

        if ((srcAtt != null) && (!srcAtt.equals(""))) {
            try {
                URL base = ((HTMLDocument)elem.getDocument()).getBase();
                src = new URL(base, srcAtt);
                htmlPane = new FrameEditorPane();
                htmlPane.addHyperlinkListener(this);
                JEditorPane host = getHostPane();
                boolean isAutoFormSubmission = true;
                if (host != null) {
                    htmlPane.setEditable(host.isEditable());
                    String charset = (String) host.getClientProperty("charset");
                    if (charset != null) {
                        htmlPane.putClientProperty("charset", charset);
                    }
                    HTMLEditorKit hostKit = (HTMLEditorKit)host.getEditorKit();
                    if (hostKit != null) {
                        isAutoFormSubmission = hostKit.isAutoFormSubmission();
                    }
                }
                htmlPane.setPage(src);
                HTMLEditorKit kit = (HTMLEditorKit)htmlPane.getEditorKit();
                if (kit != null) {
                    kit.setAutoFormSubmission(isAutoFormSubmission);
                }

                Document doc = htmlPane.getDocument();
                if (doc instanceof HTMLDocument) {
                    ((HTMLDocument)doc).setFrameDocumentState(true);
                }
                setMargin();
                createScrollPane();
                setBorder();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        createdComponent = true;
        return scroller;
    }

    JEditorPane getHostPane() {
        Container c = getContainer();
        while ((c != null) && ! (c instanceof JEditorPane)) {
            c = c.getParent();
        }
        return (JEditorPane) c;
    }


    /**
     * Sets the parent view for the FrameView.
     * Also determines if the FrameView should be editable
     * or not based on whether the JTextComponent that
     * contains it is editable.
     *
     * <p>
     *  设置FrameView的父视图。还根据包含它的JTextComponent是否可编辑来确定FrameView是否应该是可编辑的。
     * 
     * 
     * @param parent View
     */
    public void setParent(View parent) {
        if (parent != null) {
            JTextComponent t = (JTextComponent)parent.getContainer();
            editable = t.isEditable();
        }
        super.setParent(parent);
    }


    /**
     * Also determines if the FrameView should be editable
     * or not based on whether the JTextComponent that
     * contains it is editable. And then proceeds to call
     * the superclass to do the paint().
     *
     * <p>
     *  还根据包含它的JTextComponent是否可编辑来确定FrameView是否应该是可编辑的。然后继续调用超类做paint()。
     * 
     * 
     * @param parent View
     * @see text.ComponentView#paint
     */
    public void paint(Graphics g, Shape allocation) {

        Container host = getContainer();
        if (host != null && htmlPane != null &&
            htmlPane.isEditable() != ((JTextComponent)host).isEditable()) {
            editable = ((JTextComponent)host).isEditable();
            htmlPane.setEditable(editable);
        }
        super.paint(g, allocation);
    }


    /**
     * If the marginwidth or marginheight attributes have been specified,
     * then the JEditorPane's margin's are set to the new values.
     * <p>
     *  如果已指定marginwidth或marginheight属性,则将JEditorPane的边距设置为新值。
     * 
     */
    private void setMargin() {
        int margin = 0;
        Insets in = htmlPane.getMargin();
        Insets newInsets;
        boolean modified = false;
        AttributeSet attributes = getElement().getAttributes();
        String marginStr = (String)attributes.getAttribute(HTML.Attribute.MARGINWIDTH);
        if ( in != null) {
            newInsets = new Insets(in.top, in.left, in.right, in.bottom);
        } else {
            newInsets = new Insets(0,0,0,0);
        }
        if (marginStr != null) {
            margin = Integer.parseInt(marginStr);
            if (margin > 0) {
                newInsets.left = margin;
                newInsets.right = margin;
                modified = true;
            }
        }
        marginStr = (String)attributes.getAttribute(HTML.Attribute.MARGINHEIGHT);
        if (marginStr != null) {
            margin = Integer.parseInt(marginStr);
            if (margin > 0) {
                newInsets.top = margin;
                newInsets.bottom = margin;
                modified = true;
            }
        }
        if (modified) {
            htmlPane.setMargin(newInsets);
        }
    }

    /**
     * If the frameborder attribute has been specified, either in the frame,
     * or by the frames enclosing frameset, the JScrollPane's setBorder()
     * method is invoked to achieve the desired look.
     * <p>
     *  如果已经指定frameborder属性,或者在帧中,或者通过包围框架集的帧,JScrollPane的setBorder()方法被调用以实现所需的外观。
     * 
     */
    private void setBorder() {

        AttributeSet attributes = getElement().getAttributes();
        String frameBorder = (String)attributes.getAttribute(HTML.Attribute.FRAMEBORDER);
        if ((frameBorder != null) &&
            (frameBorder.equals("no") || frameBorder.equals("0"))) {
            // make invisible borders.
            scroller.setBorder(null);
        }
    }


    /**
     * This method creates the JScrollPane.  The scrollbar policy is determined by
     * the scrolling attribute.  If not defined, the default is "auto" which
     * maps to the scrollbar's being displayed as needed.
     * <p>
     *  此方法创建JScrollPane。滚动条策略由滚动属性确定。如果未定义,默认值为"auto",映射到根据需要显示的滚动条。
     * 
     */
    private void createScrollPane() {
        AttributeSet attributes = getElement().getAttributes();
        String scrolling = (String)attributes.getAttribute(HTML.Attribute.SCROLLING);
        if (scrolling == null) {
            scrolling = "auto";
        }

        if (!scrolling.equals("no")) {
            if (scrolling.equals("yes")) {
                scroller = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                                           JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            } else {
                // scrollbars will be displayed if needed
                //
                scroller = new JScrollPane();
            }
        } else {
            scroller = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_NEVER,
                                       JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        }

        JViewport vp = scroller.getViewport();
        vp.add(htmlPane);
        vp.setBackingStoreEnabled(true);
        scroller.setMinimumSize(new Dimension(5,5));
        scroller.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
    }


    /**
     * Finds the outermost FrameSetView.  It then
     * returns that FrameSetView's container.
     * <p>
     *  查找最外面的FrameSetView。然后它返回FrameSetView的容器。
     * 
     */
    JEditorPane getOutermostJEditorPane() {

        View parent = getParent();
        FrameSetView frameSetView = null;
        while (parent != null) {
            if (parent instanceof FrameSetView) {
                frameSetView = (FrameSetView)parent;
            }
            parent = parent.getParent();
        }
        if (frameSetView != null) {
            return (JEditorPane)frameSetView.getContainer();
        }
        return null;
    }


    /**
     * Returns true if this frame is contained within
     * a nested frameset.
     * <p>
     *  如果此框架包含在嵌套框架集中,则返回true。
     * 
     */
    private boolean inNestedFrameSet() {
        FrameSetView parent = (FrameSetView)getParent();
        return (parent.getParent() instanceof FrameSetView);
    }


    /**
     * Notification of a change relative to a
     * hyperlink. This method searches for the outermost
     * JEditorPane, and then fires an HTMLFrameHyperlinkEvent
     * to that frame.  In addition, if the target is _parent,
     * and there is not nested framesets then the target is
     * reset to _top.  If the target is _top, in addition to
     * firing the event to the outermost JEditorPane, this
     * method also invokes the setPage() method and explicitly
     * replaces the current document with the destination url.
     *
     * <p>
     * 相对于超链接的更改的通知。此方法搜索最外面的JEditorPane,然后向该框架触发一个HTMLFrameHyperlinkEvent。
     * 此外,如果目标是_parent,并且没有嵌套的框架集,则目标被重置为_top。
     * 如果目标是_top,除了将事件触发到最外层的JEditorPane之外,此方法还会调用setPage()方法,并使用目标URL显式替换当前文档。
     * 
     * 
     * @param HyperlinkEvent
     */
    public void hyperlinkUpdate(HyperlinkEvent evt) {

        JEditorPane c = getOutermostJEditorPane();
        if (c == null) {
            return;
        }

        if (!(evt instanceof HTMLFrameHyperlinkEvent)) {
            c.fireHyperlinkUpdate(evt);
            return;
        }

        HTMLFrameHyperlinkEvent e = (HTMLFrameHyperlinkEvent)evt;

        if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
            String target = e.getTarget();
            String postTarget = target;

            if (target.equals("_parent") && !inNestedFrameSet()){
                target = "_top";
            }

            if (evt instanceof FormSubmitEvent) {
                HTMLEditorKit kit = (HTMLEditorKit)c.getEditorKit();
                if (kit != null && kit.isAutoFormSubmission()) {
                    if (target.equals("_top")) {
                        try {
                            movePostData(c, postTarget);
                            c.setPage(e.getURL());
                        } catch (IOException ex) {
                            // Need a way to handle exceptions
                        }
                    } else {
                        HTMLDocument doc = (HTMLDocument)c.getDocument();
                        doc.processHTMLFrameHyperlinkEvent(e);
                    }
                } else {
                    c.fireHyperlinkUpdate(evt);
                }
                return;
            }

            if (target.equals("_top")) {
                try {
                    c.setPage(e.getURL());
                } catch (IOException ex) {
                    // Need a way to handle exceptions
                    // ex.printStackTrace();
                }
            }
            if (!c.isEditable()) {
                c.fireHyperlinkUpdate(new HTMLFrameHyperlinkEvent(c,
                                                                  e.getEventType(),
                                                                  e.getURL(),
                                                                  e.getDescription(),
                                                                  getElement(),
                                                                  e.getInputEvent(),
                                                                  target));
            }
        }
    }

    /**
     * Gives notification from the document that attributes were changed
     * in a location that this view is responsible for.  Currently this view
     * handles changes to its SRC attribute.
     *
     * <p>
     *  从文档中提供属性在此视图负责的位置中更改的通知。当前,此视图处理对其SRC属性的更改。
     * 
     * 
     * @param e the change information from the associated document
     * @param a the current allocation of the view
     * @param f the factory to use to rebuild if the view has children
     *
     */
    public void changedUpdate(DocumentEvent e, Shape a, ViewFactory f) {

        Element elem = getElement();
        AttributeSet attributes = elem.getAttributes();

        URL oldPage = src;

        String srcAtt = (String)attributes.getAttribute(HTML.Attribute.SRC);
        URL base = ((HTMLDocument)elem.getDocument()).getBase();
        try {
            if (!createdComponent) {
                return;
            }

            Object postData = movePostData(htmlPane, null);
            src = new URL(base, srcAtt);
            if (oldPage.equals(src) && (src.getRef() == null) && (postData == null)) {
                return;
            }

            htmlPane.setPage(src);
            Document newDoc = htmlPane.getDocument();
            if (newDoc instanceof HTMLDocument) {
                ((HTMLDocument)newDoc).setFrameDocumentState(true);
            }
        } catch (MalformedURLException e1) {
            // Need a way to handle exceptions
            //e1.printStackTrace();
        } catch (IOException e2) {
            // Need a way to handle exceptions
            //e2.printStackTrace();
        }
    }

    /**
     * Move POST data from temporary storage into the target document property.
     *
     * <p>
     *  将POST数据从临时存储移动到目标文档属性。
     * 
     * 
     * @return the POST data or null if no data found
     */
    private Object movePostData(JEditorPane targetPane, String frameName) {
        Object postData = null;
        JEditorPane p = getOutermostJEditorPane();
        if (p != null) {
            if (frameName == null) {
                frameName = (String) getElement().getAttributes().getAttribute(
                        HTML.Attribute.NAME);
            }
            if (frameName != null) {
                String propName = FormView.PostDataProperty + "." + frameName;
                Document d = p.getDocument();
                postData = d.getProperty(propName);
                if (postData != null) {
                    targetPane.getDocument().putProperty(
                            FormView.PostDataProperty, postData);
                    d.putProperty(propName, null);
                }
            }
        }

        return postData;
    }

    /**
     * Determines the minimum span for this view along an
     * axis.
     *
     * <p>
     *  确定沿轴的此视图的最小跨度。
     * 
     * 
     * @param axis may be either <code>View.X_AXIS</code> or
     *  <code>View.Y_AXIS</code>
     * @return the preferred span; given that we do not
     * support resizing of frames, the minimum span returned
     * is the same as the preferred span
     *
     */
    public float getMinimumSpan(int axis) {
      return 5;
    }

    /**
     * Determines the maximum span for this view along an
     * axis.
     *
     * <p>
     *  确定沿轴的此视图的最大跨度。
     * 
     * 
     * @param axis may be either <code>View.X_AXIS</code> or
     *  <code>View.Y_AXIS</code>
     * @return the preferred span; given that we do not
     * support resizing of frames, the maximum span returned
     * is the same as the preferred span
     *
     */
    public float getMaximumSpan(int axis) {
        return Integer.MAX_VALUE;
    }

    /** Editor pane rendering frame of HTML document
     *  It uses the same editor kits classes as outermost JEditorPane
     * <p>
     *  它使用与最外面的JEditorPane相同的编辑器包类
     */
    class FrameEditorPane extends JEditorPane implements FrameEditorPaneTag {
        public EditorKit getEditorKitForContentType(String type) {
            EditorKit editorKit = super.getEditorKitForContentType(type);
            JEditorPane outerMostJEditorPane = null;
            if ((outerMostJEditorPane = getOutermostJEditorPane()) != null) {
                EditorKit inheritedEditorKit = outerMostJEditorPane.getEditorKitForContentType(type);
                if (! editorKit.getClass().equals(inheritedEditorKit.getClass())) {
                    editorKit = (EditorKit) inheritedEditorKit.clone();
                    setEditorKitForContentType(type, editorKit);
                }
            }
            return editorKit;
        }

        FrameView getFrameView() {
            return FrameView.this;
        }
    }
}
