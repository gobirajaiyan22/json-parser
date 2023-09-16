package com.example.jsonparser.service;

import com.example.jsonparser.config.ParserConfig;
import com.example.jsonparser.dto.FieldDetail;
import com.example.jsonparser.dto.MasterRecordList;
import com.example.jsonparser.dto.RequestDto;
import io.micrometer.common.util.StringUtils;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MasterRecordServiceImpl implements MasterRecordService {
    private final ParserConfig parserConfig;

    @Override public List<String> getSingleFieldList(RequestDto requestDto) {
        return getSingleFieldList(requestDto.getMasterRecordList(), parserConfig.getField());
    }

    @Override public List<String> getCombinedDataList(RequestDto requestDto) {
        return getCombinedCodeList(requestDto.getMasterRecordList(), parserConfig.getCombinedField());
    }

    private List<String> getCombinedCodeList(List<MasterRecordList> masterRecordList, String fieldConfig) {
        var configArrayList = Arrays
            .stream(fieldConfig.split(":"))
            .toList();
        return masterRecordList
            .stream()
            .map(record -> {
                AtomicReference<String> result = new AtomicReference<>("");
                record
                    .getFieldDetails()
                    .stream()
                    .filter(fieldDetail -> StringUtils.isNotBlank(fieldDetail.getAttributeValue()) && configArrayList.contains(fieldDetail
                        .getAttributeCode()))
                    .forEach(field -> {
                        if (field
                            .getAttributeCode()
                            .equalsIgnoreCase(configArrayList.get(0))) {
                            result.set(field.getAttributeValue() + result.get());
                        } else {
                            result.set(result.get() + " (" + field.getAttributeValue() + ")");
                        }
                    });
                return result.get();

            })
            .collect(Collectors.toList());
    }

    private List<String> getSingleFieldList(List<MasterRecordList> masterRecordList, String fieldConfig) {
        return masterRecordList
            .stream()
            .flatMap(record -> record
                .getFieldDetails()
                .stream()
                .filter(fieldDetail -> fieldDetail
                    .getAttributeCode()
                    .equalsIgnoreCase(fieldConfig))
                .map(
                    FieldDetail::getAttributeValue))
            .collect(Collectors.toList());
    }
}
