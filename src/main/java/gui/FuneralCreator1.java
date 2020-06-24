package gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import main.Helper;
import main.Main;
import model.Cemetery;
import model.Customer;
import model.GraveDigger;
import org.hibernate.Session;

import java.io.IOException;
import java.time.LocalDate;


public class FuneralCreator1 extends FuneralCreatorBase {
    @FXML
    public Label dateSuggestionLabel;

    @FXML
    private VBox vbox2;

    @FXML
    private VBox vbox1;

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
        HBox.setHgrow(vbox1, Priority.ALWAYS);
        HBox.setHgrow(vbox2, Priority.ALWAYS);
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

        datePicker.valueProperty().addListener((ov, oldValue, newValue) -> {
            if(!checkDate(newValue)) {
                dateSuggestionLabel.setText("Termin niedostępny. Proponowany: "+getNextAvailableDate());
            } else {
                dateSuggestionLabel.setText("");
            };
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

    /**
     * Check resources availability on date
     * @param localDate date
     * @return true if date is available, else false
     */
    public boolean checkDate(LocalDate localDate) {
        var ret = true;
        Session session = Main.sessionFactory.openSession();
        session.beginTransaction();
        var graveDiggers = Helper.selectAll(GraveDigger.class, session);
        if (graveDiggers.size() == 0) ret = false;
        for (var digger : graveDiggers) {
            if (!digger.isAvailable(localDate)) {
                ret = false;
                break;
            }
        }
        session.getTransaction().commit();
        session.close();
        return ret;
    }

    /**
     * Get next date, on which the funeral can happen.
     * @return next available date
     */
    public LocalDate getNextAvailableDate() {
        var date = LocalDate.now();
        while (true) {
            if (checkDate(date)) {
                break;
            } else {
                date = date.plusDays(1);
            }
        }
        return date;
    }
}
