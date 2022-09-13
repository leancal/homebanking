package com.mindhub.homebanking.DTO;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class PdfDTO {

    private LocalDateTime dateFrom;

    private LocalDateTime dateTo;

    private String accountNumber;

    public PdfDTO(LocalDateTime dateFrom, LocalDateTime dateTo, String accountNumber) {
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.accountNumber = accountNumber;
    }

    public LocalDateTime getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(LocalDateTime dateFrom) {
        this.dateFrom = dateFrom;
    }

    public LocalDateTime getDateTo() {
        return dateTo;
    }

    public void setDateTo(LocalDateTime dateTo) {
        this.dateTo = dateTo;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }


}
