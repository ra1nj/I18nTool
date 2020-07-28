package util;

import com.alibaba.fastjson.JSONArray;
import java.io.IOException;

public class TranslateUtil  {
    public String toEnglish(String content) throws IOException{
        return translate(content,"en");
    }

    public String toVietnamese(String content) throws IOException{
        return translate(content,"vi");
    }

    public String toKoren(String content) throws IOException{
        return translate(content,"ko");
    }

    public String translate(String content, String to) throws IOException {
        String res = "";
        StringBuffer sb = new StringBuffer();
        sb.append("https://translate.google.cn/translate_a/single?q=");
        String encodedText = java.net.URLEncoder.encode(content, "UTF-8");
        sb.append(encodedText);
        sb.append("&sl=").append("auto");
        sb.append("&tl=").append(to);
        sb.append("&hl=").append(to);
        sb.append("&client=").append("t");
        sb.append("&ie=").append("UTF-8");
        sb.append("&oe=").append("UTF-8");
        sb.append("&otf=").append(1);
        sb.append("&ssel=").append(0);
        sb.append("&tsel=").append(0);
        sb.append("&kc=").append(7);
        String[] dts = {"at", "bd", "ex", "ld", "md", "qca", "rw", "rm", "ss", "t"};
        for(String dt:dts ){
            sb.append("&dt=").append(dt);
        }
        GoogleTranslateToken gtt = new GoogleTranslateToken();
        String token = gtt.getToken(content);
        sb.append("&tk=").append(token);
        HttpClient3 httpClient = new HttpClient3();
        String httpRes =  httpClient.doGet(sb.toString());
        if(httpRes != null){
           res = JSONArray.parseArray(httpRes).getJSONArray(0).getJSONArray(0).get(0).toString();
        }
        return res;
    }

}

