package codesecurity.api.request;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

public abstract class CodeSecurityCreateRequest {
    private String hygieiaId;
    @NotNull
    private long timestamp;
    @NotNull
    private String projectName;
    @NotNull
    private String projectUrl;
    @NotNull
    private String serverUrl;
    @NotNull
    private Map<String, String> metrics = new HashMap<>();

    public String getHygieiaId() {
        return hygieiaId;
    }

    public void setHygieiaId(String hygieiaId) {
        this.hygieiaId = hygieiaId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectUrl() {
        return projectUrl;
    }

    public void setProjectUrl(String projectUrl) {
        this.projectUrl = projectUrl;
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public Map<String, String> getMetrics() {
        return metrics;
    }

    public void setMetrics(Map<String, String> metrics) { this.metrics = metrics; }

}
