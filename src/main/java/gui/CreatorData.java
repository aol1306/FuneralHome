package gui;

import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CreatorData {
    // creator page 1
    private String clientName;
    private String clientSurname;
    private String clientPhoneNumber;
    private LocalDate funeralDate;
    // creator page 2
    private boolean standardCoffinSelected = true;
    private boolean premiumCoffinSelected;
    private List<String> selectedDecorations = new ArrayList<>();
    private String deadmanName;
    private String deadmanSurname;
    private List<FuneralCreator2.TableData> selectedSets = new ArrayList<>();

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientSurname() {
        return clientSurname;
    }

    public void setClientSurname(String clientSurname) {
        this.clientSurname = clientSurname;
    }

    public String getClientPhoneNumber() {
        return clientPhoneNumber;
    }

    public void setClientPhoneNumber(String clientPhoneNumber) {
        this.clientPhoneNumber = clientPhoneNumber;
    }

    public LocalDate getFuneralDate() {
        return funeralDate;
    }

    public void setFuneralDate(LocalDate funeralDate) {
        this.funeralDate = funeralDate;
    }

    public boolean isStandardCoffinSelected() {
        return standardCoffinSelected;
    }

    public void setStandardCoffinSelected(boolean standardCoffinSelected) {
        this.standardCoffinSelected = standardCoffinSelected;
    }

    public boolean isPremiumCoffinSelected() {
        return premiumCoffinSelected;
    }

    public void setPremiumCoffinSelected(boolean premiumCoffinSelected) {
        this.premiumCoffinSelected = premiumCoffinSelected;
    }

    public List<String> getSelectedDecorations() {
        return selectedDecorations;
    }

    public void setSelectedDecorations(List<String> selectedDecorations) {
        this.selectedDecorations = selectedDecorations;
    }

    public String getDeadmanName() {
        return deadmanName;
    }

    public void setDeadmanName(String deadmanName) {
        this.deadmanName = deadmanName;
    }

    public String getDeadmanSurname() {
        return deadmanSurname;
    }

    public void setDeadmanSurname(String deadmanSurname) {
        this.deadmanSurname = deadmanSurname;
    }

    public List<FuneralCreator2.TableData> getSelectedSets() {
        return selectedSets;
    }

    public void setSelectedSets(List<FuneralCreator2.TableData> selectedSets) {
        this.selectedSets = selectedSets;
    }
}
