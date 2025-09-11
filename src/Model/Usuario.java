package Model;
public class Usuario {
    private int id;
    private String nomeCompleto;
    private String perfil;
    private String login;
    private String cpf;
    private String unidade;
    private String email;

    public Usuario(String nomeCompleto, String login, String perfil) {
        this.nomeCompleto = nomeCompleto;
        this.login = login;
        this.perfil = perfil;
    }

    // Construtor para ser usado no Login
    public Usuario(int id, String nomeCompleto, String perfil) {
        this.id = id;
        this.nomeCompleto = nomeCompleto;
        this.perfil = perfil;
    }

    // Construtor completo para uso do DAO
    public Usuario(int id, String nomeCompleto, String perfil, String login, String cpf, String unidade, String email) {
        this.id = id;
        this.nomeCompleto = nomeCompleto;
        this.perfil = perfil;
        this.login = login;
        this.cpf = cpf;
        this.unidade = unidade;
        this.email = email;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public String getPerfil() {
        return perfil;
    }

    public String getLogin() {
        return login;
    }

    public String getCpf() {
        return cpf;
    }

    public String getUnidade() {
        return unidade;
    }

    public String getEmail() {
        return email;
    }

    // Setters
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(int id) { this.id = id; }
}