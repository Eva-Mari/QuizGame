package com.example.application.views.PlayerView;

import com.example.application.entity.Player;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import java.awt.*;

public class PlayerView extends HorizontalLayout {

    private H1 header = new H1("Players");

    private Grid<Player> playerListGrid;

    TextField inputTextField;


}
