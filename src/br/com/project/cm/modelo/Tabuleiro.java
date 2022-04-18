package br.com.project.cm.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Tabuleiro {

    private int linhas;
    private int colunas;
    private int minas;

    private final List<Campo> campos = new ArrayList<>();

    public Tabuleiro(int linhas, int colunas, int minas) {
        this.linhas = linhas;
        this.colunas = colunas;
        this.minas = minas;
        
        gerarCampos(); //baseando-se nas linhas e colunas.
        associarVizinhos(); //so sera executado 1 vez
        sortearMinas();
        
    }

    public void abrir(int linha, int colunas){
        campos.parallelStream()
                .filter(campo -> campo.getLinha() == linha && campo.getColuna() == colunas )
                .findFirst().ifPresent(campo -> campo.abrir()); //pegando o primeiro elemento e se estiver presente
        // ele vai retornar o metodo abrir().


    }


    public void alternarMarcacao(int linha, int colunas){
        campos.parallelStream()
                .filter(campo -> campo.getLinha() == linha && campo.getColuna() == colunas )
                .findFirst().ifPresent(campo -> campo.alternarMarcacao()); //pegando o primeiro elemento e se estiver presente
        // ele vai retornar o metodo abrir().


    }

    private void sortearMinas() {
        long minasArmadas = 0;
        Predicate<Campo> minado = campo -> campo.isMinado();

        do{ //quantidade de minas armadas igual a quantidade de campos minados igual a TRUE.
            minasArmadas = campos.stream().filter(minado).count(); //o count retorna um tipo long.

            //TYPE CASTING JAVA - A conversão de tipo é quando você atribui um valor de um tipo de dados primitivo a outro tipo .

            int aleatorio = (int) (Math.random() * campos.size()); // e preciso gerar um valor aleatorio a partir do array dos Campos.
            campos.get(aleatorio).minar();

        }while (minasArmadas < minas);

        // A QUANTIDADE DE MINAS ARMADAS TEM QUE SER IGUAL A QUANTIDADE DE MINAS.

        //CASO ISSO NAO SEJA VERDADEIRO IRA FICAR FAZENDO O LOOP.

    }

    public boolean objetivoAlcancado(){ // se todos os campos derem metch com objetivo alcançado o jogo foi ganhado.
        return campos.stream().allMatch(campo -> campo.objetivoAlcancado());
    }

    //REINICIALIZAR TODOS OS CAMPOS DO JOGO
    public void reiniciar(){
        campos.stream().forEach(campo -> campo.reiniciarJogo());
        sortearMinas();
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();

        //SEQUENCIA DE CAMPOS LINEAR, UMA UNICA SEQUENCIA DENTRO DE UMA LISTA.
        int v = 0;
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) { // valor das colunas , primeria linhas passa-se todas as colunas.
                sb.append(" ");
                sb.append(campos.get(v)); //valor do campo
                sb.append(" ");
                v++;
            }// quebrar em linhas separadas.
            sb.append("\n");
        }

        return sb.toString();
    }

    private void associarVizinhos() {

        //VOU PERCORRER A LISTA  DUAS VEZES E FAZER TODOS SE ASSOCIAREM UM VIZINHO COM O OUTRO. E DEFINIR QUEM PODE OU NAO SER VIZINHO.

        for(Campo c1 : campos){
            for (Campo c2 : campos){
                c1.adicionarVizinho(c2);
            }
        }
    }

    private void gerarCampos() {
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                campos.add(new Campo(i,j));
            }
        }
    }

}
