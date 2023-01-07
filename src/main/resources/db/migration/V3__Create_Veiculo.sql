CREATE TABLE IF NOT EXISTS veiculo (
  id INT AUTO_INCREMENT PRIMARY KEY,
  categoria_id int NOT NULL,
  montadora_id int NOT NULL,
  modelo VARCHAR(45) NOT NULL,
  ano_fabricacao int NOT NULL,
  ano_modelo int NOT NULL,
  valor_fipe DECIMAL(19,2) NOT NULL,
  status_veiculo VARCHAR(15) NOT NULL,
  foreign key (categoria_id) references categoria(id),
  foreign key (montadora_id) references montadora(id)
);

INSERT INTO veiculo VALUES (1, 1, 8, "Bicicleta Elétrica",2022,2022,32000.00,"Disponivel");
INSERT INTO veiculo VALUES (2, 2, 1, "Ciclomotor HONDA",2019,2020,25000.45,"Disponivel");
INSERT INTO veiculo VALUES (3, 3, 6, "Motoneta HONDA",2019,2020,19500.00,"Disponivel");
INSERT INTO veiculo VALUES (4, 4, 1, "HONDA NX/FALCON",2008,2008,17000.99,"Disponivel");
INSERT INTO veiculo VALUES (5, 5, 7, "Triciculo monster",2015,2016,32500.44,"Disponivel");
INSERT INTO veiculo VALUES (6, 7, 1, "Honda CIVIC",2019,2020,75590.00,"Disponivel");
INSERT INTO veiculo VALUES (7, 7, 1, "Honda ACCORD",2021,2022,95780.44,"Disponivel");
INSERT INTO veiculo VALUES (8, 7, 2, "HILUX",2021,2022,160000.00,"Disponivel");
INSERT INTO veiculo VALUES (9, 7, 2, "HYARIS",2021,2022,122000.00,"Disponivel");
INSERT INTO veiculo VALUES (10, 7, 3, "GOL G6",2019,2020,49500.00,"Disponivel");
INSERT INTO veiculo VALUES (11, 7, 3, "GOL G5",2012,2013,40000.00,"Disponivel");
INSERT INTO veiculo VALUES (12, 7, 3, "AMAROK",2022,2023,185000.00,"Disponivel");
INSERT INTO veiculo VALUES (13, 7, 3, "JETTA",2018,2019,87500.11,"Disponivel");
INSERT INTO veiculo VALUES (14, 7, 4, "I30 G1",2012,2012,43500.19,"Disponivel");
INSERT INTO veiculo VALUES (15, 7, 4, "I30 G2",2016,2016,74140.19,"Disponivel");
INSERT INTO veiculo VALUES (16, 7, 4, "Veloster",2016,2016,82500.00,"Disponivel");
INSERT INTO veiculo VALUES (17, 7, 5, "Fusion",2015,2016,85000.00,"Disponivel");
INSERT INTO veiculo VALUES (18, 7, 5, "KA",2019,2019,62000.00,"Disponivel");
INSERT INTO veiculo VALUES (19, 7, 6, "IX35",2019,2019,145000.00,"Disponivel");
INSERT INTO veiculo VALUES (20, 7, 7, "GLC",2019,2019,175000.00,"Disponivel");
INSERT INTO veiculo VALUES (21, 7, 7, "Classe C",2021,2022,185000.00,"Disponivel");
INSERT INTO veiculo VALUES (22, 7, 8, "Model S",2022,2022,230000.00,"Disponivel");
INSERT INTO veiculo VALUES (23, 7, 8, "Model 3",2022,2022,245000.00,"Disponivel");
INSERT INTO veiculo VALUES (24, 7, 8, "Model X",2022,2022,275000.00,"Disponivel");
INSERT INTO veiculo VALUES (25, 8, 7, "Micro Onibus 25 L",2022,2022,175000.00,"Disponivel");
INSERT INTO veiculo VALUES (26, 9, 7, "Onibus 55 L",2022,2023,210000.00,"Disponivel");
