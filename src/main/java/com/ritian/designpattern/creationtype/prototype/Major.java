package com.ritian.designpattern.creationtype.prototype;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author ritian.Zhang
 * @date 2019/04/23
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Major implements Cloneable, Serializable {
    private static final long serialVersionUID = 5005705310750902325L;
    private String name;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
