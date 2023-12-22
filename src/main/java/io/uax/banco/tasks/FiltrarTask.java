package io.uax.banco.tasks;

import java.util.concurrent.RecursiveTask;
import java.util.Collections;
import java.util.List;
import io.uax.banco.domain.Usuario;

public class FiltrarTask extends RecursiveTask<List<Usuario>> {
    private final List<Usuario> usuarios;

    public FiltrarTask(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    @Override
    protected List<Usuario> compute() {
        if (usuarios.size() <= 1) {
            return usuarios;
        } else {
            FiltrarTask leftTask = new FiltrarTask(usuarios.subList(0, usuarios.size() / 2));
            FiltrarTask rightTask = new FiltrarTask(usuarios.subList(usuarios.size() / 2, usuarios.size()));

            rightTask.fork(); // realiza la tarea en un hilo separado
            List<Usuario> leftResult = leftTask.compute();
            List<Usuario> rightResult = rightTask.join();

            leftResult.addAll(rightResult);
            Collections.sort(leftResult, (u1, u2) -> u1.getAmount().compareTo(u2.getAmount()));
            return leftResult;
        }
    }
}