/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.modelo;

import ec.com.atikasoft.proteus.enums.TipoCuentaEnum;
import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Servidor
 *
 * @author Alvaro Tituaña <alvaro.tituania@marcasoft.ec>
 */
@Entity
@Table(name = "cuentas_bancarias", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = CuentaBancaria.BUSCAR_VIGENTES,
            query = "SELECT a FROM CuentaBancaria a where a.vigente=true and a.servidorId=?1"),
    @NamedQuery(name = CuentaBancaria.BUSCAR_POR_BENEFICIARIO_INSTITUCIONAL,
            query = "SELECT a FROM CuentaBancaria a where a.vigente=true and a.beneficiarioInstitucion.id=?1"),
    @NamedQuery(name = CuentaBancaria.BUSCAR_POR_BENEFICIARIO_ESPECIAL,
            query = "SELECT a FROM CuentaBancaria a where a.vigente=true and a.beneficiarioEspecial.id=?1"),
    @NamedQuery(name = CuentaBancaria.BUSCAR_PARA_NOMINA_POR_BENEFICIARIO_ESPECIAL,
            query = "SELECT a FROM CuentaBancaria a where a.vigente=true and a.beneficiarioEspecial.numeroIdentificacion=?1 "
            + "and a.pagoNomina=true "),
    @NamedQuery(name = CuentaBancaria.BUSCAR_PARA_NOMINA_POR_BENEFICIARIO_INTITUCIONAL,
            query = "SELECT a FROM CuentaBancaria a where a.vigente=true and a.beneficiarioInstitucion.numeroIdentificacion=?1 "
            + "and a.pagoNomina=true "),
    @NamedQuery(name = CuentaBancaria.BUSCAR_TIENE_NOMINA,
            query = "SELECT a FROM CuentaBancaria a where a.vigente=true and a.pagoNomina=true"),
    @NamedQuery(name = CuentaBancaria.BUSCAR_PARA_NOMINA_POR_SERVIDOR,
            query = "SELECT a FROM CuentaBancaria a where a.vigente=true and a.servidorId=?1 and a.pagoNomina=true"),
    @NamedQuery(name = CuentaBancaria.BUSCAR_POR_CODIGO,
            query = "SELECT a FROM CuentaBancaria a where a.numerCuenta=?1 and a.vigente=true"),
    @NamedQuery(name = CuentaBancaria.BUSCAR_POR_BANCO_TIPO_Y_NUMERO,
            query = "SELECT a FROM CuentaBancaria a where a.banco.id =?1 and a.tipoCuenta =?2 and a.numerCuenta=?3 and a.vigente=true")
})
public class CuentaBancaria extends EntidadBasica {

    /**
     * Nombre para busqeda de vigentes.
     */
    public static final String BUSCAR_VIGENTES = "CuentaBancaria.buscarVigente";

    /**
     * Nombre para busqeda de vigentes.
     */
    public static final String BUSCAR_TIENE_NOMINA = "CuentaBancaria.buscarTieneNomina";

    /**
     * Variable parabusqeda por código.
     */
    public static final String BUSCAR_POR_CODIGO = "CuentaBancaria.buscarporCodigo";
    /**
     * Variable parabusqeda por beneficiario institucional.
     */
    public static final String BUSCAR_POR_BENEFICIARIO_INSTITUCIONAL = "CuentaBancaria.buscarporBeneficiarioInstitucional";
    /**
     * Variable parabusqeda por beneficiario especial.
     */
    public static final String BUSCAR_POR_BENEFICIARIO_ESPECIAL = "CuentaBancaria.buscarporBeneficiarioEspecial";

    /**
     * Variable para buscar cuenta bancaria que usa para pagar en nomina por
     * servidor.
     */
    public static final String BUSCAR_PARA_NOMINA_POR_SERVIDOR = "CuentaBancaria.buscarporServidorPagoNomina";
    /**
     * Variable para buscar cuenta bancaria que usa para pagar en nomina por
     * servidor.
     */
    public static final String BUSCAR_PARA_NOMINA_POR_BENEFICIARIO_ESPECIAL = "CuentaBancaria.buscarporBeneficiarioEspecialPagoNomina";
    /**
     * Variable para buscar cuenta bancaria que usa para pagar en nomina por
     * servidor.
     */
    public static final String BUSCAR_PARA_NOMINA_POR_BENEFICIARIO_INTITUCIONAL = "CuentaBancaria.buscarporBeneficiarioInstucionalPagoNomina";

    /**
     * Busca si la cuenta existe dado el banco, el tipo y el numero
     */
    public static final String BUSCAR_POR_BANCO_TIPO_Y_NUMERO = "CuentaBancaria.buscarPorBancoTipoYNumero";

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * numerCuenta
     */
    @Basic(optional = false)
    @NotNull
    @Column(name = "numero_cuenta")
    private String numerCuenta;

    /**
     * tipoCuenta
     */
    @Basic(optional = false)
    @NotNull
    @Column(name = "tipo_cuenta")
    private String tipoCuenta;

    /**
     * tipoCuenta
     */
    @Basic(optional = false)
    @NotNull
    @Column(name = "paga_nomina")
    private Boolean pagoNomina;

    /**
     * banco
     */
    @JoinColumn(name = "banco_id", updatable = false, insertable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Banco banco;

    /**
     * bancoId
     */
    @Column(name = "banco_id")
    private Long bancoId;

    /**
     * banco
     */
    @JoinColumn(name = "servidor_id", updatable = false, insertable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Servidor servidor;

    /**
     * Campo que indica que la información bancaria correponde al servidor.
     */
    @Column(name = "servidor_id")
    private Long servidorId;

    /**
     * Campo que indica que almacena la información bancaria de un beneficiario
     * insitucional.
     */
    @JoinColumn(name = "beneficiario_institucion_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private BeneficiarioInstitucional beneficiarioInstitucion;

    /**
     * Campo que indica que almacena la información bancaria de un beneficiario
     * especial.
     */
    @JoinColumn(name = "beneficiario_especial_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private BeneficiarioEspecial beneficiarioEspecial;

    /**
     * Tipo de persona: Servidor / Beneficiario institucional / Beneficiario
     * especial.
     */
    @Transient
    private String tipoPersona;

    /**
     * Enum de tipo de cuentas
     */
    @Transient
    private TipoCuentaEnum tipoCuentaEnum;

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * @return the numerCuenta
     */
    public String getNumerCuenta() {
        return numerCuenta;
    }

    /**
     * @param numerCuenta the numerCuenta to set
     */
    public void setNumerCuenta(final String numerCuenta) {
        this.numerCuenta = numerCuenta;
    }

    /**
     * @return the tipoCuenta
     */
    public String getTipoCuenta() {
        return tipoCuenta;
    }

    /**
     * @param tipoCuenta the tipoCuenta to set
     */
    public void setTipoCuenta(final String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    /**
     * @return the pagoNomina
     */
    public Boolean getPagoNomina() {
        return pagoNomina;
    }

    /**
     * @param pagoNomina the pagoNomina to set
     */
    public void setPagoNomina(final Boolean pagoNomina) {
        this.pagoNomina = pagoNomina;
    }

    /**
     * @return the banco
     */
    public Banco getBanco() {
        return banco;
    }

    /**
     * @param banco the banco to set
     */
    public void setBanco(final Banco banco) {
        this.banco = banco;
    }

    /**
     * @return the bancoId
     */
    public Long getBancoId() {
        return bancoId;
    }

    /**
     * @param bancoId the bancoId to set
     */
    public void setBancoId(final Long bancoId) {
        this.bancoId = bancoId;
    }

    /**
     * @return the servidor
     */
    public Servidor getServidor() {
        return servidor;
    }

    /**
     * @param servidor the servidor to set
     */
    public void setServidor(final Servidor servidor) {
        this.servidor = servidor;
    }

    /**
     * @return the servidorId
     */
    public Long getServidorId() {
        return servidorId;
    }

    /**
     * @param servidorId the servidorId to set
     */
    public void setServidorId(final Long servidorId) {
        this.servidorId = servidorId;
    }

    /**
     * @return the beneficiarioInstitucion
     */
    public BeneficiarioInstitucional getBeneficiarioInstitucion() {
        return beneficiarioInstitucion;
    }

    /**
     * @param beneficiarioInstitucion the beneficiarioInstitucion to set
     */
    public void setBeneficiarioInstitucion(BeneficiarioInstitucional beneficiarioInstitucion) {
        this.beneficiarioInstitucion = beneficiarioInstitucion;
    }

    /**
     * @return the beneficiarioEspecial
     */
    public BeneficiarioEspecial getBeneficiarioEspecial() {
        return beneficiarioEspecial;
    }

    /**
     * @param beneficiarioEspecial the beneficiarioEspecial to set
     */
    public void setBeneficiarioEspecial(BeneficiarioEspecial beneficiarioEspecial) {
        this.beneficiarioEspecial = beneficiarioEspecial;
    }

    /**
     * @return the tipoPersona
     */
    public String getTipoPersona() {
        return tipoPersona;
    }

    /**
     * @param tipoPersona the tipoPersona to set
     */
    public void setTipoPersona(String tipoPersona) {
        this.tipoPersona = tipoPersona;
    }

    public TipoCuentaEnum getTipoCuentaEnum() {
        return tipoCuentaEnum;
    }

    public void setTipoCuentaEnum(TipoCuentaEnum tipoCuentaEnum) {
        this.tipoCuentaEnum = tipoCuentaEnum;
    }
}
