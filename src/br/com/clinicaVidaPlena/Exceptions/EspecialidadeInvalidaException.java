package br.com.clinicaVidaPlena.Exceptions;

public class EspecialidadeInvalidaException extends Exception {
    public EspecialidadeInvalidaException() {
        super();
    }
    public EspecialidadeInvalidaException(String mensagem) {
        super(mensagem);
    }
    public String toString() {
        return "a seguinte excecao ocorreu: " + this.getClass().getName() + "\n"
             + "Mensagem: " + this.getMessage() + "\n";
    }
}