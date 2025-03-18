package com.dougfsilva.formativa_gerenciamento_escolar.exception;

import java.io.Serial;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ErroPadrao  implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private long timestamp;
    private Integer status;
    private String error;

}
