/*
 * Copyright (c) 2016, Samuel James Serwan Heath
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
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