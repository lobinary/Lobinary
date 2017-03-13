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

package java.awt;

import java.beans.ConstructorProperties;

import java.lang.annotation.Native;

/**
 * The <code>BasicStroke</code> class defines a basic set of rendering
 * attributes for the outlines of graphics primitives, which are rendered
 * with a {@link Graphics2D} object that has its Stroke attribute set to
 * this <code>BasicStroke</code>.
 * The rendering attributes defined by <code>BasicStroke</code> describe
 * the shape of the mark made by a pen drawn along the outline of a
 * {@link Shape} and the decorations applied at the ends and joins of
 * path segments of the <code>Shape</code>.
 * These rendering attributes include:
 * <dl>
 * <dt><i>width</i>
 * <dd>The pen width, measured perpendicularly to the pen trajectory.
 * <dt><i>end caps</i>
 * <dd>The decoration applied to the ends of unclosed subpaths and
 * dash segments.  Subpaths that start and end on the same point are
 * still considered unclosed if they do not have a CLOSE segment.
 * See {@link java.awt.geom.PathIterator#SEG_CLOSE SEG_CLOSE}
 * for more information on the CLOSE segment.
 * The three different decorations are: {@link #CAP_BUTT},
 * {@link #CAP_ROUND}, and {@link #CAP_SQUARE}.
 * <dt><i>line joins</i>
 * <dd>The decoration applied at the intersection of two path segments
 * and at the intersection of the endpoints of a subpath that is closed
 * using {@link java.awt.geom.PathIterator#SEG_CLOSE SEG_CLOSE}.
 * The three different decorations are: {@link #JOIN_BEVEL},
 * {@link #JOIN_MITER}, and {@link #JOIN_ROUND}.
 * <dt><i>miter limit</i>
 * <dd>The limit to trim a line join that has a JOIN_MITER decoration.
 * A line join is trimmed when the ratio of miter length to stroke
 * width is greater than the miterlimit value.  The miter length is
 * the diagonal length of the miter, which is the distance between
 * the inside corner and the outside corner of the intersection.
 * The smaller the angle formed by two line segments, the longer
 * the miter length and the sharper the angle of intersection.  The
 * default miterlimit value of 10.0f causes all angles less than
 * 11 degrees to be trimmed.  Trimming miters converts
 * the decoration of the line join to bevel.
 * <dt><i>dash attributes</i>
 * <dd>The definition of how to make a dash pattern by alternating
 * between opaque and transparent sections.
 * </dl>
 * All attributes that specify measurements and distances controlling
 * the shape of the returned outline are measured in the same
 * coordinate system as the original unstroked <code>Shape</code>
 * argument.  When a <code>Graphics2D</code> object uses a
 * <code>Stroke</code> object to redefine a path during the execution
 * of one of its <code>draw</code> methods, the geometry is supplied
 * in its original form before the <code>Graphics2D</code> transform
 * attribute is applied.  Therefore, attributes such as the pen width
 * are interpreted in the user space coordinate system of the
 * <code>Graphics2D</code> object and are subject to the scaling and
 * shearing effects of the user-space-to-device-space transform in that
 * particular <code>Graphics2D</code>.
 * For example, the width of a rendered shape's outline is determined
 * not only by the width attribute of this <code>BasicStroke</code>,
 * but also by the transform attribute of the
 * <code>Graphics2D</code> object.  Consider this code:
 * <blockquote><tt>
 *      // sets the Graphics2D object's Transform attribute
 *      g2d.scale(10, 10);
 *      // sets the Graphics2D object's Stroke attribute
 *      g2d.setStroke(new BasicStroke(1.5f));
 * </tt></blockquote>
 * Assuming there are no other scaling transforms added to the
 * <code>Graphics2D</code> object, the resulting line
 * will be approximately 15 pixels wide.
 * As the example code demonstrates, a floating-point line
 * offers better precision, especially when large transforms are
 * used with a <code>Graphics2D</code> object.
 * When a line is diagonal, the exact width depends on how the
 * rendering pipeline chooses which pixels to fill as it traces the
 * theoretical widened outline.  The choice of which pixels to turn
 * on is affected by the antialiasing attribute because the
 * antialiasing rendering pipeline can choose to color
 * partially-covered pixels.
 * <p>
 * For more information on the user space coordinate system and the
 * rendering process, see the <code>Graphics2D</code> class comments.
 * <p>
 *  <code> BasicStroke </code>类为图形基元的轮廓定义了一组基本的渲染属性,它们用一个{@link Graphics2D}对象来渲染,该对象的Stroke属性设置为<code> B
 * asicStroke </code >由<code> BasicStroke </code>定义的渲染属性描述了沿着{@link Shape}的轮廓绘制的笔的形状,以及应用于<代码>形状</code>这
 * 些呈现属性包括：。
 * <dl>
 * <dt> <i> width </i> <dd>垂直于笔轨迹测量的笔宽度<d>端盖</i> <dd>应用于未封闭的子路径和破折段如果没有CLOSE段,则在同一点上开始和结束的子路径仍被视为未闭合。
 * 有关CLOSE段的更多信息,请参阅{@link javaawtgeomPathIterator#SEG_CLOSE SEG_CLOSE}三个不同的装饰是：{@link #CAP_BUTT} ,{@link #CAP_ROUND}
 * 和{@link #CAP_SQUARE} <dt>线连接</i> <dd>在两个路径段的交点处以及在子路径的端点的交点处应用的装饰即使用{@link javaawtgeomPathIterator#SEG_CLOSE SEG_CLOSE}
 * 关闭三种不同的装饰是：{@link #JOIN_BEVEL},{@link #JOIN_MITER}和{@link #JOIN_ROUND} <dt> <i>斜接限制</i> <dd>修剪线连接的限制具
 * 有JOIN_MITER装饰当斜角长度与笔画宽度的比率大于miterlimit值时,线连接被修剪。
 * <dt> <i> width </i> <dd>垂直于笔轨迹测量的笔宽度<d>端盖</i> <dd>应用于未封闭的子路径和破折段如果没有CLOSE段,则在同一点上开始和结束的子路径仍被视为未闭合。
 * 斜角长度是斜角的对角线长度,其是交叉点的内角和外角之间的距离两条线段形成的角度越小,斜角越长,交叉角越锐利默认的最小值100f使得所有小于11度的角度被修剪修剪斜角将线连接的装饰转换为斜角<dt> <i>
 *  dash属性</i> <dd>如何通过在不透明和透明部分之间交替来创建虚线图案的定义。
 * <dt> <i> width </i> <dd>垂直于笔轨迹测量的笔宽度<d>端盖</i> <dd>应用于未封闭的子路径和破折段如果没有CLOSE段,则在同一点上开始和结束的子路径仍被视为未闭合。
 * </dl>
 * 所有指定测量值的属性和控制返回轮廓形状的距离都在与原始未纹理<code> Shape </code>参数相同的坐标系中测量。
 * 当<code> Graphics2D </code>对象使用<code> Stroke </code>对象在其<code> draw </code>方法之一的执行期间重新定义路径,在应用<code> G
 * raphics2D </code> transform属性之前,以原始形式提供几何体。
 * 所有指定测量值的属性和控制返回轮廓形状的距离都在与原始未纹理<code> Shape </code>参数相同的坐标系中测量。
 *  ,诸如笔宽度的属性在<code> Graphics2D </code>对象的用户空间坐标系中被解释,并且受到在该特定图像中用户空间到设备空间变换的缩放和剪切效果的影响<code> Graphic2D 
 * </code>例如,渲染形状轮廓的宽度不仅由这个<code> BasicStroke </code>的width属性决定,还由<code> Graphics2D </code>对象的transform属
 * 性决定。
 * 所有指定测量值的属性和控制返回轮廓形状的距离都在与原始未纹理<code> Shape </code>参数相同的坐标系中测量。
 * 考虑这个代码： <blockquote> <tt> //设置Graphics2D对象的Transform属性g2dscale(10,10); //设置Graphics2D对象的Stroke属性g2dse
 * tStroke(new BasicStroke(15f)); </tt> </blockquote>假设没有其他缩放变换添加到<code> Graphics2D </code>对象,生成的线将是大约15
 * 像素宽像示例代码演示,浮点线提供更好精度,特别是当大变换与<code> Graphics2D </code>对象一起使用时当线是对角线时,精确的宽度取决于渲染管线如何选择哪些像素填充,因为它跟踪理论加宽
 * 的轮廓。
 * 所有指定测量值的属性和控制返回轮廓形状的距离都在与原始未纹理<code> Shape </code>参数相同的坐标系中测量。
 * 打开哪些像素的选择受抗锯齿属性的影响,因为抗锯齿渲染管线可以选择颜色部分覆盖的像素。
 * <p>
 * 有关用户空间坐标系统和渲染过程的更多信息,请参阅<code> Graphics2D </code>类注释
 * 
 * 
 * @see Graphics2D
 * @author Jim Graham
 */
public class BasicStroke implements Stroke {

    /**
     * Joins path segments by extending their outside edges until
     * they meet.
     * <p>
     *  通过延伸其外边缘连接路径段,直到它们相遇
     * 
     */
    @Native public final static int JOIN_MITER = 0;

    /**
     * Joins path segments by rounding off the corner at a radius
     * of half the line width.
     * <p>
     *  通过以半角线宽对圆角进行四舍五入来连接路径段
     * 
     */
    @Native public final static int JOIN_ROUND = 1;

    /**
     * Joins path segments by connecting the outer corners of their
     * wide outlines with a straight segment.
     * <p>
     *  通过将其宽轮廓的外角连接到直线段来连接路径段
     * 
     */
    @Native public final static int JOIN_BEVEL = 2;

    /**
     * Ends unclosed subpaths and dash segments with no added
     * decoration.
     * <p>
     *  结束未封闭的子路径和短划线段,不添加装饰
     * 
     */
    @Native public final static int CAP_BUTT = 0;

    /**
     * Ends unclosed subpaths and dash segments with a round
     * decoration that has a radius equal to half of the width
     * of the pen.
     * <p>
     *  以半径等于钢笔宽度的一半的圆形装饰结束未闭合的子路径和虚线段
     * 
     */
    @Native public final static int CAP_ROUND = 1;

    /**
     * Ends unclosed subpaths and dash segments with a square
     * projection that extends beyond the end of the segment
     * to a distance equal to half of the line width.
     * <p>
     *  结束未闭合的子路径和短划线段,其方形投影延伸超过线段的端部到等于线宽一半的距离
     * 
     */
    @Native public final static int CAP_SQUARE = 2;

    float width;

    int join;
    int cap;
    float miterlimit;

    float dash[];
    float dash_phase;

    /**
     * Constructs a new <code>BasicStroke</code> with the specified
     * attributes.
     * <p>
     * 使用指定的属性构造一个新的<code> BasicStroke </code>
     * 
     * 
     * @param width the width of this <code>BasicStroke</code>.  The
     *         width must be greater than or equal to 0.0f.  If width is
     *         set to 0.0f, the stroke is rendered as the thinnest
     *         possible line for the target device and the antialias
     *         hint setting.
     * @param cap the decoration of the ends of a <code>BasicStroke</code>
     * @param join the decoration applied where path segments meet
     * @param miterlimit the limit to trim the miter join.  The miterlimit
     *        must be greater than or equal to 1.0f.
     * @param dash the array representing the dashing pattern
     * @param dash_phase the offset to start the dashing pattern
     * @throws IllegalArgumentException if <code>width</code> is negative
     * @throws IllegalArgumentException if <code>cap</code> is not either
     *         CAP_BUTT, CAP_ROUND or CAP_SQUARE
     * @throws IllegalArgumentException if <code>miterlimit</code> is less
     *         than 1 and <code>join</code> is JOIN_MITER
     * @throws IllegalArgumentException if <code>join</code> is not
     *         either JOIN_ROUND, JOIN_BEVEL, or JOIN_MITER
     * @throws IllegalArgumentException if <code>dash_phase</code>
     *         is negative and <code>dash</code> is not <code>null</code>
     * @throws IllegalArgumentException if the length of
     *         <code>dash</code> is zero
     * @throws IllegalArgumentException if dash lengths are all zero.
     */
    @ConstructorProperties({ "lineWidth", "endCap", "lineJoin", "miterLimit", "dashArray", "dashPhase" })
    public BasicStroke(float width, int cap, int join, float miterlimit,
                       float dash[], float dash_phase) {
        if (width < 0.0f) {
            throw new IllegalArgumentException("negative width");
        }
        if (cap != CAP_BUTT && cap != CAP_ROUND && cap != CAP_SQUARE) {
            throw new IllegalArgumentException("illegal end cap value");
        }
        if (join == JOIN_MITER) {
            if (miterlimit < 1.0f) {
                throw new IllegalArgumentException("miter limit < 1");
            }
        } else if (join != JOIN_ROUND && join != JOIN_BEVEL) {
            throw new IllegalArgumentException("illegal line join value");
        }
        if (dash != null) {
            if (dash_phase < 0.0f) {
                throw new IllegalArgumentException("negative dash phase");
            }
            boolean allzero = true;
            for (int i = 0; i < dash.length; i++) {
                float d = dash[i];
                if (d > 0.0) {
                    allzero = false;
                } else if (d < 0.0) {
                    throw new IllegalArgumentException("negative dash length");
                }
            }
            if (allzero) {
                throw new IllegalArgumentException("dash lengths all zero");
            }
        }
        this.width      = width;
        this.cap        = cap;
        this.join       = join;
        this.miterlimit = miterlimit;
        if (dash != null) {
            this.dash = (float []) dash.clone();
        }
        this.dash_phase = dash_phase;
    }

    /**
     * Constructs a solid <code>BasicStroke</code> with the specified
     * attributes.
     * <p>
     *  构造一个具有指定属性的实体<code> BasicStroke </code>
     * 
     * 
     * @param width the width of the <code>BasicStroke</code>
     * @param cap the decoration of the ends of a <code>BasicStroke</code>
     * @param join the decoration applied where path segments meet
     * @param miterlimit the limit to trim the miter join
     * @throws IllegalArgumentException if <code>width</code> is negative
     * @throws IllegalArgumentException if <code>cap</code> is not either
     *         CAP_BUTT, CAP_ROUND or CAP_SQUARE
     * @throws IllegalArgumentException if <code>miterlimit</code> is less
     *         than 1 and <code>join</code> is JOIN_MITER
     * @throws IllegalArgumentException if <code>join</code> is not
     *         either JOIN_ROUND, JOIN_BEVEL, or JOIN_MITER
     */
    public BasicStroke(float width, int cap, int join, float miterlimit) {
        this(width, cap, join, miterlimit, null, 0.0f);
    }

    /**
     * Constructs a solid <code>BasicStroke</code> with the specified
     * attributes.  The <code>miterlimit</code> parameter is
     * unnecessary in cases where the default is allowable or the
     * line joins are not specified as JOIN_MITER.
     * <p>
     *  使用指定的属性构造一个固定的<code> BasicStroke </code>在允许默认值或行连接未指定为JOIN_MITER的情况下,<code> miterlimit </code>
     * 
     * 
     * @param width the width of the <code>BasicStroke</code>
     * @param cap the decoration of the ends of a <code>BasicStroke</code>
     * @param join the decoration applied where path segments meet
     * @throws IllegalArgumentException if <code>width</code> is negative
     * @throws IllegalArgumentException if <code>cap</code> is not either
     *         CAP_BUTT, CAP_ROUND or CAP_SQUARE
     * @throws IllegalArgumentException if <code>join</code> is not
     *         either JOIN_ROUND, JOIN_BEVEL, or JOIN_MITER
     */
    public BasicStroke(float width, int cap, int join) {
        this(width, cap, join, 10.0f, null, 0.0f);
    }

    /**
     * Constructs a solid <code>BasicStroke</code> with the specified
     * line width and with default values for the cap and join
     * styles.
     * <p>
     *  构造一个具有指定线宽的固定<code> BasicStroke </code>,并且使用cap和连接样式的默认值
     * 
     * 
     * @param width the width of the <code>BasicStroke</code>
     * @throws IllegalArgumentException if <code>width</code> is negative
     */
    public BasicStroke(float width) {
        this(width, CAP_SQUARE, JOIN_MITER, 10.0f, null, 0.0f);
    }

    /**
     * Constructs a new <code>BasicStroke</code> with defaults for all
     * attributes.
     * The default attributes are a solid line of width 1.0, CAP_SQUARE,
     * JOIN_MITER, a miter limit of 10.0.
     * <p>
     *  使用所有属性的默认值构造新的<code> BasicStroke </code>默认属性是宽度为10的实线,CAP_SQUARE,JOIN_MITER,斜角限制为100
     * 
     */
    public BasicStroke() {
        this(1.0f, CAP_SQUARE, JOIN_MITER, 10.0f, null, 0.0f);
    }


    /**
     * Returns a <code>Shape</code> whose interior defines the
     * stroked outline of a specified <code>Shape</code>.
     * <p>
     *  返回一个<code> Shape </code>,其内部定义了一个指定的<code> Shape </code>的描边轮廓
     * 
     * 
     * @param s the <code>Shape</code> boundary be stroked
     * @return the <code>Shape</code> of the stroked outline.
     */
    public Shape createStrokedShape(Shape s) {
        sun.java2d.pipe.RenderingEngine re =
            sun.java2d.pipe.RenderingEngine.getInstance();
        return re.createStrokedShape(s, width, cap, join, miterlimit,
                                     dash, dash_phase);
    }

    /**
     * Returns the line width.  Line width is represented in user space,
     * which is the default-coordinate space used by Java 2D.  See the
     * <code>Graphics2D</code> class comments for more information on
     * the user space coordinate system.
     * <p>
     * 返回行宽线宽在用户空间中表示,这是Java 2D使用的默认坐标空间有关用户空间坐标系的更多信息,请参阅<code> Graphics2D </code>类注释
     * 
     * 
     * @return the line width of this <code>BasicStroke</code>.
     * @see Graphics2D
     */
    public float getLineWidth() {
        return width;
    }

    /**
     * Returns the end cap style.
     * <p>
     *  返回端盖样式
     * 
     * 
     * @return the end cap style of this <code>BasicStroke</code> as one
     * of the static <code>int</code> values that define possible end cap
     * styles.
     */
    public int getEndCap() {
        return cap;
    }

    /**
     * Returns the line join style.
     * <p>
     *  返回线连接样式
     * 
     * 
     * @return the line join style of the <code>BasicStroke</code> as one
     * of the static <code>int</code> values that define possible line
     * join styles.
     */
    public int getLineJoin() {
        return join;
    }

    /**
     * Returns the limit of miter joins.
     * <p>
     *  返回斜角连接的限制
     * 
     * 
     * @return the limit of miter joins of the <code>BasicStroke</code>.
     */
    public float getMiterLimit() {
        return miterlimit;
    }

    /**
     * Returns the array representing the lengths of the dash segments.
     * Alternate entries in the array represent the user space lengths
     * of the opaque and transparent segments of the dashes.
     * As the pen moves along the outline of the <code>Shape</code>
     * to be stroked, the user space
     * distance that the pen travels is accumulated.  The distance
     * value is used to index into the dash array.
     * The pen is opaque when its current cumulative distance maps
     * to an even element of the dash array and transparent otherwise.
     * <p>
     * 返回表示破折号段长度的数组数组中的替代条目表示破折号的不透明和透明段的用户空间长度当笔沿要进行描边的<code> Shape </code>轮廓移动时,笔行进的用户空间距离被累积。
     * 距离值用于索引到虚线阵列当其当前累积距离映射到虚线阵列的偶数元素时,笔是不透明的,否则为透明的。
     * 
     * 
     * @return the dash array.
     */
    public float[] getDashArray() {
        if (dash == null) {
            return null;
        }

        return (float[]) dash.clone();
    }

    /**
     * Returns the current dash phase.
     * The dash phase is a distance specified in user coordinates that
     * represents an offset into the dashing pattern. In other words, the dash
     * phase defines the point in the dashing pattern that will correspond to
     * the beginning of the stroke.
     * <p>
     *  返回当前破折号阶段破折号阶段是在用户坐标中指定的距离,表示到破折号模式中的偏移量。换句话说,破折号阶段定义破折式模式中将对应于笔画开始的点
     * 
     * 
     * @return the dash phase as a <code>float</code> value.
     */
    public float getDashPhase() {
        return dash_phase;
    }

    /**
     * Returns the hashcode for this stroke.
     * <p>
     * 返回此笔划的哈希码
     * 
     * 
     * @return      a hash code for this stroke.
     */
    public int hashCode() {
        int hash = Float.floatToIntBits(width);
        hash = hash * 31 + join;
        hash = hash * 31 + cap;
        hash = hash * 31 + Float.floatToIntBits(miterlimit);
        if (dash != null) {
            hash = hash * 31 + Float.floatToIntBits(dash_phase);
            for (int i = 0; i < dash.length; i++) {
                hash = hash * 31 + Float.floatToIntBits(dash[i]);
            }
        }
        return hash;
    }

    /**
     * Returns true if this BasicStroke represents the same
     * stroking operation as the given argument.
     * <p>
     *  如果此BasicStroke表示与给定参数相同的描边操作,则返回true
     * 
     */
   /**
    * Tests if a specified object is equal to this <code>BasicStroke</code>
    * by first testing if it is a <code>BasicStroke</code> and then comparing
    * its width, join, cap, miter limit, dash, and dash phase attributes with
    * those of this <code>BasicStroke</code>.
    * <p>
    *  通过首先测试一个<code> BasicStroke </code>,然后比较它的width,join,cap,miter limit,dash和dash阶段来测试指定的对象是否等于这个<code> 
    * BasicStroke </code>属性与这个<code> BasicStroke </code>的属性。
    * 
    * @param  obj the specified object to compare to this
    *              <code>BasicStroke</code>
    * @return <code>true</code> if the width, join, cap, miter limit, dash, and
    *            dash phase are the same for both objects;
    *            <code>false</code> otherwise.
    */
    public boolean equals(Object obj) {
        if (!(obj instanceof BasicStroke)) {
            return false;
        }

        BasicStroke bs = (BasicStroke) obj;
        if (width != bs.width) {
            return false;
        }

        if (join != bs.join) {
            return false;
        }

        if (cap != bs.cap) {
            return false;
        }

        if (miterlimit != bs.miterlimit) {
            return false;
        }

        if (dash != null) {
            if (dash_phase != bs.dash_phase) {
                return false;
            }

            if (!java.util.Arrays.equals(dash, bs.dash)) {
                return false;
            }
        }
        else if (bs.dash != null) {
            return false;
        }

        return true;
    }
}
