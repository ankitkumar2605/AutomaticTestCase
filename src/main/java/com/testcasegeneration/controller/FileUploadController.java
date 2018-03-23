package com.testcasegeneration.controller;

import com.testcasegeneration.demo.StorageFileNotFoundException;
import com.testcasegeneration.model.Project;
import com.testcasegeneration.model.TagValuesCO;
import com.testcasegeneration.model.UiElement;
import com.testcasegeneration.repository.StorageService;
import com.testcasegeneration.service.UiElementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

@Controller
public class FileUploadController {

    private final StorageService storageService;
    private UiElementService uiElementService;

    @Autowired
    public FileUploadController(StorageService storageService,UiElementService uiElementService) {
        this.storageService = storageService;
        this.uiElementService = uiElementService;
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public ModelAndView listUploadedFiles(ModelAndView modelAndView) throws IOException {
        modelAndView.setViewName("home");
        return modelAndView;
    }

    @RequestMapping(value = "/uiElements", method = RequestMethod.GET)
    public String uiElements(Model model) throws IOException {
        return "uiElements";
    }

    @PostMapping(value = "/upload")
    public String handleFileUpload(@RequestParam("htmlFile") MultipartFile file,
                                   @RequestParam("jsFile") MultipartFile jsFile,
                                   @RequestParam("projectName") String projectName,
                                   RedirectAttributes redirectAttributes) {
        Project project = storageService.storeUiElements(file,projectName);
        storageService.store(jsFile);
        redirectAttributes.addFlashAttribute("project",project.getId());
        return "redirect:/uiElements";
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }


    @RequestMapping(value = "/addRules", method = RequestMethod.GET)
    public ModelAndView rules(ModelAndView modelAndView,String projectId) throws IOException {
        modelAndView.addObject("project",projectId);
        modelAndView.setViewName("addRules");
        return modelAndView;
    }
}
