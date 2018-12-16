package com.ianly.util4j;

import com.alibaba.fastjson.JSONObject;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ianly
 */

public class SqlHelper {

	private static final Log log = LogFactory.getLog(SqlHelper.class);

	private static Map<String, String> sqlResourcesMap = null;

	private static Configuration cfg = null;

	public static void main(String[] args) {
		System.out.println(getSql("test"));
	}

	/**
	 *
	 * @param sqlId
	 * @param sqlParams
	 * @return sqlText
	 */
	public static String getSql(String sqlId, Map<String, Object> sqlParams) {
		String sqlText = "";
		sqlText = getSqlString(sqlId, sqlParams, sqlText);
		return sqlText;
	}

	private static String getSqlString(String sqlId, Map<String, Object> sqlParams, String sqlText) {
		try {
			if (cfg == null) {
				initSqls();
			}
			Template template = cfg.getTemplate(sqlId, "utf-8");
			StringWriter writer = new StringWriter();
			template.process(sqlParams, writer);
			sqlText = writer.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
		return sqlText;
	}

	/**
	 *
	 * @param sqlId
	 * @return
	 */
	public static String getSql(String sqlId) {
		String sqlText = "";
		try {
			if (cfg == null) {
				initSqls();
			}

			Template template = cfg.getTemplate(sqlId, "utf-8");
			StringWriter writer = new StringWriter();
			template.process(new HashMap<>(), writer);
			sqlText = writer.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}

		return sqlText;
	}

	/**
	 *
	 * @param sqlId
	 * @param json
	 * @return
	 */
	public static String getSqlFromJson(String sqlId, JSONObject json) {
		String sqlText = "";
		sqlText = getSqlString(sqlId, json, sqlText);
		return sqlText;
	}

	/**
	 *
	 */
	private static void initSqls() {
		try {
			log.warn("开始加载SQL资源......");
			cfg = new Configuration(Configuration.VERSION_2_3_25);
			StringTemplateLoader strTemplateLoader = new StringTemplateLoader();

			sqlResourcesMap = new HashMap<>();
			String classpath = ResourceUtils.getURL("classpath:").getPath();
			File statements = new File(classpath + "statements");
			File[] files = statements.listFiles();
			for (File file : files) {
				XmlBean root = XmlUtil.parseBean(file.getAbsolutePath());
				List<XmlBean> list = root.getChildren();
				for (XmlBean bean : list) {
					if (sqlResourcesMap.containsKey(bean.getId())) {
						log.warn("SQL资源加载重复[" + bean.getId() + "]");
					} else {
						sqlResourcesMap.put(bean.getId(), bean.getTextData());
						strTemplateLoader.putTemplate(bean.getId(), bean.getTextData());
					}
				}
			}
			cfg.setTemplateLoader(strTemplateLoader);
			cfg.setDefaultEncoding("UTF-8");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}
}
