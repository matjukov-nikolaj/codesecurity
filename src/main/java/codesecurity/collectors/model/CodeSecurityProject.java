package codesecurity.collectors.model;

import com.capitalone.dashboard.model.CollectorItem;

public abstract class CodeSecurityProject extends CollectorItem {

    public static final String INSTANCE_URL = "instanceUrl";
    public static final String PROJECT_NAME = "projectName";

    public String getInstanceUrl() {
        return (String) getOptions().get(INSTANCE_URL);
    }

    public void setInstanceUrl(String instanceUrl) {
        getOptions().put(INSTANCE_URL, instanceUrl);
    }

    public String getProjectName() {
        return (String) getOptions().get(PROJECT_NAME);
    }

    public void setProjectName(String name) {
        getOptions().put(PROJECT_NAME, name);
    }

}
