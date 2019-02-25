/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author lenovo
 */
@Entity
@Table(name = "ppte_premio", catalog = "pruebacom", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PptePremio.findAll", query = "SELECT p FROM PptePremio p ORDER BY p.numSorteo DESC"),
    @NamedQuery(name = "PptePremio.findByNumSorteo", query = "SELECT p FROM PptePremio p WHERE p.numSorteo = :numSorteo"),
    @NamedQuery(name = "PptePremio.findByFechaSorteo", query = "SELECT p FROM PptePremio p WHERE p.fechaSorteo = :fechaSorteo"),
    @NamedQuery(name = "PptePremio.findByMaxNumSorteo", query = "SELECT p FROM PptePremio p order by p.numSorteo DESC")})
public class PptePremio implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "num_sorteo", nullable = false, length = 100)
    private Integer numSorteo;
    @Basic(optional = false)
    @Column(name = "fecha_sorteo", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaSorteo;
    @JoinColumn(name = "cod_premio", referencedColumnName = "cod_premio", nullable = false)
    @ManyToOne(optional = false)
    private Premio codPremio;
    @JoinColumn(name = "numdoc_part", referencedColumnName = "numdoc_part", nullable = false)
    @ManyToOne(optional = false)
    private Participante numdocPart;

    public PptePremio() {
    }

    public PptePremio(Integer numSorteo) {
        this.numSorteo = numSorteo;
    }

    public PptePremio(Integer numSorteo, Date fechaSorteo) {
        this.numSorteo = numSorteo;
        this.fechaSorteo = fechaSorteo;
    }

    public Integer getNumSorteo() {
        return numSorteo;
    }

    public void setNumSorteo(Integer numSorteo) {
        this.numSorteo = numSorteo;
    }

    public Date getFechaSorteo() {
        return fechaSorteo;
    }

    public void setFechaSorteo(Date fechaSorteo) {
        this.fechaSorteo = fechaSorteo;
    }

    public Premio getCodPremio() {
        return codPremio;
    }

    public void setCodPremio(Premio codPremio) {
        this.codPremio = codPremio;
    }

    public Participante getNumdocPart() {
        return numdocPart;
    }

    public void setNumdocPart(Participante numdocPart) {
        this.numdocPart = numdocPart;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numSorteo != null ? numSorteo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PptePremio)) {
            return false;
        }
        PptePremio other = (PptePremio) object;
        if ((this.numSorteo == null && other.numSorteo != null) || (this.numSorteo != null && !this.numSorteo.equals(other.numSorteo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.PptePremio[ numSorteo=" + numSorteo + " ]";
    }

}
