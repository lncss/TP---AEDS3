public class KMP {

    public static void KMP(String text, String padrao){
        // se o padrao esta vazio 
        if (padrao == null || padrao.length() == 0){
            System.out.println("O padrao ocorre com shift 0");
            return;
        }
 
        // se o padrao esta vazio ou possui taanho maior que o texto em que se procura o padrao
        if (text == null || padrao.length() > text.length()){
            System.out.println("Padrao nao encontrado");
            return;
        }
 
        char[] carac = padrao.toCharArray(); 
 
        //prox[i] armazena o índice da próxima melhor correspondência parcial
        int[] prox = new int[padrao.length() + 1];
        for (int i = 1; i < padrao.length(); i++){
            int j = prox[i + 1];
 
            while (j > 0 && carac[j] != carac[i]) {
                j = prox[j];
            }
 
            if (j > 0 || carac[j] == carac[i]) {
                prox[i + 1] = j + 1;
            }
        }
 
        for (int i = 0, j = 0; i < text.length(); i++){
            if (j < padrao.length() && text.charAt(i) == padrao.charAt(j)){
                if (++j == padrao.length()) {
                    System.out.println("O padrao ocorre no shift  " + (i - j + 1));
                }
            }
            else if (j > 0){
                j = prox[j];
                i--; //já que i vai ser decrementado na proxima interação
            }
        }
    }
 
    // verificar se o algoritimo de kmp funciona
    /*public static void main(String[] args){
        String text = "THIS IS A HELLO WORLD";
        String pattern = "HELLO";
 
        KMP(text, pattern);
    }*/

}
