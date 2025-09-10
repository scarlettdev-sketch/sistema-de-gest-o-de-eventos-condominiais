### **Relatório do Projeto - Sistema de Gestão de Eventos Condominiais**

**Status Atual do Projeto - 10/09/2025**

#### **O que foi IMPLEMENTADO:**
* **Arquitetura:**
    * **Model:** Classes `Usuario` e `AreaComum` com atributos completos.
    * **View:** `TelaLogin`, `TelaGestaoAreas` e as novas telas `PainelAdmin` e `GerenciarUsuariosGUI` (interface gráfica).
    * **DAO:** `UsuarioDAO` e `AreaComumDAO` (acesso ao banco).
    * **Config:** `DatabaseInitializer` e `ConexaoBD` (configuração).
* **Banco de Dados MySQL:**
    * Estrutura completa da tabela `usuarios`.
    * Dados de teste inseridos.
    * Conexão com XAMPP.
* **Sistema de Autenticação:**
    * Login funcional (admin/morador).
    * Validação de credenciais.
    * Mensagem de boas-vindas.
* **Configuração do Projeto:**
    * Driver MySQL configurado.
    * Estrutura de pacotes organizada.
    * Compilação e execução funcionais.
    * **Gerenciamento de Versão:** Repositório Git configurado e sincronizado com o GitHub.

---

#### **O que ainda FALTA IMPLEMENTAR:**
* **Telas Principais:**
    * Continuar o dashboard do administrador.
    * Gestão completa de áreas comuns (CRUD).
    * Tela de cadastro de usuários (lógica de back-end).
    * Relatórios e estatísticas.
* **Funcionalidades de Áreas Comuns:**
    * Listar áreas disponíveis.
    * Adicionar/editar/remover áreas.
    * Definir regras e taxas.
    * Status de disponibilidade.
* **Sistema de Reservas:**
    * Calendário de reservas.
    * Agendamento de áreas.
    * Controle de conflitos.

---

### **Instruções para Rodar o Projeto**

#### **Pré-requisitos:**
* Java JDK 21+
* XAMPP (MySQL)
* IntelliJ IDEA ou outra IDE (recomendado IntelliJ para Java).

#### **Passos:**
1.  **Iniciar XAMPP:**
    * Inicie o MySQL na porta 3306.
    * *(Dica: Caso de erro para iniciar o MySQL no XAMPP, abra o gerenciador de tarefas (`Ctrl + Alt + Del`) e encerre as tarefas `mysql` ou `sql` em aberto. É um erro comum que pode causar dor de cabeça.)*
2.  **Sincronizar com o GitHub:**
    * No terminal do IntelliJ, use `git pull origin main` para baixar a versão mais recente do projeto.
3.  **Criar o Banco de Dados:**
    * Acesse `http://localhost/phpmyadmin`
    * Execute o script `database/schema.sql` no phpMyAdmin.
4.  **Compilar e Executar:**
    * **Compilar (bash/cmd):**
        * `javac -encoding UTF-8 -cp "lib\mysql-connector-j-9.3.0.jar;src" src\Main.java src\Config*.java src\DAO*.java src\Model*.java src\View*.java`
    * **Executar (bash/cmd):**
        * `java -cp "lib\mysql-connector-j-9.3.0.jar;src" Main`

#### **Credenciais de Teste:**
* **Admin:** admin / admin123
* **Morador:** joao / 123456
* **Morador:** maria / 123456
* **Funcionário:** carlos / func123

---

### **Resolução de Problemas Comuns**

#### **Problemas com bibliotecas no IntelliJ:**
* **Adicionar a biblioteca MySQL:**
    1.  Abra `File → Project Structure` (ou `Ctrl + Alt + Shift + S`).
    2.  Em `Libraries`, adicione o arquivo `lib/mysql-connector-j-9.3.0.jar`.
    3.  Vincule a biblioteca ao módulo `main` em `Modules` → `Dependencies`.
* **Configurar Encoding UTF-8:**
    1.  Abra `File → Settings` (ou `Ctrl + Alt + S`).
    2.  Vá para `Editor → File Encodings`.
    3.  Defina `Global Encoding` e `Project Encoding` para `UTF-8`.
    4.  Marque `✅ Transparent native-to-ascii conversion`.

#### **Remover arquivos `.class` (se necessário):**
* **PowerShell:**
    * `Get-ChildItem -Path src -Recurse -Filter "*.class" | Remove-Item -Force`
* **Cmd:**
    * `cmd /c "del /s src\*.class"`

---

### **Próximas Etapas**
[ ] Dashboard do administrador
[ ] CRUD de áreas comuns
[ ] Sistema de reservas
[ ] Relatórios

