package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import javax.swing.table.DefaultTableModel;
import view.Menu;

public class Abstract extends Functions {

    public Abstract(Menu view) {
        super(view);
    }

    @Override
    public void searchCupcakeByCategory() {

        String searchCategory = view.getSearchText().trim();

        if (searchCategory.isEmpty()) {
            view.showMessage("Please enter a category to search!");
            return;
        }

        DefaultTableModel model = (DefaultTableModel) view.getTblcupcakes().getModel();
        model.setRowCount(0); // clear table first

        try (BufferedReader reader = new BufferedReader(new FileReader("Cake_Details.txt"))) {
            String line;
            boolean found = false;

            while ((line = reader.readLine()) != null) {
                // skip empty lines to avoid parsing error
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] parts = line.split("\\|");
                if (parts.length == 5) {
                    String flavor = parts[0].trim();
                    String category = parts[1].trim();
                    String size = parts[2].trim();
                    int qty = Integer.parseInt(parts[3].trim());
                    double price = Double.parseDouble(parts[4].trim());

                    if (category.equalsIgnoreCase(searchCategory)) {
                        model.addRow(new Object[]{flavor, category, size, qty, price});
                        found = true;
                    }
                } else {
                    // skip invalid lines
                    continue;
                }
            }

            if (!found) {
                view.showMessage("No cupcakes found for category: " + searchCategory);
            }

            view.clearCFields(); // clear search input after search

        } catch (Exception e) {
            view.showMessage("Error searching cupcakes: " + e.getMessage());
        }
    }

    @Override
    public void deleteCupcake() {

        String flavorToDelete = view.getDeleteText().trim();

        if (flavorToDelete.isEmpty()) {
            view.showMessage("Please enter a flavor to delete!");
            return;
        }

        File inputFile = new File("Cake_Details.txt");
        File tempFile = new File("Temp_Cake_Details.txt");

        boolean found = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue; // skip empty lines
                }

                String[] parts = line.split("\\|");
                if (parts.length == 5) {
                    String flavor = parts[0].trim();

                    if (!flavor.equalsIgnoreCase(flavorToDelete)) {
                        writer.write(line);
                        writer.newLine();
                    } else {
                        found = true;
                    }
                }
            }

        } catch (Exception e) {
            view.showMessage("Error deleting cupcake: " + e.getMessage());
            return;
        }

        // Replace original file with temp
        if (inputFile.delete()) {
            tempFile.renameTo(inputFile);
        }

        if (found) {
            view.showMessage("Cupcake deleted successfully!");
        } else {
            view.showMessage("No cupcake found with flavor: " + flavorToDelete);
        }

        view.clearDFields(); // clear delete input field
    }
}
