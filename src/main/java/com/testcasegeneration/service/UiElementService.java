package com.testcasegeneration.service;

import com.testcasegeneration.model.UiElement;
import com.testcasegeneration.repository.UiElementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UiElementService {

    private UiElementRepository uiElementRepository;
    private ProjectService projectService;

    @Autowired
    public UiElementService(UiElementRepository uiElementRepository,ProjectService projectService){
        this.uiElementRepository = uiElementRepository;
        this.projectService = projectService;
    }

    public UiElement findById(String id){
        return uiElementRepository.findOne(Long.parseLong(id));
    }

    public void saveUiElement(UiElement uiElement){
        uiElementRepository.save(uiElement);
    }

    public List<UiElement> findAllByProject(String id){
        return uiElementRepository.findAllByProject(projectService.findById(id));
    }

    public void deleteUiElement(String id){
        uiElementRepository.delete(Long.parseLong(id));
    }

}

