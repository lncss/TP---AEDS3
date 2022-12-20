// path: Huffman.java

import java.util.*;
import java.io.*;

// -------------------- árvore --------------------

class HuffmanTree implements Comparable<HuffmanTree> {
    public final int frequency; // frequência da árvore

    public HuffmanTree(int freq) { frequency = freq; }

    // compara a frequência com outra árvore	
    public int compareTo(HuffmanTree tree) {
        return frequency - tree.frequency;
    }
}

// ------------------------------------------------

// -------------------- folha --------------------

class HuffmanLeaf extends HuffmanTree {
    public final char value; // valor do caractere

    public HuffmanLeaf(int freq, char val) {
        super(freq);
        value = val;
    }
}

// ------------------------------------------------

// -------------------- nó --------------------

class HuffmanNode extends HuffmanTree {
    public final HuffmanTree left, right; // sub-árvores

    // atribui as árvores como esquerda e direita
    public HuffmanNode(HuffmanTree l, HuffmanTree r) {
        super(l.frequency + r.frequency);
        left = l;
        right = r;
    }
}

// --------------------------------------------

public class Huffman {

    // ---------- criar árvore ----------

    public static HuffmanTree buildTree (int[] chars) {

        PriorityQueue<HuffmanTree> trees = new PriorityQueue<HuffmanTree>(); // fila de prioridade

        // criar uma folha para cada caractere e adicioná-la à fila
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] > 0) {
                trees.offer(new HuffmanLeaf(chars[i], (char) i)); // cria o nó folha e insere na fila
            }
        }


        while (trees.size() > 1) {
            // remover as duas árvores de menor frequência
            // --- poll: retorna o proximo elemento da fila ou null se a fila estiver vazia ---
            HuffmanTree a = trees.poll();
            HuffmanTree b = trees.poll();

            // combinar as duas árvores em um novo nó e adicioná-lo à fila
            trees.offer(new HuffmanNode(a, b)); // a = left / b = right
        }
        return trees.poll(); // nó raiz da árvore
    }

    // ----------------------------------

    // ---------- inicio codigo ----------

    public static void start (String text) throws Exception {
        
        // máximo de 256 caracteres diferentes
        int[] chars = new int[256];
        for (char c : text.toCharArray()) {
            chars[c]++;
        }

        HuffmanTree arvore = buildTree(chars); // criar uma árvore para a compactação

        String compress = compress(arvore, text); // compactar a string
        System.out.println("Texto codificado: " + compress);

        String decompress = decompress(arvore, compress); // descompactar a string
        System.out.println("Texto decodificado: " + decompress);

    }

    // -----------------------------------

    // ---------- compactar ----------

    public static String compress(HuffmanTree tree, String text) {
        
        assert tree != null; // verifica se a árvore não é nula

        String compress = "";

        for (char c : text.toCharArray()) {
            compress += (code(tree, new StringBuffer(), c));
        }

        return compress; // retorna a string compactada
    }
        
    public static String compress (String text) {
        // máximo de 256 caracteres diferentes
        int[] chars = new int[256];
        for (char c : text.toCharArray()) {
            chars[c]++;
        }

        HuffmanTree arvore = buildTree(chars); // criar uma árvore para a compactação
        
        return compress(arvore, text);
    }
    // -------------------------------

    // ---------- codigo ----------

    public static String code (HuffmanTree tree, StringBuffer prefixo, char c) {
        
        assert tree != null; // verifica se a árvore não é nula

        // ----- se tiver um nó folha (último nó) -----
        if ((tree instanceof HuffmanLeaf) == true) {

            HuffmanLeaf folha = (HuffmanLeaf) tree; // arvore = folha

            // --- retorna o texto compactado ---
            if (folha.value == c) {
                return prefixo.toString();
            }
        } 

        // ----- se tiver um nó que não seja folha -----
        else if ((tree instanceof HuffmanNode) == true) {

            HuffmanNode no = (HuffmanNode) tree; // arvore = no

            // percorre a esquerda da árvore
            prefixo.append('0'); // adiciona 0 ao prefixo
            String esquerda = code(no.left, prefixo, c);
            prefixo.deleteCharAt(prefixo.length() - 1);

            // percorre a direita da árvore
            prefixo.append('1'); // adiciona 1 ao prefixo
            String direita = code(no.right, prefixo, c);
            prefixo.deleteCharAt(prefixo.length() - 1);

            // --- retorna o prefixo ---
            if (esquerda == null) {
                return direita;
            } else {
                return esquerda;
            }
        }

        return null;
    }

    // ----------------------------

    // ---------- descompressao ----------

    public static String decompress(HuffmanTree tree, String text) {
        
        assert tree != null; // verifica se a árvore não é nula

        String decompress = "";

        HuffmanNode no = (HuffmanNode) tree; // arvore = tree

        for (char c : text.toCharArray()) {
            if (c == '0') {
                if (no.left instanceof HuffmanLeaf) {
                    decompress += ((HuffmanLeaf)no.left).value;
                    no = (HuffmanNode) tree;
                } else {
                    no = (HuffmanNode) no.left;
                }
            } else if (c == '1') {
                if (no.right instanceof HuffmanLeaf) {
                    decompress += ((HuffmanLeaf) no.right).value;
                    no = (HuffmanNode) tree;
                } else {
                    no = (HuffmanNode) no.right;
                }
            }
        }

        return decompress; // retorna string descompactada
    }

    // -----------------------------------
}