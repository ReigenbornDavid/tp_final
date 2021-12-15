package com.informatorio.tp_final.entity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "startup")
@Getter
@Setter
public class Startup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private String content;

    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate creationDate;

    private Double income;

    private Boolean published;

    private String url;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy = "startup", cascade = CascadeType.ALL)
    private List<Tag> tags = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "startup", cascade = CascadeType.ALL)
    private List<Vote> votes = new ArrayList<>();

    @JsonIgnore
    @ManyToMany(mappedBy = "startups")
    private List<Event> events = new ArrayList<>();

    public Startup() {
    }
}
