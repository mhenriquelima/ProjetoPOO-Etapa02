public class PacienteNaoEncontradoException extends Exception {
    public PacienteNaoEncontradoException() {
        super();
    }
    public PacienteNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
    public String toString() {
        return "a seguinte excecao ocorreu: " + this.getClass().getName() + "\n"
             + "Mensagem: " + this.getMessage() + "\n";
    }
}