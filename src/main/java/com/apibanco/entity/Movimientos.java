package com.apibanco.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "movimientos")
public class Movimientos {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="movimientosid")
	private int movimientosid;	
	
	@Column(name = "fecha")
	private Date fecha;
	
	@Column(name = "tipomovimiento")
	private String tipomovimiento;
	
	@Column(name = "valor")
	private float valor;
	
	@Column(name = "saldo")
	private float saldo;
	
	@Column(name = "estado")
	private String estado;
	
	@Column(name = "fkclienteid")
	private int fkclienteid;
	
	@Column(name = "fkcuentaid")
	private int fkcuentaid;

	@Temporal(TemporalType.DATE)
    Date creationDateTime;
	
	
	
	
	public Date getCreationDateTime() {
		return creationDateTime;
	}

	public void setCreationDateTime(Date creationDateTime) {
		this.creationDateTime = creationDateTime;
	}

	public int getMovimientosid() {
		return movimientosid;
	}

	public void setMovimientosid(int movimientosid) {
		this.movimientosid = movimientosid;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getTipomovimiento() {
		return tipomovimiento;
	}

	public void setTipomovimiento(String tipomovimiento) {
		this.tipomovimiento = tipomovimiento;
	}

	public float getValor() {
		return valor;
	}

	public void setValor(float valor) {
		this.valor = valor;
	}

	public float getSaldo() {
		return saldo;
	}

	public void setSaldo(float saldo) {
		this.saldo = saldo;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public int getFkclienteid() {
		return fkclienteid;
	}

	public void setFkclienteid(int fkclienteid) {
		this.fkclienteid = fkclienteid;
	}

	public int getFkcuentaid() {
		return fkcuentaid;
	}

	public void setFkcuentaid(int fkcuentaid) {
		this.fkcuentaid = fkcuentaid;
	}
	
	
	

}
