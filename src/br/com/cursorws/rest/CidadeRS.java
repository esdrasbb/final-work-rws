package br.com.cursorws.rest;

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

import br.com.cursorws.business.CidadeBC;
import br.com.cursorws.business.exception.BeanNotFoundException;
import br.com.cursorws.business.exception.ValidacaoException;
import br.com.cursorws.model.Cidade;

@Path("cidades")
public class CidadeRS {
	@Inject
	private CidadeBC cidadeBC;

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Cidade selecionar(@PathParam("id") Long id) {
		try {
			return cidadeBC.selecionar(id);
		} catch (BeanNotFoundException e) {
			throw new NotFoundException(e.getMessage());
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response inserir(Cidade body) {
		try {
			Long id = cidadeBC.inserir(body);
			String url = "/api/cidades/" + id;
			return Response.status(Status.CREATED).header("Location", url).entity(id).build();
		} catch (ValidacaoException e) {
			return tratarValidacaoException(e);
		}
	}

	@PUT
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response atualizar(@PathParam("id") Long id, Cidade cidade) {
		try {
			cidade.setId(id);
			cidadeBC.atualizar(cidade);
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
			Cidade cidade = cidadeBC.excluir(id);
			return Response.status(Status.OK).entity(cidade).build();
		} catch (BeanNotFoundException e) {
			throw new NotFoundException();
		}
	}

	private Response tratarValidacaoException(ValidacaoException e) {
		return Response.status(Status.NOT_ACCEPTABLE).entity(e.getErros()).build();
	}
}