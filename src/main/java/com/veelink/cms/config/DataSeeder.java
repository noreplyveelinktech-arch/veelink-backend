package com.veelink.cms.config;

import com.veelink.cms.entity.AboutContent;
import com.veelink.cms.entity.AdminUser;
import com.veelink.cms.entity.CompanySettings;
import com.veelink.cms.entity.Course;
import com.veelink.cms.entity.CourseCategory;
import com.veelink.cms.entity.HomeContent;
import com.veelink.cms.entity.HomeSection;
import com.veelink.cms.entity.TeamMember;
import com.veelink.cms.entity.enums.CourseStatus;
import com.veelink.cms.entity.enums.Role;
import com.veelink.cms.entity.enums.SectionType;
import com.veelink.cms.entity.enums.TrainingMode;
import com.veelink.cms.repository.AboutContentRepository;
import com.veelink.cms.repository.AdminUserRepository;
import com.veelink.cms.repository.CompanySettingsRepository;
import com.veelink.cms.repository.CourseCategoryRepository;
import com.veelink.cms.repository.CourseRepository;
import com.veelink.cms.repository.HomeContentRepository;
import com.veelink.cms.repository.HomeSectionRepository;
import com.veelink.cms.repository.TeamMemberRepository;
import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final AdminUserRepository adminUserRepository;
    private final CompanySettingsRepository companySettingsRepository;
    private final HomeContentRepository homeContentRepository;
    private final HomeSectionRepository homeSectionRepository;
    private final AboutContentRepository aboutContentRepository;
    private final CourseCategoryRepository courseCategoryRepository;
    private final CourseRepository courseRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        seedAdminUser();
        seedCompanySettings();
        seedHomeContent();
        seedHomeSections();
        seedAboutContent();
        seedCourseCategories();
        seedCourses();
        seedTeamMembers();
    }

    private void seedAdminUser() {
        if (adminUserRepository.count() == 0) {
            AdminUser adminUser = new AdminUser();
            adminUser.setName("Super Admin");
            adminUser.setEmail("admin@veelinktechnologies.com");
            adminUser.setPassword(passwordEncoder.encode("Admin@123"));
            adminUser.setRole(Role.SUPER_ADMIN);
            adminUser.setIsActive(Boolean.TRUE);
            adminUserRepository.save(adminUser);
            log.info("====================================================");
            log.info("Seeded default admin user");
            log.info("Email: admin@veelinktechnologies.com");
            log.info("Password: Admin@123");
            log.info("====================================================");
        }
    }

    private void seedCompanySettings() {
        if (companySettingsRepository.count() == 0) {
            CompanySettings settings = new CompanySettings();
            settings.setCompanyName("Veelink Technologies");
            settings.setTagline("Empowering careers through practical IT training");
            settings.setShortDescription("Industry-focused training institute offering software development and testing programs.");
            settings.setLongDescription("A modern, data-driven training institute CMS backend designed to power courses, enquiries, content, and team management.");
            settings.setEmail("info@veelinktechnologies.com");
            settings.setPhoneNumber("+91 90000 00000");
            settings.setWhatsappNumber("+91 90000 00000");
            settings.setAddress("Hyderabad, Telangana, India");
            settings.setGoogleMapsUrl("https://maps.google.com");
            settings.setWorkingHours("Mon - Sat, 9:00 AM - 7:00 PM");
            settings.setFacebookUrl("https://facebook.com/veelinktechnologies");
            settings.setInstagramUrl("https://instagram.com/veelinktechnologies");
            settings.setLinkedinUrl("https://linkedin.com/company/veelinktechnologies");
            settings.setYoutubeUrl("https://youtube.com/@veelinktechnologies");
            settings.setTwitterUrl("https://twitter.com/veelinktech");
            settings.setPrimaryEmail("veelinktechnologies@gmail.com");
            settings.setNoreplyEmail("noreply.veelinktech@gmail.com");
            settings.setEnquiryNotificationEmail("admissions@veelinktechnologies.com");
            settings.setEnquiryCcEmail("support@veelinktechnologies.com");
            settings.setEnquiryBccEmail(null);
            settings.setStudentConfirmationEnabled(Boolean.TRUE);
            settings.setEmailSenderName("Veelink Admissions Team");
            companySettingsRepository.save(settings);
        }
    }

    private void seedHomeContent() {
        if (homeContentRepository.count() == 0) {
            HomeContent homeContent = new HomeContent();
            homeContent.setHeroTitle("Build job-ready IT skills with expert-led training");
            homeContent.setHeroSubtitle("Hands-on learning for testing, Java, Spring Boot, and full stack development");
            homeContent.setHeroDescription("Launch or grow your technology career with practical courses, mentor support, and real project experience.");
            homeContent.setPrimaryButtonText("Explore Courses");
            homeContent.setPrimaryButtonLink("/courses");
            homeContent.setSecondaryButtonText("Enquire Now");
            homeContent.setSecondaryButtonLink("/enquiry");
            homeContent.setWhyChooseUsTitle("Why Choose Us");
            homeContent.setHighlightsTitle("Program Highlights");
            homeContentRepository.save(homeContent);
        }
    }

    private void seedHomeSections() {
        if (homeSectionRepository.count() == 0) {
            homeSectionRepository.saveAll(List.of(
                    section(SectionType.WHY_CHOOSE_US, "Experienced Trainers", "Learn from professionals with real project and mentoring experience.", 1),
                    section(SectionType.WHY_CHOOSE_US, "Practical Curriculum", "Every course blends theory with assignments, labs, and project work.", 2),
                    section(SectionType.WHY_CHOOSE_US, "Flexible Learning Modes", "Choose online, offline, or hybrid learning based on your schedule.", 3),
                    section(SectionType.WHY_CHOOSE_US, "Career Support", "Get interview preparation, resume guidance, and placement assistance.", 4),
                    section(SectionType.HIGHLIGHT, "Live Projects", "Work on industry-style assignments that strengthen your portfolio.", 1),
                    section(SectionType.HIGHLIGHT, "Updated Syllabus", "Stay current with modern tools, frameworks, and QA practices.", 2),
                    section(SectionType.HIGHLIGHT, "Small Batches", "Receive personalized attention and direct mentor interaction.", 3)
            ));
        }
    }

    private void seedAboutContent() {
        if (aboutContentRepository.count() == 0) {
            AboutContent aboutContent = new AboutContent();
            aboutContent.setPageTitle("About Our Institute");
            aboutContent.setPageSubtitle("Helping students and professionals gain practical technology skills");
            aboutContent.setAboutDescription("We focus on career-oriented IT training that bridges the gap between academic learning and industry expectations.");
            aboutContent.setMission("To deliver practical, affordable, and high-impact IT education for learners at every stage of their careers.");
            aboutContent.setVision("To become a trusted destination for modern software training and career transformation.");
            aboutContent.setValuesTitle("Our Core Values");
            aboutContentRepository.save(aboutContent);
        }
    }

    private void seedCourseCategories() {
        if (courseCategoryRepository.count() == 0) {
            CourseCategory testing = new CourseCategory();
            testing.setCategoryName("Testing");
            testing.setDescription("Software testing, QA, and automation programs.");
            testing.setIsActive(Boolean.TRUE);

            CourseCategory development = new CourseCategory();
            development.setCategoryName("Development");
            development.setDescription("Application development and backend engineering programs.");
            development.setIsActive(Boolean.TRUE);

            courseCategoryRepository.saveAll(List.of(testing, development));
        }
    }

    private void seedCourses() {
        if (courseRepository.count() == 0) {
            CourseCategory testing = courseCategoryRepository.findByCategoryNameIgnoreCase("Testing").orElseThrow();
            CourseCategory development = courseCategoryRepository.findByCategoryNameIgnoreCase("Development").orElseThrow();
            courseRepository.saveAll(List.of(
                    course("Manual Testing", "Quality Assurance", testing, "8 Weeks", TrainingMode.ONLINE, new BigDecimal("12000"), 1,
                            "Master test planning, defect life cycle, SDLC, STLC, and real-world QA documentation."),
                    course("Automation Testing (Selenium)", "Quality Assurance", testing, "10 Weeks", TrainingMode.HYBRID, new BigDecimal("18000"), 2,
                            "Learn Selenium WebDriver, Java basics, TestNG, Maven, and automation framework design."),
                    course("Java Development", "Software Development", development, "12 Weeks", TrainingMode.OFFLINE, new BigDecimal("22000"), 3,
                            "Build strong foundations in core Java, OOP, collections, multithreading, and JDBC."),
                    course("Spring Boot Development", "Software Development", development, "10 Weeks", TrainingMode.HYBRID, new BigDecimal("25000"), 4,
                            "Develop REST APIs, layered applications, JPA persistence, validation, and security using Spring Boot."),
                    course("Full Stack Development", "Software Development", development, "16 Weeks", TrainingMode.HYBRID, new BigDecimal("35000"), 5,
                            "Learn frontend and backend integration with modern JavaScript, Java APIs, databases, and deployment."),
                    course("Backend Development", "Software Development", development, "12 Weeks", TrainingMode.ONLINE, new BigDecimal("28000"), 6,
                            "Focus on API design, relational databases, security, performance, and scalable backend services."),
                    course("Microservices Development", "Software Development", development, "14 Weeks", TrainingMode.HYBRID, new BigDecimal("32000"), 7,
                            "Build distributed systems with service communication, discovery, resilience, and deployment patterns.")
            ));
        }
    }

    private void seedTeamMembers() {
        if (teamMemberRepository.count() == 0) {
            teamMemberRepository.saveAll(List.of(
                    teamMember("Ananya Rao", "Lead Trainer", "Specializes in QA, mentoring, and interview preparation.", 1),
                    teamMember("Rahul Verma", "Technical Mentor", "Experienced backend engineer focused on Java and Spring ecosystems.", 2),
                    teamMember("Sneha Iyer", "Career Success Manager", "Supports learners with planning, reviews, and career readiness.", 3)
            ));
        }
    }

    private HomeSection section(SectionType type, String title, String description, int order) {
        HomeSection section = new HomeSection();
        section.setSectionType(type);
        section.setTitle(title);
        section.setDescription(description);
        section.setDisplayOrder(order);
        section.setIsActive(Boolean.TRUE);
        return section;
    }

    private Course course(String name, String department, CourseCategory category, String duration, TrainingMode trainingMode,
                          BigDecimal fee, int displayOrder, String description) {
        Course course = new Course();
        course.setCourseName(name);
        course.setCourseDepartment(department);
        course.setCourseCategory(category);
        course.setDuration(duration);
        course.setTrainingMode(trainingMode);
        course.setFee(fee);
        course.setDisplayOrder(displayOrder);
        course.setCourseDescription(description);
        course.setStatus(CourseStatus.ACTIVE);
        return course;
    }

    private TeamMember teamMember(String fullName, String designation, String description, int displayOrder) {
        TeamMember member = new TeamMember();
        member.setFullName(fullName);
        member.setDesignation(designation);
        member.setDescription(description);
        member.setDisplayOrder(displayOrder);
        member.setIsActive(Boolean.TRUE);
        return member;
    }
}