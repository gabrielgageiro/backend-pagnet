CREATE TABLE IF NOT EXISTS transacao(
    id SERIAL PRIMARY KEY,
    valor DECIMAL(10,2) NOT NULL,
    data DATE NOT NULL,
    tipo int NOT NULL,
    cpf VARCHAR(11) NOT NULL,
    cartao VARCHAR(16) NOT NULL,
    hora TIME NOT NULL,
    dono_loja VARCHAR(255) NOT NULL,
    nome_loja VARCHAR(255) NOT NULL
);
