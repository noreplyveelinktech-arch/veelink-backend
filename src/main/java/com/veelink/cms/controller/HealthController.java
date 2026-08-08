package com.veelink.cms.controller;

import com.veelink.cms.dto.common.MessageResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Public, unauthenticated health check used by uptime/"keep-alive" ping
 * services (e.g. cron-job.org, UptimeRobot) to prevent free-tier hosts
 * such as Render from spinning the service down after inactivity.
 */
@RestController
@RequestMapping("/api/health")
public class HealthController {

    @GetMapping
    public MessageResponse health() {
        return new MessageResponse("OK");
    }
}
