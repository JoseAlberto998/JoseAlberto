package com.mycompany.clinicaveterinariajosealberto.controller;

import com.mycompany.clinicaveterinariajosealberto.dto.DtoIngreso;
import com.mycompany.clinicaveterinariajosealberto.dto.DtoIngresoRegistro;
import com.mycompany.clinicaveterinariajosealberto.dto.DtoIngresoUpdate;
import com.mycompany.clinicaveterinariajosealberto.model.Ingreso;
import com.mycompany.clinicaveterinariajosealberto.model.Mascota;
import com.mycompany.clinicaveterinariajosealberto.serviceImpl.IngresoServiceImpl;
import com.mycompany.clinicaveterinariajosealberto.serviceImpl.MascotaServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Jose Alberto
 * @Version 1.0
 */
@RestController
@RequestMapping("hospitalclinicovet-api/ingreso")
@Validated
public class IngresoController {

    @Autowired
    IngresoServiceImpl ingresoService;

    @Autowired
    MascotaServiceImpl mascotaService;

    @Operation(summary = "List all ingresos", description = "This endpoint list all insgresos.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ok", content = @Content(schema = @Schema(implementation = DtoIngreso.class))),
        @ApiResponse(responseCode = "204", description = "No Content")
    })

    @GetMapping()
    public ResponseEntity<List<DtoIngreso>> listAllIngresos() {

        List<DtoIngreso> ingresos = ingresoService.trasformToIngresoDTO(ingresoService.listAllIngresos());

        if (!ingresos.isEmpty()) {
            return ResponseEntity.ok(ingresos);
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

    }

    @Operation(summary = "Create a new Ingreso", description = "This endpoint creates a new Ingreso.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Created", content = @Content(schema = @Schema(implementation = Ingreso.class))),
        @ApiResponse(responseCode = "404", description = "Mascota not found", content = @Content(schema = @Schema(implementation = String.class))),
        @ApiResponse(responseCode = "409", description = "Conflict", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @PostMapping()
    public ResponseEntity<?> createIngreso(@Valid @RequestBody DtoIngresoRegistro ingreso) {

        /*Comprobación si existe la mascota asignada al ingreso*/
        Mascota mascota = mascotaService.listById(ingreso.getId_mascota());
        if (mascota == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Mascota not found");
        }

        /*Comprobación dni*/
        if (!mascota.getDni_responsable().equals(ingreso.getDniPersona())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error Dni del responsable de la mascota no coincide con el dni de la persona que realiza el ingreso");
        }
        if (mascota.isInactive() == true) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        Ingreso in = new Ingreso(ingreso.getFecha_ingreso(), ingreso.getDniPersona(), mascota);
        Ingreso createdIngreso = ingresoService.addIngreso(in);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdIngreso);
    }

    @Operation(summary = "Update Ingreso", description = "This endpoint update Ingreso.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ok", content = @Content(schema = @Schema(implementation = Ingreso.class))),
        @ApiResponse(responseCode = "400", description = "Json invalid", content = @Content(schema = @Schema(implementation = String.class))),
        @ApiResponse(responseCode = "404", description = "Mascota or ingreso not found", content = @Content(schema = @Schema(implementation = String.class))),
        @ApiResponse(responseCode = "400", description = "Status FINALIZADO and date out is null", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @PutMapping("/{idMascota}/{idIngreso}")
    public ResponseEntity<Ingreso> updateIngreso(@PathVariable("idMascota") int idMascota, @PathVariable("idIngreso") int idIngreso, @RequestBody DtoIngresoUpdate ingresodto) {

        if (ingresodto.getEstado() == null && ingresodto.getFecha_finalizacion() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        Mascota checkMascota = mascotaService.listById(idMascota);
        Ingreso checkIngreso = ingresoService.listById(idIngreso);

        if (checkMascota == null || checkIngreso == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        if (!checkIngreso.getMascota().getId_mascota().equals(checkMascota.getId_mascota())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        if (ingresodto.getEstado() != null) {
            if (ingresodto.getEstado().equals(Ingreso.Estado.FINALIZADO)) {
                if (checkIngreso.getFecha_finalizacion() == null && ingresodto.getFecha_finalizacion() == null) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
                }
            }
        }
        if (ingresodto.getEstado() != null) {
            checkIngreso.setEstado(ingresodto.getEstado());
        }
        if (ingresodto.getFecha_finalizacion() != null) {
            checkIngreso.setFecha_finalizacion(ingresodto.getFecha_finalizacion());
        }

        return ResponseEntity.ok(ingresoService.updateIngreso(checkIngreso));

    }

    @Operation(summary = "Delete Ingreso", description = "This endpoint delete Ingreso (status ANULADO but dont delete in database).")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ok", content = @Content(schema = @Schema(implementation = Ingreso.class))),
        @ApiResponse(responseCode = "404", description = "Ingreso not found", content = @Content(schema = @Schema(implementation = String.class)))
    })

    @DeleteMapping("/{idIngreso}")
    public ResponseEntity deleteIngreso(@PathVariable("idIngreso") int idIngreso) {
        Ingreso ingreso = ingresoService.listById(idIngreso);
        if (ingreso == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else if (ingreso.getEstado().equals(Ingreso.Estado.ANULADO)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } else {
            ingresoService.deleteIngreso(ingreso);
        }

        return ResponseEntity.ok("delete successful");
    }

}
