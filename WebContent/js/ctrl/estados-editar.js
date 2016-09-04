$(function() {
	var id = obterParametroDaUrlPorNome('id');
	if (id) {
		$("#cidades-div").show();
		$("#cidades-alert").hide();
		$("#nova-cidade-btn").attr("href", "cidades-editar.html?estadoId=" + id);
		EstadosProxy.selecionar(id).done(obterOk).fail(tratarErro);
	}
	$("#excluir").click(function(event) {
		var id = $("#id").val();
		EstadosProxy.excluir(id).done(excluirOk).fail(tratarErro);
	});
	$("#salvar").click(
			function(event) {
				limparMensagensErro();
				var estado = {
					id : $("#id").val(),
					nome : $("#nome").val(),
					sigla : $("#sigla").val()
				};

				console.log(estado);
				
				if (estado.id) {
					EstadosProxy.atualizar(estado.id, estado).done(
							atualizarOk).fail(tratarErro);
				} else {
					EstadosProxy.inserir(estado).done(inserirOk).fail(
							tratarErro);
				}
			});
});

function inserirOk(data, textStatus, jqXHR) {
	$("#id").val(data);
	$("#global-message").addClass("alert-success").text(
			"Estado com id = " + data + " criado com sucesso.").show();
	$("#nova-cidade-btn").attr("href", "cidades-editar.html?estadoId=" + data);
	$("#cidades-alert").hide();
	$("#cidades-div").show();
}
function tratarErro(request) {
	switch (request.status) {
	case 404:
		$("#global-message").addClass("alert-danger").text(
				"O registro solicitado não foi encontrado!").show();
		break;
	case 406:
		$("form input").each(function() {
			var id = $(this).attr("id");
			var message = null;
			$.each(request.responseJSON, function(index, value) {
				if (id == value.propriedade) {
					message = value.mensagem;
				}
			});
			if (message) {
				$("#" + id).parent().addClass("has-error");
				$("#" + id + "-message").html(message).show();
				$(this).focus();
			} else {
				$("#" + id).parent().removeClass("has-error");
				$("#" + id + "-message").hide();
			}
		});
		$("#global-message").addClass("alert-danger").text(
				"Verifique erros no formulário!").show();
		break;
	default:
		$("#global-message").addClass("alert-danger").text("Erro inesperado.")
				.show();
		break;
	}
}

function obterParametroDaUrlPorNome(name) {
	name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
	var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"), results = regex
			.exec(location.search);
	return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g,
			" "));
}

function obterOk(data) {
	$("#id").val(data.id);
	$("#nome").val(data.nome);
	$("#sigla").val(data.sigla);
	
	populaCidades(data.cidades);
}

function atualizarOk(data, textStatus, jqXHR) {
	$("#global-message").addClass("alert-success").text(
			"Estado atualizado com sucesso.").show();
}

function excluirOk(data, textStatus, jqXHR) {
	$("#id").val(null);
	$("#nome").val(null);
	$("#sigla").val(null);
	$("#cidades-div").hide();
	$("#cidades-alert").show();
	$("#global-message").addClass("alert-success").text(
			"estado excluído com sucesso.").show();
}

function limparMensagensErro() {
	/* Limpa as mensagens de erro */
	$("#global-message").removeClass("alert-danger alert-success").empty()
			.hide();
	$(".control-label").parent().removeClass("has-success");
	$(".text-danger").parent().removeClass("has-error");
	$(".text-danger").hide();
}

function populaCidades(cidades){
	console.log(cidades);
	var body = document.getElementsByTagName("tbody")[0];
	$(body).empty();
	if (cidades) {
		for (var i = 0; i < cidades.length; i++) {
			var cidade = cidades[i];
			var row = document.createElement('tr');
			
			var cellId = document.createElement('td');
			var textId = document.createTextNode(cidade.id);
			var linkId = document.createElement('a');
			linkId.setAttribute("href", "cidades-editar.html?id="
					+ cidade.id + "&estadoId=" + cidade.estadoId);
			linkId.appendChild(textId);
			cellId.appendChild(linkId);
			
			var cellNome = document.createElement('td');
			var textNome = document.createTextNode(cidade.nome);
			cellNome.appendChild(textNome);
						
			var cellContituicao = document.createElement('td');
			var textContituicao = document.createTextNode(cidade.data);
			cellContituicao.appendChild(textContituicao);
			
			var cellPopulacao = document.createElement('td');
			var textPopulacao = document.createTextNode(cidade.populacao);
			cellPopulacao.appendChild(textPopulacao);

			var cellPIB = document.createElement('td');
			var textPIB = document.createTextNode(cidade.pib);
			cellPIB.appendChild(textPIB);
			
			row.appendChild(cellId);
			row.appendChild(cellNome);
			row.appendChild(cellContituicao);
			row.appendChild(cellPopulacao);
			row.appendChild(cellPIB);
			body.appendChild(row);
		}
	} else {
		var table = document.getElementsByTagName("table")[0];
		var foot = document.createElement('tfoot');
		var emptyRow = document.createElement('tr');
		var emptyCell = document.createElement('td');
		var noRegisterText = document
				.createTextNode("Nenhum registro encontrado!");
		emptyCell.appendChild(noRegisterText);
		emptyCell.setAttribute("colspan", 5);
		emptyRow.appendChild(emptyCell);
		foot.appendChild(emptyRow);
		table.appendChild(foot);
	}		
}
