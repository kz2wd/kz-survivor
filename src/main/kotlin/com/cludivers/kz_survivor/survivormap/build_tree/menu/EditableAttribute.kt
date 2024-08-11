package com.cludivers.kz_survivor.survivormap.build_tree.menu


@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class EditableAttribute(
    val displayMode: DisplayModes = DisplayModes.PREFER_PREVIEW_DISPLAY,
    val defaultIcon: DefaultIcons = DefaultIcons.DEFAULT
)
