import java.util.*;

public class Lzw {
     // comprimir uma string em uma lista de inteiros que indica o arquivo da compressao lwz
     public static List<Integer> compressao(String text) { //função que retornar uma lista de inteiros 

        int dicioTam = 256; //define tamanho do dicionario 
        //O hashMap é utilizado para montar o dicionario de forma que cada string vai ter um valor(inteiro) associado a ela
        Map<String,Integer> dicionario = new HashMap<String,Integer>();
        for (int i = 0; i < 256; i++)
            dicionario.put("" + (char)i, i); // adiciona uma string e um indicie ao dicionario
        
        String w = ""; //armazenar a maior string 'w' existente no dicionario
        List<Integer> resp = new ArrayList<Integer>(); // variavele que vai armazenar o arquivo de inteiros do dicionario
        for (char a : text.toCharArray()) {
            String wa = w + a; //string wa recebe a string w mais o caracter a
            if (dicionario.containsKey(wa)) //verifica se no dicionario já existe a string wa
                w = wa; //string w recebe string wa
            else { // caso a string wa não esteja dentro do dicionario
                resp.add(dicionario.get(w)); //adiciona a lista de inteiros o valor da string w
                dicionario.put(wa, dicioTam++); // Adiciona a string wa ao dicionario e aumenta o tamanho do dicionario
                // 'avançando' para a posicao de a
                w = "" + a; //string w recebe somente o caractere a para continuar a construir o dicionario
            }
        }
        
        if (!w.equals(""))
            resp.add(dicionario.get(w));//Escreve o código para a string w
        return resp; 
    }
  
    public static String descompressao(List<Integer> dado) { // função que recebe uma lista de inteiros e devolve a string descomprimida
        
        int dicioTam = 256; //tam do dicionario
        Map<Integer,String> dicionario = new HashMap<Integer,String>(); //incializar o dicionario
        for (int i = 0; i < 256; i++)
            dicionario.put(i, "" + (char)i); //adicionar ao dicionario o indice e o 'string'
        
        String w = "" + (char)(int)dado.remove(0); //string w recebe o primeiro
        StringBuffer resp = new StringBuffer(w); //String resp, recebe o valor da string w 
        for (int k : dado) { //repete ate o fim dos indices 
            String entrada;
            if (dicionario.containsKey(k)) // verifica se eno dicionario tem o inteiro k
                entrada = dicionario.get(k); // string entrada recebe o valor de k
            else if (k == dicioTam) // verifica se k é do tamanho do dicionario
                entrada = w + w.charAt(0); //string entrada recebe string w mais o primieiro char da string w
            else
                throw new IllegalArgumentException("Compressao de k ruim: " + k);
            
            resp.append(entrada); // acrescenta a string resp o valor da string entrada
            
            // Adiciona a string w+entrada[0] para o dicionario
            dicionario.put(dicioTam++, w + entrada.charAt(0));
            
            w = entrada; // string w recebe a string entrada
        }
        return resp.toString(); //retorna a resposta em formato de string
    }

}
