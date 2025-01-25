package Utils;

public class Menu {
    public void menuImprime(){
        System.out.println();
        System.out.println("BANCO:\n");
        System.out.println("1 - Cadastro de usuario");
        System.out.println("2 - Criacao de conta bancaria");
        System.out.println("3 - Operacoes bancarias");
        System.out.println("4 - Login");
        System.out.println("5 - Logout");
        System.out.println("0 - Sair");
        System.out.print("Digite um numero correspondente a uma das opcoes: ");
    }

    public void menuCorrentLoginImprime(Corrent corrent){
        System.out.println("\nDados da conta:");
        System.out.println(corrent.getNome());
        System.out.println(corrent.getCpf());
        System.out.println(corrent.getIdConta());
        System.out.println(corrent.getAgencia());
        System.out.println(corrent.getTipo());
        System.out.println(corrent.getSaldo());
    }
    public void menuPoupancaLoginImprime(Poupanca poupanca){
        System.out.println("\nDados da conta:");
        System.out.println(poupanca.getNome());
        System.out.println(poupanca.getCpf());
        System.out.println(poupanca.getIdConta());
        System.out.println(poupanca.getAgencia());
        System.out.println(poupanca.getTipo());
        System.out.println(poupanca.getSaldo());
    }
    public void menuSalarioLoginImprime(Salario salario){
        System.out.println("\nDados da conta:");
        System.out.println(salario.getNome());
        System.out.println(salario.getCpf());
        System.out.println(salario.getIdConta());
        System.out.println(salario.getEmpregador());
        System.out.println(salario.getAgencia());
        System.out.println(salario.getTipo());
        System.out.println(salario.getSaldo());
        System.out.println(salario.getStatus());
    }

    public void menuOperacoesCorrentImprime(){
        System.out.println("\nOperacoes bancarias:\n");
        System.out.println("1 - Consulta de saldo");
        System.out.println("2 - Saque");
        System.out.println("3 - Deposito");
        System.out.println("4 - Transferencia");
        System.out.println("0 - Sair do Menu de Operações bancarias");
        System.out.print("Digite o numero correspondente a operação que deseja realizar: ");
    }

    public void menuOperacoesPoupancaImprime(){
        System.out.println("\nOperacoes bancarias:\n");
        System.out.println("1 - Consulta de saldo");
        System.out.println("2 - Saque");
        System.out.println("3 - Deposito");
        System.out.println("4 - Transferencia");
        System.out.println("0 - Sair do Menu de Operações bancarias");
        System.out.print("Digite o numero correspondente a operação que deseja realizar: ");
    }

    public void menuOperacoesSalarioImprime(){
        System.out.println("\nOperacoes bancarias:\n");
        System.out.println("1 - Consulta de saldo");
        System.out.println("2 - Saque");
        System.out.println("0 - Sair do Menu de Operações bancarias");
        System.out.print("Digite o numero correspondente a operação que deseja realizar: ");
    }

    public void opcoesAgenciasImprime(){
        System.out.println("\n1 - Banco do Brasil");
        System.out.println("2 - Bradesco");
        System.out.println("3 - Itau");
        System.out.println("4 - Nubank");
        System.out.println("5 - Santander");
    }

    public void tiposContaImprime(){
        System.out.println("\nTipos de contas e suas caracteristicas individuais:\n");
        System.out.println("1 - Conta Corrente: Taxas de manutenção.");
        System.out.println("2 - Conta Poupanca: Rendimento mensal");
        System.out.println("3 - Conta Salario: Restrita a depositos do empregador e limite de 2 saques por hora.");
    }
}