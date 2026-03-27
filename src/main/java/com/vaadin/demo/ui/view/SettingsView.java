package com.vaadin.demo.ui.view;

import com.vaadin.demo.data.SampleData;
import com.vaadin.demo.service.SourceService;
import com.vaadin.demo.ui.component.SourceViewerDialog;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("settings")
@PageTitle("Settings — Vaadin Demo")
public class SettingsView extends VerticalLayout {

    public SettingsView(SourceService sourceService) {
        setWidthFull();
        setPadding(true);
        setSpacing(false);
        getStyle().set("gap", "var(--vaadin-gap-m)");

        // Header
        HorizontalLayout header = new HorizontalLayout();
        header.addClassName("view-header");
        header.setWidthFull();
        header.setJustifyContentMode(JustifyContentMode.BETWEEN);
        header.setAlignItems(Alignment.CENTER);

        H2 title = new H2("Settings");
        title.getStyle().set("margin", "0");

        Button viewSource = new Button(VaadinIcon.CODE.create());
        viewSource.addThemeVariants(ButtonVariant.LUMO_TERTIARY, ButtonVariant.LUMO_ICON);
        viewSource.setAriaLabel("View source");
        viewSource.addClickListener(e -> new SourceViewerDialog(SettingsView.class, sourceService).open());

        header.add(title, viewSource);
        add(header);

        TabSheet tabSheet = new TabSheet();
        tabSheet.setWidthFull();

        tabSheet.add("General", buildGeneralTab());
        tabSheet.add("Notifications", buildNotificationsTab());
        tabSheet.add("Security", buildSecurityTab());
        tabSheet.add("Integrations", buildIntegrationsTab());

        addAndExpand(tabSheet);
    }

    private VerticalLayout buildGeneralTab() {
        FormLayout form = new FormLayout();

        TextField orgName = new TextField("Organization Name");
        orgName.setValue("Acme Corporation");

        TextField orgUrl = new TextField("Website URL");
        orgUrl.setValue("https://acme.example.com");

        EmailField supportEmail = new EmailField("Support Email");
        supportEmail.setValue("support@acme.example.com");

        Select<String> timezone = new Select<>();
        timezone.setLabel("Timezone");
        timezone.setItems("UTC", "US/Eastern", "US/Pacific", "Europe/London", "Europe/Berlin", "Asia/Tokyo");
        timezone.setValue("UTC");

        Select<String> language = new Select<>();
        language.setLabel("Language");
        language.setItems("English", "Finnish", "German", "French", "Spanish");
        language.setValue("English");

        form.add(orgName, orgUrl, supportEmail, timezone, language);

        Button save = new Button("Save Changes");
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        save.addClickListener(e -> {
            Notification n = Notification.show("General settings saved", 3000, Notification.Position.BOTTOM_END);
            n.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        });

        VerticalLayout layout = new VerticalLayout(form, save);
        layout.setPadding(false);
        return layout;
    }

    private VerticalLayout buildNotificationsTab() {
        FormLayout form = new FormLayout();
        form.add(
            new Checkbox("Email me on new orders", true),
            new Checkbox("Email me on failed payments", true),
            new Checkbox("Weekly usage digest", true),
            new Checkbox("Security alerts", true),
            new Checkbox("Product updates and announcements", false),
            new Checkbox("Marketing emails", false)
        );

        Button save = new Button("Save Preferences");
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        save.addClickListener(e -> {
            Notification n = Notification.show("Notification preferences saved", 3000, Notification.Position.BOTTOM_END);
            n.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        });

        VerticalLayout layout = new VerticalLayout(form, save);
        layout.setPadding(false);
        return layout;
    }

    private VerticalLayout buildSecurityTab() {
        FormLayout form = new FormLayout();

        PasswordField currentPwd = new PasswordField("Current Password");
        PasswordField newPwd = new PasswordField("New Password");
        PasswordField confirmPwd = new PasswordField("Confirm New Password");

        form.add(currentPwd, newPwd, confirmPwd);

        Button changePassword = new Button("Change Password");
        changePassword.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        changePassword.addClickListener(e -> {
            Notification n = Notification.show("Password change is not implemented in this demo", 3000, Notification.Position.BOTTOM_END);
            n.addThemeVariants(NotificationVariant.LUMO_PRIMARY);
        });

        Hr separator = new Hr();

        H4 twoFactor = new H4("Two-Factor Authentication");
        Paragraph twoFactorDesc = new Paragraph("Add an extra layer of security to your account.");
        Button enableTwoFactor = new Button("Enable 2FA");
        enableTwoFactor.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        enableTwoFactor.addClickListener(e -> {
            Notification n = Notification.show("2FA setup is not implemented in this demo", 3000, Notification.Position.BOTTOM_END);
            n.addThemeVariants(NotificationVariant.LUMO_PRIMARY);
        });

        VerticalLayout layout = new VerticalLayout(form, changePassword, separator, twoFactor, twoFactorDesc, enableTwoFactor);
        layout.setPadding(false);
        return layout;
    }

    private VerticalLayout buildIntegrationsTab() {
        VerticalLayout layout = new VerticalLayout();
        layout.setPadding(false);

        for (SampleData.Integration integration : SampleData.integrations()) {
            Div card = new Div();
            card.addClassName("integration-card");

            Div info = new Div();
            info.addClassName("integration-info");
            Span name = new Span(integration.name());
            name.getStyle().set("font-weight", "600").set("display", "block");
            Span desc = new Span(integration.description());
            desc.getStyle().set("font-size", "var(--aura-font-size-s)").set("color", "var(--vaadin-text-color-secondary)");
            info.add(name, desc);

            HorizontalLayout actions = new HorizontalLayout();
            actions.addClassName("integration-actions");
            actions.setAlignItems(Alignment.CENTER);
            actions.setSpacing(false);
            actions.getStyle().set("gap", "var(--vaadin-gap-s)");

            Checkbox enabled = new Checkbox();
            enabled.setAriaLabel("Enable " + integration.name());
            enabled.setValue(integration.enabled());
            enabled.addValueChangeListener(ev -> {
                String msg = ev.getValue()
                        ? integration.name() + " enabled"
                        : integration.name() + " disabled";
                Notification n = Notification.show(msg, 2000, Notification.Position.BOTTOM_END);
                n.addThemeVariants(NotificationVariant.LUMO_PRIMARY);
            });

            Button connect = new Button("Configure");
            connect.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);
            connect.addClickListener(e -> {
                Notification n = Notification.show("Integration configuration is not yet implemented", 3000, Notification.Position.BOTTOM_END);
                n.addThemeVariants(NotificationVariant.LUMO_PRIMARY);
            });

            actions.add(enabled, connect);
            card.add(info, actions);
            layout.add(card);
        }

        return layout;
    }
}
