package br.com.clinicaVidaPlena.model.pessoa;

public class ClinicoGeral extends Profissional {
    private String encaminhamento;

    public ClinicoGeral(String nome) {
        super(nome, "clinica geral");
        this.encaminhamento = "Não informado";
    }

    public ClinicoGeral(String nome, String registro, double valor) {
        super(nome, "clinica geral", registro, valor);
        this.encaminhamento = "Não informado";
    }

    public ClinicoGeral(String nome, String registro, double valor, String[] dias, int totalDias) {
        super(nome, "clinica geral", registro, valor, dias, totalDias);
        this.encaminhamento = "Não informado";
    }

    public void registrarEncaminhamento(String encaminhamento) {
        this.encaminhamento = encaminhamento;
    }

    @Override
    public void registrarEspecifico(Object atendimento) {
        System.out.println("Registro específico para clínica geral");
    }

    @Override
    public String exibirResumo() {
        String resumo = super.exibirResumo();
        resumo = resumo + " | Encaminhamento: " + encaminhamento;
        return resumo;
    }
}