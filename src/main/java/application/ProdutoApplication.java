package application;

import dao.ProdutoDAO;
import model.Categoria;
import model.Produto;
import utils.JPAUtil;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

public class ProdutoApplication {

    private static EntityManager em = JPAUtil.getEntityManager();
    private static ProdutoDAO dao = new ProdutoDAO(em);

    public static void main(String[] args) {

        try {
            Produto celular = new Produto();
            celular.setNome("Samsung S10");
            celular.setPreco(new BigDecimal(2500.89));
            celular.setCategoria(Categoria.CELULARES);
            celular.setDescricao("Samsung Galaxy S10 128 GB preto-prisma 8 GB RAM");

            Produto livro = new Produto();
            livro.setNome("O poder do hábito");
            livro.setPreco(new BigDecimal(36.99));
            livro.setCategoria(Categoria.LIVROS);
            livro.setDescricao("O poder do hábito Capa comum ");

            //insert
            System.out.println("INSERT: ");
            ProdutoApplication.em.getTransaction().begin();
            List<Produto> listProduto = List.of(celular, livro);
            listProduto.forEach(p -> ProdutoApplication.dao.cadastrar(p));
            ProdutoApplication.em.clear();
            System.out.println("...");

            //update
            System.out.println("UPDATE: ");
            celular.setPreco(new BigDecimal(2800.99));
            System.out.println(celular);
            ProdutoApplication.dao.atualizar(celular);
            ProdutoApplication.em.flush();
            ProdutoApplication.em.clear();
            System.out.println("...");

            //select  buscar todos
            System.out.println("SELECT: Buscar Todos");
            ProdutoApplication.dao.buscarTodos().forEach(System.out::println);
            System.out.println("...");

            //select  buscar por ID
            System.out.println("SELECT: Buscar por ID");
            System.out.println(ProdutoApplication.dao.buscarPorId(2L));
            System.out.println("...");

            //delete
            System.out.println("DELETE: ");
            System.out.println(ProdutoApplication.dao.deletar(celular));
            System.out.println("...");

            //select  buscar todos
            System.out.println("SELECT: Buscar Todos");
            ProdutoApplication.dao.buscarTodos().forEach(System.out::println);
            System.out.println("...");

            ProdutoApplication.em.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
            if (ProdutoApplication.em.getTransaction().isActive()) {
                ProdutoApplication.em.getTransaction().rollback();
            }
        } finally {
            ProdutoApplication.em.close();
        }
    }
}
