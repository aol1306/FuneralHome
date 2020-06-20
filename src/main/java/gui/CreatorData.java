package gui;

import java.time.LocalDate;

public class CreatorData {
    private String clientName;
    private String clientSurname;
    private String clientPhoneNumber;
    private LocalDate funeralDate;

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
}