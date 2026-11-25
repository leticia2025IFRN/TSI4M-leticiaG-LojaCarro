const USUARIOS = [
    { usuario: "admin", senha: "admin123", nome: "Administrador", cargo: "Administrador" },
    { usuario: "vendedor", senha: "vendas123", nome: "Vendedor", cargo: "Vendedor" }
];

const formularioLogin = document.getElementById("formularioLogin");
const campoUsuario = document.getElementById("inputUsuario");
const campoSenha = document.getElementById("inputSenha");
const mensagemErro = document.getElementById("mensagemErro");

if (obterUsuarioLogado()) {
    window.location.replace(consumirRedirecionamento("../index.html"));
}

[campoUsuario, campoSenha].forEach((campo) => {
    campo.addEventListener("input", () => mensagemErro.classList.add("d-none"));
});

formularioLogin.addEventListener("submit", (evento) => {
    evento.preventDefault();

    const usuarioDigitado = campoUsuario.value.trim();
    const senhaDigitada = campoSenha.value;

    if (!usuarioDigitado || !senhaDigitada) {
        exibirErro("Informe usuário e senha.");
        return;
    }

    const encontrado = USUARIOS.find((usuario) =>
        usuario.usuario.toLowerCase() === usuarioDigitado.toLowerCase() && usuario.senha === senhaDigitada
    );

    if (!encontrado) {
        exibirErro("Usuário ou senha inválidos.");
        campoSenha.value = "";
        campoSenha.focus();
        return;
    }

    salvarUsuarioLogado({
        usuario: encontrado.usuario,
        nome: encontrado.nome,
        cargo: encontrado.cargo
    });

    window.location.replace(consumirRedirecionamento("../index.html"));
});

function exibirErro(texto) {
    mensagemErro.textContent = texto;
    mensagemErro.classList.remove("d-none");
}
