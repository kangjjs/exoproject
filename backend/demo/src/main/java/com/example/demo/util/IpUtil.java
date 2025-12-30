package com.example.demo.util;

import jakarta.servlet.http.HttpServletRequest;

public class IpUtil {
    public static String getClientIp(HttpServletRequest request) {
        String xff = request.getHeader("X-Forwarded-For");
        if (xff != null && !xff.isBlank()) {
            // XFF는 "client, proxy1, proxy2" 형태일 수 있음 → 첫 번째가 진짜
            return xff.split(",")[0].trim();
        }
        String xrip = request.getHeader("X-Real-IP");
        if (xrip != null && !xrip.isBlank()) return xrip.trim();

        return request.getRemoteAddr();
    }
}
