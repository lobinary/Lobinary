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

import java.util.Map;

/**
 * Extended by classes that store results of compilations.  State
 * might be stored in the form of Java classes, Java class files or scripting
 * language opcodes.  The script may be executed repeatedly
 * without reparsing.
 * <br><br>
 * Each <code>CompiledScript</code> is associated with a <code>ScriptEngine</code> -- A call to an  <code>eval</code>
 * method of the <code>CompiledScript</code> causes the execution of the script by the
 * <code>ScriptEngine</code>.  Changes in the state of the <code>ScriptEngine</code> caused by execution
 * of the <code>CompiledScript</code>  may visible during subsequent executions of scripts by the engine.
 *
 * <p>
 *  由存储编译结果的类扩展。状态可以以Java类,Java类文件或脚本语言操作码的形式存储。脚本可以重复执行而不重新解析。
 *  <br> <br>每个<code> CompiledScript </code>与<code> ScriptEngine </code>相关联 - 调用<code> CompiledScript </code>
 * 代码>使脚本由<code> ScriptEngine </code>执行。
 *  由存储编译结果的类扩展。状态可以以Java类,Java类文件或脚本语言操作码的形式存储。脚本可以重复执行而不重新解析。
 * 由于执行<code> CompiledScript </code>而导致的<code> ScriptEngine </code>状态的变化可能在引擎的脚本的后续执行期间可见。
 * 
 * 
 * @author Mike Grogan
 * @since 1.6
 */
public abstract class CompiledScript {

    /**
     * Executes the program stored in this <code>CompiledScript</code> object.
     *
     * <p>
     *  执行存储在此<code> CompiledScript </code>对象中的程序。
     * 
     * 
     * @param context A <code>ScriptContext</code> that is used in the same way as
     * the <code>ScriptContext</code> passed to the <code>eval</code> methods of
     * <code>ScriptEngine</code>.
     *
     * @return The value returned by the script execution, if any.  Should return <code>null</code>
     * if no value is returned by the script execution.
     *
     * @throws ScriptException if an error occurs.
     * @throws NullPointerException if context is null.
     */

    public abstract Object eval(ScriptContext context) throws ScriptException;

    /**
     * Executes the program stored in the <code>CompiledScript</code> object using
     * the supplied <code>Bindings</code> of attributes as the <code>ENGINE_SCOPE</code> of the
     * associated <code>ScriptEngine</code> during script execution.  If bindings is null,
     * then the effect of calling this method is same as that of eval(getEngine().getContext()).
     * <p>.
     * The <code>GLOBAL_SCOPE</code> <code>Bindings</code>, <code>Reader</code> and <code>Writer</code>
     * associated with the default <code>ScriptContext</code> of the associated <code>ScriptEngine</code> are used.
     *
     * <p>
     *  使用提供的<code> Bindings </code>属性作为相关<code> ScriptEngine </code>的<code> ENGINE_SCOPE </code>,执行存储在<code>
     *  CompiledScript </code>脚本执行。
     * 如果bindings为null,那么调用此方法的效果与eval(getEngine()。getContext())的效果相同。 <p>。
     * 与关联的<code> ScriptContext </code>的默认<code>相关联的<code> GLOBAL_SCOPE </code> <code>绑定</code>,<code> Reade
     * r </code>和<code> Writer </code>使用<code> ScriptEngine </code>。
     * 如果bindings为null,那么调用此方法的效果与eval(getEngine()。getContext())的效果相同。 <p>。
     * 
     * 
     * @param bindings The bindings of attributes used for the <code>ENGINE_SCOPE</code>.
     *
     * @return The return value from the script execution
     *
     * @throws ScriptException if an error occurs.
     */
    public Object eval(Bindings bindings) throws ScriptException {

        ScriptContext ctxt = getEngine().getContext();

        if (bindings != null) {
            SimpleScriptContext tempctxt = new SimpleScriptContext();
            tempctxt.setBindings(bindings, ScriptContext.ENGINE_SCOPE);
            tempctxt.setBindings(ctxt.getBindings(ScriptContext.GLOBAL_SCOPE),
                    ScriptContext.GLOBAL_SCOPE);
            tempctxt.setWriter(ctxt.getWriter());
            tempctxt.setReader(ctxt.getReader());
            tempctxt.setErrorWriter(ctxt.getErrorWriter());
            ctxt = tempctxt;
        }

        return eval(ctxt);
    }


    /**
     * Executes the program stored in the <code>CompiledScript</code> object.  The
     * default <code>ScriptContext</code> of the associated <code>ScriptEngine</code> is used.
     * The effect of calling this method is same as that of eval(getEngine().getContext()).
     *
     * <p>
     * 执行存储在<code> CompiledScript </code>对象中的程序。
     * 使用相关<code> ScriptEngine </code>的默认<code> ScriptContext </code>。调用此方法的效果与eval(getEngine()。
     * getContext())的效果相同。
     * 
     * 
     * @return The return value from the script execution
     *
     * @throws ScriptException if an error occurs.
     */
    public Object eval() throws ScriptException {
        return eval(getEngine().getContext());
    }

    /**
     * Returns the <code>ScriptEngine</code> whose <code>compile</code> method created this <code>CompiledScript</code>.
     * The <code>CompiledScript</code> will execute in this engine.
     *
     * <p>
     * 
     * @return The <code>ScriptEngine</code> that created this <code>CompiledScript</code>
     */
    public abstract ScriptEngine getEngine();

}
