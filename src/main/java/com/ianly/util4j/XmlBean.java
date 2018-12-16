package com.ianly.util4j;

import java.util.List;
import java.util.Map;

public class XmlBean {

	private String id;
	private String nodeName;
	private Map<String, String> attrMap;
	private String textData;
	private List<XmlBean> children;
	
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public Map<String, String> getAttrMap() {
		return attrMap;
	}

	public void setAttrMap(Map<String, String> attrMap) {
		this.attrMap = attrMap;
	}

	public String getTextData() {
		return textData;
	}

	public void setTextData(String textData) {
		this.textData = textData;
	}

	public List<XmlBean> getChildren() {
		return children;
	}

	public void setChildren(List<XmlBean> children) {
		this.children = children;
	}

	@Override
	public String toString() {
		return "XmlBean [nodeName=" + nodeName + ", attrMap=" + attrMap + ", textData=" + textData + ", children="
				+ children + "]";
	}

}
