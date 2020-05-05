package org.sudhs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.sudhs.model.DeltaFormat;

import java.util.HashMap;
import java.util.Map;

public class JsonDelta {
    public String findDelta(String firstJson, String secondJson) throws JsonProcessingException {
        // 1. Convert the JSON into Map
        ObjectMapper objectMapper = new ObjectMapper();
        final Map<String, Object> firstJsonMap ;
        final Map<String, Object> secondJsonMap ;
        Map<String, Object> jsonMap1 = objectMapper.readValue(
                firstJson, new TypeReference<Map<String,Object>>(){});
        Map<String, Object> jsonMap2 = objectMapper.readValue(
                secondJson, new TypeReference<Map<String,Object>>(){});

        firstJsonMap = convertJsonToMap(jsonMap1);
        System.out.println("First Json Map :: " + firstJsonMap);
        secondJsonMap = convertJsonToMap(jsonMap2);
        System.out.println("Second Json Map :: " + firstJsonMap);

        // 2. Find the difference between the 2 Maps
        Map<String, DeltaFormat> deltaJsonMap = new HashMap<>();

        for(String key : firstJsonMap.keySet()) {
            if(!firstJsonMap.get(key).equals(secondJsonMap.get(key))) {
                DeltaFormat format = new DeltaFormat(key, (String)firstJsonMap.get(key), (String)secondJsonMap.get(key));
                deltaJsonMap.put(key, format);
            }
        }

        // 3. Construct the JSON from the delta map
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode arrayNode = mapper.createArrayNode();
        for(String key : deltaJsonMap.keySet()) {
            ObjectNode innerNode = mapper.valueToTree(deltaJsonMap.get(key));
            arrayNode.add(innerNode);
        }
        System.out.println(" THE FINAL DELTA JSON IS ==> " + arrayNode.toPrettyString());
        return arrayNode.toPrettyString();
    }

    private Map<String, Object> convertJsonToMap(Map<String, Object> jsonMap) {
        return convertJsonToMap(jsonMap, "");
    }

    private Map<String, Object> convertJsonToMap(Map<String, Object> jsonMap, String currKey) {
        Map<String, Object> finalValueMap = new HashMap<>();
        for(String key : jsonMap.keySet()) {
            if(jsonMap.get(key) instanceof String){
                String valueKey = currKey.concat(key);
                System.out.println("Instance of String :: key :: " + key);
                finalValueMap.put(valueKey, jsonMap.get(key));
            } else if(jsonMap.get(key) instanceof Map) {
                currKey = currKey.concat(key).concat(".");
                System.out.println("Instance of Map :: key :: " + key);
                finalValueMap.putAll(convertJsonToMap((Map<String, Object>) jsonMap.get(key), currKey));
            }
        }
        return finalValueMap;
    }

}