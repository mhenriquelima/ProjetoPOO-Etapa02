package br.com.clinicaVidaPlena.model.pessoa;

public class Nutricionista extends Profissional {
    private String planoAlimentar;

    public Nutricionista(String nome) {
        super(nome, "nutricao");
        this.planoAlimentar = "Não informado";
    }

    public Nutricionista(String nome, String registro, double valor) {
        super(nome, "nutricao", registro, valor);
        this.planoAlimentar = "Não informado";
    }

    public Nutricionista(String nome, String registro, double valor, String[] dias, int totalDias) {
        super(nome, "nutricao", registro, valor, dias, totalDias);
        this.planoAlimentar = "Não informado";
    }

    public void registrarPlanoAlimentar(String planoAlimentar) {
        this.planoAlimentar = planoAlimentar;
    }

    @Override
    public void registrarEspecifico(Object atendimento) {
        System.out.println("Registro específico para nutrição");
    }

    @Override
    public String exibirResumo() {
        String resumo = super.exibirResumo();
        resumo = resumo + " | Plano alimentar: " + planoAlimentar;
        return resumo;
    }
}