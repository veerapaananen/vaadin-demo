package com.vaadin.demo.ui.view;

import com.vaadin.demo.data.SampleData;
import com.vaadin.demo.service.SourceService;
import com.vaadin.demo.ui.component.SourceViewerDialog;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.card.Card;
import com.vaadin.flow.component.card.CardVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import java.time.format.DateTimeFormatter;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

@Route("")
@RouteAlias("dashboard")
@PageTitle("Dashboard — Vaadin Demo")
public class DashboardView extends VerticalLayout {

    public DashboardView(SourceService sourceService) {
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

        H2 title = new H2("Dashboard");
        title.getStyle().set("margin", "0");

        Button viewSource = new Button(VaadinIcon.CODE.create());
        viewSource.addThemeVariants(ButtonVariant.LUMO_TERTIARY, ButtonVariant.LUMO_ICON);
        viewSource.setAriaLabel("View source");
        viewSource.addClickListener(e -> new SourceViewerDialog(DashboardView.class, sourceService).open());

        header.add(title, viewSource);
        add(header);

        // Stat cards
        FlexLayout statsRow = new FlexLayout();
        statsRow.setFlexWrap(FlexLayout.FlexWrap.WRAP);
        statsRow.getStyle().set("gap", "var(--vaadin-gap-m)");

        statsRow.add(
            statCard("Total Revenue", "$128,430", "+12.5%", true),
            statCard("Active Users", "3,842", "+4.2%", true),
            statCard("New Orders", "284", "+8.1%", true),
            statCard("Churn Rate", "2.4%", "-0.3%", false)
        );
        add(statsRow);

        // Actions toolbar
        MenuBar menuBar = new MenuBar();
        var exportItem = menuBar.addItem("Export");
        exportItem.addClickListener(e -> notify("Export started — your file will be ready shortly", NotificationVariant.LUMO_PRIMARY));
        var refreshItem = menuBar.addItem("Refresh");
        refreshItem.addClickListener(e -> notify("Data refreshed", NotificationVariant.LUMO_SUCCESS));

        Span sectionTitle = new Span("Recent Orders");
        sectionTitle.getStyle().set("font-weight", "600").set("font-size", "var(--aura-font-size-m)");

        HorizontalLayout toolbar = new HorizontalLayout(sectionTitle, menuBar);
        toolbar.setWidthFull();
        toolbar.setJustifyContentMode(JustifyContentMode.BETWEEN);
        toolbar.setAlignItems(Alignment.CENTER);
        add(toolbar);

        // Orders grid
        DateTimeFormatter dateFmt = DateTimeFormatter.ofPattern("MMM d, yyyy");
        Grid<SampleData.Order> grid = new Grid<>(SampleData.Order.class, false);
        grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES, GridVariant.LUMO_NO_BORDER);
        grid.addColumn(SampleData.Order::id).setHeader("Order ID").setAutoWidth(true).setFlexGrow(0).setSortable(true);
        grid.addColumn(SampleData.Order::customer).setHeader("Customer").setFlexGrow(1).setSortable(true);
        grid.addColumn(SampleData.Order::amount).setHeader("Amount").setAutoWidth(true).setFlexGrow(0);
        grid.addComponentColumn(o -> statusBadge(o.status())).setHeader("Status").setAutoWidth(true).setFlexGrow(0);
        grid.addColumn(o -> o.date().format(dateFmt)).setHeader("Date").setAutoWidth(true).setFlexGrow(0).setSortable(true);
        grid.setItems(SampleData.orders());
        grid.setWidthFull();
        addAndExpand(grid);
    }

    private Card statCard(String label, String value, String trend, boolean favorable) {
        Card card = new Card();
        card.addThemeVariants(CardVariant.OUTLINED);
        card.addClassName("stat-card");
        card.setTitle(label);

        Span valueSpan = new Span(value);
        valueSpan.addClassName("stat-value");

        boolean increasing = trend.startsWith("+");
        String arrow = increasing ? "\u25B2 " : "\u25BC ";
        Span trendSpan = new Span(arrow + trend);
        trendSpan.addClassName("stat-trend");
        trendSpan.addClassName(favorable ? "up" : "down");

        Div trendRow = new Div(trendSpan);
        trendRow.addClassName("stat-trend-row");
        card.add(valueSpan, trendRow);
        return card;
    }

    private Span statusBadge(String status) {
        Span badge = new Span(status);
        String theme = switch (status) {
            case "Completed"  -> "badge success";
            case "Processing" -> "badge primary";
            case "Failed"     -> "badge error";
            case "Pending"    -> "badge contrast";
            default           -> "badge";
        };
        badge.getElement().setAttribute("theme", theme);
        return badge;
    }

    private void notify(String message, NotificationVariant variant) {
        Notification n = Notification.show(message, 3000, Notification.Position.BOTTOM_END);
        n.addThemeVariants(variant);
    }
}
