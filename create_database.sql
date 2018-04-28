CREATE SCHEMA `dev` ;
-- Times do Coracao
CREATE TABLE `dev`.`time_coracao` (
  `timeCoracaoId` INT NOT NULL AUTO_INCREMENT,
  `timeCoracaoNome` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`timeCoracaoId`))
COMMENT = 'Armazena a lista de Times do Coracao';

-- Dados da Campanha
CREATE TABLE `dev`.`campanha` (
  `campanhaId` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(100) NOT NULL,
  `dataInicio` DATE NOT NULL,
  `dataVencimento` DATE NOT NULL,
  `dataUltimaAtualizacao` DATE NULL,
  `statusCampanha` INT(1) NULL DEFAULT 2 COMMENT '1 - Ativa\n2 - Inativa\n3 - Expirada',
  PRIMARY KEY (`campanhaId`))
COMMENT = 'Armazena os dados da campanha';

-- Dados dos Clientes
CREATE TABLE `dev`.`cliente` (
  `clienteId` INT NOT NULL,
  `nomeCompleto` VARCHAR(100) NOT NULL,
  `email` VARCHAR(100) NOT NULL,
  `dataNascimento` DATE NOT NULL,
  `statusCliente` INT(1) NOT NULL COMMENT '1 - Ativo\n2 - Inativo',
  PRIMARY KEY (`clienteId`))
COMMENT = 'Armaena os dados dos clientes';

-- Criacao das FK's 
ALTER TABLE `dev`.`campanha` 
ADD COLUMN `timeCoracaoId` INT NOT NULL AFTER `statusCampanha`,
ADD INDEX `timeCoracaoFK_Campanha_idx` (`timeCoracaoId` ASC) VISIBLE;
;
ALTER TABLE `dev`.`campanha` 
ADD CONSTRAINT `timeCoracaoFK_Campanha`
  FOREIGN KEY (`timeCoracaoId`)
  REFERENCES `dev`.`time_coracao` (`timeCoracaoId`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE `dev`.`cliente` 
ADD COLUMN `timeCoracaoId` INT NULL AFTER `statusCliente`,
ADD INDEX `timeCoracaoFK_Cliente_idx` (`timeCoracaoId` ASC) VISIBLE;
;
ALTER TABLE `dev`.`cliente` 
ADD CONSTRAINT `timeCoracaoFK_Cliente`
  FOREIGN KEY (`timeCoracaoId`)
  REFERENCES `dev`.`time_coracao` (`timeCoracaoId`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

  
-- Criacao da Tabela de Associacao entre Campanhas, Clientes e Time do Coracao
CREATE TABLE `dev`.`assc_campanha_cliente` (
  `asscCampanhaClienteId` INT NOT NULL,
  `campanhaId` INT NOT NULL,
  `clienteId` INT NOT NULL,
  PRIMARY KEY (`asscCampanhaClienteId`),
  INDEX `campanhaIdFK_AsscCampanhaCliente_idx` (`campanhaId` ASC) VISIBLE,
  INDEX `clienteIdFK_AsscCampanhaCliente_idx` (`clienteId` ASC) VISIBLE,
  CONSTRAINT `campanhaIdFK_AsscCampanhaCliente`
    FOREIGN KEY (`campanhaId`)
    REFERENCES `dev`.`campanha` (`campanhaId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `clienteIdFK_AsscCampanhaCliente`
    FOREIGN KEY (`clienteId`)
    REFERENCES `dev`.`cliente` (`clienteId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
COMMENT = 'Armazena as associacoes entres os clientes, time do coração e campanhas';



ENGINE = InnoDB