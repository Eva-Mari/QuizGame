package com.example.application.views.PlayerView;
import com.example.application.entity.Player;
import com.example.application.game.GameLogic;
import com.example.application.service.PlayerService;
import com.example.application.service.QuizService;
import com.example.application.service.SecurityService;
import com.example.application.views.GameView.QuizView;
import com.example.application.views.MainLayout;
//import com.example.application.views.MainView;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.spring.annotation.SpringComponent;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;


@Route(value = "/player")//, layout = MainView.class)
@PermitAll
@SpringComponent
public class PlayerView extends VerticalLayout implements BeforeEnterObserver {

    private final PlayerService playerService;
    private final H1 header = new H1("Players");

    private final Button addButton = new Button("Add player");

    private final Grid<Player> playerListGrid = new Grid<>(Player.class);

    private PlayerForm playerForm;

    private PlayerGameForm playerGameForm;

    private final GameLogic gameLogic;

    private Player selectedPlayer;

    private final Button logoutButton = new Button("Log out");

    private final SecurityService securityService;

    private QuizService quizService;



    @Autowired
    public PlayerView(PlayerService playerService, GameLogic gameLogic, SecurityService securityService, QuizService quizService){

        this.quizService = quizService;
        this.securityService = securityService;
        this.playerService = playerService;
        this.gameLogic = gameLogic;
        setSizeFull();
        configureGrid();

        configureForm();

        logoutButton.addClickListener(buttonClickEvent -> securityService.logout());

        add(header, getContent(), configureButton());

        addButton.addClickListener(buttonClickEvent -> addPlayer());
        updateGrid();
        closeEditor();

        System.out.println(playerForm.binder.getBean());
    }

    private HorizontalLayout configureButton(){
        HorizontalLayout buttonLayout = new HorizontalLayout(addButton, logoutButton);
        buttonLayout.setWidthFull();
        return buttonLayout;
    }

    private void configureGrid(){
        playerListGrid.setSizeFull();
        playerListGrid.setColumns("name", "score");
        playerListGrid.asSingleSelect().addValueChangeListener(event ->{
            selectedPlayer = event.getValue();
            gameLogic.setPlayer(selectedPlayer);
            displayGameForm(event.getValue());
        });
    }

    public HorizontalLayout getContent(){
        HorizontalLayout horizontalLayout = new HorizontalLayout(playerListGrid, playerForm, playerGameForm);
        horizontalLayout.setFlexGrow(2, playerListGrid);
        horizontalLayout.setFlexGrow(1, playerForm);
        horizontalLayout.setFlexGrow(1, playerGameForm);
        horizontalLayout.setSizeFull();
        return horizontalLayout;
    }

    private void configureForm() {
        playerForm = new PlayerForm();
        playerGameForm = new PlayerGameForm(quizService);
        playerGameForm.setWidth("25em");
        playerForm.setWidth("25em");
        playerForm.addSaveListener(this::savePlayer);
        playerForm.addCloseListener(e -> closeEditor());
        playerGameForm.addCloseListener(closeEvent -> closeGameForm());
        playerGameForm.setVisible(false);

    }

    public void updateGrid(){
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

    private void displayGameForm(Player player){

        if (player == null) {
            closeGameForm();
            System.out.println("closing gamform, player is null");
        } else {
            playerGameForm.setPlayer(player);
            playerGameForm.setVisible(true);
            System.out.println("displaying game form");
        }
    }

    private void closeGameForm() {
        playerGameForm.setPlayer(null);
        playerGameForm.setVisible(false);

    }

    private void savePlayer(PlayerForm.SaveEvent event) {
        System.out.println("inside save event: "+event.getFormPlayer().getName());
        playerService.savePlayer(event.getFormPlayer());
        updateGrid();
        closeEditor();
        System.out.println("edited and closed");
    }

    private void addPlayer() {
        playerListGrid.asSingleSelect().clear();
        editPlayer(new Player());
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        updateGrid();
    }


}
