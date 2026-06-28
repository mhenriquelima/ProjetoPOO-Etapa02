package br.com.clinicaVidaPlena.model.pessoa;

import br.com.clinicaVidaPlena.model.Atendimento;

// classe psicologo herda da classe abstrata profissional
public class Psicologo extends Profissional {
    private String abordagem;

    // SOBRECARGA: 3 construtores com o mesmo nome (Psicologo) - recebe
    // apenas o nome
    public Psicologo(String nome) {
        super(nome, "psicologia");
        this.abordagem = "Não informada";
    }

    // recebe nome, registro e valor
    public Psicologo(String nome, String registro, double valor) {
        super(nome, "psicologia", registro, valor);
        this.abordagem = "Não informada";
    }

    // recebe tudo
    public Psicologo(String nome, String registro, double valor, String[] dias, int totalDias) {
        super(nome, "psicologia", registro, valor, dias, totalDias);
        this.abordagem = "Não informada";
    }

    public void definirAbordagem(String abordagem) {
        this.abordagem = abordagem;
    }

    // SOBRESCRITA: implementa o metodo abstrato registrarEspecifico()
    // declarado em Profissional, adicionando ao atendimento a informacao
    // particular da psicologia (a abordagem terapeutica usada).
    @Override
    public void registrarEspecifico(Atendimento atendimento) {
        atendimento.adicionarProcedimento("Sessao de psicoterapia - abordagem: " + abordagem);
    }

    // SOBRESCRITA: estende o exibirResumo() de Profissional, reaproveitando
    // (super.exibirResumo()) e completando com o dado especifico da classe.
    @Override
    public String exibirResumo() {
        String resumo = super.exibirResumo();
        resumo = resumo + " | abordagem: " + abordagem;
        return resumo;
    }
}