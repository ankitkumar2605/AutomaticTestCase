package com.testcasegeneration.repository;

import com.testcasegeneration.model.Project;
import com.testcasegeneration.model.UiElement;
import com.testcasegeneration.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("uiElementRepository")
public interface UiElementRepository extends CrudRepository<UiElement, Long> {
    List<UiElement> findAllByProject(Project project);
}
