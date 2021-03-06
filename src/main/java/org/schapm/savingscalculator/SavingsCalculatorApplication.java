package org.schapm.savingscalculator;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

/**
 *
 * @author schapm
 */

public class SavingsCalculatorApplication extends Application {

    Slider sliderMonthlySavings;
    Slider sliderInterestRate;
    Label labelMonthlySavings;
    Label labelInterestRate;

    BorderPane root;
    VBox vBox;

    XYChart.Series lineDataMonthly;
    XYChart.Series lineDataInterest;
    LineChart<Number, Number> lineChart;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        initSliders();
        initChart();
        initLayouts();

        Scene scene = new Scene(root, 800, 600);

        stage.setScene(scene);
        stage.show();
    }

    public XYChart.Series monthlySavings(XYChart.Series series) {
        double monthlySavings = sliderMonthlySavings.getValue();
        double sum = 0;

        for (int i = 0; i <= 30; i++) { // 30 years
            series.getData().add(new XYChart.Data(i, sum));
            sum += monthlySavings * 12; // 12 months savings
        }

        return series;
    }

    public XYChart.Series compoundInterest(XYChart.Series series) {
        double interestRatePA = sliderInterestRate.getValue();
        double monthlySavings = sliderMonthlySavings.getValue();
        double sum = 0;

        for (int i = 0; i <= 30; i++) { // 30 years
            double time = 1; // 1 year

            series.getData().add(new XYChart.Data(i, sum));
            double CI = sum
                    * (Math.pow((1 + interestRatePA / 100), time));
            sum = CI + ((monthlySavings * 12) + ((monthlySavings * 12) * (interestRatePA / 100))); // 12 months savings + interest
        }

        return series;
    }

    private void initSliders() {
        sliderMonthlySavings = new Slider(25, 250, 50); // Min, max & default values
        sliderMonthlySavings.setShowTickLabels(true);
        sliderMonthlySavings.setShowTickMarks(true);

        sliderInterestRate = new Slider(0, 10, 2); // Min, max & default values
        sliderInterestRate.setShowTickLabels(true);
        sliderInterestRate.setShowTickMarks(true);

        sliderMonthlySavings.valueProperty().addListener((ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
            labelMonthlySavings.setText(String.format("%.1f", new_val));

            lineDataMonthly.getData().clear();
            lineDataMonthly = monthlySavings(lineDataMonthly); // Recalculate data for monthly savings
            lineDataInterest.getData().clear();
            lineDataInterest = compoundInterest(lineDataInterest); // Recalculate data for monthly savings + interest
        });

        sliderInterestRate.valueProperty().addListener((ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
            labelInterestRate.setText(String.format("%.1f", new_val));

            lineDataInterest.getData().clear();
            lineDataInterest = compoundInterest(lineDataInterest); // Recalculate data for monthly savings + interest
        });
    }

    private void initChart() {
        NumberAxis xAxis = new NumberAxis(0, 30, 1);
        xAxis.setLabel("Years");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Savings");

        lineChart = new LineChart(xAxis, yAxis);
        lineChart.setAnimated(false);
        lineChart.setTitle("Savings Calculator");

        lineDataMonthly = new XYChart.Series();
        lineDataMonthly.setName("Monthly Savings");
        lineDataInterest = new XYChart.Series();
        lineDataInterest.setName("Compound Interest");

        lineChart.getData().add(monthlySavings(lineDataMonthly));
        lineChart.getData().add(compoundInterest(lineDataInterest));
    }

    private void initLayouts() {
        root = new BorderPane();
        root.setPadding(new Insets(10));

        labelMonthlySavings = new Label();
        labelMonthlySavings.setText(String.valueOf(sliderMonthlySavings.getValue()));
        labelInterestRate = new Label();
        labelInterestRate.setText(String.valueOf(sliderInterestRate.getValue()));

        BorderPane borderPaneMonthlySavings = new BorderPane();
        borderPaneMonthlySavings.setLeft(new Label("Monthly savings"));
        borderPaneMonthlySavings.setCenter(sliderMonthlySavings);
        borderPaneMonthlySavings.setRight(labelMonthlySavings);

        BorderPane borderPaneInterestRate = new BorderPane();
        borderPaneInterestRate.setLeft(new Label("Yearly interest rate"));
        borderPaneInterestRate.setCenter(sliderInterestRate);
        borderPaneInterestRate.setRight(labelInterestRate);

        vBox = new VBox();
        vBox.getChildren().add(borderPaneMonthlySavings);
        vBox.getChildren().add(borderPaneInterestRate);

        root.setTop(vBox);
        root.setCenter(lineChart);
    }

}