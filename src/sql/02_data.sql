INSERT INTO addresses (country, city, street) VALUES 
('Russia', 'Moscow', 'Tverskaya 1'),
('Russia', 'SPb', 'Nevsky 10'),
('Russia', 'Kazan', 'Kremlin 5'),
('Belarus', 'Minsk', 'Lenin 15'),
('Ukraine', 'Kiev', 'Khreshchatyk 20'),
('Russia', 'Novosibirsk', 'Lenina 2'),
('Russia', 'Yekaterinburg', 'Lenina 10'),
('Russia', 'Rostov-on-Don', 'Sovetskaya 7'),
('Russia', 'Sochi', 'Kurortny 3');

INSERT INTO supplier (name, address_id, phone_number) VALUES 
('TechSupply Ltd', (SELECT id FROM addresses WHERE city = 'Moscow'), '+79991234567'),
('ClothingWorld', (SELECT id FROM addresses WHERE city = 'SPb'), '+79998765432'),
('BookStore Inc', (SELECT id FROM addresses WHERE city = 'Kazan'), '+79995555555'),
('FoodMart LLC', (SELECT id FROM addresses WHERE city = 'Minsk'), '+375291111111'),
('GadgetPro', (SELECT id FROM addresses WHERE city = 'Novosibirsk'), '+79990001111'),
('HomeGoods Co', (SELECT id FROM addresses WHERE city = 'Yekaterinburg'), '+79990002222'),
('StationeryPlus', (SELECT id FROM addresses WHERE city = 'Rostov-on-Don'), '+79990003333'),
('FreshFarms', (SELECT id FROM addresses WHERE city = 'Sochi'), '+79990004444');

INSERT INTO client (client_name, client_surname, birthday, gender, address_id) VALUES 
('Ivan', 'Petrov', '1990-05-15 10:30:00+03:00', 'male', (SELECT id FROM addresses WHERE city = 'Moscow')),
('Anna', 'Sidorova', '1985-08-22 14:15:30+03:00', 'female', (SELECT id FROM addresses WHERE city = 'SPb')),
('Dmitry', 'Kozlov', '1992-12-03 09:45:00+03:00', 'male', (SELECT id FROM addresses WHERE city = 'Kazan')),
('Elena', 'Volkova', '1988-03-17 16:20:15+03:00', 'female', (SELECT id FROM addresses WHERE city = 'Minsk')),
('Alex', 'Smith', '1995-07-09 12:00:00+03:00', 'male', (SELECT id FROM addresses WHERE city = 'Kiev'));

INSERT INTO product (name, category, price, available_stock, supplier_id) VALUES 
('iPhone 15', 'electronics', 89999.99, 50, (SELECT id FROM supplier WHERE name = 'TechSupply Ltd')),
('Samsung TV', 'electronics', 45000.00, 25, (SELECT id FROM supplier WHERE name = 'TechSupply Ltd')),
('MacBook Pro', 'electronics', 199999.99, 10, (SELECT id FROM supplier WHERE name = 'TechSupply Ltd')),
('Nike Sneakers', 'clothing', 8500.00, 100, (SELECT id FROM supplier WHERE name = 'ClothingWorld')),
('Levi Jeans', 'clothing', 6500.00, 75, (SELECT id FROM supplier WHERE name = 'ClothingWorld')),
('Adidas T-Shirt', 'clothing', 2500.00, 200, (SELECT id FROM supplier WHERE name = 'ClothingWorld')),
('War and Peace', 'books', 1200.00, 200, (SELECT id FROM supplier WHERE name = 'BookStore Inc')),
('Cooking Guide', 'books', 800.00, 150, (SELECT id FROM supplier WHERE name = 'BookStore Inc')),
('Python Manual', 'books', 1500.00, 80, (SELECT id FROM supplier WHERE name = 'BookStore Inc')),
('Organic Apples', 'food', 150.00, 500, (SELECT id FROM supplier WHERE name = 'FoodMart LLC')),
('Premium Coffee', 'food', 2500.00, 100, (SELECT id FROM supplier WHERE name = 'FoodMart LLC')),
('Green Tea', 'food', 350.00, 300, (SELECT id FROM supplier WHERE name = 'FoodMart LLC'));