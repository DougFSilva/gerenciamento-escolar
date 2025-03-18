# API de Gestão de Cursos e Alunos 📚🎓  

Esta API foi desenvolvida em **Spring Boot** para fins educacionais, permitindo que meus alunos de **Desenvolvimento de Sistemas** consumam uma aplicação real. A API oferece operações **CRUD** para **cursos** e **alunos**, incluindo o armazenamento e a exibição de imagens.  

## 🛠 Configuração Padrão  

- **Banco de Dados:** H2 (embutido)  
- **Documentação:** Disponível em [Swagger UI](http://localhost:8080/swagger-ui.html)  
- **Armazenamento de Imagens:** Uma pasta chamada **`imagens`** será criada na raiz do projeto para armazenar fotos dos alunos e imagens dos cursos.  

## ⚙ Personalização  

Caso seja necessário modificar alguma configuração (como o banco de dados ou diretório de imagens), edite o arquivo **`application.properties`**.  

## 🚀 Executando a Aplicação  

1. Abra o terminal na pasta onde está o **`.jar`** da aplicação.  
2. Execute o seguinte comando:  

   ```sh
   java -jar <nome_da_aplicacao>.jar
