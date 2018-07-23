package codesecurity.api.service;

import com.capitalone.dashboard.model.CollectorItem;
import com.capitalone.dashboard.model.CollectorType;
import com.capitalone.dashboard.model.Component;
import com.capitalone.dashboard.model.DataResponse;
import org.bson.types.ObjectId;
import com.google.common.collect.Iterables;

import java.util.List;
public abstract class CodeSecurityService<Type, Req>{

    public abstract DataResponse<Iterable<Type>> search(Req request);

    public abstract DataResponse<Iterable<Type>> searchType(Req request);

    protected abstract ObjectId getRequestId(Req request);

    protected abstract CollectorType getCollectorType();

    protected abstract Component getComponent(ObjectId id);

    protected DataResponse<Iterable<Type>> emptyResponse() {
        return new DataResponse<>(null, System.currentTimeMillis());
    }

    protected CollectorItem getCollectorItem(Req request) {
        CollectorItem item = null;
        Component component = getComponent(getRequestId(request));
        List<CollectorItem> items = component.getCollectorItems().get(getCollectorType());
        if (items != null) {
            item = Iterables.getFirst(items, null);
        }
        return item;
    }

    protected String getReportURL(String projectUrl, String path, String projectId) {
        StringBuilder sb = new StringBuilder(projectUrl);
        if(!projectUrl.endsWith("/")) {
            sb.append("/");
        }
        sb.append(path).append(projectId);
        return sb.toString();
    }
}
