package com.sd.stackoverflow.controller;

import com.sd.stackoverflow.entity.Tag;
import com.sd.stackoverflow.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tags")
@CrossOrigin(origins = "http://localhost:4200")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/getAll")
    @ResponseBody
    public List<Tag> getAllTags() {
        List<Tag> tags =this.tagService.retrieveTags();
        return tags;
    }

    @GetMapping("/getByName")
    @ResponseBody
    public Tag getTagByName(@RequestParam("name") String name) {
        Tag tag =this.tagService.retrieveTagByName(name);
        return tag;
    }

    @PostMapping("/addTag")
    @ResponseBody
    public String addTag(@RequestBody Tag tag){
        return  this.tagService.addTag(tag);
    }

}
