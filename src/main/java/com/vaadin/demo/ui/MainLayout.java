package com.vaadin.demo.ui;

import com.vaadin.demo.ui.component.ThemeToggle;
import com.vaadin.demo.ui.view.ComponentsView;
import com.vaadin.demo.ui.view.DashboardView;
import com.vaadin.demo.ui.view.ProductsView;
import com.vaadin.demo.ui.view.ReportsView;
import com.vaadin.demo.ui.view.SettingsView;
import com.vaadin.demo.ui.view.UsersView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.Layout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Layout
@AnonymousAllowed
public class MainLayout extends AppLayout implements AfterNavigationObserver {

    private final Span navbarTitle = new Span("Dashboard");

    public MainLayout() {
        setPrimarySection(Section.DRAWER);
        addDrawerContent();
        addNavbarContent();
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        if (getContent() != null) {
            PageTitle annotation = getContent().getClass().getAnnotation(PageTitle.class);
            if (annotation != null) {
                String title = annotation.value();
                int dash = title.indexOf(" \u2014");
                navbarTitle.setText(dash > 0 ? title.substring(0, dash) : title);
            }
        }
    }

    private void addNavbarContent() {
        DrawerToggle toggle = new DrawerToggle();
        toggle.setAriaLabel("Menu");

        navbarTitle.getStyle()
                .set("font-weight", "600")
                .set("font-size", "var(--aura-font-size-m)");

        addToNavbar(true, toggle, navbarTitle);
    }

    private void addDrawerContent() {
        // Header: logo + app name
        HorizontalLayout drawerHeader = new HorizontalLayout();
        drawerHeader.setAlignItems(FlexComponent.Alignment.CENTER);
        drawerHeader.getStyle()
                .set("padding", "var(--vaadin-gap-xl)")
                .set("gap", "var(--vaadin-gap-m)");

        var logoIcon = VaadinIcon.VAADIN_H.create();
        logoIcon.getStyle()
                .set("color", "var(--aura-accent-color)")
                .set("width", "1.5rem")
                .set("height", "1.5rem");

        Span appTitle = new Span("Vaadin Demo");
        appTitle.getStyle()
                .set("font-weight", "600")
                .set("font-size", "var(--aura-font-size-m)");

        drawerHeader.add(logoIcon, appTitle);

        // Navigation
        SideNav dashboardNav = new SideNav();
        dashboardNav.addItem(new SideNavItem("Dashboard", DashboardView.class, VaadinIcon.HOME.create()));
        dashboardNav.addItem(new SideNavItem("Components", ComponentsView.class, VaadinIcon.PUZZLE_PIECE.create()));

        SideNav mainNav = new SideNav("Workspace");
        mainNav.setCollapsible(true);
        mainNav.addItem(new SideNavItem("Products", ProductsView.class, VaadinIcon.PACKAGE.create()));
        mainNav.addItem(new SideNavItem("Users", UsersView.class, VaadinIcon.USERS.create()));
        mainNav.addItem(new SideNavItem("Reports", ReportsView.class, VaadinIcon.BAR_CHART.create()));

        SideNav growthNav = new SideNav("Growth");
        growthNav.setCollapsible(true);
        growthNav.addItem(placeholderItem("Analytics", VaadinIcon.CHART));
        growthNav.addItem(placeholderItem("Revenue", VaadinIcon.MONEY));
        growthNav.addItem(placeholderItem("Engagement", VaadinIcon.CHART_LINE));
        growthNav.addItem(placeholderItem("Billing", VaadinIcon.CREDIT_CARD));

        SideNav adminNav = new SideNav("Admin");
        adminNav.setCollapsible(true);
        adminNav.addItem(new SideNavItem("Settings", SettingsView.class, VaadinIcon.COG.create()));

        Scroller scroller = new Scroller(new VerticalLayout(dashboardNav, mainNav, growthNav, adminNav));
        scroller.setScrollDirection(Scroller.ScrollDirection.VERTICAL);
        scroller.getStyle().set("flex", "1");

        // Footer: user avatar + name
        Avatar userAvatar = new Avatar("John Smith");
        userAvatar.setAbbreviation("JS");
        userAvatar.getStyle().set("--vaadin-avatar-size", "2rem");

        Span userName = new Span("John Smith");
        userName.getStyle()
                .set("font-size", "var(--aura-font-size-s)")
                .set("flex", "1");

        ThemeToggle themeToggle = new ThemeToggle();

        HorizontalLayout drawerFooter = new HorizontalLayout(userAvatar, userName, themeToggle);
        drawerFooter.setAlignItems(FlexComponent.Alignment.CENTER);
        drawerFooter.setWidthFull();
        drawerFooter.getStyle()
                .set("padding", "var(--vaadin-gap-m)")
                .set("border-top", "1px solid var(--vaadin-border-color)")
                .set("gap", "var(--vaadin-gap-s)");

        addToDrawer(drawerHeader, scroller, drawerFooter);
    }

    private SideNavItem placeholderItem(String label, VaadinIcon icon) {
        SideNavItem item = new SideNavItem(label);
        item.setPrefixComponent(icon.create());
        item.getElement().addEventListener("click", e -> {
            Notification n = Notification.show(label + " — not yet implemented", 2500, Notification.Position.MIDDLE);
            n.addThemeVariants(NotificationVariant.LUMO_PRIMARY);
        });
        return item;
    }
}
