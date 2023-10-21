package com.example.application.views.LoginView;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.component.html.Anchor;

@Route("login")
@AnonymousAllowed
public class LoginView extends VerticalLayout {
    /**
     * URL that Spring uses to connect to Google services
     */
    private static final String OAUTH_URL = "/oauth2/authorization/google";

    public LoginView() {

        H1 header = new H1("Welcome to the Quiz Game!");
        H4 info = new H4("Log in to continue..");
        Anchor loginLink = new Anchor(OAUTH_URL, "Login with Google");

        loginLink.getElement().setAttribute("router-ignore", true);
        add(header, info, loginLink);
        getStyle().set("padding", "200px");
        setAlignItems(FlexComponent.Alignment.CENTER);
    }
}