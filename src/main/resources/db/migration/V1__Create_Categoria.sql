CREATE TABLE IF NOT EXISTS categoria (
  id INT AUTO_INCREMENT PRIMARY KEY,
  categoria VARCHAR(45) NOT NULL
);

INSERT INTO categoria (ID, categoria) VALUES (1, "Bicicleta");
INSERT INTO categoria (ID, categoria) VALUES (2, "Ciclomotor");
INSERT INTO categoria (ID, categoria) VALUES (3, "Motoneta");
INSERT INTO categoria (ID, categoria) VALUES (4, "Motocicleta");
INSERT INTO categoria (ID, categoria) VALUES (5, "Triciclo");
INSERT INTO categoria (ID, categoria) VALUES (6, "Quadriciclo");
INSERT INTO categoria (ID, categoria) VALUES (7, "Automóvel");
INSERT INTO categoria (ID, categoria) VALUES (8, "Microônibus");
INSERT INTO categoria (ID, categoria) VALUES (9, "Ônibus");
INSERT INTO categoria (ID, categoria) VALUES (10, "Reboque ou semi-reboque");