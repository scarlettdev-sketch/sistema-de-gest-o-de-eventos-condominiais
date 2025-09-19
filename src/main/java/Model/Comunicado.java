package Model;

import java.time.LocalDateTime;

public class Comunicado {

    private int id;
    private String titulo;
    private String mensagem;
    private LocalDateTime dataPublicacao;
    private int usuarioId; // FK que referencia o usuário que publicou

    public Comunicado(int id, String titulo, String mensagem, LocalDateTime dataPublicacao, int usuarioId) {
        this.id = id;
        this.titulo = titulo;
        this.mensagem = mensagem;
        this.dataPublicacao = dataPublicacao;
        this.usuarioId = usuarioId;
    }

    public Comunicado(String titulo, String mensagem, LocalDateTime dataPublicacao, int usuarioId) {
        this.titulo = titulo;
        this.mensagem = mensagem;
        this.dataPublicacao = dataPublicacao;
        this.usuarioId = usuarioId;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public LocalDateTime getDataPublicacao() {
        return dataPublicacao;
    }

    public int getUsuarioId() {
        return usuarioId;
    }
}