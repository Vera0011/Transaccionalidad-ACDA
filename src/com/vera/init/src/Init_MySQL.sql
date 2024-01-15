USE ut2;

CREATE TABLE IF NOT EXISTS Usuarios(
    id INTEGER AUTO_INCREMENT NOT NULL PRIMARY KEY,
    full_name VARCHAR(255) NOT NULL,
    user VARCHAR(255) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    creation_date DATETIME NOT NULL,
    modification_date DATETIME
);

DROP TRIGGER IF EXISTS check_duplicate_users;
DROP TRIGGER IF EXISTS add_on_update_now;

INSERT IGNORE INTO Usuarios(full_name, user, email, password, creation_date) VALUES
('John Doe', 'john_doe', 'john.doe@example.com', 'password1', '2022-01-01'),
('Jane Smith', 'jane_smith', 'jane.smith@example.com', 'password2', '2022-01-03'),
('Bob Johnson', 'bob_johnson', 'bob.johnson@example.com', 'password3', '2022-01-05'),
('Alice Williams', 'alice_williams', 'alice.williams@example.com', 'password4', '2022-01-07'),
('Charlie Brown', 'charlie_brown', 'charlie.brown@example.com', 'password5', '2022-01-09'),
('Eva Davis', 'eva_davis', 'eva.davis@example.com', 'password6', '2022-01-11'),
('Frank Miller', 'frank_miller', 'frank.miller@example.com', 'password7', '2022-01-13'),
('Grace Taylor', 'grace_taylor', 'grace.taylor@example.com', 'password8', '2022-01-15'),
('Henry Wilson', 'henry_wilson', 'henry.wilson@example.com', 'password9', '2022-01-17'),
('Ivy Robinson', 'ivy_robinson', 'ivy.robinson@example.com', 'password10', '2022-01-19'),
('Jack Harris', 'jack_harris', 'jack.harris@example.com', 'password11', '2022-01-21'),
('Kelly Turner', 'kelly_turner', 'kelly.turner@example.com', 'password12', '2022-01-23'),
('Leo Adams', 'leo_adams', 'leo.adams@example.com', 'password13', '2022-01-25'),
('Mia Clark', 'mia_clark', 'mia.clark@example.com', 'password14', '2022-01-27'),
('Nathan White', 'nathan_white', 'nathan.white@example.com', 'password15', '2022-01-29'),
('Olivia Hall', 'olivia_hall', 'olivia.hall@example.com', 'password16', '2022-01-31'),
('Peter Martin', 'peter_martin', 'peter.martin@example.com', 'password17', '2022-02-02'),
('Quinn Adams', 'quinn_adams', 'quinn.adams@example.com', 'password18', '2022-02-04'),
('Rose Davis', 'rose_davis', 'rose.davis@example.com', 'password19', '2022-02-06'),
('Samuel White', 'samuel_white', 'samuel.white@example.com', 'password20', '2022-02-08');


/* Trigger if user is duplicate */
DELIMITER %%
CREATE TRIGGER check_duplicate_users BEFORE INSERT ON Usuarios FOR EACH ROW
BEGIN
    IF ((SELECT COUNT(*) FROM Usuarios U WHERE U.user = NEW.user) != 0) THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = "Usuario duplicado";
    END IF;

    SET NEW.creation_date = CURRENT_TIME;
END%%
DELIMITER ;

/* Trigger when user is added */
DELIMITER %%
CREATE TRIGGER add_on_update_now BEFORE UPDATE ON Usuarios FOR EACH ROW
BEGIN
    SET NEW.modification_date = CURRENT_TIME;
END%%
DELIMITER ;