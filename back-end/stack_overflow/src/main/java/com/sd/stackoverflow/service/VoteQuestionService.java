package com.sd.stackoverflow.service;

import com.sd.stackoverflow.entity.Question;
import com.sd.stackoverflow.entity.User;
import com.sd.stackoverflow.entity.VoteQuestion;
import com.sd.stackoverflow.repository.QuestionRepository;
import com.sd.stackoverflow.repository.UserRepository;
import com.sd.stackoverflow.repository.VoteQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VoteQuestionService {

    @Autowired
    private VoteQuestionRepository voteQuestionRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuestionService questionService;


    public String upvoteQuestion(Long questionId, Long userId)
    {
        VoteQuestion existingVote = voteQuestionRepository.findByQuestion_QuestionIdAndUser_UserId(questionId,userId);
        if(existingVote!=null)
        {
            if(existingVote.getUpOrDown()==1) //upvote
            {
                this.voteQuestionRepository.deleteById(existingVote.getVoteQuestionId());
            }
            else //downvote
            {
               existingVote.setUpOrDown(1);
               this.voteQuestionRepository.save(existingVote);
            }
        }
        else{

            VoteQuestion newVote=new VoteQuestion();

            Question question = this.questionRepository.findById(questionId).orElse(null);
            User user = this.userRepository.findById(userId).orElse(null);

            newVote.setQuestion(question);
            newVote.setUser(user);

            newVote.setUpOrDown(1);
            this.voteQuestionRepository.save(newVote);
        }

        return "Upvote done successfully";
    }

    public String downvoteQuestion(Long questionId, Long userId)
    {
        VoteQuestion existingVote = voteQuestionRepository.findByQuestion_QuestionIdAndUser_UserId(questionId,userId);
        if(existingVote!=null)
        {
            if(existingVote.getUpOrDown()==-1) //downvote
            {
                this.voteQuestionRepository.deleteById(existingVote.getVoteQuestionId());
            }
            else //upvote
            {
                existingVote.setUpOrDown(-1);
                this.voteQuestionRepository.save(existingVote);
            }
        }
        else{

            VoteQuestion newVote=new VoteQuestion();

            Question question = this.questionRepository.findById(questionId).orElse(null);
            User user = this.userRepository.findById(userId).orElse(null);

            newVote.setQuestion(question);
            newVote.setUser(user);

            newVote.setUpOrDown(-1);
            this.voteQuestionRepository.save(newVote);
        }

        return "Downvote done successfully";
    }

    public int questionScore (Long questionId)
    {
        int score =0;

        List<VoteQuestion> votes = this.voteQuestionRepository.findByQuestion_QuestionId(questionId);
        for (VoteQuestion vote : votes) {
            score+= vote.getUpOrDown();
        }

        return score;
    }

    public List<Integer> getScores()
    {
        List<Integer> scores = new ArrayList<>();
        List<Question> questions = this.questionService.retrieveQuestions();
        for(Question question: questions)
        {
            scores.add(questionScore(question.getQuestionId()));
        }

        return scores;
    }

    public List<VoteQuestion> retrieveVotes() {
        return (List<VoteQuestion>) this.voteQuestionRepository.findAll();
    }

}
