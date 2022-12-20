
/************************************************************
 * AEDS3 - TP03
 * 
 * Integrantes: Bárbara Luciano e Luisa Nogueira
 * 
************************************************************/

import java.io.RandomAccessFile;
import java.lang.reflect.Field;
import java.io.IOException;
import java.util.*;
import java.io.File;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.util.Collections;
import java.io.DataOutputStream;
//import Lwz.java;
//import Huffman.java;

class Conta {

    // --------------- atributos ---------------

    private int idConta;
    private String nomePessoa;
    private int qtd;
    private String[] email;
    private String nomeUsuario;
    private String senha;
    private String cpf;
    private String cidade;
    private int transferenciasRealizadas;
    private float saldoConta;

    // -----------------------------------------

    // --------------- construtores ---------------

    public Conta() {
        this.idConta = 0;
        this.nomePessoa = "";
        this.qtd = 0;
        this.email = new String[qtd];
        this.nomeUsuario = "";
        this.senha = "";
        this.cpf = "";
        this.cidade = "";
        this.transferenciasRealizadas = 0;
        this.saldoConta = 0;
    }

    public Conta(int idConta, String nomePessoa, int qtd, String[] email, String nomeUsuario, String senha, String cpf,
            String cidade, int transferenciasRealizadas, float saldoConta) {
        setIdConta(idConta);
        setNomePessoa(nomePessoa);
        setQtd(qtd);
        setEmail(email);
        setNomeUsuario(nomeUsuario);
        setSenha(senha);
        setCpf(cpf);
        setCidade(cidade);
        setTransferenciasRealizadas(transferenciasRealizadas);
        setSaldoConta(saldoConta);
    }

    // --------------------------------------------

    // --------------- gets e sets ---------------

    public void setIdConta(int idConta) {
        this.idConta = idConta;
    }

    public int getIdconta() {
        return this.idConta;
    }

    public void setNomePessoa(String nomePessoa) {
        this.nomePessoa = nomePessoa;
    }

    public String getNomePessoa() {
        return this.nomePessoa;
    }

    public void setQtd(int qtd) {
        this.qtd = qtd;
    }

    public int getQtd() {
        return qtd;
    }

    public void setEmail(String[] email) {
        this.email = email;
    }

    public String[] getEmail() {
        return this.email;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getNomeUsuario() {
        return this.nomeUsuario;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getSenha() {
        return this.senha;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCpf() {
        return this.cpf;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getCidade() {
        return this.cidade;
    }

    public void setTransferenciasRealizadas(int transferenciasRealizadas) {
        this.transferenciasRealizadas = transferenciasRealizadas;
    }

    public int getTransferenciasRealizadas() {
        return this.transferenciasRealizadas;
    }

    public void setSaldoConta(float saldoConta) {
        this.saldoConta = saldoConta;
    }

    public float getSaldoConta() {
        return this.saldoConta;
    }

    // -------------------------------------------

    // --------------- metodos ---------------
    // ------- printar -------

    public Conta print() {
        return (this);
    }

    // -----------------------

    // ------- dados em bytes -------

    public byte[] toByteArray() throws Exception {
        ByteArrayOutputStream put = new ByteArrayOutputStream();
        DataOutputStream data = new DataOutputStream(put);

        // --- escreve os dados ---
        data.writeInt(this.getIdconta());
        data.writeUTF(this.getNomePessoa());

        // --- escreve todos os emails ---
        for (int i = 0; i < this.getQtd(); i++) {
            data.writeUTF(this.getEmail()[i]);
        }

        data.writeUTF(this.getNomeUsuario());
        data.writeUTF(this.getSenha());
        data.writeUTF(this.getCpf());
        data.writeUTF(this.getCidade());
        data.writeInt(this.getTransferenciasRealizadas());
        data.writeFloat(this.getSaldoConta());

        put.close(); // fecha o fluxo de dados
        data.close(); // fecha o fluxo de dados

        return put.toByteArray(); // retorna os dados em bytes

    }

    public void toStringArray(byte[] by) throws Exception {
        ByteArrayInputStream b = new ByteArrayInputStream(by);
        DataInputStream data = new DataInputStream(b);

        this.idConta = data.readInt();
        this.nomePessoa = data.readUTF();

        for (int i = 0; i < this.getQtd(); i++) {
            this.email[i] = data.readUTF();
        }

        this.nomeUsuario = data.readUTF();
        this.senha = data.readUTF();
        this.cpf = data.readUTF();
        this.transferenciasRealizadas = data.readInt();
        this.saldoConta = data.readFloat();
    }
    // ------------------------------

    // ---------------------------------------
}

class Intercalacao {

    public static void deleteArq() { // Apagar arquivos já existentes
        File file1 = new File("file1.bin");
        File file2 = new File("file2.bin");
        File file3 = new File("file3.bin");
        File file4 = new File("file4.bin");
        File file5 = new File("finalFile.bin");

        file1.delete();
        file2.delete();
        file3.delete();
        file4.delete();
        file5.delete();

    }

    public static Conta ler(RandomAccessFile arq, int pesquisa) {

        try {
            Conta conta1 = new Conta();

            arq.seek(0); // Ponteiro no inicio
            while (arq.getFilePointer() < arq.length()) { // Percorre o arq ate que seja do tamanho do arq original
                char lapide = arq.readChar();

                if (lapide == ' ') { // verifica se a lapide nn esta ativa
                    int arqSize = arq.readInt(); // Pegar o tam do registro
                    conta1.setIdConta(arq.readInt()); // Set no Id para verificar a conta

                    if (conta1.getIdconta() == pesquisa) { // Verifica o ID
                        conta1.setNomePessoa(arq.readUTF());
                        String arrayAux[] = new String[conta1.getQtd()]; // Cria um array auxiliar para receber os
                                                                         // emails
                        int qtdEmail = arq.readInt();

                        for (int i = 0; i < qtdEmail; i++) {
                            arrayAux[i] = arq.readUTF();
                        }

                        conta1.setEmail(arrayAux);
                        conta1.setNomeUsuario(arq.readUTF());
                        conta1.setSenha(arq.readUTF());
                        conta1.setCpf(arq.readUTF());
                        conta1.setCidade(arq.readUTF());
                        conta1.setTransferenciasRealizadas(arq.readInt());
                        conta1.setSaldoConta(arq.readFloat());

                        return conta1;
                    } else { // Se o ID for diferente
                        arq.skipBytes(arqSize - 4); // Pula todo o registro
                    }
                } else {
                    int tamInt = arq.readInt();
                    arq.skipBytes(tamInt);
                }
            }

            return null;
        } catch (Exception e) {
            System.out.println("Nao foi possivel ler o registro!");
            return null;
        }
    }

    public static boolean intercalar(RandomAccessFile arq, int limite) throws Exception {
        deleteArq(); // deleta os arq que ja existem
        ArrayList<Conta> conta2 = new ArrayList<Conta>();
        Conta cont = new Conta();

        // cria os arq em binario
        RandomAccessFile file1 = new RandomAccessFile("file1.bin", "rw");
        RandomAccessFile file2 = new RandomAccessFile("file2.bin", "rw");
        RandomAccessFile file3 = new RandomAccessFile("file3.bin", "rw");
        RandomAccessFile file4 = new RandomAccessFile("file4.bin", "rw");
        RandomAccessFile file5 = new RandomAccessFile("finalFile.bin", "rw");

        try {
            arq.seek(4); // Ponteiro no inicio do arq
            while (arq.getFilePointer() < arq.length()) { // percorrer todo o arq

                if (arq.readChar() == ' ') { // verificar se a lapode ta ativa
                    int tamFile = arq.readInt(); // tam do arq
                    int idNew = arq.readInt(); // set o ID
                    cont = Banco.readId(arq, idNew);

                    conta2.add(cont); // add o registro no array
                } else {
                    arq.skipBytes(arq.readInt()); // Pulo o registro
                }
            }

        } catch (Exception e) {
            arq.skipBytes(arq.readInt());
            System.out.println("Nao foi possivel ler o arquivo!");
        }

        int contVolta = 0;
        ArrayList<Conta> auxConta = new ArrayList<Conta>();

        // ------------------- 1 distribuição --------------------
        while (conta2.size() > 0) { // Enquanto o array list tiver algum dado
            contVolta++;

            for (int i = 0; i < limite; i++) {
                if (conta2.size() > 0) { // verifica se o arrayList nn ta vazio
                    auxConta.add(conta2.get(0)); // Adiciono o elemento no arrayList Temp
                    conta2.remove(0); // Removo o elemento do arrayList principal
                }
            }

            auxConta.sort((Conta c1, Conta c2) -> c1.getIdconta() - c2.getIdconta()); // Ordena temporariamente

            int tamTmp = auxConta.size();
            for (int i = 0; i < tamTmp; i++) {
                Banco.create(file1, auxConta.get(0));
                conta2.remove(0);
            }

            for (int i = 0; i < limite; i++) {
                if (conta2.size() > 0) { // verifica se o arraylist nn ta vazio
                    auxConta.add(conta2.get(0));// Adiciono o elemento no arraylist temporario
                    conta2.remove(0);// Removo o elemento do arraylist principal
                }
            }

            auxConta.sort((Conta c1, Conta c2) -> c1.getIdconta() - c2.getIdconta()); // Ordena temporariamente

            tamTmp = auxConta.size();
            for (int i = 0; i < tamTmp; i++) {
                Banco.create(file2, auxConta.get(0));
                conta2.remove(0);
            }

            auxConta.clear(); // Limpo o arraylist temporario
        }

        // Ler arquivos 1 e 2
        ArrayList<Conta> account1 = new ArrayList<Conta>();
        ArrayList<Conta> account2 = new ArrayList<Conta>();

        try {
            file1.seek(0); // Ponteiro no incio do arq
            while (file1.getFilePointer() < file1.length()) { // percorrer todo o arquivio

                char lapide = file1.readChar(); // ler a lapide
                if (lapide == ' ') { // verifica se a lapide esta vazia
                    int tamFile1 = file1.readInt(); // tam do arq
                    int idNew = file1.readInt();// set id
                    cont = ler(file1, idNew);
                    account1.add(cont);
                } else {
                    file1.skipBytes(file1.readInt()); // Pula todo o registro
                }
            }
        } catch (Exception e) {
            System.out.println("Nao foi possivel ler o arquivo");
        }

        try {
            file2.seek(0); // Ponteiro no incio do arq
            while (file2.getFilePointer() < file2.length()) { // percorrer todo o arquivio

                char lapide = file2.readChar(); // ler a lapide
                if (lapide == ' ') { // verificar se a lapide esta ativa
                    int tamFile2 = file2.readInt(); // tam do arq
                    int idNew = file2.readInt(); // set ID
                    cont = ler(file2, idNew);
                    account2.add(cont);
                } else {
                    file2.skipBytes(file2.readInt()); // Pula todo o registro
                }
            }
        } catch (Exception e) {
            System.out.println("Nao foi possivel ler o arquivo");
        }

        // ---------- Primeira Intercalação -----------
        for (int j = 0; j < contVolta; j++) {
            int usoAccount1 = 0;
            int usoAccount2 = 0;

            for (int i = 0; i < (limite * 2); i++) {
                if (account1.size() == 0 && account2.size() == 0) {
                } else if (account1.size() != 0 && account2.size() == 0 && usoAccount1 < limite) {
                    Banco.create(file3, account1.get(0));
                    account1.remove(0);
                    usoAccount1++;

                } else if (account1.size() == 0 && account2.size() != 0 && usoAccount2 < limite) {
                    Banco.create(file3, account2.get(0));
                    account2.remove(0);
                    usoAccount2++;

                } else if (usoAccount1 == limite && usoAccount2 < limite) {
                    Banco.create(file3, account2.get(0));
                    account2.remove(0);
                    usoAccount2++;

                } else if (usoAccount1 < limite && usoAccount2 == limite) {
                    Banco.create(file3, account2.get(0));
                    account2.remove(0);
                    usoAccount2++;

                } else if (account1.size() != 0 && account2.size() != 0) {
                    if (account1.get(0).getIdconta() < account2.get(0).getIdconta() && usoAccount1 < limite) {
                        Banco.create(file3, account1.get(0));
                        account1.remove(0);
                        usoAccount1++;

                    } else if (account1.get(0).getIdconta() > account2.get(0).getIdconta() && usoAccount2 < limite) {
                        Banco.create(file3, account2.get(0));
                        account2.remove(0);
                        usoAccount2++;

                    }
                }
            }

            usoAccount1 = 0;
            usoAccount2 = 0;

            for (int i = 0; i < (limite * 2); i++) {
                if (account1.size() == 0 && account2.size() == 0) {
                } else if (account1.size() != 0 && account2.size() == 0 && usoAccount1 < limite) {
                    Banco.create(file4, account1.get(0));
                    account1.remove(0);
                    usoAccount1++;

                } else if (account1.size() == 0 && account2.size() != 0 && usoAccount2 < limite) {
                    Banco.create(file4, account2.get(0));
                    account2.remove(0);
                    usoAccount2++;

                } else if (usoAccount1 == limite && usoAccount2 < limite) {
                    Banco.create(file4, account2.get(0));
                    account2.remove(0);
                    usoAccount2++;

                } else if (usoAccount1 < limite && usoAccount2 == limite) {
                    Banco.create(file4, account2.get(0));
                    account2.remove(0);
                    usoAccount2++;

                } else if (account1.size() != 0 && account2.size() != 0) {
                    if (account1.get(0).getIdconta() < account2.get(0).getIdconta() && usoAccount1 < limite) {
                        Banco.create(file4, account1.get(0));
                        account1.remove(0);
                        usoAccount1++;

                    } else if (account1.get(0).getIdconta() > account2.get(0).getIdconta() && usoAccount2 < limite) {
                        Banco.create(file4, account2.get(0));
                        account2.remove(0);
                        usoAccount2++;

                    }
                }
            }
        }

        // Ler arquivos 3 e 4

        file1.setLength(0);// Limpa o arquivo
        file2.setLength(0); // Limpa o arquivo
        account1.clear(); // Limpa o arraylist
        account2.clear(); // Limpa o arraylist
        auxConta.clear(); // Limpa o arraylist

        try {
            file3.seek(0); // Ponteiro no incio do arq
            while (file3.getFilePointer() < file3.length()) { // percorrer todo o arquivio
                Conta newAccount1 = new Conta();
                char lapide = file3.readChar(); // ler a lapide
                if (lapide == ' ') { // verifica se a lapide esta vazia
                    int tamFile3 = file3.readInt(); // tam do arq
                    int idNew = file3.readInt();// set id
                    newAccount1 = ler(file3, idNew);
                    account1.add(newAccount1);
                } else {
                    file3.skipBytes(file3.readInt()); // Pula todo o registro
                }
            }
        } catch (Exception e) {
            System.out.println("Nao foi possivel ler o arquivo");
        }

        try {
            file4.seek(0); // Ponteiro no incio do arq
            while (file4.getFilePointer() < file4.length()) { // percorrer todo o arquivio
                Conta newAccount2 = new Conta();
                char lapide = file4.readChar(); // ler a lapide
                if (lapide == ' ') { // verificar se a lapide esta ativa
                    int tamFile4 = file4.readInt(); // tam do arq
                    int idNew = file4.readInt(); // set ID
                    newAccount2 = ler(file4, idNew);
                    account2.add(newAccount2);
                } else {
                    file4.skipBytes(file4.readInt()); // Pula todo o registro
                }
            }
        } catch (Exception e) {
            System.out.println("Nao foi possivel ler o arquivo");
        }

        // ---------- Segunda Intercalação -----------
        for (int j = 0; j < contVolta; j++) {
            int usoAccount1 = 0;
            int usoAccount2 = 0;

            for (int i = 0; i < (limite * 4); i++) {
                if (account1.size() == 0 && account2.size() == 0) {
                } else if (account1.size() != 0 && account2.size() == 0 && usoAccount1 < limite) {
                    Banco.create(file1, account1.get(0));
                    account1.remove(0);
                    usoAccount1++;

                } else if (account1.size() == 0 && account2.size() != 0 && usoAccount2 < limite) {
                    Banco.create(file1, account2.get(0));
                    account2.remove(0);
                    usoAccount2++;

                } else if (usoAccount1 == limite && usoAccount2 < limite) {
                    Banco.create(file1, account2.get(0));
                    account2.remove(0);
                    usoAccount2++;

                } else if (usoAccount1 < limite && usoAccount2 == limite) {
                    Banco.create(file1, account2.get(0));
                    account2.remove(0);
                    usoAccount2++;

                } else if (account1.size() != 0 && account2.size() != 0) {
                    if (account1.get(0).getIdconta() < account2.get(0).getIdconta() && usoAccount1 < limite) {
                        Banco.create(file1, account1.get(0));
                        account1.remove(0);
                        usoAccount1++;

                    } else if (account1.get(0).getIdconta() > account2.get(0).getIdconta() && usoAccount2 < limite) {
                        Banco.create(file1, account2.get(0));
                        account2.remove(0);
                        usoAccount2++;

                    }
                }
            }

            usoAccount1 = 0;
            usoAccount2 = 0;

            for (int i = 0; i < (limite * 4); i++) {
                if (account1.size() == 0 && account2.size() == 0) {
                } else if (account1.size() != 0 && account2.size() == 0 && usoAccount1 < limite) {
                    Banco.create(file2, account1.get(0));
                    account1.remove(0);
                    usoAccount1++;

                } else if (account1.size() == 0 && account2.size() != 0 && usoAccount2 < limite) {
                    Banco.create(file2, account2.get(0));
                    account2.remove(0);
                    usoAccount2++;

                } else if (usoAccount1 == limite && usoAccount2 < limite) {
                    Banco.create(file2, account2.get(0));
                    account2.remove(0);
                    usoAccount2++;

                } else if (usoAccount1 < limite && usoAccount2 == limite) {
                    Banco.create(file2, account2.get(0));
                    account2.remove(0);
                    usoAccount2++;

                } else if (account1.size() != 0 && account2.size() != 0) {
                    if (account1.get(0).getIdconta() < account2.get(0).getIdconta() && usoAccount1 < limite) {
                        Banco.create(file2, account1.get(0));
                        account1.remove(0);
                        usoAccount1++;

                    } else if (account1.get(0).getIdconta() > account2.get(0).getIdconta() && usoAccount2 < limite) {
                        Banco.create(file2, account2.get(0));
                        account2.remove(0);
                        usoAccount2++;

                    }
                }
            }
        }

        // Ler arquivos 1 e 2
        account1.clear();
        account2.clear();

        try {
            file1.seek(0); // Ponteiro no incio do arq
            while (file1.getFilePointer() < file1.length()) { // percorrer todo o arquivio
                Conta newAccount1 = new Conta();
                char lapide = file1.readChar(); // ler a lapide
                if (lapide == ' ') { // verifica se a lapide esta vazia
                    int tamFile1 = file1.readInt(); // tam do arq
                    int idNew = file1.readInt();// set id
                    newAccount1 = ler(file1, idNew);
                    account1.add(newAccount1);
                } else {
                    file1.skipBytes(file1.readInt()); // Pula todo o registro
                }
            }
        } catch (Exception e) {
            System.out.println("Nao foi possivel ler o arquivo");
        }

        try {
            file2.seek(0); // Ponteiro no incio do arq
            while (file2.getFilePointer() < file2.length()) { // percorrer todo o arquivio
                Conta newAccount2 = new Conta();
                char lapide = file2.readChar(); // ler a lapide
                if (lapide == ' ') { // verificar se a lapide esta ativa
                    int tamFile2 = file2.readInt(); // tam do arq
                    int idNew = file2.readInt(); // set ID
                    newAccount2 = ler(file2, idNew);
                    account2.add(newAccount2);
                } else {
                    file2.skipBytes(file2.readInt()); // Pula todo o registro
                }
            }
        } catch (Exception e) {
            System.out.println("Nao foi possivel ler o arquivo");
        }

        // ---------- Terceira Intercalação -----------

        int tamFinal = account1.size() + account2.size();

        for (int i = 0; i < tamFinal; i++) {

            if (account1.size() == 0 && account2.size() == 0) {
            }

            else if (account1.size() != 0 && account2.size() == 0) {
                Banco.create(file5, account1.get(0));
                account1.remove(0);

            } else if (account1.size() == 0 && account2.size() != 0) {
                Banco.create(file5, account2.get(0));
                account2.remove(0);

            } else if (account1.size() != 0 && account2.size() != 0) {
                if (account1.get(0).getIdconta() < account2.get(0).getIdconta()) {
                    Banco.create(file5, account1.get(0));
                    account1.remove(0);

                } else if (account1.get(0).getIdconta() > account2.get(0).getIdconta()) {
                    Banco.create(file5, account2.get(0));
                    account2.remove(0);

                }
            } else {
                return false;
            }
        }
        return true;
    }
}

// --------------------- CRUD -----------------------
public class Banco {

    // ----- gerar chave aleatoria -----
    public static String getChave(String s) {

        String chave = "";

        for (int i = 0; i < s.length(); i++) {
            Random r = new Random();
            
            if (s.charAt(i) >= 48 && s.charAt(i) <= 57) { // numeros
                chave += (char) (r.nextInt(10) + 48);
            } else if (s.charAt(i) >= 65 && s.charAt(i) <= 90) { // letras maiusculas
                chave += (char) (r.nextInt(26) + 65);
            } else if (s.charAt(i) >= 97 && s.charAt(i) <= 122) { // letras minusculas
                chave += (char) (r.nextInt(26) + 97);
            } else { // outros caracteres
                chave += (char) (r.nextInt(10) + 48);
            }
        }

        return chave;
    }

    public static String key;

    // --------------- CREATE ---------------
    public static boolean create(RandomAccessFile arq, Conta conta) throws Exception {
        boolean resp = false;

        try {
            arq.seek(arq.length()); // Ponteiro vai para o final do arquivo
            arq.writeByte(0); // Lápide
            arq.writeInt(conta.toByteArray().length);// Escreve os dados em bytes

            arq.writeInt(conta.getIdconta());
            arq.writeUTF(conta.getNomePessoa());
            arq.writeInt(conta.getQtd());

            // --- escreve todos os emails ---
            for (int i = 0; i < conta.getQtd(); i++) {
                arq.writeUTF(conta.getEmail()[i]);
            }

            arq.writeUTF(conta.getNomeUsuario());

            key = getChave(conta.getSenha());
            String password = OTP.cifrar(conta.getSenha(), key);
            arq.writeUTF(password);

            arq.writeUTF(conta.getCpf());
            arq.writeUTF(conta.getCidade());
            arq.writeInt(conta.getTransferenciasRealizadas());
            arq.writeFloat(conta.getSaldoConta());

            System.out.println("Senha criptografada: " + password);
            System.out.println("Chave: " + key);

            arq.seek(0); // Ponteiro vai para o início do arquivo
            resp = true;
        } catch (Exception e) {
            throw new Exception("Erro ao inserir registro");
        }

        return resp;
    }

    // --------------------------------------

    // --------------- READ ---------------

    public static Conta readId(RandomAccessFile arq, int pesquisa) throws Exception {

        try {
            Conta conta = new Conta();
            arq.seek(4); // Ponteiro no inicio do arquivo

            while (arq.getFilePointer() < arq.length()) { // Até o ponteiro chegar ao final do arquivo
                if (arq.readByte() == 0) {
                    int tam = arq.readInt();

                    conta.setIdConta(arq.readInt());

                    if (conta.getIdconta() == pesquisa) { // Caso o id da conta seja igual ao id procurado
                        conta.setNomePessoa(arq.readUTF());

                        String[] emails = new String[1000]; // armazenar os emails

                        // --- ler todos os emails ---
                        for (int i = 0; i < conta.getQtd(); i++) {
                            emails[i] = arq.readUTF();
                        }

                        conta.setEmail(emails);// adiciona os emails

                        conta.setNomeUsuario(arq.readUTF());

                        conta.setSenha(OTP.decifrar(arq.readUTF(), key));

                        conta.setCpf(arq.readUTF());
                        conta.setCidade(arq.readUTF());
                        conta.setTransferenciasRealizadas(arq.readInt());
                        conta.setSaldoConta(arq.readFloat());

                        return conta;
                    } else {
                        arq.skipBytes(tam - 4); // Pula o restante do registro
                    }
                } else {
                    arq.skipBytes(arq.readInt()); // Pula o registro todo
                }
            }

            return null;

        } catch (Exception e) {
            System.out.println("Nao foi possível ler o registro!");
            return null;
        }
    }

    public static Conta readUser(RandomAccessFile arq, String pesquisa) {
        try {
            Conta conta = null;
            boolean searchUser = false;

            arq.seek(4); // Ponteiro no inicio do arquivo
            while (arq.getFilePointer() < arq.length() && !searchUser) {
                if (arq.readByte() == 0) {
                    conta = new Conta();
                    arq.readInt();

                    conta.setIdConta(arq.readInt());
                    conta.setNomePessoa(arq.readUTF());

                    String[] emails = new String[1000]; // armazenar os emails

                    // --- ler todos os emails ---
                    for (int i = 0; i < conta.getQtd(); i++) {
                        emails[i] = arq.readUTF();
                    }

                    conta.setEmail(emails); // adiciona os emails

                    conta.setNomePessoa(arq.readUTF());

                    conta.setSenha(OTP.decifrar(arq.readUTF(), key));

                    conta.setCpf(arq.readUTF());
                    conta.setCidade(arq.readUTF());
                    conta.setTransferenciasRealizadas(arq.readInt());
                    conta.setSaldoConta(arq.readFloat());

                    if (conta.getNomeUsuario().equals(pesquisa)) {
                        searchUser = true;
                    }
                } else { // arquivo ecluido
                    arq.skipBytes(arq.readInt());
                }
            }

            return conta;
        } catch (Exception e) {
            System.out.println("Nao foi possivel ler o registro!");
            return null;
        }
    }

    // --------------------------------------

    // --------------- UPDATE ---------------

    public static boolean update(RandomAccessFile arq, Conta conta) {
        try {

            arq.seek(4); // Ponteiro no inicio do arquivo
            while (arq.getFilePointer() < arq.length()) { // Enquanto nao chegar no fim do arquivo
                if (arq.readByte() == 0) {
                    int tam = arq.readInt();

                    if (arq.readInt() == conta.getIdconta()) { // Verifica se o ID da conta é igual ao da conta a ser
                                                               // atualizada
                        if (tam >= conta.toByteArray().length) { // verifica o tamanho dos registros, se iguais encaixa
                                                                 // no mesmo registro
                            arq.writeUTF(conta.getNomePessoa());

                            // --- atualiza todos os emails ---
                            for (int i = 0; i < conta.getQtd(); i++) {
                                arq.writeUTF(conta.getEmail()[i]);
                            }

                            arq.writeUTF(conta.getNomeUsuario());

                            key = getChave(conta.getSenha());
                            arq.writeUTF(OTP.cifrar(conta.getSenha(), key));

                            arq.writeUTF(conta.getCpf());
                            arq.writeUTF(conta.getCidade());
                            arq.writeInt(conta.getTransferenciasRealizadas());
                            arq.writeFloat(conta.getSaldoConta());

                            return true;

                        } else {// se o tam do registro for menor deve-se criar um novo registro
                            arq.seek(arq.getFilePointer() - 9); // Ponteiro no inicio do arq
                            arq.writeByte(1);
                            return create(arq, conta); // chama o metodo para criar outro registro
                        }
                    } else {
                        arq.skipBytes(tam - 4);
                    }
                } else {
                    arq.skipBytes(arq.readInt());
                }
            }

            return true;
        } catch (Exception e) {
            System.out.println("Nao foi possivel atualizar o registro!");
            return false;
        }
    }

    // --------------------------------------

    // --------------- DELETE ---------------
    public static boolean delete(RandomAccessFile arq, int idDelete) throws IOException {
        int tamDado;

        try {
            Conta conta1 = new Conta();

            arq.seek(4); // Ponteiro no inico do arquivo
            while (arq.getFilePointer() < arq.length()) { // Enqunato menor que o arquivo, percorrer ele todo
                if (arq.readByte() == 0) { // verifica se a lapide esta ativa
                    tamDado = arq.readInt(); // aramazena o tamanho do arquivo
                    conta1.setIdConta(arq.readInt());

                    // int id = arq.readInt();

                    if (conta1.getIdconta() == idDelete) { // Compara os ids, se iguais a conta sera excluida
                        arq.seek(arq.getFilePointer() - 10); // Ponteiro volta pro inicio do arquivo
                        arq.writeByte(1);
                        return true;
                    } else {
                        arq.skipBytes(tamDado - 4); // Pula o resto do registro
                    }

                } else {
                    arq.skipBytes(arq.readInt()); // Pula todo o registro, ja qu e a lapaide esta desativaada
                }
            }

            return false;
        } catch (Exception e) {
            System.out.println("Arquivo nao existe ou nao pode ser excluido!");
            return false;
        }
    }
    // --------------------------------------

    // --------------------------------------

    // -------------- METODOS ---------------

    // ------- lista invertida -------
    public static boolean listaInvertida(RandomAccessFile arq, String name) throws Exception {
        Conta conta = new Conta(); // cria uma nova conta
        RandomAccessFile list = new RandomAccessFile("listName", "rw");
        if (list.length() != 0) { // verifica se o arquivo ta vazio
            list.setLength(0); // zera o arquivo
        }

        System.out.println("\nNome: " + name);
        list.writeUTF(name); // Escreve o nome no arquivo

        arq.seek(4); // Ponteiro no inicio do arquivo
        int cont = 0; // Conta os registros
        while (arq.length() != arq.getFilePointer()) { // Enquanto nao for igual ao final do arquivo
            double ponteiro = arq.getFilePointer(); // Pega o atual ponteiro
            if (arq.readByte() == 0) {
                arq.readInt(); // tamanho
                int id = arq.readInt(); // id
                String n = arq.readUTF(); // nome

                String[] emails = new String[1000]; // armazenar os emails
                for (int i = 0; i < conta.getQtd(); i++) {
                    emails[i] = arq.readUTF(); // email
                }

                arq.readUTF(); // user
                arq.readUTF(); // senha
                arq.readUTF(); // cpf
                arq.readUTF(); // cidade
                arq.readInt(); // transacoes
                arq.readFloat(); // saldo
                if (n.equals(name)) { // Se o nome for igual ao passado por parametro
                    System.out.println("ID: " + id + "- Posicao: " + (int) ponteiro); // imprime a posicoa e o id do
                                                                                      // registro
                    list.writeInt(id); // Escreve o id no arquivo
                    list.writeInt((int) ponteiro); // Escreve a posicao do registro no arq da lista
                    cont++;
                }
            } else { // Caso o registro esteja inativo
                arq.skipBytes(arq.readInt()); // Pula o registro
            }
        }

        System.out.println("Quantidade de registros: " + cont);
        list.writeInt(cont);// Escreve a qntd no arquivo

        list.close();

        return true;
    }
    // -------------------------------

    // ----------------------------
    // ----------------------------

    public static void main(String[] args) throws Exception {

        Scanner sc = new Scanner(System.in);
        int resp = 0, id = 0, r = 0, aux = 0;
        String codeText = "";
        Conta cont = new Conta();

        RandomAccessFile arq = new RandomAccessFile("contas.txt", "rw");
        RandomAccessFile compFile = new RandomAccessFile("contasHuffmanCompressao1", "rw");

        // --------------- menu ---------------

        do {
            System.out.println("\n ----- Iniciando o Sistema --------");
            System.out.println("\n--------------- menu ---------------");
            System.out.println("\t1- Criar conta");
            System.out.println("\t2- Realizar transferencia");
            System.out.println("\t3- Ler registro (ID)");
            System.out.println("\t4- Atualizar registro");
            System.out.println("\t5- Deletar registro");
            System.out.println("\t6- Intercalar arquivo");
            System.out.println("\t7- Lista Invertida");
            System.out.println("\t8- Compressao do arquivo");
            System.out.println("\t9- Casamento de padroes por KMP");
            System.out.println("------------------------------------\n");

            do {
                // -------- pegar a escolha do usuario --------
                System.out.print("Digite a opcao desejada: ");
                resp = sc.nextInt();

                // -------- mensagem de erro --------
                if (resp < 1 || resp > 9) {
                    System.out.print("Opcao invalida!");
                }
            } while (resp < 1 || resp > 9); // repetir enquanto a opcao for invalida

            // --------------- realizar a opcao desejada ---------------

            switch (resp) {
                case 1:
                    cont = new Conta();
                    int answer = 0;
                    int posicao = 0;
                    boolean stop = false;
                    System.out.println("\n\nOpcao escolhida: \n\t1- Criar conta");

                    cont.setIdConta(id++);
                    System.out.println("Seu ID eh: " + cont.getIdconta() + "\n\n"); // informa o id da conta a medida
                                                                                    // que for criando

                    System.out.print("Digite o Nome da Pessoa: ");
                    cont.setNomePessoa(sc.next()); // ler o nome da pessoa

                    // -------- loop para adicionar varios emails --------
                    int quant = 0;

                    do {
                        int arroba = 0, ponto = 0; // variavel para verificar se o email tem @
                        cont.setQtd(quant + 1);
                        String[] temp = new String[quant + 1];

                        // -------- loop para verificar se o email tem @ --------
                        do {
                            System.out.print("Digite o Email: ");
                            temp[quant] = sc.next(); // coloca o email em uma variavel temporaria

                            if (temp[quant].contains("@")) {
                                arroba++;
                            } // verifica se o email tem @
                            if (temp[quant].contains(".") && (temp[quant].indexOf(".") > temp[quant].indexOf("@"))) {
                                ponto++;
                            } // verifica se o email tem . e se o . esta depois do @

                            if (arroba == 0 || ponto == 0) {
                                System.out.println("Email invalido!");
                            } else {

                                for (int i = 0; i < quant; i++) {
                                    if (temp[quant].equals(cont.getEmail()[i])) { // alerta quando o email digitado na
                                                                                  // conta já existe
                                        System.out.println("Email ja cadastrado!");
                                        arroba = 0;
                                        ponto = 0;
                                    }
                                }

                                for (int i = 0; i < cont.getQtd(); i++) {
                                    cont.setEmail(temp); // add email ao vetor de emails
                                }

                                break;
                            }

                        } while (arroba == 0 || ponto == 0);

                        System.out.println("Deseja adicionar um novo email? (1 - sim / 2 - nao)");
                        answer = sc.nextInt();

                        if (answer == 1)
                            quant++;

                    } while (answer == 1);

                    // -------- loop para verificar se o username é repetido --------
                    int repeat = 0; // variavel para verificar se é repetido
                    do {
                        String tmp = "";
                        System.out.print("Digite o username: ");
                        tmp = sc.next(); // ler usuario da conta

                        cont.setNomeUsuario(tmp);

                    } while (repeat != 0);

                    System.out.print("Digite a Senha: ");
                    cont.setSenha(sc.next());

                    // ------ loop para verificar se o CPF é valido ------
                    do {
                        System.out.print("Digite o CPF: ");
                        cont.setCpf(sc.next());
                        if (cont.getCpf().length() != 11) { // se o cpf nn possuir 11 numeros ele é invalido
                            System.out.println("CPF inválido!");
                        }

                    } while (cont.getCpf().length() != 11); // repetir até que o cpf seja valido

                    System.out.print("Digite a cidade: ");
                    cont.setCidade(sc.next()); // ler cidade

                    System.out.print("Digite o saldo da conta: R$ ");
                    cont.setSaldoConta(sc.nextFloat()); // saldo da conta

                    cont.setTransferenciasRealizadas(0); // Por uma nova conta não existe transferências

                    arq.writeChars("(" + cont.getIdconta() + ") " + cont.getNomePessoa() + " " + cont.getEmail() + " "
                            + cont.getNomeUsuario() + " " + cont.getSenha() + " " + cont.getCpf() + " "
                            + cont.getCidade()
                            + " " + cont.getTransferenciasRealizadas() + " " + cont.getSaldoConta() + "\n");

                    if (create(arq, cont)) {
                        System.out.println("Conta criada com sucesso!");
                    } else {
                        System.out.println("Erro ao criar conta!");
                    }

                    break;

                case 2:
                    System.out.println("\n\nOpcao escolhida: \n\t 2- Realizar transferencia");
                    System.out.println("\nDigite o ID da primeira conta: ");
                    int id1 = sc.nextInt(); // le o id da conta que vai transferir

                    System.out.println("\nDigite o ID da conta a qual deseja transferir: ");
                    int id2 = sc.nextInt();// le o id da conta que vai receber a transferencia

                    System.out.println("\nDigite o valor a ser transferido: ");
                    float value = sc.nextFloat(); // le o valo de transferencia

                    Conta account1 = readId(arq, id1);
                    Conta account2 = readId(arq, id2);

                    if (account1 == null) { // verifica se conta 1 nn é nula/ nn existe
                        System.out.println("Essa conta nao existe!");
                    } else if (account2 == null) { // verfica se a conta 2 nn é nula/ nn exite
                        System.out.println("A conta de destino nao existe!");
                    } else if (account1.getSaldoConta() < value) { // verifica se o valor a ser transferido nao é maior
                                                                   // que o valor da conta
                        System.out.println("Saldo insuficiente!");
                    } else {
                        account1.setSaldoConta(account1.getSaldoConta() - value);// tira da conta 1 o valor a ser
                                                                                 // transferido
                        account2.setSaldoConta(account2.getSaldoConta() + value);// adiciona a conta 2 o valor
                                                                                 // transferido
                        account1.setTransferenciasRealizadas(account1.getTransferenciasRealizadas() + 1); // aumenta o
                                                                                                          // numero de
                                                                                                          // transferencias
                        account2.setTransferenciasRealizadas(account2.getTransferenciasRealizadas() + 1);// aumenta o
                                                                                                         // numero de
                                                                                                         // transferencias
                        update(arq, account1); // atualiza conta 1
                        update(arq, account2);// atualiza conta 2
                    }

                    if (update(arq, account1) && update(arq, account2)) {
                        System.out.println("Transferencia realizada com suceesso!");
                    } else {
                        System.out.println("Nao foi possivel realizar a transferencia!");
                    }

                    break;

                case 3:
                    int numID = 0, count = 0;
                    System.out.println("\n\nOpcao escolhida: \n\t3- Ler registro (ID)\n");

                    // -------- pegar a escolha do usuario --------
                    System.out.print("Digite o ID da conta que deseja ler: ");
                    numID = sc.nextInt();

                    if (cont.getIdconta() == numID) { // se o id da conta for igual ao id digitado, imprimir na tela os
                                                      // dados da conta
                        System.out.println("\n\nID: " + cont.getIdconta());
                        System.out.println("Nome da pessoa: " + cont.getNomePessoa());
                        System.out.println("Email: " + cont.getEmail());
                        System.out.println("Username: " + cont.getNomeUsuario());
                        System.out.println("Senha: " + cont.getSenha());
                        System.out.println("CPF: " + cont.getCpf());
                        System.out.println("Cidade: " + cont.getCidade());
                        System.out.println("Saldo da conta: " + cont.getSaldoConta());
                        System.out.println(
                                "Transferências já realizadas: " +
                                        cont.getTransferenciasRealizadas());
                    } else {
                        count++;
                    }

                    if (count == id) { // verifica se o id existe
                        System.out.println("\nNumero ID nao encontrado!");
                    } else {
                        System.out.println("\nArquivo de registros lido com sucesso!");
                    }

                    break;

                case 4:
                    System.out.println("\n\nOpcao escolhida: \n\t4- Atualizar registro");

                    int idUpdate;
                    Conta newAccount = new Conta(); // cria nova conta

                    System.out.println("\nDigite o ID da conta que deseja atualizar: ");
                    idUpdate = sc.nextInt(); // pega o id da conta que se deseja atualizar

                    newAccount = readId(arq, idUpdate); // le o id da conta
                    if (newAccount == null)
                        System.out.println("A conta nao foi encontrada"); // verifica se a conta existe
                    else {
                        // Imprimir dados da conta atual
                        System.out.println("ID: " + newAccount.getIdconta());
                        System.out.println("Nome: " + newAccount.getNomePessoa());
                        System.out.println("Emails " + newAccount.getEmail());
                        System.out.println("CPF: " + newAccount.getCpf());
                        System.out.println("Cidade: " + newAccount.getCidade());
                        System.out.println("User: " + newAccount.getNomeUsuario());
                        System.out.println("Senha: " + newAccount.getSenha());
                        System.out.println("Saldo: " + newAccount.getSaldoConta());
                        System.out.println("Transferencias Realizadas: " + newAccount.getTransferenciasRealizadas());
                    }

                    // Opcoes para atualizar
                    System.out.println("----------- Opcoes ---------");
                    System.out.println("\t1- Nome");
                    System.out.println("\t2- CPF");
                    System.out.println("\t3- Cidade");
                    System.out.println("\t4- Usuario");
                    System.out.println("\t5- Senha");
                    System.out.println("\t6- Email");
                    System.out.println("\t7- Saldo");
                    System.out.println("\t9- Cancelar");

                    do { // verifica se aopcao escolhida existe
                        try {
                            aux = sc.nextInt();
                            if (aux < 1 || aux > 8)
                                System.out.println("Opcao invalida!");
                        } catch (Exception e) {
                            System.out.println("Digite um numero!");
                            sc.next();
                            break;
                        }
                    } while (aux < 1 || aux > 8); // Enquanto a opção for inválida continua no loop

                    switch (aux) { // atualiza os dados de acordo com a opcao escolhida
                        case 1: // nome
                            System.out.println("Digite um novo nome: ");
                            newAccount.setNomePessoa(sc.next());
                            break;
                        case 2: // CPF
                            System.out.println("Digite o novo CPF: ");
                            newAccount.setCpf(sc.next());
                            break;
                        case 3:// Cidade
                            System.out.println("Digite a Cidade: ");
                            newAccount.setCidade(sc.next());
                            break;
                        case 4:// Usuario
                            System.out.println("Digite o usuario: ");
                            newAccount.setNomeUsuario(sc.next());
                            break;
                        case 5:// Senha
                            System.out.println("Digite uma nova Senha: ");
                            newAccount.setSenha(sc.next());
                            break;
                        case 6:// Email
                            int qt = newAccount.getQtd();
                            String[] arrayAtualizacao = new String[qt];
                            int input = 0;
                            do {
                                System.out.println("Digite um novo email: ");
                                newAccount.setQtd(qt++);
                                arrayAtualizacao[qt] = sc.next();
                                System.out.println("Deseja adicionar mais emails? 1 - Sim / 2 - Nao");
                                input = sc.nextInt();
                            } while (input == 1);

                            newAccount.setEmail(arrayAtualizacao);
                            break;
                        case 7:// Saldo
                            System.out.println("Digite o novo saldo: ");
                            newAccount.setSaldoConta(sc.nextFloat());
                            break;
                        case 8: // Cancela a operacao
                            System.out.println("Operacao cancelada!");
                            break;
                    }

                    if (aux != 8) { // se diferente de 8 atualiza registro
                        if (update(arq, newAccount)) {
                            System.out.println("Conta atualizada com sucesso!");
                        } else {
                            System.out.println("Erro ao atualizar!");
                        }
                    }

                    break;

                case 5:
                    int auxId = sc.nextInt(); // le o ID do usuario que se quer deletar
                    int input1 = 0; // armazena a resposta do usuario

                    do {
                        System.out.println("\n\nOpcao escolhida: \n\t5- Deletar registro");
                        System.out.println("\nDigite o ID da conta que deseja excluir: ");

                        System.out.println(
                                "O arquivo de ID " + auxId + "sera deletado, deseja continuar? 1 -sim / 2 - nao");
                        input1 = sc.nextInt();

                        if (input1 == 1) {
                            delete(arq, auxId);
                            System.out.println("Conta deletada com sucesso!");
                        } else {
                            System.out.println("Nao foi possivel deletar a conta!");
                        }
                    } while (input1 != 1);

                    break;

                case 6:
                    System.out.println("\n\nOpcao escolhida: \n\t6- Intercalacao do arquivo");
                    System.out.println("Deseja Intercarlar os arquivos? ");
                    System.out.println("\t1- sim");
                    System.out.println("\t2- nao");
                    int limite = 0;

                    do {
                        aux = sc.nextInt();
                        System.out.println("\nDigite o limite de registros que devem ter na memoria principal: ");
                        limite = sc.nextInt();
                        if (aux < 1 || aux > 2) {
                            System.out.println("Essa opcao nao existe!");
                        } else {
                            System.out.println("Digite um numero: ");
                            sc.next();
                            System.out.println("\nDigite o limite de registros que devem ter na memoria principal: ");
                            limite = sc.nextInt();
                            break;
                        }
                    } while (aux < 1 || aux > 2);

                    if (aux == 1) {
                        if (Intercalacao.intercalar(arq, limite)) {
                            System.out.println("Arqvivos intercalados com sucesso!");
                        } else {
                            System.out.println("Nao foi possivel intercalar os arquivos!");
                        }
                    } else {
                        System.out.println("Intercalacao cancelada!");
                    }

                    break;

                case 7:
                    System.out.println("\n\nOpcao escolhida: \n\t7- Lista Invertida");
                    System.out.println("\nDeseja fazer a lista Invertida?");
                    System.out.println("\t1- Listar pelo Nome");
                    System.out.println("\t2- Cancelar Lista");

                    do {
                        try {
                            aux = sc.nextInt();
                            if (aux < 1 || aux > 2) {
                                System.out.println("Essa opcao nao existe!");
                            }
                        } catch (Exception e) {
                            System.out.println("Digite um numero valido: ");
                            sc.nextInt();
                            break;
                        }

                    } while (aux < 1 || aux > 2);

                    if (aux == 1) { // listar de acordo com o nome
                        System.out.println("Digite o nome: ");
                        String name = sc.next();

                        if (listaInvertida(arq, name) == true) {
                            System.out.println("Lista Concluida!");
                        } else {
                            System.out.println("Nao foi possivel fazer a listagem!");
                        }
                    } else {
                        System.out.println("Lista Invertida cancelada!");
                    }

                    break;

                case 8:
                    int num = 0, // ler o numero do metodo selecionado
                            IDnum = 0, // ler o id da conta
                            aux1 = 0; // contador
                    List<Integer> comprimir;

                    System.out.println("\n\nOpcao escolhida: \n\t8- Compressao do arquivo");
                    System.out.println("\nEscolha por qual metodo a compressao deve ser feita: ");
                    System.out.println("\t1- Huffman");
                    System.out.println("\t2- LZW");

                    do { // verifica se a opcao escolhida existe
                        try {
                            System.out.print("Digite a opcao escolhida: ");
                            num = sc.nextInt();
                            if (num < 1 || num > 2)
                                System.out.println("Opcao invalida!");
                        } catch (Exception e) {
                            System.out.println("Digite um numero!");
                            sc.next();
                            break;
                        }
                    } while (num < 1 || num > 2); // Enquanto a opção for inválida continua no loop

                    System.out.print("Digite o ID da conta que deseja ler: ");
                    IDnum = sc.nextInt();

                    switch (num) {
                        case 1: // compressão por huffman

                            String compressed = "";
                            long tempo = System.currentTimeMillis();

                            if (cont.getIdconta() == IDnum) {

                                // System.out.println("----- ID -----");
                                // Huffman.start("" + cont.getIdconta());
                                // compressed += Huffman.compress("" + cont.getIdconta());

                                System.out.println("----- Nome -----");
                                Huffman.start(cont.getNomePessoa());
                                compressed = Huffman.compress(cont.getNomePessoa());

                                /*
                                 * for (int i = 0; i < cont.getQtd(); i++) {
                                 * System.out.println("----- Email " + i + " -----");
                                 * Huffman.start(cont.getEmail()[i]);
                                 * compressed += Huffman.compress(cont.getEmail());
                                 * }
                                 */

                                System.out.println("----- Username -----");
                                Huffman.start(cont.getNomeUsuario());
                                compressed += Huffman.compress(cont.getNomeUsuario());

                                System.out.println("----- Senha -----");
                                Huffman.start(cont.getSenha());
                                compressed += Huffman.compress(cont.getSenha());

                                System.out.println("----- CPF -----");
                                Huffman.start(cont.getCpf());
                                compressed += Huffman.compress(cont.getCpf());

                                System.out.println("----- Cidade -----");
                                Huffman.start(cont.getCidade());
                                compressed += Huffman.compress(cont.getCidade());

                                System.out.println("----- Saldo -----");
                                Huffman.start("" + cont.getSaldoConta());
                                compressed += Huffman.compress("" + cont.getSaldoConta());

                                // System.out.println("----- Transferencias -----");
                                // Huffman.start("" + cont.getTransferenciasRealizadas());
                                // compressed += Huffman.compress("" + cont.getTransferenciasRealizadas());

                                compFile.writeUTF(compressed);

                                System.out.println("\n\nArquivo nao compactado (em bytes): " + arq.length());
                                System.out.println("Arquivo compactado (em bytes): " + compFile.length());
                                System.out
                                        .println("Tempo de compressao: " + (System.currentTimeMillis() - tempo) + "ms");

                            } else {
                                aux1++;
                            }

                            if (aux1 == id) {
                                System.out.println("\nNumero ID nao encontrado!");
                            } else {
                                System.out.println("\nArquivo de registros lido com sucesso!");
                            }

                            break;

                        case 2: // compressão por lzw

                            if (cont.getIdconta() == IDnum) {
                                // --------------------- ID ---------------------
                                /*
                                 * comprimir = Lzw.compressao(Integer.toString(cont.getIdconta()));
                                 * System.out.println("----- ID -----");
                                 * System.out.println("Compressao: " + comprimir);
                                 * System.out.println("Descompressao: " + Lzw.descompressao(comprimir));
                                 */

                                // --------------------- Nome --------------------
                                comprimir = Lzw.compressao(cont.getNomePessoa());
                                System.out.println("----- NOME -----");
                                System.out.println("Compressao: " + comprimir);
                                System.out.println("Descompressao: " + Lzw.descompressao(comprimir));

                                // ------------------ Email -----------------------
                                // COLOCAR DENTRO DE UM FOR
                                /*
                                 * int qntdd = newAccount.getQtd();
                                 * for(int i = 0; i < qntdd; i++){
                                 * comprimir = Lzw.compressao(cont.getEmail());
                                 * System.out.println("----- EMAIL -----");
                                 * System.out.println("Compressao: " + comprimir);
                                 * System.out.println("Descompressao: " + Lzw.descompressao(comprimir));
                                 * }
                                 * comprimir = Lzw.compressao(cont.getEmail());
                                 * System.out.println("----- EMAIL -----");
                                 * System.out.println("Compressao: " + comprimir);
                                 * System.out.println("Descompressao: " + Lzw.descompressao(comprimir));
                                 */

                                // ------------------- Username -------------------
                                comprimir = Lzw.compressao(cont.getNomeUsuario());
                                System.out.println();
                                System.out.println("----- USERNAME -----");
                                System.out.println("Compressao: " + comprimir);
                                System.out.println("Descompressao: " + Lzw.descompressao(comprimir));

                                // -------------------- Senha ---------------------
                                comprimir = Lzw.compressao(cont.getSenha());
                                System.out.println();
                                System.out.println("----- SENHA -----");
                                System.out.println("Compressao: " + comprimir);
                                System.out.println("Descompressao: " + Lzw.descompressao(comprimir));

                                // --------------------- Cpf ----------------------
                                comprimir = Lzw.compressao(cont.getCpf());
                                System.out.println();
                                System.out.println("----- CPF -----");
                                System.out.println("Compressao: " + comprimir);
                                System.out.println("Descompressao: " + Lzw.descompressao(comprimir));

                                // --------------------- Cidade -------------------
                                comprimir = Lzw.compressao(cont.getCidade());
                                System.out.println();
                                System.out.println("----- CIDADE -----");
                                System.out.println("Compressao: " + comprimir);
                                System.out.println("Descompressao: " + Lzw.descompressao(comprimir));

                                // ---------------------- Saldo -------------------
                                comprimir = Lzw.compressao(Double.toString(cont.getSaldoConta()));
                                System.out.println();
                                System.out.println("----- SALDO CONTA -----");
                                System.out.println("Compressao: " + comprimir);
                                System.out.println("Descompressao: " + Lzw.descompressao(comprimir));

                                // ---------------- Transferencias ----------------
                                /*
                                 * comprimir =
                                 * Lzw.compressao(Double.toString(cont.getTransferenciasRealizadas()));
                                 * System.out.println();
                                 * System.out.println("----- TRANSFERENCIAS REALIZADAS -----");
                                 * System.out.println("Compressao: " + comprimir);
                                 * System.out.println("Descompressao: " + Lzw.descompressao(comprimir));
                                 */

                            } else {
                                aux1++;
                            }

                            if (aux1 == id) {
                                System.out.println("\nNumero ID nao encontrado!");
                            } else {
                                System.out.println("\nArquivo de registros lido com sucesso!");
                            }

                            break;

                    }

                    break;

                case 9:
                    System.out.println("\n\nOpcao escolhida: \n\t9- Casamento de padroes por KMP");
                    System.out.println("\nDigite o padrao que deseja procurar nos dados de sua conta bacaria: ");
                    
                    String pattern = sc.next();
                    String texto = "";

                    //String que vai receber todos os dados da conta bancaria
                    StringBuffer text = new StringBuffer();
                    text.append(Integer.toString(cont.getIdconta()));
                    text.append(cont.getNomePessoa());
                    text.append(cont.getEmail());
                    text.append(cont.getNomeUsuario());
                    text.append(cont.getSenha());
                    text.append(cont.getCpf());
                    text.append(cont.getCidade());
                    text.append(Double.toString(cont.getSaldoConta()));
                    

                    texto = text.toString();

                    KMP.KMP(texto,pattern); 
                    break;

                default:
                    System.out.print("\n\nOpcao invalida!");
                    break;
            }

            // -------- perguntar se o usuario quer continuar --------
            do {
                System.out.print("\n\nDeseja continuar? (1 - sim / 0 - nao): ");
                r = sc.nextInt();

                // -------- mensagem de erro --------
                if (r != 1 && r != 0) {
                    System.out.print("Opcao invalida!");
                }

                // -------- mensagem de despedida --------
                if (r == 0) {
                    System.out.println("\n\nObrigado por usar o nosso sistema!");
                    break;
                }
            } while (r != 1 && r != 0);

        } while (r == 1);

        // ------------------------------------
        arq.close();
        compFile.close();
    }

}
