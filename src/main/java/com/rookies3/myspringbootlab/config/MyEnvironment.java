package com.rookies3.myspringbootlab.config;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Getter
@ToString
@Builder
public class MyEnvironment {
    String mode;
}
