package com.sd.stackoverflow.controller;

import com.sd.stackoverflow.entity.Question;
import com.sd.stackoverflow.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/questions")
@CrossOrigin(origins = "http://localhost:4200")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/getAll")
    @ResponseBody
    public List<Question> getAllQuestions() {
        List<Question> questions =this.questionService.retrieveQuestions();
        return questions;
    }

    @GetMapping("/getById")
    @ResponseBody
    public Question getQuestion(@RequestParam Long questionId) {
        return this.questionService.getQuestion(questionId);
    }

    @GetMapping("/getByTag")
    @ResponseBody
    public List<Question> getQuestionsByTag(@RequestParam String tagName) {
        List<Question> questions =this.questionService.getQuestionsByTag(tagName);
        return questions;
    }

    @GetMapping("/getByUser")
    @ResponseBody
    public List<Question> getQuestionsByUser(@RequestParam String username) {
        List<Question> questions =this.questionService.getQuestionsByUser(username);
        return questions;
    }

    @GetMapping("/getByTitle")
    @ResponseBody
    public List<Question> getQuestionsByTitle(@RequestParam String title) {
        List<Question> questions =this.questionService.getQuestionsByTitle(title);
        return questions;
    }

    @PostMapping("/addQuestion")
    @ResponseBody
    public String addQuestion(@RequestBody Question question){
        return  this.questionService.addQuestion(question);
    }

    @PutMapping("/updateQuestion")
    public String updateQuestion(@RequestBody Question question, @RequestParam Long userId){
        return  this.questionService.updateQuestion(question,userId);    }

    @DeleteMapping("/deleteById")
    @ResponseBody
    public String deleteQuestionById(@RequestParam Long questionId, @RequestParam Long userId){
        return this.questionService.deleteQuestionById(questionId,userId);
    }

}
