package com.mycompany.booktrack;

import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;

public class DashboardController implements StatsCard {

    @FXML
    private Label totalBooksLabel;
    @FXML
    private Label activeUsersLabel;
    @FXML
    private Label booksInLoanLabel;
    @FXML
    private Label lateLoansLabel;
    @FXML
    private PieChart loanPieChart;
    @FXML
    private PieChart activeUsersPieChart;
    @FXML
    private PieChart lateLoansPieChart;

    private DashboardDAO dashboardDAO = new DashboardDAO();

    private int totalBooks;
    private int activeUsers;
    private int booksInLoan;
    private int lateLoans;

    @FXML
    public void initialize() {
        // Initialize the data for the dashboard
        loadData();
        updateView();
        setupPieChartHoverEffects();
    }

    @Override
    public void loadData() {
        // Fetch data from the database
        totalBooks = dashboardDAO.getTotalBooks();
        activeUsers = dashboardDAO.getActiveUsers();
        booksInLoan = dashboardDAO.getBooksInLoan();
        lateLoans = dashboardDAO.getLateLoans();
    }

    @Override
    public void updateView() {
        // Update the labels with the actual data
        totalBooksLabel.setText(String.valueOf(totalBooks));
        activeUsersLabel.setText(String.valueOf(activeUsers));
        booksInLoanLabel.setText(String.valueOf(booksInLoan));
        lateLoansLabel.setText(String.valueOf(lateLoans));

        // Create the data for the loan pie chart
        ObservableList<PieChart.Data> loanPieChartData = FXCollections.observableArrayList(
            new PieChart.Data("Books in Loan", booksInLoan),
            new PieChart.Data("Books Available", totalBooks - booksInLoan)
        );
        loanPieChart.setData(loanPieChartData);

        // Create the data for the active users pie chart
        ObservableList<PieChart.Data> activeUsersPieChartData = FXCollections.observableArrayList(
            new PieChart.Data("Active Users", activeUsers),
            new PieChart.Data("Inactive Users", totalBooks - activeUsers) // Inactive is a placeholder for calculation
        );
        activeUsersPieChart.setData(activeUsersPieChartData);

        // Create the data for the late loans pie chart
        ObservableList<PieChart.Data> lateLoansPieChartData = FXCollections.observableArrayList(
            new PieChart.Data("Late Loans", lateLoans),
            new PieChart.Data("On Time", booksInLoan - lateLoans)
        );
        lateLoansPieChart.setData(lateLoansPieChartData);
    }

    // Method to add hover effects to pie chart slices
    private void setupPieChartHoverEffects() {
        for (PieChart.Data data : loanPieChart.getData()) {
            // On hover, show the tooltip with dynamic text
            data.getNode().setOnMouseEntered(event -> {
                String tooltipText = data.getName() + ": " + (int)data.getPieValue() + " books";
                Tooltip tooltip = new Tooltip(tooltipText);
                Tooltip.install(data.getNode(), tooltip);
            });

            // On mouse exit, remove the tooltip
            data.getNode().setOnMouseExited(event -> {
                Tooltip.uninstall(data.getNode(), null);
            });
        }

        for (PieChart.Data data : activeUsersPieChart.getData()) {
            data.getNode().setOnMouseEntered(event -> {
                String tooltipText = data.getName() + ": " + (int)data.getPieValue() + " users";
                Tooltip tooltip = new Tooltip(tooltipText);
                Tooltip.install(data.getNode(), tooltip);
            });

            data.getNode().setOnMouseExited(event -> {
                Tooltip.uninstall(data.getNode(), null);
            });
        }

        for (PieChart.Data data : lateLoansPieChart.getData()) {
            data.getNode().setOnMouseEntered(event -> {
                String tooltipText = data.getName() + ": " + (int)data.getPieValue() + " loans";
                Tooltip tooltip = new Tooltip(tooltipText);
                Tooltip.install(data.getNode(), tooltip);
            });

            data.getNode().setOnMouseExited(event -> {
                Tooltip.uninstall(data.getNode(), null);
            });
        }
    }
}

