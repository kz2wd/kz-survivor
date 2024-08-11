package com.cludivers.kz_survivor.survivormap.build_tree.menu

import com.cludivers.kz_survivor.survivormap.build_tree.SurvivorMapBuild
import jakarta.persistence.*
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class EditableAttributeTest {

    @Entity
    class DummyClass {
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE)
        @Column(name = "id", nullable = false)
        var id: Long? = null

        @EditableAttribute
        val field1 = "field1"

        @EditableAttribute
        var field2 = "field1"

        val nonEditableAttributes = "field 3"

    }

    @Test
    fun collectAttributeDummyTest() {
        val components = UserEditable.collectMenuFields(DummyClass::class)
        System.err.println(components.joinToString { it.name })
        assertEquals(2, components.size)
    }

    @Test
    fun collectAttributeSurvivorMapTest() {
        val components = UserEditable.collectMenuFields(SurvivorMapBuild::class)
        System.err.println(components.joinToString { it.name })
        assertEquals(5, components.size)
    }
}