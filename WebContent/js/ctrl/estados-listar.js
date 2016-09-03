$(function() {
	EstadosProxy.selecionarTodos().done(buscarOk);
});

function buscarOk(estados) {
	var body = document.getElementsByTagName("tbody")[0];
	$(body).empty();
	if (estados) {
		for (var i = 0; i < estados.length; i++) {
			var estado = estados[i];
			var row = document.createElement('tr');

			var cellId = document.createElement('td');
			var textId = document.createTextNode(estado.id);
			var linkId = document.createElement('a');
			linkId.setAttribute("href", "estados-editar.html?id="
					+ estado.id);
			linkId.appendChild(textId);
			cellId.appendChild(linkId);

			var cellNome = document.createElement('td');
			var textNome = document.createTextNode(estado.nome);
			cellNome.appendChild(textNome);

			var cellSigla = document.createElement('td');
			var textSigla = document.createTextNode(estado.sigla);
			cellSigla.appendChild(textSigla);

			row.appendChild(cellId);
			row.appendChild(cellNome);
			row.appendChild(cellSigla);
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
		emptyCell.setAttribute("colspan", 4);
		emptyRow.appendChild(emptyCell);
		foot.appendChild(emptyRow);
		table.appendChild(foot);
	}
}