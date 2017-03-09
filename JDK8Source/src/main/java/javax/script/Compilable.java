/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2005, Oracle and/or its affiliates. All rights reserved.
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
import java.io.Reader;

/**
 * The optional interface implemented by ScriptEngines whose methods compile scripts
 * to a form that can be executed repeatedly without recompilation.
 *
 * <p>
 *  由ScriptEngines实现的可选接口,它的方法将脚本编译为可以重复执行而不重新编译的形式。
 * 
 * 
 * @author Mike Grogan
 * @since 1.6
 */
public interface Compilable {
    /**
     * Compiles the script (source represented as a <code>String</code>) for
     * later execution.
     *
     * <p>
     *  编译脚本(源代码表示为<code> String </code>),以供以后执行。
     * 
     * 
     * @param script The source of the script, represented as a <code>String</code>.
     *
     * @return An subclass of <code>CompiledScript</code> to be executed later using one
     * of the <code>eval</code> methods of <code>CompiledScript</code>.
     *
     * @throws ScriptException if compilation fails.
     * @throws NullPointerException if the argument is null.
     *
     */

    public CompiledScript compile(String script) throws
            ScriptException;

    /**
     * Compiles the script (source read from <code>Reader</code>) for
     * later execution.  Functionality is identical to
     * <code>compile(String)</code> other than the way in which the source is
     * passed.
     *
     * <p>
     *  编译脚本(从<code> Reader </code>中读取的源代码)以供以后执行。除了传递源的方式外,函数与<code> compile(String)</code>完全相同。
     * 
     * @param script The reader from which the script source is obtained.
     *
     * @return An implementation of <code>CompiledScript</code> to be executed
     * later using one of its <code>eval</code> methods of <code>CompiledScript</code>.
     *
     * @throws ScriptException if compilation fails.
     * @throws NullPointerException if argument is null.
     */
    public CompiledScript compile(Reader script) throws
            ScriptException;
}
