package com.elite;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class DefaultControllerAdvice {
    private TeamEchoInfo info;

    public DefaultControllerAdvice() {
        info = new TeamEchoInfo("1.0", "1.1.2015");
    }

    @ModelAttribute("info")
    public TeamEchoInfo info() {
        return info;
    }

    public static class TeamEchoInfo {
        private String version;
        private String date;

        public TeamEchoInfo(String version, String date) {
            this.version = version;
            this.date = date;
        }

        public String getVersion() {
            return version;
        }

        public String getDate() {
            return date;
        }
    }
}
