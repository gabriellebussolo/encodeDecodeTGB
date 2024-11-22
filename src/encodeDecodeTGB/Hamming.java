package encodeDecodeTGB;

public class Hamming {
	private String inputEncoded;

	private String inputDecoded;
	
	public String encode(String input) {
		System.out.println("Codificando com codigo Hamming: " + input);
		
		char[] caracteresInput = input.toCharArray();
        StringBuilder encodedBuilder = new StringBuilder();


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
			
			// Divide o binaryValue em 2 para seguir o padrão de 4 bits
            String binaryValueP1 = binaryValue.substring(0, 4);
            String binaryValueP2 = binaryValue.substring(4);

            // Codifica e adiciona à StringBuilder
            encodedBuilder.append(applyHammingCode(binaryValueP1)).append(applyHammingCode(binaryValueP2));
	        
		}

        inputEncoded = encodedBuilder.toString();
		return inputEncoded;

	}
	
	public String decode(String input) {
		System.out.println("Decodificando com Hamming: " + input);
		inputDecoded = "";
        StringBuilder decodedBuilder = new StringBuilder(); // Usando StringBuilder para melhor desempenho
        
		//Divide o input em strings de 14 characteres (7+7)
        int partsCount = (input.length() + 14 - 1) / 14; 
        String[] parts = new String[partsCount];

        for (int i = 0; i < partsCount; i++) {
            int start = i * 14;
            int end = Math.min(start + 14, input.length());
            parts[i] = input.substring(start, end);
        }

        for (String part : parts) {
            //Divide em 2 para seguir o padrão de 7 bits
			String P1 = part.substring(0, 7);
	        String P2 = part.substring(7); 
	        
	        int p1Decoded = decodeHammingBlock(P1);
	        int p2Decoded = decodeHammingBlock(P2);
	        
	        // Combina os dados decodificados
            String characterEncodedString = String.format("%04d", p1Decoded) + String.format("%04d", p2Decoded);
            int characterDecoded = Integer.parseInt(characterEncodedString, 2);
            decodedBuilder.append((char) characterDecoded);
	        
        }
        
        inputDecoded = decodedBuilder.toString();        
		return inputDecoded;

	}
	
	public int decodeHammingBlock(String input) {
        // Converte a string de entrada para um array de inteiros
        int[] bits = new int[7];
        for (int i = 0; i < 7; i++) {
            bits[i] = Character.getNumericValue(input.charAt(i));
        }

        // Calculando os bits de paridade esperados
        int t5Expected = bits[0] ^ bits[1] ^ bits[2];
        int t6Expected = bits[1] ^ bits[2] ^ bits[3];
        int t7Expected = bits[2] ^ bits[3] ^ bits[0];
        
        boolean erroT5 = false;
        boolean erroT6 = false;
        boolean erroT7 = false;

       //Verificando possíveis erros
        if(t5Expected != bits[4]) {
        	erroT5 = true;
        }
        if(t6Expected != bits[5]) {
        	erroT6 = true;
        }
        if(t7Expected != bits[6]) {
        	erroT7 = true;
        }
        
        int bitErrado = -1;        
        //Erros nos bits de dados
        if(erroT5 && erroT6) {
        	bitErrado = 1;
        }else if(erroT6 && erroT7) {
        	bitErrado = 3;
        } else if(erroT7 && erroT5) {
        	bitErrado = 0;
        }
        
 
        //Erros nos bits de paridade
        if(bitErrado == -1) {
        	if(erroT5) {
            	bitErrado = 4;
            }else if(erroT6) {
            	bitErrado = 5;
            } else if(erroT7) {
            	bitErrado = 6;
            }
        }


        // Construindo a string de bits corrigidos com indicação do erro
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < bits.length; i++) {
        	if (i == bitErrado) {
                result.append("->");
            }
            result.append(bits[i]);
            
        }
        
        if (bitErrado != -1) {
            System.out.println(input + " X︎ | Dados corrigidos: " + result.toString());
            bits[bitErrado] ^= 1; // Corrige o bit errado
        }else {
        	System.out.println(input + " ✔");
        }

        // Retorna apenas os 4 bits de dados corrigidos
        String dadosCorrigidos = "" + bits[0] + bits[1] + bits[2] + bits[3];
        return Integer.parseInt(dadosCorrigidos);
	}
	
	public String applyHammingCode(String inputDados) {

        // Converte a string de entrada para um array de inteiros
        int[] dados = new int[4];
        for (int i = 0; i < 4; i++) {
            dados[i] = Character.getNumericValue(inputDados.charAt(i));
        }

        // Calculando os bits de paridade
        int t5 = dados[0] ^ dados[1] ^ dados[2]; // s1 ^ s2 ^ s3
        int t6 = dados[1] ^ dados[2] ^ dados[3]; // s2 ^ s3 ^ s4
        int t7 = dados[2] ^ dados[3] ^ dados[0]; // s3 ^ s4 ^ s1

        // Concatenando os bits em ordem: s1, s2, s3, s4, t5, t6, t7
        return "" + dados[0] + dados[1] + dados[2] + dados[3] + t5 + t6 + t7;
	}
}
