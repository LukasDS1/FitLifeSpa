package com.example.membresia_service.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import com.example.membresia_service.model.Membresia;
import com.example.membresia_service.model.Plan;
import com.example.membresia_service.repository.MembresiaRepository;
import com.example.membresia_service.repository.PlanRepository;


@ExtendWith(MockitoExtension.class)
public class MembresiaServiceTest {

    @Mock
    private MembresiaRepository membresiaRepository;

    @Mock
    private PlanRepository planRepository;

    @Mock
    private RestTemplate restTemplate;

    @Spy
    @InjectMocks
    private MembresiaService membresiaService;

    

    //Aqui verificamos que todas las membresias sean listadas
    @Test
     void findAllMembresias_ReturnsList(){
        List<Membresia> membresias = Arrays.asList(new Membresia(1L, "membresia1", "membresia1", null, null),
        new Membresia(2L, "membresia2", "membresia2", null, null));

        when(membresiaRepository.findAll()).thenReturn(membresias);

        List<Membresia> resultado = membresiaService.getByAll();

        assertThat(resultado).isEqualTo(membresias);
    }

    //Aqui verificamos que se pueda listar una membresia por id
    @Test
    void findbyIDMembresia_returnsMembresia(){
        Membresia membresia = new Membresia(1L, "membresia1", "membresia1", null, null);

        when(membresiaRepository.findById(1L)).thenReturn(Optional.of(membresia));

        Optional<Membresia> resultado = membresiaService.findByid(1L);

        assertThat(resultado).contains(membresia);
    }

    //Aqui verificamos que al buscar por id del plan se listen todas las membresias asociadas al plan 
    @Test
    void findbyIDUsuario_returnsmembresia(){
        Plan plan = new Plan(1L, "plan1", "plan1", 3333, 3333, null);

        List<Membresia> membresias = Arrays.asList(new Membresia(1L, "membresia1", "membresia1", null, plan),
        new Membresia(2L, "membresia2", "membresia2", null, plan));

        when(membresiaRepository.findByPlan_IdPlan(1L)).thenReturn(membresias);

        List<Membresia> resultado = membresiaService.findMembresiasByPlanId(1L);

        assertThat(resultado).isEqualTo(membresias);

    }

    //Aqui verificamos que al buscar por id del usuario se listen todas las membresias asociadas al usuario 
    @Test
    void findbyIDuser_returnsMembresia(){
        Long idUsuario = 1L;

        List<Membresia> membresias = Arrays.asList(new Membresia(1L, "membresia1", "membresia1", List.of(1L), null),
        new Membresia(2L, "membresia2", "membresia2", List.of(1L), null));

        when(membresiaRepository.findByUsuarioId(idUsuario)).thenReturn(membresias);

        List<Membresia> result = membresiaService.findbyidUsuario(idUsuario);

        assertThat(result).isEqualTo(membresias);
    }

    //Aqui verificamos que al crar la membresia valide que el plan no sea null y el usuario exista
    @Test
    void saveMembresia_createMembresia(){
        Long idPlan = 1L;

        Long idUsuario = 2L;

        Plan plan = new Plan(idPlan, "plan1", "plan1", 3333, 3333, null);

        List<Long> usuarios = List.of(idUsuario);

        Membresia membresia = new Membresia(1L, "membresia1", "membresia1",usuarios, plan);

        when(planRepository.existsById(idPlan)).thenReturn(true);

        doReturn(true).when(membresiaService).validateUser(idUsuario);

        when(membresiaRepository.save(membresia)).thenReturn(membresia);

        Membresia resultado = membresiaService.saveMembresia(membresia);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getNombre()).isEqualTo("membresia1");
        verify(planRepository).existsById(idPlan);
        verify(membresiaRepository).save(membresia);
    }

    //Aqui verificamos que al eliminar la membresia sea invocado el metodo
    @Test
    void deleteMembresia_ItsInvoke(){
     Long idMembresia = 1L;

     Membresia membresia = new Membresia(idMembresia, "membresia1", "membresia1",null, null);

     when(membresiaRepository.findById(idMembresia)).thenReturn(Optional.of(membresia));

     membresiaService.deleteMembresia(idMembresia);

     verify(membresiaRepository).deleteById(idMembresia);
    }

    //Aqui verificamos la existencia del usuario mediante el restemplate
    @Test
    void validarUsuariowithRest() {
    Long idUsuario = 10L;

    Map<String, Object> usuarios = new HashMap<>();
    usuarios.put("id", idUsuario); 

    when(restTemplate.getForObject(
            "http://localhost:8082/api-v1/register/exists/{id}", Map.class,idUsuario )).thenReturn(usuarios);

    Boolean resultado = membresiaService.validarUsuario(idUsuario);

    assertThat(resultado).isTrue();
}
    //Aqui verificamos que al hacer update en la membresia se actualize
    @Test
    void updatemembresiabyId_returnequals() {
   
    Membresia membresia = new Membresia(1L, "membresia1", "membresia1", List.of(1L), new Plan());
    
    when(membresiaRepository.findById(1L)).thenReturn(Optional.of(membresia));
    
    Membresia updatemembresia = new Membresia(1L, "membresia2", "membresia1", List.of(1L), new Plan());

    when(membresiaRepository.save(any(Membresia.class))).thenReturn(updatemembresia);

    Membresia resultado = membresiaService.UpdateUserById(updatemembresia);

    verify(membresiaRepository).save(any(Membresia.class));

    assertEquals("membresia2", resultado.getNombre());

}

    //Aqui verificamos que al asignar un plan a la memrbesia se asigne
    @Test
    void assignPlanToMembership_returnsassignerplan() {
    Long idMembresia = 1L;
    Long idPlan = 2L;

    Plan plan = new Plan(idPlan, "plan1", "plan1", 3333, 3333, null);
    Membresia membresia = new Membresia(1L, "membresia1", "membresia1", null,plan);
    
    when(membresiaRepository.findById(idMembresia)).thenReturn(Optional.of(membresia));

    when(planRepository.findById(idPlan)).thenReturn(Optional.of(plan));

    when(membresiaRepository.save(any())).thenReturn(membresia);

    Membresia resultado = membresiaService.assignPlanToMembership(idMembresia, idPlan);

    assertThat(resultado.getPlan()).isEqualTo(plan);
}

    //Aqui verificamos que al asignar un usuario se asigne
   @Test
    void assignUsuarioToMembership_returnassigneduser() {
    Long idMembresia = 1L;
    Long idUsuario = 10L;

    Membresia membresia = new Membresia(idMembresia, "membresia1", "membresia1", new ArrayList<>(), null);

    when(membresiaRepository.findById(idMembresia)).thenReturn(Optional.of(membresia));
    doReturn(true).when(membresiaService).validarUsuario(idUsuario);
    when(membresiaRepository.save(any())).thenReturn(membresia);

    Membresia resultado = membresiaService.assignUsuarioToMembership(idMembresia, idUsuario);

    assertThat(resultado.getIdUsuario()).contains(idUsuario);
}

    @Test
    void deleteUserFromMembresia_removesUserSuccessfully() {
    Long idMembresia = 1L;
    Long idUsuario = 10L;

    List<Long> usuarios = new ArrayList<>();
    usuarios.add(idUsuario);
    
    Membresia membresiaOriginal = new Membresia(idMembresia, "membresia1", "membresia1", usuarios, null);

    Membresia membresiaSinUsuario = new Membresia(idMembresia, "membresia1", "membresia1", new ArrayList<>(), null);

    when(membresiaRepository.findById(idMembresia)).thenReturn(Optional.of(membresiaOriginal));
    when(membresiaRepository.save(any(Membresia.class))).thenReturn(membresiaSinUsuario);

    doReturn(true).when(membresiaService).validarUsuario(idUsuario);

    Membresia result = membresiaService.deleteUsersFromMembresia(idMembresia, idUsuario);

    assertThat(result.getIdUsuario()).doesNotContain(idUsuario);
    verify(membresiaRepository).save(any(Membresia.class));
}

   











    



}
