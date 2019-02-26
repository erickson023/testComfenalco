/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import controller.ParticipanteJpaController;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.Participante;

/**
 *
 * @author lenovo
 */
public class RegistrarParticipante extends HttpServlet {

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String tpDocumento = request.getParameter("tpDocumento");
        String numDocumento = request.getParameter("numDocumento");
        String nombres = request.getParameter("nombres");
        String apellidos = request.getParameter("apellidos");

        if (request.getParameter("opcion").equals("editando")) {
            
            ParticipanteJpaController ppte = new ParticipanteJpaController(Persistence.createEntityManagerFactory("testComfenalcoPU"));
            Participante participante = ppte.findParticipante(numDocumento);
            
            participante.setTipoDoc(tpDocumento);
            participante.setNumdocPart(numDocumento);
            participante.setNombre(nombres);
            participante.setApellidos(apellidos);
            participante.setFechaNac(new Date());

            try {
                ppte.edit(participante);
            } catch (Exception e) {

            }

        } else {
            Participante participante = new Participante();

            participante.setTipoDoc(tpDocumento);
            participante.setNumdocPart(numDocumento);
            participante.setNombre(nombres);
            participante.setApellidos(apellidos);
            participante.setFechaNac(new Date());

            ParticipanteJpaController ppte = new ParticipanteJpaController(Persistence.createEntityManagerFactory("testComfenalcoPU"));
            try {
                ppte.create(participante);
            } catch (Exception e) {

            }
        }
        response.sendRedirect("listaParticipantes.jsp");
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
