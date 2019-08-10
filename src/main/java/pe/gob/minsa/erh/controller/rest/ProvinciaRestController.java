package pe.gob.minsa.erh.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.gob.minsa.erh.converter.ProvinciaConverter;
import pe.gob.minsa.erh.model.dto.UbiProvinciaDto;
import pe.gob.minsa.erh.model.entity.UbiProvinciaEntity;
import pe.gob.minsa.erh.service.ProvinciaService;

import java.util.List;

@RestController
@RequestMapping(value = "/api/provincias")
public class ProvinciaRestController {

    @Autowired
    private ProvinciaService provinciaService;
    @Autowired
    private ProvinciaConverter converter;

    @GetMapping(value = "/entities", produces = "application/json")
    public List<UbiProvinciaEntity> getEntities() {

        return provinciaService.listAll();
    }

    @GetMapping(value = "/dtos", produces = "application/json")
    public List<UbiProvinciaDto> getDtos() throws Exception {

        return converter.toListDto(provinciaService.listAll());
    }

}
