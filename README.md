 API - Folha de Pagamento Digital
-----

 # Desafio final da Gama Academy - CERC - Grupo 5

Desenvolvimento do projeto para o desafio final da academia onde foi proposto a criação de um sistema para gerenciamento de folha de pagamento de funcionários de uma empresa.

O projeto consiste em um sistema robusto, modelado para atender a necessidade de pequenas e grandes empresas. 

Neste repositório apresenta-se a solução proposta através de um **MVP**( Mínimo Produto Viável ) desenvolvido durante a primeira sprint, após análise do negócio e levantamento dos requisitos, foi analisado e desenvolvido o projeto inicial para apresentação ao cliente e evolução de suas funcionalidades.

- - - - 
>  Abaixo seguem informações técnicas do projeto e como executá-lo, bem como requisitos necessários e o passo-a-passo para *rodar* o projeto.**  
---

# Requisitos Funcionais 

Implementados nessa primeria fase:

- Manter cargos
- Manter funcionarios
- Cadastrar folha de pagamento para funcionario
- Listar folhas de pagamento
- Listar folhas de pagamento por funcionário
- Visualizar detalhes da folha de pagamento
---

### Requisitos não funcionais:

Para executar a aplicação você deve ter:

- [Java 11 +](https://www.oracle.com/java/technologies/downloads/)
- [Maven](https://maven.apache.org/download.cgi)
- [Springboot 2.6.6](https://start.spring.io/)
- [MySQL](https://www.mysql.com/)
___

##### Passo #1

Primeiramente clone o *branch* **MVP** [neste repositório do GitHub](https://github.com/Eilem/desafioCerc-Grupo5)

----


##### Passo #2 
### Como executar a aplicação:

Após clonar o projeto acesse o diretório(pasta) ***/src/main/resources***
Abra o  arquivo  ***application.yml*** e altere o conteúdo das variáveis exibidas abaixo. 
*Obs.:* Não se esqueça de salvar o arquivo após a alteração.

````

username: coloque aqui o nome do usuário do seu banco MySQL**
password: coloque aqui sua senha de acesso ao seu banco MySQL**

````
 \** Essas variávies representam sua conexão com o banco de dados MySQL.

---

##### Passo #3 

Em seu computador,no diretório raiz do projeto que foi clonado, abra o terminal e execute o comando:  

````

mvn spring-boot:run

````

Pronto!!! A API está executando, basta acessar `localhost:8080`

#### `Observação Importante!!`

Verifique se não há outra aplicação rodando na porta **`8080`** pois a esta aplicação é executada esta porta, caso a mesma esteja ocupada, você  terá um erro e não conseguirá executar e acessar a aplicação com êxito.

--- 

#### OK, mas e agora o que fazer?  Como acessar os recursos da API?  


> Para executar a API disponibilizamos a documentação dos endpoints **[aqui :)](https://folhadigital.docs.apiary.io/ )**


