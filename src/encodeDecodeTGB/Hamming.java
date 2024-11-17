package encodeDecodeTGB;

public class Hamming {
	private String inputEncoded;

	private String characterDecoded;
	private String inputDecoded;
	
	public String encode(String input) {
		System.out.println("Codificando com codigo Hamming: " + input);

		if (input.length() != 4) {
            throw new IllegalArgumentException("A entrada deve conter exatamente 4 bits.");
        }

        // Converte a string de entrada para um array de inteiros
        int[] dados = new int[4];
        for (int i = 0; i < 4; i++) {
            dados[i] = Character.getNumericValue(input.charAt(i));
        }

        // Calculando os bits de paridade
        int t5 = dados[0] ^ dados[1] ^ dados[2]; // s1 ^ s2 ^ s3
        int t6 = dados[1] ^ dados[2] ^ dados[3]; // s2 ^ s3 ^ s4
        int t7 = dados[2] ^ dados[3] ^ dados[0]; // s3 ^ s4 ^ s1

        // Concatenando os bits em ordem: s1, s2, s3, s4, t5, t6, t7
        return "" + dados[0] + dados[1] + dados[2] + dados[3] + t5 + t6 + t7;

	}
	
	public String decode(String input) {
		if (input.length() != 7) {
            throw new IllegalArgumentException("A entrada deve conter exatamente 7 bits.");
        }

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

        if (bitErrado != -1) {
            System.out.println("Bit errado na posição: " + bitErrado);
            bits[bitErrado] ^= 1; // Corrige o bit errado
        }

        // Construindo a string de bits corrigidos com indicação do erro
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < bits.length; i++) {
        	if (i == bitErrado) {
                result.append("->");
            }
            result.append(bits[i]);
            
        }

        // Retorna apenas os 4 bits de dados corrigidos
        return "Dados corrigidos: " + bits[0] + bits[1] + bits[2] + bits[3] + "\nBits com indicação: " + result.toString();
	}
}
