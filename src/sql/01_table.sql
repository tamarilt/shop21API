CREATE EXTENSION IF NOT EXISTS "pgcrypto";

-- Адреса
CREATE TABLE IF NOT EXISTS addresses (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    country VARCHAR(15) NOT NULL,
    city VARCHAR(15) NOT NULL,
    street VARCHAR(15) NOT NULL
);

-- Изображения товаров
CREATE TABLE IF NOT EXISTS images (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    image BYTEA NOT NULL
);

-- Поставщики
CREATE TABLE IF NOT EXISTS supplier (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(31) NOT NULL,
    address_id UUID NOT NULL,
    phone_number VARCHAR(15) NOT NULL,
    CONSTRAINT supplier_fk_address_id FOREIGN KEY (address_id) REFERENCES addresses(id)
);

-- Клиент
CREATE TABLE IF NOT EXISTS client (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    client_name VARCHAR(15) NOT NULL,
    client_surname VARCHAR(15),
    birthday TIMESTAMP WITH TIME ZONE NOT NULL,
    gender VARCHAR(10) NOT NULL CHECK (gender IN ('male', 'female')),
    registration_date TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    address_id UUID NOT NULL,
    CONSTRAINT client_fk_address_id_client FOREIGN KEY (address_id) REFERENCES addresses(id)
);

-- Товар
CREATE TABLE IF NOT EXISTS product (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(31) NOT NULL,
    category VARCHAR(15) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    available_stock INT NOT NULL, -- число закупленных экземпляров товара
    last_update_date TIMESTAMP WITH TIME ZONE DEFAULT NOW(), -- число последней закупки
    supplier_id UUID NOT NULL,
    image_id UUID,
    CONSTRAINT product_fk_supplier_id FOREIGN KEY (supplier_id) REFERENCES supplier(id) ON DELETE CASCADE,
    CONSTRAINT product_fk_image_id FOREIGN KEY (image_id) REFERENCES images(id) ON DELETE SET NULL
);

