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

/**
 * The optional interface implemented by ScriptEngines whose methods allow the invocation of
 * procedures in scripts that have previously been executed.
 *
 * <p>
 *  由ScriptEngines实现的可选接口,其方法允许在先前已执行的脚本中调用过程。
 * 
 * 
 * @author  Mike Grogan
 * @author  A. Sundararajan
 * @since 1.6
 */
public interface Invocable  {
    /**
     * Calls a method on a script object compiled during a previous script execution,
     * which is retained in the state of the <code>ScriptEngine</code>.
     *
     * <p>
     *  调用在上一个脚本执行期间编译的脚本对象上的方法,该方法保留在<code> ScriptEngine </code>的状态。
     * 
     * 
     * @param name The name of the procedure to be called.
     *
     * @param thiz If the procedure is a member  of a class
     * defined in the script and thiz is an instance of that class
     * returned by a previous execution or invocation, the named method is
     * called through that instance.
     *
     * @param args Arguments to pass to the procedure.  The rules for converting
     * the arguments to scripting variables are implementation-specific.
     *
     * @return The value returned by the procedure.  The rules for converting the scripting
     * variable returned by the script method to a Java Object are implementation-specific.
     *
     * @throws ScriptException if an error occurs during invocation of the method.
     * @throws NoSuchMethodException if method with given name or matching argument types cannot be found.
     * @throws NullPointerException if the method name is null.
     * @throws IllegalArgumentException if the specified thiz is null or the specified Object is
     * does not represent a scripting object.
     */
    public Object invokeMethod(Object thiz, String name, Object... args)
        throws ScriptException, NoSuchMethodException;

    /**
     * Used to call top-level procedures and functions defined in scripts.
     *
     * <p>
     *  用于调用在脚本中定义的顶级过程和函数。
     * 
     * 
     * @param name of the procedure or function to call
     * @param args Arguments to pass to the procedure or function
     * @return The value returned by the procedure or function
     *
     * @throws ScriptException if an error occurs during invocation of the method.
     * @throws NoSuchMethodException if method with given name or matching argument types cannot be found.
     * @throws NullPointerException if method name is null.
     */
     public Object invokeFunction(String name, Object... args)
        throws ScriptException, NoSuchMethodException;


     /**
     * Returns an implementation of an interface using functions compiled in
     * the interpreter. The methods of the interface
     * may be implemented using the <code>invokeFunction</code> method.
     *
     * <p>
     *  使用解释器中编译的函数返回接口的实现。可以使用<code> invokeFunction </code>方法来实现接口的方法。
     * 
     * 
     * @param <T> the type of the interface to return
     * @param clasz The <code>Class</code> object of the interface to return.
     *
     * @return An instance of requested interface - null if the requested interface is unavailable,
     * i. e. if compiled functions in the <code>ScriptEngine</code> cannot be found matching
     * the ones in the requested interface.
     *
     * @throws IllegalArgumentException if the specified <code>Class</code> object
     * is null or is not an interface.
     */
    public <T> T getInterface(Class<T> clasz);

    /**
     * Returns an implementation of an interface using member functions of
     * a scripting object compiled in the interpreter. The methods of the
     * interface may be implemented using the <code>invokeMethod</code> method.
     *
     * <p>
     *  返回接口的实现,使用在解释器中编译的脚本对象的成员函数。接口的方法可以使用<code> invokeMethod </code>方法来实现。
     * 
     * @param <T> the type of the interface to return
     * @param thiz The scripting object whose member functions are used to implement the methods of the interface.
     * @param clasz The <code>Class</code> object of the interface to return.
     *
     * @return An instance of requested interface - null if the requested interface is unavailable,
     * i. e. if compiled methods in the <code>ScriptEngine</code> cannot be found matching
     * the ones in the requested interface.
     *
     * @throws IllegalArgumentException if the specified <code>Class</code> object
     * is null or is not an interface, or if the specified Object is
     * null or does not represent a scripting object.
     */
     public <T> T getInterface(Object thiz, Class<T> clasz);

}
