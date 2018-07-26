package codesecurity.collectors.collector;

import org.bson.types.ObjectId;

import java.util.List;
import java.util.Set;

public interface CodeSecurityCollectorTask<C, P, T>{

    boolean isNewProject(P project, List<P> existingProjects);

    void addNewProject(P project, P collector);

    void refreshData(P project);

    P enabledProject(C collector, P project);

    boolean isNewData(P project, T type);

    void clean(C collector, List<P> existingProjects);

    Set<ObjectId> getUniqueIds(C collector);
}
