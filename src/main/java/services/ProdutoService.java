package services;

import dao.ProdutoDAO;
import model.Produto;
import utils.JPAUtil;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class ProdutoService {
    private EntityManager em = JPAUtil.getEntityManager();
    private ProdutoDAO dao = new ProdutoDAO(this.em);

    public void cadastrar(Produto produto) {
        try {
            this.em.getTransaction().begin();
            this.dao.cadastrar(produto);
            this.em.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
            this.em.getTransaction().rollback();
        } finally {
            this.em.close();
        }
    }

    public void atualizar(Produto produto) {
        try {
            Optional.of(this.buscarPorId(produto.getId()));

            this.em.getTransaction().begin();
            this.dao.atualizar(produto);
            this.em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            if(this.em.getTransaction().isActive()) {
                this.em.getTransaction().rollback();
            }
        } finally {
            this.em.close();
        }
    }

    public void deletar(Produto produto) {
        try {
            Optional.of(this.buscarPorId(produto.getId()));

            this.em.getTransaction().begin();
            this.dao.deletar(produto);
            this.em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            if(this.em.getTransaction().isActive()) {
                this.em.getTransaction().rollback();
            }
        } finally {
            this.em.close();
        }
    }

    public Produto buscarPorId(Long id) {
        Produto produto = null;
        try {
            produto = this.dao.buscarPorId(id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.em.close();
        }
        return produto;
    }

    public List<Produto> buscarTodos() {
        List<Produto> listProduto = null;
        try {
            listProduto = this.dao.buscarTodos();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.em.close();
        }
        return listProduto;
    }

    public List<Produto> buscarPorNome(String nome) {
        List<Produto> listProduto = null;
        try {
            listProduto = this.dao.buscarPorNome(nome);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.em.close();
        }
        return listProduto;
    }
}
