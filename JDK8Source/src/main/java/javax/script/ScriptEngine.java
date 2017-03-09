/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2005, 2013, Oracle and/or its affiliates. All rights reserved.
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

package javax.script;

import java.io.Reader;
import java.util.Map;
import java.util.Set;

/**
 * <code>ScriptEngine</code> is the fundamental interface whose methods must be
 * fully functional in every implementation of this specification.
 * <br><br>
 * These methods provide basic scripting functionality.  Applications written to this
 * simple interface are expected to work with minimal modifications in every implementation.
 * It includes methods that execute scripts, and ones that set and get values.
 * <br><br>
 * The values are key/value pairs of two types.  The first type of pairs consists of
 * those whose keys are reserved and defined in this specification or  by individual
 * implementations.  The values in the pairs with reserved keys have specified meanings.
 * <br><br>
 * The other type of pairs consists of those that create Java language Bindings, the values are
 * usually represented in scripts by the corresponding keys or by decorated forms of them.
 *
 * <p>
 *  <code> ScriptEngine </code>是基本接口,其方法必须在本规范的每个实现中都完全正常工作。 <br> <br>这些方法提供基本的脚本功能。
 * 写入这个简单接口的应用程序应该在每个实现中进行最少的修改。它包括执行脚本的方法,以及设置和获取值的方法。 <br> <br>值是两种类型的键/值对。
 * 第一类型的对由在本说明书中或通过单独实现保留和定义的密钥组成。具有保留键的对中的值具有指定的含义。
 *  <br> <br>其他类型的对由创建Java语言绑定的对组成,这些值通常由相应的键或其装饰形式在脚本中表示。
 * 
 * 
 * @author Mike Grogan
 * @since 1.6
 */

public interface ScriptEngine  {

    /**
     * Reserved key for a named value that passes
     * an array of positional arguments to a script.
     * <p>
     *  对于将位置参数数组传递给脚本的命名值的保留键。
     * 
     */
    public static final String ARGV="javax.script.argv";

    /**
     * Reserved key for a named value that is
     * the name of the file being executed.
     * <p>
     *  命名值的保留键,它是正在执行的文件的名称。
     * 
     */
    public static final String FILENAME = "javax.script.filename";

    /**
     * Reserved key for a named value that is
     * the name of the <code>ScriptEngine</code> implementation.
     * <p>
     *  命名值的保留键,它是<code> ScriptEngine </code>实现的名称。
     * 
     */
    public static final String ENGINE = "javax.script.engine";

    /**
     * Reserved key for a named value that identifies
     * the version of the <code>ScriptEngine</code> implementation.
     * <p>
     *  指定值的保留键,用于标识<code> ScriptEngine </code>实现的版本。
     * 
     */
    public static final String ENGINE_VERSION = "javax.script.engine_version";

    /**
     * Reserved key for a named value that identifies
     * the short name of the scripting language.  The name is used by the
     * <code>ScriptEngineManager</code> to locate a <code>ScriptEngine</code>
     * with a given name in the <code>getEngineByName</code> method.
     * <p>
     * 用于标识脚本语言的短名称的命名值的保留键。
     * 该名称由<code> ScriptEngineManager </code>使用以在<code> getEngineByName </code>方法中找到具有给定名称的<code> ScriptEngi
     * ne </code>。
     * 用于标识脚本语言的短名称的命名值的保留键。
     * 
     */
    public static final String NAME = "javax.script.name";

    /**
     * Reserved key for a named value that is
     * the full name of Scripting Language supported by the implementation.
     * <p>
     *  命名值的保留键,它是实现支持的脚本语言的全名。
     * 
     */
    public static final String LANGUAGE = "javax.script.language";

    /**
     * Reserved key for the named value that identifies
     * the version of the scripting language supported by the implementation.
     * <p>
     *  指定值的保留键,用于标识实现支持的脚本语言的版本。
     * 
     */
    public static final String LANGUAGE_VERSION ="javax.script.language_version";


    /**
     * Causes the immediate execution of the script whose source is the String
     * passed as the first argument.  The script may be reparsed or recompiled before
     * execution.  State left in the engine from previous executions, including
     * variable values and compiled procedures may be visible during this execution.
     *
     * <p>
     *  导致立即执行脚本,其源是作为第一个参数传递的字符串。脚本可以在执行之前被重新编译或重新编译。在此执行期间,可能会看到先前执行中遗留在引擎中的状态,包括变量值和编译过程。
     * 
     * 
     * @param script The script to be executed by the script engine.
     *
     * @param context A <code>ScriptContext</code> exposing sets of attributes in
     * different scopes.  The meanings of the scopes <code>ScriptContext.GLOBAL_SCOPE</code>,
     * and <code>ScriptContext.ENGINE_SCOPE</code> are defined in the specification.
     * <br><br>
     * The <code>ENGINE_SCOPE</code> <code>Bindings</code> of the <code>ScriptContext</code> contains the
     * bindings of scripting variables to application objects to be used during this
     * script execution.
     *
     *
     * @return The value returned from the execution of the script.
     *
     * @throws ScriptException if an error occurs in script. ScriptEngines should create and throw
     * <code>ScriptException</code> wrappers for checked Exceptions thrown by underlying scripting
     * implementations.
     * @throws NullPointerException if either argument is null.
     */
    public Object eval(String script, ScriptContext context) throws ScriptException;


    /**
     * Same as <code>eval(String, ScriptContext)</code> where the source of the script
     * is read from a <code>Reader</code>.
     *
     * <p>
     *  与<code> eval(String,ScriptContext)</code>相同,其中脚本的源是从<code> Reader </code>中读取的。
     * 
     * 
     * @param reader The source of the script to be executed by the script engine.
     *
     * @param context The <code>ScriptContext</code> passed to the script engine.
     *
     * @return The value returned from the execution of the script.
     *
     * @throws ScriptException if an error occurs in script.
     * @throws NullPointerException if either argument is null.
     */
    public Object eval(Reader reader , ScriptContext context) throws ScriptException;

    /**
     * Executes the specified script.  The default <code>ScriptContext</code> for the <code>ScriptEngine</code>
     * is used.
     *
     * <p>
     *  执行指定的脚本。使用<code> ScriptEngine </code>的默认<code> ScriptContext </code>。
     * 
     * 
     * @param script The script language source to be executed.
     *
     * @return The value returned from the execution of the script.
     *
     * @throws ScriptException if error occurs in script.
     * @throws NullPointerException if the argument is null.
     */
    public Object eval(String script) throws ScriptException;

    /**
     * Same as <code>eval(String)</code> except that the source of the script is
     * provided as a <code>Reader</code>
     *
     * <p>
     *  与<code> eval(String)</code>相同,除了脚本的源代码是作为<code> Reader </code>
     * 
     * 
     * @param reader The source of the script.
     *
     * @return The value returned by the script.
     *
     * @throws ScriptException if an error occurs in script.
     * @throws NullPointerException if the argument is null.
     */
    public Object eval(Reader reader) throws ScriptException;

    /**
     * Executes the script using the <code>Bindings</code> argument as the <code>ENGINE_SCOPE</code>
     * <code>Bindings</code> of the <code>ScriptEngine</code> during the script execution.  The
     * <code>Reader</code>, <code>Writer</code> and non-<code>ENGINE_SCOPE</code> <code>Bindings</code> of the
     * default <code>ScriptContext</code> are used. The <code>ENGINE_SCOPE</code>
     * <code>Bindings</code> of the <code>ScriptEngine</code> is not changed, and its
     * mappings are unaltered by the script execution.
     *
     * <p>
     * 在脚本执行期间,使用<code> Bindings </code>参数作为<code> ScriptEngine </code>的<code> ENGINE_SCOPE </code> <code> B
     * indings </code>使用默认<code> ScriptContext </code>的<code> Reader </code>,<code> Writer </code>和非<code> B
     * INDIN </code> 。
     *  <code> ScriptEngine </code>的<code> ENGINE_SCOPE </code> <code>绑定</code>未更改,并且其映射不会被脚本执行改变。
     * 
     * 
     * @param script The source for the script.
     *
     * @param n The <code>Bindings</code> of attributes to be used for script execution.
     *
     * @return The value returned by the script.
     *
     * @throws ScriptException if an error occurs in script.
     * @throws NullPointerException if either argument is null.
     */
    public Object eval(String script, Bindings n) throws ScriptException;

    /**
     * Same as <code>eval(String, Bindings)</code> except that the source of the script
     * is provided as a <code>Reader</code>.
     *
     * <p>
     *  与<code> eval(String,Bindings)</code>相同,只是脚本的源代码是作为<code> Reader </code>提供的。
     * 
     * 
     * @param reader The source of the script.
     * @param n The <code>Bindings</code> of attributes.
     *
     * @return The value returned by the script.
     *
     * @throws ScriptException if an error occurs.
     * @throws NullPointerException if either argument is null.
     */
    public Object eval(Reader reader , Bindings n) throws ScriptException;



    /**
     * Sets a key/value pair in the state of the ScriptEngine that may either create
     * a Java Language Binding to be used in the execution of scripts or be used in some
     * other way, depending on whether the key is reserved.  Must have the same effect as
     * <code>getBindings(ScriptContext.ENGINE_SCOPE).put</code>.
     *
     * <p>
     *  在ScriptEngine的状态中设置键/值对,可以创建要在脚本执行中使用的Java语言绑定,或者以某种其他方式使用,具体取决于键是否保留。
     * 必须具有与<code> getBindings(ScriptContext.ENGINE_SCOPE).put </code>相同的效果。
     * 
     * 
     * @param key The name of named value to add
     * @param value The value of named value to add.
     *
     * @throws NullPointerException if key is null.
     * @throws IllegalArgumentException if key is empty.
     */
    public void put(String key, Object value);


    /**
     * Retrieves a value set in the state of this engine.  The value might be one
     * which was set using <code>setValue</code> or some other value in the state
     * of the <code>ScriptEngine</code>, depending on the implementation.  Must have the same effect
     * as <code>getBindings(ScriptContext.ENGINE_SCOPE).get</code>
     *
     * <p>
     *  检索在此引擎的状态中设置的值。该值可以是使用<code> setValue </code>或某些其他值设置的<code> ScriptEngine </code>的状态,具体取决于实现。
     * 必须具有与<code> getBindings(ScriptContext.ENGINE_SCOPE).get </code>相同的效果。
     * 
     * 
     * @param key The key whose value is to be returned
     * @return the value for the given key
     *
     * @throws NullPointerException if key is null.
     * @throws IllegalArgumentException if key is empty.
     */
    public Object get(String key);


    /**
     * Returns a scope of named values.  The possible scopes are:
     * <br><br>
     * <ul>
     * <li><code>ScriptContext.GLOBAL_SCOPE</code> - The set of named values representing global
     * scope. If this <code>ScriptEngine</code> is created by a <code>ScriptEngineManager</code>,
     * then the manager sets global scope bindings. This may be <code>null</code> if no global
     * scope is associated with this <code>ScriptEngine</code></li>
     * <li><code>ScriptContext.ENGINE_SCOPE</code> - The set of named values representing the state of
     * this <code>ScriptEngine</code>.  The values are generally visible in scripts using
     * the associated keys as variable names.</li>
     * <li>Any other value of scope defined in the default <code>ScriptContext</code> of the <code>ScriptEngine</code>.
     * </li>
     * </ul>
     * <br><br>
     * The <code>Bindings</code> instances that are returned must be identical to those returned by the
     * <code>getBindings</code> method of <code>ScriptContext</code> called with corresponding arguments on
     * the default <code>ScriptContext</code> of the <code>ScriptEngine</code>.
     *
     * <p>
     *  返回命名值的范围。可能的范围是：<br> <br>
     * <ul>
     * <li> <code> ScriptContext.GLOBAL_SCOPE </code>  - 表示全局范围的命名值集合。
     * 如果<code> ScriptEngine </code>是由<code> ScriptEngineManager </code>创建的,那么管理器会设置全局范围绑定。
     * 如果没有全局范围与此<code> ScriptEngine </code>相关联</li> <li> <code> ScriptContext.ENGINE_SCOPE </code>代表这个<code>
     *  ScriptEngine </code>的状态。
     * 如果<code> ScriptEngine </code>是由<code> ScriptEngineManager </code>创建的,那么管理器会设置全局范围绑定。
     *  </li> <li>在<code> ScriptEngine </code>的默认<code> ScriptContext </code>中定义的任何其他值的范围。
     * </li>
     * </ul>
     *  <br> <br>返回的<code> Bindings </code>实例必须与由<code> ScriptContext </code>的<code> getBindings </code>方法返回
     * 的实例相同, ScriptEngine </code>的默认<code> ScriptContext </code>。
     * 
     * 
     * @param scope Either <code>ScriptContext.ENGINE_SCOPE</code> or <code>ScriptContext.GLOBAL_SCOPE</code>
     * which specifies the <code>Bindings</code> to return.  Implementations of <code>ScriptContext</code>
     * may define additional scopes.  If the default <code>ScriptContext</code> of the <code>ScriptEngine</code>
     * defines additional scopes, any of them can be passed to get the corresponding <code>Bindings</code>.
     *
     * @return The <code>Bindings</code> with the specified scope.
     *
     * @throws IllegalArgumentException if specified scope is invalid
     *
     */
    public Bindings getBindings(int scope);

    /**
     * Sets a scope of named values to be used by scripts.  The possible scopes are:
     *<br><br>
     * <ul>
     * <li><code>ScriptContext.ENGINE_SCOPE</code> - The specified <code>Bindings</code> replaces the
     * engine scope of the <code>ScriptEngine</code>.
     * </li>
     * <li><code>ScriptContext.GLOBAL_SCOPE</code> - The specified <code>Bindings</code> must be visible
     * as the <code>GLOBAL_SCOPE</code>.
     * </li>
     * <li>Any other value of scope defined in the default <code>ScriptContext</code> of the <code>ScriptEngine</code>.
     *</li>
     * </ul>
     * <br><br>
     * The method must have the same effect as calling the <code>setBindings</code> method of
     * <code>ScriptContext</code> with the corresponding value of <code>scope</code> on the default
     * <code>ScriptContext</code> of the <code>ScriptEngine</code>.
     *
     * <p>
     *  设置脚本使用的命名值的范围。可能的范围是：br> <br>
     * <ul>
     *  <li> <code> ScriptContext.ENGINE_SCOPE </code>  - 指定的<code> Bindings </code>替换<code> ScriptEngine </code>
     * 的引擎范围。
     * </li>
     *  <li> <code> ScriptContext.GLOBAL_SCOPE </code>  - 指定的<code> Bindings </code>必须显示为<code> GLOBAL_SCOPE
     *  </code>。
     * </li>
     *  <li> <code> ScriptEngine </code>的默认<code> ScriptContext </code>中定义的任何其他值范围。
     * /li>
     * </ul>
     * <br> <br>该方法必须具有与调用<code> ScriptContext </code>的<code> setBindings </code>方法相同的效果,默认值为<code> scope </code>
     *  <code> ScriptEngine </code>的<code> ScriptContext </code>。
     * 
     * 
     * @param bindings The <code>Bindings</code> for the specified scope.
     * @param scope The specified scope.  Either <code>ScriptContext.ENGINE_SCOPE</code>,
     * <code>ScriptContext.GLOBAL_SCOPE</code>, or any other valid value of scope.
     *
     * @throws IllegalArgumentException if the scope is invalid
     * @throws NullPointerException if the bindings is null and the scope is
     * <code>ScriptContext.ENGINE_SCOPE</code>
     */
    public void setBindings(Bindings bindings, int scope);


    /**
     * Returns an uninitialized <code>Bindings</code>.
     *
     * <p>
     * 
     * @return A <code>Bindings</code> that can be used to replace the state of this <code>ScriptEngine</code>.
     **/
    public Bindings createBindings();


    /**
     * Returns the default <code>ScriptContext</code> of the <code>ScriptEngine</code> whose Bindings, Reader
     * and Writers are used for script executions when no <code>ScriptContext</code> is specified.
     *
     * <p>
     *  返回未初始化的<code> Bindings </code>。
     * 
     * 
     * @return The default <code>ScriptContext</code> of the <code>ScriptEngine</code>.
     */
    public ScriptContext getContext();

    /**
     * Sets the default <code>ScriptContext</code> of the <code>ScriptEngine</code> whose Bindings, Reader
     * and Writers are used for script executions when no <code>ScriptContext</code> is specified.
     *
     * <p>
     *  返回<code> ScriptEngine </code>的默认<code> ScriptContext </code>,当没有指定<code> ScriptContext </code>时,它的Bi
     * ndings,Reader和Writers用于脚本执行。
     * 
     * 
     * @param context A <code>ScriptContext</code> that will replace the default <code>ScriptContext</code> in
     * the <code>ScriptEngine</code>.
     * @throws NullPointerException if context is null.
     */
    public void setContext(ScriptContext context);

    /**
     * Returns a <code>ScriptEngineFactory</code> for the class to which this <code>ScriptEngine</code> belongs.
     *
     * <p>
     *  设置<code> ScriptEngine </code>的默认<code> ScriptContext </code>,当没有指定<code> ScriptContext </code>时,它的Bi
     * ndings,Reader和Writers用于脚本执行。
     * 
     * 
     * @return The <code>ScriptEngineFactory</code>
     */
    public ScriptEngineFactory getFactory();
}
