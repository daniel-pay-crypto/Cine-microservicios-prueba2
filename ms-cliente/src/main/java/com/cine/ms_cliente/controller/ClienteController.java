package com.cine.ms_cliente.controller;

//import com.cine.ms_cliente.assembler.ClienteModelAssembler;
import com.cine.ms_cliente.dto.ClienteDetalleDTO;
import com.cine.ms_cliente.model.Cliente;
import com.cine.ms_cliente.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation; //eye/esta
import io.swagger.v3.oas.annotations.tags.Tag; //eye/esta
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse; //eye
import io.swagger.v3.oas.annotations.responses.ApiResponses; //eye
import jakarta.validation.Valid;
import org.slf4j.Logger; //eye
import org.slf4j.LoggerFactory; //eye
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel; //eye
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder; //eye 
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/v1/clientes")
@Tag(name = "Clientes", description = "Operaciones de los clientes del cine")
public class ClienteController {

    private static final Logger log = LoggerFactory.getLogger(ClienteController.class); //esta linea verificar
    
    @Autowired
    private ClienteService service;

    //Metodo POST para simplemente crear un nuevo cliente a nuestro sistema.
    @PostMapping
    @Operation(summary = "Crear un nuevo cliente", description = "Guarda un cliente en la base de datos local")
        @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "¡¡Cliente creado exitosamente!!"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida. favor de intentar denuevo...")
        })

    public ResponseEntity<Cliente> crear(@Valid @RequestBody Cliente cliente) {
        log.info("Endpoint POST /api/v1/clientes consumido");
        Cliente creado = service.guardar(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    //Metodo GET para obtener el detalle del cliente junto con sus tickets.
    @GetMapping("/{id}/tickets")
    @Operation(summary = "Obtener detalle del cliente", description = "Retorna el cliente con su lista de tickets comprados comunicándose con ms-tickets")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
        @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })

//El @Parameter es para documentar el parametro que se le pasa al endpoint, en este caso el id del cliente para que quede claro.
    public ResponseEntity<EntityModel<ClienteDetalleDTO>> obtenerDetalle(@Parameter(description = "ID de el cliente a buscar", example = "1") @PathVariable Long id) {
        log.info("Endpoint GET /api/v1/clientes/{}/tickets consumido", id);

        try {
        ClienteDetalleDTO detalle = service.obtenerClienteConTickets(id);

        //HATEOAS genera el link para la  navegación
        EntityModel<ClienteDetalleDTO> recurso = EntityModel.of(detalle);
        recurso.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ClienteController.class).obtenerDetalle(id)).withSelfRel());
       
        return ResponseEntity.ok(recurso);
        } catch (RuntimeException ex){
            //aca manejo el error en el caso de que cliente no exista
            throw ex;
        }
    }
}