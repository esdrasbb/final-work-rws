$(function() {
	UsuariosProxy.selecionarTodos().done(buscarOk);
});

function buscarOk(usuarios) {
	console.log(usuarios);
	var body = document.getElementsByTagName("tbody")[0];
	$(body).empty();
	if (usuarios) {
		for (var i = 0; i < usuarios.length; i++) {
			var usuario = usuarios[i];
			var row = document.createElement('tr');
			
			var cellCpf = document.createElement('td');
			var textCpf = document.createTextNode(usuario.cpf);
			var linkCpf = document.createElement('a');
			linkCpf.setAttribute("href",
			"usuarios-editar.html?id=" + usuario.id);
			linkCpf.appendChild(textCpf);
			cellCpf.appendChild(linkCpf);
			
			var cellNome = document.createElement('td');
			var textNome = document.createTextNode(usuario.nome);
			cellNome.appendChild(textNome);
			
			var cellEmail = document.createElement('td');
			var textEmail = document.createTextNode(usuario.email);
			cellEmail.appendChild(textEmail);
			
			var cellNascimento = document.createElement('td');
			var textNascimento = document.createTextNode(usuario.data);
			cellNascimento.appendChild(textNascimento);
			
			
			row.appendChild(cellCpf);
			row.appendChild(cellNome);
			row.appendChild(cellEmail);
			row.appendChild(cellNascimento);
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