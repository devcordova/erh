package pe.gob.minsa.erh.model.enums;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public enum NacionalidadEnum {

    NACIONAL("Nacional"),
    EXTRANJERO("Extranjero")
    ;

    private String label;

    NacionalidadEnum(String label) {
        this.label = label;
    }

    public static List<EstadoEnum> findAll() {
        List<EstadoEnum> list = new ArrayList<EstadoEnum>();

        for (EstadoEnum status : EstadoEnum.values()) {
            list.add(status);
        }
        return list;
    }

    public static EstadoEnum findByLabel(String label) {
        if (StringUtils.isNotBlank(label)) {
            for (EstadoEnum status : EstadoEnum.values()) {
                if (status.toString().equalsIgnoreCase(label)) {
                    return status;
                }
            }
        }
        return null;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
