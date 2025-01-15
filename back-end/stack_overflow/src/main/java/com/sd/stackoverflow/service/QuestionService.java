package com.sd.stackoverflow.service;

import com.sd.stackoverflow.entity.*;
import com.sd.stackoverflow.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private VoteQuestionRepository voteQuestionRepository;

    @Autowired
    private VoteAnswerRepository voteAnswerRepository;

    @Autowired
    private VoteAnswerService voteAnswerService;

    private ArrayList<String> pictureExtensions = new ArrayList<>(Arrays.asList(".jpg", ".jpeg", ".png", ".gif"));

    public List<Question> retrieveQuestions() {
        return (List<Question>) this.questionRepository.findAllByOrderByCreationDateTimeDesc();
    }

    public String addQuestion(Question question) {
        if (!question.getPicture().equals("")) {
            String picturePath = question.getPicture();

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

        if(question.getText().equals("") || question.getTitle().equals("") || question.getTags().size()==0)
        {
            return "Make sure neither of the required fields are empty/not-valid!";
        }

        try {
            questionRepository.save(question);
            return "Question successfully added!";
        } catch (Exception ex) {
            return "Make sure neither of the required fields are empty/not-valid!";
        }

    }

    public String updateQuestion(Question updatedQuestion, Long userId) {
        Question existingQuestion = questionRepository.findById(updatedQuestion.getQuestionId()).orElse(null);
        User currentUser = userRepository.findById(userId).orElse(null);

        if (existingQuestion != null && ((existingQuestion.getUser().getUserId().equals(userId)) || currentUser.getPosition() == 1)) {
            Optional<User> author = userRepository.findById(userId);
            if (author.isPresent()) {
                updatedQuestion.setUser(author.get());
                updatedQuestion.setCreationDateTime(existingQuestion.getCreationDateTime());
            }

            String responseToUpdating = this.addQuestion(updatedQuestion);

            if (responseToUpdating.equals("Question successfully added!")) {
                return "Question successfully updated!";
            } else {
                return responseToUpdating;
            }
        } else if (existingQuestion == null) {
            return "The question does not exist!";
        } else {
            return "You are not authorized to update the question!";
        }
    }

    public String deleteQuestionById(Long questionId, Long userId) {
        Question existingQuestion = questionRepository.findById(questionId).orElse(null);
        User currentUser = userRepository.findById(userId).orElse(null);

        if (existingQuestion != null && ((existingQuestion.getUser().getUserId().equals(userId)) || currentUser.getPosition() == 1)) {
            List<Answer> answers = answerRepository.findByQuestion_QuestionId(questionId);

            for (Answer answer : answers) {
                List<VoteAnswer> votesAnswers = voteAnswerRepository.findByAnswer_AnswerId(answer.getAnswerId());
                for (VoteAnswer vote : votesAnswers) {
                    voteAnswerRepository.deleteById(vote.getVoteAnswerId());
                }
                answerRepository.deleteById(answer.getAnswerId());
            }

            List<VoteQuestion> votes = voteQuestionRepository.findByQuestion_QuestionId(questionId);
            for (VoteQuestion vote : votes) {
                voteQuestionRepository.deleteById(vote.getVoteQuestionId());
            }

            questionRepository.deleteById(questionId);

            return "Question successfully deleted!";
        } else if (existingQuestion == null) {
            return "The question does not exist!";
        } else {
            return "You are not authorized to delete the question!";
        }
    }

    public List<Question> getQuestionsByTag(String tagName) {
        return (List<Question>) this.questionRepository.findByTagsName(tagName);
    }

    public List<Question> getQuestionsByUser(String username) {
        return (List<Question>) this.questionRepository.findByUser_Username(username);
    }

    public List<Question> getQuestionsByTitle(String title) {
        return (List<Question>) this.questionRepository.findByTitleContainingIgnoreCase(title);
    }

    public Question getQuestion(Long questionId) {
        return questionRepository.findById(questionId).orElse(null);
    }

    public List<Answer> answersByQuestion(Long questionId) {
        List<Answer> answers = answerRepository.findByQuestion_QuestionId(questionId);
        Collections.sort(answers, (a1, a2) -> Integer.compare(voteAnswerService.answerScore(a2.getAnswerId()), voteAnswerService.answerScore(a1.getAnswerId())));
        return answers;
    }
}
