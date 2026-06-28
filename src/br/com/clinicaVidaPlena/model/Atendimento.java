package br.com.clinicaVidaPlena.model;

public class Atendimento implements Exportavel {
    private int indiceConsulta;
    // COMPOSIÇÃO (R8)
    private Prontuario prontuario;

    // registro basico - so observacoes
    public Atendimento(int indiceConsulta, String observacoes) {
        this.indiceConsulta = indiceConsulta;
        this.prontuario = new Prontuario(observacoes, "");
    }

    public Atendimento(int indiceConsulta, String observacoes, String diagnostico) {
        this.indiceConsulta = indiceConsulta;
        this.prontuario = new Prontuario(observacoes, diagnostico);
    }

    // registro completo com procedimentos ja definidos
    public Atendimento(int indiceConsulta, String observacoes, String diagnostico,
                       String[] procedimentos, int totalProcedimentos) {
        this.indiceConsulta = indiceConsulta;
        this.prontuario = new Prontuario(observacoes, diagnostico);
        this.prontuario.adicionarProcedimentos(procedimentos, totalProcedimentos);
    }

    // adiciona um por vez
    public void adicionarProcedimento(String procedimento) {
        prontuario.adicionarProcedimento(procedimento);
    }

    // adiciona varios de uma vez
    public void adicionarProcedimento(String[] procs, int quantidade) {
        prontuario.adicionarProcedimentos(procs, quantidade);
    }

    public String exibirResumo() {

        String resumo = "Observacoes: " + prontuario.getObservacoes();

        if (!prontuario.getDiagnostico().equals("")) {
            resumo += "\nDiagnostico: " + prontuario.getDiagnostico();
        }

        if (!prontuario.getProcedimentosRealizados().isEmpty()) {

            resumo += "\nProcedimentos: ";

            for (int i = 0; i < prontuario.getProcedimentosRealizados().size(); i++) {

                resumo += prontuario.getProcedimentosRealizados().get(i);

                if (i < prontuario.getProcedimentosRealizados().size() - 1) {
                    resumo += ", ";
                }
            }
        }

        return resumo;
    }
    
    public String getDiagnostico() {
        return prontuario.getDiagnostico();
    }

    public int getIndiceConsulta() {
        return indiceConsulta;
    }

   @Override
    public String exportarDados() {
        return exibirResumo();
    }

}