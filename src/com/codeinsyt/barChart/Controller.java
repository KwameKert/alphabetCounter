package com.codeinsyt.barChart;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Controller {

    @FXML
    NumberAxis xAxis;
    @FXML
    CategoryAxis yAxis;

    @FXML
    Label message;

    @FXML
    BarChart<String, Integer> bc;

    public void initialize(){

    }

    @FXML
    public void selectFile() throws IOException {
        //Creating an instance of FileChooser
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Select a file File");
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt")
        );
        //creating a stage for file selection
        File file = chooser.showOpenDialog(new Stage());
        //passing file path to readFile method
        this.readFile(file.toURI());

    }




    public void readFile(URI uri) throws IOException{
        //obtaining file path from uri
        Path path = Paths.get(uri);

            System.out.println("Yes");
            List<String> words = new ArrayList<>();
            //reading file contents
            BufferedReader br = Files.newBufferedReader(path);
            String input;
            try{
                while((input = br.readLine()) != null){

                    //splitting line into array items
                    String[] sentences = input.split(" ");

                    for(String item: sentences){
                        //pushing words into array list
                        words.add(item);
                    }
                }
                //joining all words to form a string eliminating white spaces
                String fileString = String.join("",words);
                frequencyCounter(fileString.toLowerCase());
            }finally {
                if( br != null){
                    br.close();
                }
            }


    }


    public void frequencyCounter(String str){
        //using the frequency counter algorithm
        HashMap<Character, Integer> counter = new HashMap<>();
        for(int i=0; i < str.length(); i++){
            //if alphabet exists, increases it value else assign 0 to the the alphabet
            int count = counter.containsKey(str.charAt(i)) ?  counter.get(str.charAt(i)) : 0;
            counter.put(str.charAt(i), count +1);
        }
        System.out.println(counter);
        setBarChart(counter);
    }

    public void setBarChart(HashMap<Character, Integer> map){
        //setting barchart properties
        xAxis.setLabel("Alphabet");
        xAxis.setTickLabelRotation(90);
        yAxis.setLabel("Frequency");

        //Creating a barchart series
        XYChart.Series<String, Integer> series = new XYChart.Series();
        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            series.getData().add(new XYChart.Data<>( String.valueOf(entry.getKey()) , entry.getValue()));
        }
        //ploting the bar chart
        this.bc.getData().add(series);
    }


}

