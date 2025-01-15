package com.sd.stackoverflow.controller;


import com.sd.stackoverflow.entity.Answer;
import com.sd.stackoverflow.service.AnswerService;
import com.sd.stackoverflow.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/answers")
@CrossOrigin(origins = "http://localhost:4200")
public class AnswerController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private AnswerService answerService;

    @GetMapping("/getAll")
    @ResponseBody
    public List<Answer> getAllAnswers() {
        List<Answer> answers =this.answerService.retrieveAnswers();
        return answers;
    }

    @PostMapping("/addAnswer")
    @ResponseBody
    public String addAnswer(@RequestBody Answer answer){
        return  this.answerService.addAnswer(answer);
    }

    @PutMapping("/updateAnswer")
    public String updateAnswer(@RequestBody Answer answer, @RequestParam Long userId){
        return  this.answerService.updateAnswer(answer,userId);
    }

    @GetMapping("/getByQuestion")
    @ResponseBody
    public List<Answer> getAnswersByQuestion(@RequestParam Long questionId) {
        return this.questionService.answersByQuestion(questionId);
    }

    @DeleteMapping("/deleteById")
    @ResponseBody
    public String deleteAnswerById(@RequestParam Long answerId, @RequestParam Long userId){
        return this.answerService.deleteAnswerById(answerId,userId);
    }

}
