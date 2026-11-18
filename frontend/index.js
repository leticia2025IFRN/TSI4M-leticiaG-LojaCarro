const urlBase = "http://localhost:8080/employees"
let idFuncionario = 0;

// const formulario = document.getElementById("#subimitButton");
//     formulario.addEventListener("submit", (e) => {
//     e.preventDefault();
//     cadastrarFuncioanrio();
// });

document.addEventListener("DOMContentLoaded", (e) => {
    const formulario = document.getElementById("formularioCadastro");
    formulario.addEventListener("submit", (e) => {
        e.preventDefault();
        cadastrarFuncioanrio();
    });
});

async function listarTodosFuncionarios() {
    try {
        let resposta = await fetch(urlBase);
        const tabelaFuncionairos = document.getElementById("funcionarios");

        if (!resposta.ok) {
            throw new Error(`Erro HTTP! Status: ${resposta.status}`);
        }
        tabelaFuncionairos.innerHTML = "";

        let funcionarios = await resposta.json();
        console.log(funcionarios);
        funcionarios.forEach(funcionario => {
            let tr = document.createElement("tr");
            tr.innerHTML = `
            <th scope="row">${funcionario.id}</th>
            <td>${funcionario.name}</td>
            <td>${funcionario.resume}</td>
            <td>
                <div>
                    <a href="./pages/editar.html?id=${funcionario.id}" class="btn btn-warning">Editar</a>
                    <button class="btn btn-danger" onclick="prepararExclusao(${funcionario.id})" data-bs-toggle="modal" data-bs-target="#modalteste">Excluir</button>
                </div>
            </td>
            `;

            tabelaFuncionairos.appendChild(tr);
        });
    } catch (erro) {
        console.error("Erro ao buscar funcionários:", erro);
    }
}

listarTodosFuncionarios();

async function cadastrarFuncioanrio() {
    const nome = document.getElementById("inputNome").value;
    const curriculo = document.getElementById("inputCurriculo").value;
    const salario = parseFloat(document.getElementById("inputSalario").value);

    const novoFuncionario = {
        name: nome,
        resume: curriculo,
        salary: salario
    }

    try {
        let resposta = await fetch(urlBase, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(novoFuncionario)
        });

        document.querySelector("form").reset();
        await listarTodosFuncionarios();
    } catch (error) {
        alert(error);
    }
}

async function deletarFuncionario() {
    const url = new URLSearchParams(window.location.search);
    try {
        const resposta = await fetch(`${urlBase}/${idFuncionario}`, {
            method: "DELETE"
        });
    } catch (error) {
        alert("Usuario não existe!");
    }

    const modalElement = document.getElementById("modalteste");
    const modalInstance = bootstrap.Modal.getInstance(modalElement) || new bootstrap.Modal(modalElement);
    modalInstance.hide();
    await listarTodosFuncionarios();
}

function prepararExclusao(id) {
    if (id != null || id != undefined) {
        idFuncionario = id;
    }

}