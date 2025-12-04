package com.camelloncase.domain.vo;

import jakarta.persistence.Embeddable;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

@Embeddable
public class ValorMonetario {
    
    private BigDecimal quantia;

	protected ValorMonetario() {
	}
    
    public ValorMonetario(BigDecimal quantia) {
        if(quantia == null || quantia.signum() < 0) {
            throw new IllegalArgumentException("Valor Monetário não pode ser nulo ou negativo");
        }
        this.quantia = quantia.setScale(2, RoundingMode.HALF_UP);        
    }
    
    public BigDecimal getQuantia() {
        return this.quantia;
    }
    
    public ValorMonetario somar(ValorMonetario outro) {
        if(outro == null) {
            throw new IllegalArgumentException("Valor Monetário não pode ser nulo ou negativo");
        }
        return new ValorMonetario(this.quantia.add(outro.getQuantia()));
    }
    
    public ValorMonetario subtrair(ValorMonetario outro) {
        if(outro == null) {
            throw new IllegalArgumentException("Valor Monetário não pode ser nulo ou negativo");
        }
        return new ValorMonetario(this.quantia.subtract(outro.getQuantia()));
    }

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (!(obj instanceof ValorMonetario outro)) return false;
		return Objects.equals(this.quantia, outro.quantia);
	}

	@Override
	public int hashCode() {
		return Objects.hash(quantia);
	}

	@Override
	public String toString() {
		return "ValorMonetario{" +
				"quantia=" + quantia +
				'}';
	}
}
