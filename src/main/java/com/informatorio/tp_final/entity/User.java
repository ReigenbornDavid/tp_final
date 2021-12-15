package com.informatorio.tp_final.entity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@NotEmpty(message = "El nombre no debe estar vacio")
    private String firstName;

    //@NotEmpty(message = "El apellido no debe estar vacio")
    private String lastName;

    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate creationDate;

    //@NotEmpty(message = "El email no debe estar vacio")
    @Pattern(regexp="^(.+)@(\\S+)$", message=("El formato del email es incorrecto"))
    @Column(unique = true)
    private String email;

    //@NotEmpty(message = "La contraseña no debe estar vacia")
    private String password;

    private String city;

    private String province;

    private String country;

    @Enumerated(value = EnumType.STRING)
    private Type type;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Startup> startups = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Vote> votes = new ArrayList<>();

    public User() {
    }
}
