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

import java.awt.*;
import javax.swing.SwingConstants;
import javax.swing.event.*;

/**
 * <p>
 * A very important part of the text package is the <code>View</code> class.
 * As the name suggests it represents a view of the text model,
 * or a piece of the text model.
 * It is this class that is responsible for the look of the text component.
 * The view is not intended to be some completely new thing that one must
 * learn, but rather is much like a lightweight component.
 * <p>
By default, a view is very light.  It contains a reference to the parent
view from which it can fetch many things without holding state, and it
contains a reference to a portion of the model (<code>Element</code>).
A view does not
have to exactly represent an element in the model, that is simply a typical
and therefore convenient mapping.  A view can alternatively maintain a couple
of Position objects to maintain its location in the model (i.e. represent
a fragment of an element).  This is typically the result of formatting where
views have been broken down into pieces.  The convenience of a substantial
relationship to the element makes it easier to build factories to produce the
views, and makes it easier  to keep track of the view pieces as the model is
changed and the view must be changed to reflect the model.  Simple views
therefore represent an Element directly and complex views do not.
<p>
A view has the following responsibilities:
  <dl>

    <dt><b>Participate in layout.</b>
    <dd>
    <p>The view has a <code>setSize</code> method which is like
    <code>doLayout</code> and <code>setSize</code> in <code>Component</code> combined.
    The view has a <code>preferenceChanged</code> method which is
    like <code>invalidate</code> in <code>Component</code> except that one can
    invalidate just one axis
    and the child requesting the change is identified.
    <p>A View expresses the size that it would like to be in terms of three
    values, a minimum, a preferred, and a maximum span.  Layout in a view is
    can be done independently upon each axis.  For a properly functioning View
    implementation, the minimum span will be &lt;= the preferred span which in turn
    will be &lt;= the maximum span.
    </p>
    <p style="text-align:center"><img src="doc-files/View-flexibility.jpg"
                     alt="The above text describes this graphic.">
    <p>The minimum set of methods for layout are:
    <ul>
    <li>{@link #getMinimumSpan(int) getMinimumSpan}
    <li>{@link #getPreferredSpan(int) getPreferredSpan}
    <li>{@link #getMaximumSpan(int) getMaximumSpan}
    <li>{@link #getAlignment(int) getAlignment}
    <li>{@link #preferenceChanged(javax.swing.text.View, boolean, boolean) preferenceChanged}
    <li>{@link #setSize(float, float) setSize}
    </ul>

  <p>The <code>setSize</code> method should be prepared to be called a number of times
    (i.e. It may be called even if the size didn't change).
    The <code>setSize</code> method
    is generally called to make sure the View layout is complete prior to trying
    to perform an operation on it that requires an up-to-date layout.  A view's
    size should <em>always</em> be set to a value within the minimum and maximum
    span specified by that view.  Additionally, the view must always call the
    <code>preferenceChanged</code> method on the parent if it has changed the
    values for the
    layout it would like, and expects the parent to honor.  The parent View is
    not required to recognize a change until the <code>preferenceChanged</code>
    has been sent.
    This allows parent View implementations to cache the child requirements if
    desired.  The calling sequence looks something like the following:
    </p>
    <p style="text-align:center">
      <img src="doc-files/View-layout.jpg"
       alt="Sample calling sequence between parent view and child view:
       setSize, getMinimum, getPreferred, getMaximum, getAlignment, setSize">
    <p>The exact calling sequence is up to the layout functionality of
    the parent view (if the view has any children).  The view may collect
    the preferences of the children prior to determining what it will give
    each child, or it might iteratively update the children one at a time.
    </p>

    <dt><b>Render a portion of the model.</b>
    <dd>
    <p>This is done in the paint method, which is pretty much like a component
    paint method.  Views are expected to potentially populate a fairly large
    tree.  A <code>View</code> has the following semantics for rendering:
    </p>
    <ul>
    <li>The view gets its allocation from the parent at paint time, so it
    must be prepared to redo layout if the allocated area is different from
    what it is prepared to deal with.
    <li>The coordinate system is the same as the hosting <code>Component</code>
    (i.e. the <code>Component</code> returned by the
    {@link #getContainer getContainer} method).
    This means a child view lives in the same coordinate system as the parent
    view unless the parent has explicitly changed the coordinate system.
    To schedule itself to be repainted a view can call repaint on the hosting
    <code>Component</code>.
    <li>The default is to <em>not clip</em> the children.  It is more efficient
    to allow a view to clip only if it really feels it needs clipping.
    <li>The <code>Graphics</code> object given is not initialized in any way.
    A view should set any settings needed.
    <li>A <code>View</code> is inherently transparent.  While a view may render into its
    entire allocation, typically a view does not.  Rendering is performed by
    traversing down the tree of <code>View</code> implementations.
    Each <code>View</code> is responsible
    for rendering its children.  This behavior is depended upon for thread
    safety.  While view implementations do not necessarily have to be implemented
    with thread safety in mind, other view implementations that do make use of
    concurrency can depend upon a tree traversal to guarantee thread safety.
    <li>The order of views relative to the model is up to the implementation.
    Although child views will typically be arranged in the same order that they
    occur in the model, they may be visually arranged in an entirely different
    order.  View implementations may have Z-Order associated with them if the
    children are overlapping.
    </ul>
    <p>The methods for rendering are:
    <ul>
    <li>{@link #paint(java.awt.Graphics, java.awt.Shape) paint}
    </ul>

    <dt><b>Translate between the model and view coordinate systems.</b>
    <dd>
    <p>Because the view objects are produced from a factory and therefore cannot
    necessarily be counted upon to be in a particular pattern, one must be able
    to perform translation to properly locate spatial representation of the model.
    The methods for doing this are:
    <ul>
    <li>{@link #modelToView(int, javax.swing.text.Position.Bias, int, javax.swing.text.Position.Bias, java.awt.Shape) modelToView}
    <li>{@link #viewToModel(float, float, java.awt.Shape, javax.swing.text.Position.Bias[]) viewToModel}
    <li>{@link #getDocument() getDocument}
    <li>{@link #getElement() getElement}
    <li>{@link #getStartOffset() getStartOffset}
    <li>{@link #getEndOffset() getEndOffset}
    </ul>
    <p>The layout must be valid prior to attempting to make the translation.
    The translation is not valid, and must not be attempted while changes
    are being broadcasted from the model via a <code>DocumentEvent</code>.
    </p>

    <dt><b>Respond to changes from the model.</b>
    <dd>
    <p>If the overall view is represented by many pieces (which is the best situation
    if one want to be able to change the view and write the least amount of new code),
    it would be impractical to have a huge number of <code>DocumentListener</code>s.
    If each
    view listened to the model, only a few would actually be interested in the
    changes broadcasted at any given time.   Since the model has no knowledge of
    views, it has no way to filter the broadcast of change information.  The view
    hierarchy itself is instead responsible for propagating the change information.
    At any level in the view hierarchy, that view knows enough about its children to
    best distribute the change information further.   Changes are therefore broadcasted
    starting from the root of the view hierarchy.
    The methods for doing this are:
    <ul>
    <li>{@link #insertUpdate insertUpdate}
    <li>{@link #removeUpdate removeUpdate}
    <li>{@link #changedUpdate changedUpdate}
    </ul>
    <p>
</dl>
 *
 * <p>
 * <p>
 *  文本包的一个非常重要的部分是<code> View </code>类。顾名思义,它代表文本模型的视图,或者是文本模型的一部分。正是这个类负责文本组件的外观。
 * 这个视图并不是一个必须学习的全新东西,而是一个轻量级组件。
 * <p>
 *  默认情况下,视图很轻。它包含一个对父视图的引用,它可以从中获取很多没有保持状态的东西,它包含对模型的一部分的引用(<code> Element </code>)。
 * 视图不必准确地表示模型中的元素,这只是典型的并因此方便的映射。视图可以备选地保持几个Position对象以维持其在模型中的位置(即,表示元素的片段)。这通常是格式化的结果,其中视图已分解成碎片。
 * 与元素的实质关系的便利性使得更容易构建工厂以产生视图,并且使得当模型改变时更容易跟踪视图片段并且必须改变视图以反映模型。因此,简单视图直接表示元素,而复杂视图不表示。
 * <p>
 *  视图有以下职责：
 * <dl>
 * 
 *  <dt> <b>参与布局。</b>
 * <dd>
 * <p>视图具有<code> setSize </code>方法,它与<code> Component </code>组合中的<code> doLayout </code>和<code> setSize 
 * </code>该视图具有<code> preferenceChanged </code>方法,例如<code> Component </code>中的<code> invalidate </code>,
 * 除了可以仅使一个轴无效并且请求更改的子进程被识别。
 *  <p> A View表示它希望以三个值,最小值,首选和最大跨度的大小。视图中的布局可以对每个轴独立完成。对于正常工作的View实现,最小跨度将小于优选跨度,其又将小于最大跨度。
 * </p>
 *  <p style ="text-align：center"> <img src ="doc-files / View-flexibility.jpg"
 * alt="The above text describes this graphic.">
 *  <p>布局的最小方法集有：
 * <ul>
 *  <li> {@ link #getMinimumSpan(int)getMinimumSpan} <li> {@ link #getPreferredSpan(int)getPreferredSpan}
 *  <li> {@ link #getMaximumSpan(int)getMaximumSpan} <li> {@ link #getAlignment getAlignment} <li> {@ link #setSize(float,float)setSize}
 *  {@ link #preferenceChanged(javax.swing.text.View,boolean,boolean)。
 * </ul>
 * 
 * <p> <code> setSize </code>方法应该准备好被调用多次(即即使大小没有改变也可以调用它)。
 * 通常调用<code> setSize </code>方法以确保View布局在尝试对需要最新布局的操作执行之前完成。视图大小应始终</em>设置为该视图指定的最小和最大跨度内的值。
 * 此外,如果视图必须调整父级的<code> preferenceChanged </code>方法,如果它已经更改了布局的值,并且期望父级的值。
 * 在发送<code> preferenceChanged </code>之前,父视图不需要识别更改。这允许父View实现缓存子需求(如果需要)。调用顺序看起来像下面这样：。
 * </p>
 * <p style="text-align:center">
 *  <img src ="doc-files / View-layout.jpg"alt ="父视图和子视图之间的示例调用顺序：
 * setSize, getMinimum, getPreferred, getMaximum, getAlignment, setSize">
 *  <p>精确的调用顺序取决于父视图的布局功能(如果视图有任何子视图)。视图可以在确定孩子将给予每个孩子之前收集孩子的偏好,或者它可以一次一个地迭代地更新孩子。
 * </p>
 * 
 *  <dt> <b>呈现模型的一部分。</b>
 * <dd>
 * <p>这是在paint方法中完成的,这非常像一个组件paint方法。视图预计可能填充相当大的树。 <code> View </code>具有以下用于呈现的语义：
 * </p>
 * <ul>
 * <li>视图在绘制时从父级获取其分配,因此如果分配的区域与准备处理的区域不同,则必须准备重新布置布局。
 *  <li>坐标系与托管<code> Component </code>(即{@link #getContainer getContainer}方法返回的<code> Component </code>)
 * 相同。
 * <li>视图在绘制时从父级获取其分配,因此如果分配的区域与准备处理的区域不同,则必须准备重新布置布局。这意味着子视图生活在与父视图相同的坐标系中,除非父对象明确地更改了坐标系。
 * 要调度自己重绘,视图可以在托管<code> Component </code>上调用重绘。 <li>默认为<em>不剪辑</em>子项。只有当它真的感觉需要剪辑时,才允许视图剪辑更高效。
 *  <li>给出的<code> Graphics </code>对象未以任何方式初始化。视图应设置所需的任何设置。 <li> <code>查看</code>本质上是透明的。
 * 虽然视图可能呈现其整个分配,但通常视图不会。通过遍历<code> View </code>实现的树执行渲染。每个<code> View </code>负责渲染它的孩子。这种行为取决于线程安全。
 * 虽然视图实现不一定必须考虑线程安全,但确实利用并发的其他视图实现可以依赖于树遍历来保证线程安全。 <li>相对于模型的视图顺序取决于实现。
 * 虽然子视图通常以与它们在模型中出现的顺序相同的顺序排列,但它们可以以完全不同的顺序在视觉上排列。如果子节点重叠,则视图实现可以具有与它们相关联的Z顺序。
 * </ul>
 * <p>渲染的方法有：
 * <ul>
 *  <li> {@ link #paint(java.awt.Graphics,java.awt.Shape)paint}
 * </ul>
 * 
 *  <dt> <b>在模型和视图坐标系之间进行转换。</b>
 * <dd>
 *  <p>因为视图对象是从工厂生成的,因此不一定被计入特定模式,人们必须能够执行转换以正确地定位模型的空间表示。这样做的方法是：
 * <ul>
 *  <li> {@ link #modelToView(int,javax.swing.text.Position.Bias,int,javax.swing.text.Position.Bias,java.awt.Shape)modelToView}
 *  <li> {@ link #viewToModel float,float,java.awt.Shape,javax.swing.text.Position.Bias [])viewToModel} 
 * <li> {@ link #getDocument()getDocument} <li> {@ link #getElement()getElement} <li > {@ link #getStartOffset()getStartOffset}
 *  <li> {@ link #getEndOffset()getEndOffset}。
 * </ul>
 *  <p>布局必须在尝试进行翻译之前有效。翻译无效,并且在通过<code> DocumentEvent </code>从模型广播更改时,不得尝试翻译。
 * </p>
 * 
 *  <dt> <b>响应来自模型的更改。</b>
 * <dd>
 * <p>如果整体视图由许多部分表示(这是最好的情况,如果想要能够更改视图和写入最少量的新代码),拥有大量的代码是不切实际的> DocumentListener </code>。
 * 如果每个视图都倾听模型,只有少数人实际上对在任何给定时间广播的变化感兴趣。由于模型不具有视图的知识,因此它没有办法过滤变化信息的广播。视图层次结构本身负责传播更改信息。
 * 在视图层次结构中的任何级别,该视图对其子节点足够了解,以便进一步最佳地分发更改信息。因此,从视图层次结构的根开始广播更改。这样做的方法是：。
 * <ul>
 *  <li> {@ link #insertUpdate insertUpdate} <li> {@ link #removeUpdate removeUpdate} <li> {@ link #changedUpdate changedUpdate}
 * 。
 * </ul>
 * <p>
 * </dl>
 * 
 * 
 * @author  Timothy Prinzing
 */
public abstract class View implements SwingConstants {

    /**
     * Creates a new <code>View</code> object.
     *
     * <p>
     *  创建一个新的<code> View </code>对象。
     * 
     * 
     * @param elem the <code>Element</code> to represent
     */
    public View(Element elem) {
        this.elem = elem;
    }

    /**
     * Returns the parent of the view.
     *
     * <p>
     *  返回视图的父视图。
     * 
     * 
     * @return the parent, or <code>null</code> if none exists
     */
    public View getParent() {
        return parent;
    }

    /**
     *  Returns a boolean that indicates whether
     *  the view is visible or not.  By default
     *  all views are visible.
     *
     * <p>
     *  返回一个布尔值,指示视图是否可见。默认情况下,所有视图都可见。
     * 
     * 
     *  @return always returns true
     */
    public boolean isVisible() {
        return true;
    }


    /**
     * Determines the preferred span for this view along an
     * axis.
     *
     * <p>
     *  确定沿着轴的此视图的首选跨度。
     * 
     * 
     * @param axis may be either <code>View.X_AXIS</code> or
     *          <code>View.Y_AXIS</code>
     * @return   the span the view would like to be rendered into.
     *           Typically the view is told to render into the span
     *           that is returned, although there is no guarantee.
     *           The parent may choose to resize or break the view
     * @see View#getPreferredSpan
     */
    public abstract float getPreferredSpan(int axis);

    /**
     * Determines the minimum span for this view along an
     * axis.
     *
     * <p>
     *  确定沿轴的此视图的最小跨度。
     * 
     * 
     * @param axis may be either <code>View.X_AXIS</code> or
     *          <code>View.Y_AXIS</code>
     * @return  the minimum span the view can be rendered into
     * @see View#getPreferredSpan
     */
    public float getMinimumSpan(int axis) {
        int w = getResizeWeight(axis);
        if (w == 0) {
            // can't resize
            return getPreferredSpan(axis);
        }
        return 0;
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
     *          <code>View.Y_AXIS</code>
     * @return  the maximum span the view can be rendered into
     * @see View#getPreferredSpan
     */
    public float getMaximumSpan(int axis) {
        int w = getResizeWeight(axis);
        if (w == 0) {
            // can't resize
            return getPreferredSpan(axis);
        }
        return Integer.MAX_VALUE;
    }

    /**
     * Child views can call this on the parent to indicate that
     * the preference has changed and should be reconsidered
     * for layout.  By default this just propagates upward to
     * the next parent.  The root view will call
     * <code>revalidate</code> on the associated text component.
     *
     * <p>
     * 子视图可以在父对象上调用此选项来指示首选项已更改,并应重新考虑布局。默认情况下,这只是传播到下一个父。根视图将在相关联的文本组件上调用<code> revalidate </code>。
     * 
     * 
     * @param child the child view
     * @param width true if the width preference has changed
     * @param height true if the height preference has changed
     * @see javax.swing.JComponent#revalidate
     */
    public void preferenceChanged(View child, boolean width, boolean height) {
        View parent = getParent();
        if (parent != null) {
            parent.preferenceChanged(this, width, height);
        }
    }

    /**
     * Determines the desired alignment for this view along an
     * axis.  The desired alignment is returned.  This should be
     * a value &gt;= 0.0 and &lt;= 1.0, where 0 indicates alignment at
     * the origin and 1.0 indicates alignment to the full span
     * away from the origin.  An alignment of 0.5 would be the
     * center of the view.
     *
     * <p>
     *  确定沿着轴的该视图的期望对准。返回所需的对齐。这应该是值> = 0.0和<= 1.0,其中0表示在原点处的对准,1.0表示与远离原点的全跨度的对准。对齐为0.5将是视图的中心。
     * 
     * 
     * @param axis may be either <code>View.X_AXIS</code> or
     *          <code>View.Y_AXIS</code>
     * @return the value 0.5
     */
    public float getAlignment(int axis) {
        return 0.5f;
    }

    /**
     * Renders using the given rendering surface and area on that
     * surface.  The view may need to do layout and create child
     * views to enable itself to render into the given allocation.
     *
     * <p>
     *  使用给定的渲染表面和该表面上的区域渲染。视图可能需要进行布局和创建子视图,以使自身能够呈现给定的分配。
     * 
     * 
     * @param g the rendering surface to use
     * @param allocation the allocated region to render into
     */
    public abstract void paint(Graphics g, Shape allocation);

    /**
     * Establishes the parent view for this view.  This is
     * guaranteed to be called before any other methods if the
     * parent view is functioning properly.  This is also
     * the last method called, since it is called to indicate
     * the view has been removed from the hierarchy as
     * well. When this method is called to set the parent to
     * null, this method does the same for each of its children,
     * propagating the notification that they have been
     * disconnected from the view tree. If this is
     * reimplemented, <code>super.setParent()</code> should
     * be called.
     *
     * <p>
     *  为此视图建立父视图。如果父视图正常工作,这保证在任何其他方法之前被调用。这也是最后调用的方法,因为它被调用以指示视图已经从层次结构中删除。
     * 当调用此方法将父项设置为null时,此方法对其每个子项执行相同的操作,传播它们已从视图树断开的通知。如果重新实现,应该调用<code> super.setParent()</code>。
     * 
     * 
     * @param parent the new parent, or <code>null</code> if the view is
     *          being removed from a parent
     */
    public void setParent(View parent) {
        // if the parent is null then propogate down the view tree
        if (parent == null) {
            for (int i = 0; i < getViewCount(); i++) {
                if (getView(i).getParent() == this) {
                    // in FlowView.java view might be referenced
                    // from two super-views as a child. see logicalView
                    getView(i).setParent(null);
                }
            }
        }
        this.parent = parent;
    }

    /**
     * Returns the number of views in this view.  Since
     * the default is to not be a composite view this
     * returns 0.
     *
     * <p>
     *  返回此视图中的视图数。因为默认是不是一个复合视图,这将返回0。
     * 
     * 
     * @return the number of views &gt;= 0
     * @see View#getViewCount
     */
    public int getViewCount() {
        return 0;
    }

    /**
     * Gets the <i>n</i>th child view.  Since there are no
     * children by default, this returns <code>null</code>.
     *
     * <p>
     * 获取第<n>个子视图。因为默认情况下没有子级,所以返回<code> null </code>。
     * 
     * 
     * @param n the number of the view to get, &gt;= 0 &amp;&amp; &lt; getViewCount()
     * @return the view
     */
    public View getView(int n) {
        return null;
    }


    /**
     * Removes all of the children.  This is a convenience
     * call to <code>replace</code>.
     *
     * <p>
     *  删除所有的孩子。这是对<code> replace </code>的方便调用。
     * 
     * 
     * @since 1.3
     */
    public void removeAll() {
        replace(0, getViewCount(), null);
    }

    /**
     * Removes one of the children at the given position.
     * This is a convenience call to <code>replace</code>.
     * <p>
     *  删除指定位置的其中一个孩子。这是对<code> replace </code>的方便调用。
     * 
     * 
     * @since 1.3
     */
    public void remove(int i) {
        replace(i, 1, null);
    }

    /**
     * Inserts a single child view.  This is a convenience
     * call to <code>replace</code>.
     *
     * <p>
     *  插入单个子视图。这是对<code> replace </code>的方便调用。
     * 
     * 
     * @param offs the offset of the view to insert before &gt;= 0
     * @param v the view
     * @see #replace
     * @since 1.3
     */
    public void insert(int offs, View v) {
        View[] one = new View[1];
        one[0] = v;
        replace(offs, 0, one);
    }

    /**
     * Appends a single child view.  This is a convenience
     * call to <code>replace</code>.
     *
     * <p>
     *  追加单个子视图。这是对<code> replace </code>的方便调用。
     * 
     * 
     * @param v the view
     * @see #replace
     * @since 1.3
     */
    public void append(View v) {
        View[] one = new View[1];
        one[0] = v;
        replace(getViewCount(), 0, one);
    }

    /**
     * Replaces child views.  If there are no views to remove
     * this acts as an insert.  If there are no views to
     * add this acts as a remove.  Views being removed will
     * have the parent set to <code>null</code>, and the internal reference
     * to them removed so that they can be garbage collected.
     * This is implemented to do nothing, because by default
     * a view has no children.
     *
     * <p>
     *  替换子视图。如果没有视图要删除,则作为插入。如果没有视图添加这个行为作为删除。要删除的视图将父设置为<code> null </code>,并且删除它们的内部引用,以便可以对其进行垃圾回收。
     * 这被实现为什么也不做,因为默认情况下视图没有孩子。
     * 
     * 
     * @param offset the starting index into the child views to insert
     *   the new views.  This should be a value &gt;= 0 and &lt;= getViewCount
     * @param length the number of existing child views to remove
     *   This should be a value &gt;= 0 and &lt;= (getViewCount() - offset).
     * @param views the child views to add.  This value can be
     *   <code>null</code> to indicate no children are being added
     *   (useful to remove).
     * @since 1.3
     */
    public void replace(int offset, int length, View[] views) {
    }

    /**
     * Returns the child view index representing the given position in
     * the model.  By default a view has no children so this is implemented
     * to return -1 to indicate there is no valid child index for any
     * position.
     *
     * <p>
     *  返回表示模型中给定位置的子视图索引。默认情况下,一个视图没有子对象,因此实现返回-1表示没有任何有效的子索引。
     * 
     * 
     * @param pos the position &gt;= 0
     * @return  index of the view representing the given position, or
     *   -1 if no view represents that position
     * @since 1.3
     */
    public int getViewIndex(int pos, Position.Bias b) {
        return -1;
    }

    /**
     * Fetches the allocation for the given child view.
     * This enables finding out where various views
     * are located, without assuming how the views store
     * their location.  This returns <code>null</code> since the
     * default is to not have any child views.
     *
     * <p>
     *  获取给定子视图的分配。这使得能够找出各种视图位于何处,而不必假定视图如何存储它们的位置。这返回<code> null </code>,因为默认是没有任何子视图。
     * 
     * 
     * @param index the index of the child, &gt;= 0 &amp;&amp; &lt;
     *          <code>getViewCount()</code>
     * @param a  the allocation to this view
     * @return the allocation to the child
     */
    public Shape getChildAllocation(int index, Shape a) {
        return null;
    }

    /**
     * Provides a way to determine the next visually represented model
     * location at which one might place a caret.
     * Some views may not be visible,
     * they might not be in the same order found in the model, or they just
     * might not allow access to some of the locations in the model.
     * This method enables specifying a position to convert
     * within the range of &gt;=0.  If the value is -1, a position
     * will be calculated automatically.  If the value &lt; -1,
     * the {@code BadLocationException} will be thrown.
     *
     * <p>
     * 提供一种方法来确定下一个可视地表示的模型位置,在该位置处可以放置插入符号。某些视图可能不可见,它们可能不是在模型中找到的相同顺序,或者它们可能不允许访问模型中的一些位置。
     * 该方法使得能够指定在> = 0的范围内转换的位置。如果值为-1,将自动计算位置。如果值&lt; -1,将抛出{@code BadLocationException}。
     * 
     * 
     * @param pos the position to convert
     * @param a the allocated region in which to render
     * @param direction the direction from the current position that can
     *  be thought of as the arrow keys typically found on a keyboard.
     *  This will be one of the following values:
     * <ul>
     * <li>SwingConstants.WEST
     * <li>SwingConstants.EAST
     * <li>SwingConstants.NORTH
     * <li>SwingConstants.SOUTH
     * </ul>
     * @return the location within the model that best represents the next
     *  location visual position
     * @exception BadLocationException the given position is not a valid
     *                                 position within the document
     * @exception IllegalArgumentException if <code>direction</code>
     *          doesn't have one of the legal values above
     */
    public int getNextVisualPositionFrom(int pos, Position.Bias b, Shape a,
                                         int direction, Position.Bias[] biasRet)
      throws BadLocationException {
        if (pos < -1) {
            // -1 is a reserved value, see the code below
            throw new BadLocationException("Invalid position", pos);
        }

        biasRet[0] = Position.Bias.Forward;
        switch (direction) {
        case NORTH:
        case SOUTH:
        {
            if (pos == -1) {
                pos = (direction == NORTH) ? Math.max(0, getEndOffset() - 1) :
                    getStartOffset();
                break;
            }
            JTextComponent target = (JTextComponent) getContainer();
            Caret c = (target != null) ? target.getCaret() : null;
            // YECK! Ideally, the x location from the magic caret position
            // would be passed in.
            Point mcp;
            if (c != null) {
                mcp = c.getMagicCaretPosition();
            }
            else {
                mcp = null;
            }
            int x;
            if (mcp == null) {
                Rectangle loc = target.modelToView(pos);
                x = (loc == null) ? 0 : loc.x;
            }
            else {
                x = mcp.x;
            }
            if (direction == NORTH) {
                pos = Utilities.getPositionAbove(target, pos, x);
            }
            else {
                pos = Utilities.getPositionBelow(target, pos, x);
            }
        }
            break;
        case WEST:
            if(pos == -1) {
                pos = Math.max(0, getEndOffset() - 1);
            }
            else {
                pos = Math.max(0, pos - 1);
            }
            break;
        case EAST:
            if(pos == -1) {
                pos = getStartOffset();
            }
            else {
                pos = Math.min(pos + 1, getDocument().getLength());
            }
            break;
        default:
            throw new IllegalArgumentException("Bad direction: " + direction);
        }
        return pos;
    }

    /**
     * Provides a mapping, for a given character,
     * from the document model coordinate space
     * to the view coordinate space.
     *
     * <p>
     *  提供给定字符从文档模型坐标空间到视图坐标空间的映射。
     * 
     * 
     * @param pos the position of the desired character (&gt;=0)
     * @param a the area of the view, which encompasses the requested character
     * @param b the bias toward the previous character or the
     *  next character represented by the offset, in case the
     *  position is a boundary of two views; <code>b</code> will have one
     *  of these values:
     * <ul>
     * <li> <code>Position.Bias.Forward</code>
     * <li> <code>Position.Bias.Backward</code>
     * </ul>
     * @return the bounding box, in view coordinate space,
     *          of the character at the specified position
     * @exception BadLocationException  if the specified position does
     *   not represent a valid location in the associated document
     * @exception IllegalArgumentException if <code>b</code> is not one of the
     *          legal <code>Position.Bias</code> values listed above
     * @see View#viewToModel
     */
    public abstract Shape modelToView(int pos, Shape a, Position.Bias b) throws BadLocationException;

    /**
     * Provides a mapping, for a given region,
     * from the document model coordinate space
     * to the view coordinate space. The specified region is
     * created as a union of the first and last character positions.
     *
     * <p>
     *  提供给定区域从文档模型坐标空间到视图坐标空间的映射。指定的区域被创建为第一个和最后一个字符位置的联合。
     * 
     * 
     * @param p0 the position of the first character (&gt;=0)
     * @param b0 the bias of the first character position,
     *  toward the previous character or the
     *  next character represented by the offset, in case the
     *  position is a boundary of two views; <code>b0</code> will have one
     *  of these values:
     * <ul style="list-style-type:none">
     * <li> <code>Position.Bias.Forward</code>
     * <li> <code>Position.Bias.Backward</code>
     * </ul>
     * @param p1 the position of the last character (&gt;=0)
     * @param b1 the bias for the second character position, defined
     *          one of the legal values shown above
     * @param a the area of the view, which encompasses the requested region
     * @return the bounding box which is a union of the region specified
     *          by the first and last character positions
     * @exception BadLocationException  if the given position does
     *   not represent a valid location in the associated document
     * @exception IllegalArgumentException if <code>b0</code> or
     *          <code>b1</code> are not one of the
     *          legal <code>Position.Bias</code> values listed above
     * @see View#viewToModel
     */
    public Shape modelToView(int p0, Position.Bias b0, int p1, Position.Bias b1, Shape a) throws BadLocationException {
        Shape s0 = modelToView(p0, a, b0);
        Shape s1;
        if (p1 == getEndOffset()) {
            try {
                s1 = modelToView(p1, a, b1);
            } catch (BadLocationException ble) {
                s1 = null;
            }
            if (s1 == null) {
                // Assume extends left to right.
                Rectangle alloc = (a instanceof Rectangle) ? (Rectangle)a :
                                  a.getBounds();
                s1 = new Rectangle(alloc.x + alloc.width - 1, alloc.y,
                                   1, alloc.height);
            }
        }
        else {
            s1 = modelToView(p1, a, b1);
        }
        Rectangle r0 = s0.getBounds();
        Rectangle r1 = (s1 instanceof Rectangle) ? (Rectangle) s1 :
                                                   s1.getBounds();
        if (r0.y != r1.y) {
            // If it spans lines, force it to be the width of the view.
            Rectangle alloc = (a instanceof Rectangle) ? (Rectangle)a :
                              a.getBounds();
            r0.x = alloc.x;
            r0.width = alloc.width;
        }
        r0.add(r1);
        return r0;
    }

    /**
     * Provides a mapping from the view coordinate space to the logical
     * coordinate space of the model.  The <code>biasReturn</code>
     * argument will be filled in to indicate that the point given is
     * closer to the next character in the model or the previous
     * character in the model.
     *
     * <p>
     *  提供从视图坐标空间到模型的逻辑坐标空间的映射。 <code> biasReturn </code>参数将被填充以表示给定的点更接近模型中的下一个字符或模型中的上一个字符。
     * 
     * 
     * @param x the X coordinate &gt;= 0
     * @param y the Y coordinate &gt;= 0
     * @param a the allocated region in which to render
     * @return the location within the model that best represents the
     *  given point in the view &gt;= 0.  The <code>biasReturn</code>
     *  argument will be
     * filled in to indicate that the point given is closer to the next
     * character in the model or the previous character in the model.
     */
    public abstract int viewToModel(float x, float y, Shape a, Position.Bias[] biasReturn);

    /**
     * Gives notification that something was inserted into
     * the document in a location that this view is responsible for.
     * To reduce the burden to subclasses, this functionality is
     * spread out into the following calls that subclasses can
     * reimplement:
     * <ol>
     * <li>{@link #updateChildren updateChildren} is called
     * if there were any changes to the element this view is
     * responsible for.  If this view has child views that are
     * represent the child elements, then this method should do
     * whatever is necessary to make sure the child views correctly
     * represent the model.
     * <li>{@link #forwardUpdate forwardUpdate} is called
     * to forward the DocumentEvent to the appropriate child views.
     * <li>{@link #updateLayout updateLayout} is called to
     * give the view a chance to either repair its layout, to reschedule
     * layout, or do nothing.
     * </ol>
     *
     * <p>
     *  提供通知,说明在此数据视图负责的位置,文档中插入了某些内容。为了减少子类的负担,此功能分散到以下调用中,子类可以重新实现：
     * <ol>
     * <li>如果此视图负责的元素有任何更改,则调用{@ link #updateChildren updateChildren}。
     * 如果此视图具有表示子元素的子视图,则此方法应执行任何必要的操作,以确保子视图正确地表示模型。
     *  <li>调用{@ link #forwardUpdate forwardUpdate}将DocumentEvent转发到相应的子视图。
     *  <li>调用{@ link #updateLayout updateLayout}可让视图修复其布局,重新安排布局或不执行任何操作。
     * </ol>
     * 
     * 
     * @param e the change information from the associated document
     * @param a the current allocation of the view
     * @param f the factory to use to rebuild if the view has children
     * @see View#insertUpdate
     */
    public void insertUpdate(DocumentEvent e, Shape a, ViewFactory f) {
        if (getViewCount() > 0) {
            Element elem = getElement();
            DocumentEvent.ElementChange ec = e.getChange(elem);
            if (ec != null) {
                if (! updateChildren(ec, e, f)) {
                    // don't consider the element changes they
                    // are for a view further down.
                    ec = null;
                }
            }
            forwardUpdate(ec, e, a, f);
            updateLayout(ec, e, a);
        }
    }

    /**
     * Gives notification that something was removed from the document
     * in a location that this view is responsible for.
     * To reduce the burden to subclasses, this functionality is
     * spread out into the following calls that subclasses can
     * reimplement:
     * <ol>
     * <li>{@link #updateChildren updateChildren} is called
     * if there were any changes to the element this view is
     * responsible for.  If this view has child views that are
     * represent the child elements, then this method should do
     * whatever is necessary to make sure the child views correctly
     * represent the model.
     * <li>{@link #forwardUpdate forwardUpdate} is called
     * to forward the DocumentEvent to the appropriate child views.
     * <li>{@link #updateLayout updateLayout} is called to
     * give the view a chance to either repair its layout, to reschedule
     * layout, or do nothing.
     * </ol>
     *
     * <p>
     *  提供通知,说明该视图负责的位置中的文档被删除了。为了减少子类的负担,此功能分散到以下调用中,子类可以重新实现：
     * <ol>
     *  <li>如果此视图负责的元素有任何更改,则调用{@ link #updateChildren updateChildren}。
     * 如果此视图具有表示子元素的子视图,则此方法应执行任何必要的操作,以确保子视图正确地表示模型。
     *  <li>调用{@ link #forwardUpdate forwardUpdate}将DocumentEvent转发到相应的子视图。
     *  <li>调用{@ link #updateLayout updateLayout}可让视图修复其布局,重新安排布局或不执行任何操作。
     * </ol>
     * 
     * 
     * @param e the change information from the associated document
     * @param a the current allocation of the view
     * @param f the factory to use to rebuild if the view has children
     * @see View#removeUpdate
     */
    public void removeUpdate(DocumentEvent e, Shape a, ViewFactory f) {
        if (getViewCount() > 0) {
            Element elem = getElement();
            DocumentEvent.ElementChange ec = e.getChange(elem);
            if (ec != null) {
                if (! updateChildren(ec, e, f)) {
                    // don't consider the element changes they
                    // are for a view further down.
                    ec = null;
                }
            }
            forwardUpdate(ec, e, a, f);
            updateLayout(ec, e, a);
        }
    }

    /**
     * Gives notification from the document that attributes were changed
     * in a location that this view is responsible for.
     * To reduce the burden to subclasses, this functionality is
     * spread out into the following calls that subclasses can
     * reimplement:
     * <ol>
     * <li>{@link #updateChildren updateChildren} is called
     * if there were any changes to the element this view is
     * responsible for.  If this view has child views that are
     * represent the child elements, then this method should do
     * whatever is necessary to make sure the child views correctly
     * represent the model.
     * <li>{@link #forwardUpdate forwardUpdate} is called
     * to forward the DocumentEvent to the appropriate child views.
     * <li>{@link #updateLayout updateLayout} is called to
     * give the view a chance to either repair its layout, to reschedule
     * layout, or do nothing.
     * </ol>
     *
     * <p>
     * 从文档中提供属性在此视图负责的位置中更改的通知。为了减少子类的负担,此功能分散到以下调用中,子类可以重新实现：
     * <ol>
     *  <li>如果此视图负责的元素有任何更改,则调用{@ link #updateChildren updateChildren}。
     * 如果此视图具有表示子元素的子视图,则此方法应执行任何必要的操作,以确保子视图正确地表示模型。
     *  <li>调用{@ link #forwardUpdate forwardUpdate}将DocumentEvent转发到相应的子视图。
     *  <li>调用{@ link #updateLayout updateLayout}可让视图修复其布局,重新安排布局或不执行任何操作。
     * </ol>
     * 
     * 
     * @param e the change information from the associated document
     * @param a the current allocation of the view
     * @param f the factory to use to rebuild if the view has children
     * @see View#changedUpdate
     */
    public void changedUpdate(DocumentEvent e, Shape a, ViewFactory f) {
        if (getViewCount() > 0) {
            Element elem = getElement();
            DocumentEvent.ElementChange ec = e.getChange(elem);
            if (ec != null) {
                if (! updateChildren(ec, e, f)) {
                    // don't consider the element changes they
                    // are for a view further down.
                    ec = null;
                }
            }
            forwardUpdate(ec, e, a, f);
            updateLayout(ec, e, a);
        }
    }

    /**
     * Fetches the model associated with the view.
     *
     * <p>
     *  获取与视图相关联的模型。
     * 
     * 
     * @return the view model, <code>null</code> if none
     * @see View#getDocument
     */
    public Document getDocument() {
        return elem.getDocument();
    }

    /**
     * Fetches the portion of the model for which this view is
     * responsible.
     *
     * <p>
     *  获取此视图所负责的模型部分。
     * 
     * 
     * @return the starting offset into the model &gt;= 0
     * @see View#getStartOffset
     */
    public int getStartOffset() {
        return elem.getStartOffset();
    }

    /**
     * Fetches the portion of the model for which this view is
     * responsible.
     *
     * <p>
     *  获取此视图所负责的模型部分。
     * 
     * 
     * @return the ending offset into the model &gt;= 0
     * @see View#getEndOffset
     */
    public int getEndOffset() {
        return elem.getEndOffset();
    }

    /**
     * Fetches the structural portion of the subject that this
     * view is mapped to.  The view may not be responsible for the
     * entire portion of the element.
     *
     * <p>
     *  获取此视图映射到的主题的结构部分。视图可能不负责元素的整个部分。
     * 
     * 
     * @return the subject
     * @see View#getElement
     */
    public Element getElement() {
        return elem;
    }

    /**
     * Fetch a <code>Graphics</code> for rendering.
     * This can be used to determine
     * font characteristics, and will be different for a print view
     * than a component view.
     *
     * <p>
     *  获取<code> Graphics </code>以进行渲染。这可以用于确定字体特征,并且对于打印视图而不是组件视图将是不同的。
     * 
     * 
     * @return a <code>Graphics</code> object for rendering
     * @since 1.3
     */
    public Graphics getGraphics() {
        // PENDING(prinz) this is a temporary implementation
        Component c = getContainer();
        return c.getGraphics();
    }

    /**
     * Fetches the attributes to use when rendering.  By default
     * this simply returns the attributes of the associated element.
     * This method should be used rather than using the element
     * directly to obtain access to the attributes to allow
     * view-specific attributes to be mixed in or to allow the
     * view to have view-specific conversion of attributes by
     * subclasses.
     * Each view should document what attributes it recognizes
     * for the purpose of rendering or layout, and should always
     * access them through the <code>AttributeSet</code> returned
     * by this method.
     * <p>
     * 获取渲染时要使用的属性。默认情况下,这只返回相关元素的属性。应该使用此方法,而不是直接使用元素来获取对属性的访问,以允许视图特定属性混合或允许视图通过子类具有视图特定的属性转换。
     * 每个视图应该记录它为渲染或布局的目的识别什么属性,并且应该总是通过此方法返回的<code> AttributeSet </code>访问它们。
     * 
     */
    public AttributeSet getAttributes() {
        return elem.getAttributes();
    }

    /**
     * Tries to break this view on the given axis.  This is
     * called by views that try to do formatting of their
     * children.  For example, a view of a paragraph will
     * typically try to place its children into row and
     * views representing chunks of text can sometimes be
     * broken down into smaller pieces.
     * <p>
     * This is implemented to return the view itself, which
     * represents the default behavior on not being
     * breakable.  If the view does support breaking, the
     * starting offset of the view returned should be the
     * given offset, and the end offset should be less than
     * or equal to the end offset of the view being broken.
     *
     * <p>
     *  尝试在给定轴上断开此视图。这是通过尝试对他们的孩子进行格式化的视图调用的。例如,段落的视图通常将尝试将其孩子放置在行中,并且表示文本块的视图有时可以被分解成较小的块。
     * <p>
     *  这被实现以返回视图本身,其表示不可破坏的默认行为。如果视图支持断开,则返回的视图的起始偏移应该是给定的偏移,并且结束偏移应当小于或等于被中断的视图的结束偏移。
     * 
     * 
     * @param axis may be either <code>View.X_AXIS</code> or
     *          <code>View.Y_AXIS</code>
     * @param offset the location in the document model
     *   that a broken fragment would occupy &gt;= 0.  This
     *   would be the starting offset of the fragment
     *   returned
     * @param pos the position along the axis that the
     *  broken view would occupy &gt;= 0.  This may be useful for
     *  things like tab calculations
     * @param len specifies the distance along the axis
     *  where a potential break is desired &gt;= 0
     * @return the fragment of the view that represents the
     *  given span, if the view can be broken.  If the view
     *  doesn't support breaking behavior, the view itself is
     *  returned.
     * @see ParagraphView
     */
    public View breakView(int axis, int offset, float pos, float len) {
        return this;
    }

    /**
     * Creates a view that represents a portion of the element.
     * This is potentially useful during formatting operations
     * for taking measurements of fragments of the view.  If
     * the view doesn't support fragmenting (the default), it
     * should return itself.
     *
     * <p>
     *  创建表示元素的一部分的视图。这在用于对视图的片段进行测量的格式化操作期间潜在地有用。如果视图不支持分段(默认),它应该返回自身。
     * 
     * 
     * @param p0 the starting offset &gt;= 0.  This should be a value
     *   greater or equal to the element starting offset and
     *   less than the element ending offset.
     * @param p1 the ending offset &gt; p0.  This should be a value
     *   less than or equal to the elements end offset and
     *   greater than the elements starting offset.
     * @return the view fragment, or itself if the view doesn't
     *   support breaking into fragments
     * @see LabelView
     */
    public View createFragment(int p0, int p1) {
        return this;
    }

    /**
     * Determines how attractive a break opportunity in
     * this view is.  This can be used for determining which
     * view is the most attractive to call <code>breakView</code>
     * on in the process of formatting.  A view that represents
     * text that has whitespace in it might be more attractive
     * than a view that has no whitespace, for example.  The
     * higher the weight, the more attractive the break.  A
     * value equal to or lower than <code>BadBreakWeight</code>
     * should not be considered for a break.  A value greater
     * than or equal to <code>ForcedBreakWeight</code> should
     * be broken.
     * <p>
     * This is implemented to provide the default behavior
     * of returning <code>BadBreakWeight</code> unless the length
     * is greater than the length of the view in which case the
     * entire view represents the fragment.  Unless a view has
     * been written to support breaking behavior, it is not
     * attractive to try and break the view.  An example of
     * a view that does support breaking is <code>LabelView</code>.
     * An example of a view that uses break weight is
     * <code>ParagraphView</code>.
     *
     * <p>
     * 确定此视图中的休息机会的吸引力。这可以用于确定哪个视图是最有吸引力的在格式化过程中调用<code> breakView </code>。
     * 例如,表示其中具有空格的文本的视图可能比没有空格的视图更有吸引力。重量越高,断裂越有吸引力。等于或小于<code> BadBreakWeight </code>的值不应被视为中断。
     * 大于或等于<code> ForcedBreakWeight </code>的值应该被破坏。
     * <p>
     *  这被实现以提供返回<code> BadBreakWeight </code>的默认行为,除非长度大于视图的长度,在这种情况下整个视图表示片段。
     * 除非一个视图被写成支持破坏行为,尝试和破坏视图是没有吸引力的。支持断开的视图的示例是<code> LabelView </code>。
     * 使用break权重的视图的示例是<code> ParagraphView </code>。
     * 
     * 
     * @param axis may be either <code>View.X_AXIS</code> or
     *          <code>View.Y_AXIS</code>
     * @param pos the potential location of the start of the
     *   broken view &gt;= 0.  This may be useful for calculating tab
     *   positions
     * @param len specifies the relative length from <em>pos</em>
     *   where a potential break is desired &gt;= 0
     * @return the weight, which should be a value between
     *   ForcedBreakWeight and BadBreakWeight
     * @see LabelView
     * @see ParagraphView
     * @see #BadBreakWeight
     * @see #GoodBreakWeight
     * @see #ExcellentBreakWeight
     * @see #ForcedBreakWeight
     */
    public int getBreakWeight(int axis, float pos, float len) {
        if (len > getPreferredSpan(axis)) {
            return GoodBreakWeight;
        }
        return BadBreakWeight;
    }

    /**
     * Determines the resizability of the view along the
     * given axis.  A value of 0 or less is not resizable.
     *
     * <p>
     *  确定沿给定轴的视图的可重新调整性。值为0或更小不可调整大小。
     * 
     * 
     * @param axis may be either <code>View.X_AXIS</code> or
     *          <code>View.Y_AXIS</code>
     * @return the weight
     */
    public int getResizeWeight(int axis) {
        return 0;
    }

    /**
     * Sets the size of the view.  This should cause
     * layout of the view along the given axis, if it
     * has any layout duties.
     *
     * <p>
     *  设置视图的大小。这应该导致沿给定轴的视图的布局,如果它有任何布局职责。
     * 
     * 
     * @param width the width &gt;= 0
     * @param height the height &gt;= 0
     */
    public void setSize(float width, float height) {
    }

    /**
     * Fetches the container hosting the view.  This is useful for
     * things like scheduling a repaint, finding out the host
     * components font, etc.  The default implementation
     * of this is to forward the query to the parent view.
     *
     * <p>
     *  获取托管视图的容器。这对于调度重绘,查找主机组件字体等事情非常有用。默认实现是将查询转发到父视图。
     * 
     * 
     * @return the container, <code>null</code> if none
     */
    public Container getContainer() {
        View v = getParent();
        return (v != null) ? v.getContainer() : null;
    }

    /**
     * Fetches the <code>ViewFactory</code> implementation that is feeding
     * the view hierarchy.  Normally the views are given this
     * as an argument to updates from the model when they
     * are most likely to need the factory, but this
     * method serves to provide it at other times.
     *
     * <p>
     * 获取提供视图层次结构的<code> ViewFactory </code>实现。通常,视图被赋予这个作为参数,当模型最可能需要工厂时,从模型更新,但这种方法用于在其他时间提供它。
     * 
     * 
     * @return the factory, <code>null</code> if none
     */
    public ViewFactory getViewFactory() {
        View v = getParent();
        return (v != null) ? v.getViewFactory() : null;
    }

    /**
     * Returns the tooltip text at the specified location. The default
     * implementation returns the value from the child View identified by
     * the passed in location.
     *
     * <p>
     *  返回指定位置处的工具提示文本。默认实现从由传递的位置标识的子视图返回值。
     * 
     * 
     * @since 1.4
     * @see JTextComponent#getToolTipText
     */
    public String getToolTipText(float x, float y, Shape allocation) {
        int viewIndex = getViewIndex(x, y, allocation);
        if (viewIndex >= 0) {
            allocation = getChildAllocation(viewIndex, allocation);
            Rectangle rect = (allocation instanceof Rectangle) ?
                             (Rectangle)allocation : allocation.getBounds();
            if (rect.contains(x, y)) {
                return getView(viewIndex).getToolTipText(x, y, allocation);
            }
        }
        return null;
    }

    /**
     * Returns the child view index representing the given position in
     * the view. This iterates over all the children returning the
     * first with a bounds that contains <code>x</code>, <code>y</code>.
     *
     * <p>
     *  返回表示视图中给定位置的子视图索引。这遍历所有返回第一个的子类,其中包含<code> x </code>,<code> y </code>。
     * 
     * 
     * @param x the x coordinate
     * @param y the y coordinate
     * @param allocation current allocation of the View.
     * @return  index of the view representing the given location, or
     *   -1 if no view represents that position
     * @since 1.4
     */
    public int getViewIndex(float x, float y, Shape allocation) {
        for (int counter = getViewCount() - 1; counter >= 0; counter--) {
            Shape childAllocation = getChildAllocation(counter, allocation);

            if (childAllocation != null) {
                Rectangle rect = (childAllocation instanceof Rectangle) ?
                         (Rectangle)childAllocation : childAllocation.getBounds();

                if (rect.contains(x, y)) {
                    return counter;
                }
            }
        }
        return -1;
    }

    /**
     * Updates the child views in response to receiving notification
     * that the model changed, and there is change record for the
     * element this view is responsible for.  This is implemented
     * to assume the child views are directly responsible for the
     * child elements of the element this view represents.  The
     * <code>ViewFactory</code> is used to create child views for each element
     * specified as added in the <code>ElementChange</code>, starting at the
     * index specified in the given <code>ElementChange</code>.  The number of
     * child views representing the removed elements specified are
     * removed.
     *
     * <p>
     *  响应接收到模型更改的通知,更新子视图,并且此视图负责的元素有更改记录。这被实现以假定子视图直接负责该视图表示的元素的子元素。
     *  <code> ViewFactory </code>用于为指定为<code> ElementChange </code>中添加的每个元素创建子视图,从给定的<code> ElementChange </code>
     * 中指定的索引开始。
     *  响应接收到模型更改的通知,更新子视图,并且此视图负责的元素有更改记录。这被实现以假定子视图直接负责该视图表示的元素的子元素。删除表示已删除的指定元素的子视图的数量。
     * 
     * 
     * @param ec the change information for the element this view
     *  is responsible for.  This should not be <code>null</code> if
     *  this method gets called
     * @param e the change information from the associated document
     * @param f the factory to use to build child views
     * @return whether or not the child views represent the
     *  child elements of the element this view is responsible
     *  for.  Some views create children that represent a portion
     *  of the element they are responsible for, and should return
     *  false.  This information is used to determine if views
     *  in the range of the added elements should be forwarded to
     *  or not
     * @see #insertUpdate
     * @see #removeUpdate
     * @see #changedUpdate
     * @since 1.3
     */
    protected boolean updateChildren(DocumentEvent.ElementChange ec,
                                         DocumentEvent e, ViewFactory f) {
        Element[] removedElems = ec.getChildrenRemoved();
        Element[] addedElems = ec.getChildrenAdded();
        View[] added = null;
        if (addedElems != null) {
            added = new View[addedElems.length];
            for (int i = 0; i < addedElems.length; i++) {
                added[i] = f.create(addedElems[i]);
            }
        }
        int nremoved = 0;
        int index = ec.getIndex();
        if (removedElems != null) {
            nremoved = removedElems.length;
        }
        replace(index, nremoved, added);
        return true;
    }

    /**
     * Forwards the given <code>DocumentEvent</code> to the child views
     * that need to be notified of the change to the model.
     * If there were changes to the element this view is
     * responsible for, that should be considered when
     * forwarding (i.e. new child views should not get
     * notified).
     *
     * <p>
     *  将给定的<code> DocumentEvent </code>转发给需要通知模型更改的子视图。如果这个视图负责的元素有变化,那么在转发时应该考虑(即,不应该通知新的子视图)。
     * 
     * 
     * @param ec changes to the element this view is responsible
     *  for (may be <code>null</code> if there were no changes).
     * @param e the change information from the associated document
     * @param a the current allocation of the view
     * @param f the factory to use to rebuild if the view has children
     * @see #insertUpdate
     * @see #removeUpdate
     * @see #changedUpdate
     * @since 1.3
     */
    protected void forwardUpdate(DocumentEvent.ElementChange ec,
                                      DocumentEvent e, Shape a, ViewFactory f) {
        calculateUpdateIndexes(e);

        int hole0 = lastUpdateIndex + 1;
        int hole1 = hole0;
        Element[] addedElems = (ec != null) ? ec.getChildrenAdded() : null;
        if ((addedElems != null) && (addedElems.length > 0)) {
            hole0 = ec.getIndex();
            hole1 = hole0 + addedElems.length - 1;
        }

        // forward to any view not in the forwarding hole
        // formed by added elements (i.e. they will be updated
        // by initialization.
        for (int i = firstUpdateIndex; i <= lastUpdateIndex; i++) {
            if (! ((i >= hole0) && (i <= hole1))) {
                View v = getView(i);
                if (v != null) {
                    Shape childAlloc = getChildAllocation(i, a);
                    forwardUpdateToView(v, e, childAlloc, f);
                }
            }
        }
    }

    /**
     * Calculates the first and the last indexes of the child views
     * that need to be notified of the change to the model.
     * <p>
     * 计算需要通知模型更改的子视图的第一个和最后一个索引。
     * 
     * 
     * @param e the change information from the associated document
     */
    void calculateUpdateIndexes(DocumentEvent e) {
        int pos = e.getOffset();
        firstUpdateIndex = getViewIndex(pos, Position.Bias.Forward);
        if (firstUpdateIndex == -1 && e.getType() == DocumentEvent.EventType.REMOVE &&
            pos >= getEndOffset()) {
            // Event beyond our offsets. We may have represented this, that is
            // the remove may have removed one of our child Elements that
            // represented this, so, we should forward to last element.
            firstUpdateIndex = getViewCount() - 1;
        }
        lastUpdateIndex = firstUpdateIndex;
        View v = (firstUpdateIndex >= 0) ? getView(firstUpdateIndex) : null;
        if (v != null) {
            if ((v.getStartOffset() == pos) && (pos > 0)) {
                // If v is at a boundary, forward the event to the previous
                // view too.
                firstUpdateIndex = Math.max(firstUpdateIndex - 1, 0);
            }
        }
        if (e.getType() != DocumentEvent.EventType.REMOVE) {
            lastUpdateIndex = getViewIndex(pos + e.getLength(), Position.Bias.Forward);
            if (lastUpdateIndex < 0) {
                lastUpdateIndex = getViewCount() - 1;
            }
        }
        firstUpdateIndex = Math.max(firstUpdateIndex, 0);
    }

    /**
     * Updates the view to reflect the changes.
     * <p>
     *  更新视图以反映更改。
     * 
     */
    void updateAfterChange() {
        // Do nothing by default. Should be overridden in subclasses, if any.
    }

    /**
     * Forwards the <code>DocumentEvent</code> to the give child view.  This
     * simply messages the view with a call to <code>insertUpdate</code>,
     * <code>removeUpdate</code>, or <code>changedUpdate</code> depending
     * upon the type of the event.  This is called by
     * {@link #forwardUpdate forwardUpdate} to forward
     * the event to children that need it.
     *
     * <p>
     *  将<code> DocumentEvent </code>转发给给定子视图。
     * 这只是根据事件的类型调用<code> insertUpdate </code>,<code> removeUpdate </code>或<code> changedUpdate </code>这是由{@link #forwardUpdate forwardUpdate}
     * 调用,以将该事件转发给需要它的儿童。
     *  将<code> DocumentEvent </code>转发给给定子视图。
     * 
     * 
     * @param v the child view to forward the event to
     * @param e the change information from the associated document
     * @param a the current allocation of the view
     * @param f the factory to use to rebuild if the view has children
     * @see #forwardUpdate
     * @since 1.3
     */
    protected void forwardUpdateToView(View v, DocumentEvent e,
                                           Shape a, ViewFactory f) {
        DocumentEvent.EventType type = e.getType();
        if (type == DocumentEvent.EventType.INSERT) {
            v.insertUpdate(e, a, f);
        } else if (type == DocumentEvent.EventType.REMOVE) {
            v.removeUpdate(e, a, f);
        } else {
            v.changedUpdate(e, a, f);
        }
    }

    /**
     * Updates the layout in response to receiving notification of
     * change from the model.  This is implemented to call
     * <code>preferenceChanged</code> to reschedule a new layout
     * if the <code>ElementChange</code> record is not <code>null</code>.
     *
     * <p>
     *  响应从模型接收更改通知更新布局。
     * 如果<code> ElementChange </code>记录不是<code> null </code>,则实现调用<code> preferenceChanged </code>重新安排新布局。
     * 
     * 
     * @param ec changes to the element this view is responsible
     *  for (may be <code>null</code> if there were no changes)
     * @param e the change information from the associated document
     * @param a the current allocation of the view
     * @see #insertUpdate
     * @see #removeUpdate
     * @see #changedUpdate
     * @since 1.3
     */
    protected void updateLayout(DocumentEvent.ElementChange ec,
                                    DocumentEvent e, Shape a) {
        if ((ec != null) && (a != null)) {
            // should damage more intelligently
            preferenceChanged(null, true, true);
            Container host = getContainer();
            if (host != null) {
                host.repaint();
            }
        }
    }

    /**
     * The weight to indicate a view is a bad break
     * opportunity for the purpose of formatting.  This
     * value indicates that no attempt should be made to
     * break the view into fragments as the view has
     * not been written to support fragmenting.
     *
     * <p>
     *  指定视图的权重是格式化目的的坏中断机会。此值指示不应尝试将视图拆分为片段,因为视图尚未写入以支持片段化。
     * 
     * 
     * @see #getBreakWeight
     * @see #GoodBreakWeight
     * @see #ExcellentBreakWeight
     * @see #ForcedBreakWeight
     */
    public static final int BadBreakWeight = 0;

    /**
     * The weight to indicate a view supports breaking,
     * but better opportunities probably exist.
     *
     * <p>
     *  表示视图的权重支持断开,但更好的机会可能存在。
     * 
     * 
     * @see #getBreakWeight
     * @see #BadBreakWeight
     * @see #ExcellentBreakWeight
     * @see #ForcedBreakWeight
     */
    public static final int GoodBreakWeight = 1000;

    /**
     * The weight to indicate a view supports breaking,
     * and this represents a very attractive place to
     * break.
     *
     * <p>
     *  表示视图的重量支持断裂,这代表了一个非常有吸引力的断裂的地方。
     * 
     * 
     * @see #getBreakWeight
     * @see #BadBreakWeight
     * @see #GoodBreakWeight
     * @see #ForcedBreakWeight
     */
    public static final int ExcellentBreakWeight = 2000;

    /**
     * The weight to indicate a view supports breaking,
     * and must be broken to be represented properly
     * when placed in a view that formats its children
     * by breaking them.
     *
     * <p>
     *  指示视图支持断开的权重,并且必须断开以便在放置在通过断开其格式化其子代的视图中时被正确表示。
     * 
     * 
     * @see #getBreakWeight
     * @see #BadBreakWeight
     * @see #GoodBreakWeight
     * @see #ExcellentBreakWeight
     */
    public static final int ForcedBreakWeight = 3000;

    /**
     * Axis for format/break operations.
     * <p>
     *  用于格式化/中断操作的轴。
     * 
     */
    public static final int X_AXIS = HORIZONTAL;

    /**
     * Axis for format/break operations.
     * <p>
     *  用于格式化/中断操作的轴。
     * 
     */
    public static final int Y_AXIS = VERTICAL;

    /**
     * Provides a mapping from the document model coordinate space
     * to the coordinate space of the view mapped to it. This is
     * implemented to default the bias to <code>Position.Bias.Forward</code>
     * which was previously implied.
     *
     * <p>
     * 提供从文档模型坐标空间到映射到其的视图的坐标空间的映射。这被实现为默认偏好为<code> Position.Bias.Forward </code>,这是以前隐含的。
     * 
     * 
     * @param pos the position to convert &gt;= 0
     * @param a the allocated region in which to render
     * @return the bounding box of the given position is returned
     * @exception BadLocationException  if the given position does
     *   not represent a valid location in the associated document
     * @see View#modelToView
     * @deprecated
     */
    @Deprecated
    public Shape modelToView(int pos, Shape a) throws BadLocationException {
        return modelToView(pos, a, Position.Bias.Forward);
    }


    /**
     * Provides a mapping from the view coordinate space to the logical
     * coordinate space of the model.
     *
     * <p>
     *  提供从视图坐标空间到模型的逻辑坐标空间的映射。
     * 
     * 
     * @param x the X coordinate &gt;= 0
     * @param y the Y coordinate &gt;= 0
     * @param a the allocated region in which to render
     * @return the location within the model that best represents the
     *  given point in the view &gt;= 0
     * @see View#viewToModel
     * @deprecated
     */
    @Deprecated
    public int viewToModel(float x, float y, Shape a) {
        sharedBiasReturn[0] = Position.Bias.Forward;
        return viewToModel(x, y, a, sharedBiasReturn);
    }

    // static argument available for viewToModel calls since only
    // one thread at a time may call this method.
    static final Position.Bias[] sharedBiasReturn = new Position.Bias[1];

    private View parent;
    private Element elem;

    /**
     * The index of the first child view to be notified.
     * <p>
     *  要通知的第一个子视图的索引。
     * 
     */
    int firstUpdateIndex;

    /**
     * The index of the last child view to be notified.
     * <p>
     *  要通知的最后一个子视图的索引。
     */
    int lastUpdateIndex;

};
