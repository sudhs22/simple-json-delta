package org.sudhs.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DeltaFormat {
    private String path;
    private String oldValue;
    private String newValue;
}
