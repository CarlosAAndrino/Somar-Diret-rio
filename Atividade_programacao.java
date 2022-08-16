package com.mycompany.atividade_programacao;

import java.io.File;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

/**
 *
 * @author CC48052317851
 */
public class Atividade_programacao {
            
    // VERSÃO CONVENCIONAL
    public static long getSizeDirectory(File directory) {
        long length = 0;
        // Faz um laço de repeticao percorrendo todos os diretorios '.listFile()retorna uma arrayFile'
        for (File file : directory.listFiles()) {
            // Verifica se o 'file' é um diretorio se nao ele passa a funcao partindo desse diretorio (recursividade)
            if (file.isFile())
                length += file.length();
            else
                // Recursividade
                length += getSizeDirectory(file);
        }
        return length;
    }
    
    public static void main(String[] args) {       
        // Diretorio que irá efetuar as operações
        File folder = new File("C:\\Users\\carlo\\Desktop\\Programação");

        // VERSÃO CONVENCIONAL
        // Inicializa o temporizador
        long inicio = System.currentTimeMillis();
        // Chama a funcao recursiva para efetuar o calculo de forma convencional
        long soma = getSizeDirectory(folder);
        // Finaliza o temporizador
        long fim = System.currentTimeMillis();
        System.out.println("Soma: " + soma +" bytes");
        System.out.println("Tempo soma convencional: " + (fim - inicio));
        
        // VERSÃO PARALELA
        // Cria um objeto e passar como parametro uma lista com todos os diretorios do diretorio '.listFile()'
        getSizeDirectoryParallelism sv = new getSizeDirectoryParallelism(folder.listFiles());
        // Inicializa o temporizador
        inicio = System.currentTimeMillis();   
        // Invoca o paralelismo partindo de nosso objeto
        int paralelismo = ForkJoinPool.getCommonPoolParallelism();          
        ForkJoinPool pool = (ForkJoinPool) Executors.newWorkStealingPool(paralelismo);
        soma = pool.invoke(sv);
        // Finaliza o temporizador
        fim = System.currentTimeMillis();
        pool.shutdown();
        System.out.println("Soma: " + soma + " bytes");
        System.out.println("Tempo soma paralela: " + (fim - inicio));
    }
}
