package com.mycompany.atividade_programacao;

import static com.mycompany.atividade_programacao.Atividade_programacao.getSizeDirectory;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class getSizeDirectoryParallelism extends RecursiveTask<Long> {
    private File[] arr;
 
    // Define um valor limite para o tamanho da lista
    private static final int LIMITE = 5;
 
    // Funcao para puxar o File[]
    public getSizeDirectoryParallelism(File[] arr) {
        this.arr = arr;
    }
 
    @Override
    protected Long compute() {
        // Verifica se o tamanho da lista não é maior q o limite
        if (arr.length > LIMITE) {
            // Se for maior q o limite ele corta a lista pela metade
            return getSizeDirectoryParallelism.invokeAll(criarSubtarefas())
              .stream()
              .mapToLong(getSizeDirectoryParallelism::join)
              .sum();
        } else {
            // Chama a funcao de soma
            return somar(arr);
        }
    }
    
    // Separa o array  pela metade
    private Collection<getSizeDirectoryParallelism> criarSubtarefas() {
        List<getSizeDirectoryParallelism> subtarefas = new ArrayList<>();
        subtarefas.add(new getSizeDirectoryParallelism(
          Arrays.copyOfRange(arr, 0, arr.length / 2)));
        subtarefas.add(new getSizeDirectoryParallelism(
          Arrays.copyOfRange(arr, arr.length / 2, arr.length)));
        return subtarefas;
    }
 
    // Funcao recursiva para soma
    private Long somar(File[] arr) {
        long length = 0;
        int count = 0;      
        
        for (File file : arr) {
            if (file.isFile())
                length += file.length();
            else
                length += somar(file.listFiles());
            count++;
        }
        return length;
    }
}
