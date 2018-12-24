package com.qishui.commontoolslibrary.core;

import org.xml.sax.InputSource;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 * Created by zhou on 2018/12/22.
 */

public class StringUtils {


    /**
     * 是否是xmlges
     *
     * @param s
     * @return
     */
    public static boolean isXml(String s) {

        boolean flag = true;
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();
            builder.parse(new InputSource(new StringReader(s)));
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     * desc 字符串转成xml
     *
     * @param xmlString
     * @return
     */
    public static String String2Xml(String xmlString) {
        String xml = "";
        try {
            StringReader reader = new StringReader(xmlString);
            Source oldData = new StreamSource(reader);
            StreamResult newData = new StreamResult(new StringWriter());
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.transform(oldData, newData);
            xml = newData.getWriter().toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return xml;
    }


}
