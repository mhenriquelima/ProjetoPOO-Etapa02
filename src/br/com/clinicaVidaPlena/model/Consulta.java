package br.com.clinicaVidaPlena.model;

// extends é usado quando uma classe herda outra classe
// implements é usado quando uma classe segue uma ou mais interfaces
// Java permite apenas 1 extends, mas múltiplos implements

public class Consulta implements Agendavel, Exportavel {
    private String cpfPaciente;
    protected String nomeProfissional;
    protected String data;
    private String horario;
    private String tipo;
    protected String status;

    // sem tipo - assume inicial
    public Consulta(String cpfPaciente, String nomeProfissional, String data, String horario) {
        this.cpfPaciente = cpfPaciente;
        this.nomeProfissional = nomeProfissional;
        this.data = data;
        this.horario = horario;
        this.tipo = "inicial";
        this.status = "agendada";
    }

    public Consulta(String cpfPaciente, String nomeProfissional, String data, String horario, String tipo) {
        this.cpfPaciente = cpfPaciente;
        this.nomeProfissional = nomeProfissional;
        this.data = data;
        this.horario = horario;
        this.tipo = tipo;
        this.status = "agendada";
    }

    // esse aqui a gente usa na remarcacao pra poder setar o status direto
    public Consulta(String cpfPaciente, String nomeProfissional, String data,
                    String horario, String tipo, String status) {
        this.cpfPaciente = cpfPaciente;
        this.nomeProfissional = nomeProfissional;
        this.data = data;
        this.horario = horario;
        this.tipo = tipo;
        this.status = status;
    }

    @Override
    public void cancelar() {
        this.status = "cancelada";
    }

    @Override
    public void agendar() {
    this.status = "agendada";
    }

    // cancelar com motivo - retorna a msg formatada
    public String cancelar(String motivo) {
        this.status = "cancelada";
        return "Consulta cancelada. Motivo: " + motivo;
    }

    @Override
    public void remarcar() {
        this.status = "remarcada";
    }

    public void realizar() {
        this.status = "realizada";
    }

    public String getCpfPaciente() {
        return cpfPaciente;
    }

    public String getNomeProfissional() {
        return nomeProfissional;
    }

    public String getData() {
        return data;
    }

    public String getHorario() {
        return horario;
    }

    public String getTipo() {
        return tipo;
    }

    public String getStatus() {
        return status;
    }

    public String exibirResumo() {
        return "Paciente(CPF): " + cpfPaciente + " | Prof: " + nomeProfissional
                + " | Data: " + data + " | Hora: " + horario
                + " | Tipo: " + tipo + " | Status: " + status;
    }
    @Override
    public String exportarDados() {
        return exibirResumo();
    }

}