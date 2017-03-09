/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2002, Oracle and/or its affiliates. All rights reserved.
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
package javax.swing.plaf.basic;

import sun.awt.datatransfer.DataTransferer;

import java.io.*;
import java.awt.datatransfer.*;
import javax.swing.plaf.UIResource;

/**
 * A transferable implementation for the default data transfer of some Swing
 * components.
 *
 * <p>
 *  用于一些Swing组件的默认数据传输的可转移实现。
 * 
 * 
 * @author  Timothy Prinzing
 */
class BasicTransferable implements Transferable, UIResource {

    protected String plainData;
    protected String htmlData;

    private static DataFlavor[] htmlFlavors;
    private static DataFlavor[] stringFlavors;
    private static DataFlavor[] plainFlavors;

    static {
        try {
            htmlFlavors = new DataFlavor[3];
            htmlFlavors[0] = new DataFlavor("text/html;class=java.lang.String");
            htmlFlavors[1] = new DataFlavor("text/html;class=java.io.Reader");
            htmlFlavors[2] = new DataFlavor("text/html;charset=unicode;class=java.io.InputStream");

            plainFlavors = new DataFlavor[3];
            plainFlavors[0] = new DataFlavor("text/plain;class=java.lang.String");
            plainFlavors[1] = new DataFlavor("text/plain;class=java.io.Reader");
            plainFlavors[2] = new DataFlavor("text/plain;charset=unicode;class=java.io.InputStream");

            stringFlavors = new DataFlavor[2];
            stringFlavors[0] = new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType+";class=java.lang.String");
            stringFlavors[1] = DataFlavor.stringFlavor;

        } catch (ClassNotFoundException cle) {
            System.err.println("error initializing javax.swing.plaf.basic.BasicTranserable");
        }
    }

    public BasicTransferable(String plainData, String htmlData) {
        this.plainData = plainData;
        this.htmlData = htmlData;
    }


    /**
     * Returns an array of DataFlavor objects indicating the flavors the data
     * can be provided in.  The array should be ordered according to preference
     * for providing the data (from most richly descriptive to least descriptive).
     * <p>
     *  返回一个DataFlavor对象的数组,指示可以提供数据的风格。数组应根据提供数据的优先级排序(从最丰富描述到最不描述)。
     * 
     * 
     * @return an array of data flavors in which this data can be transferred
     */
    public DataFlavor[] getTransferDataFlavors() {
        DataFlavor[] richerFlavors = getRicherFlavors();
        int nRicher = (richerFlavors != null) ? richerFlavors.length : 0;
        int nHTML = (isHTMLSupported()) ? htmlFlavors.length : 0;
        int nPlain = (isPlainSupported()) ? plainFlavors.length: 0;
        int nString = (isPlainSupported()) ? stringFlavors.length : 0;
        int nFlavors = nRicher + nHTML + nPlain + nString;
        DataFlavor[] flavors = new DataFlavor[nFlavors];

        // fill in the array
        int nDone = 0;
        if (nRicher > 0) {
            System.arraycopy(richerFlavors, 0, flavors, nDone, nRicher);
            nDone += nRicher;
        }
        if (nHTML > 0) {
            System.arraycopy(htmlFlavors, 0, flavors, nDone, nHTML);
            nDone += nHTML;
        }
        if (nPlain > 0) {
            System.arraycopy(plainFlavors, 0, flavors, nDone, nPlain);
            nDone += nPlain;
        }
        if (nString > 0) {
            System.arraycopy(stringFlavors, 0, flavors, nDone, nString);
            nDone += nString;
        }
        return flavors;
    }

    /**
     * Returns whether or not the specified data flavor is supported for
     * this object.
     * <p>
     *  返回此对象是否支持指定的数据flavor。
     * 
     * 
     * @param flavor the requested flavor for the data
     * @return boolean indicating whether or not the data flavor is supported
     */
    public boolean isDataFlavorSupported(DataFlavor flavor) {
        DataFlavor[] flavors = getTransferDataFlavors();
        for (int i = 0; i < flavors.length; i++) {
            if (flavors[i].equals(flavor)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns an object which represents the data to be transferred.  The class
     * of the object returned is defined by the representation class of the flavor.
     *
     * <p>
     *  返回表示要传输的数据的对象。返回的对象的类由flavor的表示类定义。
     * 
     * 
     * @param flavor the requested flavor for the data
     * @see DataFlavor#getRepresentationClass
     * @exception IOException                if the data is no longer available
     *              in the requested flavor.
     * @exception UnsupportedFlavorException if the requested data flavor is
     *              not supported.
     */
    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
        DataFlavor[] richerFlavors = getRicherFlavors();
        if (isRicherFlavor(flavor)) {
            return getRicherData(flavor);
        } else if (isHTMLFlavor(flavor)) {
            String data = getHTMLData();
            data = (data == null) ? "" : data;
            if (String.class.equals(flavor.getRepresentationClass())) {
                return data;
            } else if (Reader.class.equals(flavor.getRepresentationClass())) {
                return new StringReader(data);
            } else if (InputStream.class.equals(flavor.getRepresentationClass())) {
                return createInputStream(flavor, data);
            }
            // fall through to unsupported
        } else if (isPlainFlavor(flavor)) {
            String data = getPlainData();
            data = (data == null) ? "" : data;
            if (String.class.equals(flavor.getRepresentationClass())) {
                return data;
            } else if (Reader.class.equals(flavor.getRepresentationClass())) {
                return new StringReader(data);
            } else if (InputStream.class.equals(flavor.getRepresentationClass())) {
                return createInputStream(flavor, data);
            }
            // fall through to unsupported

        } else if (isStringFlavor(flavor)) {
            String data = getPlainData();
            data = (data == null) ? "" : data;
            return data;
        }
        throw new UnsupportedFlavorException(flavor);
    }

    private InputStream createInputStream(DataFlavor flavor, String data)
            throws IOException, UnsupportedFlavorException {
        String cs = DataTransferer.getTextCharset(flavor);
        if (cs == null) {
            throw new UnsupportedFlavorException(flavor);
        }
        return new ByteArrayInputStream(data.getBytes(cs));
    }

    // --- richer subclass flavors ----------------------------------------------

    protected boolean isRicherFlavor(DataFlavor flavor) {
        DataFlavor[] richerFlavors = getRicherFlavors();
        int nFlavors = (richerFlavors != null) ? richerFlavors.length : 0;
        for (int i = 0; i < nFlavors; i++) {
            if (richerFlavors[i].equals(flavor)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Some subclasses will have flavors that are more descriptive than HTML
     * or plain text.  If this method returns a non-null value, it will be
     * placed at the start of the array of supported flavors.
     * <p>
     *  一些子类将具有比HTML或纯文本更具描述性的风格。如果此方法返回非空值,它将放在支持的flavor的数组的开头。
     * 
     */
    protected DataFlavor[] getRicherFlavors() {
        return null;
    }

    protected Object getRicherData(DataFlavor flavor) throws UnsupportedFlavorException {
        return null;
    }

    // --- html flavors ----------------------------------------------------------

    /**
     * Returns whether or not the specified data flavor is an HTML flavor that
     * is supported.
     * <p>
     *  返回指定的数据flavor是否是受支持的HTML flavor。
     * 
     * 
     * @param flavor the requested flavor for the data
     * @return boolean indicating whether or not the data flavor is supported
     */
    protected boolean isHTMLFlavor(DataFlavor flavor) {
        DataFlavor[] flavors = htmlFlavors;
        for (int i = 0; i < flavors.length; i++) {
            if (flavors[i].equals(flavor)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Should the HTML flavors be offered?  If so, the method
     * getHTMLData should be implemented to provide something reasonable.
     * <p>
     *  应该提供HTML风味吗?如果是这样,方法getHTMLData应该实现提供一些合理的。
     * 
     */
    protected boolean isHTMLSupported() {
        return htmlData != null;
    }

    /**
     * Fetch the data in a text/html format
     * <p>
     *  以text / html格式获取数据
     * 
     */
    protected String getHTMLData() {
        return htmlData;
    }

    // --- plain text flavors ----------------------------------------------------

    /**
     * Returns whether or not the specified data flavor is an plain flavor that
     * is supported.
     * <p>
     *  返回指定的数据flavor是否为受支持的纯风格。
     * 
     * 
     * @param flavor the requested flavor for the data
     * @return boolean indicating whether or not the data flavor is supported
     */
    protected boolean isPlainFlavor(DataFlavor flavor) {
        DataFlavor[] flavors = plainFlavors;
        for (int i = 0; i < flavors.length; i++) {
            if (flavors[i].equals(flavor)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Should the plain text flavors be offered?  If so, the method
     * getPlainData should be implemented to provide something reasonable.
     * <p>
     *  应该提供纯文本口味吗?如果是这样,getPlainData方法应该实现提供一些合理的东西。
     * 
     */
    protected boolean isPlainSupported() {
        return plainData != null;
    }

    /**
     * Fetch the data in a text/plain format.
     * <p>
     *  以文本/纯格式提取数据。
     * 
     */
    protected String getPlainData() {
        return plainData;
    }

    // --- string flavorss --------------------------------------------------------

    /**
     * Returns whether or not the specified data flavor is a String flavor that
     * is supported.
     * <p>
     *  返回指定的数据flavor是否为受支持的String flavor。
     * 
     * @param flavor the requested flavor for the data
     * @return boolean indicating whether or not the data flavor is supported
     */
    protected boolean isStringFlavor(DataFlavor flavor) {
        DataFlavor[] flavors = stringFlavors;
        for (int i = 0; i < flavors.length; i++) {
            if (flavors[i].equals(flavor)) {
                return true;
            }
        }
        return false;
    }


}
