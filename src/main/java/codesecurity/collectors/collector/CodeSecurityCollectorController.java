package codesecurity.collectors.collector;

import codesecurity.collectors.model.CodeSecurityProject;
import com.capitalone.dashboard.model.Collector;

import java.util.List;

public abstract class CodeSecurityCollectorController<CType extends Collector, PType extends CodeSecurityProject> {

    protected abstract void saveProjectToProjectRepository(PType project);

    protected abstract PType getAMovedProject(PType lhs, PType rhs);

    protected abstract PType enabledProject(CType collector, PType project);

    protected abstract void refreshCollectorData(PType project);

    protected abstract PType getNewProject();

    protected abstract PType getCurrentProjectFromProjectRepository(CType collector);

    protected abstract void refreshCollectorItemId(PType currentProject, PType project);

    public boolean isNewProject(PType project, List<PType> existingProjects) {
        return (!existingProjects.contains(project));
    }

    public void addNewProject(PType project, CType collector, List<PType> existingProjects) {
        if (existingProjects.isEmpty()) {
            addFirstProject(project, collector, existingProjects);
        } else {
            exchangeProjects(project, collector, existingProjects);
        }
    }

    private void addFirstProject(PType project, CType collector, List<PType> existingProjects) {
        addProject(project, collector, existingProjects);
        refreshData(enabledProject(collector, project));
    }

    private void refreshData(PType project) {
        if (project == null)
        {
            return;
        }
        refreshCollectorData(project);
    }

    private void addProject(PType project, CType collector, List<PType> existingProjects) {
        project.setCurrentProject(false);
        if (existingProjects.isEmpty()) {
            project.setCurrentProject(true);
        }
        project.setCollectorId(collector.getId());
        project.setEnabled(false);
        project.setDescription(project.getProjectName());

        saveProjectToProjectRepository(project);
        updateAppScanCollectorItemId(project, existingProjects);
    }

    private void updateAppScanCollectorItemId(PType project, List<PType> existingProjects) {
        if (existingProjects.isEmpty()) {
            return;
        }
        PType currentProject = existingProjects.get(0);
        refreshCollectorItemId(currentProject, project);
    }

    private void exchangeProjects(PType project, CType collector, List<PType> existingProjects) {
        PType currentProject = getCurrentProjectFromProjectRepository(collector);
        PType emptyProject = getNewProject();
        PType tempProject = getAMovedProject(emptyProject, currentProject);
        updateCurrentProject(existingProjects, project, currentProject);
        addProject(tempProject, collector, existingProjects);
        refreshData(enabledProject(collector, currentProject));
    }

    private void updateCurrentProject(List<PType> existingProjects, PType project, PType currentProject) {
        if (existingProjects.isEmpty()) {
            return;
        }
        saveProjectToProjectRepository(getAMovedProject(currentProject, project));
    }
}
