package br.com.project.cm.modelo;

import br.com.project.cm.exececao.ExplosaoException;

import java.util.ArrayList;
import java.util.List;

public class Campo {
    // linha e coluna
    private final int linha;
    private final int coluna;

    private boolean aberto = false ; // o false e o padrao do tipo boolean, mais pode ser declarado.
    private boolean minado = false;
    private boolean marcado = false;

    private List<Campo> vizinhos = new ArrayList<>();

    Campo(int linha, int coluna){
        this.coluna = coluna;
        this.linha = linha;
    }

    boolean adicionarVizinho(Campo vizinho){
        //vai ditar quem pode ou nao ser vizinho atraves da Classe Campo, quando
        // escolher o campo essa classe ira ditar seus vizinhos.

        //SE A LINHA E A COLUNA FOREM DIFERENTES EU TENHO UMA DIAGONAL
        boolean linhaDiferente = linha != vizinho.linha;
        boolean colunaDiferente = coluna != vizinho.coluna;

        boolean diagonal =  linhaDiferente && colunaDiferente;

        //CALCULO DA DIFERENÇA ENTRE LINHA E COLUNA.
        int deltaLinha = Math.abs(linha - vizinho.linha);
        int deltaColuna = Math.abs(coluna- vizinho.coluna);
        int deltaGeral = deltaColuna + deltaLinha;

        //CENARIO 1 - E IGUAL A 1 E NAO ESTA NA DIAGONAL.
        if (deltaGeral == 1 && !diagonal){
            //ADICIONA UM VIZINHO DO TIPO CAMPO A LISTA VIZINHOS.
            vizinhos.add(vizinho);
            return true;
        } else if (deltaGeral == 2 && diagonal) { //RETORNO O CENARIO 2.
            vizinhos.add(vizinho);
            return true;
        }else {
            return  false;
        }

    }

    //MARCAÇÃO DOS CAMPOS E QUE A POSSIBILIDADE DE CONTER BOMBA.
    void alternarMarcacao(){
        if(!aberto){ // NAO ABERTO = FECHADO.
            marcado = !marcado;
        }
    }

    //UMA VEZ QUE O CAMPO FOR MARCADO ELE NAO PODERA ABRIR.
    boolean abrir(){
        // SE O CAMPO NAO TIVER ABERTO E NEM MARCADO ELE PODERA ABRIR.
        if(!aberto && !marcado){
            aberto = true;

            //SE ELE ESTIVER COM BOMBA.
            if(minado){
                throw new ExplosaoException();
            }
            if(vizinhançaSegura()){
                //VAI ABRIR OS VIZINHOS ATE SER SEGURO.
                vizinhos.forEach(v -> v.abrir());
            }
            return  true;
        }else {
            return  false;
        }
    }

    boolean vizinhançaSegura(){
        //SE NENHUM VIZINHO DER METCH DESSE PREDICADO , A VIZINHANÇA E SEGURA.
        //CASO DER TRUE.
        return  vizinhos.stream().noneMatch(v -> v.minado);
    }

    void minar(){
        minado = true;
    }

    public boolean isMarcado() {
        return marcado;
    }

    public boolean isMinado() {
        return minado;
    }

    public boolean isAberto() {
        return aberto;
    }

    public boolean isFechado() {
        return !isAberto();
    }

    public int getLinha() {
        return linha;
    }

    public int getColuna() {
        return coluna;
    }

    boolean objetivoAlcancado(){
        //O CAMPO FOI ABERTO E NAO ESTA MINADO.
        boolean desvendado = !minado && aberto;
        //O CAMPO ESTA MINADO E FOI MARCADO.
        boolean protegido = minado && marcado;
        //RETORNANDO UM OU OUTRO.
        return  desvendado || protegido;
    }

    long minasNasVizinhancas(){
        //stream() - Uma sequência de elementos que suportam operações de agregação sequenciais e paralelas.

        return vizinhos.stream().filter(v -> v.minado).count(); // filtrando todos os vizinhos minados.
    }

    void reiniciarJogo(){
        aberto = false;
        minado = false;
        marcado = false;
    }
    //RETORNANDO UM TEXTO NO TERMINAL.
    public String toString(){
      if(marcado){
          return "X";
      } else if (aberto && minado) {
          return "*";
      } else if (aberto && minasNasVizinhancas() > 0) {
          return Long.toString(minasNasVizinhancas());
      } else if (aberto) {
          return " ";
      } else {
          return "?";
      }
    }


}
