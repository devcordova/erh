package pe.gob.minsa.erh.converter;

import org.joda.time.DateTime;
import org.joda.time.Years;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pe.gob.minsa.erh.common.AbstractConverter;
import pe.gob.minsa.erh.model.dto.AntecedenteFamiliarDto;
import pe.gob.minsa.erh.model.entity.AntecedenteFamiliarEntity;
import pe.gob.minsa.erh.model.enums.PerfilEnum;
import pe.gob.minsa.erh.service.AntecedenteFamiliarService;
import pe.gob.minsa.erh.service.DocumentoService;
import pe.gob.minsa.erh.service.ParentescoService;
import pe.gob.minsa.erh.service.UbiDistritoService;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class AntecedenteFamiliarConverter extends AbstractConverter<AntecedenteFamiliarEntity, AntecedenteFamiliarDto> {

    @Autowired
    private DocumentoConverter documentoConverter;

    @Autowired
    private UbiDistritoConverter ubiDistritoConverter;

    @Autowired
    private ParentescoConverter parentescoConverter;

    @Autowired
    private AntecedenteFamiliarService antecedenteFamiliarService;

    @Autowired
    private DocumentoService documentoService;

    @Autowired
    private UbiDistritoService ubiDistritoService;

    @Autowired
    private ParentescoService parentescoService;


    @Override
    protected AntecedenteFamiliarDto entityToDto(AntecedenteFamiliarEntity entity) throws Exception {
        return AntecedenteFamiliarDto.builder()
                .id(entity.getId())

                .nombre(entity.getNombre())
                .apePaterno(entity.getApePaterno())
                .apeMaterno(entity.getApeMaterno())
                .fecNacimiento(new SimpleDateFormat("dd-MM-yyyy").format(entity.getFecNacimiento()))
                .edad(Years.yearsBetween(new DateTime(entity.getFecNacimiento()), new DateTime()).getYears())
                .documento(documentoConverter.toDto(entity.getDocumento()))
                .nroDocumento(entity.getNroDocumento())
                .genero(entity.getGenero())
                .rutaImagen(entity.getRutaImagen())
                .distritoNacimiento(ubiDistritoConverter.toDto(entity.getDistritoNacimiento()))
                .condicion(entity.getCondicion())
                .origenNacionalidad(entity.getOrigenNacionalidad())
                .origenPais(entity.getOrigenPais())
                .origenEstado(entity.getOrigenEstado())
                .origenCiudad(entity.getOrigenCiudad())
                .lugarNacimiento(entity.getLugarNacimiento())
                .email(entity.getEmail())
                .distritoResidencia(ubiDistritoConverter.toDto(entity.getDistritoResidencia()))
                .direccionActual(entity.getDireccionActual())
                .lugarProcedencia(entity.getLugarProcedencia())
                .telFijo(entity.getTelFijo())
                .telMovil(entity.getTelMovil())

                .parentesco(parentescoConverter.toDto(entity.getParentesco()))
                .familiarCondicion(entity.getFamiliarCondicion())
                .mismaEnfermedad(entity.getMismaEnfermedad())
                .fecDiagnostico(new SimpleDateFormat("dd-MM-yyyy").format(entity.getFecDiagnostico()))
                .detalleEnfermedad(entity.getDetalleEnfermedad())
                .perfil(entity.getPerfil())

                .estado(entity.getEstado())
                .fecRegistro(new SimpleDateFormat("dd-MM-yyyy").format(entity.getFecRegistro()))
                .fecModificacion(new SimpleDateFormat("dd-MM-yyyy").format(entity.getFecModificacion()))
                .build();
    }

    @Override
    protected AntecedenteFamiliarEntity dtoToEntity(AntecedenteFamiliarDto dto) throws Exception {

        AntecedenteFamiliarEntity entity;

        if (dto.getId() == null) {
            entity = new AntecedenteFamiliarEntity();
            entity.setFecRegistro(new Date());
        }else {
            entity = antecedenteFamiliarService.getById(dto.getId());
        }

        entity.setId(dto.getId());

        entity.setNombre(dto.getNombre().trim());
        entity.setApePaterno(dto.getApePaterno().trim());
        entity.setApeMaterno(dto.getApeMaterno().trim());
        entity.setFecNacimiento(new SimpleDateFormat("dd-MM-yyyy").parse(dto.getFecNacimiento()));
        entity.setDocumento(documentoService.getById(dto.getDocumento().getId()));
        entity.setNroDocumento(dto.getNroDocumento());
        entity.setGenero(dto.getGenero());
        entity.setRutaImagen(entity.getRutaImagen());
        entity.setDistritoNacimiento(ubiDistritoService.getById(dto.getDistritoNacimiento().getId()));
        entity.setCondicion(dto.getCondicion());
        entity.setOrigenNacionalidad(dto.getOrigenNacionalidad());
        entity.setOrigenPais(dto.getOrigenPais());
        entity.setOrigenEstado(dto.getOrigenEstado());
        entity.setOrigenCiudad(dto.getOrigenCiudad());
        entity.setLugarNacimiento(dto.getLugarNacimiento());
        entity.setEmail(dto.getEmail());
        entity.setDistritoResidencia(ubiDistritoService.getById(dto.getDistritoResidencia().getId()));
        entity.setDireccionActual(dto.getDireccionActual());
        entity.setLugarProcedencia(dto.getLugarProcedencia());
        entity.setTelFijo(dto.getTelFijo());
        entity.setTelMovil(dto.getTelMovil());

        entity.setParentesco(parentescoService.getById(dto.getParentesco().getId()));
        entity.setFamiliarCondicion(dto.getFamiliarCondicion());
        entity.setMismaEnfermedad(dto.getMismaEnfermedad());
        entity.setFecDiagnostico(new SimpleDateFormat("dd-MM-yyyy").parse(dto.getFecDiagnostico()));
        entity.setDetalleEnfermedad(entity.getDetalleEnfermedad().trim());
        entity.setPerfil(PerfilEnum.ANTECEDENTE_FAMILIAR);

        entity.setEstado(dto.getEstado());
        entity.setFecModificacion(new Date());

        return entity;
    }

}
