package com.apibanco.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cuenta")
public class Cuenta {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="cuentaid")
	private int cuentaid;	
	
	@Column(name = "numerocuenta")
	private String numerocuenta;
	
	@Column(name = "tipocuenta")
	private String tipocuenta;
	
	@Column(name = "saldoinicial")
	private float saldoinicial;
	
	@Column(name = "estado")
	private String estado;
	
	@Column(name = "fkclienteid")
	private int fkclienteid;
		
	
	public int getFkclienteid() {
		return fkclienteid;
	}

	public void setFkclienteid(int fkclienteid) {
		this.fkclienteid = fkclienteid;
	}

	public int getCuentaid() {
		return cuentaid;
	}

	public void setCuentaid(int cuentaid) {
		this.cuentaid = cuentaid;
	}

	public String getNumerocuenta() {
		return numerocuenta;
	}

	public void setNumerocuenta(String numerocuenta) {
		this.numerocuenta = numerocuenta;
	}

	public String getTipocuenta() {
		return tipocuenta;
	}

	public void setTipocuenta(String tipocuenta) {
		this.tipocuenta = tipocuenta;
	}

	public float getSaldoinicial() {
		return saldoinicial;
	}

	public void setSaldoinicial(float saldoinicial) {
		this.saldoinicial = saldoinicial;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	
	
}
