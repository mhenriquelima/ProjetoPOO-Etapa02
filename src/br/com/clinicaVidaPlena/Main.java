package br.com.clinicaVidaPlena;
import br.com.clinicaVidaPlena.Exceptions.ConvenioNaoCobreException;
import br.com.clinicaVidaPlena.Exceptions.EspecialidadeInvalidaException;
import br.com.clinicaVidaPlena.Exceptions.HorarioIndisponivelException;
import br.com.clinicaVidaPlena.Exceptions.OperacaoInvalidaException;
import br.com.clinicaVidaPlena.Exceptions.PacienteInativoException;
import br.com.clinicaVidaPlena.Exceptions.PacienteNaoEncontradoException;
import br.com.clinicaVidaPlena.Exceptions.PagamentoInvalidoException;
import br.com.clinicaVidaPlena.Exceptions.ProfissionalNaoEncontradoException;
import br.com.clinicaVidaPlena.Exceptions.ValorInvalidoException;
import br.com.clinicaVidaPlena.model.Agendavel;
import br.com.clinicaVidaPlena.model.Atendimento;
import br.com.clinicaVidaPlena.model.Consulta;
import br.com.clinicaVidaPlena.model.Convenio;
import br.com.clinicaVidaPlena.model.pessoa.Paciente;
import br.com.clinicaVidaPlena.model.Relatorio;
import br.com.clinicaVidaPlena.model.pagamento.Pagamento;
import br.com.clinicaVidaPlena.model.pagamento.PagamentoCartao;
import br.com.clinicaVidaPlena.model.pagamento.PagamentoConvenio;
import br.com.clinicaVidaPlena.model.pagamento.PagamentoDinheiro;
import br.com.clinicaVidaPlena.model.pessoa.ClinicoGeral;
import br.com.clinicaVidaPlena.model.pessoa.Fisioterapeuta;
import br.com.clinicaVidaPlena.model.pessoa.Nutricionista;
import br.com.clinicaVidaPlena.model.pessoa.Pessoa;
import br.com.clinicaVidaPlena.model.pessoa.Profissional;
import br.com.clinicaVidaPlena.model.pessoa.Psicologo;
import br.com.clinicaVidaPlena.model.Exportavel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;

public class Main {

    // List foi escolhida porque mantém a ordem de inserção e permite acesso por índice
    static ArrayList<Paciente> pacientes = new ArrayList<>();
    static ArrayList<Profissional> profissionais = new ArrayList<>();
    static ArrayList<Consulta> consultas = new ArrayList<>();
    static ArrayList<Atendimento> atendimentos = new ArrayList<>();
    static ArrayList<Double> multas = new ArrayList<>();

    // Set foi escolhido para garantir que não existam CPFs duplicados.
    static HashSet<String> cpfs = new HashSet<>();

    // Map foi escolhido para permitir busca rápida de pacientes pelo CPF.
    static Map<String, Paciente> pacientesPorCpf = new HashMap<>();

    // Lista polimorfica: armazena qualquer subclasse de Pagamento.
    static List<Pagamento> pagamentos = new ArrayList<>();
    static List<Pessoa> pessoas = new ArrayList<Pessoa>();

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int opcao = -1;

        while (opcao != 0) {
            System.out.println("\n=== CLINICA VIDAPLENA ===");
            System.out.println("1 - Pacientes");
            System.out.println("2 - Profissionais");
            System.out.println("3 - Consultas");
            System.out.println("4 - Atendimentos");
            System.out.println("5 - Pagamentos");
            System.out.println("6 - Relatorios");
            System.out.println("0 - Sair");
            opcao = lerInteiro("Escolha: ");

            switch (opcao) {
                case 1:
                    menuPacientes();
                    break;
                case 2:
                    menuProfissionais();
                    break;
                case 3:
                    menuConsultas();
                    break;
                case 4:
                    menuAtendimentos();
                    break;
                case 5:
                    menuPagamentos();
                    break;
                case 6:
                    menuRelatorios();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opcao invalida!");
                    break;
            }
        }

        sc.close();
        System.out.println("Sistema encerrado.");
    }

    /**
     * Le um inteiro do teclado, validando a entrada.
     * Trata NumberFormatException (texto onde se espera numero): exibe
     * mensagem amigavel e pede novamente, sem encerrar o programa.
     */
    public static int lerInteiro(String mensagem) {
        int valor = 0;
        boolean valido = false;

        while (!valido) {
            try {
                System.out.print(mensagem);
                valor = Integer.parseInt(sc.nextLine());
                valido = true;
            } catch (NumberFormatException e) {
                System.out.println("Entrada invalida. Digite um numero inteiro.");
            } finally {
                // finally com proposito real: garante que toda tentativa de
                // leitura (com sucesso ou nao) seja registrada no log.
                System.out.println("--- Tentativa de leitura de inteiro finalizada ---");
            }
        }

        return valor;
    }

    /**
     * Le um double do teclado, validando a entrada.
     * Mesma logica de lerInteiro, mas para valores monetarios.
     */
    public static double lerDouble(String mensagem) {
        double valor = 0;
        boolean valido = false;

        while (!valido) {
            try {
                System.out.print(mensagem);
                valor = Double.parseDouble(sc.nextLine());
                valido = true;
            } catch (NumberFormatException e) {
                System.out.println("Entrada invalida. Digite um valor numerico (ex: 150.00).");
            } finally {
                System.out.println("--- Tentativa de leitura de valor finalizada ---");
            }
        }

        return valor;
    }

    // -------------------------------------------------------------------------
    // PACIENTES
    // -------------------------------------------------------------------------

    public static void menuPacientes() {
        int op = -1;

        while (op != 0) {
            System.out.println("\n--- PACIENTES ---");
            System.out.println("1 - Cadastrar");
            System.out.println("2 - Complementar cadastro");
            System.out.println("3 - Buscar por CPF");
            System.out.println("4 - Listar todos");
            System.out.println("5 - Desativar");
            System.out.println("0 - Voltar");
            op = lerInteiro("Opcao: ");

            switch (op) {
                case 1:
                    cadastrarPaciente();
                    break;
                case 2:
                    complementarPaciente();
                    break;
                case 3:
                    buscarPaciente();
                    break;
                case 4:
                    listarPacientes();
                    break;
                case 5:
                    desativarPaciente();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opcao invalida!");
                    break;
            }
        }
    }

    public static void cadastrarPaciente() {
        System.out.print("Nome: ");
        String nome = sc.nextLine();

        System.out.print("CPF: ");
        String cpf = sc.nextLine();

        if (!cpfs.add(cpf)) {
            System.out.println("CPF já cadastrado.");
            return;
        }

        int tipo = lerInteiro("Tipo (1-Minimo / 2-Com idade e tel / 3-Completo): ");

        Paciente novoPaciente;

         Paciente paciente;

        if (tipo == 1) {
            paciente = new Paciente(nome, cpf);

        } else if (tipo == 2) {
            int idade = lerInteiro("Idade: ");

            System.out.print("Telefone: ");
            String telefone = sc.nextLine();

            paciente = new Paciente(nome, cpf, idade, telefone);

        } else if (tipo == 3) {
            int idade = lerInteiro("Idade: ");

            System.out.print("Telefone: ");
            String telefone = sc.nextLine();

            Convenio convenio = cadastrarConvenioDoPaciente();

            paciente = new Paciente(nome, cpf, idade, telefone, convenio);
            

        } else {
            System.out.println("Tipo de cadastro invalido.");
            return;
        }
        pacientes.add(paciente);
        pessoas.add(paciente);
        pacientesPorCpf.put(paciente.getCpf(), paciente);
        
        System.out.println("Paciente cadastrado com sucesso!");
    }

    public static void complementarPaciente() {
        System.out.print("CPF: ");
        String cpf = sc.nextLine();

        int idx = buscarIndicePaciente(cpf);

        if (idx == -1) {
            System.out.println("Paciente nao encontrado.");
            return;
        }

        int tipo = lerInteiro("Vai informar convenio? (1-Nao / 2-Sim): ");

        int idade = lerInteiro("Idade: ");

        System.out.print("Telefone: ");
        String telefone = sc.nextLine();

        if (tipo == 1) {
            pacientes.get(idx).complementar(idade, telefone);

        } else if (tipo == 2) {
            Convenio convenio = cadastrarConvenioDoPaciente();
            pacientes.get(idx).complementar(idade, telefone, convenio);

        } else {
            System.out.println("Opcao invalida.");
            return;
        }

        System.out.println("Cadastro atualizado!");
    }

    /**
     * Cria um convenio usando os percentuais definidos no enunciado.
     * Como o enunciado nao define exatamente quais especialidades cada plano
     * cobre, elas sao informadas durante o cadastro.
     */
    public static Convenio cadastrarConvenioDoPaciente() {
        System.out.println("\nConvenios disponiveis:");
        System.out.println("1 - SaudePlus (40%)");
        System.out.println("2 - VidaMais (30%)");
        System.out.println("3 - BemEstar (50%)");
        System.out.println("0 - Sem convenio");
        int opcao = lerInteiro("Escolha: ");

        String nome;
        double percentual;

        switch (opcao) {
            case 1:
                nome = "SaudePlus";
                percentual = 40.0;
                break;
            case 2:
                nome = "VidaMais";
                percentual = 30.0;
                break;
            case 3:
                nome = "BemEstar";
                percentual = 50.0;
                break;
            case 0:
                return null;
            default:
                System.out.println("Convenio invalido. Paciente ficara sem convenio.");
                return null;
        }

        Convenio convenio = new Convenio(nome, percentual);

        int quantidade = lerInteiro("Quantas especialidades esse convenio cobre? ");

        for (int i = 0; i < quantidade; i++) {
            System.out.print("Especialidade " + (i + 1) + ": ");
            String especialidade = sc.nextLine().trim().toLowerCase();
            convenio.adicionarEspecialidade(especialidade);
        }

        return convenio;
    }

    public static void buscarPaciente() {
        System.out.print("CPF: ");
        String cpf = sc.nextLine();

        // HashMap<String, Paciente>.get(): busca por chave, mais eficiente
        // que percorrer a lista inteira de pacientes.
        Paciente paciente = pacientesPorCpf.get(cpf);

        if (paciente == null) {
            System.out.println("Paciente nao encontrado.");
        } else {
            System.out.println(paciente.exibirResumo());
        }
    }

   public static void listarPacientes() {
    if (pacientesPorCpf.isEmpty()) {
        System.out.println("Nenhum paciente cadastrado.");
        return;
    }

    for (Map.Entry<String, Paciente> entry : pacientesPorCpf.entrySet()) {
        System.out.println("CPF: " + entry.getKey());
        System.out.println(entry.getValue().exibirResumo());
    }
}

    public static void desativarPaciente() {
        System.out.print("CPF: ");
        String cpf = sc.nextLine();

        Paciente paciente = pacientesPorCpf.get(cpf);

        if (paciente == null) {
            System.out.println("Paciente nao encontrado.");
        } else {
            paciente.desativar();
            System.out.println("Paciente desativado.");
        }
    }

    public static int buscarIndicePaciente(String cpf) {
        for (int i = 0; i < pacientes.size(); i++) {
            if (pacientes.get(i).getCpf().equals(cpf)) {
            return i;
            }
        }

        return -1;
    }

    // -------------------------------------------------------------------------
    // PROFISSIONAIS
    // -------------------------------------------------------------------------

    public static void menuProfissionais() {
        int op = -1;

        while (op != 0) {
            System.out.println("\n--- PROFISSIONAIS ---");
            System.out.println("1 - Cadastrar");
            System.out.println("2 - Atualizar cadastro");
            System.out.println("3 - Listar todos");
            System.out.println("4 - Filtrar por especialidade");
            System.out.println("0 - Voltar");
            op = lerInteiro("Opcao: ");

            switch (op) {
                case 1:
                    cadastrarProfissional();
                    break;
                case 2:
                    atualizarProfissional();
                    break;
                case 3:
                    listarProfissionais();
                    break;
                case 4:
                    filtrarProfissionais();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opcao invalida!");
                    break;
            }
        }
    }

    public static Profissional criarProfissionalPorEspecialidade(
            String nome,
            String especialidade) {

        if (especialidade.equals("fisioterapia")) {
            return new Fisioterapeuta(nome);
        }

        if (especialidade.equals("psicologia")) {
            return new Psicologo(nome);
        }

        if (especialidade.equals("nutricao")) {
            return new Nutricionista(nome);
        }

        if (especialidade.equals("clinica geral")) {
            return new ClinicoGeral(nome);
        }

        return null;
    }

    public static Profissional criarProfissionalPorEspecialidade(
            String nome,
            String especialidade,
            String registro,
            double valor) {

        if (especialidade.equals("fisioterapia")) {
            return new Fisioterapeuta(nome, registro, valor);
        }

        if (especialidade.equals("psicologia")) {
            return new Psicologo(nome, registro, valor);
        }

        if (especialidade.equals("nutricao")) {
            return new Nutricionista(nome, registro, valor);
        }

        if (especialidade.equals("clinica geral")) {
            return new ClinicoGeral(nome, registro, valor);
        }

        return null;
    }

    public static Profissional criarProfissionalPorEspecialidade(
            String nome,
            String especialidade,
            String registro,
            double valor,
            String[] dias,
            int totalDias) {

        if (especialidade.equals("fisioterapia")) {
            return new Fisioterapeuta(nome, registro, valor, dias, totalDias);
        }

        if (especialidade.equals("psicologia")) {
            return new Psicologo(nome, registro, valor, dias, totalDias);
        }

        if (especialidade.equals("nutricao")) {
            return new Nutricionista(nome, registro, valor, dias, totalDias);
        }

        if (especialidade.equals("clinica geral")) {
            return new ClinicoGeral(nome, registro, valor, dias, totalDias);
        }

        return null;
    }

    public static void cadastrarProfissional() {
        System.out.print("Nome: ");
        String nome = sc.nextLine();

        System.out.print(
                "Especialidade (clinica geral/fisioterapia/psicologia/nutricao): "
        );
        String especialidade = sc.nextLine().trim().toLowerCase();

        if (!Profissional.especialidadeValida(especialidade)) {
            System.out.println("Especialidade invalida!");
            return;
        }

        int tipo = lerInteiro("Tipo (1-Minimo / 2-Com registro e valor / 3-Completo): ");

        Profissional profissional;

        if (tipo == 1) {
            profissional = criarProfissionalPorEspecialidade(
                    nome,
                    especialidade
            );

        } else if (tipo == 2) {
            System.out.print("Registro: ");
            String registro = sc.nextLine();

            double valor = lerDouble("Valor consulta: ");

            profissional = criarProfissionalPorEspecialidade(
                    nome,
                    especialidade,
                    registro,
                    valor
            );

        } else if (tipo == 3) {
            System.out.print("Registro: ");
            String registro = sc.nextLine();

            double valor = lerDouble("Valor consulta: ");

            int quantidade = lerInteiro("Quantos dias atende? ");

            if (quantidade < 0) {
                quantidade = 0;
            }

            if (quantidade > 7) {
                quantidade = 7;
            }

            String[] dias = new String[7];

            for (int i = 0; i < quantidade; i++) {
                System.out.print("Dia " + (i + 1) + ": ");
                dias[i] = sc.nextLine().trim().toLowerCase();
            }

            profissional = criarProfissionalPorEspecialidade(
                    nome,
                    especialidade,
                    registro,
                    valor,
                    dias,
                    quantidade
            );

        } else {
            System.out.println("Tipo de cadastro invalido.");
            return;
        }

        if (profissional == null) {
            System.out.println("Especialidade ainda nao implementada.");
            return;
        }

        profissionais.add(profissional);
        pessoas.add(profissional);

        System.out.println("Profissional cadastrado!");
    }

    public static void atualizarProfissional() {
        System.out.print("Nome do profissional: ");
        String nome = sc.nextLine();

        int idx = buscarIndiceProfissional(nome);

        if (idx == -1) {
            System.out.println("Profissional nao encontrado.");
            return;
        }

        int tipo = lerInteiro("Vai informar dias? (1-Nao / 2-Sim): ");

        System.out.print("Registro: ");
        String registro = sc.nextLine();

        double valor = lerDouble("Valor consulta: ");

        if (tipo == 1) {
            profissionais.get(idx).atualizar(registro, valor);

        } else if (tipo == 2) {
        int quantidade = lerInteiro("Quantos dias? ");

            if (quantidade < 0) {
                quantidade = 0;
            }

            if (quantidade > 7) {
                quantidade = 7;
            }

            String[] dias = new String[7];

            for (int i = 0; i < quantidade; i++) {
                System.out.print("Dia " + (i + 1) + ": ");
                dias[i] = sc.nextLine().trim().toLowerCase();
            }

        profissionais.get(idx).atualizar(
            registro,
            valor,
            dias,
            quantidade
        );

        } else {
            System.out.println("Opcao invalida.");
            return;
        }

        System.out.println("Profissional atualizado!");
    }

    public static void listarProfissionais() {
        if (profissionais.isEmpty()) {
            System.out.println("Nenhum profissional cadastrado.");
            return;
        }

        for (Profissional profissional : profissionais) {
            System.out.println(profissional.exibirResumo());
        }
    }

    public static void filtrarProfissionais() {
        System.out.print("Especialidade: ");
        String especialidade = sc.nextLine().trim().toLowerCase();

        boolean encontrou = false;

        for (int i = 0; i < profissionais.size(); i++) {
            if (profissionais.get(i).getEspecialidade().equals(especialidade)) {
                System.out.println(profissionais.get(i).exibirResumo());
                encontrou = true;
            }
        }

        if (!encontrou) {
            System.out.println("Nenhum profissional com essa especialidade.");
        }
    }

    public static int buscarIndiceProfissional(String nome) {
        for (int i = 0; i < profissionais.size(); i++) {
            if (profissionais.get(i).getNome().equals(nome)) {
                return i;
            }
        }

        return -1;
    }

    // -------------------------------------------------------------------------
    // CONSULTAS
    // -------------------------------------------------------------------------

    public static void menuConsultas() {
        int op = -1;

        while (op != 0) {
            System.out.println("\n--- CONSULTAS ---");
            System.out.println("1 - Agendar (escolher profissional)");
            System.out.println("2 - Agendar (busca por especialidade)");
            System.out.println("3 - Cancelar");
            System.out.println("4 - Remarcar");
            System.out.println("5 - Listar todas");
            System.out.println("6 - Buscar por CPF");
            System.out.println("0 - Voltar");
            op = lerInteiro("Opcao: ");

            switch (op) {
                case 1:
                    agendarComProfissional();
                    break;
                case 2:
                    agendarPorEspecialidade();
                    break;
                case 3:
                    cancelarConsulta();
                    break;
                case 4:
                    remarcarConsulta();
                    break;
                case 5:
                    listarConsultas();
                    break;
                case 6:
                    buscarConsultasPorPaciente();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opcao invalida!");
                    break;
            }
        }
    }

    public static void agendarComProfissional() {
        try {
            System.out.print("CPF do paciente: ");
            String cpf = sc.nextLine();
            validarPacienteAtivo(cpf);

            System.out.print("Nome do profissional: ");
            String nomeProf = sc.nextLine();
            int idxProf = validarProfissionalParaAgendamento(nomeProf);

            System.out.print("Data (DD/MM/AAAA): ");
            String data = sc.nextLine();
            System.out.print("Horario (HH:MM): ");
            String horario = sc.nextLine();

            validarDisponibilidadeProfissional(idxProf, data, horario);

            if (temConflito(nomeProf, data, horario)) {
                throw new HorarioIndisponivelException("Horario ocupado para esse profissional.");
            }

            int infoTipo = lerInteiro("Informar tipo? (1-Nao / 2-Sim): ");

            Agendavel agendamento;
            if (infoTipo == 1) {
                agendamento = new Consulta(cpf, nomeProf, data, horario);
            } else {
                System.out.print("Tipo (inicial/retorno/avaliacao): ");
                String tipo = sc.nextLine();
                agendamento = new Consulta(cpf, nomeProf, data, horario, tipo);
            }

            consultas.add((Consulta) agendamento);
            System.out.println("Consulta agendada com sucesso!");
        } catch (PacienteNaoEncontradoException | PacienteInativoException
                | ProfissionalNaoEncontradoException | ValorInvalidoException
                | HorarioIndisponivelException e) {
            System.out.println(e.getMessage());
            tentarSugestaoHorario();
        }
    }

    public static void agendarPorEspecialidade() {
        try {
            System.out.print("CPF do paciente: ");
            String cpf = sc.nextLine();
            validarPacienteAtivo(cpf);

            System.out.print("Especialidade: ");
            String esp = sc.nextLine();
            System.out.print("Data (DD/MM/AAAA): ");
            String data = sc.nextLine();
            System.out.print("Horario (HH:MM): ");
            String horario = sc.nextLine();

            if (!Profissional.especialidadeValida(esp)) {
                throw new EspecialidadeInvalidaException("Especialidade invalida.");
            }

            int idxProf = buscarProfissionalPorEspecialidadeDisponivel(esp, data, horario);
            if (idxProf == -1) {
                throw new HorarioIndisponivelException("Nao existe profissional disponivel nesse horario.");
            }

            Agendavel agendamento = new Consulta(cpf, profissionais.get(idxProf).getNome(), data, horario);
            consultas.add((Consulta) agendamento);
            System.out.println("Consulta agendada com " + profissionais.get(idxProf).getNome() + "!");
        } catch (PacienteNaoEncontradoException | PacienteInativoException
                | EspecialidadeInvalidaException | HorarioIndisponivelException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void validarPacienteAtivo(String cpf)
        throws PacienteNaoEncontradoException, PacienteInativoException {

        Paciente paciente = pacientesPorCpf.get(cpf);

        if (paciente == null) {
            throw new PacienteNaoEncontradoException("Paciente nao encontrado.");
        }

        if (!paciente.isAtivo()) {
            throw new PacienteInativoException("Paciente inativo. Nao e possivel agendar.");
        }
    }   

    private static int validarProfissionalParaAgendamento(String nomeProf)
            throws ProfissionalNaoEncontradoException, ValorInvalidoException {
        int idxProf = buscarIndiceProfissional(nomeProf);
        if (idxProf == -1) {
            throw new ProfissionalNaoEncontradoException("Profissional nao encontrado.");
        }
        if (profissionais.get(idxProf).getValorConsulta() <= 0) {
            throw new ValorInvalidoException("Profissional sem valor definido. Nao pode agendar.");
        }
        return idxProf;
    }

    private static void validarDisponibilidadeProfissional(int idxProf, String data, String horario)
            throws HorarioIndisponivelException {
        String diaSemana = descobrirDiaSemana(data);
        if (!profissionais.get(idxProf).atendeNoHorario(diaSemana, horario)) {
            throw new HorarioIndisponivelException("Profissional nao atende nesse dia/horario.");
        }
    }

    private static int buscarProfissionalPorEspecialidadeDisponivel(String esp, String data, String horario)
            throws HorarioIndisponivelException {
        boolean encontrouEspecialidade = false;

        for (int i = 0; i < profissionais.size(); i++) {
            if (!profissionais.get(i).getEspecialidade().equals(esp)) {
                continue;
            }
            encontrouEspecialidade = true;
            if (profissionais.get(i).getValorConsulta() <= 0) {
                continue;
            }

            try {
                validarDisponibilidadeProfissional(i, data, horario);
            } catch (HorarioIndisponivelException e) {
                continue;
            }

            if (!temConflito(profissionais.get(i).getNome(), data, horario)) {
                return i;
            }
        }

        if (!encontrouEspecialidade) {
            throw new HorarioIndisponivelException("Nao ha profissional cadastrado para essa especialidade.");
        }

        return -1;
    }

    private static void tentarSugestaoHorario() {
        int escolha = lerInteiro("Deseja tentar sugestao de horario? (1-Sim / 2-Nao): ");
        if (escolha != 1) {
            return;
        }

        System.out.print("Nome do profissional: ");
        String nomeProf = sc.nextLine();
        System.out.print("Data (DD/MM/AAAA): ");
        String data = sc.nextLine();

        String sugestao = sugerirHorario(nomeProf, data);
        if (sugestao.equals("")) {
            System.out.println("Nenhum horario disponivel nesse dia.");
            return;
        }

        System.out.println("Sugestao: " + sugestao);
    }

    public static void cancelarConsulta() {
        System.out.print("CPF: ");
        String cpf = sc.nextLine();

        System.out.print("Data (DD/MM/AAAA): ");
        String data = sc.nextLine();

        System.out.print("Horario (HH:MM): ");
        String horario = sc.nextLine();

        int idxConsulta = -1;

        for (int i = 0; i < consultas.size(); i++) {
            if (consultas.get(i).getCpfPaciente().equals(cpf)
                    && consultas.get(i).getData().equals(data)
                    && consultas.get(i).getHorario().equals(horario)) {

                idxConsulta = i;
                break;
            }
        }

        if (idxConsulta == -1) {
            System.out.println("Consulta nao encontrada.");
            return;
        }

        if (consultas.get(idxConsulta).getStatus().equals("realizada")) {
            System.out.println("Consulta ja realizada. Nao pode cancelar.");
            return;
        }

        if (consultas.get(idxConsulta).getStatus().equals("cancelada")) {
            System.out.println("Consulta ja cancelada.");
            return;
        }

        System.out.print("Horario atual (HH:MM): ");
        String horaAtual = sc.nextLine();

        int horaConsulta = Integer.parseInt(horario.substring(0, 2));
        int horaAgora = Integer.parseInt(horaAtual.substring(0, 2));
        int diferenca = horaConsulta - horaAgora;

        if (diferenca < 2) {
            System.out.println("Multa de R$50.00 aplicada!");
            multas.add(50.0);
        }

        int informarMotivo = lerInteiro("Informar motivo? (1-Nao / 2-Sim): ");

        if (informarMotivo == 1) {
            consultas.get(idxConsulta).cancelar();
        } else {
            System.out.print("Motivo: ");
            String motivo = sc.nextLine();
            System.out.println(consultas.get(idxConsulta).cancelar(motivo));
        }

        System.out.println("Consulta cancelada.");
    }

    public static void remarcarConsulta() {
        System.out.print("CPF: ");
        String cpf = sc.nextLine();

        System.out.print("Data original (DD/MM/AAAA): ");
        String dataOriginal = sc.nextLine();

        System.out.print("Horario original (HH:MM): ");
        String horarioOriginal = sc.nextLine();

        int idxConsulta = -1;

        for (int i = 0; i < consultas.size(); i++) {
            if (consultas.get(i).getCpfPaciente().equals(cpf)
                    && consultas.get(i).getData().equals(dataOriginal)
                    && consultas.get(i).getHorario().equals(horarioOriginal)
                    && consultas.get(i).getStatus().equals("agendada")) {

                idxConsulta = i;
                break;
            }
        }

        if (idxConsulta == -1) {
            System.out.println("Consulta agendada nao encontrada.");
            return;
        }

        int tipo = lerInteiro("Apenas trocar horario no mesmo dia? (1-Sim / 2-Nao): ");

        String novaData;
        String novoHorario;

        if (tipo == 1) {
            novaData = dataOriginal;

            System.out.print("Novo horario: ");
            novoHorario = sc.nextLine();

        } else {
            System.out.print("Nova data (DD/MM/AAAA): ");
            novaData = sc.nextLine();

            System.out.print("Novo horario (HH:MM): ");
            novoHorario = sc.nextLine();
        }

        String nomeProf = consultas.get(idxConsulta).getNomeProfissional();
        int idxProfissional = buscarIndiceProfissional(nomeProf);

        if (idxProfissional == -1) {
            System.out.println("Profissional nao encontrado.");
            return;
        }

        if (tipo == 2) {
            String diaSemana = descobrirDiaSemana(novaData);

            if (!profissionais.get(idxProfissional).atendeNoDia(diaSemana)) {
                System.out.println("Profissional nao atende nesse dia.");
                return;
            }
        }

        if (temConflito(nomeProf, novaData, novoHorario)) {
            System.out.println("Horario ocupado. Nao foi possivel remarcar.");
            return;
        }

        consultas.get(idxConsulta).remarcar();

        consultas.add(new Consulta(
                cpf,
                nomeProf,
                novaData,
                novoHorario,
                consultas.get(idxConsulta).getTipo()
        ));

        System.out.println("Consulta remarcada com sucesso!");
    }

    public static void listarConsultas() {
        if (consultas.isEmpty()) {
            System.out.println("Nenhuma consulta.");
            return;
        }

        for (int i = 0; i < consultas.size(); i++) {
            System.out.println("[" + i + "] " + consultas.get(i).exibirResumo());
        }
    }

    public static void buscarConsultasPorPaciente() {
        System.out.print("CPF: ");
        String cpf = sc.nextLine();

        boolean encontrou = false;

        for (int i = 0; i < consultas.size(); i++) {
            if (consultas.get(i).getCpfPaciente().equals(cpf)) {
                System.out.println("[" + i + "] " + consultas.get(i).exibirResumo());
                encontrou = true;
            }
        }

        if (!encontrou) {
            System.out.println("Nenhuma consulta encontrada.");
        }
    }

     // verifica se ja tem consulta nesse horario com esse profissional
    public static boolean temConflito(String nomeProf, String data, String horario) {
        int idxProf = buscarIndiceProfissional(nomeProf);
        if (idxProf == -1) {
            return true;
        }

        String diaSemana = descobrirDiaSemana(data);
        if (!profissionais.get(idxProf).atendeNoHorario(diaSemana, horario)) {
            return true;
        }

        for (int i = 0; i < consultas.size(); i++) {
            if (consultas.get(i).getNomeProfissional().equals(nomeProf)
                    && consultas.get(i).getData().equals(data)
                    && consultas.get(i).getHorario().equals(horario)
                    && consultas.get(i).getStatus().equals("agendada")) {

                return true;
            }
        }

        return false;
    }

    // sugere proximo horario livre (de hora em hora, 08h ate 18h)
    public static String sugerirHorario(String nomeProf, String data) {
        int idxProf = buscarIndiceProfissional(nomeProf);
        if (idxProf == -1) {
            return "";
        }

        String diaSemana = descobrirDiaSemana(data);

        for (int h = 8; h <= 21; h++) {
            String teste;
            if (h < 10) {
                teste = "0" + h + ":00";
            } else {
                teste = h + ":00";
            }

            if (!profissionais.get(idxProf).atendeNoHorario(diaSemana, teste)) {
                continue;
            }

            if (!temConflito(nomeProf, data, teste)) {
                return teste;
            }
        }

        return "";
    }

    public static String descobrirDiaSemana(String data) {
        String dataLimpa = data.trim();

        int dia = Integer.parseInt(dataLimpa.substring(0, 2));
        int mes = Integer.parseInt(dataLimpa.substring(3, 5));
        int ano = Integer.parseInt(dataLimpa.substring(6, 10));

        if (mes < 3) {
            mes += 12;
            ano--;
        }

        int k = ano % 100;
        int j = ano / 100;

        int resultado = (
                dia
                        + (13 * (mes + 1)) / 5
                        + k
                        + k / 4
                        + j / 4
                        - 2 * j
        ) % 7;

        if (resultado < 0) {
            resultado += 7;
        }

        String[] nomes = {
                "sabado",
                "domingo",
                "segunda",
                "terca",
                "quarta",
                "quinta",
                "sexta"
        };

        return nomes[resultado];
    }

    // -------------------------------------------------------------------------
    // ATENDIMENTOS
    // -------------------------------------------------------------------------

    public static void menuAtendimentos() {
        int op = -1;

        while (op != 0) {
            System.out.println("\n--- ATENDIMENTOS ---");
            System.out.println("1 - Registrar atendimento");
            System.out.println("0 - Voltar");
            op = lerInteiro("Opcao: ");

            if (op == 1) {
                registrarAtendimento();
            } else if (op != 0) {
                System.out.println("Opcao invalida!");
            }
        }
    }

    public static void registrarAtendimento() {
        int idxConsulta = lerInteiro("Indice da consulta: ");

        if (idxConsulta < 0 || idxConsulta >= consultas.size()) {
            System.out.println("Indice invalido.");
            return;
        }

        if (!consultas.get(idxConsulta).getStatus().equals("agendada")) {
            try {
                throw new OperacaoInvalidaException(
                        "So pode registrar atendimento em consulta agendada."
                );
            } catch (OperacaoInvalidaException e) {
                System.out.println(e.getMessage());
            }
            return;
        }

        System.out.print("Observacoes: ");
        String observacoes = sc.nextLine();

        int tipo = lerInteiro("Tipo de registro (1-So obs / 2-Com diagnostico / 3-Completo): ");

        Atendimento novoAtendimento;

        if (tipo == 1) {
            atendimentos.add(new Atendimento(
                    idxConsulta,
                    observacoes
            ));

        } else if (tipo == 2) {
            System.out.print("Diagnostico: ");
            String diagnostico = sc.nextLine();

            atendimentos.add(new Atendimento(
                    idxConsulta,
                    observacoes,
                    diagnostico
            ));

        } else if (tipo == 3) {
            System.out.print("Diagnostico: ");
            String diagnostico = sc.nextLine();

            int forma = lerInteiro(
                    "Como informar procedimentos? (1-Um por vez / 2-Todos de uma vez): "
            );

            String[] procedimentos = new String[10];
            int quantidadeProcedimentos = 0;

            if (forma == 1) {
                String procedimento = "";

                while (!procedimento.equals("fim")
                        && quantidadeProcedimentos < procedimentos.length) {

                    System.out.print("Procedimento (ou 'fim'): ");
                    procedimento = sc.nextLine();

                    if (!procedimento.equals("fim")) {
                        procedimentos[quantidadeProcedimentos] = procedimento;
                    }
                }

            } else if (forma == 2) {
            quantidadeProcedimentos = lerInteiro("Quantos? ");

                if (quantidadeProcedimentos < 0) {
                    quantidadeProcedimentos = 0;
                }

                if (quantidadeProcedimentos > procedimentos.length) {
                    quantidadeProcedimentos = procedimentos.length;
                }

                for (int i = 0; i < quantidadeProcedimentos; i++) {
                    System.out.print("Proc " + (i + 1) + ": ");
                    procedimentos[i] = sc.nextLine();
                }

            } else {
                System.out.println("Forma invalida.");
                return;
            }

            atendimentos.add(new Atendimento(
                    idxConsulta,
                    observacoes,
                    diagnostico,
                    procedimentos,
                    quantidadeProcedimentos
            ));

        } else {
            System.out.println("Tipo de registro invalido.");
            return;
        }
        String nomeProfissional = consultas.get(idxConsulta).getNomeProfissional();
        int idxProf = buscarIndiceProfissional(nomeProfissional);

        // LIGACAO DINAMICA conforme R5: cada especialidade adiciona ao
        // atendimento as informacoes particulares dela (registrarEspecifico).
        if (idxProf != -1) {
            profissionais.get(idxProf).registrarEspecifico(atendimentos.get(atendimentos.size() - 1));
        }
        consultas.get(idxConsulta).realizar();

        System.out.println("\n--- RESUMO ---");
        System.out.println(atendimentos.get(atendimentos.size() - 1).exibirResumo());
        System.out.println("Consulta marcada como realizada.");
    }

    // -------------------------------------------------------------------------
    // PAGAMENTOS
    // -------------------------------------------------------------------------

    public static void menuPagamentos() {
        int op = -1;

        while (op != 0) {
            System.out.println("\n--- PAGAMENTOS ---");
            System.out.println("1 - Pagamento direto");
            System.out.println("2 - Pagamento automatico");
            System.out.println("3 - Listar pagamentos");
            System.out.println("0 - Voltar");
            op = lerInteiro("Opcao: ");

            switch (op) {
                case 1:
                    pagamentoDireto();
                    break;
                case 2:
                    pagamentoAutomatico();
                    break;
                case 3:
                    listarPagamentos();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opcao invalida!");
                    break;
            }
        }
    }

    public static void pagamentoDireto() {
        int idxConsulta = lerInteiro("Indice da consulta: ");

        if (idxConsulta < 0 || idxConsulta >= consultas.size()) {
            System.out.println("Indice invalido.");
            return;
        }

        double valorBase = lerDouble("Valor base: ");

        if (valorBase < 0) {
            System.out.println("Valor invalido.");
            return;
        }

        System.out.print("Tipo (dinheiro/cartao): ");
        String tipoPagamento = sc.nextLine().trim().toLowerCase();

        try {
            Pagamento pagamento;

            if (tipoPagamento.equals("dinheiro")) {
                pagamento = new PagamentoDinheiro(
                        idxConsulta,
                        valorBase
                );

            } else if (tipoPagamento.equals("cartao")) {
            int parcelas = lerInteiro("Parcelas (1 a 6): ");

                pagamento = new PagamentoCartao(
                        idxConsulta,
                        valorBase,
                        parcelas
                );

            } else {
                System.out.println("Tipo de pagamento invalido.");
                return;
            }

            adicionarPagamento(pagamento);

            System.out.println("Pagamento registrado!");
            System.out.println(pagamento.exibirResumo());

        } catch (PagamentoInvalidoException e) {
            System.out.println(
                    "Erro ao registrar pagamento: "
                            + e.getMessage()
            );
        }
    }

    public static void pagamentoAutomatico() {
        int idxConsulta = lerInteiro("Indice da consulta: ");

        if (idxConsulta < 0 || idxConsulta >= consultas.size()) {
            System.out.println("Indice invalido.");
            return;
        }

        Consulta consulta = consultas.get(idxConsulta);
        int idxProfissional = buscarIndiceProfissional(
                consulta.getNomeProfissional()
        );

        if (idxProfissional == -1) {
            System.out.println("Profissional nao encontrado.");
            return;
        }

        int idxPaciente = buscarIndicePaciente(
                consulta.getCpfPaciente()
        );

        if (idxPaciente == -1) {
            System.out.println("Paciente nao encontrado.");
            return;
        }

        Profissional profissional = profissionais.get(idxProfissional);
        Paciente paciente = pacientes.get(idxPaciente);

        double valorBase = profissional.getValorConsulta();

        if (valorBase < 0) {
            System.out.println("Valor da consulta invalido.");
            return;
        }

        int possuiMulta = lerInteiro("Tem multa pendente? (1-Nao / 2-Sim): ");

        double valorMulta = 0.0;

        if (possuiMulta == 2) {
        valorMulta = lerDouble("Valor da multa: ");

            if (valorMulta < 0) {
                System.out.println("Valor da multa invalido.");
                return;
            }
        }

        System.out.println("Valor base: R$" + valorBase);

        System.out.print("Tipo (dinheiro/cartao/convenio): ");
        String tipoPagamento = sc.nextLine().trim().toLowerCase();

        try {
            Pagamento pagamento;

            switch (tipoPagamento) {
                case "dinheiro":
                    pagamento = new PagamentoDinheiro(
                            idxConsulta,
                            valorBase,
                            valorMulta
                    );
                    break;

                case "cartao":
                int parcelas = lerInteiro("Parcelas (1 a 6): ");

                    pagamento = new PagamentoCartao(
                            idxConsulta,
                            valorBase,
                            parcelas,
                            valorMulta
                    );
                    break;

                case "convenio":
                    pagamento = new PagamentoConvenio(
                            idxConsulta,
                            valorBase,
                            paciente.getConvenio(),
                            profissional.getEspecialidade(),
                            valorMulta
                    );
                    break;

                default:
                    System.out.println("Tipo de pagamento invalido.");
                    return;
            }

            adicionarPagamento(pagamento);

            System.out.println("Pagamento registrado!");
            System.out.println(pagamento.exibirResumo());

        } catch (ConvenioNaoCobreException e) {
            System.out.println(e);

        } catch (PagamentoInvalidoException e) {
            System.out.println(e);
        }
    }

    public static void adicionarPagamento(Pagamento pagamento) {
        // LIGACAO DINAMICA conforme R5:
        // o metodo executado depende da subclasse real do objeto.
        pagamento.setValorFinal(pagamento.calcularValorFinal());
        pagamentos.add(pagamento);
    }

    public static void listarPagamentos() {
        if (pagamentos.isEmpty()) {
            System.out.println("Nenhum pagamento registrado.");
            return;
        }

        System.out.println("\n=== PAGAMENTOS REGISTRADOS ===");

        for (Pagamento pagamento : pagamentos) {
            // LIGACAO DINAMICA conforme R5.
            pagamento.setValorFinal(pagamento.calcularValorFinal());

            System.out.println(
                    pagamento.getClass().getSimpleName()
                            + " -> "
                            + pagamento.exibirResumo()
            );
        }
    }

    public static void relatorioUnificadoPessoas() {
        System.out.println("\nRELATORIO UNIFICADO DE PESSOAS");

        if (pessoas.isEmpty()) {
            System.out.println("Nenhuma pessoa cadastrada.");
            return;
        }

        int totalPacientesRelatorio = 0;
        int totalProfissionaisRelatorio = 0;

        for (Pessoa pessoa : pessoas) {
            // LIGAÇÃO DINÂMICA: o método chamado depende do tipo REAL do objeto, não do tipo da referência
            System.out.println(pessoa.exibirResumo());

            if (pessoa instanceof Paciente) {
                totalPacientesRelatorio++;
            }

            if (pessoa instanceof Profissional) {
                totalProfissionaisRelatorio++;

                Profissional prof = (Profissional) pessoa;

                if (prof instanceof Fisioterapeuta) {
                    System.out.println("Tipo real: Fisioterapeuta");
                } else if (prof instanceof Psicologo) {
                    System.out.println("Tipo real: Psicologo");
                } else if (prof instanceof Nutricionista) {
                    System.out.println("Tipo real: Nutricionista");
                } else if (prof instanceof ClinicoGeral) {
                    System.out.println("Tipo real: Clinico Geral");
                }
            }

            System.out.println("---");
        }

        System.out.println("Total de pacientes: " + totalPacientesRelatorio);
        System.out.println("Total de profissionais: " + totalProfissionaisRelatorio);
    }
    public static void exportarDadosOperacionais() {

        List<Exportavel> exportacoes = new ArrayList<>();

        exportacoes.addAll(consultas);
        exportacoes.addAll(atendimentos);
        exportacoes.addAll(pagamentos);

        for (Exportavel item : exportacoes) {
            System.out.println(item.exportarDados());
        }
    }

    // -------------------------------------------------------------------------
    // RELATORIOS
    // -------------------------------------------------------------------------

    // HashMap<Integer, Atendimento>: liga o indice da consulta ao atendimento
    // correspondente, para o Relatorio buscar o diagnostico por indice (get)
    // em vez de percorrer a lista inteira de atendimentos.
    private static Map<Integer, Atendimento> mapaAtendimentosPorConsulta() {
        Map<Integer, Atendimento> mapa = new HashMap<>();
        for (Atendimento atendimento : atendimentos) {
            mapa.put(atendimento.getIndiceConsulta(), atendimento);
        }
        return mapa;
    }

    // HashMap<Integer, Double>: liga o indice da consulta multada ao valor
    // da multa, no mesmo espirito do mapa de atendimentos acima.
    private static Map<Integer, Double> mapaMultasPorIndice() {
        Map<Integer, Double> mapa = new HashMap<>();
        for (int i = 0; i < multas.size(); i++) {
            mapa.put(i, multas.get(i));
        }
        return mapa;
    }

    public static void menuRelatorios() {
        int op = -1;

        while (op != 0) {
            System.out.println("\n--- RELATORIOS ---");
            System.out.println("1 - Geral");
            System.out.println("2 - Por profissional");
            System.out.println("3 - Por periodo");
            System.out.println("4 - Resumo financeiro");
            System.out.println("5 - Cancelamentos");
            System.out.println("6 - Multas aplicadas");
            System.out.println("7 - Relatorio de pagamentos");
            System.out.println("8 - Relatorio unificado de pessoas");
            System.out.println("9 - Exportar dados operacionais");
            System.out.println("0 - Voltar");
            op = lerInteiro("Opcao: ");

            switch (op) {
                case 1:
                    Relatorio.gerarRelatorio(
                            consultas,
                            mapaAtendimentosPorConsulta()
                    );
                    break;

                case 2:
                    System.out.print("Nome do profissional: ");
                    String nome = sc.nextLine();

                    Relatorio.gerarRelatorio(
                            consultas,
                            mapaAtendimentosPorConsulta(),
                            nome
                    );
                    break;

                case 3:
                    System.out.print("Data inicio (DD/MM/AAAA): ");
                    String inicio = sc.nextLine();

                    System.out.print("Data fim (DD/MM/AAAA): ");
                    String fim = sc.nextLine();

                    Relatorio.gerarRelatorio(
                            consultas,
                            mapaAtendimentosPorConsulta(),
                            inicio,
                            fim
                    );
                    break;

                case 4:
                    Relatorio.gerarResumoFinanceiro(
                            consultas,
                            pagamentos,
                            mapaMultasPorIndice()
                    );
                    break;
                case 5:
                    Relatorio.gerarRelatorioCancelamentos(consultas);
                    break;
                case 6:
                    Relatorio.gerarRelatorioMultasAplicadas(mapaMultasPorIndice());
                    break;
                case 7:
                    Relatorio.gerarRelatorioPagamentos(pagamentos);
                    break;
                case 8:
                    relatorioUnificadoPessoas();
                    break;
                case 9:
                    exportarDadosOperacionais();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opcao invalida!");
                    break;
            }
        }
    }
}