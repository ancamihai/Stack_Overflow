package com.sd.stackoverflow.service;

import com.sd.stackoverflow.entity.Tag;
import com.sd.stackoverflow.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {

    @Autowired
    private TagRepository tagRepository;

    public List<Tag> retrieveTags() {
        return (List<Tag>) this.tagRepository.findAll();
    }

    public String addTag(Tag tag) {
        try {
            this.tagRepository.save(tag);
            return "Tag with name " + tag.getName() + " successfully added!";
        } catch (Exception ex) {
            return "There already exists a tag with the same name!";
        }
    }

    public Tag retrieveTagByName(String name) {
        Tag tag = this.tagRepository.findByName(name);
        return tag;
    }

}
