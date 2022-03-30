package com.example.fs.subdirs;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomId implements Serializable {

    private static final long serialVersionUID = -7405672795246402202L;

    String directory;
    String id;
}
