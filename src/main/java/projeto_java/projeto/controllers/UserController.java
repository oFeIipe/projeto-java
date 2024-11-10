package projeto_java.projeto.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projeto_java.projeto.dto.LoginDTO;
import projeto_java.projeto.entidades.Usuario;
import projeto_java.projeto.infra.ExceptionHandler;
import projeto_java.projeto.repositories.UserRepository;
import projeto_java.projeto.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/get")
    public ResponseEntity <List<Usuario>> getAllUsers(){
        return ResponseEntity.ok(userRepository.findAll());
    }

    @PostMapping("/register")
    public ResponseEntity<Usuario> register(@RequestBody Usuario user) {

        //if (userRepository.findByEmail(user.getUsername()).isPresent()) {
        //    throw new ExceptionHandler("Usuário já existente");
        //}
        Usuario novoUsuario = userService.register(user);
        return new ResponseEntity<>(novoUsuario, HttpStatus.CREATED);

    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO user){
        if(userService.login(user.email(), user.password())){
            return ResponseEntity.ok("Login feito com sucesso");
        }
        else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email ou senha inválidos");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logOut(){
        userService.logOut();
        return ResponseEntity.ok("LogOut feito com sucesso");
    }


}
