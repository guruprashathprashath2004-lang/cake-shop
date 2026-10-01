package controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import model.Cashier_login;
import view.Login;

public class Cashier {

    private Login login;

    public Cashier(Login login) {
        this.login = login;
    }

    // Menu login validation     
    public void cashierLogin() {
        String username = login.getUsernameText().trim();
        String password = login.getPasswordText().trim();

        // Validate empty fields first
        if (username.isEmpty() || password.isEmpty()) {
            login.clearFields();
            login.showMessage("Username or password cannot be empty !!");
            return;
        }

        Cashier_login cashier = new Cashier_login(username, password);

        if (validateCashier(cashier)) {
            login.showMessage("Login Successful! Welcome " + cashier.getUsername());
            new view.Menu().setVisible(true);
            login.closeFrame();
        } else {
            login.showMessage("Invalid cashier credentials");
            login.clearFields();
        }
    }

    private boolean validateCashier(Cashier_login cashier) {
        
        try (BufferedReader reader = new BufferedReader(
                new FileReader("Cashiers_Details.txt"))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 2) {
                    String storedUser = parts[0].trim();
                    String storedPass = parts[1].trim();

                    if (storedUser.equalsIgnoreCase(cashier.getUsername().trim())
                            && storedPass.equals(cashier.getPassword().trim())) {
                        return true;
                    }
                }
            }

        } catch (IOException e) {
            login.showMessage("Error reading cashier file: " + e.getMessage());
        }

        return false;
    }

}
