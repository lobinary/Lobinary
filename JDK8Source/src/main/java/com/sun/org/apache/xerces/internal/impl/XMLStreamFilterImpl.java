/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2005, 2006, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.org.apache.xerces.internal.impl;

import javax.xml.XMLConstants;
import javax.xml.stream.Location;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.StreamFilter;
import javax.xml.stream.XMLStreamException;
import javax.xml.namespace.QName;
import javax.xml.stream.events.XMLEvent;


/**
 *
 * <p>
 * 
 * @author Joe Wang:
 * This is a rewrite of the original class. The focus is on removing caching, and make the filtered
 * stream reader more compatible with those in other implementations. Note however, that this version
 * will not solve all the issues related to the undefined condition in the spec. The priority is
 * to pass the TCK. Issues arising due to the requirement, that is, (1) should it initiate at BEGIN_DOCUMENT
 * or an accepted event; (2) should hasNext() advance the underlining stream in order to find an acceptable
 * event, would have to wait until 1.1 of StAX in which the filtered stream reader would be defined more clearly.
 */

public class XMLStreamFilterImpl implements javax.xml.stream.XMLStreamReader {

    private StreamFilter fStreamFilter = null;
    private XMLStreamReader fStreamReader = null;
    private int fCurrentEvent;
    private boolean fEventAccepted = false;

    /**the very issue around a long discussion. but since we must pass the TCK, we have to allow
     * hasNext() to advance the underlining stream in order to find the next acceptable event
     * <p>
     *  hasNext()来推进下划线流,以便找到下一个可接受的事件
     * 
     */
    private boolean fStreamAdvancedByHasNext = false;
    /** Creates a new instance of XMLStreamFilterImpl */

    public XMLStreamFilterImpl(XMLStreamReader reader,StreamFilter filter){
        fStreamReader = reader;
        this.fStreamFilter = filter;

        //this is debatable to initiate at an acceptable event,
        //but it's neccessary in order to pass the TCK and yet avoid skipping element
        try {
            if (fStreamFilter.accept(fStreamReader)) {
                fEventAccepted = true;
            } else {
                findNextEvent();
            }
        }catch(XMLStreamException xs){
            System.err.println("Error while creating a stream Filter"+xs);
        }
    }

    /**
     *
     * <p>
     * 
     * @param sf
     */
    protected void setStreamFilter(StreamFilter sf){
        this.fStreamFilter = sf;
    }

    /**
     *
     * <p>
     * 
     * @return
     * @throws XMLStreamException
     */
    public int next() throws XMLStreamException {
        if (fStreamAdvancedByHasNext && fEventAccepted) {
            fStreamAdvancedByHasNext = false;
            return fCurrentEvent;
        }
        int event = findNextEvent();
        if (event != -1) {
            return event;
        }

        throw new IllegalStateException("The stream reader has reached the end of the document, or there are no more "+
                                    " items to return");
    }
    /**
     *
     * <p>
     * 
     * @throws XMLStreamException
     * @return
     */
    public int nextTag() throws XMLStreamException {
        if (fStreamAdvancedByHasNext && fEventAccepted &&
                (fCurrentEvent == XMLEvent.START_ELEMENT || fCurrentEvent == XMLEvent.START_ELEMENT)) {
            fStreamAdvancedByHasNext = false;
            return fCurrentEvent;
        }

        int event = findNextTag();
        if (event != -1) {
            return event;
        }
        throw new IllegalStateException("The stream reader has reached the end of the document, or there are no more "+
                                    " items to return");
    }

    /**
     *
     * <p>
     * 
     * @throws XMLStreamException
     * @return
     */
    public boolean hasNext() throws XMLStreamException {
        if (fStreamReader.hasNext()) {
            if (!fEventAccepted) {
                if ((fCurrentEvent = findNextEvent()) == -1) {
                    return false;
                } else {
                    fStreamAdvancedByHasNext = true;
                }
            }
            return true;
        }
        return false;
    }

    private int findNextEvent() throws XMLStreamException {
        fStreamAdvancedByHasNext = false;
        while(fStreamReader.hasNext()){
            fCurrentEvent = fStreamReader.next();
            if(fStreamFilter.accept(fStreamReader)){
                fEventAccepted = true;
                return fCurrentEvent;
            }
        }
        //although it seems that IllegalStateException should be thrown when next() is called
        //on a stream that has no more items, we have to assume END_DOCUMENT is always accepted
        //in order to pass the TCK
        if (fCurrentEvent == XMLEvent.END_DOCUMENT)
            return fCurrentEvent;
        else
            return -1;
    }
    private int findNextTag() throws XMLStreamException {
        fStreamAdvancedByHasNext = false;
        while(fStreamReader.hasNext()){
            fCurrentEvent = fStreamReader.nextTag();
            if(fStreamFilter.accept(fStreamReader)){
                fEventAccepted = true;
                return fCurrentEvent;
            }
        }
        if (fCurrentEvent == XMLEvent.END_DOCUMENT)
            return fCurrentEvent;
        else
            return -1;
    }
    /**
     *
     * <p>
     * 
     * @throws XMLStreamException
     */
    public void close() throws XMLStreamException {
        fStreamReader.close();
    }

    /**
     *
     * <p>
     * 
     * @return
     */
    public int getAttributeCount() {
        return fStreamReader.getAttributeCount();
    }

    /**
     *
     * <p>
     * 
     * @param index
     * @return
     */
    public QName getAttributeName(int index) {
        return fStreamReader.getAttributeName(index);
    }

    /**
     *
     * <p>
     * 
     * @param index
     * @return
     */
    public String getAttributeNamespace(int index) {
        return fStreamReader.getAttributeNamespace(index);
    }

    /**
     *
     * <p>
     * 
     * @param index
     * @return
     */
    public String getAttributePrefix(int index) {
        return fStreamReader.getAttributePrefix(index);
    }

    /**
     *
     * <p>
     * 
     * @param index
     * @return
     */
    public String getAttributeType(int index) {
        return fStreamReader.getAttributeType(index);
    }

    /**
     *
     * <p>
     * 
     * @param index
     * @return
     */
    public String getAttributeValue(int index) {
        return fStreamReader.getAttributeValue(index);
    }

    /**
     *
     * <p>
     * 
     * @param namespaceURI
     * @param localName
     * @return
     */
    public String getAttributeValue(String namespaceURI, String localName) {
        return fStreamReader.getAttributeValue(namespaceURI,localName);
    }

    /**
     *
     * <p>
     * 
     * @return
     */
    public String getCharacterEncodingScheme() {
        return fStreamReader.getCharacterEncodingScheme();
    }

    /**
     *
     * <p>
     * 
     * @throws XMLStreamException
     * @return
     */
    public String getElementText() throws XMLStreamException {
        return fStreamReader.getElementText();
    }

    /**
     *
     * <p>
     * 
     * @return
     */
    public String getEncoding() {
        return fStreamReader.getEncoding();
    }

    /**
     *
     * <p>
     * 
     * @return
     */
    public int getEventType() {
        return fStreamReader.getEventType();
    }

    /**
     *
     * <p>
     * 
     * @return
     */
    public String getLocalName() {
        return fStreamReader.getLocalName();
    }

    /**
     *
     * <p>
     * 
     * @return
     */
    public javax.xml.stream.Location getLocation() {
        return fStreamReader.getLocation();
    }

    /**
     *
     * <p>
     * 
     * @return
     */
    public javax.xml.namespace.QName getName() {
        return fStreamReader.getName();
    }

    /**
     *
     * <p>
     * 
     * @return
     */
    public javax.xml.namespace.NamespaceContext getNamespaceContext() {
        return fStreamReader.getNamespaceContext();
    }

    /**
     *
     * <p>
     * 
     * @return
     */
    public int getNamespaceCount() {
        return fStreamReader.getNamespaceCount();
    }

    /**
     *
     * <p>
     * 
     * @param index
     * @return
     */
    public String getNamespacePrefix(int index) {
        return fStreamReader.getNamespacePrefix(index);
    }

    /**
     *
     * <p>
     * 
     * @return
     */
    public String getNamespaceURI() {
        return fStreamReader.getNamespaceURI();
    }

    /**
     *
     * <p>
     * 
     * @param index
     * @return
     */
    public String getNamespaceURI(int index) {
        return fStreamReader.getNamespaceURI(index);
    }

    /**
     *
     * <p>
     * 
     * @param prefix
     * @return
     */
    public String getNamespaceURI(String prefix) {
        return fStreamReader.getNamespaceURI(prefix);
    }

    /**
     *
     * <p>
     * 
     * @return
     */
    public String getPIData() {
        return fStreamReader.getPIData();
    }

    /**
     *
     * <p>
     * 
     * @return
     */
    public String getPITarget() {
        return fStreamReader.getPITarget();
    }

    /**
     *
     * <p>
     * 
     * @return
     */
    public String getPrefix() {
        return fStreamReader.getPrefix();
    }

    /**
     *
     * <p>
     * 
     * @param name
     * @throws IllegalArgumentException
     * @return
     */
    public Object getProperty(java.lang.String name) throws java.lang.IllegalArgumentException {
        return fStreamReader.getProperty(name);
    }

    /**
     *
     * <p>
     * 
     * @return
     */
    public String getText() {
        return fStreamReader.getText();
    }

    /**
     *
     * <p>
     * 
     * @return
     */
    public char[] getTextCharacters() {
        return fStreamReader.getTextCharacters();
    }

    /**
     *
     * <p>
     * 
     * @param sourceStart
     * @param target
     * @param targetStart
     * @param length
     * @throws XMLStreamException
     * @return
     */
    public int getTextCharacters(int sourceStart, char[] target, int targetStart, int length) throws XMLStreamException {
        return fStreamReader.getTextCharacters(sourceStart, target,targetStart,length);
    }

    /**
     *
     * <p>
     * 
     * @return
     */
    public int getTextLength() {
        return fStreamReader.getTextLength();
    }

    /**
     *
     * <p>
     * 
     * @return
     */
    public int getTextStart() {
        return fStreamReader.getTextStart();
    }

    /**
     *
     * <p>
     * 
     * @return
     */
    public String getVersion() {
        return fStreamReader.getVersion();
    }

    /**
     *
     * <p>
     * 
     * @return
     */
    public boolean hasName() {
        return fStreamReader.hasName();
    }

    /**
     *
     * <p>
     * 
     * @return
     */
    public boolean hasText() {
        return fStreamReader.hasText();
    }

    /**
     *
     * <p>
     * 
     * @return
     * @param index
     */
    public boolean isAttributeSpecified(int index) {
        return fStreamReader.isAttributeSpecified(index);
    }

    /**
     *
     * <p>
     * 
     * @return
     */
    public boolean isCharacters() {
        return fStreamReader.isCharacters();
    }

    /**
     *
     * <p>
     * 
     * @return
     */
    public boolean isEndElement() {
        return fStreamReader.isEndElement();
    }

    /**
     *
     * <p>
     * 
     * @return
     */
    public boolean isStandalone() {
        return fStreamReader.isStandalone();
    }

    /**
     *
     * <p>
     * 
     * @return
     */
    public boolean isStartElement() {
        return fStreamReader.isStartElement();
    }

    /**
     *
     * <p>
     * 
     * @return
     */
    public boolean isWhiteSpace() {
        return fStreamReader.isWhiteSpace();
    }


    /**
     *
     * <p>
     * 
     * @param type
     * @param namespaceURI
     * @param localName
     * @throws XMLStreamException
     */
    public void require(int type, String namespaceURI, String localName) throws XMLStreamException {
        fStreamReader.require(type,namespaceURI,localName);
    }

    /**
     *
     * <p>
     * 
     * @return
     */
    public boolean standaloneSet() {
        return fStreamReader.standaloneSet();
    }

    /**
     *
     * <p>
     * 
     * @param index
     * @return
     */
    public String getAttributeLocalName(int index){
        return fStreamReader.getAttributeLocalName(index);
    }
}
