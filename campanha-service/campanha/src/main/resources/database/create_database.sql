CREATE SCHEMA `dev` ;
CREATE TABLE `time_coracao` (
  `timeCoracaoId` int(11) NOT NULL AUTO_INCREMENT,
  `timeCoracaoNome` varchar(100) NOT NULL,
  PRIMARY KEY (`timeCoracaoId`)
) COMMENT='Armazena a lista de Times do Coracao'

CREATE TABLE `campanha` (
  `campanhaId` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(100) NOT NULL,
  `dataInicio` date NOT NULL,
  `dataVencimento` date NOT NULL,
  `dataUltimaAtualizacao` date DEFAULT NULL,
  `statusCampanha` int(1) DEFAULT '2' COMMENT '1 - Ativa\\n2 - Pendente\\n3 - Expirada',
  `timeCoracaoId` int(11) NOT NULL,
  PRIMARY KEY (`campanhaId`),
  KEY `timeCoracaoFK_Campanha_idx` (`timeCoracaoId`),
  CONSTRAINT `timeCoracaoFK_Campanha` FOREIGN KEY (`timeCoracaoId`) REFERENCES `time_coracao` (`timecoracaoid`)
) COMMENT='Armazena os dados da campanha'

CREATE TABLE `cliente` (
  `clienteId` int(11) NOT NULL,
  `nomeCompleto` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `dataNascimento` date NOT NULL,
  `statusCliente` int(1) NOT NULL COMMENT '1 - Ativo\n2 - Inativo',
  `timeCoracaoId` int(11) DEFAULT NULL,
  PRIMARY KEY (`clienteId`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  KEY `timeCoracaoFK_Cliente_idx` (`timeCoracaoId`),
  CONSTRAINT `timeCoracaoFK_Cliente` FOREIGN KEY (`timeCoracaoId`) REFERENCES `time_coracao` (`timecoracaoid`)
) COMMENT='Armaena os dados dos clientes'

CREATE TABLE `assc_campanha_cliente` (
  `asscCampanhaClienteId` int(11) NOT NULL AUTO_INCREMENT,
  `campanhaId` int(11) NOT NULL,
  `clienteId` int(11) NOT NULL,
  PRIMARY KEY (`asscCampanhaClienteId`),
  KEY `campanhaIdFK_AsscCampanhaCliente_idx` (`campanhaId`),
  KEY `clienteIdFK_AsscCampanhaCliente_idx` (`clienteId`),
  CONSTRAINT `campanhaIdFK_AsscCampanhaCliente` FOREIGN KEY (`campanhaId`) REFERENCES `campanha` (`campanhaid`),
  CONSTRAINT `clienteIdFK_AsscCampanhaCliente` FOREIGN KEY (`clienteId`) REFERENCES `cliente` (`clienteid`)
) COMMENT='Armazena as associacoes entres os clientes, time do coração e campanhas'