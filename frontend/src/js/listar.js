import { urlBase } from "./services/api";

document.addEventListener("DOMContentLoaded", listarCarros);

async function listarCarros() {
    const tbody = document.getElementById("carros-lista");

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
    linkEditar.href = `editar.html?id=${carro.id}`;
    linkEditar.className = "btn btn-warning";
    linkEditar.textContent = "Editar";
    tdAcoes.appendChild(linkEditar);
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
