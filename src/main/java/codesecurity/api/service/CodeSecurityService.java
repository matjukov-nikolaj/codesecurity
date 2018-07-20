package codesecurity.api.service;

import com.capitalone.dashboard.model.CollectorItem;
import com.capitalone.dashboard.model.CollectorType;
import com.capitalone.dashboard.model.Component;
import com.capitalone.dashboard.model.DataResponse;
import com.capitalone.dashboard.repository.CollectorRepository;
import com.capitalone.dashboard.repository.ComponentRepository;
import org.bson.types.ObjectId;
import com.google.common.collect.Iterables;

import java.util.List;
public abstract class CodeSecurityService<RepoT, T, Req> {

    protected final RepoT securityRepository;
    protected final ComponentRepository componentRepository;
    protected final CollectorRepository collectorRepository;

    public CodeSecurityService(RepoT securityRepository,
                               ComponentRepository componentRepository,
                               CollectorRepository collectorRepository) {
        this.securityRepository = securityRepository;
        this.componentRepository = componentRepository;
        this.collectorRepository = collectorRepository;
    }

    public abstract DataResponse<Iterable<T>> search(Req request);

    public abstract DataResponse<Iterable<T>> searchType(Req request);

    protected abstract ObjectId getRequestId(Req request);

    protected abstract CollectorType getCollectortype();

    protected DataResponse<Iterable<T>> emptyResponse() {
        return new DataResponse<>(null, System.currentTimeMillis());
    }

    protected CollectorItem getCollectorItem(Req request) {
        CollectorItem item = null;
        Component component = componentRepository.findOne(getRequestId(request));
        List<CollectorItem> items = component.getCollectorItems().get(getCollectortype());
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
