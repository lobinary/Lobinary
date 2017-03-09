/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, Oracle and/or its affiliates. All rights reserved.
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

package javax.imageio;

import javax.imageio.metadata.IIOMetadata;

/**
 * An interface providing metadata transcoding capability.
 *
 * <p> Any image may be transcoded (written to a different format
 * than the one it was originally stored in) simply by performing
 * a read operation followed by a write operation.  However, loss
 * of data may occur in this process due to format differences.
 *
 * <p> In general, the best results will be achieved when
 * format-specific metadata objects can be created to encapsulate as
 * much information about the image and its associated metadata as
 * possible, in terms that are understood by the specific
 * <code>ImageWriter</code> used to perform the encoding.
 *
 * <p> An <code>ImageTranscoder</code> may be used to convert the
 * <code>IIOMetadata</code> objects supplied by the
 * <code>ImageReader</code> (representing per-stream and per-image
 * metadata) into corresponding objects suitable for encoding by a
 * particular <code>ImageWriter</code>.  In the case where the methods
 * of this interface are being called directly on an
 * <code>ImageWriter</code>, the output will be suitable for that
 * writer.
 *
 * <p> The internal details of converting an <code>IIOMetadata</code>
 * object into a writer-specific format will vary according to the
 * context of the operation.  Typically, an <code>ImageWriter</code>
 * will inspect the incoming object to see if it implements additional
 * interfaces with which the writer is familiar.  This might be the
 * case, for example, if the object was obtained by means of a read
 * operation on a reader plug-in written by the same vendor as the
 * writer.  In this case, the writer may access the incoming object by
 * means of its plug-in specific interfaces.  In this case, the
 * re-encoding may be close to lossless if the image file format is
 * kept constant.  If the format is changing, the writer may still
 * attempt to preserve as much information as possible.
 *
 * <p> If the incoming object does not implement any additional
 * interfaces known to the writer, the writer has no choice but to
 * access it via the standard <code>IIOMetadata</code> interfaces such
 * as the tree view provided by <code>IIOMetadata.getAsTree</code>.
 * In this case, there is likely to be significant loss of
 * information.
 *
 * <p> An independent <code>ImageTranscoder</code> essentially takes
 * on the same role as the writer plug-in in the above examples.  It
 * must be familiar with the private interfaces used by both the
 * reader and writer plug-ins, and manually instantiate an object that
 * will be usable by the writer.  The resulting metadata objects may
 * be used by the writer directly.
 *
 * <p> No independent implementations of <code>ImageTranscoder</code>
 * are provided as part of the standard API.  Instead, the intention
 * of this interface is to provide a way for implementations to be
 * created and discovered by applications as the need arises.
 *
 * <p>
 *  提供元数据代码转换功能的接口。
 * 
 *  <p>任何图像可以通过简单地执行读取操作,然后执行写入操作来转码(写入与其最初存储的格式不同的格式)。然而,由于格式差异,在此过程中可能发生数据丢失。
 * 
 *  通常,当可以创建格式特定的元数据对象时,将实现最好的结果,以便尽可能多地封装关于图像及其相关联的元数据的信息,特定的<code> ImageWriter </> code>用于执行编码。
 * 
 *  <p> <code> ImageTranscoder </code>可用于将由<code> ImageReader </code>(表示每个流和每个图像元数据)提供的<code> IIOMetadat
 * a </code>对象转换为适合于由特定<code> ImageWriter </code>进行编码的对应对象。
 * 在这种接口的方法直接在<code> ImageWriter </code>上调用的情况下,输出将适合于该写入器。
 * 
 * <p>将<code> IIOMetadata </code>对象转换为特定于写入程序的格式的内部细节将根据操作的上下文而有所不同。
 * 通常,<code> ImageWriter </code>会检查传入的对象,看看它是否实现了作者熟悉的附加接口。
 * 这可能是这种情况,例如,如果对象是通过对与作者相同的供应商编写的读取器插件进行读取操作而获得的。在这种情况下,写入者可以通过其插入特定接口访问传入对象。
 * 在这种情况下,如果图像文件格式保持恒定,则重新编码可以接近无损。如果格式正在改变,则写入者仍然可以尝试保留尽可能多的信息。
 * 
 *  <p>如果传入对象未实现写入器已知的任何其他接口,则写入器不得不通过标准<code> IIOMetadata </code>接口访问它,例如由<code> IIOMetadata提供的树视图.getA
 * sTree </code>。
 * 在这种情况下,可能会有重大的信息丢失。
 * 
 */
public interface ImageTranscoder {

    /**
     * Returns an <code>IIOMetadata</code> object that may be used for
     * encoding and optionally modified using its document interfaces
     * or other interfaces specific to the writer plug-in that will be
     * used for encoding.
     *
     * <p> An optional <code>ImageWriteParam</code> may be supplied
     * for cases where it may affect the structure of the stream
     * metadata.
     *
     * <p> If the supplied <code>ImageWriteParam</code> contains
     * optional setting values not understood by this writer or
     * transcoder, they will be ignored.
     *
     * <p>
     *  <p>独立的<code> ImageTranscoder </code>本质上与上面例子中的writer插件具有相同的作用。它必须熟悉读写器插件使用的私有接口,并且手动实例化一个可供写者使用的对象。
     * 所得到的元数据对象可以由写者直接使用。
     * 
     * <p>没有提供作为标准API的一部分的<code> ImageTranscoder </code>的独立实现。相反,这个接口的意图是为应用程序创建和发现的实现提供一种方法,当需要出现时。
     * 
     * 
     * @param inData an <code>IIOMetadata</code> object representing
     * stream metadata, used to initialize the state of the returned
     * object.
     * @param param an <code>ImageWriteParam</code> that will be used to
     * encode the image, or <code>null</code>.
     *
     * @return an <code>IIOMetadata</code> object, or
     * <code>null</code> if the plug-in does not provide metadata
     * encoding capabilities.
     *
     * @exception IllegalArgumentException if <code>inData</code> is
     * <code>null</code>.
     */
    IIOMetadata convertStreamMetadata(IIOMetadata inData,
                                      ImageWriteParam param);

    /**
     * Returns an <code>IIOMetadata</code> object that may be used for
     * encoding and optionally modified using its document interfaces
     * or other interfaces specific to the writer plug-in that will be
     * used for encoding.
     *
     * <p> An optional <code>ImageWriteParam</code> may be supplied
     * for cases where it may affect the structure of the image
     * metadata.
     *
     * <p> If the supplied <code>ImageWriteParam</code> contains
     * optional setting values not understood by this writer or
     * transcoder, they will be ignored.
     *
     * <p>
     *  返回可用于编码的<code> IIOMetadata </code>对象,并且可以使用其文档接口或其他将用于编码的编写器插件的接口进行修改。
     * 
     *  <p>可以为可能影响流元数据结构的情况提供可选的<code> ImageWriteParam </code>。
     * 
     *  <p>如果提供的<code> ImageWriteParam </code>包含此作者或代码转换器不能理解的可选设置值,则会被忽略。
     * 
     * 
     * @param inData an <code>IIOMetadata</code> object representing
     * image metadata, used to initialize the state of the returned
     * object.
     * @param imageType an <code>ImageTypeSpecifier</code> indicating
     * the layout and color information of the image with which the
     * metadata will be associated.
     * @param param an <code>ImageWriteParam</code> that will be used to
     * encode the image, or <code>null</code>.
     *
     * @return an <code>IIOMetadata</code> object,
     * or <code>null</code> if the plug-in does not provide
     * metadata encoding capabilities.
     *
     * @exception IllegalArgumentException if either of
     * <code>inData</code> or <code>imageType</code> is
     * <code>null</code>.
     */
    IIOMetadata convertImageMetadata(IIOMetadata inData,
                                     ImageTypeSpecifier imageType,
                                     ImageWriteParam param);
}
