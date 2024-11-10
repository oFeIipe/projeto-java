package projeto_java.projeto.controllers;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import projeto_java.projeto.dto.PostDTO;
import projeto_java.projeto.entidades.Post;
import projeto_java.projeto.entidades.Usuario;
import projeto_java.projeto.infra.ExceptionHandler;
import projeto_java.projeto.repositories.PostRepository;
import projeto_java.projeto.repositories.UserRepository;
import projeto_java.projeto.services.PostService;
import projeto_java.projeto.services.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> createPost(@RequestBody Post post){
        Usuario usuarioLogado = userService.getUsuarioLogado();

        if(usuarioLogado == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Faça login para poder fazer postagens!");
        }



        post.setUsuario(usuarioLogado);
        Post novoPost = postService.createPost(post);
        return new ResponseEntity<>(novoPost, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity <List<Post>> getAllPosts(){
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @PutMapping
    @Transactional
    public ResponseEntity editPost(@RequestBody PostDTO post){
        Usuario usuarioLogado = userService.getUsuarioLogado();
        if(usuarioLogado == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Faça login para poder editar postagens!");
        }

        Optional<Post> optionalPost = postRepository.findById(post.id());
        if (optionalPost.isPresent()) {
            Post postEditado = optionalPost.get();
            postEditado.setConteudo(post.conteudo());
            return ResponseEntity.ok(post);
        } else {
            throw new EntityNotFoundException();
        }
    }
}
