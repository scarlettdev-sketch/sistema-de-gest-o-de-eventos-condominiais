### **Relatório do Projeto - Sistema de Gestão de Eventos Condominiais**

**Status Atual do Projeto - 16/09/2025**

#### **O que foi IMPLEMENTADO:**

* **Arquitetura:**
    * **Model:** Classes `Usuario`, `AreaComum`, `Reserva` e `Comunicado` com atributos completos e construtores corrigidos.
    * **View:** `TelaLogin` (lógica de redirecionamento corrigida), `PainelAdmin`, `GerenciarUsuariosGUI`, `VerReservasGUI`, `PublicarComunicadoGUI`, **`MinhasReservasGUI`** e **`VerComunicadosGUI`** (interfaces gráficas iniciais).
    * **DAO:** `UsuarioDAO`, `AreaComumDAO`, `ReservaDAO` e `ComunicadoDAO` (acesso ao banco, com métodos de inserção e listagem corrigidos).
    * **Config:** `DatabaseInitializer` e `ConexaoBD` (configuração).

* **Banco de Dados MySQL:**
    * Estrutura completa da tabela `usuarios`.
    * **Tabela `reservas` corrigida e funcional com `id` (chave primária), `status`, `descricao` e chaves estrangeiras (`usuario_id`, `area_comum_id`)** para total sincronia com o código.
    * **Tabela `comunicados` criada e funcional.**
    * Dados de teste inseridos.
    * Conexão com XAMPP.

* **Sistema de Autenticação:**
    * Login funcional (admin/morador/funcionário).
    * Validação de credenciais.
    * Mensagem de boas-vindas.
    * Redirecionamento do usuário para o painel de acordo com o perfil.

* **Funcionalidades do Administrador:**
    * **Dashboard:** Painel principal com botões para as funcionalidades de gerenciamento.
    * **CRUD de Gerenciamento:** Lógica completa para criar, ler, atualizar e deletar (`CRUD`) de usuários e áreas comuns.
    * **Gestão de Reservas:** Interface visual para o administrador visualizar todas as reservas em uma tabela, com a lógica para aprovar e rejeitar.
    * **Publicação de Comunicados:** Interface visual para o administrador criar e salvar comunicados no banco de dados.

* **Funcionalidades do Morador:**
    * **Painel:** Interface principal com botões para as funcionalidades de reserva e comunicados.
    * **Agendamento de Reservas:** Tela `FazerReservaGUI` funcional, que permite ao morador preencher os dados e salvar a reserva no banco de dados.
    * **Visualização de Reservas:** Tela `MinhasReservasGUI` para que o morador veja suas próprias reservas.
    * **Visualização de Comunicados:** Interface para o morador ler os comunicados.

* **Funcionalidades do Funcionário:**
    * **Painel:** Interface principal com botões para as funcionalidades de agendamentos e comunicados.
    * **Visualização de Comunicados:** Interface para o funcionário ler os comunicados.

* **Configuração do Projeto:**
    * Driver MySQL configurado.
    * Estrutura de pacotes organizada.
    * Compilação e execução funcionais.
    * **Gerenciamento de Versão:** Repositório Git configurado e **sincronizado na branch `main`**.

---

#### **O que ainda FALTA IMPLEMENTAR:**

* **Lógica de Negócio e Funcionalidades:**
    * **Inserir uma descrição na rejeição de reserva:** A lógica de rejeição precisa ser aprimorada para permitir que o administrador justifique o motivo da rejeição.
    * **Controle de conflitos para o agendamento de reservas:** Implementar a validação para evitar que uma área seja reservada duas vezes no mesmo horário.

* **Relatórios e Estatísticas:**
    * Funcionalidade para gerar relatórios de uso de áreas comuns e desempenho de usuários.

---

### **Instruções para Rodar o Projeto**

#### **Pré-requisitos:**
* Java JDK 21+
* XAMPP (MySQL)
* IntelliJ IDEA ou outra IDE (recomendado IntelliJ para Java).

#### **Passos:**
1.  **Iniciar XAMPP:**
    * Inicie o MySQL na porta 3306.
2.  **Sincronizar com o GitHub:**
    * No terminal do IntelliJ, use `git pull origin main` para baixar a versão mais recente do projeto.
3.  **Criar o Banco de Dados:**
    * Acesse `http://localhost/phpmyadmin`
    * Execute o script `database/schema.sql` no phpMyAdmin.
4.  **Compilar e Executar:**
    * **Compilar (bash/cmd):**
        * `javac -encoding UTF-8 -cp "lib\mysql-connector-j-9.3.0.jar;src" src\Main.java src\Config\*.java src\DAO\*.java src\Model\*.java src\View\*.java`
    * **Executar (bash/cmd):**
        * `java -cp "lib\mysql-connector-j-9.3.0.jar;src" Main`

---

### **Próximas Etapas**
[ ] Inserir descrição na rejeição de reserva
[ ] Controle de conflitos para agendamento
[ ] Relatórios e estatísticas
