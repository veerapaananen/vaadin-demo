package com.vaadin.demo.ui;

import com.vaadin.demo.ui.component.ViewFooter;
import com.vaadin.demo.ui.component.ViewHeader;
import com.vaadin.demo.ui.component.ViewHeading;
import com.vaadin.demo.ui.util.Lucide;
import com.vaadin.demo.ui.util.Theme;
import com.vaadin.demo.ui.view.*;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.avatar.AvatarVariant;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.Layout;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Layout
@AnonymousAllowed
public class MainLayout extends AppLayout {

    public MainLayout() {
        setPrimarySection(Section.DRAWER);
        initDrawer();
    }

    private void initDrawer() {
        // Header
        Avatar appLogo = new Avatar();
        appLogo.addThemeNames(Theme.AVATAR_VAADIN, Theme.AVATAR_SQUARE);
        appLogo.addThemeVariants(AvatarVariant.AURA_FILLED);
        appLogo.getStyle()
                .set("--vaadin-avatar-size", "1.25lh")
                .set("margin-inline", "calc(var(--vaadin-padding-inline-container) - var(--vaadin-side-nav-item-border-width) - (var(--vaadin-avatar-size) - var(--vaadin-icon-size, 1lh)) / 2)");

        ViewHeading appName = new ViewHeading("Vaadin Demo");

        DrawerToggle toggle = new DrawerToggle();
        toggle.addThemeNames(Theme.DRAWER_TOGGLE_PERMANENT);
        toggle.addThemeVariants(ButtonVariant.TERTIARY);

        ViewHeader header = new ViewHeader(appLogo, appName, toggle);

        // Navigation
        SideNav nav = new SideNav();
        nav.addItem(new SideNavItem("Dashboard", DashboardView.class, Lucide.HOUSE.create()));
        nav.addItem(new SideNavItem("Components", ComponentsView.class, Lucide.COMPONENT.create()));

        SideNav workspaceNav = new SideNav("Workspace");
        workspaceNav.addItem(new SideNavItem("Products", ProductsView.class, Lucide.BARCODE.create()));
        workspaceNav.addItem(new SideNavItem("Users", UsersView.class, Lucide.USERS.create()));
        workspaceNav.addItem(new SideNavItem("Reports", ReportsView.class, Lucide.FILE_CHART_COLUMN_INCREASING.create()));
        workspaceNav.setCollapsible(true);

        SideNav growthNav = new SideNav("Growth");
        growthNav.addItem(placeholderItem("Analytics", Lucide.CHART_PIE));
        growthNav.addItem(placeholderItem("Revenue", Lucide.PIGGY_BANK));
        growthNav.addItem(placeholderItem("Engagement", Lucide.ACTIVITY));
        growthNav.addItem(placeholderItem("Billing", Lucide.CREDIT_CARD));
        growthNav.setCollapsible(true);

        SideNav adminNav = new SideNav("Admin");
        adminNav.addItem(new SideNavItem("Settings", SettingsView.class, Lucide.SETTINGS.create()));
        adminNav.setCollapsible(true);

        Scroller scroller = new Scroller();
        scroller.getElement().appendChild(nav.getElement(), new Hr().getElement(), workspaceNav.getElement(), new Hr().getElement(), growthNav.getElement(), new Hr().getElement(), adminNav.getElement());

        // Footer
        Avatar avatar = new Avatar("John Smith");
        avatar.addThemeVariants(AvatarVariant.AURA_FILLED);
        avatar.getStyle().set("--vaadin-avatar-size", "1.25lh");
        avatar.setAbbreviation("J");

        Button button = new Button("John Smith");
        button.addThemeVariants(ButtonVariant.TERTIARY);
        button.setPrefixComponent(avatar);

        ViewFooter footer = new ViewFooter(button);

        // Add it all together
        addToDrawer(header, scroller, footer);
    }

    private SideNavItem placeholderItem(String label, Lucide icon) {
        SideNavItem item = new SideNavItem(label);
        item.setPrefixComponent(icon.create());
        item.getElement().addEventListener("click",
                e -> Notification.show(label + " not yet implemented")
        );
        return item;
    }
}
