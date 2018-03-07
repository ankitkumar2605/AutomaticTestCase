package com.testcasegeneration.repository;

import com.testcasegeneration.model.Project;
import com.testcasegeneration.model.UiElement;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.LinkedList;
import java.util.stream.Stream;

public interface StorageService {

    void init();

    void store(MultipartFile file);

    Project storeUiElements(MultipartFile file, String projectName);

    Stream<Path> loadAll();

    Path load(String filename);

    Resource loadAsResource(String filename);

    void deleteAll();

}
