package com.vaadin.demo.ui;

import com.vaadin.demo.ui.component.ViewFooter;
import com.vaadin.demo.ui.component.ViewHeader;
import com.vaadin.demo.ui.component.ViewHeading;
import com.vaadin.demo.ui.util.Lucide;
import com.vaadin.demo.ui.util.Tailwind.Margin;
import com.vaadin.demo.ui.util.Tailwind.Width;
import com.vaadin.demo.ui.util.Theme;
import com.vaadin.demo.ui.view.*;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.avatar.AvatarVariant;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.ScrollerVariant;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.component.sidenav.SideNavVariant;
import com.vaadin.flow.router.Layout;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import java.util.List;

@Layout
@AnonymousAllowed
public class MainLayout extends AppLayout {

    public MainLayout() {
        setPrimarySection(Section.DRAWER);
        initDrawer();
    }

    private void initDrawer() {
        addToDrawer(createHeader(), createScroller(), createFooter());
    }

    private ViewHeader createHeader() {
        Avatar appLogo = new Avatar();
        appLogo.addThemeNames(Theme.AVATAR_VAADIN, Theme.AVATAR_SQUARE);
        appLogo.addThemeVariants(AvatarVariant.AURA_FILLED, AvatarVariant.XSMALL);
        appLogo.addClassName(Margin.Horizontal.XSMALL);

        ViewHeading appName = new ViewHeading("Vaadin Demo");

        DrawerToggle toggle = new DrawerToggle();
        toggle.addThemeNames(Theme.DRAWER_TOGGLE_PERMANENT);
        toggle.addThemeVariants(ButtonVariant.TERTIARY);

        return new ViewHeader(appLogo, appName, toggle);
    }

    private Scroller createScroller() {
        SideNav nav = new SideNav();
        nav.addThemeVariants(SideNavVariant.AURA_FILLED);
        nav.addItem(new SideNavItem("Dashboard", DashboardView.class, Lucide.HOUSE.create()));
        nav.addItem(new SideNavItem("Components", ComponentsView.class, Lucide.COMPONENT.create()));

        SideNav workspaceNav = new SideNav("Workspace");
        workspaceNav.addThemeVariants(SideNavVariant.AURA_FILLED);
        workspaceNav.addItem(new SideNavItem("Products", ProductsView.class, Lucide.BARCODE.create()));
        workspaceNav.addItem(new SideNavItem("Users", UsersView.class, Lucide.USERS.create()));
        workspaceNav.addItem(placeholderItem("Reports", Lucide.FILE_CHART_COLUMN_INCREASING));
        workspaceNav.setCollapsible(true);

        SideNav growthNav = new SideNav("Growth");
        growthNav.addThemeVariants(SideNavVariant.AURA_FILLED);
        growthNav.addItem(placeholderItem("Analytics", Lucide.CHART_PIE));
        growthNav.addItem(placeholderItem("Revenue", Lucide.PIGGY_BANK));
        growthNav.addItem(placeholderItem("Engagement", Lucide.ACTIVITY));
        growthNav.addItem(placeholderItem("Billing", Lucide.CREDIT_CARD));
        growthNav.setCollapsible(true);

        SideNav adminNav = new SideNav("Admin");
        adminNav.addThemeVariants(SideNavVariant.AURA_FILLED);
        adminNav.addItem(new SideNavItem("Settings", SettingsView.class, Lucide.SETTINGS.create()));
        adminNav.setCollapsible(true);

        Scroller scroller = new Scroller();
        scroller.addThemeVariants(ScrollerVariant.OVERFLOW_INDICATORS);
        scroller.getElement().appendChild(nav.getElement(), new Hr().getElement(), workspaceNav.getElement(), new Hr().getElement(), growthNav.getElement(), new Hr().getElement(), adminNav.getElement());
        return scroller;
    }

    private ViewFooter createFooter() {
        Avatar avatar = new Avatar("John Smith");
        avatar.addThemeVariants(AvatarVariant.AURA_FILLED, AvatarVariant.XSMALL);
        avatar.setAbbreviation("J");

        MenuBar menuBar = new MenuBar();
        menuBar.addThemeVariants(MenuBarVariant.LUMO_TERTIARY);

        MenuItem user = menuBar.addItem(avatar);
        user.add(new Text("John Smith"));
        SubMenu userMenu = user.getSubMenu();

        userMenu.addItem("Profile");
        userMenu.addItem("Preferences");
        addThemeItems(userMenu.addItem("Theme").getSubMenu());
        userMenu.addSeparator();
        userMenu.addItem("Help");
        userMenu.addSeparator();
        userMenu.addItem("Sign out");

        return new ViewFooter(menuBar);
    }

    private void addThemeItems(SubMenu themeMenu) {
        MenuItem system = themeMenu.addItem("System");
        system.setCheckable(true);
        system.setChecked(true);
        MenuItem light = themeMenu.addItem("Light");
        light.setCheckable(true);
        MenuItem dark = themeMenu.addItem("Dark");
        dark.setCheckable(true);

        List.of(system, light, dark).forEach(item ->
                item.addClickListener(e -> List.of(system, light, dark).forEach(t -> t.setChecked(t == item))));

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
