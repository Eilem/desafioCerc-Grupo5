 API MVP
-----

 # Desafio final da Gama Academy - CERC - Grupo 5

Desenvolvimento do desafio final


### Requisitos não funcionais:

Para executar a aplicação você deve ter:

- [Java 11 +](https://www.oracle.com/java/technologies/downloads/)
- [Maven](https://maven.apache.org/download.cgi)
- [Springboot 2.6.6](https://start.spring.io/)
- [MySQL](https://www.mysql.com/)
___

## Como executar a aplicação:

Acesse o arquivo  ***application.yml***  que está localizado no diretório \src\main\resources
altere o conteúdo das variáveis abaixo, que representam su conexão com o banco de dados MySQL.

````

username: coloque aqui o nome do usuário do seu banco MySQL
password: coloque aqui sua senha de acesso ao seu banco MySQL

````
 

### Observação: 
verifique se não há outra aplicação rodando na porta **8080** pois a esta aplicação é executada na porta  8080, caso esta esteja ocupada , você  terá um erro e não conseguirá acessar a aplicação.

----

Abrir o terminal de seu computador  e executar o comando :  

````

mvn spring-boot:run

````

#Documentação da API

´Para acessar a documentação da API após executar a aplicação acesse em seu navegador: 

localhost:8080/doc/api


____

# Requisitos Funcionais
____

# Requisitos Postergados
