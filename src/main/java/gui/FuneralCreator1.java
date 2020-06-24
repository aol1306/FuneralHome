package gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import model.Customer;

import java.io.IOException;
import java.time.LocalDate;


public class FuneralCreator1 extends FuneralCreatorBase {

    @FXML
    private Button backButton;

    @FXML
    private Button nextButton;

    @FXML
    private TextField clientName;

    @FXML
    private TextField clientSurname;

    @FXML
    private TextField clientPhoneNumber;

    @FXML
    private DatePicker datePicker;

    public void initialize() {
        backButton.setOnAction(e -> {
            // go to splash
            try {
                var pane = (AnchorPane) FXMLLoader.load(getClass().getResource("/splash.fxml"));
                rootPane.getChildren().setAll(pane);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        nextButton.setOnAction(e -> {
            saveData();
            setView("/funeralcreator2.fxml");
        });

        // disable past on date picker
        LocalDate today = LocalDate.now();
        datePicker.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.compareTo(today) < 0);
            }
        });
    }

    private void saveData() {
        var customer = new Customer();
        customer.setName(clientName.getText());
        customer.setSurname(clientSurname.getText());
        customer.setPhoneNumber(clientPhoneNumber.getText());
        this.creatorData.getFuneral().setFuneralDate(datePicker.getValue());
        this.creatorData.getFuneral().setCustomer(customer);
    }

    public void initData(CreatorData data) {
        super.initData(data);
        // restore data
        this.clientName.setText(this.creatorData.getFuneral().getCustomer().getName());
        this.clientSurname.setText(this.creatorData.getFuneral().getCustomer().getSurname());
        this.clientPhoneNumber.setText(this.creatorData.getFuneral().getCustomer().getPhoneNumber());
        this.datePicker.setValue(this.creatorData.getFuneral().getFuneralDate());
    }
}
