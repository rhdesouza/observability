CREATE TABLE IF NOT EXISTS categoria (
  id INT AUTO_INCREMENT PRIMARY KEY,
  categoria VARCHAR(45) NOT NULL,
  valor_diaria_minima DECIMAL(19,2)
);

INSERT INTO categoria (ID, categoria, valor_diaria_minima) VALUES (1, "Bicicleta", "30.00");
INSERT INTO categoria (ID, categoria, valor_diaria_minima) VALUES (2, "Ciclomotor", "49.90");
INSERT INTO categoria (ID, categoria, valor_diaria_minima) VALUES (3, "Motoneta", "52.50");
INSERT INTO categoria (ID, categoria, valor_diaria_minima) VALUES (4, "Motocicleta", "60.00");
INSERT INTO categoria (ID, categoria, valor_diaria_minima) VALUES (5, "Triciclo", "95.00");
INSERT INTO categoria (ID, categoria, valor_diaria_minima) VALUES (6, "Quadriciclo", "120.00");
INSERT INTO categoria (ID, categoria, valor_diaria_minima) VALUES (7, "Automóvel", "155.00");
INSERT INTO categoria (ID, categoria, valor_diaria_minima) VALUES (8, "Microônibus", "450.00");
INSERT INTO categoria (ID, categoria, valor_diaria_minima) VALUES (9, "Ônibus", "750.00");
INSERT INTO categoria (ID, categoria, valor_diaria_minima) VALUES (10, "Reboque ou semi-reboque", "900.50");