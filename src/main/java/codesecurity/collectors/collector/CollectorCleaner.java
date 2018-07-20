//package codesecurity.collector;
//
//import com.capitalone.dashboard.model.CollectorItem;
//import com.capitalone.dashboard.model.CollectorType;
//import com.capitalone.dashboard.repository.ComponentRepository;
//import org.apache.commons.collections.CollectionUtils;
//import org.bson.types.ObjectId;
//
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//public class CollectorCleaner<PR, C, P> {
//    private final PR projectRepository;
//    private final ComponentRepository dbComponentRepository;
//
//    public CollectorCleaner(PR projectRepository, ComponentRepository dbComponentRepository) {
//        this.projectRepository = projectRepository;
//        this.dbComponentRepository = dbComponentRepository;
//    }
//
//    /**
//     * Clean up unused checkmarx collector items
//     *
//     * @param collector the {@link C}
//     */
//
//    @SuppressWarnings("PMD.AvoidDeeplyNestedIfStmts")
//    private void clean(C collector, List<P> existingProjects) {
//        Set<ObjectId> uniqueIDs = getUniqueIds(collector);
//        List<P> stateChangeJobList = new ArrayList<>();
//        for (P job : existingProjects) {
//            if ((job.isEnabled() && !uniqueIDs.contains(job.getId())) ||  // if it was enabled but not on a dashboard
//                    (!job.isEnabled() && uniqueIDs.contains(job.getId()))) { // OR it was disabled and now on a dashboard
//                job.setEnabled(uniqueIDs.contains(job.getId()));
//                stateChangeJobList.add(job);
//            }
//        }
//        if (!CollectionUtils.isEmpty(stateChangeJobList)) {
//            this.projectRepository.save(stateChangeJobList);
//        }
//    }
//
//    private Set<ObjectId> getUniqueIds(C collector) {
//        Set<ObjectId> uniqueIDs = new HashSet<>();
//        for (com.capitalone.dashboard.model.Component comp : dbComponentRepository
//                .findAll()) {
//            if (comp.getCollectorItems().isEmpty()) continue;
//            List<CollectorItem> itemList = comp.getCollectorItems().get(CollectorType.CheckMarx);
//            if (CollectionUtils.isEmpty(itemList)) continue;
//
//            for (CollectorItem ci : itemList) {
//                if (collector.getId().equals(ci.getCollectorId())) {
//                    uniqueIDs.add(ci.getId());
//                }
//            }
//        }
//        return uniqueIDs;
//    }
//
//}
