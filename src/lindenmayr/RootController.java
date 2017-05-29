/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lindenmayr;

import Logo3D.Cube;
import Logo3D.Logo3DCanvas;
import Logo3D.TurtleState3D;
import Logo3D.WorkCam;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
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

    @FXML
    Slider slider_rotation;

    @FXML
    Canvas canvas;

    @FXML
    AnchorPane anchorPane;

    Rules rules;

    Scene scene3d;
    AnchorPane root;

    Logo3DCanvas canvas3D;

    // for the new scene mouse dragged listener
    double mousePosX;
    double mousePosY;
    double oldMousePosX;
    double oldMousePosY;
    double deltaX;
    double deltaY;

    @FXML
    private void handleButtonAction(ActionEvent event) {
        String input = tf_input.getText();

        input = rules.applyRules(input);

        //((LogoCanvas) canvas).drawString(input);
        Group g = canvas3D.drawString(input);
        System.out.println(anchorPane);
        root
                .getChildren()
                .add(g);
        tf_input.setText(input);
    }

    @FXML
    public void handleAddButtonAction(ActionEvent event) {
        rules.addRuleFromString(tf_new_rule.getText());
    }

    @FXML
    public void handleDeleteButtonAction(ActionEvent event) {
        Map.Entry<Character, String> entry = table_rules.getSelectionModel().getSelectedItem();
        rules.removeRule(entry.getKey());
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        rules = new Rules();

        canvas3D = new Logo3DCanvas(30, 90);

        setAllListeners();

        // set new scene for 3d output
        Stage graphicStage = new Stage();

        root = new AnchorPane();
        root.getChildren().add(new ScrollPane());
        scene3d = null;

        scene3d = new Scene(root, 600, 400);

        graphicStage.setTitle("3D Graphic");
        graphicStage.setScene(scene3d);
        graphicStage.show();

        TurtleState3D state = new TurtleState3D();

        final PhongMaterial redMaterial = new PhongMaterial();
        redMaterial.setDiffuseColor(Color.DARKRED);
        redMaterial.setSpecularColor(Color.RED);

        final PhongMaterial blueMaterial = new PhongMaterial();
        blueMaterial.setDiffuseColor(Color.DARKBLUE);
        blueMaterial.setSpecularColor(Color.BLUE);

        Cube cylinder = new Cube(50, Color.RED, Color.BLUE, Color.YELLOW, Color.AQUA, Color.BLUEVIOLET, Color.WHITE, 0);

        root.getChildren().add(cylinder);

        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setTranslateZ(-1000);
        camera.setNearClip(0.1);
        camera.setFarClip(10000.0);
        scene3d.setCamera(camera);

        Slider sliderAlpha = new Slider();
        sliderAlpha.setMin(0);
        sliderAlpha.setMax(7);
        sliderAlpha.valueProperty().addListener((observable, oldValue, newValue) -> {
        });

        Slider sliderBeta = new Slider();
        sliderBeta.setMin(0);
        sliderBeta.setMax(7);
        sliderBeta.valueProperty().addListener((observable, oldValue, newValue) -> {
        });

        sliderBeta.setLayoutY(50);
        Stage sliderStage = new Stage();
        sliderStage.setTitle("Sliders");

        AnchorPane sliderPane = new AnchorPane();
        sliderPane.getChildren().addAll(sliderAlpha, sliderBeta);
        Scene sliderScene = new Scene(sliderPane, 100, 200);

        sliderStage.setScene(sliderScene);
        sliderStage.show();

        // Fractal plant, Angle = 25
        rules.addRule('X', "F-[[X]+X]+F[+FX]-X");
        rules.addRule('F', "FF");

        //canvas = new LogoCanvas(deltaAngle, factor);
        //scrollPane.setContent(canvas);
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

    private void setAllListeners() {
        table_column_A.setCellValueFactory((TableColumn.CellDataFeatures<Map.Entry<Character, String>, Character> param) -> {
            return new SimpleObjectProperty(param.getValue().getKey());
        });

        table_column_B.setCellValueFactory((TableColumn.CellDataFeatures<Map.Entry<Character, String>, String> param) -> {
            return new SimpleObjectProperty(param.getValue().getValue());
        });

        ObservableList<Map.Entry<Character, String>> list = FXCollections.observableArrayList(rules.getRules().entrySet());
        table_rules.setItems(list);

        slider_angle.valueProperty().addListener((observable, oldValue, newValue) -> {
//            ((LogoCanvas) canvas).setAngle((double) newValue);
//            ((LogoCanvas) canvas).drawString(tf_input.getText());
        });

        slider_factor.valueProperty().addListener((observable, oldValue, newValue) -> {
            factor = (double) newValue;
//            ((LogoCanvas) canvas).setLength((double) newValue);
//            ((LogoCanvas) canvas).drawString(tf_input.getText());
        });

        slider_rotation.valueProperty().addListener((observable, oldValue, newValue) -> {
//            ((LogoCanvas) canvas).setRotation((double) newValue);
//            ((LogoCanvas) canvas).drawString(tf_input.getText());
        });

        tf_input.textProperty().addListener((observable, oldValue, newValue) -> {
            //((LogoCanvas) canvas).drawString(tf_input.getText());
            Group g = canvas3D.drawString(tf_input.getText());
            root.getChildren().add(g);

        });

        // add Listener to
        MapChangeListener listener = (MapChangeListener) (MapChangeListener.Change change) -> {
            ObservableList<Map.Entry<Character, String>> llist = FXCollections.observableArrayList(rules.getRules().entrySet());
            table_rules.setItems(llist);
        };

        rules.addListener(listener);

    }

}
