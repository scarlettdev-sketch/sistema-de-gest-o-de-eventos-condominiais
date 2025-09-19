-- Criar banco de dados se não existir
CREATE DATABASE IF NOT EXISTS `condominio` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

-- Usar o banco de dados
USE `condominio`;

-- Tabela de Usuários
CREATE TABLE IF NOT EXISTS `usuarios` (
  `id` INT AUTO_INCREMENT PRIMARY KEY,
  `nome_completo` VARCHAR(100) NOT NULL,
  `cpf` VARCHAR(14) NULL,
  `email` VARCHAR(100) NULL,
  `unidade` VARCHAR(20) NULL,
  `login` VARCHAR(50) NOT NULL UNIQUE,
  `senha_hash` VARCHAR(255) NOT NULL,
  `perfil` ENUM('administrador', 'morador', 'funcionario') NOT NULL DEFAULT 'morador',
  `ativo` BOOLEAN NOT NULL DEFAULT TRUE,
  `data_criacao` TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabela de Áreas Comuns
CREATE TABLE IF NOT EXISTS `areas_comuns` (
  `id` INT AUTO_INCREMENT PRIMARY KEY,
  `nome` VARCHAR(100) NOT NULL UNIQUE,
  `descricao` TEXT NULL,
  `capacidade_maxima` INT NOT NULL,
  `regras_uso` TEXT NULL,
  `taxa_reserva` DECIMAL(10, 2) NOT NULL DEFAULT 0.00,
  `ativa` BOOLEAN NOT NULL DEFAULT TRUE,
  `data_criacao` TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabela de Reservas
CREATE TABLE IF NOT EXISTS `reservas` (
  `id` INT AUTO_INCREMENT PRIMARY KEY,
  `usuario_id` INT NULL,
  `area_comum_id` INT NOT NULL,
  `data_reserva` DATE NOT NULL,
  `horario_inicio` TIME NOT NULL,
  `horario_fim` TIME NOT NULL,
  `descricao` TEXT NULL,
  `motivo_rejeicao` TEXT NULL,
  `status` ENUM('pendente', 'aprovada', 'rejeitada', 'cancelada') NOT NULL DEFAULT 'pendente',
  `data_solicitacao` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (`usuario_id`) REFERENCES `usuarios`(`id`) ON DELETE SET NULL,
  FOREIGN KEY (`area_comum_id`) REFERENCES `areas_comuns`(`id`) ON DELETE CASCADE
);

-- Tabela de Comunicados
CREATE TABLE IF NOT EXISTS `comunicados` (
  `id` INT AUTO_INCREMENT PRIMARY KEY,
  `usuario_id` INT NULL,
  `titulo` VARCHAR(255) NOT NULL,
  `mensagem` TEXT NOT NULL,
  `data_publicacao` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (`usuario_id`) REFERENCES `usuarios`(`id`) ON DELETE SET NULL
);


-- =================================================================
-- INSERÇÃO DE DADOS DE TESTE (SOMENTE SE NÃO EXISTIREM)
-- =================================================================

INSERT IGNORE INTO `usuarios` (`nome_completo`, `login`, `senha_hash`, `perfil`) VALUES
('Administrador do Sistema', 'admin', 'admin123', 'administrador'),
('João Silva', 'joao', '123456', 'morador'),
('Maria Oliveira', 'maria', '123456', 'morador'),
('Carlos Souza', 'carlos', 'func123', 'funcionario');

INSERT IGNORE INTO `areas_comuns` (`nome`, `descricao`, `capacidade_maxima`, `taxa_reserva`) VALUES
('Salão de Festas', 'Amplo salão para eventos e confraternizações.', 50, 150.00),
('Churrasqueira Gourmet', 'Área com churrasqueira, forno de pizza e mesas.', 25, 50.00),
('Piscina Adulto e Infantil', 'Área de lazer com piscina para todas as idades.', 30, 0.00),
('Quadra Poliesportiva', 'Quadra para prática de futsal, vôlei e basquete.', 20, 25.50);