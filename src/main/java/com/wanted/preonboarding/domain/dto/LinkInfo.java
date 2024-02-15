package com.wanted.preonboarding.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@JsonInclude(Include.NON_NULL)
public class LinkInfo {
  private String  link;
  private String  method;
  private String  description;
  private Map<String, Object> schema;
}
