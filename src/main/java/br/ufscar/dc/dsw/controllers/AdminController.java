package br.ufscar.dc.dsw.controllers;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import br.ufscar.dc.dsw.domain.Usuario;
import br.ufscar.dc.dsw.errors.Erro;

@WebServlet(urlPatterns = {"/admin/*", "/admin/locadoras/*", "/admin/clientes/*"})
public class AdminController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	Usuario usuario = (Usuario) request.getSession().getAttribute("usuarioLogado");
    	Erro erros = new Erro();

    	if(usuario == null) {
    		response.sendRedirect(request.getContextPath());
    	} 
        else if(usuario.getAdmin()) {
			String path = request.getServletPath();
			if(path.contains("locadoras")) {
				RequestDispatcher dispatcher = request.getRequestDispatcher("/locadoras");
            	dispatcher.forward(request, response);
			}
			else if(path.contains("clientes")) {
				RequestDispatcher dispatcher = request.getRequestDispatcher("/clientes");
            	dispatcher.forward(request, response);
			}
			else {
		        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/logado/admin/index.jsp");	
            	dispatcher.forward(request, response);
			}
    		
    	} else {
    		erros.add("acesso_negado_adm");
    		request.setAttribute("mensagens", erros);
    		RequestDispatcher rd = request.getRequestDispatcher("/noAuth.jsp");
    		rd.forward(request, response);
    	}
    }
}