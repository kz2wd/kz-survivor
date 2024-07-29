package com.cludivers.prototyping

import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration

class PlayableClassProcessor : SymbolProcessor {
    override fun process(resolver: Resolver): List<KSAnnotated> {
        val buildableClasses = resolver.getSymbolsWithAnnotation(PlayableClass::class.qualifiedName!!)
        buildableClasses.filterIsInstance<KSClassDeclaration>().forEach { ksClass ->

        }
        return emptyList()
    }
}