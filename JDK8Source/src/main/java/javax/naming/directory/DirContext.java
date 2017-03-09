/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2013, Oracle and/or its affiliates. All rights reserved.
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

package javax.naming.directory;

import javax.naming.*;

/**
 * The directory service interface, containing
 * methods for examining and updating attributes
 * associated with objects, and for searching the directory.
 *
 * <h1>Names</h1>
 * Each name passed as an argument to a <tt>DirContext</tt> method is relative
 * to that context.  The empty name is used to name the context itself.
 * The name parameter may never be null.
 * <p>
 * Most of the methods have overloaded versions with one taking a
 * <code>Name</code> parameter and one taking a <code>String</code>.
 * These overloaded versions are equivalent in that if
 * the <code>Name</code> and <code>String</code> parameters are just
 * different representations of the same name, then the overloaded
 * versions of the same methods behave the same.
 * In the method descriptions below, only one version is documented.
 * The second version instead has a link to the first:  the same
 * documentation applies to both.
 * <p>
 * See <tt>Context</tt> for a discussion on the interpretation of the
 * name argument to the <tt>Context</tt> methods. These same rules
 * apply to the name argument to the <tt>DirContext</tt> methods.
 *
 * <h1>Attribute Models</h1>
 * There are two basic models of what attributes should be
 * associated with.  First, attributes may be directly associated with a
 * DirContext object.
 * In this model, an attribute operation on the named object is
 * roughly equivalent
 * to a lookup on the name (which returns the DirContext object),
 * followed by the attribute operation invoked on the DirContext object
 * in which the caller supplies an empty name. The attributes can be viewed
 * as being stored along with the object (note that this does not imply that
 * the implementation must do so).
 * <p>
 * The second model is that attributes are associated with a
 * name (typically an atomic name) in a DirContext.
 * In this model, an attribute operation on the named object is
 * roughly equivalent to a lookup on the name of the parent DirContext of the
 * named object, followed by the attribute operation invoked on the parent
 * in which the caller supplies the terminal atomic name.
 * The attributes can be viewed as being stored in the parent DirContext
 * (again, this does not imply that the implementation must do so).
 * Objects that are not DirContexts can have attributes, as long as
 * their parents are DirContexts.
 * <p>
 * JNDI support both of these models.
 * It is up to the individual service providers to decide where to
 * "store" attributes.
 * JNDI clients are safest when they do not make assumptions about
 * whether an object's attributes are stored as part of the object, or stored
 * within the parent object and associated with the object's name.
 *
 * <h1>Attribute Type Names</h1>
 * In the <tt>getAttributes()</tt> and <tt>search()</tt> methods,
 * you can supply the attributes to return by supplying a list of
 * attribute names (strings).
 * The attributes that you get back might not have the same names as the
 * attribute names you have specified. This is because some directories
 * support features that cause them to return other attributes.  Such
 * features include attribute subclassing, attribute name synonyms, and
 * attribute language codes.
 * <p>
 * In attribute subclassing, attributes are defined in a class hierarchy.
 * In some directories, for example, the "name" attribute might be the
 * superclass of all name-related attributes, including "commonName" and
 * "surName".  Asking for the "name" attribute might return both the
 * "commonName" and "surName" attributes.
 * <p>
 * With attribute type synonyms, a directory can assign multiple names to
 * the same attribute. For example, "cn" and "commonName" might both
 * refer to the same attribute. Asking for "cn" might return the
 * "commonName" attribute.
 * <p>
 * Some directories support the language codes for attributes.
 * Asking such a directory for the "description" attribute, for example,
 * might return all of the following attributes:
 * <ul>
 * <li>description
 * <li>description;lang-en
 * <li>description;lang-de
 * <li>description;lang-fr
 * </ul>
 *
 *
 *<h1>Operational Attributes</h1>
 *<p>
 * Some directories have the notion of "operational attributes" which are
 * attributes associated with a directory object for administrative
 * purposes. An example of operational attributes is the access control
 * list for an object.
 * <p>
 * In the <tt>getAttributes()</tt> and <tt>search()</tt> methods,
 * you can specify that all attributes associated with the requested objects
 * be returned by supply <tt>null</tt> as the list of attributes to return.
 * The attributes returned do <em>not</em> include operational attributes.
 * In order to retrieve operational attributes, you must name them explicitly.
 *
 *
 * <h1>Named Context</h1>
 * <p>
 * There are certain methods in which the name must resolve to a context
 * (for example, when searching a single level context). The documentation
 * of such methods
 * use the term <em>named context</em> to describe their name parameter.
 * For these methods, if the named object is not a DirContext,
 * <code>NotContextException</code> is thrown.
 * Aside from these methods, there is no requirement that the
 * <em>named object</em> be a DirContext.
 *
 *<h1>Parameters</h1>
 *<p>
 * An <tt>Attributes</tt>, <tt>SearchControls</tt>, or array object
 * passed as a parameter to any method will not be modified by the
 * service provider.  The service provider may keep a reference to it
 * for the duration of the operation, including any enumeration of the
 * method's results and the processing of any referrals generated.
 * The caller should not modify the object during this time.
 * An <tt>Attributes</tt> object returned by any method is owned by
 * the caller.  The caller may subsequently modify it; the service
 * provider will not.
 *
 *<h1>Exceptions</h1>
 *<p>
 * All the methods in this interface can throw a NamingException or
 * any of its subclasses. See NamingException and their subclasses
 * for details on each exception.
 *
 * <p>
 *  目录服务接口,包含用于检查和更新与对象相关联的属性以及用于搜索目录的方法。
 * 
 *  <h1>名称</h1>作为参数传递给<tt> DirContext </tt>方法的每个名称都与该上下文相关。空名称用于命名上下文本身。 name参数永远不能为null。
 * <p>
 *  大多数方法具有重载版本,其中一个采用<code> Name </code>参数,一个采用<code> String </code>。
 * 这些重载的版本是等同的,如果<code> Name </code>和<code> String </code>参数只是同一个名称的不同表示,那么相同方法的重载版本行为相同。
 * 在下面的方法描述中,只记录了一个版本。第二个版本有一个链接到第一个：同样的文档适用于两者。
 * <p>
 *  有关<tt>上下文</tt>方法的名称参数解释的讨论,请参见<tt>上下文</tt>。这些规则同样适用于<tt> DirContext </tt>方法的name参数。
 * 
 * <h1>属性模型</h1>有两个基本模型的属性应该关联。首先,属性可以直接与DirContext对象相关联。
 * 在这个模型中,命名对象上的属性操作大致相当于对名称的查找(返回DirContext对象),随后是在DirContext对象上调用的属性操作,调用者在其中提供一个空名称。
 * 属性可以被视为与对象一起存储(注意,这并不意味着实现必须这样做)。
 * <p>
 *  第二个模型是属性与DirContext中的名称(通常是原子名称)相关联。
 * 在此模型中,命名对象上的属性操作大致相当于对命名对象的父DirContext的名称进行查找,后跟在父调用的属性操作(调用者在其中提供终端原子名称)。
 * 属性可以被视为存储在父DirContext中(同样,这并不意味着实现必须这样做)。不是DirContexts的对象可以有属性,只要他们的父对象是DirContexts。
 * <p>
 *  JNDI支持这两个模型。由各个服务提供商决定"存储"属性的位置。 JNDI客户端是最安全的,因为他们不会假定对象的属性是作为对象的一部分存储还是存储在父对象中并与对象的名称相关联。
 * 
 * <h1>属性类型名称</h1>在<tt> getAttributes()</tt>和<tt> search()</tt>方法中,可以通过提供属性名称列表字符串)。
 * 您返回的属性可能不具有与您指定的属性名称相同的名称。这是因为一些目录支持使其返回其他属性的功能。这样的特征包括属性子类化,属性名称同义词和属性语言代码。
 * <p>
 *  在属性子类化中,属性在类层次结构中定义。在某些目录中,例如,"name"属性可能是所有与名称相关的属性的超类,包括"commonName"和"surName"。
 * 请求"name"属性可能返回"commonName"和"surName"属性。
 * <p>
 *  使用属性类型同义词,目录可以为同一属性分配多个名称。例如,"cn"和"commonName"可能都引用相同的属性。请求"cn"可能返回"commonName"属性。
 * <p>
 *  一些目录支持属性的语言代码。例如,为"description"属性请求这样的目录可能返回所有以下属性：
 * <ul>
 *  <li>描述<li>描述; lang-en <li>描述; lang-de <li>描述; lang-fr
 * </ul>
 * 
 *  h1>操作属性</h1>
 * p>
 * 一些目录具有"操作属性"的概念,这些属性是与用于管理目的的目录对象相关联的属性。操作属性的示例是对象的访问控制列表。
 * <p>
 *  在<tt> getAttributes()</tt>和<tt> search()</tt>方法中,您可以指定与所请求对象相关联的所有属性都由供应<tt> null </tt>返回的属性列表。
 * 返回的属性<em>不</em>包括操作属性。为了检索操作属性,必须明确命名它们。
 * 
 *  <h1>命名的上下文</h1>
 * <p>
 *  有一些方法,其中名称必须解析为上下文(例如,当搜索单个级别上下文时)。这样的方法的文档使用术语<em>命名的上下文</em>来描述它们的名称参数。
 * 对于这些方法,如果命名对象不是DirContext,则抛出<code> NotContextException </code>。除了这些方法,没有要求<em>命名对象</em>是DirContext。
 * 
 *  h1>参数</h1>
 * p>
 * 作为参数传递到任何方法的<tt>属性</tt>,<tt> SearchControls </tt>或数组对象不会被服务提供商修改。
 * 服务提供商可以在操作的持续时间保持对其的引用,包括方法的结果的任何枚举以及生成的任何引用的处理。调用者在此期间不应该修改对象。
 * 由任何方法返回的<tt> Attributes </tt>对象由调用者拥有。调用者可以随后修改它;服务提供商不会。
 * 
 *  h1>异常</h1>
 * p>
 *  这个接口中的所有方法都可以抛出一个NamingException或其任何子类。有关每个异常的详细信息,请参阅NamingException及其子类。
 * 
 * 
 * @author Rosanna Lee
 * @author Scott Seligman
 * @author R. Vasudevan
 *
 * @see javax.naming.Context
 * @since 1.3
 */

public interface DirContext extends Context {

    /**
     * Retrieves all of the attributes associated with a named object.
     * See the class description regarding attribute models, attribute
     * type names, and operational attributes.
     *
     * <p>
     *  检索与命名对象关联的所有属性。请参阅关于属性模型,属性类型名称和操作属性的类描述。
     * 
     * 
     * @param name
     *          the name of the object from which to retrieve attributes
     * @return  the set of attributes associated with <code>name</code>.
     *          Returns an empty attribute set if name has no attributes;
     *          never null.
     * @throws  NamingException if a naming exception is encountered
     *
     * @see #getAttributes(String)
     * @see #getAttributes(Name, String[])
     */
    public Attributes getAttributes(Name name) throws NamingException;

    /**
     * Retrieves all of the attributes associated with a named object.
     * See {@link #getAttributes(Name)} for details.
     *
     * <p>
     *  检索与命名对象关联的所有属性。有关详情,请参阅{@link #getAttributes(Name)}。
     * 
     * 
     * @param name
     *          the name of the object from which to retrieve attributes
     * @return  the set of attributes associated with <code>name</code>
     *
     * @throws  NamingException if a naming exception is encountered
     */
    public Attributes getAttributes(String name) throws NamingException;

    /**
     * Retrieves selected attributes associated with a named object.
     * See the class description regarding attribute models, attribute
     * type names, and operational attributes.
     *
     * <p> If the object does not have an attribute
     * specified, the directory will ignore the nonexistent attribute
     * and return those requested attributes that the object does have.
     *
     * <p> A directory might return more attributes than was requested
     * (see <strong>Attribute Type Names</strong> in the class description),
     * but is not allowed to return arbitrary, unrelated attributes.
     *
     * <p> See also <strong>Operational Attributes</strong> in the class
     * description.
     *
     * <p>
     *  检索与命名对象关联的所选属性。请参阅关于属性模型,属性类型名称和操作属性的类描述。
     * 
     *  <p>如果对象没有指定属性,目录将忽略不存在的属性,并返回对象具有的请求的属性。
     * 
     * <p>目录可能会返回比请求的属性更多的属性(请参阅类描述中的<strong>属性类型名称</strong>),但不允许返回任意不相关的属性。
     * 
     *  <p>另请参阅类说明中的<strong>操作属性</strong>。
     * 
     * 
     * @param name
     *          the name of the object from which to retrieve attributes
     * @param attrIds
     *          the identifiers of the attributes to retrieve.
     *          null indicates that all attributes should be retrieved;
     *          an empty array indicates that none should be retrieved.
     * @return  the requested attributes; never null
     *
     * @throws  NamingException if a naming exception is encountered
     */
    public Attributes getAttributes(Name name, String[] attrIds)
            throws NamingException;

    /**
     * Retrieves selected attributes associated with a named object.
     * See {@link #getAttributes(Name, String[])} for details.
     *
     * <p>
     *  检索与命名对象关联的所选属性。有关详细信息,请参阅{@link #getAttributes(Name,String [])}。
     * 
     * 
     * @param name
     *          The name of the object from which to retrieve attributes
     * @param attrIds
     *          the identifiers of the attributes to retrieve.
     *          null indicates that all attributes should be retrieved;
     *          an empty array indicates that none should be retrieved.
     * @return  the requested attributes; never null
     *
     * @throws  NamingException if a naming exception is encountered
     */
    public Attributes getAttributes(String name, String[] attrIds)
            throws NamingException;

    /**
     * This constant specifies to add an attribute with the specified values.
     * <p>
     * If attribute does not exist,
     * create the attribute.  The resulting attribute has a union of the
     * specified value set and the prior value set.
     * Adding an attribute with no value will throw
     * <code>InvalidAttributeValueException</code> if the attribute must have
     * at least  one value.  For a single-valued attribute where that attribute
     * already exists, throws <code>AttributeInUseException</code>.
     * If attempting to add more than one value to a single-valued attribute,
     * throws <code>InvalidAttributeValueException</code>.
     * <p>
     * The value of this constant is <tt>1</tt>.
     *
     * <p>
     *  此常量指定添加具有指定值的属性。
     * <p>
     *  如果属性不存在,请创建属性。结果属性具有指定值集和先前值集的联合。
     * 添加没有值的属性将会引发<code> InvalidAttributeValueException </code>,如果属性必须至少有一个值。
     * 对于该属性已存在的单值属性,throws <code> AttributeInUseException </code>。
     * 如果尝试向单值属性添加多个值,请抛出<code> InvalidAttributeValueException </code>。
     * <p>
     *  此常数的值为<tt> 1 </tt>。
     * 
     * 
     * @see ModificationItem
     * @see #modifyAttributes
     */
    public final static int ADD_ATTRIBUTE = 1;

    /**
     * This constant specifies to replace an attribute with specified values.
     *<p>
     * If attribute already exists,
     * replaces all existing values with new specified values.  If the
     * attribute does not exist, creates it.  If no value is specified,
     * deletes all the values of the attribute.
     * Removal of the last value will remove the attribute if the attribute
     * is required to have at least one value.  If
     * attempting to add more than one value to a single-valued attribute,
     * throws <code>InvalidAttributeValueException</code>.
     * <p>
     * The value of this constant is <tt>2</tt>.
     *
     * <p>
     *  此常量指定用指定值替换属性。
     * p>
     * 如果属性已存在,则使用新的指定值替换所有现有值。如果该属性不存在,则创建它。如果未指定值,则删除属性的所有值。如果属性需要具有至少一个值,则删除最后一个值将删除该属性。
     * 如果尝试向单值属性添加多个值,请抛出<code> InvalidAttributeValueException </code>。
     * <p>
     *  该常数的值为<tt> 2 </tt>。
     * 
     * 
     * @see ModificationItem
     * @see #modifyAttributes
     */
    public final static int REPLACE_ATTRIBUTE = 2;

    /**
     * This constant specifies to delete
     * the specified attribute values from the attribute.
     *<p>
     * The resulting attribute has the set difference of its prior value set
     * and the specified value set.
     * If no values are specified, deletes the entire attribute.
     * If the attribute does not exist, or if some or all members of the
     * specified value set do not exist, this absence may be ignored
     * and the operation succeeds, or a NamingException may be thrown to
     * indicate the absence.
     * Removal of the last value will remove the attribute if the
     * attribute is required to have at least one value.
     * <p>
     * The value of this constant is <tt>3</tt>.
     *
     * <p>
     *  此常量指定从属性中删除指定的属性值。
     * p>
     *  结果属性具有其先前值集和指定值集的差异。如果未指定值,则删除整个属性。
     * 如果该属性不存在,或者如果指定值集合的某些或所有成员不存在,则可以忽略此缺失,并且操作成功,或者可以抛出NamingException以指示不存在。
     * 如果属性需要具有至少一个值,则删除最后一个值将删除该属性。
     * <p>
     *  此常数的值为<tt> 3 </tt>。
     * 
     * 
     * @see ModificationItem
     * @see #modifyAttributes
     */
    public final static int REMOVE_ATTRIBUTE = 3;

    /**
     * Modifies the attributes associated with a named object.
     * The order of the modifications is not specified.  Where
     * possible, the modifications are performed atomically.
     *
     * <p>
     *  修改与命名对象关联的属性。未指定修改的顺序。在可能的情况下,以原子方式进行修饰。
     * 
     * 
     * @param name
     *          the name of the object whose attributes will be updated
     * @param mod_op
     *          the modification operation, one of:
     *                  <code>ADD_ATTRIBUTE</code>,
     *                  <code>REPLACE_ATTRIBUTE</code>,
     *                  <code>REMOVE_ATTRIBUTE</code>.
     * @param attrs
     *          the attributes to be used for the modification; may not be null
     *
     * @throws  AttributeModificationException if the modification cannot
     *          be completed successfully
     * @throws  NamingException if a naming exception is encountered
     *
     * @see #modifyAttributes(Name, ModificationItem[])
     */
    public void modifyAttributes(Name name, int mod_op, Attributes attrs)
            throws NamingException;

    /**
     * Modifies the attributes associated with a named object.
     * See {@link #modifyAttributes(Name, int, Attributes)} for details.
     *
     * <p>
     *  修改与命名对象关联的属性。有关详情,请参阅{@link #modify属性(Name,int,Attributes)}。
     * 
     * 
     * @param name
     *          the name of the object whose attributes will be updated
     * @param mod_op
     *          the modification operation, one of:
     *                  <code>ADD_ATTRIBUTE</code>,
     *                  <code>REPLACE_ATTRIBUTE</code>,
     *                  <code>REMOVE_ATTRIBUTE</code>.
     * @param attrs
     *          the attributes to be used for the modification; may not be null
     *
     * @throws  AttributeModificationException if the modification cannot
     *          be completed successfully
     * @throws  NamingException if a naming exception is encountered
     */
    public void modifyAttributes(String name, int mod_op, Attributes attrs)
            throws NamingException;

    /**
     * Modifies the attributes associated with a named object using
     * an ordered list of modifications.
     * The modifications are performed
     * in the order specified.  Each modification specifies a
     * modification operation code and an attribute on which to
     * operate.  Where possible, the modifications are
     * performed atomically.
     *
     * <p>
     * 使用有序的修改列表修改与命名对象关联的属性。按指定的顺序执行修改。每个修改指定修改操作代码和要在其上操作的属性。在可能的情况下,以原子方式进行修饰。
     * 
     * 
     * @param name
     *          the name of the object whose attributes will be updated
     * @param mods
     *          an ordered sequence of modifications to be performed;
     *          may not be null
     *
     * @throws  AttributeModificationException if the modifications
     *          cannot be completed successfully
     * @throws  NamingException if a naming exception is encountered
     *
     * @see #modifyAttributes(Name, int, Attributes)
     * @see ModificationItem
     */
    public void modifyAttributes(Name name, ModificationItem[] mods)
            throws NamingException;

    /**
     * Modifies the attributes associated with a named object using
     * an ordered list of modifications.
     * See {@link #modifyAttributes(Name, ModificationItem[])} for details.
     *
     * <p>
     *  使用有序的修改列表修改与命名对象关联的属性。有关详细信息,请参阅{@link #modifyAttributes(Name,ModificationItem [])}。
     * 
     * 
     * @param name
     *          the name of the object whose attributes will be updated
     * @param mods
     *          an ordered sequence of modifications to be performed;
     *          may not be null
     *
     * @throws  AttributeModificationException if the modifications
     *          cannot be completed successfully
     * @throws  NamingException if a naming exception is encountered
     */
    public void modifyAttributes(String name, ModificationItem[] mods)
            throws NamingException;

    /**
     * Binds a name to an object, along with associated attributes.
     * If <tt>attrs</tt> is null, the resulting binding will have
     * the attributes associated with <tt>obj</tt> if <tt>obj</tt> is a
     * <tt>DirContext</tt>, and no attributes otherwise.
     * If <tt>attrs</tt> is non-null, the resulting binding will have
     * <tt>attrs</tt> as its attributes; any attributes associated with
     * <tt>obj</tt> are ignored.
     *
     * <p>
     *  为对象绑定名称以及关联的属性。
     * 如果<tt> attrs </tt>为null,则如果<tt> obj </tt>是<tt> DirContext </tt>,则结果绑定将具有与<tt> obj </tt>没有属性。
     * 如果<tt> attrs </tt>为非null,则生成的绑定将具有<tt> attrs </tt>作为其属性;将忽略与<tt> obj </tt>相关联的任何属性。
     * 
     * 
     * @param name
     *          the name to bind; may not be empty
     * @param obj
     *          the object to bind; possibly null
     * @param attrs
     *          the attributes to associate with the binding
     *
     * @throws  NameAlreadyBoundException if name is already bound
     * @throws  InvalidAttributesException if some "mandatory" attributes
     *          of the binding are not supplied
     * @throws  NamingException if a naming exception is encountered
     *
     * @see Context#bind(Name, Object)
     * @see #rebind(Name, Object, Attributes)
     */
    public void bind(Name name, Object obj, Attributes attrs)
            throws NamingException;

    /**
     * Binds a name to an object, along with associated attributes.
     * See {@link #bind(Name, Object, Attributes)} for details.
     *
     * <p>
     *  为对象绑定名称以及关联的属性。有关详细信息,请参阅{@link #bind(Name,Object,Attributes)}。
     * 
     * 
     * @param name
     *          the name to bind; may not be empty
     * @param obj
     *          the object to bind; possibly null
     * @param attrs
     *          the attributes to associate with the binding
     *
     * @throws  NameAlreadyBoundException if name is already bound
     * @throws  InvalidAttributesException if some "mandatory" attributes
     *          of the binding are not supplied
     * @throws  NamingException if a naming exception is encountered
     */
    public void bind(String name, Object obj, Attributes attrs)
            throws NamingException;

    /**
     * Binds a name to an object, along with associated attributes,
     * overwriting any existing binding.
     * If <tt>attrs</tt> is null and <tt>obj</tt> is a <tt>DirContext</tt>,
     * the attributes from <tt>obj</tt> are used.
     * If <tt>attrs</tt> is null and <tt>obj</tt> is not a <tt>DirContext</tt>,
     * any existing attributes associated with the object already bound
     * in the directory remain unchanged.
     * If <tt>attrs</tt> is non-null, any existing attributes associated with
     * the object already bound in the directory are removed and <tt>attrs</tt>
     * is associated with the named object.  If <tt>obj</tt> is a
     * <tt>DirContext</tt> and <tt>attrs</tt> is non-null, the attributes
     * of <tt>obj</tt> are ignored.
     *
     * <p>
     * 将对象的名称以及关联的属性绑定,覆盖任何现有的绑定。
     * 如果<tt> attrs </tt>为null,<tt> obj </tt>为<tt> DirContext </tt>,则使用<tt> obj </tt>的属性。
     * 如果<tt> attrs </tt>为null且<tt> obj </tt>不是<tt> DirContext </tt>,则与目录中已绑定的对象相关联的任何现有属性保持不变。
     * 如果<tt> attrs </tt>为非空,则与已在目录中绑定的对象相关联的任何现有属性都将被删除,并且<tt> attrs </tt>与命名对象相关联。
     * 如果<tt> obj </tt>是<tt> DirContext </tt>且<tt> attrs </tt>是非空的,则忽略<tt> obj </tt>的属性。
     * 
     * 
     * @param name
     *          the name to bind; may not be empty
     * @param obj
     *          the object to bind; possibly null
     * @param attrs
     *          the attributes to associate with the binding
     *
     * @throws  InvalidAttributesException if some "mandatory" attributes
     *          of the binding are not supplied
     * @throws  NamingException if a naming exception is encountered
     *
     * @see Context#bind(Name, Object)
     * @see #bind(Name, Object, Attributes)
     */
    public void rebind(Name name, Object obj, Attributes attrs)
            throws NamingException;

    /**
     * Binds a name to an object, along with associated attributes,
     * overwriting any existing binding.
     * See {@link #rebind(Name, Object, Attributes)} for details.
     *
     * <p>
     *  将对象的名称以及关联的属性绑定,覆盖任何现有的绑定。有关详细信息,请参阅{@link #rebind(Name,Object,Attributes)}。
     * 
     * 
     * @param name
     *          the name to bind; may not be empty
     * @param obj
     *          the object to bind; possibly null
     * @param attrs
     *          the attributes to associate with the binding
     *
     * @throws  InvalidAttributesException if some "mandatory" attributes
     *          of the binding are not supplied
     * @throws  NamingException if a naming exception is encountered
     */
    public void rebind(String name, Object obj, Attributes attrs)
            throws NamingException;

    /**
     * Creates and binds a new context, along with associated attributes.
     * This method creates a new subcontext with the given name, binds it in
     * the target context (that named by all but terminal atomic
     * component of the name), and associates the supplied attributes
     * with the newly created object.
     * All intermediate and target contexts must already exist.
     * If <tt>attrs</tt> is null, this method is equivalent to
     * <tt>Context.createSubcontext()</tt>.
     *
     * <p>
     *  创建和绑定新的上下文以及关联的属性。此方法创建具有给定名称的新子上下文,将其绑定在目标上下文(由除名称的终端原子组件之外的所有目标上下文中),并将提供的属性与新创建的对象相关联。
     * 所有中间和目标上下文必须已存在。如果<tt> attrs </tt>为null,则此方法等效于<tt> Context.createSubcontext()</tt>。
     * 
     * 
     * @param name
     *          the name of the context to create; may not be empty
     * @param attrs
     *          the attributes to associate with the newly created context
     * @return  the newly created context
     *
     * @throws  NameAlreadyBoundException if the name is already bound
     * @throws  InvalidAttributesException if <code>attrs</code> does not
     *          contain all the mandatory attributes required for creation
     * @throws  NamingException if a naming exception is encountered
     *
     * @see Context#createSubcontext(Name)
     */
    public DirContext createSubcontext(Name name, Attributes attrs)
            throws NamingException;

    /**
     * Creates and binds a new context, along with associated attributes.
     * See {@link #createSubcontext(Name, Attributes)} for details.
     *
     * <p>
     *  创建和绑定新的上下文以及关联的属性。有关详情,请参阅{@link #createSubcontext(Name,Attributes)}。
     * 
     * 
     * @param name
     *          the name of the context to create; may not be empty
     * @param attrs
     *          the attributes to associate with the newly created context
     * @return  the newly created context
     *
     * @throws  NameAlreadyBoundException if the name is already bound
     * @throws  InvalidAttributesException if <code>attrs</code> does not
     *          contain all the mandatory attributes required for creation
     * @throws  NamingException if a naming exception is encountered
     */
    public DirContext createSubcontext(String name, Attributes attrs)
            throws NamingException;

// -------------------- schema operations

    /**
     * Retrieves the schema associated with the named object.
     * The schema describes rules regarding the structure of the namespace
     * and the attributes stored within it.  The schema
     * specifies what types of objects can be added to the directory and where
     * they can be added; what mandatory and optional attributes an object
     * can have. The range of support for schemas is directory-specific.
     *
     * <p> This method returns the root of the schema information tree
     * that is applicable to the named object. Several named objects
     * (or even an entire directory) might share the same schema.
     *
     * <p> Issues such as structure and contents of the schema tree,
     * permission to modify to the contents of the schema
     * tree, and the effect of such modifications on the directory
     * are dependent on the underlying directory.
     *
     * <p>
     * 检索与命名对象关联的模式。该模式描述了关于命名空间的结构和存储在其中的属性的规则。模式指定可以将哪些类型的对象添加到目录以及它们可以添加到何处;对象可以具有什么强制和可选属性。
     * 对模式的支持范围是特定于目录的。
     * 
     *  <p>此方法返回适用于命名对象的模式信息树的根。几个命名对象(甚至整个目录)可能共享同一个模式。
     * 
     *  <p>诸如架构树的结构和内容,修改到架构树内容的许可以及此类修改对目录的影响等问题都取决于底层目录。
     * 
     * 
     * @param name
     *          the name of the object whose schema is to be retrieved
     * @return  the schema associated with the context; never null
     * @throws  OperationNotSupportedException if schema not supported
     * @throws  NamingException if a naming exception is encountered
     */
    public DirContext getSchema(Name name) throws NamingException;

    /**
     * Retrieves the schema associated with the named object.
     * See {@link #getSchema(Name)} for details.
     *
     * <p>
     *  检索与命名对象关联的模式。有关详细信息,请参阅{@link #getSchema(Name)}。
     * 
     * 
     * @param name
     *          the name of the object whose schema is to be retrieved
     * @return  the schema associated with the context; never null
     * @throws  OperationNotSupportedException if schema not supported
     * @throws  NamingException if a naming exception is encountered
     */
    public DirContext getSchema(String name) throws NamingException;

    /**
     * Retrieves a context containing the schema objects of the
     * named object's class definitions.
     *<p>
     * One category of information found in directory schemas is
     * <em>class definitions</em>.  An "object class" definition
     * specifies the object's <em>type</em> and what attributes (mandatory
     * and optional) the object must/can have. Note that the term
     * "object class" being referred to here is in the directory sense
     * rather than in the Java sense.
     * For example, if the named object is a directory object of
     * "Person" class, <tt>getSchemaClassDefinition()</tt> would return a
     * <tt>DirContext</tt> representing the (directory's) object class
     * definition of "Person".
     *<p>
     * The information that can be retrieved from an object class definition
     * is directory-dependent.
     *<p>
     * Prior to JNDI 1.2, this method
     * returned a single schema object representing the class definition of
     * the named object.
     * Since JNDI 1.2, this method returns a <tt>DirContext</tt> containing
     * all of the named object's class definitions.
     *
     * <p>
     *  检索包含命名对象的类定义的模式对象的上下文。
     * p>
     * 在目录模式中找到的一类信息是<em>类定义</em>。 "对象类"定义指定对象的<em>类型</em>以及对象必须/可以具有的属性(强制和可选)。
     * 注意,这里引用的术语"对象类"是在目录意义上而不是在Java意义上。
     * 例如,如果命名对象是"Person"类的目录对象,则<tt> getSchemaClassDefinition()</tt>将返回表示(目录的)"Person"的对象类定义的<tt> DirContex
     * t </tt> 。
     * 注意,这里引用的术语"对象类"是在目录意义上而不是在Java意义上。
     * p>
     *  可以从对象类定义检索的信息与目录相关。
     * p>
     *  在JNDI 1.2之前,此方法返回一个表示命名对象的类定义的模式对象。从JNDI 1.2开始,此方法返回一个包含所有命名对象的类定义的<tt> DirContext </tt>。
     * 
     * 
     * @param name
     *          the name of the object whose object class
     *          definition is to be retrieved
     * @return  the <tt>DirContext</tt> containing the named
     *          object's class definitions; never null
     *
     * @throws  OperationNotSupportedException if schema not supported
     * @throws  NamingException if a naming exception is encountered
     */
    public DirContext getSchemaClassDefinition(Name name)
            throws NamingException;

    /**
     * Retrieves a context containing the schema objects of the
     * named object's class definitions.
     * See {@link #getSchemaClassDefinition(Name)} for details.
     *
     * <p>
     *  检索包含命名对象的类定义的模式对象的上下文。有关详细信息,请参阅{@link #getSchemaClassDefinition(Name)}。
     * 
     * 
     * @param name
     *          the name of the object whose object class
     *          definition is to be retrieved
     * @return  the <tt>DirContext</tt> containing the named
     *          object's class definitions; never null
     *
     * @throws  OperationNotSupportedException if schema not supported
     * @throws  NamingException if a naming exception is encountered
     */
    public DirContext getSchemaClassDefinition(String name)
            throws NamingException;

// -------------------- search operations

    /**
     * Searches in a single context for objects that contain a
     * specified set of attributes, and retrieves selected attributes.
     * The search is performed using the default
     * <code>SearchControls</code> settings.
     * <p>
     * For an object to be selected, each attribute in
     * <code>matchingAttributes</code> must match some attribute of the
     * object.  If <code>matchingAttributes</code> is empty or
     * null, all objects in the target context are returned.
     *<p>
     * An attribute <em>A</em><sub>1</sub> in
     * <code>matchingAttributes</code> is considered to match an
     * attribute <em>A</em><sub>2</sub> of an object if
     * <em>A</em><sub>1</sub> and <em>A</em><sub>2</sub> have the same
     * identifier, and each value of <em>A</em><sub>1</sub> is equal
     * to some value of <em>A</em><sub>2</sub>.  This implies that the
     * order of values is not significant, and that
     * <em>A</em><sub>2</sub> may contain "extra" values not found in
     * <em>A</em><sub>1</sub> without affecting the comparison.  It
     * also implies that if <em>A</em><sub>1</sub> has no values, then
     * testing for a match is equivalent to testing for the presence
     * of an attribute <em>A</em><sub>2</sub> with the same
     * identifier.
     *<p>
     * The precise definition of "equality" used in comparing attribute values
     * is defined by the underlying directory service.  It might use the
     * <code>Object.equals</code> method, for example, or might use a schema
     * to specify a different equality operation.
     * For matching based on operations other than equality (such as
     * substring comparison) use the version of the <code>search</code>
     * method that takes a filter argument.
     * <p>
     * When changes are made to this <tt>DirContext</tt>,
     * the effect on enumerations returned by prior calls to this method
     * is undefined.
     *<p>
     * If the object does not have the attribute
     * specified, the directory will ignore the nonexistent attribute
     * and return the requested attributes that the object does have.
     *<p>
     * A directory might return more attributes than was requested
     * (see <strong>Attribute Type Names</strong> in the class description),
     * but is not allowed to return arbitrary, unrelated attributes.
     *<p>
     * See also <strong>Operational Attributes</strong> in the class
     * description.
     *
     * <p>
     *  在单个上下文中搜索包含指定的一组属性的对象,并检索所选的属性。使用默认的<code> SearchControls </code>设置执行搜索。
     * <p>
     *  对于要选择的对象,<code> matchingAttributes </code>中的每个属性必须匹配对象的某个属性。
     * 如果<code> matchingAttributes </code>为空或为空,则返回目标上下文中的所有对象。
     * p>
     * matchingAttributes </code>中的属性<em> A </em> <sub> 1 </sub>被视为匹配属性<em> A </em> <sub> 2 </sub>对象如果<em> A
     *  </em> <sub> 1 </sub>和<em> A </em> <sub> 2 </sub>具有相同的标识符, / em> <sub> 1 </sub>等于<em> A </em> <sub> 2
     *  </sub>的某个值。
     * 这意味着值的顺序不重要,并且<em> A </em> <sub> 2 </sub>可能包含在<em> A </em> <sub> 1中未找到的"extra" </sub>,而不影响比较。
     * 这也意味着如果<em> A </em> <sub> 1 </sub>没有值,则测试匹配等同于测试属性<em> A </em> <sub > 2 </sub>具有相同的标识符。
     * p>
     *  在比较属性值时使用的"相等"的精确定义由基础目录服务定义。它可能使用<code> Object.equals </code>方法,或者可能使用模式来指定不同的等式运算。
     * 对于基于除等式之外的操作的匹配(例如子串比较),使用接受过滤器参数的<code> search </code>方法的版本。
     * <p>
     *  当对此<tt> DirContext </tt>进行更改时,对此方法的先前调用返回的枚举的影响未定义。
     * p>
     *  如果对象没有指定的属性,该目录将忽略不存在的属性,并返回对象具有的请求的属性。
     * p>
     * 目录可能返回的属性超过请求的属性(请参阅类描述中的<strong>属性类型名称</strong>),但不允许返回任意不相关的属性。
     * p>
     *  另请参见类描述中的<strong>操作属性</strong>。
     * 
     * 
     * @param name
     *          the name of the context to search
     * @param matchingAttributes
     *          the attributes to search for.  If empty or null,
     *          all objects in the target context are returned.
     * @param attributesToReturn
     *          the attributes to return.  null indicates that
     *          all attributes are to be returned;
     *          an empty array indicates that none are to be returned.
     * @return
     *          a non-null enumeration of <tt>SearchResult</tt> objects.
     *          Each <tt>SearchResult</tt> contains the attributes
     *          identified by <code>attributesToReturn</code>
     *          and the name of the corresponding object, named relative
     *          to the context named by <code>name</code>.
     * @throws  NamingException if a naming exception is encountered
     *
     * @see SearchControls
     * @see SearchResult
     * @see #search(Name, String, Object[], SearchControls)
     */
    public NamingEnumeration<SearchResult>
        search(Name name,
               Attributes matchingAttributes,
               String[] attributesToReturn)
        throws NamingException;

    /**
     * Searches in a single context for objects that contain a
     * specified set of attributes, and retrieves selected attributes.
     * See {@link #search(Name, Attributes, String[])} for details.
     *
     * <p>
     *  在单个上下文中搜索包含指定的一组属性的对象,并检索所选的属性。有关详细信息,请参阅{@link #search(Name,Attributes,String [])}。
     * 
     * 
     * @param name
     *          the name of the context to search
     * @param matchingAttributes
     *          the attributes to search for
     * @param attributesToReturn
     *          the attributes to return
     * @return  a non-null enumeration of <tt>SearchResult</tt> objects
     * @throws  NamingException if a naming exception is encountered
     */
    public NamingEnumeration<SearchResult>
        search(String name,
               Attributes matchingAttributes,
               String[] attributesToReturn)
        throws NamingException;

    /**
     * Searches in a single context for objects that contain a
     * specified set of attributes.
     * This method returns all the attributes of such objects.
     * It is equivalent to supplying null as
     * the <tt>atributesToReturn</tt> parameter to the method
     * <code>search(Name, Attributes, String[])</code>.
     * <br>
     * See {@link #search(Name, Attributes, String[])} for a full description.
     *
     * <p>
     *  在单个上下文中搜索包含指定的一组属性的对象。此方法返回此类对象的所有属性。
     * 它等效于将<tt> atributesToReturn </tt>参数提供给方法<code> search(Name,Attributes,String [])</code>。
     * <br>
     *  有关完整说明,请参阅{@link #search(Name,Attributes,String [])}。
     * 
     * 
     * @param name
     *          the name of the context to search
     * @param matchingAttributes
     *          the attributes to search for
     * @return  an enumeration of <tt>SearchResult</tt> objects
     * @throws  NamingException if a naming exception is encountered
     *
     * @see #search(Name, Attributes, String[])
     */
    public NamingEnumeration<SearchResult>
        search(Name name, Attributes matchingAttributes)
        throws NamingException;

    /**
     * Searches in a single context for objects that contain a
     * specified set of attributes.
     * See {@link #search(Name, Attributes)} for details.
     *
     * <p>
     *  在单个上下文中搜索包含指定的一组属性的对象。有关详情,请参阅{@link #search(Name,Attributes)}。
     * 
     * 
     * @param name
     *          the name of the context to search
     * @param matchingAttributes
     *          the attributes to search for
     * @return  an enumeration of <tt>SearchResult</tt> objects
     * @throws  NamingException if a naming exception is encountered
     */
    public NamingEnumeration<SearchResult>
        search(String name, Attributes matchingAttributes)
        throws NamingException;

    /**
     * Searches in the named context or object for entries that satisfy the
     * given search filter.  Performs the search as specified by
     * the search controls.
     * <p>
     * The format and interpretation of <code>filter</code> follows RFC 2254
     * with the
     * following interpretations for <code>attr</code> and <code>value</code>
     * mentioned in the RFC.
     * <p>
     * <code>attr</code> is the attribute's identifier.
     * <p>
     * <code>value</code> is the string representation the attribute's value.
     * The translation of this string representation into the attribute's value
     * is directory-specific.
     * <p>
     * For the assertion "someCount=127", for example, <code>attr</code>
     * is "someCount" and <code>value</code> is "127".
     * The provider determines, based on the attribute ID ("someCount")
     * (and possibly its schema), that the attribute's value is an integer.
     * It then parses the string "127" appropriately.
     *<p>
     * Any non-ASCII characters in the filter string should be
     * represented by the appropriate Java (Unicode) characters, and
     * not encoded as UTF-8 octets.  Alternately, the
     * "backslash-hexcode" notation described in RFC 2254 may be used.
     *<p>
     * If the directory does not support a string representation of
     * some or all of its attributes, the form of <code>search</code> that
     * accepts filter arguments in the form of Objects can be used instead.
     * The service provider for such a directory would then translate
     * the filter arguments to its service-specific representation
     * for filter evaluation.
     * See <code>search(Name, String, Object[], SearchControls)</code>.
     * <p>
     * RFC 2254 defines certain operators for the filter, including substring
     * matches, equality, approximate match, greater than, less than.  These
     * operators are mapped to operators with corresponding semantics in the
     * underlying directory. For example, for the equals operator, suppose
     * the directory has a matching rule defining "equality" of the
     * attributes in the filter. This rule would be used for checking
     * equality of the attributes specified in the filter with the attributes
     * of objects in the directory. Similarly, if the directory has a
     * matching rule for ordering, this rule would be used for
     * making "greater than" and "less than" comparisons.
     *<p>
     * Not all of the operators defined in RFC 2254 are applicable to all
     * attributes.  When an operator is not applicable, the exception
     * <code>InvalidSearchFilterException</code> is thrown.
     * <p>
     * The result is returned in an enumeration of <tt>SearchResult</tt>s.
     * Each <tt>SearchResult</tt> contains the name of the object
     * and other information about the object (see SearchResult).
     * The name is either relative to the target context of the search
     * (which is named by the <code>name</code> parameter), or
     * it is a URL string. If the target context is included in
     * the enumeration (as is possible when
     * <code>cons</code> specifies a search scope of
     * <code>SearchControls.OBJECT_SCOPE</code> or
     * <code>SearchControls.SUBSTREE_SCOPE</code>), its name is the empty
     * string. The <tt>SearchResult</tt> may also contain attributes of the
     * matching object if the <tt>cons</tt> argument specified that attributes
     * be returned.
     *<p>
     * If the object does not have a requested attribute, that
     * nonexistent attribute will be ignored.  Those requested
     * attributes that the object does have will be returned.
     *<p>
     * A directory might return more attributes than were requested
     * (see <strong>Attribute Type Names</strong> in the class description)
     * but is not allowed to return arbitrary, unrelated attributes.
     *<p>
     * See also <strong>Operational Attributes</strong> in the class
     * description.
     *
     * <p>
     *  在命名上下文或对象中搜索满足给定搜索过滤器的条目。根据搜索控件指定执行搜索。
     * <p>
     *  <code> filter </code>的格式和解释遵循RFC 2254,对RFC中提及的<code> attr </code>和<code> value </code>有以下解释。
     * <p>
     *  <code> attr </code>是属性的标识符。
     * <p>
     *  <code> value </code>是属性值的字符串表示形式。将此字符串表示转换为属性的值是特定于目录的。
     * <p>
     * 对于断言"someCount = 127",例如,<code> attr </code>是"someCount",<code> value </code>是"127"。
     * 提供者基于属性ID("someCount")(以及可能的其模式)确定属性的值是整数。然后它适当地解析字符串"127"。
     * p>
     *  过滤器字符串中的任何非ASCII字符都应由适当的Java(Unicode)字符表示,而不能编码为UTF-8八位字节。或者,可以使用RFC 2254中描述的"反斜杠 - 十六进制码"符号。
     * p>
     *  如果目录不支持其某些或所有属性的字符串表示,则可以使用以对象形式接受过滤器参数的<code> search </code>形式。
     * 然后,这样的目录的服务提供商将过滤器参数转换为其服务特定的表示以用于过滤器评估。
     * 参见<code> search(Name,String,Object [],SearchControls)</code>。
     * <p>
     * RFC 2254为过滤器定义了某些运算符,包括子字符串匹配,等于,近似匹配,大于,小于。这些运算符映射到底层目录中具有相应语义的运算符。
     * 例如,对于equals运算符,假设目录具有在过滤器中定义属性的"等同性"的匹配规则。此规则将用于检查过滤器中指定的属性与目录中对象的属性的相等性。
     * 类似地,如果目录具有用于排序的匹配规则,则该规则将用于进行"大于"和"小于"比较。
     * p>
     *  并非RFC 2254中定义的所有运算符都适用于所有属性。当操作符不适用时,抛出异常<code> InvalidSearchFilterException </code>。
     * <p>
     * 结果以<tt> SearchResult </tt>的枚举返回。每个<tt> SearchResult </tt>包含对象的名称和有关对象的其他信息(请参阅SearchResult)。
     * 该名称是相对于搜索的目标上下文(由<code> name </code>参数命名),或者它是一个URL字符串。
     * 如果在枚举中包括目标上下文(在<code> cons </code>指定搜索范围<code> SearchControls.OBJECT_SCOPE </code>或<code> SearchContr
     * ols.SUBSTREE_SCOPE </code>时可能)它的名称是空字符串。
     * 该名称是相对于搜索的目标上下文(由<code> name </code>参数命名),或者它是一个URL字符串。
     * 如果<tt> cons </tt>参数指定返回属性,则<tt> SearchResult </tt>也可能包含匹配对象的属性。
     * p>
     *  如果对象没有请求的属性,那么不存在的属性将被忽略。将返回对象具有的那些请求的属性。
     * p>
     *  目录可能会返回比请求的属性更多的属性(请参阅类描述中的<strong>属性类型名称</strong>),但不允许返回任意不相关的属性。
     * p>
     *  另请参见类描述中的<strong>操作属性</strong>。
     * 
     * 
     * @param name
     *          the name of the context or object to search
     * @param filter
     *          the filter expression to use for the search; may not be null
     * @param cons
     *          the search controls that control the search.  If null,
     *          the default search controls are used (equivalent
     *          to <tt>(new SearchControls())</tt>).
     * @return  an enumeration of <tt>SearchResult</tt>s of
     *          the objects that satisfy the filter; never null
     *
     * @throws  InvalidSearchFilterException if the search filter specified is
     *          not supported or understood by the underlying directory
     * @throws  InvalidSearchControlsException if the search controls
     *          contain invalid settings
     * @throws  NamingException if a naming exception is encountered
     *
     * @see #search(Name, String, Object[], SearchControls)
     * @see SearchControls
     * @see SearchResult
     */
    public NamingEnumeration<SearchResult>
        search(Name name,
               String filter,
               SearchControls cons)
        throws NamingException;

    /**
     * Searches in the named context or object for entries that satisfy the
     * given search filter.  Performs the search as specified by
     * the search controls.
     * See {@link #search(Name, String, SearchControls)} for details.
     *
     * <p>
     *  在命名上下文或对象中搜索满足给定搜索过滤器的条目。根据搜索控件指定执行搜索。有关详情,请参阅{@link #search(Name,String,SearchControls)}。
     * 
     * 
     * @param name
     *          the name of the context or object to search
     * @param filter
     *          the filter expression to use for the search; may not be null
     * @param cons
     *          the search controls that control the search.  If null,
     *          the default search controls are used (equivalent
     *          to <tt>(new SearchControls())</tt>).
     *
     * @return  an enumeration of <tt>SearchResult</tt>s for
     *          the objects that satisfy the filter.
     * @throws  InvalidSearchFilterException if the search filter specified is
     *          not supported or understood by the underlying directory
     * @throws  InvalidSearchControlsException if the search controls
     *          contain invalid settings
     * @throws  NamingException if a naming exception is encountered
     */
    public NamingEnumeration<SearchResult>
        search(String name,
               String filter,
               SearchControls cons)
        throws NamingException;

    /**
     * Searches in the named context or object for entries that satisfy the
     * given search filter.  Performs the search as specified by
     * the search controls.
     *<p>
     * The interpretation of <code>filterExpr</code> is based on RFC
     * 2254.  It may additionally contain variables of the form
     * <code>{i}</code> -- where <code>i</code> is an integer -- that
     * refer to objects in the <code>filterArgs</code> array.  The
     * interpretation of <code>filterExpr</code> is otherwise
     * identical to that of the <code>filter</code> parameter of the
     * method <code>search(Name, String, SearchControls)</code>.
     *<p>
     * When a variable <code>{i}</code> appears in a search filter, it
     * indicates that the filter argument <code>filterArgs[i]</code>
     * is to be used in that place.  Such variables may be used
     * wherever an <em>attr</em>, <em>value</em>, or
     * <em>matchingrule</em> production appears in the filter grammar
     * of RFC 2254, section 4.  When a string-valued filter argument
     * is substituted for a variable, the filter is interpreted as if
     * the string were given in place of the variable, with any
     * characters having special significance within filters (such as
     * <code>'*'</code>) having been escaped according to the rules of
     * RFC 2254.
     *<p>
     * For directories that do not use a string representation for
     * some or all of their attributes, the filter argument
     * corresponding to an attribute value may be of a type other than
     * String.  Directories that support unstructured binary-valued
     * attributes, for example, should accept byte arrays as filter
     * arguments.  The interpretation (if any) of filter arguments of
     * any other type is determined by the service provider for that
     * directory, which maps the filter operations onto operations with
     * corresponding semantics in the underlying directory.
     *<p>
     * This method returns an enumeration of the results.
     * Each element in the enumeration contains the name of the object
     * and other information about the object (see <code>SearchResult</code>).
     * The name is either relative to the target context of the search
     * (which is named by the <code>name</code> parameter), or
     * it is a URL string. If the target context is included in
     * the enumeration (as is possible when
     * <code>cons</code> specifies a search scope of
     * <code>SearchControls.OBJECT_SCOPE</code> or
     * <code>SearchControls.SUBSTREE_SCOPE</code>),
     * its name is the empty string.
     *<p>
     * The <tt>SearchResult</tt> may also contain attributes of the matching
     * object if the <tt>cons</tt> argument specifies that attributes be
     * returned.
     *<p>
     * If the object does not have a requested attribute, that
     * nonexistent attribute will be ignored.  Those requested
     * attributes that the object does have will be returned.
     *<p>
     * A directory might return more attributes than were requested
     * (see <strong>Attribute Type Names</strong> in the class description)
     * but is not allowed to return arbitrary, unrelated attributes.
     *<p>
     * If a search filter with invalid variable substitutions is provided
     * to this method, the result is undefined.
     * When changes are made to this DirContext,
     * the effect on enumerations returned by prior calls to this method
     * is undefined.
     *<p>
     * See also <strong>Operational Attributes</strong> in the class
     * description.
     *
     * <p>
     * 在命名上下文或对象中搜索满足给定搜索过滤器的条目。根据搜索控件指定执行搜索。
     * p>
     *  <code> filterExpr </code>的解释基于RFC 2254.它可以另外包含<code> {i} </code>形式的变量 - 其中<code> i </code>是一个整数 -  -
     *  指代<code> filterArgs </code>数组中的对象。
     *  <code> filterExpr </code>的解释与方法<code> search(Name,String,SearchControls)</code>的<code> filter </code>
     * 参数的解释相同。
     * p>
     *  当变量<code> {i} </code>出现在搜索过滤器中时,它指示将在该位置使用过滤器参数<code> filterArgs [i] </code>。
     * 在RFC 2254的第4部分的过滤器语法中出现<em> attr </em>,<em>值</em>或<em> matchingrule </em>值过滤器参数替换变量,过滤器被解释为如果给定字符串代替变
     * 量,任何在过滤器(例如<code>'*'</code>)中具有特殊意义的字符已经被根据RFC 2254的规则进行转义。
     *  当变量<code> {i} </code>出现在搜索过滤器中时,它指示将在该位置使用过滤器参数<code> filterArgs [i] </code>。
     * p>
     * 对于不对其某些或所有属性使用字符串表示的目录,与属性值对应的过滤器参数可以是除了String之外的类型。支持非结构化二进制值属性的目录,例如,应该接受字节数组作为过滤器参数。
     * 任何其他类型的过滤器参数的解释(如果有的话)由该目录的服务提供者确定,其将过滤器操作映射到具有基本目录中的对应语义的操作。
     * p>
     *  此方法返回结果的枚举。枚举中的每个元素都包含对象的名称和关于对象的其他信息(请参阅<code> SearchResult </code>)。
     * 该名称是相对于搜索的目标上下文(由<code> name </code>参数命名),或者它是一个URL字符串。
     * 如果在枚举中包括目标上下文(在<code> cons </code>指定搜索范围<code> SearchControls.OBJECT_SCOPE </code>或<code> SearchContr
     * 
     * @param name
     *          the name of the context or object to search
     * @param filterExpr
     *          the filter expression to use for the search.
     *          The expression may contain variables of the
     *          form "<code>{i}</code>" where <code>i</code>
     *          is a nonnegative integer.  May not be null.
     * @param filterArgs
     *          the array of arguments to substitute for the variables
     *          in <code>filterExpr</code>.  The value of
     *          <code>filterArgs[i]</code> will replace each
     *          occurrence of "<code>{i}</code>".
     *          If null, equivalent to an empty array.
     * @param cons
     *          the search controls that control the search.  If null,
     *          the default search controls are used (equivalent
     *          to <tt>(new SearchControls())</tt>).
     * @return  an enumeration of <tt>SearchResult</tt>s of the objects
     *          that satisfy the filter; never null
     *
     * @throws  ArrayIndexOutOfBoundsException if <tt>filterExpr</tt> contains
     *          <code>{i}</code> expressions where <code>i</code> is outside
     *          the bounds of the array <code>filterArgs</code>
     * @throws  InvalidSearchControlsException if <tt>cons</tt> contains
     *          invalid settings
     * @throws  InvalidSearchFilterException if <tt>filterExpr</tt> with
     *          <tt>filterArgs</tt> represents an invalid search filter
     * @throws  NamingException if a naming exception is encountered
     *
     * @see #search(Name, Attributes, String[])
     * @see java.text.MessageFormat
     */
    public NamingEnumeration<SearchResult>
        search(Name name,
               String filterExpr,
               Object[] filterArgs,
               SearchControls cons)
        throws NamingException;

    /**
     * Searches in the named context or object for entries that satisfy the
     * given search filter.  Performs the search as specified by
     * the search controls.
     * See {@link #search(Name, String, Object[], SearchControls)} for details.
     *
     * <p>
     * ols.SUBSTREE_SCOPE </code>时可能)它的名称是空字符串。
     * 该名称是相对于搜索的目标上下文(由<code> name </code>参数命名),或者它是一个URL字符串。
     * p>
     *  如果<tt> cons </tt>参数指定返回属性,则<tt> SearchResult </tt>也可能包含匹配对象的属性。
     * p>
     *  如果对象没有请求的属性,那么不存在的属性将被忽略。将返回对象具有的那些请求的属性。
     * p>
     * 目录可能会返回比请求的属性更多的属性(请参阅类描述中的<strong>属性类型名称</strong>),但不允许返回任意不相关的属性。
     * p>
     * 
     * @param name
     *          the name of the context or object to search
     * @param filterExpr
     *          the filter expression to use for the search.
     *          The expression may contain variables of the
     *          form "<code>{i}</code>" where <code>i</code>
     *          is a nonnegative integer.  May not be null.
     * @param filterArgs
     *          the array of arguments to substitute for the variables
     *          in <code>filterExpr</code>.  The value of
     *          <code>filterArgs[i]</code> will replace each
     *          occurrence of "<code>{i}</code>".
     *          If null, equivalent to an empty array.
     * @param cons
     *          the search controls that control the search.  If null,
     *          the default search controls are used (equivalent
     *          to <tt>(new SearchControls())</tt>).
     * @return  an enumeration of <tt>SearchResult</tt>s of the objects
     *          that satisfy the filter; never null
     *
     * @throws  ArrayIndexOutOfBoundsException if <tt>filterExpr</tt> contains
     *          <code>{i}</code> expressions where <code>i</code> is outside
     *          the bounds of the array <code>filterArgs</code>
     * @throws  InvalidSearchControlsException if <tt>cons</tt> contains
     *          invalid settings
     * @throws  InvalidSearchFilterException if <tt>filterExpr</tt> with
     *          <tt>filterArgs</tt> represents an invalid search filter
     * @throws  NamingException if a naming exception is encountered
     */
    public NamingEnumeration<SearchResult>
        search(String name,
               String filterExpr,
               Object[] filterArgs,
               SearchControls cons)
        throws NamingException;
}
