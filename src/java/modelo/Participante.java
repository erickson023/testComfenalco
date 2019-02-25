/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author lenovo
 */
@Entity
@Table(catalog = "pruebacom", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Participante.findAll", query = "SELECT p FROM Participante p"),
    @NamedQuery(name = "Participante.findByTipoDoc", query = "SELECT p FROM Participante p WHERE p.tipoDoc = :tipoDoc"),
    @NamedQuery(name = "Participante.findByNumdocPart", query = "SELECT p FROM Participante p WHERE p.numdocPart = :numdocPart"),
    @NamedQuery(name = "Participante.findByNombre", query = "SELECT p FROM Participante p WHERE p.nombre = :nombre"),
    @NamedQuery(name = "Participante.findByApellidos", query = "SELECT p FROM Participante p WHERE p.apellidos = :apellidos"),
    @NamedQuery(name = "Participante.findByFechaNac", query = "SELECT p FROM Participante p WHERE p.fechaNac = :fechaNac")})
public class Participante implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "tipo_doc", nullable = false, length = 3)
    private String tipoDoc;
    @Id
    @Basic(optional = false)
    @Column(name = "numdoc_part", nullable = false, length = 15)
    private String numdocPart;
    @Basic(optional = false)
    @Column(nullable = false, length = 50)
    private String nombre;
    @Basic(optional = false)
    @Column(nullable = false, length = 50)
    private String apellidos;
    @Basic(optional = false)
    @Column(name = "fecha_nac", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaNac;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "numdocPart")
    private List<PptePremio> pptePremioList;

    public Participante() {
    }

    public Participante(String numdocPart) {
        this.numdocPart = numdocPart;
    }

    public Participante(String numdocPart, String tipoDoc, String nombre, String apellidos, Date fechaNac) {
        this.numdocPart = numdocPart;
        this.tipoDoc = tipoDoc;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.fechaNac = fechaNac;
    }

    public String getTipoDoc() {
        return tipoDoc;
    }

    public void setTipoDoc(String tipoDoc) {
        this.tipoDoc = tipoDoc;
    }

    public String getNumdocPart() {
        return numdocPart;
    }

    public void setNumdocPart(String numdocPart) {
        this.numdocPart = numdocPart;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public Date getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(Date fechaNac) {
        this.fechaNac = fechaNac;
    }

    @XmlTransient
    public List<PptePremio> getPptePremioList() {
        return pptePremioList;
    }

    public void setPptePremioList(List<PptePremio> pptePremioList) {
        this.pptePremioList = pptePremioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numdocPart != null ? numdocPart.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Participante)) {
            return false;
        }
        Participante other = (Participante) object;
        if ((this.numdocPart == null && other.numdocPart != null) || (this.numdocPart != null && !this.numdocPart.equals(other.numdocPart))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Participante[ numdocPart=" + numdocPart + " ]";
    }
    
}
