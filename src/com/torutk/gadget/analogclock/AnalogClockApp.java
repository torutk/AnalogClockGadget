/*
 *  Copyright © 2014 TAKAHASHI,Toru
 */
package com.torutk.gadget.analogclock;

import java.util.Map;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * アナログ時計表示プログラム。
 * 
 * @author TAKAHASHI,Toru
 */
public class AnalogClockApp extends Application {
    private static final double INITIAL_WINDOW_SIZE = 200d;
    private static final double MAX_SCALE = 6d;
    private static final double MIN_SCALE = 0.32;

    private double dragStartX;
    private double dragStartY;
    private ContextMenu popup = new ContextMenu();
    private BooleanProperty alwaysOnTopProperty = new SimpleBooleanProperty(false);
    private Region root;
    private Stage stage;
    private ResourceBundle bundle;
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = initPrimaryStage(primaryStage);
        alwaysOnTopProperty.addListener(((observable, oldValue, newValue) -> stage.setAlwaysOnTop(newValue)));
        parseParameters();
        bundle = ResourceBundle.getBundle(getClass().getName());
        root = FXMLLoader.load(getClass().getResource("AnalogClockView.fxml"), bundle);
        Scene scene = new Scene(root, INITIAL_WINDOW_SIZE, INITIAL_WINDOW_SIZE, Color.TRANSPARENT);
        root.prefWidthProperty().bind(scene.widthProperty());
        root.prefHeightProperty().bind(scene.heightProperty());

        // マウスのドラッグ操作でウィンドウを移動
        scene.setOnMousePressed(e -> {
            dragStartX = e.getSceneX();
            dragStartY = e.getSceneY();
        });
        scene.setOnMouseDragged(e -> {
            stage.setX(e.getScreenX() - dragStartX);
            stage.setY(e.getScreenY() - dragStartY);
        });
        // 時計のサイズを変更する
        // マウスのホイール操作によるScrollEventを選別してウィンドウサイズを変更
        scene.setOnScroll(e -> {
            if (e.getTouchCount() != 0 || e.isInertia()) return;
            double zoomFactor = e.getDeltaY() > 0 ? 1.1 : 0.9;
            zoom(zoomFactor);
        });
        // タッチパネルのピンチ操作でウィンドウサイズを変更
        scene.setOnZoom(e -> {
            zoom(e.getZoomFactor());
        });

        // ポップアップメニュー        
        MenuItem exitItem = new MenuItem(bundle.getString("menu_exit"));
        exitItem.setOnAction(e -> Platform.exit());
        MenuItem zoomInItem = new MenuItem(bundle.getString("menu_zoomIn"));
        zoomInItem.setOnAction(e -> zoom(1.1));
        MenuItem zoomOutItem = new MenuItem(bundle.getString("menu_zoomOut"));
        zoomOutItem.setOnAction(e -> zoom(0.9));
        CheckMenuItem onTopItem = new CheckMenuItem(bundle.getString("menu_alwaysOnTop"));
        onTopItem.selectedProperty().bindBidirectional(alwaysOnTopProperty);

        popup.getItems().addAll(zoomInItem, zoomOutItem, onTopItem, exitItem);
        // コンテキストメニュー操作（OS依存）をしたときに、ポップアップメニュー表示
        // Windows OSでは、マウスの右クリック、touchパネルの長押しで発生
        root.setOnContextMenuRequested(e -> {
            popup.show(stage, e.getScreenX(), e.getScreenY());
        });
        
        stage.setScene(scene);
        stage.show();
    }

    private Stage initPrimaryStage(Stage primaryStage) {
        primaryStage.initStyle(StageStyle.UTILITY);
        primaryStage.setOpacity(0);
        primaryStage.setWidth(0);
        primaryStage.setHeight(0);
        primaryStage.setX(Double.MAX_VALUE);

        Stage secondaryStage = new Stage(StageStyle.TRANSPARENT);
        secondaryStage.initOwner(primaryStage);
        primaryStage.show();
        return secondaryStage;
    }

    private void zoom(double factor) {
        double scale = root.getScaleX() * factor;
        scale = Math.max(Math.min(scale, MAX_SCALE), MIN_SCALE);
        root.setScaleX(scale);
        root.setScaleY(scale);
        stage.setWidth(INITIAL_WINDOW_SIZE * scale);
        stage.setHeight(INITIAL_WINDOW_SIZE * scale);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    private void parseParameters() {
        Map<String, String> params = getParameters().getNamed();
        Platform.runLater(() -> zoom(Double.valueOf(params.getOrDefault("scale", "1.0"))));
        Platform.runLater(() -> stage.setX(Double.valueOf(params.getOrDefault("x", "0.0"))));
        Platform.runLater(() -> stage.setY(Double.valueOf(params.getOrDefault("y", "0.0"))));
        AnalogClockViewController.setTargetFramerate(Double.valueOf(params.getOrDefault("fps", "60.0")));
        alwaysOnTopProperty.setValue(Boolean.valueOf(params.getOrDefault("on-top", "false")));
    }

}
