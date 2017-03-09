/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 1998, Oracle and/or its affiliates. All rights reserved.
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
package javax.swing.event;

import java.util.EventListener;

/**
 * Interface for an observer to register to receive notifications
 * of changes to a text document.
 * <p>
 * The default implementation of
 * the Document interface (AbstractDocument) supports asynchronous
 * mutations.  If this feature is used (i.e. mutations are made
 * from a thread other than the Swing event thread), the listeners
 * will be notified via the mutating thread.  <em>This means that
 * if asynchronous updates are made, the implementation of this
 * interface must be threadsafe</em>!
 * <p>
 * The DocumentEvent notification is based upon the JavaBeans
 * event model.  There is no guarantee about the order of delivery
 * to listeners, and all listeners must be notified prior to making
 * further mutations to the Document.  <em>This means implementations
 * of the DocumentListener may not mutate the source of the event
 * (i.e. the associated Document)</em>.
 *
 * <p>
 *  观察者注册接收文本文档更改的通知的界面。
 * <p>
 *  Document接口(AbstractDocument)的默认实现支持异步突变。如果使用此功能(即,从Swing事件线程以外的线程进行突变),则将通过变异线程通知侦听器。
 *  <em>这意味着如果进行异步更新,此接口的实现必须是线程安全的</em>！。
 * <p>
 *  DocumentEvent通知基于JavaBeans事件模型。没有关于向听众递送的顺序的保证,并且在对文档进行进一步突变之前必须通知所有听众。
 *  <em>这意味着DocumentListener的实现不能改变事件的来源(即相关联的文档)</em>。
 * 
 * 
 * @author  Timothy Prinzing
 * @see javax.swing.text.Document
 * @see javax.swing.text.StyledDocument
 * @see DocumentEvent
 */
public interface DocumentListener extends EventListener {

    /**
     * Gives notification that there was an insert into the document.  The
     * range given by the DocumentEvent bounds the freshly inserted region.
     *
     * <p>
     *  提供通知,说明文档中有插入。 DocumentEvent给出的范围限制新插入的区域。
     * 
     * 
     * @param e the document event
     */
    public void insertUpdate(DocumentEvent e);

    /**
     * Gives notification that a portion of the document has been
     * removed.  The range is given in terms of what the view last
     * saw (that is, before updating sticky positions).
     *
     * <p>
     *  提供文档的一部分已删除的通知。该范围是根据视图最后看到的(即,在更新粘性位置之前)给出的。
     * 
     * 
     * @param e the document event
     */
    public void removeUpdate(DocumentEvent e);

    /**
     * Gives notification that an attribute or set of attributes changed.
     *
     * <p>
     *  提供属性或属性集更改的通知。
     * 
     * @param e the document event
     */
    public void changedUpdate(DocumentEvent e);
}
