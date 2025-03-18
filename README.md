# API de Gestão de Cursos e Alunos 📚🎓  

Esta API foi desenvolvida em **Spring Boot** para fins educacionais, permitindo que meus alunos de **Desenvolvimento de Sistemas** consumam uma aplicação real. A API oferece operações **CRUD** para **cursos** e **alunos**, incluindo o armazenamento e a exibição de imagens.  

## 🛠 Configuração Padrão  

- **Banco de Dados:** H2 (embutido)  
- **Documentação:** Disponível em [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)  
- **Armazenamento de Imagens:** Uma pasta chamada **`imagens`** será criada na raiz do projeto para armazenar fotos dos alunos e imagens dos cursos.  

## ⚙ Personalização  

Caso seja necessário modificar alguma configuração (como o banco de dados ou diretório de imagens), edite o arquivo **`application.properties`**.  

## 🚀 Executando a Aplicação  

### 📦 Gerando o Arquivo `.jar`  

1. Certifique-se de ter o **Maven** instalado e configurado corretamente.  
2. No terminal, navegue até a raiz do projeto e execute:  

   ```sh
   mvn clean package
3. Após a conclusão do processo, o arquivo .jar será gerado na pasta target/.

## ▶️ Executando a Aplicação

1. Abra a pasta Target no terminal

2. Execute o seguinte comando para rodar a aplicação:

   ```sh
   java -jar nome_da_aplicacao.jar

Você pode renomear o jar da aplicação e mudar de pasta se quiser, e para executar basta estar com o terminal aberto nessa nova pasta e realizar o mesmo comando

### Agora a API estará disponível para uso! 🚀
