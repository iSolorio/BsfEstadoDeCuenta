package mx.gob.bansefi.EstadoDeCuenta.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by AppWIns on 10/07/2017.
 */
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDTO {

    @Getter @Setter
    private String TEXT_CODE;
    @Getter @Setter
    private String TEXT_ARG1;

}
