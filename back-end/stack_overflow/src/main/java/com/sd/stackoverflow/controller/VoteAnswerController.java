package com.sd.stackoverflow.controller;

import com.sd.stackoverflow.entity.VoteAnswer;
import com.sd.stackoverflow.service.VoteAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/voteAnswers")
@CrossOrigin(origins = "http://localhost:4200")
public class VoteAnswerController {

    @Autowired
    private VoteAnswerService voteAnswerService;

    @PostMapping("/upvoteAnswer")
    @ResponseBody
    public String upvoteAnswer(@RequestParam Long answerId, @RequestParam Long userId)
    {
        return this.voteAnswerService.upvoteAnswer(answerId,userId);
    }

    @PostMapping("/downvoteAnswer")
    @ResponseBody
    public String downvoteAnswer(@RequestParam Long answerId, @RequestParam Long userId)
    {

        return this.voteAnswerService.downvoteAnswer(answerId,userId);
    }

    @GetMapping("/getAll")
    @ResponseBody
    public List<VoteAnswer> getAllVotes() {
        List<VoteAnswer> votes =this.voteAnswerService.retrieveVotes();
        return votes;
    }

    @GetMapping("/getScores")
    @ResponseBody
    public List<Integer> getScores(@RequestParam Long questionId)
    {
        return this.voteAnswerService.getScores(questionId);
    }

}
