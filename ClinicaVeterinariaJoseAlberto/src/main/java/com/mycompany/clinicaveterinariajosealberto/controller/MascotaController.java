
package com.mycompany.clinicaveterinariajosealberto.controller;

import com.mycompany.clinicaveterinariajosealberto.dto.DtoMascotaRegistro;
import com.mycompany.clinicaveterinariajosealberto.model.Ingreso;
import com.mycompany.clinicaveterinariajosealberto.model.Mascota;
import com.mycompany.clinicaveterinariajosealberto.serviceImpl.MascotaServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Jose Alberto
 * @Version 1.0
 */
@RestController
@RequestMapping("hospitalclinicovet-api/mascota")
@Validated
public class MascotaController {
    
    @Autowired
    MascotaServiceImpl mascotaService;
    
   
     @Operation(summary = "Search mascota by id", description = "This endpoint search mascota by id.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ok", content = @Content(schema = @Schema(implementation = Mascota.class))),
        @ApiResponse(responseCode = "404", description = "Mascota not found"),
    })
    @GetMapping("/{idMascota}")
    public ResponseEntity<Mascota> listMascotaById(@PathVariable("idMascota") int id_mascota) {
         Mascota mascota = mascotaService.listById(id_mascota);
        if(mascota == null)
             return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        
        return ResponseEntity.ok(mascota);
    }
    @Operation(summary = "Search all ingresos by mascota id", description = "This endpoint search all ingresos by mascota id.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ok", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Ingreso.class)))),
        @ApiResponse(responseCode = "404", description = "Mascota not found"),
        @ApiResponse(responseCode = "204", description = "No Content"),
    })
    @GetMapping("/{idMascota}/ingreso")
    public ResponseEntity<?> listIngresosByMascotaId(@PathVariable("idMascota") int id_mascota) {
        Mascota mascota = mascotaService.listById(id_mascota);
        if(mascota == null)
             return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        if(!mascota.getIngresosList().isEmpty())
        {
            return ResponseEntity.ok(mascota.getIngresosList());
        }
        else
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    
    @Operation(summary = "Create a new mascota", description = "This endpoint create a new mascota.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Created", content = @Content(schema = @Schema(implementation = Mascota.class))),
        @ApiResponse(responseCode = "400", description = "Bad request"),
    })
    
    @PostMapping()
    public ResponseEntity<Mascota> createMascota(@Valid @RequestBody DtoMascotaRegistro mascotadto) {
        Mascota mascota = mascotaService.transformDtoToMascota(mascotadto);
       return ResponseEntity.status(HttpStatus.CREATED).body(mascotaService.addMascota(mascota));
    }
    @Operation(summary = "Delete mascota", description = "This endpoint delete mascota only set inactive but not drop in database.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "404", description = "Not found"),
    })
    @DeleteMapping("/{idMascota}")
    public ResponseEntity setInactiveMascota(@PathVariable("idMascota") int id_mascota) {
        Mascota mascota = mascotaService.listById(id_mascota);
        if(mascota == null)
             return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        else if (mascota.isInactive() == true)
             return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        else
            mascotaService.deleteMascota(mascota);
        
        return ResponseEntity.ok("delete successful");
    }
    
}
