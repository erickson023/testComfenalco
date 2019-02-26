<%-- 
    Document   : participante
    Created on : 24/02/2019, 03:21:23 PM
    Author     : lenovo
--%>

<%@page import="modelo.Participante"%>
<%@page import="javax.persistence.Persistence"%>
<%@page import="controller.ParticipanteJpaController"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    Participante p = null;
    String cc = "";
    if (request.getParameter("doc1") != null) {
        cc = (String) request.getParameter("doc1");
        ParticipanteJpaController pte = new ParticipanteJpaController(Persistence.createEntityManagerFactory("testComfenalcoPU"));
        p = pte.findParticipante(cc);
        System.out.print(p.getNombre());
        

    }
%>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <title>PRACTICA COMFENALCO</title>
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="apple-touch-icon" href="apple-touch-icon.png">

        <link rel="stylesheet" href="css/bootstrap.css">
        <link href="css/font-awesome.css" rel="stylesheet" type="text/css"/>
        <script src="js/bootstrap-datetimepicker.js" type="text/javascript"></script>
        <script src="js/jquery-2.1.1.min.js" type="text/javascript"></script>
        <script src="js/moment.js" type="text/javascript"></script>
    </head>

    <body style="background-color: white">
        <div class="row">
            <nav class="navbar navbar-default" style="background-color: #aa1916; ">
                <a class="navbar-brand" href="#">SORTEO</a>
                <div class=" collapse navbar-collapse" >                 
                    <ul class="navbar-nav nav">
                        <li class="nav-item">
                            <a class="nav-link" href="index.jsp" style="font-weight: bold">Sorteo</a>
                        </li>
                    </ul>
                    <ul class="navbar-nav nav">
                        <li class="nav-item">
                            <a class="nav-link" href="listaParticipantes.jsp" style="font-weight: bold">Paticipantes</a>
                        </li>
                    </ul>

                </div>
            </nav>  
        </div>
        <form action="Participante" method="post">
            <div class=" ">
                <div class="row">   
                    <div class="col-md-5"></div>
                    <label class="control-label col-md-2 center-block" style="text-align: center;color: #b43432">Tipo documento</label>                                
                </div>
                <div class="row">
                    <div class="col-md-4 col-md-offset-4">
                        <input required=""  type="text" <%= p != null ? "value= '" + p.getTipoDoc()+ "'" : "" %> style="text-align: center" name="tpDocumento" class="form-control"  placeholder="Tipo documento">
                        
                    </div>
                </div>
                <div class="row" style="padding-top: 10px">   
                    <div class="col-md-5"></div>
                    <label  class="control-label col-md-2 center-block" style="text-align: center;color: #b43432">Numero documento</label>                                
                </div>
                <div class="row">
                    <div class="col-md-4 col-md-offset-4">
                        <input required="" type="text" <%= p != null ? "value= '" + p.getNumdocPart()+ "'" : "" %> style="text-align: center" name="numDocumento" class="form-control"  placeholder="documento">
                        
                    </div>
                </div>
                <div class="row" style="padding-top: 10px">   
                    <div class="col-md-5"></div>
                    <label  class="control-label col-md-2 center-block" style="text-align: center;color: #b43432">Nombres</label>                                
                </div>
                <div class="row">
                    <div class="col-md-4 col-md-offset-4">
                        <input  type="text" <%= p != null ? "value= '" + p.getNombre()+ "'" : "" %> style="text-align: center" name="nombres" class="form-control"  placeholder="Nombres">
                        
                    </div>
                </div>
                <div class="row" style="padding-top: 10px">   
                    <div class="col-md-5"></div>
                    <label  class="control-label col-md-2 center-block" style="text-align: center;color: #b43432">Apellidos</label>                                
                </div>
                <div class="row">
                    <div class="col-md-4 col-md-offset-4">
                        <input  type="text" <%= p != null ? "value= '" + p.getApellidos()+ "'" : "" %> style="text-align: center" name="apellidos" class="form-control"  placeholder="Apellidos">
                        
                    </div>
                </div>
                
                
                
                        <div>
                            <input  type="hidden" <%= p != null ? "value= 'editando'" : " value='creando'" %> name="opcion">
                        </div>

            </div>
            <br>
            <div class="">
                <button type="submit" class=" col-md-2 col-md-offset-5 btn btn-success" ><%= p != null ? "ACTUALIZAR" : "REGISTRAR" %></button>
                
            </div>
                

        </div>
</body>
</html>



