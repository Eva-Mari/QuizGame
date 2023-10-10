package com.example.application.views.PlayerView;

import com.example.application.entity.Player;
import com.example.application.service.QuizService;
import com.example.application.views.GameView.DropDownMenu;
import com.example.application.views.GameView.QuizView;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.shared.Registration;


public class PlayerGameForm extends FormLayout {

    TextField name = new TextField("Name");
    TextField score = new TextField("Score");

    Button close = new Button("Close");

    private final Button startgameButton = new Button("Start Game");

    Binder<Player> binder = new BeanValidationBinder<>(Player.class);

    private Player selectedPlayer;

    private final DropDownMenu dropDownMenu;
    private final QuizService quizService;



    public PlayerGameForm(QuizService quizService) {

        this.quizService = quizService;
        this.dropDownMenu = new DropDownMenu(quizService.getUrLhandler());

        binder.bindInstanceFields(this);

        score.setEnabled(false);
        name.setEnabled(false);


        add(name, score, dropDownMenu, createButtonsLayout());
    }

    private Component createButtonsLayout() {

        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        close.addClickShortcut(Key.ESCAPE);


        close.addClickListener(event -> fireEvent(new PlayerGameForm.CloseEvent(this)));

        startgameButton.addClassName("test-style");

        startgameButton.addClickListener(buttonClickEvent ->
                navigateToQuizView());
        return new HorizontalLayout(close, startgameButton);
    }

    private void navigateToQuizView() {
        VaadinSession.getCurrent().setAttribute("selectedPlayer", selectedPlayer);
        UI.getCurrent().navigate(QuizView.class);
    }

    public void setPlayer(Player player) {
        if(player != null) {
            System.out.println("game form Set bean is " + player.getName());
            System.out.println("should not be null");
        }else{
            System.out.println("player is null");}
        selectedPlayer = player;
        binder.setBean(player);
    }

    public static abstract class PlayerGameFormEvent extends ComponentEvent<PlayerGameForm> {
        private final Player player;

        protected PlayerGameFormEvent(PlayerGameForm source, Player player) {
            super(source, false);
            this.player = player;
        }

    }

    public static class CloseEvent extends PlayerGameForm.PlayerGameFormEvent {
        CloseEvent(PlayerGameForm source) {
            super(source, null);
        }
    }


    public Registration addCloseListener(ComponentEventListener<PlayerGameForm.CloseEvent> listener) {
        return addListener(PlayerGameForm.CloseEvent.class, listener);
    }


}
