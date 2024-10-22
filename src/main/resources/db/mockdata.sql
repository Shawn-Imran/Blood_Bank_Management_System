INSERT INTO users (username, password, email, phone, user_type, blood_group)
VALUES
    ('john_doe', 'password123', 'john.doe@example.com', '123-456-7890', 'admin', 'A+'),
    ('jane_smith', 'password456', 'jane.smith@example.com', '234-567-8901', 'receiver', 'B+'),
    ('alice_jones', 'password789', 'alice.jones@example.com', '345-678-9012', 'donor', 'AB-'),
    ('bob_brown', 'password012', 'bob.brown@example.com', '456-789-0123', 'donor', 'O+'),
    ('charlie_black', 'password345', 'charlie.black@example.com', '567-890-1234', 'donor', 'A-');


-- Insert some blood store data
INSERT INTO blood_store (blood_group, quantity)
VALUES
    ('A+', 100),
    ('A-', 100),
    ('B+', 100),
    ('B-', 100),
    ('AB+', 100),
    ('AB-', 100),
    ('O+', 100),
    ('O-', 100);