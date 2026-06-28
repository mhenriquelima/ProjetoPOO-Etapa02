package br.com.clinicaVidaPlena.Exceptions;

// excecao generica para operacoes que nao fazem sentido no estado atual
// do sistema (ex: tentar parcelar um pagamento em dinheiro, tentar
// realizar atendimento de uma consulta que nao esta agendada, etc.)
public class OperacaoInvalidaException extends Exception {
    public OperacaoInvalidaException() {
        super();
    }

    public OperacaoInvalidaException(String mensagem) {
        super(mensagem);
    }

    public OperacaoInvalidaException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }

    @Override
    public String toString() {
        return "a seguinte excecao ocorreu: " + this.getClass().getName() + "\n"
             + "Mensagem: " + this.getMessage() + "\n";
    }
}