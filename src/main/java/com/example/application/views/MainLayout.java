package com.example.application.views;
import com.example.application.views.PlayerView.PlayerView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * TODO: uppdatera poäng efter varje runda?
 * TODO: om ny spelare skapas och sen start game, måste denna sparas till db
 * TODO: oklart fel i listan vid navigering lägst till höger och längst till vänster
 * TODO: env variabler och pusha till github
 * TODO: redigera login view till ngt trevligare
 */
@Route("")
@PermitAll
public class MainLayout extends AppLayout {
    private final PlayerView playerView;

    @Autowired
    public MainLayout(PlayerView playerView){
        this.playerView = playerView;
        createHeader();
        // ... Other layout configuration ...
        setContent(playerView);

    }

    private void createHeader(){
        H1 logo = new H1("Quiz Game");
        logo.addClassNames(
                LumoUtility.FontSize.LARGE,
                LumoUtility.Margin.MEDIUM);

        var header = new HorizontalLayout(new DrawerToggle(), logo );

        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.setWidthFull();
        header.addClassNames(
                LumoUtility.Padding.Vertical.NONE,
                LumoUtility.Padding.Horizontal.MEDIUM);

        addToNavbar(header);

    }
}
