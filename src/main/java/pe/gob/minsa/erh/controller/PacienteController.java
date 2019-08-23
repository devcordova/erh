package pe.gob.minsa.erh.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pe.gob.minsa.erh.converter.EnfermedadConverter;
import pe.gob.minsa.erh.converter.IpressConverter;
import pe.gob.minsa.erh.converter.PacienteConverter;
import pe.gob.minsa.erh.model.dto.PacienteDto;
import pe.gob.minsa.erh.model.entity.PacienteEntity;
import pe.gob.minsa.erh.model.enums.EstadoEnum;
import pe.gob.minsa.erh.model.enums.GeneroEnum;
import pe.gob.minsa.erh.model.enums.PerfilEnum;
import pe.gob.minsa.erh.service.EnfermedadService;
import pe.gob.minsa.erh.service.IpressService;
import pe.gob.minsa.erh.service.PacienteService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@Secured({"ROLE_MASTER", "ROLE_DIRECTOR", "ROLE_MEDICO", "ROLE_PACIENTE"})
@RequestMapping("/paciente")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;
    @Autowired
    private PacienteConverter pacienteConverter;

    @Autowired
    private IpressService ipressService;
    @Autowired
    private IpressConverter ipressConverter;

    @Autowired
    private EnfermedadService enfermedadService;
    @Autowired
    private EnfermedadConverter enfermedadConverter;

    @RequestMapping(method = RequestMethod.GET)
    public String listar(@RequestParam(value = "search", required = false) String search, Model model) throws Exception {
        model.addAttribute("titulo", "Paciente");
        model.addAttribute("opcion", "Búsqueda");

        if (StringUtils.isNotBlank(search)) {

            List<PacienteDto> pacientes  = new ArrayList<>();

            PacienteEntity entity = pacienteService.findPacienteEntityByNroDocumento(search);

            if(entity != null){
                pacientes.add(pacienteConverter.toDto(entity));
            }

            if (pacientes.size() > 0) {
                model.addAttribute("pacientes", pacientes);
                model.addAttribute("success",  String.format("Se encontró paciente con el documento nro %s", search));
            } else {
                model.addAttribute("warning", String.format("No se encontró paciente con el documento nro %s", search));
            }

        }

        return "paciente/listar";
    }

    @RequestMapping(value = "/mostrar/{id}", method = RequestMethod.GET)
    public String mostrar(@PathVariable(value = "id") Long id, Model model) throws Exception {
        model.addAttribute("titulo", "Paciente");
        model.addAttribute("opcion", "Mostrar");
        model.addAttribute("paciente", pacienteConverter.toDto(pacienteService.getById(id)));
        return "paciente/mostrar";
    }

    @Secured({"ROLE_MASTER", "ROLE_MEDICO"})
    @RequestMapping(value = "/editar/{id}", method = RequestMethod.GET)
    public String editar(@PathVariable(value = "id") Long id, Model model) throws Exception {
        model.addAttribute("titulo", "Paciente");
        model.addAttribute("opcion", "Editar");
        model.addAttribute("paciente", pacienteConverter.toDto(pacienteService.getById(id)));
        model.addAttribute("ipresses", ipressConverter.toListDto(ipressService.listAll()));
        return "paciente/formulario";
    }

    @Secured({"ROLE_MASTER", "ROLE_MEDICO"})
    @RequestMapping(value = "/nuevo", method = RequestMethod.GET)
    public String nuevo(Model model) throws Exception {
        model.addAttribute("titulo", "Paciente");
        model.addAttribute("opcion", "Nuevo");
        model.addAttribute("paciente", PacienteDto.builder()
                .genero(GeneroEnum.OTRO)
                .estado(EstadoEnum.ACTIVO)
                .fecRegistro(new SimpleDateFormat("dd-MM-yyyy").format(new Date()))
                .fecModificacion(new SimpleDateFormat("dd-MM-yyyy").format(new Date()))
                .perfil(PerfilEnum.DIRECTOR)
                .build());
        model.addAttribute("ipresses", ipressConverter.toListDto(ipressService.listAll()));
        return "paciente/formulario";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String saveOrUpdate(PacienteDto dto, Model model) throws Exception {
        PacienteEntity newEntity = pacienteService.saveOrUpdate(pacienteConverter.toEntity(dto));
        model.addAttribute("newEntity", pacienteConverter.toDto(newEntity));
        return "redirect:/paciente/";
    }

    @Secured({"ROLE_MASTER", "ROLE_MEDICO"})
    @RequestMapping(value = "/eliminar/{id}")
    public String delete(@PathVariable(value = "id") Long id) {
        pacienteService.delete(id);
        return "redirect:/paciente/";
    }

    @RequestMapping(value = "/{id}/enfermedad", method = RequestMethod.GET)
    public String listarEnfermedades(@PathVariable(value = "id") Long id, Model model) throws Exception {
        model.addAttribute("titulo", "Enfermedad");
        model.addAttribute("opcion", "Búsqueda");

        PacienteEntity entity = pacienteService.getById(id);
        model.addAttribute("enfermedades", enfermedadConverter.toListDto(enfermedadService.findEnfermedadEntitiesByPaciente(entity)));
        return "enfermedad/listar";
    }

    @RequestMapping(value = "/{id}/antecedentefamiliar", method = RequestMethod.GET)
    public String listarAntecedentesFamiliares(@PathVariable(value = "id") Long id, Model model) throws Exception {
        model.addAttribute("titulo", "Antecedente Familiar");
        model.addAttribute("opcion", "Búsqueda");

        PacienteEntity entity = pacienteService.getById(id);
        model.addAttribute("paciente", pacienteConverter.toDto(entity));
        return "antecedentefamiliar/listar";
    }

}
