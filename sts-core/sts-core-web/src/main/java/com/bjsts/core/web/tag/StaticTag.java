package com.bjsts.core.web.tag;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

/**
 * Created by cld on 2016/2/18.
 */
public class StaticTag extends SimpleTagSupport {

    public static final Logger logger = LoggerFactory.getLogger(StaticTag.class);

    /* 使用说明：
	 * 1 在jsp页面中引入自定义taglib
	 * 2 使用static标签添加js或css
	 *
	 * 示例：
	 * 1 引用taglib
	 * <%@ taglib prefix="win" uri="http://www.8win.com/taglib"%>
	 *
	 * 2 添加js或css文件
	 *
	 * <win:static type="css" url="/css/test/absolute.css"/>
	 *
	 * 带参数url
	 * <win:static type="js" url="/js/test/with-param.js?param=234"/>
	 * */

    private static String rootDomain;

    private static String rootPath;

    private static String rootVersion;

    private static String jsDomain;

    private static String jsPath;

    private static String jsVersion;

    private static String cssDomain;

    private static String cssPath;

    private static String cssVersion;

    private static final Joiner newLineJoiner = Joiner.on("\n");
    private static final Joiner commaJoiner = Joiner.on(",");

    private String type;

    private String url;

    private static boolean merge = false;

    // 载入配置
    static {
        loadConfig();
    }

    protected static Properties readPropFile(String filePath) {
        try (InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath)) {
            Properties properties = new Properties();
            properties.load(inputStream);
            return properties;
        } catch (IOException e) {
            logger.error("读取静态文件配置异常, file={}", filePath);
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 载入配置
     */
    protected static void loadConfig() {
        String[] searchableFilePath = new String[] {
            "static.properties"
        };

        Properties properties = null;
        for (String filePath : searchableFilePath) {
            properties = readPropFile(filePath);
            if (properties != null) {
                break;
            }
        }

        if (properties == null) {
            logger.error("未读取到有效的静态文件配置");
            throw new RuntimeException("未读取到有效的静态文件配置");
        }

        String mergeValue = properties.getProperty("static.merge", "false");
        if ("true".equals(mergeValue)) {
            merge = true;
        }

        rootDomain = properties.getProperty("static.root.domain", StringUtils.EMPTY);
        rootPath = properties.getProperty("static.root.path", StringUtils.EMPTY);
        rootVersion = properties.getProperty("static.root.version", String.valueOf(System.currentTimeMillis()));

        jsDomain = properties.getProperty("static.js.domain", rootDomain);
        jsPath = properties.getProperty("static.js.path", rootPath);
        jsVersion = properties.getProperty("static.js.version", rootVersion);

        cssDomain = properties.getProperty("static.css.domain", rootDomain);
        cssPath = properties.getProperty("static.css.path", rootPath);
        cssVersion = properties.getProperty("static.css.version", rootVersion);
    }

    @Override
    public void doTag() throws JspException, IOException {
        getJspContext().getOut().println(this.getHtmlTag(this.url, this.type.toLowerCase()));
    }

    /**
     * html标签
     */
    protected String getHtmlTag(String url, String type) {
        if (StringUtils.isBlank(url)) {
            return StringUtils.EMPTY;
        }

        String pattern, domain, path, version;
        if ("js".equals(type)) {
            pattern = "<script type=\"text/javascript\" src=\"%s\"></script>";
            domain = jsDomain;
            path = jsPath;
            version = jsVersion;
        } else if ("css".equals(type)) {
            pattern = "<link rel=\"stylesheet\" type=\"text/css\" href=\"%s\" />";
            domain = cssDomain;
            path = cssPath;
            version = cssVersion;
        } else {
            return StringUtils.EMPTY;
        }

        // 最终输出行
        List<String> outputList = Lists.newArrayList();
        String[] urls = StringUtils.split(url, ",");


        List<String> cannotMergeUrlList;
        if (merge) {
            // 启用merge静态内容
            List<String> canMergeUrlList = Lists.newArrayList();
            cannotMergeUrlList = Lists.newArrayList();
            for (String s : urls) {
                // 不合并带参数的链接
                if (s.contains("?")) {
                    cannotMergeUrlList.add(s);
                } else {
                    canMergeUrlList.add(s);
                }
            }

            outputList.add(String.format(pattern, joinUrl("??" + commaJoiner.join(canMergeUrlList), domain, path, version, "?")));
        } else {
            // 否则全都不merge
            cannotMergeUrlList = Lists.newArrayList(urls);
        }

        for (String s : cannotMergeUrlList) {
            String connector = "?";
            if (s.contains("?")) {
                connector = "&";
            }
            outputList.add(String.format(pattern, joinUrl(s, domain, path, version, connector)));
        }

        return newLineJoiner.join(outputList);
    }

    protected String joinUrl(String url, String domain, String path, String version, String connector) {
        return String.format("%s%s%s%sv=%s", domain, path, url, connector, version);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
