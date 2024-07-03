package edu.utvt.attendance.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import edu.utvt.attendance.entities.Persona;
import edu.utvt.attendance.service.PersonaService;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

@RestController
@RequestMapping("/personas")
public class PersonaController {
    @Autowired
    private PersonaService personaService;

    @GetMapping
    public List<Persona> getAllPersonas() {
        return personaService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Persona> getPersonaById(@PathVariable UUID id) {
        Optional<Persona> persona = personaService.findById(id);
        return persona.map(new Function<Persona, ResponseEntity<Persona>>() {
            @Override
            public ResponseEntity<Persona> apply(Persona p) {
                return ResponseEntity.ok(p);
            }
        }).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<Persona> getPersonaByNombre(@PathVariable String nombre) {
        Optional<Persona> persona = personaService.findByNombre(nombre);
        return persona.map(new Function<Persona, ResponseEntity<Persona>>() {
            @Override
            public ResponseEntity<Persona> apply(Persona p) {
                return ResponseEntity.ok(p);
            }
        }).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Persona createPersona(@Valid @RequestBody Persona persona) {
        return personaService.save(persona);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Persona> updatePersona(@PathVariable UUID id, @Valid @RequestBody Persona personaDetails) {
        Optional<Persona> persona = personaService.findById(id);
        return persona.map(new Function<Persona, ResponseEntity<Persona>>() {
            @Override
            public ResponseEntity<Persona> apply(Persona p) {
                p.setNombre(personaDetails.getNombre());
                p.setEdad(personaDetails.getEdad());
                p.setFechaNacimiento(personaDetails.getFechaNacimiento());
                Persona updatedPersona = personaService.save(p);
                return ResponseEntity.ok(updatedPersona);
            }
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePersona(@PathVariable UUID id) {
        Optional<Persona> persona = personaService.findById(id);
        return persona.map(new Function<Persona, ResponseEntity<Void>>() {
            @Override
            public ResponseEntity<Void> apply(Persona p) {
                personaService.deleteById(id);
                return ResponseEntity.ok().build();
            }
        }).orElse(ResponseEntity.notFound().build());
    }
}
