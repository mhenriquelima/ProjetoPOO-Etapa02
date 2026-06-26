package br.com.clinicaVidaPlena.model.pessoa;

import java.util.ArrayList;
import java.util.List;

import br.com.clinicaVidaPlena.model.HorarioDisponivel;

public abstract class Profissional extends Pessoa {
    public String especialidade;
    public String registroProfissional;
    public double valorConsulta;
    // AGREGAÇÃO (R8)
    private List<HorarioDisponivel> horariosDisponiveis;    

    public Profissional(String nome, String especialidade) {
        super(nome, "00000000000", "Não Informado", "01/01/1980");
        this.especialidade = especialidade;
        this.registroProfissional = "";
        this.valorConsulta = 0;
        this.horariosDisponiveis = new ArrayList<>();
    }

    public Profissional(String nome, String especialidade, String registroProfissional, double valorConsulta) {
        super(nome, "00000000000", "Não Informado", "01/01/1980");
        this.especialidade = especialidade;
        this.registroProfissional = registroProfissional;
        this.valorConsulta = valorConsulta;
        this.horariosDisponiveis = new ArrayList<>();
    }

    public Profissional(String nome, String especialidade, String registroProfissional, double valorConsulta, String[] dias, int totalDias) {
        super(nome, "00000000000", "Não Informado", "01/01/1980");
        this.especialidade = especialidade;
        this.registroProfissional = registroProfissional;
        this.valorConsulta = valorConsulta;
        this.horariosDisponiveis = new ArrayList<>();
        for (int i = 0; i < totalDias; i++) {
            horariosDisponiveis.add(new HorarioDisponivel(dias[i], "manha")
            );
        }
    }

    protected boolean validarRegistro() {
        return this.registroProfissional != null && !this.registroProfissional.trim().isEmpty();
    }

    public abstract void registrarEspecifico(Object atendimento);

    public void atualizar(String registro, double valor) {
        this.registroProfissional = registro;
        this.valorConsulta = valor;
    }

    public void atualizar(String registro, double valor, String[] dias, int totalDias) {
        this.registroProfissional = registro;
        this.valorConsulta = valor;
        this.horariosDisponiveis = new ArrayList<>();
        for (int i = 0; i < totalDias; i++) {
            horariosDisponiveis.add(new HorarioDisponivel(dias[i], "manha")
            );
        }
    }

    public boolean atendeNoDia(String dia) {

        for (HorarioDisponivel h : horariosDisponiveis) {

            if (h.getDiaDaSemana().equals(dia)) {
                return true;
            }
        }

        return false;
    }

    public boolean atendeNoHorario(String dia, String horario) {
        for (HorarioDisponivel h : horariosDisponiveis) {
            if (h.getDiaDaSemana().equals(dia) && horarioNoTurno(horario, h.getTurno())) {
                return true;
            }
        }
        return false;
    }

    private boolean horarioNoTurno(String horario, String turno) {
        int hora = Integer.parseInt(horario.substring(0, 2));

        if (turno.equals("manha")) {
            return hora >= 8 && hora < 12;
        }

        if (turno.equals("tarde")) {
            return hora >= 12 && hora < 18;
        }

        if (turno.equals("noite")) {
            return hora >= 18 && hora <= 21;
        }

        return false;
    }

    public static boolean especialidadeValida(String esp) {
        if (esp.equals("clinica geral")) return true;
        if (esp.equals("fisioterapia")) return true;
        if (esp.equals("psicologia")) return true;
        if (esp.equals("nutricao")) return true;
        return false;
    }

    @Override
    public String exibirResumo() {
        String dias = "";
    for (int i = 0; i < horariosDisponiveis.size(); i++) {
        if (i > 0) dias += ", ";
        dias += horariosDisponiveis.get(i).getDiaDaSemana();
    }
        return "Nome: " + nome + " | Espec: " + especialidade + " | Reg: " + registroProfissional
                + " | Valor: R$" + valorConsulta + " | Dias: " + dias;
    }

    public void adicionarHorario(HorarioDisponivel horario) {
    horariosDisponiveis.add(horario);
    }

    public void removerHorario(HorarioDisponivel horario) {
        horariosDisponiveis.remove(horario);
    }

    public List<HorarioDisponivel> getHorariosDisponiveis() {
        return horariosDisponiveis;
    }

    public static void reatribuirHorario(Profissional origem, Profissional destino, HorarioDisponivel horario) {
        origem.removerHorario(horario);
        destino.adicionarHorario(horario);
    }
}
