package org.umang.pearl;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
 
 
public class ExampleHandler extends DefaultHandler{
 
        // ===========================================================
        // Fields
        // ===========================================================
       
        private boolean in_outertag = false;
        private boolean in_innertag = false;
        private boolean in_mytag = false;
        
        private PearlActivity pa;
       
        private ParsedExampleDataSet myParsedExampleDataSet=new ParsedExampleDataSet();
        
       
 
        // ===========================================================
        // Getter & Setter
        // ===========================================================
 
        public ParsedExampleDataSet getParsedData() {
                return this.myParsedExampleDataSet;
        }
 
        
        @Override
        public void startDocument() throws SAXException {
                this.myParsedExampleDataSet = new ParsedExampleDataSet();
        }
 
        @Override
        public void endDocument() throws SAXException {
                // Nothing to do
        }
 
        /** Gets be called on opening tags like:
         * <tag>
         * Can provide attribute(s), when xml was like:
         * <tag attribute="attributeValue">*/
        @Override
        public void startElement(String namespaceURI, String localName,
                        String qName, Attributes atts) throws SAXException {
                if (localName.equals("rss")) {
                        this.in_outertag = true;
                }else if (localName.equals("channel")) {
                        this.in_innertag = true;
                }else if (localName.equals("title")) {
                        this.in_mytag = true;
                        
                }
        }
       
        /** Gets be called on closing tags like:
         * </tag> */
        @Override
        public void endElement(String namespaceURI, String localName, String qName)
                        throws SAXException {
                if (localName.equals("rss")) {
                        this.in_outertag = false;
                }else if (localName.equals("channel")) {
                        this.in_innertag = false;
                }else if (localName.equals("title")) {
                        this.in_mytag = false;
                }
        }
       
        /** Gets be called on the following structure:
         * <tag>characters</tag> */
        @Override
    public void characters(char ch[], int start, int length) {
                if(this.in_mytag){
                myParsedExampleDataSet.setExtractedString(new String(ch, start, length));
        }
    }
}