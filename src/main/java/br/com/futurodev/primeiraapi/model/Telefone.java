package br.com.futurodev.primeiraapi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
@Getter
@Setter
@Entity
@Table(name = "telefone")
public class Telefone implements Serializable {

    @EqualsAndHashCode.Include

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numero;

    private String tipo;

    //private Long idUsuario;

    @ManyToOne
    @JoinColumn(name = "idUsuario", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_usuario"))
    @JsonBackReference
    private Usuario usuario;
}
