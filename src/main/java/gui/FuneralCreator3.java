package gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.Helper;
import main.Main;
import model.Cemetery;

import javax.swing.*;
import java.util.List;

public class FuneralCreator3 extends FuneralCreatorBase {
    @FXML
    private Button backButton;

    @FXML
    private Button nextButton;

    @FXML
    private ImageView loadingImageView;

    @FXML
    private ComboBox<Cemetery> cemeteryComboBox;

    private ObservableList<Cemetery> cemeteryComboBoxList;

    private List<Cemetery> cemeteryList;

    public void initialize() {
        loadCemeteries();
        backButton.setOnAction(e -> {
            setView("/funeralcreator2.fxml");
        });
        nextButton.setOnAction(e -> {
            setView("/funeralcreator4.fxml");
        });
        cemeteryComboBoxList = FXCollections.observableArrayList();
        cemeteryComboBox.setItems(cemeteryComboBoxList);
    }

    public void initData(CreatorData data) {
        super.initData(data);
    }

    private void loadCemeteries() {
        var worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                var session = Main.sessionFactory.openSession();
                session.beginTransaction();
                cemeteryList = Helper.selectAll(Cemetery.class, session);
                session.getTransaction().commit();
                session.close();

                setProgress(1);
                return null;
            }
        };
        worker.addPropertyChangeListener(evt -> {
            if("progress".equals(evt.getPropertyName())) {
                loadingImageView.setVisible(false);
                cemeteryComboBoxList.addAll(cemeteryList);
            }
        });
        worker.execute();
    }
}