package edu.unimagdalena.jpa.entities;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import jakarta.persistence.*;

import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Partidas")
public class Partida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPartida;

    @Column(nullable = false)
    private String creador;

    @Column(nullable = false)
    private String deporte;

    @Column(nullable = false)
    private String ciudad;

    @Column(nullable = false)
    private String provincia;

    @Temporal(TemporalType.DATE)
    private LocalDate fecha;

    @Temporal(TemporalType.TIME)
    private LocalTime horaComienzo;

    @Temporal(TemporalType.TIME)
    private LocalTime horaFinal;

    @Column(nullable = false)
    @ManyToMany
    @JoinTable(name = "usuarios_partidas", joinColumns = @JoinColumn(name = "idPartida", referencedColumnName = "idPartida"), inverseJoinColumns = @JoinColumn(name = "idUsuario", referencedColumnName = "idUsuario"))
    private List<Usuario> participantes;

    @Column(nullable = false)
    private String comentarios;
    @Column(nullable = false)
    private Integer suplentes;

}
