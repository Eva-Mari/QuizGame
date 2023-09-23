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

import java.awt.*;


@Route(value = "playerview", layout = MainLayout.class)
public class PlayerView extends VerticalLayout {

    private final PlayerService playerService;
    private final H1 header = new H1("Players");

    private final Grid<Player> playerListGrid = new Grid<>(Player.class);


    @Autowired
    public PlayerView(PlayerService playerService){

        this.playerService = playerService;
        setSizeFull();
        configureGrid();
        updateGrid();

        add(header, playerListGrid);
    }

    private void configureGrid(){
        playerListGrid.setSizeFull();
        playerListGrid.setColumns("name", "score");

    }

    private void updateGrid(){
        playerListGrid.setItems(playerService.getPlayers());
    }
}
