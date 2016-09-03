var EstadosProxy = {
	url : "api/estados",
	selecionar : function(id) {
		return $.ajax({
			type : "GET",
			url : this.url + "/" + id
		});
	},
	selecionarTodos : function() {
		return $.ajax({
			type : "GET",
			url : this.url
		});
	},
	inserir : function(estado) {
		return $.ajax({
			type : "POST",
			url : this.url,
			data : JSON.stringify(estado),
			contentType : "application/json"
		});
	},

	atualizar : function(id, estado) {
		return $.ajax({
			type : "PUT",
			url : this.url + "/" + id,
			data : JSON.stringify(estado),
			contentType : "application/json"
		});
	},
	excluir : function(id) {
		return $.ajax({
			type : "DELETE",
			url : this.url + "/" + id
		});
	}
};