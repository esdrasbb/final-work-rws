package br.com.cursorws.rest;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import br.com.cursorws.business.VeiculoBC;
import br.com.cursorws.business.exception.ValidacaoException;
import br.com.cursorws.business.exception.BeanNotFoundException;
import br.com.cursorws.model.Veiculo;

@Path("veiculos")
public class VeiculosRS {
	@Inject
	private VeiculoBC veiculoBC;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Veiculo> selecionar() {
		return veiculoBC.selecionar();
	}

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Veiculo selecionar(@PathParam("id") Long id) {
		try {
			return veiculoBC.selecionar(id);
		} catch (BeanNotFoundException e) {
			throw new NotFoundException(e.getMessage());
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response inserir(Veiculo body) {
		try {
			Long id = veiculoBC.inserir(body);
			String url = "/api/veiculos/" + id;
			return Response.status(Status.CREATED).header("Location", url).entity(id).build();
		} catch (ValidacaoException e) {
			return tratarValidacaoException(e);
		}
	}

	@PUT
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response atualizar(@PathParam("id") Long id, Veiculo veiculo) {
		try {
			veiculo.setId(id);
			veiculoBC.atualizar(veiculo);
			return Response.status(Status.OK).entity(id).build();
		} catch (BeanNotFoundException e) {
			throw new NotFoundException();
		} catch (ValidacaoException e) {
			return tratarValidacaoException(e);
		}
	}

	@DELETE
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response excluir(@PathParam("id") Long id) {
		try {
			Veiculo veiculo = veiculoBC.excluir(id);
			return Response.status(Status.OK).entity(veiculo).build();
		} catch (BeanNotFoundException e) {
			throw new NotFoundException();
		}
	}

	private Response tratarValidacaoException(ValidacaoException e) {
		return Response.status(Status.NOT_ACCEPTABLE).entity(e.getErros()).build();
	}
}