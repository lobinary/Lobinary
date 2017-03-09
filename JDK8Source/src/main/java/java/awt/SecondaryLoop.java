/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2010, Oracle and/or its affiliates. All rights reserved.
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

package java.awt;

/**
 * A helper interface to run the nested event loop.
 * <p>
 * Objects that implement this interface are created with the
 * {@link EventQueue#createSecondaryLoop} method. The interface
 * provides two methods, {@link #enter} and {@link #exit},
 * which can be used to start and stop the event loop.
 * <p>
 * When the {@link #enter} method is called, the current
 * thread is blocked until the loop is terminated by the
 * {@link #exit} method. Also, a new event loop is started
 * on the event dispatch thread, which may or may not be
 * the current thread. The loop can be terminated on any
 * thread by calling its {@link #exit} method. After the
 * loop is terminated, the {@code SecondaryLoop} object can
 * be reused to run a new nested event loop.
 * <p>
 * A typical use case of applying this interface is AWT
 * and Swing modal dialogs. When a modal dialog is shown on
 * the event dispatch thread, it enters a new secondary loop.
 * Later, when the dialog is hidden or disposed, it exits
 * the loop, and the thread continues its execution.
 * <p>
 * The following example illustrates a simple use case of
 * secondary loops:
 *
 * <pre>
 *   SecondaryLoop loop;
 *
 *   JButton jButton = new JButton("Button");
 *   jButton.addActionListener(new ActionListener() {
 *       {@code @Override}
 *       public void actionPerformed(ActionEvent e) {
 *           Toolkit tk = Toolkit.getDefaultToolkit();
 *           EventQueue eq = tk.getSystemEventQueue();
 *           loop = eq.createSecondaryLoop();
 *
 *           // Spawn a new thread to do the work
 *           Thread worker = new WorkerThread();
 *           worker.start();
 *
 *           // Enter the loop to block the current event
 *           // handler, but leave UI responsive
 *           if (!loop.enter()) {
 *               // Report an error
 *           }
 *       }
 *   });
 *
 *   class WorkerThread extends Thread {
 *       {@code @Override}
 *       public void run() {
 *           // Perform calculations
 *           doSomethingUseful();
 *
 *           // Exit the loop
 *           loop.exit();
 *       }
 *   }
 * </pre>
 *
 * <p>
 *  用于运行嵌套事件循环的辅助接口。
 * <p>
 *  实现此接口的对象是使用{@link EventQueue#createSecondaryLoop}方法创建的。
 * 该接口提供了两种方法{@link #enter}和{@link #exit},可用于启动和停止事件循环。
 * <p>
 *  当调用{@link #enter}方法时,当前线程被阻塞,直到循环由{@link #exit}方法终止。此外,在事件分派线程上开始新的事件循环,其可以是或可以不是当前线程。
 * 该循环可以通过调用其{@link #exit}方法在任何线程上终止。循环结束后,可以重用{@code SecondaryLoop}对象以运行新的嵌套事件循环。
 * <p>
 *  应用此接口的典型用例是AWT和Swing模态对话框。当在事件分派线程上显示模态对话框时,它进入新的辅助循环。后来,当对话被隐藏或处理时,它退出循环,线程继续执行。
 * <p>
 *  以下示例说明了辅助循环的一个简单用例：
 * 
 * <pre>
 *  次循环;
 * 
 *  JButton jButton = new JButton("Button"); jButton.addActionListener(new ActionListener(){{@code @Override}
 *  public void actionPerformed(ActionEvent e){Toolkit tk = Toolkit.getDefaultToolkit(); EventQueue eq = tk.getSystemEventQueue(); loop = eq.createSecondaryLoop。
 * 
 * //产生一个新的线程来做工作Thread worker = new WorkerThread(); worker.start();
 * 
 *  //输入循环以阻塞当前事件//处理程序,但是让UI响应if(！loop.enter()){//报告错误}}});
 * 
 * 
 * @see Dialog#show
 * @see EventQueue#createSecondaryLoop
 * @see Toolkit#getSystemEventQueue
 *
 * @author Anton Tarasov, Artem Ananiev
 *
 * @since 1.7
 */
public interface SecondaryLoop {

    /**
     * Blocks the execution of the current thread and enters a new
     * secondary event loop on the event dispatch thread.
     * <p>
     * This method can be called by any thread including the event
     * dispatch thread. This thread will be blocked until the {@link
     * #exit} method is called or the loop is terminated. A new
     * secondary loop will be created on the event dispatch thread
     * for dispatching events in either case.
     * <p>
     * This method can only start one new event loop at a time per
     * object. If a secondary event loop has already been started
     * by this object and is currently still running, this method
     * returns {@code false} to indicate that it was not successful
     * in starting a new event loop. Otherwise, this method blocks
     * the calling thread and later returns {@code true} when the
     * new event loop is terminated. At such time, this object can
     * again be used to start another new event loop.
     *
     * <p>
     *  类WorkerThread extends Thread {{@code @Override} public void run(){//执行计算doSomethingUseful();
     * 
     *  // Exit the loop loop.exit(); }}
     * </pre>
     * 
     * 
     * @return {@code true} after termination of the secondary loop,
     *         if the secondary loop was started by this call,
     *         {@code false} otherwise
     */
    public boolean enter();

    /**
     * Unblocks the execution of the thread blocked by the {@link
     * #enter} method and exits the secondary loop.
     * <p>
     * This method resumes the thread that called the {@link #enter}
     * method and exits the secondary loop that was created when
     * the {@link #enter} method was invoked.
     * <p>
     * Note that if any other secondary loop is started while this
     * loop is running, the blocked thread will not resume execution
     * until the nested loop is terminated.
     * <p>
     * If this secondary loop has not been started with the {@link
     * #enter} method, or this secondary loop has already finished
     * with the {@link #exit} method, this method returns {@code
     * false}, otherwise {@code true} is returned.
     *
     * <p>
     *  阻止当前线程的执行,并在事件分派线程上输入新的辅助事件循环。
     * <p>
     *  这个方法可以被包括事件分派线程的任何线程调用。此线程将被阻塞,直到调用{@link #exit}方法或循环终止。将在事件分派线程上创建一个新的辅助循环,用于在任一情况下分派事件。
     * <p>
     *  此方法只能每次启动一个新的事件循环。如果辅助事件循环已经由此对象启动,并且当前仍在运行,则此方法返回{@code false}以指示它在启动新的事件循环中不成功。
     * 否则,此方法会阻塞调用线程,并在新事件循环终止时返回{@code true}。此时,该对象可以再次用于启动另一个新的事件循环。
     * 
     * 
     * @return {@code true} if this loop was previously started and
     *         has not yet been finished with the {@link #exit} method,
     *         {@code false} otherwise
     */
    public boolean exit();

}
