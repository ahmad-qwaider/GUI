import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.collections.FXCollections;
public class ElectronicStoreView extends Pane {

    private ListView<Product>       stockList, popularList;
    private ListView<String> cartList;
    private TextField revenueField, salesField, saleField;
    private Button addButton, removeButton, resetButton, completeButton;
    Label label3;

    public Button getAddButton() {return addButton;}

    public Button getCompleteButton() {return completeButton;}

    public Button getRemoveButton() {return removeButton;}

    public Button getResetButton() {return resetButton;}

    public ListView<Product> getStockList() {return stockList;}

    public ListView<String> getCartList() {return cartList;}


    public ElectronicStoreView() {
        // Create the labels
        Label label1 = new Label("Store Summary:");
        label1.relocate(30, 10);
        Label label2 = new Label("Store Stock:");
        label2.relocate(300, 10);
        label3 = new Label("Current Cart  ($0.00):");
        label3.relocate(590, 10);
        Label label4 = new Label("# Sales:");
        label4.relocate(25, 40);
        Label label5 = new Label("Revenue:");
        label5.relocate(17,70);
        Label label6 = new Label("$ / Sale:");
        label6.relocate(23,100);
        Label label7 = new Label("Most Popular Items:");
        label7.relocate(30,130);

        // Create lists
        popularList = new ListView<Product>();
        popularList.setPrefSize(145,140);
        popularList.relocate(10,150);

        stockList = new ListView<Product>();
        stockList.setPrefSize(250,250);
        stockList.relocate(210,40);

        cartList = new ListView<String>();
        cartList.setPrefSize(250,250);
        cartList.relocate(515,40);

        // Create Textfields
        salesField = new TextField();
        salesField.setPrefSize(75,15);
        salesField.relocate(70, 37);
        salesField.setEditable(false);

        revenueField = new TextField();
        revenueField.setPrefSize(75,15);
        revenueField.relocate(70,68);
        revenueField.setEditable(false);

        saleField = new TextField();
        saleField.setPrefSize(75,15);
        saleField.relocate(70,99);
        saleField.setEditable(false);

        // Create buttons
        addButton = new Button("Add to cart");
        addButton.relocate(270, 300);
        addButton.setPrefSize(120,40);

        resetButton = new Button("Reset Store");
        resetButton.relocate(20, 300);
        resetButton.setPrefSize(120,40);

        removeButton = new Button("Remove from cart");
        removeButton.relocate(516, 300);
        removeButton.setPrefSize(120,40);

        completeButton = new Button("Complete Sale");
        completeButton.relocate(645, 300);
        completeButton.setPrefSize(120,40);

        // disabling buttons
        addButton.setDisable(true);
        removeButton.setDisable(true);
        completeButton.setDisable(true);

        // Set text
        salesField.setText("0");
        revenueField.setText("0.00");
        saleField.setText("N/A");


        getChildren().addAll(label1, label2, label3, label4, label5, label6,label7, popularList, stockList,cartList,salesField,saleField,revenueField, addButton, resetButton,removeButton,completeButton);

    }
    public void update(ElectronicStore model, int selectedProduct) {
        Product[] productlist = model.getStock();
        stockList.getItems().setAll(productlist);
        addButton.setDisable(selectedProduct <0);
        cartList.getItems().setAll(model.getCartlist());
        label3.setText("Current Cart  ($"+String.format("%.2f", model.getTotalCart())+"):");
        salesField.setText(String.format("%d", model.getSales()));
        revenueField.setText(String.format("%.2f", model.getRevenue()));
        saleField.setText(model.getSale());
        popularList.getItems().setAll(model.mostPopular());
    }
}
