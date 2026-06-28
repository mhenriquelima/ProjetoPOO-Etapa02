package br.com.clinicaVidaPlena.model;
import br.com.clinicaVidaPlena.model.pagamento.Pagamento;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class Relatorio {

    // mostra todas as consultas
    public static void gerarRelatorio(ArrayList<Consulta> consultas, int totalConsultas,
                                      ArrayList<Atendimento> atendimentos, int totalAtendimentos) {
        System.out.println("\n=== RELATORIO GERAL ===");
        for (int i = 0; i < totalConsultas; i++) {
            System.out.println(consultas.get(i).exibirResumo());
            // verifica se tem diagnostico
            String diag = buscarDiagnostico(i, atendimentos);
            if (!diag.equals("")) {
                System.out.println("  Diagnostico: " + diag);
            }
            System.out.println("---");
        }
    }

    // filtra por profissional
    public static void gerarRelatorio(ArrayList<Consulta> consultas, int totalConsultas,
                                      ArrayList<Atendimento> atendimentos, int totalAtendimentos,
                                      String nomeProfissional) {
        System.out.println("\n=== RELATORIO - " + nomeProfissional + " ===");
        boolean achou = false;
        for (int i = 0; i < totalConsultas; i++) {
            if (consultas.get(i).nomeProfissional.equals(nomeProfissional)) {
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

    // filtra por periodo (data inicio e fim)
    public static void gerarRelatorio(ArrayList<Consulta> consultas, int totalConsultas,
                                      ArrayList<Atendimento> atendimentos, int totalAtendimentos,
                                      String dataInicio, String dataFim) {
        System.out.println("\n=== RELATORIO - " + dataInicio + " a " + dataFim + " ===");
        for (int i = 0; i < totalConsultas; i++) {
            if (estaNoIntervalo(consultas.get(i).data, dataInicio, dataFim)) {
                System.out.println(consultas.get(i).exibirResumo());
                String diag = buscarDiagnostico(i, atendimentos);
                if (!diag.equals("")) {
                    System.out.println("  Diagnostico: " + diag);
                }
                System.out.println("---");
            }
        }
    }

    // resumo financeiro do dia
    public static void gerarResumoFinanceiro(
        ArrayList<Consulta> consultas,
        int totalConsultas,
        List<Pagamento> pagamentos,
        ArrayList<Double> multas,
        int totalMultas) {

    // relatorio de cancelamentos
    public static void gerarRelatorioCancelamentos(List<Consulta> consultas) {
        System.out.println("\n=== RELATORIO DE CANCELAMENTOS ===");
        boolean achou = false;

    for (int i = 0; i < consultas.size(); i++) {
        if (consultas.get(i).status.equals("realizada")) {
            realizadas++;
        }

        if (consultas.get(i).status.equals("cancelada")) {
            canceladas++;
        }
    }

    public static void gerarRelatorioCancelamentos(Consulta[] consultas, int totalConsultas) {
        gerarRelatorioCancelamentos(converterConsultas(consultas, totalConsultas));
    }

    for (int i = 0; i < totalMultas; i++) {
        totalEmMultas += multas.get(i);
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
            if (consultas.get(i).status.equals("realizada")) realizadas++;
            if (consultas.get(i).status.equals("cancelada")) canceladas++;
        }

        for (int i = 0; i < pagamentos.size(); i++) {
            totalFaturado = totalFaturado + pagamentos.get(i).valorFinal;
        }

        if (multas != null) {
            for (double valorMulta : multas.values()) {
                totalEmMultas = totalEmMultas + valorMulta;
            }
        }
    }

    // busca diagnostico de um atendimento pelo indice da consulta
    public static String buscarDiagnostico(int indiceConsulta, ArrayList<Atendimento>atendimentos) {
        for (Atendimento atendimento : atendimentos) {
            if (atendimento.indiceConsulta == indiceConsulta) {
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
            mapa.put(atendimentos[i].indiceConsulta, atendimentos[i]);
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

            pagamento.valorFinal =
                    valorCalculado;

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
