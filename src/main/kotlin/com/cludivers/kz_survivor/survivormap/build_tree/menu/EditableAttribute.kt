package com.cludivers.kz_survivor.survivormap.build_tree.menu

import com.cludivers.kz_survivor.survivormap.build_tree.CustomIconBuild


@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class EditableAttribute(val defaultIcon: DefaultIcons)
