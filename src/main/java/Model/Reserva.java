package Model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reserva {

    private int id;
    private int usuarioId;
    private int areaComumId;
    private LocalDate data;
    private LocalTime horarioInicio;
    private LocalTime horarioFim;
    private String status;
    private String descricao;
    private String motivoRejeicao;

    // Construtor completo
    public Reserva(int id, int usuarioId, int areaComumId, LocalDate data, LocalTime horarioInicio, LocalTime horarioFim, String status, String descricao) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.areaComumId = areaComumId;
        this.data = data;
        this.horarioInicio = horarioInicio;
        this.horarioFim = horarioFim;
        this.status = status;
        this.descricao = descricao;
    }

    // Construtor sem ID para novas reservas
    public Reserva(int usuarioId, int areaComumId, LocalDate data, LocalTime horarioInicio, LocalTime horarioFim, String status, String descricao) {
        this.usuarioId = usuarioId;
        this.areaComumId = areaComumId;
        this.data = data;
        this.horarioInicio = horarioInicio;
        this.horarioFim = horarioFim;
        this.status = status;
        this.descricao = descricao;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public int getAreaComumId() {
        return areaComumId;
    }

    public void setAreaComumId(int areaComumId) {
        this.areaComumId = areaComumId;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalTime getHorarioInicio() {
        return horarioInicio;
    }

    public void setHorarioInicio(LocalTime horarioInicio) {
        this.horarioInicio = horarioInicio;
    }

    public LocalTime getHorarioFim() {
        return horarioFim;
    }

    public void setHorarioFim(LocalTime horarioFim) {
        this.horarioFim = horarioFim;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getMotivoRejeicao() {
        return motivoRejeicao;
    }

    public void setMotivoRejeicao(String motivoRejeicao) {
        this.motivoRejeicao = motivoRejeicao;
    }
}
