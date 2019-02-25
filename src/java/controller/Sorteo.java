/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.Date;
import java.util.List;
import javax.persistence.Persistence;
import modelo.Participante;
import modelo.PptePremio;
import modelo.Premio;

/**
 *
 * @author lenovo
 */
public class Sorteo {
    
    public void sortear(){
        ParticipanteJpaController ppte = new ParticipanteJpaController(Persistence.createEntityManagerFactory("testComfenalcoPU"));
        PptePremioJpaController pptPremio = new PptePremioJpaController(Persistence.createEntityManagerFactory("testComfenalcoPU"));
        PremioJpaController premio = new PremioJpaController(Persistence.createEntityManagerFactory("testComfenalcoPU"));
        List<Participante> participantes = ppte.findParticipanteEntities();
        List<Premio> premios = premio.premiosEntregar();
        
        if(premios.isEmpty()) return;
        int rdmppte = (int)(Math.random()*(participantes.size()-1));
        int rdmpremio = (int)(Math.random()*(premios.size()-1));
        
        PptePremio ganador = new PptePremio();
        ganador.setCodPremio(premios.get(rdmpremio));
        ganador.setFechaSorteo(new Date());
        try{
            ganador.setNumSorteo(pptPremio.getMaxNumSorteo().getNumSorteo()+1);
        }catch(Exception e){
            ganador.setNumSorteo(1);
        }
        ganador.setNumdocPart(participantes.get(rdmppte));
        Premio p = premios.get(rdmpremio);
        short c = p.getCant();
        p.setCant((short) (c-1));
        try{
        pptPremio.create(ganador);
        
        }catch( Exception e){
            
        }
        try{
            premio.edit(p);
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
}

