package br.com.clinicaVidaPlena.Exceptions;

public class ProfissionalNaoEncontradoException extends Exception {
    public ProfissionalNaoEncontradoException() {
        super();
    }
    public ProfissionalNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
    public String toString() {
        return "a seguinte excecao ocorreu: " + this.getClass().getName() + "\n"
             + "Mensagem: " + this.getMessage() + "\n";
    }
}