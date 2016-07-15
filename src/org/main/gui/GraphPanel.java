/*
 * The MIT License
 *
 * Copyright 2016 Samuel James Serwan Heath.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

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
import org.main.myunitracker.Unit;


/**
 * @author Samuel Heath
 */
public class GraphPanel extends JPanel {
    
    private XYChart.Series series;
    private LineChart<String,Number> lineChart;
    private Unit unit;
    
    public GraphPanel(Unit u) {
        this.setVisible(true);
        final JFXPanel fxPanel = new JFXPanel();
        this.unit = u;
        
        add(fxPanel);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                initFX(fxPanel);
            }
        });
    }
    
    private void initFX(JFXPanel fxPanel) {
        // This method is invoked on the JavaFX thread
        Scene scene = createScene();
        fxPanel.setScene(scene);
    } 
    
    private Scene createScene() {
        Stage s = new Stage();
        s.setTitle("Unit Progress");
        //Defining the axes
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Assessments");
        yAxis.setLabel("Marks (%)");
        yAxis.setUpperBound(100.0);
        //creating the chart
        lineChart = new LineChart(xAxis,yAxis);
        lineChart.setTitle(unit.getUnitName() + " Progress");
        lineChart.setStyle(".default-color0.chart-series-line { -fx-stroke: #f0e68c; }");
        //defining a series
        series = new XYChart.Series();
        series.setName(lineChart.getTitle());
        for (Assessment a : unit.getAssessments()) {
            if (!a.getAssessmentName().equals("Final Exam")) 
            series.getData().add(new XYChart.Data(a.getAssessmentName(),(a.getPercentage())));
        }
        Scene scene = new Scene(lineChart,826,600);
        lineChart.setLegendVisible(false);
        lineChart.getData().add(series);
        Platform.setImplicitExit(false);
       
        return scene;
    }
    
    public void updateGraph(Unit u) {
        final Unit unit = u;
        Platform.runLater(new Runnable() {
            @Override public void run() {
                series.getData().clear();
                series.setName(unit.getUnitName());
                for (Assessment a : unit.getAssessments()) {
                    if (!a.getAssessmentName().equals("Final Exam")) {
                        series.getData().add(new XYChart.Data(a.getAssessmentName(),(a.getPercentage())));
                    }
                }
            }
        });
        this.repaint();
    }
}