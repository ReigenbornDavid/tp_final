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

    private String firstName;

    private String lastName;

    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate creationDate;

    @Pattern(regexp="^(.+)@(\\S+)$", message=("Formato Incorrecto"))
    @Column(unique = true, length = 45)
    private String email;

    private String password;

    private String city;

    private String province;

    private String country;

    @Enumerated(value = EnumType.STRING)
    private Type type;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Startup> startups = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Vote> votes = new ArrayList<>();

    public User() {
    }

    public User(Long id, String firstName, String lastName, LocalDate creationDate, String email, String password, String city, String province, String country, Type type) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.creationDate = creationDate;
        this.email = email;
        this.password = password;
        this.city = city;
        this.province = province;
        this.country = country;
        this.type = type;
    }
}
