package com.sd.stackoverflow.service;


import com.sd.stackoverflow.entity.*;
import com.sd.stackoverflow.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.mindrot.jbcrypt.BCrypt;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    String salt = BCrypt.gensalt();

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private VoteQuestionRepository voteQuestionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private VoteAnswerRepository voteAnswerRepository;

    @Autowired
    private SaltRepository saltRepository;

    @Autowired
    private EmailSenderService emailSenderService;

    @Autowired
    private SMSSenderService SMSSenderService;

    public List<User> retrieveUsers() {
        return (List<User>) this.userRepository.findAll();
    }

    public String addUser(User user) {
        User existingUser = this.userRepository.findByUsername(user.getUsername());
        if (existingUser != null) {
            return "User with the same username already exists!";
        } else {
            existingUser = this.userRepository.findByEmail(user.getEmail());
            if (existingUser != null) {
                return "User with the same e-mail already exists!";
            } else {
                try {
                    if(user.getUsername().equals("")|| user.getPassword().equals("") || user.getEmail().equals("") || user.getFirstName().equals("") || user.getLastName().equals("") || user.getPhone().equals(""))
                    {
                        return "Make sure that no required field is empty!";
                    }

                    String hashedPassword = hashPassword(user.getPassword(),salt);
                    user.setPassword(hashedPassword);
                    this.userRepository.save(user);
                    Salt saltUser = new Salt();
                    saltUser.setUser(user);
                    saltUser.setSalt(salt);
                    this.saltRepository.save(saltUser);

                    return "User " + user.getUsername() + " successfully created!";
                } catch (Exception ex) {
                    return "Make sure that no required field is empty!";
                }
            }
        }
    }

    public String updateUser(User user) {
        User existingUser = this.userRepository.findById(user.getUserId()).orElse(null);
        if (existingUser == null) {
            return "There is no user to update!";
        } else {
            try {
                User anotherUser=this.userRepository.findByUsername(user.getUsername());
                if(anotherUser != null) {
                    if (anotherUser.getUserId() != existingUser.getUserId()) {
                        return "There is already another user with this username!";
                    }
                }

                anotherUser=this.userRepository.findByEmail(user.getEmail());
                if(anotherUser != null)
                {
                    if(anotherUser.getUserId()!= existingUser.getUserId()) {
                        return "There is already another user with this email!";
                    }
                }

                if(user.getUsername().equals("")|| user.getPassword().equals("") || user.getEmail().equals("") || user.getFirstName().equals("") || user.getLastName().equals("") || user.getPhone().equals(""))
                {
                    return "Make sure that no required field is empty!";
                }

                String hashedPassword = hashPassword(user.getPassword(), salt);
                user.setPassword(hashedPassword);
                this.userRepository.save(user);
                Salt saltUser = this.saltRepository.findByUser_UserId(existingUser.getUserId());
                saltUser.setUser(user);
                saltUser.setSalt(salt);
                this.saltRepository.save(saltUser);

                return "User with id " + user.getUserId() + " successfully updated!";
            } catch (Exception ex) {
                return "Make sure that no required field is empty!";
            }
        }
    }

    public User findUser(String username, String password) {
        User existingUser = this.userRepository.findByUsername(username);
        if (existingUser == null) {
            return null;
        } else {
            Salt saltUser = this.saltRepository.findByUser_UserId(existingUser.getUserId());
            String hashedPassword = hashPassword(password, saltUser.getSalt());
            if(hashedPassword == null || hashedPassword.compareTo(existingUser.getPassword()) != 0)
            {
                return null;
            }
            else
            {
                return existingUser;
            }
        }
    }

    public String deleteUserById(Long id) {
        try {
            this.userRepository.deleteById(id);
            return "Entry successfully deleted!";
        } catch (Exception e) {
            return "Failed to delete entry with id:" + id;
        }
    }

    public String hashPassword(String plainTextPassword, String salt) {
        return BCrypt.hashpw(plainTextPassword, salt);
    }

    public List<UserScoreDTO> userWithScores() {
        List<User> users = (List<User>) this.userRepository.findAll();
        List<UserScoreDTO> usersWithScores = new ArrayList<>();


        for (User user : users) {
            float score = 0;
            List<Question> questions = this.questionRepository.findByUser_UserId(user.getUserId());
            for (Question question : questions) {
                List<VoteQuestion> voteQuestions = this.voteQuestionRepository.findByQuestion_QuestionId(question.getQuestionId());
                for (VoteQuestion vote : voteQuestions) {
                    if (vote.getUpOrDown() == 1) {
                        score += 2.5;
                    } else {
                        score -= 1.5;
                    }
                }
            }

            List<Answer> answers = this.answerRepository.findByUser_UserId(user.getUserId());
            for (Answer answer : answers) {
                List<VoteAnswer> voteAnswers = this.voteAnswerRepository.findByAnswer_AnswerId(answer.getAnswerId());
                for (VoteAnswer vote : voteAnswers) {
                    if (vote.getUpOrDown() == 1) {
                        score += 5.0;
                    } else {
                        score -= 2.5;
                    }
                }
            }

            List<VoteAnswer> votes = this.voteAnswerRepository.findByUser_UserId(user.getUserId());
            for (VoteAnswer vote : votes) {
                if (vote.getUpOrDown() == -1) {
                    score -= 1.5;
                }
            }

            usersWithScores.add(new UserScoreDTO(user.getUserId(), score));
        }

        return usersWithScores;
    }

    public String banUser(long userId) {
        User existingUser = this.userRepository.findById(userId).orElse(null);
        if (existingUser == null) {
            return "There is no user to ban!";
        } else {
            existingUser.setBanned(1);
            this.userRepository.save(existingUser);
            this.emailSenderService.sendEmail(existingUser.getEmail(), "Ban - Stack Overflow", "User "+ existingUser.getUsername()+ " has been banned from Stack Overflow :(");
            this.SMSSenderService.sendSMS(existingUser.getPhone(),"User "+ existingUser.getUsername()+ " has been banned from Stack Overflow :(");
            return "User with id " + userId + " banned!";
        }
    }

    public String unbanUser(long userId) {
        User existingUser = this.userRepository.findById(userId).orElse(null);
        if (existingUser == null) {
            return "There is no user to unban!";
        } else {
            existingUser.setBanned(0);
            this.userRepository.save(existingUser);
            this.emailSenderService.sendEmail(existingUser.getEmail(), "Unban - Stack Overflow", "User "+ existingUser.getUsername()+ " has been unbanned from Stack Overflow :)");
            this.SMSSenderService.sendSMS(existingUser.getPhone(),"User "+ existingUser.getUsername()+ " has been unbanned from Stack Overflow :)");
            return "User with id " + userId + " unbanned!";
        }
    }

}
