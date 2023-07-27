package com.moonbox.inventory.controllers;

import com.moonbox.inventory.dto.MeasuringUnitRequestDto;
import com.moonbox.inventory.entities.MeasuringUnit;
import com.moonbox.inventory.repositories.MeasuringUnitRepository;
import eu.hoefel.unit.Unit;
import eu.hoefel.unit.UnitPrefix;
import eu.hoefel.unit.Units;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "/api/v1/units")
@AllArgsConstructor
public class MeasuringUnitsController {

    private final MeasuringUnitRepository measuringUnitRepository;

    @GetMapping
    public ResponseEntity<List<MeasuringUnit>> getUoms() {
        return ResponseEntity.ok(measuringUnitRepository.findAll());
    }

    @GetMapping(value = "/supported")
    public ResponseEntity<Set<Unit>> getSupportedUnits() {
        return ResponseEntity.ok(Units.DEFAULT_UNITS);
    }

    @GetMapping(value = "/prefixes/supported")
    public ResponseEntity<Set<UnitPrefix>> getSupportedPrefixes() {
        return ResponseEntity.ok(Units.DEFAULT_PREFIXES);
    }

    @PostMapping
    public ResponseEntity<MeasuringUnit> insertUom(
            @RequestBody @Valid MeasuringUnitRequestDto dto) {

        MeasuringUnit unitToSave = new MeasuringUnit();
        unitToSave.setName(dto.getName());
        unitToSave.setSymbol(dto.getSymbol());
        unitToSave.setDescription(dto.getDescription());

        return ResponseEntity.ok(measuringUnitRepository.save(unitToSave));
    }
}
