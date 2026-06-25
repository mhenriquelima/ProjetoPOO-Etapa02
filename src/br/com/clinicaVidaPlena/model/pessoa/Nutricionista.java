package br.com.clinicaVidaPlena.model.pessoa;

public class Nutricionista extends Profissional {

    private int totalDietas;

    public Nutricionista(String nome) {
        super(nome, "nutricao");
        this.totalDietas = 0;
    }

    public Nutricionista(String nome, String registro, double valor) {
        super(nome, "nutricao", registro, valor);
        this.totalDietas = 0;
    }

    public Nutricionista(String nome, String registro, double valor, String[] dias, int totalDias) {
        super(nome, "nutricao", registro, valor, dias, totalDias);
        this.totalDietas = 0;
    }

    public void registrarDieta() {
        totalDietas++;
    }

    @Override
    public void registrarEspecifico(Object atendimento) {
        System.out.println("Registro específico para nutrição");
    }

    @Override
    public String exibirResumo() {
        return super.exibirResumo()
                + " | Dietas: " + totalDietas;
    }
}