package com.miage.altea.tp.trainer_api.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Pokemon {
    private int pokemonTypeId;

    private int level;
}
