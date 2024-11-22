package encodeDecodeTGB;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);
		boolean proceed = true; // Controla se o programa continua executando

		while (proceed) {
			// Escolha entre codificação ou decodificação
			System.out.println("Escolha a operação que deseja realizar:");
			System.out.println("1 - Codificação");
			System.out.println("2 - Decodificação");

			int option = scanner.nextInt();

			if (option != 1 && option != 2) {
				System.out.println("Opção inválida. Encerrando o programa.");
				return;
			}

			// Escolha do método de codificação/decodificação
			System.out.println("Escolha o método:");
			System.out.println("1 - Codigo de Repeticao Ri");
			System.out.println("2 - Hamming (7,4)");

			int method = scanner.nextInt();

			if (method < 1 || method > 2) {
				System.out.println("Método inválido. Encerrando o programa.");
				return;
			}

			// Solicita ao usuário a entrada para codificação/decodificação
			scanner.nextLine();
			System.out.println("Digite o símbolo ou codeword que deseja processar:");
			String input = scanner.nextLine();

			CodigoDeRepeticaoRi codigoRepeticaoRi = new CodigoDeRepeticaoRi();
			Hamming hamming = new Hamming();


			// Processa a entrada com base na operação e no método escolhido
			switch (option) {
			case 1: // Codificação
				switch (method) {
				case 1:
					System.out.println("Digite o valor do i:");
					int i = Integer.parseInt(scanner.nextLine());
					System.out.println(codigoRepeticaoRi.encode(input, i));
					System.out.println("-----------------------------------------");
					break;
				case 2:
					System.out.println(hamming.encode(input));
					System.out.println("-----------------------------------------");
					break;
				}
				break;
			case 2: // Decodificação
				switch (method) {
				case 1:
					System.out.println("Digite o valor do i:");
					int i = Integer.parseInt(scanner.nextLine());
					System.out.println(codigoRepeticaoRi.decode(input, i));
					System.out.println("-----------------------------------------");
					break;
				case 2:
					System.out.println(hamming.decode(input));
					System.out.println("-----------------------------------------");
					break;
				}
				break;
			}

			// Pergunta se o usuário deseja realizar outra ação
			System.out.println("Deseja realizar outra operação? (S/N)");
			String answer = scanner.nextLine().trim().toUpperCase();
			if (!answer.equals("S")) {
				proceed = false;
				System.out.println("Encerrando o programa.");
			}
		}

		scanner.close();
	}
}
