/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 2004, Oracle and/or its affiliates. All rights reserved.
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

package java.awt.image.renderable;
import java.awt.image.RenderedImage;
import java.io.Serializable;
import java.util.Vector;

/**
 * A <code>ParameterBlock</code> encapsulates all the information about sources and
 * parameters (Objects) required by a RenderableImageOp, or other
 * classes that process images.
 *
 * <p> Although it is possible to place arbitrary objects in the
 * source Vector, users of this class may impose semantic constraints
 * such as requiring all sources to be RenderedImages or
 * RenderableImage.  <code>ParameterBlock</code> itself is merely a container and
 * performs no checking on source or parameter types.
 *
 * <p> All parameters in a <code>ParameterBlock</code> are objects; convenience
 * add and set methods are available that take arguments of base type and
 * construct the appropriate subclass of Number (such as
 * Integer or Float).  Corresponding get methods perform a
 * downward cast and have return values of base type; an exception
 * will be thrown if the stored values do not have the correct type.
 * There is no way to distinguish between the results of
 * "short s; add(s)" and "add(new Short(s))".
 *
 * <p> Note that the get and set methods operate on references.
 * Therefore, one must be careful not to share references between
 * <code>ParameterBlock</code>s when this is inappropriate.  For example, to create
 * a new <code>ParameterBlock</code> that is equal to an old one except for an
 * added source, one might be tempted to write:
 *
 * <pre>
 * ParameterBlock addSource(ParameterBlock pb, RenderableImage im) {
 *     ParameterBlock pb1 = new ParameterBlock(pb.getSources());
 *     pb1.addSource(im);
 *     return pb1;
 * }
 * </pre>
 *
 * <p> This code will have the side effect of altering the original
 * <code>ParameterBlock</code>, since the getSources operation returned a reference
 * to its source Vector.  Both pb and pb1 share their source Vector,
 * and a change in either is visible to both.
 *
 * <p> A correct way to write the addSource function is to clone
 * the source Vector:
 *
 * <pre>
 * ParameterBlock addSource (ParameterBlock pb, RenderableImage im) {
 *     ParameterBlock pb1 = new ParameterBlock(pb.getSources().clone());
 *     pb1.addSource(im);
 *     return pb1;
 * }
 * </pre>
 *
 * <p> The clone method of <code>ParameterBlock</code> has been defined to
 * perform a clone of both the source and parameter Vectors for
 * this reason.  A standard, shallow clone is available as
 * shallowClone.
 *
 * <p> The addSource, setSource, add, and set methods are
 * defined to return 'this' after adding their argument.  This allows
 * use of syntax like:
 *
 * <pre>
 * ParameterBlock pb = new ParameterBlock();
 * op = new RenderableImageOp("operation", pb.add(arg1).add(arg2));
 * </pre>
 * <p>
 *  A <码>的ParameterBlock </code>的封装有关人士并通过RenderableImageOp所需的参数(对象),或者其他处理图像的类的信息。
 * 
 *  <P>虽然可以放置任意对象在源向量,这个类的用户可以并处语义约束,如要求所有的来源是的RenderedImage或RenderableImage的。
 *  <code> ParameterBlock </code>本身只是一个容器,不执行对源或参数类型的检查。
 * 
 *  <p> <code> ParameterBlock </code>中的所有参数都是对象;方便的添加和设置方法可以接受基本类型的参数,并构造Number的适当子类(如Integer或Float)。
 * 相应的get方法执行向下转换并具有基本类型的返回值;如果存储的值没有正确的类型,将抛出异常。没有办法区分"短添加(s)"和"添加(新短)"的结果。
 * 
 *  <p>请注意,get和set方法对引用操作。因此,当这不合适时,必须小心不要在<code> ParameterBlock </code>之间共享引用。
 * 例如,要创建一个新的<code> ParameterBlock </code>等于除了添加的源之外的旧代码,可能会试图写：。
 * 
 * <pre>
 * ParameterBlock addSource(ParameterBlock pb,RenderableImage im){ParameterBlock pb1 = new ParameterBlock(pb.getSources()); pb1.addSource(im); return pb1; }
 * }。
 * </pre>
 * 
 *  <p>这段代码会改变原来的&lt; code&gt; ParameterBlock&lt; / code&gt;的副作用,因为getSources操作返回了对其源向量的引用。
 *  pb和pb1共享它们的源向量,并且两者中的改变是可见的。
 * 
 *  <p>编写addSource函数的正确方法是克隆源向量：
 * 
 * <pre>
 *  ParameterBlock addSource(ParameterBlock pb,RenderableImage im){ParameterBlock pb1 = new ParameterBlock(pb.getSources()。
 * clone()); pb1.addSource(im); return pb1; }}。
 * </pre>
 * 
 *  <p> <code> ParameterBlock </code>的克隆方法已定义为执行源和参数向量的克隆。一个标准的浅克隆可以作为shallowClone。
 * 
 *  <p> addSource,setSource,add和set方法定义为在添加参数后返回'this'。这允许使用如下语法：
 * 
 * <pre>
 *  ParameterBlock pb = new ParameterBlock(); op = new RenderableImageOp("operation",pb.add(arg1).add(ar
 * g2));。
 * </pre>
 * 
 * */
public class ParameterBlock implements Cloneable, Serializable {
    /** A Vector of sources, stored as arbitrary Objects. */
    protected Vector<Object> sources = new Vector<Object>();

    /** A Vector of non-source parameters, stored as arbitrary Objects. */
    protected Vector<Object> parameters = new Vector<Object>();

    /** A dummy constructor. */
    public ParameterBlock() {}

    /**
     * Constructs a <code>ParameterBlock</code> with a given Vector
     * of sources.
     * <p>
     *  用给定的源向量构造一个<code> ParameterBlock </code>。
     * 
     * 
     * @param sources a <code>Vector</code> of source images
     */
    public ParameterBlock(Vector<Object> sources) {
        setSources(sources);
    }

    /**
     * Constructs a <code>ParameterBlock</code> with a given Vector of sources and
     * Vector of parameters.
     * <p>
     *  使用给定的源向量和参数向量构造一个<code> ParameterBlock </code>。
     * 
     * 
     * @param sources a <code>Vector</code> of source images
     * @param parameters a <code>Vector</code> of parameters to be used in the
     *        rendering operation
     */
    public ParameterBlock(Vector<Object> sources,
                          Vector<Object> parameters)
    {
        setSources(sources);
        setParameters(parameters);
    }

    /**
     * Creates a shallow copy of a <code>ParameterBlock</code>.  The source and
     * parameter Vectors are copied by reference -- additions or
     * changes will be visible to both versions.
     *
     * <p>
     *  创建<code> ParameterBlock </code>的浅副本。源和参数向量通过引用复制 - 添加或更改将对两个版本可见。
     * 
     * 
     * @return an Object clone of the <code>ParameterBlock</code>.
     */
    public Object shallowClone() {
        try {
            return super.clone();
        } catch (Exception e) {
            // We can't be here since we implement Cloneable.
            return null;
        }
    }

    /**
     * Creates a copy of a <code>ParameterBlock</code>.  The source and parameter
     * Vectors are cloned, but the actual sources and parameters are
     * copied by reference.  This allows modifications to the order
     * and number of sources and parameters in the clone to be invisible
     * to the original <code>ParameterBlock</code>.  Changes to the shared sources or
     * parameters themselves will still be visible.
     *
     * <p>
     * 创建<code> ParameterBlock </code>的副本。克隆了源和参数矢量,但是通过引用复制实际的源和参数。
     * 这允许对克隆中的源和参数的顺序和数量的修改对于原始的<code> ParameterBlock </code>是不可见的。对共享源或参数本身的更改仍然可见。
     * 
     * 
     * @return an Object clone of the <code>ParameterBlock</code>.
     */
    public Object clone() {
        ParameterBlock theClone;

        try {
            theClone = (ParameterBlock) super.clone();
        } catch (Exception e) {
            // We can't be here since we implement Cloneable.
            return null;
        }

        if (sources != null) {
            theClone.setSources((Vector)sources.clone());
        }
        if (parameters != null) {
            theClone.setParameters((Vector)parameters.clone());
        }
        return (Object) theClone;
    }

    /**
     * Adds an image to end of the list of sources.  The image is
     * stored as an object in order to allow new node types in the
     * future.
     *
     * <p>
     *  将图像添加到源列表的结尾。图像存储为对象,以便将来允许新的节点类型。
     * 
     * 
     * @param source an image object to be stored in the source list.
     * @return a new <code>ParameterBlock</code> containing the specified
     *         <code>source</code>.
     */
    public ParameterBlock addSource(Object source) {
        sources.addElement(source);
        return this;
    }

    /**
     * Returns a source as a general Object.  The caller must cast it into
     * an appropriate type.
     *
     * <p>
     *  将一个源作为一般对象返回。调用者必须将其转换为适当的类型。
     * 
     * 
     * @param index the index of the source to be returned.
     * @return an <code>Object</code> that represents the source located
     *         at the specified index in the <code>sources</code>
     *         <code>Vector</code>.
     * @see #setSource(Object, int)
     */
    public Object getSource(int index) {
        return sources.elementAt(index);
    }

    /**
     * Replaces an entry in the list of source with a new source.
     * If the index lies beyond the current source list,
     * the list is extended with nulls as needed.
     * <p>
     *  使用新源替换源列表中的条目。如果索引位于当前源列表之外,则根据需要使用null扩展列表。
     * 
     * 
     * @param source the specified source image
     * @param index the index into the <code>sources</code>
     *              <code>Vector</code> at which to
     *              insert the specified <code>source</code>
     * @return a new <code>ParameterBlock</code> that contains the
     *         specified <code>source</code> at the specified
     *         <code>index</code>.
     * @see #getSource(int)
     */
    public ParameterBlock setSource(Object source, int index) {
        int oldSize = sources.size();
        int newSize = index + 1;
        if (oldSize < newSize) {
            sources.setSize(newSize);
        }
        sources.setElementAt(source, index);
        return this;
    }

    /**
     * Returns a source as a <code>RenderedImage</code>.  This method is
     * a convenience method.
     * An exception will be thrown if the source is not a RenderedImage.
     *
     * <p>
     *  以<code> RenderedImage </code>形式返回源。这种方法是一种方便的方法。如果源不是RenderedImage,将抛出异常。
     * 
     * 
     * @param index the index of the source to be returned
     * @return a <code>RenderedImage</code> that represents the source
     *         image that is at the specified index in the
     *         <code>sources</code> <code>Vector</code>.
     */
    public RenderedImage getRenderedSource(int index) {
        return (RenderedImage) sources.elementAt(index);
    }

    /**
     * Returns a source as a RenderableImage.  This method is a
     * convenience method.
     * An exception will be thrown if the sources is not a RenderableImage.
     *
     * <p>
     *  将来源作为RenderableImage返回。这种方法是一种方便的方法。如果源不是RenderableImage,将抛出异常。
     * 
     * 
     * @param index the index of the source to be returned
     * @return a <code>RenderableImage</code> that represents the source
     *         image that is at the specified index in the
     *         <code>sources</code> <code>Vector</code>.
     */
    public RenderableImage getRenderableSource(int index) {
        return (RenderableImage) sources.elementAt(index);
    }

    /**
     * Returns the number of source images.
     * <p>
     *  返回源图像的数量。
     * 
     * 
     * @return the number of source images in the <code>sources</code>
     *         <code>Vector</code>.
     */
    public int getNumSources() {
        return sources.size();
    }

    /**
     * Returns the entire Vector of sources.
     * <p>
     *  返回整个Vector向量。
     * 
     * 
     * @return the <code>sources</code> <code>Vector</code>.
     * @see #setSources(Vector)
     */
    public Vector<Object> getSources() {
        return sources;
    }

    /**
     * Sets the entire Vector of sources to a given Vector.
     * <p>
     *  将源的整个向量设置为给定向量。
     * 
     * 
     * @param sources the <code>Vector</code> of source images
     * @see #getSources
     */
    public void setSources(Vector<Object> sources) {
        this.sources = sources;
    }

    /** Clears the list of source images. */
    public void removeSources() {
        sources = new Vector();
    }

    /**
     * Returns the number of parameters (not including source images).
     * <p>
     *  返回参数数量(不包括源图像)。
     * 
     * 
     * @return the number of parameters in the <code>parameters</code>
     *         <code>Vector</code>.
     */
    public int getNumParameters() {
        return parameters.size();
    }

    /**
     * Returns the entire Vector of parameters.
     * <p>
     *  返回参数的整个Vector。
     * 
     * 
     * @return the <code>parameters</code> <code>Vector</code>.
     * @see #setParameters(Vector)
     */
    public Vector<Object> getParameters() {
        return parameters;
    }

    /**
     * Sets the entire Vector of parameters to a given Vector.
     * <p>
     *  将参数的整个向量设置为给定向量。
     * 
     * 
     * @param parameters the specified <code>Vector</code> of
     *        parameters
     * @see #getParameters
     */
    public void setParameters(Vector<Object> parameters) {
        this.parameters = parameters;
    }

    /** Clears the list of parameters. */
    public void removeParameters() {
        parameters = new Vector();
    }

    /**
     * Adds an object to the list of parameters.
     * <p>
     *  将对象添加到参数列表。
     * 
     * 
     * @param obj the <code>Object</code> to add to the
     *            <code>parameters</code> <code>Vector</code>
     * @return a new <code>ParameterBlock</code> containing
     *         the specified parameter.
     */
    public ParameterBlock add(Object obj) {
        parameters.addElement(obj);
        return this;
    }

    /**
     * Adds a Byte to the list of parameters.
     * <p>
     *  将一个字节添加到参数列表。
     * 
     * 
     * @param b the byte to add to the
     *            <code>parameters</code> <code>Vector</code>
     * @return a new <code>ParameterBlock</code> containing
     *         the specified parameter.
     */
    public ParameterBlock add(byte b) {
        return add(new Byte(b));
    }

    /**
     * Adds a Character to the list of parameters.
     * <p>
     *  向参数列表中添加一个字符。
     * 
     * 
     * @param c the char to add to the
     *            <code>parameters</code> <code>Vector</code>
     * @return a new <code>ParameterBlock</code> containing
     *         the specified parameter.
     */
    public ParameterBlock add(char c) {
        return add(new Character(c));
    }

    /**
     * Adds a Short to the list of parameters.
     * <p>
     * 在参数列表中添加一个Short。
     * 
     * 
     * @param s the short to add to the
     *            <code>parameters</code> <code>Vector</code>
     * @return a new <code>ParameterBlock</code> containing
     *         the specified parameter.
     */
    public ParameterBlock add(short s) {
        return add(new Short(s));
    }

    /**
     * Adds a Integer to the list of parameters.
     * <p>
     *  向参数列表中添加一个整数。
     * 
     * 
     * @param i the int to add to the
     *            <code>parameters</code> <code>Vector</code>
     * @return a new <code>ParameterBlock</code> containing
     *         the specified parameter.
     */
    public ParameterBlock add(int i) {
        return add(new Integer(i));
    }

    /**
     * Adds a Long to the list of parameters.
     * <p>
     *  向参数列表中添加一个Long。
     * 
     * 
     * @param l the long to add to the
     *            <code>parameters</code> <code>Vector</code>
     * @return a new <code>ParameterBlock</code> containing
     *         the specified parameter.
     */
    public ParameterBlock add(long l) {
        return add(new Long(l));
    }

    /**
     * Adds a Float to the list of parameters.
     * <p>
     *  将浮点数添加到参数列表。
     * 
     * 
     * @param f the float to add to the
     *            <code>parameters</code> <code>Vector</code>
     * @return a new <code>ParameterBlock</code> containing
     *         the specified parameter.
     */
    public ParameterBlock add(float f) {
        return add(new Float(f));
    }

    /**
     * Adds a Double to the list of parameters.
     * <p>
     *  向参数列表中添加Double。
     * 
     * 
     * @param d the double to add to the
     *            <code>parameters</code> <code>Vector</code>
     * @return a new <code>ParameterBlock</code> containing
     *         the specified parameter.
     */
    public ParameterBlock add(double d) {
        return add(new Double(d));
    }

    /**
     * Replaces an Object in the list of parameters.
     * If the index lies beyond the current source list,
     * the list is extended with nulls as needed.
     * <p>
     *  替换参数列表中的对象。如果索引位于当前源列表之外,则根据需要使用null扩展列表。
     * 
     * 
     * @param obj the parameter that replaces the
     *        parameter at the specified index in the
     *        <code>parameters</code> <code>Vector</code>
     * @param index the index of the parameter to be
     *        replaced with the specified parameter
     * @return a new <code>ParameterBlock</code> containing
     *        the specified parameter.
     */
    public ParameterBlock set(Object obj, int index) {
        int oldSize = parameters.size();
        int newSize = index + 1;
        if (oldSize < newSize) {
            parameters.setSize(newSize);
        }
        parameters.setElementAt(obj, index);
        return this;
    }

    /**
     * Replaces an Object in the list of parameters with a Byte.
     * If the index lies beyond the current source list,
     * the list is extended with nulls as needed.
     * <p>
     *  用一个字节替换参数列表中的一个对象。如果索引位于当前源列表之外,则根据需要使用null扩展列表。
     * 
     * 
     * @param b the parameter that replaces the
     *        parameter at the specified index in the
     *        <code>parameters</code> <code>Vector</code>
     * @param index the index of the parameter to be
     *        replaced with the specified parameter
     * @return a new <code>ParameterBlock</code> containing
     *        the specified parameter.
     */
    public ParameterBlock set(byte b, int index) {
        return set(new Byte(b), index);
    }

    /**
     * Replaces an Object in the list of parameters with a Character.
     * If the index lies beyond the current source list,
     * the list is extended with nulls as needed.
     * <p>
     *  用参数替换参数列表中的对象。如果索引位于当前源列表之外,则根据需要使用null扩展列表。
     * 
     * 
     * @param c the parameter that replaces the
     *        parameter at the specified index in the
     *        <code>parameters</code> <code>Vector</code>
     * @param index the index of the parameter to be
     *        replaced with the specified parameter
     * @return a new <code>ParameterBlock</code> containing
     *        the specified parameter.
     */
    public ParameterBlock set(char c, int index) {
        return set(new Character(c), index);
    }

    /**
     * Replaces an Object in the list of parameters with a Short.
     * If the index lies beyond the current source list,
     * the list is extended with nulls as needed.
     * <p>
     *  用Short替换参数列表中的对象。如果索引位于当前源列表之外,则根据需要使用null扩展列表。
     * 
     * 
     * @param s the parameter that replaces the
     *        parameter at the specified index in the
     *        <code>parameters</code> <code>Vector</code>
     * @param index the index of the parameter to be
     *        replaced with the specified parameter
     * @return a new <code>ParameterBlock</code> containing
     *        the specified parameter.
     */
    public ParameterBlock set(short s, int index) {
        return set(new Short(s), index);
    }

    /**
     * Replaces an Object in the list of parameters with an Integer.
     * If the index lies beyond the current source list,
     * the list is extended with nulls as needed.
     * <p>
     *  用Integer替换参数列表中的对象。如果索引位于当前源列表之外,则根据需要使用null扩展列表。
     * 
     * 
     * @param i the parameter that replaces the
     *        parameter at the specified index in the
     *        <code>parameters</code> <code>Vector</code>
     * @param index the index of the parameter to be
     *        replaced with the specified parameter
     * @return a new <code>ParameterBlock</code> containing
     *        the specified parameter.
     */
    public ParameterBlock set(int i, int index) {
        return set(new Integer(i), index);
    }

    /**
     * Replaces an Object in the list of parameters with a Long.
     * If the index lies beyond the current source list,
     * the list is extended with nulls as needed.
     * <p>
     *  将参数列表中的对象替换为Long。如果索引位于当前源列表之外,则根据需要使用null扩展列表。
     * 
     * 
     * @param l the parameter that replaces the
     *        parameter at the specified index in the
     *        <code>parameters</code> <code>Vector</code>
     * @param index the index of the parameter to be
     *        replaced with the specified parameter
     * @return a new <code>ParameterBlock</code> containing
     *        the specified parameter.
     */
    public ParameterBlock set(long l, int index) {
        return set(new Long(l), index);
    }

    /**
     * Replaces an Object in the list of parameters with a Float.
     * If the index lies beyond the current source list,
     * the list is extended with nulls as needed.
     * <p>
     *  用浮点数替换参数列表中的对象。如果索引位于当前源列表之外,则根据需要使用null扩展列表。
     * 
     * 
     * @param f the parameter that replaces the
     *        parameter at the specified index in the
     *        <code>parameters</code> <code>Vector</code>
     * @param index the index of the parameter to be
     *        replaced with the specified parameter
     * @return a new <code>ParameterBlock</code> containing
     *        the specified parameter.
     */
    public ParameterBlock set(float f, int index) {
        return set(new Float(f), index);
    }

    /**
     * Replaces an Object in the list of parameters with a Double.
     * If the index lies beyond the current source list,
     * the list is extended with nulls as needed.
     * <p>
     *  将参数列表中的对象替换为Double。如果索引位于当前源列表之外,则根据需要使用null扩展列表。
     * 
     * 
     * @param d the parameter that replaces the
     *        parameter at the specified index in the
     *        <code>parameters</code> <code>Vector</code>
     * @param index the index of the parameter to be
     *        replaced with the specified parameter
     * @return a new <code>ParameterBlock</code> containing
     *        the specified parameter.
     */
    public ParameterBlock set(double d, int index) {
        return set(new Double(d), index);
    }

    /**
     * Gets a parameter as an object.
     * <p>
     *  获取参数作为对象。
     * 
     * 
     * @param index the index of the parameter to get
     * @return an <code>Object</code> representing the
     *         the parameter at the specified index
     *         into the <code>parameters</code>
     *         <code>Vector</code>.
     */
    public Object getObjectParameter(int index) {
        return parameters.elementAt(index);
    }

    /**
     * A convenience method to return a parameter as a byte.  An
     * exception is thrown if the parameter is
     * <code>null</code> or not a <code>Byte</code>.
     *
     * <p>
     * 一种方便的方法,以字节形式返回参数。如果参数为<code> null </code>或不是<code> Byte </code>,则抛出异常。
     * 
     * 
     * @param index the index of the parameter to be returned.
     * @return the parameter at the specified index
     *         as a <code>byte</code> value.
     * @throws ClassCastException if the parameter at the
     *         specified index is not a <code>Byte</code>
     * @throws NullPointerException if the parameter at the specified
     *         index is <code>null</code>
     * @throws ArrayIndexOutOfBoundsException if <code>index</code>
     *         is negative or not less than the current size of this
     *         <code>ParameterBlock</code> object
     */
    public byte getByteParameter(int index) {
        return ((Byte)parameters.elementAt(index)).byteValue();
    }

    /**
     * A convenience method to return a parameter as a char.  An
     * exception is thrown if the parameter is
     * <code>null</code> or not a <code>Character</code>.
     *
     * <p>
     *  一个方便的方法来返回一个参数作为char。如果参数为<code> null </code>或不是<code> Character </code>,则抛出异常。
     * 
     * 
     * @param index the index of the parameter to be returned.
     * @return the parameter at the specified index
     *         as a <code>char</code> value.
     * @throws ClassCastException if the parameter at the
     *         specified index is not a <code>Character</code>
     * @throws NullPointerException if the parameter at the specified
     *         index is <code>null</code>
     * @throws ArrayIndexOutOfBoundsException if <code>index</code>
     *         is negative or not less than the current size of this
     *         <code>ParameterBlock</code> object
     */
    public char getCharParameter(int index) {
        return ((Character)parameters.elementAt(index)).charValue();
    }

    /**
     * A convenience method to return a parameter as a short.  An
     * exception is thrown if the parameter is
     * <code>null</code> or not a <code>Short</code>.
     *
     * <p>
     *  一个方便的方法来返回一个短的参数。如果参数为<code> null </code>或不是<code> Short </code>,则抛出异常。
     * 
     * 
     * @param index the index of the parameter to be returned.
     * @return the parameter at the specified index
     *         as a <code>short</code> value.
     * @throws ClassCastException if the parameter at the
     *         specified index is not a <code>Short</code>
     * @throws NullPointerException if the parameter at the specified
     *         index is <code>null</code>
     * @throws ArrayIndexOutOfBoundsException if <code>index</code>
     *         is negative or not less than the current size of this
     *         <code>ParameterBlock</code> object
     */
    public short getShortParameter(int index) {
        return ((Short)parameters.elementAt(index)).shortValue();
    }

    /**
     * A convenience method to return a parameter as an int.  An
     * exception is thrown if the parameter is
     * <code>null</code> or not an <code>Integer</code>.
     *
     * <p>
     *  一个方便的方法来返回一个int参数。如果参数为<code> null </code>或不是<code> Integer </code>,则抛出异常。
     * 
     * 
     * @param index the index of the parameter to be returned.
     * @return the parameter at the specified index
     *         as a <code>int</code> value.
     * @throws ClassCastException if the parameter at the
     *         specified index is not a <code>Integer</code>
     * @throws NullPointerException if the parameter at the specified
     *         index is <code>null</code>
     * @throws ArrayIndexOutOfBoundsException if <code>index</code>
     *         is negative or not less than the current size of this
     *         <code>ParameterBlock</code> object
     */
    public int getIntParameter(int index) {
        return ((Integer)parameters.elementAt(index)).intValue();
    }

    /**
     * A convenience method to return a parameter as a long.  An
     * exception is thrown if the parameter is
     * <code>null</code> or not a <code>Long</code>.
     *
     * <p>
     *  一个方便的方法来返回一个参数为long。如果参数为<code> null </code>或不是<code> Long </code>,则抛出异常。
     * 
     * 
     * @param index the index of the parameter to be returned.
     * @return the parameter at the specified index
     *         as a <code>long</code> value.
     * @throws ClassCastException if the parameter at the
     *         specified index is not a <code>Long</code>
     * @throws NullPointerException if the parameter at the specified
     *         index is <code>null</code>
     * @throws ArrayIndexOutOfBoundsException if <code>index</code>
     *         is negative or not less than the current size of this
     *         <code>ParameterBlock</code> object
     */
    public long getLongParameter(int index) {
        return ((Long)parameters.elementAt(index)).longValue();
    }

    /**
     * A convenience method to return a parameter as a float.  An
     * exception is thrown if the parameter is
     * <code>null</code> or not a <code>Float</code>.
     *
     * <p>
     *  一个方便的方法来返回一个参数作为float。如果参数为<code> null </code>或不是<code> Float </code>,则抛出异常。
     * 
     * 
     * @param index the index of the parameter to be returned.
     * @return the parameter at the specified index
     *         as a <code>float</code> value.
     * @throws ClassCastException if the parameter at the
     *         specified index is not a <code>Float</code>
     * @throws NullPointerException if the parameter at the specified
     *         index is <code>null</code>
     * @throws ArrayIndexOutOfBoundsException if <code>index</code>
     *         is negative or not less than the current size of this
     *         <code>ParameterBlock</code> object
     */
    public float getFloatParameter(int index) {
        return ((Float)parameters.elementAt(index)).floatValue();
    }

    /**
     * A convenience method to return a parameter as a double.  An
     * exception is thrown if the parameter is
     * <code>null</code> or not a <code>Double</code>.
     *
     * <p>
     *  一个方便的方法将参数返回为double。如果参数为<code> null </code>或不是<code> Double </code>,则抛出异常。
     * 
     * 
     * @param index the index of the parameter to be returned.
     * @return the parameter at the specified index
     *         as a <code>double</code> value.
     * @throws ClassCastException if the parameter at the
     *         specified index is not a <code>Double</code>
     * @throws NullPointerException if the parameter at the specified
     *         index is <code>null</code>
     * @throws ArrayIndexOutOfBoundsException if <code>index</code>
     *         is negative or not less than the current size of this
     *         <code>ParameterBlock</code> object
     */
    public double getDoubleParameter(int index) {
        return ((Double)parameters.elementAt(index)).doubleValue();
    }

    /**
     * Returns an array of Class objects describing the types
     * of the parameters.
     * <p>
     *  返回描述参数类型的Class对象数组。
     * 
     * @return an array of <code>Class</code> objects.
     */
    public Class [] getParamClasses() {
        int numParams = getNumParameters();
        Class [] classes = new Class[numParams];
        int i;

        for (i = 0; i < numParams; i++) {
            Object obj = getObjectParameter(i);
            if (obj instanceof Byte) {
              classes[i] = byte.class;
            } else if (obj instanceof Character) {
              classes[i] = char.class;
            } else if (obj instanceof Short) {
              classes[i] = short.class;
            } else if (obj instanceof Integer) {
              classes[i] = int.class;
            } else if (obj instanceof Long) {
              classes[i] = long.class;
            } else if (obj instanceof Float) {
              classes[i] = float.class;
            } else if (obj instanceof Double) {
              classes[i] = double.class;
            } else {
              classes[i] = obj.getClass();
            }
        }

        return classes;
    }
}
