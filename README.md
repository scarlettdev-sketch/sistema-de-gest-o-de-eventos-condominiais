# Sistema de Gestão de Eventos Condominiais

**Status do Projeto:** Versão 1.0 - Concluído (18/09/2025)

Este projeto, desenvolvido como requisito acadêmico, consiste em um sistema desktop completo para a gestão de áreas comuns e comunicação em um condomínio. A aplicação permite que administradores, moradores e funcionários interajam com o sistema de acordo com seus perfis de acesso.

## Funcionalidades Implementadas

### Arquitetura e Tecnologia
- **Arquitetura:** Padrão de camadas bem definido (Model, View, DAO, Config), facilitando a manutenção e escalabilidade.
- **Banco de Dados:** Schema relacional robusto em MySQL, com integridade referencial garantida por chaves estrangeiras.
- **Gerenciamento de Dependências:** O projeto utiliza **Maven** para gerenciar as bibliotecas externas (FlatLaf, MySQL Connector), garantindo consistência no ambiente de desenvolvimento.
- **UI/UX:** A interface gráfica foi modernizada com o tema **FlatLaf (Light)**, proporcionando uma experiência de usuário limpa e agradável, com uso de ícones e dicas de interface.

### Funcionalidades do Administrador (`admin / admin123`)
- **Dashboard Central:** Painel de controle com acesso a todas as funcionalidades administrativas.
- **Gestão de Usuários (CRUD):** Interface completa para criar, listar, atualizar e excluir usuários de todos os perfis.
- **Gestão de Áreas Comuns (CRUD):** Interface completa para criar, listar, atualizar e desativar áreas comuns.
- **Gestão de Reservas:** Visualização de todas as reservas solicitadas.
  - **Aprovação/Rejeição:** Funcionalidade para aprovar ou rejeitar reservas pendentes.
  - **Justificativa de Rejeição:** Ao rejeitar, o administrador pode inserir um motivo que fica registrado no sistema.
- **Publicação de Comunicados:** Ferramenta para criar e publicar comunicados gerais para todos os usuários.
- **Relatórios Gerenciais:** Tela de estatísticas que exibe um relatório de uso por área comum, incluindo o número de reservas e o valor total arrecadado.

### Funcionalidades do Morador (`joao / 123456`)
- **Painel do Morador:** Tela principal com acesso às suas funcionalidades.
- **Solicitação de Reserva:**
  - Interface para selecionar área, data e horário para um evento.
  - **Sistema Anti-Conflito:** O sistema valida em tempo real se o horário desejado já está ocupado, impedindo agendamentos duplos.
- **Visualização de Suas Reservas:** O morador pode consultar o status de suas próprias solicitações de reserva.
- **Mural de Comunicados:** Tela interativa para visualizar os comunicados publicados pela administração.

### Funcionalidades do Funcionário (`carlos / func123`)
- **Painel do Funcionário:** Tela de acesso simplificada.
- **Agenda Geral:** Visualização de todas as reservas **aprovadas**, permitindo que a equipe se programe para a preparação e limpeza das áreas.
- **Mural de Comunicados:** Acesso de leitura aos comunicados da administração.

## Tecnologias Utilizadas
- **Linguagem:** Java 21
- **Interface Gráfica:** Swing
- **Banco de Dados:** MySQL
- **Build/Dependency Manager:** Apache Maven
- **Look and Feel:** FlatLaf
- **Conector BD:** MySQL Connector/J

## Instruções para Rodar o Projeto

### Pré-requisitos
- Java JDK 17+
- Apache Maven
- XAMPP (ou qualquer servidor MySQL)

### Passos

1.  **Iniciar o Servidor MySQL:**
    - Inicie o MySQL na porta `3306`.

2.  **Configurar o Banco de Dados:**
    - Acesse o `phpMyAdmin` (ou um cliente SQL de sua preferência).
    - **IMPORTANTE:** Caso já exista um banco de dados `condominio`, **exclua-o (DROP)** para garantir uma instalação limpa.
    - Cole o conteúdo de database/schema.sql e EXECUTE.
    - Execute o script localizado em `database/schema.sql` para criar todas as tabelas e inserir os dados de teste.

3.  **Executar a Aplicação:**

    **Pelo IntelliJ IDEA (Recomendado)**
    - Abra o projeto no IntelliJ.
    - Espere a IDE sincronizar com o `pom.xml` e baixar as dependências.
    - Abra o arquivo `src/main/java/Main.java`.
    - Clique no botão verde de "Play" (▶) ao lado do método `main`.

    `

## Estrutura do Projeto Maven
```
sistema-de-gest-o-de-eventos-condominiais
├── database
│   └── schema.sql
├── src
│   ├── main
│   │   ├── java
│   │   │   ├── Config
│   │   │   ├── DAO
│   │   │   ├── Model
│   │   │   ├── View
│   │   │   └── Main.java
│   │   └── resources
│   │       └── icons
├── pom.xml
└── README.md
```

## Credenciais de Teste
- **Admin:** `admin` / `admin123`
- **Morador:** `joao` / `123456`
- **Funcionário:** `carlos` / `func123`
