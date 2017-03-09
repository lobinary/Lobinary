/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 2000, Oracle and/or its affiliates. All rights reserved.
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

package org.omg.CORBA;

/**
 * An object used in <code>Request</code> operations
 * to specify the context object in which context strings
 * must be resolved before being sent along with the request invocation.
 * A <code>Context</code> object
 * contains a list of properties in the form of <code>NamedValue</code>
 * objects. These properties represent information
 * about the client, the environment, or the circumstances of a request
 * and generally are properties that might be inconvenient
 * to pass as parameters.
 * <P>
 * A <code>Context</code> object is created by first calling the
 * <code>ORB</code> method <code>get_default_context</code>
 * and then calling the method <code>create_child</code> on the
 * default context.
 * <P>
 * Each property in a <code>Context</code> object is represented by
 * a <code>NamedValue</code> object.  The property name is contained
 * in the <code>NamedValue</code> object's <code>name</code> field, and
 * the value associated with the name is contained in the <code>Any</code>
 * object that was assigned to the <code>NamedValue</code> object's
 * <code>value</code> field.
 * <P>
 * <code>Context</code> properties can represent a portion of a client's
 * or application's environment that is meant to be propagated to
 * (and made implicitly part of) a server's environment.
 * (Examples might be a window identifier or user preference information).
 * Once a server has been invoked (that is, after the properties are
 * propagated), the server may query its <code>Context</code> object
 * for these properties using the method <code>get_values</code>.
 *
 *<P>
 * When an operation declaration includes a context clause,
 * the stubs and skeletons will have an additional argument
 * added for the context.  When an operation invocation occurs,
 * the ORB causes the properties that were named in the operation
 * definition in IDL and
 * that are present in the client's <code>Context</code> object
 * to be provided in the <code>Context</code> object parameter to
 * the invoked method.
 * <P>
 * <code>Context</code> property names (which are strings)
 * typically have the form of an OMG IDL identifier or
 * a series of OMG IDL identifiers separated by periods.
 * A context property name pattern is either a property name
 * or a property name followed by a single "*".  A property
 * name pattern without a trailing "*" is said to match only
 * itself.  A property name pattern of the form "&lt;name&gt;*" matches any
 * property name that starts with &lt;name&gt; and continues with zero
 * or more additional characters.
 * <P>
 * Property name patterns are used in the context clause of
 * an operation definition and as a parameter for the
 * method <code>Context.get_values</code>.
 * <P>
 * <code>Context</code> objects may be "chained" together to achieve a
 * particular defaulting behavior.  A <code>Context</code>
 * object created with the method <code>create_child</code> will
 * be chained to its parent (the <code>Context</code> object
 * that created it), and that means that the parent will be searched
 * after the child in a search for property names.
 *<P>
 * Properties defined in a particular <code>Context</code> object
 * effectively override those properties in the next higher level.
 * The scope used in a search for properties may be restricted by specifying a
 * starting scope and by using the flag <code>CTX_RESTRICT_SCOPE</code>
 * when invoking the method <code>get_values</code>.
 * <P>
 * A <code>Context</code> object may be named for purposes of specifying
 * a starting search scope.
 *
 * <p>
 *  在<code> Request </code>操作中使用的对象,用于指定在与请求调用一起发送之前必须解析上下文字符串的上下文对象。
 *  <code> Context </code>对象包含<code> NamedValue </code>对象形式的属性列表。
 * 这些属性表示关于客户端,环境或请求的情况的信息,并且通常是可能不方便作为参数传递的属性。
 * <P>
 *  通过首先调用<code> ORB </code>方法<code> get_default_context </code>然后在默认上下文中调用<code> create_child </code>方法
 * 来创建<code> Context </code> 。
 * <P>
 *  <code> Context </code>对象中的每个属性由<code> NamedValue </code>对象表示。
 * 属性名称包含在<code> NamedValue </code>对象的<code> name </code>字段中,并且与该名称相关联的值包含在分配给的<code> Any </code> <code>
 *  NamedValue </code>对象的<code>值</code>字段。
 *  <code> Context </code>对象中的每个属性由<code> NamedValue </code>对象表示。
 * <P>
 * <code>上下文</code>属性可以表示客户端或应用程序环境的一部分,该环境意在被传播到服务器的环境中(并隐式地成为其一部分)。 (示例可以是窗口标识符或用户偏好信息)。
 * 一旦服务器被调用(即,在属性被传播之后),服务器可以使用方法<code> get_values </code>来查询其<code> Context </code>对象的这些属性。
 * 
 * P>
 *  当操作声明包括上下文子句时,存根和骨架将为上下文添加一个附加参数。
 * 当发生操作调用时,ORB使得在IDL中的操作定义中被命名并且存在于客户端的<code> Context </code>对象中的属性在<code> Context </code>对象中提供参数调用方法。
 * <P>
 *  <code>上下文</code>属性名称(它们是字符串)通常具有以句点分隔的OMG IDL标识符或一系列OMG IDL标识符的形式。上下文属性名称模式是属性名称或属性名称后跟单个"*"。
 * 没有尾随"*"的属性名称模式据说仅匹配其自身。 "&lt; name&gt; *"形式的属性名模式匹配以&lt; name&gt; *开头的任何属性名称。并继续零个或多个附加字符。
 * <P>
 * 属性名称模式在操作定义的上下文子句中使用,并且作为方法<code> Context.get_values </code>的参数。
 * <P>
 *  <code>上下文</code>对象可以"链接"在一起以实现特定的默认行为。
 * 使用方法<code> create_child </code>创建的<code> Context </code>对象将链接到其父代(创建它的<code> Context </code>对象),这意味着父
 * 代将在搜索之后的子搜索属性名称。
 *  <code>上下文</code>对象可以"链接"在一起以实现特定的默认行为。
 * P>
 *  在特定<code> Context </code>对象中定义的属性有效地覆盖了下一个更高级别中的那些属性。
 * 在搜索属性时使用的范围可以通过指定起始范围和在调用方法<code> get_values </code>时使用标志<code> CTX_RESTRICT_SCOPE </code>来加以限制。
 * <P>
 *  可以出于指定开始搜索范围的目的而命名<code> Context </code>对象。
 * 
 * 
 * @since   JDK1.2
 */

public abstract class Context {

    /**
     * Retrieves the name of this <code>Context</code> object.
     *
     * <p>
     *  检索此<code> Context </code>对象的名称。
     * 
     * 
     * @return                  the name of this <code>Context</code> object
     */

    public abstract String context_name();


    /**
     * Retrieves the parent of this <code>Context</code> object.
     *
     * <p>
     *  检索此<code> Context </code>对象的父代。
     * 
     * 
     * @return                  the <code>Context</code> object that is the
     *                    parent of this <code>Context</code> object
     */

    public abstract Context parent();

    /**
     * Creates a <code>Context</code> object with the given string as its
     * name and with this <code>Context</code> object set as its parent.
     * <P>
     * The new <code>Context</code> object is chained into its parent
     * <code>Context</code> object.  This means that in a search for
     * matching property names, if a match is not found in this context,
     * the search will continue in the parent.  If that is not successful,
     * the search will continue in the grandparent, if there is one, and
     * so on.
     *
     *
     * <p>
     *  创建一个以给定字符串作为其名称并将此<code> Context </code>对象设置为其父对象的<code> Context </code>对象。
     * <P>
     * 新的<code> Context </code>对象被链接到其父<code> Context </code>对象中。这意味着在搜索匹配的属性名称时,如果在此上下文中找不到匹配,则搜索将在父级中继续。
     * 如果这不成功,搜索将继续在祖父母,如果有一个,等等。
     * 
     * 
     * @param child_ctx_name    the <code>String</code> object to be set as
     *                        the name of the new <code>Context</code> object
     * @return                  the newly-created child <code>Context</code> object
     *                    initialized with the specified name
     */

    public abstract Context create_child(String child_ctx_name);

    /**
     * Creates a <code>NamedValue</code> object and adds it to this
     * <code>Context</code> object.  The <code>name</code> field of the
     * new <code>NamedValue</code> object is set to the given string,
     * the <code>value</code> field is set to the given <code>Any</code>
     * object, and the <code>flags</code> field is set to zero.
     *
     * <p>
     *  创建一个<code> NamedValue </code>对象并将其添加到此<code> Context </code>对象。
     * 新的<code> NamedValue </code>对象的<code> name </code>字段设置为给定的字符串,<code> value </code>字段设置为给定的<code> Any < code>
     * 对象,并且将<code> flags </code>字段设置为零。
     *  创建一个<code> NamedValue </code>对象并将其添加到此<code> Context </code>对象。
     * 
     * 
     * @param propname          the name of the property to be set
     * @param propvalue         the <code>Any</code> object to which the
     *                        value of the property will be set.  The
     *                        <code>Any</code> object's <code>value</code>
     *                        field contains the value to be associated
     *                        with the given propname; the
     *                        <code>kind</code> field must be set to
     *                        <code>TCKind.tk_string</code>.
     */

    public abstract void set_one_value(String propname, Any propvalue);

    /**
       I Sets one or more property values in this <code>Context</code>
       * object. The <code>NVList</code> supplied to this method
       * contains one or more <code>NamedValue</code> objects.
       * In each <code>NamedValue</code> object,
       * the <code>name</code> field holds the name of the property, and
       * the <code>flags</code> field must be set to zero.
       * The <code>NamedValue</code> object's <code>value</code> field
       * contains an <code>Any</code> object, which, in turn, contains the value
       * for the property.  Since the value is always a string,
       * the <code>Any</code> object must have the <code>kind</code>
       * field of its <code>TypeCode</code> set to <code>TCKind.tk_string</code>.
       *
       * <p>
       *  I在此<code> Context </code>对象中设置一个或多个属性值。
       * 提供给此方法的<code> NVList </code>包含一个或多个<code> NamedValue </code>对象。
       * 在每个<code> NamedValue </code>对象中,<code> name </code>字段保存属性的名称,并且<code> flags </code>字段必须设置为零。
       *  <code> NamedValue </code>对象的<code> value </code>字段包含一个<code> Any </code>对象,其中包含属性的值。
       * 由于该值总是一个字符串,所以<code> Any </code>对象必须将其<code> TypeCode </code>的<code> kind </code>字段设置为<code> TCKind.t
       * k_string <代码>。
       *  <code> NamedValue </code>对象的<code> value </code>字段包含一个<code> Any </code>对象,其中包含属性的值。
       * 
       * 
       * @param values          an NVList containing the property
       *                                    names and associated values to be set
       *
       * @see #get_values
       * @see org.omg.CORBA.NamedValue
       * @see org.omg.CORBA.Any
       */

    public abstract void set_values(NVList values);

    /**
     * Deletes from this <code>Context</code> object the
     * <code>NamedValue</code> object(s) whose
     * <code>name</code> field matches the given property name.
     * If the <code>String</code> object supplied for
     * <code>propname</code> has a
     * trailing wildcard character ("*"), then
     * all <code>NamedValue</code> objects whose <code>name</code>
     * fields match will be deleted. The search scope is always
     * limited to this <code>Context</code> object.
     * <P>
     * If no matching property is found, an exception is returned.
     *
     * <p>
     * 从<code> Context </code>对象中删除<code> Name </code>字段与给定属性名称匹配的<code> NamedValue </code>对象。
     * 如果为<code> propname </code>提供的<code> String </code>对象有尾随通配符("*"),那么<code> name <代码>字段匹配将被删除。
     * 搜索范围始终限于此<code> Context </code>对象。
     * <P>
     *  如果未找到匹配属性,则返回异常。
     * 
     * 
     * @param propname          name of the property to be deleted
     */

    public abstract void delete_values(String propname);

    /**
     * Retrieves the <code>NamedValue</code> objects whose
     * <code>name</code> field matches the given name or name
     * pattern.   This method allows for wildcard searches,
     * which means that there can be multiple matches and
     * therefore multiple values returned. If the
     * property is not found at the indicated level, the search
     * continues up the context object tree until a match is found or
     * all <code>Context</code> objects in the chain have been exhausted.
     * <P>
     * If no match is found, an error is returned and no property list
     * is returned.
     *
     * <p>
     * 
     * @param start_scope               a <code>String</code> object indicating the
     *                context object level at which to initiate the
     *                          search for the specified properties
     *                          (for example, "_USER", "_GROUP", "_SYSTEM"). Valid scope
     *                          names are implementation-specific. If a
     *                          scope name is omitted, the search
     *                          begins with the specified context
     *                          object. If the specified scope name is
     *                          not found, an exception is returned.
     * @param op_flags       an operation flag.  The one flag
     *                that may be specified is <code>CTX_RESTRICT_SCOPE</code>.
     *                If this flag is specified, searching is limited to the
     *                          specified <code>start_scope</code> or this
     *                <code>Context</code> object.
     * @param pattern           the property name whose values are to
     *                          be retrieved. <code>pattern</code> may be a
     *                name or a name with a
     *                          trailing wildcard character ("*").
     *
     * @return          an <code>NVList</code> containing all the property values
     *                (in the form of <code>NamedValue</code> objects)
     *                whose associated property name matches the given name or
     *                name pattern
     * @see #set_values
     * @see org.omg.CORBA.NamedValue
     */

    abstract public NVList get_values(String start_scope, int op_flags,
                                      String pattern);
};
