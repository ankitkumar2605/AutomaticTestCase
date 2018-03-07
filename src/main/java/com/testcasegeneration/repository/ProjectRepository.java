package com.testcasegeneration.repository;

import com.testcasegeneration.model.Project;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("projectRepository")
public interface ProjectRepository extends CrudRepository<Project, Long> {
     Project findById(Long id);
}
