package com.ianly.util4j;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.*;

public class XmlUtil {

	public static Document getDocument(String xmlPath) {
		SAXReader reader = null;
		Document doc = null;
		try {
			reader = new SAXReader();
			doc = reader.read(new File(xmlPath));
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return doc;
	}

	public static List<Element> getElements(Document doc) {
		Element rootElement = doc.getRootElement();
		List<Element> list = rootElement.elements();
		return list;
	}

	public static List<Element> getElements(Document doc, String nodeName) {
		Element rootElement = doc.getRootElement();
		List<Element> list = rootElement.elements(nodeName);
		return list;
	}

	public static XmlBean parseBean(String xmlPath) {
		SAXReader reader = null;
		Document document = null;
		Element rootElement = null;
		try {
			reader = new SAXReader();
			document = reader.read(new File(xmlPath));
			rootElement = document.getRootElement();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return createXmlBean(rootElement);
	}

	private static XmlBean createXmlBean(Element element) {
		XmlBean xmlBean = new XmlBean();
		xmlBean.setNodeName(element.getName());
		xmlBean.setTextData(element.getText());

		// 设置属性
		Map<String, String> attrMap = new HashMap<String, String>();
		Iterator<Attribute> attrIter = element.attributeIterator();
		while (attrIter.hasNext()) {
			Attribute attr = attrIter.next();
			attrMap.put(attr.getName(), attr.getValue());
		}
		if (!attrMap.isEmpty()) {
			xmlBean.setAttrMap(attrMap);
			
			if (attrMap.containsKey("id")) {
				xmlBean.setId(attrMap.get("id"));
			}
		}

		// 设置子节点
		List<XmlBean> children = new ArrayList<XmlBean>();
		Iterator<Element> elementIter = element.elementIterator();
		while (elementIter.hasNext()) {
			Element element2 = elementIter.next();
			XmlBean bean = createXmlBean(element2);
			children.add(bean);
		}
		if (!children.isEmpty()) {
			xmlBean.setChildren(children);
		}
		return xmlBean;
	}

}
