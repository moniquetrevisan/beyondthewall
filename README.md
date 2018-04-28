Dependencias
  - java
  - maven
  - otp_win64_20.3.exe
  - Instalaçao e Configuração do rabbitmq > rabbitmq-server-3.7.4.exe
    - iniciar o rabbitmq - verificar via Servicos (Win)
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
  
  
  # beyondthewall
