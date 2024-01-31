### Run the project:

*Caso queria rodar o projeto sem se preocupar com detalhes internos
``docker-compose up -d`` pode demorar um pouco, já que ele builda o arquivo jar*
<br><br>
*Caso queria rodar manualmente atente-se aos requisitos:*
 - Java 17
 - Configurar MySQL
    - username configurado: root
    - password configurado: root

### Requests:

*A API disponibiliza duas formas para facilitar o envio de request à aplicação:
[Swagger](http://localhost:8080/api/swagger-ui/index.html) ou
[Collection do Postman](Akross%20API.postman_collection.json)*

### It might be useful:

*[Converter imagem para Base64](https://www.base64-image.de/)*

- Esse site em especifico converte a imagem para base64 e tem a opção copiar o código gerado para o
  clipboard. **ATENÇÂO: remover parâmetros do inicio ``data:image/png;base64,``**

### Photos:

*As fotos geradas vão para dentro da pasta de recursos, mais especificamente aqui: [Photos](src/main/resources/photos)*
