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
import java.io.Writer;
import java.io.Reader;

/**
 * The interface whose implementing classes are used to connect Script Engines
 * with objects, such as scoped Bindings, in hosting applications.  Each scope is a set
 * of named attributes whose values can be set and retrieved using the
 * <code>ScriptContext</code> methods. ScriptContexts also expose Readers and Writers
 * that can be used by the ScriptEngines for input and output.
 *
 * <p>
 *  其实现类用于将脚本引擎与托管应用程序中的对象(如作用域绑定)连接的接口。每个范围是一组命名属性,其值可以使用<code> ScriptContext </code>方法设置和检索。
 *  ScriptContexts还公开了可供ScriptEngines用于输入和输出的读者和作者。
 * 
 * 
 * @author Mike Grogan
 * @since 1.6
 */
public interface ScriptContext {


    /**
     * EngineScope attributes are visible during the lifetime of a single
     * <code>ScriptEngine</code> and a set of attributes is maintained for each
     * engine.
     * <p>
     *  EngineScope属性在单个<code> ScriptEngine </code>的生​​命周期内可见,并为每个引擎维护一组属性。
     * 
     */
    public static final int ENGINE_SCOPE = 100;

    /**
     * GlobalScope attributes are visible to all engines created by same ScriptEngineFactory.
     * <p>
     *  GlobalScope属性对于由同一ScriptEngineFactory创建的所有引擎都可见。
     * 
     */
    public static final int GLOBAL_SCOPE = 200;


    /**
     * Associates a <code>Bindings</code> instance with a particular scope in this
     * <code>ScriptContext</code>.  Calls to the <code>getAttribute</code> and
     * <code>setAttribute</code> methods must map to the <code>get</code> and
     * <code>put</code> methods of the <code>Bindings</code> for the specified scope.
     *
     * <p>
     *  将<code>绑定</code>实例与此<code> ScriptContext </code>中的特定作用域相关联。
     * 对<code> getAttribute </code>和<code> setAttribute </code>方法的调用必须映射到<code> Bindings </code>方法的<code> ge
     * t </code>和<code> put </code> / code>为指定的作用域。
     *  将<code>绑定</code>实例与此<code> ScriptContext </code>中的特定作用域相关联。
     * 
     * 
     * @param  bindings The <code>Bindings</code> to associate with the given scope
     * @param scope The scope
     *
     * @throws IllegalArgumentException If no <code>Bindings</code> is defined for the
     * specified scope value in ScriptContexts of this type.
     * @throws NullPointerException if value of scope is <code>ENGINE_SCOPE</code> and
     * the specified <code>Bindings</code> is null.
     *
     */
    public void setBindings(Bindings bindings, int scope);

    /**
     * Gets the <code>Bindings</code>  associated with the given scope in this
     * <code>ScriptContext</code>.
     *
     * <p>
     *  获取与<code> ScriptContext </code>中给定范围相关联的<code> Bindings </code>。
     * 
     * 
     * @return The associated <code>Bindings</code>.  Returns <code>null</code> if it has not
     * been set.
     *
     * @param scope The scope
     * @throws IllegalArgumentException If no <code>Bindings</code> is defined for the
     * specified scope value in <code>ScriptContext</code> of this type.
     */
    public Bindings getBindings(int scope);

    /**
     * Sets the value of an attribute in a given scope.
     *
     * <p>
     *  设置给定作用域中属性的值。
     * 
     * 
     * @param name The name of the attribute to set
     * @param value The value of the attribute
     * @param scope The scope in which to set the attribute
     *
     * @throws IllegalArgumentException
     *         if the name is empty or if the scope is invalid.
     * @throws NullPointerException if the name is null.
     */
    public void setAttribute(String name, Object value, int scope);

    /**
     * Gets the value of an attribute in a given scope.
     *
     * <p>
     *  获取给定作用域中属性的值。
     * 
     * 
     * @param name The name of the attribute to retrieve.
     * @param scope The scope in which to retrieve the attribute.
     * @return The value of the attribute. Returns <code>null</code> is the name
     * does not exist in the given scope.
     *
     * @throws IllegalArgumentException
     *         if the name is empty or if the value of scope is invalid.
     * @throws NullPointerException if the name is null.
     */
    public Object getAttribute(String name, int scope);

    /**
     * Remove an attribute in a given scope.
     *
     * <p>
     *  删除给定范围中的属性。
     * 
     * 
     * @param name The name of the attribute to remove
     * @param scope The scope in which to remove the attribute
     *
     * @return The removed value.
     * @throws IllegalArgumentException
     *         if the name is empty or if the scope is invalid.
     * @throws NullPointerException if the name is null.
     */
    public Object removeAttribute(String name, int scope);

    /**
     * Retrieves the value of the attribute with the given name in
     * the scope occurring earliest in the search order.  The order
     * is determined by the numeric value of the scope parameter (lowest
     * scope values first.)
     *
     * <p>
     * 检索在搜索顺序中最早出现的范围中具有给定名称的属性的值。该顺序由范围参数的数值确定(最低范围值首先)。
     * 
     * 
     * @param name The name of the the attribute to retrieve.
     * @return The value of the attribute in the lowest scope for
     * which an attribute with the given name is defined.  Returns
     * null if no attribute with the name exists in any scope.
     * @throws NullPointerException if the name is null.
     * @throws IllegalArgumentException if the name is empty.
     */
    public Object getAttribute(String name);


    /**
     * Get the lowest scope in which an attribute is defined.
     * <p>
     *  获取定义属性的最低范围。
     * 
     * 
     * @param name Name of the attribute
     * .
     * @return The lowest scope.  Returns -1 if no attribute with the given
     * name is defined in any scope.
     * @throws NullPointerException if name is null.
     * @throws IllegalArgumentException if name is empty.
     */
    public int getAttributesScope(String name);

    /**
     * Returns the <code>Writer</code> for scripts to use when displaying output.
     *
     * <p>
     *  返回<code> Writer </code>以便在显示输出时使用的脚本。
     * 
     * 
     * @return The <code>Writer</code>.
     */
    public Writer getWriter();


    /**
     * Returns the <code>Writer</code> used to display error output.
     *
     * <p>
     *  返回用于显示错误输出的<code> Writer </code>。
     * 
     * 
     * @return The <code>Writer</code>
     */
    public Writer getErrorWriter();

    /**
     * Sets the <code>Writer</code> for scripts to use when displaying output.
     *
     * <p>
     *  设置<code> Writer </code>以便在显示输出时使用的脚本。
     * 
     * 
     * @param writer The new <code>Writer</code>.
     */
    public void setWriter(Writer writer);


    /**
     * Sets the <code>Writer</code> used to display error output.
     *
     * <p>
     *  设置用于显示错误输出的<code> Writer </code>。
     * 
     * 
     * @param writer The <code>Writer</code>.
     */
    public void setErrorWriter(Writer writer);

    /**
     * Returns a <code>Reader</code> to be used by the script to read
     * input.
     *
     * <p>
     *  返回要由脚本用于读取输入的<code> Reader </code>。
     * 
     * 
     * @return The <code>Reader</code>.
     */
    public Reader getReader();


    /**
     * Sets the <code>Reader</code> for scripts to read input
     * .
     * <p>
     *  将脚本的<code> Reader </code>设置为读取输入。
     * 
     * 
     * @param reader The new <code>Reader</code>.
     */
    public void setReader(Reader reader);

    /**
     * Returns immutable <code>List</code> of all the valid values for
     * scope in the ScriptContext.
     *
     * <p>
     *  在ScriptContext中返回scope的所有有效值的不可变的<code> List </code>。
     * 
     * @return list of scope values
     */
    public List<Integer> getScopes();
}
