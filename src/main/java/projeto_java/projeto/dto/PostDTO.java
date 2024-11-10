package projeto_java.projeto.dto;

import projeto_java.projeto.entidades.Usuario;

import java.time.LocalDateTime;

public record PostDTO(
        String id,

        String conteudo,

        LocalDateTime dataCriacao,

        Usuario usuario) {
}
