package br.com.clinicaVidaPlena.Exceptions;

public class HorarioOcupadoException extends Exception {
    public HorarioOcupadoException() {
        super();
    }
    public HorarioOcupadoException(String mensagem) {
        super(mensagem);
    }
public HorarioOcupadoException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
    @Override
    public String toString() {
        return "a seguinte excecao ocorreu: " + this.getClass().getName() + "\n"
             + "Mensagem: " + this.getMessage() + "\n";
    }
}