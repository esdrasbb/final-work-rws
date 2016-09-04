$(function() {
	var estadoId = obterParametroDaUrlPorNome('estadoId');
	$("#back-estado-btn").attr("href", "estados-editar.html?id=" + estadoId);
	console.log(estadoId);
	$("#estadoId").val(estadoId);
	
	var id = obterParametroDaUrlPorNome('id');
	if (id) {
		CidadesProxy.selecionar(id, estadoId).done(obterOk).fail(tratarErro);
	}
	$("#excluir").click(function(event) {
		var id = $("#id").val();
		CidadesProxy.excluir(id, estadoId).done(excluirOk).fail(tratarErro);
	});
	$("#salvar").click(
			function(event) {
				limparMensagensErro();
				var cidade = {
					id : $("#id").val(),
					estadoId : $("#estadoId").val(),
					nome : $("#nome").val(),
					data : $("#data").val(),
					populacao : $("#populacao").val(),
					pib : $("#pib").val()
				};

				if (cidade.id) {
					CidadesProxy.atualizar(cidade.id, cidade).done(
							atualizarOk).fail(tratarErro);
				} else {
					CidadesProxy.inserir(cidade).done(inserirOk).fail(
							tratarErro);
				}
			});
});

function inserirOk(data, textStatus, jqXHR) {
	$("#id").val(data);
	$("#global-message").addClass("alert-success").text(
			"Cidade com id = " + data + " criado com sucesso.").show();
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
	$("#data").val(data.data);
	$("#populacao").val(data.populacao);
	$("#pib").val(data.pib);
}

function atualizarOk(data, textStatus, jqXHR) {
	$("#global-message").addClass("alert-success").text(
			"Cidade atualizada com sucesso.").show();
}

function excluirOk(data, textStatus, jqXHR) {
	$("#id").val(null);
	$("#nome").val(null);
	$("#data").val(null);
	$("#populacao").val(null);
	$("#pib").val(null);
	$("#global-message").addClass("alert-success").text(
			"Cidade excluída com sucesso.").show();
}

function limparMensagensErro() {
	/* Limpa as mensagens de erro */
	$("#global-message").removeClass("alert-danger alert-success").empty()
			.hide();
	$(".control-label").parent().removeClass("has-success");
	$(".text-danger").parent().removeClass("has-error");
	$(".text-danger").hide();
}