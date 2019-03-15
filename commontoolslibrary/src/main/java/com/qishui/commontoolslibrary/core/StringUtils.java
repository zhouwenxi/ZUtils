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
 * 添加人: add by qishui
 * 添加时间: 2019/3/15  17:17
 * 添加注释: 字符串操作
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
        return UUID.randomUUID().toString().replace("-", "").toUpperCase();
    }


    /**
     * 判断是否为空
     *
     * @param s
     * @return
     */
    public static Boolean isNull(String s) {

        return "".equals(s) || s == null;
    }

    /**
     * 判断是否不为空
     *
     * @param s
     * @return
     */
    public static Boolean isNotNull(String s) {

        return !"".equals(s) && s != null;
    }

    /**
     * 判断是否相等
     *
     * @param s1
     * @param s2
     * @return
     */
    public static Boolean isEquals(String s1, String s2) {

        if (isNotNull(s1) && isNotNull(s2)) {
            if (s1.equals(s2)) {
                return true;
            }
        }

        return isNull(s1) && isNull(s2);
    }


    /**
     * 叠加字符串
     *
     * @param obj
     * @return
     */
    public static String addString(Object... obj) {

        if (obj == null || obj.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();

        for (Object o : obj) {
            sb.append(o);
        }

        return sb.toString();
    }

    /**
     * 非空转化
     *
     * @param obj
     * @return
     */
    public static String getString(Object obj) {
        return addString(obj);
    }


    /**
     * 截取字符串
     * firstIndex 0开始
     * lastIndex 截取末位个数 1开始
     */

    public static String substring(String result, int firstIndex, int lastIndex) {

        if (isNotNull(result)) {
            if (firstIndex >= result.length() || result.length() <= lastIndex) {
                return "";
            }
            return result.substring(firstIndex, result.length() - lastIndex);
        }
        return "";
    }

    /**
     * 转大写
     *
     * @param result
     * @return
     */
    public static String toUpperCase(String result) {

        if (isNull(result)) {
            return "";
        } else {
            return result.toUpperCase();
        }
    }

    /**
     * 转小写
     *
     * @param result
     * @return
     */
    public static String toLowerCase(String result) {

        if (isNull(result)) {
            return "";
        } else {
            return result.toLowerCase();
        }
    }

}
