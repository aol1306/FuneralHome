package gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

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
            setView("/splash.fxml");
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
        this.creatorData.setClientName(clientName.getText());
        this.creatorData.setClientSurname(clientSurname.getText());
        this.creatorData.setClientPhoneNumber(clientPhoneNumber.getText());
        this.creatorData.setFuneralDate(datePicker.getValue());

    }

    public void initData(CreatorData data) {
        super.initData(data);
        // restore data
        this.clientName.setText(this.creatorData.getClientName());
        this.clientSurname.setText(this.creatorData.getClientSurname());
        this.clientPhoneNumber.setText(this.creatorData.getClientPhoneNumber());
        this.datePicker.setValue(this.creatorData.getFuneralDate());
    }
}
