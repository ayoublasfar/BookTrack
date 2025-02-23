-- Create the database
CREATE DATABASE IF NOT EXISTS BookTrack;
USE BookTrack;

-- Create the Users table
CREATE TABLE IF NOT EXISTS Users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    phoneNumber VARCHAR(20)
);

-- Create the Books table
CREATE TABLE IF NOT EXISTS Books (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    genre VARCHAR(100),
    isAvailable BOOLEAN DEFAULT TRUE
);

-- Create the Loans table
CREATE TABLE IF NOT EXISTS Loans (
    id INT AUTO_INCREMENT PRIMARY KEY,
    bookId INT,
    userId INT,
    loanDate DATE,
    returnDate DATE,
    isReturned BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (bookId) REFERENCES Books(id) ON DELETE CASCADE,
    FOREIGN KEY (userId) REFERENCES Users(id) ON DELETE CASCADE
);

-- Sample Data for Users Table
INSERT INTO Users (name, email, phoneNumber) 
VALUES 
    ('John Doe', 'john.doe@example.com', '1234567890'),
    ('Jane Smith', 'jane.smith@example.com', '9876543210');

-- Sample Data for Books Table
INSERT INTO Books (title, author, genre, isAvailable) 
VALUES 
    ('The Great Gatsby', 'F. Scott Fitzgerald', 'Fiction', TRUE),
    ('Moby Dick', 'Herman Melville', 'Adventure', TRUE),
    ('1984', 'George Orwell', 'Dystopian', FALSE);

-- Sample Data for Loans Table
INSERT INTO Loans (bookId, userId, loanDate, returnDate, isReturned) 
VALUES 
    (1, 1, '2025-01-01', '2025-01-15', TRUE),
    (2, 2, '2025-01-05', '2025-01-20', FALSE);
