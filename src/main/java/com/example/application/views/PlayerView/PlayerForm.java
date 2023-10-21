package com.example.application.views.PlayerView;

import com.example.application.entity.Player;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.BinderValidationStatus;
import com.vaadin.flow.shared.Registration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PlayerForm extends FormLayout {

    TextField name = new TextField("Name");
    TextField score = new TextField("Score");
    Button save = new Button("Save");
    Button close = new Button("Cancel");
    Binder<Player> binder = new BeanValidationBinder<>(Player.class);
    private Player selectedPlayer;

    private static final Logger logger = LoggerFactory.getLogger(PlayerForm.class);



    public PlayerForm() {

        binder.bindInstanceFields(this);

        score.setEnabled(false);

        add(name, score, createButtonsLayout());
    }

    private Component createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickListener(event -> validateAndSave());
        close.addClickListener(event -> fireEvent(new CloseEvent(this)));

        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));

        return new HorizontalLayout(save, close);
    }

    private void validateAndSave() {

        logger.debug("This is the validate and save binder player: "+binder.getBean());
        if(binder.isValid()) {
            fireEvent(new SaveEvent(this, binder.getBean()));
        }
        else {
            BinderValidationStatus<Player> validationStatus = binder.validate();
            logger.debug("Validation errors: " + validationStatus.getValidationErrors());
        }
    }

    public void setPlayer(Player player) {
        if(player != null) {
            logger.debug("Set bean is " + player.getName());
        }else{
            logger.debug("player is null");}
        selectedPlayer = player;
        binder.setBean(player);
    }

    public static abstract class PlayerFormEvent extends ComponentEvent<PlayerForm> {
        private final Player player;

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
            logger.debug("save event: "+player.getName());
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
