#Repositório para Cadastro de Campanhas e Clientes

Beyond The Wall 
--------------------------
Este repositório possui um sistema simples que permite o cadastro e consulta de clientes e campanhas.  

    Arquitetura de Microserviços
    --------------------------
    De acordo com os requisitos do sistema, deveriam ser criados serviços diferentes para atender as demandas relacionadas ao cadastro de campanhas, clientes e associação entre eles, para isso utilizei a arquitetura de microserviços, pois é a que mais atende a esses requisitos. 
    Para atender ao requisito de enviar notificações para sinalizar alterações, fez-se necessária a criação de um modo que permitisse a troca de mensagens entre os serviços, então utilizei o RabbitMQ para criar um micro sistema de mensageria para a troca de notificações entre os serviços.


    Estrututa do projeto: eureka-service, campanha-service e cliente-service
    --------------------------
      - eureka-service = possui a função de server para o eureka;
      - campanha-service = serviço responsável por prover as requisições dos cadastros de campanha e associação entre clientes e campanhas;
      - cliente-service = serviço responsável por prover as requisições dos cadastros de clientes e consumir informações do módulo de campanha.
 
      
    Informações para Deploy
    --------------------------
      - Java 8
      - Maven 9
      - Utilizado para notificações entre serviços
        - otp_win64_20.3.exe
        - Instalação e configuração do rabbitmq > rabbitmq-server-3.7.4.exe
          - Iniciar o rabbitmq - verificar via Serviços (Win)
            cd C:\Program Files\RabbitMQ Server\rabbitmq_server-3.7.4\sbin
            rabbitmqctl.bat 
            rabbitmqctl status
            rabbitmqctl stop
          - Habilitar o management via http
            https://www.rabbitmq.com/install-windows.html
            https://www.rabbitmq.com/management.html
            https://rawcdn.githack.com/rabbitmq/rabbitmq-management/v3.7.4/priv/www/api/index.html
            cd C:\Program Files\RabbitMQ Server\rabbitmq_server-3.7.4\plugins
            rabbitmq-plugins.bat enable rabbitmq_management
            restart rabbitmq service
            acessar http://localhost:15672
              user: guest
              pass: guest
      - Tomcat 9
      - MySQL 8
        - copiar o driver do mysql-conector para dentro da pasta lib do Tomcat
        - criar o banco de dados utilizando o script "beyondthewall\campanha-service\campanha\src\main\resources\database\create_database.sql"
        - fazer a carga na tabela de times - utilizar o script "beyondthewall\campanha-service\campanha\src\main\resources\database\database-inicial-values.sql"
          Obs: É obrigatório popular a tabela de times_coracao!!
      - Subir primeiramente o eureka-service (Server)
      - Subir os demais componetes: campanha-service e cliente-service (Clientes)
