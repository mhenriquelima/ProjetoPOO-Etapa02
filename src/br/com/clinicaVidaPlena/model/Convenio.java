package br.com.clinicaVidaPlena.model;

import java.util.ArrayList;
import java.util.List;

public class Convenio {

    private String nome;
    private double percentualCobertura;
    private List<String> especialidadesCobertas;

    public Convenio(String nome, double percentualCobertura) {
        this.nome = nome;
        this.percentualCobertura = percentualCobertura;
        this.especialidadesCobertas = new ArrayList<>();
    }

    public void adicionarEspecialidade(String especialidade) {
        especialidadesCobertas.add(especialidade);
    }

    public boolean cobreEspecialidade(String especialidade) {
        return especialidadesCobertas.contains(especialidade);
    }

    public String getNome() {
        return nome;
    }

    public double getPercentualCobertura() {
        return percentualCobertura;
    }
}