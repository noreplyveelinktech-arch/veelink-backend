package com.veelink.cms.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "about_content")
public class AboutContent extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "page_title", length = 255)
    private String pageTitle;

    @Column(name = "page_subtitle", length = 255)
    private String pageSubtitle;

    @Column(name = "about_description", columnDefinition = "TEXT")
    private String aboutDescription;

    @Column(columnDefinition = "TEXT")
    private String mission;

    @Column(columnDefinition = "TEXT")
    private String vision;

    @Column(name = "about_image_url", length = 500)
    private String aboutImageUrl;

    @Column(name = "values_title", length = 255)
    private String valuesTitle;
}