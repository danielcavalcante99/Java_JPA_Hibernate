package resource;

import model.Produto;
import services.ProdutoService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/produto")
public class ProdutoResource {

    private static ProdutoService service = new ProdutoService();

    @GET
    @Path("/buscartodos")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarTodos() {
        List<Produto> listProduto = ProdutoResource.service.buscarTodos();
        return Response.ok(listProduto).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarPorId(@PathParam("id") Long id) {
        Produto produto = ProdutoResource.service.buscarPorId(id);
        return Response.ok(produto).build();
    }

    @GET
    @Path("/buscarpornome/{nome}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarPorNome(@PathParam("nome") String nome) {
        List<Produto> produtos = ProdutoResource.service.buscarPorNome(nome);
        return Response.ok(produtos).build();
    }

    @POST
    @Path("/cadastrar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response cadastrar(Produto novoProduto) {
        ProdutoResource.service.cadastrar(novoProduto);
        return Response.status(Response.Status.CREATED).entity(novoProduto).build();
    }

    @PUT
    @Path("/atualizar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response atualizar(Produto atualizarProduto) {
        ProdutoResource.service.atualizar(atualizarProduto);
        return Response.status(Response.Status.CREATED).entity(atualizarProduto).build();
    }

    @PUT
    @Path("/deletar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletar(Produto deletarProduto) {
        ProdutoResource.service.deletar(deletarProduto);
        return Response.status(Response.Status.CREATED).entity(deletarProduto).build();
    }

}
