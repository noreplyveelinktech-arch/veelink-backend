package com.veelink.cms.controller;

import com.veelink.cms.dto.common.MessageResponse;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Public, unauthenticated health check used by uptime/"keep-alive" ping
 * services (e.g. cron-job.org, UptimeRobot) to prevent free-tier hosts
 * such as Render from spinning the service down after inactivity.
 *
 * Also runs a trivial query against the DataSource so the same ping keeps a
 * serverless database (e.g. Neon free tier) from auto-suspending its compute
 * or silently closing idle pooled connections between real user requests.
 */
@Slf4j
@RestController
@RequestMapping("/api/health")
@RequiredArgsConstructor
public class HealthController {

    private final DataSource dataSource;

    @GetMapping
    public MessageResponse health() {
        try (var connection = dataSource.getConnection(); var statement = connection.createStatement()) {
            statement.execute("SELECT 1");
        } catch (Exception ex) {
            log.warn("Health check database ping failed: {}", ex.getMessage());
            return new MessageResponse(false, "DB_UNREACHABLE");
        }
        return new MessageResponse("OK");
    }
}
