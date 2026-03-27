package com.vaadin.demo.ui.view;

import com.vaadin.demo.data.SampleData;
import com.vaadin.demo.service.SourceService;
import com.vaadin.demo.ui.component.SourceViewerDialog;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("products")
@PageTitle("Products — Vaadin Demo")
public class ProductsView extends VerticalLayout {

    public ProductsView(SourceService sourceService) {
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

        H2 title = new H2("Products");
        title.getStyle().set("margin", "0");

        HorizontalLayout headerActions = new HorizontalLayout();
        headerActions.setSpacing(false);
        headerActions.getStyle().set("gap", "var(--vaadin-gap-s)");

        Button addProduct = new Button("Add Product", VaadinIcon.PLUS.create());
        addProduct.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        addProduct.addClickListener(e -> openProductDialog(null));

        Button viewSource = new Button(VaadinIcon.CODE.create());
        viewSource.addThemeVariants(ButtonVariant.LUMO_TERTIARY, ButtonVariant.LUMO_ICON);
        viewSource.setAriaLabel("View source");
        viewSource.addClickListener(e -> new SourceViewerDialog(ProductsView.class, sourceService).open());

        headerActions.add(addProduct, viewSource);
        header.add(title, headerActions);
        add(header);

        // Grid
        Grid<SampleData.Product> grid = new Grid<>(SampleData.Product.class, false);
        grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES, GridVariant.LUMO_NO_BORDER);

        var nameCol = grid.addColumn(SampleData.Product::name)
                .setHeader("Name").setWidth("200px").setFlexGrow(2).setResizable(true).setSortable(true);
        var categoryCol = grid.addColumn(SampleData.Product::category)
                .setHeader("Category").setWidth("140px").setFlexGrow(1).setSortable(true);
        var priceCol = grid.addColumn(SampleData.Product::price)
                .setHeader("Price").setAutoWidth(true).setFlexGrow(0).setSortable(true);
        var stockCol = grid.addColumn(SampleData.Product::stock)
                .setHeader("Stock").setAutoWidth(true).setFlexGrow(0).setSortable(true);
        var statusCol = grid.addComponentColumn(p -> {
            Span badge = new Span(p.status());
            badge.getElement().setAttribute("theme", "badge " + statusBadgeTheme(p.status()));
            return badge;
        }).setHeader("Status").setAutoWidth(true).setFlexGrow(0);
        var actionsCol = grid.addComponentColumn(product -> {
            Button edit = new Button(VaadinIcon.EDIT.create());
            edit.addThemeVariants(ButtonVariant.LUMO_TERTIARY, ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_SMALL);
            edit.setAriaLabel("Edit product");
            edit.addClickListener(e -> openProductDialog(product));
            return edit;
        }).setWidth("72px").setFlexGrow(0);

        var dataView = grid.setItems(SampleData.products());
        grid.setWidthFull();

        // Header row filters
        HeaderRow filterRow = grid.appendHeaderRow();

        TextField nameFilter = new TextField();
        nameFilter.setPlaceholder("Filter name...");
        nameFilter.setWidthFull();
        nameFilter.setClearButtonVisible(true);
        nameFilter.getStyle().set("--vaadin-text-field-default-width", "auto");
        filterRow.getCell(nameCol).setComponent(nameFilter);

        Select<String> categoryFilter = new Select<>();
        categoryFilter.setItems("", "Software", "Infrastructure", "Marketing", "Sales", "Support",
                "Developer", "Security", "Data", "Compliance", "Enterprise", "Tools");
        categoryFilter.setPlaceholder("All");
        categoryFilter.setWidthFull();
        filterRow.getCell(categoryCol).setComponent(categoryFilter);

        Select<String> statusFilter = new Select<>();
        statusFilter.setItems("", "Active", "Beta", "Deprecated", "Inactive");
        statusFilter.setPlaceholder("All");
        statusFilter.setWidthFull();
        filterRow.getCell(statusCol).setComponent(statusFilter);
        filterRow.getCell(priceCol).setText("");
        filterRow.getCell(stockCol).setText("");
        filterRow.getCell(actionsCol).setText("");

        Runnable applyFilter = () -> dataView.setFilter(p ->
            (nameFilter.getValue().isBlank() || p.name().toLowerCase().contains(nameFilter.getValue().toLowerCase())) &&
            (categoryFilter.getValue() == null || categoryFilter.getValue().isBlank() || categoryFilter.getValue().equals(p.category())) &&
            (statusFilter.getValue() == null || statusFilter.getValue().isBlank() || statusFilter.getValue().equals(p.status())));

        nameFilter.addValueChangeListener(e -> applyFilter.run());
        categoryFilter.addValueChangeListener(e -> applyFilter.run());
        statusFilter.addValueChangeListener(e -> applyFilter.run());

        addAndExpand(grid);
    }

    private String statusBadgeTheme(String status) {
        return switch (status) {
            case "Active" -> "success";
            case "Beta" -> "contrast";
            case "Deprecated" -> "error";
            default -> "";
        };
    }

    private void openProductDialog(SampleData.Product product) {
        Dialog dialog = new Dialog();
        dialog.setHeaderTitle(product == null ? "Add Product" : "Edit Product");
        dialog.setWidth("400px");

        FormLayout form = new FormLayout();

        TextField name = new TextField("Name");
        name.setValue(product != null ? product.name() : "");

        ComboBox<String> category = new ComboBox<>("Category");
        category.setItems("Software", "Infrastructure", "Marketing", "Sales", "Support",
                "Developer", "Security", "Data", "Compliance", "Enterprise", "Tools");
        category.setValue(product != null ? product.category() : null);

        TextField price = new TextField("Price");
        price.setValue(product != null ? product.price() : "");

        NumberField stock = new NumberField("Stock");
        stock.setValue(product != null ? (double) product.stock() : 0.0);
        stock.setStep(1);
        stock.setMin(0);

        Select<String> status = new Select<>();
        status.setLabel("Status");
        status.setItems("Active", "Beta", "Deprecated", "Inactive");
        status.setValue(product != null ? product.status() : "Active");

        form.add(name, category, price, stock, status);
        dialog.add(form);

        Button save = new Button("Save", e -> {
            dialog.close();
            Notification n = Notification.show(
                product == null ? "Product created successfully" : "Product updated successfully",
                3000, Notification.Position.BOTTOM_END);
            n.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        });
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button cancel = new Button("Cancel", e -> dialog.close());
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        dialog.getFooter().add(cancel, save);
        dialog.open();
    }
}
