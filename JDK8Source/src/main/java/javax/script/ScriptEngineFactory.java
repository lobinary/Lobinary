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

import java.util.List;

/**
 * <code>ScriptEngineFactory</code> is used to describe and instantiate
 * <code>ScriptEngines</code>.
 * <br><br>
 * Each class implementing <code>ScriptEngine</code> has a corresponding factory
 * that exposes metadata describing the engine class.
 * <br><br>The <code>ScriptEngineManager</code>
 * uses the service provider mechanism described in the <i>Jar File Specification</i> to obtain
 * instances of all <code>ScriptEngineFactories</code> available in
 * the current ClassLoader.
 *
 * <p>
 *  <code> ScriptEngineFactory </code>用于描述和实例化<code> ScriptEngines </code>。
 *  <br> <br>每个实现<code> ScriptEngine </code>的类都有一个相应的工厂,公开描述引擎类的元数据。
 *  <br> <br> <code> ScriptEngineManager </code>使用<i> Jar文件规范</i>中描述的服务提供程序机制来获取当前可用的所有<code> ScriptEngi
 * neFactories </code>实例ClassLoader。
 *  <br> <br>每个实现<code> ScriptEngine </code>的类都有一个相应的工厂,公开描述引擎类的元数据。
 * 
 * 
 * @since 1.6
 */
public interface ScriptEngineFactory {
    /**
     * Returns the full  name of the <code>ScriptEngine</code>.  For
     * instance an implementation based on the Mozilla Rhino Javascript engine
     * might return <i>Rhino Mozilla Javascript Engine</i>.
     * <p>
     *  返回<code> ScriptEngine </code>的全名。
     * 例如,基于Mozilla Rhino Javascript引擎的实现可能会返回<i> Rhino Mozilla Javascript Engine </i>。
     * 
     * 
     * @return The name of the engine implementation.
     */
    public String getEngineName();

    /**
     * Returns the version of the <code>ScriptEngine</code>.
     * <p>
     *  返回<code> ScriptEngine </code>的版本。
     * 
     * 
     * @return The <code>ScriptEngine</code> implementation version.
     */
    public String getEngineVersion();


    /**
     * Returns an immutable list of filename extensions, which generally identify scripts
     * written in the language supported by this <code>ScriptEngine</code>.
     * The array is used by the <code>ScriptEngineManager</code> to implement its
     * <code>getEngineByExtension</code> method.
     * <p>
     *  返回文件扩展名的不可变列表,它通常标识以此<code> ScriptEngine </code>支持的语言编写的脚本。
     * 该数组由<code> ScriptEngineManager </code>使用来实现<code> getEngineByExtension </code>方法。
     * 
     * 
     * @return The list of extensions.
     */
    public List<String> getExtensions();


    /**
     * Returns an immutable list of mimetypes, associated with scripts that
     * can be executed by the engine.  The list is used by the
     * <code>ScriptEngineManager</code> class to implement its
     * <code>getEngineByMimetype</code> method.
     * <p>
     *  返回与引擎可以执行的脚本相关联的mimetypes的不可变列表。
     * 该列表由<code> ScriptEngineManager </code>类使用以实现其<code> getEngineByMimetype </code>方法。
     * 
     * 
     * @return The list of mime types.
     */
    public List<String> getMimeTypes();

    /**
     * Returns an immutable list of  short names for the <code>ScriptEngine</code>, which may be used to
     * identify the <code>ScriptEngine</code> by the <code>ScriptEngineManager</code>.
     * For instance, an implementation based on the Mozilla Rhino Javascript engine might
     * return list containing {&quot;javascript&quot;, &quot;rhino&quot;}.
     * <p>
     * 返回<code> ScriptEngine </code>的不变的短名称列表,可用于通过<code> ScriptEngineManager </code>标识<code> ScriptEngine </code>
     * 。
     * 例如,基于Mozilla Rhino Javascript引擎的实现可能返回包含{"javascript","rhino"}的列表。
     * 
     * 
     * @return an immutable list of short names
     */
    public List<String> getNames();

    /**
     * Returns the name of the scripting langauge supported by this
     * <code>ScriptEngine</code>.
     * <p>
     *  返回此<code> ScriptEngine </code>支持的脚本语言的名称。
     * 
     * 
     * @return The name of the supported language.
     */
    public String getLanguageName();

    /**
     * Returns the version of the scripting language supported by this
     * <code>ScriptEngine</code>.
     * <p>
     *  返回此<code> ScriptEngine </code>支持的脚本语言的版本。
     * 
     * 
     * @return The version of the supported language.
     */
    public String getLanguageVersion();

    /**
     * Returns the value of an attribute whose meaning may be implementation-specific.
     * Keys for which the value is defined in all implementations are:
     * <ul>
     * <li>ScriptEngine.ENGINE</li>
     * <li>ScriptEngine.ENGINE_VERSION</li>
     * <li>ScriptEngine.NAME</li>
     * <li>ScriptEngine.LANGUAGE</li>
     * <li>ScriptEngine.LANGUAGE_VERSION</li>
     * </ul>
     * <p>
     * The values for these keys are the Strings returned by <code>getEngineName</code>,
     * <code>getEngineVersion</code>, <code>getName</code>, <code>getLanguageName</code> and
     * <code>getLanguageVersion</code> respectively.<br><br>
     * A reserved key, <code><b>THREADING</b></code>, whose value describes the behavior of the engine
     * with respect to concurrent execution of scripts and maintenance of state is also defined.
     * These values for the <code><b>THREADING</b></code> key are:<br><br>
     * <ul>
     * <li><code>null</code> - The engine implementation is not thread safe, and cannot
     * be used to execute scripts concurrently on multiple threads.
     * <li><code>&quot;MULTITHREADED&quot;</code> - The engine implementation is internally
     * thread-safe and scripts may execute concurrently although effects of script execution
     * on one thread may be visible to scripts on other threads.
     * <li><code>&quot;THREAD-ISOLATED&quot;</code> - The implementation satisfies the requirements
     * of &quot;MULTITHREADED&quot;, and also, the engine maintains independent values
     * for symbols in scripts executing on different threads.
     * <li><code>&quot;STATELESS&quot;</code> - The implementation satisfies the requirements of
     * <li><code>&quot;THREAD-ISOLATED&quot;</code>.  In addition, script executions do not alter the
     * mappings in the <code>Bindings</code> which is the engine scope of the
     * <code>ScriptEngine</code>.  In particular, the keys in the <code>Bindings</code>
     * and their associated values are the same before and after the execution of the script.
     * </ul>
     * <br><br>
     * Implementations may define implementation-specific keys.
     *
     * <p>
     *  返回属性的值,属性的含义可能是实现特定的。在所有实现中为其定义值的键为：
     * <ul>
     *  <li> ScriptEngine.ENGINE_ENGSION </li> <li> ScriptEngine.ENGINE_VERSION </li> <li> ScriptEngine.NAME
     *  </li> <li> ScriptEngine.LANGUAGE </li> <li> ScriptEngine.LANGUAGE_VERSION </li>。
     * </ul>
     * <p>
     *  这些键的值是<code> getEngineName </code>,<code> getEngineVersion </code>,<code> getName </code>,<code> get
     * LanguageName </code>和<code> getLanguageVersion </code>。
     * 保留键</b> </code>,其值描述引擎在脚本并发执行和维护时的行为。状态也被定义。 <code> <b> THREADING </b> </code>键的这些值为：<br> <br>。
     * <ul>
     * <li> <code> null </code>  - 引擎实现不是线程安全的,不能用于在多个线程上并发执行脚本。
     *  <li> <code>"MULTITHREADED"</code>  - 引擎实现是内部线程安全的,并且脚本可以并发执行,虽然一个线程上的脚本执行的效果对其他线程上的脚本可见。
     *  <li> <code>"THREAD-ISOLATED"</code>  - 该实现满足"MULTITHREADED"的要求,并且引擎对在不同线程上执行的脚本中的符号维护独立的值。
     *  <li> <code>"STATELESS"</code>  - 实施方案满足<li> <code>"THREAD-ISOLATED"</code>的要求。
     * 此外,脚本执行不会更改<code> Bindings </code>中的映射,这是<code> ScriptEngine </code>的引擎范围。
     * 特别地,<code> Bindings </code>中的键及其关联的值在脚本执行之前和之后是相同的。
     * </ul>
     *  <br> <br>实现可以定义实现特定的键。
     * 
     * 
     * @param key The name of the parameter
     * @return The value for the given parameter. Returns <code>null</code> if no
     * value is assigned to the key.
     *
     */
    public Object getParameter(String key);

    /**
     * Returns a String which can be used to invoke a method of a  Java object using the syntax
     * of the supported scripting language.  For instance, an implementation for a Javascript
     * engine might be;
     *
     * <pre>{@code
     * public String getMethodCallSyntax(String obj,
     *                                   String m, String... args) {
     *      String ret = obj;
     *      ret += "." + m + "(";
     *      for (int i = 0; i < args.length; i++) {
     *          ret += args[i];
     *          if (i < args.length - 1) {
     *              ret += ",";
     *          }
     *      }
     *      ret += ")";
     *      return ret;
     * }
     * } </pre>
     * <p>
     *
     * <p>
     *  返回一个String,可以使用支持的脚本语言的语法来调用Java对象的方法。例如,Javascript引擎的实现可能是;
     * 
     * <pre> {@ code public String getMethodCallSyntax(String obj,String m,String ... args){String ret = obj; ret + ="。
     * " + m +"("; for(int i = 0; i <args.length; i ++){ret + = args [i]; if(i <args.length  -  1){ret + =" } ret + =")"; return ret; }} </pre>
     * 。
     * <p>
     * 
     * 
     * @param obj The name representing the object whose method is to be invoked. The
     * name is the one used to create bindings using the <code>put</code> method of
     * <code>ScriptEngine</code>, the <code>put</code> method of an <code>ENGINE_SCOPE</code>
     * <code>Bindings</code>,or the <code>setAttribute</code> method
     * of <code>ScriptContext</code>.  The identifier used in scripts may be a decorated form of the
     * specified one.
     *
     * @param m The name of the method to invoke.
     * @param args names of the arguments in the method call.
     *
     * @return The String used to invoke the method in the syntax of the scripting language.
     */
    public String getMethodCallSyntax(String obj, String m, String... args);

    /**
     * Returns a String that can be used as a statement to display the specified String  using
     * the syntax of the supported scripting language.  For instance, the implementation for a Perl
     * engine might be;
     *
     * <pre><code>
     * public String getOutputStatement(String toDisplay) {
     *      return "print(" + toDisplay + ")";
     * }
     * </code></pre>
     *
     * <p>
     *  返回可用作语句的字符串,以使用受支持的脚本语言的语法显示指定的字符串。例如,Perl引擎的实现可能是;
     * 
     *  <pre> <code> public String getOutputStatement(String toDisplay){return"print("+ toDisplay +")"; } </code>
     *  </pre>。
     * 
     * 
     * @param toDisplay The String to be displayed by the returned statement.
     * @return The string used to display the String in the syntax of the scripting language.
     *
     *
     */
    public String getOutputStatement(String toDisplay);


    /**
     * Returns a valid scripting language executable program with given statements.
     * For instance an implementation for a PHP engine might be:
     *
     * <pre>{@code
     * public String getProgram(String... statements) {
     *      String retval = "<?\n";
     *      int len = statements.length;
     *      for (int i = 0; i < len; i++) {
     *          retval += statements[i] + ";\n";
     *      }
     *      return retval += "?>";
     * }
     * }</pre>
     *
     * <p>
     *  使用给定的语句返回有效的脚本语言可执行程序。例如,PHP引擎的实现可能是：
     * 
     *  <pre> {@ code public String getProgram(String ... statements){String retval ="<?\ n"; int len = statements.length; for(int i = 0; i <len; i ++){retval + = statements [i] +"; \ n"; } return retval + ="?>
     * "; }} </pre>。
     * 
     * 
     *  @param statements The statements to be executed.  May be return values of
     *  calls to the <code>getMethodCallSyntax</code> and <code>getOutputStatement</code> methods.
     *  @return The Program
     */

    public String getProgram(String... statements);

    /**
     * Returns an instance of the <code>ScriptEngine</code> associated with this
     * <code>ScriptEngineFactory</code>. A new ScriptEngine is generally
     * returned, but implementations may pool, share or reuse engines.
     *
     * <p>
     * 
     * @return A new <code>ScriptEngine</code> instance.
     */
    public  ScriptEngine getScriptEngine();
}
