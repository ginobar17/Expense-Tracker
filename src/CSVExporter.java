import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CSVExporter {

    public static void exportTransactions(List<Transaction> transactions) {
        String filename = "transactions.csv";

        try (FileWriter writer = new FileWriter(filename)) {
            writer.write('\ufeff'); // UTF-8 BOM
            writer.append("ID,Type,Category,Notes,Amount,Date,Payment Method\n");

            double totalIncome = 0.0;
            double totalExpense = 0.0;

            for (Transaction t : transactions) {
                writer.append(String.valueOf(t.getId())).append(",");
                writer.append(escapeField(t.getType())).append(",");
                writer.append(escapeField(t.getCategory())).append(",");
                writer.append(escapeField(t.getNotes())).append(",");
                writer.append(String.format("%.2f", t.getAmount())).append(",");
                writer.append("\"" + t.getTransactionDate() + "\"").append(",");
                writer.append(escapeField(t.getPaymentMethod())).append("\n");

                if ("Income".equals(t.getType())) {
                    totalIncome += t.getAmount();
                } else {
                    totalExpense += t.getAmount();
                }
            }

            writer.append("\n");
            writer.append("====================,====================,====================\n");
            writer.append("TOTAL INCOME:,₹" + String.format("%.2f", totalIncome) + "\n");
            writer.append("TOTAL EXPENSES:,₹" + String.format("%.2f", totalExpense) + "\n");
            writer.append("NET BALANCE:,₹" + String.format("%.2f", (totalIncome - totalExpense)) + "\n");
            writer.append("====================,====================,====================\n");

            writer.flush();
            System.out.println("✓ Export completed: " + filename);

            try {
                java.awt.Desktop.getDesktop().open(new java.io.File(filename));
            } catch (IOException ex) {
                System.out.println("Could not auto-open file: " + ex.getMessage());
            }

        } catch (IOException e) {
            System.err.println("Export error: " + e.getMessage());
        }
    }

    private static String escapeField(String value) {
        if (value == null) return "";
        if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }
        return value;
    }
}
