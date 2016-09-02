$(function() {
	VeiculosProxy.selecionarTodos().done(buscarOk);
});

function buscarOk(veiculos) {
	var body = document.getElementsByTagName("tbody")[0];
	$(body).empty();
	if (veiculos) {
		for (var i = 0; i < veiculos.length; i++) {
			var veiculo = veiculos[i];
			var row = document.createElement('tr');

			var cellId = document.createElement('td');
			var textId = document.createTextNode(veiculo.id);
			var linkId = document.createElement('a');
			linkId.setAttribute("href", "veiculos-editar.html?id="
					+ veiculo.id);
			linkId.appendChild(textId);
			cellId.appendChild(linkId);

			var cellPlaca = document.createElement('td');
			var textPlaca = document.createTextNode(veiculo.placa);
			cellPlaca.appendChild(textPlaca);

			var cellNome = document.createElement('td');
			var textNome = document.createTextNode(veiculo.nome);
			cellNome.appendChild(textNome);

			var cellEmplacamento = document.createElement('td');
			var textEmplacamento = document.createTextNode(veiculo.data);
			cellEmplacamento.appendChild(textEmplacamento);

			row.appendChild(cellId);
			row.appendChild(cellPlaca);
			row.appendChild(cellNome);
			row.appendChild(cellEmplacamento);
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