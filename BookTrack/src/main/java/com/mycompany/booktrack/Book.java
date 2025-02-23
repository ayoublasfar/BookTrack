package com.mycompany.booktrack;

import javafx.beans.property.*;

public class Book {

    private final IntegerProperty id;
    private final StringProperty title;
    private final StringProperty author;
    private final StringProperty genre;
    private final BooleanProperty isAvailable;

    // Constructor
    public Book(int id, String title, String author, String genre, boolean isAvailable) {
        this.id = new SimpleIntegerProperty(id);
        this.title = new SimpleStringProperty(title);
        this.author = new SimpleStringProperty(author);
        this.genre = new SimpleStringProperty(genre);
        this.isAvailable = new SimpleBooleanProperty(isAvailable);
    }

    // Property Getters
    public IntegerProperty getIdProperty() {
        return id;
    }

    public StringProperty getTitleProperty() {
        return title;
    }

    public StringProperty getAuthorProperty() {
        return author;
    }

    public StringProperty getGenreProperty() {
        return genre;
    }

    public BooleanProperty getAvailableProperty() {
        return isAvailable;
    }

    // Value Getters and Setters
    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getTitle() {
        return title.get();
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public String getAuthor() {
        return author.get();
    }

    public void setAuthor(String author) {
        this.author.set(author);
    }

    public String getGenre() {
        return genre.get();
    }

    public void setGenre(String genre) {
        this.genre.set(genre);
    }

    public boolean isAvailable() {
        return isAvailable.get();
    }

    public void setAvailable(boolean available) {
        this.isAvailable.set(available);
    }

    @Override
    public String toString() {
        return "Book [id=" + id.get() + ", title=" + title.get() + ", author=" + author.get() +
               ", genre=" + genre.get() + ", isAvailable=" + isAvailable.get() + "]";
    }
}
