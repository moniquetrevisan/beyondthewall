#Repositório para Cadastro de Campanhas e Clientes - Desafio Proposto pela NetShoes

os exercícios 1, 2 e 3 foram resolvidos através do projeto Beyond the Wall

Beyond The Wall 
--------------------------
Este repositório possui um sistema simples que permite o cadastro e consulta de clientes e campanhas. Desenvolvido para atender aos requisitos do desafio. 

- Exercício 1 e 2

    Arquitetura de Microserviços
    --------------------------
    De acordo com os requisitos do sistemas, deveriam ser criados serviços diferentes para atender as demandas relacionadas ao cadastro de campanhas, clientes e associação entre eles, para isso utilizei a arquitetura de microserviços, pois é a que mais atende a esses requisitos. 
    Para atender ao requisito de enviar notificações para sinalizar alterações, fez-se necessária a criação de um modo que permitisse a troca de mensagens entre os serviços, então utilizei o RabbitMQ para criar um micro sistema de mensageria para a troca de notificações entre os serviços.


    Estrututa do projeto: eureka-service, campanha-service e cliente-service
    --------------------------
      - eureka-service = possui a função de server para o eureka;
      - campanha-service = serviço responsável por prover as requisições dos cadastros de campanha e associação entre clientes e campanhas;
      - cliente-service = serviço responsável por prover as requisições dos cadastros de clientes e consumir informações do módulo de campanha.

    Requisitos Não Funcionais
    --------------------------
    A estrutura escolhida atende a todos os requisitos não funcionais, que em média recebe 100 req/s. A configuração default de todos os componentes já é suficiente, porém caso tenha um aumento desse requisito teria apenas que realizar um tunning das configurações ou escalar alguns serviços, a depender do requisito.
      
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
   
   
- Exercício 3

  Resolvido através do projeto stream incluído no repositório.
  
  
- Exercício 4

  Deadlock é um problema da programação concorrente, que acontece quando duas threads paralelas tentam acessar o mesmo objeto ou ponto do sistema que só permite acesso serial e para isso é necessário realizar o lock de determinado objeto ou ponto do sistema.
  Quando este controle de acesso não é corretamente controlado, temos um cenário que faz com que uma thread "espere" a outra liberar o lock para poder seguir, e dessa forma criamos uma espera infinita, já que uma está esperando a outra terminar, todas estão no estado de wait.
  
  Para exemplificar este cenário, tem-se o seguindo trecho de código (reproduzido através de diversas fontes da internet):
      public class TestThread {
         public static Object Lock1 = new Object();
         public static Object Lock2 = new Object();
         
         public static void main(String args[]) {
            ThreadDemo1 T1 = new ThreadDemo1();
            ThreadDemo2 T2 = new ThreadDemo2();
            T1.start();
            T2.start();
         }
         
         private static class ThreadDemo1 extends Thread {
            public void run() {
               synchronized (Lock1) {
                  System.out.println("Thread 1: Holding lock 1...");
                  
                  try { Thread.sleep(10); }
                  catch (InterruptedException e) {}
                  System.out.println("Thread 1: Waiting for lock 2...");
                  
                  synchronized (Lock2) {
                     System.out.println("Thread 1: Holding lock 1 & 2...");
                  }
               }
            }
         }
         private static class ThreadDemo2 extends Thread {
            public void run() {
               synchronized (Lock2) {
                  System.out.println("Thread 2: Holding lock 2...");
                  
                  try { Thread.sleep(10); }
                  catch (InterruptedException e) {}
                  System.out.println("Thread 2: Waiting for lock 1...");
                  
                  synchronized (Lock1) {
                     System.out.println("Thread 2: Holding lock 1 & 2...");
                  }
               }
            }
         } 
      }

  A Saída deste sistema deve ter a mesma lógica abaixo, comprovando que ambas as threads estão no estado de wait:
    Thread 1: Holding lock 1...
    Thread 2: Holding lock 2...
    Thread 1: Waiting for lock 2...
    Thread 2: Waiting for lock 1...
  
  

- Exercício 05

  Esta questão pode ser abstraída para a mesma decisão que nós programadores fazemos quando temos uma parte importante do código que irá nos exigir performance e a execução de várias tarefas simultâneas.
  A principal pergunta que procuro responder nestes casos, seria quais são as tarefas que o sistema pode fazer em paralelo e não irá exigir uma sequência de estados inter-relacionados? Qual é volume de dados que minhas estruturas de dados deverão processar? Qual a frequência de I/O que será executada?
  Na maioria das tarefas paralelizamos boa parte dos processos, como por exemplo, podemos construir uma estrutura que possua várias threads executando o recebimento de mensagens, pois cada mensagem seria um evento único e não possui dependências, porém quando iremos processar uma lista de objetos e fazer manipulações dentro destas iterações, geralmente temos que pensar e analisar o mesmo cenário. Na maioria dos casos, escolho utilizar a iteração linear (abstraindo para Stream, geralmente utilizo a Stream), e apenas analiso a necessidade de se paralelizar (usando o ParallelStream) nas seguintes condições:
    - Quando tenho uma quantidade enorme de itens para processar e o processamento de cada item é relativamente pesado e pode ser feito em paralelo.
    - Quando a abordagem linear se mostra mais pesada que o custo de fazermos uso do paralelismo, já que usar várias threads e coordenar os pontos e execuções também são tarefas "caras".
    - Quando tenho muitas camadas de paralelismo, analiso se vale a pena adicionar mais uma camada de paralelismo ao conjunto como um todo.
