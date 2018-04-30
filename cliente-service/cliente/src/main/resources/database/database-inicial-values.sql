INSERT INTO `dev`.`time_coracao` (`timeCoracaoId`, `timeCoracaoNome`) VALUES ('1', 'SANTOS');
INSERT INTO `dev`.`time_coracao` (`timeCoracaoId`, `timeCoracaoNome`) VALUES ('2', 'PALMEIRAS');
INSERT INTO `dev`.`time_coracao` (`timeCoracaoId`, `timeCoracaoNome`) VALUES ('3', 'SAO PAULO');
INSERT INTO `dev`.`time_coracao` (`timeCoracaoId`, `timeCoracaoNome`) VALUES ('4', 'CORINTHIANS');
INSERT INTO `dev`.`time_coracao` (`timeCoracaoId`, `timeCoracaoNome`) VALUES ('5', 'PONTE PRETA');
INSERT INTO `dev`.`time_coracao` (`timeCoracaoId`, `timeCoracaoNome`) VALUES ('6', 'GUARANI');
INSERT INTO `dev`.`time_coracao` (`timeCoracaoId`, `timeCoracaoNome`) VALUES ('7', 'FLAMENGO');
INSERT INTO `dev`.`time_coracao` (`timeCoracaoId`, `timeCoracaoNome`) VALUES ('8', 'FLUMINENSE');
INSERT INTO `dev`.`time_coracao` (`timeCoracaoId`, `timeCoracaoNome`) VALUES ('9', 'VASCO');
INSERT INTO `dev`.`time_coracao` (`timeCoracaoId`, `timeCoracaoNome`) VALUES ('10', 'BOTAFOGO');
INSERT INTO `dev`.`time_coracao` (`timeCoracaoId`, `timeCoracaoNome`) VALUES ('11', 'SPORT');
INSERT INTO `dev`.`time_coracao` (`timeCoracaoId`, `timeCoracaoNome`) VALUES ('12', 'GREMIO');
INSERT INTO `dev`.`time_coracao` (`timeCoracaoId`, `timeCoracaoNome`) VALUES ('13', 'BRASIL');

INSERT INTO `dev`.`cliente` (`clienteId`, `nomeCompleto`, `email`, `dataNascimento`, `statusCliente`, `timeCoracaoId`) VALUES ('1', 'Monique Trevisan Volpe', 'moniquetrevisan@hotmail.com', '1987-05-1987', '1', '1'); 

INSERT INTO `dev`.`campanha` (`campanhaId`, `nome`, `dataInicio`, `dataVencimento`, `dataUltimaAtualizacao`, `statusCampanha`, `timeCoracaoId`) VALUES ('1', 'Campanha1', '2018-05-01', '2018-05-03', '2018-04-30', '2', '1');
INSERT INTO `dev`.`campanha` (`campanhaId`, `nome`, `dataInicio`, `dataVencimento`, `dataUltimaAtualizacao`, `statusCampanha`, `timeCoracaoId`) VALUES ('2', 'Campanha2', '2018-05-01', '2018-05-02', '2018-04-30', '2', '1');
INSERT INTO `dev`.`campanha` (`campanhaId`, `nome`, `dataInicio`, `dataVencimento`, `dataUltimaAtualizacao`, `statusCampanha`, `timeCoracaoId`) VALUES ('3', 'Campanha3', '2018-05-20', '2018-05-30', '2018-04-30', '2', '2');
COMMIT;