package com.woojin.recipick.presentation.add_recipe.title_and_ingredients

/** 분수 표현을 위한 data class */
data class Fraction(val numerator: Int, val denominator: Int) {
    override fun toString(): String {
        //분모가 1이면 분자면 표현, 아니면 분수 표현
        return if (denominator == 1) "$numerator" else "$numerator/$denominator"
    }
}
