package com.example.application.views.GameView;

import com.example.application.game.URLhandler;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class DropDownMenu extends Composite<VerticalLayout> {


    public DropDownMenu(URLhandler urLhandler) {

        ComboBox<String> categoryCombo = new ComboBox<>("Select category");
        ComboBox<String> difficultyCombo = new ComboBox<>("Select difficulty");

        categoryCombo.setItems("All", "Music", "Sport and leisure", "Film and tv", "Arts and literature"
            , "History", "Society and culture", "Science", "Geography", "Food and drink",
                    "General knowledge");
        difficultyCombo.setItems("All", "Easy", "Medium", "Hard");

        categoryCombo.addValueChangeListener(event -> {
            String selectedOption = event.getValue();
            String chosenCategory = DropDownMenu.getUserChoice(selectedOption);
            urLhandler.setCategory(chosenCategory);
            urLhandler.getUpdatedUrl();

            });

            difficultyCombo.addValueChangeListener(event->{
                String selectedDifficulty = event.getValue();
                selectedDifficulty = DropDownMenu.getUserChoice(selectedDifficulty);
                urLhandler.setDifficulty(selectedDifficulty);
                urLhandler.getUpdatedUrl();
            });

            getContent().add(categoryCombo, difficultyCombo);
        }

    public static String getUserChoice(String choice){

        String userChoice = choice.replaceAll(" ", "_");
        userChoice = userChoice.toLowerCase();
        return userChoice;
    }
    }