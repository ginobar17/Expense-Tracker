import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class FinanceTrackerUI {
    private BorderPane mainLayout;
    private TableView<Transaction> transactionTable;
    private ObservableList<Transaction> transactionList;
    private Label balanceLabel;
    private Label incomeLabel;
    private Label expenseLabel;

    public FinanceTrackerUI() {
        mainLayout = new BorderPane();
        mainLayout.setPadding(new Insets(20));
        mainLayout.setStyle("-fx-background-color: #f5f5f5;");

        transactionList = FXCollections.observableArrayList();

        // Top section
        mainLayout.setTop(createHeaderSection());

        // Center section
        mainLayout.setCenter(createTableSection());

        // Right section
        mainLayout.setRight(createInputSection());

        // Bottom section
        mainLayout.setBottom(createSummarySection());

        loadTransactionsFromDatabase();
    }

    private VBox createHeaderSection() {
        VBox header = new VBox(10);
        header.setPadding(new Insets(0, 0, 20, 0));
        header.setAlignment(Pos.CENTER);

        Label title = new Label("💰 Personal Finance Tracker");
        title.setFont(Font.font("System", FontWeight.BOLD, 24));
        title.setStyle("-fx-text-fill: #2c3e50;");

        HBox statsBox = new HBox(30);
        statsBox.setAlignment(Pos.CENTER);
        statsBox.setPadding(new Insets(15));
        statsBox.setStyle("-fx-background-color: white; -fx-background-radius: 10; " +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 2);");

        balanceLabel = createStatLabel("Balance", "₹0.00", "#3498db");
        incomeLabel = createStatLabel("Income", "₹0.00", "#27ae60");
        expenseLabel = createStatLabel("Expenses", "₹0.00", "#e74c3c");

        statsBox.getChildren().addAll(balanceLabel, createSeparator(), incomeLabel,
                createSeparator(), expenseLabel);

        header.getChildren().addAll(title, statsBox);
        return header;
    }

    private Label createStatLabel(String title, String value, String color) {
        VBox container = new VBox(5);
        container.setAlignment(Pos.CENTER);

        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #7f8c8d;");

        Label valueLabel = new Label(value);
        valueLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: " + color + ";");

        container.getChildren().addAll(titleLabel, valueLabel);

        Label wrapper = new Label();
        wrapper.setGraphic(container);
        return wrapper;
    }

    private Region createSeparator() {
        Region separator = new Region();
        separator.setPrefWidth(1);
        separator.setStyle("-fx-background-color: #ecf0f1;");
        return separator;
    }

    private VBox createTableSection() {
        VBox tableSection = new VBox(10);
        tableSection.setPadding(new Insets(0, 10, 0, 0));

        Label tableTitle = new Label("Recent Transactions");
        tableTitle.setFont(Font.font("System", FontWeight.SEMI_BOLD, 16));

        transactionTable = new TableView<>();
        transactionTable.setItems(transactionList);
        transactionTable.setStyle("-fx-background-color: white; -fx-background-radius: 10;");

        TableColumn<Transaction, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(data -> data.getValue().idProperty().asObject());
        idCol.setPrefWidth(50);

        TableColumn<Transaction, String> typeCol = new TableColumn<>("Type");
        typeCol.setCellValueFactory(data -> data.getValue().typeProperty());
        typeCol.setPrefWidth(90);

        TableColumn<Transaction, String> categoryCol = new TableColumn<>("Category");
        categoryCol.setCellValueFactory(data -> data.getValue().categoryProperty());
        categoryCol.setPrefWidth(120);

        TableColumn<Transaction, String> notesCol = new TableColumn<>("Notes");
        notesCol.setCellValueFactory(data -> data.getValue().notesProperty());
        notesCol.setPrefWidth(180);

        TableColumn<Transaction, Double> amountCol = new TableColumn<>("Amount");
        amountCol.setCellValueFactory(data -> data.getValue().amountProperty().asObject());
        amountCol.setPrefWidth(100);

        TableColumn<Transaction, String> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(data -> data.getValue().transactionDateProperty());
        dateCol.setPrefWidth(110);

        TableColumn<Transaction, String> paymentCol = new TableColumn<>("Payment");
        paymentCol.setCellValueFactory(data -> data.getValue().paymentMethodProperty());
        paymentCol.setPrefWidth(100);

        transactionTable.getColumns().addAll(idCol, typeCol, categoryCol, notesCol,
                amountCol, dateCol, paymentCol);

        tableSection.getChildren().addAll(tableTitle, transactionTable);
        return tableSection;
    }

    private VBox createInputSection() {
        VBox inputSection = new VBox(15);
        inputSection.setPadding(new Insets(10, 0, 0, 10));
        inputSection.setPrefWidth(280);
        inputSection.setStyle("-fx-background-color: white; -fx-background-radius: 10; " +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 2);");
        inputSection.setPadding(new Insets(20));

        Label formTitle = new Label("Add Transaction");
        formTitle.setFont(Font.font("System", FontWeight.SEMI_BOLD, 16));

        ComboBox<String> typeCombo = new ComboBox<>();
        typeCombo.setPromptText("Type");
        typeCombo.getItems().addAll("Income", "Expense");
        typeCombo.setMaxWidth(Double.MAX_VALUE);

        ComboBox<String> categoryCombo = new ComboBox<>();
        categoryCombo.setPromptText("Category");
        categoryCombo.setMaxWidth(Double.MAX_VALUE);

        TextField notesField = new TextField();
        notesField.setPromptText("Notes");

        TextField amountField = new TextField();
        amountField.setPromptText("Amount");

        ComboBox<String> paymentCombo = new ComboBox<>();
        paymentCombo.setPromptText("Payment Method");
        paymentCombo.getItems().addAll("Cash", "Card", "UPI", "Net Banking", "Other");
        paymentCombo.setMaxWidth(Double.MAX_VALUE);

        typeCombo.setOnAction(e -> {
            String type = typeCombo.getValue();
            categoryCombo.getItems().clear();
            if ("Income".equals(type)) {
                categoryCombo.getItems().addAll("Salary", "Freelance", "Investment",
                        "Gift", "Other");
            } else if ("Expense".equals(type)) {
                categoryCombo.getItems().addAll("Food", "Transport", "Shopping",
                        "Entertainment", "Bills", "Health", "Other");
            }
        });

        Button addButton = new Button("Add Transaction");
        addButton.setMaxWidth(Double.MAX_VALUE);
        addButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; " +
                "-fx-font-weight: bold; -fx-padding: 10;");

        Button deleteButton = new Button("Delete Selected");
        deleteButton.setMaxWidth(Double.MAX_VALUE);
        deleteButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; " +
                "-fx-font-weight: bold; -fx-padding: 10;");

        Button exportButton = new Button("Export to CSV");
        exportButton.setMaxWidth(Double.MAX_VALUE);
        exportButton.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; " +
                "-fx-font-weight: bold; -fx-padding: 10;");

        addButton.setOnAction(e -> handleAddTransaction(typeCombo, categoryCombo, notesField,
                amountField, paymentCombo));

        deleteButton.setOnAction(e -> handleDeleteTransaction());

        exportButton.setOnAction(e -> {
            CSVExporter.exportTransactions(transactionList);
            showAlert(Alert.AlertType.INFORMATION, "Export successful!", "transactions.csv");
        });

        inputSection.getChildren().addAll(formTitle, typeCombo, categoryCombo, notesField,
                amountField, paymentCombo, addButton,
                deleteButton, exportButton);
        return inputSection;
    }

    private VBox createSummarySection() {
        VBox summary = new VBox(10);
        summary.setPadding(new Insets(20, 0, 0, 0));

        Label summaryTitle = new Label("Category Summary");
        summaryTitle.setFont(Font.font("System", FontWeight.SEMI_BOLD, 14));

        ListView<String> summaryList = new ListView<>();
        summaryList.setPrefHeight(100);
        summaryList.setStyle("-fx-background-color: white; -fx-background-radius: 10;");

        summary.getChildren().addAll(summaryTitle, summaryList);
        return summary;
    }

    private void handleAddTransaction(ComboBox<String> typeCombo, ComboBox<String> categoryCombo,
                                      TextField notesField, TextField amountField,
                                      ComboBox<String> paymentCombo) {
        String type = typeCombo.getValue();
        String category = categoryCombo.getValue();
        String notes = notesField.getText();
        String payment = paymentCombo.getValue();

        if (type == null || category == null) {
            showAlert(Alert.AlertType.WARNING, "Missing Information",
                    "Please select type and category");
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(amountField.getText());
        } catch (NumberFormatException ex) {
            showAlert(Alert.AlertType.WARNING, "Invalid Amount",
                    "Please enter a valid number");
            return;
        }

        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MMM-yyyy"));

        if (DataManager.insertTransaction(type, category, notes, amount, date, payment)) {
            transactionList.clear();
            loadTransactionsFromDatabase();

            typeCombo.setValue(null);
            categoryCombo.setValue(null);
            notesField.clear();
            amountField.clear();
            paymentCombo.setValue(null);
        }
    }

    private void handleDeleteTransaction() {
        Transaction selected = transactionTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection",
                    "Please select a transaction to delete");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirm Deletion");
        confirm.setHeaderText("Delete this transaction?");
        confirm.setContentText(selected.getCategory() + " - ₹" + selected.getAmount());

        if (confirm.showAndWait().get() == ButtonType.OK) {
            if (DataManager.removeTransaction(selected.getId())) {
                transactionList.remove(selected);
                updateStatistics();
            }
        }
    }

    private void loadTransactionsFromDatabase() {
        transactionList.addAll(DataManager.fetchAllTransactions());
        updateStatistics();
    }

    private void updateStatistics() {
        double totalIncome = 0;
        double totalExpense = 0;

        for (Transaction t : transactionList) {
            if ("Income".equals(t.getType())) {
                totalIncome += t.getAmount();
            } else {
                totalExpense += t.getAmount();
            }
        }

        double balance = totalIncome - totalExpense;

        ((Label)((VBox)balanceLabel.getGraphic()).getChildren().get(1))
                .setText(String.format("₹%.2f", balance));
        ((Label)((VBox)incomeLabel.getGraphic()).getChildren().get(1))
                .setText(String.format("₹%.2f", totalIncome));
        ((Label)((VBox)expenseLabel.getGraphic()).getChildren().get(1))
                .setText(String.format("₹%.2f", totalExpense));
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public BorderPane getView() {
        return mainLayout;
    }
}
