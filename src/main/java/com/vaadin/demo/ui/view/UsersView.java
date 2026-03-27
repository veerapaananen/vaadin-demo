package com.vaadin.demo.ui.view;

import com.vaadin.demo.data.SampleData;
import com.vaadin.demo.service.SourceService;
import com.vaadin.demo.ui.component.SourceViewerDialog;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("users")
@PageTitle("Users — Vaadin Demo")
public class UsersView extends VerticalLayout {

    public UsersView(SourceService sourceService) {
        setSizeFull();
        setPadding(true);
        setSpacing(false);
        getStyle().set("gap", "var(--vaadin-gap-m)");

        // Header
        HorizontalLayout header = new HorizontalLayout();
        header.addClassName("view-header");
        header.setWidthFull();
        header.setJustifyContentMode(JustifyContentMode.BETWEEN);
        header.setAlignItems(Alignment.CENTER);

        H2 title = new H2("Users");
        title.getStyle().set("margin", "0");

        HorizontalLayout headerActions = new HorizontalLayout();
        headerActions.setSpacing(false);
        headerActions.getStyle().set("gap", "var(--vaadin-gap-s)");

        Button inviteUser = new Button("Invite User", VaadinIcon.ENVELOPE.create());
        inviteUser.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        inviteUser.addClickListener(e -> {
            Notification n = Notification.show("Invitation sent — feature not yet implemented in this demo", 3000, Notification.Position.BOTTOM_END);
            n.addThemeVariants(NotificationVariant.LUMO_PRIMARY);
        });

        Button viewSource = new Button(VaadinIcon.CODE.create());
        viewSource.addThemeVariants(ButtonVariant.LUMO_TERTIARY, ButtonVariant.LUMO_ICON);
        viewSource.setAriaLabel("View source");
        viewSource.addClickListener(e -> new SourceViewerDialog(UsersView.class, sourceService).open());

        headerActions.add(inviteUser, viewSource);
        header.add(title, headerActions);
        add(header);

        // Grid
        Grid<SampleData.User> grid = new Grid<>(SampleData.User.class, false);
        grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES, GridVariant.LUMO_NO_BORDER);

        var nameCol = grid.addComponentColumn(user -> {
            Avatar avatar = new Avatar(user.name());
            avatar.setAbbreviation(user.initials());
            avatar.getStyle().set("--vaadin-avatar-size", "2rem");
            Span name = new Span(user.name());
            name.getStyle().set("font-weight", "500");
            HorizontalLayout cell = new HorizontalLayout(avatar, name);
            cell.setAlignItems(Alignment.CENTER);
            cell.setSpacing(false);
            cell.getStyle().set("gap", "var(--vaadin-gap-s)");
            return cell;
        }).setHeader("Name").setWidth("200px").setFlexGrow(1).setResizable(true)
          .setComparator(u -> u.name());

        var emailCol = grid.addColumn(SampleData.User::email)
                .setHeader("Email").setWidth("220px").setFlexGrow(1).setResizable(true).setSortable(true);

        var roleCol = grid.addComponentColumn(user -> {
            Span badge = new Span(user.role());
            badge.getElement().setAttribute("theme", "badge " + roleBadgeTheme(user.role()));
            return badge;
        }).setHeader("Role").setAutoWidth(true).setFlexGrow(0)
          .setComparator(u -> u.role());

        var lastLoginCol = grid.addColumn(SampleData.User::lastLogin)
                .setHeader("Last Login").setAutoWidth(true).setFlexGrow(0).setSortable(true);

        var actionsCol = grid.addComponentColumn(user -> {
            Button edit = new Button(VaadinIcon.EDIT.create());
            edit.addThemeVariants(ButtonVariant.LUMO_TERTIARY, ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_SMALL);
            edit.setAriaLabel("Edit user");
            edit.addClickListener(e -> openUserDialog(user));
            return edit;
        }).setWidth("72px").setFlexGrow(0);

        var dataView = grid.setItems(SampleData.users());
        grid.setWidthFull();

        // Header row filters
        HeaderRow filterRow = grid.appendHeaderRow();

        TextField nameFilter = new TextField();
        nameFilter.setPlaceholder("Filter name...");
        nameFilter.setWidthFull();
        nameFilter.setClearButtonVisible(true);
        nameFilter.getStyle().set("--vaadin-text-field-default-width", "auto");
        filterRow.getCell(nameCol).setComponent(nameFilter);

        TextField emailFilter = new TextField();
        emailFilter.setPlaceholder("Filter email...");
        emailFilter.setWidthFull();
        emailFilter.setClearButtonVisible(true);
        emailFilter.getStyle().set("--vaadin-text-field-default-width", "auto");
        filterRow.getCell(emailCol).setComponent(emailFilter);

        Select<String> roleFilter = new Select<>();
        roleFilter.setItems("", "Admin", "Developer", "Viewer", "Billing");
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

        addAndExpand(grid);
    }

    private void openUserDialog(SampleData.User user) {
        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("Edit User");
        dialog.setWidth("400px");

        Avatar avatar = new Avatar(user.name());
        avatar.setAbbreviation(user.initials());
        avatar.getStyle().set("--vaadin-avatar-size", "3.5rem");

        H3 nameSpan = new H3(user.name());
        nameSpan.getStyle().set("margin", "0");

        Span emailSpan = new Span(user.email());
        emailSpan.getStyle()
                .set("color", "var(--vaadin-text-color-secondary)")
                .set("font-size", "var(--aura-font-size-s)");

        VerticalLayout userInfo = new VerticalLayout(nameSpan, emailSpan);
        userInfo.setPadding(false);
        userInfo.setSpacing(false);
        userInfo.getStyle().set("gap", "2px");

        HorizontalLayout avatarRow = new HorizontalLayout(avatar, userInfo);
        avatarRow.setAlignItems(Alignment.CENTER);
        avatarRow.setWidthFull();
        avatarRow.getStyle()
                .set("padding-bottom", "var(--vaadin-gap-m)")
                .set("border-bottom", "1px solid var(--vaadin-border-color)");

        Select<String> role = new Select<>();
        role.setLabel("Role");
        role.setItems("Admin", "Developer", "Viewer", "Billing");
        role.setValue(user.role());
        role.setWidthFull();

        Span lastLogin = new Span("Last login: " + user.lastLogin());
        lastLogin.getStyle()
                .set("font-size", "var(--aura-font-size-xs)")
                .set("color", "var(--aura-text-color-tertiary)");

        VerticalLayout content = new VerticalLayout(avatarRow, role, lastLogin);
        content.setPadding(false);
        content.setSpacing(false);
        content.getStyle().set("gap", "var(--vaadin-gap-m)");
        dialog.add(content);

        Button save = new Button("Save Changes", e -> {
            dialog.close();
            Notification n = Notification.show(
                    user.name() + "'s role updated to " + role.getValue(),
                    3000, Notification.Position.BOTTOM_END);
            n.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        });
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button cancel = new Button("Cancel", e -> dialog.close());
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        dialog.getFooter().add(cancel, save);
        dialog.open();
    }

    private String roleBadgeTheme(String role) {
        return switch (role) {
            case "Admin"     -> "error";
            case "Developer" -> "success";
            case "Billing"   -> "contrast";
            default          -> "";
        };
    }
}
