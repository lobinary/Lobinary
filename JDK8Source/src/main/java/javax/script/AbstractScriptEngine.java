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
import java.util.Iterator;

/**
 * Provides a standard implementation for several of the variants of the <code>eval</code>
 * method.
 * <br><br>
 * <code><b>eval(Reader)</b></code><p><code><b>eval(String)</b></code><p>
 * <code><b>eval(String, Bindings)</b></code><p><code><b>eval(Reader, Bindings)</b></code>
 * <br><br> are implemented using the abstract methods
 * <br><br>
 * <code><b>eval(Reader,ScriptContext)</b></code> or
 * <code><b>eval(String, ScriptContext)</b></code>
 * <br><br>
 * with a <code>SimpleScriptContext</code>.
 * <br><br>
 * A <code>SimpleScriptContext</code> is used as the default <code>ScriptContext</code>
 * of the <code>AbstractScriptEngine</code>..
 *
 * <p>
 *  提供了<code> eval </code>方法的几个变体的标准实现。
 *  <br> <br> <code> <b> eval(Reader)</b> </code> <p> <code> <b> eval(String)</b> </code> <p> <code > <b>
 *  eval(String,Bindings)</b> </code> <p> <code> <b> eval(Reader,Bindings)</b> </code> <br> <br>抽象方法<br>
 *  <br> <code> <b> eval(Reader,ScriptContext)</b> </code>或<code> <b> eval(String,ScriptContext)</<br> <br>
 * 与<code> SimpleScriptContext </code>。
 *  提供了<code> eval </code>方法的几个变体的标准实现。
 *  <br> <br> <code> SimpleScriptContext </code>用作<code> AbstractScriptEngine </code>的默认<code> ScriptCon
 * text </code> ..。
 *  提供了<code> eval </code>方法的几个变体的标准实现。
 * 
 * 
 * @author Mike Grogan
 * @since 1.6
 */
public abstract class AbstractScriptEngine  implements ScriptEngine {

    /**
     * The default <code>ScriptContext</code> of this <code>AbstractScriptEngine</code>.
     * <p>
     *  此<code> AbstractScriptEngine </code>的默认<code> ScriptContext </code>。
     * 
     */

    protected ScriptContext context;

    /**
     * Creates a new instance of AbstractScriptEngine using a <code>SimpleScriptContext</code>
     * as its default <code>ScriptContext</code>.
     * <p>
     *  使用<code> SimpleScriptContext </code>作为其默认<code> ScriptContext </code>创建AbstractScriptEngine的新实例。
     * 
     */
    public AbstractScriptEngine() {

        context = new SimpleScriptContext();

    }

    /**
     * Creates a new instance using the specified <code>Bindings</code> as the
     * <code>ENGINE_SCOPE</code> <code>Bindings</code> in the protected <code>context</code> field.
     *
     * <p>
     *  使用指定的<code> Bindings </code>作为受保护的<code>上下文</code>字段中的<code> ENGINE_SCOPE </code> <code> Bindings </code>
     * 创建一个新实例。
     * 
     * 
     * @param n The specified <code>Bindings</code>.
     * @throws NullPointerException if n is null.
     */
    public AbstractScriptEngine(Bindings n) {

        this();
        if (n == null) {
            throw new NullPointerException("n is null");
        }
        context.setBindings(n, ScriptContext.ENGINE_SCOPE);
    }

    /**
     * Sets the value of the protected <code>context</code> field to the specified
     * <code>ScriptContext</code>.
     *
     * <p>
     *  将protected <code> context </code>字段的值设置为指定的<code> ScriptContext </code>。
     * 
     * 
     * @param ctxt The specified <code>ScriptContext</code>.
     * @throws NullPointerException if ctxt is null.
     */
    public void setContext(ScriptContext ctxt) {
        if (ctxt == null) {
            throw new NullPointerException("null context");
        }
        context = ctxt;
    }

    /**
     * Returns the value of the protected <code>context</code> field.
     *
     * <p>
     *  返回protected <code> context </code>字段的值。
     * 
     * 
     * @return The value of the protected <code>context</code> field.
     */
    public ScriptContext getContext() {
        return context;
    }

    /**
     * Returns the <code>Bindings</code> with the specified scope value in
     * the protected <code>context</code> field.
     *
     * <p>
     *  返回受保护的<code>上下文</code>字段中指定范围值的<code> Bindings </code>。
     * 
     * 
     * @param scope The specified scope
     *
     * @return The corresponding <code>Bindings</code>.
     *
     * @throws IllegalArgumentException if the value of scope is
     * invalid for the type the protected <code>context</code> field.
     */
    public Bindings getBindings(int scope) {

        if (scope == ScriptContext.GLOBAL_SCOPE) {
            return context.getBindings(ScriptContext.GLOBAL_SCOPE);
        } else if (scope == ScriptContext.ENGINE_SCOPE) {
            return context.getBindings(ScriptContext.ENGINE_SCOPE);
        } else {
            throw new IllegalArgumentException("Invalid scope value.");
        }
    }

    /**
     * Sets the <code>Bindings</code> with the corresponding scope value in the
     * <code>context</code> field.
     *
     * <p>
     * 在<code> context </code>字段中设置<code> Bindings </code>与对应的范围值。
     * 
     * 
     * @param bindings The specified <code>Bindings</code>.
     * @param scope The specified scope.
     *
     * @throws IllegalArgumentException if the value of scope is
     * invalid for the type the <code>context</code> field.
     * @throws NullPointerException if the bindings is null and the scope is
     * <code>ScriptContext.ENGINE_SCOPE</code>
     */
    public void setBindings(Bindings bindings, int scope) {

        if (scope == ScriptContext.GLOBAL_SCOPE) {
            context.setBindings(bindings, ScriptContext.GLOBAL_SCOPE);;
        } else if (scope == ScriptContext.ENGINE_SCOPE) {
            context.setBindings(bindings, ScriptContext.ENGINE_SCOPE);;
        } else {
            throw new IllegalArgumentException("Invalid scope value.");
        }
    }

    /**
     * Sets the specified value with the specified key in the <code>ENGINE_SCOPE</code>
     * <code>Bindings</code> of the protected <code>context</code> field.
     *
     * <p>
     *  在受保护的<code>上下文</code>字段的<code> ENGINE_SCOPE </code>绑定</code>中使用指定的键设置指定的值。
     * 
     * 
     * @param key The specified key.
     * @param value The specified value.
     *
     * @throws NullPointerException if key is null.
     * @throws IllegalArgumentException if key is empty.
     */
    public void put(String key, Object value) {

        Bindings nn = getBindings(ScriptContext.ENGINE_SCOPE);
        if (nn != null) {
            nn.put(key, value);
        }

    }

    /**
     * Gets the value for the specified key in the <code>ENGINE_SCOPE</code> of the
     * protected <code>context</code> field.
     *
     * <p>
     *  获取受保护的<code>上下文</code>字段的<code> ENGINE_SCOPE </code>中指定键的值。
     * 
     * 
     * @return The value for the specified key.
     *
     * @throws NullPointerException if key is null.
     * @throws IllegalArgumentException if key is empty.
     */
    public Object get(String key) {

        Bindings nn = getBindings(ScriptContext.ENGINE_SCOPE);
        if (nn != null) {
            return nn.get(key);
        }

        return null;
    }


    /**
     * <code>eval(Reader, Bindings)</code> calls the abstract
     * <code>eval(Reader, ScriptContext)</code> method, passing it a <code>ScriptContext</code>
     * whose Reader, Writers and Bindings for scopes other that <code>ENGINE_SCOPE</code>
     * are identical to those members of the protected <code>context</code> field.  The specified
     * <code>Bindings</code> is used instead of the <code>ENGINE_SCOPE</code>
     *
     * <code>Bindings</code> of the <code>context</code> field.
     *
     * <p>
     *  <code> eval(Reader,Bindings)</code>调用抽象<code> eval(Reader,ScriptContext)</code>方法,传递一个<code> ScriptC
     * ontext </code>,它的Reader,Writer和Bindings其他<code> ENGINE_SCOPE </code>与受保护的<code>上下文</code>字段的成员相同。
     * 使用指定的<code> Bindings </code>而不是<code> ENGINE_SCOPE </code>。
     * 
     *  <code>上下文</code>字段的<code>绑定</code>。
     * 
     * 
     * @param reader A <code>Reader</code> containing the source of the script.
     * @param bindings A <code>Bindings</code> to use for the <code>ENGINE_SCOPE</code>
     * while the script executes.
     *
     * @return The return value from <code>eval(Reader, ScriptContext)</code>
     * @throws ScriptException if an error occurs in script.
     * @throws NullPointerException if any of the parameters is null.
     */
    public Object eval(Reader reader, Bindings bindings ) throws ScriptException {

        ScriptContext ctxt = getScriptContext(bindings);

        return eval(reader, ctxt);
    }


    /**
     * Same as <code>eval(Reader, Bindings)</code> except that the abstract
     * <code>eval(String, ScriptContext)</code> is used.
     *
     * <p>
     *  与<code> eval(Reader,Bindings)</code>相同,只是使用抽象<code> eval(String,ScriptContext)</code>。
     * 
     * 
     * @param script A <code>String</code> containing the source of the script.
     *
     * @param bindings A <code>Bindings</code> to use as the <code>ENGINE_SCOPE</code>
     * while the script executes.
     *
     * @return The return value from <code>eval(String, ScriptContext)</code>
     * @throws ScriptException if an error occurs in script.
     * @throws NullPointerException if any of the parameters is null.
     */
    public Object eval(String script, Bindings bindings) throws ScriptException {

        ScriptContext ctxt = getScriptContext(bindings);

        return eval(script , ctxt);
    }

    /**
     * <code>eval(Reader)</code> calls the abstract
     * <code>eval(Reader, ScriptContext)</code> passing the value of the <code>context</code>
     * field.
     *
     * <p>
     *  <code> eval(Reader)</code>调用传递<code> context </code>字段值的抽象<code> eval(Reader,ScriptContext)</code>。
     * 
     * 
     * @param reader A <code>Reader</code> containing the source of the script.
     * @return The return value from <code>eval(Reader, ScriptContext)</code>
     * @throws ScriptException if an error occurs in script.
     * @throws NullPointerException if any of the parameters is null.
     */
    public Object eval(Reader reader) throws ScriptException {


        return eval(reader, context);
    }

    /**
     * Same as <code>eval(Reader)</code> except that the abstract
     * <code>eval(String, ScriptContext)</code> is used.
     *
     * <p>
     *  与<code> eval(Reader)</code>相同,只是使用抽象<code> eval(String,ScriptContext)</code>。
     * 
     * 
     * @param script A <code>String</code> containing the source of the script.
     * @return The return value from <code>eval(String, ScriptContext)</code>
     * @throws ScriptException if an error occurs in script.
     * @throws NullPointerException if any of the parameters is null.
     */
    public Object eval(String script) throws ScriptException {


        return eval(script, context);
    }

    /**
     * Returns a <code>SimpleScriptContext</code>.  The <code>SimpleScriptContext</code>:
     *<br><br>
     * <ul>
     * <li>Uses the specified <code>Bindings</code> for its <code>ENGINE_SCOPE</code>
     * </li>
     * <li>Uses the <code>Bindings</code> returned by the abstract <code>getGlobalScope</code>
     * method as its <code>GLOBAL_SCOPE</code>
     * </li>
     * <li>Uses the Reader and Writer in the default <code>ScriptContext</code> of this
     * <code>ScriptEngine</code>
     * </li>
     * </ul>
     * <br><br>
     * A <code>SimpleScriptContext</code> returned by this method is used to implement eval methods
     * using the abstract <code>eval(Reader,Bindings)</code> and <code>eval(String,Bindings)</code>
     * versions.
     *
     * <p>
     *  返回<code> SimpleScriptContext </code>。 <code> SimpleScriptContext </code>：br> <br>
     * <ul>
     *  <li>对其<code> ENGINE_SCOPE </code>使用指定的<code> Bindings </code>
     * </li>
     *  <li>使用抽象<code> getGlobalScope </code>方法返回的<code> Bindings </code>作为其<code> GLOBAL_SCOPE </code>
     * </li>
     * 
     * @param nn Bindings to use for the <code>ENGINE_SCOPE</code>
     * @return The <code>SimpleScriptContext</code>
     */
    protected ScriptContext getScriptContext(Bindings nn) {

        SimpleScriptContext ctxt = new SimpleScriptContext();
        Bindings gs = getBindings(ScriptContext.GLOBAL_SCOPE);

        if (gs != null) {
            ctxt.setBindings(gs, ScriptContext.GLOBAL_SCOPE);
        }

        if (nn != null) {
            ctxt.setBindings(nn,
                    ScriptContext.ENGINE_SCOPE);
        } else {
            throw new NullPointerException("Engine scope Bindings may not be null.");
        }

        ctxt.setReader(context.getReader());
        ctxt.setWriter(context.getWriter());
        ctxt.setErrorWriter(context.getErrorWriter());

        return ctxt;

    }
}
