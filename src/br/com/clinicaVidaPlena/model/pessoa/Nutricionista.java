package br.com.clinicaVidaPlena.model.pessoa;

import br.com.clinicaVidaPlena.model.Atendimento;

public class Nutricionista extends Profissional {
    private String planoAlimentar;

    // SOBRECARGA: 3 construtores com o mesmo nome (Nutricionista) - recebe
    // apenas o nome
    public Nutricionista(String nome) {
        super(nome, "nutricao");
        this.planoAlimentar = "Não informado";
    }

    // recebe nome, registro e valor
    public Nutricionista(String nome, String registro, double valor) {
        super(nome, "nutricao", registro, valor);
        this.planoAlimentar = "Não informado";
    }

    // recebe tudo
    public Nutricionista(String nome, String registro, double valor, String[] dias, int totalDias) {
        super(nome, "nutricao", registro, valor, dias, totalDias);
        this.planoAlimentar = "Não informado";
    }

    public void registrarPlanoAlimentar(String planoAlimentar) {
        this.planoAlimentar = planoAlimentar;
    }

    // SOBRESCRITA: implementa o metodo abstrato registrarEspecifico()
    // declarado em Profissional, adicionando ao atendimento a informacao
    // particular da nutricao (o plano alimentar definido).
    @Override
    public void registrarEspecifico(Atendimento atendimento) {
        atendimento.adicionarProcedimento("Avaliacao nutricional - plano: " + planoAlimentar);
    }

    // SOBRESCRITA: estende o exibirResumo() de Profissional, reaproveitando
    // (super.exibirResumo()) e completando com o dado especifico da classe.
    @Override
    public String exibirResumo() {
        String resumo = super.exibirResumo();
        resumo = resumo + " | Plano alimentar: " + planoAlimentar;
        return resumo;
    }
}