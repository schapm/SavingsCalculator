package org.schapm.savingscalculator;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
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

        Scene scene = new Scene(root, 800, 600);

        stage.setScene(scene);
        stage.show();
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
            //lineDataMonthly = monthlySavings(lineDataMonthly); // Recalculate data for monthly savings
            lineDataInterest.getData().clear();
            //lineDataInterest = compoundInterest(lineDataInterest); // Recalculate data for monthly savings + interest
        });

        sliderInterestRate.valueProperty().addListener((ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
            labelInterestRate.setText(String.format("%.1f", new_val));

            lineDataInterest.getData().clear();
            //lineDataInterest = compoundInterest(lineDataInterest); // Recalculate data for monthly savings + interest
        });
    }

}