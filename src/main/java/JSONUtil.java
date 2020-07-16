import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.io.*;
import java.util.Map;

public class JSONUtil {
    /**
     * 读取json文件，返回json串
     * @param fileName
     * @return
     */
    public static String readJsonFile(String fileName) {
        String jsonStr = "";
        try {
            File jsonFile = new File(fileName);
            FileReader fileReader = new FileReader(jsonFile);

            Reader reader = new InputStreamReader(new FileInputStream(jsonFile),"utf-8");
            int ch = 0;
            StringBuffer sb = new StringBuffer();
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            fileReader.close();
            reader.close();
            jsonStr = sb.toString();
            return jsonStr;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String findKeyInJSONFile(String fileName, String content){
        String jsonStr = JSONUtil.readJsonFile(fileName);
        if(jsonStr != null){
            JSONObject jsonObj = JSONObject.parseObject(jsonStr);
            Map map = jsonObj.getInnerMap();
            return getKeyByValue(content,map);
        }else{
            return null;
        }
    }

    public static String getKeyByValue(String value, Map<String,String> map){
        for(String key : map.keySet()){
            if(value.equals(map.get(key))){
                return key;
            }
        }
        return null;
    }

    public static void writeInJSONFile(String fileName, String key, String value) throws IOException {
        JSONObject jsonObject = null;
        File file = new File(fileName);
        if(!file.exists()){
            if(!file.getParentFile().exists()){
                file.getParentFile().mkdir();
            }
            file.createNewFile();
            jsonObject = new JSONObject();
        }else{
            jsonObject = JSONObject.parseObject(readJsonFile(fileName));
        }
        jsonObject.put(key,value);
        String jsonStr = jsonObject.toString(SerializerFeature.PrettyFormat);
        FileOutputStream fos = new FileOutputStream(file);
        OutputStreamWriter osw = new OutputStreamWriter(fos);
        BufferedWriter bw = new BufferedWriter(osw);
        bw.write(jsonStr);
        bw.flush();
        bw.close();
    }



//    public static String getNewKeyInJSONFile(String fileName){
//        String jsonStr = readJsonFile(fileName);
//        if(jsonStr != null){
//            JSONObject jsonObj = JSONObject.parseObject(jsonStr);
//
//        }
//        return null;
//    }

}
