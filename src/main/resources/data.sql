-- Insert users into app_user
INSERT INTO app_user (name, created_at, email, password, roles)
VALUES
    ('Admin User 1', '2024-11-29T11:00:00', 'admin1@example.com', 'hashedpassword1', 'ADMIN'),
    ('Admin User 2', '2024-11-29T11:00:00', 'admin2@example.com', 'hashedpassword2', 'ADMIN'),
    ('Driver User 1', '2024-11-29T11:00:00', 'driver1@example.com', 'hashedpassword3', 'DRIVER'),
    ('Driver User 2', '2024-11-29T11:00:00', 'driver2@example.com', 'hashedpassword4', 'DRIVER'),
    ('Rider User 1', '2024-11-29T11:00:00', 'rider1@example.com', 'hashedpassword5', 'RIDER'),
    ('Rider User 2', '2024-11-29T11:00:00', 'rider2@example.com', 'hashedpassword6', 'RIDER'),
    ('Rider User 3', '2024-11-29T11:00:00', 'rider3@example.com', 'hashedpassword7', 'RIDER'),
    ('Rider User 4', '2024-11-29T11:00:00', 'rider4@example.com', 'hashedpassword8', 'RIDER'),
    ('Rider User 5', '2024-11-29T11:00:00', 'rider5@example.com', 'hashedpassword9', 'RIDER');
