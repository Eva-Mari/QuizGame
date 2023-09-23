package com.example.application.views;


import com.example.application.views.GameView.QuizView;
import com.example.application.views.PlayerView.PlayerView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.lumo.LumoUtility;
import com.vaadin.flow.theme.lumo.LumoUtility.AlignItems;
import com.vaadin.flow.theme.lumo.LumoUtility.BoxSizing;
import com.vaadin.flow.theme.lumo.LumoUtility.Display;
import com.vaadin.flow.theme.lumo.LumoUtility.FlexDirection;
import com.vaadin.flow.theme.lumo.LumoUtility.FontSize;
import com.vaadin.flow.theme.lumo.LumoUtility.FontWeight;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import com.vaadin.flow.theme.lumo.LumoUtility.Height;
import com.vaadin.flow.theme.lumo.LumoUtility.ListStyleType;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import com.vaadin.flow.theme.lumo.LumoUtility.Overflow;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;
import com.vaadin.flow.theme.lumo.LumoUtility.TextColor;
import com.vaadin.flow.theme.lumo.LumoUtility.Whitespace;
import com.vaadin.flow.theme.lumo.LumoUtility.Width;
import org.vaadin.lineawesome.LineAwesomeIcon;

import javax.swing.text.html.ListView;

/**
 * The main view is a top-level placeholder for other views.
 */
public class MainLayout extends AppLayout {

    public MainLayout(){
        createHeader();
        createDrawer();
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

    private void createDrawer() {
        addToDrawer(new VerticalLayout(
                new RouterLink("QuizView", QuizView.class),
                new RouterLink("PlayerView", PlayerView.class)
        ));
    }

    /**
     * A simple navigation item component, based on ListItem element.
     */
//    public static class MenuItemInfo extends ListItem {
//
//        private final Class<? extends Component> view;
//
//        public MenuItemInfo(String menuTitle, Component icon, Class<? extends Component> view) {
//            this.view = view;
//            RouterLink link = new RouterLink();
//            // Use Lumo classnames for various styling
//            link.addClassNames(Display.FLEX, Gap.XSMALL, Height.XSMALL, AlignItems.CENTER, Padding.Horizontal.SMALL,
//                    TextColor.BODY);
//            link.setRoute(view);
//
//            Span text = new Span(menuTitle);
//            // Use Lumo classnames for various styling
//            text.addClassNames(FontWeight.MEDIUM, FontSize.MEDIUM, Whitespace.NOWRAP);
//
//            if (icon != null) {
//                link.add(icon);
//            }
//            link.add(text);
//            add(link);
//        }
//
//        public Class<?> getView() {
//            return view;
//        }
//    }
//
//    public MainLayout() {
//        addToNavbar(createHeaderContent());
//
//    }
//
//    private Component createHeaderContent() {
//        Header header = new Header();
//        header.addClassNames(BoxSizing.BORDER, Display.FLEX, FlexDirection.COLUMN, Width.FULL);
//
//        Nav nav = new Nav();
//        nav.addClassNames(Display.FLEX, Overflow.AUTO, Padding.Horizontal.MEDIUM, Padding.Vertical.XSMALL);
//
//        // Wrap the links in a list; improves accessibility
//        UnorderedList list = new UnorderedList();
//        list.addClassNames(Display.FLEX, Gap.SMALL, ListStyleType.NONE, Margin.LARGE, Padding.NONE);
//        nav.add(list);
//
//        for (MenuItemInfo menuItem : createMenuItems()) {
//            list.add(menuItem);
//        }
//
//        header.add(nav);
//        return header;
//    }
//
//    private MenuItemInfo[] createMenuItems() {
//        return new MenuItemInfo[]{ //
//                new MenuItemInfo("Quiz View", LineAwesomeIcon.GLOBE_SOLID.create(), QuizView.class), //
//        };
//    }

}
