import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public abstract class Pessoa {
    protected String nome;
    protected String cpf;
    protected String telefone;
    protected LocalDate dataNascimento;

    public Pessoa(String nome, String cpf, String telefone, String dataNascimento) {
        this.nome = nome;
        this.cpf = validarCpf(cpf);
        this.telefone = validarTelefone(telefone);
        setDataNascimento(dataNascimento);
    }

    public abstract String exibirResumo();

    public int calcularIdade() {
        if (this.dataNascimento == null) return 0;
        return Period.between(this.dataNascimento, LocalDate.now()).getYears();
    }


    private String validarCpf(String cpf) {
        if (cpf == null || cpf.trim().isEmpty()) {
            throw new IllegalArgumentException("O CPF não pode ser vazio ou nulo.");
        }
        String cpfLimpo = cpf.replaceAll("\\D", "");
        
        if (cpfLimpo.length() != 11) {
            throw new IllegalArgumentException("CPF inválido. Deve conter 11 dígitos.");
        }
        return cpfLimpo;
    }

    private String validarTelefone(String telefone) {
        if (telefone == null || telefone.trim().isEmpty()) {
            throw new IllegalArgumentException("O telefone não pode ser vazio ou nulo.");
        }
        return telefone;
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCpf() { return cpf; }
    
    public void setCpf(String cpf) {
        this.cpf = validarCpf(cpf);
    }

    public String getTelefone() { return telefone; }
    
    public void setTelefone(String telefone) {
        this.telefone = validarTelefone(telefone);
    }

    public String getDataNascimento() {
        if (this.dataNascimento == null) return "";
        return this.dataNascimento.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public void setDataNascimento(String dataNascimento) {
        if (dataNascimento == null || dataNascimento.trim().isEmpty()) {
            throw new IllegalArgumentException("A data de nascimento não pode ser vazia.");
        }
        try {
            this.dataNascimento = LocalDate.parse(dataNascimento, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        } catch (Exception e) {
            throw new IllegalArgumentException("Formato de data inválido. Use dd/MM/yyyy.");
        }
    }
}