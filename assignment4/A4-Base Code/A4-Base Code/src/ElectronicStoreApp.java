import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.FXCollections;
import javafx.event.*;
import javafx.scene.input.MouseEvent;

public class ElectronicStoreApp extends Application {

    int lastSelectedIndex = -1;
    public void start(Stage primaryStage) {
        Pane  aPane = new Pane();


        ElectronicStore model;
        model =  ElectronicStore.createStore();
        ElectronicStoreView view = new ElectronicStoreView();
        view.update(model, view.getStockList().getSelectionModel().getSelectedIndex());


        view.getAddButton().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                if (lastSelectedIndex >= 0) {
                    model.addTocart(lastSelectedIndex);
                    view.getCompleteButton().setDisable(false);
                }
                view.update(model, lastSelectedIndex);
                view.getStockList().getSelectionModel().select(lastSelectedIndex);
            }
        });


        view.getRemoveButton().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                model.removeFromCart(view.getCartList().getSelectionModel().getSelectedItems().toString());
                view.getRemoveButton().setDisable(true);
                if (model.getCartlist().length <= 0){
                    view.getCompleteButton().setDisable(true);
                }
                view.update(model, view.getCartList().getSelectionModel().getSelectedIndex());

            }
        });

        view.getCompleteButton().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                 model.setSales(1);
                 model.setRevenue(model.getTotalCart());
                 model.setSale(String.valueOf(String.format("%.2f", model.getRevenue()/model.getSales())));
                 model.deleteCartList();
                 view.getCompleteButton().setDisable(true);
                 view.update(model, -1);
            }
        });

        view.getResetButton().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                model.reset();
                view.update(model, -1);
            }
        });


        view.getStockList().setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                int selectedIndex = view.getStockList().getSelectionModel().getSelectedIndex();
                lastSelectedIndex = view.getStockList().getSelectionModel().getSelectedIndex();
                view.update(model, selectedIndex);
                view.getStockList().getSelectionModel().select(selectedIndex);

            }
        });


        view.getCartList().setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                int selectedIndex = view.getCartList().getSelectionModel().getSelectedIndex();
                view.getRemoveButton().setDisable(false);
                view.update(model, selectedIndex);
                view.getCartList().getSelectionModel().select(selectedIndex);

            }
        });







        aPane.getChildren().add(view);

        primaryStage.setTitle("Electronic Store Application - " + model.getName());
        primaryStage.setResizable(false);
        primaryStage.setWidth(800);
        primaryStage.setHeight(400);
        primaryStage.setScene(new Scene(aPane));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}



