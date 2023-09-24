package com.example.application.views.PlayerView;

import com.example.application.entity.Player;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.BinderValidationStatus;
import com.vaadin.flow.shared.Registration;

import java.util.List;

public class PlayerForm extends FormLayout {

    TextField name = new TextField("Name");
    TextField score = new TextField("Score");

    Button save = new Button("Save");
    Button close = new Button("Cancel");

    Binder<Player> binder = new BeanValidationBinder<>(Player.class);

    public PlayerForm(List<Player> players) {

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
        return new HorizontalLayout(save, close);
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
            System.out.println("Set beand is " + player.getName());
        }else{
            System.out.println("player is null");}
        binder.setBean(player);
    }

    public static abstract class PlayerFormEvent extends ComponentEvent<PlayerForm> {
        private Player player;

        protected PlayerFormEvent(PlayerForm source, Player player) {
            super(source, false);
            this.player = player;
        }

        public Player getFormPlayer() {

            System.out.println("returnning get form player: "+player.getName());
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
