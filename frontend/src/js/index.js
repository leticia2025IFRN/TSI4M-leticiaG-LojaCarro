import { urlBase } from "./services/api";
let idCarroParaExcluir = null;

document.addEventListener("DOMContentLoaded", () => {
    const formulario = document.getElementById("formularioCadastro");
    formulario.addEventListener("submit", (evento) => {
        evento.preventDefault();
        cadastrarCarro();
    });

    document.getElementById("btnConfirmarExclusao").addEventListener("click", deletarCarro);

    listarCarros();
});

async function listarCarros() {
    const tbody = document.getElementById("carros");
    try {
        const resposta = await fetch(urlBase);
        if (!resposta.ok) {
            throw new Error(`Erro HTTP! Status: ${resposta.status}`);
        }

        const carros = await resposta.json();
        tbody.replaceChildren();

        if (carros.length === 0) {
            const tr = document.createElement("tr");
            const td = document.createElement("td");
            td.colSpan = 5;
            td.textContent = "Nenhum carro cadastrado.";
            tr.appendChild(td);
            tbody.appendChild(tr);
            return;
        }

        carros.forEach((carro) => tbody.appendChild(criarLinha(carro)));
    } catch (erro) {
        console.error("Erro ao buscar carros:", erro);
        alert(erro.message);
    }
}

function criarLinha(carro) {
    const tr = document.createElement("tr");

    const th = document.createElement("th");
    th.scope = "row";
    th.textContent = carro.id;
    tr.appendChild(th);
    tr.appendChild(criarCelula(carro.modelo));
    tr.appendChild(criarCelula(carro.ano));
    tr.appendChild(criarCelula(formatarPreco(carro.preco)));

    const tdAcoes = document.createElement("td");
    const linkEditar = document.createElement("a");
    linkEditar.href = `./pages/editar.html?id=${carro.id}`;
    linkEditar.className = "btn btn-warning me-2";
    linkEditar.textContent = "Editar";

    const botaoExcluir = document.createElement("button");
    botaoExcluir.type = "button";
    botaoExcluir.className = "btn btn-danger";
    botaoExcluir.textContent = "Excluir";
    botaoExcluir.dataset.bsToggle = "modal";
    botaoExcluir.dataset.bsTarget = "#modalteste";
    botaoExcluir.addEventListener("click", () => prepararExclusao(carro.id));

    tdAcoes.append(linkEditar, botaoExcluir);
    tr.appendChild(tdAcoes);

    return tr;
}

function criarCelula(texto) {
    const td = document.createElement("td");
    td.textContent = texto;
    return td;
}

function formatarPreco(valor) {
    return Number(valor).toLocaleString("pt-BR", { style: "currency", currency: "BRL" });
}

async function cadastrarCarro() {
    const modelo = document.getElementById("inputModelo").value.trim();
    const ano = Number.parseInt(document.getElementById("inputAno").value, 10);
    const preco = arredondarPreco(Number.parseFloat(document.getElementById("inputPreco").value));

    if (!modelo) {
        alert("Informe o modelo do carro.");
        return;
    }
    if (!Number.isInteger(ano)) {
        alert("Informe um ano válido.");
        return;
    }
    if (!Number.isFinite(preco) || preco < 0) {
        alert("Informe um preço válido.");
        return;
    }

    try {
        const resposta = await fetch(urlBase, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ modelo, ano, preco })
        });

        if (!resposta.ok) {
            const erro = await resposta.json().catch(() => ({}));
            throw new Error(erro.erro || `Erro HTTP! Status: ${resposta.status}`);
        }

        document.getElementById("formularioCadastro").reset();
        await listarCarros();
    } catch (erro) {
        alert(erro.message);
    }
}

async function deletarCarro() {
    if (idCarroParaExcluir === null) {
        return;
    }

    try {
        const resposta = await fetch(`${urlBase}/${idCarroParaExcluir}`, {
            method: "DELETE"
        });

        if (!resposta.ok) {
            const erro = await resposta.json().catch(() => ({}));
            throw new Error(erro.erro || `Erro HTTP! Status: ${resposta.status}`);
        }
    } catch (erro) {
        alert(erro.message);
    } finally {
        fecharModal();
        idCarroParaExcluir = null;
        await listarCarros();
    }
}

function prepararExclusao(id) {
    idCarroParaExcluir = id;
}

function fecharModal() {
    const modalElement = document.getElementById("modalteste");
    const modalInstance = bootstrap.Modal.getInstance(modalElement) || bootstrap.Modal.getOrCreateInstance(modalElement);
    modalInstance.hide();
}

function arredondarPreco(valor) {
    return Number.isFinite(valor) ? Math.round(valor * 100) / 100 : NaN;
}
