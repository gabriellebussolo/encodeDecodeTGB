package encodeDecodeTGB;

public class CodigoDeRepeticaoRi {

	private String inputEncoded;

	private String characterDecoded;
	private String inputDecoded;

	public String encode(String input, int i) {
		System.out.println("Codificando com codigo de repeticao R" + i + ": " + input);

		char[] caracteresInput = input.toCharArray();
		inputEncoded = "";

		for (char c : caracteresInput) {

			// Converte char para o valor em ASCII
			int valorASCII = (int) c;

			// Converte o valor em ASCII para binario
			String binaryValue = Integer.toBinaryString(valorASCII);

			// Garante que o binario possui 8 caracteres
			if (binaryValue.length() < 8) {
				while(binaryValue.length() < 8)
					binaryValue = "0" + binaryValue;
			}

			char[] binaryArray = binaryValue.toCharArray();

			// Percorre o binario acrescentando a repeticao Ri
			for (char c1 : binaryArray) {
				if (c1 == '0') {
					for (int j = 0; j < i; j++)
						inputEncoded = inputEncoded + "0";
				} else {
					for (int j = 0; j < i; j++)
						inputEncoded = inputEncoded + "1";
				}
			}

		}

		return inputEncoded;

	}

	public String decode(String input, int i) {
		System.out.println("Decodificando com codigo de repeticao R"+ i + ": " + input);
		
		char[] caracteresInput = input.toCharArray();
	
		characterDecoded = "";
		inputDecoded = "";
		
		int contBits = 0;
		int cont1 = 0;
		int cont0 = 0;
		int contRepeticao = 0;
		
		
		for(char c : caracteresInput) {
			
			
			if(contRepeticao == i-1) {
				if (c == '0')
					cont0++;
				else
					cont1++;
				
				// Acrescenta na decodificacao do caracter o binario que mais apareceu na repeticao
				if (cont0 > cont1)
					characterDecoded += '0';
				else
					characterDecoded += '1';
					
				cont0 = 0;
				cont1 = 0;
				contRepeticao = 0;
					
				contBits++;
				
				// Caso tenha contado 8 bits, forma o caracter
				if(contBits == 8) {	
					int valueASCII = Integer.parseInt(characterDecoded, 2);
					
					inputDecoded = inputDecoded + (char) valueASCII;
					
					characterDecoded = "";
					contBits = 0;
				}
					
			}
			else {
				if (c == '0')
					cont0++;
				else
					cont1++;
				contRepeticao++;
			}
		
				
		}

	return inputDecoded;
	}

}
