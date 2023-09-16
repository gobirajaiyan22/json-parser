package com.example.jsonparser.service;

import com.example.jsonparser.dto.RequestDto;
import java.util.List;

public interface MasterRecordService {
    List<String> getSingleFieldList(RequestDto requestDto);

    List<String> getCombinedDataList(RequestDto requestDto);
}
