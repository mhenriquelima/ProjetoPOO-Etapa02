package br.com.clinicaVidaPlena.Exceptions;

public class PacienteInativoException extends Exception {
    public PacienteInativoException() {
        super();
    }
    public PacienteInativoException(String mensagem) {
        super(mensagem);
    }
public PacienteInativoException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
    @Override
    public String toString() {
        return "a seguinte excecao ocorreu: " + this.getClass().getName() + "\n"
             + "Mensagem: " + this.getMessage() + "\n";
    }
}