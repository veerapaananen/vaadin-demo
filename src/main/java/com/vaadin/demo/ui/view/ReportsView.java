package com.vaadin.demo.ui.view;

import com.vaadin.demo.service.SourceService;
import com.vaadin.demo.ui.component.SourceViewerDialog;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("reports")
@PageTitle("Reports — Vaadin Demo")
public class ReportsView extends VerticalLayout {

    public ReportsView(SourceService sourceService) {
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

        H2 title = new H2("Reports");
        title.getStyle().set("margin", "0");

        Button viewSource = new Button(VaadinIcon.CODE.create());
        viewSource.addThemeVariants(ButtonVariant.LUMO_TERTIARY, ButtonVariant.LUMO_ICON);
        viewSource.setAriaLabel("View source");
        viewSource.addClickListener(e -> new SourceViewerDialog(ReportsView.class, sourceService).open());

        header.add(title, viewSource);
        add(header);

        // Coming soon message
        Paragraph comingSoon = new Paragraph(
                "This view demonstrates placeholder layout patterns for embedding " +
                        "Vaadin Charts and analytics dashboards.");
        comingSoon.getStyle()
                .set("color", "var(--vaadin-text-color-secondary)")
                .set("margin", "0")
                .set("max-width", "560px");

        add(comingSoon);

        // Chart placeholders
        FlexLayout chartsRow = new FlexLayout();
        chartsRow.setFlexWrap(FlexLayout.FlexWrap.WRAP);
        chartsRow.getStyle().set("gap", "var(--vaadin-gap-m)");
        chartsRow.setWidthFull();

        chartsRow.add(
                chartPlaceholder("Revenue Over Time", "Line chart — monthly revenue for the last 12 months"),
                chartPlaceholder("Orders by Category", "Pie chart — distribution of orders across product categories")
        );

        addAndExpand(chartsRow);
    }

    private Div chartPlaceholder(String title, String description) {
        Div placeholder = new Div();
        placeholder.addClassName("chart-placeholder");
        placeholder.getStyle().set("flex", "1").set("min-width", "280px");

        var icon = VaadinIcon.CHART.create();
        icon.getStyle().set("width", "2.5rem").set("height", "2.5rem").set("color", "var(--vaadin-text-color-disabled)");

        Span titleSpan = new Span(title);
        titleSpan.getStyle()
                .set("font-weight", "600")
                .set("font-size", "var(--aura-font-size-m)");

        Span desc = new Span(description);
        desc.getStyle()
                .set("font-size", "var(--aura-font-size-s)")
                .set("text-align", "center")
                .set("max-width", "240px");

        placeholder.add(icon, titleSpan, desc);
        return placeholder;
    }
}
