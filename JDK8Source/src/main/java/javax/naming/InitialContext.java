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

package javax.naming;

import java.util.Hashtable;
import javax.naming.spi.NamingManager;
import com.sun.naming.internal.ResourceManager;

/**
 * This class is the starting context for performing naming operations.
 *<p>
 * All naming operations are relative to a context.
 * The initial context implements the Context interface and
 * provides the starting point for resolution of names.
 *<p>
 * <a name=ENVIRONMENT></a>
 * When the initial context is constructed, its environment
 * is initialized with properties defined in the environment parameter
 * passed to the constructor, and in any
 * <a href=Context.html#RESOURCEFILES>application resource files</a>.
 * In addition, a small number of standard JNDI properties may
 * be specified as system properties or as applet parameters
 * (through the use of {@link Context#APPLET}).
 * These special properties are listed in the field detail sections of the
 * <a href=Context.html#field_detail><tt>Context</tt></a> and
 * <a href=ldap/LdapContext.html#field_detail><tt>LdapContext</tt></a>
 * interface documentation.
 *<p>
 * JNDI determines each property's value by merging
 * the values from the following two sources, in order:
 * <ol>
 * <li>
 * The first occurrence of the property from the constructor's
 * environment parameter and (for appropriate properties) the applet
 * parameters and system properties.
 * <li>
 * The application resource files (<tt>jndi.properties</tt>).
 * </ol>
 * For each property found in both of these two sources, or in
 * more than one application resource file, the property's value
 * is determined as follows.  If the property is
 * one of the standard JNDI properties that specify a list of JNDI
 * factories (see <a href=Context.html#LISTPROPS><tt>Context</tt></a>),
 * all of the values are
 * concatenated into a single colon-separated list.  For other
 * properties, only the first value found is used.
 *
 *<p>
 * The initial context implementation is determined at runtime.
 * The default policy uses the environment property
 * "{@link Context#INITIAL_CONTEXT_FACTORY java.naming.factory.initial}",
 * which contains the class name of the initial context factory.
 * An exception to this policy is made when resolving URL strings, as described
 * below.
 *<p>
 * When a URL string (a <tt>String</tt> of the form
 * <em>scheme_id:rest_of_name</em>) is passed as a name parameter to
 * any method, a URL context factory for handling that scheme is
 * located and used to resolve the URL.  If no such factory is found,
 * the initial context specified by
 * <tt>"java.naming.factory.initial"</tt> is used.  Similarly, when a
 * <tt>CompositeName</tt> object whose first component is a URL string is
 * passed as a name parameter to any method, a URL context factory is
 * located and used to resolve the first name component.
 * See {@link NamingManager#getURLContext
 * <tt>NamingManager.getURLContext()</tt>} for a description of how URL
 * context factories are located.
 *<p>
 * This default policy of locating the initial context and URL context
 * factories may be overridden
 * by calling
 * <tt>NamingManager.setInitialContextFactoryBuilder()</tt>.
 *<p>
 * NoInitialContextException is thrown when an initial context cannot
 * be instantiated. This exception can be thrown during any interaction
 * with the InitialContext, not only when the InitialContext is constructed.
 * For example, the implementation of the initial context might lazily
 * retrieve the context only when actual methods are invoked on it.
 * The application should not have any dependency on when the existence
 * of an initial context is determined.
 *<p>
 * When the environment property "java.naming.factory.initial" is
 * non-null, the InitialContext constructor will attempt to create the
 * initial context specified therein. At that time, the initial context factory
 * involved might throw an exception if a problem is encountered. However,
 * it is provider implementation-dependent when it verifies and indicates
 * to the users of the initial context any environment property- or
 * connection- related problems. It can do so lazily--delaying until
 * an operation is performed on the context, or eagerly, at the time
 * the context is constructed.
 *<p>
 * An InitialContext instance is not synchronized against concurrent
 * access by multiple threads. Multiple threads each manipulating a
 * different InitialContext instance need not synchronize.
 * Threads that need to access a single InitialContext instance
 * concurrently should synchronize amongst themselves and provide the
 * necessary locking.
 *
 * <p>
 *  这个类是执行命名操作的起始上下文。
 * p>
 *  所有命名操作都与上下文相关。初始上下文实现Context接口并提供用于解析名称的起点。
 * p>
 *  <a name=ENVIRONMENT> </a>构建初始上下文时,将使用传递给构造函数的environment参数中定义的属性初始化其环境,并在任何<a href=Context.html#RESOURCEFILES>
 * 应用程序资源文件< / a>。
 * 此外,可以将少量标准JNDI属性指定为系统属性或小程序参数(通过使用{@link Context#APPLET})。
 * 这些特殊属性列在<a href=Context.html#field_detail> <tt>上下文</tt> </a>和<a href=ldap/LdapContext.html#field_detail>
 *  <tt>的字段详细信息部分中。
 * 此外,可以将少量标准JNDI属性指定为系统属性或小程序参数(通过使用{@link Context#APPLET})。 LdapContext </tt> </a>接口文档。
 * p>
 *  JNDI通过按顺序合并以下两个源的值来确定每个属性的值：
 * <ol>
 * <li>
 *  从构造函数的环境参数和(对于适当的属性)applet参数和系统属性的属性的第一次出现。
 * <li>
 *  应用程序资源文件(<tt> jndi.properties </tt>)。
 * </ol>
 * 对于在这两个源中或在多个应用程序资源文件中找到的每个属性,按如下确定属性的值。
 * 如果属性是指定JNDI工厂列表的标准JNDI属性之一(请参阅<a href=Context.html#LISTPROPS> <tt>上下文</tt> </a>),则所有值都将连接到单个以冒号分隔的列表。
 * 对于在这两个源中或在多个应用程序资源文件中找到的每个属性,按如下确定属性的值。对于其他属性,只使用找到的第一个值。
 * 
 * p>
 *  初始上下文实现在运行时确定。
 * 默认策略使用环境属性"{@link Context#INITIAL_CONTEXT_FACTORY java.naming.factory.initial}",其中包含初始上下文工厂的类名。
 * 在解析URL字符串时,会出现此策略的例外情况,如下所述。
 * p>
 *  当URL字符串(<em> scheme_id：rest_of_name </em>形式的<tt> String </tt>)作为name参数传递给任何方法时,将定位和使用用于处理该方案的URL上下文工
 * 厂以解析URL。
 * 如果没有找到这样的工厂,则使用由<tt>"java.naming.factory.initial"</tt>指定的初始上下文。
 * 类似地,当第一个组件是URL字符串的<tt> CompositeName </tt>对象作为name参数传递给任何方法时,将会找到一个URL上下文工厂,用于解析名字组件。
 * 有关URL上下文工厂如何定位的说明,请参阅{@link NamingManager#getURLContext <tt> NamingManager.getURLContext()</tt>}。
 * p>
 * 可以通过调用<tt> NamingManager.setInitialContextFactoryBuilder()</tt>来覆盖定位初始上下文和URL上下文工厂的默认策略。
 * p>
 *  无法初始化上下文时抛出NoInitialContextException。这个异常可以在与InitialContext的任何交互期间抛出,不仅在构造InitialContext时。
 * 例如,初始上下文的实现可能只在实际方法被调用时才会检索上下文。应用程序不应该对何时确定初始上下文的存在具有任何依赖性。
 * p>
 *  当环境属性"java.naming.factory.initial"为非空时,InitialContext构造函数将尝试创建其中指定的初始上下文。
 * 在那时,涉及的初始上下文工厂可能在遇到问题时抛出异常。然而,当它验证并向初始上下文的用户指示任何环境属性或连接相关的问题时,它是依赖于提供者实现的。
 * 它可以这么做 - 延迟,直到对上下文执行操作,或者急切地,在构建上下文时。
 * p>
 * InitialContext实例不会与多个线程的并发访问同步。每个操作不同InitialContext实例的多个线程不需要同步。
 * 需要同时访问单个InitialContext实例的线程应在它们之间同步并提供必要的锁定。
 * 
 * 
 * @author Rosanna Lee
 * @author Scott Seligman
 *
 * @see Context
 * @see NamingManager#setInitialContextFactoryBuilder
 *      NamingManager.setInitialContextFactoryBuilder
 * @since JNDI 1.1 / Java 2 Platform, Standard Edition, v 1.3
 */

public class InitialContext implements Context {

    /**
     * The environment associated with this InitialContext.
     * It is initialized to null and is updated by the constructor
     * that accepts an environment or by the <tt>init()</tt> method.
     * <p>
     *  与此InitialContext相关联的环境。它初始化为null,并由接受环境的构造函数或通过<tt> init()</tt>方法更新。
     * 
     * 
     * @see #addToEnvironment
     * @see #removeFromEnvironment
     * @see #getEnvironment
     */
    protected Hashtable<Object,Object> myProps = null;

    /**
     * Field holding the result of calling NamingManager.getInitialContext().
     * It is set by getDefaultInitCtx() the first time getDefaultInitCtx()
     * is called. Subsequent invocations of getDefaultInitCtx() return
     * the value of defaultInitCtx.
     * <p>
     *  字段保存调用NamingManager.getInitialContext()的结果。它由getDefaultInitCtx()设置,第一次调用getDefaultInitCtx()。
     * 随后调用getDefaultInitCtx()将返回defaultInitCtx的值。
     * 
     * 
     * @see #getDefaultInitCtx
     */
    protected Context defaultInitCtx = null;

    /**
     * Field indicating whether the initial context has been obtained
     * by calling NamingManager.getInitialContext().
     * If true, its result is in <code>defaultInitCtx</code>.
     * <p>
     *  指示是否通过调用NamingManager.getInitialContext()获得初始上下文的字段。如果为true,则其结果位于<code> defaultInitCtx </code>中。
     * 
     */
    protected boolean gotDefault = false;

    /**
     * Constructs an initial context with the option of not
     * initializing it.  This may be used by a constructor in
     * a subclass when the value of the environment parameter
     * is not yet known at the time the <tt>InitialContext</tt>
     * constructor is called.  The subclass's constructor will
     * call this constructor, compute the value of the environment,
     * and then call <tt>init()</tt> before returning.
     *
     * <p>
     *  构造具有不初始化它的选项的初始上下文。当在调用<tt> InitialContext </tt>构造函数时尚未知道环境参数的值时,这可能会被子类中的构造函数使用。
     * 子类的构造函数将调用此构造函数,计算环境的值,然后在返回之前调用<tt> init()</tt>。
     * 
     * 
     * @param lazy
     *          true means do not initialize the initial context; false
     *          is equivalent to calling <tt>new InitialContext()</tt>
     * @throws  NamingException if a naming exception is encountered
     *
     * @see #init(Hashtable)
     * @since 1.3
     */
    protected InitialContext(boolean lazy) throws NamingException {
        if (!lazy) {
            init(null);
        }
    }

    /**
     * Constructs an initial context.
     * No environment properties are supplied.
     * Equivalent to <tt>new InitialContext(null)</tt>.
     *
     * <p>
     *  构造初始上下文。不提供环境属性。等效于<tt> new InitialContext(null)</tt>。
     * 
     * 
     * @throws  NamingException if a naming exception is encountered
     *
     * @see #InitialContext(Hashtable)
     */
    public InitialContext() throws NamingException {
        init(null);
    }

    /**
     * Constructs an initial context using the supplied environment.
     * Environment properties are discussed in the class description.
     *
     * <p> This constructor will not modify <tt>environment</tt>
     * or save a reference to it, but may save a clone.
     * Caller should not modify mutable keys and values in
     * <tt>environment</tt> after it has been passed to the constructor.
     *
     * <p>
     * 使用提供的环境构造初始上下文。环境属性在类描述中讨论。
     * 
     *  <p>此构造函数不会修改<tt>环境</tt>或保存对它的引用,但可以保存克隆。调用者在传递给构造函数后,不应在<tt>环境</tt>中修改可变键和值。
     * 
     * 
     * @param environment
     *          environment used to create the initial context.
     *          Null indicates an empty environment.
     *
     * @throws  NamingException if a naming exception is encountered
     */
    public InitialContext(Hashtable<?,?> environment)
        throws NamingException
    {
        if (environment != null) {
            environment = (Hashtable)environment.clone();
        }
        init(environment);
    }

    /**
     * Initializes the initial context using the supplied environment.
     * Environment properties are discussed in the class description.
     *
     * <p> This method will modify <tt>environment</tt> and save
     * a reference to it.  The caller may no longer modify it.
     *
     * <p>
     *  使用提供的环境初始化初始上下文。环境属性在类描述中讨论。
     * 
     *  <p>此方法将修改<tt>环境</tt>并保存对它的引用。呼叫者可能不再修改它。
     * 
     * 
     * @param environment
     *          environment used to create the initial context.
     *          Null indicates an empty environment.
     *
     * @throws  NamingException if a naming exception is encountered
     *
     * @see #InitialContext(boolean)
     * @since 1.3
     */
    @SuppressWarnings("unchecked")
    protected void init(Hashtable<?,?> environment)
        throws NamingException
    {
        myProps = (Hashtable<Object,Object>)
                ResourceManager.getInitialEnvironment(environment);

        if (myProps.get(Context.INITIAL_CONTEXT_FACTORY) != null) {
            // user has specified initial context factory; try to get it
            getDefaultInitCtx();
        }
    }

    /**
     * A static method to retrieve the named object.
     * This is a shortcut method equivalent to invoking:
     * <p>
     * <code>
     *        InitialContext ic = new InitialContext();
     *        Object obj = ic.lookup();
     * </code>
     * <p> If <tt>name</tt> is empty, returns a new instance of this context
     * (which represents the same naming context as this context, but its
     * environment may be modified independently and it may be accessed
     * concurrently).
     *
     * <p>
     *  用于检索命名对象的静态方法。这是一个相当于调用的快捷方法：
     * <p>
     * <code>
     *  InitialContext ic = new InitialContext(); Object obj = ic.lookup();
     * </code>
     *  <p>如果<tt> name </tt>为空,则返回此上下文的新实例(代表与此上下文相同的命名上下文,但其环境可以单独修改,并且可以同时访问)。
     * 
     * 
     * @param <T> the type of the returned object
     * @param name
     *          the name of the object to look up
     * @return  the object bound to <tt>name</tt>
     * @throws  NamingException if a naming exception is encountered
     *
     * @see #doLookup(String)
     * @see #lookup(Name)
     * @since 1.6
     */
    @SuppressWarnings("unchecked")
    public static <T> T doLookup(Name name)
        throws NamingException {
        return (T) (new InitialContext()).lookup(name);
    }

   /**
     * A static method to retrieve the named object.
     * See {@link #doLookup(Name)} for details.
     * <p>
     *  用于检索命名对象的静态方法。有关详情,请参阅{@link #doLookup(Name)}。
     * 
     * 
     * @param <T> the type of the returned object
     * @param name
     *          the name of the object to look up
     * @return  the object bound to <tt>name</tt>
     * @throws  NamingException if a naming exception is encountered
     * @since 1.6
     */
    @SuppressWarnings("unchecked")
    public static <T> T doLookup(String name)
        throws NamingException {
        return (T) (new InitialContext()).lookup(name);
    }

    private static String getURLScheme(String str) {
        int colon_posn = str.indexOf(':');
        int slash_posn = str.indexOf('/');

        if (colon_posn > 0 && (slash_posn == -1 || colon_posn < slash_posn))
            return str.substring(0, colon_posn);
        return null;
    }

    /**
     * Retrieves the initial context by calling
     * <code>NamingManager.getInitialContext()</code>
     * and cache it in defaultInitCtx.
     * Set <code>gotDefault</code> so that we know we've tried this before.
     * <p>
     *  通过调用<code> NamingManager.getInitialContext()</code>获取初始上下文,并将其缓存在defaultInitCtx中。
     * 设置<code> gotDefault </code>,以便我们知道我们之前已经尝试过。
     * 
     * 
     * @return The non-null cached initial context.
     * @exception NoInitialContextException If cannot find an initial context.
     * @exception NamingException If a naming exception was encountered.
     */
    protected Context getDefaultInitCtx() throws NamingException{
        if (!gotDefault) {
            defaultInitCtx = NamingManager.getInitialContext(myProps);
            gotDefault = true;
        }
        if (defaultInitCtx == null)
            throw new NoInitialContextException();

        return defaultInitCtx;
    }

    /**
     * Retrieves a context for resolving the string name <code>name</code>.
     * If <code>name</code> name is a URL string, then attempt
     * to find a URL context for it. If none is found, or if
     * <code>name</code> is not a URL string, then return
     * <code>getDefaultInitCtx()</code>.
     *<p>
     * See getURLOrDefaultInitCtx(Name) for description
     * of how a subclass should use this method.
     * <p>
     * 检索用于解析字符串名称<code> name </code>的上下文。如果<code> name </code> name是一个URL字符串,则尝试查找它的URL上下文。
     * 如果没有找到,或<code> name </code>不是一个URL字符串,则返回<code> getDefaultInitCtx()</code>。
     * p>
     *  有关子类如何使用此方法的说明,请参阅getURLOrDefaultInitCtx(Name)。
     * 
     * 
     * @param name The non-null name for which to get the context.
     * @return A URL context for <code>name</code> or the cached
     *         initial context. The result cannot be null.
     * @exception NoInitialContextException If cannot find an initial context.
     * @exception NamingException In a naming exception is encountered.
     * @see javax.naming.spi.NamingManager#getURLContext
     */
    protected Context getURLOrDefaultInitCtx(String name)
        throws NamingException {
        if (NamingManager.hasInitialContextFactoryBuilder()) {
            return getDefaultInitCtx();
        }
        String scheme = getURLScheme(name);
        if (scheme != null) {
            Context ctx = NamingManager.getURLContext(scheme, myProps);
            if (ctx != null) {
                return ctx;
            }
        }
        return getDefaultInitCtx();
    }

    /**
     * Retrieves a context for resolving <code>name</code>.
     * If the first component of <code>name</code> name is a URL string,
     * then attempt to find a URL context for it. If none is found, or if
     * the first component of <code>name</code> is not a URL string,
     * then return <code>getDefaultInitCtx()</code>.
     *<p>
     * When creating a subclass of InitialContext, use this method as
     * follows.
     * Define a new method that uses this method to get an initial
     * context of the desired subclass.
     * <blockquote><pre>
     * protected XXXContext getURLOrDefaultInitXXXCtx(Name name)
     * throws NamingException {
     *  Context answer = getURLOrDefaultInitCtx(name);
     *  if (!(answer instanceof XXXContext)) {
     *    if (answer == null) {
     *      throw new NoInitialContextException();
     *    } else {
     *      throw new NotContextException("Not an XXXContext");
     *    }
     *  }
     *  return (XXXContext)answer;
     * }
     * </pre></blockquote>
     * When providing implementations for the new methods in the subclass,
     * use this newly defined method to get the initial context.
     * <blockquote><pre>
     * public Object XXXMethod1(Name name, ...) {
     *  throws NamingException {
     *    return getURLOrDefaultInitXXXCtx(name).XXXMethod1(name, ...);
     * }
     * </pre></blockquote>
     *
     * <p>
     *  检索用于解析<code> name </code>的上下文。如果<code> name </code> name的第一个组件是一个URL字符串,那么尝试为它找到一个URL上下文。
     * 如果没有找到,或<code> name </code>的第一个组件不是一个URL字符串,则返回<code> getDefaultInitCtx()</code>。
     * p>
     *  当创建InitialContext的子类时,使用此方法如下。定义一个新方法,使用此方法来获取所需子类的初始上下文。
     *  <block> <pre> protected XXXContext getURLOrDefaultInitXXXCtx(Name name)throws NamingException {Context answer = getURLOrDefaultInitCtx(name); if(！)(answer instanceof XXXContext)){if(answer == null){throw new NoInitialContextException(); }
     *  else {throw new NotContextException("Not an XXXContext"); }} return(XXXContext)answer; } </pre> </blockquote>
     * 当为子类中的新方法提供实现时,使用这个新定义的方法来获取初始上下文。
     *  当创建InitialContext的子类时,使用此方法如下。定义一个新方法,使用此方法来获取所需子类的初始上下文。
     *  <blockquote> <pre> public Object XXXMethod1(name name,...){throws NamingException {return getURLOrDefaultInitXXXCtx(name).XXXMethod1(name,...); }
     * 
     * @param name The non-null name for which to get the context.
     * @return A URL context for <code>name</code> or the cached
     *         initial context. The result cannot be null.
     * @exception NoInitialContextException If cannot find an initial context.
     * @exception NamingException In a naming exception is encountered.
     *
     * @see javax.naming.spi.NamingManager#getURLContext
     */
    protected Context getURLOrDefaultInitCtx(Name name)
        throws NamingException {
        if (NamingManager.hasInitialContextFactoryBuilder()) {
            return getDefaultInitCtx();
        }
        if (name.size() > 0) {
            String first = name.get(0);
            String scheme = getURLScheme(first);
            if (scheme != null) {
                Context ctx = NamingManager.getURLContext(scheme, myProps);
                if (ctx != null) {
                    return ctx;
                }
            }
        }
        return getDefaultInitCtx();
    }

// Context methods
// Most Javadoc is deferred to the Context interface.

    public Object lookup(String name) throws NamingException {
        return getURLOrDefaultInitCtx(name).lookup(name);
    }

    public Object lookup(Name name) throws NamingException {
        return getURLOrDefaultInitCtx(name).lookup(name);
    }

    public void bind(String name, Object obj) throws NamingException {
        getURLOrDefaultInitCtx(name).bind(name, obj);
    }

    public void bind(Name name, Object obj) throws NamingException {
        getURLOrDefaultInitCtx(name).bind(name, obj);
    }

    public void rebind(String name, Object obj) throws NamingException {
        getURLOrDefaultInitCtx(name).rebind(name, obj);
    }

    public void rebind(Name name, Object obj) throws NamingException {
        getURLOrDefaultInitCtx(name).rebind(name, obj);
    }

    public void unbind(String name) throws NamingException  {
        getURLOrDefaultInitCtx(name).unbind(name);
    }

    public void unbind(Name name) throws NamingException  {
        getURLOrDefaultInitCtx(name).unbind(name);
    }

    public void rename(String oldName, String newName) throws NamingException {
        getURLOrDefaultInitCtx(oldName).rename(oldName, newName);
    }

    public void rename(Name oldName, Name newName)
        throws NamingException
    {
        getURLOrDefaultInitCtx(oldName).rename(oldName, newName);
    }

    public NamingEnumeration<NameClassPair> list(String name)
        throws NamingException
    {
        return (getURLOrDefaultInitCtx(name).list(name));
    }

    public NamingEnumeration<NameClassPair> list(Name name)
        throws NamingException
    {
        return (getURLOrDefaultInitCtx(name).list(name));
    }

    public NamingEnumeration<Binding> listBindings(String name)
            throws NamingException  {
        return getURLOrDefaultInitCtx(name).listBindings(name);
    }

    public NamingEnumeration<Binding> listBindings(Name name)
            throws NamingException  {
        return getURLOrDefaultInitCtx(name).listBindings(name);
    }

    public void destroySubcontext(String name) throws NamingException  {
        getURLOrDefaultInitCtx(name).destroySubcontext(name);
    }

    public void destroySubcontext(Name name) throws NamingException  {
        getURLOrDefaultInitCtx(name).destroySubcontext(name);
    }

    public Context createSubcontext(String name) throws NamingException  {
        return getURLOrDefaultInitCtx(name).createSubcontext(name);
    }

    public Context createSubcontext(Name name) throws NamingException  {
        return getURLOrDefaultInitCtx(name).createSubcontext(name);
    }

    public Object lookupLink(String name) throws NamingException  {
        return getURLOrDefaultInitCtx(name).lookupLink(name);
    }

    public Object lookupLink(Name name) throws NamingException {
        return getURLOrDefaultInitCtx(name).lookupLink(name);
    }

    public NameParser getNameParser(String name) throws NamingException {
        return getURLOrDefaultInitCtx(name).getNameParser(name);
    }

    public NameParser getNameParser(Name name) throws NamingException {
        return getURLOrDefaultInitCtx(name).getNameParser(name);
    }

    /**
     * Composes the name of this context with a name relative to
     * this context.
     * Since an initial context may never be named relative
     * to any context other than itself, the value of the
     * <tt>prefix</tt> parameter must be an empty name (<tt>""</tt>).
     * <p>
     *  </pre> </blockquote>。
     *  当创建InitialContext的子类时,使用此方法如下。定义一个新方法,使用此方法来获取所需子类的初始上下文。
     * 
     */
    public String composeName(String name, String prefix)
            throws NamingException {
        return name;
    }

    /**
     * Composes the name of this context with a name relative to
     * this context.
     * Since an initial context may never be named relative
     * to any context other than itself, the value of the
     * <tt>prefix</tt> parameter must be an empty name.
     * <p>
     * 使用相对于此上下文的名称构造此上下文的名称。由于初始上下文可能永远不会相对于除本身之外的任何上下文命名,所以<tt>前缀</tt>参数的值必须是空名称(<tt>""</tt>)。
     * 
     */
    public Name composeName(Name name, Name prefix)
        throws NamingException
    {
        return (Name)name.clone();
    }

    public Object addToEnvironment(String propName, Object propVal)
            throws NamingException {
        myProps.put(propName, propVal);
        return getDefaultInitCtx().addToEnvironment(propName, propVal);
    }

    public Object removeFromEnvironment(String propName)
            throws NamingException {
        myProps.remove(propName);
        return getDefaultInitCtx().removeFromEnvironment(propName);
    }

    public Hashtable<?,?> getEnvironment() throws NamingException {
        return getDefaultInitCtx().getEnvironment();
    }

    public void close() throws NamingException {
        myProps = null;
        if (defaultInitCtx != null) {
            defaultInitCtx.close();
            defaultInitCtx = null;
        }
        gotDefault = false;
    }

    public String getNameInNamespace() throws NamingException {
        return getDefaultInitCtx().getNameInNamespace();
    }
};
