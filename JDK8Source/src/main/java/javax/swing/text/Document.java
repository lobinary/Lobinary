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
package javax.swing.text;

import javax.swing.event.*;

/**
 * <p>
 * The <code>Document</code> is a container for text that serves
 * as the model for swing text components.  The goal for this
 * interface is to scale from very simple needs (a plain text textfield)
 * to complex needs (an HTML or XML document, for example).
 *
 * <p><b><font size=+1>Content</font></b>
 * <p>
 * At the simplest level, text can be
 * modeled as a linear sequence of characters. To support
 * internationalization, the Swing text model uses
 * <a href="http://www.unicode.org/">unicode</a> characters.
 * The sequence of characters displayed in a text component is
 * generally referred to as the component's <em>content</em>.
 * <p>
 * To refer to locations within the sequence, the coordinates
 * used are the location between two characters.  As the diagram
 * below shows, a location in a text document can be referred to
 * as a position, or an offset. This position is zero-based.
 * <p style="text-align:center"><img src="doc-files/Document-coord.gif"
 * alt="The following text describes this graphic.">
 * <p>
 * In the example, if the content of a document is the
 * sequence "The quick brown fox," as shown in the preceding diagram,
 * the location just before the word "The" is 0, and the location after
 * the word "The" and before the whitespace that follows it is 3.
 * The entire sequence of characters in the sequence "The" is called a
 * <em>range</em>.
 * <p>The following methods give access to the character data
 * that makes up the content.
 * <ul>
 * <li>{@link #getLength()}
 * <li>{@link #getText(int, int)}
 * <li>{@link #getText(int, int, javax.swing.text.Segment)}
 * </ul>
 * <p><b><font size=+1>Structure</font></b>
 * <p>
 * Text is rarely represented simply as featureless content. Rather,
 * text typically has some sort of structure associated with it.
 * Exactly what structure is modeled is up to a particular Document
 * implementation.  It might be as simple as no structure (i.e. a
 * simple text field), or it might be something like diagram below.
 * <p style="text-align:center"><img src="doc-files/Document-structure.gif"
 * alt="Diagram shows Book->Chapter->Paragraph">
 * <p>
 * The unit of structure (i.e. a node of the tree) is referred to
 * by the <a href="Element.html">Element</a> interface.  Each Element
 * can be tagged with a set of attributes.  These attributes
 * (name/value pairs) are defined by the
 * <a href="AttributeSet.html">AttributeSet</a> interface.
 * <p>The following methods give access to the document structure.
 * <ul>
 * <li>{@link #getDefaultRootElement()}
 * <li>{@link #getRootElements()}
 * </ul>
 *
 * <p><b><font size=+1>Mutations</font></b>
 * <p>
 * All documents need to be able to add and remove simple text.
 * Typically, text is inserted and removed via gestures from
 * a keyboard or a mouse.  What effect the insertion or removal
 * has upon the document structure is entirely up to the
 * implementation of the document.
 * <p>The following methods are related to mutation of the
 * document content:
 * <ul>
 * <li>{@link #insertString(int, java.lang.String, javax.swing.text.AttributeSet)}
 * <li>{@link #remove(int, int)}
 * <li>{@link #createPosition(int)}
 * </ul>
 *
 * <p><b><font size=+1>Notification</font></b>
 * <p>
 * Mutations to the <code>Document</code> must be communicated to
 * interested observers.  The notification of change follows the event model
 * guidelines that are specified for JavaBeans.  In the JavaBeans
 * event model, once an event notification is dispatched, all listeners
 * must be notified before any further mutations occur to the source
 * of the event.  Further, order of delivery is not guaranteed.
 * <p>
 * Notification is provided as two separate events,
 * <a href="../event/DocumentEvent.html">DocumentEvent</a>, and
 * <a href="../event/UndoableEditEvent.html">UndoableEditEvent</a>.
 * If a mutation is made to a <code>Document</code> through its api,
 * a <code>DocumentEvent</code> will be sent to all of the registered
 * <code>DocumentListeners</code>.  If the <code>Document</code>
 * implementation supports undo/redo capabilities, an
 * <code>UndoableEditEvent</code> will be sent
 * to all of the registered <code>UndoableEditListener</code>s.
 * If an undoable edit is undone, a <code>DocumentEvent</code> should be
 * fired from the Document to indicate it has changed again.
 * In this case however, there should be no <code>UndoableEditEvent</code>
 * generated since that edit is actually the source of the change
 * rather than a mutation to the <code>Document</code> made through its
 * api.
 * <p style="text-align:center"><img src="doc-files/Document-notification.gif"
 * alt="The preceding text describes this graphic.">
 * <p>
 * Referring to the above diagram, suppose that the component shown
 * on the left mutates the document object represented by the blue
 * rectangle. The document responds by dispatching a DocumentEvent to
 * both component views and sends an UndoableEditEvent to the listening
 * logic, which maintains a history buffer.
 * <p>
 * Now suppose that the component shown on the right mutates the same
 * document.  Again, the document dispatches a DocumentEvent to both
 * component views and sends an UndoableEditEvent to the listening logic
 * that is maintaining the history buffer.
 * <p>
 * If the history buffer is then rolled back (i.e. the last UndoableEdit
 * undone), a DocumentEvent is sent to both views, causing both of them to
 * reflect the undone mutation to the document (that is, the
 * removal of the right component's mutation). If the history buffer again
 * rolls back another change, another DocumentEvent is sent to both views,
 * causing them to reflect the undone mutation to the document -- that is,
 * the removal of the left component's mutation.
 * <p>
 * The methods related to observing mutations to the document are:
 * <ul>
 * <li><a href="#addDocumentListener(javax.swing.event.DocumentListener)">addDocumentListener(DocumentListener)</a>
 * <li><a href="#removeDocumentListener(javax.swing.event.DocumentListener)">removeDocumentListener(DocumentListener)</a>
 * <li><a href="#addUndoableEditListener(javax.swing.event.UndoableEditListener)">addUndoableEditListener(UndoableEditListener)</a>
 * <li><a href="#removeUndoableEditListener(javax.swing.event.UndoableEditListener)">removeUndoableEditListener(UndoableEditListener)</a>
 * </ul>
 *
 * <p><b><font size=+1>Properties</font></b>
 * <p>
 * Document implementations will generally have some set of properties
 * associated with them at runtime.  Two well known properties are the
 * <a href="#StreamDescriptionProperty">StreamDescriptionProperty</a>,
 * which can be used to describe where the <code>Document</code> came from,
 * and the <a href="#TitleProperty">TitleProperty</a>, which can be used to
 * name the <code>Document</code>.  The methods related to the properties are:
 * <ul>
 * <li>{@link #getProperty(java.lang.Object)}
 * <li>{@link #putProperty(java.lang.Object, java.lang.Object)}
 * </ul>
 *
 * <p>For more information on the <code>Document</code> class, see
 * <a href="http://java.sun.com/products/jfc/tsc">The Swing Connection</a>
 * and most particularly the article,
 * <a href="http://java.sun.com/products/jfc/tsc/articles/text/element_interface">
 * The Element Interface</a>.
 *
 * <p>
 * <p>
 *  <code> Document </code>是用作文本的容器,用作swing文本组件的模型。这个接口的目标是从非常简单的需求(纯文本文本字段)到复杂的需求(例如HTML或XML文档)。
 * 
 *  <p> <b> <font size = + 1>内容</font> </b>
 * <p>
 *  在最简单的层次上,文本可以被建模为线性的字符序列。为了支持国际化,Swing文本模型使用<a href="http://www.unicode.org/"> unicode </a>字符。
 * 在文本组件中显示的字符序列通常被称为组件的<em>内容</em>。
 * <p>
 *  要引用序列中的位置,所使用的坐标是两个字符之间的位置。如下图所示,文本文档中的位置可以称为位置或偏移量。此位置为零。
 *  <p style ="text-align：center"> <img src ="doc-files / Document-coord.gif"。
 * alt="The following text describes this graphic.">
 * <p>
 *  在该示例中,如果文档的内容是序列"快速棕狐狸",如前图所示,恰好在单词"The"之前的位置是0,并且单词"The"之后的位置和之前其后面的空格是3.序列"The"中的整个字符序列被称为<em>范围</em>
 * 。
 *  <p>以下方法允许访问组成内容的字符数据。
 * <ul>
 * <li> {@ link #getLength()} <li> {@ link #getText(int,int)} <li> {@ link #getText(int,int,javax.swing.text.Segment)}
 * 。
 * </ul>
 *  <p> <b> <font size = + 1>结构</font> </b>
 * <p>
 *  文本很少被表示为无特征的内容。相反,文本通常具有与其相关联的某种结构。具体什么结构被建模取决于特定的Document实现。它可能是简单的没有结构(即一个简单的文本字段),或者它可能是类似下面的图。
 *  <p style ="text-align：center"> <img src ="doc-files / Document-structure.gif"。
 * alt="Diagram shows Book->Chapter->Paragraph">
 * <p>
 *  结构单元(即树的节点)由<a href="Element.html">元素</a>界面引用。每个元素可以用一组属性标记。
 * 这些属性(名称/值对)由<a href="AttributeSet.html"> AttributeSet </a>界面定义。 <p>以下方法允许访问文档结构。
 * <ul>
 *  <li> {@ link #getDefaultRootElement()} <li> {@ link #getRootElements()}
 * </ul>
 * 
 *  <p> <b> <font size = + 1>突变</font> </b>
 * <p>
 *  所有文档都需要能够添加和删除简单文本。通常,通过来自键盘或鼠标的手势插入和移除文本。插入或移除对文档结构的影响完全取决于文档的实现。 <p>以下方法与文档内容的突变相关：
 * <ul>
 *  <li> {@ link #insertString(int,java.lang.String,javax.swing.text.AttributeSet)} <li> {@ link #remove(int,int)}
 *  <li> {@ link #createPosition )}。
 * </ul>
 * 
 * <p> <b> <font size = + 1>通知</font> </b>
 * <p>
 *  对<code>文档</code>的变更必须传达给感兴趣的观察者。更改通知遵循为JavaBean指定的事件模型准则。
 * 在JavaBeans事件模型中,一旦分派事件通知,所有侦听器必须在事件源发生任何进一步突变之前通知。此外,不保证交货的顺序。
 * <p>
 *  通知是作为两个单独的事件提供的,<a href="../event/DocumentEvent.html"> DocumentEvent </a>和<a href="../event/UndoableEditEvent.html">
 *  UndoableEditEvent </a> 。
 * 如果通过其api对<code> Document </code>作出突变,则<code> DocumentEvent </code>将被发送到所有注册的<code> DocumentListeners 
 * </code>。
 * 如果<code> Document </code>实现支持撤消/重做功能,则<code> UndoableEditEvent </code>将被发送到所有注册的<code> UndoableEditLi
 * stener </code>。
 * 如果撤消了可撤销的编辑,则应从文档中触发<code> DocumentEvent </code>以指示其再次更改。
 * 在这种情况下,应该没有生成<code> UndoableEditEvent </code>,因为该编辑实际上是更改的来源,而不是通过其api对<code> Document </code>的突变。
 *  <p style ="text-align：center"> <img src ="doc-files / Document-notification.gif"。
 * alt="The preceding text describes this graphic.">
 * <p>
 * 参考上面的图,假设左侧所示的组件使由蓝色矩形表示的文档对象变形。文档通过向两个组件视图分派DocumentEvent来响应,并向侦听逻辑发送UndoableEditEvent,该逻辑维护历史缓冲器。
 * <p>
 *  现在假设右侧显示的组件改变了同一个文档。同样,文档向两个组件视图分派DocumentEvent,并向正在维护历史缓冲区的侦听逻辑发送UndoableEditEvent。
 * <p>
 *  如果历史缓冲区随后被回退(即最后一个UndoableEdit被撤消),则DocumentEvent被发送到两个视图,使得它们都反映文档的未发生的突变(即去除正确的组件的突变)。
 * 如果历史缓冲区再次回滚另一个更改,则另一个DocumentEvent将发送到这两个视图,从而使它们在文档中反映已撤消的突变 - 即删除左侧组件的突变。
 * <p>
 *  与观察文档的突变相关的方法是：
 * <ul>
 * <li> <a href="#addDocumentListener(javax.swing.event.DocumentListener)"> addDocumentListener(Document
 * Listener)</a> <li> <a href="#removeDocumentListener(javax.swing.event.DocumentListener)"> removeDocum
 * entListener(DocumentListener)</a> <li> <a href="#addUndoableEditListener(javax.swing.event.UndoableEditListener)">
 *  addUndoableEditListener(UndoableEditListener)</a> <li> <a href ="#removeUndoableEditListener(javax。
 *  swing.event.UndoableEditListener)"> removeUndoableEditListener(UndoableEditListener)</a>。
 * </ul>
 * 
 *  <p> <b> <font size = + 1>属性</font> </b>
 * <p>
 *  文档实现通常在运行时具有与它们相关联的一些属性集合。
 * 两个众所周知的属性是<a href="#StreamDescriptionProperty"> StreamDescriptionProperty </a>,可用于描述<code> Document </code>
 * 的来源,<a href ="#TitleProperty" > TitleProperty </a>,可用于命名<code> Document </code>。
 *  文档实现通常在运行时具有与它们相关联的一些属性集合。与属性相关的方法有：。
 * <ul>
 *  <li> {@ link #getProperty(java.lang.Object)} <li> {@ link #putProperty(java.lang.Object,java.lang.Object)}
 * 。
 * </ul>
 * 
 *  <p>有关<code>文档</code>类的详细信息,请参阅<a href="http://java.sun.com/products/jfc/tsc"> Swing连接</a>以及特别是文章,
 * <a href="http://java.sun.com/products/jfc/tsc/articles/text/element_interface">
 *  元素接口</a>。
 * 
 * 
 * @author  Timothy Prinzing
 *
 * @see javax.swing.event.DocumentEvent
 * @see javax.swing.event.DocumentListener
 * @see javax.swing.event.UndoableEditEvent
 * @see javax.swing.event.UndoableEditListener
 * @see Element
 * @see Position
 * @see AttributeSet
 */
public interface Document {

    /**
     * Returns number of characters of content currently
     * in the document.
     *
     * <p>
     *  返回文档中当前内容的字符数。
     * 
     * 
     * @return number of characters &gt;= 0
     */
    public int getLength();

    /**
     * Registers the given observer to begin receiving notifications
     * when changes are made to the document.
     *
     * <p>
     *  注册给定的观察者,以便在对文档进行更改时开始接收通知。
     * 
     * 
     * @param listener the observer to register
     * @see Document#removeDocumentListener
     */
    public void addDocumentListener(DocumentListener listener);

    /**
     * Unregisters the given observer from the notification list
     * so it will no longer receive change updates.
     *
     * <p>
     * 从通知列表中取消注册给定的观察者,因此它将不再接收更改更新。
     * 
     * 
     * @param listener the observer to register
     * @see Document#addDocumentListener
     */
    public void removeDocumentListener(DocumentListener listener);

    /**
     * Registers the given observer to begin receiving notifications
     * when undoable edits are made to the document.
     *
     * <p>
     *  注册给定的观察器,以在对文档进行可撤销的编辑时开始接收通知。
     * 
     * 
     * @param listener the observer to register
     * @see javax.swing.event.UndoableEditEvent
     */
    public void addUndoableEditListener(UndoableEditListener listener);

    /**
     * Unregisters the given observer from the notification list
     * so it will no longer receive updates.
     *
     * <p>
     *  从通知列表中取消注册给定的观察者,因此它将不再接收更新。
     * 
     * 
     * @param listener the observer to register
     * @see javax.swing.event.UndoableEditEvent
     */
    public void removeUndoableEditListener(UndoableEditListener listener);

    /**
     * Gets the properties associated with the document.
     *
     * <p>
     *  获取与文档相关联的属性。
     * 
     * 
     * @param key a non-<code>null</code> property key
     * @return the properties
     * @see #putProperty(Object, Object)
     */
    public Object getProperty(Object key);

    /**
     * Associates a property with the document.  Two standard
     * property keys provided are: <a href="#StreamDescriptionProperty">
     * <code>StreamDescriptionProperty</code></a> and
     * <a href="#TitleProperty"><code>TitleProperty</code></a>.
     * Other properties, such as author, may also be defined.
     *
     * <p>
     *  将属性与文档关联。
     * 提供的两个标准属性键为：<a href="#StreamDescriptionProperty"> <code> StreamDescriptionProperty </code> </a>和<a href="#TitleProperty">
     *  <code> TitleProperty </code> </a> 。
     *  将属性与文档关联。也可以定义其他属性,例如作者。
     * 
     * 
     * @param key the non-<code>null</code> property key
     * @param value the property value
     * @see #getProperty(Object)
     */
    public void putProperty(Object key, Object value);

    /**
     * Removes a portion of the content of the document.
     * This will cause a DocumentEvent of type
     * DocumentEvent.EventType.REMOVE to be sent to the
     * registered DocumentListeners, unless an exception
     * is thrown.  The notification will be sent to the
     * listeners by calling the removeUpdate method on the
     * DocumentListeners.
     * <p>
     * To ensure reasonable behavior in the face
     * of concurrency, the event is dispatched after the
     * mutation has occurred. This means that by the time a
     * notification of removal is dispatched, the document
     * has already been updated and any marks created by
     * <code>createPosition</code> have already changed.
     * For a removal, the end of the removal range is collapsed
     * down to the start of the range, and any marks in the removal
     * range are collapsed down to the start of the range.
     * <p style="text-align:center"><img src="doc-files/Document-remove.gif"
     *  alt="Diagram shows removal of 'quick' from 'The quick brown fox.'">
     * <p>
     * If the Document structure changed as result of the removal,
     * the details of what Elements were inserted and removed in
     * response to the change will also be contained in the generated
     * DocumentEvent. It is up to the implementation of a Document
     * to decide how the structure should change in response to a
     * remove.
     * <p>
     * If the Document supports undo/redo, an UndoableEditEvent will
     * also be generated.
     *
     * <p>
     *  删除文档内容的一部分。这将导致DocumentEvent.EventType.REMOVE类型的DocumentEvent被发送到已注册的DocumentListeners,除非抛出异常。
     * 通知将通过调用DocumentListeners上的removeUpdate方法发送给侦听器。
     * <p>
     *  为了确保面对并发的合理行为,在发生突变之后调度事件。这意味着,在发送删除通知时,文档已经更新,并且由<code> createPosition </code>创建的任何标记已经更改。
     * 对于移除,移除范围的结束向下折叠到范围的开始,并且移除范围中的任何标记都将折叠到范围的开头。
     *  <p style ="text-align：center"> <img src ="doc-files / Document-remove.gif"。
     * alt="Diagram shows removal of 'quick' from 'The quick brown fox.'">
     * <p>
     * 如果文档结构因删除而更改,则响应更改插入和删除元素的详细信息也将包含在生成的DocumentEvent中。它是由一个文档的实现决定如何结构应该改变响应删除。
     * <p>
     *  如果文档支持撤消/重做,也将生成UndoableEditEvent。
     * 
     * 
     * @param offs  the offset from the beginning &gt;= 0
     * @param len   the number of characters to remove &gt;= 0
     * @exception BadLocationException  some portion of the removal range
     *   was not a valid part of the document.  The location in the exception
     *   is the first bad position encountered.
     * @see javax.swing.event.DocumentEvent
     * @see javax.swing.event.DocumentListener
     * @see javax.swing.event.UndoableEditEvent
     * @see javax.swing.event.UndoableEditListener
     */
    public void remove(int offs, int len) throws BadLocationException;

    /**
     * Inserts a string of content.  This will cause a DocumentEvent
     * of type DocumentEvent.EventType.INSERT to be sent to the
     * registered DocumentListers, unless an exception is thrown.
     * The DocumentEvent will be delivered by calling the
     * insertUpdate method on the DocumentListener.
     * The offset and length of the generated DocumentEvent
     * will indicate what change was actually made to the Document.
     * <p style="text-align:center"><img src="doc-files/Document-insert.gif"
     *  alt="Diagram shows insertion of 'quick' in 'The quick brown fox'">
     * <p>
     * If the Document structure changed as result of the insertion,
     * the details of what Elements were inserted and removed in
     * response to the change will also be contained in the generated
     * DocumentEvent.  It is up to the implementation of a Document
     * to decide how the structure should change in response to an
     * insertion.
     * <p>
     * If the Document supports undo/redo, an UndoableEditEvent will
     * also be generated.
     *
     * <p>
     *  插入一串内容。这将导致DocumentEvent.EventType.INSERT类型的DocumentEvent发送到已注册的DocumentListers,除非抛出异常。
     *  DocumentEvent将通过调用DocumentListener上的insertUpdate方法来传递。生成的DocumentEvent的偏移量和长度将指示对文档实际做了哪些更改。
     *  <p style ="text-align：center"> <img src ="doc-files / Document-insert.gif"。
     * alt="Diagram shows insertion of 'quick' in 'The quick brown fox'">
     * <p>
     *  如果文档结构因插入而更改,则响应于更改插入和删除元素的详细信息也将包含在生成的DocumentEvent中。它是由一个文档的实现决定如何结构应该改变响应插入。
     * <p>
     *  如果文档支持撤消/重做,也将生成UndoableEditEvent。
     * 
     * 
     * @param offset  the offset into the document to insert the content &gt;= 0.
     *    All positions that track change at or after the given location
     *    will move.
     * @param str    the string to insert
     * @param a      the attributes to associate with the inserted
     *   content.  This may be null if there are no attributes.
     * @exception BadLocationException  the given insert position is not a valid
     * position within the document
     * @see javax.swing.event.DocumentEvent
     * @see javax.swing.event.DocumentListener
     * @see javax.swing.event.UndoableEditEvent
     * @see javax.swing.event.UndoableEditListener
     */
    public void insertString(int offset, String str, AttributeSet a) throws BadLocationException;

    /**
     * Fetches the text contained within the given portion
     * of the document.
     *
     * <p>
     *  获取文档给定部分中包含的文本。
     * 
     * 
     * @param offset  the offset into the document representing the desired
     *   start of the text &gt;= 0
     * @param length  the length of the desired string &gt;= 0
     * @return the text, in a String of length &gt;= 0
     * @exception BadLocationException  some portion of the given range
     *   was not a valid part of the document.  The location in the exception
     *   is the first bad position encountered.
     */
    public String getText(int offset, int length) throws BadLocationException;

    /**
     * Fetches the text contained within the given portion
     * of the document.
     * <p>
     * If the partialReturn property on the txt parameter is false, the
     * data returned in the Segment will be the entire length requested and
     * may or may not be a copy depending upon how the data was stored.
     * If the partialReturn property is true, only the amount of text that
     * can be returned without creating a copy is returned.  Using partial
     * returns will give better performance for situations where large
     * parts of the document are being scanned.  The following is an example
     * of using the partial return to access the entire document:
     *
     * <pre><code>
     *
     * &nbsp; int nleft = doc.getDocumentLength();
     * &nbsp; Segment text = new Segment();
     * &nbsp; int offs = 0;
     * &nbsp; text.setPartialReturn(true);
     * &nbsp; while (nleft &gt; 0) {
     * &nbsp;     doc.getText(offs, nleft, text);
     * &nbsp;     // do someting with text
     * &nbsp;     nleft -= text.count;
     * &nbsp;     offs += text.count;
     * &nbsp; }
     *
     * </code></pre>
     *
     * <p>
     *  获取文档给定部分中包含的文本。
     * <p>
     * 如果txt参数的partialReturn属性为false,则段中返回的数据将是请求的整个长度,根据数据的存储方式,可能或可能不是副本。
     * 如果partialReturn属性为true,则只返回无需创建副本即可返回的文本量。使用部分返回将为正在扫描文档的大部分的情况提供更好的性能。以下是使用部分返回访问整个文档的示例：。
     * 
     *  <pre> <code>
     * 
     *  &nbsp; int nleft = doc.getDocumentLength(); &nbsp; segment text = new Segment(); &nbsp; int offs = 0
     * ; &nbsp; text.setPartialReturn(true); &nbsp; while(nleft> 0){&nbsp; doc.getText(offs,nleft,text); &nbsp; // do somethinging with text&nbsp; nleft  -  = text.count; &nbsp; offs + = text.count; &nbsp; }
     * }。
     * 
     *  </code> </pre>
     * 
     * 
     * @param offset  the offset into the document representing the desired
     *   start of the text &gt;= 0
     * @param length  the length of the desired string &gt;= 0
     * @param txt the Segment object to return the text in
     *
     * @exception BadLocationException  Some portion of the given range
     *   was not a valid part of the document.  The location in the exception
     *   is the first bad position encountered.
     */
    public void getText(int offset, int length, Segment txt) throws BadLocationException;

    /**
     * Returns a position that represents the start of the document.  The
     * position returned can be counted on to track change and stay
     * located at the beginning of the document.
     *
     * <p>
     *  返回表示文档开头的位置。返回的位置可以计入跟踪更改并保留位于文档开头。
     * 
     * 
     * @return the position
     */
    public Position getStartPosition();

    /**
     * Returns a position that represents the end of the document.  The
     * position returned can be counted on to track change and stay
     * located at the end of the document.
     *
     * <p>
     *  返回表示文档结尾的位置。返回的位置可以计入跟踪更改并保留位于文档末尾。
     * 
     * 
     * @return the position
     */
    public Position getEndPosition();

    /**
     * This method allows an application to mark a place in
     * a sequence of character content. This mark can then be
     * used to tracks change as insertions and removals are made
     * in the content. The policy is that insertions always
     * occur prior to the current position (the most common case)
     * unless the insertion location is zero, in which case the
     * insertion is forced to a position that follows the
     * original position.
     *
     * <p>
     * 此方法允许应用程序标记字符内容序列中的位置。然后,可以使用此标记跟踪更改,因为在内容中插入和删除。
     * 策略是插入总是发生在当前位置(最常见的情况)之前,除非插入位置是零,在这种情况下插入被强制到跟随原始位置的位置。
     * 
     * 
     * @param offs  the offset from the start of the document &gt;= 0
     * @return the position
     * @exception BadLocationException  if the given position does not
     *   represent a valid location in the associated document
     */
    public Position createPosition(int offs) throws BadLocationException;

    /**
     * Returns all of the root elements that are defined.
     * <p>
     * Typically there will be only one document structure, but the interface
     * supports building an arbitrary number of structural projections over the
     * text data. The document can have multiple root elements to support
     * multiple document structures.  Some examples might be:
     * </p>
     * <ul>
     * <li>Text direction.
     * <li>Lexical token streams.
     * <li>Parse trees.
     * <li>Conversions to formats other than the native format.
     * <li>Modification specifications.
     * <li>Annotations.
     * </ul>
     *
     * <p>
     *  返回定义的所有根元素。
     * <p>
     *  通常只有一个文档结构,但是界面支持在文本数据上构建任意数量的结构投影。文档可以具有多个根元素以支持多个文档结构。一些例子可能是：
     * </p>
     * <ul>
     *  <li>文字方向。 <li>词汇令牌流。 <li>解析树。 <li>转换为原生格式以外的格式。 <li>修改规格。 <li>注释。
     * </ul>
     * 
     * 
     * @return the root element
     */
    public Element[] getRootElements();

    /**
     * Returns the root element that views should be based upon,
     * unless some other mechanism for assigning views to element
     * structures is provided.
     *
     * <p>
     *  返回视图应该基于的根元素,除非提供了将视图分配给元素结构的一些其他机制。
     * 
     * 
     * @return the root element
     */
    public Element getDefaultRootElement();

    /**
     * Allows the model to be safely rendered in the presence
     * of concurrency, if the model supports being updated asynchronously.
     * The given runnable will be executed in a way that allows it
     * to safely read the model with no changes while the runnable
     * is being executed.  The runnable itself may <em>not</em>
     * make any mutations.
     *
     * <p>
     *  允许在存在并发性的情况下安全地呈现模型(如果模型支持异步更新)。给定的runnable将以允许其在执行可运行的时候安全地读取模型而没有改变的方式执行。可运行的本身可能不会</em>进行任何突变。
     * 
     * 
     * @param r a <code>Runnable</code> used to render the model
     */
    public void render(Runnable r);

    /**
     * The property name for the description of the stream
     * used to initialize the document.  This should be used
     * if the document was initialized from a stream and
     * anything is known about the stream.
     * <p>
     * 用于初始化文档的流的描述的属性名称。如果文档是从流初始化的,并且已知有关流的任何内容,则应使用此选项。
     * 
     */
    public static final String StreamDescriptionProperty = "stream";

    /**
     * The property name for the title of the document, if
     * there is one.
     * <p>
     *  文档标题的属性名称(如果有的话)。
     */
    public static final String TitleProperty = "title";


}
