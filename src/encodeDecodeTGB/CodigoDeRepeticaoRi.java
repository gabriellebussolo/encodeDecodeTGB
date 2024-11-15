package encodeDecodeTGB;

public class CodigoDeRepeticaoRi {
	
	private String inputEncoded;
	
	private char characterDecoded;
	private String inputDecoded;
	
	public String encode(String input, int i) {
		System.out.println("Codificando com codigo de repeticao Ri: " + input);
		
		char[] caracteresInput = input.toCharArray();
		inputEncoded = "";
		
		for(char c : caracteresInput) {
			
			// Converte char para o valor em ASCII
			int valorASCII = (int) c; 
			
			// Converte o valor em ASCII para binario
			String binaryValue = Integer.toBinaryString(valorASCII);
			
			// Garante que o binario possui 8 caracteres
			if (binaryValue.length() < 8) {
				for (int j = 0; j < 8 - binaryValue.length(); j++)
					binaryValue = "0" + binaryValue;
			}
			
			char[] binaryArray = binaryValue.toCharArray();
			
			// Percorre o binario acrescentando a repeticao Ri
			for(char c1 : binaryArray) {
				if (c1 == '0') { //TODO fazer outro for AQUI PQ O I nao é fixo
					for (int j = 0; j < i; j++)
						inputEncoded = inputEncoded + "0";
				}
				else {
					for (int j = 0; j < i; j++)
						inputEncoded = inputEncoded + "1";
				}
			}
			
		}
		
		return inputEncoded;
		
	}
	
	public String decode(String input) {
		return "";
	}
}
