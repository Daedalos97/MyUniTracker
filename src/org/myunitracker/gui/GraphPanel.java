/*
 * Copyright 2017 Samuel James Serwan Heath.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.myunitracker.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.layout.AnchorPane;

//MyUniTracker Imports
import org.myunitracker.main.Assessment;
import org.myunitracker.main.Unit;


/**
 * @author Samuel James Serwan Heath
 */
public class GraphPanel extends JPanel {
    
    private LineChart<String,Number> lineChart;
    private Unit unit;
    private XYChart.Series series;
    
    public GraphPanel(Unit u) {
        setVisible(true);
        this.setLayout(new BorderLayout());
        
        final JFXPanel fxPanel = new JFXPanel();
        this.unit = u;
        add(fxPanel);
        //Make FX thread
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        fxPanel.setScene(createScene());
                    }
                });
            }
        });
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
        
        //Creating the chart
        lineChart = new LineChart(xAxis,yAxis);
        lineChart.setTitle(unit.getUnitName() + " Progress");
        lineChart.setStyle(".default-color0.chart-series-line { -fx-stroke: #f0e68c; }");
        
        //Defining a series
        series = new XYChart.Series();
        series.setName(lineChart.getTitle());
        for (Assessment a : unit.getAssessments()) {
            if (!a.getAssessmentName().equals("Final Exam")) 
            series.getData().add(new XYChart.Data(a.getAssessmentName(),(a.getPercentage())));
        }
        AnchorPane anchorPane = new AnchorPane();
        
        AnchorPane.setTopAnchor(lineChart, 0.0);
        AnchorPane.setBottomAnchor(lineChart, 0.0);
        AnchorPane.setLeftAnchor(lineChart, 0.0);
        AnchorPane.setRightAnchor(lineChart, 0.0);
        anchorPane.getChildren().add(lineChart);
        
        Scene scene = new Scene(anchorPane);
        
        lineChart.setLegendVisible(false);
        lineChart.getData().add(series);
        Platform.setImplicitExit(false);
       
        return scene;
    }
    
    public void updateGraph(Unit u) {
        final Unit unit_temp = u;
        Platform.runLater(new Runnable() {
            @Override public void run() {
                series.getData().clear();
                series.setName(unit_temp.getUnitName());
                for (Assessment a : unit_temp.getAssessments()) {
                    if (!a.getAssessmentName().equals("Final Exam")) {
                        series.getData().add(new XYChart.Data(a.getAssessmentName(),(a.getPercentage())));
                    }
                }
            }
        });
        this.repaint();
    }
}