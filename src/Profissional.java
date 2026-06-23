import java.util.ArrayList;
import java.util.List;

public class Profissional {
    public String nome;
    public String especialidade;
    public String registroProfissional;
    public double valorConsulta;
    // AGREGAÇÃO (R8)
    private List<HorarioDisponivel> horariosDisponiveis;    

    // so nome e especialidade
    public Profissional(String nome, String especialidade) {
        this.nome = nome;
        this.especialidade = especialidade;
        this.registroProfissional = "";
        this.valorConsulta = 0;
        this.horariosDisponiveis = new ArrayList<>();
    }

    public Profissional(String nome, String especialidade, String registroProfissional, double valorConsulta) {
        this.nome = nome;
        this.especialidade = especialidade;
        this.registroProfissional = registroProfissional;
        this.valorConsulta = valorConsulta;
        this.horariosDisponiveis = new ArrayList<>();
    }

    // construtor completo com dias
    public Profissional(String nome, String especialidade, String registroProfissional,
                        double valorConsulta, String[] dias, int totalDias) {
        this.nome = nome;
        this.especialidade = especialidade;
        this.registroProfissional = registroProfissional;
        this.valorConsulta = valorConsulta;
        this.horariosDisponiveis = new ArrayList<>();
        for (int i = 0; i < totalDias; i++) {
            horariosDisponiveis.add(new HorarioDisponivel(dias[i], "manha")
            );
        }
    }

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

    // verifica se o profissional atende naquele dia
    public boolean atendeNoDia(String dia) {

        for (HorarioDisponivel h : horariosDisponiveis) {

            if (h.getDiaDaSemana().equals(dia)) {
                return true;
            }
        }

        return false;
    }

    // valida as especialidades aceitas pela clinica
    public static boolean especialidadeValida(String esp) {
        if (esp.equals("clinica geral")) return true;
        if (esp.equals("fisioterapia")) return true;
        if (esp.equals("psicologia")) return true;
        if (esp.equals("nutricao")) return true;
        return false;
    }

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

    public static void reatribuirHorario(Profissional origem, Profissional destino, HorarioDisponivel horario) {
        origem.removerHorario(horario);
        destino.adicionarHorario(horario);
    }
}
