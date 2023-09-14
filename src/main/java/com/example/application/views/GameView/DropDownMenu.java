package com.example.application.views.GameView;

import com.example.application.game.URLhandler;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class DropDownMenu extends Composite<VerticalLayout> {

    private final ComboBox<String> categoryCombo;

    private final ComboBox<String> difficultyCombo;


    public DropDownMenu(URLhandler urLhandler) {
            // Create a ComboBox (dropdown menu)
            categoryCombo = new ComboBox<>("Select category");

            difficultyCombo = new ComboBox<>("Select difficulty");

            // Add some items to the dropdown
            categoryCombo.setItems("All", "Music", "Sport and leisure", "Film and tv", "Arts and literature"
            , "History", "Society and culture", "Science", "Geography", "Food and drink",
                    "General knowledge");

            difficultyCombo.setItems("All", "Easy", "Medium", "Hard");

            // Set an event listener to handle item selection
            categoryCombo.addValueChangeListener(event -> {
                String selectedOption = event.getValue();
                String chosenCategory = DropDownMenu.getUserChoice(selectedOption);
                //quizService.setCategory(chosenCategory);
                urLhandler.setCategory(chosenCategory);
                urLhandler.getUpdatedUrl();

            });

            difficultyCombo.addValueChangeListener(event->{
                String selectedDifficulty = event.getValue();
                selectedDifficulty = DropDownMenu.getUserChoice(selectedDifficulty);
                //quizService.setDifficulty(selectedDifficulty);
                urLhandler.setDifficulty(selectedDifficulty);
                urLhandler.getUpdatedUrl();
            });

            // Add the ComboBox to the layout
            getContent().add(categoryCombo, difficultyCombo);
        }

    public static String getUserChoice(String choice){

        String userChoice = choice.replaceAll(" ", "_");
        userChoice = userChoice.toLowerCase();
        return userChoice;
    }
    }