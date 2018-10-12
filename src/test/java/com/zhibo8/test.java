package com.zhibo8;

import org.apache.commons.lang.StringUtils;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: 19shusheng.com
 * @Description:
 * @Date: Created in 11:43 2018/8/2
 * @Modified By:
 */
public class test {
    public static void main(String[] args) {
        String colums="\n" +
                "from,url,type,channel,content_type,\n" +
                "from,url,type,content_type,channel,\n" +
                "from,title,url,type,\n" +
                "from,\n" +
                "from,\n" +
                "from,\n" +
                ",\n" +
                ",\n" +
                "tab,subtab,from,matchid,type,\n" +
                "tab,subtab,fron,duration,\n" +
                "from,\n" +
                "from,duration,\n" +
                "from,\n" +
                "from,\n" +
                "from,\n" +
                "from,\n" +
                "from,\n" +
                "from,duration,\n" +
                "from,\n" +
                "from,\n" +
                "from,type,\n" +
                "from,\n" +
                "from,duration,\n" +
                "usercode,\n" +
                "usercode,\n" +
                "from,url,type,\n" +
                "label,url,type,model,from,content,\n" +
                ",\n" +
                "duration,\n" +
                "label,\n" +
                "from,\n" +
                "from,duration,\n" +
                "name,\n" +
                "name,\n" +
                "tab,matchid,url,type,from(入口),visit_team,home_team,action,\n" +
                "from,\n" +
                "from,duration,\n" +
                "tab,\n" +
                "tab,duration,\n" +
                ",\n" +
                ",\n" +
                ",\n" +
                ",\n" +
                "usercode,type,from,\n" +
                "usercode,type,from,duration,\n" +
                "usercode,\n" +
                "usercode,\n" +
                "usercode,\n" +
                "usercode,\n" +
                "scheme_id,from,matchid,\n" +
                "scheme_id,duration,from,matchid,\n" +
                ",\n" +
                "\n" +
                "matchid\n" +
                "matchid,duration,\n" +
                "tab,matchid,url,type,from(入口),visit_team,home_team,action,\n" +
                "title,url,type,visit_team,home_team,list,channel_url,matchid,\n" +
                "title,url,type,from(入口),\n" +
                "title,url,type,from(入口),\n" +
                "title,url,type,from(入口),\n" +
                "title,url,type,list,channel_url,\n" +
                "title,tid,type,from(入口),\n" +
                "title,url,type,from(入口),\n" +
                "title,url,\n" +
                "title,url,type,from(入口),\n" +
                "tab,from,subtab,\n" +
                "tab,from,\n" +
                "tab,from,\n" +
                "tab,from,\n" +
                "url,from,\n" +
                "tab,sidebar,subtab,from,\n" +
                "tab,from,\n" +
                "from,\n" +
                "from,\n" +
                "from,\n" +
                "tab,from,\n" +
                "topic_id,from,title,\n" +
                "title,url,from,\n" +
                "product_id,from,title,\n" +
                "title,url,type,from(入口),tag,\n" +
                "title,from,author_id,\n" +
                "from,\n" +
                "tab,from,\n" +
                "from ,\n" +
                "tab,from,\n" +
                "from ,\n" +
                "from(入口),\n" +
                "from(入口),\n" +
                "matchid,\n" +
                "matchid,\n" +
                "usercode,type,from,\n" +
                ",\n" +
                "scheme_id,duration,from,matchid,\n" +
                ",\n" +
                ",\n" +
                ",\n" +
                ",\n" +
                "matchid,\n" +
                "from(入口),\n" +
                "from(入口),duration,\n" +
                "from(入口),\n" +
                "from(入口),duration,\n" +
                "title,url,type,from(入口),\n" +
                "title,url,type,from(入口),duration,\n" +
                "title,url,type,from,\n" +
                "type,\n" +
                "type,\n" +
                "type,\n" +
                "type,\n" +
                ",\n" +
                ",\n" +
                ",\n" +
                "type,\n" +
                "type,\n" +
                "type,\n" +
                "matchid,\n" +
                "matchid,duration,\n" +
                "matchid,\n" +
                "matchid,duration,\n" +
                "usercode,type,from,\n" +
                "usercode,type,from,\n" +
                "scheme_id,\n" +
                ",\n" +
                ",\n" +
                "duration,\n" +
                "scheme_id,duration,from,matchid,\n" +
                "scheme_id,duration,from,matchid,\n" +
                ",\n" +
                ",\n" +
                ",\n" +
                "duration,\n" +
                ",\n" +
                "duration,\n" +
                "type,\n" +
                ",\n" +
                "duration,\n" +
                ",\n" +
                "duration,\n" +
                ",\n" +
                ",\n" +
                "matchid,\n" +
                "matchid,\n" +
                "matchid,\n" +
                "matchid,\n" +
                "matchid,duration,\n" +
                "matchid,\n" +
                "matchid,\n" +
                "matchid,\n" +
                "matchid,\n" +
                "matchid,duration,\n" +
                "matchid,\n" +
                "matchid,\n" +
                "from,tab,subtab,sidebar,type,\n" +
                ",\n" +
                "from,\n" +
                "duration,from,\n" +
                "type,\n" +
                "type,duration,\n" +
                "from,\n" +
                "from,\n" +
                "from,\n" +
                "from,\n" +
                "duration,from,\n" +
                "title,url,type,from,tag,\n" +
                "title,url,type,from,tag,\n" +
                "title,url,type,from,tag,\n" +
                "title,url,type,from,tag,\n" +
                "tab,from,\n" +
                "tab,from,duration,\n" +
                "from,\n" +
                "duration,from,\n" +
                "tab,from,\n" +
                "tab,from,duration,\n" +
                "from,\n" +
                "duration,from,\n" +
                "tab,matchid,url,type,from(入口),visit_team,home_team,action,\n" +
                "tab,matchid,url,type,from(入口),visit_team,home_team,duration,\n" +
                "tab,matchid,url,type,visit_team,home_team,duration,\n" +
                "title,url,,list,channel_url,matchid,from,\n" +
                "title,url,type,visit_team,home_team,list,channel_url,matchid,\n" +
                "title,url,type,visit_team,home_team,list,channel_url,duration,matchid,\n" +
                "matchid,type,visit_team,home_team,duration,url,\n" +
                "title,url,type,from(入口),\n" +
                "title,url,type,from(入口),duration,\n" +
                "title,url,type,from(入口),\n" +
                "title,url,type,from(入口),duration,\n" +
                "title,url,type,from(入口),\n" +
                "title,url,type,from(入口),duration,\n" +
                "title,url,,list,channel_url,\n" +
                "title,url,type,list,channel_url,\n" +
                "title,url,type,,list,channel_url,duration,\n" +
                "title,tid,type,from(入口),\n" +
                "title,tid,type,from(入口),duration,\n" +
                "title,url,type,from(入口),\n" +
                "title,url,type,from(入口),duration,\n" +
                "title,url,\n" +
                "title,url,type,from(入口),\n" +
                "title,url,type,from(入口),duration,\n" +
                "tab,from,subtab,\n" +
                "tab,from,duration,subtab,\n" +
                "tab,from,\n" +
                "tab,from,duration,\n" +
                "tab,from,\n" +
                "tab,from,duration,\n" +
                "tab,from,\n" +
                "tab,from,duration,\n" +
                "url,from,\n" +
                "url,duration,\n" +
                "tab,sidebar,subtab,from,\n" +
                "sidebar,subtab,tab,duration,\n" +
                "matchid,url,type,from(入口),\n" +
                "matchid,url,type,from(入口),\n" +
                "matchid,url,type,from(入口),\n" +
                "matchid,url,type,from(入口),\n" +
                "matchid,url,type,from(入口),\n" +
                "matchid,url,type,from(入口),\n" +
                "tab,from,\n" +
                "tab,from,duration,\n" +
                "duration,from,\n" +
                "name,\n" +
                "from,\n" +
                "from,\n" +
                "from,\n" +
                "from,\n" +
                "from,\n" +
                "from,duration,\n" +
                "name,\n" +
                "name,\n" +
                "from,duration,\n" +
                "from,\n" +
                "tab,from,\n" +
                "tab,from,duration,\n" +
                "duration,from,\n" +
                "from,\n" +
                "from,\n" +
                "from,\n" +
                "topic_id,from,title,\n" +
                "topic_id,from,duration,title,\n" +
                "title,url,from,\n" +
                "title,url,from,duration,\n" +
                "product_id,from,title,type,\n" +
                "product_id,from,title,\n" +
                "product_id,from,duration,title,\n" +
                "title,url,type,from(入口),tag,\n" +
                "title,url,type,from(入口),duration,tag,\n" +
                "title,url,type,from,tag,\n" +
                "title,url,type,duration,tag,\n" +
                "title,url,type,duration,tag,\n" +
                "title,from,author_id,\n" +
                "title,from,duration,,author_id,\n" +
                ",\n" +
                ",\n" +
                ",\n" +
                ",\n" +
                ",\n" +
                ",\n" +
                ",\n" +
                ",\n" +
                ",\n" +
                ",";

        String replaceBlank = replaceBlank(colums);
        String[] colsArr = replaceBlank.split(",");
        Set<String> colsSet = new HashSet<>();
        for (String col : colsArr) {
            if(StringUtils.isNotBlank(col)){
                colsSet.add(col);
            }
        }
        System.out.println("字段数量="+colsSet.size());
        for (String col : colsSet) {
            System.out.println(col);
        }
    }

    public static String replaceBlank(String str) {
        String dest = "";
        if (str!=null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

}
