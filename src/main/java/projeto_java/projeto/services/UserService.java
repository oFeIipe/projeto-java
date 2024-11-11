package projeto_java.projeto.services;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projeto_java.projeto.entidades.Usuario;
import projeto_java.projeto.infra.UserAlreadyExists;
import projeto_java.projeto.repositories.UserRepository;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Getter
    private Usuario usuarioLogado;

    public Usuario register(Usuario usuario) {
        if(jaExisteUsuario(usuario.getEmail())){
            throw new UserAlreadyExists();
        }
        return userRepository.save(usuario);
    }

    public boolean jaExisteUsuario(String email){
        return userRepository.findByEmail(email).isPresent();
    }

    public boolean login(String email, String password){
        Optional<Usuario> user = userRepository.findByEmail(email);
        if(user.isPresent() && user.get().getPassword().equals(password)){
             usuarioLogado = user.get();
            return true;
        }
        return false;
    }

    public void logOut(){
        usuarioLogado = null;
    }
}
