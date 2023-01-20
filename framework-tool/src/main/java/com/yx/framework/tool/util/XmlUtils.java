package com.yx.framework.tool.util;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.basic.DateConverter;
import com.thoughtworks.xstream.io.xml.CompactWriter;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import com.jarvis.framework.core.constant.CharsetConstants;
import com.yx.framework.tool.constant.DateTimePatternConstants;
import com.yx.framework.tool.exception.XmlParseException;
import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.xml.sax.InputSource;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.net.URL;
import java.util.TimeZone;

/**
 * <p>Xml解析工具类</p>
 *
 * @author 王涛
 * @date 2019-11-18 14:44:36
 */
public abstract class XmlUtils {

    /**
     * Xml文件头
     */
    private static final String XML_TAG = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";

    /**
     * 解决双下划线
     */
    private static final XmlFriendlyNameCoder NAME_CODER = new XmlFriendlyNameCoder("_-", "_");

    /**
     * Dom解析驱动
     */
    private static DomDriver DOM_DRIVER = new DomDriver(CharsetConstants.UTF8, NAME_CODER);


    /**
     * 时间转换器
     */
    private static final DateConverter DATE_CONVERTER = new DateConverter(DateTimePatternConstants.LONG_DATE_PATTERN_LINE, null, TimeZone.getTimeZone("GMT+8"));


    // =============================common============================

    /**
     * 获取XStream实列对象
     *
     * @return XStream实列对象
     */
    public static XStream getInstance() {
        XStream instance = new XStream(DOM_DRIVER);
        // 去掉class属性
        instance.aliasSystemAttribute(null, "class");
        // 自动探测注解
        instance.autodetectAnnotations(true);
        // 忽略未知元素
        instance.ignoreUnknownElements();
        // 设置默认的安全校验
        XStream.setupDefaultSecurity(instance);
        instance.allowTypesByWildcard(new String[]{"com.yx.**"});
        // 使用本地的类加载器
        instance.setClassLoader(XmlUtils.class.getClassLoader());
        return instance;
    }

    public static final String getText(String xmlStr, String xpathStr) {
        Document document = getDocumentFromXmlString(xmlStr);
        Node node = document.selectSingleNode(xpathStr);
        return null == node ? null : node.getText();
    }

    /**
     * 获取element对象的text值
     *
     * @param e
     * @param tag
     * @return
     */
    public static final String getText(Element e, String tag) {
        Element element = e.element(tag);
        if (null != element) {
            return element.getText();
        } else {
            return null;
        }
    }

    public static final Document getDocumentFromXmlString(String xmlStr) {
        try {
            return DocumentHelper.parseText(xmlStr);
        } catch (DocumentException e) {
            throw new XmlParseException(e.getMessage());
        }
    }

    // =============================to xml============================

    /**
     * 将对象序列化为Xml字符串并替换根节点
     *
     * @param rootTagName
     * @param object
     * @return
     */
    public static String beanToXml(String rootTagName, Object object) throws XmlParseException {
        String xmlStr = beanToXml(object);
        Document document = getDocumentFromXmlString(xmlStr);
        Element rootElement = document.getRootElement();
        rootElement.setName(rootTagName);
        xmlStr = documentToXml(document, true);
        return xmlStr;
    }

    /**
     * 将对象序列化为Xml字符串
     *
     * @param object
     * @return
     */
    public static String beanToXml(Object object) throws XmlParseException {
        return beanToXml(object, true);
    }

    /**
     * 将对象序列化为Xml字符串
     *
     * @param object
     * @param isCompress 是否压缩
     * @return
     */
    public static String beanToXml(Object object, boolean isCompress) throws XmlParseException {
        XStream xStream = getInstance();
        try {
            String xmlStr;
            // 以压缩的方式输出XML
            if (isCompress) {
                StringWriter sw = new StringWriter();
                xStream.marshal(object, new CompactWriter(sw));
                xmlStr = sw.toString().replace("__", "_");
            } else {
                // 以格式化的方式输出XML
                xmlStr = XML_TAG + "\n" + xStream.toXML(object);
            }
            return xmlStr;
        } catch (Exception e) {
            throw new XmlParseException(e.getMessage());
        }
    }

    /**
     * 将对象序列化为Xml字符串并保存到File
     *
     * @param obj
     * @param file
     */
    public static void beanToXmlFile(Object obj, File file) throws XmlParseException {
        beanToXmlFile(beanToXml(obj), file);
    }

    /**
     * 将Xml字符串保存到File
     *
     * @param xml
     * @param file
     */
    public static void beanToXmlFile(String xml, File file) throws XmlParseException {
        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            InputSource is = new InputSource(new ByteArrayInputStream(xml.getBytes()));
            Source xmlSource = new SAXSource(is);
            transformer.transform(xmlSource, new StreamResult(file));
        } catch (Exception e) {
            throw new XmlParseException(e.getMessage());
        }
    }

    /**
     * 将Document对象序列化为Xml字符串
     *
     * @param document
     * @param isCompress
     * @return
     */
    public static String documentToXml(Document document, boolean isCompress) throws XmlParseException {
        StringWriter stringWriter = new StringWriter();
        // 压缩的格式
        OutputFormat outputFormat;
        if (isCompress) {
            outputFormat = OutputFormat.createCompactFormat();
            // 格式化的格式
        } else {
            outputFormat = OutputFormat.createPrettyPrint();
        }
        outputFormat.setSuppressDeclaration(true);
        outputFormat.setEncoding(CharsetConstants.UTF8);
        XMLWriter xmlWriter = new XMLWriter(stringWriter, outputFormat);
        try {
            xmlWriter.write(document);
        } catch (IOException e) {
            throw new XmlParseException(e.getMessage());
        } finally {
            try {
                xmlWriter.flush();
                xmlWriter.close();
            } catch (IOException e) {
                throw new XmlParseException(e.getMessage());
            }
        }
        return stringWriter.toString();
    }

    // =============================to bean============================

    /**
     * 从字符串反序列化对象
     *
     * @param xmlStr
     * @param rootTagName
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T xmlToBean(String xmlStr, String rootTagName, Class<T> clazz) throws XmlParseException {

        Document document = getDocumentFromXmlString(xmlStr);
        Element rootElement = document.getRootElement();
        rootElement.setName(rootTagName);
        xmlStr = documentToXml(document, true);
        return xmlToBean(xmlStr, clazz);
    }

    /**
     * 从字符串反序列化对象
     *
     * @param xmlStr
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T xmlToBean(String xmlStr, Class<T> clazz) throws XmlParseException {
        XStream xStream = getInstance();
        xStream.processAnnotations(clazz);
        try {
            Object object = xStream.fromXML(xmlStr);
            T cast = clazz.cast(object);
            return cast;
        } catch (Exception e) {
            throw new XmlParseException(e.getMessage());
        }
    }

    /**
     * 从Reader反序列化对象
     *
     * @param reader
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T xmlToBean(Reader reader, Class<T> clazz) throws XmlParseException {
        XStream xStream = getInstance();
        xStream.processAnnotations(clazz);
        try {
            Object object = xStream.fromXML(reader);
            T cast = clazz.cast(object);
            return cast;
        } catch (Exception e) {
            throw new XmlParseException(e.getMessage());
        }
    }

    /**
     * 从InputStream反序列化对象
     *
     * @param input
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T xmlToBean(InputStream input, Class<T> clazz) throws XmlParseException {
        XStream xStream = getInstance();
        xStream.processAnnotations(clazz);
        try {
            Object object = xStream.fromXML(input);
            T cast = clazz.cast(object);
            return cast;
        } catch (Exception e) {
            throw new XmlParseException(e.getMessage());
        }
    }

    /**
     * 从URL反序列化对象
     *
     * @param url
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T xmlToBean(URL url, Class<T> clazz) throws XmlParseException {
        XStream xStream = getInstance();
        xStream.processAnnotations(clazz);
        try {
            Object object = xStream.fromXML(url);
            T cast = clazz.cast(object);
            return cast;
        } catch (Exception e) {
            throw new XmlParseException(e.getMessage());
        }
    }

    /**
     * 从File反序列化对象
     *
     * @param file
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T xmlToBean(File file, Class<T> clazz) throws XmlParseException {
        XStream xStream = getInstance();
        xStream.processAnnotations(clazz);
        try {
            Object object = xStream.fromXML(file);
            T cast = clazz.cast(object);
            return cast;
        } catch (Exception e) {
            throw new XmlParseException(e.getMessage());
        }
    }
}
