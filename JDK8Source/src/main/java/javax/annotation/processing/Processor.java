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

package javax.annotation.processing;

import java.util.Set;
import javax.lang.model.util.Elements;
import javax.lang.model.AnnotatedConstruct;
import javax.lang.model.element.*;
import javax.lang.model.SourceVersion;

/**
 * The interface for an annotation processor.
 *
 * <p>Annotation processing happens in a sequence of {@linkplain
 * javax.annotation.processing.RoundEnvironment rounds}.  On each
 * round, a processor may be asked to {@linkplain #process process} a
 * subset of the annotations found on the source and class files
 * produced by a prior round.  The inputs to the first round of
 * processing are the initial inputs to a run of the tool; these
 * initial inputs can be regarded as the output of a virtual zeroth
 * round of processing.  If a processor was asked to process on a
 * given round, it will be asked to process on subsequent rounds,
 * including the last round, even if there are no annotations for it
 * to process.  The tool infrastructure may also ask a processor to
 * process files generated implicitly by the tool's operation.
 *
 * <p> Each implementation of a {@code Processor} must provide a
 * public no-argument constructor to be used by tools to instantiate
 * the processor.  The tool infrastructure will interact with classes
 * implementing this interface as follows:
 *
 * <ol>
 *
 * <li>If an existing {@code Processor} object is not being used, to
 * create an instance of a processor the tool calls the no-arg
 * constructor of the processor class.
 *
 * <li>Next, the tool calls the {@link #init init} method with
 * an appropriate {@code ProcessingEnvironment}.
 *
 * <li>Afterwards, the tool calls {@link #getSupportedAnnotationTypes
 * getSupportedAnnotationTypes}, {@link #getSupportedOptions
 * getSupportedOptions}, and {@link #getSupportedSourceVersion
 * getSupportedSourceVersion}.  These methods are only called once per
 * run, not on each round.
 *
 * <li>As appropriate, the tool calls the {@link #process process}
 * method on the {@code Processor} object; a new {@code Processor}
 * object is <em>not</em> created for each round.
 *
 * </ol>
 *
 * If a processor object is created and used without the above
 * protocol being followed, then the processor's behavior is not
 * defined by this interface specification.
 *
 * <p> The tool uses a <i>discovery process</i> to find annotation
 * processors and decide whether or not they should be run.  By
 * configuring the tool, the set of potential processors can be
 * controlled.  For example, for a {@link javax.tools.JavaCompiler
 * JavaCompiler} the list of candidate processors to run can be
 * {@linkplain javax.tools.JavaCompiler.CompilationTask#setProcessors
 * set directly} or controlled by a {@linkplain
 * javax.tools.StandardLocation#ANNOTATION_PROCESSOR_PATH search path}
 * used for a {@linkplain java.util.ServiceLoader service-style}
 * lookup.  Other tool implementations may have different
 * configuration mechanisms, such as command line options; for
 * details, refer to the particular tool's documentation.  Which
 * processors the tool asks to {@linkplain #process run} is a function
 * of the types of the annotations <em>{@linkplain AnnotatedConstruct present}</em>
 * on the {@linkplain
 * RoundEnvironment#getRootElements root elements}, what {@linkplain
 * #getSupportedAnnotationTypes annotation types a processor
 * supports}, and whether or not a processor {@linkplain #process
 * claims the annotation types it processes}.  A processor will be asked to
 * process a subset of the annotation types it supports, possibly an
 * empty set.
 *
 * For a given round, the tool computes the set of annotation types
 * that are present on the elements enclosed within the root elements.
 * If there is at least one annotation type present, then as
 * processors claim annotation types, they are removed from the set of
 * unmatched annotation types.  When the set is empty or no more
 * processors are available, the round has run to completion.  If
 * there are no annotation types present, annotation processing still
 * occurs but only <i>universal processors</i> which support
 * processing all annotation types, {@code "*"}, can claim the (empty)
 * set of annotation types.
 *
 * <p>An annotation type is considered present if there is at least
 * one annotation of that type present on an element enclosed within
 * the root elements of a round. For this purpose, a type parameter is
 * considered to be enclosed by its {@linkplain
 * TypeParameterElement#getGenericElement generic
 * element}. Annotations on {@linkplain
 * java.lang.annotation.ElementType#TYPE_USE type uses}, as opposed to
 * annotations on elements, are ignored when computing whether or not
 * an annotation type is present.
 *
 * <p>An annotation is present if it meets the definition of being
 * present given in {@link AnnotatedConstruct}. In brief, an
 * annotation is considered present for the purposes of discovery if
 * it is directly present or present via inheritance. An annotation is
 * <em>not</em> considered present by virtue of being wrapped by a
 * container annotation. Operationally, this is equivalent to an
 * annotation being present on an element if and only if it would be
 * included in the results of {@link
 * Elements#getAllAnnotationMirrors(Element)} called on that element. Since
 * annotations inside container annotations are not considered
 * present, to properly process {@linkplain
 * java.lang.annotation.Repeatable repeatable annotation types},
 * processors are advised to include both the repeatable annotation
 * type and its containing annotation type in the set of {@linkplain
 * #getSupportedAnnotationTypes() supported annotation types} of a
 * processor.
 *
 * <p>Note that if a processor supports {@code "*"} and returns {@code
 * true}, all annotations are claimed.  Therefore, a universal
 * processor being used to, for example, implement additional validity
 * checks should return {@code false} so as to not prevent other such
 * checkers from being able to run.
 *
 * <p>If a processor throws an uncaught exception, the tool may cease
 * other active annotation processors.  If a processor raises an
 * error, the current round will run to completion and the subsequent
 * round will indicate an {@linkplain RoundEnvironment#errorRaised
 * error was raised}.  Since annotation processors are run in a
 * cooperative environment, a processor should throw an uncaught
 * exception only in situations where no error recovery or reporting
 * is feasible.
 *
 * <p>The tool environment is not required to support annotation
 * processors that access environmental resources, either {@linkplain
 * RoundEnvironment per round} or {@linkplain ProcessingEnvironment
 * cross-round}, in a multi-threaded fashion.
 *
 * <p>If the methods that return configuration information about the
 * annotation processor return {@code null}, return other invalid
 * input, or throw an exception, the tool infrastructure must treat
 * this as an error condition.
 *
 * <p>To be robust when running in different tool implementations, an
 * annotation processor should have the following properties:
 *
 * <ol>
 *
 * <li>The result of processing a given input is not a function of the presence or absence
 * of other inputs (orthogonality).
 *
 * <li>Processing the same input produces the same output (consistency).
 *
 * <li>Processing input <i>A</i> followed by processing input <i>B</i>
 * is equivalent to processing <i>B</i> then <i>A</i>
 * (commutativity)
 *
 * <li>Processing an input does not rely on the presence of the output
 * of other annotation processors (independence)
 *
 * </ol>
 *
 * <p>The {@link Filer} interface discusses restrictions on how
 * processors can operate on files.
 *
 * <p>Note that implementors of this interface may find it convenient
 * to extend {@link AbstractProcessor} rather than implementing this
 * interface directly.
 *
 * <p>
 *  注释处理器的接口。
 * 
 *  <p>注释处理发生在{@linkplain javax.annotation.processing.RoundEnvironment rounds}序列中。
 * 在每一轮中,可以要求处理器{@linkplain #process process}在先前轮次产生的源文件和类文件上找到的注释的子集。
 * 第一轮处理的输入是工具运行的初始输入;这些初始输入可以被认为是虚拟零次处理的输出。如果要求处理器在给定轮次上处理,则将要求其在后续轮次(包括最后一轮轮次)上处理,即使没有要处理的注释。
 * 工具基础设施还可以要求处理器处理由工具的操作隐式生成的文件。
 * 
 *  <p> {@code Processor}的每个实现都必须提供一个公开的无参构造函数,以供工具用来实例化处理器。工具基础设施将与实现此接口的类进行交互,如下所示：
 * 
 * <ol>
 * 
 *  <li>如果未使用现有的{@code Processor}对象,则要创建处理器实例,工具将调用处理器类的无参构造函数。
 * 
 *  <li>接下来,该工具使用适当的{@code ProcessingEnvironment}调用{@link #init init}方法。
 * 
 * <li>之后,该工具会调用{@link #getSupportedAnnotationTypes getSupportedAnnotationTypes},{@link #getSupportedOptions getSupportedOptions}
 * 和{@link #getSupportedSourceVersion getSupportedSourceVersion}。
 * 这些方法仅每次运行调用一次,而不是每次调用。
 * 
 *  <li>视情况,工具会调用{@code Processor}对象上的{@link #process process}方法;为每个回合创建了一个新的{@code Processor}对象<em> </em>
 * 。
 * 
 * </ol>
 * 
 *  如果在没有遵循上述协议的情况下创建和使用处理器对象,则处理器的行为不由该接口规范定义。
 * 
 * <p>工具使用<i>发现过程</i>来查找注释处理器,并决定是否应运行它们。通过配置该工具,可以控制该组潜在的处理器。
 * 例如,对于{@link javax.tools.JavaCompiler JavaCompiler},要运行的候选处理器的列表可以是{@linkplain javax.tools.JavaCompiler.CompilationTask#setProcessors set directly}
 * 或由{@linkplain javax.tools。
 * <p>工具使用<i>发现过程</i>来查找注释处理器,并决定是否应运行它们。通过配置该工具,可以控制该组潜在的处理器。
 *  StandardLocation#ANNOTATION_PROCESSOR_PATH搜索路径}用于{@linkplain java.util.ServiceLoader服务样式}查找。
 * 其他工具实现可以具有不同的配置机制,例如命令行选项;有关详细信息,请参阅特定工具的文档。
 * 该工具要求{@linkplain #process run}的哪些处理器是{@linkplain RoundEnvironment#getRootElements根元素}上的注释类型<em> {@ linkplain AnnotatedConstruct present}
 *  </em>的函数,{@ linkplain #getSupportedAnnotationTypes注释类型处理器支持},以及处理器{@linkplain #process是否声明其处理的注释类型}。
 * 其他工具实现可以具有不同的配置机制,例如命令行选项;有关详细信息,请参阅特定工具的文档。将要求处理器处理它支持的注释类型的子集,可能是空集合。
 * 
 * 对于给定的轮次,工具计算在根元素中包含的元素上存在的注释类型集合。如果存在至少一个注释类型,则当处理器要求注释类型时,从不匹配的注释类型的集合中去除它们。
 * 当集合为空或没有更多的处理器可用时,轮次已运行到完成。如果不存在注释类型,则仍然发生注释处理,但是只有支持处理所有注释类型{@code"*"}的通用处理器</i>可以声明(空)注释类型集合。
 * 
 *  <p>如果在一个回合的根元素中包含的元素上存在至少一个该类型的注释,则认为注释类型存在。
 * 为此,类型参数被认为由其{@linkplain TypeParameterElement#getGenericElement generic element}包围。
 * 在计算注释类型是否存在时,将忽略{@linkplain java.lang.annotation.ElementType#TYPE_USE type uses}的注释,而不是元素上的注释。
 * 
 * <p>如果符合{@link AnnotatedConstruct}中给出的定义,则会出现注释。简而言之,如果注释直接存在或通过继承存在,则认为注释存在用于发现的目的。
 * 注释不是</em>由于被容器注释包装而被认为是存在的。
 * 在操作上,这等价于一个元素上存在的注释,当且仅当它包含在对该元素调用的{@link Elements#getAllAnnotationMirrors(Element)}的结果中时。
 * 由于不认为容器注释内的注释存在,为了正确处理{@linkplain java.lang.annotation.Repeatable可重复注释类型},建议处理器将可重复注释类型及其包含注释类型包括在{@linkplain #getSupportedAnnotationTypes()支持的注释类型}
 * 。
 * 在操作上,这等价于一个元素上存在的注释,当且仅当它包含在对该元素调用的{@link Elements#getAllAnnotationMirrors(Element)}的结果中时。
 * 
 *  <p>请注意,如果处理器支持{@code"*"}并返回{@code true},则会声明所有注释。
 * 因此,用于例如实现附加有效性检查的通用处理器应当返回{@code false},以便不阻止其他这样的检查器能够运行。
 * 
 * <p>如果处理器抛出未捕获的异常,则工具可能会停止其他活动的注释处理器。
 * 如果处理器产生错误,当前轮次将运行到完成,并且后续轮次将指示{@linkplain RoundEnvironment#errorRaised错误被抛出}。
 * 由于注释处理器在协作环境中运行,因此处理器应该仅在没有错误恢复或报告可行的情况下抛出未捕获异常。
 * 
 *  <p>工具环境不需要支持以多线程方式访问环境资源的注释处理器,无论是{@linkplain RoundEnvironment per round}还是{@linkplain ProcessingEnvironment cross-round}
 * 。
 * 
 *  <p>如果返回注释处理程序的配置信息的方法返回{@code null},返回其他无效输入或抛出异常,则工具基础结构必须将此视为错误条件。
 * 
 *  <p>为了在不同工具实现中运行时运行稳健,注解处理器应具有以下属性：
 * 
 * <ol>
 * 
 *  <li>处理给定输入的结果不是是否存在其他输入(正交性)的函数。
 * 
 *  <li>处理相同的输入会产生相同的输出(一致性)。
 * 
 *  <li>处理输入<i> A </i>后接处理输入</i>等同于处理<B> </A>(交换性)
 * 
 *  <li>处理输入不依赖于其他注释处理器的输出(独立性)
 * 
 * </ol>
 * 
 * <p> {@link Filer}界面讨论了处理器如何对文件执行操作的限制。
 * 
 *  <p>请注意,此接口的实现者可能会发现扩展{@link AbstractProcessor}方便,而不是直接实现此接口。
 * 
 * 
 * @author Joseph D. Darcy
 * @author Scott Seligman
 * @author Peter von der Ah&eacute;
 * @since 1.6
 */
public interface Processor {
    /**
     * Returns the options recognized by this processor.  An
     * implementation of the processing tool must provide a way to
     * pass processor-specific options distinctly from options passed
     * to the tool itself, see {@link ProcessingEnvironment#getOptions
     * getOptions}.
     *
     * <p>Each string returned in the set must be a period separated
     * sequence of {@linkplain
     * javax.lang.model.SourceVersion#isIdentifier identifiers}:
     *
     * <blockquote>
     * <dl>
     * <dt><i>SupportedOptionString:</i>
     * <dd><i>Identifiers</i>
     *
     * <dt><i>Identifiers:</i>
     * <dd> <i>Identifier</i>
     * <dd> <i>Identifier</i> {@code .} <i>Identifiers</i>
     *
     * <dt><i>Identifier:</i>
     * <dd>Syntactic identifier, including keywords and literals
     * </dl>
     * </blockquote>
     *
     * <p> A tool might use this information to determine if any
     * options provided by a user are unrecognized by any processor,
     * in which case it may wish to report a warning.
     *
     * <p>
     *  返回此处理器可识别的选项。
     * 处理工具的实现必须提供一种方法,使处理器特定的选项不同于传递给工具本身的选项,参见{@link ProcessingEnvironment#getOptions getOptions}。
     * 
     *  <p>集合中返回的每个字符串必须是{@linkplain javax.lang.model.SourceVersion#isIdentifier identifiers}的句点分隔序列：
     * 
     * <blockquote>
     * <dl>
     *  <dt> <i> SupportedOptionString：</i> <dd> <i>标识符</i>
     * 
     *  <dt> <i>标识符：</i> <dd> <i>标识符</i> <dd> <i>标识符</i> {@code}
     * 
     *  <dt> <i>标识符：</i> <dd>语法标识符,包括关键字和文字
     * </dl>
     * </blockquote>
     * 
     *  <p>工具可能使用此信息来确定用户提供的任何选项是否被任何处理器无法识别,在这种情况下,它可能希望报告警告。
     * 
     * 
     * @return the options recognized by this processor or an
     *         empty collection if none
     * @see javax.annotation.processing.SupportedOptions
     */
    Set<String> getSupportedOptions();

    /**
     * Returns the names of the annotation types supported by this
     * processor.  An element of the result may be the canonical
     * (fully qualified) name of a supported annotation type.
     * Alternately it may be of the form &quot;<tt><i>name</i>.*</tt>&quot;
     * representing the set of all annotation types with canonical
     * names beginning with &quot;<tt><i>name.</i></tt>&quot;.  Finally, {@code
     * "*"} by itself represents the set of all annotation types,
     * including the empty set.  Note that a processor should not
     * claim {@code "*"} unless it is actually processing all files;
     * claiming unnecessary annotations may cause a performance
     * slowdown in some environments.
     *
     * <p>Each string returned in the set must be accepted by the
     * following grammar:
     *
     * <blockquote>
     * <dl>
     * <dt><i>SupportedAnnotationTypeString:</i>
     * <dd><i>TypeName</i> <i>DotStar</i><sub><i>opt</i></sub>
     * <dd><tt>*</tt>
     *
     * <dt><i>DotStar:</i>
     * <dd><tt>.</tt> <tt>*</tt>
     * </dl>
     * </blockquote>
     *
     * where <i>TypeName</i> is as defined in
     * <cite>The Java&trade; Language Specification</cite>.
     *
     * <p>
     * 返回此处理器支持的注释类型的名称。结果的元素可以是支持的注释类型的规范(完全限定)名称。或者,它可以是"<tt> <i> name </i>。"</tt>"的形式。
     * 表示具有以"<tt> <i> name。</i> </tt>"开头的规范名称的所有注释类型的集合。最后,{@code"*"}本身表示所有注释类型的集合,包括空集。
     * 注意,处理器不应声明{@code"*"},除非它实际上处理所有文件;声明不必要的注释可能会导致某些环境的性能下降。
     * 
     *  <p>集合中返回的每个字符串必须被以下语法接受：
     * 
     * <blockquote>
     * <dl>
     *  <dt> <i> SupportedAnnotationTypeString：</i> <dd> <i> TypeName </i> <i> DotStar </i> <sub> <i> opt </i>
     *  </tt> * </tt>。
     * 
     *  <dt> <i> DotStar：</i> <dd> <tt>。</tt> <tt> * </tt>
     * </dl>
     * </blockquote>
     * 
     *  其中<i> TypeName </i>是在<cite> Java&trade;语言规范</cite>。
     * 
     * 
     * @return the names of the annotation types supported by this processor
     * @see javax.annotation.processing.SupportedAnnotationTypes
     * @jls 3.8 Identifiers
     * @jls 6.5.5 Meaning of Type Names
     */
    Set<String> getSupportedAnnotationTypes();

    /**
     * Returns the latest source version supported by this annotation
     * processor.
     *
     * <p>
     *  返回此注释处理器支持的最新源版本。
     * 
     * 
     * @return the latest source version supported by this annotation
     * processor.
     * @see javax.annotation.processing.SupportedSourceVersion
     * @see ProcessingEnvironment#getSourceVersion
     */
    SourceVersion getSupportedSourceVersion();

    /**
     * Initializes the processor with the processing environment.
     *
     * <p>
     *  使用处理环境初始化处理器。
     * 
     * 
     * @param processingEnv environment for facilities the tool framework
     * provides to the processor
     */
    void init(ProcessingEnvironment processingEnv);

    /**
     * Processes a set of annotation types on type elements
     * originating from the prior round and returns whether or not
     * these annotation types are claimed by this processor.  If {@code
     * true} is returned, the annotation types are claimed and subsequent
     * processors will not be asked to process them; if {@code false}
     * is returned, the annotation types are unclaimed and subsequent
     * processors may be asked to process them.  A processor may
     * always return the same boolean value or may vary the result
     * based on chosen criteria.
     *
     * <p>The input set will be empty if the processor supports {@code
     * "*"} and the root elements have no annotations.  A {@code
     * Processor} must gracefully handle an empty set of annotations.
     *
     * <p>
     * 在源自先前轮次的类型元素上处理一组注释类型,并返回此处理器是否声明了这些注释类型。
     * 如果返回{@code true},则会声明注释类型,并且不会要求后续处理器处理它们;如果返回{@code false},那么注释类型是未声明的,并且可能要求后续处理器处理它们。
     * 处理器可以总是返回相同的布尔值或者可以基于所选择的标准改变结果。
     * 
     *  <p>如果处理器支持{@code"*"}且根元素没有注释,输入集将为空。 {@code Processor}必须优雅地处理一组空注释。
     * 
     * 
     * @param annotations the annotation types requested to be processed
     * @param roundEnv  environment for information about the current and prior round
     * @return whether or not the set of annotation types are claimed by this processor
     */
    boolean process(Set<? extends TypeElement> annotations,
                    RoundEnvironment roundEnv);

   /**
    * Returns to the tool infrastructure an iterable of suggested
    * completions to an annotation.  Since completions are being asked
    * for, the information provided about the annotation may be
    * incomplete, as if for a source code fragment. A processor may
    * return an empty iterable.  Annotation processors should focus
    * their efforts on providing completions for annotation members
    * with additional validity constraints known to the processor, for
    * example an {@code int} member whose value should lie between 1
    * and 10 or a string member that should be recognized by a known
    * grammar, such as a regular expression or a URL.
    *
    * <p>Since incomplete programs are being modeled, some of the
    * parameters may only have partial information or may be {@code
    * null}.  At least one of {@code element} and {@code userText}
    * must be non-{@code null}.  If {@code element} is non-{@code
    * null}, {@code annotation} and {@code member} may be {@code
    * null}.  Processors may not throw a {@code NullPointerException}
    * if some parameters are {@code null}; if a processor has no
    * completions to offer based on the provided information, an
    * empty iterable can be returned.  The processor may also return
    * a single completion with an empty value string and a message
    * describing why there are no completions.
    *
    * <p>Completions are informative and may reflect additional
    * validity checks performed by annotation processors.  For
    * example, consider the simple annotation:
    *
    * <blockquote>
    * <pre>
    * &#064;MersennePrime {
    *    int value();
    * }
    * </pre>
    * </blockquote>
    *
    * (A Mersenne prime is prime number of the form
    * 2<sup><i>n</i></sup> - 1.) Given an {@code AnnotationMirror}
    * for this annotation type, a list of all such primes in the
    * {@code int} range could be returned without examining any other
    * arguments to {@code getCompletions}:
    *
    * <blockquote>
    * <pre>
    * import static javax.annotation.processing.Completions.*;
    * ...
    * return Arrays.asList({@link Completions#of(String) of}(&quot;3&quot;),
    *                      of(&quot;7&quot;),
    *                      of(&quot;31&quot;),
    *                      of(&quot;127&quot;),
    *                      of(&quot;8191&quot;),
    *                      of(&quot;131071&quot;),
    *                      of(&quot;524287&quot;),
    *                      of(&quot;2147483647&quot;));
    * </pre>
    * </blockquote>
    *
    * A more informative set of completions would include the number
    * of each prime:
    *
    * <blockquote>
    * <pre>
    * return Arrays.asList({@link Completions#of(String, String) of}(&quot;3&quot;,          &quot;M2&quot;),
    *                      of(&quot;7&quot;,          &quot;M3&quot;),
    *                      of(&quot;31&quot;,         &quot;M5&quot;),
    *                      of(&quot;127&quot;,        &quot;M7&quot;),
    *                      of(&quot;8191&quot;,       &quot;M13&quot;),
    *                      of(&quot;131071&quot;,     &quot;M17&quot;),
    *                      of(&quot;524287&quot;,     &quot;M19&quot;),
    *                      of(&quot;2147483647&quot;, &quot;M31&quot;));
    * </pre>
    * </blockquote>
    *
    * However, if the {@code userText} is available, it can be checked
    * to see if only a subset of the Mersenne primes are valid.  For
    * example, if the user has typed
    *
    * <blockquote>
    * <code>
    * &#064;MersennePrime(1
    * </code>
    * </blockquote>
    *
    * the value of {@code userText} will be {@code "1"}; and only
    * two of the primes are possible completions:
    *
    * <blockquote>
    * <pre>
    * return Arrays.asList(of(&quot;127&quot;,        &quot;M7&quot;),
    *                      of(&quot;131071&quot;,     &quot;M17&quot;));
    * </pre>
    * </blockquote>
    *
    * Sometimes no valid completion is possible.  For example, there
    * is no in-range Mersenne prime starting with 9:
    *
    * <blockquote>
    * <code>
    * &#064;MersennePrime(9
    * </code>
    * </blockquote>
    *
    * An appropriate response in this case is to either return an
    * empty list of completions,
    *
    * <blockquote>
    * <pre>
    * return Collections.emptyList();
    * </pre>
    * </blockquote>
    *
    * or a single empty completion with a helpful message
    *
    * <blockquote>
    * <pre>
    * return Arrays.asList(of(&quot;&quot;, &quot;No in-range Mersenne primes start with 9&quot;));
    * </pre>
    * </blockquote>
    *
    * <p>
    *  返回到工具基础结构中的一个可迭代的建议完成注释。由于要求完成,所以关于注释提供的信息可能是不完整的,就像对于源代码片段。处理器可以返回空的可迭代。
    * 注释处理器应当集中精力为注释成员提供具有处理器已知的附加有效性约束的完成,例如值应在1和10之间的{@code int}成员或应该由已知语法识别的字符串成员,例如正则表达式或URL。
    * 
    * <p>由于不完整的程序正在建模,一些参数可能只有部分信息,或者可能是{@code null}。
    *  {@code element}和{@code userText}中的至少一个必须为非 -  {@ code null}。
    * 如果{@code element}不是{@ code null},{@code annotation}和{@code member}可能是{@code null}。
    * 如果一些参数是{@code null},则处理器不能抛出{@code NullPointerException};如果处理器没有基于所提供的信息提供的完成,则可以返回空的可迭代。
    * 处理器还可以返回具有空值串和描述为什么没有完成的消息的单个完成。
    * 
    *  <p>完成是信息性的,可能反映注释处理器执行的其他有效性检查。例如,考虑简单注释：
    * 
    * <blockquote>
    * <pre>
    *  @MersennePrime {int value(); }}
    * </pre>
    * </blockquote>
    * 
    *  (A Mersenne素数是形式2的质数<sup> <i> </i> </sup>  -  1)。
    * 给定此注释类型的{@code AnnotationMirror},所有可以返回{@code int}范围,而不检查{@code getCompletions}的任何其他参数：。
    * 
    * <blockquote>
    * <pre>
    *  import static javax.annotation.processing.Completions。
    * *; ... return("127")的(...)的Arrays.asList({@ link Completions#of(String)of}("3" ("2147483647")的("8191"
    * ),("131071"),("524287"。
    *  import static javax.annotation.processing.Completions。
    * </pre>
    * </blockquote>
    * 
    *  更丰富的完成集将包括每个素数的数量：
    * 
    * <blockquote>
    * <pre>
    * 返回("31","31")的("7","M3")的返回Arrays.asList({@ link Completions#of(String,String)of}("3","M2" ("524197")
    * 的("131071","M17")的("127","M7"),("8191","M13" ;"M19"),("2147483647","M31"));。
    * </pre>
    * </blockquote>
    * 
    *  但是,如果{@code userText}可用,可以检查以查看是否只有一个子集的Mersenne素数是有效的。例如,如果用户已键入
    * 
    * @param element the element being annotated
    * @param annotation the (perhaps partial) annotation being
    *                   applied to the element
    * @param member the annotation member to return possible completions for
    * @param userText source code text to be completed
    *
    * @return suggested completions to the annotation
    */
    Iterable<? extends Completion> getCompletions(Element element,
                                                  AnnotationMirror annotation,
                                                  ExecutableElement member,
                                                  String userText);
}
