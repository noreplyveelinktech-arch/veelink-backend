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
@Table(name = "home_content")
public class HomeContent extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "hero_title", length = 255)
    private String heroTitle;

    @Column(name = "hero_subtitle", length = 255)
    private String heroSubtitle;

    @Column(name = "hero_description", columnDefinition = "TEXT")
    private String heroDescription;

    @Column(name = "hero_image_url", length = 500)
    private String heroImageUrl;

    @Column(name = "primary_button_text", length = 100)
    private String primaryButtonText;

    @Column(name = "primary_button_link", length = 255)
    private String primaryButtonLink;

    @Column(name = "secondary_button_text", length = 100)
    private String secondaryButtonText;

    @Column(name = "secondary_button_link", length = 255)
    private String secondaryButtonLink;

    @Column(name = "why_choose_us_title", length = 255)
    private String whyChooseUsTitle;

    @Column(name = "highlights_title", length = 255)
    private String highlightsTitle;
}