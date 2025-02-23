package com.mycompany.booktrack;

import javafx.beans.property.*;
import java.time.LocalDate;

public class Loan extends LoanBase {

    // Constructor that calls the base class constructor
    public Loan(int id, int bookId, int userId, LocalDate loanDate, LocalDate returnDate, boolean isReturned) {
        super(id, bookId, userId, loanDate, returnDate, isReturned);
    }

    // Implement the abstract method from LoanBase
    @Override
    public void updateStatus() {
        // Logic for updating loan status (e.g., mark as returned if the return date has passed)
        if (getReturnDate().isBefore(LocalDate.now()) && !isReturned()) {
            setReturned(true);
        }
    }

    // Property Getters - Inherited from LoanBase
    @Override
    public IntegerProperty getIdProperty() {
        return super.getIdProperty(); // Calls the getter from LoanBase
    }

    @Override
    public IntegerProperty getBookIdProperty() {
        return super.getBookIdProperty();
    }

    @Override
    public IntegerProperty getUserIdProperty() {
        return super.getUserIdProperty();
    }

    @Override
    public ObjectProperty<LocalDate> getLoanDateProperty() {
        return super.getLoanDateProperty();
    }

    @Override
    public ObjectProperty<LocalDate> getReturnDateProperty() {
        return super.getReturnDateProperty();
    }

    @Override
    public BooleanProperty getReturnedProperty() {
        return super.getReturnedProperty();
    }

    // Value Getters and Setters - Inherited from LoanBase
    @Override
    public int getId() {
        return super.getId();
    }

    @Override
    public void setId(int id) {
        super.setId(id);
    }

    @Override
    public int getBookId() {
        return super.getBookId();
    }

    @Override
    public void setBookId(int bookId) {
        super.setBookId(bookId);
    }

    @Override
    public int getUserId() {
        return super.getUserId();
    }

    @Override
    public void setUserId(int userId) {
        super.setUserId(userId);
    }

    @Override
    public LocalDate getLoanDate() {
        return super.getLoanDate();
    }

    @Override
    public void setLoanDate(LocalDate loanDate) {
        super.setLoanDate(loanDate);
    }

    @Override
    public LocalDate getReturnDate() {
        return super.getReturnDate();
    }

    @Override
    public void setReturnDate(LocalDate returnDate) {
        super.setReturnDate(returnDate);
    }

    @Override
    public boolean isReturned() {
        return super.isReturned();
    }

    @Override
    public void setReturned(boolean returned) {
        super.setReturned(returned);
    }

    // Override toString() to reflect the LoanBase class
    @Override
    public String toString() {
        return "Loan [id=" + getId() + ", bookId=" + getBookId() + ", userId=" + getUserId() +
               ", loanDate=" + getLoanDate() + ", returnDate=" + getReturnDate() +
               ", isReturned=" + isReturned() + "]";
    }
}
