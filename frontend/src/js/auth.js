const CHAVE_SESSAO = "lojaCarroUsuario";
const CHAVE_REDIRECIONAMENTO = "lojaCarroRedirecionamento";

export function obterUsuarioLogado() {
    const dados = sessionStorage.getItem(CHAVE_SESSAO);
    if (!dados) {
        return null;
    }

    try {
        const usuario = JSON.parse(dados);
        if (!usuario || !usuario.usuario) {
            sessionStorage.removeItem(CHAVE_SESSAO);
            return null;
        }
        return usuario;
    } catch (erro) {
        sessionStorage.removeItem(CHAVE_SESSAO);
        return null;
    }
}

export function salvarUsuarioLogado(usuario) {
    sessionStorage.setItem(CHAVE_SESSAO, JSON.stringify(usuario));
}

export function consumirRedirecionamento(destinoPadrao) {
    const destino = sessionStorage.getItem(CHAVE_REDIRECIONAMENTO);
    sessionStorage.removeItem(CHAVE_REDIRECIONAMENTO);
    return destino || destinoPadrao;
}

function urlLogin() {
    return window.location.pathname.includes("pages/") ? "login.html" : "login.html";
}

function encerrarSessao() {
    sessionStorage.removeItem(CHAVE_SESSAO);
    sessionStorage.removeItem(CHAVE_REDIRECIONAMENTO);
    window.location.replace(urlLogin());
}

function montarBarraUsuario(usuario) {
    const emSubpasta = window.location.pathname.includes("pages/");
    const raiz = emSubpasta ? "../" : "";

    const barra = document.createElement("nav");
    barra.className = "navbar navbar-expand navbar-dark bg-dark mb-4";
    barra.innerHTML = `
        <div class="container">
            <a class="navbar-brand" href="${raiz}index.html">LojaCarro</a>
            <div class="d-flex align-items-center gap-2 gap-md-3">
                <a class="btn btn-outline-light btn-sm" href="${raiz}pages/listar.html">Listagem</a>
                <span class="text-white small d-none d-sm-inline">Olá, <strong></strong></span>
                <button type="button" class="btn btn-danger btn-sm">Sair</button>
            </div>
        </div>`;

    barra.querySelector("strong").textContent = usuario.nome || usuario.usuario;
    barra.querySelector("button").addEventListener("click", encerrarSessao);

    document.body.prepend(barra);
}

function protegerPagina() {
    const usuario = obterUsuarioLogado();
    if (!usuario) {
        sessionStorage.setItem(CHAVE_REDIRECIONAMENTO, window.location.href);
        window.location.replace(urlLogin());
        return null;
    }

    montarBarraUsuario(usuario);
    return usuario;
}

if (document.body.dataset.pagina !== "login") {
    protegerPagina();
}
