package com.veelink.cms.dto.about;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AboutContentRequestDto {
    private String pageTitle;
    private String pageSubtitle;
    private String aboutDescription;
    private String mission;
    private String vision;
    private String aboutImageUrl;
    private String valuesTitle;
}