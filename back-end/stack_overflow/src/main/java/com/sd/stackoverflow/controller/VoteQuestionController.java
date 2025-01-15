package com.sd.stackoverflow.controller;

import com.sd.stackoverflow.entity.VoteQuestion;
import com.sd.stackoverflow.service.VoteQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/voteQuestions")
@CrossOrigin(origins = "http://localhost:4200")
public class VoteQuestionController {

    @Autowired
    private VoteQuestionService voteQuestionService;

    @PostMapping("/upvoteQuestion")
    @ResponseBody
    public String upvoteQuestion(@RequestParam Long questionId, @RequestParam Long userId)
    {
        return this.voteQuestionService.upvoteQuestion(questionId,userId);
    }

    @PostMapping("/downvoteQuestion")
    @ResponseBody
    public String downvoteQuestion(@RequestParam Long questionId, @RequestParam Long userId)
    {

        return this.voteQuestionService.downvoteQuestion(questionId,userId);
    }

    @GetMapping("/getAll")
    @ResponseBody
    public List<VoteQuestion> getAllVotes() {
        List<VoteQuestion> votes =this.voteQuestionService.retrieveVotes();
        return votes;
    }

    @GetMapping("/getScores")
    @ResponseBody
    public List<Integer> getScores()
    {
        return this.voteQuestionService.getScores();
    }

    @GetMapping("/getScoreById")
    @ResponseBody
    public Integer getScoreById(Long questionId)
    {
        return this.voteQuestionService.questionScore(questionId);
    }

}
