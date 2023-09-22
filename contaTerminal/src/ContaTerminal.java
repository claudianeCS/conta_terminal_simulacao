import java.text.DecimalFormat;
import java.util.Scanner;

public class ContaTerminal {
    public static void main(String[] args) {

        System.out.println("----- Bem vindo, novo usuario! -----");

        Conta conta = new Conta();

        Scanner scanner = new Scanner(System.in);

        System.out.println("Por favor, digite o numero da Agência!");
        System.out.println("Agência: ");
        conta.setAgencia(scanner.nextLine());

        System.out.println("Nome: ");
        conta.setNomeCliente(scanner.nextLine());

        System.out.println("Numero da conta:");
        conta.setNumeroConta(Integer.parseInt(scanner.nextLine()));

        System.out.println("Deposite seu saldo:");
        conta.setSaldo(Double.parseDouble(scanner.nextLine()));

        System.out.println("________________________________________________________");
        System.out.println("Olá " + conta.getNomeCliente().toUpperCase() + ", obrigado por criar uma conta em nosso banco, sua agência é " + conta.getAgencia() +
                ", conta " + conta.getNumeroConta() + " e seu saldo R$ " + conta.getSaldo()+ " já está disponível para saque!" );

        System.out.println("----- Deseja fazer um saque? ----- ");
        System.out.println("Digite S para Sim ou N para não: ");
        String escolha = scanner.nextLine().toUpperCase();

        if (escolha.equals("S")){
            System.out.println("Digite o valor do saque: ");
            Double saque = Double.parseDouble(scanner.nextLine());
            atualizarSaldo(saque, conta);
        } else if (escolha.equals("N")){
            System.out.println("----- Ate a proxima! -----");
        }

    }

    public static void atualizarSaldo(Double saque, Conta conta){

        DecimalFormat formato = new DecimalFormat("0.00");

        Double novoSaldo = conta.getSaldo() - saque;
        conta.setSaldo(novoSaldo);

        System.out.println("Seu novo saldo e de R$: " + formato.format(conta.getSaldo()));
    }
}