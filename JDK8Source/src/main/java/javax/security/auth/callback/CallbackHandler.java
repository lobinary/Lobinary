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

package javax.security.auth.callback;

/**
 * <p> An application implements a {@code CallbackHandler} and passes
 * it to underlying security services so that they may interact with
 * the application to retrieve specific authentication data,
 * such as usernames and passwords, or to display certain information,
 * such as error and warning messages.
 *
 * <p> CallbackHandlers are implemented in an application-dependent fashion.
 * For example, implementations for an application with a graphical user
 * interface (GUI) may pop up windows to prompt for requested information
 * or to display error messages.  An implementation may also choose to obtain
 * requested information from an alternate source without asking the end user.
 *
 * <p> Underlying security services make requests for different types
 * of information by passing individual Callbacks to the
 * {@code CallbackHandler}.  The {@code CallbackHandler}
 * implementation decides how to retrieve and display information
 * depending on the Callbacks passed to it.  For example,
 * if the underlying service needs a username and password to
 * authenticate a user, it uses a {@code NameCallback} and
 * {@code PasswordCallback}.  The {@code CallbackHandler}
 * can then choose to prompt for a username and password serially,
 * or to prompt for both in a single window.
 *
 * <p> A default {@code CallbackHandler} class implementation
 * may be specified by setting the value of the
 * {@code auth.login.defaultCallbackHandler} security property.
 *
 * <p> If the security property is set to the fully qualified name of a
 * {@code CallbackHandler} implementation class,
 * then a {@code LoginContext} will load the specified
 * {@code CallbackHandler} and pass it to the underlying LoginModules.
 * The {@code LoginContext} only loads the default handler
 * if it was not provided one.
 *
 * <p> All default handler implementations must provide a public
 * zero-argument constructor.
 *
 * <p>
 *  <p>应用程序实现了{@code CallbackHandler}并将其传递给底层安全服务,以便它们可以与应用程序交互以检索特定的身份验证数据(如用户名和密码),或显示某些信息,例如错误和警告消息。
 * 
 *  <p> CallbackHandlers是以应用程序相关的方式实现的。例如,具有图形用户界面(GUI)的应用的实现可以弹出窗口以提示所请求的信息或显示错误消息。
 * 实现还可以选择从替代源获得所请求的信息,而不询问最终用户。
 * 
 *  <p>基础安全服务通过将个别回调传递到{@code CallbackHandler}来请求不同类型的信息。
 *  {@code CallbackHandler}实现决定如何根据传递给它的回调来检索和显示信息。
 * 例如,如果底层服务需要用户名和密码来验证用户,则使用{@code NameCallback}和{@code PasswordCallback}。
 * 然后,{@code CallbackHandler}可以选择提示输入用户名和密码,或者在单个窗口中提示输入。
 * 
 * <p>可以通过设置{@code auth.login.defaultCallbackHandler}安全属性的值来指定默认的{@code CallbackHandler}类实现。
 * 
 *  <p>如果安全属性设置为{@code CallbackHandler}实现类的完全限定名,那么{@code LoginContext}将加载指定的{@code CallbackHandler}并将其传
 * 递给基础的LoginModules。
 *  {@code LoginContext}只加载默认处理程序(如果没有提供)。
 * 
 *  <p>所有默认处理程序实现必须提供一个公共的零参数构造函数。
 * 
 * 
 * @see java.security.Security security properties
 */
public interface CallbackHandler {

    /**
     * <p> Retrieve or display the information requested in the
     * provided Callbacks.
     *
     * <p> The {@code handle} method implementation checks the
     * instance(s) of the {@code Callback} object(s) passed in
     * to retrieve or display the requested information.
     * The following example is provided to help demonstrate what an
     * {@code handle} method implementation might look like.
     * This example code is for guidance only.  Many details,
     * including proper error handling, are left out for simplicity.
     *
     * <pre>{@code
     * public void handle(Callback[] callbacks)
     * throws IOException, UnsupportedCallbackException {
     *
     *   for (int i = 0; i < callbacks.length; i++) {
     *      if (callbacks[i] instanceof TextOutputCallback) {
     *
     *          // display the message according to the specified type
     *          TextOutputCallback toc = (TextOutputCallback)callbacks[i];
     *          switch (toc.getMessageType()) {
     *          case TextOutputCallback.INFORMATION:
     *              System.out.println(toc.getMessage());
     *              break;
     *          case TextOutputCallback.ERROR:
     *              System.out.println("ERROR: " + toc.getMessage());
     *              break;
     *          case TextOutputCallback.WARNING:
     *              System.out.println("WARNING: " + toc.getMessage());
     *              break;
     *          default:
     *              throw new IOException("Unsupported message type: " +
     *                                  toc.getMessageType());
     *          }
     *
     *      } else if (callbacks[i] instanceof NameCallback) {
     *
     *          // prompt the user for a username
     *          NameCallback nc = (NameCallback)callbacks[i];
     *
     *          // ignore the provided defaultName
     *          System.err.print(nc.getPrompt());
     *          System.err.flush();
     *          nc.setName((new BufferedReader
     *                  (new InputStreamReader(System.in))).readLine());
     *
     *      } else if (callbacks[i] instanceof PasswordCallback) {
     *
     *          // prompt the user for sensitive information
     *          PasswordCallback pc = (PasswordCallback)callbacks[i];
     *          System.err.print(pc.getPrompt());
     *          System.err.flush();
     *          pc.setPassword(readPassword(System.in));
     *
     *      } else {
     *          throw new UnsupportedCallbackException
     *                  (callbacks[i], "Unrecognized Callback");
     *      }
     *   }
     * }
     *
     * // Reads user password from given input stream.
     * private char[] readPassword(InputStream in) throws IOException {
     *    // insert code to read a user password from the input stream
     * }
     * }</pre>
     *
     * <p>
     *  <p>检索或显示提供的回调中请求的信息。
     * 
     *  <p> {@code handle}方法实现检查传入的{@code Callback}对象的实例,以检索或显示所请求的信息。提供以下示例以帮助说明{@code handle}方法实现的外观。
     * 此示例代码仅供参考。为了简单起见,省略了许多细节,包括适当的错误处理。
     * 
     *  <pre> {@ code public void handle(Callback [] callbacks)throws IOException,UnsupportedCallbackException {。
     * 
     *  for(int i = 0; i <callbacks.length; i ++){if(callbacks [i] instanceof TextOutputCallback){
     * 
     * //根据指定的类型显示消息TextOutputCallback toc =(TextOutputCallback)callbacks [i]; switch(toc.getMessageType()){case TextOutputCallback.INFORMATION：System.out.println(toc.getMessage());打破; case TextOutputCallback.ERROR：System.out.println("ERROR："+ toc.getMessage());打破; case TextOutputCallback.WARNING：System.out.println("WARNING："+ toc.getMessage());打破; default：throw new IOException("Unsupported message type："+ toc.getMessageType()); }
     * }。
     * 
     *  } else if(callbacks [i] instanceof NameCallback){
     * 
     *  //提示用户输入用户名NameCallback nc =(NameCallback)callbacks [i];
     * 
     * @param callbacks an array of {@code Callback} objects provided
     *          by an underlying security service which contains
     *          the information requested to be retrieved or displayed.
     *
     * @exception java.io.IOException if an input or output error occurs. <p>
     *
     * @exception UnsupportedCallbackException if the implementation of this
     *          method does not support one or more of the Callbacks
     *          specified in the {@code callbacks} parameter.
     */
    void handle(Callback[] callbacks)
    throws java.io.IOException, UnsupportedCallbackException;
}
