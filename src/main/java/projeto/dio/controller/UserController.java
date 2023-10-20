package projeto.dio.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import projeto.dio.controller.dto.UserDto;
import projeto.dio.domain.model.User;
import projeto.dio.service.UserService;

@CrossOrigin
@RestController
@RequestMapping("/users")
@Tag(name = "Controle de Usuários", description = "RESTful API para gerenciamento de usuários.")
public record UserController(UserService userService) {

    @GetMapping
    @Operation(summary = "Obtenha todos os usuários", description = "Recuperar uma lista de todos os usuários registrados")
    @ApiResponses(value = { 
            @ApiResponse(responseCode = "200", description = "Operação bem sucedida")
    })
    public ResponseEntity<Object> findAll() {
        var users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtenha um usuário por ID", description = "Recuperar um usuário específico com base em seu ID")
    @ApiResponses(value = { 
            @ApiResponse(responseCode = "200", description = "Operação bem sucedida"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public ResponseEntity<UserDto> findById(@PathVariable Long id) {
        var user = userService.findById(id);
        return ResponseEntity.ok(new UserDto(user));
    }

    @PostMapping
    @Operation(summary = "Crie um novo usuário", description = "Crie um novo usuário e retorne os dados do usuário criado")
    @ApiResponses(value = { 
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de usuário inválidos fornecidos")
    })
    public ResponseEntity<UserDto> create(@RequestBody UserDto userDto) {
        var user = userService.create(userDto.toModel());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(user.getId())
                .toUri();
        return ResponseEntity.created(location).body(new UserDto(user));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um usuário", description = "Atualizar os dados de um usuário existente com base em seu ID")
    @ApiResponses(value = { 
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "422", description = "Dados de usuário inválidos fornecidos")
    })
    public ResponseEntity<UserDto> update(@PathVariable Long id, @RequestBody UserDto userDto) {
        var user = userService.update(id, userDto.toModel());
        return ResponseEntity.ok(new UserDto((User) user));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir um usuário", description = "Exclua um usuário existente com base em seu ID")
    @ApiResponses(value = { 
            @ApiResponse(responseCode = "204", description = "Usuário excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}