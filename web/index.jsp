<%-- 
    Document   : index
    Created on : 24/02/2019, 03:21:23 PM
    Author     : lenovo
--%>

<%@page import="javax.persistence.Persistence"%>
<%@page import="java.util.List"%>
<%@page import="modelo.PptePremio"%>
<%@page import="controller.PptePremioJpaController"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    PptePremioJpaController pp = new PptePremioJpaController(Persistence.createEntityManagerFactory("testComfenalcoPU"));
    List<PptePremio> ganadores = pp.getPptePremioOrderByDesc();
%>

<!DOCTYPE html>
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
        <script src="js/vendor/modernizr-2.8.3-respond-1.4.2.min.js"></script>


    </head>

    <body style="background-color: white">

        <div class="row">
            <nav class="navbar navbar-default" style="background-color: #aa1916; ">
                <a class="navbar-brand" href="#">SORTEO</a>
                <div class=" collapse navbar-collapse" >                 
                    <ul class="navbar-nav nav">
                        <li class="nav-item">
                            <a class="nav-link" href="#" style="font-weight: bold">Sorteo</a>
                        </li>
                    </ul>
                    <ul class="navbar-nav nav">
                        <li class="nav-item">
                            <a class="nav-link" href="participante.jsp" style="font-weight: bold">Paticipantes</a>
                        </li>
                    </ul>

                </div>
            </nav>  

        </div>
        <div class="container success">
            <form action="Sortear" method="post">
                <div class="alert alert-dismissible alert-success col-md-offset-4 col-md-4 text-center">
                    <button type="submit" class="btn btn-warning">Sortear</button>

                </div>
            </form>
        </div> 
        <br>
        <div class="container info" >
            <table class="table table-striped table-hover ">
                <thead>
                    <tr style="color: #000">
                        <th>#</th>
                        <th>Numero Sorteo</th>
                        <th>Fecha Sorteo</th>
                        <th>Ganador</th>
                        <th>Documento</th>
                        <th>Premio</th>
                    </tr>
                </thead>
                <tbody>

                    <%
                        int i = 1;
                        String tabla = "";
                        String fila = "";
                        for (PptePremio ppt : ganadores) {
                            fila = "";
                            fila = "<tr class='" + (i%2==0 ? "warning":"info") + "'><td> " + (i++) + " </td><td> " + ppt.getNumSorteo() + " </td><td> " + ppt.getFechaSorteo().toLocaleString() + " </td>" + "<td>" + ppt.getNumdocPart().getNombre() + " " + ppt.getNumdocPart().getApellidos() + "</td><td>" + ppt.getNumdocPart().getNumdocPart()
                                    + "</td><td>" + ppt.getCodPremio().getDescrip() + "</td></tr>";
                            tabla += fila;
                        }

                        out.print(tabla);
                    %>
                </tbody>
            </table>

        </div>
        

    </body>
</html>
