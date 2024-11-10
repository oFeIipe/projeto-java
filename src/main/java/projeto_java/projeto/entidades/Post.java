package projeto_java.projeto.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Post {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String conteudo;

    private LocalDateTime dataCriacao = LocalDateTime.now();

    @ManyToOne
    private Usuario usuario;
}
