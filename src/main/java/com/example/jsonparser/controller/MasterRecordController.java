package com.example.jsonparser.controller;

import com.example.jsonparser.dto.RequestDto;
import com.example.jsonparser.service.MasterRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class MasterRecordController {
    private final MasterRecordService masterRecordService;
    @PostMapping("/namemapping")
    public List<String> getNameMapping(@RequestBody RequestDto requestDto){
        return masterRecordService.getSingleFieldList(requestDto);
    }

    @PostMapping("/namecodemapping")
    public List<String> getNameCodeMapping(@RequestBody RequestDto requestDto){
        return masterRecordService.getCombinedDataList(requestDto);
    }
}
