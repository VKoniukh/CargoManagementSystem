package ua.koniukh.cargomanagementsystem.exeptions;

import java.time.LocalDate;

public class NoSuchOrderExeption extends Exception {

    LocalDate localDate;

    public LocalDate getLocalDate() {
        return localDate;
    }

    public NoSuchOrderExeption(String message, LocalDate localDate) {
        super(message + localDate.toString());
        this.localDate = localDate;
    }
}
