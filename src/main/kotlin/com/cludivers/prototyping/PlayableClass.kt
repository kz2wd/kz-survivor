package com.cludivers.prototyping

import kotlin.reflect.KClass

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
annotation class PlayableClass(val playableClass: KClass<*>)
