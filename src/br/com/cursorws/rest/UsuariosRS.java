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

import br.com.cursorws.business.UsuarioBC;
import br.com.cursorws.business.exception.UsuarioNaoEncontradoException;
import br.com.cursorws.business.exception.ValidacaoException;
import br.com.cursorws.model.Usuario;

@Path("usuarios")
public class UsuariosRS {
	@Inject
	private UsuarioBC usuarioBC;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Usuario> selecionar() {
		return usuarioBC.selecionar();
	}

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Usuario selecionar(@PathParam("id") Long id) {
		try {
			return usuarioBC.selecionar(id);
		} catch (UsuarioNaoEncontradoException e) {
			throw new NotFoundException(e.getMessage());
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response inserir(Usuario body) {
		try {
			Long id = usuarioBC.inserir(body);
			String url = "/api/usuarios/" + id;
			return Response.status(Status.CREATED).header("Location", url).entity(id).build();
		} catch (ValidacaoException e) {
			return tratarValidacaoException(e);
		}
	}

	@PUT
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response atualizar(@PathParam("id") Long id, Usuario usuario) {
		try {
			usuario.setId(id);
			usuarioBC.atualizar(usuario);
			return Response.status(Status.OK).entity(id).build();
		} catch (UsuarioNaoEncontradoException e) {
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
			Usuario usuario = usuarioBC.excluir(id);
			return Response.status(Status.OK).entity(usuario).build();
		} catch (UsuarioNaoEncontradoException e) {
			throw new NotFoundException();
		}
	}

	private Response tratarValidacaoException(ValidacaoException e) {
		return Response.status(Status.NOT_ACCEPTABLE).entity(e.getErros()).build();
	}
}