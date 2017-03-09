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

package javax.swing.text;

import java.util.Stack;
import java.util.Enumeration;

/**
 * <p>
 * ElementIterator, as the name suggests, iterates over the Element
 * tree.  The constructor can be invoked with either Document or an Element
 * as an argument.  If the constructor is invoked with a Document as an
 * argument then the root of the iteration is the return value of
 * document.getDefaultRootElement().
 *
 * The iteration happens in a depth-first manner.  In terms of how
 * boundary conditions are handled:
 * a) if next() is called before first() or current(), the
 *    root will be returned.
 * b) next() returns null to indicate the end of the list.
 * c) previous() returns null when the current element is the root
 *    or next() has returned null.
 *
 * The ElementIterator does no locking of the Element tree. This means
 * that it does not track any changes.  It is the responsibility of the
 * user of this class, to ensure that no changes happen during element
 * iteration.
 *
 * Simple usage example:
 *
 *    public void iterate() {
 *        ElementIterator it = new ElementIterator(root);
 *        Element elem;
 *        while (true) {
 *           if ((elem = next()) != null) {
 *               // process element
 *               System.out.println("elem: " + elem.getName());
 *           } else {
 *               break;
 *           }
 *        }
 *    }
 *
 * <p>
 * <p>
 *  ElementIterator,顾名思义,遍历元素树。可以使用Document或Element作为参数来调用构造函数。
 * 如果使用Document作为参数调用构造函数,那么迭代的根是document.getDefaultRootElement()的返回值。
 * 
 *  迭代以深度优先的方式发生。关于如何处理边界条件：a)如果next()在first()或current()之前被调用,将返回根。 b)next()返回null以指示列表的结尾。
 *  c)当当前元素是根或next()返回null时,previous()返回null。
 * 
 *  ElementIterator没有锁定元素树。这意味着它不跟踪任何更改。这是类的用户的责任,以确保在元素迭代期间不发生更改。
 * 
 *  简单用法示例：
 * 
 *  public void iterate(){ElementIterator it = new ElementIterator(root);元素元素while(true){if((elem = next())！= null){// process element System.out.println("elem："+ elem.getName()); }
 *  else {break; }}}。
 * 
 * 
 * @author Sunita Mani
 *
 */

public class ElementIterator implements Cloneable {


    private Element root;
    private Stack<StackItem> elementStack = null;

    /**
     * The StackItem class stores the element
     * as well as a child index.  If the
     * index is -1, then the element represented
     * on the stack is the element itself.
     * Otherwise, the index functions as as index
     * into the vector of children of the element.
     * In this case, the item on the stack
     * represents the "index"th child of the element
     *
     * <p>
     * StackItem类存储元素以及子索引。如果索引是-1,则在堆栈上表示的元素是元素本身。否则,索引用作元素的子元素的向量中的索引。在这种情况下,堆栈上的项表示元素的"索引"th子元素
     * 
     */
    private class StackItem implements Cloneable {
        Element item;
        int childIndex;

        private StackItem(Element elem) {
            /**
             * -1 index implies a self reference,
             * as opposed to an index into its
             * list of children.
             * <p>
             *  -1索引表示自引用,而不是其子列表中的索引。
             * 
             */
            this.item = elem;
            this.childIndex = -1;
        }

        private void incrementIndex() {
            childIndex++;
        }

        private Element getElement() {
            return item;
        }

        private int getIndex() {
            return childIndex;
        }

        protected Object clone() throws java.lang.CloneNotSupportedException {
            return super.clone();
        }
    }

    /**
     * Creates a new ElementIterator. The
     * root element is taken to get the
     * default root element of the document.
     *
     * <p>
     *  创建一个新的ElementIterator。根元素用于获取文档的默认根元素。
     * 
     * 
     * @param document a Document.
     */
    public ElementIterator(Document document) {
        root = document.getDefaultRootElement();
    }


    /**
     * Creates a new ElementIterator.
     *
     * <p>
     *  创建一个新的ElementIterator。
     * 
     * 
     * @param root the root Element.
     */
    public ElementIterator(Element root) {
        this.root = root;
    }


    /**
     * Clones the ElementIterator.
     *
     * <p>
     *  克隆ElementIterator。
     * 
     * 
     * @return a cloned ElementIterator Object.
     */
    public synchronized Object clone() {

        try {
            ElementIterator it = new ElementIterator(root);
            if (elementStack != null) {
                it.elementStack = new Stack<StackItem>();
                for (int i = 0; i < elementStack.size(); i++) {
                    StackItem item = elementStack.elementAt(i);
                    StackItem clonee = (StackItem)item.clone();
                    it.elementStack.push(clonee);
                }
            }
            return it;
        } catch (CloneNotSupportedException e) {
            throw new InternalError(e);
        }
    }


    /**
     * Fetches the first element.
     *
     * <p>
     *  获取第一个元素。
     * 
     * 
     * @return an Element.
     */
    public Element first() {
        // just in case...
        if (root == null) {
            return null;
        }

        elementStack = new Stack<StackItem>();
        if (root.getElementCount() != 0) {
            elementStack.push(new StackItem(root));
        }
        return root;
    }

    /**
     * Fetches the current depth of element tree.
     *
     * <p>
     *  获取元素树的当前深度。
     * 
     * 
     * @return the depth.
     */
    public int depth() {
        if (elementStack == null) {
            return 0;
        }
        return elementStack.size();
    }


    /**
     * Fetches the current Element.
     *
     * <p>
     *  获取当前元素。
     * 
     * 
     * @return element on top of the stack or
     *          <code>null</code> if the root element is <code>null</code>
     */
    public Element current() {

        if (elementStack == null) {
            return first();
        }

        /*
          get a handle to the element on top of the stack.
        /* <p>
        /*  获取堆栈顶部元素的句柄。
        /* 
        */
        if (! elementStack.empty()) {
            StackItem item = elementStack.peek();
            Element elem = item.getElement();
            int index = item.getIndex();
            // self reference
            if (index == -1) {
                return elem;
            }
            // return the child at location "index".
            return elem.getElement(index);
        }
        return null;
    }


    /**
     * Fetches the next Element. The strategy
     * used to locate the next element is
     * a depth-first search.
     *
     * <p>
     *  获取下一个元素。用于定位下一个元素的策略是深度优先搜索。
     * 
     * 
     * @return the next element or <code>null</code>
     *          at the end of the list.
     */
    public Element next() {

        /* if current() has not been invoked
           and next is invoked, the very first
        /* <p>
        /*  接下来被调用,第一个
        /* 
        /* 
           element will be returned. */
        if (elementStack == null) {
            return first();
        }

        // no more elements
        if (elementStack.isEmpty()) {
            return null;
        }

        // get a handle to the element on top of the stack

        StackItem item = elementStack.peek();
        Element elem = item.getElement();
        int index = item.getIndex();

        if (index+1 < elem.getElementCount()) {
            Element child = elem.getElement(index+1);
            if (child.isLeaf()) {
                /* In this case we merely want to increment
                   the child index of the item on top of the
                /* <p>
                /*  项的子索引在顶部
                /* 
                /* 
                   stack.*/
                item.incrementIndex();
            } else {
                /* In this case we need to push the child(branch)
                   on the stack so that we can iterate over its
                /* <p>
                /*  在栈上,这样我们可以迭代它的
                /* 
                /* 
                   children. */
                elementStack.push(new StackItem(child));
            }
            return child;
        } else {
            /* No more children for the item on top of the
            /* <p>
            /* 
               stack therefore pop the stack. */
            elementStack.pop();
            if (!elementStack.isEmpty()) {
                /* Increment the child index for the item that
                /* <p>
                /* 
                   is now on top of the stack. */
                StackItem top = elementStack.peek();
                top.incrementIndex();
                /* We now want to return its next child, therefore
                /* <p>
                /* 
                   call next() recursively. */
                return next();
            }
        }
        return null;
    }


    /**
     * Fetches the previous Element. If however the current
     * element is the last element, or the current element
     * is null, then null is returned.
     *
     * <p>
     *  获取上一个元素。然而,如果当前元素是最后一个元素,或当前元素为null,则返回null。
     * 
     * 
     * @return previous <code>Element</code> if available
     *
     */
    public Element previous() {

        int stackSize;
        if (elementStack == null || (stackSize = elementStack.size()) == 0) {
            return null;
        }

        // get a handle to the element on top of the stack
        //
        StackItem item = elementStack.peek();
        Element elem = item.getElement();
        int index = item.getIndex();

        if (index > 0) {
            /* return child at previous index. */
            return getDeepestLeaf(elem.getElement(--index));
        } else if (index == 0) {
            /* this implies that current is the element's
               first child, therefore previous is the
            /* <p>
            /*  第一个孩子,因此以前是的
            /* 
            /* 
               element itself. */
            return elem;
        } else if (index == -1) {
            if (stackSize == 1) {
                // current is the root, nothing before it.
                return null;
            }
            /* We need to return either the item
               below the top item or one of the
            /* <p>
            /*  低于顶部项目或其中一个
            /* 
            /* 
               former's children. */
            StackItem top = elementStack.pop();
            item = elementStack.peek();

            // restore the top item.
            elementStack.push(top);
            elem = item.getElement();
            index = item.getIndex();
            return ((index == -1) ? elem : getDeepestLeaf(elem.getElement
                                                          (index)));
        }
        // should never get here.
        return null;
    }

    /**
     * Returns the last child of <code>parent</code> that is a leaf. If the
     * last child is a not a leaf, this method is called with the last child.
     * <p>
     *  返回作为叶子的<code> parent </code>的最后一个子项。如果最后一个子节点不是一个叶子,则该方法将与最后一个子节点一起调用。
     * 
     */
    private Element getDeepestLeaf(Element parent) {
        if (parent.isLeaf()) {
            return parent;
        }
        int childCount = parent.getElementCount();
        if (childCount == 0) {
            return parent;
        }
        return getDeepestLeaf(parent.getElement(childCount - 1));
    }

    /*
      Iterates through the element tree and prints
      out each element and its attributes.
    /* <p>
    /*  迭代通过元素树并打印出每个元素及其属性。
    */
    private void dumpTree() {

        Element elem;
        while (true) {
            if ((elem = next()) != null) {
                System.out.println("elem: " + elem.getName());
                AttributeSet attr = elem.getAttributes();
                String s = "";
                Enumeration names = attr.getAttributeNames();
                while (names.hasMoreElements()) {
                    Object key = names.nextElement();
                    Object value = attr.getAttribute(key);
                    if (value instanceof AttributeSet) {
                        // don't go recursive
                        s = s + key + "=**AttributeSet** ";
                    } else {
                        s = s + key + "=" + value + " ";
                    }
                }
                System.out.println("attributes: " + s);
            } else {
                break;
            }
        }
    }
}
