public class HorarioOcupadoException extends Exception {
    public HorarioOcupadoException() {
        super();
    }
    public HorarioOcupadoException(String mensagem) {
        super(mensagem);
    }
    public String toString() {
        return "a seguinte excecao ocorreu: " + this.getClass().getName() + "\n"
             + "Mensagem: " + this.getMessage() + "\n";
    }
}