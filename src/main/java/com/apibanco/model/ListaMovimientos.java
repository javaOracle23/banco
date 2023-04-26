package com.apibanco.model;

import java.util.Date;

public class ListaMovimientos {

	private Date fecha;
	private String cliente;
	private String numeroCuenta;
	private String tipo;
	private float saldoInicial;
	private String estado;
	private float movimiento;
	private float saldoDisponible;
	
		
	public ListaMovimientos(Date fecha, String cliente, String numeroCuenta, String tipo, float saldoInicial,
			String estado, float movimiento) {
		super();
		this.fecha = fecha;
		this.cliente = cliente;
		this.numeroCuenta = numeroCuenta;
		this.tipo = tipo;
		this.saldoInicial = saldoInicial;
		this.estado = estado;
		this.movimiento = movimiento;		
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public String getCliente() {
		return cliente;
	}
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	public String getNumeroCuenta() {
		return numeroCuenta;
	}
	public void setNumeroCuenta(String numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public float getSaldoInicial() {
		return saldoInicial;
	}
	public void setSaldoInicial(float saldoInicial) {
		this.saldoInicial = saldoInicial;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public float getMovimiento() {
		return movimiento;
	}
	public void setMovimiento(float movimiento) {
		this.movimiento = movimiento;
	}
	public float getSaldoDisponible() {
		return saldoDisponible;
	}
	public void setSaldoDisponible(float saldoDisponible) {
		this.saldoDisponible = saldoDisponible;
	}
	
	
	
	
	
}
