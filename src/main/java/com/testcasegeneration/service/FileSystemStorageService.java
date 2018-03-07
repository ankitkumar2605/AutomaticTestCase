package com.testcasegeneration.service;

import com.testcasegeneration.demo.StorageException;
import com.testcasegeneration.demo.StorageFileNotFoundException;
import com.testcasegeneration.demo.StorageProperties;
import com.testcasegeneration.model.Project;
import com.testcasegeneration.model.UiElement;
import com.testcasegeneration.repository.StorageService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

@Service
public class FileSystemStorageService implements StorageService {

    private final Path rootLocation;
    private UiElementService uiElementService;
    private ProjectService projectService;

    @Autowired
    public FileSystemStorageService(StorageProperties properties,UiElementService uiElementService,ProjectService projectService) {
        this.rootLocation = Paths.get(properties.getLocation());
        this.uiElementService = uiElementService;
        this.projectService = projectService;
    }

    @Override
    public void store(MultipartFile file) {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + filename);
            }
            if (filename.contains("..")) {
                // This is a security check
                throw new StorageException(
                        "Cannot store file with relative path outside current directory "
                                + filename);
            }
            if (filename.contains(".js")) {
                Files.copy(file.getInputStream(), this.rootLocation.resolve(filename),
                        StandardCopyOption.REPLACE_EXISTING);
                return;
//                ScriptEngineManager manager = new ScriptEngineManager();
//                ScriptEngine engine = manager.getEngineByName("JavaScript");
//                if (!(engine instanceof Invocable)) {
//                    System.out.println("Invoking methods is not supported.");
//                    return;
//                }
//                Invocable inv = (Invocable) engine;
//                System.out.println(load(filename));
//
//                //engine.eval("load('" + scriptPath + "')");
//                String scriptPath = load(filename).toString();
//
//                engine.eval("load('" + scriptPath + "')");

            }

        } catch (IOException e) {
            throw new StorageException("Failed to store file " + filename, e);

        }
    }

    @Override
    public Project storeUiElements(MultipartFile file,String projectName){
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + filename);
            }
            if (filename.contains("..")) {
                // This is a security check
                throw new StorageException(
                        "Cannot store file with relative path outside current directory "
                                + filename);
            }

            Project project = new Project();
            project.setName(projectName);
            String htmlContent = new String(file.getBytes());
            Document doc = Jsoup.parse(htmlContent);
            Elements inputs = doc.getElementsByTag("input");
            for (Element inputElement : inputs) {
                String name = inputElement.attr("name");
                UiElement uiElement = new UiElement();
                uiElement.setType("Input");
                uiElement.setName(name);
                uiElement.setProject(project);
                project.getUiElement().add(uiElement);
            }
            projectService.saveProject(project);
            return project;

        } catch (IOException e) {
            throw new StorageException("Failed to store file " + filename, e);

        }

    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(path -> this.rootLocation.relativize(path));
        } catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }

    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new StorageFileNotFoundException(
                        "Could not read file: " + filename);

            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    @Override
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }
}
