<%-- 
    Document   : listaParticipantes
    Created on : 25/02/2019, 09:33:44 PM
    Author     : lenovo
--%>

<%@page import="modelo.Participante"%>
<%@page import="java.util.List"%>
<%@page import="javax.persistence.Persistence"%>
<%@page import="controller.ParticipanteJpaController"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    ParticipanteJpaController p = new ParticipanteJpaController(Persistence.createEntityManagerFactory("testComfenalcoPU"));
    List<Participante> participantes = p.findParticipanteEntities();

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
                            <a class="nav-link" href="#" style="font-weight: bold">Paticipantes</a>
                        </li>
                    </ul>

                </div>
            </nav>  

        </div>
        
        <div class="container success">
            <form action="participante.jsp" method="post">
                <div class="alert alert-dismissible alert-success col-md-offset-4 col-md-4 text-center">
                    <button type="submit" class="btn btn-warning">Nuevo Partipante</button>

                </div>
            </form>
        </div> 
        
        <div class="container info" >
            <table class="table table-striped table-hover ">
                <thead>
                    <tr style="color: #000">
                        <th>#</th>
                        <th>Tipo Documento</th>
                        <th>Documento</th>
                        <th>Nombre</th>
                        <th>Apellidos</th>
                        <th>Fecha Nacimiento</th>
                        <th></th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>

                    <%
                        int i = 1;
                        String tabla = "";
                        String fila = "";
                        for (Participante ppt : participantes) {
                            fila = "";
                            fila = "<tr class='" + (i%2==0 ? "warning":"info") + 
                            "'><td> " + (i++) + " </td><td> " + ppt.getTipoDoc()+ 
                            " </td><td> " + ppt.getNumdocPart() + " </td>" + "<td>"
                            + ppt.getNombre()+"</td><td>"+ ppt.getApellidos() + "</td>"
                            + "<td>" + ppt.getFechaNac().toLocaleString()+ "</td>"
                            + "<td><form action= 'participante.jsp' method='post' >"
                                    + " <button tipe='submit' class='btn btn-success'>Editar</button>"
                                    + "<input type='hidden' value='"+ppt.getNumdocPart()+"' name='doc1'></input>"
                                    + "</form>"
                                    + " </td>" +
                            "<td><form action= 'EliminarParticipante' method='post' >"
                                    + " <button tipe='submit' class='btn btn-danger'>Eliminar</button>"
                                    + "<input type='hidden' value='"+ppt.getNumdocPart()+"' name='doc2'></input>"
                                    + "</form>"
                                    + " </td>" +"</tr>";
                            tabla += fila;
                        }

                        out.print(tabla);
                    %>
                </tbody>
            </table>
        </div>
    </body>
</html>
