package com.vaadin.demo.ui.view;

import com.vaadin.demo.data.SampleData;
import com.vaadin.demo.service.SourceService;
import com.vaadin.demo.ui.component.SourceViewerDialog;
import com.vaadin.demo.ui.component.View;
import com.vaadin.demo.ui.component.ViewHeader;
import com.vaadin.demo.ui.util.Lucide;
import com.vaadin.demo.ui.util.Tailwind.AlignItems;
import com.vaadin.demo.ui.util.Tailwind.Border;
import com.vaadin.demo.ui.util.Tailwind.Color;
import com.vaadin.demo.ui.util.Tailwind.Display;
import com.vaadin.demo.ui.util.Tailwind.FlexDirection;
import com.vaadin.demo.ui.util.Tailwind.FontSize;
import com.vaadin.demo.ui.util.Tailwind.FontWeight;
import com.vaadin.demo.ui.util.Tailwind.Gap;
import com.vaadin.demo.ui.util.Tailwind.Overflow;
import com.vaadin.demo.ui.util.Tailwind.Padding;
import com.vaadin.demo.ui.util.Tailwind.Width;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.badge.Badge;
import com.vaadin.flow.component.badge.BadgeVariant;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.card.Card;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import static com.vaadin.demo.ui.util.Tailwind.*;

@Route("users")
@PageTitle("Users — Vaadin Demo")
public class UsersView extends View {

    public UsersView(SourceService sourceService) {
        add(createHeader(sourceService));

        Div content = new Div(createUsersCard());
        content.addClassNames(Display.FLEX, Overflow.HIDDEN, Padding.Bottom.LARGE, Padding.Horizontal.LARGE);
        add(content);
    }

    /**
     * Page header with drawer toggle, title, invite user button, and source viewer button.
     */
    private ViewHeader createHeader(SourceService sourceService) {
        DrawerToggle toggle = new DrawerToggle();
        toggle.addThemeVariants(ButtonVariant.TERTIARY);

        H1 title = new H1("Users");

        Button inviteUser = new Button("Invite User", Lucide.MAIL_PLUS.create());
        inviteUser.addThemeVariants(ButtonVariant.PRIMARY);
        inviteUser.addClickListener(e -> notify("Invitation sent — feature not yet implemented in this demo", NotificationVariant.LUMO_PRIMARY));

        Button viewSource = new Button(Lucide.CODE.create(), e -> new SourceViewerDialog(UsersView.class, sourceService).open());
        viewSource.addThemeVariants(ButtonVariant.TERTIARY);
        viewSource.setAriaLabel("View source");
        viewSource.setTooltipText("View source");

        return new ViewHeader(toggle, title, inviteUser, viewSource);
    }

    /**
     * Card containing the users grid.
     */
    private Card createUsersCard() {
        Card card = new Card();
        card.add(createUsersGrid());
        card.addClassNames(Width.FULL);
        return card;
    }

    /**
     * Grid listing users with name, email, role, last login, and actions columns.
     */
    private Grid<SampleData.User> createUsersGrid() {
        Grid<SampleData.User> grid = new Grid<>(SampleData.User.class, false);
        grid.addThemeVariants(GridVariant.NO_BORDER);
        grid.setHeightFull();

        var nameCol = grid.addComponentColumn(user -> {
            Avatar avatar = new Avatar(user.name());
            avatar.setAbbreviation(user.initials());
            avatar.getStyle().set("--vaadin-avatar-size", "2rem");

            Div div = new Div(avatar, new Text(user.name()));
            div.addClassNames(AlignItems.CENTER, Display.FLEX, FontWeight.MEDIUM, Gap.SMALL);
            return div;
        }).setComparator(u -> u.name())
                .setHeader("Name")
                .setResizable(true);

        var emailCol = grid.addColumn(SampleData.User::email)
                .setHeader("Email")
                .setResizable(true)
                .setSortable(true);

        var roleCol = grid.addComponentColumn(user -> createRoleBadge(user.role()))
                .setAutoWidth(true)
                .setComparator(u -> u.role())
                .setFlexGrow(0)
                .setHeader("Role");

        var lastLoginCol = grid.addColumn(SampleData.User::lastLogin)
                .setAutoWidth(true)
                .setFlexGrow(0)
                .setHeader("Last Login")
                .setSortable(true);

        var actionsCol = grid.addComponentColumn(user -> {
            Button edit = new Button(Lucide.SQUARE_PEN.create(), e -> openUserDialog(user));
            edit.addThemeVariants(ButtonVariant.TERTIARY);
            edit.setAriaLabel("Edit");
            edit.setTooltipText("Edit");
            return edit;
        })
                .setAutoWidth(true)
                .setFlexGrow(0);

        var dataView = grid.setItems(SampleData.users());

        // Header row filters
        HeaderRow filterRow = grid.appendHeaderRow();

        TextField nameFilter = new TextField();
        nameFilter.setClearButtonVisible(true);
        nameFilter.setPlaceholder("Filter name...");
        nameFilter.setWidthFull();
        filterRow.getCell(nameCol).setComponent(nameFilter);

        TextField emailFilter = new TextField();
        emailFilter.setClearButtonVisible(true);
        emailFilter.setPlaceholder("Filter email...");
        emailFilter.setWidthFull();
        filterRow.getCell(emailCol).setComponent(emailFilter);

        Select<String> roleFilter = new Select<>();
        roleFilter.setItems("", "Admin", "Billing", "Developer", "Viewer");
        roleFilter.setPlaceholder("All roles");
        roleFilter.setWidthFull();
        filterRow.getCell(roleCol).setComponent(roleFilter);

        filterRow.getCell(lastLoginCol).setText("");
        filterRow.getCell(actionsCol).setText("");

        Runnable applyFilter = () -> dataView.setFilter(u ->
                (nameFilter.getValue().isBlank() || u.name().toLowerCase().contains(nameFilter.getValue().toLowerCase())) &&
                (emailFilter.getValue().isBlank() || u.email().toLowerCase().contains(emailFilter.getValue().toLowerCase())) &&
                (roleFilter.getValue() == null || roleFilter.getValue().isBlank() || roleFilter.getValue().equals(u.role())));

        nameFilter.addValueChangeListener(e -> applyFilter.run());
        emailFilter.addValueChangeListener(e -> applyFilter.run());
        roleFilter.addValueChangeListener(e -> applyFilter.run());

        return grid;
    }

    /**
     * Badge indicating the role of a user.
     */
    private Badge createRoleBadge(String role) {
        Badge badge = new Badge(role);
        switch (role) {
            case "Admin"     -> badge.addThemeVariants(BadgeVariant.ERROR);
            case "Billing"   -> badge.addThemeVariants(BadgeVariant.CONTRAST);
            case "Developer" -> badge.addThemeVariants(BadgeVariant.SUCCESS);
        }
        return badge;
    }

    /**
     * Opens a dialog to edit an existing user's role.
     */
    private void openUserDialog(SampleData.User user) {
        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("Edit User");
        dialog.setWidth("400px");

        Avatar avatar = new Avatar(user.name());
        avatar.setAbbreviation(user.initials());
        avatar.getStyle().set("--vaadin-avatar-size", "3.5rem");

        H3 nameSpan = new H3(user.name());

        Span emailSpan = new Span(user.email());
        emailSpan.addClassNames(Color.SECONDARY, FontSize.SMALL);

        Div userInfo = new Div(nameSpan, emailSpan);
        userInfo.addClassNames(Display.FLEX, FlexDirection.COLUMN);

        Div avatarRow = new Div(avatar, userInfo);
        avatarRow.addClassNames(AlignItems.CENTER, Border.BOTTOM, Display.FLEX, Gap.MEDIUM, Padding.Bottom.LARGE, Width.FULL);

        Select<String> role = new Select<>();
        role.setLabel("Role");
        role.setItems("Admin", "Billing", "Developer", "Viewer");
        role.setValue(user.role());

        Span lastLogin = new Span("Last login: " + user.lastLogin());
        lastLogin.addClassNames(Color.SECONDARY, FontSize.XSMALL);

        Div content = new Div(avatarRow, role, lastLogin);
        content.addClassNames(Display.FLEX, FlexDirection.COLUMN, Gap.LARGE);
        dialog.add(content);

        Button save = new Button("Save Changes", e -> {
            dialog.close();
            notify(user.name() + "'s role updated to " + role.getValue(), NotificationVariant.SUCCESS);
        });
        save.addThemeVariants(ButtonVariant.PRIMARY);

        Button cancel = new Button("Cancel", e -> dialog.close());
        cancel.addThemeVariants(ButtonVariant.TERTIARY);

        dialog.getFooter().add(cancel, save);
        dialog.open();
    }

    /**
     * Shows a toast notification at the bottom-end of the screen.
     */
    private void notify(String message, NotificationVariant... variants) {
        Notification notification = Notification.show(message);
        notification.addThemeVariants(variants);
    }
}
