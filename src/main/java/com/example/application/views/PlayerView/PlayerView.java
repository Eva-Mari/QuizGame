package com.example.application.views.PlayerView;

import com.example.application.entity.Player;
import com.example.application.service.PlayerService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;


@Route(value = "playerview", layout = MainLayout.class)
public class PlayerView extends VerticalLayout {

    private final PlayerService playerService;
    private final H1 header = new H1("Players");

    private final Grid<Player> playerListGrid = new Grid<>(Player.class);

    private PlayerForm playerForm;


    @Autowired
    public PlayerView(PlayerService playerService){

        this.playerService = playerService;
        setSizeFull();
        configureGrid();

        configureForm();

        add(header, getContent());
        updateGrid();
        closeEditor();
    }

    private void configureGrid(){
        playerListGrid.setSizeFull();
        playerListGrid.setColumns("name", "score");
        playerListGrid.asSingleSelect().addValueChangeListener(event ->{
                editPlayer(event.getValue());
        });
    }

    public HorizontalLayout getContent(){
        HorizontalLayout horizontalLayout = new HorizontalLayout(playerListGrid, playerForm);
        horizontalLayout.setFlexGrow(2, playerListGrid);
        horizontalLayout.setFlexGrow(1, playerForm);
        horizontalLayout.setSizeFull();
        return horizontalLayout;
    }

    private void configureForm() {
        playerForm = new PlayerForm(playerService.getPlayers());
        playerForm.setWidth("25em");
        playerForm.addSaveListener(this::savePlayer); // <1>
        //playerForm.addDeleteListener(this::deleteContact); // <2>
        playerForm.addCloseListener(e -> closeEditor()); // <3>
    }




    private void updateGrid(){
        playerListGrid.setItems(playerService.getPlayers());
    }

    private void editPlayer(Player player){

        if (player == null) {
            closeEditor();
        } else {
            playerForm.setPlayer(player);
            playerForm.setVisible(true);
            addClassName("editing");
        }
    }

    private void closeEditor() {
        playerForm.setPlayer(null);
        playerForm.setVisible(false);

    }

    private void savePlayer(PlayerForm.SaveEvent event) {
        System.out.println("inside save event: "+event.getFormPlayer().getName());
        playerService.savePlayer(event.getFormPlayer());
        updateGrid();
        closeEditor();
        System.out.println("edited and closed");
    }

}
