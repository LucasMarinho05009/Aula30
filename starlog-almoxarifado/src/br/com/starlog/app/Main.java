package br.com.starlog.app;

import br.com.starlog.dao.ProdutoDAO;
import br.com.starlog.dao.ProdutoDAOSqlite;
import br.com.starlog.model.Produto;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ProdutoDAO dao = new ProdutoDAOSqlite();

        System.out.println("=== 1. CADASTRO DE PRODUTOS (CREATE) ===");
        Produto p1 = new Produto("Teclado Mecânico RGB", 320.00, 10);
        Produto p2 = new Produto("Mousepad Speed XL", 65.50, 25);

        dao.salvar(p1);
        dao.salvar(p2);

        System.out.println("Cadastrado -> " + p1);
        System.out.println("Cadastrado -> " + p2);

        System.out.println("\n=== 2. CATÁLOGO ATUAL (READ) ===");
        imprimirCatalogo(dao.listarTodos());

        System.out.println("\n=== 3. ATUALIZAÇÃO DE ESTOQUE E PREÇO (UPDATE) ===");
        p1.setEstoque(15);
        p1.setPreco(289.90);

        boolean atualizou = dao.atualizar(p1);
        if (atualizou) {
            System.out.println("Produto ID " + p1.getId() + " atualizado com sucesso.");
            dao.buscarPorId(p1.getId()).ifPresent(System.out::println);
        } else {
            System.out.println("Não foi possível atualizar o produto.");
        }

        System.out.println("\n=== 4. REMOÇÃO DE ITENS (DELETE) ===");
        boolean removeuP2 = dao.excluir(p2.getId());
        System.out.println("Remoção do ID " + p2.getId() + ": " + (removeuP2 ? "Sucesso" : "Falha"));

        Long idInexistente = 888L;
        boolean removeuInexistente = dao.excluir(idInexistente);
        System.out.println("Tentativa de remover ID " + idInexistente + ": "
                + (removeuInexistente ? "Sucesso" : "Registro não encontrado (operação ignorada)"));

        System.out.println("\n=== 5. ESTADO FINAL DO BANCO ===");
        imprimirCatalogo(dao.listarTodos());
    }

    private static void imprimirCatalogo(List<Produto> produtos) {
        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto encontrado no almoxarifado.");
            return;
        }
        for (Produto p : produtos) {
            System.out.println(p);
        }
    }
}
