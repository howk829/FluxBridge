package com.tho.fluxbridge.common.dto;

import lombok.*;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DataSpec {
    Map<String, Object> metadata;
    String message;
}