# Bootcamp Duo Project 🚀

Bem-vindo ao Bootcamp Duo Project! Esta é uma aplicação web desenvolvida com Spring Boot, demonstrando funcionalidades como autenticação de usuários, autorização baseada em papéis (roles), gerenciamento de usuários e conteúdo dinâmico com Thymeleaf.

## Visão Geral

O projeto simula uma plataforma de aprendizado (bootcamp) com diferentes tipos de usuários (ADMIN, DEVOPS, INTEL), cada um com acesso a seções específicas da aplicação. Administradores possuem um painel para gerenciar usuários e visualizar estatísticas.

## ✨ Funcionalidades Principais

*   **Autenticação de Usuários:**
    *   Login com e-mail e senha.
    *   Mecanismo de logout seguro.
*   **Registro de Usuários:**
    *   Página de cadastro público para novos usuários (perfis DEVOPS ou INTEL).
    *   Validação de força da senha no frontend.
*   **Autorização Baseada em Papéis (Roles):**
    *   Três papéis definidos: `ADMIN`, `DEVOPS`, `INTEL`.
    *   Hierarquia de papéis: `ADMIN` herda permissões de `DEVOPS` e `INTEL`.
    *   Redirecionamento pós-login baseado no papel do usuário.
    *   Páginas específicas para cada papel (`/admin`, `/devops`, `/intel`).
*   **Gerenciamento de Usuários (Painel Admin):**
    *   Listagem de todos os usuários.
    *   Criação de novos usuários pelo administrador (com atribuição de qualquer papel).
    *   Edição de informações e papéis de usuários existentes.
    *   Remoção de usuários (com proteção para não auto-exclusão).
*   **Dashboard do Administrador:**
    *   Gráfico de barras mostrando a distribuição de usuários por papel (DevOps e Intel).
*   **Interface do Usuário:**
    *   Templates Thymeleaf para renderização dinâmica de páginas.
    *   Uso de Bootstrap para estilização.
    *   Layout de painel consistente (`painel.html`).
    *   Páginas de conteúdo estático informativas (ex: DevOps, Inteligência Artificial).
*   **Tratamento de Erros:**
    *   Páginas de erro customizadas (ex: acesso negado - 403).
*   **Persistência de Dados:**
    *   Banco de dados H2 em arquivo para desenvolvimento.
    *   Spring Data JPA com Hibernate para ORM.

## 🛠️ Tecnologias Utilizadas

*   **Backend:**
    *   Java (JDK 17+ recomendado, baseado nas dependências do Spring Boot 3.x)
    *   Spring Boot 3.x
    *   Spring Security (Autenticação e Autorização)
    *   Spring Data JPA (Persistência de dados)
    *   Hibernate (Implementação JPA)
    *   Thymeleaf (Template Engine)
    *   H2 Database (Banco de dados em memória/arquivo)
*   **Frontend:**
    *   HTML5
    *   CSS3 (Bootstrap 5 via CDN no painel, Bootstrap 4 em algumas páginas estáticas)
    *   JavaScript (Validação de senha, Chart.js para gráficos)
*   **Build & Gerenciamento de Dependências:**
    *   Apache Maven (com Maven Wrapper)

## 📋 Pré-requisitos

*   JDK 17 ou superior.
*   Apache Maven 3.6+ (ou use o Maven Wrapper incluído).

## 🚀 Como Executar

1.  **Clone o repositório:**
    ```bash
    git clone https://github.com/seu-usuario/bootcamp_duo-main.git
    cd bootcamp_duo-main
    ```

2.  **Execute a aplicação usando o Maven Wrapper:**
    *   No Linux/macOS:
        ```bash
        ./mvnw spring-boot:run
        ```
    *   No Windows:
        ```bash
        mvnw.cmd spring-boot:run
        ```

3.  **Acesse a aplicação:**
    Abra seu navegador e acesse: `http://localhost:8080/`

## 🗄️ Configuração do Banco de Dados (H2)

O projeto utiliza um banco de dados H2 em modo arquivo.
*   **Localização do arquivo do banco:** `./data/bootcampduo.mv.db` (será criado no diretório raiz do projeto na primeira execução).
*   **Console H2:** Habilitado para desenvolvimento.
    *   Acesse: `http://localhost:8080/h2-console`
    *   **JDBC URL:** `jdbc:h2:file:./data/bootcampduo`
    *   **User Name:** `sa`
    *   **Password:** (deixe em branco)

## 🔐 Segurança e Papéis

*   **Login:** A página de login está disponível em `/login`.
*   **Cadastro Público:** Novos usuários podem se registrar em `/cadastrarUsuario` com os papéis `DEVOPS` ou `INTEL`.
*   **Criação de Admin:** Não há um fluxo de cadastro direto para o papel `ADMIN`.
    *   **Opção 1 (Recomendado):** Após registrar um primeiro usuário, acesse o console H2, localize o usuário na tabela `USERS` e altere manualmente o campo `ROLE` para `ADMIN`.
    *   **Opção 2 (Desenvolvimento):** Modificar temporariamente o `UsuarioController` para permitir o cadastro de um `ADMIN` ou implementar uma lógica de seed de dados (ex: via `CommandLineRunner`).
*   **Hierarquia de Papéis:**
    *   `ROLE_ADMIN` > `ROLE_DEVOPS`
    *   `ROLE_ADMIN` > `ROLE_INTEL`
    (Isso significa que um usuário ADMIN também possui as permissões de DEVOPS e INTEL).

## 🏗️ Estrutura do Projeto (Principais Pacotes Java)

*   `br.edu.fesa.bootcampduo`: Pacote raiz da aplicação Spring Boot.
    *   `BootcampduoApplication.java`: Classe principal.
    *   `controller/`: Controladores Spring MVC.
    *   `Enum/`: Enumerações (ex: `UsuarioRole`).
    *   `model/`: Entidades JPA (ex: `UsuarioModel`).
    *   `repository/`: Repositórios Spring Data JPA (ex: `UsuarioRepository`).
    *   `security/`: Configurações do Spring Security.
*   `br.edu.fesa.bootcamp.service`: Pacote para serviços (ex: `AuthorizationService`).
    *   *Nota: A `BootcampduoApplication` está configurada para escanear este pacote adicionalmente.*

## 📄 Templates e Recursos Estáticos

*   **Templates Thymeleaf:** Localizados em `src/main/resources/templates/`.
    *   `painel.html`: Layout base para páginas autenticadas.
    *   `login/`: Páginas de login e cadastro.
    *   `usuario/`: Páginas de gerenciamento de usuários.
    *   `error/`: Páginas de erro.
    *   `bootcamp/`: Páginas de conteúdo (devops, inteligencia-artificial).
*   **Recursos Estáticos:** Localizados em `src/main/resources/static/`.
    *   `bootstrap/`: Arquivos CSS do Bootstrap.
    *   `images/`: Imagens usadas nas páginas.
    *   `footer.html`, `header.html`: Fragmentos HTML (parecem ser para páginas estáticas não-Thymeleaf, possivelmente legado ou para uso via JavaScript fetch).

## ⚠️ Problemas Conhecidos e Soluções

1.  **Erro "The file is locked" no H2 Database (`bootcampduo.trace.db`):**
    *   **Causa:** Geralmente ocorre se outra instância da aplicação estiver rodando ou se uma conexão com o banco H2 (ex: pelo console H2 de um IDE) estiver ativa e bloqueando o arquivo `bootcampduo.mv.db`.
    *   **Solução:** Certifique-se de que apenas uma instância da aplicação esteja em execução e feche quaisquer outras conexões diretas ao arquivo do banco de dados H2.

2.  **JVM Crash (`hs_err_pid*.log`):**
    *   Um arquivo `hs_err_pid17784.log` foi encontrado, indicando uma falha na JVM no ambiente do usuário "Kaiki" em 19 de Maio de 2025, com JRE 21.0.7.
    *   **Causa:** Pode ser devido a problemas de ambiente específicos (conflitos de DLL, drivers, versão do Java incompatível com algum componente nativo, etc.).
    *   **Solução:** Se ocorrerem crashes similares, verifique a compatibilidade da versão do Java, atualize drivers e verifique se há softwares de terceiros (como o "360 Total Security" mencionado no log) interferindo. Para este projeto, recomenda-se Java 17.

3.  **Referências a `bootcamp-duo` em caminhos de URL:**
    *   Alguns arquivos estáticos (ex: `header.html`) e o `application.properties` (comentado) mencionam um `context-path=/bootcamp-duo`.
    *   **Comportamento Atual:** A aplicação roda no contexto raiz (`/`) por padrão.
    *   **Solução/Nota:** Se desejar usar o contexto `/bootcamp-duo`, descomente a linha `server.servlet.context-path=/bootcamp-duo` em `src/main/resources/application.properties`. Certifique-se de que todos os links Thymeleaf (`th:href="@{...}")` e referências a recursos estáticos sejam relativos ao contexto ou usem `th:href` para serem resolvidos corretamente.

## 🤝 Contribuindo

Contribuições são bem-vindas! Sinta-se à vontade para abrir issues ou pull requests.
