package appSetting;

import com.alibaba.fastjson.JSONObject;
import util.JSONUtil;


public class Language {
    public static JSONObject langMap = JSONObject.parseObject(JSONUtil.readJsonFile("lang.json"));


}
