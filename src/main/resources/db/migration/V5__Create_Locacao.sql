CREATE TABLE IF NOT EXISTS locacao (
  id INT AUTO_INCREMENT PRIMARY KEY,
  veiculo_id int NOT NULL,
  cliente_id int NOT NULL,
  data_locacao DATETIME not null,
  data_devolucao DATETIME not null,
  valor DECIMAL(19,2),
  foreign key (veiculo_id) references veiculo(id),
  foreign key (cliente_id) references cliente(id)
);
