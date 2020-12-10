package com.fz.zf.config.xss;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;

public class JsoupUtils {

    /**
     * 使用自带的basicWithImages 白名单
     * 允许的便签有a,b,blockquote,br,cite,code,dd,dl,dt,em,i,li,ol,p,pre,q,small,span,
     * strike,strong,sub,sup,u,ul,img
     * 以及a标签的href,img标签的src,align,alt,height,width,title属性
     */
    /**
     * relaxed() 允许的标签:
     * a, b, blockquote, br, caption, cite, code, col, colgroup, dd, dl, dt, em, h1, h2, h3, h4,
     * h5, h6, i, img, li, ol, p, pre, q, small, strike, strong, sub, sup, table, tbody, td, tfoot, th, thead, tr, u, ul。
     * 结果不包含标签rel=nofollow ，如果需要可以手动添加。
     */
    private static final Whitelist WHITELIST = Whitelist.basicWithImages();

    /**
     * 配置过滤化参数,不对代码进行格式化
     */
    private static final Document.OutputSettings OUTPUT_SETTINGS = new Document.OutputSettings().prettyPrint(false);

    static {
        /**
         * addTags() 设置白名单标签
         * addAttributes()  设置标签需要保留的属性 ,[:all]表示所有
         * preserveRelativeLinks()  是否保留元素的URL属性中的相对链接，或将它们转换为绝对链接,
         * 默认为false. 为false时将会把baseUri和元素的URL属性拼接起来
         */
        WHITELIST.addAttributes(":all", "style");
        WHITELIST.preserveRelativeLinks(true);
    }

    public static String clean(String content) {
        if (StringUtils.isBlank(content)) {
            return "";
        }

        return Jsoup.clean(content, "", WHITELIST, OUTPUT_SETTINGS);
    }


}
