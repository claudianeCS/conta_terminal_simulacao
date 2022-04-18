package br.com.project.cm;

import br.com.project.cm.modelo.Tabuleiro;
import br.com.project.cm.visao.TabuleiroConsole;

public class Aplication {
    public static void main(String[] args) {
        Tabuleiro tabuleiro = new Tabuleiro(6, 7, 4);

        new TabuleiroConsole(tabuleiro);
    }
}
