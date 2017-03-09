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
package javax.swing.text.html;

import java.net.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;

/**
 * Component decorator that implements the view interface
 * for form elements, &lt;input&gt;, &lt;textarea&gt;,
 * and &lt;select&gt;.  The model for the component is stored
 * as an attribute of the the element (using StyleConstants.ModelAttribute),
 * and is used to build the component of the view.  The type
 * of the model is assumed to of the type that would be set by
 * <code>HTMLDocument.HTMLReader.FormAction</code>.  If there are
 * multiple views mapped over the document, they will share the
 * embedded component models.
 * <p>
 * The following table shows what components get built
 * by this view.
 * <table summary="shows what components get built by this view">
 * <tr>
 *   <th>Element Type</th>
 *   <th>Component built</th>
 * </tr>
 * <tr>
 *   <td>input, type button</td>
 *   <td>JButton</td>
 * </tr>
 * <tr>
 *   <td>input, type checkbox</td>
 *   <td>JCheckBox</td>
 * </tr>
 * <tr>
 *   <td>input, type image</td>
 *   <td>JButton</td>
 * </tr>
 * <tr>
 *   <td>input, type password</td>
 *   <td>JPasswordField</td>
 * </tr>
 * <tr>
 *   <td>input, type radio</td>
 *   <td>JRadioButton</td>
 * </tr>
 * <tr>
 *   <td>input, type reset</td>
 *   <td>JButton</td>
 * </tr>
 * <tr>
 *   <td>input, type submit</td>
 *   <td>JButton</td>
 * </tr>
 * <tr>
 *   <td>input, type text</td>
 *   <td>JTextField</td>
 * </tr>
 * <tr>
 *   <td>select, size &gt; 1 or multiple attribute defined</td>
 *   <td>JList in a JScrollPane</td>
 * </tr>
 * <tr>
 *   <td>select, size unspecified or 1</td>
 *   <td>JComboBox</td>
 * </tr>
 * <tr>
 *   <td>textarea</td>
 *   <td>JTextArea in a JScrollPane</td>
 * </tr>
 * <tr>
 *   <td>input, type file</td>
 *   <td>JTextField</td>
 * </tr>
 * </table>
 *
 * <p>
 *  实现表单元素&lt; input&gt;,&lt; textarea&gt;和&lt; select&gt;的视图接口的组件装饰器。
 * 组件的模型存储为元素的属性(使用StyleConstants.ModelAttribute),并用于构建视图的组件。
 * 模型的类型假设为由<code> HTMLDocument.HTMLReader.FormAction </code>设置的类型。如果文档上有多个视图,它们将共享嵌入式组件模型。
 * <p>
 *  下表显示了此视图构建的组件。
 * <table summary="shows what components get built by this view">
 * <tr>
 *  <th>元素类型</th> <th>组件已构建</th>
 * </tr>
 * <tr>
 *  <td>输入,键入</td> <td> JButton </td>
 * </tr>
 * <tr>
 *  <td>输入,键入checkbox </td> <td> JCheckBox </td>
 * </tr>
 * <tr>
 *  <td>输入,输入image </td> <td> JButton </td>
 * </tr>
 * <tr>
 *  <td>输入,键入密码</td> <td> JPasswordField </td>
 * </tr>
 * <tr>
 *  <td>输入,输入radio </td> <td> JRadioButton </td>
 * </tr>
 * <tr>
 *  <td>输入,键入reset </td> <td> JButton </td>
 * </tr>
 * <tr>
 *  <td>输入,输入submit </td> <td> JButton </td>
 * </tr>
 * <tr>
 *  <td>输入,键入文本</td> <td> JTextField </td>
 * </tr>
 * <tr>
 *  <td> select,size&gt; 1或JScrollPane中定义的多个属性</td> <td> JList </td>
 * </tr>
 * <tr>
 *  <td> select,size unspecified或1 </td> <td> JComboBox </td>
 * </tr>
 * <tr>
 *  <td> textarea </td> <td> JTrollPane中的JTextArea </td>
 * </tr>
 * <tr>
 *  <td>输入,键入文件</td> <td> JTextField </td>
 * </tr>
 * </table>
 * 
 * 
 * @author Timothy Prinzing
 * @author Sunita Mani
 */
public class FormView extends ComponentView implements ActionListener {

    /**
     * If a value attribute is not specified for a FORM input element
     * of type "submit", then this default string is used.
     *
     * <p>
     * 如果没有为类型"submit"的FORM输入元素指定value属性,则使用此默认字符串。
     * 
     * 
     * @deprecated As of 1.3, value now comes from UIManager property
     *             FormView.submitButtonText
     */
    @Deprecated
    public static final String SUBMIT = new String("Submit Query");
    /**
     * If a value attribute is not specified for a FORM input element
     * of type "reset", then this default string is used.
     *
     * <p>
     *  如果没有为"reset"类型的FORM输入元素指定value属性,则使用此默认字符串。
     * 
     * 
     * @deprecated As of 1.3, value comes from UIManager UIManager property
     *             FormView.resetButtonText
     */
    @Deprecated
    public static final String RESET = new String("Reset");

    /**
     * Document attribute name for storing POST data. JEditorPane.getPostData()
     * uses the same name, should be kept in sync.
     * <p>
     *  用于存储POST数据的文档属性名称。 JEdi​​torPane.getPostData()使用相同的名称,应该保持同步。
     * 
     */
    final static String PostDataProperty = "javax.swing.JEditorPane.postdata";

    /**
     * Used to indicate if the maximum span should be the same as the
     * preferred span. This is used so that the Component's size doesn't
     * change if there is extra room on a line. The first bit is used for
     * the X direction, and the second for the y direction.
     * <p>
     *  用于指示最大跨度是否应与首选跨度相同。这是为了使组件的大小不会改变,如果在一行上有额外的空间。第一位用于X方向,第二位用于y方向。
     * 
     */
    private short maxIsPreferred;

    /**
     * Creates a new FormView object.
     *
     * <p>
     *  创建一个新的FormView对象。
     * 
     * 
     * @param elem the element to decorate
     */
    public FormView(Element elem) {
        super(elem);
    }

    /**
     * Create the component.  This is basically a
     * big switch statement based upon the tag type
     * and html attributes of the associated element.
     * <p>
     *  创建组件。这基本上是一个大的switch语句,基于相关元素的标签类型和html属性。
     * 
     */
    protected Component createComponent() {
        AttributeSet attr = getElement().getAttributes();
        HTML.Tag t = (HTML.Tag)
            attr.getAttribute(StyleConstants.NameAttribute);
        JComponent c = null;
        Object model = attr.getAttribute(StyleConstants.ModelAttribute);

        // Remove listeners previously registered in shared model
        // when a new UI component is replaced.  See bug 7189299.
        removeStaleListenerForModel(model);
        if (t == HTML.Tag.INPUT) {
            c = createInputComponent(attr, model);
        } else if (t == HTML.Tag.SELECT) {

            if (model instanceof OptionListModel) {

                JList list = new JList((ListModel) model);
                int size = HTML.getIntegerAttributeValue(attr,
                                                         HTML.Attribute.SIZE,
                                                         1);
                list.setVisibleRowCount(size);
                list.setSelectionModel((ListSelectionModel)model);
                c = new JScrollPane(list);
            } else {
                c = new JComboBox((ComboBoxModel) model);
                maxIsPreferred = 3;
            }
        } else if (t == HTML.Tag.TEXTAREA) {
            JTextArea area = new JTextArea((Document) model);
            int rows = HTML.getIntegerAttributeValue(attr,
                                                     HTML.Attribute.ROWS,
                                                     1);
            area.setRows(rows);
            int cols = HTML.getIntegerAttributeValue(attr,
                                                     HTML.Attribute.COLS,
                                                     20);
            maxIsPreferred = 3;
            area.setColumns(cols);
            c = new JScrollPane(area,
                                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        }

        if (c != null) {
            c.setAlignmentY(1.0f);
        }
        return c;
    }


    /**
     * Creates a component for an &lt;INPUT&gt; element based on the
     * value of the "type" attribute.
     *
     * <p>
     *  创建&lt; INPUT&gt;元素基于"type"属性的值。
     * 
     * 
     * @param set of attributes associated with the &lt;INPUT&gt; element.
     * @param model the value of the StyleConstants.ModelAttribute
     * @return the component.
     */
    private JComponent createInputComponent(AttributeSet attr, Object model) {
        JComponent c = null;
        String type = (String) attr.getAttribute(HTML.Attribute.TYPE);

        if (type.equals("submit") || type.equals("reset")) {
            String value = (String)
                attr.getAttribute(HTML.Attribute.VALUE);
            if (value == null) {
                if (type.equals("submit")) {
                    value = UIManager.getString("FormView.submitButtonText");
                } else {
                    value = UIManager.getString("FormView.resetButtonText");
                }
            }
            JButton button = new JButton(value);
            if (model != null) {
                button.setModel((ButtonModel)model);
                button.addActionListener(this);
            }
            c = button;
            maxIsPreferred = 3;
        } else if (type.equals("image")) {
            String srcAtt = (String) attr.getAttribute(HTML.Attribute.SRC);
            JButton button;
            try {
                URL base = ((HTMLDocument)getElement().getDocument()).getBase();
                URL srcURL = new URL(base, srcAtt);
                Icon icon = new ImageIcon(srcURL);
                button  = new JButton(icon);
            } catch (MalformedURLException e) {
                button = new JButton(srcAtt);
            }
            if (model != null) {
                button.setModel((ButtonModel)model);
                button.addMouseListener(new MouseEventListener());
            }
            c = button;
            maxIsPreferred = 3;
        } else if (type.equals("checkbox")) {
            c = new JCheckBox();
            if (model != null) {
                ((JCheckBox)c).setModel((JToggleButton.ToggleButtonModel) model);
            }
            maxIsPreferred = 3;
        } else if (type.equals("radio")) {
            c = new JRadioButton();
            if (model != null) {
                ((JRadioButton)c).setModel((JToggleButton.ToggleButtonModel)model);
            }
            maxIsPreferred = 3;
        } else if (type.equals("text")) {
            int size = HTML.getIntegerAttributeValue(attr,
                                                     HTML.Attribute.SIZE,
                                                     -1);
            JTextField field;
            if (size > 0) {
                field = new JTextField();
                field.setColumns(size);
            }
            else {
                field = new JTextField();
                field.setColumns(20);
            }
            c = field;
            if (model != null) {
                field.setDocument((Document) model);
            }
            field.addActionListener(this);
            maxIsPreferred = 3;
        } else if (type.equals("password")) {
            JPasswordField field = new JPasswordField();
            c = field;
            if (model != null) {
                field.setDocument((Document) model);
            }
            int size = HTML.getIntegerAttributeValue(attr,
                                                     HTML.Attribute.SIZE,
                                                     -1);
            field.setColumns((size > 0) ? size : 20);
            field.addActionListener(this);
            maxIsPreferred = 3;
        } else if (type.equals("file")) {
            JTextField field = new JTextField();
            if (model != null) {
                field.setDocument((Document)model);
            }
            int size = HTML.getIntegerAttributeValue(attr, HTML.Attribute.SIZE,
                                                     -1);
            field.setColumns((size > 0) ? size : 20);
            JButton browseButton = new JButton(UIManager.getString
                                           ("FormView.browseFileButtonText"));
            Box box = Box.createHorizontalBox();
            box.add(field);
            box.add(Box.createHorizontalStrut(5));
            box.add(browseButton);
            browseButton.addActionListener(new BrowseFileAction(
                                           attr, (Document)model));
            c = box;
            maxIsPreferred = 3;
        }
        return c;
    }

    private void removeStaleListenerForModel(Object model) {
        if (model instanceof DefaultButtonModel) {
            // case of JButton whose model is DefaultButtonModel
            // Need to remove stale ActionListener, ChangeListener and
            // ItemListener that are instance of AbstractButton$Handler.
            DefaultButtonModel buttonModel = (DefaultButtonModel) model;
            String listenerClass = "javax.swing.AbstractButton$Handler";
            for (ActionListener listener : buttonModel.getActionListeners()) {
                if (listenerClass.equals(listener.getClass().getName())) {
                    buttonModel.removeActionListener(listener);
                }
            }
            for (ChangeListener listener : buttonModel.getChangeListeners()) {
                if (listenerClass.equals(listener.getClass().getName())) {
                    buttonModel.removeChangeListener(listener);
                }
            }
            for (ItemListener listener : buttonModel.getItemListeners()) {
                if (listenerClass.equals(listener.getClass().getName())) {
                    buttonModel.removeItemListener(listener);
                }
            }
        } else if (model instanceof AbstractListModel) {
            // case of JComboBox and JList
            // For JList, the stale ListDataListener is instance
            // BasicListUI$Handler.
            // For JComboBox, there are 2 stale ListDataListeners, which are
            // BasicListUI$Handler and BasicComboBoxUI$Handler.
            AbstractListModel listModel = (AbstractListModel) model;
            String listenerClass1 =
                    "javax.swing.plaf.basic.BasicListUI$Handler";
            String listenerClass2 =
                    "javax.swing.plaf.basic.BasicComboBoxUI$Handler";
            for (ListDataListener listener : listModel.getListDataListeners()) {
                if (listenerClass1.equals(listener.getClass().getName())
                        || listenerClass2.equals(listener.getClass().getName()))
                {
                    listModel.removeListDataListener(listener);
                }
            }
        } else if (model instanceof AbstractDocument) {
            // case of JPasswordField, JTextField and JTextArea
            // All have 2 stale DocumentListeners.
            String listenerClass1 =
                    "javax.swing.plaf.basic.BasicTextUI$UpdateHandler";
            String listenerClass2 =
                    "javax.swing.text.DefaultCaret$Handler";
            AbstractDocument docModel = (AbstractDocument) model;
            for (DocumentListener listener : docModel.getDocumentListeners()) {
                if (listenerClass1.equals(listener.getClass().getName())
                        || listenerClass2.equals(listener.getClass().getName()))
                {
                    docModel.removeDocumentListener(listener);
                }
            }
        }
    }

    /**
     * Determines the maximum span for this view along an
     * axis. For certain components, the maximum and preferred span are the
     * same. For others this will return the value
     * returned by Component.getMaximumSize along the
     * axis of interest.
     *
     * <p>
     *  确定沿轴的此视图的最大跨度。对于某些组件,最大和首选跨度相同。对于其他人,这将返回Component.getMaximumSize沿着感兴趣的轴返回的值。
     * 
     * 
     * @param axis may be either View.X_AXIS or View.Y_AXIS
     * @return   the span the view would like to be rendered into &gt;= 0.
     *           Typically the view is told to render into the span
     *           that is returned, although there is no guarantee.
     *           The parent may choose to resize or break the view.
     * @exception IllegalArgumentException for an invalid axis
     */
    public float getMaximumSpan(int axis) {
        switch (axis) {
        case View.X_AXIS:
            if ((maxIsPreferred & 1) == 1) {
                super.getMaximumSpan(axis);
                return getPreferredSpan(axis);
            }
            return super.getMaximumSpan(axis);
        case View.Y_AXIS:
            if ((maxIsPreferred & 2) == 2) {
                super.getMaximumSpan(axis);
                return getPreferredSpan(axis);
            }
            return super.getMaximumSpan(axis);
        default:
            break;
        }
        return super.getMaximumSpan(axis);
    }


    /**
     * Responsible for processing the ActionEvent.
     * If the element associated with the FormView,
     * has a type of "submit", "reset", "text" or "password"
     * then the action is processed.  In the case of a "submit"
     * the form is submitted.  In the case of a "reset"
     * the form is reset to its original state.
     * In the case of "text" or "password", if the
     * element is the last one of type "text" or "password",
     * the form is submitted.  Otherwise, focus is transferred
     * to the next component in the form.
     *
     * <p>
     * 负责处理ActionEvent。如果与FormView关联的元素具有"提交","重置","文本"或"密码"类型,则处理动作。在"提交"的情况下,提交表单。在"复位"的情况下,表单被重置为其原始状态。
     * 在"文本"或"密码"的情况下,如果元素是类型"文本"或"密码"的最后一个,则提交表单。否则,焦点将传递到窗体中的下一个组件。
     * 
     * 
     * @param evt the ActionEvent.
     */
    public void actionPerformed(ActionEvent evt) {
        Element element = getElement();
        StringBuilder dataBuffer = new StringBuilder();
        HTMLDocument doc = (HTMLDocument)getDocument();
        AttributeSet attr = element.getAttributes();

        String type = (String) attr.getAttribute(HTML.Attribute.TYPE);

        if (type.equals("submit")) {
            getFormData(dataBuffer);
            submitData(dataBuffer.toString());
        } else if (type.equals("reset")) {
            resetForm();
        } else if (type.equals("text") || type.equals("password")) {
            if (isLastTextOrPasswordField()) {
                getFormData(dataBuffer);
                submitData(dataBuffer.toString());
            } else {
                getComponent().transferFocus();
            }
        }
    }


    /**
     * This method is responsible for submitting the form data.
     * A thread is forked to undertake the submission.
     * <p>
     *  此方法负责提交表单数据。一个线程被分叉进行提交。
     * 
     */
    protected void submitData(String data) {
        Element form = getFormElement();
        AttributeSet attrs = form.getAttributes();
        HTMLDocument doc = (HTMLDocument) form.getDocument();
        URL base = doc.getBase();

        String target = (String) attrs.getAttribute(HTML.Attribute.TARGET);
        if (target == null) {
            target = "_self";
        }

        String method = (String) attrs.getAttribute(HTML.Attribute.METHOD);
        if (method == null) {
            method = "GET";
        }
        method = method.toLowerCase();
        boolean isPostMethod = method.equals("post");
        if (isPostMethod) {
            storePostData(doc, target, data);
        }

        String action = (String) attrs.getAttribute(HTML.Attribute.ACTION);
        URL actionURL;
        try {
            actionURL = (action == null)
                ? new URL(base.getProtocol(), base.getHost(),
                                        base.getPort(), base.getFile())
                : new URL(base, action);
            if (!isPostMethod) {
                String query = data.toString();
                actionURL = new URL(actionURL + "?" + query);
            }
        } catch (MalformedURLException e) {
            actionURL = null;
        }
        final JEditorPane c = (JEditorPane) getContainer();
        HTMLEditorKit kit = (HTMLEditorKit) c.getEditorKit();

        FormSubmitEvent formEvent = null;
        if (!kit.isAutoFormSubmission() || doc.isFrameDocument()) {
            FormSubmitEvent.MethodType methodType = isPostMethod
                    ? FormSubmitEvent.MethodType.POST
                    : FormSubmitEvent.MethodType.GET;
            formEvent = new FormSubmitEvent(
                    FormView.this, HyperlinkEvent.EventType.ACTIVATED,
                    actionURL, form, target, methodType, data);

        }
        // setPage() may take significant time so schedule it to run later.
        final FormSubmitEvent fse = formEvent;
        final URL url = actionURL;
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                if (fse != null) {
                    c.fireHyperlinkUpdate(fse);
                } else {
                    try {
                        c.setPage(url);
                    } catch (IOException e) {
                        UIManager.getLookAndFeel().provideErrorFeedback(c);
                    }
                }
            }
        });
    }

    private void storePostData(HTMLDocument doc, String target, String data) {

        /* POST data is stored into the document property named by constant
         * PostDataProperty from where it is later retrieved by method
         * JEditorPane.getPostData().  If the current document is in a frame,
         * the data is initially put into the toplevel (frameset) document
         * property (named <PostDataProperty>.<Target frame name>).  It is the
         * responsibility of FrameView which updates the target frame
         * to move data from the frameset document property into the frame
         * document property.
         * <p>
         *  PostDataProperty从它以后被方法JEditorPane.getPostData()检索。
         * 如果当前文档在一个框架中,则数据最初被放入toplevel(框架集)文档属性(名为<PostDataProperty>。<Target frame name>)。
         *  FrameView的职责是更新目标框架,将数据从框架集文档属性移动到框架文档属性中。
         * 
         */

        Document propDoc = doc;
        String propName = PostDataProperty;

        if (doc.isFrameDocument()) {
            // find the top-most JEditorPane holding the frameset view.
            FrameView.FrameEditorPane p =
                    (FrameView.FrameEditorPane) getContainer();
            FrameView v = p.getFrameView();
            JEditorPane c = v.getOutermostJEditorPane();
            if (c != null) {
                propDoc = c.getDocument();
                propName += ("." + target);
            }
        }

        propDoc.putProperty(propName, data);
    }

    /**
     * MouseEventListener class to handle form submissions when
     * an input with type equal to image is clicked on.
     * A MouseListener is necessary since along with the image
     * data the coordinates associated with the mouse click
     * need to be submitted.
     * <p>
     *  MouseEventListener类,用于在单击类型为image的输入时处理表单提交。 MouseListener是必要的,因为与图像数据一起,需要提交与鼠标点击相关联的坐标。
     * 
     */
    protected class MouseEventListener extends MouseAdapter {

        public void mouseReleased(MouseEvent evt) {
            String imageData = getImageData(evt.getPoint());
            imageSubmit(imageData);
        }
    }

    /**
     * This method is called to submit a form in response
     * to a click on an image -- an &lt;INPUT&gt; form
     * element of type "image".
     *
     * <p>
     *  调用此方法以响应于对图像的点击而提交表单 - &lt; INPUT&gt;形式的"图像"元素。
     * 
     * 
     * @param imageData the mouse click coordinates.
     */
    protected void imageSubmit(String imageData) {

        StringBuilder dataBuffer = new StringBuilder();
        Element elem = getElement();
        HTMLDocument hdoc = (HTMLDocument)elem.getDocument();
        getFormData(dataBuffer);
        if (dataBuffer.length() > 0) {
            dataBuffer.append('&');
        }
        dataBuffer.append(imageData);
        submitData(dataBuffer.toString());
        return;
    }

    /**
     * Extracts the value of the name attribute
     * associated with the input element of type
     * image.  If name is defined it is encoded using
     * the URLEncoder.encode() method and the
     * image data is returned in the following format:
     *      name + ".x" +"="+ x +"&"+ name +".y"+"="+ y
     * otherwise,
     *      "x="+ x +"&y="+ y
     *
     * <p>
     * 提取与image类型的输入元素关联的name属性的值。
     * 如果name被定义,它使用URLEncoder.encode()方法编码,并且图像数据以以下格式返回：name +".x"+"="+ x +"&"+ name +"。
     * y" "="+ y否则,"x ="+ x +"&y ="+ y。
     * 
     * 
     * @param point associated with the mouse click.
     * @return the image data.
     */
    private String getImageData(Point point) {

        String mouseCoords = point.x + ":" + point.y;
        int sep = mouseCoords.indexOf(':');
        String x = mouseCoords.substring(0, sep);
        String y = mouseCoords.substring(++sep);
        String name = (String) getElement().getAttributes().getAttribute(HTML.Attribute.NAME);

        String data;
        if (name == null || name.equals("")) {
            data = "x="+ x +"&y="+ y;
        } else {
            name = URLEncoder.encode(name);
            data = name + ".x" +"="+ x +"&"+ name +".y"+"="+ y;
        }
        return data;
    }


    /**
     * The following methods provide functionality required to
     * iterate over a the elements of the form and in the case
     * of a form submission, extract the data from each model
     * that is associated with each form element, and in the
     * case of reset, reinitialize the each model to its
     * initial state.
     * <p>
     *  以下方法提供迭代表单元素所需的功能,以及在表单提交的情况下,从与每个表单元素相关联的每个模型中提取数据,并且在重置的情况下,重新初始化每个模型其初始状态。
     * 
     */


    /**
     * Returns the Element representing the <code>FORM</code>.
     * <p>
     *  返回表示<code> FORM </code>的元素。
     * 
     */
    private Element getFormElement() {
        Element elem = getElement();
        while (elem != null) {
            if (elem.getAttributes().getAttribute
                (StyleConstants.NameAttribute) == HTML.Tag.FORM) {
                return elem;
            }
            elem = elem.getParentElement();
        }
        return null;
    }

    /**
     * Iterates over the
     * element hierarchy, extracting data from the
     * models associated with the relevant form elements.
     * "Relevant" means the form elements that are part
     * of the same form whose element triggered the submit
     * action.
     *
     * <p>
     *  迭代元素层次结构,从与相关表单元素相关联的模型中提取数据。 "相关"是指表单元素是其元素触发提交操作的同一表单的一部分。
     * 
     * 
     * @param buffer        the buffer that contains that data to submit
     * @param targetElement the element that triggered the
     *                      form submission
     */
    private void getFormData(StringBuilder buffer) {
        Element formE = getFormElement();
        if (formE != null) {
            ElementIterator it = new ElementIterator(formE);
            Element next;

            while ((next = it.next()) != null) {
                if (isControl(next)) {
                    String type = (String)next.getAttributes().getAttribute
                                       (HTML.Attribute.TYPE);

                    if (type != null && type.equals("submit") &&
                        next != getElement()) {
                        // do nothing - this submit is not the trigger
                    } else if (type == null || !type.equals("image")) {
                        // images only result in data if they triggered
                        // the submit and they require that the mouse click
                        // coords be appended to the data.  Hence its
                        // processing is handled by the view.
                        loadElementDataIntoBuffer(next, buffer);
                    }
                }
            }
        }
    }

    /**
     * Loads the data
     * associated with the element into the buffer.
     * The format in which data is appended depends
     * on the type of the form element.  Essentially
     * data is loaded in name/value pairs.
     *
     * <p>
     *  将与元素关联的数据装入缓冲区。附加数据的格式取决于表单元素的类型。基本上,数据以名称/值对加载。
     * 
     */
    private void loadElementDataIntoBuffer(Element elem, StringBuilder buffer) {

        AttributeSet attr = elem.getAttributes();
        String name = (String)attr.getAttribute(HTML.Attribute.NAME);
        if (name == null) {
            return;
        }
        String value = null;
        HTML.Tag tag = (HTML.Tag)elem.getAttributes().getAttribute
                                  (StyleConstants.NameAttribute);

        if (tag == HTML.Tag.INPUT) {
            value = getInputElementData(attr);
        } else if (tag ==  HTML.Tag.TEXTAREA) {
            value = getTextAreaData(attr);
        } else if (tag == HTML.Tag.SELECT) {
            loadSelectData(attr, buffer);
        }

        if (name != null && value != null) {
            appendBuffer(buffer, name, value);
        }
    }


    /**
     * Returns the data associated with an &lt;INPUT&gt; form
     * element.  The value of "type" attributes is
     * used to determine the type of the model associated
     * with the element and then the relevant data is
     * extracted.
     * <p>
     *  返回与&lt; INPUT&gt;表单元素。 "type"属性的值用于确定与元素相关联的模型的类型,然后提取相关数据。
     * 
     */
    private String getInputElementData(AttributeSet attr) {

        Object model = attr.getAttribute(StyleConstants.ModelAttribute);
        String type = (String) attr.getAttribute(HTML.Attribute.TYPE);
        String value = null;

        if (type.equals("text") || type.equals("password")) {
            Document doc = (Document)model;
            try {
                value = doc.getText(0, doc.getLength());
            } catch (BadLocationException e) {
                value = null;
            }
        } else if (type.equals("submit") || type.equals("hidden")) {
            value = (String) attr.getAttribute(HTML.Attribute.VALUE);
            if (value == null) {
                value = "";
            }
        } else if (type.equals("radio") || type.equals("checkbox")) {
            ButtonModel m = (ButtonModel)model;
            if (m.isSelected()) {
                value = (String) attr.getAttribute(HTML.Attribute.VALUE);
                if (value == null) {
                    value = "on";
                }
            }
        } else if (type.equals("file")) {
            Document doc = (Document)model;
            String path;

            try {
                path = doc.getText(0, doc.getLength());
            } catch (BadLocationException e) {
                path = null;
            }
            if (path != null && path.length() > 0) {
                value = path;
            }
        }
        return value;
    }

    /**
     * Returns the data associated with the &lt;TEXTAREA&gt; form
     * element.  This is done by getting the text stored in the
     * Document model.
     * <p>
     *  返回与&lt; TEXTAREA&gt;关联的数据。表单元素。这通过将文本存储在Document模型中来完成。
     * 
     */
    private String getTextAreaData(AttributeSet attr) {
        Document doc = (Document)attr.getAttribute(StyleConstants.ModelAttribute);
        try {
            return doc.getText(0, doc.getLength());
        } catch (BadLocationException e) {
            return null;
        }
    }


    /**
     * Loads the buffer with the data associated with the Select
     * form element.  Basically, only items that are selected
     * and have their name attribute set are added to the buffer.
     * <p>
     * 使用与选择表单元素相关联的数据加载缓冲区。基本上,只有选择并设置其名称属性的项目才会添加到缓冲区。
     * 
     */
    private void loadSelectData(AttributeSet attr, StringBuilder buffer) {

        String name = (String)attr.getAttribute(HTML.Attribute.NAME);
        if (name == null) {
            return;
        }
        Object m = attr.getAttribute(StyleConstants.ModelAttribute);
        if (m instanceof OptionListModel) {
            OptionListModel<Option> model = (OptionListModel<Option>) m;

            for (int i = 0; i < model.getSize(); i++) {
                if (model.isSelectedIndex(i)) {
                    Option option = model.getElementAt(i);
                    appendBuffer(buffer, name, option.getValue());
                }
            }
        } else if (m instanceof ComboBoxModel) {
            ComboBoxModel model = (ComboBoxModel)m;
            Option option = (Option)model.getSelectedItem();
            if (option != null) {
                appendBuffer(buffer, name, option.getValue());
            }
        }
    }

    /**
     * Appends name / value pairs into the
     * buffer.  Both names and values are encoded using the
     * URLEncoder.encode() method before being added to the
     * buffer.
     * <p>
     *  将名称/值对附加到缓冲区中。在添加到缓冲区之前,名称和值都使用URLEncoder.encode()方法进行编码。
     * 
     */
    private void appendBuffer(StringBuilder buffer, String name, String value) {
        if (buffer.length() > 0) {
            buffer.append('&');
        }
        String encodedName = URLEncoder.encode(name);
        buffer.append(encodedName);
        buffer.append('=');
        String encodedValue = URLEncoder.encode(value);
        buffer.append(encodedValue);
    }

    /**
     * Returns true if the Element <code>elem</code> represents a control.
     * <p>
     *  如果元素<code> elem </code>表示控件,则返回true。
     * 
     */
    private boolean isControl(Element elem) {
        return elem.isLeaf();
    }

    /**
     * Iterates over the element hierarchy to determine if
     * the element parameter, which is assumed to be an
     * &lt;INPUT&gt; element of type password or text, is the last
     * one of either kind, in the form to which it belongs.
     * <p>
     *  迭代元素层次结构以确定元素参数(假定为&lt; INPUT&gt;密码或文本类型的元素是它所属的表单中的最后一个类型。
     * 
     */
    boolean isLastTextOrPasswordField() {
        Element parent = getFormElement();
        Element elem = getElement();

        if (parent != null) {
            ElementIterator it = new ElementIterator(parent);
            Element next;
            boolean found = false;

            while ((next = it.next()) != null) {
                if (next == elem) {
                    found = true;
                }
                else if (found && isControl(next)) {
                    AttributeSet elemAttr = next.getAttributes();

                    if (HTMLDocument.matchNameAttribute
                                     (elemAttr, HTML.Tag.INPUT)) {
                        String type = (String)elemAttr.getAttribute
                                                  (HTML.Attribute.TYPE);

                        if ("text".equals(type) || "password".equals(type)) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    /**
     * Resets the form
     * to its initial state by reinitializing the models
     * associated with each form element to their initial
     * values.
     *
     * param elem the element that triggered the reset
     * <p>
     *  通过将与每个表单元素相关联的模型重新初始化为其初始值,将表单重置为其初始状态。
     * 
     *  param elem触发复位的元素
     * 
     */
    void resetForm() {
        Element parent = getFormElement();

        if (parent != null) {
            ElementIterator it = new ElementIterator(parent);
            Element next;

            while((next = it.next()) != null) {
                if (isControl(next)) {
                    AttributeSet elemAttr = next.getAttributes();
                    Object m = elemAttr.getAttribute(StyleConstants.
                                                     ModelAttribute);
                    if (m instanceof TextAreaDocument) {
                        TextAreaDocument doc = (TextAreaDocument)m;
                        doc.reset();
                    } else if (m instanceof PlainDocument) {
                        try {
                            PlainDocument doc =  (PlainDocument)m;
                            doc.remove(0, doc.getLength());
                            if (HTMLDocument.matchNameAttribute
                                             (elemAttr, HTML.Tag.INPUT)) {
                                String value = (String)elemAttr.
                                           getAttribute(HTML.Attribute.VALUE);
                                if (value != null) {
                                    doc.insertString(0, value, null);
                                }
                            }
                        } catch (BadLocationException e) {
                        }
                    } else if (m instanceof OptionListModel) {
                        OptionListModel model = (OptionListModel) m;
                        int size = model.getSize();
                        for (int i = 0; i < size; i++) {
                            model.removeIndexInterval(i, i);
                        }
                        BitSet selectionRange = model.getInitialSelection();
                        for (int i = 0; i < selectionRange.size(); i++) {
                            if (selectionRange.get(i)) {
                                model.addSelectionInterval(i, i);
                            }
                        }
                    } else if (m instanceof OptionComboBoxModel) {
                        OptionComboBoxModel model = (OptionComboBoxModel) m;
                        Option option = model.getInitialSelection();
                        if (option != null) {
                            model.setSelectedItem(option);
                        }
                    } else if (m instanceof JToggleButton.ToggleButtonModel) {
                        boolean checked = ((String)elemAttr.getAttribute
                                           (HTML.Attribute.CHECKED) != null);
                        JToggleButton.ToggleButtonModel model =
                                        (JToggleButton.ToggleButtonModel)m;
                        model.setSelected(checked);
                    }
                }
            }
        }
    }


    /**
     * BrowseFileAction is used for input type == file. When the user
     * clicks the button a JFileChooser is brought up allowing the user
     * to select a file in the file system. The resulting path to the selected
     * file is set in the text field (actually an instance of Document).
     * <p>
     */
    private class BrowseFileAction implements ActionListener {
        private AttributeSet attrs;
        private Document model;

        BrowseFileAction(AttributeSet attrs, Document model) {
            this.attrs = attrs;
            this.model = model;
        }

        public void actionPerformed(ActionEvent ae) {
            // PENDING: When mime support is added to JFileChooser use the
            // accept value of attrs.
            JFileChooser fc = new JFileChooser();
            fc.setMultiSelectionEnabled(false);
            if (fc.showOpenDialog(getContainer()) ==
                  JFileChooser.APPROVE_OPTION) {
                File selected = fc.getSelectedFile();

                if (selected != null) {
                    try {
                        if (model.getLength() > 0) {
                            model.remove(0, model.getLength());
                        }
                        model.insertString(0, selected.getPath(), null);
                    } catch (BadLocationException ble) {}
                }
            }
        }
    }
}
