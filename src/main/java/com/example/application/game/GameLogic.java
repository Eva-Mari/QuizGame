package com.example.application.game;

import com.example.application.entity.Player;
import com.example.application.entity.QuizQuestion;
import com.example.application.service.QuizService;
import com.example.application.service.SecurityService;
import com.example.application.service.UserScoreChangeEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class GameLogic {

    private List<QuizQuestion> quizQuestions;
    private int currentQuestionIndex;
    private final Map<Integer, String> userAnswers;

    private int userScore;

    private final ApplicationEventPublisher eventPublisher;

    private static final Logger logger = LoggerFactory.getLogger(GameLogic.class);


    public GameLogic(ApplicationEventPublisher eventPublisher) {
        this.quizQuestions = null;
        this.currentQuestionIndex = -1;
        this.userAnswers = new HashMap<>();
        this.userScore = 0;
        this.eventPublisher = eventPublisher;
    }

    public void setPlayer(Player player) {
    }

    public int getUserScore(){
        return userScore;
    }

    public Map<Integer, String> getUserAnswers() {
        return userAnswers;
    }

    public void setFirstQuestionIndex(){
        currentQuestionIndex = -1;
    }

    public int getCurrentQuestionIndex() {
        return currentQuestionIndex;
    }

    public List<QuizQuestion> getQuizQuestions() {
        return quizQuestions;
    }

    public void updateQuizQuestions(QuizService quizService){

        logger.debug("Updating quiz questions");
        quizQuestions = quizService.getQuizQuestions();
        setFirstQuestionIndex();
        userAnswers.clear();
        userScore = 0;
    }

    public QuizQuestion getFollowingQuestion(){

        if (currentQuestionIndex < quizQuestions.size()-1) {
            currentQuestionIndex++;
            logger.debug("current question index: "+ currentQuestionIndex);
            return quizQuestions.get(currentQuestionIndex);
        }
        else {
            currentQuestionIndex++;
            logger.debug("current question index: " + currentQuestionIndex);
            return null;
        }
    }

    public QuizQuestion getPreviousQuestion() {

        if (currentQuestionIndex > 0) {
            currentQuestionIndex--;
            System.out.println(quizQuestions.get(currentQuestionIndex).question()+"answers: "+
                    quizQuestions.get(currentQuestionIndex).correctAnswer()+" "
                    +quizQuestions.get(currentQuestionIndex).incorrectAnswers());
            logger.debug("current question index: "+ currentQuestionIndex);
            return quizQuestions.get(currentQuestionIndex);
        } else {
            currentQuestionIndex--;
            logger.debug("current question index: "+ currentQuestionIndex);
            return null;
        }
    }

    public void updateUserScore() {
        this.userScore++;
        eventPublisher.publishEvent(new UserScoreChangeEvent(this, this.userScore));
    }


    public String getResult(String userAnswer){

        String correctAnswer = quizQuestions.get(currentQuestionIndex).correctAnswer();

        if(correctAnswer.equals(userAnswer)){

            updateUserScore();
            return "Correct! The answer is: " + correctAnswer;
        }
        else{
            return "Incorrect. The correct answer is: " + correctAnswer;
        }
    }
}
