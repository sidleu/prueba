
package com.reconosersdk.reconosersdk.http.regula.entities.in;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Anverso {

    @SerializedName("valor")
    @Expose
    private String valor;
    @SerializedName("formato")
    @Expose
    private String formato;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Anverso() {
    }

    /**
     * 
     * @param formato
     * @param valor
     */
    public Anverso(String valor, String formato) {
        super();
        this.valor = valor;
        this.formato = formato;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("valor", valor).append("formato", formato).toString();
    }

}
