/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1995, 2014, Oracle and/or its affiliates. All rights reserved.
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
package java.awt;

import java.awt.peer.LabelPeer;
import java.io.IOException;
import java.io.ObjectInputStream;
import javax.accessibility.*;

/**
 * A <code>Label</code> object is a component for placing text in a
 * container. A label displays a single line of read-only text.
 * The text can be changed by the application, but a user cannot edit it
 * directly.
 * <p>
 * For example, the code&nbsp;.&nbsp;.&nbsp;.
 *
 * <hr><blockquote><pre>
 * setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
 * add(new Label("Hi There!"));
 * add(new Label("Another Label"));
 * </pre></blockquote><hr>
 * <p>
 * produces the following labels:
 * <p>
 * <img src="doc-files/Label-1.gif" alt="Two labels: 'Hi There!' and 'Another label'"
 * style="float:center; margin: 7px 10px;">
 *
 * <p>
 *  <code> Label </code>对象是用于在容器中放置文本的组件。标签显示单行只读文本。文本可以由应用程序更改,但用户不能直接编辑它。
 * <p>
 *  例如,代码&nbsp;。&nbsp;。&nbsp;
 * 
 *  <hr> <blockquote> <pre> setLayout(new FlowLayout(FlowLayout.CENTER,10,10)); add(new Label("Hi There！
 * ")); add(new Label("Another Label")); </pre> </blockquote> <hr>。
 * <p>
 *  产生以下标签：
 * <p>
 *  <img src ="doc-files / Label-1.gif"alt ="两个标签：'嗨！和"另一个标签"
 * style="float:center; margin: 7px 10px;">
 * 
 * 
 * @author      Sami Shaio
 * @since       JDK1.0
 */
public class Label extends Component implements Accessible {

    static {
        /* ensure that the necessary native libraries are loaded */
        Toolkit.loadLibraries();
        if (!GraphicsEnvironment.isHeadless()) {
            initIDs();
        }
    }

    /**
     * Indicates that the label should be left justified.
     * <p>
     *  表示标签应左对齐。
     * 
     */
    public static final int LEFT        = 0;

    /**
     * Indicates that the label should be centered.
     * <p>
     *  表示标签应居中。
     * 
     */
    public static final int CENTER      = 1;

    /**
     * Indicates that the label should be right justified.
     * <p>
     *  表示标签应右对齐。
     * 
     * 
     * @since   JDK1.0t.
     */
    public static final int RIGHT       = 2;

    /**
     * The text of this label.
     * This text can be modified by the program
     * but never by the user.
     *
     * <p>
     *  此标签的文字。该文本可以由程序修改,但从不由用户修改。
     * 
     * 
     * @serial
     * @see #getText()
     * @see #setText(String)
     */
    String text;

    /**
     * The label's alignment.  The default alignment is set
     * to be left justified.
     *
     * <p>
     *  标签的对齐方式。默认对齐方式设置为左对齐。
     * 
     * 
     * @serial
     * @see #getAlignment()
     * @see #setAlignment(int)
     */
    int    alignment = LEFT;

    private static final String base = "label";
    private static int nameCounter = 0;

    /*
     * JDK 1.1 serialVersionUID
     * <p>
     *  JDK 1.1 serialVersionUID
     * 
     */
     private static final long serialVersionUID = 3094126758329070636L;

    /**
     * Constructs an empty label.
     * The text of the label is the empty string <code>""</code>.
     * <p>
     *  构造一个空标签。标签的文本是空字符串<code>""</code>。
     * 
     * 
     * @exception HeadlessException if GraphicsEnvironment.isHeadless()
     * returns true.
     * @see java.awt.GraphicsEnvironment#isHeadless
     */
    public Label() throws HeadlessException {
        this("", LEFT);
    }

    /**
     * Constructs a new label with the specified string of text,
     * left justified.
     * <p>
     *  使用指定的文本字符串构造一个新标签,左对齐。
     * 
     * 
     * @param text the string that the label presents.
     *        A <code>null</code> value
     *        will be accepted without causing a NullPointerException
     *        to be thrown.
     * @exception HeadlessException if GraphicsEnvironment.isHeadless()
     * returns true.
     * @see java.awt.GraphicsEnvironment#isHeadless
     */
    public Label(String text) throws HeadlessException {
        this(text, LEFT);
    }

    /**
     * Constructs a new label that presents the specified string of
     * text with the specified alignment.
     * Possible values for <code>alignment</code> are <code>Label.LEFT</code>,
     * <code>Label.RIGHT</code>, and <code>Label.CENTER</code>.
     * <p>
     *  构造一个新标签,以指定的对齐方式显示指定的文本字符串。
     *  <code> alignment </code>的可能值为<code> Label.LEFT </code>,<code> Label.RIGHT </code>和<code> Label.CENTE
     * R </code>。
     *  构造一个新标签,以指定的对齐方式显示指定的文本字符串。
     * 
     * 
     * @param text the string that the label presents.
     *        A <code>null</code> value
     *        will be accepted without causing a NullPointerException
     *        to be thrown.
     * @param     alignment   the alignment value.
     * @exception HeadlessException if GraphicsEnvironment.isHeadless()
     * returns true.
     * @see java.awt.GraphicsEnvironment#isHeadless
     */
    public Label(String text, int alignment) throws HeadlessException {
        GraphicsEnvironment.checkHeadless();
        this.text = text;
        setAlignment(alignment);
    }

    /**
     * Read a label from an object input stream.
     * <p>
     *  从对象输入流读取标签。
     * 
     * 
     * @exception HeadlessException if
     * <code>GraphicsEnvironment.isHeadless()</code> returns
     * <code>true</code>
     * @serial
     * @since 1.4
     * @see java.awt.GraphicsEnvironment#isHeadless
     */
    private void readObject(ObjectInputStream s)
        throws ClassNotFoundException, IOException, HeadlessException {
        GraphicsEnvironment.checkHeadless();
        s.defaultReadObject();
    }

    /**
     * Construct a name for this component.  Called by getName() when the
     * name is <code>null</code>.
     * <p>
     * 构造此组件的名称。当名称为<code> null </code>时,由getName()调用。
     * 
     */
    String constructComponentName() {
        synchronized (Label.class) {
            return base + nameCounter++;
        }
    }

    /**
     * Creates the peer for this label.  The peer allows us to
     * modify the appearance of the label without changing its
     * functionality.
     * <p>
     *  创建此标签的对等体。对等体允许我们修改标签的外观而不改变其功能。
     * 
     */
    public void addNotify() {
        synchronized (getTreeLock()) {
            if (peer == null)
                peer = getToolkit().createLabel(this);
            super.addNotify();
        }
    }

    /**
     * Gets the current alignment of this label. Possible values are
     * <code>Label.LEFT</code>, <code>Label.RIGHT</code>, and
     * <code>Label.CENTER</code>.
     * <p>
     *  获取此标签的当前对齐方式。可能的值为<code> Label.LEFT </code>,<code> Label.RIGHT </code>和<code> Label.CENTER </code>。
     * 
     * 
     * @see        java.awt.Label#setAlignment
     */
    public int getAlignment() {
        return alignment;
    }

    /**
     * Sets the alignment for this label to the specified alignment.
     * Possible values are <code>Label.LEFT</code>,
     * <code>Label.RIGHT</code>, and <code>Label.CENTER</code>.
     * <p>
     *  将此标签的对齐方式设置为指定的对齐方式。
     * 可能的值为<code> Label.LEFT </code>,<code> Label.RIGHT </code>和<code> Label.CENTER </code>。
     * 
     * 
     * @param      alignment    the alignment to be set.
     * @exception  IllegalArgumentException if an improper value for
     *                          <code>alignment</code> is given.
     * @see        java.awt.Label#getAlignment
     */
    public synchronized void setAlignment(int alignment) {
        switch (alignment) {
          case LEFT:
          case CENTER:
          case RIGHT:
            this.alignment = alignment;
            LabelPeer peer = (LabelPeer)this.peer;
            if (peer != null) {
                peer.setAlignment(alignment);
            }
            return;
        }
        throw new IllegalArgumentException("improper alignment: " + alignment);
    }

    /**
     * Gets the text of this label.
     * <p>
     *  获取此标签的文本。
     * 
     * 
     * @return     the text of this label, or <code>null</code> if
     *             the text has been set to <code>null</code>.
     * @see        java.awt.Label#setText
     */
    public String getText() {
        return text;
    }

    /**
     * Sets the text for this label to the specified text.
     * <p>
     *  将此标签的文本设置为指定的文本。
     * 
     * 
     * @param      text the text that this label displays. If
     *             <code>text</code> is <code>null</code>, it is
     *             treated for display purposes like an empty
     *             string <code>""</code>.
     * @see        java.awt.Label#getText
     */
    public void setText(String text) {
        boolean testvalid = false;
        synchronized (this) {
            if (text != this.text && (this.text == null ||
                                      !this.text.equals(text))) {
                this.text = text;
                LabelPeer peer = (LabelPeer)this.peer;
                if (peer != null) {
                    peer.setText(text);
                }
                testvalid = true;
            }
        }

        // This could change the preferred size of the Component.
        if (testvalid) {
            invalidateIfValid();
        }
    }

    /**
     * Returns a string representing the state of this <code>Label</code>.
     * This method is intended to be used only for debugging purposes, and the
     * content and format of the returned string may vary between
     * implementations. The returned string may be empty but may not be
     * <code>null</code>.
     *
     * <p>
     *  返回表示此<code> Label </code>的状态的字符串。此方法仅用于调试目的,并且返回的字符串的内容和格式可能因实现而异。
     * 返回的字符串可能为空,但可能不是<code> null </code>。
     * 
     * 
     * @return     the parameter string of this label
     */
    protected String paramString() {
        String align = "";
        switch (alignment) {
            case LEFT:   align = "left"; break;
            case CENTER: align = "center"; break;
            case RIGHT:  align = "right"; break;
        }
        return super.paramString() + ",align=" + align + ",text=" + text;
    }

    /**
     * Initialize JNI field and method IDs
     * <p>
     *  初始化JNI字段和方法ID
     * 
     */
    private static native void initIDs();


/////////////////
// Accessibility support
////////////////


    /**
     * Gets the AccessibleContext associated with this Label.
     * For labels, the AccessibleContext takes the form of an
     * AccessibleAWTLabel.
     * A new AccessibleAWTLabel instance is created if necessary.
     *
     * <p>
     *  获取与此Label相关联的AccessibleContext。对于标签,AccessibleContext采用AccessibleAWTLabel的形式。
     * 如果需要,将创建一个新的AccessibleAWTLabel实例。
     * 
     * 
     * @return an AccessibleAWTLabel that serves as the
     *         AccessibleContext of this Label
     * @since 1.3
     */
    public AccessibleContext getAccessibleContext() {
        if (accessibleContext == null) {
            accessibleContext = new AccessibleAWTLabel();
        }
        return accessibleContext;
    }

    /**
     * This class implements accessibility support for the
     * <code>Label</code> class.  It provides an implementation of the
     * Java Accessibility API appropriate to label user-interface elements.
     * <p>
     *  此类实现了对<code> Label </code>类的辅助功能支持。它提供了适用于标记用户界面元素的Java辅助功能API的实现。
     * 
     * 
     * @since 1.3
     */
    protected class AccessibleAWTLabel extends AccessibleAWTComponent
    {
        /*
         * JDK 1.3 serialVersionUID
         * <p>
         *  JDK 1.3 serialVersionUID
         * 
         */
        private static final long serialVersionUID = -3568967560160480438L;

        public AccessibleAWTLabel() {
            super();
        }

        /**
         * Get the accessible name of this object.
         *
         * <p>
         *  获取此对象的可访问名称。
         * 
         * 
         * @return the localized name of the object -- can be null if this
         * object does not have a name
         * @see AccessibleContext#setAccessibleName
         */
        public String getAccessibleName() {
            if (accessibleName != null) {
                return accessibleName;
            } else {
                if (getText() == null) {
                    return super.getAccessibleName();
                } else {
                    return getText();
                }
            }
        }

        /**
         * Get the role of this object.
         *
         * <p>
         *  获取此对象的作用。
         * 
         * @return an instance of AccessibleRole describing the role of the object
         * @see AccessibleRole
         */
        public AccessibleRole getAccessibleRole() {
            return AccessibleRole.LABEL;
        }

    } // inner class AccessibleAWTLabel

}
