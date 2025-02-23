package com.mycompany.booktrack;

import javafx.beans.property.*;
import java.time.LocalDate;

public abstract class LoanBase {

    private final IntegerProperty id;
    private final IntegerProperty bookId;
    private final IntegerProperty userId;
    private final ObjectProperty<LocalDate> loanDate;
    private final ObjectProperty<LocalDate> returnDate;
    private final BooleanProperty isReturned;

    // Constructor
    public LoanBase(int id, int bookId, int userId, LocalDate loanDate, LocalDate returnDate, boolean isReturned) {
        this.id = new SimpleIntegerProperty(id);
        this.bookId = new SimpleIntegerProperty(bookId);
        this.userId = new SimpleIntegerProperty(userId);
        this.loanDate = new SimpleObjectProperty<>(loanDate);
        this.returnDate = new SimpleObjectProperty<>(returnDate);
        this.isReturned = new SimpleBooleanProperty(isReturned);
    }

    // Abstract method that subclasses must implement
    public abstract void updateStatus();

    // Property Getters
    public IntegerProperty getIdProperty() {
        return id;
    }

    public IntegerProperty getBookIdProperty() {
        return bookId;
    }

    public IntegerProperty getUserIdProperty() {
        return userId;
    }

    public ObjectProperty<LocalDate> getLoanDateProperty() {
        return loanDate;
    }

    public ObjectProperty<LocalDate> getReturnDateProperty() {
        return returnDate;
    }

    public BooleanProperty getReturnedProperty() {
        return isReturned;
    }

    // Value Getters and Setters
    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public int getBookId() {
        return bookId.get();
    }

    public void setBookId(int bookId) {
        this.bookId.set(bookId);
    }

    public int getUserId() {
        return userId.get();
    }

    public void setUserId(int userId) {
        this.userId.set(userId);
    }

    public LocalDate getLoanDate() {
        return loanDate.get();
    }

    public void setLoanDate(LocalDate loanDate) {
        this.loanDate.set(loanDate);
    }

    public LocalDate getReturnDate() {
        return returnDate.get();
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate.set(returnDate);
    }

    public boolean isReturned() {
        return isReturned.get();
    }

    public void setReturned(boolean returned) {
        this.isReturned.set(returned);
    }

    @Override
    public String toString() {
        return "Loan [id=" + id.get() + ", bookId=" + bookId.get() + ", userId=" + userId.get() +
               ", loanDate=" + loanDate.get() + ", returnDate=" + returnDate.get() +
               ", isReturned=" + isReturned.get() + "]";
    }
}