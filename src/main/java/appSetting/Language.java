package appSetting;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.lf5.util.ResourceUtils;
import util.JSONUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class Language {
    public static String langJSON = "{'af':'Afrikaans','sq':'Albanian','am':'Amharic','ar':'Arabic','hy':'Armenian','az':'Azerbaijani','eu':'Basque','be':'Belarusian','bn':'Bengali','bs':'Bosnian','bg':'Bulgarian','ca':'Catalan','ceb':'Cebuano','ny':'Chichewa','zh-cn':'Chinese Simplified','zh-tw':'Chinese Traditional','co':'Corsican','hr':'Croatian','cs':'Czech','da':'Danish','nl':'Dutch','en':'English','eo':'Esperanto','et':'Estonian','tl':'Filipino','fi':'Finnish','fr':'French','fy':'Frisian','gl':'Galician','ka':'Georgian','de':'German','el':'Greek','gu':'Gujarati','ht':'Haitian Creole','ha':'Hausa','haw':'Hawaiian','iw':'Hebrew','hi':'Hindi','hmn':'Hmong','hu':'Hungarian','is':'Icelandic','ig':'Igbo','id':'Indonesian','ga':'Irish','it':'Italian','ja':'Japanese','jw':'Javanese','kn':'Kannada','kk':'Kazakh','km':'Khmer','ko':'Korean','ku':'Kurdish (Kurmanji)','ky':'Kyrgyz','lo':'Lao','la':'Latin','lv':'Latvian','lt':'Lithuanian','lb':'Luxembourgish','mk':'Macedonian','mg':'Malagasy','ms':'Malay','ml':'Malayalam','mt':'Maltese','mi':'Maori','mr':'Marathi','mn':'Mongolian','my':'Myanmar (Burmese)','ne':'Nepali','no':'Norwegian','ps':'Pashto','fa':'Persian','pl':'Polish','pt':'Portuguese','ma':'Punjabi','ro':'Romanian','ru':'Russian','sm':'Samoan','gd':'Scots Gaelic','sr':'Serbian','st':'Sesotho','sn':'Shona','sd':'Sindhi','si':'Sinhala','sk':'Slovak','sl':'Slovenian','so':'Somali','es':'Spanish','su':'Sundanese','sw':'Swahili','sv':'Swedish','tg':'Tajik','ta':'Tamil','te':'Telugu','th':'Thai','tr':'Turkish','uk':'Ukrainian','ur':'Urdu','uz':'Uzbek','vi':'Vietnamese','cy':'Welsh','xh':'Xhosa','yi':'Yiddish','yo':'Yoruba','zu':'Zulu'}";

    public List<LanguageVO> langList = new ArrayList<>();

    public Language(){
        Map<String,Object> langMap = JSONObject.parseObject(langJSON, Feature.OrderedField).getInnerMap();
        for (Map.Entry<String , Object> entry : langMap.entrySet()) {
            System.out.println("key = " + entry.getKey() + ", value = " + entry.getValue().toString());
            this.langList.add(new LanguageVO(entry.getKey(),entry.getValue().toString()));
        }
    }
}