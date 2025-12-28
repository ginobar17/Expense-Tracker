**# Expense Tracker (Java)**

A desktop-based **Expense Tracker application** developed using **Java and JavaFX** to help users manage and track their daily expenses in a simple and organized manner.

---

## **Description**

The Expense Tracker is a **Java-based GUI application** that allows users to record, view, and manage their income and expenses.  
It provides an intuitive interface built with **JavaFX**, enabling users to maintain personal financial records efficiently without using a web browser.

This project demonstrates the use of **JavaFX UI components, event handling, and basic data management**.

---

## **Features**

- Add income and expense entries  
- Display expense records in a structured format  
- Calculate and show total expenses  
- Simple and user-friendly graphical interface  
- Desktop application (no internet required)

---

## **Technologies Used**

- **Java**
- **JavaFX**
- **IntelliJ IDEA / Eclipse** (IDE)

---

##** Project Structure **

- `Main.java` – Application entry point  
- `AuthenticationUI.java` -  creates and manages the login user interface, validates user credentials, and loads the main finance tracker screen upon successful authentication.
- `CSVExporter.java` - exports all transaction records to a CSV file, calculates income, expenses, and balance, and automatically opens the generated file.
- `ConnectionTest.java` - to check the connection to the database
- `DataManager.java` - handles all database operations including user authentication, transaction storage, retrieval, and deletion using SQLite.
- `FinanceTrackerUI.java` - builds and manages the main application dashboard, allowing users to add, view, delete, analyze, and export financial transactions.
- `Transaction.java` - represents the data model for a financial transaction and provides JavaFX properties for seamless binding with the user interface.
---

## **How to Run the Project**

- Clone the repository:
   ```bash
   git clone https://github.com/ginobar17/Expense-Tracker.git
  
- Open the project in IntelliJ IDEA or Eclipse
- Make sure JavaFX is properly configured in your IDE
- Run the Main.java file

**Requirements**
-JDK 17 or above (or as used in the project)
-JavaFX SDK
-IDE with JavaFX support

**Learning Outcomes**
-Understanding JavaFX application structure
-Event handling in Java GUI applications
-Basic financial data handling
-Designing desktop applications using Java

**Future Enhancements**
-Data persistence using files or database
-Category-wise expense tracking
-Monthly and yearly reports

Graphical charts for expense analysis

User authentication
