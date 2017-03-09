/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1995, 2013, Oracle and/or its affiliates. All rights reserved.
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

import java.util.Hashtable;
import java.util.Vector;
import java.util.Enumeration;

import java.io.Serializable;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamField;
import java.io.IOException;

/**
 * A <code>CardLayout</code> object is a layout manager for a
 * container. It treats each component in the container as a card.
 * Only one card is visible at a time, and the container acts as
 * a stack of cards. The first component added to a
 * <code>CardLayout</code> object is the visible component when the
 * container is first displayed.
 * <p>
 * The ordering of cards is determined by the container's own internal
 * ordering of its component objects. <code>CardLayout</code>
 * defines a set of methods that allow an application to flip
 * through these cards sequentially, or to show a specified card.
 * The {@link CardLayout#addLayoutComponent}
 * method can be used to associate a string identifier with a given card
 * for fast random access.
 *
 * <p>
 *  <code> CardLayout </code>对象是容器的布局管理器。它将容器中的每个组件视为卡片。一次只能看到一个卡,并且容器用作一叠卡。
 * 添加到<code> CardLayout </code>对象的第一个组件是容器首次显示时的可见组件。
 * <p>
 *  卡的排序由容器自己的组件对象的内部排序确定。 <code> CardLayout </code>定义了一组方法,允许应用程序顺序翻转这些卡,或显示指定的卡。
 *  {@link CardLayout#addLayoutComponent}方法可用于将字符串标识符与给定卡关联以用于快速随机访问。
 * 
 * 
 * @author      Arthur van Hoff
 * @see         java.awt.Container
 * @since       JDK1.0
 */

public class CardLayout implements LayoutManager2,
                                   Serializable {

    private static final long serialVersionUID = -4328196481005934313L;

    /*
     * This creates a Vector to store associated
     * pairs of components and their names.
     * <p>
     *  这将创建一个向量来存储相关的组件对及其名称。
     * 
     * 
     * @see java.util.Vector
     */
    Vector<Card> vector = new Vector<>();

    /*
     * A pair of Component and String that represents its name.
     * <p>
     *  代表其名称的一对Component和String。
     * 
     */
    class Card implements Serializable {
        static final long serialVersionUID = 6640330810709497518L;
        public String name;
        public Component comp;
        public Card(String cardName, Component cardComponent) {
            name = cardName;
            comp = cardComponent;
        }
    }

    /*
     * Index of Component currently displayed by CardLayout.
     * <p>
     *  CardLayout当前显示的组件索引。
     * 
     */
    int currentCard = 0;


    /*
    * A cards horizontal Layout gap (inset). It specifies
    * the space between the left and right edges of a
    * container and the current component.
    * This should be a non negative Integer.
    * <p>
    *  A水平布局缺口(插图)。它指定容器的左边缘和右边缘之间的空间和当前组件。这应该是非负整数。
    * 
    * 
    * @see getHgap()
    * @see setHgap()
    */
    int hgap;

    /*
    * A cards vertical Layout gap (inset). It specifies
    * the space between the top and bottom edges of a
    * container and the current component.
    * This should be a non negative Integer.
    * <p>
    *  A卡垂直布局空白(插图)。它指定容器的顶部和底部边缘之间的空间和当前组件。这应该是非负整数。
    * 
    * 
    * @see getVgap()
    * @see setVgap()
    */
    int vgap;

    /**
    /* <p>
    /* 
     * @serialField tab         Hashtable
     *      deprectated, for forward compatibility only
     * @serialField hgap        int
     * @serialField vgap        int
     * @serialField vector      Vector
     * @serialField currentCard int
     */
    private static final ObjectStreamField[] serialPersistentFields = {
        new ObjectStreamField("tab", Hashtable.class),
        new ObjectStreamField("hgap", Integer.TYPE),
        new ObjectStreamField("vgap", Integer.TYPE),
        new ObjectStreamField("vector", Vector.class),
        new ObjectStreamField("currentCard", Integer.TYPE)
    };

    /**
     * Creates a new card layout with gaps of size zero.
     * <p>
     *  创建一个新的卡片布局,其大小为零。
     * 
     */
    public CardLayout() {
        this(0, 0);
    }

    /**
     * Creates a new card layout with the specified horizontal and
     * vertical gaps. The horizontal gaps are placed at the left and
     * right edges. The vertical gaps are placed at the top and bottom
     * edges.
     * <p>
     * 创建具有指定水平和垂直间隙的新卡布局。水平间隙位于左边缘和右边缘。垂直间隙位于顶部和底部边缘。
     * 
     * 
     * @param     hgap   the horizontal gap.
     * @param     vgap   the vertical gap.
     */
    public CardLayout(int hgap, int vgap) {
        this.hgap = hgap;
        this.vgap = vgap;
    }

    /**
     * Gets the horizontal gap between components.
     * <p>
     *  获取组件之间的水平间隙。
     * 
     * 
     * @return    the horizontal gap between components.
     * @see       java.awt.CardLayout#setHgap(int)
     * @see       java.awt.CardLayout#getVgap()
     * @since     JDK1.1
     */
    public int getHgap() {
        return hgap;
    }

    /**
     * Sets the horizontal gap between components.
     * <p>
     *  设置组件之间的水平间距。
     * 
     * 
     * @param hgap the horizontal gap between components.
     * @see       java.awt.CardLayout#getHgap()
     * @see       java.awt.CardLayout#setVgap(int)
     * @since     JDK1.1
     */
    public void setHgap(int hgap) {
        this.hgap = hgap;
    }

    /**
     * Gets the vertical gap between components.
     * <p>
     *  获取组件之间的垂直间隙。
     * 
     * 
     * @return the vertical gap between components.
     * @see       java.awt.CardLayout#setVgap(int)
     * @see       java.awt.CardLayout#getHgap()
     */
    public int getVgap() {
        return vgap;
    }

    /**
     * Sets the vertical gap between components.
     * <p>
     *  设置组件之间的垂直间距。
     * 
     * 
     * @param     vgap the vertical gap between components.
     * @see       java.awt.CardLayout#getVgap()
     * @see       java.awt.CardLayout#setHgap(int)
     * @since     JDK1.1
     */
    public void setVgap(int vgap) {
        this.vgap = vgap;
    }

    /**
     * Adds the specified component to this card layout's internal
     * table of names. The object specified by <code>constraints</code>
     * must be a string. The card layout stores this string as a key-value
     * pair that can be used for random access to a particular card.
     * By calling the <code>show</code> method, an application can
     * display the component with the specified name.
     * <p>
     *  将指定的组件添加到此卡布局的内部名称表。 <code> constraints </code>指定的对象必须是字符串。卡布局将此字符串存储为键值对,可用于对特定卡的随机访问。
     * 通过调用<code> show </code>方法,应用程序可以显示具有指定名称的组件。
     * 
     * 
     * @param     comp          the component to be added.
     * @param     constraints   a tag that identifies a particular
     *                                        card in the layout.
     * @see       java.awt.CardLayout#show(java.awt.Container, java.lang.String)
     * @exception  IllegalArgumentException  if the constraint is not a string.
     */
    public void addLayoutComponent(Component comp, Object constraints) {
      synchronized (comp.getTreeLock()) {
          if (constraints == null){
              constraints = "";
          }
        if (constraints instanceof String) {
            addLayoutComponent((String)constraints, comp);
        } else {
            throw new IllegalArgumentException("cannot add to layout: constraint must be a string");
        }
      }
    }

    /**
    /* <p>
    /* 
     * @deprecated   replaced by
     *      <code>addLayoutComponent(Component, Object)</code>.
     */
    @Deprecated
    public void addLayoutComponent(String name, Component comp) {
        synchronized (comp.getTreeLock()) {
            if (!vector.isEmpty()) {
                comp.setVisible(false);
            }
            for (int i=0; i < vector.size(); i++) {
                if (((Card)vector.get(i)).name.equals(name)) {
                    ((Card)vector.get(i)).comp = comp;
                    return;
                }
            }
            vector.add(new Card(name, comp));
        }
    }

    /**
     * Removes the specified component from the layout.
     * If the card was visible on top, the next card underneath it is shown.
     * <p>
     *  从布局中删除指定的组件。如果卡在顶部可见,则显示下面的卡。
     * 
     * 
     * @param   comp   the component to be removed.
     * @see     java.awt.Container#remove(java.awt.Component)
     * @see     java.awt.Container#removeAll()
     */
    public void removeLayoutComponent(Component comp) {
        synchronized (comp.getTreeLock()) {
            for (int i = 0; i < vector.size(); i++) {
                if (((Card)vector.get(i)).comp == comp) {
                    // if we remove current component we should show next one
                    if (comp.isVisible() && (comp.getParent() != null)) {
                        next(comp.getParent());
                    }

                    vector.remove(i);

                    // correct currentCard if this is necessary
                    if (currentCard > i) {
                        currentCard--;
                    }
                    break;
                }
            }
        }
    }

    /**
     * Determines the preferred size of the container argument using
     * this card layout.
     * <p>
     *  使用此卡布局确定容器参数的首选大小。
     * 
     * 
     * @param   parent the parent container in which to do the layout
     * @return  the preferred dimensions to lay out the subcomponents
     *                of the specified container
     * @see     java.awt.Container#getPreferredSize
     * @see     java.awt.CardLayout#minimumLayoutSize
     */
    public Dimension preferredLayoutSize(Container parent) {
        synchronized (parent.getTreeLock()) {
            Insets insets = parent.getInsets();
            int ncomponents = parent.getComponentCount();
            int w = 0;
            int h = 0;

            for (int i = 0 ; i < ncomponents ; i++) {
                Component comp = parent.getComponent(i);
                Dimension d = comp.getPreferredSize();
                if (d.width > w) {
                    w = d.width;
                }
                if (d.height > h) {
                    h = d.height;
                }
            }
            return new Dimension(insets.left + insets.right + w + hgap*2,
                                 insets.top + insets.bottom + h + vgap*2);
        }
    }

    /**
     * Calculates the minimum size for the specified panel.
     * <p>
     *  计算指定面板的最小大小。
     * 
     * 
     * @param     parent the parent container in which to do the layout
     * @return    the minimum dimensions required to lay out the
     *                subcomponents of the specified container
     * @see       java.awt.Container#doLayout
     * @see       java.awt.CardLayout#preferredLayoutSize
     */
    public Dimension minimumLayoutSize(Container parent) {
        synchronized (parent.getTreeLock()) {
            Insets insets = parent.getInsets();
            int ncomponents = parent.getComponentCount();
            int w = 0;
            int h = 0;

            for (int i = 0 ; i < ncomponents ; i++) {
                Component comp = parent.getComponent(i);
                Dimension d = comp.getMinimumSize();
                if (d.width > w) {
                    w = d.width;
                }
                if (d.height > h) {
                    h = d.height;
                }
            }
            return new Dimension(insets.left + insets.right + w + hgap*2,
                                 insets.top + insets.bottom + h + vgap*2);
        }
    }

    /**
     * Returns the maximum dimensions for this layout given the components
     * in the specified target container.
     * <p>
     *  返回给定指定目标容器中的组件的此布局的最大尺寸。
     * 
     * 
     * @param target the component which needs to be laid out
     * @see Container
     * @see #minimumLayoutSize
     * @see #preferredLayoutSize
     */
    public Dimension maximumLayoutSize(Container target) {
        return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
    }

    /**
     * Returns the alignment along the x axis.  This specifies how
     * the component would like to be aligned relative to other
     * components.  The value should be a number between 0 and 1
     * where 0 represents alignment along the origin, 1 is aligned
     * the furthest away from the origin, 0.5 is centered, etc.
     * <p>
     *  返回沿x轴的对齐。这指定了组件将如何相对于其他组件对齐。该值应为0和1之间的数字,其中0表示沿原点的对齐,1对齐距离原点最远,0.5为中心等。
     * 
     */
    public float getLayoutAlignmentX(Container parent) {
        return 0.5f;
    }

    /**
     * Returns the alignment along the y axis.  This specifies how
     * the component would like to be aligned relative to other
     * components.  The value should be a number between 0 and 1
     * where 0 represents alignment along the origin, 1 is aligned
     * the furthest away from the origin, 0.5 is centered, etc.
     * <p>
     * 返回沿y轴的对齐。这指定了组件将如何相对于其他组件对齐。该值应为0和1之间的数字,其中0表示沿原点的对齐,1对齐距离原点最远,0.5为中心等。
     * 
     */
    public float getLayoutAlignmentY(Container parent) {
        return 0.5f;
    }

    /**
     * Invalidates the layout, indicating that if the layout manager
     * has cached information it should be discarded.
     * <p>
     *  使布局无效,指示如果布局管理器具有缓存的信息,它应该被丢弃。
     * 
     */
    public void invalidateLayout(Container target) {
    }

    /**
     * Lays out the specified container using this card layout.
     * <p>
     * Each component in the <code>parent</code> container is reshaped
     * to be the size of the container, minus space for surrounding
     * insets, horizontal gaps, and vertical gaps.
     *
     * <p>
     *  使用此卡布局放出指定的容器。
     * <p>
     *  <code> parent </code>容器中的每个组件被重新塑造为容器的大小,减去周围插入的空间,水平间隙和垂直间隙。
     * 
     * 
     * @param     parent the parent container in which to do the layout
     * @see       java.awt.Container#doLayout
     */
    public void layoutContainer(Container parent) {
        synchronized (parent.getTreeLock()) {
            Insets insets = parent.getInsets();
            int ncomponents = parent.getComponentCount();
            Component comp = null;
            boolean currentFound = false;

            for (int i = 0 ; i < ncomponents ; i++) {
                comp = parent.getComponent(i);
                comp.setBounds(hgap + insets.left, vgap + insets.top,
                               parent.width - (hgap*2 + insets.left + insets.right),
                               parent.height - (vgap*2 + insets.top + insets.bottom));
                if (comp.isVisible()) {
                    currentFound = true;
                }
            }

            if (!currentFound && ncomponents > 0) {
                parent.getComponent(0).setVisible(true);
            }
        }
    }

    /**
     * Make sure that the Container really has a CardLayout installed.
     * Otherwise havoc can ensue!
     * <p>
     *  确保Container实际上已经安装了CardLayout。否则可能会造成严重破坏！
     * 
     */
    void checkLayout(Container parent) {
        if (parent.getLayout() != this) {
            throw new IllegalArgumentException("wrong parent for CardLayout");
        }
    }

    /**
     * Flips to the first card of the container.
     * <p>
     *  翻转到容器的第一张卡。
     * 
     * 
     * @param     parent   the parent container in which to do the layout
     * @see       java.awt.CardLayout#last
     */
    public void first(Container parent) {
        synchronized (parent.getTreeLock()) {
            checkLayout(parent);
            int ncomponents = parent.getComponentCount();
            for (int i = 0 ; i < ncomponents ; i++) {
                Component comp = parent.getComponent(i);
                if (comp.isVisible()) {
                    comp.setVisible(false);
                    break;
                }
            }
            if (ncomponents > 0) {
                currentCard = 0;
                parent.getComponent(0).setVisible(true);
                parent.validate();
            }
        }
    }

    /**
     * Flips to the next card of the specified container. If the
     * currently visible card is the last one, this method flips to the
     * first card in the layout.
     * <p>
     *  翻转到指定容器的下一张卡片。如果当前可见的卡是最后一个卡,则此方法将翻到布局中的第一个卡。
     * 
     * 
     * @param     parent   the parent container in which to do the layout
     * @see       java.awt.CardLayout#previous
     */
    public void next(Container parent) {
        synchronized (parent.getTreeLock()) {
            checkLayout(parent);
            int ncomponents = parent.getComponentCount();
            for (int i = 0 ; i < ncomponents ; i++) {
                Component comp = parent.getComponent(i);
                if (comp.isVisible()) {
                    comp.setVisible(false);
                    currentCard = (i + 1) % ncomponents;
                    comp = parent.getComponent(currentCard);
                    comp.setVisible(true);
                    parent.validate();
                    return;
                }
            }
            showDefaultComponent(parent);
        }
    }

    /**
     * Flips to the previous card of the specified container. If the
     * currently visible card is the first one, this method flips to the
     * last card in the layout.
     * <p>
     *  翻转到指定容器的上一张卡片。如果当前可见的卡是第一个,则此方法将翻到布局中的最后一个卡。
     * 
     * 
     * @param     parent   the parent container in which to do the layout
     * @see       java.awt.CardLayout#next
     */
    public void previous(Container parent) {
        synchronized (parent.getTreeLock()) {
            checkLayout(parent);
            int ncomponents = parent.getComponentCount();
            for (int i = 0 ; i < ncomponents ; i++) {
                Component comp = parent.getComponent(i);
                if (comp.isVisible()) {
                    comp.setVisible(false);
                    currentCard = ((i > 0) ? i-1 : ncomponents-1);
                    comp = parent.getComponent(currentCard);
                    comp.setVisible(true);
                    parent.validate();
                    return;
                }
            }
            showDefaultComponent(parent);
        }
    }

    void showDefaultComponent(Container parent) {
        if (parent.getComponentCount() > 0) {
            currentCard = 0;
            parent.getComponent(0).setVisible(true);
            parent.validate();
        }
    }

    /**
     * Flips to the last card of the container.
     * <p>
     *  翻转到容器的最后一张卡片。
     * 
     * 
     * @param     parent   the parent container in which to do the layout
     * @see       java.awt.CardLayout#first
     */
    public void last(Container parent) {
        synchronized (parent.getTreeLock()) {
            checkLayout(parent);
            int ncomponents = parent.getComponentCount();
            for (int i = 0 ; i < ncomponents ; i++) {
                Component comp = parent.getComponent(i);
                if (comp.isVisible()) {
                    comp.setVisible(false);
                    break;
                }
            }
            if (ncomponents > 0) {
                currentCard = ncomponents - 1;
                parent.getComponent(currentCard).setVisible(true);
                parent.validate();
            }
        }
    }

    /**
     * Flips to the component that was added to this layout with the
     * specified <code>name</code>, using <code>addLayoutComponent</code>.
     * If no such component exists, then nothing happens.
     * <p>
     *  使用<code> addLayoutComponent </code>,翻转到使用指定的<code> name </code>添加到此布局的组件。如果没有这样的组件存在,则什么也不发生。
     * 
     * 
     * @param     parent   the parent container in which to do the layout
     * @param     name     the component name
     * @see       java.awt.CardLayout#addLayoutComponent(java.awt.Component, java.lang.Object)
     */
    public void show(Container parent, String name) {
        synchronized (parent.getTreeLock()) {
            checkLayout(parent);
            Component next = null;
            int ncomponents = vector.size();
            for (int i = 0; i < ncomponents; i++) {
                Card card = (Card)vector.get(i);
                if (card.name.equals(name)) {
                    next = card.comp;
                    currentCard = i;
                    break;
                }
            }
            if ((next != null) && !next.isVisible()) {
                ncomponents = parent.getComponentCount();
                for (int i = 0; i < ncomponents; i++) {
                    Component comp = parent.getComponent(i);
                    if (comp.isVisible()) {
                        comp.setVisible(false);
                        break;
                    }
                }
                next.setVisible(true);
                parent.validate();
            }
        }
    }

    /**
     * Returns a string representation of the state of this card layout.
     * <p>
     *  返回此卡布局的状态的字符串表示。
     * 
     * 
     * @return    a string representation of this card layout.
     */
    public String toString() {
        return getClass().getName() + "[hgap=" + hgap + ",vgap=" + vgap + "]";
    }

    /**
     * Reads serializable fields from stream.
     * <p>
     *  从流中读取可序列化字段。
     * 
     */
    private void readObject(ObjectInputStream s)
        throws ClassNotFoundException, IOException
    {
        ObjectInputStream.GetField f = s.readFields();

        hgap = f.get("hgap", 0);
        vgap = f.get("vgap", 0);

        if (f.defaulted("vector")) {
            //  pre-1.4 stream
            Hashtable<String, Component> tab = (Hashtable)f.get("tab", null);
            vector = new Vector<>();
            if (tab != null && !tab.isEmpty()) {
                for (Enumeration<String> e = tab.keys() ; e.hasMoreElements() ; ) {
                    String key = (String)e.nextElement();
                    Component comp = (Component)tab.get(key);
                    vector.add(new Card(key, comp));
                    if (comp.isVisible()) {
                        currentCard = vector.size() - 1;
                    }
                }
            }
        } else {
            vector = (Vector)f.get("vector", null);
            currentCard = f.get("currentCard", 0);
        }
    }

    /**
     * Writes serializable fields to stream.
     * <p>
     *  将可序列化字段写入流。
     */
    private void writeObject(ObjectOutputStream s)
        throws IOException
    {
        Hashtable<String, Component> tab = new Hashtable<>();
        int ncomponents = vector.size();
        for (int i = 0; i < ncomponents; i++) {
            Card card = (Card)vector.get(i);
            tab.put(card.name, card.comp);
        }

        ObjectOutputStream.PutField f = s.putFields();
        f.put("hgap", hgap);
        f.put("vgap", vgap);
        f.put("vector", vector);
        f.put("currentCard", currentCard);
        f.put("tab", tab);
        s.writeFields();
    }
}
