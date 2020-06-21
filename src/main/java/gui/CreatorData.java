package gui;

import model.*;

import java.util.ArrayList;
import java.util.List;

public class CreatorData {
    // general
    private Funeral funeral = new Funeral();
    // creator page 2 - form data
    private boolean standardCoffinSelected = true;
    private boolean premiumCoffinSelected;
    private List<String> selectedDecorations = new ArrayList<>();
    private String deadmanName;
    private String deadmanSurname;
    private List<FuneralCreator2.TableData> selectedSets = new ArrayList<>();
    // creator page 3 - form data
    private Cemetery selectedCemetery;

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

    public Funeral getFuneral() {
        return funeral;
    }

    public void setFuneral(Funeral funeral) {
        this.funeral = funeral;
    }

    public Cemetery getSelectedCemetery() {
        return selectedCemetery;
    }

    public void setSelectedCemetery(Cemetery selectedCemetery) {
        this.selectedCemetery = selectedCemetery;
    }
}
