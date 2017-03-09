/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2011, Oracle and/or its affiliates. All rights reserved.
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

/* ****************************************************************
 ******************************************************************
 ******************************************************************
 *** COPYRIGHT (c) Eastman Kodak Company, 1997
 *** As  an unpublished  work pursuant to Title 17 of the United
 *** States Code.  All rights reserved.
 ******************************************************************
 ******************************************************************
 * <p>
 *  **************************************************** ************** ********************************
 * **** **************************** * COPYRIGHT(c)Eastman Kodak Company,1997 *根据United Nations Title 17
 * 的未发表作品*国家代码。
 * 版权所有。
 *  **************************************************** ************** ********************************
 * **** ****************************。
 * 版权所有。
 * 
 * 
 ******************************************************************/

package java.awt.image;

/**
 *  This abstract class defines an interface for extracting samples of pixels
 *  in an image.  All image data is expressed as a collection of pixels.
 *  Each pixel consists of a number of samples. A sample is a datum
 *  for one band of an image and a band consists of all samples of a
 *  particular type in an image.  For example, a pixel might contain
 *  three samples representing its red, green and blue components.
 *  There are three bands in the image containing this pixel.  One band
 *  consists of all the red samples from all pixels in the
 *  image.  The second band consists of all the green samples and
 *  the remaining band consists of all of the blue samples.  The pixel
 *  can be stored in various formats.  For example, all samples from
 *  a particular band can be stored contiguously or all samples from a
 *  single pixel can be stored contiguously.
 *  <p>
 *  Subclasses of SampleModel specify the types of samples they can
 *  represent (e.g. unsigned 8-bit byte, signed 16-bit short, etc.)
 *  and may specify how the samples are organized in memory.
 *  In the Java 2D(tm) API, built-in image processing operators may
 *  not operate on all possible sample types, but generally will work
 *  for unsigned integral samples of 16 bits or less.  Some operators
 *  support a wider variety of sample types.
 *  <p>
 *  A collection of pixels is represented as a Raster, which consists of
 *  a DataBuffer and a SampleModel.  The SampleModel allows access to
 *  samples in the DataBuffer and may provide low-level information that
 *  a programmer can use to directly manipulate samples and pixels in the
 *  DataBuffer.
 *  <p>
 *  This class is generally a fall back method for dealing with
 *  images.  More efficient code will cast the SampleModel to the
 *  appropriate subclass and extract the information needed to directly
 *  manipulate pixels in the DataBuffer.
 *
 * <p>
 *  该抽象类定义了用于提取图像中的像素样本的接口。所有图像数据被表示为像素的集合。每个像素由多个样本组成。样本是图像的一个带的数据,并且带由图像中的特定类型的所有样本组成。
 * 例如,像素可以包含表示其红色,绿色和蓝色分量的三个样本。在包含该像素的图像中有三个带。一个频带由来自图像中所有像素的所有红色样本组成。第二频带由所有绿色样本组成,剩余频带由所有蓝色样本组成。
 * 像素可以以各种格式存储。例如,来自特定频带的所有样本可以连续存储,或者来自单个像素的所有样本可以连续存储。
 * <p>
 * SampleModel的子类指定它们可以表示的样本类型(例如,无符号8位字节,有符号16位短整型等),并且可以指定样本在存储器中的组织方式。
 * 在Java 2D(tm)API中,内置图像处理操作符可能不会对所有可能的采样类型进行操作,但通常会用于16位或更少的无符号整数采样。一些操作员支持更多种类的样品。
 * <p>
 *  像素集合表示为一个光栅,它由DataBuffer和SampleModel组成。
 *  SampleModel允许访问DataBuffer中的样本,并且可以提供低级信息,程序员可以使用它直接操作DataBuffer中的样本和像素。
 * <p>
 *  这个类通常是处理图像的后退方法。更高效的代码会将SampleModel转换为适当的子类,并提取直接操作DataBuffer中的像素所需的信息。
 * 
 * 
 *  @see java.awt.image.DataBuffer
 *  @see java.awt.image.Raster
 *  @see java.awt.image.ComponentSampleModel
 *  @see java.awt.image.PixelInterleavedSampleModel
 *  @see java.awt.image.BandedSampleModel
 *  @see java.awt.image.MultiPixelPackedSampleModel
 *  @see java.awt.image.SinglePixelPackedSampleModel
 */

public abstract class SampleModel
{

    /** Width in pixels of the region of image data that this SampleModel
     *  describes.
     * <p>
     *  描述。
     * 
     */
    protected int width;

    /** Height in pixels of the region of image data that this SampleModel
     *  describes.
     * <p>
     *  描述。
     * 
     */
    protected int height;

    /** Number of bands of the image data that this SampleModel describes. */
    protected int numBands;

    /** Data type of the DataBuffer storing the pixel data.
    /* <p>
    /* 
     *  @see java.awt.image.DataBuffer
     */
    protected int dataType;

    static private native void initIDs();
    static {
        ColorModel.loadLibraries();
        initIDs();
    }

    /**
     * Constructs a SampleModel with the specified parameters.
     * <p>
     *  构造具有指定参数的SampleModel。
     * 
     * 
     * @param dataType  The data type of the DataBuffer storing the pixel data.
     * @param w         The width (in pixels) of the region of image data.
     * @param h         The height (in pixels) of the region of image data.
     * @param numBands  The number of bands of the image data.
     * @throws IllegalArgumentException if <code>w</code> or <code>h</code>
     *         is not greater than 0
     * @throws IllegalArgumentException if the product of <code>w</code>
     *         and <code>h</code> is greater than
     *         <code>Integer.MAX_VALUE</code>
     * @throws IllegalArgumentException if <code>dataType</code> is not
     *         one of the supported data types
     */
    public SampleModel(int dataType, int w, int h, int numBands)
    {
        long size = (long)w * h;
        if (w <= 0 || h <= 0) {
            throw new IllegalArgumentException("Width ("+w+") and height ("+
                                               h+") must be > 0");
        }
        if (size >= Integer.MAX_VALUE) {
            throw new IllegalArgumentException("Dimensions (width="+w+
                                               " height="+h+") are too large");
        }

        if (dataType < DataBuffer.TYPE_BYTE ||
            (dataType > DataBuffer.TYPE_DOUBLE &&
             dataType != DataBuffer.TYPE_UNDEFINED))
        {
            throw new IllegalArgumentException("Unsupported dataType: "+
                                               dataType);
        }

        if (numBands <= 0) {
            throw new IllegalArgumentException("Number of bands must be > 0");
        }

        this.dataType = dataType;
        this.width = w;
        this.height = h;
        this.numBands = numBands;
    }

    /** Returns the width in pixels.
    /* <p>
    /* 
     *  @return the width in pixels of the region of image data
     *          that this <code>SampleModel</code> describes.
     */
    final public int getWidth() {
         return width;
    }

    /** Returns the height in pixels.
    /* <p>
    /* 
     *  @return the height in pixels of the region of image data
     *          that this <code>SampleModel</code> describes.
     */
    final public int getHeight() {
         return height;
    }

    /** Returns the total number of bands of image data.
    /* <p>
    /* 
     *  @return the number of bands of image data that this
     *          <code>SampleModel</code> describes.
     */
    final public int getNumBands() {
         return numBands;
    }

    /** Returns the number of data elements needed to transfer a pixel
     *  via the getDataElements and setDataElements methods.  When pixels
     *  are transferred via these methods, they may be transferred in a
     *  packed or unpacked format, depending on the implementation of the
     *  SampleModel.  Using these methods, pixels are transferred as an
     *  array of getNumDataElements() elements of a primitive type given
     *  by getTransferType().  The TransferType may or may not be the same
     *  as the storage DataType.
     * <p>
     *  通过getDataElements和setDataElements方法。当通过这些方法传送像素时,它们可以以打包或未打包格式传送,这取决于SampleModel的实现。
     * 使用这些方法,像素作为由getTransferType()给出的基元类型的getNumDataElements()元素的数组传输。 TransferType可以与存储DataType相同或不同。
     * 
     * 
     *  @return the number of data elements.
     *  @see #getDataElements(int, int, Object, DataBuffer)
     *  @see #getDataElements(int, int, int, int, Object, DataBuffer)
     *  @see #setDataElements(int, int, Object, DataBuffer)
     *  @see #setDataElements(int, int, int, int, Object, DataBuffer)
     *  @see #getTransferType
     */
    public abstract int getNumDataElements();

    /** Returns the data type of the DataBuffer storing the pixel data.
    /* <p>
    /* 
     *  @return the data type.
     */
    final public int getDataType() {
        return dataType;
    }

    /** Returns the TransferType used to transfer pixels via the
     *  getDataElements and setDataElements methods.  When pixels
     *  are transferred via these methods, they may be transferred in a
     *  packed or unpacked format, depending on the implementation of the
     *  SampleModel.  Using these methods, pixels are transferred as an
     *  array of getNumDataElements() elements of a primitive type given
     *  by getTransferType().  The TransferType may or may not be the same
     *  as the storage DataType.  The TransferType will be one of the types
     *  defined in DataBuffer.
     * <p>
     * getDataElements和setDataElements方法。当通过这些方法传送像素时,它们可以以打包或未打包格式传送,这取决于SampleModel的实现。
     * 使用这些方法,像素作为由getTransferType()给出的基元类型的getNumDataElements()元素的数组传输。 TransferType可以与存储DataType相同或不同。
     *  TransferType将是DataBuffer中定义的类型之一。
     * 
     * 
     *  @return the transfer type.
     *  @see #getDataElements(int, int, Object, DataBuffer)
     *  @see #getDataElements(int, int, int, int, Object, DataBuffer)
     *  @see #setDataElements(int, int, Object, DataBuffer)
     *  @see #setDataElements(int, int, int, int, Object, DataBuffer)
     *  @see #getNumDataElements
     *  @see java.awt.image.DataBuffer
     */
    public int getTransferType() {
        return dataType;
    }

    /**
     * Returns the samples for a specified pixel in an int array,
     * one sample per array element.
     * ArrayIndexOutOfBoundsException may be thrown if the coordinates are
     * not in bounds.
     * <p>
     *  返回int数组中指定像素的样本,每个数组元素一个样本。如果坐标不在边界中,则可能抛出ArrayIndexOutOfBoundsException。
     * 
     * 
     * @param x         The X coordinate of the pixel location
     * @param y         The Y coordinate of the pixel location
     * @param iArray    If non-null, returns the samples in this array
     * @param data      The DataBuffer containing the image data
     * @return the samples for the specified pixel.
     * @see #setPixel(int, int, int[], DataBuffer)
     *
     * @throws NullPointerException if data is null.
     * @throws ArrayIndexOutOfBoundsException if the coordinates are
     * not in bounds, or if iArray is too small to hold the output.
     */
    public int[] getPixel(int x, int y, int iArray[], DataBuffer data) {

        int pixels[];

        if (iArray != null)
            pixels = iArray;
        else
            pixels = new int[numBands];

        for (int i=0; i<numBands; i++) {
            pixels[i] = getSample(x, y, i, data);
        }

        return pixels;
    }

    /**
     * Returns data for a single pixel in a primitive array of type
     * TransferType.  For image data supported by the Java 2D API, this
     * will be one of DataBuffer.TYPE_BYTE, DataBuffer.TYPE_USHORT,
     * DataBuffer.TYPE_INT, DataBuffer.TYPE_SHORT, DataBuffer.TYPE_FLOAT,
     * or DataBuffer.TYPE_DOUBLE.  Data may be returned in a packed format,
     * thus increasing efficiency for data transfers. Generally, obj
     * should be passed in as null, so that the Object will be created
     * automatically and will be of the right primitive data type.
     * <p>
     * The following code illustrates transferring data for one pixel from
     * DataBuffer <code>db1</code>, whose storage layout is described by
     * SampleModel <code>sm1</code>, to DataBuffer <code>db2</code>, whose
     * storage layout is described by SampleModel <code>sm2</code>.
     * The transfer will generally be more efficient than using
     * getPixel/setPixel.
     * <pre>
     *       SampleModel sm1, sm2;
     *       DataBuffer db1, db2;
     *       sm2.setDataElements(x, y, sm1.getDataElements(x, y, null, db1), db2);
     * </pre>
     * Using getDataElements/setDataElements to transfer between two
     * DataBuffer/SampleModel pairs is legitimate if the SampleModels have
     * the same number of bands, corresponding bands have the same number of
     * bits per sample, and the TransferTypes are the same.
     * <p>
     * If obj is non-null, it should be a primitive array of type TransferType.
     * Otherwise, a ClassCastException is thrown.  An
     * ArrayIndexOutOfBoundsException may be thrown if the coordinates are
     * not in bounds, or if obj is non-null and is not large enough to hold
     * the pixel data.
     * <p>
     *  返回类型TransferType的基本数组中单个像素的数据。
     * 对于Java 2D API支持的图像数据,这将是DataBuffer.TYPE_BYTE,DataBuffer.TYPE_USHORT,DataBuffer.TYPE_INT,DataBuffer.TY
     * PE_SHORT,DataBuffer.TYPE_FLOAT或DataBuffer.TYPE_DOUBLE之一。
     *  返回类型TransferType的基本数组中单个像素的数据。可以以打包格式返回数据,从而提高数据传输的效率。通常,obj应该作为null传递,以便对象将被自动创建并且将是正确的基本数据类型。
     * <p>
     *  以下代码示出将来自DataBuffer <code> db1 </code>的一个像素的数据传送到DataBuffer <code> db2 </code>,其存储布局由SampleModel <code>
     *  sm1 </code>描述,由SampleModel <code> sm2 </code>描述。
     * 传输通常比使用getPixel / setPixel更有效。
     * <pre>
     * SampleModel sm1,sm2; DataBuffer db1,db2; sm2.setDataElements(x,y,sm1.getDataElements(x,y,null,db1),db
     * 2);。
     * </pre>
     *  如果SampleModel具有相同数量的波段,对应的波段具有每个样本相同的位数,并且TransferTypes相同,则使用getDataElements / setDataElements在两个Dat
     * aBuffer / SampleModel对之间进行传输是合法的。
     * <p>
     *  如果obj是非空的,它应该是TransferType类型的原始数组。否则,抛出ClassCastException。
     * 如果坐标不在边界中,或者如果obj非空并且不足以容纳像素数据,则可能抛出ArrayIndexOutOfBoundsException。
     * 
     * 
     * @param x         The X coordinate of the pixel location.
     * @param y         The Y coordinate of the pixel location.
     * @param obj       If non-null, a primitive array in which to return
     *                  the pixel data.
     * @param data      The DataBuffer containing the image data.
     * @return the data elements for the specified pixel.
     * @see #getNumDataElements
     * @see #getTransferType
     * @see java.awt.image.DataBuffer
     * @see #setDataElements(int, int, Object, DataBuffer)
     *
     * @throws NullPointerException if data is null.
     * @throws ArrayIndexOutOfBoundsException if the coordinates are
     * not in bounds, or if obj is too small to hold the output.
     */
    public abstract Object getDataElements(int x, int y,
                                           Object obj, DataBuffer data);

    /**
     * Returns the pixel data for the specified rectangle of pixels in a
     * primitive array of type TransferType.
     * For image data supported by the Java 2D API, this
     * will be one of DataBuffer.TYPE_BYTE, DataBuffer.TYPE_USHORT,
     * DataBuffer.TYPE_INT, DataBuffer.TYPE_SHORT, DataBuffer.TYPE_FLOAT,
     * or DataBuffer.TYPE_DOUBLE.  Data may be returned in a packed format,
     * thus increasing efficiency for data transfers. Generally, obj
     * should be passed in as null, so that the Object will be created
     * automatically and will be of the right primitive data type.
     * <p>
     * The following code illustrates transferring data for a rectangular
     * region of pixels from
     * DataBuffer <code>db1</code>, whose storage layout is described by
     * SampleModel <code>sm1</code>, to DataBuffer <code>db2</code>, whose
     * storage layout is described by SampleModel <code>sm2</code>.
     * The transfer will generally be more efficient than using
     * getPixels/setPixels.
     * <pre>
     *       SampleModel sm1, sm2;
     *       DataBuffer db1, db2;
     *       sm2.setDataElements(x, y, w, h, sm1.getDataElements(x, y, w,
     *                           h, null, db1), db2);
     * </pre>
     * Using getDataElements/setDataElements to transfer between two
     * DataBuffer/SampleModel pairs is legitimate if the SampleModels have
     * the same number of bands, corresponding bands have the same number of
     * bits per sample, and the TransferTypes are the same.
     * <p>
     * If obj is non-null, it should be a primitive array of type TransferType.
     * Otherwise, a ClassCastException is thrown.  An
     * ArrayIndexOutOfBoundsException may be thrown if the coordinates are
     * not in bounds, or if obj is non-null and is not large enough to hold
     * the pixel data.
     * <p>
     *  返回类型TransferType的基本数组中指定的像素矩形的像素数据。
     * 对于Java 2D API支持的图像数据,这将是DataBuffer.TYPE_BYTE,DataBuffer.TYPE_USHORT,DataBuffer.TYPE_INT,DataBuffer.TY
     * PE_SHORT,DataBuffer.TYPE_FLOAT或DataBuffer.TYPE_DOUBLE之一。
     *  返回类型TransferType的基本数组中指定的像素矩形的像素数据。可以以打包格式返回数据,从而提高数据传输的效率。
     * 通常,obj应该作为null传递,以便对象将被自动创建并且将是正确的基本数据类型。
     * <p>
     * 以下代码说明将一个矩形像素区域的数据从DataBuffer <code> db1 </code>传输到DataBuffer <code> db2 </code>,其存储布局由SampleModel <code>
     *  sm1 </code>其存储布局由SampleModel <code> sm2 </code>描述。
     * 传输通常比使用getPixels / setPixels更有效。
     * <pre>
     *  SampleModel sm1,sm2; DataBuffer db1,db2; sm2.setDataElements(x,y,w,h,sm1.getDataElements(x,y,w,h,nul
     * l,db1),db2);。
     * </pre>
     *  如果SampleModel具有相同数量的波段,对应的波段具有每个样本相同的位数,并且TransferTypes相同,则使用getDataElements / setDataElements在两个Dat
     * aBuffer / SampleModel对之间进行传输是合法的。
     * <p>
     *  如果obj是非空的,它应该是TransferType类型的原始数组。否则,抛出ClassCastException。
     * 如果坐标不在边界中,或者如果obj非空并且不足以容纳像素数据,则可能抛出ArrayIndexOutOfBoundsException。
     * 
     * 
     * @param x         The minimum X coordinate of the pixel rectangle.
     * @param y         The minimum Y coordinate of the pixel rectangle.
     * @param w         The width of the pixel rectangle.
     * @param h         The height of the pixel rectangle.
     * @param obj       If non-null, a primitive array in which to return
     *                  the pixel data.
     * @param data      The DataBuffer containing the image data.
     * @return the data elements for the specified region of pixels.
     * @see #getNumDataElements
     * @see #getTransferType
     * @see #setDataElements(int, int, int, int, Object, DataBuffer)
     * @see java.awt.image.DataBuffer
     *
     * @throws NullPointerException if data is null.
     * @throws ArrayIndexOutOfBoundsException if the coordinates are
     * not in bounds, or if obj is too small to hold the output.
     */
    public Object getDataElements(int x, int y, int w, int h,
                                  Object obj, DataBuffer data) {

        int type = getTransferType();
        int numDataElems = getNumDataElements();
        int cnt = 0;
        Object o = null;

        int x1 = x + w;
        int y1 = y + h;

        if (x < 0 || x >= width || w > width || x1 < 0 || x1 > width ||
            y < 0 || y >= height || h > height || y1 < 0 || y1 > height)
        {
            throw new ArrayIndexOutOfBoundsException("Invalid coordinates.");
        }

        switch(type) {

        case DataBuffer.TYPE_BYTE:

            byte[] btemp;
            byte[] bdata;

            if (obj == null)
                bdata = new byte[numDataElems*w*h];
            else
                bdata = (byte[])obj;

            for (int i=y; i<y1; i++) {
                for (int j=x; j<x1; j++) {
                    o = getDataElements(j, i, o, data);
                    btemp = (byte[])o;
                    for (int k=0; k<numDataElems; k++) {
                        bdata[cnt++] = btemp[k];
                    }
                }
            }
            obj = (Object)bdata;
            break;

        case DataBuffer.TYPE_USHORT:
        case DataBuffer.TYPE_SHORT:

            short[] sdata;
            short[] stemp;

            if (obj == null)
                sdata = new short[numDataElems*w*h];
            else
                sdata = (short[])obj;

            for (int i=y; i<y1; i++) {
                for (int j=x; j<x1; j++) {
                    o = getDataElements(j, i, o, data);
                    stemp = (short[])o;
                    for (int k=0; k<numDataElems; k++) {
                        sdata[cnt++] = stemp[k];
                    }
                }
            }

            obj = (Object)sdata;
            break;

        case DataBuffer.TYPE_INT:

            int[] idata;
            int[] itemp;

            if (obj == null)
                idata = new int[numDataElems*w*h];
            else
                idata = (int[])obj;

            for (int i=y; i<y1; i++) {
                for (int j=x; j<x1; j++) {
                    o = getDataElements(j, i, o, data);
                    itemp = (int[])o;
                    for (int k=0; k<numDataElems; k++) {
                        idata[cnt++] = itemp[k];
                    }
                }
            }

            obj = (Object)idata;
            break;

        case DataBuffer.TYPE_FLOAT:

            float[] fdata;
            float[] ftemp;

            if (obj == null)
                fdata = new float[numDataElems*w*h];
            else
                fdata = (float[])obj;

            for (int i=y; i<y1; i++) {
                for (int j=x; j<x1; j++) {
                    o = getDataElements(j, i, o, data);
                    ftemp = (float[])o;
                    for (int k=0; k<numDataElems; k++) {
                        fdata[cnt++] = ftemp[k];
                    }
                }
            }

            obj = (Object)fdata;
            break;

        case DataBuffer.TYPE_DOUBLE:

            double[] ddata;
            double[] dtemp;

            if (obj == null)
                ddata = new double[numDataElems*w*h];
            else
                ddata = (double[])obj;

            for (int i=y; i<y1; i++) {
                for (int j=x; j<x1; j++) {
                    o = getDataElements(j, i, o, data);
                    dtemp = (double[])o;
                    for (int k=0; k<numDataElems; k++) {
                        ddata[cnt++] = dtemp[k];
                    }
                }
            }

            obj = (Object)ddata;
            break;
        }

        return obj;
    }

    /**
     * Sets the data for a single pixel in the specified DataBuffer from a
     * primitive array of type TransferType.  For image data supported by
     * the Java 2D API, this will be one of DataBuffer.TYPE_BYTE,
     * DataBuffer.TYPE_USHORT, DataBuffer.TYPE_INT, DataBuffer.TYPE_SHORT,
     * DataBuffer.TYPE_FLOAT, or DataBuffer.TYPE_DOUBLE.  Data in the array
     * may be in a packed format, thus increasing efficiency for data
     * transfers.
     * <p>
     * The following code illustrates transferring data for one pixel from
     * DataBuffer <code>db1</code>, whose storage layout is described by
     * SampleModel <code>sm1</code>, to DataBuffer <code>db2</code>, whose
     * storage layout is described by SampleModel <code>sm2</code>.
     * The transfer will generally be more efficient than using
     * getPixel/setPixel.
     * <pre>
     *       SampleModel sm1, sm2;
     *       DataBuffer db1, db2;
     *       sm2.setDataElements(x, y, sm1.getDataElements(x, y, null, db1),
     *                           db2);
     * </pre>
     * Using getDataElements/setDataElements to transfer between two
     * DataBuffer/SampleModel pairs is legitimate if the SampleModels have
     * the same number of bands, corresponding bands have the same number of
     * bits per sample, and the TransferTypes are the same.
     * <p>
     * obj must be a primitive array of type TransferType.  Otherwise,
     * a ClassCastException is thrown.  An
     * ArrayIndexOutOfBoundsException may be thrown if the coordinates are
     * not in bounds, or if obj is not large enough to hold the pixel data.
     * <p>
     *  从TransferType类型的基本数组设置指定DataBuffer中单个像素的数据。
     * 对于Java 2D API支持的图像数据,这将是DataBuffer.TYPE_BYTE,DataBuffer.TYPE_USHORT,DataBuffer.TYPE_INT,DataBuffer.TY
     * PE_SHORT,DataBuffer.TYPE_FLOAT或DataBuffer.TYPE_DOUBLE之一。
     *  从TransferType类型的基本数组设置指定DataBuffer中单个像素的数据。阵列中的数据可以是打包格式,因此提高了数据传输的效率。
     * <p>
     * 以下代码示出将来自DataBuffer <code> db1 </code>的一个像素的数据传送到DataBuffer <code> db2 </code>,其存储布局由SampleModel <code>
     *  sm1 </code>描述,由SampleModel <code> sm2 </code>描述。
     * 传输通常比使用getPixel / setPixel更有效。
     * <pre>
     *  SampleModel sm1,sm2; DataBuffer db1,db2; sm2.setDataElements(x,y,sm1.getDataElements(x,y,null,db1),d
     * b2);。
     * </pre>
     *  如果SampleModel具有相同数量的波段,对应的波段具有每个样本相同的位数,并且TransferTypes相同,则使用getDataElements / setDataElements在两个Dat
     * aBuffer / SampleModel对之间进行传输是合法的。
     * <p>
     *  obj必须是TransferType类型的原始数组。否则,抛出ClassCastException。
     * 如果坐标不在边界中,或者如果obj不足够大以容纳像素数据,则可能抛出ArrayIndexOutOfBoundsException。
     * 
     * 
     * @param x         The X coordinate of the pixel location.
     * @param y         The Y coordinate of the pixel location.
     * @param obj       A primitive array containing pixel data.
     * @param data      The DataBuffer containing the image data.
     * @see #getNumDataElements
     * @see #getTransferType
     * @see #getDataElements(int, int, Object, DataBuffer)
     * @see java.awt.image.DataBuffer
     *
     * @throws NullPointerException if data is null.
     * @throws ArrayIndexOutOfBoundsException if the coordinates are
     * not in bounds, or if obj is too small to hold the input.
     */
    public abstract void setDataElements(int x, int y,
                                         Object obj, DataBuffer data);

    /**
     * Sets the data for a rectangle of pixels in the specified DataBuffer
     * from a primitive array of type TransferType.  For image data supported
     * by the Java 2D API, this will be one of DataBuffer.TYPE_BYTE,
     * DataBuffer.TYPE_USHORT, DataBuffer.TYPE_INT, DataBuffer.TYPE_SHORT,
     * DataBuffer.TYPE_FLOAT, or DataBuffer.TYPE_DOUBLE.  Data in the array
     * may be in a packed format, thus increasing efficiency for data
     * transfers.
     * <p>
     * The following code illustrates transferring data for a rectangular
     * region of pixels from
     * DataBuffer <code>db1</code>, whose storage layout is described by
     * SampleModel <code>sm1</code>, to DataBuffer <code>db2</code>, whose
     * storage layout is described by SampleModel <code>sm2</code>.
     * The transfer will generally be more efficient than using
     * getPixels/setPixels.
     * <pre>
     *       SampleModel sm1, sm2;
     *       DataBuffer db1, db2;
     *       sm2.setDataElements(x, y, w, h, sm1.getDataElements(x, y, w, h,
     *                           null, db1), db2);
     * </pre>
     * Using getDataElements/setDataElements to transfer between two
     * DataBuffer/SampleModel pairs is legitimate if the SampleModels have
     * the same number of bands, corresponding bands have the same number of
     * bits per sample, and the TransferTypes are the same.
     * <p>
     * obj must be a primitive array of type TransferType.  Otherwise,
     * a ClassCastException is thrown.  An
     * ArrayIndexOutOfBoundsException may be thrown if the coordinates are
     * not in bounds, or if obj is not large enough to hold the pixel data.
     * <p>
     *  从TransferType类型的基本数组中设置指定DataBuffer中一个像素矩形的数据。
     * 对于Java 2D API支持的图像数据,这将是DataBuffer.TYPE_BYTE,DataBuffer.TYPE_USHORT,DataBuffer.TYPE_INT,DataBuffer.TY
     * PE_SHORT,DataBuffer.TYPE_FLOAT或DataBuffer.TYPE_DOUBLE之一。
     *  从TransferType类型的基本数组中设置指定DataBuffer中一个像素矩形的数据。阵列中的数据可以是打包格式,因此提高了数据传输的效率。
     * <p>
     * 以下代码说明将一个矩形像素区域的数据从DataBuffer <code> db1 </code>传输到DataBuffer <code> db2 </code>,其存储布局由SampleModel <code>
     *  sm1 </code>其存储布局由SampleModel <code> sm2 </code>描述。
     * 传输通常比使用getPixels / setPixels更有效。
     * <pre>
     *  SampleModel sm1,sm2; DataBuffer db1,db2; sm2.setDataElements(x,y,w,h,sm1.getDataElements(x,y,w,h,nul
     * l,db1),db2);。
     * </pre>
     *  如果SampleModel具有相同数量的波段,对应的波段具有每个样本相同的位数,并且TransferTypes相同,则使用getDataElements / setDataElements在两个Dat
     * aBuffer / SampleModel对之间进行传输是合法的。
     * <p>
     *  obj必须是TransferType类型的原始数组。否则,抛出ClassCastException。
     * 如果坐标不在边界中,或者如果obj不足够大以容纳像素数据,则可能抛出ArrayIndexOutOfBoundsException。
     * 
     * 
     * @param x         The minimum X coordinate of the pixel rectangle.
     * @param y         The minimum Y coordinate of the pixel rectangle.
     * @param w         The width of the pixel rectangle.
     * @param h         The height of the pixel rectangle.
     * @param obj       A primitive array containing pixel data.
     * @param data      The DataBuffer containing the image data.
     * @see #getNumDataElements
     * @see #getTransferType
     * @see #getDataElements(int, int, int, int, Object, DataBuffer)
     * @see java.awt.image.DataBuffer
     *
     * @throws NullPointerException if data is null.
     * @throws ArrayIndexOutOfBoundsException if the coordinates are
     * not in bounds, or if obj is too small to hold the input.
     */
    public void setDataElements(int x, int y, int w, int h,
                                Object obj, DataBuffer data) {

        int cnt = 0;
        Object o = null;
        int type = getTransferType();
        int numDataElems = getNumDataElements();

        int x1 = x + w;
        int y1 = y + h;

        if (x < 0 || x >= width || w > width || x1 < 0 || x1 > width ||
            y < 0 || y >= height || h > height || y1 < 0 || y1 > height)
        {
            throw new ArrayIndexOutOfBoundsException("Invalid coordinates.");
        }

        switch(type) {

        case DataBuffer.TYPE_BYTE:

            byte[] barray = (byte[])obj;
            byte[] btemp = new byte[numDataElems];

            for (int i=y; i<y1; i++) {
                for (int j=x; j<x1; j++) {
                    for (int k=0; k<numDataElems; k++) {
                        btemp[k] = barray[cnt++];
                    }

                    setDataElements(j, i, btemp, data);
                }
            }
            break;

        case DataBuffer.TYPE_USHORT:
        case DataBuffer.TYPE_SHORT:

            short[] sarray = (short[])obj;
            short[] stemp = new short[numDataElems];

            for (int i=y; i<y1; i++) {
                for (int j=x; j<x1; j++) {
                    for (int k=0; k<numDataElems; k++) {
                        stemp[k] = sarray[cnt++];
                    }

                    setDataElements(j, i, stemp, data);
                }
            }
            break;

        case DataBuffer.TYPE_INT:

            int[] iArray = (int[])obj;
            int[] itemp = new int[numDataElems];

            for (int i=y; i<y1; i++) {
                for (int j=x; j<x1; j++) {
                    for (int k=0; k<numDataElems; k++) {
                        itemp[k] = iArray[cnt++];
                    }

                    setDataElements(j, i, itemp, data);
                }
            }
            break;

        case DataBuffer.TYPE_FLOAT:

            float[] fArray = (float[])obj;
            float[] ftemp = new float[numDataElems];

            for (int i=y; i<y1; i++) {
                for (int j=x; j<x1; j++) {
                    for (int k=0; k<numDataElems; k++) {
                        ftemp[k] = fArray[cnt++];
                    }

                    setDataElements(j, i, ftemp, data);
                }
            }
            break;

        case DataBuffer.TYPE_DOUBLE:

            double[] dArray = (double[])obj;
            double[] dtemp = new double[numDataElems];

            for (int i=y; i<y1; i++) {
                for (int j=x; j<x1; j++) {
                    for (int k=0; k<numDataElems; k++) {
                        dtemp[k] = dArray[cnt++];
                    }

                    setDataElements(j, i, dtemp, data);
                }
            }
            break;
        }

    }

    /**
     * Returns the samples for the specified pixel in an array of float.
     * ArrayIndexOutOfBoundsException may be thrown if the coordinates are
     * not in bounds.
     * <p>
     *  返回float数组中指定像素的样本。如果坐标不在边界中,则可能抛出ArrayIndexOutOfBoundsException。
     * 
     * 
     * @param x         The X coordinate of the pixel location.
     * @param y         The Y coordinate of the pixel location.
     * @param fArray    If non-null, returns the samples in this array.
     * @param data      The DataBuffer containing the image data.
     * @return the samples for the specified pixel.
     * @see #setPixel(int, int, float[], DataBuffer)
     *
     * @throws NullPointerException if data is null.
     * @throws ArrayIndexOutOfBoundsException if the coordinates are
     * not in bounds, or if fArray is too small to hold the output.
     */
    public float[] getPixel(int x, int y, float fArray[],
                            DataBuffer data) {

        float pixels[];

        if (fArray != null)
            pixels = fArray;
        else
            pixels = new float[numBands];

        for (int i=0; i<numBands; i++)
            pixels[i] = getSampleFloat(x, y, i, data);

        return pixels;
    }

    /**
     * Returns the samples for the specified pixel in an array of double.
     * ArrayIndexOutOfBoundsException may be thrown if the coordinates are
     * not in bounds.
     * <p>
     *  返回double数组中指定像素的样本。如果坐标不在边界中,则可能抛出ArrayIndexOutOfBoundsException。
     * 
     * 
     * @param x         The X coordinate of the pixel location.
     * @param y         The Y coordinate of the pixel location.
     * @param dArray    If non-null, returns the samples in this array.
     * @param data      The DataBuffer containing the image data.
     * @return the samples for the specified pixel.
     * @see #setPixel(int, int, double[], DataBuffer)
     *
     * @throws NullPointerException if data is null.
     * @throws ArrayIndexOutOfBoundsException if the coordinates are
     * not in bounds, or if dArray is too small to hold the output.
     */
    public double[] getPixel(int x, int y, double dArray[],
                             DataBuffer data) {

        double pixels[];

        if(dArray != null)
            pixels = dArray;
        else
            pixels = new double[numBands];

        for (int i=0; i<numBands; i++)
            pixels[i] = getSampleDouble(x, y, i, data);

        return pixels;
    }

    /**
     * Returns all samples for a rectangle of pixels in an
     * int array, one sample per array element.
     * ArrayIndexOutOfBoundsException may be thrown if the coordinates are
     * not in bounds.
     * <p>
     *  返回int数组中一个像素矩形的所有样本,每个数组元素一个样本。如果坐标不在边界中,则可能抛出ArrayIndexOutOfBoundsException。
     * 
     * 
     * @param x         The X coordinate of the upper left pixel location.
     * @param y         The Y coordinate of the upper left pixel location.
     * @param w         The width of the pixel rectangle.
     * @param h         The height of the pixel rectangle.
     * @param iArray    If non-null, returns the samples in this array.
     * @param data      The DataBuffer containing the image data.
     * @return the samples for the specified region of pixels.
     * @see #setPixels(int, int, int, int, int[], DataBuffer)
     *
     * @throws NullPointerException if data is null.
     * @throws ArrayIndexOutOfBoundsException if the coordinates are
     * not in bounds, or if iArray is too small to hold the output.
     */
    public int[] getPixels(int x, int y, int w, int h,
                           int iArray[], DataBuffer data) {

        int pixels[];
        int Offset=0;
        int x1 = x + w;
        int y1 = y + h;

        if (x < 0 || x >= width || w > width || x1 < 0 || x1 > width ||
            y < 0 || y >= height || h > height || y1 < 0 || y1 > height)
        {
            throw new ArrayIndexOutOfBoundsException("Invalid coordinates.");
        }

        if (iArray != null)
            pixels = iArray;
        else
            pixels = new int[numBands * w * h];

        for (int i=y; i<y1; i++) {
            for (int j=x; j<x1; j++) {
                for(int k=0; k<numBands; k++) {
                    pixels[Offset++] = getSample(j, i, k, data);
                }
            }
        }

        return pixels;
    }

    /**
     * Returns all samples for a rectangle of pixels in a float
     * array, one sample per array element.
     * ArrayIndexOutOfBoundsException may be thrown if the coordinates are
     * not in bounds.
     * <p>
     * 返回float数组中一个像素矩形的所有样本,每个数组元素一个样本。如果坐标不在边界中,则可能抛出ArrayIndexOutOfBoundsException。
     * 
     * 
     * @param x         The X coordinate of the upper left pixel location.
     * @param y         The Y coordinate of the upper left pixel location.
     * @param w         The width of the pixel rectangle.
     * @param h         The height of the pixel rectangle.
     * @param fArray    If non-null, returns the samples in this array.
     * @param data      The DataBuffer containing the image data.
     * @return the samples for the specified region of pixels.
     * @see #setPixels(int, int, int, int, float[], DataBuffer)
     *
     * @throws NullPointerException if data is null.
     * @throws ArrayIndexOutOfBoundsException if the coordinates are
     * not in bounds, or if fArray is too small to hold the output.
     */
    public float[] getPixels(int x, int y, int w, int h,
                             float fArray[], DataBuffer data) {

        float pixels[];
        int Offset = 0;
        int x1 = x + w;
        int y1 = y + h;

        if (x < 0 || x >= width || w > width || x1 < 0 || x1 > width ||
            y < 0 || y >= height || h > height || y1 < 0 || y1 > height)
        {
            throw new ArrayIndexOutOfBoundsException("Invalid coordinates.");
        }

        if (fArray != null)
            pixels = fArray;
        else
            pixels = new float[numBands * w * h];

        for (int i=y; i<y1; i++) {
            for(int j=x; j<x1; j++) {
                for(int k=0; k<numBands; k++) {
                    pixels[Offset++] = getSampleFloat(j, i, k, data);
                }
            }
        }

        return pixels;
    }

    /**
     * Returns all samples for a rectangle of pixels in a double
     * array, one sample per array element.
     * ArrayIndexOutOfBoundsException may be thrown if the coordinates are
     * not in bounds.
     * <p>
     *  返回双阵列中的一个像素矩形的所有样本,每个数组元素一个样本。如果坐标不在边界中,则可能抛出ArrayIndexOutOfBoundsException。
     * 
     * 
     * @param x         The X coordinate of the upper left pixel location.
     * @param y         The Y coordinate of the upper left pixel location.
     * @param w         The width of the pixel rectangle.
     * @param h         The height of the pixel rectangle.
     * @param dArray    If non-null, returns the samples in this array.
     * @param data      The DataBuffer containing the image data.
     * @return the samples for the specified region of pixels.
     * @see #setPixels(int, int, int, int, double[], DataBuffer)
     *
     * @throws NullPointerException if data is null.
     * @throws ArrayIndexOutOfBoundsException if the coordinates are
     * not in bounds, or if dArray is too small to hold the output.
     */
    public double[] getPixels(int x, int y, int w, int h,
                              double dArray[], DataBuffer data) {
        double pixels[];
        int    Offset = 0;
        int x1 = x + w;
        int y1 = y + h;

        if (x < 0 || x >= width || w > width || x1 < 0 || x1 > width ||
            y < 0 || y >= height || h > height || y1 < 0 || y1 > height)
        {
            throw new ArrayIndexOutOfBoundsException("Invalid coordinates.");
        }

        if (dArray != null)
            pixels = dArray;
        else
            pixels = new double[numBands * w * h];

        // Fix 4217412
        for (int i=y; i<y1; i++) {
            for (int j=x; j<x1; j++) {
                for (int k=0; k<numBands; k++) {
                    pixels[Offset++] = getSampleDouble(j, i, k, data);
                }
            }
        }

        return pixels;
    }


    /**
     * Returns the sample in a specified band for the pixel located
     * at (x,y) as an int.
     * ArrayIndexOutOfBoundsException may be thrown if the coordinates are
     * not in bounds.
     * <p>
     *  以位于(x,y)的像素作为int返回指定带中的样本。如果坐标不在边界中,则可能抛出ArrayIndexOutOfBoundsException。
     * 
     * 
     * @param x         The X coordinate of the pixel location.
     * @param y         The Y coordinate of the pixel location.
     * @param b         The band to return.
     * @param data      The DataBuffer containing the image data.
     * @return the sample in a specified band for the specified pixel.
     * @see #setSample(int, int, int, int, DataBuffer)
     *
     * @throws NullPointerException if data is null.
     * @throws ArrayIndexOutOfBoundsException if the coordinates or
     * the band index are not in bounds.
     */
    public abstract int getSample(int x, int y, int b, DataBuffer data);


    /**
     * Returns the sample in a specified band
     * for the pixel located at (x,y) as a float.
     * ArrayIndexOutOfBoundsException may be thrown if the coordinates are
     * not in bounds.
     * <p>
     *  以位于(x,y)的像素作为浮点返回指定带中的样本。如果坐标不在边界中,则可能抛出ArrayIndexOutOfBoundsException。
     * 
     * 
     * @param x         The X coordinate of the pixel location.
     * @param y         The Y coordinate of the pixel location.
     * @param b         The band to return.
     * @param data      The DataBuffer containing the image data.
     * @return the sample in a specified band for the specified pixel.
     *
     * @throws NullPointerException if data is null.
     * @throws ArrayIndexOutOfBoundsException if the coordinates or
     * the band index are not in bounds.
     */
    public float getSampleFloat(int x, int y, int b, DataBuffer data) {

        float sample;
        sample = (float) getSample(x, y, b, data);
        return sample;
    }

    /**
     * Returns the sample in a specified band
     * for a pixel located at (x,y) as a double.
     * ArrayIndexOutOfBoundsException may be thrown if the coordinates are
     * not in bounds.
     * <p>
     *  将位于(x,y)的像素的指定频带中的样本返回为double。如果坐标不在边界中,则可能抛出ArrayIndexOutOfBoundsException。
     * 
     * 
     * @param x         The X coordinate of the pixel location.
     * @param y         The Y coordinate of the pixel location.
     * @param b         The band to return.
     * @param data      The DataBuffer containing the image data.
     * @return the sample in a specified band for the specified pixel.
     *
     * @throws NullPointerException if data is null.
     * @throws ArrayIndexOutOfBoundsException if the coordinates or
     * the band index are not in bounds.
     */
    public double getSampleDouble(int x, int y, int b, DataBuffer data) {

        double sample;

        sample = (double) getSample(x, y, b, data);
        return sample;
    }

    /**
     * Returns the samples for a specified band for the specified rectangle
     * of pixels in an int array, one sample per array element.
     * ArrayIndexOutOfBoundsException may be thrown if the coordinates are
     * not in bounds.
     * <p>
     *  返回int阵列中指定像素矩形的指定带的样本,每个数组元素一个样本。如果坐标不在边界中,则可能抛出ArrayIndexOutOfBoundsException。
     * 
     * 
     * @param x         The X coordinate of the upper left pixel location.
     * @param y         The Y coordinate of the upper left pixel location.
     * @param w         The width of the pixel rectangle.
     * @param h         The height of the pixel rectangle.
     * @param b         The band to return.
     * @param iArray    If non-null, returns the samples in this array.
     * @param data      The DataBuffer containing the image data.
     * @return the samples for the specified band for the specified region
     *         of pixels.
     * @see #setSamples(int, int, int, int, int, int[], DataBuffer)
     *
     * @throws NullPointerException if data is null.
     * @throws ArrayIndexOutOfBoundsException if the coordinates or
     * the band index are not in bounds, or if iArray is too small to
     * hold the output.
     */
    public int[] getSamples(int x, int y, int w, int h, int b,
                            int iArray[], DataBuffer data) {
        int pixels[];
        int Offset=0;
        int x1 = x + w;
        int y1 = y + h;

        if (x < 0 || x1 < x || x1 > width ||
            y < 0 || y1 < y || y1 > height)
        {
            throw new ArrayIndexOutOfBoundsException("Invalid coordinates.");
        }

        if (iArray != null)
            pixels = iArray;
        else
            pixels = new int[w * h];

        for(int i=y; i<y1; i++) {
            for (int j=x; j<x1; j++) {
                pixels[Offset++] = getSample(j, i, b, data);
            }
        }

        return pixels;
    }

    /**
     * Returns the samples for a specified band for the specified rectangle
     * of pixels in a float array, one sample per array element.
     * ArrayIndexOutOfBoundsException may be thrown if the coordinates are
     * not in bounds.
     * <p>
     *  返回float数组中指定像素矩形的指定带的样本,每个数组元素一个样本。如果坐标不在边界中,则可能抛出ArrayIndexOutOfBoundsException。
     * 
     * 
     * @param x         The X coordinate of the upper left pixel location.
     * @param y         The Y coordinate of the upper left pixel location.
     * @param w         The width of the pixel rectangle.
     * @param h         The height of the pixel rectangle.
     * @param b         The band to return.
     * @param fArray    If non-null, returns the samples in this array.
     * @param data      The DataBuffer containing the image data.
     * @return the samples for the specified band for the specified region
     *         of pixels.
     * @see #setSamples(int, int, int, int, int, float[], DataBuffer)
     *
     * @throws NullPointerException if data is null.
     * @throws ArrayIndexOutOfBoundsException if the coordinates or
     * the band index are not in bounds, or if fArray is too small to
     * hold the output.
     */
    public float[] getSamples(int x, int y, int w, int h,
                              int b, float fArray[],
                              DataBuffer data) {
        float pixels[];
        int   Offset=0;
        int x1 = x + w;
        int y1 = y + h;

        if (x < 0 || x1 < x || x1 > width ||
            y < 0 || y1 < y || y1 > height)
        {
            throw new ArrayIndexOutOfBoundsException("Invalid coordinates");
        }

        if (fArray != null)
            pixels = fArray;
        else
            pixels = new float[w * h];

        for (int i=y; i<y1; i++) {
            for (int j=x; j<x1; j++) {
                pixels[Offset++] = getSampleFloat(j, i, b, data);
            }
        }

        return pixels;
    }

    /**
     * Returns the samples for a specified band for a specified rectangle
     * of pixels in a double array, one sample per array element.
     * ArrayIndexOutOfBoundsException may be thrown if the coordinates are
     * not in bounds.
     * <p>
     *  返回双阵列中指定像素矩形的指定带的样本,每个数组元素一个样本。如果坐标不在边界中,则可能抛出ArrayIndexOutOfBoundsException。
     * 
     * 
     * @param x         The X coordinate of the upper left pixel location.
     * @param y         The Y coordinate of the upper left pixel location.
     * @param w         The width of the pixel rectangle.
     * @param h         The height of the pixel rectangle.
     * @param b         The band to return.
     * @param dArray    If non-null, returns the samples in this array.
     * @param data      The DataBuffer containing the image data.
     * @return the samples for the specified band for the specified region
     *         of pixels.
     * @see #setSamples(int, int, int, int, int, double[], DataBuffer)
     *
     * @throws NullPointerException if data is null.
     * @throws ArrayIndexOutOfBoundsException if the coordinates or
     * the band index are not in bounds, or if dArray is too small to
     * hold the output.
     */
    public double[] getSamples(int x, int y, int w, int h,
                               int b, double dArray[],
                               DataBuffer data) {
        double pixels[];
        int    Offset=0;
        int x1 = x + w;
        int y1 = y + h;

        if (x < 0 || x1 < x || x1 > width ||
            y < 0 || y1 < y || y1 > height)
        {
            throw new ArrayIndexOutOfBoundsException("Invalid coordinates");
        }

        if (dArray != null)
            pixels = dArray;
        else
            pixels = new double[w * h];

        for (int i=y; i<y1; i++) {
            for (int j=x; j<x1; j++) {
                pixels[Offset++] = getSampleDouble(j, i, b, data);
            }
        }

        return pixels;
    }

    /**
     * Sets a pixel in  the DataBuffer using an int array of samples for input.
     * ArrayIndexOutOfBoundsException may be thrown if the coordinates are
     * not in bounds.
     * <p>
     * 设置DataBuffer中的一个像素,使用int数组样本进行输入。如果坐标不在边界中,则可能抛出ArrayIndexOutOfBoundsException。
     * 
     * 
     * @param x         The X coordinate of the pixel location.
     * @param y         The Y coordinate of the pixel location.
     * @param iArray    The input samples in an int array.
     * @param data      The DataBuffer containing the image data.
     * @see #getPixel(int, int, int[], DataBuffer)
     *
     * @throws NullPointerException if iArray or data is null.
     * @throws ArrayIndexOutOfBoundsException if the coordinates are
     * not in bounds, or if iArray is too small to hold the input.
     */
    public void setPixel(int x, int y, int iArray[], DataBuffer data) {

        for (int i=0; i<numBands; i++)
            setSample(x, y, i, iArray[i], data);
    }

    /**
     * Sets a pixel in the DataBuffer using a float array of samples for input.
     * ArrayIndexOutOfBoundsException may be thrown if the coordinates are
     * not in bounds.
     * <p>
     *  在DataBuffer中使用浮点数组来设置输入的像素。如果坐标不在边界中,则可能抛出ArrayIndexOutOfBoundsException。
     * 
     * 
     * @param x         The X coordinate of the pixel location.
     * @param y         The Y coordinate of the pixel location.
     * @param fArray    The input samples in a float array.
     * @param data      The DataBuffer containing the image data.
     * @see #getPixel(int, int, float[], DataBuffer)
     *
     * @throws NullPointerException if fArray or data is null.
     * @throws ArrayIndexOutOfBoundsException if the coordinates are
     * not in bounds, or if fArray is too small to hold the input.
     */
    public void setPixel(int x, int y, float fArray[], DataBuffer data) {

        for (int i=0; i<numBands; i++)
            setSample(x, y, i, fArray[i], data);
    }

    /**
     * Sets a pixel in the DataBuffer using a double array of samples
     * for input.
     * <p>
     *  使用双数组样本在DataBuffer中设置像素以进行输入。
     * 
     * 
     * @param x         The X coordinate of the pixel location.
     * @param y         The Y coordinate of the pixel location.
     * @param dArray    The input samples in a double array.
     * @param data      The DataBuffer containing the image data.
     * @see #getPixel(int, int, double[], DataBuffer)
     *
     * @throws NullPointerException if dArray or data is null.
     * @throws ArrayIndexOutOfBoundsException if the coordinates are
     * not in bounds, or if fArray is too small to hold the input.
     */
    public void setPixel(int x, int y, double dArray[], DataBuffer data) {

        for (int i=0; i<numBands; i++)
            setSample(x, y, i, dArray[i], data);
    }

    /**
     * Sets all samples for a rectangle of pixels from an int array containing
     * one sample per array element.
     * ArrayIndexOutOfBoundsException may be thrown if the coordinates are
     * not in bounds.
     * <p>
     *  为包含每个数组元素一个样本的int数组设置像素矩形的所有样本。如果坐标不在边界中,则可能抛出ArrayIndexOutOfBoundsException。
     * 
     * 
     * @param x         The X coordinate of the upper left pixel location.
     * @param y         The Y coordinate of the upper left pixel location.
     * @param w         The width of the pixel rectangle.
     * @param h         The height of the pixel rectangle.
     * @param iArray    The input samples in an int array.
     * @param data      The DataBuffer containing the image data.
     * @see #getPixels(int, int, int, int, int[], DataBuffer)
     *
     * @throws NullPointerException if iArray or data is null.
     * @throws ArrayIndexOutOfBoundsException if the coordinates are
     * not in bounds, or if iArray is too small to hold the input.
     */
    public void setPixels(int x, int y, int w, int h,
                          int iArray[], DataBuffer data) {
        int Offset=0;
        int x1 = x + w;
        int y1 = y + h;

        if (x < 0 || x >= width || w > width || x1 < 0 || x1 > width ||
            y < 0 || y >= height || h > height || y1 < 0 || y1 > height)
        {
            throw new ArrayIndexOutOfBoundsException("Invalid coordinates.");
        }

        for (int i=y; i<y1; i++) {
            for (int j=x; j<x1; j++) {
                for (int k=0; k<numBands; k++) {
                    setSample(j, i, k, iArray[Offset++], data);
                }
            }
        }
    }

    /**
     * Sets all samples for a rectangle of pixels from a float array containing
     * one sample per array element.
     * ArrayIndexOutOfBoundsException may be thrown if the coordinates are
     * not in bounds.
     * <p>
     *  为包含每个数组元素一个样本的float数组设置像素矩形的所有样本。如果坐标不在边界中,则可能抛出ArrayIndexOutOfBoundsException。
     * 
     * 
     * @param x         The X coordinate of the upper left pixel location.
     * @param y         The Y coordinate of the upper left pixel location.
     * @param w         The width of the pixel rectangle.
     * @param h         The height of the pixel rectangle.
     * @param fArray    The input samples in a float array.
     * @param data      The DataBuffer containing the image data.
     * @see #getPixels(int, int, int, int, float[], DataBuffer)
     *
     * @throws NullPointerException if fArray or data is null.
     * @throws ArrayIndexOutOfBoundsException if the coordinates are
     * not in bounds, or if fArray is too small to hold the input.
     */
    public void setPixels(int x, int y, int w, int h,
                          float fArray[], DataBuffer data) {
        int Offset=0;
        int x1 = x + w;
        int y1 = y + h;

        if (x < 0 || x >= width || w > width || x1 < 0 || x1 > width||
            y < 0 || y >= height || h > height || y1 < 0 || y1 > height)
        {
            throw new ArrayIndexOutOfBoundsException("Invalid coordinates.");
        }

        for (int i=y; i<y1; i++) {
            for (int j=x; j<x1; j++) {
                for(int k=0; k<numBands; k++) {
                    setSample(j, i, k, fArray[Offset++], data);
                }
            }
        }
    }

    /**
     * Sets all samples for a rectangle of pixels from a double array
     * containing one sample per array element.
     * ArrayIndexOutOfBoundsException may be thrown if the coordinates are
     * not in bounds.
     * <p>
     *  为包含每个数组元素一个样本的双阵列设置像素矩形的所有样本。如果坐标不在边界中,则可能抛出ArrayIndexOutOfBoundsException。
     * 
     * 
     * @param x         The X coordinate of the upper left pixel location.
     * @param y         The Y coordinate of the upper left pixel location.
     * @param w         The width of the pixel rectangle.
     * @param h         The height of the pixel rectangle.
     * @param dArray    The input samples in a double array.
     * @param data      The DataBuffer containing the image data.
     * @see #getPixels(int, int, int, int, double[], DataBuffer)
     *
     * @throws NullPointerException if dArray or data is null.
     * @throws ArrayIndexOutOfBoundsException if the coordinates are
     * not in bounds, or if dArray is too small to hold the input.
     */
    public void setPixels(int x, int y, int w, int h,
                          double dArray[], DataBuffer data) {
        int Offset=0;
        int x1 = x + w;
        int y1 = y + h;

        if (x < 0 || x >= width || w > width || x1 < 0 || x1 > width ||
            y < 0 || y >= height || h > height || y1 < 0 || y1 > height)
        {
            throw new ArrayIndexOutOfBoundsException("Invalid coordinates.");
        }

        for (int i=y; i<y1; i++) {
            for (int j=x; j<x1; j++) {
                for (int k=0; k<numBands; k++) {
                    setSample(j, i, k, dArray[Offset++], data);
                }
            }
        }
    }

    /**
     * Sets a sample in the specified band for the pixel located at (x,y)
     * in the DataBuffer using an int for input.
     * ArrayIndexOutOfBoundsException may be thrown if the coordinates are
     * not in bounds.
     * <p>
     *  使用int作为输入,为DataBuffer中位于(x,y)的像素设置指定频带中的样本。如果坐标不在边界中,则可能抛出ArrayIndexOutOfBoundsException。
     * 
     * 
     * @param x         The X coordinate of the pixel location.
     * @param y         The Y coordinate of the pixel location.
     * @param b         The band to set.
     * @param s         The input sample as an int.
     * @param data      The DataBuffer containing the image data.
     * @see #getSample(int, int, int,  DataBuffer)
     *
     * @throws NullPointerException if data is null.
     * @throws ArrayIndexOutOfBoundsException if the coordinates or
     * the band index are not in bounds.
     */
    public abstract void setSample(int x, int y, int b,
                                   int s,
                                   DataBuffer data);

    /**
     * Sets a sample in the specified band for the pixel located at (x,y)
     * in the DataBuffer using a float for input.
     * The default implementation of this method casts the input
     * float sample to an int and then calls the
     * <code>setSample(int, int, int, DataBuffer)</code> method using
     * that int value.
     * ArrayIndexOutOfBoundsException may be thrown if the coordinates are
     * not in bounds.
     * <p>
     * 使用float作为输入,为DataBuffer中位于(x,y)的像素设置指定频带中的样本。
     * 该方法的默认实现将输入float sample转换为int,然后使用该int值调用<code> setSample(int,int,int,DataBuffer)</code>方法。
     * 如果坐标不在边界中,则可能抛出ArrayIndexOutOfBoundsException。
     * 
     * 
     * @param x         The X coordinate of the pixel location.
     * @param y         The Y coordinate of the pixel location.
     * @param b         The band to set.
     * @param s         The input sample as a float.
     * @param data      The DataBuffer containing the image data.
     * @see #getSample(int, int, int, DataBuffer)
     *
     * @throws NullPointerException if data is null.
     * @throws ArrayIndexOutOfBoundsException if the coordinates or
     * the band index are not in bounds.
     */
    public void setSample(int x, int y, int b,
                          float s ,
                          DataBuffer data) {
        int sample = (int)s;

        setSample(x, y, b, sample, data);
    }

    /**
     * Sets a sample in the specified band for the pixel located at (x,y)
     * in the DataBuffer using a double for input.
     * The default implementation of this method casts the input
     * double sample to an int and then calls the
     * <code>setSample(int, int, int, DataBuffer)</code> method using
     * that int value.
     * ArrayIndexOutOfBoundsException may be thrown if the coordinates are
     * not in bounds.
     * <p>
     *  在指定的频带中为位于(x,y)的像素在DataBuffer中使用双精度输入设置样本。
     * 该方法的默认实现将输入double样本转换为int,然后使用该int值调用<code> setSample(int,int,int,DataBuffer)</code>方法。
     * 如果坐标不在边界中,则可能抛出ArrayIndexOutOfBoundsException。
     * 
     * 
     * @param x         The X coordinate of the pixel location.
     * @param y         The Y coordinate of the pixel location.
     * @param b         The band to set.
     * @param s         The input sample as a double.
     * @param data      The DataBuffer containing the image data.
     * @see #getSample(int, int, int, DataBuffer)
     *
     * @throws NullPointerException if data is null.
     * @throws ArrayIndexOutOfBoundsException if the coordinates or
     * the band index are not in bounds.
     */
    public void setSample(int x, int y, int b,
                          double s,
                          DataBuffer data) {
        int sample = (int)s;

        setSample(x, y, b, sample, data);
    }

    /**
     * Sets the samples in the specified band for the specified rectangle
     * of pixels from an int array containing one sample per array element.
     * ArrayIndexOutOfBoundsException may be thrown if the coordinates are
     * not in bounds.
     * <p>
     *  对于指定的像素矩形,从包含每个数组元素一个样本的int数组中设置指定带中的样本。如果坐标不在边界中,则可能抛出ArrayIndexOutOfBoundsException。
     * 
     * 
     * @param x         The X coordinate of the upper left pixel location.
     * @param y         The Y coordinate of the upper left pixel location.
     * @param w         The width of the pixel rectangle.
     * @param h         The height of the pixel rectangle.
     * @param b         The band to set.
     * @param iArray    The input samples in an int array.
     * @param data      The DataBuffer containing the image data.
     * @see #getSamples(int, int, int, int, int, int[], DataBuffer)
     *
     * @throws NullPointerException if iArray or data is null.
     * @throws ArrayIndexOutOfBoundsException if the coordinates or
     * the band index are not in bounds, or if iArray is too small to
     * hold the input.
     */
    public void setSamples(int x, int y, int w, int h, int b,
                           int iArray[], DataBuffer data) {

        int Offset=0;
        int x1 = x + w;
        int y1 = y + h;
        if (x < 0 || x >= width || w > width || x1 < 0 || x1 > width ||
            y < 0 || y >= height || h > height || y1 < 0 || y1 > height)
        {
            throw new ArrayIndexOutOfBoundsException("Invalid coordinates.");
        }

        for (int i=y; i<y1; i++) {
            for (int j=x; j<x1; j++) {
                setSample(j, i, b, iArray[Offset++], data);
            }
        }
    }

    /**
     * Sets the samples in the specified band for the specified rectangle
     * of pixels from a float array containing one sample per array element.
     * ArrayIndexOutOfBoundsException may be thrown if the coordinates are
     * not in bounds.
     * <p>
     *  在包含每个数组元素一个样本的float数组中,为指定的像素矩形设置指定带中的样本。如果坐标不在边界中,则可能抛出ArrayIndexOutOfBoundsException。
     * 
     * 
     * @param x         The X coordinate of the upper left pixel location.
     * @param y         The Y coordinate of the upper left pixel location.
     * @param w         The width of the pixel rectangle.
     * @param h         The height of the pixel rectangle.
     * @param b         The band to set.
     * @param fArray    The input samples in a float array.
     * @param data      The DataBuffer containing the image data.
     * @see #getSamples(int, int, int, int, int, float[], DataBuffer)
     *
     * @throws NullPointerException if fArray or data is null.
     * @throws ArrayIndexOutOfBoundsException if the coordinates or
     * the band index are not in bounds, or if fArray is too small to
     * hold the input.
     */
    public void setSamples(int x, int y, int w, int h, int b,
                           float fArray[], DataBuffer data) {
        int Offset=0;
        int x1 = x + w;
        int y1 = y + h;

        if (x < 0 || x >= width || w > width || x1 < 0 || x1 > width ||
            y < 0 || y >= height || h > height || y1 < 0 || y1 > height)
        {
            throw new ArrayIndexOutOfBoundsException("Invalid coordinates.");
        }

        for (int i=y; i<y1; i++) {
            for (int j=x; j<x1; j++) {
                setSample(j, i, b, fArray[Offset++], data);
            }
        }
    }

    /**
     * Sets the samples in the specified band for the specified rectangle
     * of pixels from a double array containing one sample per array element.
     * ArrayIndexOutOfBoundsException may be thrown if the coordinates are
     * not in bounds.
     * <p>
     *  对于指定的像素矩形,从包含每个数组元素一个样本的双阵列中设置指定带中的样本。如果坐标不在边界中,则可能抛出ArrayIndexOutOfBoundsException。
     * 
     * 
     * @param x         The X coordinate of the upper left pixel location.
     * @param y         The Y coordinate of the upper left pixel location.
     * @param w         The width of the pixel rectangle.
     * @param h         The height of the pixel rectangle.
     * @param b         The band to set.
     * @param dArray    The input samples in a double array.
     * @param data      The DataBuffer containing the image data.
     * @see #getSamples(int, int, int, int, int, double[], DataBuffer)
     *
     * @throws NullPointerException if dArray or data is null.
     * @throws ArrayIndexOutOfBoundsException if the coordinates or
     * the band index are not in bounds, or if dArray is too small to
     * hold the input.
     */
    public void setSamples(int x, int y, int w, int h, int b,
                           double dArray[], DataBuffer data) {
        int Offset=0;
        int x1 = x + w;
        int y1 = y + h;


        if (x < 0 || x >= width || w > width || x1 < 0 || x1 > width ||
            y < 0 || y >= height || h > height || y1 < 0 || y1 > height)
        {
            throw new ArrayIndexOutOfBoundsException("Invalid coordinates.");
        }

        for (int i=y; i<y1; i++) {
            for (int j=x; j<x1; j++) {
                setSample(j, i, b, dArray[Offset++], data);
            }
        }
    }

    /**
     *  Creates a SampleModel which describes data in this SampleModel's
     *  format, but with a different width and height.
     * <p>
     * 创建一个SampleModel,它以此SampleModel的格式描述数据,但宽度和高度不同。
     * 
     * 
     *  @param w the width of the image data
     *  @param h the height of the image data
     *  @return a <code>SampleModel</code> describing the same image
     *          data as this <code>SampleModel</code>, but with a
     *          different size.
     */
    public abstract SampleModel createCompatibleSampleModel(int w, int h);

    /**
     * Creates a new SampleModel
     * with a subset of the bands of this
     * SampleModel.
     * <p>
     *  使用此SampleModel的波段子集创建一个新的SampleModel。
     * 
     * 
     * @param bands the subset of bands of this <code>SampleModel</code>
     * @return a <code>SampleModel</code> with a subset of bands of this
     *         <code>SampleModel</code>.
     */
    public abstract SampleModel createSubsetSampleModel(int bands[]);

    /**
     * Creates a DataBuffer that corresponds to this SampleModel.
     * The DataBuffer's width and height will match this SampleModel's.
     * <p>
     *  创建与此SampleModel对应的DataBuffer。 DataBuffer的宽度和高度将匹配此SampleModel的。
     * 
     * 
     * @return a <code>DataBuffer</code> corresponding to this
     *         <code>SampleModel</code>.
     */
    public abstract DataBuffer createDataBuffer();

    /** Returns the size in bits of samples for all bands.
    /* <p>
    /* 
     *  @return the size of samples for all bands.
     */
    public abstract int[] getSampleSize();

    /** Returns the size in bits of samples for the specified band.
    /* <p>
    /* 
     *  @param band the specified band
     *  @return the size of the samples of the specified band.
     */
    public abstract int getSampleSize(int band);

}
