package com.sd.stackoverflow.service;

import com.sd.stackoverflow.entity.Answer;
import com.sd.stackoverflow.entity.User;
import com.sd.stackoverflow.entity.VoteAnswer;
import com.sd.stackoverflow.repository.AnswerRepository;
import com.sd.stackoverflow.repository.UserRepository;
import com.sd.stackoverflow.repository.VoteAnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class VoteAnswerService {

    @Autowired
    private VoteAnswerRepository voteAnswerRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AnswerService answerService;

    public String upvoteAnswer(Long answerId, Long userId)
    {
        VoteAnswer existingVote = voteAnswerRepository.findByAnswer_AnswerIdAndUser_UserId(answerId,userId);
        if(existingVote!=null)
        {
            if(existingVote.getUpOrDown()==1) //upvote
            {
                this.voteAnswerRepository.deleteById(existingVote.getVoteAnswerId());
            }
            else //downvote
            {
                existingVote.setUpOrDown(1);
                this.voteAnswerRepository.save(existingVote);
            }
        }
        else{

            VoteAnswer newVote=new VoteAnswer();

            Answer answer = this.answerRepository.findById(answerId).orElse(null);
            User user = this.userRepository.findById(userId).orElse(null);

            newVote.setAnswer(answer);
            newVote.setUser(user);

            newVote.setUpOrDown(1);
            this.voteAnswerRepository.save(newVote);
        }

        return "Upvote done successfully";
    }

    public String downvoteAnswer(Long answerId, Long userId)
    {
        VoteAnswer existingVote = voteAnswerRepository.findByAnswer_AnswerIdAndUser_UserId(answerId,userId);
        if(existingVote!=null)
        {
            if(existingVote.getUpOrDown()==-1) //downvote
            {
                this.voteAnswerRepository.deleteById(existingVote.getVoteAnswerId());
            }
            else //upvote
            {
                existingVote.setUpOrDown(-1);
                this.voteAnswerRepository.save(existingVote);
            }
        }
        else{

            VoteAnswer newVote=new VoteAnswer();

            Answer answer = this.answerRepository.findById(answerId).orElse(null);
            User user = this.userRepository.findById(userId).orElse(null);

            newVote.setAnswer(answer);
            newVote.setUser(user);

            newVote.setUpOrDown(-1);
            this.voteAnswerRepository.save(newVote);
        }

        return "Downvote done successfully";
    }

    public int answerScore (Long answerId)
    {
        int score =0;

        List<VoteAnswer> votes = this.voteAnswerRepository.findByAnswer_AnswerId(answerId);
        for (VoteAnswer vote : votes) {
            score+= vote.getUpOrDown();
        }

        return score;
    }

    public List<Integer> getScores(Long questionId)
    {
        List<Integer> scores = new ArrayList<>();
        List<Answer> answers = this.answerRepository.findByQuestion_QuestionId(questionId);
        Collections.sort(answers, (a1, a2) -> Integer.compare(answerScore(a2.getAnswerId()), answerScore(a1.getAnswerId())));
        for(Answer answer: answers)
        {
            scores.add(answerScore(answer.getAnswerId()));
        }

        return scores;
    }

    public List<VoteAnswer> retrieveVotes() {
        return (List<VoteAnswer>) this.voteAnswerRepository.findAll();
    }
}
