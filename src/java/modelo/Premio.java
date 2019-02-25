/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
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
    @NamedQuery(name = "Premio.findAll", query = "SELECT p FROM Premio p"),
    @NamedQuery(name = "Premio.findByCodPremio", query = "SELECT p FROM Premio p WHERE p.codPremio = :codPremio"),
    @NamedQuery(name = "Premio.findByDescrip", query = "SELECT p FROM Premio p WHERE p.descrip = :descrip"),
    @NamedQuery(name = "Premio.findByCant", query = "SELECT p FROM Premio p WHERE p.cant = :cant"),
    @NamedQuery(name = "Premio.findByCantExt", query = "SELECT p FROM Premio p WHERE p.cant > 0")})
public class Premio implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "cod_premio", nullable = false, length = 100)
    private String codPremio;
    @Basic(optional = false)
    @Column(nullable = false, length = 50)
    private String descrip;
    @Basic(optional = false)
    @Column(nullable = false)
    private short cant;
    @OneToMany(mappedBy = "codPremio")
    private List<PptePremio> pptePremioList;

    public Premio() {
    }

    public Premio(String codPremio) {
        this.codPremio = codPremio;
    }

    public Premio(String codPremio, String descrip, short cant) {
        this.codPremio = codPremio;
        this.descrip = descrip;
        this.cant = cant;
    }

    public String getCodPremio() {
        return codPremio;
    }

    public void setCodPremio(String codPremio) {
        this.codPremio = codPremio;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    public short getCant() {
        return cant;
    }

    public void setCant(short cant) {
        this.cant = cant;
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
        hash += (codPremio != null ? codPremio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Premio)) {
            return false;
        }
        Premio other = (Premio) object;
        if ((this.codPremio == null && other.codPremio != null) || (this.codPremio != null && !this.codPremio.equals(other.codPremio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Premio[ codPremio=" + codPremio + " ]";
    }

}
