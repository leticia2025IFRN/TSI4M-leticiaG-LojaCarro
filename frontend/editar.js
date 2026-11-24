const urlBase = "http://localhost:8080/carro";
const parametros = new URLSearchParams(window.location.search);
const idCarro = parametros.get("id");

document.addEventListener("DOMContentLoaded", () => {
    const formulario = document.getElementById("formularioEdicao");
    formulario.addEventListener("submit", (evento) => {
        evento.preventDefault();
        atualizarCarro();
    });

    carregarCarro();
});

async function carregarCarro() {
    if (!idCarro) {
        alert("ID do carro não informado.");
        window.location.href = "../index.html";
        return;
    }

    try {
        const resposta = await fetch(`${urlBase}/${idCarro}`);
        if (!resposta.ok) {
            const erro = await resposta.json().catch(() => ({}));
            throw new Error(erro.erro || `Erro HTTP! Status: ${resposta.status}`);
        }

        const carro = await resposta.json();
        document.getElementById("inputModelo").value = carro.modelo;
        document.getElementById("inputAno").value = carro.ano;
        document.getElementById("inputPreco").value = Number(carro.preco).toFixed(2);
    } catch (erro) {
        alert(erro.message);
        window.location.href = "../index.html";
    }
}

async function atualizarCarro() {
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
        const resposta = await fetch(`${urlBase}/${idCarro}`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ modelo, ano, preco })
        });

        if (!resposta.ok) {
            const erro = await resposta.json().catch(() => ({}));
            throw new Error(erro.erro || `Erro HTTP! Status: ${resposta.status}`);
        }

        alert("Carro atualizado com sucesso.");
        window.location.href = "../index.html";
    } catch (erro) {
        alert(erro.message);
    }
}

function arredondarPreco(valor) {
    return Number.isFinite(valor) ? Math.round(valor * 100) / 100 : NaN;
}
