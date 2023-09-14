package com.example.application.game;

import com.example.application.entity.Question;
import com.example.application.entity.QuizQuestion;
import com.example.application.service.QuizService;
import com.example.application.service.UserScoreChangeEvent;
import org.springframework.context.ApplicationEventPublisher;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameLogic {

    private List<QuizQuestion> quizQuestions;
    private int currentQuestionIndex;
    private final Map<Integer, String> userAnswers;

    private int userScore;

    private final ApplicationEventPublisher eventPublisher;

    public GameLogic(ApplicationEventPublisher eventPublisher) {
        this.quizQuestions = null;
        this.currentQuestionIndex = -1;
        this.userAnswers = new HashMap<>();
        this.userScore = 0;
        this.eventPublisher = eventPublisher;
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
        quizQuestions = quizService.getQuizQuestions();
        System.out.println("All questions: ");
        for (QuizQuestion question: quizQuestions) {
            System.out.println(question.question());
        }
        setFirstQuestionIndex();
        userAnswers.clear();
        userScore = 0;
    }

    public boolean followingQuestion(){

        if (currentQuestionIndex < quizQuestions.size()-1) {
            currentQuestionIndex++;
            return false;
        }
        return true;
    }

    public QuizQuestion getCurrentQuestion() {

        return quizQuestions.get(currentQuestionIndex);
    }

    public QuizQuestion getPreviousQuestion() {
        if (currentQuestionIndex > 0) {
            currentQuestionIndex--;
            System.out.println(quizQuestions.get(currentQuestionIndex).question()+"answers: "+
                    quizQuestions.get(currentQuestionIndex).correctAnswer()+" "
                    +quizQuestions.get(currentQuestionIndex).incorrectAnswers());
            return quizQuestions.get(currentQuestionIndex);
        } else {
            System.out.println("No previous question available.");
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
