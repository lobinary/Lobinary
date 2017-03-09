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

import java.util.Vector;
import java.awt.*;
import javax.swing.event.*;

/**
 * ZoneView is a View implementation that creates zones for which
 * the child views are not created or stored until they are needed
 * for display or model/view translations.  This enables a substantial
 * reduction in memory consumption for situations where the model
 * being represented is very large, by building view objects only for
 * the region being actively viewed/edited.  The size of the children
 * can be estimated in some way, or calculated asynchronously with
 * only the result being saved.
 * <p>
 * ZoneView extends BoxView to provide a box that implements
 * zones for its children.  The zones are special View implementations
 * (the children of an instance of this class) that represent only a
 * portion of the model that an instance of ZoneView is responsible
 * for.  The zones don't create child views until an attempt is made
 * to display them. A box shaped view is well suited to this because:
 *   <ul>
 *   <li>
 *   Boxes are a heavily used view, and having a box that
 *   provides this behavior gives substantial opportunity
 *   to plug the behavior into a view hierarchy from the
 *   view factory.
 *   <li>
 *   Boxes are tiled in one direction, so it is easy to
 *   divide them into zones in a reliable way.
 *   <li>
 *   Boxes typically have a simple relationship to the model (i.e. they
 *   create child views that directly represent the child elements).
 *   <li>
 *   Boxes are easier to estimate the size of than some other shapes.
 *   </ul>
 * <p>
 * The default behavior is controlled by two properties, maxZoneSize
 * and maxZonesLoaded.  Setting maxZoneSize to Integer.MAX_VALUE would
 * have the effect of causing only one zone to be created.  This would
 * effectively turn the view into an implementation of the decorator
 * pattern.  Setting maxZonesLoaded to a value of Integer.MAX_VALUE would
 * cause zones to never be unloaded.  For simplicity, zones are created on
 * boundaries represented by the child elements of the element the view is
 * responsible for.  The zones can be any View implementation, but the
 * default implementation is based upon AsyncBoxView which supports fairly
 * large zones efficiently.
 *
 * <p>
 *  ZoneView是一个View实现,创建不创建或存储子视图的区域,直到显示或模型/视图转换需要子视图。
 * 这通过仅仅为正在被活动地查看/编辑的区域构建视图对象,使得能够在所表示的模型非常大的情况下显着减少存储器消耗。可以以某种方式估计子节点的大小,或者仅以保存的结果异步计算子节点的大小。
 * <p>
 *  ZoneView扩展了BoxView以提供一个为其子代实现区域的框。区域是特殊的View实现(这个类的实例的子代),它们只表示ZoneView的一个实例负责的模型的一部分。
 * 区域不会创建子视图,直到尝试显示它们。箱形视图非常适合于此,因为：。
 * <ul>
 * <li>
 *  框是一个大量使用的视图,并且具有提供此行为的框提供了将行为插入来自视图工厂的视图层次结构的大量机会。
 * <li>
 *  盒子在一个方向上平铺,因此很容易以可靠的方式将它们分成区域。
 * <li>
 *  框通常与模型具有简单的关系(即它们创建直接表示子元素的子视图)。
 * <li>
 *  盒子比一些其他形状更容易估计尺寸。
 * </ul>
 * <p>
 * 默认行为由两个属性maxZoneSize和maxZonesLoaded控制。将maxZoneSize设置为Integer.MAX_VALUE会产生只创建一个区域的效果。
 * 这将有效地将视图转换为装饰器模式的实现。将maxZonesLoaded设置为Integer.MAX_VALUE的值会导致永远不会卸载区域。
 * 为了简单起见,在由视图负责的元素的子元素所表示的边界上创建区域。区域可以是任何View实现,但默认实现是基于AsyncBoxView,它有效地支持相当大的区域。
 * 
 * 
 * @author  Timothy Prinzing
 * @see     View
 * @since   1.3
 */
public class ZoneView extends BoxView {

    int maxZoneSize = 8 * 1024;
    int maxZonesLoaded = 3;
    Vector<View> loadedZones;

    /**
     * Constructs a ZoneView.
     *
     * <p>
     *  构造一个ZoneView。
     * 
     * 
     * @param elem the element this view is responsible for
     * @param axis either View.X_AXIS or View.Y_AXIS
     */
    public ZoneView(Element elem, int axis) {
        super(elem, axis);
        loadedZones = new Vector<View>();
    }

    /**
     * Get the current maximum zone size.
     * <p>
     *  获取当前最大区域大小。
     * 
     */
    public int getMaximumZoneSize() {
        return maxZoneSize;
    }

    /**
     * Set the desired maximum zone size.  A
     * zone may get larger than this size if
     * a single child view is larger than this
     * size since zones are formed on child view
     * boundaries.
     *
     * <p>
     *  设置所需的最大区域大小。如果单个子视图大于此大小,则区域可能大于此大小,因为区域在子视图边界上形成。
     * 
     * 
     * @param size the number of characters the zone
     * may represent before attempting to break
     * the zone into a smaller size.
     */
    public void setMaximumZoneSize(int size) {
        maxZoneSize = size;
    }

    /**
     * Get the current setting of the number of zones
     * allowed to be loaded at the same time.
     * <p>
     *  获取允许同时加载的区域数的当前设置。
     * 
     */
    public int getMaxZonesLoaded() {
        return maxZonesLoaded;
    }

    /**
     * Sets the current setting of the number of zones
     * allowed to be loaded at the same time. This will throw an
     * <code>IllegalArgumentException</code> if <code>mzl</code> is less
     * than 1.
     *
     * <p>
     *  设置允许同时加载的区域数的当前设置。如果<code> mzl </code>小于1,则会抛出<code> IllegalArgumentException </code>。
     * 
     * 
     * @param mzl the desired maximum number of zones
     *  to be actively loaded, must be greater than 0
     * @exception IllegalArgumentException if <code>mzl</code> is &lt; 1
     */
    public void setMaxZonesLoaded(int mzl) {
        if (mzl < 1) {
            throw new IllegalArgumentException("ZoneView.setMaxZonesLoaded must be greater than 0.");
        }
        maxZonesLoaded = mzl;
        unloadOldZones();
    }

    /**
     * Called by a zone when it gets loaded.  This happens when
     * an attempt is made to display or perform a model/view
     * translation on a zone that was in an unloaded state.
     * This is implemented to check if the maximum number of
     * zones was reached and to unload the oldest zone if so.
     *
     * <p>
     *  由区域调用时,它被加载。当尝试在处于未加载状态的区域上显示或执行模型/视图转换时,会发生这种情况。这被实现以检查是否达到最大区域数,并且如果是这样,则卸载最旧的区域。
     * 
     * 
     * @param zone the child view that was just loaded.
     */
    protected void zoneWasLoaded(View zone) {
        //System.out.println("loading: " + zone.getStartOffset() + "," + zone.getEndOffset());
        loadedZones.addElement(zone);
        unloadOldZones();
    }

    void unloadOldZones() {
        while (loadedZones.size() > getMaxZonesLoaded()) {
            View zone = loadedZones.elementAt(0);
            loadedZones.removeElementAt(0);
            unloadZone(zone);
        }
    }

    /**
     * Unload a zone (Convert the zone to its memory saving state).
     * The zones are expected to represent a subset of the
     * child elements of the element this view is responsible for.
     * Therefore, the default implementation is to simple remove
     * all the children.
     *
     * <p>
     * 卸载区域(将区域转换为其内存保存状态)。区域应该表示此视图负责的元素的子元素的子集。因此,默认实现是简单删除所有的孩子。
     * 
     * 
     * @param zone the child view desired to be set to an
     *  unloaded state.
     */
    protected void unloadZone(View zone) {
        //System.out.println("unloading: " + zone.getStartOffset() + "," + zone.getEndOffset());
        zone.removeAll();
    }

    /**
     * Determine if a zone is in the loaded state.
     * The zones are expected to represent a subset of the
     * child elements of the element this view is responsible for.
     * Therefore, the default implementation is to return
     * true if the view has children.
     * <p>
     *  确定区域是否处于加载状态。区域应该表示此视图负责的元素的子元素的子集。因此,如果视图有子节点,默认实现是返回true。
     * 
     */
    protected boolean isZoneLoaded(View zone) {
        return (zone.getViewCount() > 0);
    }

    /**
     * Create a view to represent a zone for the given
     * range within the model (which should be within
     * the range of this objects responsibility).  This
     * is called by the zone management logic to create
     * new zones.  Subclasses can provide a different
     * implementation for a zone by changing this method.
     *
     * <p>
     *  创建一个视图来表示模型中给定范围(应在此对象责任范围内)的区域。这由区域管理逻辑调用以创建新区域。通过更改此方法,子类可以为区域提供不同的实现。
     * 
     * 
     * @param p0 the start of the desired zone.  This should
     *  be &gt;= getStartOffset() and &lt; getEndOffset().  This
     *  value should also be &lt; p1.
     * @param p1 the end of the desired zone.  This should
     *  be &gt; getStartOffset() and &lt;= getEndOffset().  This
     *  value should also be &gt; p0.
     */
    protected View createZone(int p0, int p1) {
        Document doc = getDocument();
        View zone;
        try {
            zone = new Zone(getElement(),
                            doc.createPosition(p0),
                            doc.createPosition(p1));
        } catch (BadLocationException ble) {
            // this should puke in some way.
            throw new StateInvariantError(ble.getMessage());
        }
        return zone;
    }

    /**
     * Loads all of the children to initialize the view.
     * This is called by the <code>setParent</code> method.
     * This is reimplemented to not load any children directly
     * (as they are created by the zones).  This method creates
     * the initial set of zones.  Zones don't actually get
     * populated however until an attempt is made to display
     * them or to do model/view coordinate translation.
     *
     * <p>
     *  加载所有子项以初始化视图。这由<code> setParent </code>方法调用。这被重新实现以不直接加载任何孩子(因为它们是由区域创建的)。此方法创建初始区域集。
     * 区域实际上不填充,直到尝试显示它们或做模型/视图坐标翻译。
     * 
     * 
     * @param f the view factory
     */
    protected void loadChildren(ViewFactory f) {
        // build the first zone.
        Document doc = getDocument();
        int offs0 = getStartOffset();
        int offs1 = getEndOffset();
        append(createZone(offs0, offs1));
        handleInsert(offs0, offs1 - offs0);
    }

    /**
     * Returns the child view index representing the given position in
     * the model.
     *
     * <p>
     *  返回表示模型中给定位置的子视图索引。
     * 
     * 
     * @param pos the position &gt;= 0
     * @return  index of the view representing the given position, or
     *   -1 if no view represents that position
     */
    protected int getViewIndexAtPosition(int pos) {
        // PENDING(prinz) this could be done as a binary
        // search, and probably should be.
        int n = getViewCount();
        if (pos == getEndOffset()) {
            return n - 1;
        }
        for(int i = 0; i < n; i++) {
            View v = getView(i);
            if(pos >= v.getStartOffset() &&
               pos < v.getEndOffset()) {
                return i;
            }
        }
        return -1;
    }

    void handleInsert(int pos, int length) {
        int index = getViewIndex(pos, Position.Bias.Forward);
        View v = getView(index);
        int offs0 = v.getStartOffset();
        int offs1 = v.getEndOffset();
        if ((offs1 - offs0) > maxZoneSize) {
            splitZone(index, offs0, offs1);
        }
    }

    void handleRemove(int pos, int length) {
        // IMPLEMENT
    }

    /**
     * Break up the zone at the given index into pieces
     * of an acceptable size.
     * <p>
     *  将给定索引处的区域分解成可接受大小的块。
     * 
     */
    void splitZone(int index, int offs0, int offs1) {
        // divide the old zone into a new set of bins
        Element elem = getElement();
        Document doc = elem.getDocument();
        Vector<View> zones = new Vector<View>();
        int offs = offs0;
        do {
            offs0 = offs;
            offs = Math.min(getDesiredZoneEnd(offs0), offs1);
            zones.addElement(createZone(offs0, offs));
        } while (offs < offs1);
        View oldZone = getView(index);
        View[] newZones = new View[zones.size()];
        zones.copyInto(newZones);
        replace(index, 1, newZones);
    }

    /**
     * Returns the zone position to use for the
     * end of a zone that starts at the given
     * position.  By default this returns something
     * close to half the max zone size.
     * <p>
     *  返回要用于从给定位置开始的区域结束的区域位置。默认情况下,返回的值接近最大区域大小的一半。
     * 
     */
    int getDesiredZoneEnd(int pos) {
        Element elem = getElement();
        int index = elem.getElementIndex(pos + (maxZoneSize / 2));
        Element child = elem.getElement(index);
        int offs0 = child.getStartOffset();
        int offs1 = child.getEndOffset();
        if ((offs1 - pos) > maxZoneSize) {
            if (offs0 > pos) {
                return offs0;
            }
        }
        return offs1;
    }

    // ---- View methods ----------------------------------------------------

    /**
     * The superclass behavior will try to update the child views
     * which is not desired in this case, since the children are
     * zones and not directly effected by the changes to the
     * associated element.  This is reimplemented to do nothing
     * and return false.
     * <p>
     * 超类行为将尝试更新子视图,这在这种情况下是不希望的,因为子对象是区域,并且不直接受到对相关联元素的改变的影响。这被重新实现以不做任何事情并返回false。
     * 
     */
    protected boolean updateChildren(DocumentEvent.ElementChange ec,
                                     DocumentEvent e, ViewFactory f) {
        return false;
    }

    /**
     * Gives notification that something was inserted into the document
     * in a location that this view is responsible for.  This is largely
     * delegated to the superclass, but is reimplemented to update the
     * relevant zone (i.e. determine if a zone needs to be split into a
     * set of 2 or more zones).
     *
     * <p>
     *  提供通知,说明在此数据视图负责的位置,文档中插入了某些内容。这在很大程度上委托给超类,但是被重新实现以更新相关区域(即,确定区域是否需要被划分为一组2个或更多个区域)。
     * 
     * 
     * @param changes the change information from the associated document
     * @param a the current allocation of the view
     * @param f the factory to use to rebuild if the view has children
     * @see View#insertUpdate
     */
    public void insertUpdate(DocumentEvent changes, Shape a, ViewFactory f) {
        handleInsert(changes.getOffset(), changes.getLength());
        super.insertUpdate(changes, a, f);
    }

    /**
     * Gives notification that something was removed from the document
     * in a location that this view is responsible for.  This is largely
     * delegated to the superclass, but is reimplemented to update the
     * relevant zones (i.e. determine if zones need to be removed or
     * joined with another zone).
     *
     * <p>
     *  提供通知,说明该视图负责的位置中的文档被删除了。这在很大程度上被委托给超类,但是被重新实现以更新相关区域(即,确定区域是否需要被移除或与另一区域连接)。
     * 
     * 
     * @param changes the change information from the associated document
     * @param a the current allocation of the view
     * @param f the factory to use to rebuild if the view has children
     * @see View#removeUpdate
     */
    public void removeUpdate(DocumentEvent changes, Shape a, ViewFactory f) {
        handleRemove(changes.getOffset(), changes.getLength());
        super.removeUpdate(changes, a, f);
    }

    /**
     * Internally created view that has the purpose of holding
     * the views that represent the children of the ZoneView
     * that have been arranged in a zone.
     * <p>
     *  内部创建的视图,其目的是保存代表ZoneView中已排列在区域中的子对象的视图。
     * 
     */
    class Zone extends AsyncBoxView {

        private Position start;
        private Position end;

        public Zone(Element elem, Position start, Position end) {
            super(elem, ZoneView.this.getAxis());
            this.start = start;
            this.end = end;
        }

        /**
         * Creates the child views and populates the
         * zone with them.  This is done by translating
         * the positions to child element index locations
         * and building views to those elements.  If the
         * zone is already loaded, this does nothing.
         * <p>
         *  创建子视图并使用它们填充区域。这是通过将位置转换为子元素索引位置和构建视图到这些元素。如果区域已经加载,则不执行任何操作。
         * 
         */
        public void load() {
            if (! isLoaded()) {
                setEstimatedMajorSpan(true);
                Element e = getElement();
                ViewFactory f = getViewFactory();
                int index0 = e.getElementIndex(getStartOffset());
                int index1 = e.getElementIndex(getEndOffset());
                View[] added = new View[index1 - index0 + 1];
                for (int i = index0; i <= index1; i++) {
                    added[i - index0] = f.create(e.getElement(i));
                }
                replace(0, 0, added);

                zoneWasLoaded(this);
            }
        }

        /**
         * Removes the child views and returns to a
         * state of unloaded.
         * <p>
         *  删除子视图并返回卸载状态。
         * 
         */
        public void unload() {
            setEstimatedMajorSpan(true);
            removeAll();
        }

        /**
         * Determines if the zone is in the loaded state
         * or not.
         * <p>
         *  确定区域是否处于加载状态。
         * 
         */
        public boolean isLoaded() {
            return (getViewCount() != 0);
        }

        /**
         * This method is reimplemented to not build the children
         * since the children are created when the zone is loaded
         * rather then when it is placed in the view hierarchy.
         * The major span is estimated at this point by building
         * the first child (but not storing it), and calling
         * setEstimatedMajorSpan(true) followed by setSpan for
         * the major axis with the estimated span.
         * <p>
         * 重新实现此方法以不构建子项,因为子区域在加载区域时创建,而不是在放置在视图层次结构中时创建。
         * 此时通过构建第一个子(但不存储它)来估计主跨度,并且调用setEstimatedMajorSpan(true),随后使用具有估计跨度的长轴的setSpan。
         * 
         */
        protected void loadChildren(ViewFactory f) {
            // mark the major span as estimated
            setEstimatedMajorSpan(true);

            // estimate the span
            Element elem = getElement();
            int index0 = elem.getElementIndex(getStartOffset());
            int index1 = elem.getElementIndex(getEndOffset());
            int nChildren = index1 - index0;

            // replace this with something real
            //setSpan(getMajorAxis(), nChildren * 10);

            View first = f.create(elem.getElement(index0));
            first.setParent(this);
            float w = first.getPreferredSpan(X_AXIS);
            float h = first.getPreferredSpan(Y_AXIS);
            if (getMajorAxis() == X_AXIS) {
                w *= nChildren;
            } else {
                h += nChildren;
            }

            setSize(w, h);
        }

        /**
         * Publish the changes in preferences upward to the parent
         * view.
         * <p>
         * This is reimplemented to stop the superclass behavior
         * if the zone has not yet been loaded.  If the zone is
         * unloaded for example, the last seen major span is the
         * best estimate and a calculated span for no children
         * is undesirable.
         * <p>
         *  将首选项中的更改向上发布到父视图。
         * <p>
         *  如果区域尚未加载,则重新实现以停止超类行为。例如,如果区域被卸载,则最后看到的主跨度是最佳估计,并且对于没有子代的计算跨度是不期望的。
         * 
         */
        protected void flushRequirementChanges() {
            if (isLoaded()) {
                super.flushRequirementChanges();
            }
        }

        /**
         * Returns the child view index representing the given position in
         * the model.  Since the zone contains a cluster of the overall
         * set of child elements, we can determine the index fairly
         * quickly from the model by subtracting the index of the
         * start offset from the index of the position given.
         *
         * <p>
         *  返回表示模型中给定位置的子视图索引。由于区域包含整个子元素集合的集群,我们可以通过从给定位置的索引中减去起始偏移的索引,从模型中相当快地确定索引。
         * 
         * 
         * @param pos the position >= 0
         * @return  index of the view representing the given position, or
         *   -1 if no view represents that position
         * @since 1.3
         */
        public int getViewIndex(int pos, Position.Bias b) {
            boolean isBackward = (b == Position.Bias.Backward);
            pos = (isBackward) ? Math.max(0, pos - 1) : pos;
            Element elem = getElement();
            int index1 = elem.getElementIndex(pos);
            int index0 = elem.getElementIndex(getStartOffset());
            return index1 - index0;
        }

        protected boolean updateChildren(DocumentEvent.ElementChange ec,
                                         DocumentEvent e, ViewFactory f) {
            // the structure of this element changed.
            Element[] removedElems = ec.getChildrenRemoved();
            Element[] addedElems = ec.getChildrenAdded();
            Element elem = getElement();
            int index0 = elem.getElementIndex(getStartOffset());
            int index1 = elem.getElementIndex(getEndOffset()-1);
            int index = ec.getIndex();
            if ((index >= index0) && (index <= index1)) {
                // The change is in this zone
                int replaceIndex = index - index0;
                int nadd = Math.min(index1 - index0 + 1, addedElems.length);
                int nremove = Math.min(index1 - index0 + 1, removedElems.length);
                View[] added = new View[nadd];
                for (int i = 0; i < nadd; i++) {
                    added[i] = f.create(addedElems[i]);
                }
                replace(replaceIndex, nremove, added);
            }
            return true;
        }

        // --- View methods ----------------------------------

        /**
         * Fetches the attributes to use when rendering.  This view
         * isn't directly responsible for an element so it returns
         * the outer classes attributes.
         * <p>
         *  获取渲染时要使用的属性。此视图不直接负责元素,因此返回外部类属性。
         * 
         */
        public AttributeSet getAttributes() {
            return ZoneView.this.getAttributes();
        }

        /**
         * Renders using the given rendering surface and area on that
         * surface.  This is implemented to load the zone if its not
         * already loaded, and then perform the superclass behavior.
         *
         * <p>
         *  使用给定的渲染表面和该表面上的区域渲染。这被实现以加载区域,如果它尚未加载,然后执行超类行为。
         * 
         * 
         * @param g the rendering surface to use
         * @param a the allocated region to render into
         * @see View#paint
         */
        public void paint(Graphics g, Shape a) {
            load();
            super.paint(g, a);
        }

        /**
         * Provides a mapping from the view coordinate space to the logical
         * coordinate space of the model.  This is implemented to first
         * make sure the zone is loaded before providing the superclass
         * behavior.
         *
         * <p>
         *  提供从视图坐标空间到模型的逻辑坐标空间的映射。这是实现为首先确保在提供超类行为之前加载区域。
         * 
         * 
         * @param x   x coordinate of the view location to convert >= 0
         * @param y   y coordinate of the view location to convert >= 0
         * @param a the allocated region to render into
         * @return the location within the model that best represents the
         *  given point in the view >= 0
         * @see View#viewToModel
         */
        public int viewToModel(float x, float y, Shape a, Position.Bias[] bias) {
            load();
            return super.viewToModel(x, y, a, bias);
        }

        /**
         * Provides a mapping from the document model coordinate space
         * to the coordinate space of the view mapped to it.  This is
         * implemented to provide the superclass behavior after first
         * making sure the zone is loaded (The zone must be loaded to
         * make this calculation).
         *
         * <p>
         * 提供从文档模型坐标空间到映射到其的视图的坐标空间的映射。这被实现以在首次确保加载区域之后提供超类行为(必须加载区域以进行此计算)。
         * 
         * 
         * @param pos the position to convert
         * @param a the allocated region to render into
         * @return the bounding box of the given position
         * @exception BadLocationException  if the given position does not represent a
         *   valid location in the associated document
         * @see View#modelToView
         */
        public Shape modelToView(int pos, Shape a, Position.Bias b) throws BadLocationException {
            load();
            return super.modelToView(pos, a, b);
        }

        /**
         * Start of the zones range.
         *
         * <p>
         *  区域范围的开始。
         * 
         * 
         * @see View#getStartOffset
         */
        public int getStartOffset() {
            return start.getOffset();
        }

        /**
         * End of the zones range.
         * <p>
         *  区域范围的结束。
         * 
         */
        public int getEndOffset() {
            return end.getOffset();
        }

        /**
         * Gives notification that something was inserted into
         * the document in a location that this view is responsible for.
         * If the zone has been loaded, the superclass behavior is
         * invoked, otherwise this does nothing.
         *
         * <p>
         *  提供通知,说明在此数据视图负责的位置,文档中插入了某些内容。如果区域已加载,则调用超类行为,否则不会执行任何操作。
         * 
         * 
         * @param e the change information from the associated document
         * @param a the current allocation of the view
         * @param f the factory to use to rebuild if the view has children
         * @see View#insertUpdate
         */
        public void insertUpdate(DocumentEvent e, Shape a, ViewFactory f) {
            if (isLoaded()) {
                super.insertUpdate(e, a, f);
            }
        }

        /**
         * Gives notification that something was removed from the document
         * in a location that this view is responsible for.
         * If the zone has been loaded, the superclass behavior is
         * invoked, otherwise this does nothing.
         *
         * <p>
         *  提供通知,说明该视图负责的位置中的文档被删除了。如果区域已加载,则调用超类行为,否则不会执行任何操作。
         * 
         * 
         * @param e the change information from the associated document
         * @param a the current allocation of the view
         * @param f the factory to use to rebuild if the view has children
         * @see View#removeUpdate
         */
        public void removeUpdate(DocumentEvent e, Shape a, ViewFactory f) {
            if (isLoaded()) {
                super.removeUpdate(e, a, f);
            }
        }

        /**
         * Gives notification from the document that attributes were changed
         * in a location that this view is responsible for.
         * If the zone has been loaded, the superclass behavior is
         * invoked, otherwise this does nothing.
         *
         * <p>
         *  从文档中提供属性在此视图负责的位置中更改的通知。如果区域已加载,则调用超类行为,否则不会执行任何操作。
         * 
         * @param e the change information from the associated document
         * @param a the current allocation of the view
         * @param f the factory to use to rebuild if the view has children
         * @see View#removeUpdate
         */
        public void changedUpdate(DocumentEvent e, Shape a, ViewFactory f) {
            if (isLoaded()) {
                super.changedUpdate(e, a, f);
            }
        }

    }
}
