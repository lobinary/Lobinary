/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, 2013, Oracle and/or its affiliates. All rights reserved.
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
package java.util.jar;

import java.util.SortedMap;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.File;
import java.io.IOException;
import java.beans.PropertyChangeListener;




/**
 * Transforms a JAR file to or from a packed stream in Pack200 format.
 * Please refer to Network Transfer Format JSR 200 Specification at
 * <a href=http://jcp.org/aboutJava/communityprocess/review/jsr200/index.html>http://jcp.org/aboutJava/communityprocess/review/jsr200/index.html</a>
 * <p>
 * Typically the packer engine is used by application developers
 * to deploy or host JAR files on a website.
 * The unpacker  engine is used by deployment applications to
 * transform the byte-stream back to JAR format.
 * <p>
 * Here is an example using  packer and unpacker:
 * <pre>{@code
 *    import java.util.jar.Pack200;
 *    import java.util.jar.Pack200.*;
 *    ...
 *    // Create the Packer object
 *    Packer packer = Pack200.newPacker();
 *
 *    // Initialize the state by setting the desired properties
 *    Map p = packer.properties();
 *    // take more time choosing codings for better compression
 *    p.put(Packer.EFFORT, "7");  // default is "5"
 *    // use largest-possible archive segments (>10% better compression).
 *    p.put(Packer.SEGMENT_LIMIT, "-1");
 *    // reorder files for better compression.
 *    p.put(Packer.KEEP_FILE_ORDER, Packer.FALSE);
 *    // smear modification times to a single value.
 *    p.put(Packer.MODIFICATION_TIME, Packer.LATEST);
 *    // ignore all JAR deflation requests,
 *    // transmitting a single request to use "store" mode.
 *    p.put(Packer.DEFLATE_HINT, Packer.FALSE);
 *    // discard debug attributes
 *    p.put(Packer.CODE_ATTRIBUTE_PFX+"LineNumberTable", Packer.STRIP);
 *    // throw an error if an attribute is unrecognized
 *    p.put(Packer.UNKNOWN_ATTRIBUTE, Packer.ERROR);
 *    // pass one class file uncompressed:
 *    p.put(Packer.PASS_FILE_PFX+0, "mutants/Rogue.class");
 *    try {
 *        JarFile jarFile = new JarFile("/tmp/testref.jar");
 *        FileOutputStream fos = new FileOutputStream("/tmp/test.pack");
 *        // Call the packer
 *        packer.pack(jarFile, fos);
 *        jarFile.close();
 *        fos.close();
 *
 *        File f = new File("/tmp/test.pack");
 *        FileOutputStream fostream = new FileOutputStream("/tmp/test.jar");
 *        JarOutputStream jostream = new JarOutputStream(fostream);
 *        Unpacker unpacker = Pack200.newUnpacker();
 *        // Call the unpacker
 *        unpacker.unpack(f, jostream);
 *        // Must explicitly close the output.
 *        jostream.close();
 *    } catch (IOException ioe) {
 *        ioe.printStackTrace();
 *    }
 * }</pre>
 * <p>
 * A Pack200 file compressed with gzip can be hosted on HTTP/1.1 web servers.
 * The deployment applications can use "Accept-Encoding=pack200-gzip". This
 * indicates to the server that the client application desires a version of
 * the file encoded with Pack200 and further compressed with gzip. Please
 * refer to  <a href="{@docRoot}/../technotes/guides/deployment/deployment-guide/pack200.html">Java Deployment Guide</a> for more details and
 * techniques.
 * <p>
 * Unless otherwise noted, passing a <tt>null</tt> argument to a constructor or
 * method in this class will cause a {@link NullPointerException} to be thrown.
 *
 * <p>
 *  将JAR文件转换为或从Pack200格式的打包流中转换。
 * 请参阅网络传输格式JSR 200规范,网址为：<a href=http://jcp.org/aboutJava/communityprocess/review/jsr200/index.html> ht
 * tp://jcp.org/aboutJava/communityprocess/review/jsr200/ index.html </a>。
 *  将JAR文件转换为或从Pack200格式的打包流中转换。
 * <p>
 *  通常,应用程序开发人员使用打包程序引擎在网站上部署或托管JAR文件。解包器引擎被部署应用程序用于将字节流转换回JAR格式。
 * <p>
 *  这里是一个使用packer和unpacker的例子：<pre> {@ code import java.util.jar.Pack200; import java.util.jar.Pack200。
 * *; ... //创建Packer对象Packer packer = Pack200.newPacker();。
 * 
 * //通过设置所需的属性初始化状态Map p = packer.properties(); // take more time选择编码以获得更好的压缩p.put(Packer.EFFORT,"7"); /
 * /默认为"5"//使用最大 - 可能的归档段(压缩率> 10％)。
 *  p.put(Packer.SEGMENT_LIMIT,"-1"); //重新排序文件以获得更好的压缩。
 *  p.put(Packer.KEEP_FILE_ORDER,Packer.FALSE); //将修改时间修改为单个值。
 *  p.put(Packer.MODIFICATION_TIME,Packer.LATEST); //忽略所有JAR缩小请求,//传输单个请求以使用"store"模式。
 *  p.put(Packer.DEFLATE_HINT,Packer.FALSE); // discard debug attributes p.put(Packer.CODE_ATTRIBUTE_PFX
 *  +"LineNumberTable",Packer.STRIP); //如果属性无法识别,则抛出错误p.put(Packer.UNKNOWN_ATTRIBUTE,Packer.ERROR); //传递
 * 一个类文件uncompressed：p.put(Packer.PASS_FILE_PFX + 0,"mutants / Rogue.class"); try {JarFile jarFile = new JarFile("/ tmp / testref.jar"); FileOutputStream fos = new FileOutputStream("/ tmp / test.pack"); //调用packer packer.pack(jarFile,fos); jarFile.close(); fos.close();。
 *  p.put(Packer.MODIFICATION_TIME,Packer.LATEST); //忽略所有JAR缩小请求,//传输单个请求以使用"store"模式。
 * 
 *  File f = new File("/ tmp / test.pack"); FileOutputStream fostream = new FileOutputStream("/ tmp / te
 * st.jar"); JarOutputStream jostream = new JarOutputStream(fostream); Unpacker unpacker = Pack200.newUn
 * packer(); //调用unpacker unpacker.unpack(f,jostream); //必须显式关闭输出。
 *  jostream.close(); } catch(IOException ioe){ioe.printStackTrace(); }} </pre>。
 * <p>
 * 使用gzip压缩的Pack200文件可以托管在HTTP / 1.1 Web服务器上。部署应用程序可以使用"Accept-Encoding = pack200-gzip"。
 * 这向服务器指示客户端应用程序需要使用Pack200编码的文件的版本,并使用gzip进一步压缩。
 * 有关更多详细信息和技巧,请参阅<a href="{@docRoot}/../technotes/guides/deployment/deployment-guide/pack200.html"> Jav
 * a部署指南</a>。
 * 这向服务器指示客户端应用程序需要使用Pack200编码的文件的版本,并使用gzip进一步压缩。
 * <p>
 *  除非另有说明,否则将<tt> null </tt>参数传递给此类中的构造函数或方法将导致抛出{@link NullPointerException}。
 * 
 * 
 * @author John Rose
 * @author Kumar Srinivasan
 * @since 1.5
 */
public abstract class Pack200 {
    private Pack200() {} //prevent instantiation

    // Static methods of the Pack200 class.
    /**
     * Obtain new instance of a class that implements Packer.
     * <ul>
     * <li><p>If the system property <tt>java.util.jar.Pack200.Packer</tt>
     * is defined, then the value is taken to be the fully-qualified name
     * of a concrete implementation class, which must implement Packer.
     * This class is loaded and instantiated.  If this process fails
     * then an unspecified error is thrown.</p></li>
     *
     * <li><p>If an implementation has not been specified with the system
     * property, then the system-default implementation class is instantiated,
     * and the result is returned.</p></li>
     * </ul>
     *
     * <p>Note:  The returned object is not guaranteed to operate
     * correctly if multiple threads use it at the same time.
     * A multi-threaded application should either allocate multiple
     * packer engines, or else serialize use of one engine with a lock.
     *
     * <p>
     *  获取实现Packer的类的新实例。
     * <ul>
     *  <li> <p>如果定义了系统属性<tt> java.util.jar.Pack200.Packer </tt>,则该值将被视为具体实现类的完全限定名称,该类必须实现封隔器。此类已加载并实例化。
     * 如果此过程失败,则会抛出未指定的错误。</p> </li>。
     * 
     *  <li> <p>如果尚未使用系统属性指定实施,则系统默认实现类将被实例化,并返回结果。</p> </li>
     * </ul>
     * 
     *  <p>注意：如果多个线程同时使用它,返回的对象不能保证正确操作。多线程应用程序应该分配多个打包引擎,或者序列化使用带锁的一个引擎。
     * 
     * 
     * @return  A newly allocated Packer engine.
     */
    public synchronized static Packer newPacker() {
        return (Packer) newInstance(PACK_PROVIDER);
    }


    /**
     * Obtain new instance of a class that implements Unpacker.
     * <ul>
     * <li><p>If the system property <tt>java.util.jar.Pack200.Unpacker</tt>
     * is defined, then the value is taken to be the fully-qualified
     * name of a concrete implementation class, which must implement Unpacker.
     * The class is loaded and instantiated.  If this process fails
     * then an unspecified error is thrown.</p></li>
     *
     * <li><p>If an implementation has not been specified with the
     * system property, then the system-default implementation class
     * is instantiated, and the result is returned.</p></li>
     * </ul>
     *
     * <p>Note:  The returned object is not guaranteed to operate
     * correctly if multiple threads use it at the same time.
     * A multi-threaded application should either allocate multiple
     * unpacker engines, or else serialize use of one engine with a lock.
     *
     * <p>
     *  获取实现Unpacker的类的新实例。
     * <ul>
     * <li> <p>如果定义了系统属性<tt> java.util.jar.Pack200.Unpacker </tt>,则该值将被视为具体实现类的完全限定名称,该类必须实现开箱。类被加载并实例化。
     * 如果此过程失败,则会抛出未指定的错误。</p> </li>。
     * 
     *  <li> <p>如果尚未使用系统属性指定实施,则系统默认实现类将被实例化,并返回结果。</p> </li>
     * </ul>
     * 
     *  <p>注意：如果多个线程同时使用它,返回的对象不能保证正确操作。多线程应用程序应该分配多个解包器引擎,或者使用锁来序列化使用一个引擎。
     * 
     * 
     * @return  A newly allocated Unpacker engine.
     */

    public static Unpacker newUnpacker() {
        return (Unpacker) newInstance(UNPACK_PROVIDER);
    }

    // Interfaces
    /**
     * The packer engine applies various transformations to the input JAR file,
     * making the pack stream highly compressible by a compressor such as
     * gzip or zip. An instance of the engine can be obtained
     * using {@link #newPacker}.

     * The high degree of compression is achieved
     * by using a number of techniques described in the JSR 200 specification.
     * Some of the techniques are sorting, re-ordering and co-location of the
     * constant pool.
     * <p>
     * The pack engine is initialized to an initial state as described
     * by their properties below.
     * The initial state can be manipulated by getting the
     * engine properties (using {@link #properties}) and storing
     * the modified properties on the map.
     * The resource files will be passed through with no changes at all.
     * The class files will not contain identical bytes, since the unpacker
     * is free to change minor class file features such as constant pool order.
     * However, the class files will be semantically identical,
     * as specified in
     * <cite>The Java&trade; Virtual Machine Specification</cite>.
     * <p>
     * By default, the packer does not change the order of JAR elements.
     * Also, the modification time and deflation hint of each
     * JAR element is passed unchanged.
     * (Any other ZIP-archive information, such as extra attributes
     * giving Unix file permissions, are lost.)
     * <p>
     * Note that packing and unpacking a JAR will in general alter the
     * bytewise contents of classfiles in the JAR.  This means that packing
     * and unpacking will in general invalidate any digital signatures
     * which rely on bytewise images of JAR elements.  In order both to sign
     * and to pack a JAR, you must first pack and unpack the JAR to
     * "normalize" it, then compute signatures on the unpacked JAR elements,
     * and finally repack the signed JAR.
     * Both packing steps should
     * use precisely the same options, and the segment limit may also
     * need to be set to "-1", to prevent accidental variation of segment
     * boundaries as class file sizes change slightly.
     * <p>
     * (Here's why this works:  Any reordering the packer does
     * of any classfile structures is idempotent, so the second packing
     * does not change the orderings produced by the first packing.
     * Also, the unpacker is guaranteed by the JSR 200 specification
     * to produce a specific bytewise image for any given transmission
     * ordering of archive elements.)
     * <p>
     * In order to maintain backward compatibility, the pack file's version is
     * set to accommodate the class files present in the input JAR file. In
     * other words, the pack file version will be the latest, if the class files
     * are the latest and conversely the pack file version will be the oldest
     * if the class file versions are also the oldest. For intermediate class
     * file versions the corresponding pack file version will be used.
     * For example:
     *    If the input JAR-files are solely comprised of 1.5  (or  lesser)
     * class files, a 1.5 compatible pack file is  produced. This will also be
     * the case for archives that have no class files.
     *    If the input JAR-files contains a 1.6 class file, then the pack file
     * version will be set to 1.6.
     * <p>
     * Note: Unless otherwise noted, passing a <tt>null</tt> argument to a
     * constructor or method in this class will cause a {@link NullPointerException}
     * to be thrown.
     * <p>
     * <p>
     *  打包器引擎对输入JAR文件应用各种转换,使压缩包(如gzip或zip)可高度压缩包流。可以使用{@link #newPacker}获取引擎的实例。
     * 
     *  通过使用在JSR 200规范中描述的多种技术来实现高度的压缩。一些技术是常量池的排序,重新排序和共同定位。
     * <p>
     * 包引擎被初始化为初始状态,如下面的属性所描述的。初始状态可以通过获取引擎属性(使用{@link #properties})并在地图上存储修改的属性来操作。资源文件将被传递通过,没有任何更改。
     * 类文件将不包含相同的字节,因为解包器可以自由更改辅助类文件特性,例如常量池顺序。然而,类文件将在语义上相同,如在<cite> Java&trade;虚拟机规范</cite>。
     * <p>
     *  默认情况下,打包程序不会更改JAR元素的顺序。此外,每个JAR元素的修改时间和通缩提示不变地传递。 (任何其他ZIP压缩文件信息,例如赋予Unix文件权限的额外属性都会丢失。)
     * <p>
     *  注意,打包和解包JAR通常会改变JAR中类文件的字节内容。这意味着打包和解包通常将使依赖于JAR元素的成像图像的任何数字签名无效。
     * 为了签名和打包JAR,您必须首先打包和解包JAR以"正常化"它,然后计算未打包的JAR元素上的签名,最后重新打包签名的JAR。
     * 两个打包步骤应使用完全相同的选项,并且段限制也可能需要设置为"-1",以防止类文件大小稍微改变时段边界的意外变化。
     * <p>
     * (这里是为什么这样工作：任何重排序打包任何类文件结构是幂等的,所以第二个打包不改变第一个打包产生的顺序。
     * 此外,解包器由JSR 200规范保证产生一个特定的bytewise图像对于档案元素的任何给定的传输排序)。
     * <p>
     *  为了保持向后兼容性,包文件的版本设置为适应输入JAR文件中存在的类文件。换句话说,包文件版本将是最新的,如果类文件是最新的,并且相反地,如果类文件版本也是最旧的,则包文件版本将是最旧的。
     * 对于中间类文件版本,将使用相应的包文件版本。例如：如果输入JAR文件仅包含1.5(或更少)类文件,则会生成一个1.5兼容的包文件。这也将是没有类文件的档案的情况。
     * 如果输入JAR文件包含1.6类文件,则包文件版本将设置为1.6。
     * <p>
     *  注意：除非另有说明,否则向此类中的构造函数或方法传递<tt> null </tt>参数将导致抛出{@link NullPointerException}。
     * <p>
     * 
     * @since 1.5
     */
    public interface Packer {
        /**
         * This property is a numeral giving the estimated target size N
         * (in bytes) of each archive segment.
         * If a single input file requires more than N bytes,
         * it will be given its own archive segment.
         * <p>
         * As a special case, a value of -1 will produce a single large
         * segment with all input files, while a value of 0 will
         * produce one segment for each class.
         * Larger archive segments result in less fragmentation and
         * better compression, but processing them requires more memory.
         * <p>
         * The size of each segment is estimated by counting the size of each
         * input file to be transmitted in the segment, along with the size
         * of its name and other transmitted properties.
         * <p>
         * The default is -1, which means the packer will always create a single
         * segment output file. In cases where extremely large output files are
         * generated, users are strongly encouraged to use segmenting or break
         * up the input file into smaller JARs.
         * <p>
         * A 10Mb JAR packed without this limit will
         * typically pack about 10% smaller, but the packer may require
         * a larger Java heap (about ten times the segment limit).
         * <p>
         *  该属性是给出每个归档段​​的估计目标大小N(以字节为单位)的数字。如果单个输入文件需要超过N个字节,则将给予其自己的归档段。
         * <p>
         * 作为特殊情况,值-1将产生具有所有输入文件的单个大段,而值0将为每个类产生一个段。较大的归档段导致较少的碎片和更好的压缩,但是处理它们需要更多的内存。
         * <p>
         *  通过计数要在段中传输的每个输入文件的大小以及其名称的大小和其他传输的属​​性来估计每个段的大小。
         * <p>
         *  默认值为-1,这意味着打包程序将始终创建单个段输出文件。在生成极大的输出文件的情况下,强烈建议用户使用分段或将输入文件分解为较小的JAR。
         * <p>
         *  没有这个限制的10Mb JAR通常包装大约小10％,但是封装器可能需要更大的Java堆(大约是段限制的十倍)。
         * 
         */
        String SEGMENT_LIMIT    = "pack.segment.limit";

        /**
         * If this property is set to {@link #TRUE}, the packer will transmit
         * all elements in their original order within the source archive.
         * <p>
         * If it is set to {@link #FALSE}, the packer may reorder elements,
         * and also remove JAR directory entries, which carry no useful
         * information for Java applications.
         * (Typically this enables better compression.)
         * <p>
         * The default is {@link #TRUE}, which preserves the input information,
         * but may cause the transmitted archive to be larger than necessary.
         * <p>
         *  如果此属性设置为{@link #TRUE},则打包程序将在源归档中按原始顺序传输所有元素。
         * <p>
         *  如果设置为{@link #FALSE},打包程序可能会重新排序元素,也会删除JAR目录条目,这些条目不包含Java应用程序的有用信息。 (通常这可以实现更好的压缩。)
         * <p>
         *  默认值为{@link #TRUE},它保留输入信息,但可能导致传输的归档大于必需。
         * 
         */
        String KEEP_FILE_ORDER = "pack.keep.file.order";


        /**
         * If this property is set to a single decimal digit, the packer will
         * use the indicated amount of effort in compressing the archive.
         * Level 1 may produce somewhat larger size and faster compression speed,
         * while level 9 will take much longer but may produce better compression.
         * <p>
         * The special value 0 instructs the packer to copy through the
         * original JAR file directly, with no compression.  The JSR 200
         * standard requires any unpacker to understand this special case
         * as a pass-through of the entire archive.
         * <p>
         * The default is 5, investing a modest amount of time to
         * produce reasonable compression.
         * <p>
         * 如果此属性设置为一个十进制数字,则压缩程序将使用指定的压缩归档的工作量。级别1可以产生稍大的尺寸和更快的压缩速度,而级别9将花费更长的时间但可以产生更好的压缩。
         * <p>
         *  特殊值0指示打包器直接复制原始JAR文件,而不压缩。 JSR 200标准要求任何解包器来理解这种特殊情况,作为整个存档的传递。
         * <p>
         *  默认值为5,投入适量的时间来产生合理的压缩。
         * 
         */
        String EFFORT           = "pack.effort";

        /**
         * If this property is set to {@link #TRUE} or {@link #FALSE}, the packer
         * will set the deflation hint accordingly in the output archive, and
         * will not transmit the individual deflation hints of archive elements.
         * <p>
         * If this property is set to the special string {@link #KEEP}, the packer
         * will attempt to determine an independent deflation hint for each
         * available element of the input archive, and transmit this hint separately.
         * <p>
         * The default is {@link #KEEP}, which preserves the input information,
         * but may cause the transmitted archive to be larger than necessary.
         * <p>
         * It is up to the unpacker implementation
         * to take action upon the hint to suitably compress the elements of
         * the resulting unpacked jar.
         * <p>
         * The deflation hint of a ZIP or JAR element indicates
         * whether the element was deflated or stored directly.
         * <p>
         *  如果此属性设置为{@link #TRUE}或{@link #FALSE},则打包程序将在输出归档中相应设置缩小提示,并且不会传输归档元素的单独缩小提示。
         * <p>
         *  如果此属性设置为特殊字符串{@link #KEEP},则打包程序将尝试为输入归档的每个可用元素确定独立的缩小提示,并单独传输此提示。
         * <p>
         *  默认值为{@link #KEEP},它保留输入信息,但可能导致传输的归档大于必需。
         * <p>
         *  由拆包器实现根据提示采取动作以适当地压缩所得到的未包装的罐的元件。
         * <p>
         *  ZIP或JAR元素的缩小提示表示元素是否被放缩或直接存储。
         * 
         */
        String DEFLATE_HINT     = "pack.deflate.hint";

        /**
         * If this property is set to the special string {@link #LATEST},
         * the packer will attempt to determine the latest modification time,
         * among all the available entries in the original archive or the latest
         * modification time of all the available entries in each segment.
         * This single value will be transmitted as part of the segment and applied
         * to all the entries in each segment, {@link #SEGMENT_LIMIT}.
         * <p>
         * This can marginally decrease the transmitted size of the
         * archive, at the expense of setting all installed files to a single
         * date.
         * <p>
         * If this property is set to the special string {@link #KEEP},
         * the packer transmits a separate modification time for each input
         * element.
         * <p>
         * The default is {@link #KEEP}, which preserves the input information,
         * but may cause the transmitted archive to be larger than necessary.
         * <p>
         * It is up to the unpacker implementation to take action to suitably
         * set the modification time of each element of its output file.
         * <p>
         * 如果此属性设置为特殊字符串{@link #LATEST},则打包程序将尝试在原始归档中的所有可用条目或每个段中所有可用条目的最近修改时间之间确定最近的修改时间。
         * 此单一值将作为段的一部分传输,并应用于每个段中的所有条目{@link #SEGMENT_LIMIT}。
         * <p>
         *  这可能会略微降低归档的传输大小,但会将所有已安装的文件设置为单个日期。
         * <p>
         *  如果此属性设置为特殊字符串{@link #KEEP},则打包器为每个输入元素传输单独的修改时间。
         * <p>
         *  默认值为{@link #KEEP},它保留输入信息,但可能导致传输的归档大于必需。
         * <p>
         *  由解包器实现来采取动作来适当地设置其输出文件的每个元素的修改时间。
         * 
         * 
         * @see #SEGMENT_LIMIT
         */
        String MODIFICATION_TIME        = "pack.modification.time";

        /**
         * Indicates that a file should be passed through bytewise, with no
         * compression.  Multiple files may be specified by specifying
         * additional properties with distinct strings appended, to
         * make a family of properties with the common prefix.
         * <p>
         * There is no pathname transformation, except
         * that the system file separator is replaced by the JAR file
         * separator '/'.
         * <p>
         * The resulting file names must match exactly as strings with their
         * occurrences in the JAR file.
         * <p>
         * If a property value is a directory name, all files under that
         * directory will be passed also.
         * <p>
         * Examples:
         * <pre>{@code
         *     Map p = packer.properties();
         *     p.put(PASS_FILE_PFX+0, "mutants/Rogue.class");
         *     p.put(PASS_FILE_PFX+1, "mutants/Wolverine.class");
         *     p.put(PASS_FILE_PFX+2, "mutants/Storm.class");
         *     # Pass all files in an entire directory hierarchy:
         *     p.put(PASS_FILE_PFX+3, "police/");
         * }</pre>
         * <p>
         *  表示文件应通过字节传递,而不压缩。可以通过指定附加的具有不同字符串的附加属性来指定多个文件,以使得具有共同前缀的属性族。
         * <p>
         *  没有路径名转换,除了系统文件分隔符被JAR文件分隔符'/'替换。
         * <p>
         *  生成的文件名必须与在JAR文件中出现的字符串完全匹配。
         * <p>
         *  如果属性值是目录名,则该目录下的所有文件也将被传递。
         * <p>
         * 示例：<pre> {@ code Map p = packer.properties(); p.put(PASS_FILE_PFX + 0,"mutants / Rogue.class"); p.put(PASS_FILE_PFX + 1,"mutants / Wolverine.class"); p.put(PASS_FILE_PFX + 2,"mutants / Storm.class"); #传递整个目录层次结构中的所有文件：p.put(PASS_FILE_PFX + 3,"police /"); }
         *  </pre>。
         * 
         */
        String PASS_FILE_PFX            = "pack.pass.file.";

        /// Attribute control.

        /**
         * Indicates the action to take when a class-file containing an unknown
         * attribute is encountered.  Possible values are the strings {@link #ERROR},
         * {@link #STRIP}, and {@link #PASS}.
         * <p>
         * The string {@link #ERROR} means that the pack operation
         * as a whole will fail, with an exception of type <code>IOException</code>.
         * The string
         * {@link #STRIP} means that the attribute will be dropped.
         * The string
         * {@link #PASS} means that the whole class-file will be passed through
         * (as if it were a resource file) without compression, with  a suitable warning.
         * This is the default value for this property.
         * <p>
         * Examples:
         * <pre>{@code
         *     Map p = pack200.getProperties();
         *     p.put(UNKNOWN_ATTRIBUTE, ERROR);
         *     p.put(UNKNOWN_ATTRIBUTE, STRIP);
         *     p.put(UNKNOWN_ATTRIBUTE, PASS);
         * }</pre>
         * <p>
         *  指示遇到包含未知属性的类文件时要执行的操作。可能的值为字符串{@link #ERROR},{@link #STRIP}和{@link #PASS}。
         * <p>
         *  字符串{@link #ERROR}意味着打包操作作为一个整体将失败,除了<code> IOException </code>类型。字符串{@link #STRIP}意味着该属性将被删除。
         * 字符串{@link #PASS}意味着整个类文件将在没有压缩的情况下通过(就像它是资源文件一样),并带有适当的警告。这是此属性的默认值。
         * <p>
         *  示例：<pre> {@ code Map p = pack200.getProperties(); p.put(UNKNOWN_ATTRIBUTE,ERROR); p.put(UNKNOWN_ATTRIBUTE,STRIP); p.put(UNKNOWN_ATTRIBUTE,PASS); }
         *  </pre>。
         * 
         */
        String UNKNOWN_ATTRIBUTE        = "pack.unknown.attribute";

        /**
         * When concatenated with a class attribute name,
         * indicates the format of that attribute,
         * using the layout language specified in the JSR 200 specification.
         * <p>
         * For example, the effect of this option is built in:
         * <code>pack.class.attribute.SourceFile=RUH</code>.
         * <p>
         * The special strings {@link #ERROR}, {@link #STRIP}, and {@link #PASS} are
         * also allowed, with the same meaning as {@link #UNKNOWN_ATTRIBUTE}.
         * This provides a way for users to request that specific attributes be
         * refused, stripped, or passed bitwise (with no class compression).
         * <p>
         * Code like this might be used to support attributes for JCOV:
         * <pre><code>
         *     Map p = packer.properties();
         *     p.put(CODE_ATTRIBUTE_PFX+"CoverageTable",       "NH[PHHII]");
         *     p.put(CODE_ATTRIBUTE_PFX+"CharacterRangeTable", "NH[PHPOHIIH]");
         *     p.put(CLASS_ATTRIBUTE_PFX+"SourceID",           "RUH");
         *     p.put(CLASS_ATTRIBUTE_PFX+"CompilationID",      "RUH");
         * </code></pre>
         * <p>
         * Code like this might be used to strip debugging attributes:
         * <pre><code>
         *     Map p = packer.properties();
         *     p.put(CODE_ATTRIBUTE_PFX+"LineNumberTable",    STRIP);
         *     p.put(CODE_ATTRIBUTE_PFX+"LocalVariableTable", STRIP);
         *     p.put(CLASS_ATTRIBUTE_PFX+"SourceFile",        STRIP);
         * </code></pre>
         * <p>
         *  当与类属性名称连接时,使用JSR 200规范中指定的布局语言指示该属性的格式。
         * <p>
         *  例如,此选项的效果内置：<code> pack.class.attribute.SourceFile = RUH </code>。
         * <p>
         * 也允许使用特殊字符串{@link #ERROR},{@link #STRIP}和{@link #PASS},其含义与{@link #UNKNOWN_ATTRIBUTE}相同。
         * 这为用户提供了一种方式来请求拒绝,剥离或按位传递特定属性(没有类压缩)。
         * <p>
         *  这样的代码可能用于支持JCOV的属性：<pre> <code> Map p = packer.properties(); p.put(CODE_ATTRIBUTE_PFX +"CoverageTabl
         * e","NH [PHHII]"); p.put(CODE_ATTRIBUTE_PFX +"CharacterRangeTable","NH [PHPOHIIH]"); p.put(CLASS_ATTRI
         * BUTE_PFX +"SourceID","RUH"); p.put(CLASS_ATTRIBUTE_PFX +"CompilationID","RUH"); </code> </pre>。
         * <p>
         *  像这样的代码可能用于剥离调试属性：<pre> <code> Map p = packer.properties(); p.put(CODE_ATTRIBUTE_PFX +"LineNumberTabl
         * e",STRIP); p.put(CODE_ATTRIBUTE_PFX +"LocalVariableTable",STRIP); p.put(CLASS_ATTRIBUTE_PFX +"SourceF
         * ile",STRIP); </code> </pre>。
         * 
         */
        String CLASS_ATTRIBUTE_PFX      = "pack.class.attribute.";

        /**
         * When concatenated with a field attribute name,
         * indicates the format of that attribute.
         * For example, the effect of this option is built in:
         * <code>pack.field.attribute.Deprecated=</code>.
         * The special strings {@link #ERROR}, {@link #STRIP}, and
         * {@link #PASS} are also allowed.
         * <p>
         *  当与字段属性名称连接时,表示该属性的格式。例如,此选项的效果内置：<code> pack.field.attribute.Deprecated = </code>。
         * 也允许使用特殊字符串{@link #ERROR},{@link #STRIP}和{@link #PASS}。
         * 
         * 
         * @see #CLASS_ATTRIBUTE_PFX
         */
        String FIELD_ATTRIBUTE_PFX      = "pack.field.attribute.";

        /**
         * When concatenated with a method attribute name,
         * indicates the format of that attribute.
         * For example, the effect of this option is built in:
         * <code>pack.method.attribute.Exceptions=NH[RCH]</code>.
         * The special strings {@link #ERROR}, {@link #STRIP}, and {@link #PASS}
         * are also allowed.
         * <p>
         *  当与方法属性名称连接时,表示该属性的格式。例如,此选项的效果内置于：<code> pack.method.attribute.Exceptions = NH [RCH] </code>。
         * 也允许使用特殊字符串{@link #ERROR},{@link #STRIP}和{@link #PASS}。
         * 
         * 
         * @see #CLASS_ATTRIBUTE_PFX
         */
        String METHOD_ATTRIBUTE_PFX     = "pack.method.attribute.";

        /**
         * When concatenated with a code attribute name,
         * indicates the format of that attribute.
         * For example, the effect of this option is built in:
         * <code>pack.code.attribute.LocalVariableTable=NH[PHOHRUHRSHH]</code>.
         * The special strings {@link #ERROR}, {@link #STRIP}, and {@link #PASS}
         * are also allowed.
         * <p>
         * 当与代码属性名称连接时,表示该属性的格式。
         * 例如,此选项的效果内置于：<code> pack.code.attribute.LocalVariableTable = NH [PHOHRUHRSHH] </code>。
         * 也允许使用特殊字符串{@link #ERROR},{@link #STRIP}和{@link #PASS}。
         * 
         * 
         * @see #CLASS_ATTRIBUTE_PFX
         */
        String CODE_ATTRIBUTE_PFX       = "pack.code.attribute.";

        /**
         * The unpacker's progress as a percentage, as periodically
         * updated by the unpacker.
         * Values of 0 - 100 are normal, and -1 indicates a stall.
         * Progress can be monitored by polling the value of this
         * property.
         * <p>
         * At a minimum, the unpacker must set progress to 0
         * at the beginning of a packing operation, and to 100
         * at the end.
         * <p>
         *  解包器的进度为百分比,由解包器定期更新。 0  -  100的值是正常的,-1表示停顿。可以通过轮询此属性的值来监视进度。
         * <p>
         *  至少,解包器必须在打包操作开始时将进度设置为0,并在结束时将其设置为100。
         * 
         */
        String PROGRESS                 = "pack.progress";

        /** The string "keep", a possible value for certain properties.
        /* <p>
        /* 
         * @see #DEFLATE_HINT
         * @see #MODIFICATION_TIME
         */
        String KEEP  = "keep";

        /** The string "pass", a possible value for certain properties.
        /* <p>
        /* 
         * @see #UNKNOWN_ATTRIBUTE
         * @see #CLASS_ATTRIBUTE_PFX
         * @see #FIELD_ATTRIBUTE_PFX
         * @see #METHOD_ATTRIBUTE_PFX
         * @see #CODE_ATTRIBUTE_PFX
         */
        String PASS  = "pass";

        /** The string "strip", a possible value for certain properties.
        /* <p>
        /* 
         * @see #UNKNOWN_ATTRIBUTE
         * @see #CLASS_ATTRIBUTE_PFX
         * @see #FIELD_ATTRIBUTE_PFX
         * @see #METHOD_ATTRIBUTE_PFX
         * @see #CODE_ATTRIBUTE_PFX
         */
        String STRIP = "strip";

        /** The string "error", a possible value for certain properties.
        /* <p>
        /* 
         * @see #UNKNOWN_ATTRIBUTE
         * @see #CLASS_ATTRIBUTE_PFX
         * @see #FIELD_ATTRIBUTE_PFX
         * @see #METHOD_ATTRIBUTE_PFX
         * @see #CODE_ATTRIBUTE_PFX
         */
        String ERROR = "error";

        /** The string "true", a possible value for certain properties.
        /* <p>
        /* 
         * @see #KEEP_FILE_ORDER
         * @see #DEFLATE_HINT
         */
        String TRUE = "true";

        /** The string "false", a possible value for certain properties.
        /* <p>
        /* 
         * @see #KEEP_FILE_ORDER
         * @see #DEFLATE_HINT
         */
        String FALSE = "false";

        /** The string "latest", a possible value for certain properties.
        /* <p>
        /* 
         * @see #MODIFICATION_TIME
         */
        String LATEST = "latest";

        /**
         * Get the set of this engine's properties.
         * This set is a "live view", so that changing its
         * contents immediately affects the Packer engine, and
         * changes from the engine (such as progress indications)
         * are immediately visible in the map.
         *
         * <p>The property map may contain pre-defined implementation
         * specific and default properties.  Users are encouraged to
         * read the information and fully understand the implications,
         * before modifying pre-existing properties.
         * <p>
         * Implementation specific properties are prefixed with a
         * package name associated with the implementor, beginning
         * with <tt>com.</tt> or a similar prefix.
         * All property names beginning with <tt>pack.</tt> and
         * <tt>unpack.</tt> are reserved for use by this API.
         * <p>
         * Unknown properties may be ignored or rejected with an
         * unspecified error, and invalid entries may cause an
         * unspecified error to be thrown.
         *
         * <p>
         * The returned map implements all optional {@link SortedMap} operations
         * <p>
         *  获取此引擎的属性集。该集合是"实时视图",因此更改其内容会立即影响Packer引擎,并且来自引擎的更改(如进度指示)会立即在地图中显示。
         * 
         *  <p>属性映射可以包含预定义的实现特定和默认属性。鼓励用户在修改预先存在的属性之前阅读信息并完全理解其含义。
         * <p>
         *  实现特定属性以与实现者关联的包名称开头,以<tt> com。</tt>开头或类似的前缀。以<tt> pack。</tt>和<tt> unpack。</tt>开头的所有属性名称都保留供此API使用。
         * <p>
         *  未知属性可能会被忽略或使用未指定的错误拒绝,并且无效条目可能会导致抛出未指定的错误。
         * 
         * <p>
         * 返回的映射实现所有可选的{@link SortedMap}操作
         * 
         * 
         * @return A sorted association of property key strings to property
         * values.
         */
        SortedMap<String,String> properties();

        /**
         * Takes a JarFile and converts it into a Pack200 archive.
         * <p>
         * Closes its input but not its output.  (Pack200 archives are appendable.)
         * <p>
         *  获取JarFile并将其转换为Pack200压缩包。
         * <p>
         *  关闭其输入,但不关闭其输出。 (Pack200存档是可附加的。)
         * 
         * 
         * @param in a JarFile
         * @param out an OutputStream
         * @exception IOException if an error is encountered.
         */
        void pack(JarFile in, OutputStream out) throws IOException ;

        /**
         * Takes a JarInputStream and converts it into a Pack200 archive.
         * <p>
         * Closes its input but not its output.  (Pack200 archives are appendable.)
         * <p>
         * The modification time and deflation hint attributes are not available,
         * for the JAR manifest file and its containing directory.
         *
         * <p>
         *  获取一个JarInputStream并将其转换为Pack200压缩包。
         * <p>
         *  关闭其输入,但不关闭其输出。 (Pack200存档是可附加的。)
         * <p>
         *  JAR清单文件及其包含目录的修改时间和通缩提示属性不可用。
         * 
         * 
         * @see #MODIFICATION_TIME
         * @see #DEFLATE_HINT
         * @param in a JarInputStream
         * @param out an OutputStream
         * @exception IOException if an error is encountered.
         */
        void pack(JarInputStream in, OutputStream out) throws IOException ;

        /**
         * Registers a listener for PropertyChange events on the properties map.
         * This is typically used by applications to update a progress bar.
         *
         * <p> The default implementation of this method does nothing and has
         * no side-effects.</p>
         *
         * <p><b>WARNING:</b> This method is omitted from the interface
         * declaration in all subset Profiles of Java SE that do not include
         * the {@code java.beans} package. </p>

         * <p>
         *  在属性映射上为PropertyChange事件注册侦听器。这通常由应用程序用于更新进度条。
         * 
         *  <p>此方法的默认实现无效,没有副作用。</p>
         * 
         *  <p> <b>警告：</b>此方法在Java SE的所有子集配置文件的接口声明中省略,不包括{@code java.beans}包。 </p>
         * 
         * 
         * @see #properties
         * @see #PROGRESS
         * @param listener  An object to be invoked when a property is changed.
         * @deprecated The dependency on {@code PropertyChangeListener} creates
         *             a significant impediment to future modularization of the
         *             Java platform. This method will be removed in a future
         *             release.
         *             Applications that need to monitor progress of the packer
         *             can poll the value of the {@link #PROGRESS PROGRESS}
         *             property instead.
         */
        @Deprecated
        default void addPropertyChangeListener(PropertyChangeListener listener) {
        }

        /**
         * Remove a listener for PropertyChange events, added by
         * the {@link #addPropertyChangeListener}.
         *
         * <p> The default implementation of this method does nothing and has
         * no side-effects.</p>
         *
         * <p><b>WARNING:</b> This method is omitted from the interface
         * declaration in all subset Profiles of Java SE that do not include
         * the {@code java.beans} package. </p>
         *
         * <p>
         *  删除由{@link #addPropertyChangeListener}添加的PropertyChange事件的侦听器。
         * 
         *  <p>此方法的默认实现无效,没有副作用。</p>
         * 
         *  <p> <b>警告：</b>此方法在Java SE的所有子集配置文件的接口声明中省略,不包括{@code java.beans}包。 </p>
         * 
         * 
         * @see #addPropertyChangeListener
         * @param listener  The PropertyChange listener to be removed.
         * @deprecated The dependency on {@code PropertyChangeListener} creates
         *             a significant impediment to future modularization of the
         *             Java platform. This method will be removed in a future
         *             release.
         */
        @Deprecated
        default void removePropertyChangeListener(PropertyChangeListener listener) {
        }
    }

    /**
     * The unpacker engine converts the packed stream to a JAR file.
     * An instance of the engine can be obtained
     * using {@link #newUnpacker}.
     * <p>
     * Every JAR file produced by this engine will include the string
     * "<tt>PACK200</tt>" as a zip file comment.
     * This allows a deployer to detect if a JAR archive was packed and unpacked.
     * <p>
     * Note: Unless otherwise noted, passing a <tt>null</tt> argument to a
     * constructor or method in this class will cause a {@link NullPointerException}
     * to be thrown.
     * <p>
     * This version of the unpacker is compatible with all previous versions.
     * <p>
     *  解包器引擎将打包流转换为JAR文件。可以使用{@link #newUnpacker}获取引擎的实例。
     * <p>
     * 此引擎生成的每个JAR文件都将包含字符串"<tt> PACK200 </tt>"作为zip文件注释。这允许部署程序检测JAR归档是否已打包和解包。
     * <p>
     *  注意：除非另有说明,否则向此类中的构造函数或方法传递<tt> null </tt>参数将导致抛出{@link NullPointerException}。
     * <p>
     *  此版本的解包程序与所有先前版本兼容。
     * 
     * 
     * @since 1.5
     */
    public interface Unpacker {

        /** The string "keep", a possible value for certain properties.
        /* <p>
        /* 
         * @see #DEFLATE_HINT
         */
        String KEEP  = "keep";

        /** The string "true", a possible value for certain properties.
        /* <p>
        /* 
         * @see #DEFLATE_HINT
         */
        String TRUE = "true";

        /** The string "false", a possible value for certain properties.
        /* <p>
        /* 
         * @see #DEFLATE_HINT
         */
        String FALSE = "false";

        /**
         * Property indicating that the unpacker should
         * ignore all transmitted values for DEFLATE_HINT,
         * replacing them by the given value, {@link #TRUE} or {@link #FALSE}.
         * The default value is the special string {@link #KEEP},
         * which asks the unpacker to preserve all transmitted
         * deflation hints.
         * <p>
         *  指示解压缩程序应忽略DEFLATE_HINT的所有传输值,将其替换为给定值{@link #TRUE}或{@link #FALSE}的属性。
         * 默认值是特殊字符串{@link #KEEP},它要求解包器保留所有传输的通缩提示。
         * 
         */
        String DEFLATE_HINT      = "unpack.deflate.hint";



        /**
         * The unpacker's progress as a percentage, as periodically
         * updated by the unpacker.
         * Values of 0 - 100 are normal, and -1 indicates a stall.
         * Progress can be monitored by polling the value of this
         * property.
         * <p>
         * At a minimum, the unpacker must set progress to 0
         * at the beginning of a packing operation, and to 100
         * at the end.
         * <p>
         *  解包器的进度为百分比,由解包器定期更新。 0  -  100的值是正常的,-1表示停顿。可以通过轮询此属性的值来监视进度。
         * <p>
         *  至少,解包器必须在打包操作开始时将进度设置为0,并在结束时将其设置为100。
         * 
         */
        String PROGRESS         = "unpack.progress";

        /**
         * Get the set of this engine's properties. This set is
         * a "live view", so that changing its
         * contents immediately affects the Packer engine, and
         * changes from the engine (such as progress indications)
         * are immediately visible in the map.
         *
         * <p>The property map may contain pre-defined implementation
         * specific and default properties.  Users are encouraged to
         * read the information and fully understand the implications,
         * before modifying pre-existing properties.
         * <p>
         * Implementation specific properties are prefixed with a
         * package name associated with the implementor, beginning
         * with <tt>com.</tt> or a similar prefix.
         * All property names beginning with <tt>pack.</tt> and
         * <tt>unpack.</tt> are reserved for use by this API.
         * <p>
         * Unknown properties may be ignored or rejected with an
         * unspecified error, and invalid entries may cause an
         * unspecified error to be thrown.
         *
         * <p>
         *  获取此引擎的属性集。该集合是"实时视图",因此更改其内容会立即影响Packer引擎,并且来自引擎的更改(如进度指示)会立即在地图中显示。
         * 
         *  <p>属性映射可以包含预定义的实现特定和默认属性。鼓励用户在修改预先存在的属性之前阅读信息并完全理解其含义。
         * <p>
         * 实现特定属性以与实现者关联的包名称开头,以<tt> com。</tt>开头或类似的前缀。以<tt> pack。</tt>和<tt> unpack。</tt>开头的所有属性名称都保留供此API使用。
         * <p>
         *  未知属性可能会被忽略或使用未指定的错误拒绝,并且无效条目可能会导致抛出未指定的错误。
         * 
         * 
         * @return A sorted association of option key strings to option values.
         */
        SortedMap<String,String> properties();

        /**
         * Read a Pack200 archive, and write the encoded JAR to
         * a JarOutputStream.
         * The entire contents of the input stream will be read.
         * It may be more efficient to read the Pack200 archive
         * to a file and pass the File object, using the alternate
         * method described below.
         * <p>
         * Closes its input but not its output.  (The output can accumulate more elements.)
         * <p>
         *  读取Pack200存档,并将编码的JAR写入JarOutputStream。将读取输入流的全部内容。将Pack200归档文件读取到文件并传递File对象可能更有效,使用下面描述的替代方法。
         * <p>
         *  关闭其输入,但不关闭其输出。 (输出可累积更多元素。)
         * 
         * 
         * @param in an InputStream.
         * @param out a JarOutputStream.
         * @exception IOException if an error is encountered.
         */
        void unpack(InputStream in, JarOutputStream out) throws IOException;

        /**
         * Read a Pack200 archive, and write the encoded JAR to
         * a JarOutputStream.
         * <p>
         * Does not close its output.  (The output can accumulate more elements.)
         * <p>
         *  读取Pack200存档,并将编码的JAR写入JarOutputStream。
         * <p>
         *  不关闭其输出。 (输出可累积更多元素。)
         * 
         * 
         * @param in a File.
         * @param out a JarOutputStream.
         * @exception IOException if an error is encountered.
         */
        void unpack(File in, JarOutputStream out) throws IOException;

        /**
         * Registers a listener for PropertyChange events on the properties map.
         * This is typically used by applications to update a progress bar.
         *
         * <p> The default implementation of this method does nothing and has
         * no side-effects.</p>
         *
         * <p><b>WARNING:</b> This method is omitted from the interface
         * declaration in all subset Profiles of Java SE that do not include
         * the {@code java.beans} package. </p>
         *
         * <p>
         *  在属性映射上为PropertyChange事件注册侦听器。这通常由应用程序用于更新进度条。
         * 
         *  <p>此方法的默认实现无效,没有副作用。</p>
         * 
         *  <p> <b>警告：</b>此方法在Java SE的所有子集配置文件的接口声明中省略,不包括{@code java.beans}包。 </p>
         * 
         * 
         * @see #properties
         * @see #PROGRESS
         * @param listener  An object to be invoked when a property is changed.
         * @deprecated The dependency on {@code PropertyChangeListener} creates
         *             a significant impediment to future modularization of the
         *             Java platform. This method will be removed in a future
         *             release.
         *             Applications that need to monitor progress of the
         *             unpacker can poll the value of the {@link #PROGRESS
         *             PROGRESS} property instead.
         */
        @Deprecated
        default void addPropertyChangeListener(PropertyChangeListener listener) {
        }

        /**
         * Remove a listener for PropertyChange events, added by
         * the {@link #addPropertyChangeListener}.
         *
         * <p> The default implementation of this method does nothing and has
         * no side-effects.</p>
         *
         * <p><b>WARNING:</b> This method is omitted from the interface
         * declaration in all subset Profiles of Java SE that do not include
         * the {@code java.beans} package. </p>
         *
         * <p>
         *  删除由{@link #addPropertyChangeListener}添加的PropertyChange事件的侦听器。
         * 
         *  <p>此方法的默认实现无效,没有副作用。</p>
         * 
         * @see #addPropertyChangeListener
         * @param listener  The PropertyChange listener to be removed.
         * @deprecated The dependency on {@code PropertyChangeListener} creates
         *             a significant impediment to future modularization of the
         *             Java platform. This method will be removed in a future
         *             release.
         */
        @Deprecated
        default void removePropertyChangeListener(PropertyChangeListener listener) {
        }
    }

    // Private stuff....

    private static final String PACK_PROVIDER = "java.util.jar.Pack200.Packer";
    private static final String UNPACK_PROVIDER = "java.util.jar.Pack200.Unpacker";

    private static Class<?> packerImpl;
    private static Class<?> unpackerImpl;

    private synchronized static Object newInstance(String prop) {
        String implName = "(unknown)";
        try {
            Class<?> impl = (PACK_PROVIDER.equals(prop))? packerImpl: unpackerImpl;
            if (impl == null) {
                // The first time, we must decide which class to use.
                implName = java.security.AccessController.doPrivileged(
                    new sun.security.action.GetPropertyAction(prop,""));
                if (implName != null && !implName.equals(""))
                    impl = Class.forName(implName);
                else if (PACK_PROVIDER.equals(prop))
                    impl = com.sun.java.util.jar.pack.PackerImpl.class;
                else
                    impl = com.sun.java.util.jar.pack.UnpackerImpl.class;
            }
            // We have a class.  Now instantiate it.
            return impl.newInstance();
        } catch (ClassNotFoundException e) {
            throw new Error("Class not found: " + implName +
                                ":\ncheck property " + prop +
                                " in your properties file.", e);
        } catch (InstantiationException e) {
            throw new Error("Could not instantiate: " + implName +
                                ":\ncheck property " + prop +
                                " in your properties file.", e);
        } catch (IllegalAccessException e) {
            throw new Error("Cannot access class: " + implName +
                                ":\ncheck property " + prop +
                                " in your properties file.", e);
        }
    }

}
