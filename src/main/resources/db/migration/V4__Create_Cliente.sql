CREATE TABLE IF NOT EXISTS cliente (
  id INT AUTO_INCREMENT PRIMARY KEY,
  nome VARCHAR(45) NOT NULL,
  cpf VARCHAR(45) NOT NULL,
  endereco VARCHAR(100) NOT NULL,
  status_cliente VARCHAR(45) NOT NULL
);

insert into cliente values (1, "Rafael Henrique", "111.222.333-55", "Rua das dores, 154 - Betim/MG", "Ativo");
insert into cliente values (2, "João das Couves", "123.456.789-10", "Rua das árvores, 66 - Belo Horizonte/MG", "Ativo");
insert into cliente values (3, "Thiago das Pedras", "555.444.666-11", "Av. das Palmeiras, 99 - São Paulo/SP", "Ativo");
insert into cliente values (4, "Maria da Silva", "777.888.999-33", "Av. Paulinea, 147 - São Paulo/SP", "Ativo");
insert into cliente values (5, "Sebastião Moreira Salgado", "417.852.933-44", "Beco das Pedras, Sn - Ribeirão das Neves/MG", "Ativo");
insert into cliente values (6, "Dolores Margoth", "481.157.697-54", "Rua das Almas, 666 - João Monlevade/MG", "Ativo");
