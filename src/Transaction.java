import javafx.beans.property.*;

public class Transaction {
    private IntegerProperty id;
    private StringProperty type;
    private StringProperty category;
    private StringProperty notes;
    private DoubleProperty amount;
    private StringProperty transactionDate;
    private StringProperty paymentMethod;

    public Transaction(int id, String type, String category, String notes, double amount, String transactionDate, String paymentMethod) {
        this.id = new SimpleIntegerProperty(id);
        this.type = new SimpleStringProperty(type);
        this.category = new SimpleStringProperty(category);
        this.notes = new SimpleStringProperty(notes);
        this.amount = new SimpleDoubleProperty(amount);
        this.transactionDate = new SimpleStringProperty(transactionDate);
        this.paymentMethod = new SimpleStringProperty(paymentMethod);
    }

    // Property getters for TableView
    public IntegerProperty idProperty() { return id; }
    public StringProperty typeProperty() { return type; }
    public StringProperty categoryProperty() { return category; }
    public StringProperty notesProperty() { return notes; }
    public DoubleProperty amountProperty() { return amount; }
    public StringProperty transactionDateProperty() { return transactionDate; }
    public StringProperty paymentMethodProperty() { return paymentMethod; }

    // Regular getters
    public int getId() { return id.get(); }
    public String getType() { return type.get(); }
    public String getCategory() { return category.get(); }
    public String getNotes() { return notes.get(); }
    public double getAmount() { return amount.get(); }
    public String getTransactionDate() { return transactionDate.get(); }
    public String getPaymentMethod() { return paymentMethod.get(); }
}
