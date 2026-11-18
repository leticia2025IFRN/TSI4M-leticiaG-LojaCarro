const urlBase = "http://localhost:8080/employees"
const url = new URLSearchParams(window.location.search);

async function trazerInformacoesDoFuncionario() {
    const nome = document.getElementById("inputNome");
    const curriculo = document.getElementById("inputCurriculo");
    const salario = document.getElementById("inputSalario");
    const resposta = await fetch(`${urlBase}/${url.get("id")}`);
    const funcionario = await resposta.json();
    console.log(funcionario);
    nome.value = funcionario.name;
    curriculo.value = funcionario.resume;
    salario.value = funcionario.salary;
}

async function atualizarFuncionario() {
    

    console.log(`id = ${url.get('id')}`);
}
atualizarFuncionario();
trazerInformacoesDoFuncionario();
