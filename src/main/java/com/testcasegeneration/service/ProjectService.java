package com.testcasegeneration.service;

import com.testcasegeneration.model.Project;
import com.testcasegeneration.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    private ProjectRepository projectRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository){
        this.projectRepository = projectRepository;
    }

    public void saveProject(Project project){
        projectRepository.save(project);
    }

    public Project findById(String id){
        return projectRepository.findById(Long.parseLong(id));
    }

}

