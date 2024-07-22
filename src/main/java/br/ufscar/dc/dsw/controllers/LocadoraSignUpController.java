package br.ufscar.dc.dsw.controllers;

import br.ufscar.dc.dsw.dao.LocadoraDAO;
import br.ufscar.dc.dsw.dao.UsuarioDAO;
import br.ufscar.dc.dsw.domain.Locadora;
import br.ufscar.dc.dsw.domain.Usuario;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(urlPatterns = { "/registrar-locadora/*" })
public class LocadoraSignUpController extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    private LocadoraDAO daoLocadora;
    private UsuarioDAO daoUsuario;

    @Override
    public void init() {
        daoLocadora = new LocadoraDAO();
        daoUsuario = new UsuarioDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("PASSEI POR: CadastroLocadoraController");

        String action = request.getPathInfo();
        System.out.println("ACTION -> " + request.getPathInfo());
        if (action == null) {
            action = "";
        }
    
        try {
            switch (action) {
                case "/cadastro":
                    apresentaFormCadastro(request, response);
                    break;

                case "/insercao":
                    insere(request, response);
                    break;

                default:
                    apresentaFormCadastro(request, response);
                    break;
            }
        } catch (RuntimeException | IOException | ServletException e) {
            throw new ServletException(e);
        }
    }

    private void apresentaFormCadastro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().setAttribute("listaLocadoras", new LocadoraDAO().getAll());
        RequestDispatcher dispatcher = request.getRequestDispatcher("/cadastroUsuario/locadora/formulario.jsp");
        dispatcher.forward(request, response);
    }

    private void insere(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        try {
            String email = request.getParameter("email");

            // Verificar se o email já existe
            if(daoUsuario.getUserByEmail(email) != null) {
                String mensagemErro = "O email já está em uso.";
                request.setAttribute("mensagemErro", mensagemErro);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/cadastroUsuario/locadora/formulario.jsp");
                dispatcher.forward(request, response);
                return;
            }

            String cnpj = request.getParameter("cnpj");
            // Verificar se o CPF já existe
            if(daoLocadora.getLocadoraByCNPJ(cnpj) != null) {
                String mensagemErro = "O CNPJ já está em uso.";
                request.setAttribute("mensagemErro", mensagemErro);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/cadastroUsuario/locadora/formulario.jsp");
                dispatcher.forward(request, response);
                return;
            }

            String senha = request.getParameter("senha");
            String nome = request.getParameter("nome");

            String administrador = request.getParameter("administrador");
            boolean admin = false;

            if(administrador == null || administrador == "0") {
                admin = false;
            }
            else {
                admin = true;
            }

            Usuario usuario = new Usuario(email, senha, nome, admin, true);
            daoUsuario.insertUser(usuario);
            usuario = daoUsuario.getUserByEmail(email);

            String cidade = request.getParameter("cidade");
            Locadora locadora = new Locadora(usuario.getId(), cnpj, email, senha, nome, admin, true, cidade);

            daoLocadora.insertLocadora(locadora);
            response.sendRedirect(request.getContextPath() + "/index.jsp");
        } 
        catch (RuntimeException | IOException e) {
            throw new ServletException(e);
        }
    }
    
}