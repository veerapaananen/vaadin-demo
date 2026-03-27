package com.vaadin.demo.ui.view;

import com.vaadin.demo.service.SourceService;
import com.vaadin.demo.ui.component.SourceViewerDialog;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Route("components")
@PageTitle("Components — Vaadin Demo")
public class ComponentsView extends VerticalLayout {

    public ComponentsView(SourceService sourceService) {
        setWidthFull();
        setPadding(true);
        setSpacing(false);
        getStyle().set("gap", "var(--vaadin-gap-m)");

        HorizontalLayout header = new HorizontalLayout();
        header.addClassName("view-header");
        header.setWidthFull();
        header.setJustifyContentMode(JustifyContentMode.BETWEEN);
        header.setAlignItems(Alignment.CENTER);

        H2 pageTitle = new H2("Components");
        pageTitle.getStyle().set("margin", "0");

        Button viewSource = new Button(VaadinIcon.CODE.create());
        viewSource.addThemeVariants(ButtonVariant.LUMO_TERTIARY, ButtonVariant.LUMO_ICON);
        viewSource.setAriaLabel("View source");
        viewSource.addClickListener(e -> new SourceViewerDialog(ComponentsView.class, sourceService).open());

        header.add(pageTitle, viewSource);
        add(header);

        // Main component grid — auto-fill columns, min 280px
        Div gallery = new Div();
        gallery.addClassName("component-gallery");

        gallery.add(
            cardButtons(),
            cardColors(),
            cardInputs(),
            cardSelect(),
            cardDateTimePicker(),
            cardCheckboxRadio(),
            cardTypography(),
            cardGrid(),
            cardTabs(),
            cardProgress(),
            cardMenuBar(),
            cardDialog()
        );

        add(gallery);
    }

    // ── Card helpers ──────────────────────────────────────────────────────

    private Div card(String title) {
        Div card = new Div();
        card.getStyle()
                .set("background", "var(--vaadin-background-color)")
                .set("border", "1px solid var(--vaadin-border-color)")
                .set("border-radius", "var(--vaadin-radius-l)")
                .set("padding", "var(--vaadin-gap-m)")
                .set("display", "flex")
                .set("flex-direction", "column")
                .set("gap", "var(--vaadin-gap-s)")
                .set("box-shadow", "var(--aura-shadow-xs)");

        Span cardTitle = new Span(title);
        cardTitle.getStyle()
                .set("font-size", "var(--aura-font-size-xs)")
                .set("font-weight", "600")
                .set("text-transform", "uppercase")
                .set("letter-spacing", "0.06em")
                .set("color", "var(--aura-text-color-tertiary)");
        card.add(cardTitle);
        return card;
    }

    // ── Individual cards ──────────────────────────────────────────────────

    private Div cardButtons() {
        Div c = card("Buttons");

        FlexLayout row1 = new FlexLayout();
        row1.getStyle().set("gap", "var(--vaadin-gap-s)").set("flex-wrap", "wrap");

        Button primary = new Button("Primary");
        primary.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button secondary = new Button("Default");

        Button tertiary = new Button("Tertiary");
        tertiary.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        Button icon = new Button(VaadinIcon.BELL.create());
        icon.addThemeVariants(ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_TERTIARY);
        icon.setAriaLabel("Notifications");

        row1.add(primary, secondary, tertiary, icon);

        FlexLayout row2 = new FlexLayout();
        row2.getStyle().set("gap", "var(--vaadin-gap-s)").set("flex-wrap", "wrap");

        Button success = new Button("Success");
        success.addThemeVariants(ButtonVariant.LUMO_SUCCESS, ButtonVariant.LUMO_PRIMARY);

        Button error = new Button("Error");
        error.addThemeVariants(ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_PRIMARY);

        Button notify = new Button("Show Notification", VaadinIcon.BELL.create());
        notify.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        notify.addClickListener(e -> {
            Notification n = Notification.show("Hello from Vaadin!", 2500, Notification.Position.BOTTOM_END);
            n.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        });

        row2.add(success, error, notify);
        c.add(row1, row2);
        return c;
    }

    private Div cardColors() {
        Div c = card("Color Palette");

        String[][] colors = {
            {"Accent",  "var(--aura-accent-color)"},
            {"Success", "var(--aura-green)"},
            {"Error",   "var(--aura-red)"},
            {"Warning", "var(--aura-orange)"},
            {"Primary text",   "var(--aura-accent-text-color)"},
            {"Body text",      "var(--vaadin-text-color)"},
            {"Secondary text", "var(--vaadin-text-color-secondary)"},
            {"Base",    "var(--vaadin-background-color)"},
            {"Contrast 5%",  "var(--vaadin-background-container)"},
            {"Contrast 10%", "var(--vaadin-border-color)"},
            {"Contrast 20%", "var(--vaadin-border-color-secondary)"},
            {"Contrast",     "var(--vaadin-text-color)"}
        };

        FlexLayout swatches = new FlexLayout();
        swatches.getStyle().set("gap", "var(--vaadin-gap-xs)").set("flex-wrap", "wrap");

        for (String[] color : colors) {
            Div swatch = new Div();
            swatch.getElement().setAttribute("title", color[0]);
            swatch.getStyle()
                    .set("width", "24px").set("height", "24px")
                    .set("border-radius", "50%")
                    .set("background", color[1])
                    .set("border", "1px solid var(--vaadin-border-color-secondary)")
                    .set("cursor", "default");
            swatches.add(swatch);
        }

        // Badges row
        FlexLayout badges = new FlexLayout();
        badges.getStyle().set("gap", "var(--vaadin-gap-xs)").set("flex-wrap", "wrap").set("margin-top", "var(--vaadin-gap-xs)");
        for (String[] t : new String[][]{{"Default",""}, {"Success","success"}, {"Error","error"}, {"Contrast","contrast"}, {"Primary","primary"}}) {
            Span b = new Span(t[0]);
            b.getElement().setAttribute("theme", "badge " + t[1]);
            badges.add(b);
        }

        c.add(swatches, badges);
        return c;
    }

    private Div cardInputs() {
        Div c = card("Text Inputs");

        TextField text = new TextField("Name");
        text.setPlaceholder("Enter name...");
        text.setWidthFull();

        TextField withIcon = new TextField("Search");
        withIcon.setPrefixComponent(VaadinIcon.SEARCH.create());
        withIcon.setClearButtonVisible(true);
        withIcon.setWidthFull();

        TextArea area = new TextArea("Description");
        area.setPlaceholder("Enter description...");
        area.setWidthFull();
        area.setMaxHeight("80px");

        c.add(text, withIcon, area);
        return c;
    }

    private Div cardSelect() {
        Div c = card("Select & ComboBox");

        Select<String> select = new Select<>();
        select.setLabel("Options");
        select.setItems("Option 1", "Option 2", "Option 3");
        select.setValue("Option 1");
        select.setWidthFull();

        ComboBox<String> combo = new ComboBox<>("Country");
        combo.setItems("Andorra", "Australia", "Austria", "Belgium", "Brazil",
                "Canada", "Denmark", "Finland", "France", "Germany",
                "Iceland", "Ireland", "Italy", "Japan", "Netherlands",
                "New Zealand", "Norway", "Portugal", "Spain", "Sweden", "Switzerland");
        combo.setValue("Andorra");
        combo.setWidthFull();

        c.add(select, combo);
        return c;
    }

    private Div cardDateTimePicker() {
        Div c = card("Date & Time Pickers");

        DatePicker datePicker = new DatePicker("Date");
        datePicker.setValue(LocalDate.now());
        datePicker.setWidthFull();

        TimePicker timePicker = new TimePicker("Time");
        timePicker.setValue(LocalTime.of(12, 0));
        timePicker.setWidthFull();

        c.add(datePicker, timePicker);
        return c;
    }

    private Div cardCheckboxRadio() {
        Div c = card("Checkbox & Radio");

        Div checkboxGroup = new Div();
        checkboxGroup.getStyle().set("display", "flex").set("flex-direction", "column").set("gap", "var(--vaadin-gap-xs)");
        Span checkLabel = new Span("Options");
        checkLabel.getStyle().set("font-size", "var(--aura-font-size-s)").set("font-weight", "500");
        checkboxGroup.add(checkLabel);
        for (String opt : new String[]{"Option 1", "Option 2", "Option 3"}) {
            Checkbox cb = new Checkbox(opt);
            cb.setValue(opt.equals("Option 1"));
            checkboxGroup.add(cb);
        }

        RadioButtonGroup<String> radio = new RadioButtonGroup<>();
        radio.setLabel("Sizes");
        radio.setItems("Small", "Medium", "Large");
        radio.setValue("Medium");

        c.add(checkboxGroup, radio);
        return c;
    }

    private Div cardTypography() {
        Div c = card("Typography");

        H1 h1 = new H1("Heading 1");
        h1.getStyle().set("margin", "0").set("font-size", "var(--aura-font-size-xl)");
        H2 h2 = new H2("Heading 2");
        h2.getStyle().set("margin", "0").set("font-size", "var(--aura-font-size-l)");
        H3 h3 = new H3("Heading 3");
        h3.getStyle().set("margin", "0").set("font-size", "var(--aura-font-size-m)");
        H4 h4 = new H4("Heading 4");
        h4.getStyle().set("margin", "0").set("font-size", "var(--aura-font-size-s)");
        H5 h5 = new H5("Heading 5");
        h5.getStyle().set("margin", "0").set("font-size", "var(--aura-font-size-xs)");

        Paragraph body = new Paragraph("Body text — the quick brown fox jumps over the lazy dog.");
        body.getStyle().set("margin", "0").set("font-size", "var(--aura-font-size-s)");

        Span secondary = new Span("Secondary text");
        secondary.getStyle().set("color", "var(--vaadin-text-color-secondary)").set("font-size", "var(--aura-font-size-xs)");

        c.add(h1, h2, h3, h4, h5, body, secondary);
        return c;
    }

    record Row(String name, String email, String status) {}

    private Div cardGrid() {
        Div c = card("Grid");
        c.getStyle().set("grid-column", "span 2");

        Grid<Row> grid = new Grid<>(Row.class, false);
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_ROW_STRIPES);
        grid.addColumn(Row::name).setHeader("Name").setFlexGrow(1);
        grid.addColumn(Row::email).setHeader("Email").setFlexGrow(1);
        grid.addComponentColumn(row -> {
            Span badge = new Span(row.status());
            String theme = switch (row.status()) {
                case "Active" -> "badge success";
                case "Inactive" -> "badge error";
                default -> "badge contrast";
            };
            badge.getElement().setAttribute("theme", theme);
            return badge;
        }).setHeader("Status").setAutoWidth(true).setFlexGrow(0);

        grid.setItems(List.of(
            new Row("Alice Johnson", "alice@example.com", "Active"),
            new Row("Bob Smith", "bob@example.com", "Active"),
            new Row("Carol White", "carol@example.com", "Inactive"),
            new Row("David Brown", "david@example.com", "Active"),
            new Row("Eva Martinez", "eva@example.com", "Pending")
        ));
        grid.setAllRowsVisible(true);
        c.add(grid);
        return c;
    }

    private Div cardTabs() {
        Div c = card("Tabs");

        Tabs tabs = new Tabs();
        tabs.add(new Tab("Overview"), new Tab("Details"), new Tab("Settings"));
        tabs.setWidthFull();

        Div content = new Div();
        content.getStyle()
                .set("padding", "var(--vaadin-gap-s)")
                .set("background", "var(--vaadin-background-container)")
                .set("border-radius", "var(--vaadin-radius-m)")
                .set("font-size", "var(--aura-font-size-s)")
                .set("color", "var(--vaadin-text-color-secondary)");
        content.setText("Tab content area");

        c.add(tabs, content);
        return c;
    }

    private Div cardProgress() {
        Div c = card("Progress");

        ProgressBar pb1 = new ProgressBar(0, 100, 65);
        pb1.setWidthFull();

        Span label1 = new Span("65% complete");
        label1.getStyle().set("font-size", "var(--aura-font-size-xs)").set("color", "var(--vaadin-text-color-secondary)");

        ProgressBar pb2 = new ProgressBar(0, 100, 30);
        pb2.setWidthFull();
        pb2.getElement().setAttribute("theme", "error");

        Span label2 = new Span("30% — low");
        label2.getStyle().set("font-size", "var(--aura-font-size-xs)").set("color", "var(--aura-red-text)");

        ProgressBar pb3 = new ProgressBar();
        pb3.setIndeterminate(true);
        pb3.setWidthFull();

        Span label3 = new Span("Indeterminate");
        label3.getStyle().set("font-size", "var(--aura-font-size-xs)").set("color", "var(--vaadin-text-color-secondary)");

        c.add(pb1, label1, pb2, label2, pb3, label3);
        return c;
    }

    private Div cardMenuBar() {
        Div c = card("Menu Bar");

        MenuBar menuBar = new MenuBar();
        menuBar.setWidthFull();
        var actions = menuBar.addItem("Actions");
        actions.getSubMenu().addItem("Edit");
        actions.getSubMenu().addItem("Duplicate");
        actions.getSubMenu().addItem("Archive");
        actions.getSubMenu().addItem("Delete");

        var view = menuBar.addItem("View");
        view.getSubMenu().addItem("Compact");
        view.getSubMenu().addItem("Comfortable");

        var moreItem = menuBar.addItem(VaadinIcon.ELLIPSIS_DOTS_V.create());
        moreItem.setAriaLabel("More actions");

        HorizontalLayout row = new HorizontalLayout();
        row.getStyle().set("gap", "var(--vaadin-gap-s)").set("flex-wrap", "wrap");

        for (String[] b : new String[][]{{"Filled",""}, {"Outlined","tertiary"}, {"Small","small"}}) {
            Span badge = new Span(b[0]);
            badge.getElement().setAttribute("theme", "badge" + (b[1].isEmpty() ? "" : " " + b[1]));
            row.add(badge);
        }

        c.add(menuBar, row);
        return c;
    }

    private Div cardDialog() {
        Div c = card("Dialog & Notification");

        Button openDialog = new Button("Open Dialog", VaadinIcon.EXTERNAL_LINK.create());
        openDialog.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        openDialog.addClickListener(e -> {
            Dialog d = new Dialog();
            d.setHeaderTitle("Example Dialog");
            VerticalLayout content = new VerticalLayout();
            content.setPadding(false);
            content.add(new Paragraph("This is a Vaadin Dialog component."));
            TextField field = new TextField("Your input");
            field.setWidthFull();
            content.add(field);
            d.add(content);
            Button confirm = new Button("Confirm", ev -> {
                d.close();
                Notification.show("Confirmed: " + field.getValue(), 2000, Notification.Position.BOTTOM_END);
            });
            confirm.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            d.getFooter().add(new Button("Cancel", ev -> d.close()), confirm);
            d.open();
        });

        Button toastInfo = new Button("Info Toast");
        toastInfo.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        toastInfo.addClickListener(e -> Notification.show("Info notification", 2000, Notification.Position.BOTTOM_END));

        Button toastSuccess = new Button("Success Toast");
        toastSuccess.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        toastSuccess.addClickListener(e -> {
            Notification n = Notification.show("Operation successful!", 2000, Notification.Position.BOTTOM_END);
            n.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        });

        Button toastError = new Button("Error Toast");
        toastError.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        toastError.addClickListener(e -> {
            Notification n = Notification.show("Something went wrong", 2000, Notification.Position.BOTTOM_END);
            n.addThemeVariants(NotificationVariant.LUMO_ERROR);
        });

        FlexLayout btns = new FlexLayout(toastInfo, toastSuccess, toastError);
        btns.getStyle().set("gap", "var(--vaadin-gap-s)").set("flex-wrap", "wrap");

        c.add(openDialog, btns);
        return c;
    }
}
