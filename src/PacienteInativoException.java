public class PacienteInativoException extends Exception {
    public PacienteInativoException() {
        super();
    }
    public PacienteInativoException(String mensagem) {
        super(mensagem);
    }
    public String toString() {
        return "a seguinte excecao ocorreu: " + this.getClass().getName() + "\n"
             + "Mensagem: " + this.getMessage() + "\n";
    }
}