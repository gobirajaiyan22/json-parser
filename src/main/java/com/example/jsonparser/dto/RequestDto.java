package com.example.jsonparser.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RequestDto {
    private String                 companyCode;
    private String                 entityCode;
    @NonNull
    private List<MasterRecordList> masterRecordList;
}