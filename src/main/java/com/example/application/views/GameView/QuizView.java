package com.example.application.views.GameView;

import com.example.application.entity.QuizQuestion;
import com.example.application.game.GameLogic;
import com.example.application.service.QuizService;
import com.example.application.service.UserScoreChangeEvent;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@PageTitle("Quiz Game")
@Route(value = "hello", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class QuizView extends VerticalLayout {

    private final QuizService quizService;
    private final GameLogic gameLogic;
    private final Div questionDiv;
    private final Button[] answerButtons;
    private final Button startGame;
    private final DropDownMenu dropDownMenu;
    private final FlexLayout questionLayout = new FlexLayout();
    private final FlexLayout answerLayout = new FlexLayout();
    private final Button nextQuestionButton = new Button("Next Question");

    private final Button endGameButton = new Button("End Game");
    private final Button restartGameButton;
    private static final int NUM_ANSWER_OPTIONS = 4;
    private final Button previousButton;
    private final Div scoreDiv;



    @Autowired
    public QuizView(QuizService quizService, ApplicationEventPublisher eventPublisher) {
        this.quizService = quizService;
        this.gameLogic = new GameLogic(eventPublisher);
        this.questionDiv = new Div();
        this.answerButtons = new Button[4];
        this.dropDownMenu = new DropDownMenu(quizService.getUrLhandler());
        this.startGame = new Button("Start Game");
        restartGameButton = new Button("Restart Game");
        startGame.addClassName("test-style"); // Add a custom CSS class to the Label
        this.previousButton = new Button("Previous question");

        HorizontalLayout buttonLayout = alignHorizontalLayout();
        buttonLayout.add(previousButton);
        buttonLayout.add(nextQuestionButton);
        buttonLayout.add(endGameButton);

        HorizontalLayout headerLayout = alignHorizontalLayout();

        HorizontalLayout startGameLayout = alignHorizontalLayout();
        startGameLayout.add(startGame);


        scoreDiv = new Div();
        scoreDiv.addClassName("score-div");


        headerLayout.add(scoreDiv);


        configureFlexLayout(questionLayout);
        configureFlexLayout(answerLayout);

        Div answerDiv = new Div();
        for (int i = 0; i < NUM_ANSWER_OPTIONS; i++) {
            answerButtons[i] = new Button();
            answerButtons[i].addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            answerDiv.add(answerButtons[i], new Div());
            int selectedAnswer = i;
            answerButtons[i].addClickListener(event -> displayResult(selectedAnswer));
        }

        nextQuestionButton.addClickListener(event -> loadNextQuestion());
        previousButton.addClickListener(buttonClickEvent -> loadPreviousQuestion());

        endGameButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        endGameButton.addClickListener(buttonClickEvent -> restartGame());

        questionLayout.add(questionDiv);
        answerLayout.add(answerDiv);

        // Initially, hide everything except the difficulty/category selection and "Start Game" button
        restartGame();

        // Add click listener to start game button
        startGame.addClickListener(buttonClickEvent -> {
            startGame();
        });

        restartGameButton.addClickListener(buttonClickEvent -> {
            restartGame();
        });

        add(dropDownMenu, startGameLayout, headerLayout, questionLayout, answerLayout, buttonLayout, restartGameButton);
    }


    @EventListener
    public void handleUserScoreChangeEvent(UserScoreChangeEvent event) {
        int newUserScore = event.getNewUserScore();
        updateScoreDiv(newUserScore);
    }

    private void updateScoreDiv(int score) {
        Span scoreSpan = new Span("Total score: " + score);
        scoreDiv.removeAll();
        scoreDiv.add(scoreSpan);
    }

    private void restartGame() {

        // Hide and show appropriate components
        dropDownMenu.setVisible(true);
        questionLayout.setVisible(false);
        answerLayout.setVisible(false);
        scoreDiv.setVisible(false);
        nextQuestionButton.setVisible(false);
        previousButton.setVisible(false);
        startGame.setVisible(true); // Show the "Start Game" button again
        restartGameButton.setVisible(false); // Hide the restart button
        endGameButton.setVisible(false);
    }
    private void startGame(){

        gameLogic.updateQuizQuestions(quizService);
        System.out.println("updating questions.. "+ gameLogic.getQuizQuestions().size());
        gameLogic.setFirstQuestionIndex();
        loadNextQuestion();

        // Make everything visible except the "Start Game" button and difficulty/category selection
        questionLayout.setVisible(true);
        answerLayout.setVisible(true);
        nextQuestionButton.setVisible(true);
        previousButton.setVisible(true);
        dropDownMenu.setVisible(false);
        startGame.setVisible(false);
        scoreDiv.setVisible(true);
        endGameButton.setVisible(true);
    }

    private void endGame(){

        System.out.println("USER SCORE: "+ gameLogic.getUserScore());
        updateScoreDiv(gameLogic.getUserScore());
        //scoreDiv.setVisible(true);
        answerLayout.setVisible(false);
        questionDiv.setVisible(false);
        endGameButton.setVisible(false);
        nextQuestionButton.setVisible(false);
        previousButton.setVisible(false);
        restartGameButton.setVisible(true);
    }

    private void loadNextQuestion() {
        // fetch data from the trivia API
        boolean gameOver = gameLogic.followingQuestion();
        QuizQuestion quizQuestion = gameLogic.getCurrentQuestion();

        if (!gameOver) {

            // update the div with the question
            answerLayout.setVisible(true);
            questionDiv.setText("Question "+ gameLogic.getCurrentQuestionIndex()+": "+ quizQuestion.question().text());
            updateAnswerButtons(quizQuestion);
            checkAnswerStatus(quizQuestion);
            updateScoreDiv(gameLogic.getUserScore());

        }else {

            questionDiv.setText("No following questions");
            answerLayout.setVisible(false);
//            questionDiv.setText("No more questions to load!");
//            System.out.println("USER SCORE: "+ gameLogic.getUserScore());
//            updateScoreDiv(gameLogic.getUserScore());
//            //scoreDiv.setVisible(true);
//            answerLayout.setVisible(false);
//            nextQuestionButton.setVisible(false);
//            previousButton.setVisible(false);
//            restartGameButton.setVisible(true);
        }
    }

    private void loadPreviousQuestion(){
        QuizQuestion quizQuestion = gameLogic.getPreviousQuestion();

        if(quizQuestion != null) {
            answerLayout.setVisible(true);
            questionDiv.setText("Question " + gameLogic.getCurrentQuestionIndex() + ": " + quizQuestion.question().text());
            updateAnswerButtons(quizQuestion);
            checkAnswerStatus(quizQuestion);
        }
        else{
            questionDiv.setText("No previous questions to load!");
            answerLayout.setVisible(false);
        }

    }

    private void displayResult(int selectedAnswerIndex) {

        // disable possibility to choosing a different answer
        for (int i = 0; i < NUM_ANSWER_OPTIONS; i++) {
            answerButtons[i].setEnabled(false);
            answerButtons[i].addThemeVariants();
        }

        if (!gameLogic.getUserAnswers().containsKey(gameLogic.getCurrentQuestionIndex())) {
            // Store the user's answer for the current question
            // check answer
            String selectedAnswer = answerButtons[selectedAnswerIndex].getText();
            questionDiv.setText(gameLogic.getCurrentQuestion().question().text() +" **"+gameLogic.getResult(selectedAnswer));
            gameLogic.getUserAnswers().put(gameLogic.getCurrentQuestionIndex(), selectedAnswer);
        }

    }

    private void checkAnswerStatus(QuizQuestion quizQuestion){
        if (gameLogic.getUserAnswers().containsKey(gameLogic.getCurrentQuestionIndex())) {
            // User has answered this question, disable the answer buttons
            for (int i = 0; i < NUM_ANSWER_OPTIONS; i++) {
                answerButtons[i].setEnabled(false);
            }
        } else {
            // User has not answered this question, enable the answer buttons
            updateAnswerButtons(quizQuestion);
        }
    }

    private void updateAnswerButtons(QuizQuestion quizQuestion){
        // add the answer alternatives to an arraylist, and shuffle it
        List<String> answerOptions = new ArrayList<>();
        answerOptions.add(quizQuestion.correctAnswer());
        answerOptions.addAll(quizQuestion.incorrectAnswers());
        Collections.shuffle(answerOptions);

        // add the answer alternatives to the button texts
        for (int i = 0; i < NUM_ANSWER_OPTIONS; i++) {
            answerButtons[i].setText(answerOptions.get(i));
            answerButtons[i].setEnabled(true);
        }
    }

    private void configureFlexLayout(FlexLayout flexLayout){
        flexLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        flexLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        flexLayout.setWidthFull();
    }

    private HorizontalLayout alignHorizontalLayout(){
        HorizontalLayout layout = new HorizontalLayout();
        layout.setPadding(true);
        layout.setAlignItems(Alignment.CENTER);
        layout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        layout.setWidthFull();

        return layout;
    }
}
