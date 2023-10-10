package com.example.application.views.PlayerView;

import com.example.application.entity.Player;
import com.example.application.service.PlayerService;
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
import com.vaadin.flow.data.binder.BinderValidationStatus;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.shared.Registration;


public class PlayerForm extends FormLayout {

    TextField name = new TextField("Name");
    TextField score = new TextField("Score");

    Button save = new Button("Save");
    Button close = new Button("Cancel");

    //private final PlayerService playerService;

    private Button startgameButton = new Button("Start Game");

    Binder<Player> binder = new BeanValidationBinder<>(Player.class);

    private Player selectedPlayer;

   // private QuizService quizService;



    public PlayerForm() {



        binder.bindInstanceFields(this);

        score.setEnabled(false);


        add(name, score, createButtonsLayout());
    }

    private Component createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(event -> validateAndSave());
        close.addClickListener(event -> fireEvent(new CloseEvent(this)));

        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));

        startgameButton.addClassName("test-style");

        startgameButton.addClickListener(buttonClickEvent ->
                navigateToQuizView());
        return new HorizontalLayout(save, close);
    }

    private void navigateToQuizView() {
        VaadinSession.getCurrent().setAttribute("selectedPlayer", selectedPlayer);
        UI.getCurrent().navigate(QuizView.class);
    }


    private void validateAndSave() {

        System.out.println("This is the validate and save binder player: "+binder.getBean());
        if(binder.isValid()) {
            fireEvent(new SaveEvent(this, binder.getBean()));
        }
        else {
            BinderValidationStatus<Player> validationStatus = binder.validate();
            System.out.println("Validation errors: " + validationStatus.getValidationErrors());
        }
    }

    public void setPlayer(Player player) {
        if(player != null) {
            System.out.println("Set bean is " + player.getName());
        }else{
            System.out.println("player is null");}
        selectedPlayer = player;
        binder.setBean(player);
    }

    public static abstract class PlayerFormEvent extends ComponentEvent<PlayerForm> {
        private Player player;

        protected PlayerFormEvent(PlayerForm source, Player player) {
            super(source, false);
            this.player = player;
        }

        public Player getFormPlayer() {
            return player;
        }
    }

    public static class SaveEvent extends PlayerFormEvent {
        SaveEvent(PlayerForm source, Player player) {

            super(source, player);
            System.out.println("save event: "+player.getName());
        }
    }


    public static class CloseEvent extends PlayerFormEvent {
        CloseEvent(PlayerForm source) {
            super(source, null);
        }
    }


    public Registration addSaveListener(ComponentEventListener<SaveEvent> listener) {
        return addListener(SaveEvent.class, listener);
    }
    public Registration addCloseListener(ComponentEventListener<CloseEvent> listener) {
        return addListener(CloseEvent.class, listener);
    }


}
