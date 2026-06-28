package br.com.clinicaVidaPlena.Exceptions;

public class ValorInvalidoException extends Exception {
    public ValorInvalidoException() {
        super();
    }
    public ValorInvalidoException(String mensagem) {
        super(mensagem);
    }
    public ValorInvalidoException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }

    @Override
    public String toString() {
        return "a seguinte excecao ocorreu: " + this.getClass().getName() + "\n"
            + "Mensagem: " + this.getMessage() + "\n";
    }
}