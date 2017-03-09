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

import java.awt.event.ActionEvent;
import java.awt.KeyboardFocusManager;
import java.awt.Component;
import java.util.Hashtable;
import java.util.Enumeration;
import javax.swing.Action;
import javax.swing.AbstractAction;
import javax.swing.KeyStroke;

/**
 * An Action implementation useful for key bindings that are
 * shared across a number of different text components.  Because
 * the action is shared, it must have a way of getting it's
 * target to act upon.  This class provides support to try and
 * find a text component to operate on.  The preferred way of
 * getting the component to act upon is through the ActionEvent
 * that is received.  If the Object returned by getSource can
 * be narrowed to a text component, it will be used.  If the
 * action event is null or can't be narrowed, the last focused
 * text component is tried.  This is determined by being
 * used in conjunction with a JTextController which
 * arranges to share that information with a TextAction.
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
 * <p>
 *  一个Action实现,对于跨多个不同文本组件共享的键绑定很有用。因为行动是共享的,它必须有一种方式获得它的目标行动。这个类提供了支持来尝试和找到一个文本组件来操作。
 * 使组件采取行动的首选方式是通过接收到的ActionEvent。如果getSource返回的对象可以缩小到一个文本组件,它将被使用。如果操作事件为null或无法缩小,则尝试最后一个聚焦的文本组件。
 * 这是通过与JTextController结合使用来确定的,JTextController安排与TextAction共享该信息。
 * <p>
 *  <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
 *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
 * 
 * 
 * @author  Timothy Prinzing
 */
public abstract class TextAction extends AbstractAction {

    /**
     * Creates a new JTextAction object.
     *
     * <p>
     *  创建一个新的JTextAction对象。
     * 
     * 
     * @param name the name of the action
     */
    public TextAction(String name) {
        super(name);
    }

    /**
     * Determines the component to use for the action.
     * This if fetched from the source of the ActionEvent
     * if it's not null and can be narrowed.  Otherwise,
     * the last focused component is used.
     *
     * <p>
     *  确定要用于操作的组件。如果从ActionEvent的源获取,如果它不为null并且可以缩小。否则,使用最后聚焦的组件。
     * 
     * 
     * @param e the ActionEvent
     * @return the component
     */
    protected final JTextComponent getTextComponent(ActionEvent e) {
        if (e != null) {
            Object o = e.getSource();
            if (o instanceof JTextComponent) {
                return (JTextComponent) o;
            }
        }
        return getFocusedComponent();
    }

    /**
     * Takes one list of
     * commands and augments it with another list
     * of commands.  The second list takes precedence
     * over the first list; that is, when both lists
     * contain a command with the same name, the command
     * from the second list is used.
     *
     * <p>
     * 获取一个命令列表,并用另一个命令列表来增加它。第二个列表优先于第一个列表;也就是说,当两个列表都包含具有相同名称的命令时,使用来自第二个列表的命令。
     * 
     * 
     * @param list1 the first list, may be empty but not
     *              <code>null</code>
     * @param list2 the second list, may be empty but not
     *              <code>null</code>
     * @return the augmented list
     */
    public static final Action[] augmentList(Action[] list1, Action[] list2) {
        Hashtable<String, Action> h = new Hashtable<String, Action>();
        for (Action a : list1) {
            String value = (String)a.getValue(Action.NAME);
            h.put((value!=null ? value:""), a);
        }
        for (Action a : list2) {
            String value = (String)a.getValue(Action.NAME);
            h.put((value!=null ? value:""), a);
        }
        Action[] actions = new Action[h.size()];
        int index = 0;
        for (Enumeration e = h.elements() ; e.hasMoreElements() ;) {
            actions[index++] = (Action) e.nextElement();
        }
        return actions;
    }

    /**
     * Fetches the text component that currently has focus.
     * This allows actions to be shared across text components
     * which is useful for key-bindings where a large set of
     * actions are defined, but generally used the same way
     * across many different components.
     *
     * <p>
     *  获取当前具有焦点的文本组件。这允许跨文本组件共享操作,这对于定义大量操作的键绑定是有用的,但通常在许多不同组件中使用相同的方式。
     * 
     * @return the component
     */
    protected final JTextComponent getFocusedComponent() {
        return JTextComponent.getFocusedComponent();
    }
}
