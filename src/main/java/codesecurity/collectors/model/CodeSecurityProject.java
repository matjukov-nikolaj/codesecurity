package codesecurity.collectors.model;

import com.capitalone.dashboard.model.CollectorItem;

public abstract class CodeSecurityProject extends CollectorItem {

    public static final String INSTANCE_URL = "instanceUrl";
    public static final String PROJECT_NAME = "projectName";
    public static final String PROJECT_DATE = "projectDate";
    public static final String CURRENT = "current";

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

    public String getProjectDate() {
        return (String) getOptions().get(PROJECT_DATE);
    }

    public void setProjectDate(String date) {
        getOptions().put(PROJECT_DATE, date);
    }

    public String getCurrentProject() {
        return (String) getOptions().get(CURRENT);
    }

    public void setCurrentProject(Boolean current) {
        getOptions().put(CURRENT, current);
    }


}
