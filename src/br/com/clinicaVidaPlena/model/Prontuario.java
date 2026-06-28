package br.com.clinicaVidaPlena.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Prontuario {

    private String observacoes;
    private String diagnostico;
    private List<String> procedimentosRealizados;
    private LocalDate dataRegistro;

    // COMPOSIÇÃO (R8): construtor package-private - um Prontuario só pode
    // ser criado por dentro de Atendimento (mesmo pacote), nunca
    // externamente. Isso reforça que o Prontuario não existe sem o
    // Atendimento que o contém.
    Prontuario(String observacoes, String diagnostico) {
        this.observacoes = observacoes;
        this.diagnostico = diagnostico;
        this.procedimentosRealizados = new ArrayList<>();
        this.dataRegistro = LocalDate.now();
    }

    public void adicionarProcedimento(String procedimento) {
        procedimentosRealizados.add(procedimento);
    }

    public void adicionarProcedimentos(String[] procs, int quantidade) {
        for (int i = 0; i < quantidade; i++) {
            procedimentosRealizados.add(procs[i]);
        }
    }

    public String getObservacoes() {
        return observacoes;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public List<String> getProcedimentosRealizados() {
        return procedimentosRealizados;
    }

    public LocalDate getDataRegistro() {
        return dataRegistro;
    }
}