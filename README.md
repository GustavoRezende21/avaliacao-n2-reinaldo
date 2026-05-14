# Engenharia de Software - 6º Período - Avaliação N2

> **Plataforma E-commerce:** Sistema completo (Backend e Frontend) voltado para a venda de computadores e peças de hardware (PC Gamer).

---

## Integrantes do Grupo
* Gabriel Cândido Ribeiro da Silva;
* Gustavo Rezende Gabriel;
* Theo da Silva Sá;
* João Vitor Bier Barros.

---

## Vídeo Demonstrativo do Sistema Rodando
Demonstração do sistema em funcionamento através do link abaixo:
[Clique aqui para assistir ao vídeo no Google Drive: ](https://drive.google.com/file/d/1-131XTq50scdTNFkAvUzdscepnLlI8xG/view?usp=drive_link)

---

## Descrição da Proposta
A proposta consiste no desenvolvimento de uma plataforma de E-commerce especializada no nicho de Computadores Gamer e Hardware. No sistema foi construído um backend RESTful em Java e um frontend em React.

### Principais Funcionalidades
* **Logar:** Porta de acesso ao sistema (Sem segurança), somente valida se os dados estão persistidos no Banco de Dados.
* **CRUD de Usuários:** Gerenciamento completo (Criação, Leitura, Atualização e Exclusão) dos usuários.
* **CRUD de Produtos:** Gerenciamento completo (Criação, Leitura, Atualização e Exclusão) do catálogo de peças.
* **Registrar Vendas:** Gerenciamento responsável pelo processamento da compras e fechamento do pedido.

---

## Tecnologias Utilizadas

### Backend
* **[Java (17) + Spring Boot (4.0.6)]**
* **[Dependências: H2 Database, Spring Data JPA, Spring Boot DevTools, Spring Web]**
* **[Dependências de Testes: JUnit, Selenium, WebDriver Manager (Chrome), Mockito, Rest Assured, Hamcrest]**

### Frontend
* **React + Vite (JavaScript) (Versão Recente)**
* **[Dependências: Axios, react-router-dom]**

---

## Ferramentas Necessárias
* **Java Development Kit (JDK) 17**.
* **Node.js** (Versão LTS recomendada) para gerenciar e rodar o Frontend.
* **IntelliJ IDEA** (Community ou Ultimate) ou qualquer IDE de sua preferência.
* **Google Chrome** (Necessário para a execução dos testes automatizados do Selenium).

---

### 1. Instruções para Rodar o Backend

1. Abra a sua IDE de preferência (Recomendado: IntelliJ).
2. Faça a importação do projeto.
3. Aguarde a IDE importar todas as dependências do Maven.
4. Localize a classe principal do projeto (Diretório: `...\avaliacao-n2-reinaldo\backend\src\main\java\com\avaliacaoReinaldo\n2\N2Application.java`).
5. Execute o projeto.
6. O console abrirá indicando o sucesso da execução. Por padrão, o servidor subirá no endereço: (`http://localhost:8080`).

---

### 1. Instruções para Rodar o Frontend

Abra o seu terminal externo ou utilize o terminal embutido da IDE e execute os passos abaixo (Recomendado: VSCode):

1. Navegue até o diretório do frontend (cd `...\avaliacao-n2-reinaldo\frontend`).
2. Instale todas as dependências necessárias (npm install).
3. Inicie o servidor de desenvolvimento do Vite (npm run dev).
4. 6. O console vai indicar o sucesso da execução. Por padrão, o servidor subirá no endereço: (`http://localhost:5173`).
