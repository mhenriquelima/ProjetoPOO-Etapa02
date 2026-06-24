package br.com.clinicaVidaPlena.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Prontuario {

    String observacoes;
    String diagnostico;
    List<String> procedimentosRealizados;
    LocalDate dataRegistro;

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
}