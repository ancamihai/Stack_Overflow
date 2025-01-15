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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class AnswerService {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VoteAnswerRepository voteAnswerRepository;

    private ArrayList<String> pictureExtensions = new ArrayList<>(Arrays.asList(".jpg", ".jpeg", ".png", ".gif"));

    public List<Answer> retrieveAnswers() {
        return (List<Answer>) this.answerRepository.findAll();
    }

    public String addAnswer(Answer answer) {
        if (!answer.getPicture().equals("")) {
            String picturePath = answer.getPicture();

            boolean correctExtension = false;
            for (String extension : pictureExtensions) {
                if (picturePath.endsWith(extension)) {
                    correctExtension = true;
                }
            }

            if (!correctExtension) {
                return "The path you provided is not for an image!";
            }
        }

        if(answer.getText().equals(""))
        {
            return "You must provide text for an answer!";
        }

        try {
            answerRepository.save(answer);
            return "Answer successfully added!";
        } catch (
                Exception ex) {
            return "Make sure neither of the required fields are empty!";
        }

    }


    public String updateAnswer(Answer updatedAnswer, Long userId) {
        Answer existingAnswer = answerRepository.findById(updatedAnswer.getAnswerId()).orElse(null);
        User currentUser = userRepository.findById(userId).orElse(null);

        if (existingAnswer != null && (existingAnswer.getUser().getUserId().equals(userId) || currentUser.getPosition() == 1)) {
            Optional<User> author = userRepository.findById(userId);

            if (author.isPresent()) {
                updatedAnswer.setUser(author.get());
                updatedAnswer.setQuestion(existingAnswer.getQuestion());
                updatedAnswer.setCreationDateTime(existingAnswer.getCreationDateTime());
            }

            String responseToUpdating = this.addAnswer(updatedAnswer);
            if (responseToUpdating.equals("Answer successfully added!")) {
                return "Answer successfully updated!";
            } else {
                return responseToUpdating;
            }

        } else if (existingAnswer == null) {
            return "The answer does not exist!";
        } else {
            return "You are not authorized to update the answer!";
        }
    }

    public String deleteAnswerById(Long answerId, Long userId) {
        Answer existingAnswer = answerRepository.findById(answerId).orElse(null);
        User currentUser = userRepository.findById(userId).orElse(null);

        if (existingAnswer != null && (existingAnswer.getUser().getUserId().equals(userId) || currentUser.getPosition() == 1)) {
            List<VoteAnswer> votesAnswers = voteAnswerRepository.findByAnswer_AnswerId(answerId);

            for (VoteAnswer vote : votesAnswers) {
                voteAnswerRepository.deleteById(vote.getVoteAnswerId());
            }

            answerRepository.deleteById(answerId);
            return "Answer successfully deleted!";
        } else if (existingAnswer == null) {
            return "The answer does not exist!";
        } else {
            return "You are not authorized to delete the answer!";
        }
    }

}
