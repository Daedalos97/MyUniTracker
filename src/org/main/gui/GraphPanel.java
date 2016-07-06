package org.main.gui;

import org.main.myunitracker.Assessment;
import javax.swing.JPanel;
import javafx.scene.chart.LineChart;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import javafx.application.Platform;
import org.main.myunitracker.MyUniTracker;


/**
 *
 * @author Samuel Heath
 */
public class GraphPanel extends JPanel {
    
    public XYChart.Series series;
    
    public GraphPanel(int index) {
        this.setSize(200, 300);
        this.setVisible(true);
        final JFXPanel fxPanel = new JFXPanel();
        final int i = index;
        
        add(fxPanel);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                initFX(fxPanel,i);
            }
        });
    }
    
    private void initFX(JFXPanel fxPanel, int idx) {
        // This method is invoked on the JavaFX thread
        Scene scene = createScene(idx);
        fxPanel.setScene(scene);
    } 
    
    private Scene createScene(int idx) {
        Stage s = new Stage();
        s.setTitle("Unit Progress");
        //Defining the axes
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Assessments");
        yAxis.setLabel("Marks (%)");
        yAxis.setUpperBound(100.0);
        //creating the chart
        final LineChart<String,Number> lineChart = new LineChart(xAxis,yAxis);
        lineChart.setTitle(MyUniTracker.units.get(idx).getName() + " Progress");
        lineChart.setStyle(".default-color0.chart-series-line { -fx-stroke: #f0e68c; }");
        //defining a series
        series = new XYChart.Series();
        series.setName(lineChart.getTitle());
        for (Assessment a : MyUniTracker.units.get(idx).getAssessments()) {
            if (!a.getAssessmentName().equals("Final Exam")) 
            series.getData().add(new XYChart.Data(a.getAssessmentName(),(a.getPercentage())));
        }
        Scene scene = new Scene(lineChart,855,600);
        lineChart.getData().add(series);
        Platform.setImplicitExit(false);
       
        return scene;
    }
    
    public void updateGraph(int i) {
        final int n = i;
        Platform.runLater(new Runnable() {
            @Override public void run() {
                series.getData().clear();
                for (Assessment a : MyUniTracker.units.get(n).getAssessments()) {
                    if (!a.getAssessmentName().equals("Final Exam")) {
                        series.getData().add(new XYChart.Data(a.getAssessmentName(),(a.getPercentage())));
                    }
                }
            }
        });
    }
}