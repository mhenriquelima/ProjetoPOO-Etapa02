package br.com.clinicaVidaPlena.model;

import br.com.clinicaVidaPlena.model.pagamento.Pagamento;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Relatorio {

    // mostra todas as consultas
    public static void gerarRelatorio(List<Consulta> consultas, Map<Integer, Atendimento> atendimentos) {
        System.out.println("\n=== RELATORIO GERAL ===");
        for (int i = 0; i < consultas.size(); i++) {
            System.out.println(consultas.get(i).exibirResumo());
            // verifica se tem diagnostico
            String diag = buscarDiagnostico(i, atendimentos);
            if (!diag.equals("")) {
                System.out.println("  Diagnostico: " + diag);
            }
            System.out.println("---");
        }
    }

    public static void gerarRelatorio(ArrayList<Consulta> consultas, int totalConsultas,
                                      ArrayList<Atendimento> atendimentos, int totalAtendimentos) {
        gerarRelatorio(consultas, converterAtendimentos(atendimentos, totalAtendimentos));
    }

    // filtra por profissional
    public static void gerarRelatorio(List<Consulta> consultas, Map<Integer, Atendimento> atendimentos,
                                      String nomeProfissional) {
        System.out.println("\n=== RELATORIO - " + nomeProfissional + " ===");
        boolean achou = false;
        for (int i = 0; i < consultas.size(); i++) {
            if (consultas.get(i).getNomeProfissional().equals(nomeProfissional)) {
                System.out.println(consultas.get(i).exibirResumo());
                String diag = buscarDiagnostico(i, atendimentos);
                if (!diag.equals("")) {
                    System.out.println("  Diagnostico: " + diag);
                }
                System.out.println("---");
                achou = true;
            }
        }
        if (!achou) {
            System.out.println("Nenhuma consulta encontrada para esse profissional.");
        }
    }

    public static void gerarRelatorio(ArrayList<Consulta> consultas, int totalConsultas,
                                      ArrayList<Atendimento> atendimentos, int totalAtendimentos,
                                      String nomeProfissional) {
        gerarRelatorio(consultas, converterAtendimentos(atendimentos, totalAtendimentos), nomeProfissional);
    }

    // filtra por periodo (data inicio e fim)
    public static void gerarRelatorio(List<Consulta> consultas, Map<Integer, Atendimento> atendimentos,
                                      String dataInicio, String dataFim) {
        System.out.println("\n=== RELATORIO - " + dataInicio + " a " + dataFim + " ===");
        for (int i = 0; i < consultas.size(); i++) {
            if (estaNoIntervalo(consultas.get(i).getData(), dataInicio, dataFim)) {
                System.out.println(consultas.get(i).exibirResumo());
                String diag = buscarDiagnostico(i, atendimentos);
                if (!diag.equals("")) {
                    System.out.println("  Diagnostico: " + diag);
                }
                System.out.println("---");
            }
        }
    }

    public static void gerarRelatorio(ArrayList<Consulta> consultas, int totalConsultas,
                                      ArrayList<Atendimento> atendimentos, int totalAtendimentos,
                                      String dataInicio, String dataFim) {
        gerarRelatorio(consultas, converterAtendimentos(atendimentos, totalAtendimentos), dataInicio, dataFim);
    }

    // relatorio de cancelamentos
    public static void gerarRelatorioCancelamentos(List<Consulta> consultas) {
        System.out.println("\n=== RELATORIO DE CANCELAMENTOS ===");
        boolean achou = false;

        for (int i = 0; i < consultas.size(); i++) {
            if (consultas.get(i).getStatus().equals("cancelada")) {
                System.out.println(consultas.get(i).exibirResumo());
                System.out.println("---");
                achou = true;
            }
        }

        if (!achou) {
            System.out.println("Nenhuma consulta cancelada.");
        }
    }

    public static void gerarRelatorioCancelamentos(Consulta[] consultas, int totalConsultas) {
        gerarRelatorioCancelamentos(converterConsultas(consultas, totalConsultas));
    }

    // relatorio de multas aplicadas
    public static void gerarRelatorioMultasAplicadas(Map<Integer, Double> multas) {
        System.out.println("\n=== MULTAS APLICADAS ===");

        if (multas.isEmpty()) {
            System.out.println("Nenhuma multa aplicada.");
            return;
        }

        double totalEmMultas = 0;

        for (double valorMulta : multas.values()) {
            System.out.println("Multa: R$" + valorMulta);
            totalEmMultas += valorMulta;
        }

        System.out.println("Total em multas: R$" + totalEmMultas);
    }

    public static void gerarRelatorioMultasAplicadas(double[] multas, int totalMultas) {
        gerarRelatorioMultasAplicadas(converterMultas(multas, totalMultas));
    }

    // resumo financeiro do dia
    public static void gerarResumoFinanceiro(List<Consulta> consultas,
                                             List<Pagamento> pagamentos,
                                             Map<Integer, Double> multas) {
        int realizadas = 0;
        int canceladas = 0;
        double totalFaturado = 0;
        double totalEmMultas = 0;

        for (int i = 0; i < consultas.size(); i++) {
            if (consultas.get(i).getStatus().equals("realizada")) realizadas++;
            if (consultas.get(i).getStatus().equals("cancelada")) canceladas++;
        }

        for (int i = 0; i < pagamentos.size(); i++) {
            totalFaturado = totalFaturado + pagamentos.get(i).getValorFinal();
        }

        if (multas != null) {
            for (double valorMulta : multas.values()) {
                totalEmMultas = totalEmMultas + valorMulta;
            }
        }

        System.out.println("\n=== RESUMO FINANCEIRO ===");
        System.out.println("Consultas realizadas: " + realizadas);
        System.out.println("Consultas canceladas: " + canceladas);
        System.out.println("Total faturado: R$" + totalFaturado);
        System.out.println("Total em multas: R$" + totalEmMultas);
        System.out.println("Total liquido: R$" + (totalFaturado - totalEmMultas));
    }

    // busca diagnostico de um atendimento pelo indice da consulta
    public static String buscarDiagnostico(int indiceConsulta, Map<Integer, Atendimento> atendimentos) {
        Atendimento atendimento = atendimentos.get(indiceConsulta);
        if (atendimento == null) {
            return "";
        }
        return atendimento.getDiagnostico();
    }

    public static String buscarDiagnostico(int indiceConsulta, ArrayList<Atendimento> atendimentos) {
        for (Atendimento atendimento : atendimentos) {
            if (atendimento.getIndiceConsulta() == indiceConsulta) {
                return atendimento.getDiagnostico();
            }
        }
        return "";
    }

    public static String buscarDiagnostico(int indiceConsulta, Atendimento[] atendimentos, int total) {
        return buscarDiagnostico(indiceConsulta, converterAtendimentos(atendimentos, total));
    }

    private static List<Consulta> converterConsultas(Consulta[] consultas, int totalConsultas) {
        List<Consulta> lista = new ArrayList<>();

        for (int i = 0; i < totalConsultas; i++) {
            lista.add(consultas[i]);
        }

        return lista;
    }

    private static List<Pagamento> converterPagamentos(Pagamento[] pagamentos, int totalPagamentos) {
        List<Pagamento> lista = new ArrayList<>();

        for (int i = 0; i < totalPagamentos; i++) {
            lista.add(pagamentos[i]);
        }

        return lista;
    }

    private static Map<Integer, Atendimento> converterAtendimentos(Atendimento[] atendimentos, int totalAtendimentos) {
        Map<Integer, Atendimento> mapa = new LinkedHashMap<>();

        for (int i = 0; i < totalAtendimentos; i++) {
            mapa.put(atendimentos[i].getIndiceConsulta(), atendimentos[i]);
        }

        return mapa;
    }

    private static Map<Integer, Atendimento> converterAtendimentos(ArrayList<Atendimento> atendimentos, int totalAtendimentos) {
        Map<Integer, Atendimento> mapa = new LinkedHashMap<>();

        for (int i = 0; i < totalAtendimentos && i < atendimentos.size(); i++) {
            mapa.put(atendimentos.get(i).getIndiceConsulta(), atendimentos.get(i));
        }

        return mapa;
    }

    private static Map<Integer, Double> converterMultas(double[] multas, int totalMultas) {
        Map<Integer, Double> mapa = new LinkedHashMap<>();

        for (int i = 0; i < totalMultas; i++) {
            mapa.put(i, multas[i]);
        }

        return mapa;
    }

    // compara datas convertendo pra numero inteiro (AAAAMMDD)
    public static boolean estaNoIntervalo(String data, String inicio, String fim) {
        int valorData = converterDataParaNumero(data);
        int valorInicio = converterDataParaNumero(inicio);
        int valorFim = converterDataParaNumero(fim);
        return valorData >= valorInicio && valorData <= valorFim;
    }

    // converte DD/MM/AAAA pra um numero tipo 20260519 pra poder comparar
    private static int converterDataParaNumero(String data) {
        int dia = Integer.parseInt(data.substring(0, 2));
        int mes = Integer.parseInt(data.substring(3, 5));
        int ano = Integer.parseInt(data.substring(6, 10));
        return ano * 10000 + mes * 100 + dia;
    }
    public static void gerarRelatorioPagamentos(
        List<Pagamento> pagamentos) {

        System.out.println(
                "\n=== RELATORIO DE PAGAMENTOS ==="
        );

        if (pagamentos.isEmpty()) {
            System.out.println(
                    "Nenhum pagamento registrado."
            );
            return;
        }

        double total = 0.0;

        for (Pagamento pagamento : pagamentos) {

            // LIGACAO DINAMICA conforme R5
            double valorCalculado =
                    pagamento.calcularValorFinal();

            pagamento.setValorFinal(valorCalculado);

            System.out.println(
                    pagamento.getClass()
                            .getSimpleName()
                            + " -> "
                            + pagamento.exibirResumo()
            );

            total += valorCalculado;
        }

        total =
                Math.round(total * 100.0) / 100.0;

        System.out.println(
                "Total dos pagamentos: R$" + total
        );
    }
}