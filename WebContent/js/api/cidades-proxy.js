var CidadesProxy = {
	url : "api/cidades",
	selecionar : function(id, estadoId) {
		return $.ajax({
			type : "GET",
			url : this.url + "/" + id + "/" + estadoId
		});
	},
	inserir : function(cidade) {
		return $.ajax({
			type : "POST",
			url : this.url,
			data : JSON.stringify(cidade),
			contentType : "application/json"
		});
	},
	atualizar : function(id, cidade) {
		return $.ajax({
			type : "PUT",
			url : this.url + "/" + id,
			data : JSON.stringify(cidade),
			contentType : "application/json"
		});
	},
	excluir : function(id, estadoId) {
		return $.ajax({
			type : "DELETE",
			url : this.url + "/" + id + "/" + estadoId
		});
	}
};