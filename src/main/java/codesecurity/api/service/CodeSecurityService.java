package codesecurity.api.service;

import com.capitalone.dashboard.model.CollectorItem;
import com.capitalone.dashboard.model.CollectorType;
import com.capitalone.dashboard.model.Component;
import com.capitalone.dashboard.model.DataResponse;
import org.bson.types.ObjectId;
import com.google.common.collect.Iterables;

import java.util.*;

public abstract class CodeSecurityService<Type, Req>{

    public DataResponse<Iterable<Type>> emptyResponse() {
        return new DataResponse<>(null, System.currentTimeMillis());
    }

    public Type getPreviousData() {
        return this.previousData;
    }

    public Type getResultOfComparisonBetweenCurrAndPrevData(Type currentData) {
        this.previousData = getPreviousDataFromProjectRepository(currentData);
        if (this.previousData == null) {
            return null;
        }
        Map<String, Integer> difference = getDifferenceBetweenTheCurrAndPrevData(currentData, this.previousData);
        return getResultOfComparison(difference);
    }

    public CollectorItem getCollectorItem(Req request) {
        CollectorItem item = null;
        Component component = getComponent(getRequestId(request));
        List<CollectorItem> items = component.getCollectorItems().get(getCollectorType());
        if (items != null) {
            item = Iterables.getFirst(items, null);
        }
        return item;
    }

    protected Type getPreviousDataFromProjectRepository(Type result) {
        String projectName = getProjectNameFromObject(result);
        List<Type> dataByCurrentProjectName = getDataByCurrentProjectName(projectName);
        dataByCurrentProjectName.removeIf(p -> (getProjectTimestamp(p).equals(getProjectTimestamp(result))));
        if (dataByCurrentProjectName.isEmpty()) {
            return null;
        }
        return Collections.max(dataByCurrentProjectName, Comparator.comparing(object -> getProjectTimestamp(object)));
    }

    protected Type previousData = null;

    protected abstract ObjectId getRequestId(Req request);

    protected abstract CollectorType getCollectorType();

    protected abstract Component getComponent(ObjectId id);

    protected abstract Integer getMetricField(Type object, String metricField);

    protected abstract String getAFirstFieldFromMap();

    protected abstract String getASecondFiledFromMap();

    protected abstract String getAThirdFieldFromMap();

    protected abstract List<Type> getDataByCurrentProjectName(String projectName);

    protected abstract String getProjectNameFromObject(Type object);

    protected abstract Long getProjectTimestamp(Type object);

    protected abstract Type getResultOfComparison(Map<String, Integer> difference);

    protected Map<String, Integer> getDifferenceBetweenTheCurrAndPrevData(Type current, Type previous) {
        Map<String, Integer> difference = new HashMap<>();
        difference.put(getAFirstFieldFromMap(), getDifferenceOfMetrics(current, previous, getAFirstFieldFromMap()));
        difference.put(getASecondFiledFromMap(), getDifferenceOfMetrics(current, previous, getASecondFiledFromMap()));
        difference.put(getAThirdFieldFromMap(), getDifferenceOfMetrics(current, previous, getAThirdFieldFromMap()));
        return difference;
    }

    protected Integer getDifferenceOfMetrics(Type current, Type previous, String metricField) {
        int firstValue = getMetricField(previous, metricField);
        int secondValue = getMetricField(current, metricField);
        return (firstValue - secondValue);
    }
}
