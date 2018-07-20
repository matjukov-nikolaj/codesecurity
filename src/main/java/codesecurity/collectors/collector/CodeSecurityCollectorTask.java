//package codesecurity.collector;
//
//import com.capitalone.dashboard.collector.CollectorTask;
//import com.capitalone.dashboard.model.Collector;
//import com.capitalone.dashboard.repository.BaseCollectorRepository;
//
//import java.util.List;
//
//public abstract class CodeSecurityCollectorTask<C, P> {
//
//    protected boolean isNewProject(P project, List<P> existingProjects) {
//        return (!existingProjects.contains(project));
//    }
//
//    private void addNewProject(P project, C collector) {
//        project.setCollectorId(collector.getId());
//        project.setEnabled(false);
//        project.setDescription(project.getProjectName());
//        checkMarxProjectRepository.save(project);
//    }
//
//
//}
