package br.com.clinicaVidaPlena.model.pagamento;

import br.com.clinicaVidaPlena.Exceptions.PagamentoInvalidoException;

public class TestePagamentos {

    public static void main(String[] args) {
        double valorBase = 200.0;

        Pagamento dinheiro =
                new PagamentoDinheiro(0, valorBase);

        try {
            Pagamento cartao =
                    new PagamentoCartao(
                            0,
                            valorBase,
                            6
                    );

            System.out.println(
                    "Mesmo valor base: R$" + valorBase
            );

            System.out.println(
                    "Dinheiro: R$"
                            + dinheiro.calcularValorFinal()
            );

            System.out.println(
                    "Cartao 6x: R$"
                            + cartao.calcularValorFinal()
            );

            // Teste proposital da excecao
            new PagamentoCartao(
                    0,
                    valorBase,
                    7
            );

        } catch (PagamentoInvalidoException e) {
            System.out.println(
                    "Erro esperado: " + e.getMessage()
            );
        }
    }
}