/***** Lobxxx Translate Finished ******/
package com.sun.org.apache.xerces.internal.xinclude;
import com.sun.org.apache.xerces.internal.xni.parser.XMLComponent;
import com.sun.org.apache.xerces.internal.xni.parser.XMLDocumentFilter;



public interface XPointerSchema extends XMLComponent, XMLDocumentFilter{

    /**
     * set the Schema Name  eg element , xpointer
     */
    public void setXPointerSchemaName(String schemaName);

    /**
     * Return  Schema Name  eg element , xpointer
     *
     * <p>
     *  返回模式名称,例如element,xpointer
     * 
     */
    public String getXpointerSchemaName();

    /**
     * Parent Contenhandler for the this contenthandler.
     * // not sure about the parameter type. It can be Contenthandler instead of Object type.
     * <p>
     *  父Contenhandler为这个contenthandler。 //不确定参数类型。它可以是Contenthandler而不是对象类型。
     * 
     */
    public void setParent(Object parent);

    /**
     * return the Parent Contenthandler
     * <p>
     *  返回Parent Contenthandler
     * 
     */
    public Object getParent();

    /**
     * Content of the XPointer Schema. Xpath to be resolved.
     * <p>
     *  XPointer模式的内容。 Xpath要解析。
     * 
     */
    public void setXPointerSchemaPointer(String content);

    /**
     * Return the XPointer Schema.
     * <p>
     *  返回XPointer模式。
     */
    public String getXPointerSchemaPointer();

    public boolean isSubResourceIndentified();

    public void reset();

}
