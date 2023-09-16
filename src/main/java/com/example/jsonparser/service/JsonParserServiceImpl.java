package com.example.jsonparser.service;

import com.example.jsonparser.config.ParserConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Deprecated
public class JsonParserServiceImpl implements JsonParserService {
    private static final String       MATCH_ATTRIBUTE_VALUE_KEY = "attributeValue";
    private final        ParserConfig parserConfig;
    private final        ObjectMapper objectMapper;

    @Override
    public List<String> getCountryNameCodeMapping(String requestJson) {
        List<String> resultList = new ArrayList<>();
        JsonNode node = readJson(requestJson);
        //        processJson(resultList, node, parserConfig.getNameAndCode().split(":"));
        return null;
    }

    private void processJson(List<String> resultList, JsonNode node, String matchField, AtomicBoolean isFieldPresent) {
        var iterator = node.fields();
        while (iterator.hasNext()) {
            var currentNode = iterator.next();
            processValue(resultList, matchField, currentNode.getValue(), isFieldPresent);
            if (isFieldPresent.get()) {
                isFieldPresent.set(false);
                if (node.has(MATCH_ATTRIBUTE_VALUE_KEY) && node
                    .get(MATCH_ATTRIBUTE_VALUE_KEY)
                    .isTextual()) {
                    resultList.add(node
                        .get(MATCH_ATTRIBUTE_VALUE_KEY)
                        .textValue());
                }
            }
        }
    }

    private void processValue(List<String> resultList, String matchField, JsonNode currentNodeValue, AtomicBoolean isFieldPresent) {
        if (currentNodeValue.isObject()) {
            processJson(resultList, currentNodeValue, matchField, isFieldPresent);
        } else if (currentNodeValue.isArray()) {
            ArrayNode arrayNode = (ArrayNode) currentNodeValue;
            for (JsonNode currentArrayNode : arrayNode) {
                processValue(resultList, matchField, currentArrayNode, isFieldPresent);
            }
        } else {
            if (currentNodeValue.isTextual() && currentNodeValue
                .textValue()
                .equalsIgnoreCase(matchField)) {
                isFieldPresent.set(true);
            }
        }
    }

    @Override
    public List<String> getCountryNameListOld(String requestJson) {
        List<String> resultList = new ArrayList<>();
        JsonNode node = readJson(requestJson);
        AtomicBoolean isFieldPresent = new AtomicBoolean(false);
        processJson(resultList, node, parserConfig.getField(), isFieldPresent);
        return resultList;
    }

    @Override public List<String> getCountryNameList(String requestJson) {
        return null;
    }

    private JsonNode readJson(String requestJson) {
        try {
            return objectMapper.readTree(requestJson);
        }
        catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
