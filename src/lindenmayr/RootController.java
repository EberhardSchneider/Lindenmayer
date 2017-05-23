/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lindenmayr;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.util.Callback;

/**
 *
 * @author eberh_000
 */
public class RootController implements Initializable {
    
   
    
    @FXML
    TextArea tf_input;
    
    @FXML
    TableView<Map.Entry<Character, String>> table_rules;
    
    @FXML
    TableColumn<Map.Entry<Character, String>, Character> table_column_A;
     
    @FXML
    TableColumn<Map.Entry<Character, String>, String> table_column_B;
    
    @FXML
    TextField tf_new_rule;
    
    @FXML
    Button btn_delete_rule;
    
    @FXML
    Button btn_add_rule;
    
    @FXML
    Slider slider_angle;
    
    double deltaAngle = 90;
    
    @FXML
    Slider slider_factor;
    
    double factor = 1;
    
    @FXML Slider
    slider_rotation;
    
    @FXML 
    Canvas canvas;
    
    @FXML
    ScrollPane scrollPane;
    
    Rules rules;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        String input = tf_input.getText();
        
        input = rules.applyRules( input );
        
        ( (LogoCanvas)canvas ).drawString( input );
        
        tf_input.setText( input );
    }
    
    @FXML
    public void handleAddButtonAction(ActionEvent event) {
        rules.addRuleFromString( tf_new_rule.getText() );
    }
    
    @FXML
    public void handleDeleteButtonAction(ActionEvent event) {
        Map.Entry<Character, String>  entry = table_rules.getSelectionModel().getSelectedItem();
        rules.removeRule( entry.getKey() );
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        rules = new Rules();
        
        
        table_column_A.setCellValueFactory((TableColumn.CellDataFeatures<Map.Entry<Character, String>, Character> param) -> {
            return new SimpleObjectProperty( param.getValue().getKey() );
        });
        
        table_column_B.setCellValueFactory((TableColumn.CellDataFeatures<Map.Entry<Character, String>, String> param) -> {
            return new SimpleObjectProperty( param.getValue().getValue() );
        });
        
        ObservableList<Map.Entry<Character, String>> list = FXCollections.observableArrayList( rules.getRules().entrySet() );
        table_rules.setItems( list );
        
        slider_angle.valueProperty().addListener( (observable, oldValue, newValue ) -> {
            ( (LogoCanvas) canvas ).setAngle( (double)newValue );
            ( (LogoCanvas)canvas ).drawString( tf_input.getText() );
        });
        
        slider_factor.valueProperty().addListener( (observable, oldValue, newValue ) -> {
            factor = (double)newValue;
            ( (LogoCanvas) canvas ).setLength( (double)newValue );
            ( (LogoCanvas)canvas ).drawString( tf_input.getText() );
        });
        
        slider_rotation.valueProperty().addListener( (observable, oldValue, newValue ) -> {
            ( (LogoCanvas) canvas ).setRotation( (double)newValue );
            ( (LogoCanvas)canvas ).drawString( tf_input.getText() );
        });
        
        tf_input.textProperty().addListener( (observable, oldValue, newValue ) -> {
            ( (LogoCanvas)canvas ).drawString( tf_input.getText() );
        });
        
        // add Listener to
        MapChangeListener listener = (MapChangeListener) (MapChangeListener.Change change) -> {
            ObservableList<Map.Entry<Character, String>> llist = FXCollections.observableArrayList( rules.getRules().entrySet() );
            table_rules.setItems( llist );
        };
        
        rules.addListener( listener );
        
        // Fractal plant, Angle = 25
        rules.addRule( 'X', "F-[[X]+X]+F[+FX]-X");
        rules.addRule('F',"FF");
        
       
        
        canvas = new LogoCanvas( deltaAngle, factor );
        scrollPane.setContent( canvas );
//        
//        tf_rules.textProperty().addListener( (observable, oldValue, newValue) -> {
//            HashMap<Character, String> r = Rules.getRulesFromString( (String) newValue );
//            if ( r == null ) {
//                tf_rules.setStyle("-fx-text-fill: red");
//            } else {
//                for (Map.Entry<Character, String> entry : r.entrySet() ) {
//                    rules.addRule( entry.getKey(), entry.getValue() );
//                }
//            }
//        });
        
    }    
    
}
