import java.util.ArrayList;
import java.util.Scanner;

class Conta {

    String nome;
    int numeroConta;
    String senha;
    String chavePix;
    double saldo;

    Conta(String nome, int numeroConta, String senha, String chavePix) {
        this.nome = nome;
        this.numeroConta = numeroConta;
        this.senha = senha;
        this.chavePix = chavePix;
        this.saldo = 0;
    }

    void depositar(double valor) {
        if (valor > 0) {
            saldo += valor;
            System.out.println("Depósito realizado com sucesso!");
        } else {
            System.out.println("Valor inválido!");
        }
    }

    void sacar(double valor) {
        if (valor > 0 && valor <= saldo) {
            saldo -= valor;
            System.out.println("Saque realizado com sucesso!");
        } else {
            System.out.println("Saldo insuficiente ou valor inválido!");
        }
    }

    void transferir(Conta destino, double valor) {
        if (valor > 0 && valor <= saldo) {
            this.saldo -= valor;
            destino.saldo += valor;
            System.out.println("Transferência realizada com sucesso!");
        } else {
            System.out.println("Transferência inválida!");
        }
    }

    void mostrarSaldo() {
        System.out.println("Saldo atual: R$ " + saldo);
    }
}

public class Main {

    static Scanner leia = new Scanner(System.in);
    static ArrayList<Conta> contas = new ArrayList<>();
    static Conta logado = null;

    public static void main(String[] args) {

        int opcao;

        do {

            System.out.println("===== SISTEMA BANCÁRIO =====");
            System.out.println("1 - Criar conta");
            System.out.println("2 - Login");
            System.out.println("3 - Sair");

            opcao = lerInt();

            switch (opcao) {

                case 1:
                    criarConta();
                    break;

                case 2:
                    login();
                    if (logado != null) {
                        menuConta();
                    }
                    break;

                case 3:
                    System.out.println("Sistema encerrado.");
                    break;

                default:
                    System.out.println("Opção inválida!");
            }

        } while (opcao != 3);

        leia.close();
    }

    static void criarConta() {

        System.out.print("Nome: ");
        String nome = leia.nextLine();

        System.out.print("Número da conta: ");
        int numero = lerInt();

        System.out.print("Senha: ");
        String senha = leia.nextLine();

        System.out.print("Chave PIX: ");
        String chavePix = leia.nextLine();

        contas.add(new Conta(nome, numero, senha, chavePix));

        System.out.println("Conta criada com sucesso!");
    }

    static void login() {

        System.out.print("Número da conta: ");
        int numero = lerInt();

        System.out.print("Senha: ");
        String senha = leia.nextLine();

        for (Conta c : contas) {

            if (c.numeroConta == numero && c.senha.equals(senha)) {

                logado = c;

                System.out.println("\nLogin realizado com sucesso!");
                System.out.println("Bem-vinda(o), " + c.nome);
                System.out.println("Conta: " + c.numeroConta);
                System.out.println("Chave PIX: " + c.chavePix);

                return;
            }
        }

        System.out.println("Conta ou senha inválidos!");
    }

    static void menuConta() {

        int opcao;

        do {

            System.out.println("===== CONTA LOGADA =====");
            System.out.println("1 - Depositar");
            System.out.println("2 - Sacar");
            System.out.println("3 - Transferir");
            System.out.println("4 - PIX");
            System.out.println("5 - Ver saldo");
            System.out.println("6 - Logout");

            opcao = lerInt();

            switch (opcao) {

                case 1:

                    System.out.print("Valor depósito: ");
                    logado.depositar(lerDouble());

                    break;

                case 2:

                    System.out.print("Valor saque: ");
                    logado.sacar(lerDouble());

                    break;

                case 3:

                    System.out.print("Número da conta destino: ");
                    int destino = lerInt();

                    Conta contaDestino = buscarConta(destino);

                    if (contaDestino != null) {

                        System.out.print("Valor transferência: ");
                        double valor = lerDouble();

                        logado.transferir(contaDestino, valor);

                    } else {

                        System.out.println("Conta destino não encontrada!");
                    }

                    break;

                case 4:

                    System.out.print("Digite a chave PIX destino: ");
                    String chavePix = leia.nextLine();

                    Conta contaPix = buscarPix(chavePix);

                    if (contaPix != null) {

                        System.out.print("Valor do PIX: ");
                        double valorPix = lerDouble();

                        logado.transferir(contaPix, valorPix);

                    } else {

                        System.out.println("Chave PIX não encontrada!");
                    }

                    break;

                case 5:

                    logado.mostrarSaldo();

                    break;

                case 6:

                    logado = null;
                    System.out.println("Logout realizado!");

                    break;

                default:

                    System.out.println("Opção inválida!");
            }

        } while (logado != null);
    }

    static Conta buscarConta(int numero) {

        for (Conta c : contas) {

            if (c.numeroConta == numero) {
                return c;
            }
        }

        return null;
    }

    static Conta buscarPix(String chavePix) {

        for (Conta c : contas) {

            if (c.chavePix.equals(chavePix)) {
                return c;
            }
        }

        return null;
    }

    static int lerInt() {

        while (true) {

            try {

                return Integer.parseInt(leia.nextLine());

            } catch (Exception e) {

                System.out.print("Digite apenas números: ");
            }
        }
    }

    static double lerDouble() {

        while (true) {

            try {

                return Double.parseDouble(leia.nextLine());

            } catch (Exception e) {

                System.out.print("Digite um valor válido: ");
            }
        }
    }
}
