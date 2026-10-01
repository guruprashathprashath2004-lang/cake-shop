package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.table.DefaultTableModel;
import model.Manager_login;
import view.Login;
import view.Add_Cashier;

public class Manager {

    private Login login;
    private Add_Cashier add;

    public Manager(Login login) {
        this.login = login;
    }

    public Manager(Add_Cashier add) {
        this.add = add;
    }

    // Managerchoice login validation     
    public void managerLogin() {
        String username = login.getUsernameText().trim();
        String password = login.getPasswordText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            login.clearFields();
            login.showMessage("Username or password cannot be empty !!");
            return;
        }

        Manager_login[] managers = {
            new Manager_login("admin", "admin123") // demo credentials
        };

        boolean valid = false;
        for (Manager_login m : managers) {
            if (validateManager(m, username, password)) {
                valid = true;
                break;
            }
        }

        if (valid) {
            login.showMessage("Login Successful!");
            new view.Managerchoice().setVisible(true);
            login.closeFrame();
        } else {
            login.clearFields();
            login.showMessage("Invalid manager credentials!");
        }
    }

    private boolean validateManager(Manager_login manager, String inputUsername, String inputPassword) {
        return inputUsername.equals(manager.getUsername()) && inputPassword.equals(manager.getPassword());
    }

// Adds a cashier to Cashiers_Details.txt  
    public void addCashier() {

        String username = add.getCUsernameText().trim();
        String password = add.getCPasswordText().trim();

        // Validate empty fields first
        if (username.isEmpty() || password.isEmpty()) {
            add.clearCFields();
            add.showMessage("Username and password cannot be empty!");
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter("Cashiers_Details.txt", true))) {
            writer.write(username + "|" + password);
            writer.newLine();
            add.clearCFields();
            add.showMessage("Cashier added successfully!");
        } catch (IOException e) {
            add.clearCFields();
            add.showMessage("Error saving cashier: " + e.getMessage());
        }
    }

// Displays all cupcakes in the JTable    
    public void viewCashierDetails() {
        DefaultTableModel model = (DefaultTableModel) add.getTblcashier().getModel();
        model.setRowCount(0); // clear existing rows

        File ViewFile = new File("Cashiers_Details.txt");
        if (!ViewFile.exists() || ViewFile.length() == 0) {
            add.showMessage("No cashier detail found yet!");
            return;
        }

        try (BufferedReader reader = new BufferedReader(
                new FileReader("Cashiers_Details.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|"); // split by pipe |
                if (parts.length == 2) {
                    String usermane = parts[0];
                    String password = parts[1];

                    model.addRow(new Object[]{usermane, password});
                }
            }
        } catch (Exception e) {
            add.showMessage("Error loading cashiers: " + e.getMessage());
        }
    }

    public void deleteCashier() {

        String DeleteCashier = add.getCUsernameText().trim();

        if (DeleteCashier.isEmpty()) {
            add.showMessage("Please enter a username to delete!");
            return;
        }

        File inputFile = new File("Cashiers_Details.txt");
        File tempFile = new File("Temp_Cashiers_Details.txt");

        boolean found = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue; // skip empty lines
                }

                String[] parts = line.split("\\|");
                if (parts.length == 2) {
                    String username = parts[0].trim();

                    if (!username.equalsIgnoreCase(DeleteCashier)) {
                        writer.write(line);
                        writer.newLine();
                    } else {
                        found = true;
                    }
                }
            }

        } catch (Exception e) {
            add.showMessage("Error deleting cashier: " + e.getMessage());
            return;
        }

        // Replace original file with temp
        if (inputFile.delete()) {
            tempFile.renameTo(inputFile);
        }

        if (found) {
            add.showMessage("Cashier deleted successfully!");
        } else {
            add.showMessage("No cashier found with username: " + DeleteCashier);
        }
        add.clearCFields();
    }

}
