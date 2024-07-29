package com.cludivers.kz_survivor.survivormap.build_tree.sectors

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class MapSectorBuild {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0
}