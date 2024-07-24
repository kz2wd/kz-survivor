package com.cludivers.kz_survivor.survivormap

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class SurvivorMap {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0
}