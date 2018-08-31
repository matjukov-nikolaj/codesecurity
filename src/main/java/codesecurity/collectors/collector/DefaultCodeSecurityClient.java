package codesecurity.collectors.collector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public abstract class DefaultCodeSecurityClient<T, P> {
    protected static final Log LOG = LogFactory.getLog(DefaultCodeSecurityClient.class);

    private static final String PROTOCOL_SPLITTER = "://";

    public abstract P getProject();

    public abstract T getCurrentMetrics(P project);

    public void parseDocument(String instanceUrl) {
        try {
            this.initializationFields();
            instanceUrl = getUrlWithUserData(instanceUrl);
            Document document = getDocument(instanceUrl);
            if (document != null) {
                parseCodeSecurityDocument(document);
                setInstanceUrlInProject(instanceUrl);
            }
        } catch (Exception e) {
            LOG.error(e);
        }
    }

    protected abstract void initializationFields();

    protected abstract void parseCodeSecurityDocument(Document document) throws Exception;

    protected abstract void setInstanceUrlInProject(String instanceUrl);

    protected abstract String getDateFormat();

    protected abstract String getUsernameFromSettings();

    protected abstract String getPasswordFromSettings();

    protected long getTimeStamp(String timestamp) {
        if (!timestamp.equals("")) {
            try {
                Date date = getDate(timestamp);
                return date != null ? date.getTime() : 0;
            } catch (NullPointerException e) {
                LOG.error(e);
            }
        }
        return 0;
    }

    protected Date getDate(String timestamp) {
        try {
            return new SimpleDateFormat(getDateFormat(), Locale.ENGLISH).parse(timestamp);
        } catch (java.text.ParseException e) {
            LOG.error(timestamp + " is not in expected format " + getDateFormat(), e);
        }
        return null;
    }

    protected Document getDocument(String instanceUrl) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            URL url = new URL(instanceUrl);
            Document document = db.parse(url.openStream());
            document.getDocumentElement().normalize();
            return document;
        } catch (Exception e) {
            LOG.error("Could not parse document from: " + instanceUrl, e);
        }
        return null;
    }

    protected String getProjectDate(String testingDate) {
        Date date = getDate(testingDate);
        if (date == null) {
            return "";
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 1);
        return  calendar.get(Calendar.YEAR) + "-"
                + calendar.get(Calendar.MONTH) + "-"
                + calendar.get(Calendar.DAY_OF_MONTH);
    }

    protected String getUrlWithUserData(String url) {
        StringBuilder strBuilderUrl = new StringBuilder(url);
        String username = getUsernameFromSettings();
        String password = getPasswordFromSettings();
        if (!username.isEmpty() && !password.isEmpty()) {
            int indexOfProtocolEnd = strBuilderUrl.lastIndexOf(PROTOCOL_SPLITTER) + PROTOCOL_SPLITTER.length();
            String userData = username + ":" + password + "@";
            strBuilderUrl.insert(indexOfProtocolEnd, userData);
        }
        return strBuilderUrl.toString();
    }

}
