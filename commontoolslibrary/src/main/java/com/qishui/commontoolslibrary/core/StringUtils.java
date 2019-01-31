package com.qishui.commontoolslibrary.core;

import org.xml.sax.InputSource;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.UUID;

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

        try {
            StringReader reader = new StringReader(xmlString);
            Source oldData = new StreamSource(reader);
            StreamResult newData = new StreamResult(new StringWriter());
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.transform(oldData, newData);
            return newData.getWriter().toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取一串uuid
     *
     * @return
     */
    public static String getUUID() {
        return  UUID.randomUUID().toString().replace("-", "").toUpperCase();
    }


}
