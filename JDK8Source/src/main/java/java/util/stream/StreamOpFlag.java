/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2012, 2013, Oracle and/or its affiliates. All rights reserved.
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
package java.util.stream;

import java.util.EnumMap;
import java.util.Map;
import java.util.Spliterator;

/**
 * Flags corresponding to characteristics of streams and operations. Flags are
 * utilized by the stream framework to control, specialize or optimize
 * computation.
 *
 * <p>
 * Stream flags may be used to describe characteristics of several different
 * entities associated with streams: stream sources, intermediate operations,
 * and terminal operations.  Not all stream flags are meaningful for all
 * entities; the following table summarizes which flags are meaningful in what
 * contexts:
 *
 * <div>
 * <table>
 *   <caption>Type Characteristics</caption>
 *   <thead class="tableSubHeadingColor">
 *     <tr>
 *       <th colspan="2">&nbsp;</th>
 *       <th>{@code DISTINCT}</th>
 *       <th>{@code SORTED}</th>
 *       <th>{@code ORDERED}</th>
 *       <th>{@code SIZED}</th>
 *       <th>{@code SHORT_CIRCUIT}</th>
 *     </tr>
 *   </thead>
 *   <tbody>
 *      <tr>
 *        <th colspan="2" class="tableSubHeadingColor">Stream source</th>
 *        <td>Y</td>
 *        <td>Y</td>
 *        <td>Y</td>
 *        <td>Y</td>
 *        <td>N</td>
 *      </tr>
 *      <tr>
 *        <th colspan="2" class="tableSubHeadingColor">Intermediate operation</th>
 *        <td>PCI</td>
 *        <td>PCI</td>
 *        <td>PCI</td>
 *        <td>PC</td>
 *        <td>PI</td>
 *      </tr>
 *      <tr>
 *        <th colspan="2" class="tableSubHeadingColor">Terminal operation</th>
 *        <td>N</td>
 *        <td>N</td>
 *        <td>PC</td>
 *        <td>N</td>
 *        <td>PI</td>
 *      </tr>
 *   </tbody>
 *   <tfoot>
 *       <tr>
 *         <th class="tableSubHeadingColor" colspan="2">Legend</th>
 *         <th colspan="6" rowspan="7">&nbsp;</th>
 *       </tr>
 *       <tr>
 *         <th class="tableSubHeadingColor">Flag</th>
 *         <th class="tableSubHeadingColor">Meaning</th>
 *         <th colspan="6"></th>
 *       </tr>
 *       <tr><td>Y</td><td>Allowed</td></tr>
 *       <tr><td>N</td><td>Invalid</td></tr>
 *       <tr><td>P</td><td>Preserves</td></tr>
 *       <tr><td>C</td><td>Clears</td></tr>
 *       <tr><td>I</td><td>Injects</td></tr>
 *   </tfoot>
 * </table>
 * </div>
 *
 * <p>In the above table, "PCI" means "may preserve, clear, or inject"; "PC"
 * means "may preserve or clear", "PI" means "may preserve or inject", and "N"
 * means "not valid".
 *
 * <p>Stream flags are represented by unioned bit sets, so that a single word
 * may describe all the characteristics of a given stream entity, and that, for
 * example, the flags for a stream source can be efficiently combined with the
 * flags for later operations on that stream.
 *
 * <p>The bit masks {@link #STREAM_MASK}, {@link #OP_MASK}, and
 * {@link #TERMINAL_OP_MASK} can be ANDed with a bit set of stream flags to
 * produce a mask containing only the valid flags for that entity type.
 *
 * <p>When describing a stream source, one only need describe what
 * characteristics that stream has; when describing a stream operation, one need
 * describe whether the operation preserves, injects, or clears that
 * characteristic.  Accordingly, two bits are used for each flag, so as to allow
 * representing not only the presence of of a characteristic, but how an
 * operation modifies that characteristic.  There are two common forms in which
 * flag bits are combined into an {@code int} bit set.  <em>Stream flags</em>
 * are a unioned bit set constructed by ORing the enum characteristic values of
 * {@link #set()} (or, more commonly, ORing the corresponding static named
 * constants prefixed with {@code IS_}).  <em>Operation flags</em> are a unioned
 * bit set constructed by ORing the enum characteristic values of {@link #set()}
 * or {@link #clear()} (to inject, or clear, respectively, the corresponding
 * flag), or more commonly ORing the corresponding named constants prefixed with
 * {@code IS_} or {@code NOT_}.  Flags that are not marked with {@code IS_} or
 * {@code NOT_} are implicitly treated as preserved.  Care must be taken when
 * combining bitsets that the correct combining operations are applied in the
 * correct order.
 *
 * <p>
 * With the exception of {@link #SHORT_CIRCUIT}, stream characteristics can be
 * derived from the equivalent {@link java.util.Spliterator} characteristics:
 * {@link java.util.Spliterator#DISTINCT}, {@link java.util.Spliterator#SORTED},
 * {@link java.util.Spliterator#ORDERED}, and
 * {@link java.util.Spliterator#SIZED}.  A spliterator characteristics bit set
 * can be converted to stream flags using the method
 * {@link #fromCharacteristics(java.util.Spliterator)} and converted back using
 * {@link #toCharacteristics(int)}.  (The bit set
 * {@link #SPLITERATOR_CHARACTERISTICS_MASK} is used to AND with a bit set to
 * produce a valid spliterator characteristics bit set that can be converted to
 * stream flags.)
 *
 * <p>
 * The source of a stream encapsulates a spliterator. The characteristics of
 * that source spliterator when transformed to stream flags will be a proper
 * subset of stream flags of that stream.
 * For example:
 * <pre> {@code
 *     Spliterator s = ...;
 *     Stream stream = Streams.stream(s);
 *     flagsFromSplitr = fromCharacteristics(s.characteristics());
 *     assert(flagsFromSplitr & stream.getStreamFlags() == flagsFromSplitr);
 * }</pre>
 *
 * <p>
 * An intermediate operation, performed on an input stream to create a new
 * output stream, may preserve, clear or inject stream or operation
 * characteristics.  Similarly, a terminal operation, performed on an input
 * stream to produce an output result may preserve, clear or inject stream or
 * operation characteristics.  Preservation means that if that characteristic
 * is present on the input, then it is also present on the output.  Clearing
 * means that the characteristic is not present on the output regardless of the
 * input.  Injection means that the characteristic is present on the output
 * regardless of the input.  If a characteristic is not cleared or injected then
 * it is implicitly preserved.
 *
 * <p>
 * A pipeline consists of a stream source encapsulating a spliterator, one or
 * more intermediate operations, and finally a terminal operation that produces
 * a result.  At each stage of the pipeline, a combined stream and operation
 * flags can be calculated, using {@link #combineOpFlags(int, int)}.  Such flags
 * ensure that preservation, clearing and injecting information is retained at
 * each stage.
 *
 * The combined stream and operation flags for the source stage of the pipeline
 * is calculated as follows:
 * <pre> {@code
 *     int flagsForSourceStage = combineOpFlags(sourceFlags, INITIAL_OPS_VALUE);
 * }</pre>
 *
 * The combined stream and operation flags of each subsequent intermediate
 * operation stage in the pipeline is calculated as follows:
 * <pre> {@code
 *     int flagsForThisStage = combineOpFlags(flagsForPreviousStage, thisOpFlags);
 * }</pre>
 *
 * Finally the flags output from the last intermediate operation of the pipeline
 * are combined with the operation flags of the terminal operation to produce
 * the flags output from the pipeline.
 *
 * <p>Those flags can then be used to apply optimizations. For example, if
 * {@code SIZED.isKnown(flags)} returns true then the stream size remains
 * constant throughout the pipeline, this information can be utilized to
 * pre-allocate data structures and combined with
 * {@link java.util.Spliterator#SUBSIZED} that information can be utilized to
 * perform concurrent in-place updates into a shared array.
 *
 * For specific details see the {@link AbstractPipeline} constructors.
 *
 * <p>
 *  对应于流和操作的特征的标志。流框架利用标志来控制,专门化或优化计算。
 * 
 * <p>
 *  流标记可以用于描述与流相关联的几个不同实体的特性：流源,中间操作和终端操作。并非所有流标志对所有实体都有意义;下表总结了哪些标志在上下文中有意义：
 * 
 * <div>
 * <table>
 *  <caption>类型特性</caption>
 * <thead class="tableSubHeadingColor">
 * <tr>
 *  <th colspan ="2">&nbsp; </th> <th> {@ code DISTINCT} </th> <th> {@ code SORTED} </th> <th> {@ code ORDERED}
 *  <th> {@ code SIZED} </th> <th> {@ code SHORT_CIRCUIT} </th>。
 * </tr>
 * </thead>
 * <tbody>
 * <tr>
 *  <th colspan ="2"class ="tableSubHeadingColor">流来源</th> <td> Y </td> <td> Y </td> <td> Y </td> <td> Y
 *  </td > <td> N </td>。
 * </tr>
 * <tr>
 *  <th colspan ="2"class ="tableSubHeadingColor">中级操作</th> <td> PCI </td> <td> PCI </td> <td> PCI </td>
 *  <td> PC </td > <td> PI </td>。
 * </tr>
 * <tr>
 *  <th colspan ="2"class ="tableSubHeadingColor">终端操作</th> <td> N </td> <td> N </td> <td> PC </td> <td>
 *  N </td > <td> PI </td>。
 * </tr>
 * </tbody>
 * <tfoot>
 * <tr>
 *  <th class ="tableSubHeadingColor"colspan ="2">图例</th> <th colspan ="6"rowspan ="7">&nbsp; </th>
 * </tr>
 * <tr>
 *  <th class ="tableSubHeadingColor"> Flag </th> <th class ="tableSubHeadingColor">含义</th> <th colspan ="6">
 *  </th>。
 * </tr>
 * <tr> <td> Y </td> <td>允许</td> </tr> <tr> <td> N </td> <td>无效</td> td> P </td> <td>保存</td> </tr> <tr> 
 * <td> C </td> <td>清除</td> / td> <td>注入</td> </tr>。
 * </tfoot>
 * </table>
 * </div>
 * 
 *  <p>在上表中,"PCI"表示"可以保留,清除或注入"; "PC"表示"可以保留或清除","PI"表示"可以保留或注入","N"表示"无效"。
 * 
 *  流标志由单位字节集表示,使得单个字可以描述给定流实体的所有特征,并且例如,流源的标志可以有效地与用于以后操作的标志组合在那个流。
 * 
 *  <p>位掩码{@link #STREAM_MASK},{@link #OP_MASK}和{@link #TERMINAL_OP_MASK}可以与一组位流标记进行AND运算,以产生仅包含该实体类型的有效
 * 标记的掩码。
 * 
 * <p>当描述流源时,只需要描述流具有什么特征;当描述流操作时,需要描述操作是保留,注入还是清除该特性。因此,对于每个标志使用两个比特,以便不仅允许表示特性的存在,而且操作如何修改该特性。
 * 有两种常见形式,其中标志位组合成{@code int}位集合。
 *  </em>是通过对{@link #set()}的枚举特征值进行OR运算(或者更常见地,将以{@code IS_}为前缀的对应静态命名常数进行OR运算)构造的联合比特集合。 。
 *  <em>操作标志</em>是通过对{@link #set()}或{@link #clear()}的枚举特征值进行OR运算(分别注入或清除相应的标志),或者更常用ORing以{@code IS_}或{@code NOT_}
 * 为前缀的相应命名常量。
 *  </em>是通过对{@link #set()}的枚举特征值进行OR运算(或者更常见地,将以{@code IS_}为前缀的对应静态命名常数进行OR运算)构造的联合比特集合。 。
 * 未标记为{@code IS_}或{@code NOT_}的标记将被隐式视为已保留。在组合比特组时必须小心,以正确的顺序应用正确的组合操作。
 * 
 * <p>
 * 除了{@link #SHORT_CIRCUIT}之外,流特性可以从等效的{@link java.util.Spliterator}特性派生：{@link java.util.Spliterator#DISTINCT}
 * ,{@link java.util.Spliterator #SORTED},{@link java.util.Spliterator#ORDERED}和{@link java.util.Spliterator#SIZED}
 * 。
 * 可以使用{@link #fromCharacteristics(java.util.Spliterator)}方法将分隔符特征位集转换为流标记,并使用{@link #toCharacteristics(int)}
 * 转换回。
 *  (位集{@link #SPLITERATOR_CHARACTERISTICS_MASK}用于AND位,设置位以产生可转换为流标志的有效Spliterator特性位集。)。
 * 
 * <p>
 *  流的源封装了分裂器。当转换为流标志时,该源分枝器的特性将是该流的流标志的正确子集。
 * 例如：<pre> {@code Spliterator s = ...; Stream stream = Streams.stream(s); flagsFromSplitr = fromCharacteristics(s.characteristics()); assert(flagsFromSplitr&stream.getStreamFlags()== flagsFromSplitr); }
 *  </pre>。
 *  流的源封装了分裂器。当转换为流标志时,该源分枝器的特性将是该流的流标志的正确子集。
 * 
 * <p>
 * 在输入流上执行以创建新的输出流的中间操作可以保留,清除或注入流或操作特性。类似地,对输入流执行以产生输出结果的终端操作可以保存,清除或注入流或操作特性。
 * 保存意味着如果该特性存在于输入上,则它也存在于输出上。清零意味着输出上不存在特性,而与输入无关。注入意味着该特性存在于输出上,而与输入无关。如果特征未被清除或注入,则隐含地保留该特征。
 * 
 * <p>
 *  流水线包括包含分割器的流源,一个或多个中间操作,以及最终产生结果的终端操作。在流水线的每个阶段,可以使用{@link #combineOpFlags(int,int)}计算组合流和操作标志。
 * 这些标志确保在每个阶段保留,清除和注入信息。
 * 
 *  流水线源阶段的组合流和操作标志计算如下：<pre> {@code int flagsForSourceStage = combineOpFlags(sourceFlags,INITIAL_OPS_VALUE); }
 *  </pre>。
 * 
 * 流水线中每个后续中间操作阶段的组合流和操作标志计算如下：<pre> {@code int flagsForThisStage = combineOpFlags(flagsForPreviousStage,thisOpFlags); }
 *  </pre>。
 * 
 *  最后,从流水线的最后中间操作输出的标志与终端操作的操作标志组合以产生从流水线输出的标志。
 * 
 *  <p>那些标志可以用于应用优化。
 * 例如,如果{@code SIZED.isKnown(flags)}返回true,那么流大小在整个流水线中保持不变,该信息可以用于预分配数据结构,并与{@link java.util.Spliterator#SUBSIZED }
 * 该信息可以用于在共享阵列中执行并发的就地更新。
 *  <p>那些标志可以用于应用优化。
 * 
 *  有关具体细节,请参阅{@link AbstractPipeline}构造函数。
 * 
 * 
 * @since 1.8
 */
enum StreamOpFlag {

    /*
     * Each characteristic takes up 2 bits in a bit set to accommodate
     * preserving, clearing and setting/injecting information.
     *
     * This applies to stream flags, intermediate/terminal operation flags, and
     * combined stream and operation flags. Even though the former only requires
     * 1 bit of information per characteristic, is it more efficient when
     * combining flags to align set and inject bits.
     *
     * Characteristics belong to certain types, see the Type enum. Bit masks for
     * the types are constructed as per the following table:
     *
     *                        DISTINCT  SORTED  ORDERED  SIZED  SHORT_CIRCUIT
     *          SPLITERATOR      01       01       01      01        00
     *               STREAM      01       01       01      01        00
     *                   OP      11       11       11      10        01
     *          TERMINAL_OP      00       00       10      00        01
     * UPSTREAM_TERMINAL_OP      00       00       10      00        00
     *
     * 01 = set/inject
     * 10 = clear
     * 11 = preserve
     *
     * Construction of the columns is performed using a simple builder for
     * non-zero values.
     * <p>
     *  每个特性在位集中占用2位,以适应保存,清除和设置/注入信息。
     * 
     *  这适用于流标志,中间/终端操作标志以及组合的流和操作标志。即使前者仅每个特性需要1位信息,但是当组合标志以对准置位和注入位时更有效。
     * 
     *  特性属于某些类型,请参见类型枚举。类型的位掩码按照下表构建：
     * 
     * DISTINCT SORTED ORDERED SIZED SHORT_CIRCUIT SPLITERATOR 01 01 01 01 00 STREAM 01 01 01 01 00 OP 11 11
     *  11 10 01 TERMINAL_OP 00 00 10 00 01 UPSTREAM_TERMINAL_OP 00 00 10 00 00。
     * 
     *  01 =设置/注入10 =清除11 =保留
     * 
     *  使用非零值的简单构建器来执行列的构造。
     * 
     */


    // The following flags correspond to characteristics on Spliterator
    // and the values MUST be equal.
    //

    /**
     * Characteristic value signifying that, for each pair of
     * encountered elements in a stream {@code x, y}, {@code !x.equals(y)}.
     * <p>
     * A stream may have this value or an intermediate operation can preserve,
     * clear or inject this value.
     * <p>
     *  特征值表示对于流{@code x,y}中的每一对遇到的元素{@code！x.equals(y)}。
     * <p>
     *  流可以具有该值或者中间操作可以保留,清除或注入该值。
     * 
     */
    // 0, 0x00000001
    // Matches Spliterator.DISTINCT
    DISTINCT(0,
             set(Type.SPLITERATOR).set(Type.STREAM).setAndClear(Type.OP)),

    /**
     * Characteristic value signifying that encounter order follows a natural
     * sort order of comparable elements.
     * <p>
     * A stream can have this value or an intermediate operation can preserve,
     * clear or inject this value.
     * <p>
     * Note: The {@link java.util.Spliterator#SORTED} characteristic can define
     * a sort order with an associated non-null comparator.  Augmenting flag
     * state with addition properties such that those properties can be passed
     * to operations requires some disruptive changes for a singular use-case.
     * Furthermore, comparing comparators for equality beyond that of identity
     * is likely to be unreliable.  Therefore the {@code SORTED} characteristic
     * for a defined non-natural sort order is not mapped internally to the
     * {@code SORTED} flag.
     * <p>
     *  特征值表示遭遇顺序遵循可比较元素的自然排序顺序。
     * <p>
     *  流可以具有该值,或者中间操作可以保留,清除或注入该值。
     * <p>
     *  注意：{@link java.util.Spliterator#SORTED}特性可以使用关联的非空比较器定义排序顺序。
     * 使用添加属性来增强标志状态,以便将这些属性传递到操作,需要对单个用例进行一些破坏性更改。此外,比较等同于超过身份的比较器可能是不可靠的。
     * 因此,定义的非自然排序顺序的{@code SORTED}特性不会在内部映射到{@code SORTED}标志。
     * 
     */
    // 1, 0x00000004
    // Matches Spliterator.SORTED
    SORTED(1,
           set(Type.SPLITERATOR).set(Type.STREAM).setAndClear(Type.OP)),

    /**
     * Characteristic value signifying that an encounter order is
     * defined for stream elements.
     * <p>
     * A stream can have this value, an intermediate operation can preserve,
     * clear or inject this value, or a terminal operation can preserve or clear
     * this value.
     * <p>
     *  特征值表示为流元素定义了遇到顺序。
     * <p>
     * 流可以具有该值,中间操作可以保留,清除或注入该值,或者终端操作可以保留或清除该值。
     * 
     */
    // 2, 0x00000010
    // Matches Spliterator.ORDERED
    ORDERED(2,
            set(Type.SPLITERATOR).set(Type.STREAM).setAndClear(Type.OP).clear(Type.TERMINAL_OP)
                    .clear(Type.UPSTREAM_TERMINAL_OP)),

    /**
     * Characteristic value signifying that size of the stream
     * is of a known finite size that is equal to the known finite
     * size of the source spliterator input to the first stream
     * in the pipeline.
     * <p>
     * A stream can have this value or an intermediate operation can preserve or
     * clear this value.
     * <p>
     *  特征值表示流的大小是已知的有限大小,其等于输入到流水线中的第一流的源分裂器的已知有限大小。
     * <p>
     *  流可以具有该值,或者中间操作可以保留或清除该值。
     * 
     */
    // 3, 0x00000040
    // Matches Spliterator.SIZED
    SIZED(3,
          set(Type.SPLITERATOR).set(Type.STREAM).clear(Type.OP)),

    // The following Spliterator characteristics are not currently used but a
    // gap in the bit set is deliberately retained to enable corresponding
    // stream flags if//when required without modification to other flag values.
    //
    // 4, 0x00000100 NONNULL(4, ...
    // 5, 0x00000400 IMMUTABLE(5, ...
    // 6, 0x00001000 CONCURRENT(6, ...
    // 7, 0x00004000 SUBSIZED(7, ...

    // The following 4 flags are currently undefined and a free for any further
    // spliterator characteristics.
    //
    //  8, 0x00010000
    //  9, 0x00040000
    // 10, 0x00100000
    // 11, 0x00400000

    // The following flags are specific to streams and operations
    //

    /**
     * Characteristic value signifying that an operation may short-circuit the
     * stream.
     * <p>
     * An intermediate operation can preserve or inject this value,
     * or a terminal operation can preserve or inject this value.
     * <p>
     *  特征值表示操作可能使流短路。
     * <p>
     *  中间操作可以保留或注入此值,或者终端操作可以保留或注入此值。
     * 
     */
    // 12, 0x01000000
    SHORT_CIRCUIT(12,
                  set(Type.OP).set(Type.TERMINAL_OP));

    // The following 2 flags are currently undefined and a free for any further
    // stream flags if/when required
    //
    // 13, 0x04000000
    // 14, 0x10000000
    // 15, 0x40000000

    /**
     * Type of a flag
     * <p>
     *  标志的类型
     * 
     */
    enum Type {
        /**
         * The flag is associated with spliterator characteristics.
         * <p>
         *  该标志与分隔符特征相关联。
         * 
         */
        SPLITERATOR,

        /**
         * The flag is associated with stream flags.
         * <p>
         *  该标志与流标志相关联。
         * 
         */
        STREAM,

        /**
         * The flag is associated with intermediate operation flags.
         * <p>
         *  该标志与中间操作标志相关联。
         * 
         */
        OP,

        /**
         * The flag is associated with terminal operation flags.
         * <p>
         *  该标志与终端操作标志相关联。
         * 
         */
        TERMINAL_OP,

        /**
         * The flag is associated with terminal operation flags that are
         * propagated upstream across the last stateful operation boundary
         * <p>
         *  该标志与在上一个有状态操作边界上传播的终端操作标志相关联
         * 
         */
        UPSTREAM_TERMINAL_OP
    }

    /**
     * The bit pattern for setting/injecting a flag.
     * <p>
     *  用于设置/注入标志的位模式。
     * 
     */
    private static final int SET_BITS = 0b01;

    /**
     * The bit pattern for clearing a flag.
     * <p>
     *  用于清除标志的位模式。
     * 
     */
    private static final int CLEAR_BITS = 0b10;

    /**
     * The bit pattern for preserving a flag.
     * <p>
     *  用于保留标志的位模式。
     * 
     */
    private static final int PRESERVE_BITS = 0b11;

    private static MaskBuilder set(Type t) {
        return new MaskBuilder(new EnumMap<>(Type.class)).set(t);
    }

    private static class MaskBuilder {
        final Map<Type, Integer> map;

        MaskBuilder(Map<Type, Integer> map) {
            this.map = map;
        }

        MaskBuilder mask(Type t, Integer i) {
            map.put(t, i);
            return this;
        }

        MaskBuilder set(Type t) {
            return mask(t, SET_BITS);
        }

        MaskBuilder clear(Type t) {
            return mask(t, CLEAR_BITS);
        }

        MaskBuilder setAndClear(Type t) {
            return mask(t, PRESERVE_BITS);
        }

        Map<Type, Integer> build() {
            for (Type t : Type.values()) {
                map.putIfAbsent(t, 0b00);
            }
            return map;
        }
    }

    /**
     * The mask table for a flag, this is used to determine if a flag
     * corresponds to a certain flag type and for creating mask constants.
     * <p>
     *  用于标志的掩码表,这用于确定标志是否对应于某个标志类型和用于创建掩码常量。
     * 
     */
    private final Map<Type, Integer> maskTable;

    /**
     * The bit position in the bit mask.
     * <p>
     *  位掩码中的位位置。
     * 
     */
    private final int bitPosition;

    /**
     * The set 2 bit set offset at the bit position.
     * <p>
     *  设置2位在位位置偏移。
     * 
     */
    private final int set;

    /**
     * The clear 2 bit set offset at the bit position.
     * <p>
     *  清除2位设置偏移在位位置。
     * 
     */
    private final int clear;

    /**
     * The preserve 2 bit set offset at the bit position.
     * <p>
     *  保留2位设置偏移在位位置。
     * 
     */
    private final int preserve;

    private StreamOpFlag(int position, MaskBuilder maskBuilder) {
        this.maskTable = maskBuilder.build();
        // Two bits per flag
        position *= 2;
        this.bitPosition = position;
        this.set = SET_BITS << position;
        this.clear = CLEAR_BITS << position;
        this.preserve = PRESERVE_BITS << position;
    }

    /**
     * Gets the bitmap associated with setting this characteristic.
     *
     * <p>
     * 获取与设置此特性关联的位图。
     * 
     * 
     * @return the bitmap for setting this characteristic
     */
    int set() {
        return set;
    }

    /**
     * Gets the bitmap associated with clearing this characteristic.
     *
     * <p>
     *  获取与清除此特性相关联的位图。
     * 
     * 
     * @return the bitmap for clearing this characteristic
     */
    int clear() {
        return clear;
    }

    /**
     * Determines if this flag is a stream-based flag.
     *
     * <p>
     *  确定此标志是否是基于流的标志。
     * 
     * 
     * @return true if a stream-based flag, otherwise false.
     */
    boolean isStreamFlag() {
        return maskTable.get(Type.STREAM) > 0;
    }

    /**
     * Checks if this flag is set on stream flags, injected on operation flags,
     * and injected on combined stream and operation flags.
     *
     * <p>
     *  检查此标志是否设置为流标志,注入操作标志,并注入组合流和操作标志。
     * 
     * 
     * @param flags the stream flags, operation flags, or combined stream and
     *        operation flags
     * @return true if this flag is known, otherwise false.
     */
    boolean isKnown(int flags) {
        return (flags & preserve) == set;
    }

    /**
     * Checks if this flag is cleared on operation flags or combined stream and
     * operation flags.
     *
     * <p>
     *  检查此标志是否在操作标志或组合流和操作标志上清除。
     * 
     * 
     * @param flags the operation flags or combined stream and operations flags.
     * @return true if this flag is preserved, otherwise false.
     */
    boolean isCleared(int flags) {
        return (flags & preserve) == clear;
    }

    /**
     * Checks if this flag is preserved on combined stream and operation flags.
     *
     * <p>
     *  检查此标志是否保留在组合流和操作标志上。
     * 
     * 
     * @param flags the combined stream and operations flags.
     * @return true if this flag is preserved, otherwise false.
     */
    boolean isPreserved(int flags) {
        return (flags & preserve) == preserve;
    }

    /**
     * Determines if this flag can be set for a flag type.
     *
     * <p>
     *  确定是否可以为标志类型设置此标志。
     * 
     * 
     * @param t the flag type.
     * @return true if this flag can be set for the flag type, otherwise false.
     */
    boolean canSet(Type t) {
        return (maskTable.get(t) & SET_BITS) > 0;
    }

    /**
     * The bit mask for spliterator characteristics
     * <p>
     *  Spliterator特性的位掩码
     * 
     */
    static final int SPLITERATOR_CHARACTERISTICS_MASK = createMask(Type.SPLITERATOR);

    /**
     * The bit mask for source stream flags.
     * <p>
     *  源流标记的位掩码。
     * 
     */
    static final int STREAM_MASK = createMask(Type.STREAM);

    /**
     * The bit mask for intermediate operation flags.
     * <p>
     *  中间操作标志的位掩码。
     * 
     */
    static final int OP_MASK = createMask(Type.OP);

    /**
     * The bit mask for terminal operation flags.
     * <p>
     *  终端操作标志的位掩码。
     * 
     */
    static final int TERMINAL_OP_MASK = createMask(Type.TERMINAL_OP);

    /**
     * The bit mask for upstream terminal operation flags.
     * <p>
     *  上游终端操作标志的位掩码。
     * 
     */
    static final int UPSTREAM_TERMINAL_OP_MASK = createMask(Type.UPSTREAM_TERMINAL_OP);

    private static int createMask(Type t) {
        int mask = 0;
        for (StreamOpFlag flag : StreamOpFlag.values()) {
            mask |= flag.maskTable.get(t) << flag.bitPosition;
        }
        return mask;
    }

    /**
     * Complete flag mask.
     * <p>
     *  完成标志掩码。
     * 
     */
    private static final int FLAG_MASK = createFlagMask();

    private static int createFlagMask() {
        int mask = 0;
        for (StreamOpFlag flag : StreamOpFlag.values()) {
            mask |= flag.preserve;
        }
        return mask;
    }

    /**
     * Flag mask for stream flags that are set.
     * <p>
     *  设置的流标志的标志掩码。
     * 
     */
    private static final int FLAG_MASK_IS = STREAM_MASK;

    /**
     * Flag mask for stream flags that are cleared.
     * <p>
     *  被清除的流标志的标志掩码。
     * 
     */
    private static final int FLAG_MASK_NOT = STREAM_MASK << 1;

    /**
     * The initial value to be combined with the stream flags of the first
     * stream in the pipeline.
     * <p>
     *  要与流水线中第一个流的流标志组合的初始值。
     * 
     */
    static final int INITIAL_OPS_VALUE = FLAG_MASK_IS | FLAG_MASK_NOT;

    /**
     * The bit value to set or inject {@link #DISTINCT}.
     * <p>
     *  设置或注入{@link #DISTINCT}的位值。
     * 
     */
    static final int IS_DISTINCT = DISTINCT.set;

    /**
     * The bit value to clear {@link #DISTINCT}.
     * <p>
     *  要清除{@link #DISTINCT}的位值。
     * 
     */
    static final int NOT_DISTINCT = DISTINCT.clear;

    /**
     * The bit value to set or inject {@link #SORTED}.
     * <p>
     *  用于设置或注入{@link #SORTED}的位值。
     * 
     */
    static final int IS_SORTED = SORTED.set;

    /**
     * The bit value to clear {@link #SORTED}.
     * <p>
     *  要清除{@link #SORTED}的位值。
     * 
     */
    static final int NOT_SORTED = SORTED.clear;

    /**
     * The bit value to set or inject {@link #ORDERED}.
     * <p>
     *  要设置或注入{@link #ORDERED}的位值。
     * 
     */
    static final int IS_ORDERED = ORDERED.set;

    /**
     * The bit value to clear {@link #ORDERED}.
     * <p>
     *  要清除{@link #ORDERED}的位值。
     * 
     */
    static final int NOT_ORDERED = ORDERED.clear;

    /**
     * The bit value to set {@link #SIZED}.
     * <p>
     *  位值设置{@link #SIZED}。
     * 
     */
    static final int IS_SIZED = SIZED.set;

    /**
     * The bit value to clear {@link #SIZED}.
     * <p>
     *  要清除{@link #SIZED}的位值。
     * 
     */
    static final int NOT_SIZED = SIZED.clear;

    /**
     * The bit value to inject {@link #SHORT_CIRCUIT}.
     * <p>
     *  要注入{@link #SHORT_CIRCUIT}的位值。
     * 
     */
    static final int IS_SHORT_CIRCUIT = SHORT_CIRCUIT.set;

    private static int getMask(int flags) {
        return (flags == 0)
               ? FLAG_MASK
               : ~(flags | ((FLAG_MASK_IS & flags) << 1) | ((FLAG_MASK_NOT & flags) >> 1));
    }

    /**
     * Combines stream or operation flags with previously combined stream and
     * operation flags to produce updated combined stream and operation flags.
     * <p>
     * A flag set on stream flags or injected on operation flags,
     * and injected combined stream and operation flags,
     * will be injected on the updated combined stream and operation flags.
     *
     * <p>
     * A flag set on stream flags or injected on operation flags,
     * and cleared on the combined stream and operation flags,
     * will be cleared on the updated combined stream and operation flags.
     *
     * <p>
     * A flag set on the stream flags or injected on operation flags,
     * and preserved on the combined stream and operation flags,
     * will be injected on the updated combined stream and operation flags.
     *
     * <p>
     * A flag not set on the stream flags or cleared/preserved on operation
     * flags, and injected on the combined stream and operation flags,
     * will be injected on the updated combined stream and operation flags.
     *
     * <p>
     * A flag not set on the stream flags or cleared/preserved on operation
     * flags, and cleared on the combined stream and operation flags,
     * will be cleared on the updated combined stream and operation flags.
     *
     * <p>
     * A flag not set on the stream flags,
     * and preserved on the combined stream and operation flags
     * will be preserved on the updated combined stream and operation flags.
     *
     * <p>
     * A flag cleared on operation flags,
     * and preserved on the combined stream and operation flags
     * will be cleared on the updated combined stream and operation flags.
     *
     * <p>
     * A flag preserved on operation flags,
     * and preserved on the combined stream and operation flags
     * will be preserved on the updated combined stream and operation flags.
     *
     * <p>
     *  将流或操作标志与先前组合的流和操作标志组合以产生更新的组合流和操作标志。
     * <p>
     * 对流标记设置的标志或注入操作标志的标志以及注入的组合流和操作标志将被注入到更新的组合流和操作标志上。
     * 
     * <p>
     *  在流标志上设置或注入操作标志并在组合的流和操作标志上清除的标志将在更新的组合流和操作标志上被清除。
     * 
     * <p>
     *  在流标志上设置的标志或注入在操作标志上的标志,并且保留在组合流和操作标志上的标志将被注入到更新的组合流和操作标志上。
     * 
     * <p>
     *  不在流标志上设置或者在操作标志上被清除/保留并且被注入到组合流和操作标志上的标志将被注入到更新的组合流和操作标志上。
     * 
     * <p>
     *  未在流标志上设置或在操作标志上清除/保留的标志,以及在组合流和操作标志上清除的标志将在更新的组合流和操作标志上清除。
     * 
     * <p>
     *  不对流标志设置的标志,以及保留在组合流和操作标志上的标志将保留在更新的组合流和操作标志上。
     * 
     * <p>
     *  在操作标志上清除的标志以及保留在组合流和操作标志上的标志将在更新的组合流和操作标志上被清除。
     * 
     * @param newStreamOrOpFlags the stream or operation flags.
     * @param prevCombOpFlags previously combined stream and operation flags.
     *        The value {#link INITIAL_OPS_VALUE} must be used as the seed value.
     * @return the updated combined stream and operation flags.
     */
    static int combineOpFlags(int newStreamOrOpFlags, int prevCombOpFlags) {
        // 0x01 or 0x10 nibbles are transformed to 0x11
        // 0x00 nibbles remain unchanged
        // Then all the bits are flipped
        // Then the result is logically or'ed with the operation flags.
        return (prevCombOpFlags & StreamOpFlag.getMask(newStreamOrOpFlags)) | newStreamOrOpFlags;
    }

    /**
     * Converts combined stream and operation flags to stream flags.
     *
     * <p>Each flag injected on the combined stream and operation flags will be
     * set on the stream flags.
     *
     * <p>
     * 
     * <p>
     *  保存在操作标志上并保留在组合流和操作标志上的标志将保留在更新的组合流和操作标志上。
     * 
     * 
     * @param combOpFlags the combined stream and operation flags.
     * @return the stream flags.
     */
    static int toStreamFlags(int combOpFlags) {
        // By flipping the nibbles 0x11 become 0x00 and 0x01 become 0x10
        // Shift left 1 to restore set flags and mask off anything other than the set flags
        return ((~combOpFlags) >> 1) & FLAG_MASK_IS & combOpFlags;
    }

    /**
     * Converts stream flags to a spliterator characteristic bit set.
     *
     * <p>
     * 将组合流和操作标志转换为流标志。
     * 
     *  <p>注入到组合流和操作标志上的每个标志将被设置在流标志上。
     * 
     * 
     * @param streamFlags the stream flags.
     * @return the spliterator characteristic bit set.
     */
    static int toCharacteristics(int streamFlags) {
        return streamFlags & SPLITERATOR_CHARACTERISTICS_MASK;
    }

    /**
     * Converts a spliterator characteristic bit set to stream flags.
     *
     * @implSpec
     * If the spliterator is naturally {@code SORTED} (the associated
     * {@code Comparator} is {@code null}) then the characteristic is converted
     * to the {@link #SORTED} flag, otherwise the characteristic is not
     * converted.
     *
     * <p>
     *  将流标记转换为Spliterator特性位集。
     * 
     * 
     * @param spliterator the spliterator from which to obtain characteristic
     *        bit set.
     * @return the stream flags.
     */
    static int fromCharacteristics(Spliterator<?> spliterator) {
        int characteristics = spliterator.characteristics();
        if ((characteristics & Spliterator.SORTED) != 0 && spliterator.getComparator() != null) {
            // Do not propagate the SORTED characteristic if it does not correspond
            // to a natural sort order
            return characteristics & SPLITERATOR_CHARACTERISTICS_MASK & ~Spliterator.SORTED;
        }
        else {
            return characteristics & SPLITERATOR_CHARACTERISTICS_MASK;
        }
    }

    /**
     * Converts a spliterator characteristic bit set to stream flags.
     *
     * <p>
     *  将Spliterator特性位转换为流标记。
     * 
     *  @implSpec如果分割器自然{@code SORTED}(相关联的{@code Comparator}是{@code null}),那么特性将转换为{@link #SORTED}标志,否则不会转换
     * 特性。
     * 
     * @param characteristics the spliterator characteristic bit set.
     * @return the stream flags.
     */
    static int fromCharacteristics(int characteristics) {
        return characteristics & SPLITERATOR_CHARACTERISTICS_MASK;
    }
}
