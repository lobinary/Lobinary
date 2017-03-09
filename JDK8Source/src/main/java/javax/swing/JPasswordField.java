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
package javax.swing;

import javax.swing.text.*;
import javax.swing.plaf.*;
import javax.accessibility.*;

import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.*;
import java.util.Arrays;

/**
 * <code>JPasswordField</code> is a lightweight component that allows
 * the editing of a single line of text where the view indicates
 * something was typed, but does not show the original characters.
 * You can find further information and examples in
 * <a href="https://docs.oracle.com/javase/tutorial/uiswing/components/textfield.html">How to Use Text Fields</a>,
 * a section in <em>The Java Tutorial.</em>
 * <p>
 * <code>JPasswordField</code> is intended
 * to be source-compatible with <code>java.awt.TextField</code>
 * used with <code>echoChar</code> set.  It is provided separately
 * to make it easier to safely change the UI for the
 * <code>JTextField</code> without affecting password entries.
 * <p>
 * <strong>NOTE:</strong>
 * By default, JPasswordField disables input methods; otherwise, input
 * characters could be visible while they were composed using input methods.
 * If an application needs the input methods support, please use the
 * inherited method, <code>enableInputMethods(true)</code>.
 * <p>
 * <strong>Warning:</strong> Swing is not thread safe. For more
 * information see <a
 * href="package-summary.html#threading">Swing's Threading
 * Policy</a>.
 * <p>
 * <strong>Warning:</strong>
 * Serialized objects of this class will not be compatible with
 * future Swing releases. The current serialization support is
 * appropriate for short term storage or RMI between applications running
 * the same version of Swing.  As of 1.4, support for long term storage
 * of all JavaBeans&trade;
 * has been added to the <code>java.beans</code> package.
 * Please see {@link java.beans.XMLEncoder}.
 *
 * @beaninfo
 *  attribute: isContainer false
 * description: Allows the editing of a line of text but doesn't show the characters.
 *
 * <p>
 *  <code> JPasswordField </code>是一个轻量级组件,允许编辑单行文本,其中视图指示输入的内容,但不显示原始字符。
 * 您可以在<a href="https://docs.oracle.com/javase/tutorial/uiswing/components/textfield.html">如何使用文本字段</a>中
 * 找到更多信息和示例, em> Java教程。
 *  <code> JPasswordField </code>是一个轻量级组件,允许编辑单行文本,其中视图指示输入的内容,但不显示原始字符。</em>。
 * <p>
 *  <code> JPasswordField </code>旨在与<code> echoChar </code>设置使用的<code> java.awt.TextField </code>源兼容。
 * 它是单独提供的,以便更容易安全地更改<code> JTextField </code>的UI,而不影响密码输入。
 * <p>
 *  <strong>注意</strong>：默认情况下,JPasswordField禁用输入法;否则,输入字符在使用输入法组合时可以是可见的。
 * 如果应用程序需要输入方法支持,请使用继承的方法<code> enableInputMethods(true)</code>。
 * <p>
 *  <strong>警告：</strong> Swing不是线程安全的。有关详情,请参阅<a href="package-summary.html#threading"> Swing的线程策略</a>。
 * <p>
 * <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
 *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
 * 
 *  @beaninfo属性：isContainer false description：允许编辑一行文本,但不显示字符。
 * 
 * 
 * @author  Timothy Prinzing
 */
public class JPasswordField extends JTextField {

    /**
     * Constructs a new <code>JPasswordField</code>,
     * with a default document, <code>null</code> starting
     * text string, and 0 column width.
     * <p>
     *  构造一个新的<code> JPasswordField </code>,带有一个默认文档,<code> null </code>起始文本字符串和0列宽。
     * 
     */
    public JPasswordField() {
        this(null,null,0);
    }

    /**
     * Constructs a new <code>JPasswordField</code> initialized
     * with the specified text.  The document model is set to the
     * default, and the number of columns to 0.
     *
     * <p>
     *  构造一个用指定文本初始化的新<code> JPasswordField </code>。文档模型设置为默认值,列数设置为0。
     * 
     * 
     * @param text the text to be displayed, <code>null</code> if none
     */
    public JPasswordField(String text) {
        this(null, text, 0);
    }

    /**
     * Constructs a new empty <code>JPasswordField</code> with the specified
     * number of columns.  A default model is created, and the initial string
     * is set to <code>null</code>.
     *
     * <p>
     *  用指定的列数构造一个新的空的<code> JPasswordField </code>。创建一个默认模型,并将初始字符串设置为<code> null </code>。
     * 
     * 
     * @param columns the number of columns &gt;= 0
     */
    public JPasswordField(int columns) {
        this(null, null, columns);
    }

    /**
     * Constructs a new <code>JPasswordField</code> initialized with
     * the specified text and columns.  The document model is set to
     * the default.
     *
     * <p>
     *  构造一个用指定的文本和列初始化的新<code> JPasswordField </code>。文档模型设置为默认值。
     * 
     * 
     * @param text the text to be displayed, <code>null</code> if none
     * @param columns the number of columns &gt;= 0
     */
    public JPasswordField(String text, int columns) {
        this(null, text, columns);
    }

    /**
     * Constructs a new <code>JPasswordField</code> that uses the
     * given text storage model and the given number of columns.
     * This is the constructor through which the other constructors feed.
     * The echo character is set to '*', but may be changed by the current
     * Look and Feel.  If the document model is
     * <code>null</code>, a default one will be created.
     *
     * <p>
     *  构造一个使用给定文本存储模型和给定列数的新<code> JPasswordField </code>。这是其他构造函数通过的构造函数。
     *  echo字符设置为'*',但可以通过当前的Look and Feel改变。如果文档模型是<code> null </code>,将创建​​一个默认的模型。
     * 
     * 
     * @param doc  the text storage to use
     * @param txt the text to be displayed, <code>null</code> if none
     * @param columns  the number of columns to use to calculate
     *   the preferred width &gt;= 0; if columns is set to zero, the
     *   preferred width will be whatever naturally results from
     *   the component implementation
     */
    public JPasswordField(Document doc, String txt, int columns) {
        super(doc, txt, columns);
        // We could either leave this on, which wouldn't be secure,
        // or obscure the composted text, which essentially makes displaying
        // it useless. Therefore, we turn off input methods.
        enableInputMethods(false);
    }

    /**
     * Returns the name of the L&amp;F class that renders this component.
     *
     * <p>
     * 返回呈现此组件的L&amp; F类的名称。
     * 
     * 
     * @return the string "PasswordFieldUI"
     * @see JComponent#getUIClassID
     * @see UIDefaults#getUI
     */
    public String getUIClassID() {
        return uiClassID;
    }


    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     * 
     * @since 1.6
     */
    public void updateUI() {
        if(!echoCharSet) {
            echoChar = '*';
        }
        super.updateUI();
    }

    /**
     * Returns the character to be used for echoing.  The default is '*'.
     * The default may be different depending on the currently running Look
     * and Feel. For example, Metal/Ocean's default is a bullet character.
     *
     * <p>
     *  返回要用于回显的字符。默认值为"*"。根据当前运行的外观和感觉,默认值可能不同。例如,Metal / Ocean的默认值是一个项目符号字符。
     * 
     * 
     * @return the echo character, 0 if unset
     * @see #setEchoChar
     * @see #echoCharIsSet
     */
    public char getEchoChar() {
        return echoChar;
    }

    /**
     * Sets the echo character for this <code>JPasswordField</code>.
     * Note that this is largely a suggestion, since the
     * view that gets installed can use whatever graphic techniques
     * it desires to represent the field.  Setting a value of 0 indicates
     * that you wish to see the text as it is typed, similar to
     * the behavior of a standard <code>JTextField</code>.
     *
     * <p>
     *  为此<code> JPasswordField </code>设置回显字符。注意,这在很大程度上是一个建议,因为安装的视图可以使用任何图形技术,它想要代表的领域。
     * 设置值0表示希望在输入时看到文本,类似于标准<code> JTextField </code>的行为。
     * 
     * 
     * @param c the echo character to display
     * @see #echoCharIsSet
     * @see #getEchoChar
     * @beaninfo
     * description: character to display in place of the real characters
     *   attribute: visualUpdate true
     */
    public void setEchoChar(char c) {
        echoChar = c;
        echoCharSet = true;
        repaint();
        revalidate();
    }

    /**
     * Returns true if this <code>JPasswordField</code> has a character
     * set for echoing.  A character is considered to be set if the echo
     * character is not 0.
     *
     * <p>
     *  如果此<code> JPasswordField </code>具有用于回显的字符集,则返回true。如果echo字符不为0,则认为字符被设置。
     * 
     * 
     * @return true if a character is set for echoing
     * @see #setEchoChar
     * @see #getEchoChar
     */
    public boolean echoCharIsSet() {
        return echoChar != 0;
    }

    // --- JTextComponent methods ----------------------------------

    /**
     * Invokes <code>provideErrorFeedback</code> on the current
     * look and feel, which typically initiates an error beep.
     * The normal behavior of transferring the
     * currently selected range in the associated text model
     * to the system clipboard, and removing the contents from
     * the model, is not acceptable for a password field.
     * <p>
     *  对当前外观调用<code> provideErrorFeedback </code>,通常会发出错误提示音。
     * 将相关联的文本模型中当前选择的范围传送到系统剪贴板并从模型中移除内容的正常行为对于密码字段是不可接受的。
     * 
     */
    public void cut() {
        if (getClientProperty("JPasswordField.cutCopyAllowed") != Boolean.TRUE) {
            UIManager.getLookAndFeel().provideErrorFeedback(this);
        } else {
            super.cut();
        }
    }

    /**
     * Invokes <code>provideErrorFeedback</code> on the current
     * look and feel, which typically initiates an error beep.
     * The normal behavior of transferring the
     * currently selected range in the associated text model
     * to the system clipboard, and leaving the contents from
     * the model, is not acceptable for a password field.
     * <p>
     *  对当前外观调用<code> provideErrorFeedback </code>,通常会发出错误提示音。
     * 将相关联的文本模型中当前选择的范围传送到系统剪贴板并留下来自模型的内容的正常行为对于密码字段是不可接受的。
     * 
     */
    public void copy() {
        if (getClientProperty("JPasswordField.cutCopyAllowed") != Boolean.TRUE) {
            UIManager.getLookAndFeel().provideErrorFeedback(this);
        } else {
            super.copy();
        }
    }

    /**
     * Returns the text contained in this <code>TextComponent</code>.
     * If the underlying document is <code>null</code>, will give a
     * <code>NullPointerException</code>.
     * <p>
     * For security reasons, this method is deprecated.  Use the
     <code>* getPassword</code> method instead.
     <code>* <p>
     <code>* 返回此<code> TextComponent </code>中包含的文本。
     <code>* 如果底层文档是<code> null </code>,将给出一个<code> NullPointerException </code>。
     <code>* <p>
     <code>*  出于安全原因,不推荐使用此方法。请改用<code> * getPassword </code>方法。
     <code>* 
     <code>* 
     * @deprecated As of Java 2 platform v1.2,
     * replaced by <code>getPassword</code>.
     * @return the text
     */
    @Deprecated
    public String getText() {
        return super.getText();
    }

    /**
     * Fetches a portion of the text represented by the
     * component.  Returns an empty string if length is 0.
     * <p>
     * For security reasons, this method is deprecated.  Use the
     * <code>getPassword</code> method instead.
     * <p>
     *  获取由组件表示的文本的一部分。如果length为0,则返回一个空字符串。
     * <p>
     *  出于安全原因,不推荐使用此方法。请改用<code> getPassword </code>方法。
     * 
     * 
     * @deprecated As of Java 2 platform v1.2,
     * replaced by <code>getPassword</code>.
     * @param offs the offset &gt;= 0
     * @param len the length &gt;= 0
     * @return the text
     * @exception BadLocationException if the offset or length are invalid
     */
    @Deprecated
    public String getText(int offs, int len) throws BadLocationException {
        return super.getText(offs, len);
    }

    /**
     * Returns the text contained in this <code>TextComponent</code>.
     * If the underlying document is <code>null</code>, will give a
     * <code>NullPointerException</code>.  For stronger
     * security, it is recommended that the returned character array be
     * cleared after use by setting each character to zero.
     *
     * <p>
     *  返回此<code> TextComponent </code>中包含的文本。
     * 如果底层文档是<code> null </code>,将给出一个<code> NullPointerException </code>。
     * 为了更强的安全性,建议在使用后通过将每个字符设置为零来清除返回的字符数组。
     * 
     * 
     * @return the text
     */
    public char[] getPassword() {
        Document doc = getDocument();
        Segment txt = new Segment();
        try {
            doc.getText(0, doc.getLength(), txt); // use the non-String API
        } catch (BadLocationException e) {
            return null;
        }
        char[] retValue = new char[txt.count];
        System.arraycopy(txt.array, txt.offset, retValue, 0, txt.count);
        return retValue;
    }

    /**
     * See readObject() and writeObject() in JComponent for more
     * information about serialization in Swing.
     * <p>
     *  有关Swing中序列化的更多信息,请参阅JComponent中的readObject()和writeObject()。
     * 
     */
    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        if (getUIClassID().equals(uiClassID)) {
            byte count = JComponent.getWriteObjCounter(this);
            JComponent.setWriteObjCounter(this, --count);
            if (count == 0 && ui != null) {
                ui.installUI(this);
            }
        }
    }

    // --- variables -----------------------------------------------

    /**
    /* <p>
    /* 
     * @see #getUIClassID
     * @see #readObject
     */
    private static final String uiClassID = "PasswordFieldUI";

    private char echoChar;

    private boolean echoCharSet = false;


    /**
     * Returns a string representation of this <code>JPasswordField</code>.
     * This method is intended to be used only for debugging purposes, and the
     * content and format of the returned string may vary between
     * implementations. The returned string may be empty but may not
     * be <code>null</code>.
     *
     * <p>
     *  返回此<code> JPasswordField </code>的字符串表示形式。此方法仅用于调试目的,并且返回的字符串的内容和格式可能因实现而异。
     * 返回的字符串可能为空,但可能不是<code> null </code>。
     * 
     * 
     * @return  a string representation of this <code>JPasswordField</code>
     */
    protected String paramString() {
        return super.paramString() +
        ",echoChar=" + echoChar;
    }


    /**
     * This method is a hack to get around the fact that we cannot
     * directly override setUIProperty because part of the inheritance hierarchy
     * goes outside of the javax.swing package, and therefore calling a package
     * private method isn't allowed. This method should return true if the property
     * was handled, and false otherwise.
     * <p>
     *  这个方法是一个黑客,我们不能直接重写setUIProperty,因为部分继承层次结构超出了javax.swing包,因此不允许调用包私有方法。
     * 如果处理该属性,此方法应返回true,否则返回false。
     * 
     */
    boolean customSetUIProperty(String propertyName, Object value) {
        if (propertyName == "echoChar") {
            if (!echoCharSet) {
                setEchoChar((Character)value);
                echoCharSet = false;
            }
            return true;
        }
        return false;
    }

/////////////////
// Accessibility support
////////////////


    /**
     * Returns the <code>AccessibleContext</code> associated with this
     * <code>JPasswordField</code>. For password fields, the
     * <code>AccessibleContext</code> takes the form of an
     * <code>AccessibleJPasswordField</code>.
     * A new <code>AccessibleJPasswordField</code> instance is created
     * if necessary.
     *
     * <p>
     * 返回与此<code> JPasswordField </code>关联的<code> AccessibleContext </code>。
     * 对于密码字段,<code> AccessibleContext </code>采用<code> AccessibleJPasswordField </code>的形式。
     * 如果需要,将创建一个新的<code> AccessibleJPasswordField </code>实例。
     * 
     * 
     * @return an <code>AccessibleJPasswordField</code> that serves as the
     *         <code>AccessibleContext</code> of this
     *         <code>JPasswordField</code>
     */
    public AccessibleContext getAccessibleContext() {
        if (accessibleContext == null) {
            accessibleContext = new AccessibleJPasswordField();
        }
        return accessibleContext;
    }

    /**
     * This class implements accessibility support for the
     * <code>JPasswordField</code> class.  It provides an implementation of the
     * Java Accessibility API appropriate to password field user-interface
     * elements.
     * <p>
     * <strong>Warning:</strong>
     * Serialized objects of this class will not be compatible with
     * future Swing releases. The current serialization support is
     * appropriate for short term storage or RMI between applications running
     * the same version of Swing.  As of 1.4, support for long term storage
     * of all JavaBeans&trade;
     * has been added to the <code>java.beans</code> package.
     * Please see {@link java.beans.XMLEncoder}.
     * <p>
     *  此类实现<code> JPasswordField </code>类的辅助功能支持。它提供了适用于密码字段用户界面元素的Java可访问性API的实现。
     * <p>
     *  <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
     *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
     * 
     */
    protected class AccessibleJPasswordField extends AccessibleJTextField {

        /**
         * Gets the role of this object.
         *
         * <p>
         *  获取此对象的作用。
         * 
         * 
         * @return an instance of AccessibleRole describing the role of the
         *   object (AccessibleRole.PASSWORD_TEXT)
         * @see AccessibleRole
         */
        public AccessibleRole getAccessibleRole() {
            return AccessibleRole.PASSWORD_TEXT;
        }

        /**
         * Gets the <code>AccessibleText</code> for the <code>JPasswordField</code>.
         * The returned object also implements the
         * <code>AccessibleExtendedText</code> interface.
         *
         * <p>
         *  获取<code> JPasswordField </code>的<code> AccessibleText </code>。
         * 返回的对象还实现<code> AccessibleExtendedText </code>接口。
         * 
         * 
         * @return <code>AccessibleText</code> for the JPasswordField
         * @see javax.accessibility.AccessibleContext
         * @see javax.accessibility.AccessibleContext#getAccessibleText
         * @see javax.accessibility.AccessibleText
         * @see javax.accessibility.AccessibleExtendedText
         *
         * @since 1.6
         */
        public AccessibleText getAccessibleText() {
            return this;
        }

        /*
         * Returns a String filled with password echo characters. The String
         * contains one echo character for each character (including whitespace)
         * that the user entered in the JPasswordField.
         * <p>
         *  返回一个用密码echo字符填充的字符串。字符串包含用户在JPasswordField中输入的每个字符(包括空格)的一个回显字符。
         * 
         */
        private String getEchoString(String str) {
            if (str == null) {
                return null;
            }
            char[] buffer = new char[str.length()];
            Arrays.fill(buffer, getEchoChar());
            return new String(buffer);
        }

        /**
         * Returns the <code>String</code> at a given <code>index</code>.
         *
         * <p>
         *  返回给定<code>索引</code>下的<code> String </code>。
         * 
         * 
         * @param part the <code>CHARACTER</code>, <code>WORD</code> or
         * <code>SENTENCE</code> to retrieve
         * @param index an index within the text
         * @return a <code>String</code> if <code>part</code> and
         * <code>index</code> are valid.
         * Otherwise, <code>null</code> is returned
         *
         * @see javax.accessibility.AccessibleText#CHARACTER
         * @see javax.accessibility.AccessibleText#WORD
         * @see javax.accessibility.AccessibleText#SENTENCE
         *
         * @since 1.6
         */
        public String getAtIndex(int part, int index) {
           String str = null;
            if (part == AccessibleText.CHARACTER) {
                str = super.getAtIndex(part, index);
            } else {
                // Treat the text displayed in the JPasswordField
                // as one word and sentence.
                char password[] = getPassword();
                if (password == null ||
                    index < 0 || index >= password.length) {
                    return null;
                }
                str = new String(password);
            }
            return getEchoString(str);
        }

        /**
         * Returns the <code>String</code> after a given <code>index</code>.
         *
         * <p>
         *  返回给定的<code>索引</code>之后的<code> String </code>。
         * 
         * 
         * @param part the <code>CHARACTER</code>, <code>WORD</code> or
         * <code>SENTENCE</code> to retrieve
         * @param index an index within the text
         * @return a <code>String</code> if <code>part</code> and
         * <code>index</code> are valid.
         * Otherwise, <code>null</code> is returned
         *
         * @see javax.accessibility.AccessibleText#CHARACTER
         * @see javax.accessibility.AccessibleText#WORD
         * @see javax.accessibility.AccessibleText#SENTENCE
         *
         * @since 1.6
         */
        public String getAfterIndex(int part, int index) {
            if (part == AccessibleText.CHARACTER) {
                String str = super.getAfterIndex(part, index);
                return getEchoString(str);
            } else {
                // There is no word or sentence after the text
                // displayed in the JPasswordField.
                return null;
            }
        }

        /**
         * Returns the <code>String</code> before a given <code>index</code>.
         *
         * <p>
         *  在给定的<code>索引</code>之前返回<code> String </code>。
         * 
         * 
         * @param part the <code>CHARACTER</code>, <code>WORD</code> or
         * <code>SENTENCE</code> to retrieve
         * @param index an index within the text
         * @return a <code>String</code> if <code>part</code> and
         * <code>index</code> are valid.
         * Otherwise, <code>null</code> is returned
         *
         * @see javax.accessibility.AccessibleText#CHARACTER
         * @see javax.accessibility.AccessibleText#WORD
         * @see javax.accessibility.AccessibleText#SENTENCE
         *
         * @since 1.6
         */
        public String getBeforeIndex(int part, int index) {
            if (part == AccessibleText.CHARACTER) {
                String str = super.getBeforeIndex(part, index);
                return getEchoString(str);
            } else {
                // There is no word or sentence before the text
                // displayed in the JPasswordField.
                return null;
            }
        }

        /**
         * Returns the text between two <code>indices</code>.
         *
         * <p>
         * 返回两个<code> indices </code>之间的文本。
         * 
         * 
         * @param startIndex the start index in the text
         * @param endIndex the end index in the text
         * @return the text string if the indices are valid.
         * Otherwise, <code>null</code> is returned
         *
         * @since 1.6
         */
        public String getTextRange(int startIndex, int endIndex) {
            String str = super.getTextRange(startIndex, endIndex);
            return getEchoString(str);
        }


        /**
         * Returns the <code>AccessibleTextSequence</code> at a given
         * <code>index</code>.
         *
         * <p>
         *  返回给定<code>索引</code>下的<code> AccessibleTextSequence </code>。
         * 
         * 
         * @param part the <code>CHARACTER</code>, <code>WORD</code>,
         * <code>SENTENCE</code>, <code>LINE</code> or <code>ATTRIBUTE_RUN</code> to
         * retrieve
         * @param index an index within the text
         * @return an <code>AccessibleTextSequence</code> specifying the text if
         * <code>part</code> and <code>index</code> are valid.  Otherwise,
         * <code>null</code> is returned
         *
         * @see javax.accessibility.AccessibleText#CHARACTER
         * @see javax.accessibility.AccessibleText#WORD
         * @see javax.accessibility.AccessibleText#SENTENCE
         * @see javax.accessibility.AccessibleExtendedText#LINE
         * @see javax.accessibility.AccessibleExtendedText#ATTRIBUTE_RUN
         *
         * @since 1.6
         */
        public AccessibleTextSequence getTextSequenceAt(int part, int index) {
            if (part == AccessibleText.CHARACTER) {
                AccessibleTextSequence seq = super.getTextSequenceAt(part, index);
                if (seq == null) {
                    return null;
                }
                return new AccessibleTextSequence(seq.startIndex, seq.endIndex,
                                                  getEchoString(seq.text));
            } else {
                // Treat the text displayed in the JPasswordField
                // as one word, sentence, line and attribute run
                char password[] = getPassword();
                if (password == null ||
                    index < 0 || index >= password.length) {
                    return null;
                }
                String text = new String(password);
                return new AccessibleTextSequence(0, password.length - 1,
                                                  getEchoString(text));
            }
        }

        /**
         * Returns the <code>AccessibleTextSequence</code> after a given
         * <code>index</code>.
         *
         * <p>
         *  返回给定<code>索引</code>之后的<code> AccessibleTextSequence </code>。
         * 
         * 
         * @param part the <code>CHARACTER</code>, <code>WORD</code>,
         * <code>SENTENCE</code>, <code>LINE</code> or <code>ATTRIBUTE_RUN</code> to
         * retrieve
         * @param index an index within the text
         * @return an <code>AccessibleTextSequence</code> specifying the text if
         * <code>part</code> and <code>index</code> are valid.  Otherwise,
         * <code>null</code> is returned
         *
         * @see javax.accessibility.AccessibleText#CHARACTER
         * @see javax.accessibility.AccessibleText#WORD
         * @see javax.accessibility.AccessibleText#SENTENCE
         * @see javax.accessibility.AccessibleExtendedText#LINE
         * @see javax.accessibility.AccessibleExtendedText#ATTRIBUTE_RUN
         *
         * @since 1.6
         */
        public AccessibleTextSequence getTextSequenceAfter(int part, int index) {
            if (part == AccessibleText.CHARACTER) {
                AccessibleTextSequence seq = super.getTextSequenceAfter(part, index);
                if (seq == null) {
                    return null;
                }
                return new AccessibleTextSequence(seq.startIndex, seq.endIndex,
                                                  getEchoString(seq.text));
            } else {
                // There is no word, sentence, line or attribute run
                // after the text displayed in the JPasswordField.
                return null;
            }
        }

        /**
         * Returns the <code>AccessibleTextSequence</code> before a given
         * <code>index</code>.
         *
         * <p>
         *  在给定的<code>索引</code>之前返回<code> AccessibleTextSequence </code>。
         * 
         * @param part the <code>CHARACTER</code>, <code>WORD</code>,
         * <code>SENTENCE</code>, <code>LINE</code> or <code>ATTRIBUTE_RUN</code> to
         * retrieve
         * @param index an index within the text
         * @return an <code>AccessibleTextSequence</code> specifying the text if
         * <code>part</code> and <code>index</code> are valid.  Otherwise,
         * <code>null</code> is returned
         *
         * @see javax.accessibility.AccessibleText#CHARACTER
         * @see javax.accessibility.AccessibleText#WORD
         * @see javax.accessibility.AccessibleText#SENTENCE
         * @see javax.accessibility.AccessibleExtendedText#LINE
         * @see javax.accessibility.AccessibleExtendedText#ATTRIBUTE_RUN
         *
         * @since 1.6
         */
        public AccessibleTextSequence getTextSequenceBefore(int part, int index) {
            if (part == AccessibleText.CHARACTER) {
                AccessibleTextSequence seq = super.getTextSequenceBefore(part, index);
                if (seq == null) {
                    return null;
                }
                return new AccessibleTextSequence(seq.startIndex, seq.endIndex,
                                                  getEchoString(seq.text));
            } else {
                // There is no word, sentence, line or attribute run
                // before the text displayed in the JPasswordField.
                return null;
            }
        }
    }
}
