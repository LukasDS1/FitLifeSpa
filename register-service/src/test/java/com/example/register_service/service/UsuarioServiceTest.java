package com.example.register_service.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.register_service.model.Rol;
import com.example.register_service.model.Usuario;
import com.example.register_service.repository.RolRepository;
import com.example.register_service.repository.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private RolRepository rolRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioService usuarioService;

    //Aqui verificamos que al momento de encontrar todos los usuarios el metodo alojado en el servicio se comporte de igual manera como lo haria el repositorio
    @Test 
    void findAll_returnsListFromRepository(){
        List<Usuario> usuarios = Arrays.asList(new Usuario(1L, "test@gmail.com", "test1", "test1", "test1", "test1", "test1", "test1", null),
        new Usuario(3L, "test1@gmail.com", "test2", "test2", "test2", "test2", "test2", "test2", null));

        when(usuarioRepository.findAll()).thenReturn(usuarios);

        List<Usuario> resultado = usuarioService.findAll();

        assertThat(resultado).isEqualTo(usuarios);

    }

    //Aqui verificamos que al momento de encontrar todos los usuarios el metodo alojado en el servicio se comporte de igual manera como lo haria el repositorio
    @Test
    void findById_returnsUserByID(){
        Usuario usuario = new Usuario(1L, "test@gmail.com", "test1", "test1", "test1", "test1", "test1", "test1", null);

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        Usuario resultado = usuarioService.findByID(1L);

        assertThat(resultado).isEqualTo(usuario);
    }

    //Aqui verificamos que al momento de encontrar  los usuarios por el email el metodo alojado en el servicio se comporte de igual manera como lo haria el repositorio
    @Test
    void findByEmail_returnsUserByEmail(){
        Usuario usuario = new Usuario(1L, "test@gmail.com", "test1", "test1", "test1", "test1", "test1", "test1", null);

        when(usuarioRepository.findByEmail("test@gmail.com")).thenReturn(usuario);

        Usuario resultado = usuarioService.getByMail("test@gmail.com");

        assertThat(resultado).isEqualTo(usuario);
    }

    
    //Aqui verificamos que al momento de encontrar  los usuarios por el email el metodo alojado en el servicio se comporte de igual manera como lo haria el repositorio
    @Test
    void deleteById_itsInvoked(){
        Long idUsuario = 1L;
        
        Usuario usuario = new Usuario(1L, "test@gmail.com", "test1", "test1", "test1", "test1", "test1", "test1", null);
        
        when(usuarioRepository.findById(idUsuario)).thenReturn(Optional.of(usuario));

        usuarioService.deleteByid(idUsuario);

        verify(usuarioRepository).delete(usuario);
    }

    //aqui verificamos que al guarde el usuario de manera correcta
    @Test
    void saveUser_saveusers(){
    Rol rol = new Rol(1L, "oal", null);
    
    when(rolRepository.findById(rol.getIdRol())).thenReturn(Optional.of(rol));

    Usuario usuario = new Usuario(1L, "test@gmail.com", "test1", "test1", "test1", "test1", "test1", "test1", rol);

    when(passwordEncoder.encode("test1")).thenReturn("encrypt");

    Usuario usuarioGuardado = new Usuario(1L, "test@gmail.com", "encrypt", "test1", "test1", "test1", "test1", "test1", null);

    when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioGuardado);

    Usuario resultado = usuarioService.saveUsuario(usuario);

    verify(passwordEncoder).encode("test1");

    assertThat(resultado.getEmail()).isEqualTo("test@gmail.com");
    assertThat(resultado.getPassword()).isEqualTo("encrypt");
}

    @Test
    public void testUpdateUserById() {

    Usuario usuario = new Usuario(1L, "test@gmail.com", "test1", "test1", "test1", "test1", "test1", "test1", null);
    when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

    Usuario newusuario = new Usuario(1L, "test2@gmail.com", "test1", "test1", "test1", "test1", "test1", "test1", null);
    
    when(usuarioRepository.save(any(Usuario.class))).thenReturn(newusuario);

    Usuario resultado = usuarioService.UpdateUserById(newusuario);
    verify(usuarioRepository).save(any(Usuario.class));

    assertEquals("test2@gmail.com", resultado.getEmail());
  

}


  






    

}
