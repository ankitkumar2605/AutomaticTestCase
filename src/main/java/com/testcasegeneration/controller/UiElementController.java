package com.testcasegeneration.controller;

import com.testcasegeneration.demo.ResponseDto;
import com.testcasegeneration.model.TagValuesCO;
import com.testcasegeneration.model.UiElement;
import com.testcasegeneration.model.UiElementDTO;
import com.testcasegeneration.service.UiElementService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UiElementController {

    @Autowired
    private UiElementService uiElementService;

    @Autowired
    private ModelMapper modelMapper;

    @RequestMapping(value = "/uiElementsList", method = RequestMethod.GET,produces = {"application/json"})
    public List<UiElementDTO> listUiElements(@RequestParam("projectId") String projectId) throws IOException {
        List<UiElement> uiElementList;
        uiElementList =  uiElementService.findAllByProject(projectId);
        List<UiElementDTO> uiElementDTO = uiElementList.stream().map(this::convertToDto).collect((Collectors.toList()));
        return uiElementDTO;
    }

    @RequestMapping(value = "/deleteUiElement", method = RequestMethod.GET,produces = {"application/json"})
    public ResponseDto deleteUiElement(@RequestParam("id") String id) throws IOException {
        ResponseDto responseDto = new ResponseDto();
        try {
            uiElementService.deleteUiElement(id);
            responseDto.setMessage("Successfully Deleted");
            responseDto.setStatus(true);

        }catch (Exception e){
            e.printStackTrace();
            responseDto.setMessage("Exception while deleted element");
            responseDto.setStatus(false);
        }
        return responseDto;
    }


    @RequestMapping(value = "/saveTagValues",method = RequestMethod.POST,produces = {"application/json"})
    public ResponseDto saveTagValues(@RequestBody TagValuesCO[] tagValuesCO){
        ResponseDto responseDto = new ResponseDto();
        try {
            Arrays.stream(tagValuesCO).forEach(tagValuesCo -> {
                UiElement uiElement = uiElementService.findById(tagValuesCo.getId());
                ArrayList<String> tagValues = new ArrayList<String>(Arrays.asList(tagValuesCo.getTagValues().split("\\s*,\\s*")));
                uiElement.setTagValues(tagValues);
                uiElementService.saveUiElement(uiElement);
            });
            responseDto.setMessage("Successfully Added");
            responseDto.setStatus(true);
        }catch (Exception e){
            e.printStackTrace();
            responseDto.setMessage("Exception while deleted element");
            responseDto.setStatus(false);
        }
        return responseDto;
    }

    private UiElementDTO convertToDto(UiElement uiElement) {
        UiElementDTO uiElementDTO = modelMapper.map(uiElement, UiElementDTO.class);
        return uiElementDTO;
    }
}
