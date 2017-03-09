/***** Lobxxx Translate Finished ******/
/*
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

/*
 *
 *
 *
 *
 *
 * Written by Doug Lea with assistance from members of JCP JSR-166
 * Expert Group and released to the public domain, as explained at
 * http://creativecommons.org/publicdomain/zero/1.0/
 * <p>
 *  由Doug Lea在JCP JSR-166专家组成员的帮助下撰写,并发布到公共领域,如http://creativecommons.org/publicdomain/zero/1.0/
 * 
 */

package java.util.concurrent;
import java.util.function.Supplier;
import java.util.function.Consumer;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.BiFunction;
import java.util.concurrent.Executor;

/**
 * A stage of a possibly asynchronous computation, that performs an
 * action or computes a value when another CompletionStage completes.
 * A stage completes upon termination of its computation, but this may
 * in turn trigger other dependent stages.  The functionality defined
 * in this interface takes only a few basic forms, which expand out to
 * a larger set of methods to capture a range of usage styles: <ul>
 *
 * <li>The computation performed by a stage may be expressed as a
 * Function, Consumer, or Runnable (using methods with names including
 * <em>apply</em>, <em>accept</em>, or <em>run</em>, respectively)
 * depending on whether it requires arguments and/or produces results.
 * For example, {@code stage.thenApply(x -> square(x)).thenAccept(x ->
 * System.out.print(x)).thenRun(() -> System.out.println())}. An
 * additional form (<em>compose</em>) applies functions of stages
 * themselves, rather than their results. </li>
 *
 * <li> One stage's execution may be triggered by completion of a
 * single stage, or both of two stages, or either of two stages.
 * Dependencies on a single stage are arranged using methods with
 * prefix <em>then</em>. Those triggered by completion of
 * <em>both</em> of two stages may <em>combine</em> their results or
 * effects, using correspondingly named methods. Those triggered by
 * <em>either</em> of two stages make no guarantees about which of the
 * results or effects are used for the dependent stage's
 * computation.</li>
 *
 * <li> Dependencies among stages control the triggering of
 * computations, but do not otherwise guarantee any particular
 * ordering. Additionally, execution of a new stage's computations may
 * be arranged in any of three ways: default execution, default
 * asynchronous execution (using methods with suffix <em>async</em>
 * that employ the stage's default asynchronous execution facility),
 * or custom (via a supplied {@link Executor}).  The execution
 * properties of default and async modes are specified by
 * CompletionStage implementations, not this interface. Methods with
 * explicit Executor arguments may have arbitrary execution
 * properties, and might not even support concurrent execution, but
 * are arranged for processing in a way that accommodates asynchrony.
 *
 * <li> Two method forms support processing whether the triggering
 * stage completed normally or exceptionally: Method {@link
 * #whenComplete whenComplete} allows injection of an action
 * regardless of outcome, otherwise preserving the outcome in its
 * completion. Method {@link #handle handle} additionally allows the
 * stage to compute a replacement result that may enable further
 * processing by other dependent stages.  In all other cases, if a
 * stage's computation terminates abruptly with an (unchecked)
 * exception or error, then all dependent stages requiring its
 * completion complete exceptionally as well, with a {@link
 * CompletionException} holding the exception as its cause.  If a
 * stage is dependent on <em>both</em> of two stages, and both
 * complete exceptionally, then the CompletionException may correspond
 * to either one of these exceptions.  If a stage is dependent on
 * <em>either</em> of two others, and only one of them completes
 * exceptionally, no guarantees are made about whether the dependent
 * stage completes normally or exceptionally. In the case of method
 * {@code whenComplete}, when the supplied action itself encounters an
 * exception, then the stage exceptionally completes with this
 * exception if not already completed exceptionally.</li>
 *
 * </ul>
 *
 * <p>All methods adhere to the above triggering, execution, and
 * exceptional completion specifications (which are not repeated in
 * individual method specifications). Additionally, while arguments
 * used to pass a completion result (that is, for parameters of type
 * {@code T}) for methods accepting them may be null, passing a null
 * value for any other parameter will result in a {@link
 * NullPointerException} being thrown.
 *
 * <p>This interface does not define methods for initially creating,
 * forcibly completing normally or exceptionally, probing completion
 * status or results, or awaiting completion of a stage.
 * Implementations of CompletionStage may provide means of achieving
 * such effects, as appropriate.  Method {@link #toCompletableFuture}
 * enables interoperability among different implementations of this
 * interface by providing a common conversion type.
 *
 * <p>
 *  可能异步计算的阶段,执行动作或在另一个CompletionStage完成时计算值。阶段在其计算结束时完成,但是这又可以触发其他相关阶段。
 * 此接口中定义的功能只需要几个基本形式,可扩展到一组更大的方法来捕获一系列使用样式：<ul>。
 * 
 *  <li>阶段执行的计算可以表示为Function,Consumer或Runnable(使用名称包括<em> apply </em>,<em> accept </em>或<em> / em>),具体取决
 * 于它是否需要参数和/或产生结果。
 * For example, {@code stage.thenApply(x -> square(x)).thenAccept(x ->
 *  System.out.print(x))。thenRun(() - > System.out.println())}。
 * 附加表单(<em> compose </em>)适用于阶段本身的功能,而不是结果。 </li>。
 * 
 * <li>一个阶段的执行可以通过完成单个阶段或两个阶段或两个阶段中的任一阶段来触发。使用具有前缀<em>然后</em>的方法来排列对单个阶段的依赖性。
 * 通过完成两个阶段的<em>两者而触发的那些可以使用相应命名的方法来组合</em>它们的结果或效果。由<em>两个阶段的<em>触发的那些不保证关于哪个结果或效果用于依赖阶段的计算。</li>。
 * 
 *  <li>阶段之间的依赖性控制计算的触发,但不以其他方式保证任何特定的排序。
 * 另外,新阶段的计算的执行可以以三种方式中的任一种来布置：默认执行,默认异步执行(使用具有后缀<as> </em>的方法,其使用阶段的默认异步执行设施)或定制提供的{@link Executor})。
 * 默认和异步模式的执行属性由CompletionStage实现指定,而不是此接口。具有显式Executor参数的方法可能具有任意执行属性,甚至可能不支持并行执行,但是以适应异步的方式进行处理。
 * 
 * <li>两种方法表单支持处理触发阶段是正常完成还是异常完成：方法{@link #whenComplete whenComplete}允许注入操作,而不管结果如何,否则在结束时保留结果。
 * 方法{@link #handle handle}另外允许阶段计算替换结果,其可以使得能够由其他依赖阶段进一步处理。
 * 在所有其他情况下,如果一个阶段的计算使用(未检查的)异常或错误突然终止,则所有需要其完成的依赖阶段也会异常完成,其中{@link CompletionException}将异常作为其原因。
 * 如果阶段依赖于两个阶段的两者,并且两者完全异常,则CompletionException可以对应于这些异常中的任一个。
 * 如果一个阶段依赖于两个其他的阶段,并且只有其中一个阶段完全异常,则不能保证依赖阶段是正常完成还是异常完成。
 * 在方法{@code whenComplete}的情况下,当提供的操作本身遇到异常时,如果尚未完全异常完成,则此阶段异常完成此异常。</li>。
 * 
 * </ul>
 * 
 * <p>所有方法都遵循上述触发,执行和异常完成规范(在单个方法规范中不再重复)。
 * 另外,尽管用于传递接受它们的方法的完成结果(即,类型为{@code T}的参数)的参数可能为空,但是为任何其他参数传递空值将导致{@link NullPointerException}抛出。
 * 
 *  <p>此接口没有定义最初创建,强制完成正常或异常探测完成状态或结果,或等待阶段完成的方法。 CompletionStage的实现可以提供适当的实现这些效果的手段。
 * 方法{@link #toCompletableFuture}通过提供常见的转换类型来实现此接口的不同实现之间的互操作性。
 * 
 * 
 * @author Doug Lea
 * @since 1.8
 */
public interface CompletionStage<T> {

    /**
     * Returns a new CompletionStage that, when this stage completes
     * normally, is executed with this stage's result as the argument
     * to the supplied function.
     *
     * See the {@link CompletionStage} documentation for rules
     * covering exceptional completion.
     *
     * <p>
     *  返回一个新的CompletionStage,当此阶段正常完成时,将执行此阶段的结果作为提供的函数的参数。
     * 
     *  有关涵盖特殊完成的规则​​,请参阅{@link CompletionStage}文档。
     * 
     * 
     * @param fn the function to use to compute the value of
     * the returned CompletionStage
     * @param <U> the function's return type
     * @return the new CompletionStage
     */
    public <U> CompletionStage<U> thenApply(Function<? super T,? extends U> fn);

    /**
     * Returns a new CompletionStage that, when this stage completes
     * normally, is executed using this stage's default asynchronous
     * execution facility, with this stage's result as the argument to
     * the supplied function.
     *
     * See the {@link CompletionStage} documentation for rules
     * covering exceptional completion.
     *
     * <p>
     *  返回一个新的CompletionStage,当此阶段正常完成时,使用此阶段的默认异步执行工具执行该阶段的结果作为提供的函数的参数。
     * 
     *  有关涵盖特殊完成的规则​​,请参阅{@link CompletionStage}文档。
     * 
     * 
     * @param fn the function to use to compute the value of
     * the returned CompletionStage
     * @param <U> the function's return type
     * @return the new CompletionStage
     */
    public <U> CompletionStage<U> thenApplyAsync
        (Function<? super T,? extends U> fn);

    /**
     * Returns a new CompletionStage that, when this stage completes
     * normally, is executed using the supplied Executor, with this
     * stage's result as the argument to the supplied function.
     *
     * See the {@link CompletionStage} documentation for rules
     * covering exceptional completion.
     *
     * <p>
     * 返回一个新的CompletionStage,当此阶段正常完成时,使用提供的Executor执行,该阶段的结果作为提供的函数的参数。
     * 
     *  有关涵盖特殊完成的规则​​,请参阅{@link CompletionStage}文档。
     * 
     * 
     * @param fn the function to use to compute the value of
     * the returned CompletionStage
     * @param executor the executor to use for asynchronous execution
     * @param <U> the function's return type
     * @return the new CompletionStage
     */
    public <U> CompletionStage<U> thenApplyAsync
        (Function<? super T,? extends U> fn,
         Executor executor);

    /**
     * Returns a new CompletionStage that, when this stage completes
     * normally, is executed with this stage's result as the argument
     * to the supplied action.
     *
     * See the {@link CompletionStage} documentation for rules
     * covering exceptional completion.
     *
     * <p>
     *  返回一个新的CompletionStage,当此阶段正常完成时,将执行此阶段的结果作为提供的操作的参数。
     * 
     *  有关涵盖特殊完成的规则​​,请参阅{@link CompletionStage}文档。
     * 
     * 
     * @param action the action to perform before completing the
     * returned CompletionStage
     * @return the new CompletionStage
     */
    public CompletionStage<Void> thenAccept(Consumer<? super T> action);

    /**
     * Returns a new CompletionStage that, when this stage completes
     * normally, is executed using this stage's default asynchronous
     * execution facility, with this stage's result as the argument to
     * the supplied action.
     *
     * See the {@link CompletionStage} documentation for rules
     * covering exceptional completion.
     *
     * <p>
     *  返回一个新的CompletionStage,当此阶段正常完成时,使用此阶段的默认异步执行工具执行该阶段的结果作为提供的操作的参数。
     * 
     *  有关涵盖特殊完成的规则​​,请参阅{@link CompletionStage}文档。
     * 
     * 
     * @param action the action to perform before completing the
     * returned CompletionStage
     * @return the new CompletionStage
     */
    public CompletionStage<Void> thenAcceptAsync(Consumer<? super T> action);

    /**
     * Returns a new CompletionStage that, when this stage completes
     * normally, is executed using the supplied Executor, with this
     * stage's result as the argument to the supplied action.
     *
     * See the {@link CompletionStage} documentation for rules
     * covering exceptional completion.
     *
     * <p>
     *  返回一个新的CompletionStage,当此阶段正常完成时,使用提供的Executor执行该阶段的结果作为提供的操作的参数。
     * 
     *  有关涵盖特殊完成的规则​​,请参阅{@link CompletionStage}文档。
     * 
     * 
     * @param action the action to perform before completing the
     * returned CompletionStage
     * @param executor the executor to use for asynchronous execution
     * @return the new CompletionStage
     */
    public CompletionStage<Void> thenAcceptAsync(Consumer<? super T> action,
                                                 Executor executor);
    /**
     * Returns a new CompletionStage that, when this stage completes
     * normally, executes the given action.
     *
     * See the {@link CompletionStage} documentation for rules
     * covering exceptional completion.
     *
     * <p>
     *  返回一个新的CompletionStage,当此阶段正常完成时,执行给定的操作。
     * 
     *  有关涵盖特殊完成的规则​​,请参阅{@link CompletionStage}文档。
     * 
     * 
     * @param action the action to perform before completing the
     * returned CompletionStage
     * @return the new CompletionStage
     */
    public CompletionStage<Void> thenRun(Runnable action);

    /**
     * Returns a new CompletionStage that, when this stage completes
     * normally, executes the given action using this stage's default
     * asynchronous execution facility.
     *
     * See the {@link CompletionStage} documentation for rules
     * covering exceptional completion.
     *
     * <p>
     *  返回一个新的CompletionStage,当此阶段正常完成时,使用此阶段的默认异步执行设施执行给定的操作。
     * 
     * 有关涵盖特殊完成的规则​​,请参阅{@link CompletionStage}文档。
     * 
     * 
     * @param action the action to perform before completing the
     * returned CompletionStage
     * @return the new CompletionStage
     */
    public CompletionStage<Void> thenRunAsync(Runnable action);

    /**
     * Returns a new CompletionStage that, when this stage completes
     * normally, executes the given action using the supplied Executor.
     *
     * See the {@link CompletionStage} documentation for rules
     * covering exceptional completion.
     *
     * <p>
     *  返回一个新的CompletionStage,当此阶段正常完成时,使用提供的Executor执行给定的操作。
     * 
     *  有关涵盖特殊完成的规则​​,请参阅{@link CompletionStage}文档。
     * 
     * 
     * @param action the action to perform before completing the
     * returned CompletionStage
     * @param executor the executor to use for asynchronous execution
     * @return the new CompletionStage
     */
    public CompletionStage<Void> thenRunAsync(Runnable action,
                                              Executor executor);

    /**
     * Returns a new CompletionStage that, when this and the other
     * given stage both complete normally, is executed with the two
     * results as arguments to the supplied function.
     *
     * See the {@link CompletionStage} documentation for rules
     * covering exceptional completion.
     *
     * <p>
     *  返回一个新的CompletionStage,当这个和其他给定阶段都正常完成时,将两个结果作为提供的函数的参数执行。
     * 
     *  有关涵盖特殊完成的规则​​,请参阅{@link CompletionStage}文档。
     * 
     * 
     * @param other the other CompletionStage
     * @param fn the function to use to compute the value of
     * the returned CompletionStage
     * @param <U> the type of the other CompletionStage's result
     * @param <V> the function's return type
     * @return the new CompletionStage
     */
    public <U,V> CompletionStage<V> thenCombine
        (CompletionStage<? extends U> other,
         BiFunction<? super T,? super U,? extends V> fn);

    /**
     * Returns a new CompletionStage that, when this and the other
     * given stage complete normally, is executed using this stage's
     * default asynchronous execution facility, with the two results
     * as arguments to the supplied function.
     *
     * See the {@link CompletionStage} documentation for rules
     * covering exceptional completion.
     *
     * <p>
     *  返回一个新的CompletionStage,当此和其他给定阶段正常完成时,使用此阶段的默认异步执行设施执行,两个结果作为提供的函数的参数。
     * 
     *  有关涵盖特殊完成的规则​​,请参阅{@link CompletionStage}文档。
     * 
     * 
     * @param other the other CompletionStage
     * @param fn the function to use to compute the value of
     * the returned CompletionStage
     * @param <U> the type of the other CompletionStage's result
     * @param <V> the function's return type
     * @return the new CompletionStage
     */
    public <U,V> CompletionStage<V> thenCombineAsync
        (CompletionStage<? extends U> other,
         BiFunction<? super T,? super U,? extends V> fn);

    /**
     * Returns a new CompletionStage that, when this and the other
     * given stage complete normally, is executed using the supplied
     * executor, with the two results as arguments to the supplied
     * function.
     *
     * See the {@link CompletionStage} documentation for rules
     * covering exceptional completion.
     *
     * <p>
     *  返回一个新的CompletionStage,当这个和其他给定阶段正常完成时,使用提供的执行器执行,两个结果作为提供的函数的参数。
     * 
     *  有关涵盖特殊完成的规则​​,请参阅{@link CompletionStage}文档。
     * 
     * 
     * @param other the other CompletionStage
     * @param fn the function to use to compute the value of
     * the returned CompletionStage
     * @param executor the executor to use for asynchronous execution
     * @param <U> the type of the other CompletionStage's result
     * @param <V> the function's return type
     * @return the new CompletionStage
     */
    public <U,V> CompletionStage<V> thenCombineAsync
        (CompletionStage<? extends U> other,
         BiFunction<? super T,? super U,? extends V> fn,
         Executor executor);

    /**
     * Returns a new CompletionStage that, when this and the other
     * given stage both complete normally, is executed with the two
     * results as arguments to the supplied action.
     *
     * See the {@link CompletionStage} documentation for rules
     * covering exceptional completion.
     *
     * <p>
     *  返回一个新的CompletionStage,当此和其他给定阶段都正常完成时,将两个结果作为所提供操作的参数执行。
     * 
     *  有关涵盖特殊完成的规则​​,请参阅{@link CompletionStage}文档。
     * 
     * 
     * @param other the other CompletionStage
     * @param action the action to perform before completing the
     * returned CompletionStage
     * @param <U> the type of the other CompletionStage's result
     * @return the new CompletionStage
     */
    public <U> CompletionStage<Void> thenAcceptBoth
        (CompletionStage<? extends U> other,
         BiConsumer<? super T, ? super U> action);

    /**
     * Returns a new CompletionStage that, when this and the other
     * given stage complete normally, is executed using this stage's
     * default asynchronous execution facility, with the two results
     * as arguments to the supplied action.
     *
     * <p>
     * 返回一个新的CompletionStage,当此和其他给定阶段正常完成时,将使用此阶段的默认异步执行工具执行,两个结果作为提供的操作的参数。
     * 
     * 
     * @param other the other CompletionStage
     * @param action the action to perform before completing the
     * returned CompletionStage
     * @param <U> the type of the other CompletionStage's result
     * @return the new CompletionStage
     */
    public <U> CompletionStage<Void> thenAcceptBothAsync
        (CompletionStage<? extends U> other,
         BiConsumer<? super T, ? super U> action);

    /**
     * Returns a new CompletionStage that, when this and the other
     * given stage complete normally, is executed using the supplied
     * executor, with the two results as arguments to the supplied
     * function.
     *
     * <p>
     *  返回一个新的CompletionStage,当这个和其他给定阶段正常完成时,使用提供的执行器执行,两个结果作为提供的函数的参数。
     * 
     * 
     * @param other the other CompletionStage
     * @param action the action to perform before completing the
     * returned CompletionStage
     * @param executor the executor to use for asynchronous execution
     * @param <U> the type of the other CompletionStage's result
     * @return the new CompletionStage
     */
    public <U> CompletionStage<Void> thenAcceptBothAsync
        (CompletionStage<? extends U> other,
         BiConsumer<? super T, ? super U> action,
         Executor executor);

    /**
     * Returns a new CompletionStage that, when this and the other
     * given stage both complete normally, executes the given action.
     *
     * See the {@link CompletionStage} documentation for rules
     * covering exceptional completion.
     *
     * <p>
     *  返回一个新的CompletionStage,当这个和其他给定阶段都正常完成时,执行给定的操作。
     * 
     *  有关涵盖特殊完成的规则​​,请参阅{@link CompletionStage}文档。
     * 
     * 
     * @param other the other CompletionStage
     * @param action the action to perform before completing the
     * returned CompletionStage
     * @return the new CompletionStage
     */
    public CompletionStage<Void> runAfterBoth(CompletionStage<?> other,
                                              Runnable action);
    /**
     * Returns a new CompletionStage that, when this and the other
     * given stage complete normally, executes the given action using
     * this stage's default asynchronous execution facility.
     *
     * See the {@link CompletionStage} documentation for rules
     * covering exceptional completion.
     *
     * <p>
     *  返回一个新的CompletionStage,当此和其他给定阶段正常完成时,使用此阶段的默认异步执行设施执行给定的操作。
     * 
     *  有关涵盖特殊完成的规则​​,请参阅{@link CompletionStage}文档。
     * 
     * 
     * @param other the other CompletionStage
     * @param action the action to perform before completing the
     * returned CompletionStage
     * @return the new CompletionStage
     */
    public CompletionStage<Void> runAfterBothAsync(CompletionStage<?> other,
                                                   Runnable action);

    /**
     * Returns a new CompletionStage that, when this and the other
     * given stage complete normally, executes the given action using
     * the supplied executor.
     *
     * See the {@link CompletionStage} documentation for rules
     * covering exceptional completion.
     *
     * <p>
     *  返回一个新的CompletionStage,当此和其他给定阶段正常完成时,使用提供的执行器执行给定的操作。
     * 
     *  有关涵盖特殊完成的规则​​,请参阅{@link CompletionStage}文档。
     * 
     * 
     * @param other the other CompletionStage
     * @param action the action to perform before completing the
     * returned CompletionStage
     * @param executor the executor to use for asynchronous execution
     * @return the new CompletionStage
     */
    public CompletionStage<Void> runAfterBothAsync(CompletionStage<?> other,
                                                   Runnable action,
                                                   Executor executor);
    /**
     * Returns a new CompletionStage that, when either this or the
     * other given stage complete normally, is executed with the
     * corresponding result as argument to the supplied function.
     *
     * See the {@link CompletionStage} documentation for rules
     * covering exceptional completion.
     *
     * <p>
     *  返回一个新的CompletionStage,当此或其他给定阶段正常完成时,将执行相应的结果作为所提供函数的参数。
     * 
     *  有关涵盖特殊完成的规则​​,请参阅{@link CompletionStage}文档。
     * 
     * 
     * @param other the other CompletionStage
     * @param fn the function to use to compute the value of
     * the returned CompletionStage
     * @param <U> the function's return type
     * @return the new CompletionStage
     */
    public <U> CompletionStage<U> applyToEither
        (CompletionStage<? extends T> other,
         Function<? super T, U> fn);

    /**
     * Returns a new CompletionStage that, when either this or the
     * other given stage complete normally, is executed using this
     * stage's default asynchronous execution facility, with the
     * corresponding result as argument to the supplied function.
     *
     * See the {@link CompletionStage} documentation for rules
     * covering exceptional completion.
     *
     * <p>
     * 返回一个新的CompletionStage,当此或其他给定阶段正常完成时,将使用此阶段的默认异步执行工具执行,相应的结果作为参数提供的函数。
     * 
     *  有关涵盖特殊完成的规则​​,请参阅{@link CompletionStage}文档。
     * 
     * 
     * @param other the other CompletionStage
     * @param fn the function to use to compute the value of
     * the returned CompletionStage
     * @param <U> the function's return type
     * @return the new CompletionStage
     */
    public <U> CompletionStage<U> applyToEitherAsync
        (CompletionStage<? extends T> other,
         Function<? super T, U> fn);

    /**
     * Returns a new CompletionStage that, when either this or the
     * other given stage complete normally, is executed using the
     * supplied executor, with the corresponding result as argument to
     * the supplied function.
     *
     * See the {@link CompletionStage} documentation for rules
     * covering exceptional completion.
     *
     * <p>
     *  返回一个新的CompletionStage,当此或其他给定阶段正常完成时,将使用提供的执行器执行,相应的结果作为参数提供的函数。
     * 
     *  有关涵盖特殊完成的规则​​,请参阅{@link CompletionStage}文档。
     * 
     * 
     * @param other the other CompletionStage
     * @param fn the function to use to compute the value of
     * the returned CompletionStage
     * @param executor the executor to use for asynchronous execution
     * @param <U> the function's return type
     * @return the new CompletionStage
     */
    public <U> CompletionStage<U> applyToEitherAsync
        (CompletionStage<? extends T> other,
         Function<? super T, U> fn,
         Executor executor);

    /**
     * Returns a new CompletionStage that, when either this or the
     * other given stage complete normally, is executed with the
     * corresponding result as argument to the supplied action.
     *
     * See the {@link CompletionStage} documentation for rules
     * covering exceptional completion.
     *
     * <p>
     *  返回一个新的CompletionStage,当此或其他给定阶段正常完成时,将执行相应的结果作为所提供操作的参数。
     * 
     *  有关涵盖特殊完成的规则​​,请参阅{@link CompletionStage}文档。
     * 
     * 
     * @param other the other CompletionStage
     * @param action the action to perform before completing the
     * returned CompletionStage
     * @return the new CompletionStage
     */
    public CompletionStage<Void> acceptEither
        (CompletionStage<? extends T> other,
         Consumer<? super T> action);

    /**
     * Returns a new CompletionStage that, when either this or the
     * other given stage complete normally, is executed using this
     * stage's default asynchronous execution facility, with the
     * corresponding result as argument to the supplied action.
     *
     * See the {@link CompletionStage} documentation for rules
     * covering exceptional completion.
     *
     * <p>
     *  返回一个新的CompletionStage,当此或其他给定阶段正常完成时,将使用此阶段的默认异步执行工具执行,相应的结果作为所提供操作的参数。
     * 
     *  有关涵盖特殊完成的规则​​,请参阅{@link CompletionStage}文档。
     * 
     * 
     * @param other the other CompletionStage
     * @param action the action to perform before completing the
     * returned CompletionStage
     * @return the new CompletionStage
     */
    public CompletionStage<Void> acceptEitherAsync
        (CompletionStage<? extends T> other,
         Consumer<? super T> action);

    /**
     * Returns a new CompletionStage that, when either this or the
     * other given stage complete normally, is executed using the
     * supplied executor, with the corresponding result as argument to
     * the supplied function.
     *
     * See the {@link CompletionStage} documentation for rules
     * covering exceptional completion.
     *
     * <p>
     *  返回一个新的CompletionStage,当此或其他给定阶段正常完成时,将使用提供的执行器执行,相应的结果作为参数提供的函数。
     * 
     * 有关涵盖特殊完成的规则​​,请参阅{@link CompletionStage}文档。
     * 
     * 
     * @param other the other CompletionStage
     * @param action the action to perform before completing the
     * returned CompletionStage
     * @param executor the executor to use for asynchronous execution
     * @return the new CompletionStage
     */
    public CompletionStage<Void> acceptEitherAsync
        (CompletionStage<? extends T> other,
         Consumer<? super T> action,
         Executor executor);

    /**
     * Returns a new CompletionStage that, when either this or the
     * other given stage complete normally, executes the given action.
     *
     * See the {@link CompletionStage} documentation for rules
     * covering exceptional completion.
     *
     * <p>
     *  返回一个新的CompletionStage,当此或其他给定阶段正常完成时,执行给定的操作。
     * 
     *  有关涵盖特殊完成的规则​​,请参阅{@link CompletionStage}文档。
     * 
     * 
     * @param other the other CompletionStage
     * @param action the action to perform before completing the
     * returned CompletionStage
     * @return the new CompletionStage
     */
    public CompletionStage<Void> runAfterEither(CompletionStage<?> other,
                                                Runnable action);

    /**
     * Returns a new CompletionStage that, when either this or the
     * other given stage complete normally, executes the given action
     * using this stage's default asynchronous execution facility.
     *
     * See the {@link CompletionStage} documentation for rules
     * covering exceptional completion.
     *
     * <p>
     *  返回一个新的CompletionStage,当此或其他给定阶段正常完成时,使用此阶段的默认异步执行设施执行给定的操作。
     * 
     *  有关涵盖特殊完成的规则​​,请参阅{@link CompletionStage}文档。
     * 
     * 
     * @param other the other CompletionStage
     * @param action the action to perform before completing the
     * returned CompletionStage
     * @return the new CompletionStage
     */
    public CompletionStage<Void> runAfterEitherAsync
        (CompletionStage<?> other,
         Runnable action);

    /**
     * Returns a new CompletionStage that, when either this or the
     * other given stage complete normally, executes the given action
     * using the supplied executor.
     *
     * See the {@link CompletionStage} documentation for rules
     * covering exceptional completion.
     *
     * <p>
     *  返回一个新的CompletionStage,当此或其他给定阶段正常完成时,使用提供的执行程序执行给定的操作。
     * 
     *  有关涵盖特殊完成的规则​​,请参阅{@link CompletionStage}文档。
     * 
     * 
     * @param other the other CompletionStage
     * @param action the action to perform before completing the
     * returned CompletionStage
     * @param executor the executor to use for asynchronous execution
     * @return the new CompletionStage
     */
    public CompletionStage<Void> runAfterEitherAsync
        (CompletionStage<?> other,
         Runnable action,
         Executor executor);

    /**
     * Returns a new CompletionStage that, when this stage completes
     * normally, is executed with this stage as the argument
     * to the supplied function.
     *
     * See the {@link CompletionStage} documentation for rules
     * covering exceptional completion.
     *
     * <p>
     *  返回一个新的CompletionStage,当此阶段正常完成时,将以此阶段作为提供的函数的参数执行。
     * 
     *  有关涵盖特殊完成的规则​​,请参阅{@link CompletionStage}文档。
     * 
     * 
     * @param fn the function returning a new CompletionStage
     * @param <U> the type of the returned CompletionStage's result
     * @return the CompletionStage
     */
    public <U> CompletionStage<U> thenCompose
        (Function<? super T, ? extends CompletionStage<U>> fn);

    /**
     * Returns a new CompletionStage that, when this stage completes
     * normally, is executed using this stage's default asynchronous
     * execution facility, with this stage as the argument to the
     * supplied function.
     *
     * See the {@link CompletionStage} documentation for rules
     * covering exceptional completion.
     *
     * <p>
     *  返回一个新的CompletionStage,当此阶段正常完成时,将使用此阶段的默认异步执行工具执行,此阶段作为提供的函数的参数。
     * 
     *  有关涵盖特殊完成的规则​​,请参阅{@link CompletionStage}文档。
     * 
     * 
     * @param fn the function returning a new CompletionStage
     * @param <U> the type of the returned CompletionStage's result
     * @return the CompletionStage
     */
    public <U> CompletionStage<U> thenComposeAsync
        (Function<? super T, ? extends CompletionStage<U>> fn);

    /**
     * Returns a new CompletionStage that, when this stage completes
     * normally, is executed using the supplied Executor, with this
     * stage's result as the argument to the supplied function.
     *
     * See the {@link CompletionStage} documentation for rules
     * covering exceptional completion.
     *
     * <p>
     * 返回一个新的CompletionStage,当此阶段正常完成时,使用提供的Executor执行,该阶段的结果作为提供的函数的参数。
     * 
     *  有关涵盖特殊完成的规则​​,请参阅{@link CompletionStage}文档。
     * 
     * 
     * @param fn the function returning a new CompletionStage
     * @param executor the executor to use for asynchronous execution
     * @param <U> the type of the returned CompletionStage's result
     * @return the CompletionStage
     */
    public <U> CompletionStage<U> thenComposeAsync
        (Function<? super T, ? extends CompletionStage<U>> fn,
         Executor executor);

    /**
     * Returns a new CompletionStage that, when this stage completes
     * exceptionally, is executed with this stage's exception as the
     * argument to the supplied function.  Otherwise, if this stage
     * completes normally, then the returned stage also completes
     * normally with the same value.
     *
     * <p>
     *  返回一个新的CompletionStage,当此阶段异常完成时,将作为提供的函数的参数使用此阶段的异常执行。否则,如果此阶段正常完成,则返回的阶段也将以相同的值正常完成。
     * 
     * 
     * @param fn the function to use to compute the value of the
     * returned CompletionStage if this CompletionStage completed
     * exceptionally
     * @return the new CompletionStage
     */
    public CompletionStage<T> exceptionally
        (Function<Throwable, ? extends T> fn);

    /**
     * Returns a new CompletionStage with the same result or exception as
     * this stage, that executes the given action when this stage completes.
     *
     * <p>When this stage is complete, the given action is invoked with the
     * result (or {@code null} if none) and the exception (or {@code null}
     * if none) of this stage as arguments.  The returned stage is completed
     * when the action returns.  If the supplied action itself encounters an
     * exception, then the returned stage exceptionally completes with this
     * exception unless this stage also completed exceptionally.
     *
     * <p>
     *  返回与此阶段具有相同结果或异常的新CompletionStage,该阶段在此阶段完成时执行给定操作。
     * 
     *  <p>当这个阶段完成时,使用结果(或{@code null}(如果没有))和本阶段的异常(或{@code null}如果没有)调用给定的操作作为参数。当操作返回时,返回的阶段完成。
     * 如果提供的操作本身遇到异常,则返回的阶段异常地完成此异常,除非此阶段也异常完成。
     * 
     * 
     * @param action the action to perform
     * @return the new CompletionStage
     */
    public CompletionStage<T> whenComplete
        (BiConsumer<? super T, ? super Throwable> action);

    /**
     * Returns a new CompletionStage with the same result or exception as
     * this stage, that executes the given action using this stage's
     * default asynchronous execution facility when this stage completes.
     *
     * <p>When this stage is complete, the given action is invoked with the
     * result (or {@code null} if none) and the exception (or {@code null}
     * if none) of this stage as arguments.  The returned stage is completed
     * when the action returns.  If the supplied action itself encounters an
     * exception, then the returned stage exceptionally completes with this
     * exception unless this stage also completed exceptionally.
     *
     * <p>
     *  返回与此阶段具有相同结果或异常的新CompletionStage,当此阶段完成时,使用此阶段的默认异步执行设施执行给定操作。
     * 
     * <p>当这个阶段完成时,使用结果(或{@code null}(如果没有))和本阶段的异常(或{@code null}如果没有)调用给定的操作作为参数。当操作返回时,返回的阶段完成。
     * 如果提供的操作本身遇到异常,则返回的阶段异常地完成此异常,除非此阶段也异常完成。
     * 
     * 
     * @param action the action to perform
     * @return the new CompletionStage
     */
    public CompletionStage<T> whenCompleteAsync
        (BiConsumer<? super T, ? super Throwable> action);

    /**
     * Returns a new CompletionStage with the same result or exception as
     * this stage, that executes the given action using the supplied
     * Executor when this stage completes.
     *
     * <p>When this stage is complete, the given action is invoked with the
     * result (or {@code null} if none) and the exception (or {@code null}
     * if none) of this stage as arguments.  The returned stage is completed
     * when the action returns.  If the supplied action itself encounters an
     * exception, then the returned stage exceptionally completes with this
     * exception unless this stage also completed exceptionally.
     *
     * <p>
     *  返回一个与此阶段具有相同结果或异常的新CompletionStage,当此阶段完成时,使用提供的Executor执行给定操作。
     * 
     *  <p>当这个阶段完成时,使用结果(或{@code null}(如果没有))和本阶段的异常(或{@code null}如果没有)调用给定的操作作为参数。当操作返回时,返回的阶段完成。
     * 如果提供的操作本身遇到异常,则返回的阶段异常地完成此异常,除非此阶段也异常完成。
     * 
     * 
     * @param action the action to perform
     * @param executor the executor to use for asynchronous execution
     * @return the new CompletionStage
     */
    public CompletionStage<T> whenCompleteAsync
        (BiConsumer<? super T, ? super Throwable> action,
         Executor executor);

    /**
     * Returns a new CompletionStage that, when this stage completes
     * either normally or exceptionally, is executed with this stage's
     * result and exception as arguments to the supplied function.
     *
     * <p>When this stage is complete, the given function is invoked
     * with the result (or {@code null} if none) and the exception (or
     * {@code null} if none) of this stage as arguments, and the
     * function's result is used to complete the returned stage.
     *
     * <p>
     *  返回一个新的CompletionStage,当此阶段正常完成或异常完成时,将执行此阶段的结果和异常作为提供的函数的参数。
     * 
     *  <p>当这个阶段完成时,使用结果(或{@code null}(如果没有))和本阶段的异常(或{@code null}如果没有)作为参数调用给定的函数,函数的结果用于完成返回阶段。
     * 
     * 
     * @param fn the function to use to compute the value of the
     * returned CompletionStage
     * @param <U> the function's return type
     * @return the new CompletionStage
     */
    public <U> CompletionStage<U> handle
        (BiFunction<? super T, Throwable, ? extends U> fn);

    /**
     * Returns a new CompletionStage that, when this stage completes
     * either normally or exceptionally, is executed using this stage's
     * default asynchronous execution facility, with this stage's
     * result and exception as arguments to the supplied function.
     *
     * <p>When this stage is complete, the given function is invoked
     * with the result (or {@code null} if none) and the exception (or
     * {@code null} if none) of this stage as arguments, and the
     * function's result is used to complete the returned stage.
     *
     * <p>
     * 返回一个新的CompletionStage,当此阶段正常完成或异常完成时,使用此阶段的默认异步执行机制执行该阶段的结果和异常作为提供的函数的参数。
     * 
     *  <p>当这个阶段完成时,使用结果(或{@code null}(如果没有))和本阶段的异常(或{@code null}如果没有)作为参数调用给定的函数,函数的结果用于完成返回阶段。
     * 
     * 
     * @param fn the function to use to compute the value of the
     * returned CompletionStage
     * @param <U> the function's return type
     * @return the new CompletionStage
     */
    public <U> CompletionStage<U> handleAsync
        (BiFunction<? super T, Throwable, ? extends U> fn);

    /**
     * Returns a new CompletionStage that, when this stage completes
     * either normally or exceptionally, is executed using the
     * supplied executor, with this stage's result and exception as
     * arguments to the supplied function.
     *
     * <p>When this stage is complete, the given function is invoked
     * with the result (or {@code null} if none) and the exception (or
     * {@code null} if none) of this stage as arguments, and the
     * function's result is used to complete the returned stage.
     *
     * <p>
     *  返回一个新的CompletionStage,当此阶段正常完成或异常完成时,使用提供的执行器执行该阶段的结果和异常作为提供的函数的参数。
     * 
     *  <p>当这个阶段完成时,使用结果(或{@code null}(如果没有))和本阶段的异常(或{@code null}如果没有)作为参数调用给定的函数,函数的结果用于完成返回阶段。
     * 
     * 
     * @param fn the function to use to compute the value of the
     * returned CompletionStage
     * @param executor the executor to use for asynchronous execution
     * @param <U> the function's return type
     * @return the new CompletionStage
     */
    public <U> CompletionStage<U> handleAsync
        (BiFunction<? super T, Throwable, ? extends U> fn,
         Executor executor);

    /**
     * Returns a {@link CompletableFuture} maintaining the same
     * completion properties as this stage. If this stage is already a
     * CompletableFuture, this method may return this stage itself.
     * Otherwise, invocation of this method may be equivalent in
     * effect to {@code thenApply(x -> x)}, but returning an instance
     * of type {@code CompletableFuture}. A CompletionStage
     * implementation that does not choose to interoperate with others
     * may throw {@code UnsupportedOperationException}.
     *
     * <p>
     *  返回{@link CompletableFuture}维护与此阶段相同的完成属性。如果这个阶段已经是一个CompletableFuture,这个方法可能会返回这个阶段本身。
     * 否则,调用此方法可能等效于{@code thenApply(x  - > x)},但返回类型为{@code CompletableFuture}的实例。
     * 
     * @return the CompletableFuture
     * @throws UnsupportedOperationException if this implementation
     * does not interoperate with CompletableFuture
     */
    public CompletableFuture<T> toCompletableFuture();

}
