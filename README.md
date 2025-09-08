# raspberry-awards-analyzer-api
API responsável por realizar a análise do intervalo de vitórias do raspberry awards

#### OBS: Para executar a API é necessário ter o docker instalado na maquina.

##### 1 - Em um terminal navegue até a pasta raiz do projeto

##### 2 - Execute o comando para gerar a imagem docker
    docker build -t raspberry-awards-analyzer-api .

##### 3 - Execute o comando para subir os containers
    docker-compose up --build

##### 4 - Documentação da api pode ser acessada
    http://localhost:8080/swagger-ui/index.html 