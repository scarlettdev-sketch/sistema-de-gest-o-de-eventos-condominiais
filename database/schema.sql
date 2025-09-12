-- Criar banco de dados
CREATE DATABASE IF NOT EXISTS condominio;
USE condominio;

-- Criar tabela de usuários (se não existir)
-- Criar tabela de usuários compatível com o DAO
CREATE TABLE IF NOT EXISTS usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome_completo VARCHAR(100) NOT NULL,
    cpf VARCHAR(14),
    email VARCHAR(100),
    unidade VARCHAR(10),
    login VARCHAR(50) UNIQUE NOT NULL,
    senha_hash VARCHAR(255) NOT NULL,
    perfil ENUM('administrador', 'morador', 'funcionario') DEFAULT 'morador',
    ativo BOOLEAN DEFAULT TRUE,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Criar tabela de áreas comuns
CREATE TABLE IF NOT EXISTS areas_comuns (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    capacidade_maxima INT NOT NULL,
    regras_uso TEXT,
    taxa_reserva DECIMAL(10,2) DEFAULT 0.00,
    ativa BOOLEAN DEFAULT TRUE,
    descricao TEXT,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Inserir usuários de teste
INSERT IGNORE INTO usuarios (nome_completo, login, senha_hash, perfil) VALUES
('Administrador do Sistema', 'admin', 'admin123', 'administrador'),
('João Silva', 'joao', '123456', 'morador'),
('Maria Santos', 'maria', '123456', 'morador'),
('Carlos Funcionário', 'carlos', 'func123', 'funcionario');

-- Inserir dados iniciais de áreas comuns
INSERT IGNORE INTO areas_comuns (nome, capacidade_maxima, regras_uso, taxa_reserva, descricao) VALUES
('Salão de Festas', 50, 'Proibido fumar. Limpeza obrigatória após uso.', 150.00, 'Salão principal para eventos'),
('Churrasqueira', 20, 'Uso até 22h. Limpeza obrigatória.', 80.00, 'Área de churrasqueira'),
('Piscina', 30, 'Uso até 20h. Crianças acompanhadas.', 0.00, 'Piscina adulto e infantil'),
('Quadra Esportiva', 20, 'Uso até 22h. Calçado adequado obrigatório.', 50.00, 'Quadra poliesportiva');

CREATE TABLE areas_comuns (
    id_area INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    capacidade_maxima INT,
    regras_uso TEXT,  -- Adicione esta linha
    taxa_reserva DECIMAL(10, 2),
    descricao TEXT
);