public class Main {
    public static void main(String[] args) {

        System.out.println("---- Bem vindo ao Banco!-----");
        Cliente cliente = new Cliente();
        cliente.setNome("Claudiane");

        Conta corrente = new ContaCorrente(cliente);
        Conta poupanca = new ContaPoupanca(cliente);

        corrente.depositar(100);
        corrente.transferir(100, poupanca);

        corrente.imprimirExtrato();
        poupanca.imprimirExtrato();
    }
}