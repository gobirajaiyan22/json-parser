package com.example.jsonparser.service;

import java.util.List;

@Deprecated
public interface JsonParserService {
    List<String> getCountryNameCodeMapping(String requestJson);

    List<String> getCountryNameListOld(String requestJson);

    List<String> getCountryNameList(String requestJson);
}
