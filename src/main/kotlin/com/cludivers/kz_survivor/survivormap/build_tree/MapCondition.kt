package com.cludivers.kz_survivor.survivormap.build_tree

import java.util.function.BooleanSupplier


/**
 * Boolean supplier should implement the test in order to see if the condition is met (true should be return)
 * Or false if the condition is not met, if the condition is not met, it will be return in the condition list.
 * Then the message will be displayed to the user, as well as
 * a suggested fix, implemented in the Runnable run method. The fix message is also displayed to the user.
 */
abstract class MapCondition(
    val message: String,
    val fixMessage: String
): BooleanSupplier, Runnable