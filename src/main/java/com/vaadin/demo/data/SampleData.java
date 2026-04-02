package com.vaadin.demo.data;

import java.time.LocalDate;
import java.util.List;

public final class SampleData {

    private SampleData() {
    }

    public static List<Order> orders() {
        return List.of(
                new Order("ORD-001", "Alice Johnson", "$1,240.00", "Completed", LocalDate.of(2026, 3, 20)),
                new Order("ORD-002", "Bob Smith", "$390.50", "Pending", LocalDate.of(2026, 3, 21)),
                new Order("ORD-003", "Carol White", "$875.00", "Completed", LocalDate.of(2026, 3, 21)),
                new Order("ORD-004", "David Brown", "$2,100.00", "Processing", LocalDate.of(2026, 3, 22)),
                new Order("ORD-005", "Eva Martinez", "$150.75", "Completed", LocalDate.of(2026, 3, 22)),
                new Order("ORD-006", "Frank Lee", "$3,450.00", "Failed", LocalDate.of(2026, 3, 22)),
                new Order("ORD-007", "Grace Kim", "$780.00", "Completed", LocalDate.of(2026, 3, 23)),
                new Order("ORD-008", "Henry Davis", "$540.25", "Pending", LocalDate.of(2026, 3, 23)),
                new Order("ORD-009", "Iris Chen", "$1,900.00", "Processing", LocalDate.of(2026, 3, 23)),
                new Order("ORD-010", "Jack Wilson", "$230.00", "Completed", LocalDate.of(2026, 3, 23)),
                new Order("ORD-011", "Karen Taylor", "$4,200.00", "Completed", LocalDate.of(2026, 3, 24)),
                new Order("ORD-012", "Leo Anderson", "$670.50", "Pending", LocalDate.of(2026, 3, 24)),
                new Order("ORD-013", "Mia Thomas", "$1,050.00", "Completed", LocalDate.of(2026, 3, 24)),
                new Order("ORD-014", "Noah Jackson", "$88.00", "Failed", LocalDate.of(2026, 3, 24)),
                new Order("ORD-015", "Olivia Harris", "$2,750.00", "Processing", LocalDate.of(2026, 3, 24)),
                new Order("ORD-016", "Paul Martinez", "$315.00", "Completed", LocalDate.of(2026, 3, 24)),
                new Order("ORD-017", "Quinn Robinson", "$1,600.00", "Completed", LocalDate.of(2026, 3, 24)),
                new Order("ORD-018", "Rachel Clark", "$490.00", "Pending", LocalDate.of(2026, 3, 24)),
                new Order("ORD-019", "Samuel Lewis", "$3,100.00", "Processing", LocalDate.of(2026, 3, 24)),
                new Order("ORD-020", "Tina Walker", "$725.50", "Completed", LocalDate.of(2026, 3, 24))
        );
    }

    public static List<Product> products() {
        return List.of(
                new Product("Analytics Pro", "Software", "$49.99/mo", 999, "Active"),
                new Product("Cloud Storage 1TB", "Infrastructure", "$9.99/mo", 500, "Active"),
                new Product("Email Campaigns", "Marketing", "$29.99/mo", 250, "Active"),
                new Product("CRM Starter", "Sales", "$19.99/mo", 400, "Active"),
                new Product("Support Desk", "Support", "$14.99/mo", 300, "Active"),
                new Product("API Gateway", "Infrastructure", "$99.99/mo", 150, "Active"),
                new Product("Data Warehouse", "Data", "$149.99/mo", 75, "Beta"),
                new Product("Mobile SDK", "Developer", "$0.00", 999, "Active"),
                new Product("SSO Integration", "Security", "$39.99/mo", 200, "Active"),
                new Product("Audit Logs", "Compliance", "$24.99/mo", 180, "Active"),
                new Product("White Label", "Enterprise", "$299.99/mo", 50, "Active"),
                new Product("Legacy Importer", "Tools", "$0.00", 10, "Deprecated")
        );
    }

    public static List<User> users() {
        return List.of(
                new User("Alice Johnson", "alice@example.com", "Admin", "2026-03-24 09:15", "AJ"),
                new User("Bob Smith", "bob@example.com", "Developer", "2026-03-24 08:42", "BS"),
                new User("Carol White", "carol@example.com", "Viewer", "2026-03-23 17:30", "CW"),
                new User("David Brown", "david@example.com", "Developer", "2026-03-23 14:05", "DB"),
                new User("Eva Martinez", "eva@example.com", "Admin", "2026-03-22 11:20", "EM"),
                new User("Frank Lee", "frank@example.com", "Viewer", "2026-03-21 16:45", "FL"),
                new User("Grace Kim", "grace@example.com", "Developer", "2026-03-24 07:58", "GK"),
                new User("Henry Davis", "henry@example.com", "Billing", "2026-03-20 10:10", "HD"),
                new User("Iris Chen", "iris@example.com", "Developer", "2026-03-24 09:50", "IC"),
                new User("Jack Wilson", "jack@example.com", "Viewer", "2026-03-19 15:00", "JW")
        );
    }

    public static List<Integration> integrations() {
        return List.of(
                new Integration("GitHub", "Sync repositories and trigger workflows", true),
                new Integration("Slack", "Send notifications to Slack channels", true),
                new Integration("Stripe", "Process payments and manage subscriptions", true),
                new Integration("Salesforce", "Sync CRM data with Salesforce", false),
                new Integration("Google Analytics", "Track user behavior and conversions", false),
                new Integration("Jira", "Create and update issues automatically", false)
        );
    }

    public record Order(String id, String customer, String amount, String status, LocalDate date) {
    }

    public record Product(String name, String category, String price, int stock, String status) {
    }

    public record User(String name, String email, String role, String lastLogin, String initials) {
    }

    public record Integration(String name, String description, boolean enabled) {
    }
}
