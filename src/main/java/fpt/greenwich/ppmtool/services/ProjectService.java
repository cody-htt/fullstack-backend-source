package fpt.greenwich.ppmtool.services;

import fpt.greenwich.ppmtool.domain.Project;
import fpt.greenwich.ppmtool.exceptions.ProjectIdException;
import fpt.greenwich.ppmtool.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public Project saveOrUpdateProject(Project project) {
        try {
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            return projectRepository.save(project);
        } catch (Exception ex) {
            throw new ProjectIdException("Project ID '" + project.getProjectIdentifier().toUpperCase() + "' already existed");
        }
    }

    public Project findProjectByIdentifier(String projectId) {
        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());
        if (project == null) {
            String message = "Project ID '" + projectId.toUpperCase() + "' does not exist";
            throw new ProjectIdException(message);
        }

        return project;
    }

    public Iterable<Project> findAllProject() {
        return projectRepository.findAll();
    }

    public void deleteProjectByIdentifier(String projectId) {
        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());
        if (project == null) {
            String message = "Can not delete Project with ID '" + projectId.toUpperCase() + "'. This project does not exist";
            throw new ProjectIdException(message);
        }

        projectRepository.delete(project);
    }
}
