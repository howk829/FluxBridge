package com.tho.fluxbridgebroker.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Builder
@Getter
public class DataSpec {
    Map<String, Object> metadata;
    String message;
}