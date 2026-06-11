package com.cine.ms_cliente.service;

import com.cine.ms_cliente.dto.ClienteDetalleDTO;
import com.cine.ms_cliente.dto.TicketDTO;
import com.cine.ms_cliente.model.Cliente;
import com.cine.ms_cliente.repository.ClienteRepository;
import net.datafaker.Faker;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import reactor.core.publisher.Mono;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;

// IMPORTACIONES EXPLÍCITAS
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private WebClient.Builder webClientBuilder;

    @Mock
    private WebClient webClient;

    @SuppressWarnings("rawtypes")
    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @SuppressWarnings("rawtypes")
    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    @InjectMocks
    private ClienteService clienteService;

    private Faker faker;

    @BeforeEach
    void setUp() {
        // Inicializamos DataFaker para antes de cada prueb debido a que es un objeto pesado.
        faker = new Faker();
    }

    @Test
    void testObtenerClienteConTickets() {
        // GIVEN: Creamos nuestros datos simulados con DataFaker
        Long idSimulado = faker.number().randomNumber();
        String nombreAleatorio = faker.name().firstName();
        String apellidoAleatorio = faker.name().lastName();

        Cliente clienteFalso = new Cliente();
        clienteFalso.setId(idSimulado);
        clienteFalso.setNombre(nombreAleatorio);
        clienteFalso.setApellido(apellidoAleatorio);
        clienteFalso.setEmail(faker.internet().emailAddress());

        // Entrenamos al Mock: Cuando el repositorio busque este ID, responderá con nuestro cliente falso
        when(clienteRepository.findById(idSimulado)).thenReturn(Optional.of(clienteFalso));

        // Entrenamos al Mock del WebClient para que no intente conectarse de verdad
        when(webClientBuilder.build()).thenReturn(webClient);
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);

        // Simulamos un ticket falso usando Faker
        TicketDTO ticketFalso = new TicketDTO();
        ticketFalso.setId(faker.number().randomNumber());
        ticketFalso.setAsiento(faker.letterify("?#")); // Ejemplo: A1, B2
        ticketFalso.setPrecio((double) faker.number().numberBetween(1000, 10000));
        TicketDTO[] ticketsArray = {ticketFalso};

        when(responseSpec.bodyToMono(TicketDTO[].class)).thenReturn(Mono.just(ticketsArray));

        // WHEN: Ejecutamos la acción del servicio que queremos evaluar
        ClienteDetalleDTO resultado = clienteService.obtenerClienteConTickets(idSimulado);

        // THEN: Validamos que las compuertas de datos funcionen de forma idónea
        assertNotNull(resultado, "El DTO resultante no debería ser nulo");
        assertEquals(nombreAleatorio + " " + apellidoAleatorio, resultado.getNombreCompleto(), "El nombre transformado al DTO debe coincidir con el de la DB");
        assertEquals(1, resultado.getTickets().size(), "Debe contener exactamente 1 ticket simulado");

        // Verificamos que el servicio realmente haya consultado al repositorio exactamente 1 vez
        verify(clienteRepository, times(1)).findById(idSimulado);
    }

    @Test
    void testBuscarPorId_ClienteNoEncontrado() {
        // GIVEN: Un ID que no existe
        Long idInexistente = faker.number().randomNumber();
        when(clienteRepository.findById(idInexistente)).thenReturn(Optional.empty());

        // WHEN & THEN: Verificamos que explote con el mensaje correcto
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            clienteService.buscarPorId(idInexistente);
        });

        assertEquals("Cliente no encontrado.", exception.getMessage(), "El mensaje de error debe ser exacto");
        verify(clienteRepository, times(1)).findById(idInexistente);
    }

    @Test
    void testGuardarCliente() {
        // GIVEN
        Cliente clienteNuevo = new Cliente();
        clienteNuevo.setNombre(faker.name().firstName());
        clienteNuevo.setApellido(faker.name().lastName());
        clienteNuevo.setEmail(faker.internet().emailAddress());

        when(clienteRepository.save(clienteNuevo)).thenReturn(clienteNuevo);

        // WHEN
        Cliente resultado = clienteService.guardar(clienteNuevo);

        // THEN
        assertNotNull(resultado, "El cliente guardado no debe ser nulo");
        assertEquals(clienteNuevo.getNombre(), resultado.getNombre());
        verify(clienteRepository, times(1)).save(clienteNuevo);
    }
}